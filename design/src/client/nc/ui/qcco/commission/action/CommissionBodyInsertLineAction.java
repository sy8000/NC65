package nc.ui.qcco.commission.action;

import java.awt.event.ActionEvent;
import java.util.UUID;

import nc.ui.pub.bill.BillScrollPane;
import nc.ui.pubapp.uif2app.actions.BodyInsertLineAction;

public class CommissionBodyInsertLineAction extends BodyInsertLineAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8735882997111929724L;
	@Override
	protected boolean doBeforeAction(ActionEvent e) {
		/*getCardPanel().getBodyPanel().getTable().getSelectionModel().setSelectionInterval(-1, -1);
	    
	    ((BillScrollPane)getCardPanel().getBodySelectedScrollPane()).getTable().changeSelection(-1, -1, false, false);
	    
	    ((BillScrollPane)getCardPanel().getBodySelectedScrollPane()).getTable().requestFocus();*/
		
		
		//getCardPanel().getBodyPanel().addLine();
		//getCardPanel().firePropertyChange(null, true, true);
		boolean rt = super.doBeforeAction(e);
		
		/*getCardPanel().getBodyPanel().delLine();*/
		//f**k tank 2019年8月18日21:12:27
		getCardPanel().firePropertyChange(null, true, true);
		//getCardPanel().delLine();
		return rt;
		
	}

	@Override
	protected void afterLineInsert(int index) {
		super.afterLineInsert(index);
		// 设置uuid
		if (getCardPanel().getBodyItem("uniqueid") != null) {

			String uuid = UUID.randomUUID().toString();
			uuid = uuid.replace("-", "");
			getCardPanel().setBodyValueAt(uuid, index, "uniqueid");
			getCardPanel().getBillModel().setValueAt(uuid,index, "uniqueid");
		}
	}
}
