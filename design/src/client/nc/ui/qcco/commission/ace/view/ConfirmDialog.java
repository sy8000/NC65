package nc.ui.qcco.commission.ace.view;

/**
 * MessageDialog ��Ϣ��ʾ���� ˵��:1.��Ϣ��ʾ��̳�UIDialog,Ϊģ̬��ʾ.
 * 2.������Ϣ��ʾ�����͵��ò�ͬ�ķ���,ǿ�ҽ���ʹ�þ�̬���÷���,���Ƽ��ڿ��ӻ�״̬��������(���ٱ�����Ҫ��ʽ����destroy����).
 * 3.Ԥ��7���������������ť����: OK_OPTION = 0; OK_CANCEL_OPTION = 1; YES_NO_OPTION = 2;
 * YES_NO_CANCEL_OPTION = 3; YES_YESTOALL_NO_CANCEL_OPTION = 4;
 * YES_YESTOALL_NO_NOTOALL_OPTION = 5; YES_YESTOALL_NO_NOTOALL_CANCEL_OPTION =
 * 6;
 * 
 * 4.��ֱ����showXXXDlg()��ȡ���ذ�ť�������ж�: ��ť�����Ѿ���UIDialog�ж���: ID_OK = 1; ID_CANCEL = 2;
 * ID_YES = 4; ID_NO = 8; MessageDialog����: ID_YESTOALL=16; ID_NOTOALL=32;
 * 
 * 5.�̳�ToftPanel�����ֱ��ʹ��ToftPanel�е�showXXXMessage������ʾ��Ϣ,���ַ�ʽĬ�ϱ���---����,����,ѯ��.
 * 6.����Ĭ�ϰ�ť�ķ������ã���ȱʡָ��һ����ť ʾ��:��ToftPanel������,����Ĭ����Ϣ����:
 * showHintMessage("��������..."); //��״̬����ʾ ...... showErrorMessage("�����ظ�"+"!");
 * ...... showWarningMessage("��ֹ����"+"!"); ......
 * if(showYesNoMessage("ȷ��ɾ����?")==UIDialog.ID_YES){ .................. };
 * ������������UIDialog��������,�ɼ�����Ϣ�����硰NCϵͳ��,��Ϣ����ΪnullʱΪĬ����Ϣ����---����,����,ѯ��:
 * MessageDialog.showHintDlg(this,"NCϵͳ","��������...");//����һ�Ի��� ......
 * MessageDialog.showErrorDlg(this,null,"�����ظ�"+"!"); ......
 * MessageDialog.showWarningDlg(this,"NCϵͳ","��ֹ����"+"!"); ......
 * if(MessageDialog.showYesNoDlg(this,"NCϵͳ","ȷ��ɾ����?")==UIDialog.ID_YES){
 * .................. } //showInputDlg����������ַ���(Object������),null��ʾCancel���� Object
 * s = MessageDialog.showInputDlg(this,"NCϵͳ", "��������",
 * getUITextField().getText()); if (s != null)
 * getUITextField().setText(s.toString()); ..................
 * 
 * //showSelectDlg����ѡ��Ķ���(Object������),null��ʾCancel���� Object[]
 * value={"ֵһ","ֵ��","ֵ��"}; Object s = MessageDialog.showSelectDlg(this,"NCϵͳ",
 * "����ѡ������",value,0);//0��ʾ����MessageDialogĬ����ʾ����(Ϊ2) if (s != null)
 * getUITextField().setText(s.toString()); ..................
 * 
 * ����:����
 */
// 2002-10-25, ����getUIComboBox_Input��λ��:setBounds(29, 72, 200, 22);
// �Ը����ؼ���λ������.�޸���:����
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Set;

import nc.bs.logging.Logger;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIButtonLayout;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIManager;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UIScrollPane;
import nc.ui.pub.beans.UITextArea;
import nc.ui.pub.beans.UITextField;
import nc.ui.pub.beans.event.IMessageHistory;
import nc.ui.pub.beans.event.MessageHistoryEvent;
import nc.ui.pub.style.Style;
import nc.uitheme.ui.ThemeResourceCenter;

public class ConfirmDialog extends nc.ui.pub.beans.UIDialog implements java.awt.event.KeyListener,
		java.awt.event.ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1059964896682866708L;

	private nc.ui.pub.beans.UIButton ivjUIButton1 = null;

	private nc.ui.pub.beans.UIButton ivjUIButton2 = null;

	private nc.ui.pub.beans.UIButton ivjUIButton3 = null;

	private javax.swing.JPanel ivjUIDialogContentPane = null;

	private nc.ui.pub.beans.UITextArea ivjUITextMessage = null;

	private int m_nType = OK_OPTION;

	// private static MessageDialog m_singleton = null;

	public final static int WARNING_ICON = 0;

	public final static int ERROR_ICON = 1;

	public final static int HINT_ICON = 2;

	public final static int QUESTION_ICON = 3;

	//
	public final static int OK_OPTION = 0;

	public final static int OK_CANCEL_OPTION = 1;

	public final static int YES_NO_OPTION = 2;

	public final static int YES_NO_CANCEL_OPTION = 3;

	public final static int YES_YESTOALL_NO_CANCEL_OPTION = 4;

	public final static int YES_YESTOALL_NO_NOTOALL_OPTION = 5;

	public final static int YES_YESTOALL_NO_NOTOALL_CANCEL_OPTION = 6;

	public final static int CONFIRM_REJECT_PREVIEW = 7;

	public final static int CONFIRM_PREVIEW = 8;
	//
	public final static int ID_YESTOALL = 16;

	public final static int ID_NOTOALL = 32;

	public final static int ID_CLOSE = 64;
	//
	public final static int ID_CONFIRM = 128;
	public final static int ID_REJECT = 256;
	public final static int ID_PREVIEW = 512;

	public final static int TEXTMAXLENGTH = 20;

	private nc.ui.pub.beans.UILabel ivjUILabelIcon = null;

	IvjEventHandler ivjEventHandler = new IvjEventHandler();

	protected java.lang.Object inputValue;

	private nc.ui.pub.beans.UITextField ivjUITextField_Input = null;

	private java.lang.String sShortKey;

	private nc.ui.pub.beans.UIComboBox ivjUIComboBox_Input = null;

	private nc.ui.pub.beans.UIScrollPane ivjUIScrollPane_Message = null;

	private nc.ui.pub.beans.UIPanel ivjUIPanel_Buttons = null;

	private nc.ui.pub.beans.UIPanel ivjUIPanel_Icon = null;

	private nc.ui.pub.beans.UIPanel ivjUIPanel_Message = null;

	private nc.ui.pub.beans.UIButton ivjUIButton4 = null;

	private nc.ui.pub.beans.UIButton ivjUIButton5 = null;

	private boolean keyListenerDisabled = false;

	private int focusbutton = -1; // ���㰴ť���

	public final static String TEXT_STR = "TextStr";

	public final static String TEXT_DATE = "TextDate";

	public final static String TEXT_TIME = "TextTime";

	public final static String TEXT_DATETIME = "TextDateTime";

	public final static String TEXT_INT = "TextInt";

	public final static String TEXT_DBL = "TextDbl";

	public final static int BOUND_WIDTH_LITTLE = 236;

	public final static int BOUND_WIDTH_MIDDLE = 286;

	public final static int BOUND_HEIGHT_LITTLE = 100;

	public final static int BOUND_HEIGHT_MIDDLE = 170;

	public final static int SIZE_WIDTH_LITTLE = 320;

	public final static int SIZE_WIDTH_MIDDLE = 400;// 450

	public final static int SIZE_HEIGHT_LITTLE = 160;

	public final static int SIZE_HEIGHT_MIDDLE = 250;// 280

	public final static int SIZE_CHOOSER_LENGTH = 1500;// ʹ�����ִ��ڴ�С�ĳ��Ƚ���,FontMetrics.stringWidth()

	public static int bound_x = 11;

	public static int bound_y = 6;

	public static int bound_width = BOUND_WIDTH_LITTLE;

	public static int bound_height = BOUND_HEIGHT_LITTLE;

	public static int size_width = SIZE_WIDTH_LITTLE;

	public static int size_height = SIZE_HEIGHT_LITTLE;

	public static java.awt.FontMetrics fm = null;

	public static int[] m_btnkeys = new int[] { ID_OK, ID_NO, ID_CANCEL, ID_YESTOALL, ID_NOTOALL };

	private static Color dlgContentBGColor = ThemeResourceCenter.getInstance().getColor(
			"themeres/dialog/dialogResConf", "dialogContentPane.backgroundColor");

	private static String strURL = "";
	
	public static TaskBodyPanel urlChoosePanel = null;
	

	public void setFocusButton(int nbutton) {
		focusbutton = nbutton;

	}

	public int getFocusButton() {
		return focusbutton;
	}

	class IvjEventHandler implements java.awt.event.ActionListener {

		public void actionPerformed(java.awt.event.ActionEvent e) {

			if (e.getSource() == ConfirmDialog.this.getUIButton1())
				connEtoC1(e);
			if (e.getSource() == ConfirmDialog.this.getUIButton2())
				connEtoC2(e);
			if (e.getSource() == ConfirmDialog.this.getUIButton3())
				connEtoC3(e);
			if (e.getSource() == ConfirmDialog.this.getUIButton4())
				connEtoC4(e);
			if (e.getSource() == ConfirmDialog.this.getUIButton5())
				connEtoC5(e);
		};
	};

	private nc.ui.pub.beans.UICheckBox ivjchkShow = null;

	// fgj2002-09-17
	private static boolean m_isCloseWindow = true;

	private UIPanel adjustpanel = null;

	/**
	 * UIOptionPane ������ע��.
	 * 
	 * @deprecated
	 */
	public ConfirmDialog() {
		super();
		initialize();
	}

	/**
	 * UIOptionPane ������ע��.
	 * 
	 * @param parent
	 *            java.awt.Container
	 * @deprecated
	 */
	public ConfirmDialog(java.awt.Container parent) {
		super(parent);
		initialize();
	}

	/**
	 * UIOptionPane ������ע��.
	 * 
	 * @param parent
	 *            java.awt.Container
	 * @param title
	 *            java.lang.String
	 * @deprecated
	 */
	public ConfirmDialog(java.awt.Container parent, String title) {
		super(parent, title);
		initialize();
	}

	/**
	 * UIOptionPane ������ע��.
	 * 
	 * @param owner
	 *            java.awt.Frame
	 * @deprecated
	 */
	public ConfirmDialog(java.awt.Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * UIOptionPane ������ע��.
	 * 
	 * @param owner
	 *            java.awt.Frame
	 * @param title
	 *            java.lang.String
	 * @deprecated
	 */
	public ConfirmDialog(java.awt.Frame owner, String title) {
		super(owner, title);
		initialize();
	}

	private void btn1_ActionPerformed() {

		switch (m_nType) {

		case YES_NO_OPTION:
		case YES_NO_CANCEL_OPTION:
		case YES_YESTOALL_NO_NOTOALL_OPTION:
		case YES_YESTOALL_NO_CANCEL_OPTION:
		case YES_YESTOALL_NO_NOTOALL_CANCEL_OPTION:
			setResult(ID_YES);
			break;
		case CONFIRM_REJECT_PREVIEW:
		case CONFIRM_PREVIEW:
			setResult(ID_CONFIRM);
			break;
		case OK_OPTION:
		case OK_CANCEL_OPTION:
		default:
			setResult(ID_OK);
		}
		// this.setVisible(false);
		if (getUITextField_Input().isVisible())
			setInputValue(getUITextField_Input().getText());
		else if (getUIComboBox_Input().isVisible())
			setInputValue(getUIComboBox_Input().getSelectedItem());
		else
			setInputValue(null);
		close();
		return;
	}

	private void btn2_ActionPerformed() {
		if (m_nType == CONFIRM_REJECT_PREVIEW) {
			setResult(ID_REJECT);
		} else {
			setResult(ID_NO);
		}
		setInputValue(getUITextField_Input().getText());
		close();
		return;
	}

	private void btn3_ActionPerformed() {
		if (m_nType == CONFIRM_REJECT_PREVIEW || m_nType == CONFIRM_PREVIEW) {
			if(urlChoosePanel!=null){
				if(urlChoosePanel.showModal()==1){
					;
				}else{
					return ;
				}
			}else{
				WebBrowser.open(strURL, getTitle());
			}
			
		} else {
			setResult(ID_CANCEL);
			setInputValue(null);
			close();
		}
		return;
	}

	private void btn4_ActionPerformed() {
		if (m_nType == CONFIRM_REJECT_PREVIEW || m_nType == CONFIRM_PREVIEW) {
			setResult(ID_CANCEL);
		} else {
			setResult(ID_YESTOALL);
		}
		setInputValue(null);
		close();
		return;
	}

	private void btn5_ActionPerformed() {
		setResult(ID_NOTOALL);
		setInputValue(null);
		close();
		return;
	}

	/**
	 * connEtoC1: (UIButton1.action.actionPerformed(java.awt.event.ActionEvent)
	 * --> UIOptionPane.btn1_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
	 * 
	 * @param arg1
	 *            java.awt.event.ActionEvent
	 */
	/* ����:�˷�������������. */
	private void connEtoC1(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.btn1_ActionPerformed();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoC2: (UIButton2.action.actionPerformed(java.awt.event.ActionEvent)
	 * --> UIOptionPane.btn2_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
	 * 
	 * @param arg1
	 *            java.awt.event.ActionEvent
	 */
	/* ����:�˷�������������. */
	private void connEtoC2(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.btn2_ActionPerformed();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoC3: (UIButton3.action.actionPerformed(java.awt.event.ActionEvent)
	 * --> UIOptionPane.btn3_ActionPerformed(Ljava.awt.event.ActionEvent;)V)
	 * 
	 * @param arg1
	 *            java.awt.event.ActionEvent
	 */
	/* ����:�˷�������������. */
	private void connEtoC3(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.btn3_ActionPerformed();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoC4: (UIButton4.action.actionPerformed(java.awt.event.ActionEvent)
	 * --> MessageDialog.btn4_ActionPerformed()V)
	 * 
	 * @param arg1
	 *            java.awt.event.ActionEvent
	 */
	/* ����:�˷�������������. */
	private void connEtoC4(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.btn4_ActionPerformed();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * connEtoC5: (UIButton5.action.actionPerformed(java.awt.event.ActionEvent)
	 * --> MessageDialog.btn5_ActionPerformed()V)
	 * 
	 * @param arg1
	 *            java.awt.event.ActionEvent
	 */
	/* ����:�˷�������������. */
	private void connEtoC5(java.awt.event.ActionEvent arg1) {
		try {
			// user code begin {1}
			// user code end
			this.btn5_ActionPerformed();
			// user code begin {2}
			// user code end
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {3}
			// user code end
			handleException(ivjExc);
		}
	}

	/**
	 * ���� chkShow ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UICheckBox
	 */
	/* ����:�˷�������������. */
	private UICheckBox getchkShow() {
		if (ivjchkShow == null) {
			try {
				ivjchkShow = new nc.ui.pub.beans.UICheckBox();
				ivjchkShow.setName("chkShow");
				ivjchkShow.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000089")/*
																											 * @
																											 * res
																											 * "������ʾ"
																											 */);
				ivjchkShow.setBounds(230, 72, 84, 22);
				// user code begin {1}
				ivjchkShow.setVisible(false);
				if (m_isCloseWindow) {
					ivjchkShow.setSelected(false);
				} else {
					ivjchkShow.setSelected(true);
				}
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjchkShow;
	}

	/**
	 * 
	 * ��������:(2001-4-27 14:53:13)
	 * 
	 * @return java.lang.Object
	 */
	public java.lang.Object getInputValue() {
		return inputValue;
	}

	/**
	 * 
	 * ��������:(2001-5-18 15:59:16)
	 * 
	 * @return java.lang.String
	 */
	public String getShortKey() {
		return sShortKey;
	}

	/**
	 * 
	 * ��������:(2000-11-10 14:45:54)
	 * 
	 * @return MessageDialog
	 */
	public static ConfirmDialog getSingleton() {
		return new ConfirmDialog(null);
	}

	/**
	 * 
	 * ��������:(2000-11-10 14:45:54)
	 * 
	 * @return MessageDialog
	 */
	public static ConfirmDialog getSingleton(Container parent) {
		return new ConfirmDialog(parent);
	}

	/**
	 * ���� UIButton1 ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UIButton
	 */
	/* ����:�˷�������������. */
	private UIButton getUIButton1() {
		if (ivjUIButton1 == null) {
			ivjUIButton1 = new nc.ui.pub.beans.UIButton();
			ivjUIButton1.setName("UIButton1");
			ivjUIButton1.setIButtonType(0/** JavaĬ��(�Զ���) */
			);
			ivjUIButton1.setPreferredSize(new java.awt.Dimension(72, 22));
			ivjUIButton1.setText("UIButton");
			ivjUIButton1.setMargin(new java.awt.Insets(2, 2, 2, 2));
		}
		return ivjUIButton1;
	}

	/**
	 * ���� UIButton2 ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UIButton
	 */
	/* ����:�˷�������������. */
	private UIButton getUIButton2() {
		if (ivjUIButton2 == null) {
			ivjUIButton2 = new nc.ui.pub.beans.UIButton();
			ivjUIButton2.setName("UIButton2");
			ivjUIButton2.setIButtonType(0/** JavaĬ��(�Զ���) */
			);
			ivjUIButton2.setPreferredSize(new java.awt.Dimension(72, 22));
			ivjUIButton2.setText("UIButton");
			ivjUIButton2.setMargin(new java.awt.Insets(2, 2, 2, 2));
		}
		return ivjUIButton2;
	}

	/**
	 * ���� UIButton3 ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UIButton
	 */
	/* ����:�˷�������������. */
	private UIButton getUIButton3() {
		if (ivjUIButton3 == null) {
			ivjUIButton3 = new nc.ui.pub.beans.UIButton();
			ivjUIButton3.setName("UIButton3");
			ivjUIButton3.setIButtonType(0/** JavaĬ��(�Զ���) */
			);
			ivjUIButton3.setPreferredSize(new java.awt.Dimension(72, 22));
			ivjUIButton3.setText("UIButton");
			ivjUIButton3.setMargin(new java.awt.Insets(2, 2, 2, 2));
		}
		return ivjUIButton3;
	}

	/**
	 * ���� UIButton4 ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UIButton
	 */
	/* ����:�˷�������������. */
	private UIButton getUIButton4() {
		if (ivjUIButton4 == null) {
			ivjUIButton4 = new nc.ui.pub.beans.UIButton();
			ivjUIButton4.setName("UIButton4");
			ivjUIButton4.setIButtonType(0/** JavaĬ��(�Զ���) */
			);
			ivjUIButton4.setPreferredSize(new java.awt.Dimension(72, 22));
			ivjUIButton4.setText("UIButton4");
			ivjUIButton4.setMargin(new java.awt.Insets(2, 2, 2, 2));
			ivjUIButton4.setVisible(false);
		}
		return ivjUIButton4;
	}

	/**
	 * ���� UIButton5 ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UIButton
	 */
	/* ����:�˷�������������. */
	private UIButton getUIButton5() {
		if (ivjUIButton5 == null) {
			ivjUIButton5 = new nc.ui.pub.beans.UIButton();
			ivjUIButton5.setName("UIButton5");
			ivjUIButton5.setIButtonType(0/** JavaĬ��(�Զ���) */
			);
			ivjUIButton5.setPreferredSize(new java.awt.Dimension(72, 22));
			ivjUIButton5.setText("UIButton5");
			ivjUIButton5.setMargin(new java.awt.Insets(2, 2, 2, 2));
			ivjUIButton5.setVisible(false);
		}
		return ivjUIButton5;
	}

	/**
	 * ���� UITextField1 ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UIComboBox
	 */
	/* ����:�˷�������������. */
	private UIComboBox getUIComboBox_Input() {

		if (ivjUIComboBox_Input == null) {
			try {
				ivjUIComboBox_Input = new nc.ui.pub.beans.UIComboBox();
				ivjUIComboBox_Input.setName("UIComboBox_Input");
				ivjUIComboBox_Input.setPreferredSize(new java.awt.Dimension(200, 22));
				ivjUIComboBox_Input.setBounds(29, 172, 260, 22);
				ivjUIComboBox_Input.setVisible(false);
				// user code begin {1}
				ivjUIComboBox_Input.setMaximumRowCount(2);
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjUIComboBox_Input;
	}

	/**
	 * ���� UIDialogContentPane ����ֵ.
	 * 
	 * @return javax.swing.JPanel
	 */
	/* ����:�˷�������������. */
	private javax.swing.JPanel getUIDialogContentPane() {
		if (ivjUIDialogContentPane == null) {
			try {
				ivjUIDialogContentPane = new javax.swing.JPanel();
				ivjUIDialogContentPane.setName("UIDialogContentPane");
				ivjUIDialogContentPane.setOpaque(true);
				ivjUIDialogContentPane.setBackground(UIManager.getColor("MessageDialog.bgcolor"));

				ivjUIDialogContentPane.setLayout(new java.awt.BorderLayout());
				UIPanel pnl = new UIPanel();
				pnl.setMinimumSize(new Dimension(50, 20));
				pnl.setOpaque(false);
				ivjUIDialogContentPane.add(pnl, "North");

				getUIDialogContentPane().add(getUIPanel_Icon(), "West");

				getUIDialogContentPane().add(getUIPanel_Buttons(), "South");

				// zsb+:������Ϣ���ļ��
				javax.swing.JPanel panCenter = new javax.swing.JPanel();
				panCenter.setOpaque(false);
				panCenter.setLayout(new java.awt.GridBagLayout());
				java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
				gbc.fill = GridBagConstraints.BOTH;
				gbc.insets = new java.awt.Insets(0, 5, 8, 8);
				gbc.weightx = 1;
				gbc.weighty = 1;
				gbc.gridy = 0;
				panCenter.add(getUIPanel_Message(), gbc);
				getUIDialogContentPane().add(panCenter, "Center");
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjUIDialogContentPane;
	}

	/**
	 * ���� UILabel1 ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UILabel
	 */
	/* ����:�˷�������������. */
	private UILabel getUILabelIcon() {
		if (ivjUILabelIcon == null) {
			try {
				ivjUILabelIcon = new nc.ui.pub.beans.UILabel();
				ivjUILabelIcon.setName("UILabelIcon");
				ivjUILabelIcon.setText("");
				ivjUILabelIcon.setBounds(20, 5, 32, 32);
				ivjUILabelIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
				ivjUILabelIcon.setILabelType(0/** JavaĬ��(�Զ���) */
				);
				ivjUILabelIcon.setIcon(null);
				ivjUILabelIcon.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
				ivjUILabelIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjUILabelIcon;
	}

	/**
	 * ���� UIPanel2 ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UIPanel
	 */
	/* ����:�˷�������������. */
	private UIPanel getUIPanel_Buttons() {
		if (ivjUIPanel_Buttons == null) {
			try {
				// zsb update:
				ivjUIPanel_Buttons = new nc.ui.pub.beans.UIPanel() {
					/**
					 * 
					 */
					private static final long serialVersionUID = -8555340372138760844L;

					public void paint(java.awt.Graphics g) {
						super.paint(g);
						// �����滭һ������
						g.setColor(UIManager.getColor("MessageDialog.linecolor"));
						g.drawLine(5, 0, getWidth() - 5, 0);
						// ���������滭һ������
						g.setColor(java.awt.Color.white);
						g.drawLine(5, 1, getWidth() - 5, 1);
					}
				};
				ivjUIPanel_Buttons.setName("UIPanel_Buttons");
				ivjUIPanel_Buttons.setPreferredSize(new java.awt.Dimension(0, 40));
				// ivjUIPanel_Buttons.setBackground(UIManager.getColor("MessageDialog.bgcolor"));
				ivjUIPanel_Buttons.setLayout(getUIPanel_ButtonsUIButtonLayout());
				ivjUIPanel_Buttons.setOpaque(false);
				getUIPanel_Buttons().add(getUIButton1(), getUIButton1().getName());
				getUIPanel_Buttons().add(getUIButton4(), getUIButton4().getName());
				getUIPanel_Buttons().add(getUIButton2(), getUIButton2().getName());
				getUIPanel_Buttons().add(getUIButton5(), getUIButton5().getName());
				getUIPanel_Buttons().add(getUIButton3(), getUIButton3().getName());
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjUIPanel_Buttons;
	}

	/**
	 * ���� UIPanel_ButtonsUIButtonLayout ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UIButtonLayout
	 */
	/* ����:�˷�������������. */
	private UIButtonLayout getUIPanel_ButtonsUIButtonLayout() {
		nc.ui.pub.beans.UIButtonLayout ivjUIPanel_ButtonsUIButtonLayout = null;
		try {
			/* �������� */
			ivjUIPanel_ButtonsUIButtonLayout = new nc.ui.pub.beans.UIButtonLayout(true, 0, -1, 10, -1, -1);
			ivjUIPanel_ButtonsUIButtonLayout.setGap(8);
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
		;
		return ivjUIPanel_ButtonsUIButtonLayout;
	}

	/**
	 * ���� UIPanel1 ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UIPanel
	 */
	/* ����:�˷�������������. */
	private UIPanel getUIPanel_Icon() {
		if (ivjUIPanel_Icon == null) {
			try {
				ivjUIPanel_Icon = new nc.ui.pub.beans.UIPanel();
				ivjUIPanel_Icon.setName("UIPanel_Icon");
				ivjUIPanel_Icon.setPreferredSize(new java.awt.Dimension(60, 0));
				ivjUIPanel_Icon.setOpaque(false);
				ivjUIPanel_Icon.setLayout(null);
				getUIPanel_Icon().add(getUILabelIcon());
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjUIPanel_Icon;
	}

	/**
	 * ���� UIPanel3 ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UIPanel
	 */
	/* ����:�˷�������������. */
	private UIPanel getUIPanel_Message() {
		if (ivjUIPanel_Message == null) {
			try {
				ivjUIPanel_Message = new UIPanel();
				ivjUIPanel_Message.setName("UIPanel_Message");
				ivjUIPanel_Message.setPreferredSize(new java.awt.Dimension(bound_width + bound_x + 5, bound_height
						+ bound_y + 5));
				ivjUIPanel_Message.setLayout(new java.awt.BorderLayout());
				// ivjUIPanel_Message.setLayout(null);
				ivjUIPanel_Message.setOpaque(false);
				ivjUIPanel_Message.add(getAdjustPanel(), "North");
				ivjUIPanel_Message.add(getUIScrollPane_Message(), "Center");
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjUIPanel_Message;
	}

	private UIPanel getAdjustPanel() {
		if (adjustpanel == null) {
			adjustpanel = new UIPanel();
			adjustpanel.setOpaque(false);
			adjustpanel.setPreferredSize(new Dimension(200, 5));
		}
		return adjustpanel;
	}

	/**
	 * ���� UIScrollPane1 ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UIScrollPane
	 */
	/* ����:�˷�������������. */
	private UIScrollPane getUIScrollPane_Message() {
		if (ivjUIScrollPane_Message == null) {
			try {
				ivjUIScrollPane_Message = new nc.ui.pub.beans.UIScrollPane();
				// zsb+:ȥ��border
				ivjUIScrollPane_Message.setBorder(null);
				ivjUIScrollPane_Message.setOpaque(false);
				ivjUIScrollPane_Message.setName("UIScrollPane_Message");
				ivjUIScrollPane_Message.setPreferredSize(new java.awt.Dimension(bound_width, bound_height));
				ivjUIScrollPane_Message
						.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				ivjUIScrollPane_Message
						.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				ivjUIScrollPane_Message.setBounds(bound_x, bound_y, bound_width, bound_height);
				getUIScrollPane_Message().setViewportView(getUITextMessage());
				getUIScrollPane_Message().setBorder(null);
			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjUIScrollPane_Message;
	}

	/**
	 * �������
	 */
	public void ScrollToTop() {
		getUITextMessage().setCaretPosition(0);
		// getUITextMessage().scrollRectToVisible(new Rectangle(0,0,
		// getUITextMessage().getWidth(),getUITextMessage().getHeight()));
	}

	/**
	 * ���� UITextField1 ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UITextField
	 */
	/* ����:�˷�������������. */
	private UITextField getUITextField_Input() {
		if (ivjUITextField_Input == null) {
			try {
				ivjUITextField_Input = new nc.ui.pub.beans.UITextField();

				// zsb+:
				// ivjUITextField_Input.setMargin(new java.awt.Insets(2,1,8,8));

				ivjUITextField_Input.setName("UITextField_Input");
				ivjUITextField_Input.setPreferredSize(new java.awt.Dimension(220, 22));
				ivjUITextField_Input.setBounds(86, bound_y + bound_height, 200, 22);
				ivjUITextField_Input.setVisible(true);
				// user code begin {1}
				ivjUITextField_Input.setVisible(false);
				ivjUITextField_Input.setBounds(29, 20, 260, 22);
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjUITextField_Input;
	}

	/**
	 * ���� UITextMessage ����ֵ.
	 * 
	 * @return nc.ui.pub.beans.UITextArea
	 */
	/* ����:�˷�������������. */
	private UITextArea getUITextMessage() {
		if (ivjUITextMessage == null) {
			try {
				// class MessageShowTextUI extends BasicTextAreaUI
				// {
				// protected void paintBackground(Graphics g) {
				// g.setColor(UIManager.getColor("MessageDialog.bgcolor"));
				// g.fillRect(0, 0, getComponent().getWidth(),
				// getComponent().getHeight());
				// }
				// }
				ivjUITextMessage = new nc.ui.pub.beans.UITextArea();
				ivjUITextMessage.setName("UITextMessage");
				ivjUITextMessage.setLineWrap(true);
				ivjUITextMessage.setTabSize(4);
				ivjUITextMessage.setWrapStyleWord(true);
				ivjUITextMessage.setText("");
				ivjUITextMessage.setColumns(36);
				ivjUITextMessage.setRows(0);
				ivjUITextMessage.setMargin(new java.awt.Insets(2, 4, 2, 4));
				ivjUITextMessage.setBounds(0, 0, bound_width - 20, bound_height - 20);
				ivjUITextMessage.setEditable(false);
				// ivjUITextMessage.setBackground(UIManager.getColor("MessageDialog.bgcolor"));
				ivjUITextMessage.setBackground(dlgContentBGColor);
				ivjUITextMessage.setBorder(null);
				ivjUITextMessage.setForeground(Color.black);
				ivjUITextMessage.setOpaque(true);
				ivjUITextMessage.updateUI();
				// ivjUITextMessage.setUI(new MessageShowTextUI());

			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return ivjUITextMessage;
	}

	/**
	 * ÿ�������׳��쳣ʱ������
	 * 
	 * @param exception
	 *            java.lang.Throwable
	 */
	private void handleException(java.lang.Throwable exception) {
		/* ��ȥ���и��е�ע��,�Խ�δ��׽�����쳣��ӡ�� stdout. */

	}

	/**
	 * ��ʼ������
	 * 
	 * @exception java.lang.Exception
	 *                �쳣˵��.
	 */
	/* ����:�˷�������������. */
	private void initConnections() throws java.lang.Exception {
		// user code begin {1}
		// user code end
		getUIButton1().addActionListener(ivjEventHandler);
		getUIButton2().addActionListener(ivjEventHandler);
		getUIButton3().addActionListener(ivjEventHandler);
		getUIButton4().addActionListener(ivjEventHandler);
		getUIButton5().addActionListener(ivjEventHandler);
	}

	/**
	 * ��ʼ����.
	 */
	/* ����:�˷�������������. */
	private void initialize() {
		try {
			// user code begin {1}
			// user code end
			setName("MessageDialog");
			setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			setSize(size_width, size_height);
			setModal(true);
			setResizable(false);
			setContentPane(getUIDialogContentPane());
			initConnections();
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
		// user code begin {2}
		getchkShow().addItemListener(this);
		// user code end
	}

	/**
	 * �Ƿ�رմ��� ��������:(2001-4-27 14:53:13)
	 * 
	 * @return java.lang.Object
	 */
	public static boolean isCloseWindow() {
		return m_isCloseWindow;
	}

	/**
	 * Invoked when an item has been selected or deselected. The code written
	 * for this method performs the operations that need to occur when an item
	 * is selected (or deselected).
	 */
	public void itemStateChanged(java.awt.event.ItemEvent e) {
		if (e.getSource() == getchkShow()) {
			if (getchkShow().isSelected()) {
				m_isCloseWindow = false;
			} else {
				m_isCloseWindow = true;
			}
		}
	}

	/**
	 * 
	 * ��������:(2001-5-18 15:28:58)
	 */
	public void keyPressed(java.awt.event.KeyEvent e) {

		int keyCode = e.getKeyCode();
		int modifiers = e.getModifiers();
		if (keyCode == java.awt.event.KeyEvent.VK_INSERT && getUITextField_Input().getText() != null) {
			btn1_ActionPerformed();
			getUITextField_Input().requestFocus();
			return;
		}
		if (modifiers < 2 && (getUITextField_Input().hasFocus() || getUIComboBox_Input().hasFocus()) || keyCode == 16
				|| keyCode == 17 || keyCode == 18)
			return;
		if (e.getKeyChar() == '?' || e.getKeyChar() == '+')
			return;
		int index = -1;
		if (modifiers < 2) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				// �س���,�ո��
				index = getCurrentFocusButton();
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				if (m_nType == OK_CANCEL_OPTION || m_nType == YES_NO_CANCEL_OPTION
						|| m_nType == YES_YESTOALL_NO_CANCEL_OPTION || m_nType == YES_YESTOALL_NO_NOTOALL_CANCEL_OPTION)
					index = 2;
			} else if (m_nType == CONFIRM_REJECT_PREVIEW || m_nType == CONFIRM_PREVIEW) {
				index = 4;
			} else {
				index = getShortKey().indexOf(String.valueOf(e.getKeyChar()).toUpperCase());
				if (index < 0) // �����������Ҽ�
				{
					if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_RIGHT) {
						moveFocusToNextButton();
					} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_LEFT) {
						moveFocusToPreviousButton();
					}
				}
			}
		} else if ((modifiers & KeyEvent.ALT_MASK) != 0) {
			index = -1;
			switch (keyCode) {
			case KeyEvent.VK_Y:
				index = getShortKey().indexOf("Y");
				break;
			case KeyEvent.VK_O:
				index = getShortKey().indexOf("O");
				break;
			case KeyEvent.VK_N:
				index = getShortKey().indexOf("N");
				break;
			case KeyEvent.VK_C:
				index = getShortKey().indexOf("C");
				break;
			case KeyEvent.VK_A:
				index = getShortKey().indexOf("A");
				break;
			}
		}

		switch (index) {
		case 0: // O��Y
			btn1_ActionPerformed();
			return;
		case 1: // N��C
			btn2_ActionPerformed();
			return;
		case 2: // C
			btn3_ActionPerformed();
			return;
		case 3: // A
			btn4_ActionPerformed();
			return;
		case 4: // O
			btn5_ActionPerformed();
			return;
		}
		return;
	}

	private void moveFocusToNextButton() {
		int index = getCurrentFocusButton();
		setFocusButton(getNextButton(index));
		moveFocusWithinButtons();
	}

	private void moveFocusToPreviousButton() {
		int index = getCurrentFocusButton();
		setFocusButton(getPreviousButton(index));
		moveFocusWithinButtons();
	}

	private int getNextButton(int curbtn) {
		UIButton button = getButtonByIndex(curbtn);
		// ȷ����ǰ��ť�ɼ�
		while (!button.isVisible()) {
			curbtn = getNextButtonIndex(curbtn);
			button = getButtonByIndex(curbtn);
		}
		// �õ���һ����ť
		curbtn = getNextButtonIndex(curbtn);
		button = getButtonByIndex(curbtn);
		while (!button.isVisible()) {
			curbtn = getNextButtonIndex(curbtn);
			button = getButtonByIndex(curbtn);
		}
		return m_btnkeys[curbtn];
	}

	private int getPreviousButton(int curbtn) {
		UIButton button = getButtonByIndex(curbtn);
		// ȷ����ǰ��ť�ɼ�
		while (!button.isVisible()) {
			curbtn = getPreviousButtonIndex(curbtn);
			button = getButtonByIndex(curbtn);
		}
		// �õ���һ����ť
		curbtn = getPreviousButtonIndex(curbtn);
		button = getButtonByIndex(curbtn);
		while (!button.isVisible()) {
			curbtn = getPreviousButtonIndex(curbtn);
			button = getButtonByIndex(curbtn);
		}
		return m_btnkeys[curbtn];
	}

	private int getPreviousButtonIndex(int curbtn) {
		if (curbtn == 0)
			curbtn = m_btnkeys.length - 1;
		else
			curbtn--;
		return curbtn;
	}

	private int getNextButtonIndex(int curbtn) {
		if (curbtn == m_btnkeys.length - 1)
			curbtn = 0;
		else
			curbtn++;
		return curbtn;
	}

	private UIButton getButtonByIndex(int curbtn) {
		UIButton button = getUIButton1();
		switch (curbtn) {
		case 0: // O��Y
			button = getUIButton1();
			break;
		case 1: // N��C
			button = getUIButton2();
			break;
		case 2: // C
			button = getUIButton3();
			break;
		case 3: // A
			button = getUIButton4();
			break;
		case 4: // O
			button = getUIButton5();
			break;
		}
		return button;
	}

	private int getCurrentFocusButton() {
		int index = -1;
		if (getUIButton1().hasFocus())
			return 0;
		else if (getUIButton2().hasFocus())
			return 1;
		else if (getUIButton3().hasFocus())
			return 2;
		else if (getUIButton4().hasFocus())
			index = 3;
		else if (getUIButton5().hasFocus())
			index = 4;
		return index;
	}

	/**
	 * 
	 * ��������:(2001-5-18 15:28:58)
	 */
	public void keyReleased(java.awt.event.KeyEvent e) {
		return;
	}

	/**
	 * 
	 * ��������:(2001-5-18 15:28:58)
	 */
	public void keyTyped(java.awt.event.KeyEvent e) {
	}

	/**
	 * ����ڵ� - ��������ΪӦ�ó�������ʱ,�����������.
	 * 
	 * @param args
	 *            java.lang.String[]
	 */
	public static void main(java.lang.String[] args) {
		try {
			Style.refreshStyle();

		} catch (Throwable exception) {

		}
	}

	/**
	 * 
	 * ��������:(2001-4-27 14:53:13)
	 * 
	 * @param newInputValue
	 *            java.lang.Object
	 */
	public void setInputValue(java.lang.Object newInputValue) {
		inputValue = newInputValue;
	}

	/**
	 * 
	 * ��������:(2001-5-18 15:54:57)
	 * 
	 * @param sKey
	 *            java.lang.String
	 */
	protected void setShortKey(String sKey) {
		sShortKey = sKey;
	}

	public static void showUnknownErrorDlg(Container parent, Exception e) {
		Logger.error(e.getMessage(), e);
		String message = NCLangRes.getInstance().getStrByID("_beans", "UPP_Beans-000104")/* δ֪���� */;
		message += ": " + e.getMessage();
		showErrorDlg(parent, null, message);
	}

	public static int showErrorDlg(Container parent, String title, String error) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("beansv5", "UPPbeansv5-000013")/*
																								 * @
																								 * res
																								 * "������Ϣ"
																								 */;

		}
		dispatchMesasge(parent, MessageHistoryEvent.ERROR, error);
		return showMessageBox(parent, ConfirmDialog.OK_OPTION, ConfirmDialog.ERROR_ICON, title, error, ID_CLOSE);
	}

	public static int showHintDlg(Container parent, String title, String hint) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000053")/*
																							 * @
																							 * res
																							 * "��ʾ"
																							 */;
		}
		dispatchMesasge(parent, MessageHistoryEvent.HINT, hint);
		return showMessageBox(parent, ConfirmDialog.OK_OPTION, ConfirmDialog.HINT_ICON, title, hint, ID_OK);
	}

	// dongdb ++
	public static int showHintsDlg(Container parent, String title, String hint) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000053")/*
																							 * @
																							 * res
																							 * "��ʾ"
																							 */;
		}
		dispatchMesasge(parent, MessageHistoryEvent.HINT, hint);
		return showMessageBox(parent, ConfirmDialog.OK_OPTION, ConfirmDialog.WARNING_ICON, title, hint, ID_OK);
	}

	private static Object showInputBox(Container parent, int type, int icon, String title, String message,
			Object firstValue, int maxLength, int dec, String dataType) {
		// ��ʾ��Ϣ
		// String messageDisplay = StringUtil.recoverWrapLineChar(message);
		// 2014-12-11 ���ڴ��������Ĵ�"d:\\festival"����ʾ�������⣬�޸�Ϊ��Ҫת�崦��
		String messageDisplay = message;
		// if (message != null && !message.trim().equals(""))
		// messageDisplay =
		// nc.ui.pub.ClientEnvironment.getInstance().getModuleLang().getString(message);
		if (messageDisplay == null)
			messageDisplay = "";
		// ��¼��ǰ�ڵ��
		// FIXME
		// ȡ�����ڵ���MessageDialogʱ��ȡ�õ�ǰ���ģ��ţ��ڹر�ʱ�����û�ȥ����UIDialog��û�����ƴ���Ӧͳһ��MainFrame�н��С�����public�ֲ�ʱע�͡�
		// String strModuleCode =
		// nc.ui.pub.ClientEnvironment.getInstance().getModuleCode();
		ConfirmDialog dlg = getSingleton(parent);
		if (dlg.isVisible()) { // �������ʵ������ʹ�þ�������һ��
			dlg = new ConfirmDialog(parent);
		}
		// dlg.setParent(parent);
		if (fm == null)
			fm = dlg.getFontMetrics(new Font("Dialog", Font.PLAIN, 20));
		if (message != null && fm.stringWidth(message) >= SIZE_CHOOSER_LENGTH) {
			size_width = SIZE_WIDTH_MIDDLE;
			size_height = SIZE_HEIGHT_MIDDLE;
			bound_width = BOUND_WIDTH_MIDDLE;
			bound_height = BOUND_HEIGHT_MIDDLE;
		} else {
			size_width = SIZE_WIDTH_LITTLE;
			size_height = SIZE_HEIGHT_LITTLE;
			bound_width = BOUND_WIDTH_LITTLE;
			bound_height = BOUND_HEIGHT_LITTLE;
		}
		dlg.setSize(size_width, size_height);
		dlg.getUIComboBox_Input().setVisible(false);
		dlg.getUIPanel_Message().remove(dlg.getUIComboBox_Input());
		dlg.getUIButton1().requestFocus();
		dlg.getUIButton4().setVisible(false);
		dlg.getUIButton5().setVisible(false);
		//
		javax.swing.JPanel pnl = new javax.swing.JPanel();
		pnl.setLayout(new java.awt.GridLayout(0, 1, 3, 3));
		pnl.setPreferredSize(new Dimension(220, 22));
		pnl.add(dlg.getUITextField_Input());

		dlg.getUIPanel_Message().add(pnl, java.awt.BorderLayout.SOUTH);

		if (maxLength > 0)
			dlg.getUITextField_Input().setMaxLength(maxLength);
		if (dataType != null && dataType.trim().length() != 0 && !dataType.equalsIgnoreCase(TEXT_STR)) {
			if (dataType.equalsIgnoreCase(TEXT_INT)) {
				dlg.getUITextField_Input().setTextType(TEXT_INT);
			} else if (dataType.equalsIgnoreCase(TEXT_DBL)) {
				dlg.getUITextField_Input().setTextType(TEXT_DBL);
				dlg.getUITextField_Input().setNumPoint(dec);
			} else if (dataType.equalsIgnoreCase(TEXT_DATE)) {
				dlg.getUITextField_Input().setTextType(TEXT_DATE);
				dlg.getUITextField_Input().setMaxLength(10);
			} else if (dataType.equalsIgnoreCase(TEXT_TIME)) {
				dlg.getUITextField_Input().setTextType(TEXT_TIME);
				dlg.getUITextField_Input().setMaxLength(8);
			} else if (dataType.equalsIgnoreCase(TEXT_DATETIME)) {
				dlg.getUITextField_Input().setTextType(TEXT_DATETIME);
				dlg.getUITextField_Input().setMaxLength(19);
			}
		}
		dlg.getUITextField_Input().setVisible(true);
		dlg.getUITextField_Input().setText("");
		if (firstValue != null)
			dlg.getUITextField_Input().setText(firstValue.toString());
		if (!dlg.keyListenerDisabled) {
			dlg.addKeyListener(dlg);
			dlg.keyListenerDisabled = true;
		}
		// dlg.removeKeyListener(dlg);
		// ���ð�ť
		dlg.m_nType = type;
		switch (type) {
		case YES_NO_OPTION:
			dlg.getUIButton1().setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000051")/*
																												 * @
																												 * res
																												 * "��"
																												 */);
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000050")/*
																												 * @
																												 * res
																												 * "��"
																												 */);
			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton3().setVisible(false);
			dlg.setShortKey("YN");
			break;

		case YES_NO_CANCEL_OPTION:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000051")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000050")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(N)");
			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton3().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000002")/*
																							 * @
																							 * res
																							 * "ȡ��"
																							 */+ "(C)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNC");
			break;
		case CONFIRM_REJECT_PREVIEW:
			dlg.getUIButton1().setText("ȷ��(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText("����(N)");
			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton3().setText("Ԥ��(V)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNV");
			break;
		case CONFIRM_PREVIEW:
			dlg.getUIButton1().setText("ȷ��(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText("����(N)");
			dlg.getUIButton2().setVisible(false);
			dlg.getUIButton3().setText("Ԥ��(V)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNV");
			break;
		case OK_OPTION:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000021")/*
																							 * @
																							 * res
																							 * "ȷ��"
																							 */+ "(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setVisible(false);
			dlg.getUIButton3().setVisible(false);
			dlg.setShortKey("Y");
			break;
		case OK_CANCEL_OPTION:
		default:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000021")/*
																							 * @
																							 * res
																							 * "ȷ��"
																							 */+ "(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setVisible(false);
			dlg.getUIButton3().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000002")/*
																							 * @
																							 * res
																							 * "ȡ��"
																							 */+ "(C)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("Y?C");

		}
		// ��ֹ�ȼ�
		// dlg.setShortKey("");

		// ��ʾͼ��
		String key = null;
		switch (icon) {
		case WARNING_ICON:
			key = "/GIF/WARNING.GIF";
			break;
		case QUESTION_ICON:
			key = "/GIF/QUESTION.GIF";
			break;
		case ERROR_ICON:
			key = "/GIF/ERROR.GIF";
			break;
		case HINT_ICON:
		default:
			key = "/GIF/HINT.GIF";
		}
		try {
			dlg.getUILabelIcon().setIcon(new javax.swing.ImageIcon(dlg.getClass().getResource(key)));

		} catch (java.lang.Exception ivjExc) {
		}

		dlg.getUITextMessage().setText(messageDisplay);
		// ���ñ���
		dlg.setTitle(title);
		// ������ʾλ��
		Object returnObject = null;
		int returnKeyCode = dlg.showModal();
		// �ָ���ǰ�ڵ��
		if (returnKeyCode == ID_OK || returnKeyCode == ID_YES) {
			returnObject = dlg.getInputValue();
		} else if (returnKeyCode == ID_CONFIRM || returnKeyCode == ID_REJECT) {
			returnObject = new Object[] { returnKeyCode, dlg.getInputValue() };
		}
		dlg.destroy();
		return returnObject;
	}

	/* ����������ʾ */
	private static Object showInputBox(Container parent, int type, int icon, String title, String message,
			Object firstValue, int maxLength, boolean isCheckShow) {
		// ��ʾ��Ϣ
		// String messageDisplay = StringUtil.recoverWrapLineChar(message);
		// 2014-12-11 ���ڴ��������Ĵ�"d:\\festival"����ʾ�������⣬�޸�Ϊ��Ҫת�崦��
		String messageDisplay = message;
		// if (message != null && !message.trim().equals(""))
		// messageDisplay =
		// nc.ui.pub.ClientEnvironment.getInstance().getModuleLang().getString(message);
		if (messageDisplay == null)
			messageDisplay = "";
		// ��¼��ǰ�ڵ��
		// FIXME
		// ȡ�����ڵ���MessageDialogʱ��ȡ�õ�ǰ���ģ��ţ��ڹر�ʱ�����û�ȥ����UIDialog��û�����ƴ���Ӧͳһ��MainFrame�н��С�����public�ֲ�ʱע�͡�
		// String strModuleCode =
		// nc.ui.pub.ClientEnvironment.getInstance().getModuleCode();
		ConfirmDialog dlg = getSingleton(parent);
		if (dlg.isVisible()) { // �������ʵ������ʹ�þ�������һ��
			dlg = new ConfirmDialog(parent);
		}
		// dlg.setParent(parent);
		if (fm == null)
			fm = dlg.getFontMetrics(new Font("Dialog", Font.PLAIN, 20));
		if (message != null && fm.stringWidth(message) >= SIZE_CHOOSER_LENGTH) {
			size_width = SIZE_WIDTH_MIDDLE;
			size_height = SIZE_HEIGHT_MIDDLE;
			bound_width = BOUND_WIDTH_MIDDLE;
			bound_height = BOUND_HEIGHT_MIDDLE;
		} else {
			size_width = SIZE_WIDTH_LITTLE;
			size_height = SIZE_HEIGHT_LITTLE;
			bound_width = BOUND_WIDTH_LITTLE;
			bound_height = BOUND_HEIGHT_LITTLE;
		}
		dlg.setSize(size_width, size_height);
		dlg.getUIComboBox_Input().setVisible(false);
		dlg.getUIPanel_Message().remove(dlg.getUIComboBox_Input());
		dlg.getUIButton1().requestFocus();
		dlg.getUIButton4().setVisible(false);
		dlg.getUIButton5().setVisible(false);
		//
		// int reduce = 30;
		// dlg.getUIScrollPane_Message().setBounds(bound_x, bound_y,
		// bound_width, bound_height - reduce);
		// dlg.getUIScrollPane_Message().setPreferredSize(
		// new java.awt.Dimension(bound_width, bound_height - reduce));
		// //
		// dlg.getUITextField_Input().setBounds(bound_x, bound_y + bound_height
		// - reduce + 10, bound_width - 10, 25);
		javax.swing.JPanel pnl = new javax.swing.JPanel();
		pnl.setLayout(new java.awt.GridLayout(0, 1, 3, 3));
		int h = isCheckShow ? 45 : 22;
		pnl.setPreferredSize(new Dimension(200, h));
		pnl.add(dlg.getUITextField_Input());
		if (isCheckShow) {
			pnl.add(dlg.getchkShow());
			dlg.getchkShow().setVisible(true);
		}

		dlg.getUIPanel_Message().add(pnl, java.awt.BorderLayout.SOUTH);

		// if (isCheckShow) {
		// dlg.getUIPanel_Message().add(dlg.getchkShow(),
		// dlg.getchkShow().getName());
		// dlg.getchkShow().setVisible(true);
		// dlg.getUITextField_Input().setBounds(bound_x-1,
		// bound_y+bound_height-reduce+6, bound_width-120, 22);
		// dlg.getchkShow().setBounds(bound_width-110,
		// bound_y+bound_height-reduce+6, bound_width-40, 22);
		// }
		if (maxLength > 0)
			dlg.getUITextField_Input().setMaxLength(maxLength);
		dlg.getUITextField_Input().setVisible(true);
		dlg.getUITextField_Input().setText("");
		if (firstValue != null)
			dlg.getUITextField_Input().setText(firstValue.toString());
		if (!dlg.keyListenerDisabled) {
			dlg.addKeyListener(dlg);
			dlg.keyListenerDisabled = true;
		}
		// dlg.removeKeyListener(dlg);
		// ���ð�ť
		dlg.m_nType = type;
		switch (type) {
		case YES_NO_OPTION:
			dlg.getUIButton1().setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000051")/*
																												 * @
																												 * res
																												 * "��"
																												 */);
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000050")/*
																												 * @
																												 * res
																												 * "��"
																												 */);
			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton3().setVisible(false);
			dlg.setShortKey("YN");
			break;

		case YES_NO_CANCEL_OPTION:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000051")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000050")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(N)");
			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton3().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000002")/*
																							 * @
																							 * res
																							 * "ȡ��"
																							 */+ "(C)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNC");
			break;
		case CONFIRM_REJECT_PREVIEW:
			dlg.getUIButton1().setText("ȷ��(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText("����(N)");
			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton3().setText("Ԥ��(V)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNV");
			break;
		case CONFIRM_PREVIEW:
			dlg.getUIButton1().setText("ȷ��(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText("����(N)");
			dlg.getUIButton2().setVisible(false);
			dlg.getUIButton3().setText("Ԥ��(V)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNV");
			break;
		case OK_OPTION:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000021")/*
																							 * @
																							 * res
																							 * "ȷ��"
																							 */+ "(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setVisible(false);
			dlg.getUIButton3().setVisible(false);
			dlg.setShortKey("Y");
			break;
		case OK_CANCEL_OPTION:
		default:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000021")/*
																							 * @
																							 * res
																							 * "ȷ��"
																							 */+ "(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setVisible(false);
			dlg.getUIButton3().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000002")/*
																							 * @
																							 * res
																							 * "ȡ��"
																							 */+ "(C)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("Y?C");

		}
		// ��ֹ�ȼ�
		// dlg.setShortKey("");

		// ��ʾͼ��
		String key = null;
		switch (icon) {
		case WARNING_ICON:
			key = "/GIF/WARNING.GIF";
			break;
		case QUESTION_ICON:
			key = "/GIF/QUESTION.GIF";
			break;
		case ERROR_ICON:
			key = "/GIF/ERROR.GIF";
			break;
		case HINT_ICON:
		default:
			key = "/GIF/HINT.GIF";
		}
		try {
			dlg.getUILabelIcon().setIcon(new javax.swing.ImageIcon(dlg.getClass().getResource(key)));

		} catch (java.lang.Exception ivjExc) {
		}
		// ����TextArea�߶�
		/*
		 * int len = messageDisplay.getBytes().length; if (len > 108) { int rows
		 * = (len - 1) / 30 + 1; int height = 16; int first = 10;
		 * dlg.getUITextMessage().setBounds(0, 0, 200, rows * height + first);
		 * dlg.getUITextMessage().setPreferredSize(new Dimension(200, rows *
		 * height + first)); }
		 */
		java.util.StringTokenizer token = new java.util.StringTokenizer(messageDisplay, "\n");
		int iRows = 0;
		while (token.hasMoreElements()) {
			int iLen = token.nextElement().toString().getBytes().length;
			iRows += (iLen - 1) / 36 + 1;
		}
		// if (iRows >= 3) {
		// int height = 17;
		// int first = 10;
		// dlg.getUITextMessage().setBounds(0, 0, bound_width - 36, iRows *
		// height + first);
		// dlg.getUITextMessage().setPreferredSize(
		// new Dimension(bound_width-36, iRows * height + first));
		// }
		//
		dlg.getUITextMessage().setText(messageDisplay);
		// ���ñ���
		dlg.setTitle(title);
		// ������ʾλ��
		Object returnObject = null;

		int returnKeyCode = dlg.showModal();
		// �ָ���ǰ�ڵ��
		// nc.ui.pub.ClientEnvironment.getInstance().setModuleCode(strModuleCode);
		if (returnKeyCode == ID_OK || returnKeyCode == ID_YES) {
			returnObject = dlg.getInputValue();
			if (returnObject == null)
				returnObject = "";
		}
		dlg.destroy();
		return returnObject;
	}

	public static Object showInputDlg(Container parent, int type, String title, String message, Object firstValue,
			int maxLength) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000090")/*
																							 * @
																							 * res
																							 * "����"
																							 */;
		}
		return showInputBox(parent, type, ConfirmDialog.WARNING_ICON, title, message, firstValue, maxLength, false);
	}

	public static Object showInputDlg(Container parent, int type, String title, String message, Object firstValue,
			int maxLength, int dec, String dataType) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000090")/*
																							 * @
																							 * res
																							 * "����"
																							 */;
		}
		return showInputBox(parent, type, ConfirmDialog.WARNING_ICON, title, message, firstValue, maxLength, dec,
				dataType);
	}

	public static Object showInputDlg(Container parent, int type, String title, String message, Object firstValue,
			int maxLength, int dec, String dataType, String url) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000090")/*
																							 * @
																							 * res
																							 * "����"
																							 */;
		}

		strURL = url;

		return showInputBox(parent, type, ConfirmDialog.WARNING_ICON, title, message, firstValue, maxLength, dec,
				dataType);
	}

	public static Object showInputDlg(Container parent, String title, String message, Object firstValue) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000090")/*
																							 * @
																							 * res
																							 * "����"
																							 */;
		}
		return showInputBox(parent, ConfirmDialog.OK_CANCEL_OPTION, ConfirmDialog.WARNING_ICON, title, message,
				firstValue, ConfirmDialog.TEXTMAXLENGTH, false);
	}

	public static Object showInputDlg(Container parent, String title, String message, Object firstValue, int maxLength) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000090")/*
																							 * @
																							 * res
																							 * "����"
																							 */;
		}
		return showInputBox(parent, ConfirmDialog.OK_CANCEL_OPTION, ConfirmDialog.WARNING_ICON, title, message,
				firstValue, maxLength, false);
	}

	public static Object showInputDlg(Container parent, String title, String message, Object firstValue, int maxLength,
			int dec, String dataType) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000090")/*
																							 * @
																							 * res
																							 * "����"
																							 */;
		}
		return showInputBox(parent, ConfirmDialog.OK_CANCEL_OPTION, ConfirmDialog.WARNING_ICON, title, message,
				firstValue, maxLength, dec, dataType);
	}

	/* �����Ƿ�������ʾ���� */
	public static Object showInputDlg(Container parent, String title, String message, Object firstValue, int maxLength,
			boolean isChkShow) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000090")/*
																							 * @
																							 * res
																							 * "����"
																							 */;
		}
		return showInputBox(parent, ConfirmDialog.OK_CANCEL_OPTION, ConfirmDialog.WARNING_ICON, title, message,
				firstValue, maxLength, isChkShow);
	}

	private static int showMessageBox(Container parent, int type, int icon, String title, String message, int button) {
		// String messageDisplay = StringUtil.recoverWrapLineChar(message);
		// 2014-12-11 ���ڴ��������Ĵ�"d:\\festival"����ʾ�������⣬�޸�Ϊ��Ҫת�崦��
		String messageDisplay = message;
		if (messageDisplay == null)
			messageDisplay = "";
		ConfirmDialog dlg = getSingleton(parent);
		if (dlg.isVisible()) { // �������ʵ������ʹ�þ�������һ��
			dlg = new ConfirmDialog(parent);
		}

		dlg.setFocusButton(button);
		dlg.setDefaultButton(button);

		if (fm == null)
			fm = dlg.getFontMetrics(new Font("Dialog", Font.PLAIN, 20));

		if (message != null && fm.stringWidth(message) >= SIZE_CHOOSER_LENGTH + 100) {
			size_width = SIZE_WIDTH_MIDDLE;
			size_height = SIZE_HEIGHT_MIDDLE;
			bound_width = BOUND_WIDTH_MIDDLE;
			bound_height = BOUND_HEIGHT_MIDDLE;
		} else {
			size_width = SIZE_WIDTH_LITTLE;
			size_height = SIZE_HEIGHT_LITTLE;
			bound_width = BOUND_WIDTH_LITTLE;
			bound_height = BOUND_HEIGHT_LITTLE;
		}
		dlg.setSize(size_width, size_height);
		dlg.getUIScrollPane_Message().setBounds(bound_x, bound_y, bound_width, bound_height);

		dlg.getUITextField_Input().setVisible(false);
		dlg.getUIComboBox_Input().setVisible(false);
		dlg.getUIButton4().setVisible(false);
		dlg.getUIButton5().setVisible(false);

		dlg.getUIScrollPane_Message().setBounds(bound_x, bound_y, bound_width, bound_height);
		dlg.getUIScrollPane_Message().setPreferredSize(new java.awt.Dimension(bound_width, bound_height));

		if (!dlg.keyListenerDisabled) {
			dlg.addKeyListener(dlg);
			dlg.keyListenerDisabled = true;
		}

		// ���ð�ť
		dlg.m_nType = type;

		switch (type) {
		case OK_CANCEL_OPTION:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000021")/*
																							 * @
																							 * res
																							 * "ȷ��"
																							 */+ "(Y)");

			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setVisible(false);
			dlg.getUIButton3().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000002")/*
																							 * @
																							 * res
																							 * "ȡ��"
																							 */+ "(C)");

			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("Y?C");
			break;

		case YES_NO_OPTION:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000051")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(Y)");

			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000050")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(N)");

			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton3().setVisible(false);
			dlg.setShortKey("YN");
			break;

		case YES_NO_CANCEL_OPTION:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000051")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(Y)");

			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000050")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(N)");

			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton3().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000002")/*
																							 * @
																							 * res
																							 * "ȡ��"
																							 */+ "(C)");

			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNC");
			break;
		case CONFIRM_REJECT_PREVIEW:
			dlg.getUIButton1().setText("ȷ��(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText("����(N)");
			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton3().setText("Ԥ��(V)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNV");
			break;
		case CONFIRM_PREVIEW:
			dlg.getUIButton1().setText("ȷ��(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText("����(N)");
			dlg.getUIButton2().setVisible(false);
			dlg.getUIButton3().setText("Ԥ��(V)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNV");
			break;
		case YES_YESTOALL_NO_CANCEL_OPTION:
			dlg.setSize(size_width + 40, size_height);
			dlg.getUIScrollPane_Message().setBounds(bound_x, bound_y, bound_width + 20, bound_height);

			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000051")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(Y)");

			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton4().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000091")/*
																							 * @
																							 * res
																							 * "ȫ��"
																							 */+ "(A)");
			dlg.getUIButton4().setVisible(true);
			dlg.getUIButton2().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000050")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(N)");
			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton3().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000002")/*
																							 * @
																							 * res
																							 * "ȡ��"
																							 */+ "(C)");

			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNCA");
			break;
		case YES_YESTOALL_NO_NOTOALL_OPTION:
			dlg.setSize(size_width + 40, size_height);
			dlg.getUIScrollPane_Message().setBounds(bound_x, bound_y, bound_width + 20, bound_height);

			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000051")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(Y)");

			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton4().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000091")/*
																							 * @
																							 * res
																							 * "ȫ��"
																							 */+ "(A)");

			dlg.getUIButton4().setVisible(true);
			dlg.getUIButton2().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000050")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(N)");

			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton5().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000092")/*
																							 * @
																							 * res
																							 * "ȫ��"
																							 */+ "(O)");

			dlg.getUIButton5().setVisible(true);
			dlg.getUIButton3().setVisible(false);
			dlg.setShortKey("YN?AO");
			break;
		case YES_YESTOALL_NO_NOTOALL_CANCEL_OPTION:
			dlg.setSize(size_width + 140, size_height);
			dlg.getUIScrollPane_Message().setBounds(bound_x, bound_y, bound_width + 20, bound_height);

			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000051")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton4().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000091")/*
																							 * @
																							 * res
																							 * "ȫ��"
																							 */+ "(A)");
			dlg.getUIButton4().setVisible(true);
			dlg.getUIButton2().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000050")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(N)");
			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton5().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000092")/*
																							 * @
																							 * res
																							 * "ȫ��"
																							 */+ "(O)");
			dlg.getUIButton5().setVisible(true);
			dlg.getUIButton3().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000002")/*
																							 * @
																							 * res
																							 * "ȡ��"
																							 */+ "(C)");

			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNCAO");
			break;
		case OK_OPTION:
		default:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000021")/*
																							 * @
																							 * res
																							 * "ȷ��"
																							 */+ "(Y)");

			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setVisible(false);
			dlg.getUIButton3().setVisible(false);
			dlg.setShortKey("Y");

			if (ID_CLOSE == button) {
				dlg.getUIButton1().setText(
						nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000095")/*
																								 * @
																								 * res
																								 * "�ر�"
																								 */+ "(C)");

				dlg.setShortKey("C");
			}
		}
		// ��ʾͼ��
		switch (icon) {
		case WARNING_ICON:
			dlg.getUILabelIcon().setIcon(Style.getImage("��Ϣ.����"));/*
																 * -=notranslate=
																 * -
																 */

			break;
		case QUESTION_ICON:
			dlg.getUILabelIcon().setIcon(Style.getImage("��Ϣ.����"));/*
																 * -=notranslate=
																 * -
																 */

			break;
		case ERROR_ICON:
			dlg.getUILabelIcon().setIcon(Style.getImage("��Ϣ.����"));/*
																 * -=notranslate=
																 * -
																 */

			break;
		case HINT_ICON:
			dlg.getUILabelIcon().setIcon(Style.getImage("��Ϣ.��ʾ"));/*
																 * -=notranslate=
																 * -
																 */
			break;
		default:
			dlg.getUILabelIcon().setIcon(Style.getImage("��Ϣ.��ʾ"));/*
																 * -=notranslate=
																 * -
																 */

		}

		// ��ʾ��Ϣ
		// ����TextArea�߶�
		// ����ҫ�޸Ŀ�ʼ
		/*
		 * int len = messageDisplay.getBytes().length; if (len > 180) { int rows
		 * = (len - 1) / 36 + 1; int height = 16; int first = 10;
		 * dlg.getUITextMessage().setBounds(0, 0, 200, rows * height + first);
		 * dlg.getUITextMessage().setPreferredSize(new Dimension(200, rows *
		 * height + first)); }
		 */
		java.util.StringTokenizer token = new java.util.StringTokenizer(messageDisplay, "\n");
		int iRows = 0;
		while (token.hasMoreElements()) {
			int iLen = token.nextElement().toString().getBytes().length;
			iRows += (iLen - 1) / 36 + 1;
		}

		if (iRows >= 5) {
			int height = 18;// 17
			int first = 20;
			dlg.getUITextMessage().setBounds(0, 0, 200, iRows * height + first);
		}

		// add by hxr
		if (messageDisplay.length() >= dlg.getUITextMessage().getMaxLength())
			messageDisplay = messageDisplay.substring(0, dlg.getUITextMessage().getMaxLength() - 5) + "...";

		// ����ҫ�޸Ľ���
		dlg.getUITextMessage().setText(messageDisplay);
		// ���ñ���
		dlg.setTitle(title);

		int returnKeyCode = dlg.showModal();
		// �ָ���ǰ�ڵ��
		dlg.destroy();
		return returnKeyCode;
	}

	private void setDefaultButton(int button) {
		UIButton btn = getButtonById(button);
		if (btn != null)
			btn.getRootPane().setDefaultButton(btn);
	}

	public static int showOkCancelDlg(Container parent, String title, String question) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000093")/*
																							 * @
																							 * res
																							 * "ѯ��"
																							 */;
		}
		return showMessageBox(parent, ConfirmDialog.OK_CANCEL_OPTION, ConfirmDialog.QUESTION_ICON, title, question,
				ID_OK);
	}

	public static int showOkCancelDlg(Container parent, String title, String question, int button) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000093")/*
																							 * @
																							 * res
																							 * "ѯ��"
																							 */;
		}
		return showMessageBox(parent, ConfirmDialog.OK_CANCEL_OPTION, ConfirmDialog.QUESTION_ICON, title, question,
				button);
	}

	@SuppressWarnings("unchecked")
	private static Object showSelectBox(Container parent, int type, int icon, String title, String message,
			Object[] allValue, int maximumRowCount) {
		// ��ʾ��Ϣ
		String messageDisplay = message;
		// if (message != null && !message.trim().equals(""))
		// messageDisplay =
		// nc.ui.pub.ClientEnvironment.getInstance().getModuleLang().getString(message);
		if (messageDisplay == null)
			messageDisplay = "";
		// ��¼��ǰ�ڵ��
		// FIXME
		// ȡ�����ڵ���MessageDialogʱ��ȡ�õ�ǰ���ģ��ţ��ڹر�ʱ�����û�ȥ����UIDialog��û�����ƴ���Ӧͳһ��MainFrame�н��С�����public�ֲ�ʱע�͡�
		// String strModuleCode =
		// nc.ui.pub.ClientEnvironment.getInstance().getModuleCode();
		ConfirmDialog dlg = getSingleton(parent);
		if (dlg.isVisible()) { // �������ʵ������ʹ�þ�������һ��
			dlg = new ConfirmDialog(parent);
		}
		// dlg.setParent(parent);
		if (fm == null)
			fm = dlg.getFontMetrics(new Font("Dialog", Font.PLAIN, 20));
		if (messageDisplay != null && fm.stringWidth(messageDisplay) >= SIZE_CHOOSER_LENGTH) {
			size_width = SIZE_WIDTH_MIDDLE;
			size_height = SIZE_HEIGHT_MIDDLE;
			bound_width = BOUND_WIDTH_MIDDLE;
			bound_height = BOUND_HEIGHT_MIDDLE;
		} else {
			size_width = SIZE_WIDTH_LITTLE;
			size_height = SIZE_HEIGHT_LITTLE;
			bound_width = BOUND_WIDTH_LITTLE;
			bound_height = BOUND_HEIGHT_LITTLE;
		}
		dlg.setSize(size_width, size_height);
		dlg.getUITextField_Input().setVisible(false);
		dlg.getUITextField_Input().setText("");
		dlg.getUIButton1().requestFocus();
		dlg.getUIButton4().setVisible(false);
		dlg.getUIButton5().setVisible(false);

		dlg.getUIPanel_Message().remove(dlg.getUITextField_Input());
		if (allValue != null && allValue.length > 0)
			for (int i = 0; i < allValue.length; i++)
				dlg.getUIComboBox_Input().addItem(allValue[i]);
		if (maximumRowCount > 0)
			dlg.getUIComboBox_Input().setMaximumRowCount(maximumRowCount);
		//
		int reduce = 30;
		dlg.getUIScrollPane_Message().setBounds(bound_x, bound_y, bound_width, bound_height - reduce);
		dlg.getUIScrollPane_Message().setPreferredSize(new java.awt.Dimension(bound_width, bound_height - reduce));
		//
		dlg.getUIComboBox_Input().setBounds(bound_x, bound_y + bound_height - reduce + 7, bound_width - 10, 22);
		dlg.getUIPanel_Message().add(dlg.getUIComboBox_Input(), java.awt.BorderLayout.SOUTH);
		dlg.getUIComboBox_Input().setVisible(true);

		if (!dlg.keyListenerDisabled) {
			dlg.addKeyListener(dlg);
			dlg.keyListenerDisabled = true;
		}
		// ���ð�ť
		dlg.m_nType = type;
		switch (type) {
		case OK_CANCEL_OPTION:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000021")/*
																							 * @
																							 * res
																							 * "ȷ��"
																							 */+ "(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setVisible(false);
			dlg.getUIButton3().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000002")/*
																							 * @
																							 * res
																							 * "ȡ��"
																							 */+ "(C)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("Y?C");
			break;

		case YES_NO_OPTION:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000051")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000050")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(N)");
			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton3().setVisible(false);
			dlg.setShortKey("YN");
			break;

		case YES_NO_CANCEL_OPTION:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000051")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000050")/*
																							 * @
																							 * res
																							 * "��"
																							 */+ "(N)");
			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton3().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000002")/*
																							 * @
																							 * res
																							 * "ȡ��"
																							 */+ "(C)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNC");
			break;
		case CONFIRM_REJECT_PREVIEW:
			dlg.getUIButton1().setText("ȷ��(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText("����(N)");
			dlg.getUIButton2().setVisible(true);
			dlg.getUIButton3().setText("Ԥ��(V)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNV");
			break;
		case CONFIRM_PREVIEW:
			dlg.getUIButton1().setText("ȷ��(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setText("����(N)");
			dlg.getUIButton2().setVisible(false);
			dlg.getUIButton3().setText("Ԥ��(V)");
			dlg.getUIButton3().setVisible(true);
			dlg.setShortKey("YNV");
			break;
		case OK_OPTION:

		default:
			dlg.getUIButton1().setText(
					nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000021")/*
																							 * @
																							 * res
																							 * "ȷ��"
																							 */+ "(Y)");
			dlg.getUIButton1().setVisible(true);
			dlg.getUIButton2().setVisible(false);
			dlg.getUIButton3().setVisible(false);
			dlg.setShortKey("Y");
		}

		// ��ʾͼ��
		String key = null;
		switch (icon) {
		case WARNING_ICON:
			// key = "����";
			key = "/GIF/WARNING.GIF";
			break;
		case QUESTION_ICON:
			// key = "����";
			key = "/GIF/QUESTION.GIF";
			break;
		case ERROR_ICON:
			// key = "����";
			key = "/GIF/ERROR.GIF";
			break;
		case HINT_ICON:
		default:
			// key = "��ʾ";
			key = "/GIF/HINT.GIF";
		}
		try {
			// dlg.getUILabelIcon().setIcon(Style.getImage("��Ϣ." + key));
			dlg.getUILabelIcon().setIcon(new javax.swing.ImageIcon(dlg.getClass().getResource(key)));

		} catch (java.lang.Exception ivjExc) {
		}
		// ����TextArea�߶�
		/*
		 * int len = messageDisplay.getBytes().length; if (len > 108) { int rows
		 * = (len - 1) / 30 + 1; int height = 16; int first = 10;
		 * dlg.getUITextMessage().setBounds(0, 0, 200, rows * height + first);
		 * dlg.getUITextMessage().setPreferredSize(new Dimension(200, rows *
		 * height + first)); }
		 */
		java.util.StringTokenizer token = new java.util.StringTokenizer(messageDisplay, "\n");
		int iRows = 0;
		while (token.hasMoreElements()) {
			int iLen = token.nextElement().toString().getBytes().length;
			iRows += (iLen - 1) / 36 + 1;
		}
		if (iRows >= 3) {
			int height = 17;
			int first = 10;
			dlg.getUITextMessage().setBounds(0, 0, bound_width - 36, iRows * height + first);
			dlg.getUITextMessage().setPreferredSize(new Dimension(bound_width - 36, iRows * height + first));
		}
		dlg.getUITextMessage().setText(messageDisplay);
		// ���ñ���
		dlg.setTitle(title);
		Object returnObject = null;
		int returnKeyCode = dlg.showModal();
		// �ָ���ǰ�ڵ��
		// nc.ui.pub.ClientEnvironment.getInstance().setModuleCode(strModuleCode);
		if (returnKeyCode == ID_OK || returnKeyCode == ID_YES) {
			returnObject = dlg.getInputValue();
		}
		dlg.destroy();
		return returnObject;
	}

	public static Object showSelectDlg(Container parent, int type, String title, String message, Object[] allValue,
			int maximumRowCount) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "null")/*
																				 * @
																				 * res
																				 * "ѡ��"
																				 */;
		}
		return showSelectBox(parent, type, ConfirmDialog.WARNING_ICON, title, message, allValue, maximumRowCount);
	}

	public static Object showSelectDlg(Container parent, String title, String message, Object[] allValue,
			int maximumRowCount) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "null")/*
																				 * @
																				 * res
																				 * "ѡ��"
																				 */;
		}
		return showSelectBox(parent, ConfirmDialog.OK_CANCEL_OPTION, ConfirmDialog.WARNING_ICON, title, message,
				allValue, maximumRowCount);
	}

	public static int showWarningDlg(Container parent, String title, String warning) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000090")/*
																							 * @
																							 * res
																							 * "����"
																							 */;
		}
		dispatchMesasge(parent, MessageHistoryEvent.WARNING, warning);
		return showMessageBox(parent, ConfirmDialog.OK_OPTION, ConfirmDialog.WARNING_ICON, title, warning, ID_OK);
	}

	public static int showYesNoCancelDlg(Container parent, String title, String question) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000093")/*
																							 * @
																							 * res
																							 * "ѯ��"
																							 */;
		}
		return showMessageBox(parent, ConfirmDialog.YES_NO_CANCEL_OPTION, ConfirmDialog.QUESTION_ICON, title, question,
				ID_YES);
	}

	public static int showYesNoCancelDlg(Container parent, String title, String question, int button) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000093")/*
																							 * @
																							 * res
																							 * "ѯ��"
																							 */;
		}
		return showMessageBox(parent, ConfirmDialog.YES_NO_CANCEL_OPTION, ConfirmDialog.QUESTION_ICON, title, question,
				button);
	}

	public static int showYesNoDlg(Container parent, String title, String question) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000093")/*
																							 * @
																							 * res
																							 * "ѯ��"
																							 */;
		}
		return showMessageBox(parent, ConfirmDialog.YES_NO_OPTION, ConfirmDialog.QUESTION_ICON, title, question, ID_YES);
	}

	public static int showYesNoDlg(Container parent, String title, String question, int button) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000093")/*
																							 * @
																							 * res
																							 * "ѯ��"
																							 */;
		}
		return showMessageBox(parent, ConfirmDialog.YES_NO_OPTION, ConfirmDialog.QUESTION_ICON, title, question, button);
	}

	public static int showYesToAllNoCancelDlg(Container parent, String title, String question) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000093")/*
																							 * @
																							 * res
																							 * "ѯ��"
																							 */;
		}
		return showMessageBox(parent, ConfirmDialog.YES_YESTOALL_NO_CANCEL_OPTION, ConfirmDialog.QUESTION_ICON, title,
				question, ID_YES);
	}

	public static int showYesToAllNoCancelDlg(Container parent, String title, String question, int button) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000093")/*
																							 * @
																							 * res
																							 * "ѯ��"
																							 */;
		}
		return showMessageBox(parent, ConfirmDialog.YES_YESTOALL_NO_CANCEL_OPTION, ConfirmDialog.QUESTION_ICON, title,
				question, button);
	}

	public static int showYesToAllNoToAllCancelDlg(Container parent, String title, String question) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000093")/*
																							 * @
																							 * res
																							 * "ѯ��"
																							 */;
		}
		return showMessageBox(parent, ConfirmDialog.YES_YESTOALL_NO_NOTOALL_CANCEL_OPTION, ConfirmDialog.QUESTION_ICON,
				title, question, ID_YES);
	}

	public static int showYesToAllNoToAllCancelDlg(Container parent, String title, String question, int button) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000093")/*
																							 * @
																							 * res
																							 * "ѯ��"
																							 */;
		}
		return showMessageBox(parent, ConfirmDialog.YES_YESTOALL_NO_NOTOALL_CANCEL_OPTION, ConfirmDialog.QUESTION_ICON,
				title, question, button);
	}

	public static int showYesToAllNoToAllDlg(Container parent, String title, String question) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000093")/*
																							 * @
																							 * res
																							 * "ѯ��"
																							 */;
		}
		return showMessageBox(parent, ConfirmDialog.YES_YESTOALL_NO_NOTOALL_OPTION, ConfirmDialog.QUESTION_ICON, title,
				question, ID_YES);
	}

	public static int showYesToAllNoToAllDlg(Container parent, String title, String question, int button) {
		if (title == null) {
			title = nc.ui.ml.NCLangRes.getInstance().getStrByID("_Beans", "UPP_Beans-000093")/*
																							 * @
																							 * res
																							 * "ѯ��"
																							 */;
		}
		return showMessageBox(parent, ConfirmDialog.YES_YESTOALL_NO_NOTOALL_OPTION, ConfirmDialog.QUESTION_ICON, title,
				question, button);
	}

	/** �ɷ���Ϣ��ʵ��<code>nc.ui.pub.beans.event.IMessageHistory</code>��Container */
	private static void dispatchMesasge(Container aContainer, int aType, String aMsg) {
		Component c = aContainer;

		while (c != null) {
			if (c instanceof IMessageHistory) {
				((IMessageHistory) c).dispatchMessage(new MessageHistoryEvent(aContainer, aType, aMsg));
				return;
			} else {
				c = c.getParent();
			}
		}
	}

	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_OPENED) {
			ScrollToTop();
			moveFocusWithinButtons();
			getUIPanel_Buttons().transferFocus();
			if (getUITextField_Input().isVisible()) {
				getUITextField_Input().requestFocus();
			}
		}
	}

	/**
	 * �л���ť�Ľ���
	 */
	private void moveFocusWithinButtons() {
		switch (getFocusButton()) {
		case ID_NO:
			getUIButton2().requestFocus();
			break;
		case ID_CANCEL:
			getUIButton3().requestFocus();
			break;
		case ID_YESTOALL:
			getUIButton4().requestFocus();
			break;
		case ID_NOTOALL:
			getUIButton5().requestFocus();
			break;
		case ID_OK:
		case ID_YES:
		default:
			getUIButton1().requestFocus();
		}
	}

	private UIButton getButtonById(int curbtn) {
		UIButton button = null;
		switch (getFocusButton()) {
		case ID_NO:
			button = getUIButton2();
			break;
		case ID_CANCEL:
			button = getUIButton3();
			break;
		case ID_YESTOALL:
			button = getUIButton4();
			break;
		case ID_NOTOALL:
			button = getUIButton5();
			break;
		case ID_OK:
		case ID_YES:
		case ID_CLOSE:
		default:
			button = getUIButton1();
		}
		return button;
	}
}
