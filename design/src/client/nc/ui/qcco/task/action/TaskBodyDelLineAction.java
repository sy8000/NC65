package nc.ui.qcco.task.action;

import java.awt.event.ActionEvent;
import java.util.List;

import nc.ui.pubapp.uif2app.actions.BodyDelLineAction;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterRowEditEvent;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.vo.qcco.task.TaskHBodyVO;

public class TaskBodyDelLineAction extends BodyDelLineAction {
	public TaskBodyDelLineAction(ShowUpableBillForm grandCard,BillForm mainBillForm){
		this.grandCard = grandCard;
		this.mainBillForm = mainBillForm;
	}
	
	private ShowUpableBillForm grandCard;
	private BillForm mainBillForm;//

	public ShowUpableBillForm getGrandCard() {
		return grandCard;
	}

	public void setGrandCard(ShowUpableBillForm grandCard) {
		this.grandCard = grandCard;
	}

	public BillForm getMainBillForm() {
		return mainBillForm;
	}

	public void setMainBillForm(BillForm mainBillForm) {
		this.mainBillForm = mainBillForm;
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.doAction(e);
		if(this.getCardPanel().getRowCount() <=0){
			getCardPanel().getBodyPanel().addLine();
			getCardPanel().getBodyPanel().delLine();
		}
	}
}
