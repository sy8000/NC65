package nc.ui.pubapp.uif2app.components.grand;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import nc.bs.logging.Logger;
import nc.bs.pubapp.utils.UserDefineRefUtils;
import nc.desktop.ui.WorkbenchEnvironment;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pubapp.uif2app.components.grand.action.ExpendShrinkGrandListAction;
import nc.ui.pubapp.uif2app.components.grand.model.MainGrandAppEvent;
import nc.ui.pubapp.uif2app.components.grand.model.MainGrandEventTypeEnum;
import nc.ui.pubapp.uif2app.components.grand.model.MainGrandStatusTypeEnum;
import nc.ui.pubapp.uif2app.components.grand.util.CardPanelEventUtil;
import nc.ui.pubapp.uif2app.event.list.ListBodyRowChangedEvent;
import nc.ui.pubapp.uif2app.event.list.ListBodyTabChangedEvent;
import nc.ui.pubapp.uif2app.event.list.ListHeadRowChangedEvent;
import nc.ui.pubapp.uif2app.query2.model.IModelDataManager;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.UIState;
import nc.ui.uif2.components.AutoShowUpEventSource;
import nc.ui.uif2.components.IAutoShowUpEventListener;
import nc.ui.uif2.editor.BillListView;
import nc.ui.uif2.model.AppEventConst;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.commission.CommissionHVO;
import nc.vo.qcco.task.AggTaskHVO;
import nc.vo.qcco.task.TaskBVO;
import nc.vo.qcco.task.TaskHVO;
import nc.vo.uif2.AppStatusRegisteryCallback;

import org.jfree.util.Log;

/**
 * �������б����
 */
public class ListGrandPanelComposite extends GrandPanelComposite {
	private static final long serialVersionUID = 1480002637488567694L;

	// �б�չ������Action
	private ExpendShrinkGrandListAction expendShrinkGrandListAction = null;

	public JPanel switchGrandPanel;

	protected ListGrandPanel expendPane;

	private AutoShowUpEventSource autoShowUpComponent = new AutoShowUpEventSource(this);
	
	private IModelDataManager dataManager;
	
	
	

	public IModelDataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(IModelDataManager dataManager) {
		this.dataManager = dataManager;
	}

	@Override
	public void initUI() {
		// ��ȡ������Ϣ
		this.readListCacheInfo();
		// �������������
		super.initUI();
		// ��Ӽ���
		this.initListListener();
		// ���ø�����������model
		this.getMainGrandAssist().setMainGrandModel(this.getModel());
		// ���ûص�����(���ڼ�¼�ϴδ򿪽���״̬)
		this.registeCallbak();
		
	}

	/**
	 * ��ȡ�б�����Ϣ
	 */
	public void readListCacheInfo() {
		int lastsize = this.getLastSize();
		if (lastsize == 0 || lastsize < 0) {
			this.getModel().setMainGrandListEnumState(MainGrandStatusTypeEnum.list_main_show_grand_hide);
			this.getListCardSplitPane().setEnabled(false);
		} else {
			this.getModel().setMainGrandListEnumState(MainGrandStatusTypeEnum.list_main_show_grand_show);
			this.getListCardSplitPane().setDividerLocation(lastsize);
			this.setCurrentLocation(lastsize);
			if (this.getModel().getListGrandEnumState() == MainGrandStatusTypeEnum.list_grand_hide) {
				this.getListCardSplitPane().setEnabled(false);
			} else {
				this.getListCardSplitPane().setEnabled(true);
			}
		}
	}

	@Override
	public Component getGrandPanel() {
		if (this.expendPane != null) {
			if (this.expendPane.getGrandComoPanel() != null
					&& this.expendPane.getGrandComoPanel().getRightComponent() != null) {
				if (this.getModel().getListGrandEnumState() == MainGrandStatusTypeEnum.list_grand_show) {
					// ���model��ǰ����״̬Ϊ���ڵ����������������Ϊtrue
					this.expendPane.getGrandComoPanel().getRightComponent().setVisible(true);
				} else {
					this.expendPane.getGrandComoPanel().getRightComponent().setVisible(false);
				}
			}
		} else {
			// ��XML�ж�ȡ��Ϣ
			this.readListGrandConfigXML();
			// �ж��б��µ���ʾ״̬�����Ϊ0��ʶ����ʾ�б�״̬�������Ϣ
			if (this.getModel().getListGrandEnumState() == MainGrandStatusTypeEnum.list_grand_hide) {
				this.expendPane.getGrandComoPanel().getRightComponent().setVisible(false);

				this.listPanelBeforeResize();
			} else {
				this.expendPane.getGrandComoPanel().getRightComponent().setVisible(true);
			}
		}

		return this.expendPane;
	}

	/**
	 * ��ȡ�����ļ�XML�й����б����Ϣ
	 */
	public void readListGrandConfigXML() {
		Map<String, Object> panelmap = this.getMaingrandrelationship().getBodyTabTOGrandListComposite();

		Iterator<String> keys = panelmap.keySet().iterator();
		this.switchGrandPanel = new UIPanel();
		this.switchGrandPanel.setLayout(new CardLayout());

		this.expendPane = new ListGrandPanel();
		this.expendPane.setGrandlabel(new JLabel(this.getGrandString()));
		this.expendPane.setExpendShrinkAction(this.expendShrinkGrandListAction);

		this.getModel().addAppEventListener(this.expendPane);
		// ������model�ɷ��¼�
		this.getModel().mainListGrandInit();
		JSplitPane splitane = this.expendPane.getGrandComoPanel();
		splitane.setEnabled(false);
		if (this.getModel().getListGrandEnumState() == MainGrandStatusTypeEnum.list_grand_hide) {
			// ��������ʾ���������Ϊfalse
			splitane.setBottomComponent(this.switchGrandPanel);
			splitane.getBottomComponent().setVisible(true);
		} else {
			splitane.setBottomComponent(this.switchGrandPanel);
		}

		while (keys.hasNext()) {
			String uniquekey = keys.next();
			Object component = panelmap.get(uniquekey);
			Object addcomponent = null;
			if (component instanceof BillListView) {
				addcomponent = ((BillListView) component).getBillListPanel();
				// ���б�ҳǩ�л���Ӽ���
				((BillListView) component).getBillListPanel().getBodyTabbedPane().addChangeListener(this.getMediator());
			} else if (component instanceof BillForm) {
				addcomponent = ((BillForm) component).getBillCardPanel();
			}
			this.switchGrandPanel.add((Component) addcomponent, uniquekey);
		}
	}

	/**
	 * ��ʼ������������б���Ϣ
	 */
	protected void initListListener() {
		// ������model��Ӽ���
		((BillListView) this.mainPanel).getModel().addAppEventListener(this);
	}

	@Override
	public void paint(Graphics g) {

		super.paint(g);

		String currentBodyTab = ((BillListView) this.getMainPanel()).getBillListPanel().getChildListPanel()
				.getTableCode();
		Component grandComposite = (Component) this.getMaingrandrelationship().getBodyTabTOGrandCardComposite()
				.get(currentBodyTab);
		if (this.getGrandPanel() != null && grandComposite != null && isNeedToRepaint()) {

			if (this.getModel().getListGrandEnumState() == MainGrandStatusTypeEnum.list_grand_hide) {

				setGrandHideStatus(true);

			} else if (this.getModel().getCardGrandEnumState() == MainGrandStatusTypeEnum.card_grand_show) {

				if (getCurrentLocation() < 1 && getCurrentLocation() > 0.9) {

					this.getListCardSplitPane().setDividerLocation(0.7);
				} else {
					// �Ǳ���ֵʱ
					//mod �ͻ�Ҫ��,���ÿ�ζ���Ҫչ�� tank 2020��3��17�� 21:41:59
					setCurrentLocation(0.7f);
					this.getListCardSplitPane().setDividerLocation(getCurrentLocation());
				}
			}
			
			this.getListCardSplitPane().setDividerLocation(0.7);
			this.setNeedToRepaint(false);
			getModel().setMainGrandListEnumState(MainGrandStatusTypeEnum.list_main_show_grand_show);
			getModel().grandListExpend();
			//mod end �ͻ�Ҫ��,���ÿ�ζ���Ҫչ�� tank 2020��3��17�� 21:41:59
		}
	}

	/**
	 * ��������ͼ�б���
	 */
	public void shrinkGrandComboList() {
		((ListGrandPanel) this.getGrandPanel()).shrinkGrandListComboPanel();
		this.setCurrentLocation(this.getListCardSplitPane().getDividerLocation());
		this.setGrandPanelMin();
	}

	/**
	 * ��������
	 */
	@Override
	public boolean setGrandPanelMax() {
		boolean ret = false;
		if (this.getModel().getListGrandEnumState() != MainGrandStatusTypeEnum.list_grand_hide) {// �����Ϊ��ʾ״̬�򲻽�����ʾ
			this.getListCardSplitPane().setResizeWeight(0);
			this.getListCardSplitPane().setDividerLocation(0);
			this.getListCardSplitPane().resetToPreferredSizes();
		}
		ret = true;
		return ret;
	}

	@Override
	public void showMeUp() {
		// ��ת�������ݴ�����棬���ذ�ť�ڱ༭̬Ҳ�ǿɼ��ģ������ڷ����л��б�����ʱ��
		// Ҫ��UI״̬�޸ĳɷǱ༭̬���������İ�ť��ʾ��������⡣
		if (((BillListView) this.getMainPanel()).getModel() != null
				&& ((BillListView) this.getMainPanel()).getModel().getUiState() != UIState.NOT_EDIT) {
			((BillListView) this.getMainPanel()).getModel().setUiState(UIState.NOT_EDIT);
		}
		if (!this.getModel().isAddReturnList()) {// ����Ǳ༭���أ������һ���¼�ת��
			// �����иı��¼�
			int currentRow = ((BillListView) this.mainPanel).getBillListPanel().getBodyTable().getSelectedRow();
			ListBodyRowChangedEvent event = new ListBodyRowChangedEvent(
					((BillListView) this.mainPanel).getBillListPanel(), new BillEditEvent(this, currentRow, currentRow));
			this.getModel().getMainModel().fireEvent(event);
		}
		this.autoShowUpComponent.showMeUp();
		
		try {
			//ˢ������
			if (getDataManager() != null) {
				getDataManager().refresh();
			}
			if (this.getModel().getSelectedData() instanceof CommissionHVO) {
				//ˢ���ӱ����
				if (this.getModel().getSelectedData() instanceof CommissionHVO) {
					UserDefineRefUtils.refreshBillListBodyDefRefs(
							(AggCommissionHVO) (this.getModel().getSelectedData()),
							(BillListView) this.mainPanel, "pk_commission_b",
							CommissionBVO.class);
				}
			}else if (this.getModel().getSelectedData() instanceof TaskHVO) {
				//by he
				UserDefineRefUtils.refreshBillCardBodyDefRefs(
						(AggTaskHVO)(this.getModel().getSelectedData()),
						(BillForm)this.mainPanel, "pk_task_b", TaskBVO.class);
			}

		} catch (BusinessException e) {
			Log.debug(e.getMessage());
		}
	}

	/**
	 * ��ȡ�ͻ��˵Ĵ򿪴�С
	 * 
	 * @return
	 */
	@Override
	public Integer getLastSize() {
		int d = 0;

		BillManageModel mainmodel = (BillManageModel) ((BillListView) this.getMainPanel()).getModel();

		Object statusObj = mainmodel.getContext().getStatusRegistery().getAppStatusObject(getListCacheId());
		if (statusObj == null || !(statusObj instanceof HashMap)) {
			return 0;
		}
		d = (Integer) ((HashMap) statusObj).get("Size");
		return d;
	}

	/**
	 * �洢�ͻ����б���һ�δ�С״̬
	 */
	protected void registeCallbak() {
		BillManageModel mainmodel = ((BillListView) this.getMainPanel()).getModel();
		if (mainmodel.getContext() == null || mainmodel.getContext().getStatusRegistery() == null) {
			return;
		}
		mainmodel.getContext().getStatusRegistery().addCallback(new AppStatusRegisteryCallback() {
			@Override
			public Object getID() {
				return getListCacheId();
			}

			@Override
			public Object getStatusObject() {
				Map<Object, Object> map = new HashMap<Object, Object>();
				map.put("Size", ListGrandPanelComposite.this.getListCardSplitPane().getDividerLocation());
				return map;
			}
		});

	}

	private String getListCacheId() {
		String userId = WorkbenchEnvironment.getInstance().getLoginUser().getCuserid();
		String funCode = this.getModel().getMainModel().getContext().getFuncInfo().getFuncode();
		return userId + funCode + "ListGrand_Size";
	}

	// �б��¼�����
	@SuppressWarnings("unchecked")
	@Override
	public void handleEvent(AppEvent event) {

		if (event instanceof ListBodyRowChangedEvent) {

			this.bodyRowChangeProcess();
			
		} else if (event instanceof ListBodyTabChangedEvent) {

			this.tabChangeProcess();
			
		} else if (event instanceof MainGrandAppEvent) {

			this.grandEventProcess(event);
			
		} else if (event instanceof ListHeadRowChangedEvent) {
		    //����
			if("C0J00203".equals(((ListHeadRowChangedEvent) event).getContext().getNodeCode())){
				try{
					((ListHeadRowChangedEvent) event).getBillListPanel()
						.getBodyBillModel().sortByColumn("runorder", true);
				}catch(IllegalArgumentException e){
					Logger.debug("relation ref reload.");
					Logger.debug(e.getMessage());
				}
			}
			// �б��ͷ���л�ʱ�Ĵ���(�����ʾ������������)
			this.getModel().setAddReturnList(true);
			this.clearGrandData();
			
		} else if (event.getType().equals(AppEventConst.MODEL_INITIALIZED)) {
			
			this.clearGrandData();
			this.getModel().getSelectGrandRowNum().clear();
			this.getModel().getQueryDataMap().clear();
			List<Object> datas = this.getModel().getMainModel().getData();
			for (Object obj : datas) {
				CircularlyAccessibleValueObject[] childVOs = ((AggregatedValueObject) obj).getChildrenVO();
				if (childVOs != null) {
					this.getModel().parseGrandData(obj);
				}
				
			}
			/**
			 * �޸���������ڽ����޷���ʾ��������
			 */
			if(datas!=null && datas.size() == 1){
				((BillListView) this.getMainPanel()).getModel().directlyUpdate(datas.get(0));
			}
			//AbstractBill aggVO = (AbstractBill) ((BillListView) this.getMainPanel()).getModel().getSelectedData();
			
		} else if (event.getType().equals(AppEventConst.SELECTED_DATE_CHANGED)) {

			AbstractBill aggVoWihGrand = CardPanelEventUtil.selectedDataChange(this);

			if (aggVoWihGrand != null) {

				this.getModel().parseGrandData(aggVoWihGrand);
			}
			
		} else if (event.getType().equals(AppEventConst.SELECTION_CHANGED) && this.isShowing()) {

			CardPanelEventUtil.grandListModelInit(this);
			
		}
	}

	/**
	 * ����������
	 */
	private void clearGrandData() {

		String currentbodyTabCode = ((BillListView) this.getMainPanel()).getBillListPanel().getChildListPanel()
				.getTableCode();
		// ���ݵ�ǰ��ҳǩ��ȡ���view
		BillListView grandListView = (BillListView) this.getMaingrandrelationship().getBodyTabTOGrandListComposite()
				.get(currentbodyTabCode);
		if (grandListView != null) {
			String[] grandTabCodes = grandListView.getBillListPanel().getBillListData().getBodyTableCodes();
			for (String grandTabcode : grandTabCodes) {
				grandListView.getBillListPanel().getBillListData().getBodyBillModel(grandTabcode).clearBodyData();
			}
		}
	}

	private void grandEventProcess(AppEvent event) {

		if (event.getType().equals(MainGrandEventTypeEnum.grandlist_expend.toString())) {
			// �б��������չ���
			this.displayGrandPanel();

		} else if (event.getType().equals(MainGrandEventTypeEnum.grandlist_shrink.toString())) {
			// �б�������������
			this.setGrandPanelMin();

		} else if (event.getType().equals(MainGrandEventTypeEnum.grand_hide.toString())) {
			// �б�������������
			this.hideGrandPanel();

		} else if (event.getType().equals(MainGrandEventTypeEnum.grand_display.toString())) {
			// �б��������ʾ���
			this.displayGrandPanel();

		} else if (event.getType().equals(MainGrandEventTypeEnum.grandlist_max.toString())) {
			// �б������������
			this.setGrandPanelMax();

		} else if (event.getType().equals(MainGrandEventTypeEnum.grandlist_min.toString())) {
			// �б������ԭ���(����Ĭ�Ϸָ��ߴ�С)
			this.resizeGrandPanel();
		}
	}

	/*
	 * ����ҳǩ�л���ʱ����ض�Ӧ�����ģ���Լ���Ӧ������
	 */
	private void tabChangeProcess() {
		// ��õ�ǰѡ����
		int currentRow = ((BillListView) this.mainPanel).getBillListPanel().getBodyTable().getSelectedRow();
		String currentbodyTabCode = ((BillListView) this.getMainPanel()).getBillListPanel().getChildListPanel()
				.getTableCode();
		// ���ݵ�ǰ��ҳǩ��ȡ���view
		BillListView grandListView = (BillListView) this.getMaingrandrelationship().getBodyTabTOGrandListComposite()
				.get(currentbodyTabCode);
		// ������ģ��
		this.getMainGrandAssist().updateGrandListByBodyTab(this);
		if (grandListView != null && currentRow != -1) {
			// �������������
			AbstractBill aggVO = (AbstractBill) ((BillListView) this.getMainPanel()).getModel().getSelectedData();
			String bodyClassName = aggVO.getChildrenVO()[0].getClass().getName();
			List<Object> grandVOList = this.getMainGrandAssist().getGrandListDataByMainRow(
					(BillListView) this.mainPanel, currentRow, grandListView, bodyClassName,
					this.getMaingrandrelationship());
			if (grandVOList != null) {
				// �����������ݲ���ʾ
				grandListView.getBillListPanel().getBodyBillModel().setBodyDataVO(grandVOList.toArray(new SuperVO[0]));
				grandListView.getBillListPanel().getBodyBillModel().loadLoadRelationItemValue();
				grandListView.getBillListPanel().getBodyBillModel().execLoadFormula();

				// ssx add for append user define REFs
				// on 2019-03-06
				UserDefineRefUtils.refreshBillListGrandDefRefs(grandListView, grandVOList);
				//
			} else {
				grandListView.getBillListPanel().getBodyBillModel().clearBodyData();
			}
		}
	}

	private void bodyRowChangeProcess() {
		// ��õ�ǰѡ����
		int currentRow = ((BillListView) this.mainPanel).getBillListPanel().getBodyTable().getSelectedRow();
		String currentbodyTabCode = ((BillListView) this.getMainPanel()).getBillListPanel().getChildListPanel()
				.getTableCode();
		// ���ݵ�ǰ��ҳǩ��ȡ���view
		BillListView grandListView = (BillListView) this.getMaingrandrelationship().getBodyTabTOGrandListComposite()
				.get(currentbodyTabCode);
		if (grandListView != null) {
			//XXX fix
			
			AbstractBill aggVO = (AbstractBill) ((BillListView) this.getMainPanel()).getModel().getSelectedData();
			//getDataManager().refresh();
			if (aggVO != null && null != aggVO.getChildrenVO() && aggVO.getChildrenVO().length != 0) {
				String bodyClassName = aggVO.getChildrenVO()[0].getClass().getName();
				List<Object> grandVOList = this.getMainGrandAssist().getGrandListDataByMainRow(
						(BillListView) this.mainPanel, currentRow, grandListView, bodyClassName,
						this.getMaingrandrelationship());
				if (grandVOList != null && grandVOList.size() != 0) {
					grandListView.getBillListPanel().getBodyBillModel()
							.setBodyDataVO(grandVOList.toArray(new SuperVO[0]));
					grandListView.getBillListPanel().getBodyBillModel().loadLoadRelationItemValue();
					grandListView.getBillListPanel().getBodyBillModel().execLoadFormula();

					// ssx add for append user define REFs
					// on 2019-03-06
					UserDefineRefUtils.refreshBillListGrandDefRefs(grandListView, grandVOList);
					//
				} else {
					String[] grandTabCodes = grandListView.getBillListPanel().getBillListData().getBodyTableCodes();
					for (String grandTabcode : grandTabCodes) {
						grandListView.getBillListPanel().getBillListData().getBodyBillModel(grandTabcode)
								.clearBodyData();
					}
				}
			}
		}
	}

	@Override
	public void setAutoShowUpEventListener(IAutoShowUpEventListener l) {
		this.autoShowUpComponent.setAutoShowUpEventListener(l);
	}

	@Override
	public boolean isDisplyGrandPanel() {
		return this.getGrandPanel().isVisible();
	}

	public ExpendShrinkGrandListAction getExpendShrinkGrandListAction() {
		return this.expendShrinkGrandListAction;
	}

	public void setExpendShrinkGrandListAction(final ExpendShrinkGrandListAction expendShrinkGrandListAction) {
		this.expendShrinkGrandListAction = expendShrinkGrandListAction;
	}
}
