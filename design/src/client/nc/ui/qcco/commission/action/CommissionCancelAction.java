package nc.ui.qcco.commission.action;

import java.awt.event.ActionEvent;

import nc.ui.pubapp.uif2app.actions.CancelAction;
import nc.ui.pubapp.uif2app.components.grand.CardGrandPanelComposite;
import nc.ui.qcco.commission.model.MainSubBillModel;

public class CommissionCancelAction extends CancelAction {
	private CardGrandPanelComposite mainGrandPanel;

	public CardGrandPanelComposite getMainGrandPanel() {
		return mainGrandPanel;
	}

	public void setMainGrandPanel(CardGrandPanelComposite mainGrandPanel) {
		this.mainGrandPanel = mainGrandPanel;
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		super.doAction(e);

		// �ɱ��״̬����ʱ����״̬
		((MainSubBillModel) this.getModel()).setChangeStatus(false);
		// ((MainSubBillModel) this.getModel()).resetBillFormEnableState();
		//
		mainGrandPanel.showMeUp();
	}

}
