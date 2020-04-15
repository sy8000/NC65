package nc.ui.qcco.commission.action;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import nc.bs.framework.common.NCLocator;
import nc.bs.pubapp.utils.UserDefineRefUtils;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pubapp.uif2app.AppUiState;
import nc.ui.pubapp.uif2app.components.grand.CardGrandPanelComposite;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterRowEditEvent;
import nc.ui.pubapp.uif2app.model.IAppModelEx;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.qcco.utils.CommisionUtils;
import nc.ui.uif2.UIState;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ISuperVO;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.commission.CommissionRVO;

import org.apache.commons.lang.SerializationUtils;

public class CommissionCopyAction extends
		nc.ui.pubapp.uif2app.actions.CopyAction {
	private CardGrandPanelComposite billForm;


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

	public CommissionCopyAction() {
		super();
	}

	public void doAction(ActionEvent e) throws Exception {

		Object object = getModel().getSelectedData();
		if (object instanceof AbstractBill)

		{
			AbstractBill aggvo = (AbstractBill) SerializationUtils
					.clone((Serializable) object);
			//截取初始父vo，用户要求一致的数据
			String creater = (String)aggvo.getParentVO().getAttributeValue("creator");
			String cuserid = (String)aggvo.getParentVO().getAttributeValue("cuserid");
			String contract = (String)aggvo.getParentVO().getAttributeValue("contract");
			String email = (String)aggvo.getParentVO().getAttributeValue("email");
			String mobile = (String)aggvo.getParentVO().getAttributeValue("mobile");

			if (getCopyActionProcessor() != null) {
				getCopyActionProcessor().processVOAfterCopy(aggvo,
						getModel().getContext());

			}

			if (getModel() instanceof IAppModelEx) {
				getModel().setOtherUiState(UIState.COPY_ADD);
				getModel().setAppUiState(AppUiState.COPY_ADD);
			} else {
				getModel().setUiState(UIState.ADD);

			}
			
			//获取新的code
			CommisionUtils projNo = new CommisionUtils();
			aggvo.getParentVO().setAttributeValue("billno", projNo.getCommissionPreCode("A"));
			
			aggvo.getParentVO().setAttributeValue("creater",creater);
			aggvo.getParentVO().setAttributeValue("cuserid",cuserid);
			aggvo.getParentVO().setAttributeValue("contract",contract);
			aggvo.getParentVO().setAttributeValue("email",email);
			aggvo.getParentVO().setAttributeValue("mobile",mobile);
			//billForm.setValue(aggvo);
			ISuperVO[] bodyVOs = aggvo.getChildren(CommissionBVO.class);
			//aggvo.setChildren(CommissionBVO.class, null);
			getEditor().setValue(aggvo);
			
			getEditor().getBillCardPanel().getBillTable().selectAll();
			getEditor().getBillCardPanel().delLine();
			getEditor().setBodyStatusNew();
			
			
			
			//getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").addLine();
			
			CommissionBVO newVO = null;
			if (bodyVOs != null && bodyVOs.length > 0) {
				List<CommissionRVO> pastedRVOs = null;
				
				for (ISuperVO bodyvo : bodyVOs) {
					pastedRVOs = new ArrayList<CommissionRVO>();
						CommissionBVO bodySource = (CommissionBVO) bodyvo;
						CommissionRVO[] taskRVOs = bodySource.getPk_commission_r();
						if(null == taskRVOs){
							IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
							@SuppressWarnings("unchecked")
							List<CommissionRVO> rvo  =
							(List<CommissionRVO>)bs.executeQuery(" select * from qc_commission_r where dr = 0 and  pk_commission_b = '"+bodySource.getPk_commission_b()+"'", 
									new BeanListProcessor(CommissionRVO.class));
							if(rvo!=null && rvo.size() > 0){
								taskRVOs = rvo.toArray(new CommissionRVO[0]);
							}
						}
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
						getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").addLine();
						//getEditor().getBillCardPanel().getBillTable().setColumnSelectionInterval(this.getCardPanel().getRowCount() - 1,1); 
						//updateStatus();
						newVO = (CommissionBVO)bodySource.clone();
						fillPastedBodyVO(newVO);
						fillGrandPanel(taskRVOs);
						//getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").addLine();
						//fireEvent();
						//刷新操作
						UserDefineRefUtils.refreshBillCardBodyDefRefs4SingleRow((SuperVO)newVO,(getEditor().getBillCardPanel().getRowCount("pk_commission_b") - 1),
								(BillForm)billForm.getMainPanel(), "pk_commission_b");
						List<Object> svoList = new ArrayList<>();
						//List<Object> rvoList = new ArrayList<>();
						svoList.addAll(pastedRVOs);
						UserDefineRefUtils.refreshBillCardGrandDefRefs(grandCard, "pk_commission_r", svoList);
				}
				
				//getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").addLine();
				//getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").getTable().getTableHeader().repaint();
				//getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").getTable().changeSelection(0, 0, false, false);
				//唉... 2019年9月10日00:04:19
				getModel().setOtherUiState(UIState.NOT_EDIT);
				getBillForm().getAutoShowUpComponent().showMeUp();
				/*AggCommissionHVO aggvo4Fresh = (AggCommissionHVO)aggvo.clone();
				List<CommissionBVO> bvolist = new ArrayList<>();
				for (int row = 0; row < getCardPanel().getRowCount("pk_commission_b"); row++) {
					
					SuperVO bodyVO = (SuperVO) billForm.getBillCardPanel().getBillModel(tabCode)
							.getBodyValueRowVO(row, bodyVOClass.getName());
					bvolist.add(bodyVO);
				}*/
				UserDefineRefUtils.refreshBillCardBodyDefRefs(
						(AggCommissionHVO)(billForm.getValue()),
						(BillForm)billForm.getMainPanel(), "pk_commission_b", CommissionBVO.class);
				throw new BusinessException();
				
				
				//getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").d
				//getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").delLine(new int[] { this.getCardPanel().getRowCount()});
				//getEditor().getBillCardPanel().getBodyPanel("pk_commission_b")
				//.delLine(new int[] { this.getCardPanel().getRowCount() - 1 });
				//getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").addLine();
				//getEditor().getBillCardPanel().getBodyPanel("pk_commission_b")
				//.delLine(new int[] { this.getCardPanel().getRowCount() - 1 });
				//fireEvent();
				//getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").addLine();
				//getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").addLine();
				/*getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").addLine();
				getEditor().getBillCardPanel().getBodyPanel("pk_commission_b")
				.delLine(new int[] { this.getCardPanel().getRowCount() - 1 });*/
//				getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").delLine();
//				getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").delLine();
				//fireEvent();
				//getEditor().getBillCardPanel().getBodyPanel("pk_commission_b").delLine();
				//fireEvent();
				//billForm.showMeUp();
				//billForm.showMeUp();
			}
			
			
			
		} else {
			throw new BusinessException(NCLangRes4VoTransl.getNCLangRes()
					.getStrByID("pubapp_0", "0pubapp-0126"));
			
		}
		
		
	}
	private BillCardPanel getCardPanel() {
		return getEditor().getBillCardPanel();
	}
	
	private void fillPastedBodyVO(CommissionBVO bodySource2) {
		if(null != bodySource2){
			int row = getCardPanel().getRowCount("pk_commission_b") - 1;
			getCardPanel().setBodyValueAt(row+1, row, "rowno", "pk_commission_b");
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
