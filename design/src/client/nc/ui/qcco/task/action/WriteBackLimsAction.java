package nc.ui.qcco.task.action;

import java.awt.event.ActionEvent;

import nc.bs.framework.common.NCLocator;
import nc.itf.qcco.ITaskMaintain;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pubapp.uif2app.actions.pflow.ScriptPFlowAction;
import nc.ui.uif2.UIState;
import nc.vo.pub.SuperVO;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.qcco.task.AggTaskHVO;

public class WriteBackLimsAction extends ScriptPFlowAction {

	public WriteBackLimsAction() {
		setBtnName("回写LIMS");
		setCode("writebacklims");
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void doAction(ActionEvent e) throws Exception {
		Object selectedData = model.getSelectedData();
		if (selectedData != null) {
			NCLocator.getInstance().lookup(ITaskMaintain.class).writeBackLims((AggTaskHVO) selectedData);
		} else {
			MessageDialog.showWarningDlg(null, "提示", "未选择任何数据!");
		}
	}

	protected boolean isActionEnable() {
		AbstractBill aggVO = (AbstractBill) this.getModel().getSelectedData();
		if (aggVO == null) {
			return false;
		}
		SuperVO hvo = (SuperVO) aggVO.getParentVO();
		if (hvo == null) {
			return false;
		}
		return this.getModel().getUiState() == UIState.NOT_EDIT;
	}
}
