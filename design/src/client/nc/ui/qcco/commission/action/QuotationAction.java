package nc.ui.qcco.commission.action;

import java.awt.event.ActionEvent;

import nc.bs.dao.DAOException;
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
import nc.ui.uif2.NCAction;
import nc.ui.uif2.UIState;
import nc.ui.uif2.model.AbstractAppModel;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.commission.CommissionHVO;
import nc.vo.qcco.qccommission.DocStates;

public class QuotationAction extends NCAction {

	/**
	 * serial no
	 */
	private static final long serialVersionUID = -1L;
	private IUAPQueryBS bs;
	
	private IUAPQueryBS getBS(){
		if(bs==null){
			bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		}
		return bs;
	}

	public QuotationAction() {
		setBtnName("报价单预览");
		setCode("quotation");
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
			String url = "\\\\lims_app.hongfa.cn\\lims_coa_sharemenu$\\Invoice\\%REPORT_NO%.pdf";
			AggCommissionHVO aggvo = (AggCommissionHVO) this.getModel().getSelectedData();
			url = url.replace("%REPORT_NO%", aggvo.getParentVO().getBillno());
			

			Object[] value = (Object[]) ConfirmDialog.showInputDlg(this.getModel().getContext().getEntranceUI(),
					ConfirmDialog.CONFIRM_PREVIEW, "报价单预览", "请输入意见", "", 200, 0, ConfirmDialog.TEXT_STR, url);

			int rtnID = (Integer) value[0];
			String txtMessage = (String) value[1];

			if (rtnID == ConfirmDialog.ID_CONFIRM) {
				try{
					confirtm(aggvo.getParentVO());
					MessageDialog.showHintDlg(getModel().getContext().getEntranceUI(), "结果", "确认成功!");
				}catch(Exception ex){
					MessageDialog.showErrorDlg(getModel().getContext().getEntranceUI(), "错误!", ex.getMessage());
				}
				
				
			} else if (rtnID == ConfirmDialog.ID_REJECT) {

			}

		} catch (Exception e) {
			Logger.error(e.getCause());
		}

	}

	private void confirtm(CommissionHVO commissionHVO) throws BusinessException {
		NCLocator.getInstance().lookup(ICommissionMaintain.class).quotationConfirtm(commissionHVO);
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
		//状态要为"报价单确认"
		String statusStr = ((CommissionHVO)hvo).getDocstatus();
		statusStr = (statusStr==null?"0":statusStr);
		int status = Integer.parseInt(statusStr);
		if(status < DocStates.QUOTATION_CONFIRMATION.toIntValue()){
			return false;
		}
		// 没回写的单据,不能使用此按钮
		try {
			Integer count = (Integer) getBS().executeQuery("select count(*) from project where name = '"
					+aggVO.getParent().getAttributeValue("billno")+"'",
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
