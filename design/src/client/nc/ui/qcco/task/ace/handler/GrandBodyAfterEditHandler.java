package nc.ui.qcco.task.ace.handler;

import nc.bs.framework.common.NCLocator;
import nc.bs.pubapp.utils.UserDefineRefUtils;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCellEditor;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.qcco.task.refmodel.TaskAnalyseComponentRefModel;
import nc.vo.pub.BusinessException;
import nc.vo.qcco.task.TaskRVO;

public class GrandBodyAfterEditHandler implements IAppEventHandler<CardBodyAfterEditEvent> {
	private BillForm mainBillForm;//

	public BillForm getMainBillForm() {
		return mainBillForm;
	}

	public void setMainBillForm(BillForm mainBillForm) {
		this.mainBillForm = mainBillForm;
	}
	@Override
	public void handleAppEvent(CardBodyAfterEditEvent e) {
		String pk_commission_h = getMainBillForm().getBillCardPanel().getHeadItem("pk_commission_h").getValue();
		if ("textvalue".equals(e.getKey())) {
			//值类型
			String valuetype = (String)e.getBillCardPanel().getBodyValueAt(e.getRow(), "valuetype");
			//原始值
			String initStr = e.getOldValue()==null?"":e.getOldValue().toString();
			//新值
			String bigStr = e.getValue() == null?"":e.getValue().toString();
			if(valuetype.equals("列表允许用户输入")){
				if(!initStr.equals(bigStr)){
					//清空参照信息
					e.getBillCardPanel().setBodyValueAt(null, e.getRow(), "pk_refvalue", "pk_task_s");
					e.getBillCardPanel().setBodyValueAt(null, e.getRow(), "englishdescription", "pk_task_s");
					e.getBillCardPanel().setBodyValueAt("已修改", e.getRow(), "conditionstatus");
					e.getBillCardPanel().setBodyValueAt(bigStr, e.getRow(), "formatted_entry");
					e.getBillCardPanel().setBodyValueAt(bigStr, e.getRow(), "textvalue");
				}
			}else{
				if (bigStr != null && !initStr.equals(bigStr) ) {
					e.getBillCardPanel().setBodyValueAt("已修改", e.getRow(), "conditionstatus");
				} else {
					e.getBillCardPanel().setBodyValueAt("未录入", e.getRow(), "conditionstatus");
				}
				e.getBillCardPanel().setBodyValueAt(bigStr, e.getRow(), "formatted_entry");
				e.getBillCardPanel().setBodyValueAt(bigStr, e.getRow(), "textvalue");
			}

		} else if ("unit".equals(e.getKey())) {
			//转化成小时
			/*String valuetype = (String)e.getBillCardPanel().getBodyValueAt(e.getRow(), "valuetype");
			if(valuetype!=null && valuetype.equals("计算型")){
				String unit = (String)e.getValue();
				String value = String.valueOf(e.getBillCardPanel().getBodyValueAt(e.getRow(), "textvalue"));
				CommonUtils utils = new CommonUtils();
				int valueInt = utils.changeTime2H(value,unit);
				e.getBillCardPanel().setBodyValueAt(valueInt, e.getRow(), "textvalue");
				e.getBillCardPanel().setBodyValueAt(valueInt, e.getRow(), "formatted_entry");
				e.getBillCardPanel().setBodyValueAt("h", e.getRow(), "unit");
			}*/
			
		}else if ("valuetype".equals(e.getKey())) {
		/*	//转化成小时
			String valuetype = (String)e.getValue();
			if(valuetype!=null && valuetype.equals("计算型")){
				String unit = (String)e.getBillCardPanel().getBodyValueAt(e.getRow(), "unit");
				String value = String.valueOf(e.getBillCardPanel().getBodyValueAt(e.getRow(), "textvalue"));
				CommonUtils utils = new CommonUtils();
				int valueInt = utils.changeTime2H(value,unit);
				e.getBillCardPanel().setBodyValueAt(valueInt, e.getRow(), "textvalue");
				e.getBillCardPanel().setBodyValueAt(valueInt, e.getRow(), "formatted_entry");
				e.getBillCardPanel().setBodyValueAt("h", e.getRow(), "unit");
			}*/
			if (e.getOldValue() != null && e.getValue() == null) {
				fixEmpty(e);
				return;
			}
			if(e.getValue()!=null && !"".equals(e.getValue())){
				e.getBillCardPanel().setBodyValueAt(e.getValue(), e.getRow(), "pk_valuetype");
			}
		} else if ("refvalue".equals(e.getKey())) {
			if (e.getValue() != null) {
				e.getBillCardPanel().setBodyValueAt("已修改", e.getRow(), "conditionstatus");
			} else {
				e.getBillCardPanel().setBodyValueAt("未录入", e.getRow(), "conditionstatus");
			}
			e.getBillCardPanel().setBodyValueAt(e.getValue(), e.getRow(), "formatted_entry");
		} else if("instrument".equals(e.getKey())){
			if(e.getValue()!=null && !"".equals(e.getValue())){
				e.getBillCardPanel().setBodyValueAt(e.getValue(), e.getRow(), "pk_instrument");
			}
		}
		else if("samplegroup".equals(e.getKey())){
			if(e.getValue()!=null && !"".equals(e.getValue())){
				e.getBillCardPanel().setBodyValueAt(e.getValue(), e.getRow(), "pk_samplegroup");
				
				
				//手工输入需要设置实验参数
				String group = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "samplegroup");
				int bodyRow = getMainBillForm().getBillCardPanel().getBillTable().getSelectedRow();
				if (bodyRow >= 0) {
					String findAnaliyStrSql = "SELECT DISTINCT TRIM(NC_ANALYSIS_NAME)   analysisname " + " FROM NC_TEST_AFTER p "
							+ " INNER JOIN NC_RESULT_TYPE t ON t.PK_RESULT_TYPE = p.PK_RESULT_TYPE "
							+ " INNER JOIN NC_UNITS_TYPE u ON u.PK_UNITS_TYPE = p.PK_UNITS_TYPE "
							+ " INNER JOIN NC_SAMPLE_GROUP g ON g.NC_SAMPLE_NAME IN ( '" + group + "' ) "
							+ " INNER JOIN qc_commission_b c ON c.PK_COMMISSION_H = '" + pk_commission_h + "' "
							+ " AND c.PK_SAMPLEGROUP = g.PK_SAMPLE_GROUP "
							+ " WHERE p.nc_enstard = ( SELECT NC_BBASEN_NAME FROM NC_BASEN_TYPE "
							+ " WHERE PK_BASEN_TYPE = c.PK_ENTERPRISESTANDARD) AND p.nc_sample_point = "
							+ " ( SELECT TRIM(NC_BASPRODPOINT_NAME) FROM "
							+ " NC_BASPROD_POINT WHERE PK_BASPROD_POINT = c.PK_PRODUCTSPEC) "
							+ " AND ' ' || p.Nc_contact_type || ',' LIKE '% '|| c.CONTACTTYPE ||',%' "
							+ " AND ' ' || p.NC_COIL_TYPE || ',' LIKE '% '|| " + " ( SELECT NC_BASPRODSTRUCT_NAME  FROM NC_BASPROD_STRUCT "
							+ " WHERE PK_BASPROD_STRUCT = c.pk_structuretype ) ||',%' " + " AND p.nc_coil_current = ( "
							+ " SELECT  NC_BASPRODSTRUCT_NAME FROM "
							+ " NC_BASPROD_STRUCT WHERE PK_BASPROD_STRUCT = c.pk_structuretype) and rownum = 1 ";
					String analiy = null;
					try {
						IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
						analiy = (String) bs.executeQuery(findAnaliyStrSql, new ColumnProcessor());
					} catch (BusinessException e1) {
						e1.printStackTrace();
					}
					String anaName = null;
					if (analiy != null && !"".equals(analiy)) {
						anaName = String.valueOf(analiy);
					}
					e.getBillCardPanel().setBodyValueAt(anaName, e.getRow(), "analysisname", "pk_task_r");

				}
				
			}
		}
		else if("component".equals(e.getKey())){
			if(e.getValue()!=null && !"".equals(e.getValue())){
				e.getBillCardPanel().setBodyValueAt(e.getValue(), e.getRow(), "pk_component");
			}
			// 手工输入参照值,需要进行过滤
			BillCellEditor bitem = (BillCellEditor) e.getSource();
			if (bitem.getComponent() instanceof UIRefPane) {
				UIRefPane refPane = (UIRefPane) bitem.getComponent();
				((TaskAnalyseComponentRefModel)refPane.getRefModel()).setAnalysisName(null);
			}
			
			
		}

	}
	/**
	 * fix 修改后为空的问题
	 * 
	 * @return
	 */
	private void fixEmpty(CardBodyAfterEditEvent e) {
		TaskRVO grandVO = new TaskRVO();
		grandVO.setAttributeValue(e.getKey(), e.getOldValue());
		UserDefineRefUtils.refreshItemRefValue(grandVO, e.getBillCardPanel().getBodyPanel().getTable(), e.getRow(), e
				.getBillCardPanel().getBodyItem(e.getKey()), true);

	}

}
