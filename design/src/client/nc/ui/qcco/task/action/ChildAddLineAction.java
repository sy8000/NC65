package nc.ui.qcco.task.action;

import java.awt.event.ActionEvent;

import nc.bs.logging.Logger;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.pubapp.uif2app.actions.BodyAddLineAction;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.qcco.task.model.SubGrandBillModel;
import nc.vo.pub.BusinessException;

public class ChildAddLineAction extends BodyAddLineAction{
	public ChildAddLineAction(BillForm mainBillForm) {
		billFormEditor = mainBillForm;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -4628859649050919500L;
	private BillForm billFormEditor;//


	public BillForm getBillFormEditor() {
		return billFormEditor;
	}

	public void setBillFormEditor(BillForm billFormEditor) {
		this.billFormEditor = billFormEditor;
	}
	@Override
	public void doAction(ActionEvent e) throws Exception {
		int curRow = billFormEditor.getBillCardPanel().getBodyPanel().getTable().getSelectedRow();
		if(curRow < 0){
			throw new BusinessException("请选择表体行!");
		}
		super.doAction(e);
	}
	@Override
	protected void afterLineInsert(int index) {
		super.afterLineInsert(index);
		//测试条件
		getCardPanel().getBillModel().setCellEditable(index,"pk_testconditionitem",true);
		getCardPanel().getBillModel().setCellEditable(index,"isoptional",true);
		getCardPanel().getBillModel().setCellEditable(index,"isallow_out",true);
		getCardPanel().getBillModel().setCellEditable(index,"instrument",true);
		getCardPanel().getBillModel().setValueAt(1, index, "valueways");
		
		//试验后
		getCardPanel().getBillModel().setCellEditable(index,"samplegroup",true);
		getCardPanel().getBillModel().setCellEditable(index,"component",true);
		getCardPanel().getBillModel().setCellEditable(index,"valuetype",true);
		getCardPanel().getBillModel().setCellEditable(index,"stdmaxvalue",true);
		getCardPanel().getBillModel().setCellEditable(index,"stdminvalue",true);
		getCardPanel().getBillModel().setCellEditable(index,"pk_unit",true);
		getCardPanel().getBillModel().setCellEditable(index,"judgeflag",true);
		getCardPanel().getBillModel().setCellEditable(index,"testflag",true);
		getCardPanel().getBillModel().setCellEditable(index,"pk_testtemperature",true);

		if (getCardPanel().getBodyItem("isoptional") != null) {
			UICheckBox isoptinal = (UICheckBox) (getCardPanel().getBodyItem("isoptional").getComponent());
			isoptinal.setEnabled(true);
		}
		if (getCardPanel().getBodyItem("isallow_out") != null) {
			UICheckBox isallow_out = (UICheckBox) (getCardPanel().getBodyItem("isallow_out").getComponent());
			isallow_out.setEnabled(true);
		}
		if (getCardPanel().getBodyItem("judgeflag") != null) {
			UICheckBox judgeflag = (UICheckBox) (getCardPanel().getBodyItem("judgeflag").getComponent());
			judgeflag.setEnabled(true);
		}
		if (getCardPanel().getBodyItem("testflag") != null) {
			UICheckBox testflag = (UICheckBox) (getCardPanel().getBodyItem("testflag").getComponent());
			testflag.setEnabled(true);
		}

	}

}
