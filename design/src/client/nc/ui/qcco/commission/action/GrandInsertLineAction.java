package nc.ui.qcco.commission.action;

import java.awt.event.ActionEvent;



import nc.ui.pubapp.uif2app.actions.BodyInsertLineAction;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.vo.pub.BusinessException;

public class GrandInsertLineAction extends BodyInsertLineAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1786642231642310460L;

	public GrandInsertLineAction(BillForm mainBillForm) {
		billFormEditor = mainBillForm;
	}
	
	private BillForm billFormEditor;//
	
	public BillForm getBillFormEditor() {
		return billFormEditor;
	}

	public void setBillFormEditor(BillForm billFormEditor) {
		this.billFormEditor = billFormEditor;
	}
	@Override
	public void doAction(ActionEvent e) throws Exception {
		int curRow = billFormEditor.getBillCardPanel().getBodyPanel().getTable().getSelectedRow();
		if(curRow < 0){
			throw new BusinessException("请选择表体行!");
		}
		super.doAction(e);
	}
	
	
	@Override
	protected void afterLineInsert(int index) {
		super.afterLineInsert(index);
		//值类型
		getCardPanel().getBillModel().setCellEditable(index,"valuetype",true);

	}
}
