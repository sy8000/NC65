package nc.ui.qcco.task.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nc.bs.pubapp.utils.UserDefineRefUtils;
import nc.ui.pub.bill.BillScrollPane;
import nc.ui.pubapp.uif2app.actions.BodyPasteToTailAction;
import nc.ui.pubapp.uif2app.components.grand.CardGrandPanelComposite;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterRowEditEvent;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.ISuperVO;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.qcco.task.AggTaskHVO;
import nc.vo.qcco.task.TaskBVO;
import nc.vo.qcco.task.TaskRVO;
import nc.vo.qcco.task.TaskSVO;

public class TaskBodyPasteToTailAction extends BodyPasteToTailAction {
	
	/**
	 * serial no
	 */
	private static final long serialVersionUID = 8292276981135372115L;
	private ShowUpableBillForm grandCard;// mainBillForm
	private CardGrandPanelComposite billForm;
	public CardGrandPanelComposite getBillForm() {
		return billForm;
	}

	public void setBillForm(CardGrandPanelComposite billForm) {
		this.billForm = billForm;
	}
	public ShowUpableBillForm getGrandCard() {
		return grandCard;
	}

	public void setGrandCard(ShowUpableBillForm grandCard) {
		this.grandCard = grandCard;
	}
	/*public void doAction() {
		super.doAction();

		Integer order = (Integer) getCardPanel().getBillModel().getValueAt(lastPastedRow(), "runorder");
		AggTaskHVO aggvo = (AggTaskHVO) this.getModel().getSelectedData();
		ISuperVO[] bodyVOs = aggvo.getChildren(TaskBVO.class);
		if (bodyVOs != null && bodyVOs.length > 0) {
			List<TaskSVO> pastedSVOs = new ArrayList<TaskSVO>();
			for (ISuperVO bodyvo : bodyVOs) {
				Integer bodyOrder = (Integer) bodyvo.getAttributeValue("runorder");
				if (bodyOrder.equals(order)) {
					TaskBVO bodySource = (TaskBVO) bodyvo;
					TaskSVO[] taskSVOs = bodySource.getPk_task_s();
					for (TaskSVO rvo : taskSVOs) {
						TaskSVO pvo = (TaskSVO) rvo.clone();
						pvo.setPk_task_b(null);
						pvo.setPk_task_s(null);
						pvo.setStatus(VOStatus.NEW);
						pastedSVOs.add(pvo);
					}
					break;
				}
			}
			TaskBVO tgtVO = (TaskBVO) getCardPanel().getBillModel().getBodyValueRowVO(lastPastedRow(),
					TaskBVO.class.getName());
			tgtVO.setPk_task_s(pastedSVOs.toArray(new TaskSVO[0]));
			
		}
		
	}*/

	@Override
	public void doAction(ActionEvent e) throws Exception {
		//super.doAction(e);
		//手动填充孙表
		//没找到选择第几行的...方法
		//getCardPanel().getBodyPanel("pk_task_b").delLine();
		int rowSource = getCardPanel().getBodyPanel().getTable().getSelectedRow();
		Integer order = (Integer) getCardPanel().getBillModel().getValueAt(rowSource, "runorder");
		AggTaskHVO aggvo =  (AggTaskHVO)this.getBillForm().getValue();
		ISuperVO[] bodyVOs = aggvo.getChildren(TaskBVO.class);
		TaskBVO newVO = null;
		if (bodyVOs != null && bodyVOs.length > 0) {
			List<TaskSVO> pastedSVOs = new ArrayList<TaskSVO>();
			List<TaskRVO> pastedRVOs = new ArrayList<TaskRVO>();
			for (ISuperVO bodyvo : bodyVOs) {
				Integer bodyOrder = (Integer) bodyvo.getAttributeValue("runorder");
				if (bodyOrder.equals(order)) {
					TaskBVO bodySource = (TaskBVO) bodyvo;
					TaskSVO[] taskSVOs = bodySource.getPk_task_s();
					TaskRVO[] taskRVOs = bodySource.getPk_task_r();
					if(taskSVOs!=null){
						for (TaskSVO rvo : taskSVOs) {
							TaskSVO pvo = (TaskSVO) rvo.clone();
							pvo.setPk_task_b(null);
							pvo.setPk_task_s(null);
							pvo.setStatus(VOStatus.NEW);
							pastedSVOs.add(pvo);
						}
					}
					if(taskRVOs!=null){
						for (TaskRVO rvo : taskRVOs) {
							TaskRVO pvo = (TaskRVO) rvo.clone();
							pvo.setPk_task_b(null);
							pvo.setPk_task_r(null);
							pvo.setStatus(VOStatus.NEW);
							pastedRVOs.add(pvo);
						}
					}
					
					// 复制出的数据有点问题,没办法添加孙表,手动复制
					getCardPanel().getBodyPanel("pk_task_b").addLine();
					//updateStatus();
					newVO = (TaskBVO)bodySource.clone();
					fillPastedBodyVO(newVO);
					fillGrandPanel(taskSVOs,taskRVOs);
					fireEvent();
					break;
				}
			}
			TaskBVO tgtVO = (TaskBVO) getCardPanel().getBillModel().getBodyValueRowVO(lastPastedRow(),
					TaskBVO.class.getName());
			tgtVO.setStatus(VOStatus.NEW);
			tgtVO.setPk_task_s(pastedSVOs.toArray(new TaskSVO[0]));
			tgtVO.setPk_task_r(pastedRVOs.toArray(new TaskRVO[0]));
			
			
			//刷新操作
			UserDefineRefUtils.refreshBillCardBodyDefRefs4SingleRow((SuperVO)newVO,(getCardPanel().getRowCount("pk_task_b") - 1),
					(BillForm)billForm.getMainPanel(), "pk_task_b");
			List<Object> svoList = new ArrayList<>();
			//List<Object> rvoList = new ArrayList<>();
			svoList.addAll(pastedRVOs);
			UserDefineRefUtils.refreshBillCardGrandDefRefs(grandCard, "pk_task_r", svoList);
		}
		
	}
	private void fillPastedBodyVO(TaskBVO bodySource2) {
		if(null != bodySource2){
			int row = getCardPanel().getRowCount("pk_task_b") - 1;
			getCardPanel().setBodyValueAt(bodySource2.getTaskcode(), row, "taskcode", "pk_task_b");
			getCardPanel().setBodyValueAt(bodySource2.getTestitem(), row, "testitem", "pk_task_b");
			getCardPanel().setBodyValueAt(bodySource2.getStandardclause(), row, "standardclause", "pk_task_b");
			getCardPanel().setBodyValueAt(bodySource2.getPk_testresultname(), row, "pk_testresultname", "pk_task_b");
			getCardPanel().setBodyValueAt(bodySource2.getTestresultshortname(), row, "testresultshortname", "pk_task_b");
			getCardPanel().setBodyValueAt(bodySource2.getRunorder(), row, "runorder", "pk_task_b");
			getCardPanel().setBodyValueAt(bodySource2.getTestnumber(), row, "testnumber", "pk_task_b");
			getCardPanel().setBodyValueAt(bodySource2.getSampleallocation(), row, "sampleallocation", "pk_task_b");
			getCardPanel().setBodyValueAt(bodySource2.getSamplequantity(), row, "samplequantity", "pk_task_b");
			String uuid = UUID.randomUUID().toString();	
			uuid = uuid.replace("-", "");
			getCardPanel().setBodyValueAt(uuid, row, "uniquekey", "pk_task_b");
			getCardPanel().setBodyValueAt(VOStatus.NEW, row, "status", "pk_task_b");
		}
	}

	private void fillGrandPanel(TaskSVO[] taskSVOs,TaskRVO[] taskRVOs) {
		if(null == taskSVOs || taskSVOs.length <=0){
			return ;
		}
		String tablecode = this.getGrandCard().getBillCardPanel().getBodyPanel().getTableCode();
		if (!tablecode.equals("pk_task_s")) {
			this.getGrandCard().getBillCardPanel().getBodyTabbedPane().setSelectedIndex(0);
		}
		if(taskSVOs!=null){
			for(TaskSVO svo : taskSVOs){
				if(null == svo) {
					continue;
				}
				getGrandCard().getBillCardPanel().getBodyPanel("pk_task_s").addLine();
				int row = this.getGrandCard().getBillCardPanel().getRowCount("pk_task_s") - 1;
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getPk_testconditionitem(), row, "pk_testconditionitem", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getPk_testconditionitem(), row, "testconditionitem", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getIsoptional(), row, "isoptional", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getIsallow_out(), row, "isallow_out", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getPk_instrument(), row, "pk_instrument", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getPk_instrument(), row, "instrument", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getPk_valuetype(), row, "pk_valuetype", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getPk_valuetype(), row, "valuetype", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getValueways(), row, "valueways", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getTextvalue(), row, "textvalue", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getPk_refvalue(), row, "pk_refvalue", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getUnit(), row, "unit", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getFormatted_entry(), row, "formatted_entry", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getMax_limit(), row, "max_limit", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getMin_limit(), row, "min_limit", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getEnglishdescription(), row, "englishdescription", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getPk_list_table(), row, "pk_list_table", "pk_task_s");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(svo.getStatus(), row, "status", "pk_task_s");
			}
		}
		
		//切页签,继续新增
		if (!this.getGrandCard().getBillCardPanel().getBodyTabbedPane().getSelectedTableCode()
				.equals("pk_task_r")) {
			// MessageDialog.showErrorDlg(e.getContext().getEntranceUI(),
			// "提示", "请将孙表切换到试验后参数页签");
			this.getGrandCard().getBillCardPanel().getBodyTabbedPane().setSelectedIndex(1);
			// getGrandCard().getBillCardPanel().getTabbedPane(1).setSelectedIndex(1);
			// return;
		}
		if(taskRVOs!=null){
			for(TaskRVO rvo : taskRVOs){
				if(null == rvo) {
					continue;
				}
				getGrandCard().getBillCardPanel().getBodyPanel("pk_task_r").addLine();
				int row = this.getGrandCard().getBillCardPanel().getRowCount("pk_task_r") - 1;
				
				this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_samplegroup(), row, "pk_samplegroup", "pk_task_r");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_samplegroup(), row, "samplegroup", "pk_task_r");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_component(), row, "pk_component", "pk_task_r");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_component(), row, "component", "pk_task_r");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_valuetype(), row, "pk_valuetype", "pk_task_r");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_valuetype(), row, "valuetype", "pk_task_r");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getStdmaxvalue(), row, "stdmaxvalue", "pk_task_r");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getStdminvalue(), row, "stdminvalue", "pk_task_r");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_unit(), row, "pk_unit", "pk_task_r");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getJudgeflag(), row, "judgeflag", "pk_task_r");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getTestflag(), row, "testflag", "pk_task_r");
				this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_testtemperature(), row, "pk_testtemperature", "pk_task_r");
				
				this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getStatus(), row, "status", "pk_task_r");
			}
		}
	}
	 private void fireEvent() {
		    int rowCount = getCardPanel().getBillModel().getRowCount();
		    int pasteLineNumer = getCardPanel().getBillModel().getPasteLineNumer();
		    if (pasteLineNumer > 0) {
		      int[] rows = new int[pasteLineNumer];
		      for (int i = 0; i < pasteLineNumer; i++) {
		        rows[i] = (rowCount - pasteLineNumer + i);
		      }
		      getModel().fireEvent(new CardBodyAfterRowEditEvent(getCardPanel(), nc.ui.pubapp.uif2app.event.card.BodyRowEditType.PASTELINETOTAIL, rows));
		    }
		  }
}
