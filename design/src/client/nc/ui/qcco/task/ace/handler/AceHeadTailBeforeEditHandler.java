package nc.ui.qcco.task.ace.handler;

import java.util.HashSet;
import java.util.Set;

import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardHeadTailBeforeEditEvent;
import nc.ui.qcco.commission.refmodel.CommissionRefModel;
import nc.ui.qcco.commission.refmodel.ProductContactRefModel;
import nc.ui.qcco.commission.refmodel.ProductLastClassRefModel;
import nc.ui.qcco.commission.refmodel.ProductSecondClassRefModel;

public class AceHeadTailBeforeEditHandler implements
		IAppEventHandler<CardHeadTailBeforeEditEvent> {

	@Override
	public void handleAppEvent(CardHeadTailBeforeEditEvent e) {
		if ("pk_commission_h".equals(e.getKey())) {
			if (e.getBillCardPanel().getHeadItem("pk_task_h").getValue()!=null) {
				e.setReturnValue(false);
				return;
			}
			BillItem billItem = (BillItem) e.getSource();
			((CommissionRefModel) ((UIRefPane) billItem.getComponent())
					.getRefModel()).setWherePart("1=1  and isnull(dr,0)=0 and QC_COMMISSION_H.BILLNO not in (select billno from qc_task_h where   billno is not null and dr != 1)");;
					
			UIRefPane pane = (UIRefPane) (e.getBillCardPanel().getHeadItem("pk_commission_h").getComponent());
			CommissionRefModel refModel = (CommissionRefModel) (pane.getRefModel());
			refModel.setCacheEnabled(false);
			pane.setCacheEnabled(false);
			
			refModel.reloadData();
			refModel.fireChange();
		}
		e.setReturnValue(true);
	}
}
