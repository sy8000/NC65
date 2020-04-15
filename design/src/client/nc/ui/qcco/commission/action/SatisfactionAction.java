package nc.ui.qcco.commission.action;

import java.awt.event.ActionEvent;

import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.itf.pubapp.pub.smart.IBillQueryService;
import nc.itf.qcco.ICommissionMaintain;
import nc.md.persist.framework.MDPersistenceService;
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
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.commission.CommissionHVO;
import nc.vo.qcco.qccommission.DocStates;

public class SatisfactionAction extends NCAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1L;

	public SatisfactionAction() {
		setBtnName("满意度评价");
		setCode("satisfaction");
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
		String value = (String) ConfirmDialog.showSelectDlg(this.getModel().getContext().getEntranceUI(), "满意度评价",
				"请选择评价内容", new String[] { "非常满意", "比较满意", "满意", "不满意" }, 4);
		/*if (rtnID == ConfirmDialog.ID_CONFIRM) {
			AggCommissionHVO aggvo = (AggCommissionHVO) this.getModel().getSelectedData();
			try{
				confirtm(aggvo.getParentVO());
				MessageDialog.showHintDlg(getModel().getContext().getEntranceUI(), "消息", "确认成功!");
			}catch (Exception ex) {
				MessageDialog.showErrorDlg(getModel().getContext().getEntranceUI(), "错误", ex.getMessage());
			}
			
		} else if (rtnID == ConfirmDialog.ID_REJECT) {

		}
		confirtm()*/
		if(value!=null){
			AggCommissionHVO aggvo = (AggCommissionHVO) this.getModel().getSelectedData();
			confirtm(aggvo.getParentVO());
		}
		
	}
	
	private void confirtm(CommissionHVO parentVO) throws BusinessException {
		NCLocator.getInstance().lookup(ICommissionMaintain.class).satisfactComfirtm(parentVO);
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
		//状态要为"满意度评价"
		String statusStr = ((CommissionHVO) hvo).getDocstatus();
		statusStr = (statusStr == null ? "0" : statusStr);
		int status = Integer.parseInt(statusStr);
		if (status != DocStates.SATISFACTION_EVALUATION.toIntValue()) {
			return false;
		}
		return this.getModel().getUiState() == UIState.NOT_EDIT;
	}
}
