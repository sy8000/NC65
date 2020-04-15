package nc.ui.qcco.task.ace.handler;

import java.util.Vector;

import nc.ui.pub.bill.BillItem;
import nc.ui.pubapp.uif2app.components.grand.CardGrandPanelComposite;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.uif2.ShowStatusBarMsgUtil;

import org.apache.commons.lang.StringUtils;

public class AceBodyAfterEditHandler implements IAppEventHandler<CardBodyAfterEditEvent> {
	private ShowUpableBillForm grandCard;// mainBillForm
	private BillForm mainBillForm;//

	public BillForm getMainBillForm() {
		return mainBillForm;
	}

	public void setMainBillForm(BillForm mainBillForm) {
		this.mainBillForm = mainBillForm;
	}
	private CardGrandPanelComposite billForm;
	
	
	public CardGrandPanelComposite getBillForm() {
		return billForm;
	}

	public void setBillForm(CardGrandPanelComposite billForm) {
		this.billForm = billForm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleAppEvent(CardBodyAfterEditEvent e) {
		// 执行顺序改变后重新排序
		if ("runorder".equals(e.getKey())) {
			doSortAndReCode(e);
		} else if ("sampleallocation".equals(e.getKey())) {
			if (StringUtils.isEmpty((String) e.getValue())) {
				e.getBillCardPanel().setBodyValueAt(null, e.getRow(), "samplequantity");
				int rowu = this.getGrandCard().getBillCardPanel().getRowCount();
				if (rowu >= 0) {
					int[] rows = new int[rowu];
					for (int i = 0; i < rowu; i++) {
						rows[i] = i;
					}
					this.getGrandCard().getBillCardPanel().getBodyPanel("pk_task_r").delLine(rows);
				}
			}
		}
	}

	public ShowUpableBillForm getGrandCard() {
		return grandCard;
	}

	public void setGrandCard(ShowUpableBillForm grandCard) {
		this.grandCard = grandCard;
	}

	private void doSortAndReCode(CardBodyAfterEditEvent e) {
		// get 委托单号
		BillItem billItem = mainBillForm.getBillCardPanel().getHeadItem("pk_commission_h.billno");
		String commissionCode = "";
		if (billItem != null && billItem.getValueObject() != null
				&& !String.valueOf(billItem.getValueObject()).replaceAll(" ", "").equals("")) {
			commissionCode = String.valueOf(billItem.getValueObject());
		} else {
			commissionCode = "";
		}

		if ("runorder".equals(e.getKey())) {
			mainBillForm.getBillCardPanel().getBillModel().sortByColumn("runorder", true);
			Vector dataVector = e.getBillCardPanel().getBillModel().getDataVector();
			StringBuilder sb = new StringBuilder();
			sb.append(commissionCode);
			if (dataVector != null && dataVector.size() > 0) {
				for (int i = 0; i < dataVector.size(); i++) {
					if (dataVector != null) {
						int rowNoColNumber = e.getBillCardPanel().getBillModel().getBodyColByKey("rowno");
						
						if (rowNoColNumber >= 0) {
							// 改变行号
							if (dataVector.get(i) != null) {
								Vector colData = (Vector) dataVector.get(i);
								colData.set(rowNoColNumber, i + 1);
							}

						}
						int runorderColNumber = e.getBillCardPanel().getBillModel().getBodyColByKey("taskcode");
						if (runorderColNumber >= 0) {
							// 重新生成编号
							if (dataVector.get(i) != null) {
								sb.append("-");
								if (i < 9) {
									sb.append(0);
								}
								sb.append(i + 1);
								Vector colData = (Vector) dataVector.get(i);
								colData.set(runorderColNumber, sb.toString());
								sb.delete(sb.length() - 3, sb.length());
							}
						} else {
							ShowStatusBarMsgUtil.showErrorMsg("重新生成编号失败!", "未找到'任务编号'字段", e.getContext());
							break;
						}
						/*//precolumn
						int precolumnColNumber = e.getBillCardPanel().getBillModel().getBodyColByKey("precolumn");
						if (precolumnColNumber >= 0) {
							// 改变新增态下的precolumn ,保证孙表对应关系
							if (dataVector.get(i) != null) {
								Vector colData = (Vector) dataVector.get(i);
								colData.set(rowNoColNumber, i + 1);
							}
						}*/
					}
					//更改孙对应关系表
					
				}
			}

		}

	}

}
