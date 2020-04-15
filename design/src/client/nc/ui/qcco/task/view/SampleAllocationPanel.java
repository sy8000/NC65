package nc.ui.qcco.task.view;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import bsh.This;

import com.borland.jbcl.control.CheckboxPanel;
import com.hazelcast.spi.annotation.PrivateApi;
import com.ibm.db2.jcc.sqlj.e;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;
import com.ufida.report.free.plugin.fieldattribute.SmartCheckBoxList;
import com.ufida.report.free.plugin.fieldattribute.SmartListData;

import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.hr.utils.ResHelper;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.SQLParameter;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UIScrollPane;
import nc.ui.pub.beans.UITextField;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.ValueChangedListener;
import nc.ui.pub.beans.progress.IProgressMonitor;
import nc.ui.pub.beans.progress.NCProgresses;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillListData;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.formula.ui.InputHandler.insert_break;
import nc.ui.qcco.commission.refmodel.SampleAllocationRefModel;
import nc.ui.qcco.commission.refmodel.UnitTypeRefModel;
import nc.ui.qcco.task.utils.StringOrderUtils;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.qcco.task.SampleAllocatVO;
import nc.vo.qcco.task.TaskBodyVO;
import nc.vo.trade.bdinfo.BaseDocVO;

public class SampleAllocationPanel extends UIDialog implements
		nc.ui.pub.bill.BillEditListener2, ActionListener {
	private BillListPanel billListHeadPanel = null;
	private BillCardPanel billListBodyPanel = null;
	private static final long serialVersionUID = 1L;
	private UIPanel bnPanel = null;
	private UIButton okButton = null;
	private UIButton cancelButton = null;
	private Checkbox checkbox = null;

	private JPanel ivjUIDialogContentPane1;

	private UIPanel ivjUIPanel1 = null;
	private JTextField projectTypeField = null;// 项目类型
	private UILabel projectTypeLabel = null;// 项目类型

	private UIButton btnOKtop = null;// 确定按钮
	private String pk_commission_h;
	List<Integer> listnum = new ArrayList<Integer>();
	private String projectType;

	private CircularlyAccessibleValueObject[] ss = null;
	private JPanel ivjUIDialogContentPane = null;
	private String selectedstr;
	private Integer testnum;
	private String[] strs;
	private String beforesample;
	//原始选择列
	private List<String> selectedList;
	
	

	public List<String> getSelectedList() {
		return selectedList;
	}

	public void setSelectedList(List<String> selectedList) {
		this.selectedList = selectedList;
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

	public String getSelectedstr() {
		return selectedstr;
	}

	public void setSelectedstr(String selectedstr) {
		this.selectedstr = selectedstr;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public SampleAllocationPanel(String pk_commission_h,Object obj) throws DAOException {
		this.pk_commission_h = pk_commission_h;
		beforesample = (String)obj;
		if (null != beforesample) {
			getListDatas();
			try {
				strs = StringOrderUtils.outDisOrderArray(beforesample, listnum);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		initialize();
	}

	private void initialize() {
		// this.setTitle("请选择数据");
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(400, 480));
		this.setContentPane(getUIDialogContentPane());

	}

	private Container getUIDialogContentPane() {
		if (ivjUIDialogContentPane == null) {
			try {
				ivjUIDialogContentPane = new javax.swing.JPanel();
				ivjUIDialogContentPane.setName("ivjUIDialogContentPane");
				ivjUIDialogContentPane.setLayout(new java.awt.BorderLayout());
				getUIDialogContentPane().add(getUIDialogContentPane1(),
						BorderLayout.CENTER);
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
			this.okButton.setText(NCLangRes.getInstance().getStrByID("common",
					"UC001-0000044"));
			this.okButton.setPreferredSize(new Dimension(60, 22));
			this.okButton.addActionListener(this);
		}
		return this.okButton;
	}

	private UIButton getCancelButton() {
		if (this.cancelButton == null) {
			this.cancelButton = new UIButton();
			this.cancelButton.setBounds(new Rectangle(200, 5, 30, 20));
			this.cancelButton.setText(NCLangRes.getInstance().getStrByID(
					"common", "UC001-0000008"));
			this.cancelButton.setPreferredSize(new Dimension(60, 22));
			this.cancelButton.addActionListener(this);
		}
		return this.cancelButton;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(getOkButton())) {
			setResult(UIDialog.ID_OK);
			SampleAllocatVO[] sampleAllocatVOsAllocatVO = (SampleAllocatVO[]) getBillListHeadPanel()
					.getBodyBillModel().getBodySelectedVOs(
							"nc.vo.qcco.task.SampleAllocatVO");
			if (sampleAllocatVOsAllocatVO != null
					&& sampleAllocatVOsAllocatVO.length > 0) {
				List<String> strlist = new ArrayList<>();
				for (SampleAllocatVO taskBodyVO : sampleAllocatVOsAllocatVO) {
					strlist.add(taskBodyVO.getSampleallocat());
				}
				try {
					testnum = strlist.size();
					selectedList = new ArrayList<>();
					selectedList.addAll(strlist);
					selectedstr = StringOrderUtils.outOrderString(
							strlist.toArray(new String[strlist.size()]),
							listnum);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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

				ivjUIPanel1.add(getBtnOKtop());
				ivjUIPanel1.setBounds(50, 10, 300, 40);
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
				btnOKtop.setText("确定");
				btnOKtop.addActionListener(this);
				btnOKtop.setBounds(250, 5, 50, 30);
				btnOKtop.registerKeyboardAction(this, KeyStroke.getKeyStroke(
						KeyEvent.VK_Y, InputEvent.ALT_MASK),
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
				projectTypeLabel.setText("样品分配");
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

	private void handleException(Throwable exception) {
		Logger.error("--------- 未捕捉到的异常 ---------");
		MessageDialog.showErrorDlg(this, null, exception.getMessage());
		exception.printStackTrace();
	}

	private void onButtonOKtopClicked() {
		ivjUIDialogContentPane = null;
		ivjUIDialogContentPane1 = null;
		SampleAllocatVO[] sampleAllocatVOsAllocatVO = (SampleAllocatVO[])billListHeadPanel
				.getBodyBillModel().getBodySelectedVOs(
						"nc.vo.qcco.task.SampleAllocatVO");
		billListHeadPanel = null;
		this.projectType = getSampleNameField().getText();

		try {
			if (listnum != null) {
				
				List<String> numlistnew = new ArrayList<>();
				if (null != sampleAllocatVOsAllocatVO && sampleAllocatVOsAllocatVO.length > 0) {
					for(SampleAllocatVO sa : sampleAllocatVOsAllocatVO){
						numlistnew.add(sa.getSampleallocat());
					}
				}
				/*if (strs != null && strs.length > 0) {
					for (int i = 0; i < strs.length; i++) {
						numlistnew.add(strs[i]);
					}
				}*/
				String[] strss = StringOrderUtils.outDisOrderArray(projectType, listnum);
				for (int i = 0; i < strss.length; i++) {
					numlistnew.add(strss[i]);
				}
				strs = numlistnew.toArray(new String[numlistnew.size()]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getContentPane().hide();
		initialize();

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
			billListHeadPanel.loadTemplet("1001ZZ100000000022UV");
			billListHeadPanel.setVisible(true);
			billListHeadPanel.setEnabled(true);
			billListHeadPanel.setBounds(10, 55, 380, 380);

			billListHeadPanel.setMultiSelect(true);
			Map<String, Integer> maps = getListDatas();
			List<String> lists = getLastDatas(maps);
			// 查询出listData
			List<SampleAllocatVO> taskvos = new ArrayList<>();
			if (null != lists && lists.size() > 0) {
				for (String string : lists) {
					SampleAllocatVO sampleAllocatVO = new SampleAllocatVO();
					sampleAllocatVO.setSampleallocat(string);
					taskvos.add(sampleAllocatVO);
				}
				/*for (int i = 0; i < 25; i++) {
					SampleAllocatVO sampleAllocatVO = new SampleAllocatVO();
					sampleAllocatVO.setSampleallocat("String"+i);
					taskvos.add(sampleAllocatVO);
				}*/
			}
			billListHeadPanel.setBodyValueVO(taskvos
					.toArray(new SampleAllocatVO[0]));
			SampleAllocatVO[] bodyValueVOs = (SampleAllocatVO[]) billListHeadPanel
					.getBodyBillModel().getBodyValueVOs(
							"nc.vo.qcco.task.SampleAllocatVO");
			List<Integer> indexlist = new ArrayList<>();
			if (strs != null && strs.length > 0) {
				for (String str : strs) {
					for (int i = 0; i < bodyValueVOs.length; i++) {
						if (str.equals(bodyValueVOs[i].getSampleallocat())) {
							indexlist.add(i);
						}
					}
				}
				for (Integer integer : indexlist) {

					billListHeadPanel.getBodyBillModel()
							.setRowState(integer, 4);
				}
			}
			// billListHeadPanel.getBodyBillModel().addRowAttributeObject(2,
			// key, o);
			billListHeadPanel.setAutoscrolls(true);
		}
		return billListHeadPanel;
	}

	@Override
	public boolean beforeEdit(BillEditEvent paramBillEditEvent) {
		// TODO Auto-generated method stub
		return false;
	}

	private List<String> getLastDatas(Map<String, Integer> maps) {
		List<String> list = new ArrayList<String>();
		for (String key : maps.keySet()) {
			Integer quantity = Integer.valueOf(maps.get(key));
			if (key.equalsIgnoreCase("A")) {
				if (quantity > 0) {
					for (int x = 1; x <= quantity; x++) {
						list.add(key + String.valueOf(x));
					}
				}
			}
		}
		for (String key : maps.keySet()) {
			Integer quantity = Integer.valueOf(maps.get(key));
			if (key.equalsIgnoreCase("B")) {
				if (quantity > 0) {
					for (int x = 1; x <= quantity; x++) {
						list.add(key + String.valueOf(x));
					}
				}
			}
		}
		for (String key : maps.keySet()) {
			Integer quantity = Integer.valueOf(maps.get(key));
			if (key.equalsIgnoreCase("C")) {
				if (quantity > 0) {
					for (int x = 1; x <= quantity; x++) {
						list.add(key + String.valueOf(x));
					}
				}
			}
		}
		for (String key : maps.keySet()) {
			Integer quantity = Integer.valueOf(maps.get(key));
			if (key.equalsIgnoreCase("D")) {
				if (quantity > 0) {
					for (int x = 1; x <= quantity; x++) {
						list.add(key + String.valueOf(x));
					}
				}
			}
		}
		
		//java.util.Collections.sort(list);
		return list;
	}

	private Map<String, Integer> getListDatas() {
		IUAPQueryBS iUAPQueryBS = (IUAPQueryBS) NCLocator.getInstance().lookup(
				IUAPQueryBS.class.getName());

		Map<String, Integer> maps = new HashMap<String, Integer>();

		try {
			List<Map<String, Object>> custlist = (List<Map<String, Object>>) iUAPQueryBS
					.executeQuery(
							"select "
									+ "samplegroup.nc_sample_name, commission.QUANTITY from QC_COMMISSION_B commission left join NC_SAMPLE_GROUP samplegroup "
									+ "on commission.PK_SAMPLEGROUP = samplegroup.pk_sample_group where "
									+ "commission.PK_COMMISSION_H='"
									+ pk_commission_h
									+ "' order by samplegroup.nc_sample_code",
							new MapListProcessor());

			if (null != custlist && custlist.size() > 0) {
				for (Map<String, Object> map : custlist) {
					maps.put(map.get("nc_sample_name").toString(), Integer
							.parseInt(String.valueOf(map.get("quantity"))));
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
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maps;
	}

}
