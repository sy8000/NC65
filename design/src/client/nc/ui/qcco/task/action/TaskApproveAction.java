package nc.ui.qcco.task.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.qcco.ITaskMaintain;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.progress.IProgressMonitor;
import nc.ui.pub.beans.progress.NCProgresses;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.pubapp.uif2app.actions.pflow.ApproveScriptAction;

public class TaskApproveAction extends ApproveScriptAction {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6806890916145681621L;

	@Override
	public void doAction(final ActionEvent e) throws Exception {
		//super.doAction(e);
		new Thread(new Runnable() {
			@Override
			public void run() {
				IProgressMonitor progressMonitor = NCProgresses
						.createDialogProgressMonitor(TaskApproveAction.this
								.getModel().getContext().getEntranceUI());

				progressMonitor.beginTask("审批中..", -1);
				progressMonitor.setProcessInfo("数据回写中,请稍后..."); // 数据导入中,请稍后......
				try {
					TaskApproveAction.super.doAction(e);
					ITaskMaintain taskMaintain = NCLocator.getInstance().lookup(ITaskMaintain.class);
					List<String> tableName = new  ArrayList<String>();
					List<String> pkList = new  ArrayList<String>();
					//'RESULT','TEST','SAMPLE','C_PROJ_PARA_A','C_PROJ_TASK_PARA_B',c_proj_login_sample,c_proj_task,
					/*pkList.add("RESULT_NUMBER");
					pkList.add("TEST_NUMBER");
					pkList.add("SAMPLE_NUMBER");
					pkList.add("SEQ_NUM");
					pkList.add("SEQ_NUM");
					pkList.add("SEQ_NUM");
					pkList.add("SEQ_NUM");
					pkList.add("APPROVAL_ID");
					tableName.add("RESULT");
					tableName.add("SAMPLE");
					tableName.add("TEST");
					tableName.add("C_PROJ_PARA_A");
					tableName.add("C_PROJ_TASK_PARA_B");
					tableName.add("C_PROJ_LOGIN_SAMPLE");
					tableName.add("C_PROJ_TASK");
					tableName.add("APPROVAL");*/
					String updateLimsValue[][] = {{"RESULT_NUMBER","RESULT"},{"TEST_NUMBER","TEST"},{"SAMPLE_NUMBER","SAMPLE"},{"SEQ_NUM","C_PROJ_PARA_A"},{"SEQ_NUM","C_PROJ_TASK_PARA_B"},{"SEQ_NUM","C_PROJ_LOGIN_SAMPLE"},{"SEQ_NUM","C_PROJ_TASK"},{"APPROVAL_ID","APPROVAL"}};
					
					/*for(String t : tableName){
						taskMaintain.updatelimsflag(t);
					}*/
					if(PfUtilClient.isSuccess() == true){
						for(int i=0;i<updateLimsValue.length;i++){
							taskMaintain.updatelimsflag(updateLimsValue[i][0],updateLimsValue[i][1]);
						}
						MessageDialog.showHintDlg(TaskApproveAction.this
								.getModel().getContext().getEntranceUI(), null, "审批完成!"); // 数据导入成功！
					}else{
						MessageDialog.showHintDlg(TaskApproveAction.this
								.getModel().getContext().getEntranceUI(), null, "审批已取消!");
					}
					
				} catch (Exception e) {
					Logger.error(e);
					MessageDialog.showErrorDlg(
							TaskApproveAction.this
							.getModel().getContext().getEntranceUI(), null,
							e.getMessage());
				} finally {
					progressMonitor.done();
				}
			}
		}).start();
		
	}
}
