package nc.ui.qcco.task.action;

import java.awt.event.ActionEvent;

import nc.ui.uif2.actions.EditAction;
import nc.vo.pub.BusinessException;
import nc.vo.qcco.task.AggTaskHVO;
import nc.vo.qcco.task.TaskHVO;

	public class TaskEditAction extends EditAction {
	@Override
	public void doAction(ActionEvent e) throws Exception {
		AggTaskHVO aggvo = (AggTaskHVO)getModel().getSelectedData();
		if(aggvo!=null && aggvo.getParentVO()!=null){
			TaskHVO hvo = aggvo.getParentVO();
			Integer approveStatus = hvo.getApprovestatus();
			if(approveStatus!=null && approveStatus != -1){
				throw new BusinessException("非自由态单据不允许修改!");
			}
		}
		super.doAction(e);
	}
}
