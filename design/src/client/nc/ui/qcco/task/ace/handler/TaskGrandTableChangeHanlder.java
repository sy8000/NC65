package nc.ui.qcco.task.ace.handler;

import nc.ui.pubapp.uif2app.components.grand.CardGrandPanelComposite;
import nc.ui.pubapp.uif2app.components.grand.util.CardPanelEventUtil;

public class TaskGrandTableChangeHanlder {
	private CardGrandPanelComposite billForm;
	public CardGrandPanelComposite getBillForm() {
		return billForm;
	}

	public void setBillForm(CardGrandPanelComposite billForm) {
		this.billForm = billForm;
	}
	public void refreshTaskGrand(){
		CardPanelEventUtil.grandModelInit(billForm);
	}
}
