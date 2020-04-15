package nc.ui.qcco.commission.action;

import java.awt.event.ActionEvent;
import java.net.URL;

import nc.bap.portal.page.dialog.Dialog;
import nc.bs.framework.common.NCLocator;
import nc.bs.uif2.IActionCode;
import nc.itf.qcco.ICommissionMaintain;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pubapp.pub.task.ISingleBillService;
import nc.ui.pubapp.uif2app.components.grand.model.MainGrandModel;
import nc.ui.pubapp.uif2app.model.BillManageModel;
import nc.ui.sm.login.ClientAssistant;
import nc.ui.uif2.IShowMsgConstant;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.UIState;
import nc.ui.uif2.actions.ActionInitializer;
import nc.ui.uif2.components.CommonConfirmDialogUtils;
import nc.vo.pub.SuperVO;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.tmpub.util.DialogTool;

public class CommissionDeleteAction extends
		nc.ui.pubapp.uif2app.actions.pflow.DeleteScriptAction {

	/**
	 * nc.ui.pubapp.uif2app.actions.DeleteAction
	 */
	private static final long serialVersionUID = -6347211104481801200L;
	private MainGrandModel mainGrandModel;
	private BillManageModel model;
	private ISingleBillService<AggCommissionHVO> singleBillService;

	@Override
	public void doAction(ActionEvent e) throws Exception {
		 
		if (4 == CommonConfirmDialogUtils.showConfirmDeleteDialog(getModel()
				.getContext().getEntranceUI())) {
			Object value = this.getMainGrandModel().getSelectedData();
			if (value != null) {
				Object object = this.getSingleBillService().operateBill(
						(AggCommissionHVO) value);
				this.getModel().directlyDelete(object);
				this.getMainGrandModel().directlyDelete(object);
				this.showSuccessInfo();
			}
		}
	}

	public CommissionDeleteAction() {
		super();
		ActionInitializer.initializeAction(this, IActionCode.DELETE);
	}

	protected void showSuccessInfo() {
		ShowStatusBarMsgUtil.showStatusBarMsg(
				IShowMsgConstant.getDelSuccessInfo(), getModel().getContext());
	}

	public ISingleBillService<AggCommissionHVO> getSingleBillService() {
		return singleBillService;
	}

	public void setSingleBillService(
			ISingleBillService<AggCommissionHVO> singleBillService) {
		this.singleBillService = singleBillService;
	}

	public MainGrandModel getMainGrandModel() {
		return mainGrandModel;
	}

	public void setMainGrandModel(MainGrandModel mainGrandModel) {
		this.mainGrandModel = mainGrandModel;
	}

	public BillManageModel getModel() {
		return model;
	}

	public void setModel(BillManageModel model) {
		this.model = model;
		this.model.addAppEventListener(this);
	}

	@Override
	protected boolean isActionEnable() {
		AbstractBill aggVO = (AbstractBill) getModel().getSelectedData();
		if (aggVO == null) {
			return false;
		}
		SuperVO hvo = (SuperVO) aggVO.getParentVO();
		if (hvo == null) {
			return false;
		}
		// �����Ѿ��ύ�����޸�
		if (hvo.getPrimaryKey() != null) {
			ICommissionMaintain service = NCLocator.getInstance().lookup(ICommissionMaintain.class);
			if (!service.isEditAble(hvo.getPrimaryKey())) {
				return false;
			}
		}
		return getModel().getUiState() == UIState.NOT_EDIT;
	}
}
