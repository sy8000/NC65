package nc.ui.qcco.commission.action;

import java.awt.event.ActionEvent;

import nc.ui.pubapp.uif2app.components.grand.action.GrandBodyAddLineAction;
import nc.vo.pub.BusinessException;

public class GrandAddLineAction extends GrandBodyAddLineAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8728368950625673675L;
	
	@Override
	public void doAction(ActionEvent e) throws Exception {
		int curRow = getMainForm().getBillCardPanel().getBodyPanel().getTable().getSelectedRow();
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
