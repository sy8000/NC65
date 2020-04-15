package nc.ui.qcco.task.action;

import java.awt.event.ActionEvent;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.ui.pubapp.uif2app.actions.pflow.CommitScriptAction;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.qcco.task.AggTaskHVO;
import nc.vo.qcco.task.TaskBVO;

public class TaskCommitScriptAction extends CommitScriptAction {
	private static Integer ERROR_TEMP_SAVE = 1;
	private static Integer ERROR_EMPTY_BODY = 2;
	private static Integer GREEN_LIGHT = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5233378459958513820L;

	@Override
	public void doAction(ActionEvent e) throws Exception {
		if(canCommit() == ERROR_TEMP_SAVE){
			throw new BusinessException("不能提交暂存态的单据!");
		}
		if(canCommit() == ERROR_EMPTY_BODY){
			throw new BusinessException("表体不能为空!");
		}
		super.doAction(e);
	}
	/**
	 * 暂存态,表体为空,不能提交
	 * @return
	 */
	private int canCommit(){
		AggTaskHVO obj = (AggTaskHVO)getModel().getSelectedData();
		if(null == obj){
			return ERROR_TEMP_SAVE;
		}
		if(obj.getChildren(TaskBVO.class)==null || obj.getChildren(TaskBVO.class).length <= 0){
			return ERROR_EMPTY_BODY;
		}
		IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		try {
			Integer billstatds = (Integer)bs.executeQuery("select billstatus from qc_task_h where pk_task_h = '"+obj.getPrimaryKey()+"' and dr = 0", 
					new ColumnProcessor());
			if(billstatds==null || -1 == billstatds){
				return GREEN_LIGHT;
			}
		} catch (BusinessException e) {
			Logger.error(e);
			return ERROR_TEMP_SAVE;
		}
		return ERROR_TEMP_SAVE;
	}
	@Override
	protected boolean isActionEnable() {
		if(canCommit()!=GREEN_LIGHT){
			return false;
		}
		return super.isActionEnable();
	}
}
