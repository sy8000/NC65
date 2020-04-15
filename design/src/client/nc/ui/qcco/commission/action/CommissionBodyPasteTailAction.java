package nc.ui.qcco.commission.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nc.bs.pubapp.utils.UserDefineRefUtils;
import nc.ui.pubapp.uif2app.actions.BodyPasteToTailAction;
import nc.ui.pubapp.uif2app.components.grand.CardGrandPanelComposite;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterRowEditEvent;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.vo.pub.ISuperVO;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.commission.CommissionRVO;

public class CommissionBodyPasteTailAction extends BodyPasteToTailAction {
	public CommissionBodyPasteTailAction(CardGrandPanelComposite billForm){
		this.billForm = billForm;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 8083283811257084498L;
	private CardGrandPanelComposite billForm;

	// private IDataOperationService service;

	public CardGrandPanelComposite getBillForm() {
		return billForm;
	}

	public void setBillForm(CardGrandPanelComposite billForm) {
		this.billForm = billForm;
	}
	private ShowUpableBillForm grandCard;
	
	public ShowUpableBillForm getGrandCard() {
		return grandCard;
	}

	public void setGrandCard(ShowUpableBillForm grandCard) {
		this.grandCard = grandCard;
	}
	@Override
	public void doAction(ActionEvent e) throws Exception {
		//super.doAction(e);
		//手动填充孙表
		//没找到选择第几行的...方法
		//getCardPanel().getBodyPanel("pk_task_b").delLine();
		int rowSource = getCardPanel().getBodyPanel().getTable().getSelectedRow();
		String order = String.valueOf(getCardPanel().getBillModel().getValueAt(rowSource, "samplegroup")) ;
		AggCommissionHVO aggvo = (AggCommissionHVO) this.getBillForm().getValue();
		ISuperVO[] bodyVOs = aggvo.getChildren(CommissionBVO.class);
		CommissionBVO newVO = null;
		if (bodyVOs != null && bodyVOs.length > 0) {
			List<CommissionRVO> pastedRVOs = new ArrayList<CommissionRVO>();
			for (ISuperVO bodyvo : bodyVOs) {
				String bodyOrder = String.valueOf(bodyvo.getAttributeValue("samplegroup")) ;
				if (bodyOrder.equals(order)) {
					CommissionBVO bodySource = (CommissionBVO) bodyvo;
					CommissionRVO[] taskRVOs = bodySource.getPk_commission_r();
					if(taskRVOs!=null){
						for (CommissionRVO rvo : taskRVOs) {
							CommissionRVO pvo = (CommissionRVO) rvo.clone();
							pvo.setPk_commission_b(null);
							pvo.setPk_commission_r(null);
							pvo.setStatus(VOStatus.NEW);
							pastedRVOs.add(pvo);
						}
					}
					
					// 复制出的数据有点问题,没办法添加孙表,手动复制
					getCardPanel().getBodyPanel("pk_commission_b").addLine();
					//updateStatus();
					newVO = (CommissionBVO)bodySource.clone();
					fillPastedBodyVO(newVO);
					fillGrandPanel(taskRVOs);
					fireEvent();
					break;
				}
			}
			CommissionBVO tgtVO = (CommissionBVO) getCardPanel().getBillModel().getBodyValueRowVO(lastPastedRow(),
					CommissionBVO.class.getName());
			tgtVO.setStatus(VOStatus.NEW);
			tgtVO.setPk_commission_r(pastedRVOs.toArray(new CommissionRVO[0]));
			
			//刷新操作
			UserDefineRefUtils.refreshBillCardBodyDefRefs4SingleRow((SuperVO)newVO,(getCardPanel().getRowCount("pk_commission_b") - 1),
					(BillForm)billForm.getMainPanel(), "pk_commission_b");
			List<Object> svoList = new ArrayList<>();
			//List<Object> rvoList = new ArrayList<>();
			svoList.addAll(pastedRVOs);
			UserDefineRefUtils.refreshBillCardGrandDefRefs(grandCard, "pk_commission_r", svoList);
			//billForm.showMeUp();
		}
		
	}
	private void fillPastedBodyVO(CommissionBVO bodySource2) {
		if(null != bodySource2){
			int row = getCardPanel().getRowCount("pk_commission_b") - 1;
			getCardPanel().setBodyValueAt(bodySource2.getPk_productserial(), row, "pk_productserial", "pk_commission_b");
			//getCardPanel().setBodyValueAt(bodySource2.getPk_productserial(), row, "productserial", "pk_commission_b");
			getCardPanel().setBodyValueAt(bodySource2.getPk_enterprisestandard(), row, "pk_enterprisestandard", "pk_commission_b");
			//getCardPanel().setBodyValueAt(bodySource2.getPk_enterprisestandard(), row, "enterprisestandard", "pk_commission_b");
			getCardPanel().setBodyValueAt(bodySource2.getTypeno(), row, "typeno", "pk_commission_b");
			getCardPanel().setBodyValueAt(bodySource2.getPk_productspec(), row, "pk_productspec", "pk_commission_b");
			//getCardPanel().setBodyValueAt(bodySource2.getPk_productspec(), row, "productspec", "pk_commission_b");
			getCardPanel().setBodyValueAt(bodySource2.getPk_structuretype(), row, "pk_structuretype", "pk_commission_b");
			//getCardPanel().setBodyValueAt(bodySource2.getPk_structuretype(), row, "structuretype", "pk_commission_b");
			getCardPanel().setBodyValueAt(bodySource2.getContacttype(), row, "contacttype", "pk_commission_b");
			//getCardPanel().setBodyValueAt(bodySource2.getContacttype(), row, "ref_contacttype", "pk_commission_b");
			getCardPanel().setBodyValueAt(bodySource2.getManufacturer(), row, "manufacturer", "pk_commission_b");
			getCardPanel().setBodyValueAt(bodySource2.getPk_contactbrand(), row, "pk_contactbrand", "pk_commission_b");
			//getCardPanel().setBodyValueAt(bodySource2.getPk_contactbrand(), row, "contactbrand", "pk_commission_b");
			getCardPanel().setBodyValueAt(bodySource2.getContactmodel(), row, "contactmodel", "pk_commission_b");
			getCardPanel().setBodyValueAt(bodySource2.getProductstage(), row, "productstage", "pk_commission_b");
			getCardPanel().setBodyValueAt(bodySource2.getPk_samplegroup(), row, "pk_samplegroup", "pk_commission_b");
			//getCardPanel().setBodyValueAt(bodySource2.getPk_samplegroup(), row, "samplegroup", "pk_commission_b");
			getCardPanel().setBodyValueAt(bodySource2.getQuantity(), row, "quantity", "pk_commission_b");
			getCardPanel().setBodyValueAt(bodySource2.getAnalysisref(), row, "analysisref", "pk_commission_b");
			getCardPanel().setBodyValueAt(bodySource2.getOtherinfo(), row, "otherinfo", "pk_commission_b");
			String uuid = UUID.randomUUID().toString();
			uuid = uuid.replace("-", "");
			getCardPanel().setBodyValueAt(uuid, row, "uniqueid", "pk_commission_b");
			getCardPanel().setBodyValueAt(VOStatus.NEW, row, "status", "pk_commission_b");
		}
	}

	private void fillGrandPanel(CommissionRVO[] taskRVOs) {
		if(null == taskRVOs || taskRVOs.length <=0){
			return ;
		}
		for(CommissionRVO rvo : taskRVOs){
			if(null == rvo) {
				continue;
			}
			getGrandCard().getBillCardPanel().getBodyPanel("pk_commission_r").addLine();
			int row = this.getGrandCard().getBillCardPanel().getRowCount("pk_commission_r") - 1;
			
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getAnalysisname(), row, "analysisname", "pk_commission_r");
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_samplegroup(), row, "pk_samplegroup", "pk_commission_r");
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_samplegroup(), row, "samplegroup", "pk_commission_r");
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_component(), row, "pk_component", "pk_commission_r");
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_component(), row, "component", "pk_commission_r");
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_valuetype(), row, "pk_valuetype", "pk_commission_r");
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getPk_valuetype(), row, "valuetype", "pk_commission_r");
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getStdmaxvalue(), row, "stdmaxvalue", "pk_commission_r");
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getStdminvalue(), row, "stdminvalue", "pk_commission_r");
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getUnitname(), row, "unitname", "pk_commission_r");
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getJudgeflag(), row, "judgeflag", "pk_commission_r");
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getTestflag(), row, "testflag", "pk_commission_r");
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getProductstage(), row, "productstage", "pk_commission_r");
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getIsautogeneration(), row, "isautogeneration", "pk_commission_r");
			
			
			this.getGrandCard().getBillCardPanel().setBodyValueAt(rvo.getStatus(), row, "status", "pk_commission_r");
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
