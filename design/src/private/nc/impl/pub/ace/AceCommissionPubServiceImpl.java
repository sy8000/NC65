package nc.impl.pub.ace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.qcco.commission.ace.bp.AceCommissionApproveBP;
import nc.bs.qcco.commission.ace.bp.AceCommissionDeleteBP;
import nc.bs.qcco.commission.ace.bp.AceCommissionInsertBP;
import nc.bs.qcco.commission.ace.bp.AceCommissionSendApproveBP;
import nc.bs.qcco.commission.ace.bp.AceCommissionUnApproveBP;
import nc.bs.qcco.commission.ace.bp.AceCommissionUnSendApproveBP;
import nc.hr.utils.InSQLCreator;
import nc.impl.pubapp.pattern.data.bill.BillLazyQuery;
import nc.impl.pubapp.pattern.data.bill.tool.BillTransferTool;
import nc.impl.pubapp.pattern.data.vo.VODelete;
import nc.impl.pubapp.pattern.data.vo.VOInsert;
import nc.impl.pubapp.pattern.data.vo.VOUpdate;
import nc.md.persist.framework.IMDPersistenceQueryService;
import nc.md.persist.framework.IMDPersistenceService;
import nc.ui.qc.continuebatch.maintain.view.continuebatch_config;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.ISuperVO;
import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.commission.CommissionCVO;
import nc.vo.qcco.commission.CommissionHVO;
import nc.vo.qcco.commission.CommissionRVO;
import nc.vo.qcco.task.AggTaskHVO;
import nc.vo.qcco.task.TaskHVO;
import nc.vo.qcco.task.TaskSVO;

public abstract class AceCommissionPubServiceImpl {
	IMDPersistenceService persist = NCLocator.getInstance().lookup(IMDPersistenceService.class);
	IMDPersistenceQueryService query = NCLocator.getInstance().lookup(IMDPersistenceQueryService.class);
	private BaseDAO dao = null;

	public BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}

	// 新增
	public AggCommissionHVO[] pubinsertBills(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[] originBills)
			throws BusinessException {
		try {
			// 数据库中数据和前台传递过来的差异VO合并后的结果
			BillTransferTool<AggCommissionHVO> transferTool = new BillTransferTool<AggCommissionHVO>(clientFullVOs);
			// 调用BP
			AceCommissionInsertBP action = new AceCommissionInsertBP();
			AggCommissionHVO[] retvos = action.insert(clientFullVOs);
			// 构造返回数据
			// return transferTool.getBillForToClient(retvos);
			// 处理ts问题
			dealTs(retvos);

			return retvos;
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
		return null;
	}

	/**
	 * 孙表存储时,要造成ts不一致的问题
	 * 
	 * @param vos
	 * @throws BusinessException
	 */
	private void dealTs(AggCommissionHVO[] vos) throws BusinessException {
		if (vos != null && vos.length > 0) {
			for (AggCommissionHVO hvo : vos) {
				if (hvo == null) {
					continue;
				}
				// 更新孙表的ts
				String sql = " update qc_commission_r "
						+ " set ts = (select ts from qc_commission_h where PK_COMMISSION_H = '" + hvo.getPrimaryKey()
						+ "') " + " where PK_COMMISSION_R in ( " + " select PK_COMMISSION_R from qc_commission_r r "
						+ " left join qc_commission_b b on b.PK_COMMISSION_B = r.PK_COMMISSION_B "
						+ " left join qc_commission_h h on h.PK_COMMISSION_H = h.PK_COMMISSION_H "
						+ " where h.PK_COMMISSION_H = '" + hvo.getPrimaryKey() + "' " + "  ) ";
				this.getDao().executeUpdate(sql);
			}

		}

	}

	// 删除
	public void pubdeleteBills(AggCommissionHVO[] vos) throws BusinessException {
		try {
			List<String> list = new ArrayList<String>();
			for (AggCommissionHVO vo : vos) {
				list.add((String) vo.getParent().getAttributeValue("pk_commission_h"));
			}
			InSQLCreator insql = new InSQLCreator();
			String commissionInSQL = insql.getInSQL(list.toArray(new String[0]));
			List<CommissionHVO> lists = (List<CommissionHVO>) this.getDao().retrieveByClause(CommissionHVO.class,
					"pk_commission_h in(" + commissionInSQL + ")");
			if (lists.size() > 0) {
				for (AggCommissionHVO vo : vos) {
					CircularlyAccessibleValueObject[] bvos = (CircularlyAccessibleValueObject[]) vo.getChildrenVO();
					for (CommissionHVO hvo : lists) {
						vo.getParentVO().setTs(hvo.getTs());
						if (null != hvo.getAttributeValue("pk_commission_h")
								&& vo.getParentVO().getAttributeValue("pk_commission_h") != null
								&& hvo.getAttributeValue("pk_commission_h").equals(
										vo.getParentVO().getAttributeValue("pk_commission_h"))) {
							for (CircularlyAccessibleValueObject bvo : bvos) {
								((CommissionBVO) bvo).setTs(hvo.getTs());
								CommissionRVO[] rvos = ((CommissionBVO) bvo).getPk_commission_r();
								if (null != rvos) {
									for (CommissionRVO rvo : rvos) {
										rvo.setTs(hvo.getTs());
									}
								}
							}
						}
					}

				}
			}
			BillTransferTool<AggCommissionHVO> transferTool = new BillTransferTool<AggCommissionHVO>(
					(AggCommissionHVO[]) vos);
			AggCommissionHVO[] fullBills = transferTool.getClientFullInfoBill();
			String[] tableCodes = fullBills[0].getTableCodes();
			for (String tableCode : tableCodes) {
				SuperVO[] originChildrens = (SuperVO[]) fullBills[0].getTableVO(tableCode);
				// 注意多子对多孙,map需要区分
				Map<String, SuperVO[]> originGrandMap = new HashMap<String, SuperVO[]>();
				for (SuperVO childVO : originChildrens) {
					// 将当前页签下的当前子的所有孙都查询出来了,并赋值给originBills中的孙。
					String originChildPK = ((CommissionBVO) childVO).getPrimaryKey();
					Collection originGVOs = query.queryBillOfVOByCond(CommissionRVO.class, "pk_commission_b = '"
							+ originChildPK + "'", false);
					CommissionRVO[] originGrandvos = (CommissionRVO[]) originGVOs.toArray(new CommissionRVO[originGVOs
							.size()]);
					((CommissionBVO) childVO).setPk_commission_r(originGrandvos);
					for (int i = 0; originGrandvos != null && i < originGrandvos.length; i++) {
						persist.deleteBill(originGrandvos[i]);
					}
					// this.deleteVO((List<ISuperVO>) originGVOs);
				}
			}

			AceCommissionDeleteBP deleteBP = new AceCommissionDeleteBP();
			deleteBP.delete(fullBills);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}

	}

	// 修改
	public AggCommissionHVO[] pubupdateBills(AggCommissionHVO[] vos) throws BusinessException {
		AggCommissionHVO[] vosClone = vos.clone();
		// 删除原vo
		deleteOldVO(vos);
		AggCommissionHVO[] vosNew = deal2New(vosClone);
		// 创建时间
		List<UFDateTime> createTimeList = new ArrayList<>();
		for (AggCommissionHVO aggTask : vos) {
			createTimeList.add(aggTask.getParentVO().getCreationtime());
		}
		List<String> createtorList = new ArrayList<>();
		for (AggCommissionHVO aggTask : vos) {
			createtorList.add(aggTask.getParentVO().getCreator());
		}
		// 处理审计信息-前
		dealPubBefore(vosNew);
		AggCommissionHVO[] rtnVO = pubinsertBills(vosNew, null);
		// 处理审计信息-后
		dealPubAfter(rtnVO, createTimeList, createtorList);
		return rtnVO;
	}
	
	private void dealPubBefore(AggCommissionHVO[] vosNew) throws DAOException {
		if (null == vosNew) {
			return;
		}
		for (int i = 0; i < vosNew.length; i++) {
			if (vosNew[i] != null) {
				CommissionHVO newVO = vosNew[i].getParentVO();
				if (newVO != null) {
					UFDateTime ts = new UFDateTime();
					newVO.setTs(ts);
					newVO.setModifiedtime(ts);
					newVO.setModifier(InvocationInfoProxy.getInstance().getUserId());
					newVO.setLastmaketime(ts);
				}
			}
		}
	}
	
	private void dealPubAfter(AggCommissionHVO[] vosNew, List<UFDateTime> createTimeList,List<String> createtorList) throws DAOException {
		if (null == vosNew) {
			return;
		}
		for (int i = 0; i < vosNew.length; i++) {
			if (vosNew[i] != null && createTimeList.get(i) != null) {
				CommissionHVO newVO = vosNew[i].getParentVO();

				newVO.setCreationtime(createTimeList.get(i));
				newVO.setCreator(createtorList.get(i));
				getDao().executeUpdate(
						"update qc_commission_h set creationtime = '" + createTimeList.get(i) + "' where billno = '" + newVO.getBillno() + "'");
				getDao().executeUpdate(
						"update qc_commission_h set creator = '" + (createtorList.get(i)==null?InvocationInfoProxy.getInstance().getUserId():createtorList.get(i))
								+ "' where billno = '" + newVO.getBillno() + "'");

			}
		}
	}

	/**
	 * delete
	 * 
	 * @param vos
	 * @throws BusinessException
	 */
	private void deleteOldVO(AggCommissionHVO[] vos) throws BusinessException {
		Set<String> deletePks = new HashSet();
		for (AggCommissionHVO vo : vos) {
			deletePks.add(vo.getPrimaryKey());
		}
		// 子表删除
		getDao().deleteByPKs(CommissionHVO.class, deletePks.toArray(new String[0]));

		InSQLCreator insql = new InSQLCreator();
		String deletePksInSQL = insql.getInSQL(deletePks.toArray(new String[0]));
		// 子表切换
		getDao().executeUpdate("update qc_commission_b set dr = 0 where PK_COMMISSION_h in (" + deletePksInSQL + ")");
		// 孙表切换
		getDao().executeUpdate(
				"update qc_commission_r set dr = 0 where pk_commission_r in ( "
						+ " select pk_commission_r from qc_commission_r r "
						+ " left join qc_commission_b b on b.PK_COMMISSION_b = r.PK_COMMISSION_b "
						+ " where b.PK_COMMISSION_h in(" + deletePksInSQL + ") ) ");

	}

	

	/**
	 * 包装成一个新数据
	 * 
	 * @param Bills
	 * @throws DAOException
	 */
	private AggCommissionHVO[] deal2New(AggCommissionHVO[] Bills) throws DAOException {
		if (Bills != null) {
			for (AggCommissionHVO aggvo : Bills) {
				if (aggvo != null && aggvo.getChildren(CommissionBVO.class) != null && aggvo.getParentVO() != null) {
					aggvo.getParentVO().setTs(null);
					aggvo.getParentVO().setPk_commission_h(null);
					aggvo.getParentVO().setStatus(2);
					//aggvo.getParentVO().setCreator(null);
					//aggvo.getParentVO().setCreationtime(null);
					aggvo.getParentVO().setModifiedtime(null);
					aggvo.getParentVO().setModifier(null);
					CommissionBVO[] bvos = (CommissionBVO[]) (aggvo.getChildren(CommissionBVO.class));
					List<CommissionBVO> bvoList = new ArrayList<>();
					for (CommissionBVO bvo : bvos) {
						if (bvo != null) {
							if(3==bvo.getStatus()){
								continue;
							}
							bvo.setPk_commission_h(null);
							bvo.setPk_commission_b(null);
							bvo.setTs(null);
							bvo.setStatus(2);
							if (bvo.getPk_commission_r() != null) {
								List<CommissionRVO> tempList = new ArrayList<>();
								for (CommissionRVO rvo : bvo.getPk_commission_r()) {
									if (rvo != null) {
										if(3==rvo.getStatus()){
											continue;
										}
										rvo.setPk_commission_b(null);
										rvo.setPk_commission_r(null);
										rvo.setTs(null);
										rvo.setStatus(2);
										tempList.add(rvo);
									}
								}
								bvo.setPk_commission_r(tempList.toArray(new CommissionRVO[0]));
							}
						}
						bvoList.add(bvo);
					}
					aggvo.setChildren(CommissionBVO.class, bvoList.toArray(new CommissionBVO[0]));
					CommissionCVO[] cvos = (CommissionCVO[]) (aggvo.getChildren(CommissionCVO.class));
					List<CommissionCVO> cvoList = new ArrayList<>();
					if (cvos != null && cvos.length > 0) {
						for (CommissionCVO cvo : cvos) {
							if (cvo != null) {
								if(3==cvo.getStatus()){
									continue;
								}
								cvo.setPk_commission_h(null);
								cvo.setPk_commission_c(null);
								cvo.setTs(null);
								cvo.setStatus(2);
							}
							cvoList.add(cvo);
						}
					}
					aggvo.setChildren(CommissionCVO.class, cvoList.toArray(new CommissionCVO[0]));
				}
			}
		}
		return Bills;
	}

	// 参考 BillUpdate.persistent方法
	private void persistent(Map<IVOMeta, List<ISuperVO>> fullGrandVOs, Map<IVOMeta, List<ISuperVO>> originGrandVOs) {
		Map<IVOMeta, List<ISuperVO>> originIndex = new HashMap<IVOMeta, List<ISuperVO>>();
		Map<IVOMeta, List<ISuperVO>> deleteIndex = new HashMap<IVOMeta, List<ISuperVO>>();
		Map<IVOMeta, List<ISuperVO>> newIndex = new HashMap<IVOMeta, List<ISuperVO>>();
		Map<IVOMeta, List<ISuperVO>> updateIndex = new HashMap<IVOMeta, List<ISuperVO>>();

		for (List<ISuperVO> list : fullGrandVOs.values()) {
			this.process(list, originGrandVOs, originIndex, deleteIndex, newIndex, updateIndex);
		}
		this.persistent(originIndex, deleteIndex, newIndex, updateIndex);

	}

	private void process(List<ISuperVO> list, Map<IVOMeta, List<ISuperVO>> originGrandVOs,
			Map<IVOMeta, List<ISuperVO>> originIndex, Map<IVOMeta, List<ISuperVO>> deleteIndex,
			Map<IVOMeta, List<ISuperVO>> newIndex, Map<IVOMeta, List<ISuperVO>> updateIndex) {
		for (ISuperVO vo : list) {
			this.process(vo, originGrandVOs, originIndex, deleteIndex, newIndex, updateIndex);
		}
	}

	private void process(ISuperVO vo, Map<IVOMeta, List<ISuperVO>> originGrandVOs,
			Map<IVOMeta, List<ISuperVO>> originIndex, Map<IVOMeta, List<ISuperVO>> deleteIndex,
			Map<IVOMeta, List<ISuperVO>> newIndex, Map<IVOMeta, List<ISuperVO>> updateIndex) {
		IVOMeta voMeta = vo.getMetaData();

		int status = vo.getStatus();
		if (status == VOStatus.NEW) {
			List<ISuperVO> list = this.get(voMeta, newIndex);
			list.add(vo);
		} else if (status == VOStatus.UPDATED) {
			List<ISuperVO> updateList = this.get(voMeta, updateIndex);
			updateList.add(vo);

			// 根据当前vo获取原始vo
			ISuperVO originVO = this.get(originGrandVOs, vo.getMetaData(), vo.getPrimaryKey());
			List<ISuperVO> originList = this.get(voMeta, originIndex);
			originList.add(originVO);
		} else if (status == VOStatus.DELETED) {
			List<ISuperVO> list = this.get(voMeta, deleteIndex);
			list.add(vo);
		}
	}

	private void persistent(Map<IVOMeta, List<ISuperVO>> originIndex, Map<IVOMeta, List<ISuperVO>> deleteIndex,
			Map<IVOMeta, List<ISuperVO>> newIndex, Map<IVOMeta, List<ISuperVO>> updateIndex) {
		for (List<ISuperVO> list : deleteIndex.values()) {
			this.deleteVO(list);
		}
		for (List<ISuperVO> list : newIndex.values()) {
			this.insertVO(list);
		}
		for (Entry<IVOMeta, List<ISuperVO>> entry : updateIndex.entrySet()) {
			List<ISuperVO> list = entry.getValue();
			List<ISuperVO> originList = originIndex.get(entry.getKey());
			this.updateVO(list, originList);
		}
	}

	// 参考BillIndex，结合两个vo，利用元数据库中的vo的pk在界面传来的值中查找
	private Map<IVOMeta, List<ISuperVO>> getFullGrandVOs(Map<IVOMeta, List<ISuperVO>> fullGrandVOs,
			Map<IVOMeta, List<ISuperVO>> originGrandVOs) {
		if (originGrandVOs == null || originGrandVOs.size() == 0)
			return fullGrandVOs;
		//
		// 应该如何获取meta？
		// 可能会有问题
		//
		for (Iterator itmeta = originGrandVOs.keySet().iterator(); itmeta.hasNext();) {
			IVOMeta meta = (IVOMeta) itmeta.next();
			List<ISuperVO> originvos = originGrandVOs.get(meta);
			if (originvos == null || originvos.size() == 0)
				continue;
			for (Iterator itvo = originvos.iterator(); itvo.hasNext();) {
				ISuperVO originvo = (ISuperVO) itvo.next();
				String pk = originvo.getPrimaryKey();
				if (pk != null) {
					ISuperVO vo = findGrandVOByPk(fullGrandVOs.get(meta), pk);
					if (vo == null) {
						originvo.setStatus(VOStatus.DELETED);
						if (fullGrandVOs.get(meta) == null || fullGrandVOs.get(meta).size() == 0) {
							List<ISuperVO> list = new ArrayList<ISuperVO>();
							list.add(originvo);
							fullGrandVOs.put(meta, list);
						} else
							fullGrandVOs.get(meta).add(originvo);
					}
				}
			}
		}
		return fullGrandVOs;
	}

	/**
	 * 根据孙实体元数据、孙实体主键获取实体
	 * 
	 * @param voMeta
	 *            孙实体元数据
	 * @param key
	 *            孙实体主键
	 * @return 孙实体
	 */
	public ISuperVO get(Map<IVOMeta, List<ISuperVO>> originGrandVOs, IVOMeta voMeta, String key) {
		if (originGrandVOs.containsKey(voMeta)) {
			return findGrandVOByPk(originGrandVOs.get(voMeta), key);
		}
		return null;
	}

	private ISuperVO findGrandVOByPk(List<ISuperVO> originGrandVOs, String key) {
		if (originGrandVOs == null || originGrandVOs.size() == 0)
			return null;
		Iterator it = originGrandVOs.iterator();
		while (it.hasNext()) {
			SuperVO grandvo = (SuperVO) it.next();
			if (grandvo.getPrimaryKey() != null && grandvo.getPrimaryKey().equals(key)) {
				return grandvo;
			}
		}
		return null;
	}

	private void updateVO(List<ISuperVO> list, List<ISuperVO> originList) {
		VOUpdate<ISuperVO> bo = new VOUpdate<ISuperVO>();
		int length = list.size();
		if (length > 0) {
			ISuperVO[] vos = new ISuperVO[length];
			vos = list.toArray(vos);

			ISuperVO[] originVOs = new ISuperVO[length];
			originVOs = originList.toArray(originVOs);

			bo.update(vos, originVOs);
		}
	}

	private void deleteVO(List<ISuperVO> list) {
		VODelete<ISuperVO> bo = new VODelete<ISuperVO>();
		int length = list.size();
		if (length > 0) {
			ISuperVO[] vos = new ISuperVO[length];
			vos = list.toArray(vos);
			bo.delete(vos);
		}
	}

	private void insertVO(List<ISuperVO> list) {
		VOInsert<ISuperVO> bo = new VOInsert<ISuperVO>();
		int length = list.size();
		if (length > 0) {
			ISuperVO[] vos = new ISuperVO[length];
			vos = list.toArray(vos);
			bo.insert(vos);
		}
	}

	public AggCommissionHVO[] pubquerybills(IQueryScheme queryScheme) throws BusinessException {
		AggCommissionHVO[] bills = null;
		try {
			this.preQuery(queryScheme);
			BillLazyQuery<AggCommissionHVO> query = new BillLazyQuery<AggCommissionHVO>(AggCommissionHVO.class);
			bills = query.query(queryScheme, null);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
		return bills;
	}

	private List<ISuperVO> get(IVOMeta voMeta, Map<IVOMeta, List<ISuperVO>> index) {
		if (index.containsKey(voMeta)) {
			return index.get(voMeta);
		}
		List<ISuperVO> list = new ArrayList<ISuperVO>();
		index.put(voMeta, list);
		return list;
	}

	/**
	 * 由子类实现，查询之前对queryScheme进行加工，加入自己的逻辑
	 * 
	 * @param queryScheme
	 */
	protected void preQuery(IQueryScheme queryScheme) {
		// 查询之前对queryScheme进行加工，加入自己的逻辑
	}

	// 提交
	public AggCommissionHVO[] pubsendapprovebills(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[] originBills)
			throws BusinessException {
		AceCommissionSendApproveBP bp = new AceCommissionSendApproveBP();
		AggCommissionHVO[] retvos = bp.sendApprove(clientFullVOs, originBills);
		return retvos;
	}

	// 收回
	public AggCommissionHVO[] pubunsendapprovebills(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[] originBills)
			throws BusinessException {
		AceCommissionUnSendApproveBP bp = new AceCommissionUnSendApproveBP();
		AggCommissionHVO[] retvos = bp.unSend(clientFullVOs, originBills);
		return retvos;
	};

	// 审批
	public AggCommissionHVO[] pubapprovebills(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[] originBills)
			throws BusinessException {
		for (int i = 0; clientFullVOs != null && i < clientFullVOs.length; i++) {
			clientFullVOs[i].getParentVO().setStatus(VOStatus.UPDATED);
		}
		AceCommissionApproveBP bp = new AceCommissionApproveBP();
		AggCommissionHVO[] retvos = bp.approve(clientFullVOs, originBills);
		return retvos;
	}

	// 弃审

	public AggCommissionHVO[] pubunapprovebills(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[] originBills)
			throws BusinessException {
		for (int i = 0; clientFullVOs != null && i < clientFullVOs.length; i++) {
			clientFullVOs[i].getParentVO().setStatus(VOStatus.UPDATED);
		}
		AceCommissionUnApproveBP bp = new AceCommissionUnApproveBP();
		AggCommissionHVO[] retvos = bp.unApprove(clientFullVOs, originBills);
		return retvos;
	}

}