package nc.impl.pub.ace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.qcco.task.ace.bp.AceTaskApproveBP;
import nc.bs.qcco.task.ace.bp.AceTaskDeleteBP;
import nc.bs.qcco.task.ace.bp.AceTaskInsertBP;
import nc.bs.qcco.task.ace.bp.AceTaskSendApproveBP;
import nc.bs.qcco.task.ace.bp.AceTaskUnApproveBP;
import nc.bs.qcco.task.ace.bp.AceTaskUnSendApproveBP;
import nc.bs.qcco.task.ace.bp.AceTaskUpdateBP;
import nc.hr.utils.InSQLCreator;
import nc.impl.pubapp.pattern.data.bill.BillLazyQuery;
import nc.impl.pubapp.pattern.data.bill.tool.BillTransferTool;
import nc.impl.pubapp.pattern.data.vo.VODelete;
import nc.impl.pubapp.pattern.data.vo.VOInsert;
import nc.impl.pubapp.pattern.data.vo.VOUpdate;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.md.persist.framework.IMDPersistenceQueryService;
import nc.md.persist.framework.IMDPersistenceService;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ISuperVO;
import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.qcco.commission.CommissionHVO;
import nc.vo.qcco.task.AggTaskHVO;
import nc.vo.qcco.task.TaskBVO;
import nc.vo.qcco.task.TaskHVO;
import nc.vo.qcco.task.TaskRVO;
import nc.vo.qcco.task.TaskSVO;

public abstract class AceTaskPubServiceImpl {
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
	public AggTaskHVO[] pubinsertBills(AggTaskHVO[] clientFullVOs, AggTaskHVO[] originBills) throws BusinessException {
		try {
			// 数据库中数据和前台传递过来的差异VO合并后的结果
			BillTransferTool<AggTaskHVO> transferTool = new BillTransferTool<AggTaskHVO>(clientFullVOs);
			// 调用BP
			AceTaskInsertBP action = new AceTaskInsertBP();
			AggTaskHVO[] retvos = action.insert(clientFullVOs);
			// 构造返回数据
			// return transferTool.getBillForToClient(retvos);
			return retvos;
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
		return null;
	}

	// 删除
	public void pubdeleteBills(AggTaskHVO[] vos) throws BusinessException {
		try {
			BillTransferTool<AggTaskHVO> transferTool = new BillTransferTool<AggTaskHVO>((AggTaskHVO[]) vos);
			AggTaskHVO[] fullBills = transferTool.getClientFullInfoBill();
			String[] tableCodes = fullBills[0].getTableCodes();
			for (String tableCode : tableCodes) {
				SuperVO[] originChildrens = (SuperVO[]) fullBills[0].getTableVO(tableCode);
				// 注意多子对多孙,map需要区分
				Map<String, SuperVO[]> originGrandMap = new HashMap<String, SuperVO[]>();
				for (SuperVO childVO : originChildrens) {
					// 将当前页签下的当前子的所有孙都查询出来了,并赋值给originBills中的孙。
					String originChildPK = ((TaskBVO) childVO).getPrimaryKey();
					Collection originRVOs = query.queryBillOfVOByCond(TaskRVO.class, "pk_task_b = '" + originChildPK
							+ "'", false);
					TaskRVO[] originGrandvos = (TaskRVO[]) originRVOs.toArray(new TaskRVO[originRVOs.size()]);
					((TaskBVO) childVO).setPk_task_r(originGrandvos);
					for (int i = 0; originGrandvos != null && i < originGrandvos.length; i++) {
						// originGrandvos[i].setDr(1);
						persist.deleteBill(originGrandvos[i]);
					}
					Collection originSVOs = query.queryBillOfVOByCond(TaskSVO.class, "pk_task_b = '" + originChildPK
							+ "'", false);
					TaskSVO[] soriginGrandvos = (TaskSVO[]) originSVOs.toArray(new TaskSVO[originSVOs.size()]);
					((TaskBVO) childVO).setPk_task_s(soriginGrandvos);

					for (int i = 0; soriginGrandvos != null && i < soriginGrandvos.length; i++) {
						// soriginGrandvos[i].setDr(1);
						persist.deleteBill(soriginGrandvos[i]);
					}
					// this.deleteVO((List<ISuperVO>) originRVOs);
				}
			}

			AceTaskDeleteBP deleteBP = new AceTaskDeleteBP();
			deleteBP.delete(fullBills);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}

	}

	// 修改
	public AggTaskHVO[] pubupdateBills(AggTaskHVO[] vos) throws BusinessException {
		AggTaskHVO[] vosClone = vos.clone();
		// 删除原vo
		deleteOldVO(vos);
		AggTaskHVO[] vosNew = deal2New(vosClone);
		//创建时间
		List<UFDateTime> createTimeList = new ArrayList<>();
		for(AggTaskHVO aggTask : vos){
			createTimeList.add(aggTask.getParentVO().getCreationtime());
		}
		List<String> createtorList = new ArrayList<>();
		for(AggTaskHVO aggTask : vos){
			createtorList.add(aggTask.getParentVO().getCreator());
		}
		// 处理审计信息-前
		dealPubBefore(vosNew);
		AggTaskHVO[] rtnVO = pubinsertBills(vosNew, null);
		// 处理审计信息-后
		dealPubAfter(rtnVO, createTimeList,createtorList);
		return rtnVO;
	}
	private void dealPubBefore(AggTaskHVO[] vosNew) throws DAOException {
		if (null == vosNew) {
			return;
		}
		for (int i = 0; i < vosNew.length; i++) {
			if (vosNew[i] != null) {
				TaskHVO newVO = vosNew[i].getParentVO();
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
	
	private void dealPubAfter(AggTaskHVO[] vosNew, List<UFDateTime> createTimeList,List<String> createtorList) throws DAOException {
		if (null == vosNew) {
			return;
		}
		for (int i = 0; i < vosNew.length; i++) {
			if (vosNew[i] != null && createTimeList.get(i) != null&&createtorList.get(i) != null) {
				TaskHVO newVO = vosNew[i].getParentVO();

				newVO.setCreationtime(createTimeList.get(i));
				newVO.setCreator(createtorList.get(i));
				getDao().executeUpdate(
						"update qc_task_h set creationtime = '" + createTimeList.get(i) + "' where billno = '" + newVO.getBillno() + "'");
				getDao().executeUpdate(
						"update qc_task_h set creator = '" + createtorList.get(i) + "' where billno = '" + newVO.getBillno() + "'");
				//tank 
				getDao().executeUpdate(
						"update qc_task_h set billid = '" + newVO.getPk_task_h() + "' where billno = '" + newVO.getBillno() + "'");
				getDao().executeUpdate(
						"update qc_task_h set billversionpk = '" + newVO.getPk_task_h() + "' where billno = '" + newVO.getBillno() + "'");

			}
		}
	}
	/**
	 * 包装成一个新数据
	 * 
	 * @param Bills
	 * @throws DAOException
	 */
	private AggTaskHVO[] deal2New(AggTaskHVO[] Bills) throws DAOException {
		if (Bills != null) {
			for (AggTaskHVO aggvo : Bills) {
				if (aggvo != null && aggvo.getChildren(TaskBVO.class) != null && aggvo.getParentVO() != null) {
					aggvo.getParentVO().setTs(null);
					aggvo.getParentVO().setPk_task_h(null);
					aggvo.getParentVO().setStatus(2);
					//aggvo.getParentVO().setCreator(null);
					//aggvo.getParentVO().setCreationtime(null);
					aggvo.getParentVO().setModifiedtime(null);
					aggvo.getParentVO().setModifier(null);
					TaskBVO[] bvos = (TaskBVO[]) (aggvo.getChildren(TaskBVO.class));
					List<TaskBVO> bvoList = new ArrayList<>();
					for (TaskBVO bvo : bvos) {
						if (bvo != null) {
							if(3==bvo.getStatus()){
								continue;
							}
							bvo.setPk_task_h(null);
							bvo.setPk_task_b(null);
							bvo.setTs(null);
							bvo.setStatus(2);
							if (bvo.getPk_task_r() != null) {
								List<TaskRVO> tempList = new ArrayList<>();
								for (TaskRVO rvo : bvo.getPk_task_r()) {
									if (rvo != null) {
										if(3==rvo.getStatus()){
											continue;
										}
										rvo.setPk_task_b(null);
										rvo.setPk_task_r(null);
										rvo.setTs(null);
										rvo.setStatus(2);
										tempList.add(rvo);
									}
								}
								bvo.setPk_task_r(tempList.toArray(new TaskRVO[0]));
							}
							if (bvo.getPk_task_s() != null) {
								List<TaskSVO> tempList = new ArrayList<>();
								for (TaskSVO svo : bvo.getPk_task_s()) {
									if (svo != null) {
										if(3==svo.getStatus()){
											continue;
										}
										svo.setPk_task_b(null);
										svo.setPk_task_s(null);
										svo.setTs(null);
										svo.setStatus(2);
										tempList.add(svo);
									}
								}
								bvo.setPk_task_s(tempList.toArray(new TaskSVO[0]));
							}
						}
						bvoList.add(bvo);
					}
					aggvo.setChildren(TaskBVO.class, bvoList.toArray(new TaskBVO[0]));
				}
			}
		}
		return Bills;
	}
	
	/**
	 * delete
	 * 
	 * @param vos
	 * @throws BusinessException
	 */
	private void deleteOldVO(AggTaskHVO[] vos) throws BusinessException {
		Set<String> deletePks = new HashSet<>();
		for (AggTaskHVO vo : vos) {
			deletePks.add(vo.getPrimaryKey());
		}
		// 子表删除
		getDao().deleteByPKs(TaskHVO.class, deletePks.toArray(new String[0]));

		InSQLCreator insql = new InSQLCreator();
		String deletePksInSQL = insql.getInSQL(deletePks.toArray(new String[0]));
		// 子表切换
		getDao().executeUpdate("update qc_task_b set dr = 0 where PK_task_h in (" + deletePksInSQL + ")");
		// 孙表切换
		getDao().executeUpdate(
				"update qc_task_r set dr = 0 where pk_task_r in ( "
						+ " select pk_task_r from qc_task_r r "
						+ " left join qc_task_b b on b.PK_task_b = r.PK_task_b "
						+ " where b.PK_task_h in(" + deletePksInSQL + ") ) ");
		getDao().executeUpdate(
				"update qc_task_s set dr = 0 where pk_task_s in ( "
						+ " select pk_task_s from qc_task_s r "
						+ " left join qc_task_b b on b.PK_task_b = r.PK_task_b "
						+ " where b.PK_task_h in(" + deletePksInSQL + ") ) ");

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

	public AggTaskHVO[] pubquerybills(IQueryScheme queryScheme) throws BusinessException {
		AggTaskHVO[] bills = null;
		try {
			this.preQuery(queryScheme);
			BillLazyQuery<AggTaskHVO> query = new BillLazyQuery<AggTaskHVO>(AggTaskHVO.class);
			bills = query.query(queryScheme, null);
		} catch (Exception e) {
			ExceptionUtils.marsh(e);
		}
		return bills;
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
	public AggTaskHVO[] pubsendapprovebills(AggTaskHVO[] clientFullVOs, AggTaskHVO[] originBills)
			throws BusinessException {
		AceTaskSendApproveBP bp = new AceTaskSendApproveBP();
		AggTaskHVO[] retvos = bp.sendApprove(clientFullVOs, originBills);
		return retvos;
	}

	// 收回
	public AggTaskHVO[] pubunsendapprovebills(AggTaskHVO[] clientFullVOs, AggTaskHVO[] originBills)
			throws BusinessException {
		AceTaskUnSendApproveBP bp = new AceTaskUnSendApproveBP();
		AggTaskHVO[] retvos = bp.unSend(clientFullVOs, originBills);
		return retvos;
	};

	// 审批
	public AggTaskHVO[] pubapprovebills(AggTaskHVO[] clientFullVOs, AggTaskHVO[] originBills) throws BusinessException {
		for (int i = 0; clientFullVOs != null && i < clientFullVOs.length; i++) {
			clientFullVOs[i].getParentVO().setStatus(VOStatus.UPDATED);
		}
		AceTaskApproveBP bp = new AceTaskApproveBP();
		AggTaskHVO[] retvos = bp.approve(clientFullVOs, originBills);
		return retvos;
	}

	// 弃审

	public AggTaskHVO[] pubunapprovebills(AggTaskHVO[] clientFullVOs, AggTaskHVO[] originBills)
			throws BusinessException {
		for (int i = 0; clientFullVOs != null && i < clientFullVOs.length; i++) {
			clientFullVOs[i].getParentVO().setStatus(VOStatus.UPDATED);
		}
		AceTaskUnApproveBP bp = new AceTaskUnApproveBP();
		AggTaskHVO[] retvos = bp.unApprove(clientFullVOs, originBills);
		return retvos;
	}

}