package nc.ui.qcco.commission.ace.handler;

import nc.ui.pub.beans.UIRefPane;
import nc.ui.pubapp.uif2app.components.grand.model.MainGrandModel;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyBeforeEditEvent;
import nc.ui.pubapp.uif2app.model.BillManageModel;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.qcco.commission.refmodel.AnalyseComponentRefModel;
import nc.vo.pub.lang.UFBoolean;

public class GrandBodyBeforeEditHandler implements IAppEventHandler<CardBodyBeforeEditEvent> {

	public GrandBodyBeforeEditHandler(BillForm billform) {
		super();
		this.billform = billform;

	}

	private BillForm billform;

	public BillForm getBillform() {
		return billform;
	}

	public void setBillform(BillForm billform) {
		this.billform = billform;
	}
	
	@Override
	public void handleAppEvent(CardBodyBeforeEditEvent e) {
		e.setReturnValue(true);
		if ("component".equals(e.getKey())) {
			int curRow = billform.getBillCardPanel().getBodyPanel().getTable().getSelectedRow();
			String pk_enterprisestandard = (String) billform.getBillCardPanel().getBodyValueAt(curRow,
					"pk_enterprisestandard");
			
			UIRefPane pane = (UIRefPane) (e.getBillCardPanel().getBodyItem("component").getComponent());
			AnalyseComponentRefModel refModel = (AnalyseComponentRefModel) (pane.getRefModel());
			refModel.setCacheEnabled(false);
			pane.setCacheEnabled(false);
			//客户那估计模板乱了,上两个
			UFBoolean ifAuto = (UFBoolean) e.getBillCardPanel().getBodyValueAt(e.getRow(), "isAutoGeneration");
			UFBoolean ifautu = (UFBoolean) e.getBillCardPanel().getBodyValueAt(e.getRow(), "isautogeneration");
			if ((ifAuto != null && !ifAuto.booleanValue())||(ifautu != null && !ifautu.booleanValue())) {
				refModel.setPk_ncEnstardCode(pk_enterprisestandard);
			} else {
				refModel.setPk_ncEnstardCode(null);
			}

			refModel.reloadData();
			refModel.fireChange();
		}
	}
}
