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
			//委托单主表
			IFirstWriteBackProcessor projectProcessor = new ProjectWriteBackProcessor();
			projectProcessor.setUtils(utils);
			//委托单子表
			IFirstWriteBackProcessor sampleGroupProcessor = new SampleGroupWriteBackProcessor();
			sampleGroupProcessor.setUtils(utils);
			//实验前参数
			ParaAWriteBackProcessor paraAProcessor = new ParaAWriteBackProcessor();
			paraAProcessor.setUtils(utils);
			//任务行
			IFirstWriteBackProcessor taskProcessor = new TaskWriteBackProcessor();
			taskProcessor.setUtils(utils);
			//试验后参数
			ParaBWriteBackProcessor paraBProcessor = new ParaBWriteBackProcessor();
			paraBProcessor.setUtils(utils);
			//sample表
			SampleWriteBackProcessor sampleProcessor = new SampleWriteBackProcessor();
			sampleProcessor.setUtils(utils);
			//test表
			TestWriteBackProcessor testProcessor = new TestWriteBackProcessor();
			testProcessor.setUtils(utils);
			//result表
			ResultWriteBackProcessor resultProcessor = new ResultWriteBackProcessor();
			resultProcessor.setUtils(utils);
			//审批信息表-子表+主表
			IFirstWriteBackProcessor approvalProcessor = new ApprovalInfoWriteBackProcessor();
			approvalProcessor.setUtils(utils);
			
			
			//静态资源初始化 -- 这两句话不能删除,虽然没有任何引用,是用于static代码块初始化
			@SuppressWarnings("unused")
			FirstWriteBackStaticMaping fm = new FirstWriteBackStaticMaping();
			@SuppressWarnings("unused")
			SecWriteBackStaticMaping sm = new SecWriteBackStaticMaping();
			
			
			//委托单主表回写
			projectProcessor.processFirst(data);
			// 样品组回写
			sampleGroupProcessor.processFirst(data);
			// 委托单孙表-实验前参数回写
			paraAProcessor.processFirst(data);
			// 任务单子表-任务回写
			taskProcessor.processFirst(data);
			// 任务单孙表-试验后参数回写
			paraBProcessor.processFirst(data);
			// sample表第一次回写
			sampleProcessor.processFirst(data);
			// test表第一次回写
			testProcessor.processFirst(data);
			// result表第一次回写
			resultProcessor.processFirst(data);
			// 审批表信息(主子表)回写
			approvalProcessor.processFirst(data);
			
			// sample第二次回写
			sampleProcessor.processSec(data);
			// test第二次回写
			testProcessor.processSec(data);
			// result第二次回写
			resultProcessor.processSec(data);
			//试验前参数二次回写
			paraAProcessor.processSec(data);
			//实验后参数二次回写
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
