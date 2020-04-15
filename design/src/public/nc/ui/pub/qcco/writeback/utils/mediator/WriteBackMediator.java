package nc.ui.pub.qcco.writeback.utils.mediator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.qcco.ITaskMaintain;
import nc.itf.uap.IUAPQueryBS;
import nc.ui.pub.qcco.writeback.utils.WriteBackProcessData;
import nc.ui.pub.qcco.writeback.utils.common.CommonUtils;
import nc.ui.pub.qcco.writeback.utils.mapping.FirstWriteBackStaticMaping;
import nc.ui.pub.qcco.writeback.utils.mapping.SecWriteBackStaticMaping;
import nc.ui.pub.qcco.writeback.utils.processor.IFirstWriteBackProcessor;
import nc.ui.pub.qcco.writeback.utils.processor.impl.*;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFLiteralDate;
import nc.vo.qcco.commission.CommissionHVO;

public class WriteBackMediator {
	
	public String getLIMSCancelSQL(String pk_commission_h) throws BusinessException{
		IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		CommissionHVO hvo = (CommissionHVO)bs.retrieveByPK(CommissionHVO.class, pk_commission_h);
		return "update project set C_NC_TO_LIMS = 'F' where name = '"+hvo.getBillno()+"'";
	}
	
	
	
	public List<String> getLIMSSQL(String pk_commission_h) throws BusinessException {
		List<String> rsList = new ArrayList<>();
		
		if(!StringUtils.isEmpty(pk_commission_h)){
			CommonUtils utils = new CommonUtils();
			WriteBackProcessData data = new WriteBackProcessData(pk_commission_h);
			utils.setData(data);
			//ί�е�����
			IFirstWriteBackProcessor projectProcessor = new ProjectWriteBackProcessor();
			projectProcessor.setUtils(utils);
			//ί�е��ӱ�
			IFirstWriteBackProcessor sampleGroupProcessor = new SampleGroupWriteBackProcessor();
			sampleGroupProcessor.setUtils(utils);
			//ʵ��ǰ����
			ParaAWriteBackProcessor paraAProcessor = new ParaAWriteBackProcessor();
			paraAProcessor.setUtils(utils);
			//������
			IFirstWriteBackProcessor taskProcessor = new TaskWriteBackProcessor();
			taskProcessor.setUtils(utils);
			//��������
			ParaBWriteBackProcessor paraBProcessor = new ParaBWriteBackProcessor();
			paraBProcessor.setUtils(utils);
			//sample��
			SampleWriteBackProcessor sampleProcessor = new SampleWriteBackProcessor();
			sampleProcessor.setUtils(utils);
			//test��
			TestWriteBackProcessor testProcessor = new TestWriteBackProcessor();
			testProcessor.setUtils(utils);
			//result��
			ResultWriteBackProcessor resultProcessor = new ResultWriteBackProcessor();
			resultProcessor.setUtils(utils);
			//������Ϣ��-�ӱ�+����
			IFirstWriteBackProcessor approvalProcessor = new ApprovalInfoWriteBackProcessor();
			approvalProcessor.setUtils(utils);
			
			
			//��̬��Դ��ʼ�� -- �����仰����ɾ��,��Ȼû���κ�����,������static������ʼ��
			@SuppressWarnings("unused")
			FirstWriteBackStaticMaping fm = new FirstWriteBackStaticMaping();
			@SuppressWarnings("unused")
			SecWriteBackStaticMaping sm = new SecWriteBackStaticMaping();
			
			
			//ί�е������д
			projectProcessor.processFirst(data);
			// ��Ʒ���д
			sampleGroupProcessor.processFirst(data);
			// ί�е����-ʵ��ǰ������д
			paraAProcessor.processFirst(data);
			// �����ӱ�-�����д
			taskProcessor.processFirst(data);
			// �������-����������д
			paraBProcessor.processFirst(data);
			// sample���һ�λ�д
			sampleProcessor.processFirst(data);
			// test���һ�λ�д
			testProcessor.processFirst(data);
			// result���һ�λ�д
			resultProcessor.processFirst(data);
			// ��������Ϣ(���ӱ�)��д
			approvalProcessor.processFirst(data);
			
			// sample�ڶ��λ�д
			sampleProcessor.processSec(data);
			// test�ڶ��λ�д
			testProcessor.processSec(data);
			// result�ڶ��λ�д
			resultProcessor.processSec(data);
			//����ǰ�������λ�д
			paraAProcessor.processSec(data);
			//ʵ���������λ�д
			paraBProcessor.processSec(data);
			
			
			
			rsList.addAll(utils.toLIMSSQL());
			
			
		}
		//XXX : for test
		if(rsList!=null && rsList.size() > 0){
			Logger.error("LIMS SQL WRITE BACK start:"+new UFDateTime().toStdString()+"--------------------------------");
			for(String sql : rsList){
				Logger.error(sql);
				Logger.debug(sql);
			}
			ITaskMaintain taskMaintain = NCLocator.getInstance().lookup(ITaskMaintain.class);
			String updateLimsValue[][] = {{"RESULT_NUMBER","RESULT"},{"TEST_NUMBER","TEST"},{"SAMPLE_NUMBER","SAMPLE"},{"SEQ_NUM","C_PROJ_PARA_A"},{"SEQ_NUM","C_PROJ_TASK_PARA_B"},{"SEQ_NUM","C_PROJ_LOGIN_SAMPLE"},{"SEQ_NUM","C_PROJ_TASK"},{"APPROVAL_ID","APPROVAL"}};
			for(int i=0;i<updateLimsValue.length;i++){
				taskMaintain.updatelimsflag(updateLimsValue[i][0],updateLimsValue[i][1]);
			}
			Logger.error("LIMS SQL WRITE BACK end:"+new UFDateTime().toStdString()+"--------------------------------");
		}
		
		/*if(rsList.size() > 0) {
			throw new  BusinessException("test");
		}*/
		
		return rsList;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
