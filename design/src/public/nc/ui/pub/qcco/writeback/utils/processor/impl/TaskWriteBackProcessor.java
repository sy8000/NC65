package nc.ui.pub.qcco.writeback.utils.processor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Collections;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapProcessor;
import nc.ui.pub.qcco.writeback.utils.WriteBackProcessData;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.CProjTask;
import nc.ui.pub.qcco.writeback.utils.common.CommonUtils;
import nc.ui.pub.qcco.writeback.utils.mapping.FirstWriteBackStaticMaping;
import nc.ui.pub.qcco.writeback.utils.processor.IFirstWriteBackProcessor;
import nc.vo.pf.worknote.WorkNoteVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ISuperVO;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.task.TaskBVO;

/**
 * 任务行回写
 * 
 * @author 91967
 * 
 */
public class TaskWriteBackProcessor implements IFirstWriteBackProcessor {

	private CommonUtils utils;

	@Override
	public void setUtils(CommonUtils utils) {
		this.utils = utils;
	}

	@Override
	public void processFirst(WriteBackProcessData data) throws BusinessException {
		process(data);
	}

	/**
	 * 获取Insert语句片断
	 * 
	 * @param processData
	 *            processData
	 * @throws BusinessException
	 */
	private void process(WriteBackProcessData processData) throws BusinessException {
		
		IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		// NC数据
		List<ISuperVO> srcDataList = new ArrayList<>();
		ISuperVO[] superVOs = processData.getAggTaskHVO().getChildren(TaskBVO.class);
		Collections.addAll(srcDataList, superVOs);
		if (null == srcDataList || srcDataList.size() <= 0) {
			return ;
		}

		//需要回写的LIMS数据
		List<CProjTask> taskList = initWriteBackList(srcDataList.size());
		
		// 预申请pk
		List<Integer> pkList = utils.getPrePk(TaskBVO.class, srcDataList.size());

		// 用于拼接字段名
		StringBuilder insertFields = new StringBuilder();
		// 进行列循环
		for (Entry<String, String> map : FirstWriteBackStaticMaping.BODY_TASK_MAPPING.entrySet()) {
			String fieldName = map.getKey();
			String[] fields = null;
			if (map.getValue().contains(";")) {
				fields = utils.getWriteBackFields(map.getValue().split(";"));
			} else {
				fields = utils.getWriteBackFields(new String[] { map.getValue() });
			}
			// times是处理一对多关系的
			for (String field : fields) {
				insertFields.append(field).append(",");
			}

			if (srcDataList != null && srcDataList.size() > 0) {
				for (int i = 0 ;i < srcDataList.size();i++) {
					Object unDofieldValue = srcDataList.get(i).getAttributeValue(fieldName);

					Object realValue = utils.getRealValue(unDofieldValue, fieldName, CommissionBVO.class);

					for (String field : fields) {
						taskList.get(i).setAttributeValue(field, realValue);
					}

				}
			}
		}

		
		// 回写主外键和其他信息
		if (srcDataList != null) {
			Map<String,String> rs = getAppoveInfo(processData.getAggCommissionHVO().getParentVO().getPk_commission_h());
			//审批流信息
			List<WorkNoteVO> flowList = getFlow(processData.getAggTaskHVO().getParentVO().getPk_task_h());
			String flowCode1 = null;
			String flowCode2 = null;
			String flowTime1 = null;
			String flowTime2 = null;
			if(flowList!=null && flowList.size() >0){
				flowCode1 = (String)bs.executeQuery("select user_code from sm_user where cuserid = '"+flowList.get(0).getCheckman()+"'", 
						new ColumnProcessor());
				flowTime1 = "to_timestamp('"+flowList.get(0).getDealdate().toStdString()+"','yyyy-mm-dd hh24:mi:ss.ff')";
			}
			if(flowList!=null && flowList.size() >1){
				flowCode2 = (String)bs.executeQuery("select user_code from sm_user where cuserid = '"+flowList.get(1).getCheckman()+"'", 
						new ColumnProcessor());
				flowTime2 = "to_timestamp('"+flowList.get(1).getDealdate().toStdString()+"','yyyy-mm-dd hh24:mi:ss.ff')";
			}
			
			for (int i = 0; i < srcDataList.size(); i++) {
				taskList.get(i).setAttributeValue("seq_num", pkList.get(i));

				taskList.get(i).setAttributeValue("project", processData.getProject().getAttributeValue("name"));
				
				//approve info
				taskList.get(i).setAttributeValue("changed_by", rs.get("changed_by"));
				taskList.get(i).setAttributeValue("changed_on",rs.get("changed_on")==null?null:"to_timestamp('"+rs.get("changed_on")+"','yyyy-mm-dd hh24:mi:ss.ff')");
				taskList.get(i).setAttributeValue("c_submit_by", rs.get("c_submit_by"));
				taskList.get(i).setAttributeValue("c_submit_date",rs.get("c_submit_date")==null?null: "to_timestamp('"+rs.get("c_submit_date")+"','yyyy-mm-dd hh24:mi:ss.ff') ");
				
				//写死的值 
				taskList.get(i).setAttributeValue("c_next_person", "刘凤1002472/1002742/1002768/1002824/1002487");
				taskList.get(i).setAttributeValue("c_current_process", "报价单生成");
				//持续时间
				taskList.get(i).setAttributeValue("plan_test_time", utils.getTestTime(srcDataList.get(i)));
				//符合lims的分组
				Object sampleallocationsource = srcDataList.get(i).getAttributeValue("sampleallocationsource");
				String sampleArray = sampleallocationsource ==null?null:String.valueOf(sampleallocationsource);
				try {
					taskList.get(i).setAttributeValue("assigned_sample_display", 
							CommonUtils.outOrderString4WriteBack(sampleArray));
				} catch (Exception e) {
					taskList.get(i).setAttributeValue("assigned_sample_display", "");
				}
				
				//客户经理工号 第一个审批人
				taskList.get(i).setAttributeValue("c_customermanager_by", String.valueOf(flowCode1));
				//客户经理审批时间 第一个审批时间
				taskList.get(i).setAttributeValue("c_customermanager_date", flowTime1);
				//技术主管工号 第二个审批人
				taskList.get(i).setAttributeValue("c_techsupervisor_by", String.valueOf(flowCode2));
				//技术主管审批时间 第二个审批时间
				taskList.get(i).setAttributeValue("c_techsupervisor_date", flowTime2);

				
			}
		}
		processData.setTaskList(taskList);
	}
	/**
	 * 获取审批信息 第一个为客户主管,第二个为技术主管
	 * @param pk_task_h
	 * @return
	 * @throws BusinessException 
	 */
	private List<WorkNoteVO> getFlow(String pk_task_h) throws BusinessException {
		IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		if(null != pk_task_h){
			@SuppressWarnings("unchecked")
			List<WorkNoteVO> rsList = 
					(List<WorkNoteVO>)bs.executeQuery(
							"select * from pub_workflownote where BILLID = '"+pk_task_h+"' and APPROVESTATUS = 1 order by dealdate ", 
					new BeanListProcessor(WorkNoteVO.class));
			if(null!=rsList){
				return rsList;
			}
		}
		return new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getAppoveInfo(String pk_commission_h) {
		String sql = "select job.psncode changed_by,taskh.modifiedtime changed_on,"
				+ " job2.psncode c_submit_by,ch.creationtime c_submit_date from qc_task_h taskh "
				+ " left join sm_user sm on sm.cuserid = taskh.modifier "
				+ " left join (select * from bd_psnjob jobinner where ismainjob = 'Y' ) job on rownum = 1 and job.pk_psndoc = sm.pk_psndoc "
				+ " left join qc_commission_h ch on ch.pk_commission_h = taskh.pk_commission_h "
				+ " left join sm_user sm2 on sm2.cuserid = ch.creator "
				+ " left join (select * from bd_psnjob jobinner where ismainjob = 'Y' ) job2 on rownum = 1 and job2.pk_psndoc = sm2.pk_psndoc "
				+ " where taskh.pk_commission_h = '" + pk_commission_h + "' ";
		Map<String, String> rs = null;
		try {
			rs = (Map<String, String>) new BaseDAO().executeQuery(sql, new MapProcessor());
		} catch (DAOException e) {
			rs = new HashMap<>();
			;
		}
		return rs;
	}

	private List<CProjTask> initWriteBackList(int size) {
		List<CProjTask> rsList = new ArrayList<>();
		
		for(int i = 0;i < size ;i++){
			rsList.add(new CProjTask());
		}
		return rsList;
	}
}
