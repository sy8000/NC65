package nc.ui.qcco.commission.ace.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.ValueChangedListener;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillListPanel;
import nc.vo.pub.BusinessException;
import nc.vo.qcco.task.TaskBVO;

public class TaskBodyPanel extends UIDialog implements nc.ui.pub.bill.BillEditListener2, ActionListener,
		PropertyChangeListener, ValueChangedListener {
	private BillListPanel billListHeadPanel = null;
	private static final long serialVersionUID = 1L;
	private UIPanel bnPanel = null;
	private UIButton okButton = null;
	private UIButton cancelButton = null;

	private JPanel ivjUIDialogContentPane1;

	private JPanel ivjUIDialogContentPane = null;
	private String pk_commission_h = null;
	
	private Set<String> urlSet = new HashSet<>();
	


	@SuppressWarnings("deprecation")
	public TaskBodyPanel(String pk_commission_h) throws DAOException {
		this.pk_commission_h = pk_commission_h;
		urlSet = new HashSet<>();
		initialize();
	}

	private void initialize() {
		this.setTitle("请选择单项任务");
		this.setLayout(new BorderLayout());
		this.setSize(new Dimension(600, 520));
		this.setContentPane(getUIDialogContentPane());
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
			this.bnPanel.setPreferredSize(new Dimension(380, 50));
			this.bnPanel.add(getOkButton(), null);
			this.bnPanel.add(getCancelButton(), null);
		}
		return this.bnPanel;
	}

	private UIButton getOkButton() {
		if (this.okButton == null) {
			this.okButton = new UIButton();
			this.okButton.setBounds(new Rectangle(50, 5, 30, 20));
			this.okButton.setText("预览");
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
		urlSet = new HashSet<>();
		if (e.getSource().equals(getOkButton())) {
			setResult(UIDialog.ID_OK);
			TaskBVO[] RefValueVOsAllocatVO = (TaskBVO[]) getBillListHeadPanel().getBodyBillModel()
					.getBodySelectedVOs("nc.vo.qcco.task.TaskBVO");

			if (RefValueVOsAllocatVO != null && RefValueVOsAllocatVO.length > 0) {
				for (TaskBVO taskBodyVO : RefValueVOsAllocatVO) {
					if(taskBodyVO!=null){
						urlSet.add(taskBodyVO.getDef2());
					}
				}
			}
			if(urlSet!=null && urlSet.size() > 0){
				for(String url : urlSet){
					WebBrowser.open(url, getTitle());
				}
			}
			//dispose();
		} else if (e.getSource().equals(getCancelButton())) {
			setResult(UIDialog.ID_CANCEL);
			dispose();
		} 
	}


	private JPanel getUIDialogContentPane1() {
		if (ivjUIDialogContentPane1 == null) {
			ivjUIDialogContentPane1 = new javax.swing.JPanel();
			ivjUIDialogContentPane1.setName("ivjUIDialogContentPane1");
			ivjUIDialogContentPane1.setLayout(null);
			getUIDialogContentPane1().add(getBillListHeadPanel());
		}
		return ivjUIDialogContentPane1;
	}


	private void handleException(Throwable exception) {
		Logger.error("--------- 未捕捉到的异常 ---------");
		MessageDialog.showErrorDlg(this, null, exception.getMessage());
		exception.printStackTrace();
	}

	/*
	 * 获取模版
	 */
	private BillListPanel getBillListHeadPanel() {
		if (billListHeadPanel == null) {
			billListHeadPanel = new BillListPanel();
			billListHeadPanel.loadTemplet("0001ZZ1000000000E26H");
			billListHeadPanel.setVisible(true);
			billListHeadPanel.setEnabled(true);
			billListHeadPanel.setBounds(10, 20, 583, 430);

			billListHeadPanel.setMultiSelect(true);
			List<TaskBVO> lists = getListDatas();

			billListHeadPanel.setBodyValueVO(lists.toArray(new TaskBVO[0]));

			billListHeadPanel.setAutoscrolls(true);
		}
		return billListHeadPanel;
	}

	@Override
	public boolean beforeEdit(BillEditEvent paramBillEditEvent) {
		return false;
	}

	private List<TaskBVO> getListDatas() {
		IUAPQueryBS iUAPQueryBS = (IUAPQueryBS) NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());

		if(this.pk_commission_h!=null){
			String sql = " SELECT "
					+" b.runorder, "
					+" b.taskcode, "
					+" b.testitem, "
					+" r.REPORT_FILE_NAME def2, "
					+" tasks.c_reportsignature_date def1 "
					+" FROM QC_TASK_B b "
					+" inner join QC_TASK_H h on (h.PK_TASK_H = b.PK_TASK_H and h.dr = 0 and h.PK_COMMISSION_H = '"+pk_commission_h+"') "
					+" left join C_PROJ_TASK tasks on (tasks.project = h.BILLno and b.TASKCODE = tasks.task_id) "
					+" left join reports r on r.report_number = tasks.report_number "
					+" WHERE b.dr= 0 ";
			try {
				@SuppressWarnings("unchecked")
				List<TaskBVO> lists = (List<TaskBVO>) iUAPQueryBS.executeQuery(sql,
						new BeanListProcessor(TaskBVO.class));
				return lists;
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}
		return new ArrayList<TaskBVO>();
	}

	@Override
	public void valueChanged(ValueChangedEvent arg0) {
	
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}

	public Set<String> getUrlSet() {
		return urlSet;
	}

	public void setUrlSet(Set<String> urlSet) {
		this.urlSet = urlSet;
	}
	
}
