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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.ValueChangedListener;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.qcco.commission.refmodel.ListTableRefModel;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.qcco.task.RefValueVO;

public class RefValuePanel extends UIDialog implements nc.ui.pub.bill.BillEditListener2, ActionListener,
		PropertyChangeListener, ValueChangedListener {
	private BillListPanel billListHeadPanel = null;
	private BillCardPanel billListBodyPanel = null;
	private static final long serialVersionUID = 1L;
	private UIPanel bnPanel = null;
	private UIButton okButton = null;
	private UIButton cancelButton = null;
	private Checkbox checkbox = null;
	private boolean isInit = false;

	private JPanel ivjUIDialogContentPane1;

	private UIPanel ivjUIPanel1 = null;
	private UIRefPane projectTypeField = null;// 项目类型
	private UILabel projectTypeLabel = null;// 项目类型

	private UIButton btnOKtop = null;// 确定按钮
	List<Integer> listnum = new ArrayList<Integer>();
	private String projectType;

	private CircularlyAccessibleValueObject[] ss = null;
	private JPanel ivjUIDialogContentPane = null;
	private String selectedCNstr;
	private String selectedENstr;
	private Integer testnum;
	private String[] strs;
	private String beforesample;
	private String reportLang;
	private String pk_list_table;

	public String getPk_list_table() {
		return pk_list_table;
	}

	public void setPk_list_table(String pk_list_table) {
		this.pk_list_table = pk_list_table;
	}

	public String getReportLang() {
		return reportLang;
	}

	public void setReportLang(String reportLang) {
		this.reportLang = reportLang;
	}

	public String[] getStrs() {
		return strs;
	}

	public void setStrs(String[] strs) {
		this.strs = strs;
	}

	public Integer getTestnum() {
		return testnum;
	}

	public void setTestnum(Integer testnum) {
		this.testnum = testnum;
	}

	public String getSelectedCNstr() {
		return selectedCNstr;
	}

	public void setSelectedCNstr(String selectedCNstr) {
		this.selectedCNstr = selectedCNstr;
	}
	
	public String getSelectedENstr() {
		return selectedENstr;
	}

	public void setSelectedENstr(String selectedENstr) {
		this.selectedENstr = selectedENstr;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public RefValuePanel(String pk_commission_h, String pk_list_table) throws DAOException {
		this.reportLang = getReportLangs(pk_commission_h);
		this.pk_list_table = pk_list_table;
		initialize();
	}

	private String getReportLangs(String pk_commission_h) {
		IUAPQueryBS iUAPQueryBS = (IUAPQueryBS) NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
		String reportCode = null;
		try {

			// for (TaskHBodyVO taskHBodyVO:pklists) {

			List<Map<String, String>> custlist = (List<Map<String, String>>) iUAPQueryBS.executeQuery(
					"select RP_REPORT_CODE from QC_COMMISSION_H left join NC_REPORT_LANG "
							+ "on NC_REPORT_LANG.pk_report_lang=qc_commission_h.REPORTLANG"
							+ " where pk_commission_h='" + pk_commission_h + "'", new MapListProcessor());
			for (Map<String, String> map : custlist) {
				reportCode = map.get("rp_report_code");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reportCode;
	}

	private void initialize() {
		this.setTitle("请选择参照");
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(440, 520));
		this.setContentPane(getUIDialogContentPane());
		isInit = true;
	}

	private Container getUIDialogContentPane() {
		if (ivjUIDialogContentPane == null) {
			try {
				ivjUIDialogContentPane = new javax.swing.JPanel();
				ivjUIDialogContentPane.setName("ivjUIDialogContentPane");
				ivjUIDialogContentPane.setLayout(new java.awt.BorderLayout());
				getUIDialogContentPane().add(getUIDialogContentPane1());
				getUIDialogContentPane().add(getBnPanel(), BorderLayout.SOUTH);
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
			RefValueVO[] RefValueVOsAllocatVO = (RefValueVO[]) getBillListHeadPanel().getBodyBillModel()
					.getBodySelectedVOs("nc.vo.qcco.task.RefValueVO");

			if (RefValueVOsAllocatVO != null && RefValueVOsAllocatVO.length > 0) {
				if (RefValueVOsAllocatVO.length > 1) {
					MessageDialog.showErrorDlg(this.getContentPane(), "错误", "请选择一条数据！");
					return;
				}
				for (RefValueVO taskBodyVO : RefValueVOsAllocatVO) {
					selectedCNstr = taskBodyVO.getChinaname();
					selectedENstr = taskBodyVO.getEngname();
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

	private void onButtonOKtopClicked() {
		String[] pk_list_tables = projectTypeField.getValueObj() == null ? null : (String[]) projectTypeField
				.getValueObj();
		if (null != pk_list_tables) {
			for (String string : pk_list_tables) {
				pk_list_table = string;
			}
		}
		ivjUIDialogContentPane = null;
		ivjUIDialogContentPane1 = null;
		billListHeadPanel = null;
		this.getContentPane().hide();
		initialize();

	}

	private JPanel getUIDialogContentPane1() {
		if (ivjUIDialogContentPane1 == null) {
			ivjUIDialogContentPane1 = new javax.swing.JPanel();
			ivjUIDialogContentPane1.setName("ivjUIDialogContentPane1");
			ivjUIDialogContentPane1.setLayout(null);
			// ivjUIDialogContentPane1.setLayout(new BorderLayout());
			getUIDialogContentPane1().add(getUIPanel1());
			getUIDialogContentPane1().add(getBillListHeadPanel());
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
				// ivjUIPanel1.add(getBtnOKtop());
				ivjUIPanel1.setBounds(10, 10, 300, 40);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjUIPanel1;
	}

	private UILabel getSampleNameLabel() {
		if (projectTypeLabel == null) {
			try {
				projectTypeLabel = new UILabel();
				projectTypeLabel.setName("projectTypeLabel");
				projectTypeLabel.setText("参照列表");
				projectTypeLabel.setBounds(0, 5, 80, 30);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return projectTypeLabel;
	}

	private UIRefPane getSampleNameField() {
		if (projectTypeField == null) {
			projectTypeField = new UIRefPane();
			projectTypeField.setRefModel(new ListTableRefModel());
			projectTypeField.setVisible(true);
			projectTypeField.setBounds(70, 8, 150, 50);
			projectTypeField.setButtonFireEvent(true);
			projectTypeField.addPropertyChangeListener(this);
			projectTypeField.setMultiSelectedEnabled(false);
			projectTypeField.getRefModel().setMutilLangNameRef(false);
			projectTypeField.addValueChangedListener(this);
		}
		return projectTypeField;
	}

	private UIButton getBtnOKtop() {
		if (btnOKtop == null) {
			try {
				btnOKtop = new UIButton();
				btnOKtop.setName("btnOKtop");
				btnOKtop.setText("刷新");
				btnOKtop.addActionListener(this);
				btnOKtop.setBounds(250, 8, 50, 25);
				btnOKtop.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.ALT_MASK),
						JComponent.WHEN_IN_FOCUSED_WINDOW);
			} catch (Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return btnOKtop;
	}

	private void handleException(Throwable exception) {
		Logger.error("--------- 未捕捉到的异常 ---------");
		MessageDialog.showErrorDlg(this, null, exception.getMessage());
		exception.printStackTrace();
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
			billListHeadPanel.loadTemplet("1001ZZ1000000000480B");
			billListHeadPanel.setVisible(true);
			billListHeadPanel.setEnabled(true);
			billListHeadPanel.setBounds(10, 60, 420, 390);

			billListHeadPanel.setMultiSelect(true);
			List<RefValueVO> lists = getListDatas();

			billListHeadPanel.setBodyValueVO(lists.toArray(new RefValueVO[0]));

			billListHeadPanel.setAutoscrolls(true);
		}
		return billListHeadPanel;
	}

	@Override
	public boolean beforeEdit(BillEditEvent paramBillEditEvent) {
		// TODO Auto-generated method stub
		return false;
	}

	private List<RefValueVO> getListDatas() {
		IUAPQueryBS iUAPQueryBS = (IUAPQueryBS) NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());

		List<RefValueVO> lists = new ArrayList<>();
		String sql = "select distinct trim(nc_list_code)as nc_list_code,c_en_value,value from nc_list_entry where 11 = 11 ";
		if (pk_list_table != null) {
			sql += " and pk_list_table ='" + pk_list_table + "'";
		}
		try {
			List<Map<String, Object>> custlist = (List<Map<String, Object>>) iUAPQueryBS.executeQuery(sql,
					new MapListProcessor());

			if (null != custlist && custlist.size() > 0) {
				for (Map<String, Object> map : custlist) {
					RefValueVO refValueVO = new RefValueVO();
					refValueVO
							.setChinaname(map.get("value") == null ? null : map.get("value").toString());
					refValueVO.setEngname(map.get("c_en_value") == null ? null : map.get("c_en_value").toString());
					refValueVO.setCode(map.get("nc_list_code") == null ? null : map.get("nc_list_code").toString());
					lists.add(refValueVO);
				}

			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lists;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

	}

	@Override
	public void valueChanged(ValueChangedEvent arg0) {
		if (isInit) {
			String[] pk_list_tables = projectTypeField.getValueObj() == null ? null : (String[]) projectTypeField
					.getValueObj();
			if (null != pk_list_tables) {
				for (String string : pk_list_tables) {
					pk_list_table = string;
				}
			}
			ivjUIDialogContentPane = null;
			ivjUIDialogContentPane1 = null;
			billListHeadPanel = null;
			this.getContentPane().hide();
			initialize();
		}
	}

}
