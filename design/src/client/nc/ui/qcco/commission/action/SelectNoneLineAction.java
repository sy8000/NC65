package nc.ui.qcco.commission.action;

import java.awt.event.ActionEvent;

import com.sun.org.apache.xml.internal.resolver.helpers.Debug;

import nc.bs.uif2.IActionCode;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.action.BillTableLineAction;
import nc.ui.pubapp.uif2app.actions.AbstractBodyTableExtendAction;
import nc.ui.pubapp.uif2app.actions.intf.ICardPanelDefaultActionProcessor;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.actions.ActionInitializer;
import nc.vo.pubapp.pattern.log.Log;

public class SelectNoneLineAction extends AbstractBodyTableExtendAction
		implements ICardPanelDefaultActionProcessor {

	private static final long serialVersionUID = 1L;

	public SelectNoneLineAction() {
		this.setCode(IActionCode.SELNONE);

	    NCAction action = new NCAction() {
	      private static final long serialVersionUID = 1L;

	      @Override
	      public void doAction(ActionEvent e) throws Exception {
	        // do nothing
	      }
	    };
	    ActionInitializer.initializeAction(action, IActionCode.SELNONE);
	    this.putActionValue(action);
	}

	@Override
	public int getType() {
		return 8;
	}

	@Override
	public void doAction() {
		int selectCol = getCardPanel().getBillTable().getSelectedColumn();
		if(getCardPanel().getBodyShowItems()!=null 
				&& getCardPanel().getBodyShowItems().length >=(selectCol+1)){
			BillItem showItem = getCardPanel().getBodyShowItems()[selectCol];
			String selectKey = showItem.getKey();
			BillItem[] data = getCardPanel().getBodyItems();
			if(data!=null&&data.length > 0){
				try{
					for(int i = 0 ;i < data.length ; i++){
						if(selectKey.equals("judgeflag")){
							getCardPanel().setBodyValueAt(false, i, "judgeflag");	
						}
						if(selectKey.equals("testflag")){
							getCardPanel().setBodyValueAt(false, i, "testflag");
						}
					}
				}catch(Exception e){
					Log.debug(getClass().getName()+"È«Ñ¡Ê§°Ü!");
				}
				
			}
		}
	}

}
