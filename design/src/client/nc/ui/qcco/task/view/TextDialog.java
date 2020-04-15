package nc.ui.qcco.task.view;

import java.awt.Dimension;
import java.awt.event.FocusEvent;

import javax.swing.JComponent;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import nc.ui.hr.frame.dialog.HrDialog;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UITextArea;
import nc.ui.pub.beans.UITextAreaScrollPane;
import nc.vo.uif2.LoginContext;
public class TextDialog extends HrDialog {
	
	private static final long serialVersionUID = 7896442346973257657L;
	//private LoginContext context;
	private UITextAreaScrollPane textPane;
	//private UIButton previewButton;

	public TextDialog(LoginContext context, String strTitle,String initStr) {
		super(context.getEntranceUI(), strTitle);
		//this.context = context;
		setSize(600, 400);
		getTextPane().setText(initStr);
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
            	getTextPane().requestFocus();
            }
        });
	}


	@Override
	protected JComponent createCenterPanel() {
		UIPanel contentPanel = new UIPanel();
		//
		//FormLayout layout = new FormLayout("right:150,100,150", "100,default,100,default,100,default,pref");
		//DefaultFormBuilder builder = new DefaultFormBuilder(null, contentPanel);
		//builder.nextLine();
		//builder.append(getTextPane());
		//builder.nextLine();
		contentPanel.add(getTextPane());
		//contentPanel.setLayout(null);
		return contentPanel;
	}
	
	public UITextAreaScrollPane getTextPane() {
		if (this.textPane == null) {
			textPane = new UITextAreaScrollPane();	
			Dimension d = new Dimension(500, 350);
			textPane.setSize(d);
			textPane.setPreferredSize(d);
			textPane.requestFocusInWindow();
		}
		return this.textPane;
	}
	
	public String getText(){
		String pk_wa_classs = getTextPane().getText();
		return pk_wa_classs;
		
	}


}
