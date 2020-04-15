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
		//��������Ϣ
		List<WorkNoteVO> flowList = getFlow(processData.getAggTaskHVO().getParentVO().getPk_task_h());
		//�����˹���
		String flowCode1 = null;
		String flowCode2 = null;
		//����ʱ��
		String flowTime1 = null;
		String flowTime2 = null;
		//����������
		String flowName1 = null;
		String flowName2 = null;
		//������ע
		String note1 = null;
		String note2 = null;
		//����ʱ��
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
		//������Ϣ-����
		ApprovalMain appMain = new ApprovalMain();
		//id ����
		appMain.setAttributeValue("APPROVAL_ID", utils.getPrePk("approval_id", "approval", 1).get(0));
		//�ύ����ʱ��
		appMain.setAttributeValue("APPROVAL_START", sendtime1);
		//�������ʱ��
		appMain.setAttributeValue("APPROVAL_COMPLETE", flowTime1);
		//ί�е���
		appMain.setAttributeValue("RECORD_KEY", processData.getAggCommissionHVO().getParentVO().getBillno());
		
		appMain.setAttributeValue("RECORD_VERSION", 0);
		appMain.setAttributeValue("APPROVAL_ROUTING", "HF");
		appMain.setAttributeValue("APPROVAL_GROUP", "PROJECT");
		appMain.setAttributeValue("APPROVAL_STATUS", "X");
		appMain.setAttributeValue("TABLE_NAME", "PROJECT");
		
		//�ӱ�:��һ���ͻ�����
		ApprovalInfo first = new ApprovalInfo();
		//ȡί�е���project.approval_id  
		first.setAttributeValue("approval_id", processData.getProject().getAttributeValue("approval_id"));
		//�ͻ�������1,����������2
		first.setAttributeValue("approval_step", 1);
		//�ͻ�����:CUSTOMER_M,��������:ʵ������������ί�е�
		first.setAttributeValue("approval_role", "CUSTOMER_M");
		//�ͻ�����:�ͻ��������ί�е�,��������:ʵ������������ί�е�
		first.setAttributeValue("approval_reason", "�ͻ��������ί�е�");
		//�ͻ�����:1,��������:2
		first.setAttributeValue("approval_order", 1);
		//ί�е��ύʱ��
		first.setAttributeValue("date_assigned", "to_timestamp('"+rs.get("c_submit_date")+"','yyyy-mm-dd hh24:mi:ss.ff') ");
		//ί�е���������
		first.setAttributeValue("date_approval_due", flowTime1);
		//ί�е���������
		first.setAttributeValue("date_approved", flowTime1);
		//��һ�������˹��� �ͻ�����ʱд��������ܹ���,
		//��������ʱ����role codeΪHF_YPGLY����Ա����,û���򱨴�
		first.setAttributeValue("assigned_to", String.valueOf(flowCode2));
		//�Ƿ�����(T/F) �̶�T
		first.setAttributeValue("approved", "T");
		//��ǰ�����˹���
		first.setAttributeValue("approved_by", String.valueOf(flowCode1));
		//��ǰ����������
		first.setAttributeValue("full_user_name", flowName1);
		//ֱ��дT
		first.setAttributeValue("assigned", "T");
		//3600
		first.setAttributeValue("wait_timeout", 3600);
		//ֱ��дF
		first.setAttributeValue("send_alerts", "F");
		//ֱ��дF
		first.setAttributeValue("send_email", "F");
		//����ע��
		first.setAttributeValue("approval_comment", note1);
		approvalList.add(first);
		
		
		//�ӱ�:�ڶ��� ��������
		ApprovalInfo sec = new ApprovalInfo();
		//ȡί�е���project.approval_id  
		sec.setAttributeValue("approval_id", processData.getProject().getAttributeValue("approval_id"));
		//�ͻ�������1,����������2
		sec.setAttributeValue("approval_step", 2);
		//�ͻ�����:CUSTOMER_M,��������:ʵ������������ί�е�
		sec.setAttributeValue("approval_role", "LAB_M");
		//�ͻ�����:�ͻ��������ί�е�,��������:ʵ������������ί�е�
		sec.setAttributeValue("approval_reason", "ʵ������������ί�е�");
		//�ͻ�����:1,��������:2
		sec.setAttributeValue("approval_order", 2);
		//ί�е��ύʱ��
		sec.setAttributeValue("date_assigned", "to_timestamp('"+rs.get("c_submit_date")+"','yyyy-mm-dd hh24:mi:ss.ff') ");
		//ί�е���������
		sec.setAttributeValue("date_approval_due", flowTime2);
		//ί�е���������
		sec.setAttributeValue("date_approved", flowTime2);
		//��һ�������˹��� �ͻ�����ʱд��������ܹ���,
		//��������ʱ����role codeΪHF_YPGLY����Ա����,û���򱨴�
		String sql = "select USER_CODE from sm_user_role sr "
				+" left join sm_user su on (su.dr = 0 and su.CUSERID = sr.CUSERID) "
				+" where PK_ROLE in (select PK_ROLE from sm_role where role_code = 'HF_YPGLY' and dr = 0) "
				+" and ROWNUM = 1 "
				+" order by USER_CODE ";
		String spCode = (String)bs.executeQuery(sql, new ColumnProcessor());
		if(spCode==null){
			throw new BusinessException("δ�ҵ���ɫ����Ϊ[HF_YPGLY]����Ա!");
		}
		sec.setAttributeValue("assigned_to", spCode);
		//�Ƿ�����(T/F) �̶�T
		sec.setAttributeValue("approved", "T");
		//��ǰ�����˹���
		sec.setAttributeValue("approved_by", flowCode2);
		//��ǰ����������
		sec.setAttributeValue("full_user_name", flowName2);
		//ֱ��дT
		sec.setAttributeValue("assigned", "T");
		//3600
		sec.setAttributeValue("wait_timeout", 3600);
		//ֱ��дF
		sec.setAttributeValue("send_alerts", "F");
		//ֱ��дF
		sec.setAttributeValue("send_email", "F");
		//����ע��
		sec.setAttributeValue("approval_comment", note2);
		approvalList.add(sec);
		processData.setApprovalList(approvalList);
		processData.setApprovalMain(appMain);
	}

	/**
	 * ��ȡ������Ϣ ��һ��Ϊ�ͻ�����,�ڶ���Ϊ��������
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
