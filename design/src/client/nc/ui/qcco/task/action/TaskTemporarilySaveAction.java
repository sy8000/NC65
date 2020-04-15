package nc.ui.qcco.task.action;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.KeyStroke;

import nc.bs.framework.common.NCLocator;
import nc.bs.uif2.validation.IValidationService;
import nc.itf.qcco.ITaskMaintain;
import nc.ui.pubapp.uif2app.actions.DifferentVOSaveAction;
import nc.ui.pubapp.uif2app.actions.RefreshSingleAction;
import nc.ui.pubapp.uif2app.components.grand.CardGrandPanelComposite;
import nc.ui.pubapp.uif2app.components.grand.model.MainGrandModel;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.uif2.IShowMsgConstant;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.UIState;
import nc.util.mmpub.dpub.gc.GCClientBillCombinServer;
import nc.util.mmpub.dpub.gc.GCClientBillToServer;
import nc.util.mmpub.dpub.gc.GCPseudoColUtil;
import nc.vo.pub.BusinessException;
import nc.vo.pubapp.pattern.model.entity.bill.IBill;
import nc.vo.qcco.task.AggTaskHVO;

public class TaskTemporarilySaveAction extends DifferentVOSaveAction {
	/**
	 * SaveAction DifferentVOSaveAction
	 */
	private static final long serialVersionUID = -6804039169256642670L;

	private MainGrandModel mainGrandModel;
	private CardGrandPanelComposite billForm;
	private ShowUpableBillForm billFormEditor;
	
	private RefreshSingleAction refresh;

	public RefreshSingleAction getRefresh() {
		return refresh;
	}

	public void setRefresh(RefreshSingleAction refresh) {
		this.refresh = refresh;
	}

	// private IDataOperationService service;

	public CardGrandPanelComposite getBillForm() {
		return billForm;
	}

	public void setBillForm(CardGrandPanelComposite billForm) {
		this.billForm = billForm;
	}

	public ShowUpableBillForm getBillFormEditor() {
		return billFormEditor;
	}

	public void setBillFormEditor(ShowUpableBillForm billFormEditor) {
		this.billFormEditor = billFormEditor;
	}

	IValidationService validationService;

	public TaskTemporarilySaveAction() {
		super();
		String name = "暂存"
		/* @res "暂存" */;
		setBtnName(name);
		setCode("TaskTemporarilySave");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('I', Event.CTRL_MASK));
		putValue(Action.SHORT_DESCRIPTION, name + "(Ctrl+I)");
	}

	// 注意将孙面板XXX属性设置
	@Override
	public void doAction(ActionEvent e) throws Exception {

		this.billFormEditor.getBillCardPanel().stopEditing();
		AggTaskHVO agghvo = (AggTaskHVO) this.getBillForm().getValue();
		if (null == agghvo) {
			return;
		}
		// this.validate(agghvo);
		if (this.getModel().getUiState() == UIState.ADD) {
			// this.excuteInsert(agghvo);
			GCPseudoColUtil.getInstance().setPseudoColInfo(agghvo);
			doAddSave(agghvo);
			this.getMainGrandModel().clearBufferData();
		} else if (this.getModel().getUiState() == UIState.EDIT) {
			
			ITaskMaintain t = NCLocator.getInstance().lookup(ITaskMaintain.class);
		    t.cleanTaskTempSaveTaskid((String)agghvo.getParent().getAttributeValue("billno"));
		    
			GCPseudoColUtil.getInstance().setPseudoColInfo(agghvo);
			doEditSave(agghvo);
			this.getMainGrandModel().clearBufferData();
		}
		refresh.doAction(e);
		showSuccessInfo();
	}

	@Override
	protected void doAddSave(Object value) throws Exception {
		IBill[] clientVOs = { (IBill) value };

		GCClientBillToServer<IBill> tool = new GCClientBillToServer<IBill>();

		IBill[] lightVOs = tool.constructInsert(clientVOs);

		IBill[] afterUpdateVOs = null;

		if (getService() == null) {
			throw new BusinessException("service不能为空");
		}
		afterUpdateVOs = getService().insert(lightVOs);
		
		
		
		// tank 更新单据状态 2019年9月9日14:45:32
		if (afterUpdateVOs != null && afterUpdateVOs.length > 0) {
			for (IBill bill : afterUpdateVOs) {
				ITaskMaintain taskMaintain = NCLocator.getInstance().lookup(ITaskMaintain.class);
				taskMaintain.updateBillStatus(-99, bill.getPrimaryKey());
			}
		}
		
		
		new GCClientBillCombinServer<IBill>().combine(clientVOs, afterUpdateVOs);

		getModel().setUiState(UIState.NOT_EDIT);
		getMainGrandModel().directlyAdd(clientVOs[0]);
	}

	@Override
	protected void doEditSave(Object value) throws Exception {
		IBill[] clientVOs = { (IBill) value };

		GCClientBillToServer tool = new GCClientBillToServer();

		IBill[] oldVO = { (IBill) getModel().getSelectedData() };

		IBill[] lightVOs = tool.construct(oldVO, clientVOs);

		IBill[] afterUpdateVOs = null;

		if (getService() == null) {
			throw new BusinessException("Service未找到");
		}
		afterUpdateVOs = getService().update(lightVOs);
		
		
		
		//tank 更新单据状态 2019年9月9日14:45:32
		if(afterUpdateVOs!=null && afterUpdateVOs.length > 0){
			for(IBill bill : afterUpdateVOs){
				ITaskMaintain taskMaintain = NCLocator.getInstance().lookup(ITaskMaintain.class);
				taskMaintain.updateBillStatus(-99, bill.getPrimaryKey());
			}
		}
		
		new GCClientBillCombinServer<IBill>().combine(clientVOs, afterUpdateVOs);
		//更新时，按插入逻辑，生成新的pk
		getModel().setUiState(UIState.NOT_EDIT);
	    List<AggTaskHVO> clientVOsList = new ArrayList<>();
		for (IBill bill : clientVOs) {
			if (bill instanceof AggTaskHVO) {
				clientVOsList.add((AggTaskHVO) bill);
			}
		}
	    getMainGrandModel().getMainModel().initModel(clientVOsList.toArray(new AggTaskHVO[0]));;
	}

	protected void showSuccessInfo() {
		ShowStatusBarMsgUtil.showStatusBarMsg(IShowMsgConstant.getSaveSuccessInfo(), getMainGrandModel().getMainModel()
				.getContext());
	}

	public MainGrandModel getMainGrandModel() {
		return mainGrandModel;
	}

	public void setMainGrandModel(MainGrandModel mainGrandModel) {
		this.mainGrandModel = mainGrandModel;
	}

	public IValidationService getValidationService() {
		return validationService;
	}

	public void setValidationService(IValidationService validationService) {
		this.validationService = validationService;
	}
}