package nc.ui.qcco.commission.action;

import java.awt.event.ActionEvent;

import nc.bs.framework.common.NCLocator;
import nc.itf.qcco.ICommissionMaintain;
import nc.itf.uap.IUAPQueryBS;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pubapp.uif2app.components.grand.CardGrandPanelComposite;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.qcco.commission.model.MainSubBillModel;
import nc.ui.uif2.actions.EditAction;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.commission.CommissionHVO;

public class ChangeAction extends EditAction {
	/**
	 * serial no
	 */
	private static final long serialVersionUID = 6398003887713048604L;
	private CardGrandPanelComposite mainGrandPanel;

	public CardGrandPanelComposite getMainGrandPanel() {
		return mainGrandPanel;
	}

	public void setMainGrandPanel(CardGrandPanelComposite mainGrandPanel) {
		this.mainGrandPanel = mainGrandPanel;
	}

	public ChangeAction() {
		setBtnName("���");
		setCode("ChangeAction");
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		super.doAction(e);

		((MainSubBillModel) this.getModel()).setChangeStatus(true);
		((MainSubBillModel) this.getModel()).resetBillFormEnableState();
		((MainSubBillModel) this.getModel()).cacheOldValues();
		
		
		// ˢ�²�Ʒ����
		AggCommissionHVO aggvo = (AggCommissionHVO) getModel()
				.getSelectedData();
		if (aggvo != null && aggvo.getParentVO() != null) {
			CommissionHVO hvo = aggvo.getParentVO();
			BillCardPanel mainBillCardPanel = ((BillForm) mainGrandPanel
					.getMainPanel()).getBillCardPanel();
			if (hvo.getPk_subcategory() != null) {
				((UIRefPane) mainBillCardPanel.getHeadItem("pk_subcategory")
						.getComponent()).setPK(hvo.getPk_subcategory());
			}
			if (hvo.getPk_lastcategory() != null) {
				((UIRefPane) mainBillCardPanel.getHeadItem("pk_lastcategory")
						.getComponent()).setPK(hvo.getPk_lastcategory());
			}
		}
	}

	@Override
	protected boolean isActionEnable() {
		// �����Ѿ��ύ�����޸�
		if (getModel().getSelectedData() != null) {
			AggCommissionHVO aggvo = (AggCommissionHVO) getModel().getSelectedData();
			if (aggvo.getPrimaryKey() != null) {
				ICommissionMaintain service = NCLocator.getInstance().lookup(ICommissionMaintain.class);
				if (!service.isEditAble(aggvo.getPrimaryKey())) {
					return false;
				}
			}
		}
		return super.isActionEnable();
	}

}
