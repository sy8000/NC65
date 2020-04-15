package nc.ui.qcco.task.view;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.hr.utils.InSQLCreator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITextField;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.qcco.task.TaskBodyVO;
import nc.vo.qcco.task.TaskHBodyVO;

@SuppressWarnings("unused")
public class SunlistPanel extends UIDialog implements nc.ui.pub.bill.BillEditListener2, ActionListener {
	private BillListPanel billListHeadPanel = null;
	private BillCardPanel billListBodyPanel = null;
	private static final long serialVersionUID = 1L;
	private UIPanel bnPanel = null;
	private UIButton okButton = null;
	private UIButton cancelButton = null;
	private Checkbox checkbox = null;
	private String dateTime = ""; // 日期
	private String legalOrg = "";// 法人组织
	private String hrOrg = "";// 人力资源组织
	private JPanel ivjUIDialogContentPane1;
	UIRefPane changeDateRefPane;// 变更日期参照
	private UIPanel ivjUIPanel1 = null;
	private JTextField projectTypeField = null;// 项目类型
	private UILabel projectTypeLabel = null;// 项目类型
	private JTextField testProjectField = null;// 测试项目
	private UILabel testProjectLabel = null;// 测试项目
	private JTextField businessField = null;// 企业标准
	private UILabel businessLabel = null;// 企业标准
	private JTextField scopeField = null;// 适用范围
	private UILabel scopeLabel = null;// 适用范围
	private JTextField testWayField = null;// 测试方法
	private UILabel testWayLabel = null;// 测试方法
	private JTextField projectCateField = null;// 产品类别
	private UILabel projectCateLabel = null;// 产品类别
	private UIButton btnOKtop = null;// 确定按钮
	private String pk_commission_h;
	private String productstard = null;
	private String productcate = null;
	private String projectType;
	private String testProject;
	private String scope;
	private String testWay;
	private CircularlyAccessibleValueObject[] ss = null;
	private JPanel ivjUIDialogContentPane = null;
	private List<TaskHBodyVO> pklist = null;
	private List<TaskHBodyVO> pkbodylist = new ArrayList<>();

	public List<TaskHBodyVO> getPklist() {
		if (null == pklist) {
			pklist = new ArrayList<TaskHBodyVO>();
		}
		return pklist;
	}

	public void setPklist(List<TaskHBodyVO> pklist) {
		this.pklist = pklist;
	}

	public String getProductstard() {
		return productstard;
	}

	public void setProductstard(String productstard) {
		this.productstard = productstard;
	}

	public String getProductcate() {
		return productcate;
	}

	public void setProductcate(String productcate) {
		this.productcate = productcate;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getTestProject() {
		return testProject;
	}

	public void setTestProject(String testProject) {
		this.testProject = testProject;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getTestWay() {
		return testWay;
	}

	public void setTestWay(String testWay) {
		this.testWay = testWay;
	}

	public SunlistPanel(String pk_commission_h) throws DAOException {
		this.pk_commission_h = pk_commission_h;
		if (pk_commission_h != null) {
			Map<String, String> products = getproductdata(pk_commission_h);
			if (null != products && products.size() > 0) {
				productstard = products.get("productstard");
				productcate = products.get("productcate");
			}
		}
		initialize();
	}

	private void initialize() {
		this.setTitle("请选择数据");
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(860, 440));
		this.setContentPane(getUIDialogContentPane());

	}

	private Container getUIDialogContentPane() {
		if (ivjUIDialogContentPane == null) {
			try {
				ivjUIDialogContentPane = new javax.swing.JPanel();
				ivjUIDialogContentPane.setName("ivjUIDialogContentPane");
				ivjUIDialogContentPane.setLayout(new java.awt.BorderLayout());
				ivjUIDialogContentPane.add(getUIDialogContentPane1(), BorderLayout.CENTER);
				ivjUIDialogContentPane.add(getBnPanel(), BorderLayout.SOUTH);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjUIDialogContentPane;

	}

	private UIPanel getBnPanel() {
		if (this.bnPanel == null) {
			this.bnPanel = new UIPanel();
			this.bnPanel.setLayout(new FlowLayout());
			this.bnPanel.setPreferredSize(new Dimension(500, 50));
			this.bnPanel.add(getOkButton(), null);
			this.bnPanel.add(getCancelButton(), null);
		}
		return this.bnPanel;
	}

	private UIButton getOkButton() {
		if (this.okButton == null) {
			this.okButton = new UIButton();
			this.okButton.setBounds(new Rectangle(50, 5, 30, 20));
			this.okButton.setText(NCLangRes.getInstance().getStrByID("common", "UC001-0000044"));
			this.okButton.setPreferredSize(new Dimension(60, 22));
			this.okButton.addActionListener(this);
		}
		return this.okButton;
	}

	private UIButton getCancelButton() {
		if (this.cancelButton == null) {
			this.cancelButton = new UIButton();
			this.cancelButton.setBounds(new Rectangle(200, 5, 30, 20));
			this.cancelButton.setText(NCLangRes.getInstance().getStrByID("common", "UC001-0000008"));
			this.cancelButton.setPreferredSize(new Dimension(60, 22));
			this.cancelButton.addActionListener(this);
		}
		return this.cancelButton;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(getOkButton())) {
			setResult(UIDialog.ID_OK);
			TaskBodyVO[] bodySelectedVOs = (TaskBodyVO[]) getBillListHeadPanel().getBodyBillModel().getBodySelectedVOs(
					"nc.vo.qcco.task.TaskBodyVO");
			Map<TaskHBodyVO, String> map = new HashMap<>();
			if (null != bodySelectedVOs && bodySelectedVOs.length > 0) {
				for (int i = 0; i < bodySelectedVOs.length; i++) {
					for (TaskHBodyVO taskHBodyVO : pkbodylist) {
						/*
						 * if
						 * ((taskHBodyVO.getTaskBodyVO().getAccordstandard()==
						 * null
						 * ?"":taskHBodyVO.getTaskBodyVO().getAccordstandard(
						 * )).equals
						 * (bodySelectedVOs[i].getAccordstandard()==null
						 * ?"":bodySelectedVOs[i].getAccordstandard()) &&
						 * (taskHBodyVO.getTaskBodyVO().getCbplan()==null ?
						 * "":taskHBodyVO
						 * .getTaskBodyVO().getCbplan()).equals(bodySelectedVOs
						 * [i
						 * ].getCbplan()==null?"":bodySelectedVOs[i].getCbplan(
						 * )) &&
						 * (taskHBodyVO.getTaskBodyVO().getProjectName()==null
						 * ?""
						 * :taskHBodyVO.getTaskBodyVO().getProjectName()).equals
						 * (bodySelectedVOs[i].getProjectName()==null?"":
						 * bodySelectedVOs[i].getProjectName()) &&
						 * (taskHBodyVO.getTaskBodyVO
						 * ().getProjectType()==null?""
						 * :taskHBodyVO.getTaskBodyVO
						 * ().getProjectType()).equals(
						 * bodySelectedVOs[i].getProjectType
						 * ()==null?"":bodySelectedVOs[i].getProjectType()) &&
						 * (taskHBodyVO
						 * .getTaskBodyVO().getReportName()==null?"":
						 * taskHBodyVO.
						 * getTaskBodyVO().getReportName()).equals(bodySelectedVOs
						 * [i].getReportName()==null?"":bodySelectedVOs[i].
						 * getReportName()) &&
						 * (taskHBodyVO.getTaskBodyVO().getDetailDescription
						 * ()==null
						 * ?"":taskHBodyVO.getTaskBodyVO().getDetailDescription
						 * ()
						 * ).equals(bodySelectedVOs[i].getDetailDescription()==
						 * null?"":bodySelectedVOs[i].getDetailDescription()) &&
						 * (taskHBodyVO.getTaskBodyVO().getTestList()==null?"":
						 * taskHBodyVO
						 * .getTaskBodyVO().getTestList()).equals(bodySelectedVOs
						 * [
						 * i].getTestList()==null?"":bodySelectedVOs[i].getTestList
						 * ())) { map.put(taskHBodyVO, null); }
						 */
						if (taskHBodyVO.getUnique().equals(bodySelectedVOs[i].getPk_sunlist())) {
							map.put(taskHBodyVO, null);
						}
					}
				}
			}
			if (null != map && map.size() > 0) {
				for (TaskHBodyVO taskHBodyVO : map.keySet()) {
					getPklist().add(taskHBodyVO);
					pkbodylist.clear();
				}
			}

			dispose();
		} else if (e.getSource().equals(getCancelButton())) {
			setResult(UIDialog.ID_CANCEL);
			dispose();
		} else if (e.getSource() == this.getBtnOKtop()) {
			onButtonOKtopClicked();
		}
	}

	private JPanel getUIDialogContentPane1() {
		if (ivjUIDialogContentPane1 == null) {
			ivjUIDialogContentPane1 = new javax.swing.JPanel();
			ivjUIDialogContentPane1.setName("ivjUIDialogContentPane1");
			ivjUIDialogContentPane1.setLayout(null);
			// ivjUIDialogContentPane1.setLayout(new BorderLayout());
			ivjUIDialogContentPane1.add(getUIPanel1());
			ivjUIDialogContentPane1.add(getBillListHeadPanel());
		}
		return ivjUIDialogContentPane1;
	}

	/**
	 * 
	 * @return
	 */
	private UIPanel getUIPanel1() {
		if (ivjUIPanel1 == null) {
			try {
				ivjUIPanel1 = new UIPanel();
				ivjUIPanel1.setName("ivjUIPanel1");
				ivjUIPanel1.setLayout(null);
				ivjUIPanel1.add(getSampleNameLabel());
				ivjUIPanel1.add(getSampleNameField());
				ivjUIPanel1.add(getTestProjectLabel());
				ivjUIPanel1.add(getTestProjectField());
				ivjUIPanel1.add(getBusinessLabel());
				ivjUIPanel1.add(getbusinessField());
				ivjUIPanel1.add(getScopeLabel());
				ivjUIPanel1.add(getScopeField());
				ivjUIPanel1.add(getTestWayLabel());
				ivjUIPanel1.add(getTestWayField());
				ivjUIPanel1.add(getProjectCateLabel());
				ivjUIPanel1.add(getProjectCateField());
				ivjUIPanel1.add(getBtnOKtop());
				ivjUIPanel1.setBounds(50, 10, 800, 90);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjUIPanel1;
	}

	private UIButton getBtnOKtop() {
		if (btnOKtop == null) {
			try {
				btnOKtop = new UIButton();
				btnOKtop.setName("btnOKtop");
				btnOKtop.setText("查询");
				btnOKtop.addActionListener(this);
				btnOKtop.setBounds(750, 48, 50, 25);
				btnOKtop.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.ALT_MASK),
						JComponent.WHEN_IN_FOCUSED_WINDOW);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return btnOKtop;
	}

	private UILabel getSampleNameLabel() {
		if (projectTypeLabel == null) {
			try {
				projectTypeLabel = new UILabel();
				projectTypeLabel.setName("projectTypeLabel");
				projectTypeLabel.setText("项目类型");
				projectTypeLabel.setBounds(0, 5, 80, 30);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return projectTypeLabel;
	}

	private JTextField getSampleNameField() {
		if (this.projectTypeField == null) {
			try {
				projectTypeField = new UITextField();
				projectTypeField.setName("projectTypeField");
				projectTypeField.setBounds(70, 8, 150, 50);
				projectTypeField.setText(null);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}

		}
		return this.projectTypeField;
	}

	private UILabel getTestProjectLabel() {
		if (testProjectLabel == null) {
			try {
				testProjectLabel = new UILabel();
				testProjectLabel.setName("testProjectLabel");
				testProjectLabel.setText("测试项目");
				testProjectLabel.setBounds(250, 5, 80, 30);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return testProjectLabel;
	}

	private JTextField getTestProjectField() {
		if (this.testProjectField == null) {
			try {
				testProjectField = new UITextField();
				testProjectField.setName("testProjectField");
				testProjectField.setBounds(320, 8, 150, 50);
				testProjectField.setText(null);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}

		}
		return this.testProjectField;
	}

	private UILabel getBusinessLabel() {
		if (businessLabel == null) {
			try {
				businessLabel = new UILabel();
				businessLabel.setName("businessLabel");
				businessLabel.setText("产品标准");
				businessLabel.setBounds(500, 5, 80, 30);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return businessLabel;
	}

	private JTextField getbusinessField() {
		if (this.businessField == null) {
			try {
				businessField = new UITextField();
				businessField.setName("businessField");
				businessField.setBounds(570, 8, 150, 50);
				businessField.setText(productstard == null ? null : productstard.replace(" ", ""));
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}

		}
		return this.businessField;
	}

	private UILabel getScopeLabel() {
		if (scopeLabel == null) {
			try {
				scopeLabel = new UILabel();
				scopeLabel.setName("scopeLabel");
				scopeLabel.setText("适用范围");
				scopeLabel.setBounds(0, 45, 80, 30);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return scopeLabel;
	}

	private JTextField getScopeField() {
		if (this.scopeField == null) {
			try {
				scopeField = new UITextField();
				scopeField.setName("scopeField");
				scopeField.setBounds(70, 48, 150, 50);
				scopeField.setText(null);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}

		}
		return this.scopeField;
	}

	private UILabel getTestWayLabel() {
		if (testWayLabel == null) {
			try {
				testWayLabel = new UILabel();
				testWayLabel.setName("testWayLabel");
				testWayLabel.setText("测试标准");
				testWayLabel.setBounds(250, 45, 80, 30);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return testWayLabel;
	}

	private JTextField getTestWayField() {
		if (this.testWayField == null) {
			try {
				testWayField = new UITextField();
				testWayField.setName("testWayField");
				testWayField.setBounds(320, 48, 150, 50);
				testWayField.setText(null);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}

		}
		return this.testWayField;
	}

	private UILabel getProjectCateLabel() {
		if (projectCateLabel == null) {
			try {
				projectCateLabel = new UILabel();
				projectCateLabel.setName("projectCateLabel");
				projectCateLabel.setText("产品类别");
				projectCateLabel.setBounds(500, 45, 80, 30);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return projectCateLabel;
	}

	private JTextField getProjectCateField() {
		if (this.projectCateField == null) {
			try {
				projectCateField = new UITextField();
				projectCateField.setName("projectCateField");
				projectCateField.setBounds(570, 48, 150, 50);
				projectCateField.setText(productcate == null ? null : productcate.replace(" ", ""));
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}

		}
		return this.projectCateField;
	}

	private void handleException(Throwable exception) {
		Logger.error("--------- 未捕捉到的异常 ---------");
		MessageDialog.showErrorDlg(this, null, exception.getMessage());
		exception.printStackTrace();
	}

	private void onButtonOKtopClicked() {
		ivjUIDialogContentPane = null;
		ivjUIDialogContentPane1 = null;
		billListHeadPanel = null;
		this.projectType = getSampleNameField().getText();
		this.productstard = getbusinessField().getText();
		this.productcate = getProjectCateField().getText();
		this.testProject = getTestProjectField().getText();
		this.scope = getScopeField().getText();
		this.testWay = getTestWayField().getText();
		Map<String, String> conditionmaps = new HashMap<>();
		if (null != projectType && projectType.length() > 0) {
			conditionmaps.put("projectType", this.projectType);
		}
		if (null != productstard && productstard.length() > 0) {
			conditionmaps.put("productstard", this.productstard);
		}
		if (null != productcate && productcate.length() > 0) {
			conditionmaps.put("productcate", this.productcate);
		}
		if (null != testProject && testProject.length() > 0) {
			conditionmaps.put("testProject", this.testProject);
		}
		if (null != scope && scope.length() > 0) {
			conditionmaps.put("scope", this.scope);
		}
		if (null != testWay && testWay.length() > 0) {
			conditionmaps.put("testWay", this.testWay);
		}

		// 查询出listData
		List<TaskBodyVO> taskvos = getListbody(conditionmaps);
		/*
		 * BillListData billTempletData = new BillListData(); for (int i = 0; i
		 * < 34; i++) { TaskBodyVO taskbodyvo = new TaskBodyVO();
		 * taskbodyvo.setCbplan("11" + i); taskbodyvo.setDetailDescription("22"
		 * + i); taskbodyvo.setAccordstandard("333" + i);
		 * taskbodyvo.setProjectName("44" + i); taskbodyvo.setProjectType("55" +
		 * i); taskbodyvo.setReportName("66" + i); taskbodyvo.setTestList("77" +
		 * i); taskvos.add(taskbodyvo); }
		 */

		ss = taskvos.toArray(new TaskBodyVO[0]);
		this.getContentPane().hide();
		initialize();

	}

	// 查询出弹出框内容
	public List<TaskBodyVO> getListbody(Map<String, String> conditionmaps) {
		// 先判断条数是否超标
		IUAPQueryBS iUAPQueryBS = (IUAPQueryBS) NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
		List<TaskBodyVO> conditions = new ArrayList<TaskBodyVO>();
		try {
			String sql = "SELECT  DISTINCT trim(NC_TASK_ADDUNION.pk_task_addunion)   pk_task_addunion,  trim(NC_TASK_ADDUNION.nc_testlist_name) nc_testlist_name, "
					+ "  trim(NC_TASK_ADDUNION.nc_report_name) nc_report_name,"
					+ "  trim(NC_TASK_ADDUNION.nc_analysis_method) nc_analysis_method ,    "
					+ " trim(NC_TASK_ADDUNION.nc_task_type) nc_task_type,"
					+ "    trim(NC_TASK_ADDUNION.nc_task_name) nc_task_name,"
					+ "   trim(NC_TASK_ADDUNION.nc_task_des) nc_task_des ,"
					+ "   trim(NC_TASK_ADDUNION.nc_cb_plan) nc_cb_plan,"
					+ " trim(c.c_test_condition)  c_test_condition, "
					+ " trim(c.common_name) common_name FROM    NC_TASK_ADDUNION "
					+ "left join (select a.c_test_condition,common_name,"
					+ "nc_task_addname from analysis a,nc_task_addunion nca where trim(a.name) = trim(nca.nc_task_addname) )c "
					+ "on trim(c.nc_task_addname) = trim(NC_TASK_ADDUNION.nc_task_name) WHERE 1=1 ";
			if (null != conditionmaps && conditionmaps.size() > 0 && null != conditionmaps.get("projectType")
					&& conditionmaps.get("projectType") != "") {
				sql += "and nc_task_type like '%" + conditionmaps.get("projectType") + "%'";
			}

			if (null != conditionmaps && conditionmaps.size() > 0 && null != conditionmaps.get("scope")
					&& conditionmaps.get("scope") != "") {
				sql += "and nc_task_des like '%" + conditionmaps.get("scope") + "%'";
			}

			if (null != conditionmaps && conditionmaps.size() > 0 && null != conditionmaps.get("testProject")
					&& conditionmaps.get("testProject") != "") {
				sql += "and nc_report_name like '%" + conditionmaps.get("testProject") + "%'";
			}
			if (null != conditionmaps && conditionmaps.size() > 0 && null != conditionmaps.get("testWay")
					&& conditionmaps.get("testWay") != "") {
				sql += "and nc_analysis_method like '%" + conditionmaps.get("testWay") + "%'";
			}
			if (null != conditionmaps && conditionmaps.size() > 0 && null != conditionmaps.get("productstard")
					&& conditionmaps.get("productstard") != "") {
				if (conditionmaps.get("productstard").contains(",")) {
					String[] str = (conditionmaps.get("productstard") + ",_NA").split(",");
					String sssql = "   (   nc_testlist_name like '%"
							+ str[0] + "%' ";

					for (int i = 1; i < str.length; i++) {
						sssql += " or nc_testlist_name like '%" + str[i] + "%'";
					}
					sssql += ")";
					InSQLCreator insql = new InSQLCreator();
					String psInSQL = insql.getInSQL(str);
					sql += " and (" + sssql + ")";
				} else {
					// sql+=" and nc_testlist_name like '%"+conditionmaps.get("productstard")+"%'";
					sql += " and  (nc_testlist_name like "
							+ "'%" + conditionmaps.get("productstard") + "%' or nc_testlist_name like '%_NA%')";
				}
			}
			if (null != conditionmaps && conditionmaps.size() > 0 && null != conditionmaps.get("productcate")
					&& conditionmaps.get("productcate") != "") {
				if (conditionmaps.get("productcate").contains(",")) {
					String[] str = conditionmaps.get("productcate").split(",");
					InSQLCreator insql = new InSQLCreator();
					String psInSQL = insql.getInSQL(str);
					sql += " and nc_include_protype in(" + psInSQL + ")";
				} else {
					sql += "  and nc_include_protype like '%" + conditionmaps.get("productcate") + "%'";
				}
			}

			sql += " ORDER BY NC_TESTLIST_NAME ";

			List<Map<String, String>> custlist = (List<Map<String, String>>) iUAPQueryBS.executeQuery(sql,
					new MapListProcessor());

			if (null != custlist && custlist.size() > 0) {
				int i = 0;
				pkbodylist.clear();
				for (Map<String, String> map : custlist) {
					TaskBodyVO taskbodyvo = new TaskBodyVO();
					TaskHBodyVO taskHBodyVO = new TaskHBodyVO();
					taskbodyvo.setCbplan(map.get("nc_cb_plan"));
					taskbodyvo.setAccordstandard(map.get("nc_analysis_method"));
					taskbodyvo.setDetailDescription(map.get("nc_task_des"));
					taskbodyvo.setProjectName(map.get("nc_task_name"));
					taskbodyvo.setProjectType(map.get("nc_task_type"));
					taskbodyvo.setTestList(map.get("nc_testlist_name"));
					taskbodyvo.setReportName(map.get("nc_report_name"));
					// taskbodyvo.setUnique(map.get("nc_cb_plan")+i);
					taskbodyvo.setPk_sunlist(map.get("pk_task_addunion"));
					conditions.add(taskbodyvo);

					taskHBodyVO.setProjectName(map.get("nc_task_name"));
					taskHBodyVO.setReportName(map.get("nc_report_name"));
					taskHBodyVO.setTestlistName(map.get("nc_testlist_name"));
					taskHBodyVO.setTestresultname(map.get("c_test_condition"));
					taskHBodyVO.setTestresultshortname(map.get("common_name"));
					taskHBodyVO.setUnique(map.get("pk_task_addunion"));
					taskHBodyVO.setTaskBodyVO(taskbodyvo);
					pkbodylist.add(taskHBodyVO);

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return conditions;
	}

	// 通过委托单的pk查询出子表的企业标准和产品类别
	public Map<String, String> getproductdata(String pk_commission_h) {
		IUAPQueryBS iUAPQueryBS = (IUAPQueryBS) NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
		Map<String, String> products = new HashMap<String, String>();
		Map<String, String> productstardMap = new HashMap<String, String>();
		Map<String, String> productCateMap = new HashMap<String, String>();
		String productstardstr = "";
		String productCatestr = "";
		try {
			List<Map<String, String>> custlist = (List<Map<String, String>>) iUAPQueryBS.executeQuery(
					"select distinct "
							+ " c.nc_bbasen_name ,d.name as sname,f.prod_type as fname from qc_commission_b b "
							+ "left join qc_commission_h h on h.pk_commission_h=b.pk_commission_h "
							+ "left join NC_BASEN_TYPE c on c.pk_basen_type = b.pk_enterprisestandard "
							+ " left join NC_SECOND_TYPE d on d.pk_second_type = h.pk_subcategory "
							+ " left join NC_FIRST_TYPE f on f.pk_first_type = h.pk_maincategory "
							+ " left join NC_THIRD_TYPE t on t.pk_third_type = h.pk_lastcategory "
							+ " where h.pk_commission_h='" + pk_commission_h + "'", new MapListProcessor());
			if (custlist != null && custlist.size() > 0) {
				for (Map<String, String> map : custlist) {
					if (null != map.get("nc_bbasen_name")) {
						productstardMap.put(map.get("nc_bbasen_name"), null);
					}
					if (null != map.get("sname")) {
						productCateMap.put(map.get("sname"), null);
					}
					/*
					 * if (null != map.get("fname")) {
					 * productCateMap.put(map.get("fname"), null); }
					 */
					/*
					 * if (null != map.get("tname")) {
					 * productCateMap.put(map.get("tname"), null); }
					 */
				}
				if (null != productstardMap && productstardMap.size() > 0) {
					for (String str : productstardMap.keySet()) {
						productstardstr += "," + str;
					}
				}
				if (null != productCateMap && productCateMap.size() > 0) {
					for (String str : productCateMap.keySet()) {
						productCatestr += "," + str;
					}
				}
				if (productstardstr.length() > 1) {
					products.put("productstard", productstardstr.substring(1, productstardstr.length()));
				}
				if (productCatestr.length() > 1) {
					products.put("productcate", productCatestr.substring(1, productCatestr.length()));
				}

			}

		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return products;
	}

	/*
	 * 根据选择的收款单数据，对已经填写的数据进行校验。
	 */
	private String checkListData(String redate, String backdate) {

		return null;
	}

	private String checkCardData(String redate, String backdate) {
		String message = "";
		return message;
	}

	/*
	 * 获取模版
	 */
	private BillListPanel getBillListHeadPanel() {
		if (billListHeadPanel == null) {
			billListHeadPanel = new BillListPanel();
			billListHeadPanel.loadTemplet("1001ZZ10000000001YZR");
			billListHeadPanel.setVisible(true);
			billListHeadPanel.setEnabled(true);
			billListHeadPanel.setBounds(10, 100, 840, 290);
			billListHeadPanel.setAutoscrolls(true);
			billListHeadPanel.setMultiSelect(true);

			if (ss == null) {
				Map<String, String> conditionmaps = new HashMap<>();
				conditionmaps.put("productcate", productcate == null ? null : productcate.replace(" ", ""));
				conditionmaps.put("productstard", productstard == null ? null : productstard.replace(" ", ""));
				List<TaskBodyVO> taskvos = getListbody(conditionmaps);
				ss = taskvos.toArray(new TaskBodyVO[0]);
			}
			billListHeadPanel.setBodyValueVO(ss);
		}
		return billListHeadPanel;
	}

	@Override
	public boolean beforeEdit(BillEditEvent paramBillEditEvent) {
		// TODO Auto-generated method stub
		return false;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getLegalOrg() {
		return legalOrg;
	}

	public void setLegalOrg(String legalOrg) {
		this.legalOrg = legalOrg;
	}

	public String getHrOrg() {
		return hrOrg;
	}

	public void setHrOrg(String hrOrg) {
		this.hrOrg = hrOrg;
	}

}
