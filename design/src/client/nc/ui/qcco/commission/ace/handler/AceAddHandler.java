package nc.ui.qcco.commission.ace.handler;

import java.text.SimpleDateFormat;
import java.util.Map;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapProcessor;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillItem;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.billform.AddEvent;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFLiteralDate;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.qcco.qccommission.DocStates;

import org.apache.commons.lang.StringUtils;

public class AceAddHandler implements IAppEventHandler<AddEvent> {

	@Override
	public void handleAppEvent(AddEvent e) {
		String pk_group = e.getContext().getPk_group();
		String pk_org = e.getContext().getPk_org();
		BillCardPanel panel = e.getBillForm().getBillCardPanel();
		// ��������֯Ĭ��ֵ
		panel.setHeadItem("pk_group", pk_group);
		panel.setHeadItem("pk_org", pk_org);

		IUAPQueryBS query = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		String defaultType = null;
		String pk_prefix = null;
		String maxcode = null;
		String pk_psndoc = null;
		String pk_psnorg = null;
		String pk_dept = null;
		String psnName = null;
		String psnEmail = null;
		String psnPhone = null;
		try {
			// ί�е�����
			defaultType = (String) query.executeQuery(" select pk_proj_type from NC_PROJ_TYPE where isdefault=1",
					new ColumnProcessor());
			// ί�е�����ǰ׺
			pk_prefix = (String) query.executeQuery("select pk_safe_type from NC_PROJ_PREFIX where isenable=1",
					new ColumnProcessor());
			// ��Ա����
			pk_psndoc = (String) query.executeQuery("select pk_psndoc from sm_user where cuserid = '"
					+ InvocationInfoProxy.getInstance().getUserId() + "'", new ColumnProcessor());
			// ��Ա������֯
			pk_psnorg = (String) query.executeQuery("select pk_org from sm_user where cuserid = '"
					+ InvocationInfoProxy.getInstance().getUserId() + "'", new ColumnProcessor());
			// Ա���������ڲ���
			if (!StringUtils.isEmpty(pk_psndoc)) {
				pk_dept = (String) query.executeQuery("select pk_dept from bd_psnjob where pk_psndoc = '" + pk_psndoc
						+ "' and '" + new UFLiteralDate().toString()
						+ "' between indutydate and isnull(enddutydate, '9999-12-31')", new ColumnProcessor());

				Map vals = (Map) query.executeQuery(
						"select name, email, isnull(mobile, '') teleno from bd_psndoc where pk_psndoc =  '"
								+ pk_psndoc + "'", new MapProcessor());
				psnName = (String) vals.get("name");
				psnEmail = (String) vals.get("email");
				psnPhone = (String) vals.get("teleno");
			}
		} catch (BusinessException ex) {
			ExceptionUtils.wrappBusinessException(ex.getMessage());
		}
		panel.setHeadItem("pk_commissiontype", defaultType);
		panel.getHeadItem("pk_commissiontype").setEnabled(true);
		panel.getHeadItem("pk_commissiontype").setEdit(true);
		panel.setHeadItem("codeprefix", pk_prefix);
		panel.setHeadItem("pk_owner", pk_psnorg);
		panel.setHeadItem("pk_payer", pk_psnorg);
		panel.setHeadItem("pk_dept", pk_dept);
		panel.setHeadItem("cuserid", pk_psndoc);
		panel.setHeadItem("contract", psnName);
		panel.setHeadItem("email", psnEmail);
		panel.setHeadItem("teleno", psnPhone);
		BillItem item = panel.getHeadItem("docstatus");
		if(item!=null){
			UIComboBox u = (UIComboBox)panel.getHeadItem("docstatus").getComponent();
			if(u!=null){
				u.setSelectedIndex(DocStates.COMISSION_CREATE.toIntValue());
			}
			
		}
		
		
		
		UIRefPane pane = (UIRefPane) panel.getHeadItem("codeprefix").getComponent();
		String name = pane.getRefModel().getRefNameValue();
		SimpleDateFormat dt = new SimpleDateFormat("yyMMdd");
		String seed = name + "-" + dt.format(new UFDate().toDate()) + "-";

		try {
			maxcode = (String) query.executeQuery(
					"select max(billno) from qc_commission_h where billno like 'A-______-____' and billno not like 'A-______-9999'",
					new ColumnProcessor());
		} catch (BusinessException ex) {
			ExceptionUtils.wrappBusinessException(ex.getMessage());
		}
		maxcode = maxcode == null || maxcode.equals("") ? "0000" : maxcode.substring(maxcode.length() - 4);
		panel.setHeadItem("billno", seed + String.format("%04d", Integer.valueOf(maxcode) + 1));
	}
}
