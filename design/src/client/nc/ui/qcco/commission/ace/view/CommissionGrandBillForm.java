package nc.ui.qcco.commission.ace.view;

import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.qcco.commission.action.SelectAllLineAction;

public class CommissionGrandBillForm extends ShowUpableBillForm {
	@Override
	public void initUI() {
		super.initUI();
		getBillCardPanel().getBodyPanel().addEditAction(this.getBodyLineActions().get(0));
		getBillCardPanel().getBodyPanel().addEditAction(this.getBodyLineActions().get(1));
	}
	
}
