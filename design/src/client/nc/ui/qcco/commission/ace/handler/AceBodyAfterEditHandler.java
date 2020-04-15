package nc.ui.qcco.commission.ace.handler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.bs.pubapp.utils.UserDefineRefUtils;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.beans.constenum.IConstEnum;
import nc.ui.pub.bill.BillCellEditor;
import nc.ui.pub.bill.BillItem;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.qcco.commission.refmodel.ProductSerialRefModel;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.qcco.commission.CommissionBVO;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;

public class AceBodyAfterEditHandler implements IAppEventHandler<CardBodyAfterEditEvent> {
	private ShowUpableBillForm grandCard;

	@Override
	public void handleAppEvent(CardBodyAfterEditEvent e) {
		if ("productserial".equals(e.getKey())) {
			if (e.getOldValue() != null && e.getValue() == null) {
				fixEmpty(e);
				return;
			}
			// 产品系列
			e.getBillCardPanel().setBodyValueAt(e.getValue(), e.getRow(), "pk_productserial");
			clearBodyItems(e, new String[] { "pk_productserial", "productserial", "samplegroup", "pk_samplegroup","uniqueid" });
			// 刷新温度字段
			refreshProductstage(e);
			// 刷新实验前参数
			try {
				refreshAnalysisBeforeAndGrandValus(e);
			} catch (BusinessException e1) {
				Log.debug(e1.getMessage());
			}
		} else if ("enterprisestandard".equals(e.getKey())) {
			if (e.getOldValue() != null && e.getValue() == null) {
				fixEmpty(e);
				return;
			}
			// 企业标准
			e.getBillCardPanel().setBodyValueAt(e.getValue(), e.getRow(), "pk_enterprisestandard");
			clearBodyItems(e, new String[] { "pk_productserial", "productserial", "pk_enterprisestandard",
					"enterprisestandard", "analysisref", "samplegroup", "pk_samplegroup","uniqueid" });
			// 刷新温度字段
			refreshProductstage(e);
		} else if ("productspec".equals(e.getKey())) {
			if (e.getOldValue() != null && e.getValue() == null) {
				fixEmpty(e);
				return;
			}
			// 规格号
			e.getBillCardPanel().setBodyValueAt(e.getValue(), e.getRow(), "pk_productspec");
			// clearBodyItems(e, new String[] { "pk_productserial",
			// "productserial", "pk_enterprisestandard",
			// "enterprisestandard", "pk_productspec", "productspec", "typeno",
			// "samplegroup", "pk_samplegroup" });
			// 刷新温度字段
			refreshProductstage(e);
			// 刷新实验前参数
			try {
				refreshAnalysisBeforeAndGrandValus(e);
			} catch (BusinessException e1) {
				Log.debug(e1.getMessage());
			}
		} else if ("structuretype".equals(e.getKey())) {
			if (e.getOldValue() != null && e.getValue() == null) {
				fixEmpty(e);
				return;
			}
			// 结构类型
			e.getBillCardPanel().setBodyValueAt(e.getValue(), e.getRow(), "pk_structuretype");
			// clearBodyItems(e, new String[] { "pk_productserial",
			// "productserial", "pk_enterprisestandard",
			// "enterprisestandard", "pk_productspec", "productspec",
			// "pk_structuretype", "structuretype",
			// "typeno", "analysisref", "samplegroup", "pk_samplegroup" });
			// 刷新温度字段
			refreshProductstage(e);
		} else if ("ref_contacttype".equals(e.getKey())) {
			if (e.getOldValue() != null && e.getValue() == null) {
				fixEmpty(e);
				return;
			}
			e.getBillCardPanel().setBodyValueAt(e.getValue(), e.getRow(), "contacttype");
		} else if ("contactbrand".equals(e.getKey())) {
			if (e.getOldValue() != null && e.getValue() == null) {
				fixEmpty(e);
				return;
			}
			// 触点牌号
			BillCellEditor bitem = (BillCellEditor) e.getSource();
			UIRefPane refPane = (UIRefPane) bitem.getComponent();
			e.getBillCardPanel().setBodyValueAt(refPane.getRefPK(), e.getRow(), "pk_contactbrand");
		} else if ("samplegroup".equals(e.getKey())) {
			if (e.getOldValue() != null && e.getValue() == null) {
				fixEmpty(e);
				return;
			}
			// mod tank 当样品组别和实验参数都不为空的时候进行重新生成孙表的工作,否则不进行如何工作
			BillCellEditor bitem = (BillCellEditor) e.getSource();
			UIRefPane refPane = (UIRefPane) bitem.getComponent();
			e.getBillCardPanel().setBodyValueAt(refPane.getRefPK(), e.getRow(), "pk_samplegroup");

			// 不可重复组别
			for (int i = 0; i < e.getBillCardPanel().getRowCount(); i++) {
				if (e.getRow() != i) {
					if (e.getValue().equals(e.getBillCardPanel().getBodyValueAt(i, "pk_samplegroup"))) {
						MessageDialog.showErrorDlg(e.getContext().getEntranceUI(), "错误", "样品组别 ["
								+ e.getBillCardPanel().getBodyValueAt(i, "samplegroup") + "] 发生重复。");
						e.getBillCardPanel().setBodyValueAt(e.getOldValue(), e.getRow(), "samplegroup");
						e.getBillCardPanel().setBodyValueAt(e.getOldValue(), e.getRow(), "pk_samplegroup");
						CommissionBVO bodyVO = new CommissionBVO();
						bodyVO.setAttributeValue("samplegroup", e.getOldValue());
						UserDefineRefUtils.refreshItemRefValue(bodyVO, e.getBillCardPanel().getBodyPanel().getTable(),
								e.getRow(), e.getBillCardPanel().getBodyItem("samplegroup"), true);
						return;
					}
				}
			}
		}

		if ("enterprisestandard".equals(e.getKey()) || "productspec".equals(e.getKey())
				|| "samplegroup".equals(e.getKey()) || "ref_contacttype".equals(e.getKey())) {
			if (e.getOldValue() != null && e.getValue() == null) {
				fixEmpty(e);
				return;
			}
			// 清空孙表样品
			clearGrandLines(e);
			if (e.getBillCardPanel().getBodyValueAt(e.getRow(), "samplegroup") != null
					&& e.getBillCardPanel().getBodyValueAt(e.getRow(), "analysisref") != null) {
				// 如果实验前参数不为空,那么进行生成孙表工作
				try {
					generateGrandLines(e);
				} catch (BusinessException ex) {
					Logger.error(ex.getMessage());
				}
			}
			// end
		}
	}

	/**
	 * 根据表体变动后带出实验前参数
	 * 
	 * @param e
	 * @throws BusinessException
	 */
	private void refreshAnalysisBeforeAndGrandValus(CardBodyAfterEditEvent e) throws BusinessException {

		UIRefPane refPane = (UIRefPane) e.getBillCardPanel().getBodyItem("productserial").getComponent();
		ProductSerialRefModel refMode = (ProductSerialRefModel) refPane.getRefModel();

		String description = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "productserial");
		String productspec = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "productspec");
		String c_prod_type_c1 = refMode.getSecondClassCode();

		String sql = " SELECT DISTINCT pgs.ANALYSIS analysis_name "
				+ " FROM product p , product_grade pg, prod_grade_STAGE pgs "
				+ " WHERE p.c_prod_type_c1 LIKE '"
				+ c_prod_type_c1
				+ "' " // 产品大类
				+ " AND p.description = '"
				+ description
				+ "' " // 产品系列
				+ " AND pg.sampling_point = '"
				+ productspec
				+ "' " // 规格号??!!
				+ " AND p.active = 'T' " + " AND removed = 'F' " + " and pgs.order_number = 1 "
				+ " AND p.name = pg.product  " + " AND pgs.product = p.name "
				+ " AND pgs.sampling_point = pg.sampling_point " + " AND pgs.GRADE = pg.grade ";
		IUAPQueryBS query = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		String analysis_name = (String) query.executeQuery(sql, new ColumnProcessor());
		if (analysis_name != null) {
			e.getBillCardPanel().setBodyValueAt(analysis_name, e.getRow(), "analysisref");
		}
		// 清空孙表样品
		clearGrandLines(e);
		generateGrandLines(e);
	}

	/**
	 * 根据表体变动后带出温度,如果不止一个,用串的方式显示
	 * 
	 * @param e
	 * @throws BusinessException
	 */
	private void refreshProductstage(CardBodyAfterEditEvent e) {
		String productserial = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_productserial");
		String basentype = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_enterprisestandard");
		String productspec = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_productspec");
		String productstruct = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_structuretype");

		String sql = "SELECT TRIM(BP.NC_BASPRODTEMP_CODE) NC_BASPRODTEMP_CODE, TRIM(BP.NC_BASPRODTEMP_NAME) NC_BASPRODTEMP_NAME, BP.PK_BASPROD_TEMP "
				+ " FROM NC_SAMPLE_INFO SI INNER JOIN NC_BASPROD_TEMP BP ON BP.PK_BASPROD_TEMP = SI.PK_BASPROD_TEMP "
				+ " WHERE SI.PK_BASPROD_TYPE = '"
				+ productserial
				+ "' AND SI.PK_BASEN_TYPE = '"
				+ basentype
				+ "' AND SI.PK_BASPROD_POINT = '"
				+ productspec
				+ "' AND SI.PK_BASPROD_STRUCT = '"
				+ productstruct
				+ "' "
				+ " GROUP BY BP.NC_BASPRODTEMP_CODE, BP.NC_BASPRODTEMP_NAME, BP.PK_BASPROD_TEMP "
				+ " ORDER BY CAST(BP.NC_BASPRODTEMP_CODE AS NUMBER)";
		IUAPQueryBS query = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		List<Map<String, Object>> refList = null;
		try {
			refList = (List<Map<String, Object>>) query.executeQuery(sql, new MapListProcessor());
		} catch (BusinessException e1) {
			Log.debug(e1.getMessage());
		}
		StringBuilder sb = new StringBuilder();
		if (refList != null && refList.size() > 0) {
			for (Map<String, Object> refRow : refList) {
				if (refRow != null && refRow.get("nc_basprodtemp_name") != null) {
					sb.append(refRow.get("nc_basprodtemp_name")).append(",");
				}
			}
		}
		if (sb.length() >= 1) {
			sb.deleteCharAt(sb.length() - 1);
		}

		e.getBillCardPanel().setBodyValueAt(sb.toString(), e.getRow(), "productstage");
	}

	private void clearBodyItems(CardBodyAfterEditEvent e, String[] exceptions) {
		for (BillItem item : e.getBillCardPanel().getBodyItems()) {
			if (!Arrays.asList(exceptions).contains(item.getKey()) && !"rowno".equals(item.getKey())) {
				e.getBillCardPanel().setBodyValueAt(null, e.getRow(), item.getKey());
				if (item.getKey().equals("rowno") || item.getKey().equals("uniqueid")) {
					continue;
				}
				if (e.getBillCardPanel().getBodyValueAt(e.getRow(), "samplegroup") == null
						|| e.getBillCardPanel().getBodyValueAt(e.getRow(), "analysisref") == null) {
					// 当清空样品组别或实验前参数时，清空孙表
					clearGrandLines(e);
				}
			}
		}
	}

	private void clearGrandLines(CardBodyAfterEditEvent e) {
		int rowCount = this.getGrandCard().getBillCardPanel().getRowCount();
		Set<Integer> lineSet = new HashSet<>();
		if (rowCount > 0) {

			for (int i = 0; i < rowCount; i++) {
				// 只清空自动生成的行
				UFBoolean ifAuto = (UFBoolean) getGrandCard().getBillCardPanel().getBodyValueAt(i, "isautogeneration");
				if (ifAuto != null && ifAuto.booleanValue()) {
					lineSet.add(i);
				}
			}
			Integer[] lineArray = lineSet.toArray(new Integer[0]);
			int[] resultArray = new int[lineArray.length];
			for (int i = 0; i < lineArray.length; i++) {
				resultArray[i] = lineArray[i];
			}
			this.getGrandCard().getBillCardPanel().getBodyPanel().delLine(resultArray);

		}
	}

	@SuppressWarnings("unchecked")
	private void generateGrandLines(CardBodyAfterEditEvent e) throws BusinessException {
		IUAPQueryBS query = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		List<Map<String, Object>> refList = (List<Map<String, Object>>) query
				.executeQuery(
						"select * from (SELECT distinct trim(p.nc_analysis_name) nc_analysis_name, p.pk_result_type, trim(r.nc_result_code) nc_result_code, "
								+ "trim(r.nc_result_namecn) nc_result_namecn, p.pk_test_init, trim(p.test_init_code) test_init_code, trim(p.test_init_name) test_init_name, u.pk_units_type, "
								+ "trim(u.nc_units_code) nc_units_code, trim(u.nc_units_name) nc_units_name, p.nc_max_value, "
								+ "p.nc_min_value, p.nc_stage,cast(p.def1  as int) as def1 from NC_TEST_INIT p "
								+ " INNER JOIN NC_UNITS_TYPE u ON u.pk_units_type = p.pk_units_type "
								+ " INNER JOIN NC_RESULT_TYPE r ON r.pk_result_type = p.pk_result_type "
								+ "where p.nc_enstard = '"
								+ e.getBillCardPanel().getBodyValueAt(e.getRow(), "enterprisestandard")
								+ "'  and p.nc_sample_point = '"
								+ e.getBillCardPanel().getBodyValueAt(e.getRow(), "productspec")
								+ "'  and ' ' || p.Nc_contact_type || ',' like '% "
								+ e.getBillCardPanel().getBodyValueAt(e.getRow(), "ref_contacttype")
								+ ",%'  and ' ' || p.NC_COIL_TYPE || ',' like '% "
								+ e.getBillCardPanel().getBodyValueAt(e.getRow(), "structuretype")
								+ ",%'  and p.nc_coil_current = '"
								+ e.getBillCardPanel().getBodyValueAt(e.getRow(), "structuretype")
								+ "' )"
								+ "  order by nc_stage, def1", new MapListProcessor());
		if (refList != null && refList.size() > 0) {
			for (Map<String, Object> refRow : refList) {
				this.getGrandCard().getBillCardPanel().getBodyPanel().addLine();
				int row = this.getGrandCard().getBillCardPanel().getRowCount() - 1;
				this.getGrandCard()
						.getBillCardPanel()
						.setBodyValueAt(e.getBillCardPanel().getBodyValueAt(e.getRow(), "samplegroup"), row,
								"samplegroup");
				this.getGrandCard()
						.getBillCardPanel()
						.setBodyValueAt(e.getBillCardPanel().getBodyValueAt(e.getRow(), "pk_samplegroup"), row,
								"pk_samplegroup");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(UFBoolean.TRUE, row, "judgeflag");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(UFBoolean.TRUE, row, "testflag");
				// 系统生成标识 //客户那估计模板乱了,上两个
				//this.getGrandCard().getBillCardPanel().setBodyValueAt(UFBoolean.TRUE, row, "isAutoGeneration");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(UFBoolean.TRUE, row, "isautogeneration");
				// 企业标准
				String pk_enterprisestandard = (String) e.getBillCardPanel().getBodyValueAt(e.getRow(),
						"pk_enterprisestandard");
				this.getGrandCard().getBillCardPanel()
						.setBodyValueAt(pk_enterprisestandard, row, "pk_enterprisestandard");
				String resultCode = "";
				String resultName = "";
				String refCode = "";
				String refName = "";
				for (Entry<String, Object> refValue : refRow.entrySet()) {
					if (refValue.getKey().equals("nc_analysis_name")) {
						this.getGrandCard().getBillCardPanel().setBodyValueAt(refValue.getValue(), row, "analysisname");
					} else if (refValue.getKey().equals("pk_test_init")) {
						this.getGrandCard().getBillCardPanel().setBodyValueAt(refValue.getValue(), row, "pk_component");
					} else if (refValue.getKey().equals("test_init_code")) {
						refCode = (String) refValue.getValue();
					} else if (refValue.getKey().equals("test_init_name")) {
						refName = (String) refValue.getValue();
					} else if (refValue.getKey().equals("pk_result_type")) {
						this.getGrandCard().getBillCardPanel().setBodyValueAt(refValue.getValue(), row, "pk_valuetype");
					} else if (refValue.getKey().equals("nc_result_code")) {
						resultCode = (String) refValue.getValue();
					} else if (refValue.getKey().equals("nc_result_namecn")) {
						resultName = (String) refValue.getValue();
					} else if (refValue.getKey().equals("nc_max_value")) {
						this.getGrandCard().getBillCardPanel().setBodyValueAt(refValue.getValue(), row, "stdmaxvalue");
					} else if (refValue.getKey().equals("nc_min_value")) {
						this.getGrandCard().getBillCardPanel().setBodyValueAt(refValue.getValue(), row, "stdminvalue");
					} else if (refValue.getKey().equals("nc_stage")) {
						this.getGrandCard().getBillCardPanel().setBodyValueAt(refValue.getValue(), row, "productstage");
					} else if (refValue.getKey().equals("nc_units_name")) {
						this.getGrandCard().getBillCardPanel().setBodyValueAt(refValue.getValue(), row, "unitname");
					}
				}
				if (!StringUtils.isEmpty(resultCode) && !StringUtils.isEmpty(resultName)) {
					IConstEnum aValue = new DefaultConstEnum(resultName, resultName);
					this.getGrandCard().getBillCardPanel().setBodyValueAt(aValue.getValue(), row, "valuetype");
				}

				if (!StringUtils.isEmpty(refCode) && !StringUtils.isEmpty(refName)) {
					IConstEnum aValue = new DefaultConstEnum(refName, refName);
					this.getGrandCard().getBillCardPanel().setBodyValueAt(aValue.getValue(), row, "component");
				}
				// e.getBillCardPanel().getBodyValueAt(row, "qc_commission_b");
				//如果最小值大于最大值，那么制空最大值 2020年4月2日23:18:10
				Object maxObj = this.getGrandCard().getBillCardPanel().getBodyValueAt( row, "stdmaxvalue");
				Object mixObj = this.getGrandCard().getBillCardPanel().getBodyValueAt( row, "stdminvalue");

				try{
					double maxValue = new UFDouble(String.valueOf(maxObj)).toDouble();
					double minValue = new UFDouble(String.valueOf(mixObj)).toDouble();
					if(maxValue < minValue){
						this.getGrandCard().getBillCardPanel()
						.setBodyValueAt(null, row, "stdmaxvalue");
					}
				}catch(Exception exc){
					Logger.error(exc.getMessage());
				}
				//mod end 
			}
		}
	}

	/**
	 * fix 修改后为空的问题
	 * 
	 * @return
	 */
	private void fixEmpty(CardBodyAfterEditEvent e) {
		CommissionBVO bodyVO = new CommissionBVO();
		bodyVO.setAttributeValue(e.getKey(), e.getOldValue());
		UserDefineRefUtils.refreshItemRefValue(bodyVO, e.getBillCardPanel().getBodyPanel().getTable(), e.getRow(), e
				.getBillCardPanel().getBodyItem(e.getKey()), true);

	}

	public ShowUpableBillForm getGrandCard() {
		return grandCard;
	}

	public void setGrandCard(ShowUpableBillForm grandCard) {
		this.grandCard = grandCard;
	}

}
