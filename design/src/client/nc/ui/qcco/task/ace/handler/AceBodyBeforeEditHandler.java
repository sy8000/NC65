package nc.ui.qcco.task.ace.handler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.hr.utils.InSQLCreator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.ValueChangedListener;
import nc.ui.pub.beans.constenum.DefaultConstEnum;
import nc.ui.pub.beans.constenum.IConstEnum;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pubapp.uif2app.event.IAppEventHandler;
import nc.ui.pubapp.uif2app.event.card.CardBodyBeforeEditEvent;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.qcco.task.utils.StringOrderUtils;
import nc.ui.qcco.task.view.SampleAllocationPanel;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.qcco.commission.CommissionRVO;

import org.apache.commons.lang.StringUtils;

public class AceBodyBeforeEditHandler implements IAppEventHandler<CardBodyBeforeEditEvent>, ValueChangedListener,
		ActionListener {
	private SampleAllocationPanel samplepanel;
	private UIPanel ivjUIPanel2 = null;
	private BillForm mainBillForm;//
	private ShowUpableBillForm grandCard;// mainBillForm

	public BillForm getMainBillForm() {
		return mainBillForm;
	}

	public void setMainBillForm(BillForm mainBillForm) {
		this.mainBillForm = mainBillForm;
	}

	public ShowUpableBillForm getGrandCard() {
		return grandCard;
	}

	public void setGrandCard(ShowUpableBillForm grandCard) {
		this.grandCard = grandCard;
	}

	@Override
	public void handleAppEvent(CardBodyBeforeEditEvent e) {
		if ("sampleallocation".equals(e.getKey())) {
			try {
				if (!this.getGrandCard().getBillCardPanel().getBodyTabbedPane().getSelectedTableCode()
						.equals("pk_task_r")) {
					// MessageDialog.showErrorDlg(e.getContext().getEntranceUI(),
					// "提示", "请将孙表切换到试验后参数页签");
					this.getGrandCard().getBillCardPanel().getBodyTabbedPane().setSelectedIndex(1);
					// getGrandCard().getBillCardPanel().getTabbedPane(1).setSelectedIndex(1);
					// return;
				}
				String pk_commission_h = getMainBillForm().getBillCardPanel().getHeadItem("pk_commission_h").getValue();
				if (null == pk_commission_h) {
					MessageDialog.showErrorDlg(e.getContext().getEntranceUI(), "错误", "任务单不能为空");
					return;
				}
				// getMainBillForm().getBillCardPanel().getBodyItem("sampleallocation").getValue();
				SampleAllocationPanel samplepanel = new SampleAllocationPanel(pk_commission_h, getMainBillForm()
						.getBillCardPanel().getBodyItem("sampleallocation").getValue());
				samplepanel.setTitle("样品分配");
				if (samplepanel.showModal() == 1) {
					String strvalue = samplepanel.getSelectedstr();
					Integer testnum = samplepanel.getTestnum();
					//获取原始选择的列
					List<String> sourceList = samplepanel.getSelectedList();
					String sourceStr = getSourceString(sourceList);
					BillCardPanel card = this.getMainBillForm().getBillCardPanel();
					// 校验样品分配是否重复
					List<String> strlist = new ArrayList<String>();
					card.setBodyValueAt(null, e.getRow(), "sampleallocation");
					card.setBodyValueAt(null, e.getRow(), "samplequantity");
					for (int i = 0; i <= e.getBillCardPanel().getRowCount() - 1; i++) {
						strlist.add((String) this.getMainBillForm().getBillCardPanel()
								.getBodyValueAt(i, "sampleallocation"));
					}
					if (strlist != null && strlist.size() > 0 && strvalue != null) {

						List<String> commList = validate(pk_commission_h, strlist, strvalue);
						/*
						 * if (commList.size() > 0) {
						 * MessageDialog.showErrorDlg(e.getContext()
						 * .getEntranceUI(), "错误", "样品分配不能重复。"); return; }
						 */
					}

					card.setBodyValueAt(strvalue, e.getRow(), "sampleallocation");
					// card.getBodyValueAt(e.getRow()-1, "sampleallocation");
					card.setBodyValueAt(testnum, e.getRow(), "samplequantity");
					card.setBodyValueAt(sourceStr, e.getRow(), "sampleallocationsource");
					// 给孙表试验后参数赋值
					Set<String> aSet = new HashSet();

					if (samplepanel.getSelectedstr() != null) {
						if (samplepanel.getSelectedstr().contains("A")) {
							aSet.add("A");
						}
						if (samplepanel.getSelectedstr().contains("B")) {
							aSet.add("B");
						}
						if (samplepanel.getSelectedstr().contains("C")) {
							aSet.add("C");
						}
						if (samplepanel.getSelectedstr().contains("D")) {
							aSet.add("D");
						}
						// 路过start..Ares.tank 2019年5月29日00:39:59
						dealLineStr(aSet, samplepanel.getSelectedstr());

						// end
						samplepanel = null;
					}
					// e.setValue();
					List<String> Alist = new ArrayList<>();
					Alist.addAll(aSet);
					// 根据条件查询实验后参数
					generateGrandLines(e, Alist, pk_commission_h);
				}

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if ("testnumber".equals(e.getKey())) {
			return;
		}

	}

	private String getSourceString(List<String> sourceList) {
		StringBuilder sb = new StringBuilder();
		if(sourceList!=null && sourceList.size() > 0){
			for(String s : sourceList){
				sb.append(s).append(",");
			}
			if(sb.length() > 0){
				sb.setLength(sb.length() - 1 );
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * 处理'-'连接的情况 -
	 * 
	 * @param aSet
	 * @param selectedstr
	 * @throws BusinessException
	 */
	private void dealLineStr(Set<String> aSet, String selectedstr) throws BusinessException {
		if (selectedstr != null) {
			try {
				String[] groups = selectedstr.replaceAll("\\d+", "").replaceAll(" ", "").split("-");
				if (groups.length > 1) {
					for (int i = 0; i < groups.length - 1; i++) {
						// 开头字母
						char startChar = groups[i].charAt(groups[i].length() - 1);
						// 结尾字母
						char endChar = groups[i + 1].charAt(0);
						if (endChar > startChar) {
							for (int j = startChar + 1; j < endChar; j++) {
								aSet.add(((char) j) + "");
							}
						}
					}
				}

			} catch (Exception e) {
				throw new BusinessException("输入错误:" + selectedstr);
			}
		}

	}

	private List<CommissionRVO> generateGrandLines(CardBodyBeforeEditEvent e, List<String> alist, String pk_commission_h) {
		List<CommissionRVO> list = new ArrayList<>();
		InSQLCreator insql = new InSQLCreator();
		try {
			String sampleGroupSQL = insql.getInSQL(alist.toArray(new String[0]));
			String strSQL = "";
			strSQL += " SELECT ";
			strSQL += "     TRIM(NC_ANALYSIS_NAME) analysisname, ";
			strSQL += "     PK_TEST_AFTER pk_component, ";
			strSQL += "     p.PK_RESULT_TYPE pk_valuetype, ";
			strSQL += "     TRIM(t.NC_RESULT_CODE) nc_result_code, ";
			strSQL += "     TRIM(t.NC_RESULT_NAMECN) nc_result_namecn, ";
			strSQL += "		TRIM(TEST_AFTER_CODE) test_init_code, ";
			strSQL += "		TRIM(TEST_AFTER_NAME) test_init_name, ";
			strSQL += "     NC_MAX_VALUE stdmaxvalue, ";
			strSQL += "     NC_MIN_VALUE stdminvalue, ";
			strSQL += "     p.PK_UNITS_TYPE, ";
			strSQL += "     TRIM(u.NC_UNITS_NAME) unitname, ";
			strSQL += "     NC_STAGE productstage, ";
			strSQL += "		g.PK_SAMPLE_GROUP pk_samplegroup, ";
			strSQL += "     TRIM(g.NC_SAMPLE_NAME) nc_sample_name";
			strSQL += " FROM ";
			strSQL += "     NC_TEST_AFTER p ";
			strSQL += "	INNER JOIN NC_RESULT_TYPE t ON t.PK_RESULT_TYPE = p.PK_RESULT_TYPE ";
			strSQL += " INNER JOIN NC_UNITS_TYPE u ON u.PK_UNITS_TYPE = p.PK_UNITS_TYPE ";
			strSQL += " INNER JOIN ";
			strSQL += "     NC_SAMPLE_GROUP g ";
			strSQL += " ON ";
			strSQL += "     g.NC_SAMPLE_NAME IN ";
			strSQL += "     ( " + sampleGroupSQL;
			strSQL += "         ) ";
			strSQL += " INNER JOIN ";
			strSQL += "     qc_commission_b c ";
			strSQL += " ON ";
			strSQL += "     c.PK_COMMISSION_H = '" + pk_commission_h + "' ";
			strSQL += " AND c.PK_SAMPLEGROUP = g.PK_SAMPLE_GROUP ";
			strSQL += " WHERE ";
			strSQL += "     p.nc_enstard = ";
			strSQL += "     ( ";
			strSQL += "         SELECT ";
			strSQL += "             NC_BBASEN_NAME ";
			strSQL += "         FROM ";
			strSQL += "             NC_BASEN_TYPE ";
			strSQL += "         WHERE ";
			strSQL += "             PK_BASEN_TYPE = c.PK_ENTERPRISESTANDARD) ";
			strSQL += " AND p.nc_sample_point = ";
			strSQL += "     ( ";
			strSQL += "         SELECT ";
			strSQL += "             TRIM(NC_BASPRODPOINT_NAME) ";
			strSQL += "         FROM ";
			strSQL += "             NC_BASPROD_POINT ";
			strSQL += "         WHERE ";
			strSQL += "             PK_BASPROD_POINT = c.PK_PRODUCTSPEC) ";
			strSQL += " AND ' ' || p.Nc_contact_type || ',' LIKE '% '|| c.CONTACTTYPE ||',%' ";
			strSQL += " AND ' ' || p.NC_COIL_TYPE || ',' LIKE '% '|| ";
			strSQL += "     ( ";
			strSQL += "         SELECT ";
			strSQL += "             NC_BASPRODSTRUCT_NAME ";
			strSQL += "         FROM ";
			strSQL += "             NC_BASPROD_STRUCT ";
			strSQL += "         WHERE ";
			strSQL += "             PK_BASPROD_STRUCT = c.pk_structuretype ) ||',%' ";
			strSQL += " AND p.nc_coil_current = ";
			strSQL += "     ( ";
			strSQL += "         SELECT ";
			strSQL += "             NC_BASPRODSTRUCT_NAME ";
			strSQL += "         FROM ";
			strSQL += "             NC_BASPROD_STRUCT ";
			strSQL += "         WHERE ";
			strSQL += "             PK_BASPROD_STRUCT = c.pk_structuretype) ";
			// strSQL += " AND p.nc_stage = c.PRODUCTSTAGE ";
			strSQL += " ORDER BY nc_sample_name,NC_STAGE, CAST( p.DEF1 AS INTEGER ) ";

			IUAPQueryBS iUAPQueryBS = (IUAPQueryBS) NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
			List<Map<String, Object>> refList = (List<Map<String, Object>>) iUAPQueryBS.executeQuery(strSQL,
					new MapListProcessor());
			int rowu = this.getGrandCard().getBillCardPanel().getRowCount();
			if (rowu >= 0) {
				int[] rows = new int[rowu];
				for (int i = 0; i < rowu; i++) {
					rows[i] = i;
				}
				this.getGrandCard().getBillCardPanel().getBodyPanel("pk_task_r").delLine(rows);
			}
			if (refList != null && refList.size() > 0) {
				
				for (Map<String, Object> refRow : refList) {

					this.getGrandCard().getBillCardPanel().getBodyPanel("pk_task_r").addLine();
					// int row =
					// this.getGrandCard().getBillCardPanel().getRowCount() - 1;
					int row = this.getGrandCard().getBillCardPanel().getRowCount("pk_task_r") - 1;

					String resultCode = "";
					String resultName = "";
					String refCode = "";
					String refName = "";
					for (Entry<String, Object> refValue : refRow.entrySet()) {
						if (refValue.getKey().equals("analysisname")) {
							this.getGrandCard().getBillCardPanel()
									.setBodyValueAt(refValue.getValue(), row, "analysisname", "pk_task_r");
						} else if (refValue.getKey().equals("pk_component")) {
							this.getGrandCard().getBillCardPanel()
									.setBodyValueAt(refValue.getValue(), row, "pk_component", "pk_task_r");
						} else if (refValue.getKey().equals("test_init_name")) {
							this.getGrandCard().getBillCardPanel()
									.setBodyValueAt(refValue.getValue(), row, "component", "pk_task_r");
						} else if (refValue.getKey().equals("test_init_code")) {
							refCode = (String) refValue.getValue();
						} else if (refValue.getKey().equals("test_init_name")) {
							refName = (String) refValue.getValue();
						} else if (refValue.getKey().equals("pk_samplegroup")) {
							this.getGrandCard().getBillCardPanel()
									.setBodyValueAt(refValue.getValue(), row, "pk_samplegroup", "pk_task_r");
						} else if (refValue.getKey().equals("nc_sample_name")) {
							this.getGrandCard().getBillCardPanel()
									.setBodyValueAt(refValue.getValue(), row, "samplegroup", "pk_task_r");
						} else if (refValue.getKey().equals("pk_valuetype")) {
							this.getGrandCard().getBillCardPanel()
									.setBodyValueAt(refValue.getValue(), row, "pk_valuetype", "pk_task_r");
						} else if (refValue.getKey().equals("nc_result_namecn")) {
							this.getGrandCard()
									.getBillCardPanel()
									.setBodyValueAt(
											refValue.getValue() == null ? "" : refValue.getValue().toString()
													.replace(" ", ""), row, "valuetype", "pk_task_r");
						} else if (refValue.getKey().equals("nc_result_code")) {
							resultCode = (String) refValue.getValue();
						} else if (refValue.getKey().equals("nc_result_namecn")) {
							resultName = (String) refValue.getValue();
						} else if (refValue.getKey().equals("stdmaxvalue")) {
							this.getGrandCard().getBillCardPanel()
									.setBodyValueAt(refValue.getValue(), row, "stdmaxvalue", "pk_task_r");
						} else if (refValue.getKey().equals("stdminvalue")) {
							this.getGrandCard().getBillCardPanel()
									.setBodyValueAt(refValue.getValue(), row, "stdminvalue", "pk_task_r");
						} else if (refValue.getKey().equals("productstage")) {
							this.getGrandCard().getBillCardPanel()
									.setBodyValueAt(refValue.getValue(), row, "pk_testtemperature", "pk_task_r");
						} else if (refValue.getKey().equals("unitname")) {
							this.getGrandCard().getBillCardPanel()
									.setBodyValueAt(refValue.getValue(), row, "pk_unit", "pk_task_r");
						}
					}
					if (!StringUtils.isEmpty(resultCode) && !StringUtils.isEmpty(resultName)) {
						IConstEnum aValue = new DefaultConstEnum(resultName, resultName);
						this.getGrandCard()
								.getBillCardPanel()
								.setBodyValueAt(
										aValue.getValue() == null ? "" : aValue.getValue().toString().replace(" ", ""),
										row, "valuetype", "pk_task_r");
					}

					if (!StringUtils.isEmpty(refCode) && !StringUtils.isEmpty(refName)) {
						IConstEnum aValue = new DefaultConstEnum(refName, refName);
						this.getGrandCard()
								.getBillCardPanel()
								.setBodyValueAt(
										aValue.getValue() == null ? "" : aValue.getValue().toString().replace(" ", ""),
										row, "component", "pk_task_r");
					}

					this.getGrandCard().getBillCardPanel()
							.setBodyValueAt(UFBoolean.TRUE, row, "judgeflag", "pk_task_r");
					this.getGrandCard().getBillCardPanel().setBodyValueAt(UFBoolean.TRUE, row, "testflag", "pk_task_r");
					//如果最小值大于最大值，那么制空最大值 2020年4月2日23:18:10
					Object maxObj = this.getGrandCard().getBillCardPanel().getBodyValueAt( row, "stdmaxvalue");
					Object mixObj = this.getGrandCard().getBillCardPanel().getBodyValueAt( row, "stdminvalue");

					try{
						double maxValue = new UFDouble(String.valueOf(maxObj)).toDouble();
						double minValue = new UFDouble(String.valueOf(mixObj)).toDouble();
						if(maxValue < minValue){
							this.getGrandCard().getBillCardPanel()
							.setBodyValueAt(null, row, "stdmaxvalue", "pk_task_r");
						}
					}catch(Exception exc){
						Logger.error(exc.getMessage());
					}
					//mod end 
				}
				
			}

		} catch (BusinessException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return list;
	}

	private List<String> validate(String pk_commission_h, List<String> strlist, Object value) {
		List<String[]> numlist = new ArrayList<String[]>();
		Map<String, Integer> maps = new HashMap<String, Integer>();
		IUAPQueryBS iUAPQueryBS = (IUAPQueryBS) NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
		List<String> commlist = new ArrayList<String>();
		try {
			List<Map<String, Object>> custlist = (List<Map<String, Object>>) iUAPQueryBS
					.executeQuery(
							"select "
									+ "samplegroup.nc_sample_name, commission.QUANTITY from QC_COMMISSION_B commission left join NC_SAMPLE_GROUP samplegroup "
									+ "on commission.PK_SAMPLEGROUP = samplegroup.pk_sample_group where "
									+ "commission.PK_COMMISSION_H='" + pk_commission_h
									+ "' order by samplegroup.nc_sample_code", new MapListProcessor());
			List<Integer> listnum = new ArrayList<Integer>();
			if (null != custlist && custlist.size() > 0) {
				for (Map<String, Object> map : custlist) {
					maps.put(map.get("nc_sample_name").toString(),
							Integer.parseInt(String.valueOf(map.get("quantity"))));
				}
				if (maps != null && maps.size() > 0 && listnum.size() <= 0) {
					if (maps.containsKey("A")) {
						listnum.add(maps.get("A"));
					} else {
						listnum.add(0);
					}
					if (maps.containsKey("B")) {
						listnum.add(maps.get("B"));
					} else {
						listnum.add(0);
					}
					if (maps.containsKey("C")) {
						listnum.add(maps.get("C"));
					} else {
						listnum.add(0);
					}
					if (maps.containsKey("D")) {
						listnum.add(maps.get("D"));
					} else {
						listnum.add(0);
					}
				}
			}
			if (listnum != null && listnum.size() > 0) {
				for (String str : strlist) {
					numlist.add(StringOrderUtils.outDisOrderArray(str, listnum));
				}
				String[] array = StringOrderUtils.outDisOrderArray(String.valueOf(value), listnum);

				for (String[] string : numlist) {
					for (String str : string) {
						for (String strs : array) {
							if (strs.equals(str)) {
								commlist.add(strs);
							}
						}
					}
				}

			}

		} catch (Exception e) {
			e.getStackTrace();
		}
		return commlist;
	}

	@Override
	public void valueChanged(ValueChangedEvent arg0) {
		// TODO Auto-generated method stub

	}

	private UIButton getBtnCancel() {
		if (btnCancel == null) {
			try {
				btnCancel = new UIButton();
				btnCancel.setName("btnCancel");
				btnCancel.setText("取消");
				btnCancel.addActionListener(this);
				btnCancel.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK),
						JComponent.WHEN_IN_FOCUSED_WINDOW);
			} catch (Throwable ivjExc) {
				ivjExc.getStackTrace();
			}
		}
		return btnCancel;
	}

	private UIButton btnOK = null;// OK
	private UIButton btnCancel = null;// 取消

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}