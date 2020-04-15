package nc.ui.pub.qcco.writeback.utils.processor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapProcessor;
import nc.ui.pub.qcco.writeback.utils.WriteBackProcessData;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.ApprovalInfo;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.ApprovalMain;
import nc.ui.pub.qcco.writeback.utils.common.CommonUtils;
import nc.ui.pub.qcco.writeback.utils.processor.IFirstWriteBackProcessor;
import nc.vo.pf.worknote.WorkNoteVO;
import nc.vo.pub.BusinessException;

public class ApprovalInfoWriteBackProcessor implements IFirstWriteBackProcessor {
	private CommonUtils utils;

	@Override
	public void setUtils(CommonUtils utils) {
		this.utils = utils;
	}

	@Override
	public void processFirst(WriteBackProcessData data) throws BusinessException {
		process(data);
	}

	private void process(WriteBackProcessData processData) throws BusinessException {
		IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		List<ApprovalInfo> approvalList = new ArrayList<>();
		Map<String,String> rs = getAppoveInfo(processData.getAggCommissionHVO().getParentVO().getPk_commission_h());
		//审批流信息
		List<WorkNoteVO> flowList = getFlow(processData.getAggTaskHVO().getParentVO().getPk_task_h());
		//审批人工号
		String flowCode1 = null;
		String flowCode2 = null;
		//审批时间
		String flowTime1 = null;
		String flowTime2 = null;
		//审批人姓名
		String flowName1 = null;
		String flowName2 = null;
		//审批备注
		String note1 = null;
		String note2 = null;
		//发送时间
		String sendtime1 = null;
		if(flowList!=null && flowList.size() >0){
			flowCode1 = (String)bs.executeQuery("select user_code from sm_user where cuserid = '"+flowList.get(0).getCheckman()+"'", 
					new ColumnProcessor());
			flowTime1 = "to_timestamp('"+flowList.get(0).getDealdate().toStdString()+"','yyyy-mm-dd hh24:mi:ss.ff')";
			flowName1 = (String)bs.executeQuery("select user_name from sm_user where cuserid = '"+flowList.get(0).getCheckman()+"'", 
					new ColumnProcessor());
			note1 = flowList.get(0).getChecknote();
			sendtime1 = "to_timestamp('"+flowList.get(0).getSenddate().toStdString()+"','yyyy-mm-dd hh24:mi:ss.ff')";
		}
		if(flowList!=null && flowList.size() >1){
			flowCode2 = (String)bs.executeQuery("select user_code from sm_user where cuserid = '"+flowList.get(1).getCheckman()+"'", 
					new ColumnProcessor());
			flowTime2 = "to_timestamp('"+flowList.get(1).getDealdate().toStdString()+"','yyyy-mm-dd hh24:mi:ss.ff')";
			flowName2 = (String)bs.executeQuery("select user_name from sm_user where cuserid = '"+flowList.get(1).getCheckman()+"'", 
					new ColumnProcessor());
			note2 = flowList.get(1).getChecknote();
		}
		//审批信息-主表
		ApprovalMain appMain = new ApprovalMain();
		//id 自增
		appMain.setAttributeValue("APPROVAL_ID", utils.getPrePk("approval_id", "approval", 1).get(0));
		//提交审批时间
		appMain.setAttributeValue("APPROVAL_START", sendtime1);
		//审批完成时间
		appMain.setAttributeValue("APPROVAL_COMPLETE", flowTime1);
		//委托单号
		appMain.setAttributeValue("RECORD_KEY", processData.getAggCommissionHVO().getParentVO().getBillno());
		
		appMain.setAttributeValue("RECORD_VERSION", 0);
		appMain.setAttributeValue("APPROVAL_ROUTING", "HF");
		appMain.setAttributeValue("APPROVAL_GROUP", "PROJECT");
		appMain.setAttributeValue("APPROVAL_STATUS", "X");
		appMain.setAttributeValue("TABLE_NAME", "PROJECT");
		
		//子表:第一条客户主管
		ApprovalInfo first = new ApprovalInfo();
		//取委托单中project.approval_id  
		first.setAttributeValue("approval_id", processData.getProject().getAttributeValue("approval_id"));
		//客户主管填1,技术主管填2
		first.setAttributeValue("approval_step", 1);
		//客户主管:CUSTOMER_M,技术主管:实验室主管审批委托单
		first.setAttributeValue("approval_role", "CUSTOMER_M");
		//客户主管:客户主管审核委托单,技术主管:实验室主管审批委托单
		first.setAttributeValue("approval_reason", "客户主管审核委托单");
		//客户主管:1,技术主管:2
		first.setAttributeValue("approval_order", 1);
		//委托单提交时间
		first.setAttributeValue("date_assigned", "to_timestamp('"+rs.get("c_submit_date")+"','yyyy-mm-dd hh24:mi:ss.ff') ");
		//委托单审批日期
		first.setAttributeValue("date_approval_due", flowTime1);
		//委托单审批日期
		first.setAttributeValue("date_approved", flowTime1);
		//下一步审批人工号 客户主管时写入计算主管工号,
		//技术主管时查找role code为HF_YPGLY的人员工号,没有则报错
		first.setAttributeValue("assigned_to", String.valueOf(flowCode2));
		//是否审批(T/F) 固定T
		first.setAttributeValue("approved", "T");
		//当前审批人工号
		first.setAttributeValue("approved_by", String.valueOf(flowCode1));
		//当前审批人姓名
		first.setAttributeValue("full_user_name", flowName1);
		//直接写T
		first.setAttributeValue("assigned", "T");
		//3600
		first.setAttributeValue("wait_timeout", 3600);
		//直接写F
		first.setAttributeValue("send_alerts", "F");
		//直接写F
		first.setAttributeValue("send_email", "F");
		//审批注备
		first.setAttributeValue("approval_comment", note1);
		approvalList.add(first);
		
		
		//子表:第二条 技术主管
		ApprovalInfo sec = new ApprovalInfo();
		//取委托单中project.approval_id  
		sec.setAttributeValue("approval_id", processData.getProject().getAttributeValue("approval_id"));
		//客户主管填1,技术主管填2
		sec.setAttributeValue("approval_step", 2);
		//客户主管:CUSTOMER_M,技术主管:实验室主管审批委托单
		sec.setAttributeValue("approval_role", "LAB_M");
		//客户主管:客户主管审核委托单,技术主管:实验室主管审批委托单
		sec.setAttributeValue("approval_reason", "实验室主管审批委托单");
		//客户主管:1,技术主管:2
		sec.setAttributeValue("approval_order", 2);
		//委托单提交时间
		sec.setAttributeValue("date_assigned", "to_timestamp('"+rs.get("c_submit_date")+"','yyyy-mm-dd hh24:mi:ss.ff') ");
		//委托单审批日期
		sec.setAttributeValue("date_approval_due", flowTime2);
		//委托单审批日期
		sec.setAttributeValue("date_approved", flowTime2);
		//下一步审批人工号 客户主管时写入计算主管工号,
		//技术主管时查找role code为HF_YPGLY的人员工号,没有则报错
		String sql = "select USER_CODE from sm_user_role sr "
				+" left join sm_user su on (su.dr = 0 and su.CUSERID = sr.CUSERID) "
				+" where PK_ROLE in (select PK_ROLE from sm_role where role_code = 'HF_YPGLY' and dr = 0) "
				+" and ROWNUM = 1 "
				+" order by USER_CODE ";
		String spCode = (String)bs.executeQuery(sql, new ColumnProcessor());
		if(spCode==null){
			throw new BusinessException("未找到角色编码为[HF_YPGLY]的人员!");
		}
		sec.setAttributeValue("assigned_to", spCode);
		//是否审批(T/F) 固定T
		sec.setAttributeValue("approved", "T");
		//当前审批人工号
		sec.setAttributeValue("approved_by", flowCode2);
		//当前审批人姓名
		sec.setAttributeValue("full_user_name", flowName2);
		//直接写T
		sec.setAttributeValue("assigned", "T");
		//3600
		sec.setAttributeValue("wait_timeout", 3600);
		//直接写F
		sec.setAttributeValue("send_alerts", "F");
		//直接写F
		sec.setAttributeValue("send_email", "F");
		//审批注备
		sec.setAttributeValue("approval_comment", note2);
		approvalList.add(sec);
		processData.setApprovalList(approvalList);
		processData.setApprovalMain(appMain);
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
	
}
