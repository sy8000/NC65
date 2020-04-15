package nc.ui.qcco.task.ace.handler;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UITextAreaScrollPane;
import nc.ui.pub.bill.BillItem;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyBeforeEditEvent;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.qcco.commission.refmodel.ValueTypeRefModel;
import nc.ui.qcco.task.view.RefValuePanel;
import nc.ui.qcco.task.view.TextDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.qcco.commission.refmodel.SampleGroupRefModel;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.ui.qcco.task.refmodel.TaskAnalyseComponentRefModel;
import nc.ui.uif2.UIState;

import org.apache.commons.lang.StringUtils;

public class GrandBodyBeforeEditHandler implements IAppEventHandler<CardBodyBeforeEditEvent> {

	private BillForm mainBillForm;//

	public BillForm getMainBillForm() {
		return mainBillForm;
	}

	public void setMainBillForm(BillForm mainBillForm) {
		this.mainBillForm = mainBillForm;
	}

	@Override
	public void handleAppEvent(CardBodyBeforeEditEvent e) {
		String pk_commission_h = getMainBillForm().getBillCardPanel().getHeadItem("pk_commission_h").getValue();
		if ("textvalue".equals(e.getKey())) {
			//ֵ����
			String valuetype = (String)e.getBillCardPanel().getBodyValueAt(e.getRow(), "valuetype");
			valuetype = (valuetype==null?"":valuetype);
			Integer valueways = e.getBillCardPanel().getBodyValueAt(e.getRow(), "valueways") == null ? null : (Integer) e
					.getBillCardPanel().getBodyValueAt(e.getRow(), "valueways");
			//���ֵ������'�û���������',����ȡֵ��ʽΪ�ı�,��ô�ı�ֵ��������
			if ((valueways == null||2 == valueways)&&(!valuetype.equals("�б������û�����"))) {
				e.setReturnValue(false);
				return;
			}
			
			//�����ı��� 
			Object value = e.getBillCardPanel().getBodyValueAt(e.getRow(), "textvalue");
			if(value==null && valuetype.equals("�б������û�����")){
				value = e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_refvalue");
			}
			String initStr = value==null?null:String.valueOf(value);
			if(e.getHitCount()==2 && getMainBillForm().getModel().getUiState()!=null 
					&& (UIState.NOT_EDIT!=getMainBillForm().getModel().getUiState())){
				TextDialog textDialog = new TextDialog(e.getContext(),"�ı�ֵ",initStr);
				int state = textDialog.showModal();
				if(state == 2){
					e.setReturnValue(false);
					return;
				}
				String bigStr = textDialog.getTextPane().getText();
				initStr = (initStr==null?"":initStr);
				if(valuetype.equals("�б������û�����")){
					if(!initStr.equals(bigStr)){
						//��ղ�����Ϣ
						e.getBillCardPanel().setBodyValueAt(null, e.getRow(), "pk_refvalue", "pk_task_s");
						e.getBillCardPanel().setBodyValueAt(null, e.getRow(), "englishdescription", "pk_task_s");
						e.getBillCardPanel().setBodyValueAt("���޸�", e.getRow(), "conditionstatus");
						e.getBillCardPanel().setBodyValueAt(bigStr, e.getRow(), "formatted_entry");
						e.getBillCardPanel().setBodyValueAt(bigStr, e.getRow(), "textvalue");
					}
				}else{
					if (bigStr != null && !initStr.equals(bigStr) ) {
						e.getBillCardPanel().setBodyValueAt("���޸�", e.getRow(), "conditionstatus");
					} else {
						e.getBillCardPanel().setBodyValueAt("δ¼��", e.getRow(), "conditionstatus");
					}
					e.getBillCardPanel().setBodyValueAt(bigStr, e.getRow(), "formatted_entry");
					e.getBillCardPanel().setBodyValueAt(bigStr, e.getRow(), "textvalue");
				}
			}
		} else if ("pk_refvalue".equals(e.getKey())) {
			Integer valueways = e.getBillCardPanel().getBodyValueAt(e.getRow(), "valueways") == null ? null : (Integer) e
					.getBillCardPanel().getBodyValueAt(e.getRow(), "valueways");
			if (null == valueways) {
				MessageDialog.showErrorDlg(e.getContext().getEntranceUI(), "����", "ȡֵ��ʽ����Ϊ�ա�");
			} else if (valueways == 2) {
				e.setReturnValue(true);
				String pk_list_table = e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_list_table") == null ? null : (String) e
						.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_list_table");
				try {
					RefValuePanel refValuePanel = new RefValuePanel(pk_commission_h, pk_list_table);
					if (refValuePanel.showModal() == 1) {
						String CNvalue = refValuePanel.getSelectedCNstr();
						String ENvalue = refValuePanel.getSelectedENstr();
						e.getBillCardPanel().setBodyValueAt(CNvalue, e.getRow(), "pk_refvalue", "pk_task_s");
						e.getBillCardPanel().setBodyValueAt(ENvalue, e.getRow(), "englishdescription", "pk_task_s");
						e.getBillCardPanel().setBodyValueAt("���޸�", e.getRow(), "conditionstatus", "pk_task_s");
						e.getBillCardPanel().setBodyValueAt(CNvalue, e.getRow(), "formatted_entry", "pk_task_s");
						//������û�������,��Ҫ����ı�ֵ
						String valuetype = (String)e.getBillCardPanel().getBodyValueAt(e.getRow(), "valuetype");
						if(valuetype!=null && valuetype.equals("�б������û�����")){
							e.getBillCardPanel().setBodyValueAt(null, e.getRow(), "textvalue", "pk_task_s");
						}
					}
				} catch (DAOException e1) {
					e1.printStackTrace();
				}
			}
			e.setReturnValue(false);
			return;
		} else if ("samplegroup".equals(e.getKey())) {
			// ������
			String sql = "select pk_samplegroup pk_samplegroup from QC_COMMISSION_B where PK_COMMISSION_H = '" + pk_commission_h
					+ "' and dr = 0";
			IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
			Set<String> groupPkSet = new HashSet<>();
			try {
				@SuppressWarnings("unchecked")
				List<Map<String, String>> list = (List<Map<String, String>>) bs.executeQuery(sql, new MapListProcessor());
				if (list != null && list.size() > 0) {
					for (Map<String, String> map : list) {
						groupPkSet.add(map.get("pk_samplegroup"));
					}
				}
			} catch (BusinessException e1) {
				ExceptionUtils.wrappException(e1);
			}
			String groupWhere = " pk_sample_group in (";
			if (groupPkSet != null && groupPkSet.size() > 0) {
				boolean isFist = true;
				for (String pk_group : groupPkSet) {
					if (isFist) {
						groupWhere += "'" + pk_group + "'";
						isFist = false;
					} else {
						groupWhere += ",'" + pk_group + "'";
					}

				}
				groupWhere += ") ";

				BillItem item = (BillItem) e.getSource();
				((SampleGroupRefModel) ((UIRefPane) item.getComponent()).getRefModel()).setGroupWhere(groupWhere);
			}
		} else if ("component".equals(e.getKey())) {
			// �ֹ��������ֵ,��Ҫ���й���
			String anaName = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "analysisname");
			BillItem item = (BillItem) e.getSource();
			((TaskAnalyseComponentRefModel) ((UIRefPane) item.getComponent()).getRefModel()).setAnalysisName(anaName);

		}
		e.setReturnValue(true);
	}
}
