package nc.ui.qcco.task.action;

import java.awt.event.ActionEvent;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pubapp.uif2app.actions.intf.ICardPanelDefaultActionProcessor;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.pubapp.uif2app.view.BillListView;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.qcco.commission.ace.view.WebBrowser;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.UIState;
import nc.ui.uif2.model.AbstractAppModel;
import nc.vo.pub.SuperVO;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.qcco.task.TaskBVO;

public class TaskStatusAction extends NCAction implements ICardPanelDefaultActionProcessor{

	
	private BillForm mainBillForm;//
	private ShowUpableBillForm grandCard;
	private BillListView listView;
	
	public BillForm getMainBillForm() {
		return mainBillForm;
	}

	public void setMainBillForm(BillForm mainBillForm) {
		this.mainBillForm = mainBillForm;
	}

	public BillListView getListView() {
		return listView;
	}

	public void setListView(BillListView listView) {
		this.listView = listView;
	}

	@Override
	public int getType() {
		// TODO 自动生成的方法存根
		return 0;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public TaskStatusAction() {
		setBtnName("任务状态");
		setCode("psayDemand");
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
			// 查询
			IUAPQueryBS iUAPQueryBS = (IUAPQueryBS) NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
			TaskBVO selectData = null;
			AbstractBill aggVO = (AbstractBill) this.getModel().getSelectedData();
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
			String url = (String) iUAPQueryBS.executeQuery(
					"select vdef1 from report_path where trim(nc_report_name) = '测试进度'", new ColumnProcessor());
			if (null == url) {
				url = "http://404";
			}
			WebBrowser.open(url + selectData.taskcode, selectData.taskcode + "任务状态");
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
}
