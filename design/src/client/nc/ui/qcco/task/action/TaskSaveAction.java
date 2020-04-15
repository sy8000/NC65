package nc.ui.qcco.task.action;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.bs.uif2.BusinessExceptionAdapter;
import nc.bs.uif2.IActionCode;
import nc.bs.uif2.validation.IValidationService;
import nc.bs.uif2.validation.ValidationException;
import nc.impl.qcco.TaskMaintainImpl;
import nc.itf.qcco.ITaskMaintain;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.ui.pubapp.uif2app.actions.DifferentVOSaveAction;
import nc.ui.pubapp.uif2app.actions.RefreshSingleAction;
import nc.ui.pubapp.uif2app.components.grand.CardGrandPanelComposite;
import nc.ui.pubapp.uif2app.components.grand.model.MainGrandModel;
import nc.ui.pubapp.uif2app.view.ShowUpableBillForm;
import nc.ui.uif2.IShowMsgConstant;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.UIState;
import nc.ui.uif2.actions.ActionInitializer;
import nc.util.mmpub.dpub.gc.GCClientBillCombinServer;
import nc.util.mmpub.dpub.gc.GCClientBillToServer;
import nc.util.mmpub.dpub.gc.GCPseudoColUtil;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.ISuperVO;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pattern.model.entity.bill.IBill;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.task.AggTaskHVO;
import nc.vo.qcco.task.TaskBVO;
import nc.vo.qcco.task.TaskRVO;
import nc.vo.qcco.task.TaskSVO;

public class TaskSaveAction extends DifferentVOSaveAction {
	/**
	 * SaveAction DifferentVOSaveAction
	 */
	private static final long serialVersionUID = -6804039169256642670L;
	private ShowUpableBillForm grandCard;// mainBillForm
	private MainGrandModel mainGrandModel;
	private CardGrandPanelComposite billForm;
	private ShowUpableBillForm billFormEditor;
	//private IDataOperationService service;
	private RefreshSingleAction refresh;

	public RefreshSingleAction getRefresh() {
		return refresh;
	}

	public void setRefresh(RefreshSingleAction refresh) {
		this.refresh = refresh;
	}

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

	public ShowUpableBillForm getGrandCard() {
		return grandCard;
	}

	public void setGrandCard(ShowUpableBillForm grandCard) {
		this.grandCard = grandCard;
	}

	IValidationService validationService;

	public TaskSaveAction() {
		super();
		ActionInitializer.initializeAction(this, IActionCode.SAVE);
	}

	// ע�⽫�����XXX��������
	@Override
	public void doAction(ActionEvent e) throws Exception {
		
		
		
		this.billFormEditor.getBillCardPanel().stopEditing();
		AggTaskHVO agghvo = (AggTaskHVO)this.getBillForm().getValue();
		AggTaskHVO origanVO= (AggTaskHVO)getModel().getSelectedData();
		if(null == agghvo){
			return;
		}
		if(origanVO != null && origanVO.getParentVO()!=null){
			agghvo.getParentVO().setCreator(origanVO.getParentVO().getCreator());
			agghvo.getParentVO().setCreationtime(origanVO.getParentVO().getCreationtime());
			agghvo.getParentVO().setBillmaker(origanVO.getParentVO().getBillmaker());
		}
		this.validate(agghvo);
		if (this.getModel().getUiState() == UIState.ADD) {
			//this.excuteInsert(agghvo);
			GCPseudoColUtil.getInstance().setPseudoColInfo(agghvo);
			doAddSave(agghvo);
			this.getMainGrandModel().clearBufferData();
		} else if (this.getModel().getUiState() == UIState.EDIT) {
			GCPseudoColUtil.getInstance().setPseudoColInfo(agghvo);
			doEditSave(agghvo);
			this.getMainGrandModel().clearBufferData();
		}
		refresh.doAction(e);
		showSuccessInfo();
	}
	

	

	@Override
	protected void doAddSave(Object value) throws Exception {
		IBill[] clientVOs = { (IBill)value };


		GCClientBillToServer<IBill> tool = new GCClientBillToServer<IBill>();


	    IBill[] lightVOs = tool.constructInsert(clientVOs);

	    IBill[] afterUpdateVOs = null;

	    

	    if (getService() == null) {
	      throw new BusinessException("service����Ϊ�ա�");
	    }
	    afterUpdateVOs = getService().insert(lightVOs);
	    


		// tank ���µ���״̬ 2019��9��9��14:45:32
		if (afterUpdateVOs != null && afterUpdateVOs.length > 0) {
			for (IBill bill : afterUpdateVOs) {
				ITaskMaintain taskMaintain = NCLocator.getInstance().lookup(ITaskMaintain.class);
				taskMaintain.updateBillStatus(-1, bill.getPrimaryKey());
			}
		}

	    new GCClientBillCombinServer<IBill>().combine(clientVOs, afterUpdateVOs);

	    getModel().setUiState(UIState.NOT_EDIT);
	    getMainGrandModel().directlyAdd(clientVOs[0]);
	}

	@Override
	protected void doEditSave(Object value) throws Exception {
	    IBill[] clientVOs = { (IBill)value };



	    GCClientBillToServer tool = new GCClientBillToServer();

	    IBill[] oldVO = { (IBill)getModel().getSelectedData() };



	    IBill[] lightVOs = tool.construct(oldVO, clientVOs);

	    IBill[] afterUpdateVOs = null;

	    

	    if (getService() == null) {
	      throw new BusinessException("service����Ϊ�ա�");
	    }
	    afterUpdateVOs = getService().update(lightVOs);


	    
		// tank ���µ���״̬ 2019��9��9��14:45:32
		if (afterUpdateVOs != null && afterUpdateVOs.length > 0) {
			for (IBill bill : afterUpdateVOs) {
				ITaskMaintain taskMaintain = NCLocator.getInstance().lookup(ITaskMaintain.class);
				taskMaintain.updateBillStatus(-1, bill.getPrimaryKey());
			}
		}
	    
	    new GCClientBillCombinServer<IBill>().combine(clientVOs, afterUpdateVOs);

	    getModel().setUiState(UIState.NOT_EDIT);
	    List<AggTaskHVO> clientVOsList = new ArrayList();
		for (IBill bill : clientVOs) {
			if (bill instanceof AggTaskHVO) {
				clientVOsList.add((AggTaskHVO) bill);
			}
		}
	    getMainGrandModel().getMainModel().initModel(clientVOsList.toArray(new AggTaskHVO[0]));;
	}

	protected void showSuccessInfo() {
		ShowStatusBarMsgUtil.showStatusBarMsg(IShowMsgConstant.getSaveSuccessInfo(), getMainGrandModel().getMainModel().getContext());
//		// ����Ϣ���ֶ����ر�־λ��λ
//		if (getExceptionHandler() instanceof DefaultExceptionHanler) {
//			((DefaultExceptionHanler) getExceptionHandler()).setAutoClearError(true);
//		}
	}

	/**
	 * �˷����ڵ���ģ�͵�add��update���á������Դӱ༭����ȡ����value�������У�顣
	 * @param value
	 */
	protected void validate(Object value) { 
		if(validationService!=null)
		{
			try {
				validationService.validate(value);
				validateGrand(value);
			} catch (ValidationException e) {
				throw new BusinessExceptionAdapter(e);
			}
		}
	}


	@SuppressWarnings("unchecked")
	private void validateGrand(Object value) throws BusinessExceptionAdapter{
		// ���Ƿ��ѡ��û���ģ����Ǳ��������˼�������ʱ���У��ֵ����Ϊ��
		if(value!=null && value instanceof AggTaskHVO){
			AggTaskHVO aggvo = (AggTaskHVO)value;
			if(aggvo.getChildren(TaskBVO.class)!=null){
				ISuperVO[] superVOs = aggvo.getChildren(TaskBVO.class);
				if(superVOs.length > 0){
					//ֵ����
					IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
					Map<String,String> typePk2NameMap = new HashMap<>();
					try {
						List<Map<String,String>> rsList = 
								(List<Map<String,String>>)bs.executeQuery("select nc_result_namecn, pk_result_type  from nc_result_type ",new MapListProcessor());
						if(rsList!=null && rsList.size() > 0){
							for(Map<String,String> map : rsList){
								typePk2NameMap.put(map.get("pk_result_type"), 
										map.get("nc_result_namecn")==null?null:map.get("nc_result_namecn").replaceAll(" ", ""));
							}
						}
					} catch (BusinessException e) {
						ExceptionUtils.wrappException(e);
					}
					for(ISuperVO superVO : superVOs){
						TaskBVO bvo = (TaskBVO)superVO;
						if(bvo!=null && bvo.getPk_task_s()!=null && bvo.getPk_task_s().length > 0){
							TaskSVO[] svos =  bvo.getPk_task_s();
							List<TaskSVO> tempList = new ArrayList<>();
							for(TaskSVO svo : svos){
								if(svo!=null){
									if(svo.getIsoptional()!=null && svo.getIsoptional().booleanValue()){
										String typeName = typePk2NameMap.get(svo.getPk_valuetype()==null?"":svo.getPk_valuetype());
										typeName = (typeName==null?"":typeName);
										//��ѡ��"�Ƿ��ѡ"�����ݣ�����ı�ֵ�Ͳ���ֵ��Ϊ�գ���������������,(��Ϊ������)
										boolean isValueNull = (svo.getTextvalue()==null ||svo.getTextvalue().equals(""))
												&&(svo.getPk_refvalue()==null || svo.getPk_refvalue().equals(""))
												&&(!typeName.equals("������"));
										if(isValueNull){
											continue;
										}
									}
									tempList.add(svo);
									if(svo.getIsoptional()==null || !(svo.getIsoptional().booleanValue())){
										if("duration".equals(svo.getPk_testconditionitem())
												|| "����ʱ��".equals(svo.getPk_testconditionitem())
												|| "Duration".equals(svo.getPk_testconditionitem())){
											//�����ɺ�̨���
											;
										}else{
											//������,�ı�ֵ�����ֵ����ͬʱΪ��
											if((svo.getTextvalue()==null||"".equals(svo.getTextvalue()))&&svo.getPk_refvalue()==null ){
												throw new BusinessExceptionAdapter(new BusinessException("����:["+bvo.getTestitem()+"],����������:["+svo.getPk_testconditionitem()+"],ֵ����Ϊ��!"));
											}
										}
										
									}
									//������ֵ����ֵ��ֵ,ֻ��Ϊ����
									if(svo.getTextvalue()!=null && 
											svo.getPk_valuetype()!=null && typePk2NameMap.get(svo.getPk_valuetype())!=null){
										String typeName = typePk2NameMap.get(svo.getPk_valuetype());
										if("��ֵ".equals(typeName)||"������".equals(typeName)){
											try{
												if(svo.getTextvalue()!=null){
													Double.parseDouble(svo.getTextvalue().toString());
												}
												if(svo.getMax_limit()!=null){
													Double.parseDouble(svo.getMax_limit().toString());
												}
												if(svo.getMin_limit()!=null){
													Double.parseDouble(svo.getMin_limit().toString());
												}
												
											}catch(Exception e){
												throw new BusinessExceptionAdapter(new BusinessException("����:["+bvo.getTestitem()+"],����������:["+svo.getPk_testconditionitem()
														+"],�ı�ֵ�����С��ֵ����Ϊ����!"));
											}
										}
									}
									//���պ��ı�����ͬʱ��ֵ
									if(svo.getTextvalue()!=null&& !"".equals(svo.getTextvalue()) 
											&& svo.getPk_refvalue()!=null && !"".equals(svo.getPk_refvalue())){
										throw new BusinessExceptionAdapter(new BusinessException("����:["+bvo.getTestitem()+"],����������:["+svo.getPk_testconditionitem()
												+"],���պ��ı�����ͬʱ��ֵ!"));
									}
								}
							}
							TaskRVO[] rvos =  bvo.getPk_task_r();
							for(TaskRVO rvo : rvos){
								if(rvo!=null){
									//������ֵ����ֵ��ֵ,ֻ��Ϊ����
									if( rvo.getPk_valuetype()!=null && typePk2NameMap.get(rvo.getPk_valuetype())!=null){
										String typeName = typePk2NameMap.get(rvo.getPk_valuetype());
										if("��ֵ".equals(typeName)||"������".equals(typeName)){
											try{
												if(rvo.getStdmaxvalue()!=null){
													Double.parseDouble(rvo.getStdmaxvalue().toString());
												}
												if(rvo.getStdminvalue()!=null){
													Double.parseDouble(rvo.getStdminvalue().toString());
												}
												
											}catch(Exception e){
												throw new BusinessExceptionAdapter(new BusinessException("����:["+bvo.getTestitem()+"],��������:["+rvo.getAttributeValue("component")
														+"],���С��ֵ����Ϊ����!"));
											}
										}
									}
								}
							}
							bvo.setPk_task_s(tempList.toArray(new TaskSVO[0]));
						}
					}
				}
			}
		}
		
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
