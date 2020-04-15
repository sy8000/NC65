package nc.impl.pubapp.uif2app.components.grand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.impl.pubapp.pattern.data.bill.BillLazyQuery;
import nc.itf.pubapp.uif2app.components.grand.IGrandAggVosQueryService;
import nc.md.common.AssociationKind;
import nc.md.data.access.NCObject;
import nc.md.model.IAssociation;
import nc.md.model.IAttribute;
import nc.md.model.IBean;
import nc.md.model.ICardinality;
import nc.md.model.MetaDataException;
import nc.md.persist.framework.IMDPersistenceQueryService;
import nc.ui.pubapp.uif2app.components.grand.util.ArrayUtil;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.pubapp.pattern.pub.SqlBuilder;

public class GrandAggVosQueryServiceImpl implements IGrandAggVosQueryService {

	private Map<String, List<Object>> childWithGrandMap = new HashMap<String, List<Object>>();

	private Map<String, Object> childListMap = new HashMap<String, Object>();

	public AbstractBill getAllListGrandVOS(AggregatedValueObject selectObj,
			HashMap<String, CircularlyAccessibleValueObject> grandrelationship) {

		AbstractBill bill = (AbstractBill) selectObj;
		String[] tabCodes = ((AbstractBill) selectObj).getTableCodes();
		for (String tabCode : tabCodes) {
			CircularlyAccessibleValueObject[] childVos = bill.getTableVO(tabCode);
			Map<Class<?>, String> classVOcontidion = getClassRelateSqlMap(childVos, grandrelationship);
			childListMap.clear();
			if (classVOcontidion.size() != 0) {
				for (Class<?> classVO : classVOcontidion.keySet()) {
					childWithGrandMap.clear();
					String condition = classVOcontidion.get(classVO);
					sortGrandVOTOByPk(classVO, condition);
					getChildVOListWithGrand(childVos, classVO, grandrelationship);
				}
			} else {
				if (childVos != null && childVos.length > 0) {
					for (CircularlyAccessibleValueObject childvo : childVos) {
						childListMap.put(getSuperVOpk(childvo), childvo);
					}
				}
			}
			List<Object> childList = getChildList();
			bill.setTableVO(tabCode, childList.toArray(new SuperVO[0]));
		}
		return bill;
	}

	private List<Object> getChildList() {

		List<Object> childList = new ArrayList<Object>();
		Set<String> primarySet = childListMap.keySet();
		for (String primary : primarySet) {
			childList.add(childListMap.get(primary));
		}
		return childList;
	}

	/**
	 * 将孙表设置到对应子表的属性上去
	 * 
	 * @param childVos
	 *            子表数组
	 * @param classVO
	 *            某一个孙表的类名
	 * @param grandrelationship
	 *            子表和孙表的对应关系
	 */
	private void getChildVOListWithGrand(CircularlyAccessibleValueObject[] childVos, Class<?> classVO,
			HashMap<String, CircularlyAccessibleValueObject> grandrelationship) {

		for (CircularlyAccessibleValueObject childVo : childVos) {
			String primaryKey = getSuperVOpk(childVo);
			List<Object> grandVOList = childWithGrandMap.get(primaryKey);
			if (grandVOList != null && grandVOList.size() > 0) {
				List<IAttribute> attrList = getGrandattribute(childVo);
				for (IAttribute iAttribute : attrList) {
					if (grandrelationship.get(iAttribute.getName()).getClass().equals(classVO)) {
						iAttribute.getAccessStrategy().setValue(childVo, iAttribute,
								grandVOList.toArray(ArrayUtil.toArray(grandVOList.get(0))));
						break;
					}
				}
			}
			childListMap.put(primaryKey, childVo);
		}
	}

	/**
	 * 获取孙表的class类名和子表和孙表的外键关系的Map
	 * 
	 * @param childVos
	 *            某个子页签的所有子表数据
	 * @param grandrelationship
	 *            子表和孙表的对应关系
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Map<Class<?>, String> getClassRelateSqlMap(CircularlyAccessibleValueObject[] childVos,
			HashMap<String, CircularlyAccessibleValueObject> grandrelationship) {

		Map<Class<?>, String> classVOcontidion = new HashMap<Class<?>, String>();
		if (childVos != null && childVos.length > 0) {
			// 收集子表主键
			List<String> chidrenKeys = resaveAllChildPk(childVos);
			// 收集孙表类
			List<Class<?>> classVoList = resaveGrandClass(grandrelationship, childVos[0]);

			SqlBuilder condition = new SqlBuilder();
			String fk_name = ((SuperVO) childVos[0]).getPKFieldName();
			condition.append(fk_name, chidrenKeys.toArray(new String[0]));
			String sql = condition.toString() + " and dr != '1' ";
			for (Class<?> classVo : classVoList) {
				classVOcontidion.put(classVo, sql);
			}
		}
		return classVOcontidion;
	}

	/**
	 * 将孙表按照子表数据分类在childWithGrandMap里面放好
	 * 
	 * @param grandVOList
	 *            孙表数据
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private void sortGrandVOTOByPk(Class<?> classVO, String condition) {

		ArrayList<Object> grandVOList = this.queryGrandListVO(classVO, condition);
		if (grandVOList != null && grandVOList.size() > 0) {
			for (Object obj : grandVOList) {
				String parent = ((SuperVO) obj).getParentPKFieldName();
				String childPrimary = (String) ((SuperVO) obj).getAttributeValue(parent);
				resaveGrandMap(childPrimary, obj);
			}
		}
	}

	private List<Class<?>> resaveGrandClass(HashMap<String, CircularlyAccessibleValueObject> grandrelationship,
			CircularlyAccessibleValueObject childVo) {
		List<Class<?>> classVoList = new ArrayList<Class<?>>();
		List<IAttribute> attrList = getGrandattribute(childVo);
		if (attrList != null && attrList.size() > 0) {
			for (IAttribute grandattr : attrList) {
				if (childVo != null && getSuperVOpk(childVo) != null) {
					Class<?> classVO = grandrelationship.get(grandattr.getName()).getClass();
					classVoList.add(classVO);
				}
			}
		}
		return classVoList;
	}

	private List<String> resaveAllChildPk(CircularlyAccessibleValueObject[] childVos) {

		List<String> chidrenKeys = new ArrayList<String>();
		for (CircularlyAccessibleValueObject childVo : childVos) {
			chidrenKeys.add(getSuperVOpk(childVo));
		}
		return chidrenKeys;
	}

	// 获取CAVO的主键
	private String getSuperVOpk(CircularlyAccessibleValueObject childVO) {

		String primaryKey = null;
		try {
			primaryKey = childVO.getPrimaryKey();
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
		}
		return primaryKey;
	}

	private List<IAttribute> getGrandattribute(CircularlyAccessibleValueObject childVo) {
		NCObject ncObject = NCObject.newInstance(childVo);
		IBean bean = ncObject.getRelatedBean();
		// 获取子表中对应孙实体的属性列表
		List<IAttribute> attrList = this.queryChildAttr(bean);
		return attrList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AbstractBill[] query(IQueryScheme scheme, String order,
			HashMap<String, CircularlyAccessibleValueObject> relationShip, Class<?> clazz) {

		BillLazyQuery query = new BillLazyQuery(clazz);
		AbstractBill[] result = (AbstractBill[]) query.query(scheme, order);
		if (result != null && result.length > 0) {
			AggregatedValueObject firstAggVO = this.getAllListGrandVOS(result[0], relationShip);
			result[0] = (AbstractBill) firstAggVO;
		}
		return result;
	}

	// 调用数据库查询
	@SuppressWarnings("unchecked")
	private ArrayList<Object> queryGrandListVO(Class<?> classVO, String condition) {
		ArrayList<Object> grandVOList = new ArrayList<Object>();
		IMDPersistenceQueryService queryservice = NCLocator.getInstance().lookup(IMDPersistenceQueryService.class);
		try {
			grandVOList = (ArrayList<Object>) queryservice.queryBillOfVOByCond(classVO, condition, false);
		} catch (MetaDataException e) {
			Logger.error(e.getMessage(), e);
		}
		return grandVOList;
	}

	/**
	 * 获取子表中对应孙实体的属性列表
	 */
	private List<IAttribute> queryChildAttr(IBean bean) {
		List<IAttribute> attrList = new ArrayList<IAttribute>();
		List<IAssociation> assoList = bean.getAssociationsByKind(AssociationKind.Composite, ICardinality.ASS_ONE2MANY);
		if (assoList == null || assoList.isEmpty()) {
			return attrList;
		}
		for (IAssociation assoTemp : assoList) {
			IAttribute attrTemp = assoTemp.getStartAttribute();
			attrList.add(attrTemp);
		}
		return attrList;
	}

	/**
	 * 分类算法的具体实现
	 * 
	 * @param childPrimary
	 *            子表主键
	 * @param obj
	 *            孙表VO
	 */
	private void resaveGrandMap(String childPrimary, Object obj) {

		Set<String> primarykeySet = childWithGrandMap.keySet();
		if (primarykeySet.contains(childPrimary)) {
			List<Object> childList = childWithGrandMap.get(childPrimary);
			childList.add(obj);
			childWithGrandMap.put(childPrimary, childList);
		} else {
			List<Object> childList = new ArrayList<Object>();
			childList.add(obj);
			childWithGrandMap.put(childPrimary, childList);
		}
	}
}
