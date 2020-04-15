package nc.ui.qcco.commission.action;

import java.awt.event.ActionEvent;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.pubapp.pub.smart.IBillQueryService;
import nc.itf.qcco.ICommissionMaintain;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pubapp.uif2app.components.grand.ListGrandPanelComposite;
import nc.ui.qcco.commission.ace.view.ConfirmDialog;
import nc.ui.qcco.commission.ace.view.TaskBodyPanel;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.UIState;
import nc.ui.uif2.model.AbstractAppModel;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.commission.CommissionHVO;
import nc.vo.qcco.qccommission.DocStates;

public class OfficialReportAction extends NCAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public OfficialReportAction() {
		setBtnName("正式报告预览");
		setCode("officialReport");
	}
	private IUAPQueryBS bs;
	
	private IUAPQueryBS getBS(){
		if(bs==null){
			bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		}
		return bs;
	}
	protected AbstractAppModel model = null;

	public AbstractAppModel getModel() {
		return model;
	}

	public void setModel(AbstractAppModel model) {
		this.model = model;
		this.model.addAppEventListener(this);
	}
	private ListGrandPanelComposite listView = null;
	
	
	
	public ListGrandPanelComposite getListView() {
		return listView;
	}

	public void setListView(ListGrandPanelComposite listView) {
		this.listView = listView;
	}

	@Override
	public void doAction(ActionEvent paramActionEvent) throws Exception {
		try {
			// 查询
			IUAPQueryBS iUAPQueryBS = (IUAPQueryBS) NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
			AggCommissionHVO aggvo = (AggCommissionHVO) this.getModel().getSelectedData();
			String reportformat = aggvo.getParentVO().getReportformat();
			//查询报告类型
			String reportName = (String) iUAPQueryBS.executeQuery(
					" select RP_REPORT_CODE from NC_REPORT_TYPE "
					+ " WHERE ISENABLE = 1 and PK_REPORT_TYPE = '"+reportformat+"'", new ColumnProcessor());
			if(reportName!=null && reportName.replaceAll(" ", "").equals("3")){
				ConfirmDialog.urlChoosePanel = new TaskBodyPanel(aggvo.getPrimaryKey());
			}
			String billno = aggvo.getParentVO().getBillno();
			
			String url = (String) iUAPQueryBS.executeQuery(
					" select r.report_file_name from reports r where r.report_number = "
					+ " (select project.c_rpt_report_number from project where name = '"+billno+"')", new ColumnProcessor());
			if (null == url) {
				url = "http://404";
			}
			Object[] value = (Object[]) ConfirmDialog.showInputDlg(this.getModel().getContext().getEntranceUI(),
					ConfirmDialog.CONFIRM_REJECT_PREVIEW, "正式报告预览", "请输入意见", "", 200, 0, ConfirmDialog.TEXT_STR, url);
			ConfirmDialog.urlChoosePanel = null;
			int rtnID = (Integer) value[0];
			String txtMessage = (String) value[1];
			if (rtnID == ConfirmDialog.ID_CONFIRM) {
				try{
					confirtm(aggvo.getParentVO(),txtMessage);
					MessageDialog.showHintDlg(getModel().getContext().getEntranceUI(), "消息", "确认成功!");
				}catch (Exception ex) {
					MessageDialog.showErrorDlg(getModel().getContext().getEntranceUI(), "错误", ex.getMessage());
				}
			} else if (rtnID == ConfirmDialog.ID_REJECT) {
				try{
					reject(aggvo.getParentVO(),txtMessage);
					MessageDialog.showHintDlg(getModel().getContext().getEntranceUI(), "消息", "驳回成功!");
				}catch (Exception ex) {
					MessageDialog.showErrorDlg(getModel().getContext().getEntranceUI(), "错误", ex.getMessage());
				}
				
			}
		} catch (Exception e) {
			Logger.error(e.getCause());

		}
	}

	private void confirtm(CommissionHVO parentVO,String txtMessage) throws BusinessException {
		NCLocator.getInstance().lookup(ICommissionMaintain.class).officialComfirm(parentVO,txtMessage);
		if(listView.getMainPanel().isShowing()){
			//列表态
			listView.getDataManager().refresh();
		}else{
			//卡片态
			AbstractBill oldVO = (AbstractBill)getModel().getSelectedData();
	        String pk = oldVO.getParentVO().getPrimaryKey();
	        IBillQueryService billQuery = (IBillQueryService)NCLocator.getInstance().lookup(IBillQueryService.class);
	        
	        AggregatedValueObject newVO = billQuery.querySingleBillByPk(oldVO.getClass(), pk);
	        
	        if (newVO == null)
	        {
	          throw new BusinessException(NCLangRes.getInstance().getStrByID("uif2", "RefreshSingleAction-000000"));
	        }
	        
	        model.directlyUpdate(newVO);
		}
	}
	private void reject(CommissionHVO parentVO,String txtMessage) throws BusinessException {
		NCLocator.getInstance().lookup(ICommissionMaintain.class).officialReject(parentVO,txtMessage);
		if(listView.getMainPanel().isShowing()){
			//列表态
			listView.getDataManager().refresh();
		}else{
			//卡片态
			AbstractBill oldVO = (AbstractBill)getModel().getSelectedData();
	        String pk = oldVO.getParentVO().getPrimaryKey();
	        IBillQueryService billQuery = (IBillQueryService)NCLocator.getInstance().lookup(IBillQueryService.class);
	        
	        AggregatedValueObject newVO = billQuery.querySingleBillByPk(oldVO.getClass(), pk);
	        
	        if (newVO == null)
	        {
	          throw new BusinessException(NCLangRes.getInstance().getStrByID("uif2", "RefreshSingleAction-000000"));
	        }
	        
	        model.directlyUpdate(newVO);
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
		// 状态要为"报告待确认"
		String statusStr = ((CommissionHVO) hvo).getDocstatus();
		statusStr = (statusStr == null ? "0" : statusStr);
		int status = Integer.parseInt(statusStr);
		if (status < DocStates.REPORT_CONFIRMED.toIntValue()) {
			return false;
		}
		// 没回写的单据,不能使用此按钮
		try {
			Integer count = (Integer) getBS().executeQuery(
					"select count(*) from project where name = '" + aggVO.getParent().getAttributeValue("billno") + "'",
					new ColumnProcessor());
			if (count <= 0) {
				return false;
			}
		} catch (BusinessException e) {
			ExceptionUtils.wrappException(e);
		}
		return this.getModel().getUiState() == UIState.NOT_EDIT;
	}
}
