package nc.ui.qcco.commission.ace.handler;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapProcessor;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardHeadTailAfterEditEvent;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

public class AceHeadTailAfterEditHandler implements IAppEventHandler<CardHeadTailAfterEditEvent> {

	@Override
	public void handleAppEvent(CardHeadTailAfterEditEvent e) {
		if ("pk_maincategory".equals(e.getKey())) {
			((UIRefPane) e.getBillCardPanel().getHeadItem("pk_subcategory").getComponent()).setPK(null);
			((UIRefPane) e.getBillCardPanel().getHeadItem("pk_lastcategory").getComponent()).setPK(null);
			clearBody(e.getBillCardPanel());
		} else if ("pk_subcategory".equals(e.getKey())) {
			((UIRefPane) e.getBillCardPanel().getHeadItem("pk_lastcategory").getComponent()).setPK(null);
			clearBody(e.getBillCardPanel());
		} else if ("pk_lastcategory".equals(e.getKey())) {
			clearBody(e.getBillCardPanel());
		} else if ("pk_owner".equals(e.getKey())) {
			((UIRefPane) e.getBillCardPanel().getHeadItem("pk_dept").getComponent()).setPK(null);
		} else if ("codeprefix".equals(e.getKey())) {
			if (e.getValue() != null) {
				try {
					UIRefPane pane = (UIRefPane) e.getSource();
					String name = pane.getRefModel().getRefNameValue();
					SimpleDateFormat dt = new SimpleDateFormat("yyMMdd");
					String seed = name + "-" + dt.format(new UFDate().toDate());
					IUAPQueryBS query = NCLocator.getInstance().lookup(IUAPQueryBS.class);
					String maxcode = (String) query.executeQuery(
							"select max(billno) from QC_COMMISSION_H where billno like '" + seed + "%'",
							new ColumnProcessor());
					maxcode = maxcode == null || maxcode.equals("") ? "0000" : maxcode.substring(maxcode.length() - 4);
					e.getBillCardPanel().getHeadItem("billno")
							.setValue(seed + "-" + String.format("%04d", Integer.valueOf(maxcode) + 1));
				} catch (Exception ex) {
					ExceptionUtils.wrappBusinessException(ex.getMessage());
				}
			} else {
				e.getBillCardPanel().getHeadItem("billno").setValue("");
			}
		} else if ("pk_commissiontype".equals(e.getKey())) {
			UIRefPane billItem = (UIRefPane) e.getSource();
			String typeName = billItem.getRefModel().getRefNameValue();
			if (typeName != null) {
				String[] templates = CommissionShowTemplate.getTemplateByName(typeName);
				String[] allTemplateFields = CommissionShowTemplate.getTemplateWithAllField();
				Set<String> templatesSet = new HashSet();
				
				//先把模板字段设为null,如果是模板之外的,不清,反正是全部显示
				//清空时,不清空此模板包含的字段
				if(templates!=null && templates.length > 0){
					for(String tmp : templates){
						templatesSet.add(tmp);
					}
					for(String temp : allTemplateFields){
						if(!templatesSet.contains(temp)){
							e.getBillCardPanel().getHeadItem(temp).setValue(null);
						}
						
					}
				}
				
				e.getBillCardPanel().hideHeadItem(allTemplateFields);
				if(templates == null){
					templates = allTemplateFields;
				}
				e.getBillCardPanel().showHeadItem(templates);
				
			}
		} else if ("cuserid".equals(e.getKey())){
			try {
				String tellno = (String)NCLocator.getInstance().lookup(IUAPQueryBS.class).executeQuery(
						"select mobile from bd_psndoc where pk_psndoc =  '"
								+ String.valueOf(e.getValue()) + "'", new ColumnProcessor());
				e.getBillCardPanel().setHeadItem("teleno", tellno);
			} catch (BusinessException e1) {
				Logger.error(e1);
			}
		}
	}

	private void clearBody(BillCardPanel billcardpanel) {
		for (int i = billcardpanel.getBillModel().getRowCount() - 1; i >= 0; i--) {
			billcardpanel.getBodyPanel().delLine(new int[] { i });
		}
	}

}
