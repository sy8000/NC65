package nc.ui.qcco.commission.ace.handler;

import java.util.Vector;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyBeforeEditEvent;
import nc.ui.qcco.commission.refmodel.EntTypeRefModel;
import nc.ui.qcco.commission.refmodel.ProductContactRefModel;
import nc.ui.qcco.commission.refmodel.ProductPointRefModel;
import nc.ui.qcco.commission.refmodel.ProductSerialRefModel;
import nc.ui.qcco.commission.refmodel.ProductStructRefModel;
import nc.ui.qcco.commission.refmodel.TestInitRefModel;

public class AceBodyBeforeEditHandler implements IAppEventHandler<CardBodyBeforeEditEvent> {

	@Override
	public void handleAppEvent(CardBodyBeforeEditEvent e) {
		BillItem bitem = (BillItem) e.getSource();

		if ("productserial".equals(e.getKey())) {
			// 产品系列
			String code = ((UIRefPane) ((BillItem) e.getBillCardPanel().getHeadItem("pk_maincategory")).getComponent())
					.getRefCode();
			((ProductSerialRefModel) ((UIRefPane) bitem.getComponent()).getRefModel()).setFirstClassCode(code);
			code = ((UIRefPane) ((BillItem) e.getBillCardPanel().getHeadItem("pk_subcategory")).getComponent())
					.getRefCode();
			((ProductSerialRefModel) ((UIRefPane) bitem.getComponent()).getRefModel()).setSecondClassCode(code);
			code = ((UIRefPane) ((BillItem) e.getBillCardPanel().getHeadItem("pk_lastcategory")).getComponent())
					.getRefCode();
			((ProductSerialRefModel) ((UIRefPane) bitem.getComponent()).getRefModel()).setThirdClassCode(code);
			
		} else if ("enterprisestandard".equals(e.getKey())) {
			// 企业标准
			String productserial = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_productserial");
			((EntTypeRefModel) ((UIRefPane) bitem.getComponent()).getRefModel()).setPk_basprod_type(productserial);
		} else if ("productspec".equals(e.getKey())) {
			// 规格号
			String productserial = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_productserial");
			String basentype = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_enterprisestandard");
			((ProductPointRefModel) ((UIRefPane) bitem.getComponent()).getRefModel()).setPk_basprod_type(productserial);
			((ProductPointRefModel) ((UIRefPane) bitem.getComponent()).getRefModel()).setPk_basen_type(basentype);
		} else if ("structuretype".equals(e.getKey())) {
			// 结构类型
			String productserial = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_productserial");
			String basentype = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_enterprisestandard");
			String productspec = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_productspec");
			((ProductStructRefModel) ((UIRefPane) bitem.getComponent()).getRefModel())
					.setPk_basprod_type(productserial);
			((ProductStructRefModel) ((UIRefPane) bitem.getComponent()).getRefModel()).setPk_basen_type(basentype);
			((ProductStructRefModel) ((UIRefPane) bitem.getComponent()).getRefModel()).setPk_basprod_point(productspec);
		} else if ("ref_contacttype".equals(e.getKey())) {
			// 触点类型
			String productserial = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_productserial");
			String basentype = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_enterprisestandard");
			String productspec = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_productspec");
			String productstruct = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_structuretype");
			UIRefPane pane = (UIRefPane) (e.getBillCardPanel().getBodyItem("ref_contacttype").getComponent());
			ProductContactRefModel refModel = (ProductContactRefModel) (pane.getRefModel());
			refModel.setCacheEnabled(false);
			pane.setCacheEnabled(false);
			refModel.setPk_basprod_type(productserial);
			refModel.setPk_basen_type(basentype);
			refModel.setPk_basprod_point(productspec);
			refModel.setPk_basprod_struct(productstruct);
			refModel.reloadData();
			refModel.fireChange();
		} else if ("pk_analysisref".equals(e.getKey())) {
			String entStandard = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "enterprisestandard");
			String productSpec = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "productspec");
			String productGrade = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "structuretype");
			String productStage = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "productstage");
			((TestInitRefModel) ((UIRefPane) bitem.getComponent()).getRefModel()).setEnterpriseStandard(entStandard);
			((TestInitRefModel) ((UIRefPane) bitem.getComponent()).getRefModel()).setProductSpec(productSpec);
			((TestInitRefModel) ((UIRefPane) bitem.getComponent()).getRefModel()).setProductGrade(productGrade);
			((TestInitRefModel) ((UIRefPane) bitem.getComponent()).getRefModel()).setProductStage(productStage);
		}
		fixValueLost(bitem,e);
		e.setReturnValue(true);
	}
	public void fixValueLost(BillItem bitem,CardBodyBeforeEditEvent e){
		if(bitem!=null){
			AbstractRefModel refModel = ((UIRefPane) bitem.getComponent()).getRefModel();
			if(refModel!=null){
				String pk_value = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_"+e.getKey());
				if(pk_value!=null){
					Vector vector = refModel.matchPkData(pk_value);
					refModel.setSelectedData(vector);
				}
				
			}
			
		}
		
	}
}
