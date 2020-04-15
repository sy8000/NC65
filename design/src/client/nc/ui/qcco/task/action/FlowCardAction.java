package nc.ui.qcco.task.action;

import java.awt.event.ActionEvent;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.qcco.commission.ace.view.WebBrowser;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.UIState;
import nc.ui.uif2.model.AbstractAppModel;
import nc.vo.pub.SuperVO;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.qcco.qccommission.DocStates;
import nc.vo.qcco.task.AggTaskHVO;
import nc.vo.qcco.task.TaskBVO;
import nc.ui.pubapp.uif2app.actions.AbstractBodyTableExtendAction;
import nc.vo.qcco.task.TaskHBodyVO;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.pubapp.uif2app.view.BillListView;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.pubapp.uif2app.view.ShowUpableBillListView;
import nc.ui.pubapp.uif2app.actions.BodyDelLineAction;
import nc.ui.pubapp.uif2app.actions.intf.ICardPanelDefaultActionProcessor;
import nc.ui.pubapp.uif2app.components.grand.ListGrandPanelComposite;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterRowEditEvent;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.vo.qcco.task.TaskHBodyVO;

public class FlowCardAction extends NCAction implements ICardPanelDefaultActionProcessor{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	private BillForm mainBillForm;//
	private ShowUpableBillForm grandCard;
	private BillListView listView;
	
	
	public BillListView getListView() {
		return listView;
	}

	public void setListView(BillListView listView) {
		this.listView = listView;
	}

	public ShowUpableBillForm getGrandCard() {
		return grandCard;
	}

	public void setGrandCard(ShowUpableBillForm grandCard) {
		this.grandCard = grandCard;
	}

	public FlowCardAction() {
		setBtnName("流程卡");
		setCode("flowcard");
	}

	public BillForm getMainBillForm() {
		return mainBillForm;
	}

	public void setMainBillForm(BillForm mainBillForm) {
		this.mainBillForm = mainBillForm;
	}

	protected AbstractAppModel model = null;

	public AbstractAppModel getModel() {
		return model;
	}

	public void setModel(AbstractAppModel model) {
		this.model = model;
		this.model.addAppEventListener(this);
	}

	@Override
	public void doAction(ActionEvent paramActionEvent) throws Exception {
		try {
			
			TaskBVO selectData = null;
			// 查询
			IUAPQueryBS iUAPQueryBS = (IUAPQueryBS) NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
			AbstractBill aggVO = (AbstractBill) this.getModel().getSelectedData();
			String billNo = (String)aggVO.getParentVO().getAttributeValue("billno");
			int docstatus = Integer.valueOf((String)iUAPQueryBS.executeQuery(
					"select h.docstatus from qc_commission_h h where h.billno = '" + billNo +"'", new ColumnProcessor()));
			if(docstatus < 6){
				MessageDialog.showErrorDlg(getModel().getContext().getEntranceUI(), "错误", "委托单："+ billNo +"尚未接收样品，流程卡无法查看！");
				return;
			}
			int selectRow = -1;
			if(getListView().isShowing()){
				selectRow = getListView().getBillListPanel().getBodyTable().getSelectedRow();
			}else{
				selectRow = getMainBillForm().getBillCardPanel().getBodyPanel().getTable().getSelectedRow();
			}
			
			if(selectRow>=0){
				selectData = (TaskBVO)aggVO.getChildren(TaskBVO.class)[selectRow];
			}else{
				MessageDialog.showHintDlg(this
						.getModel().getContext().getEntranceUI(), null, "没有选中任何行!"); 
				return;
			}
			
			//ShowUpableBillForm s = this.getGrandCard();
			//getCardPanel()
		    /*BillCardPanel currentPanel = this.getMainBillForm().getBillCardPanel();
		    int rows = currentPanel.getBillTable().getSelectedRow();*/
			//TaskBVO selectCol = aggVO.getChildrenVO();
			
			String task_code = "";
			//int i = getMainBillForm().getBillCardPanel().getTabbedPane(0).getSelectedIndex();
			
			
			String url = (String) iUAPQueryBS.executeQuery(
					"select vdef1 from report_path where trim(nc_report_type) = 'TASK'", new ColumnProcessor());
			if (null == url) {
				url = "http://404";
			}
			WebBrowser.open(url + selectData.taskcode, "流程卡"); 
			
		} catch (Exception e) {
			Logger.error(e.getCause());
		}

	}

	protected boolean isActionEnable() {
		AbstractBill aggVO = (AbstractBill) this.getModel().getSelectedData();
		if (aggVO == null) {
			return false;
		}
		SuperVO hvo = (SuperVO) aggVO.getParentVO();
		if (hvo == null) {
			return false;
		}
		
		return this.getModel().getUiState() == UIState.NOT_EDIT;
	}

	@Override
	public int getType() {
		// TODO 自动生成的方法存根
		return 7;
	}
}
