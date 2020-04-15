package nc.ui.qcco.commission.action;

import nc.ui.pubapp.uif2app.actions.BodyDelLineAction;

public class CommissionBodyDelLineAction extends BodyDelLineAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5166054143982059454L;

	@Override
	public void doAction() {
		super.doAction();
		getCardPanel().getBodyPanel().addLine();
		getCardPanel().getBodyPanel().delLine();
	}
}
