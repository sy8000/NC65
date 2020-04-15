package nc.ui.qcco.task.action;

import java.awt.event.ActionEvent;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.ui.qcco.commission.ace.view.WebBrowser;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.UIState;
import nc.ui.uif2.model.AbstractAppModel;
import nc.vo.pub.SuperVO;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.task.AggTaskHVO;

public class CommissionPreviewAction extends NCAction {
	public CommissionPreviewAction() {
		setBtnName("委托单预览");
		setCode("commissionpreview");
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
	public void doAction(ActionEvent arg0) throws Exception {
		try {
			// 查询
			IUAPQueryBS iUAPQueryBS = (IUAPQueryBS) NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
			String url = (String) iUAPQueryBS.executeQuery(
					"select vdef1 from report_path where nc_report_type = 'PROJ'", new ColumnProcessor());
			if (null == url) {
				url = "404.html";
			} else {
				AggTaskHVO aggvo = (AggTaskHVO) this.getModel().getSelectedData();
				/*url = url.replace("%REPORT_NAME%", aggvo.getParentVO().getBillno());
				url = url.replace("%REPORT_NO%", aggvo.getParentVO().getBillno());

				WebBrowser.open(url, "委托单预览");*/
				url = url + aggvo.getParentVO().getBillno();
				WebBrowser.open(url, "委托单预览");
			}

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
