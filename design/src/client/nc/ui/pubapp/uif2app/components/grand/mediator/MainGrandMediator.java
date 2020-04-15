package nc.ui.pubapp.uif2app.components.grand.mediator;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import nc.bs.logging.Logger;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillTabbedPaneTabChangeEvent;
import nc.ui.pub.bill.BillTabbedPaneTabChangeListener;
import nc.ui.pub.bill.BillTabbedPaneTabChangeListener2;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pubapp.uif2app.components.grand.MainGrandRelationShip;
import nc.ui.pubapp.uif2app.components.grand.model.MainGrandModel;
import nc.ui.pubapp.uif2app.components.grand.util.CardPanelEventUtil;
import nc.ui.pubapp.uif2app.components.grand.util.MainGrandAssist;
import nc.ui.pubapp.uif2app.components.grand.util.MainGrandUtil;
import nc.ui.pubapp.uif2app.event.card.CardBodyAfterEditEvent;
import nc.ui.pubapp.uif2app.event.card.CardBodyRowChangedEvent;
import nc.ui.pubapp.uif2app.event.list.ListBodyRowChangedEvent;
import nc.ui.pubapp.uif2app.event.list.ListBodyTabChangedEvent;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.AppEventListener;
import nc.ui.uif2.UIState;
import nc.ui.uif2.editor.BillListView;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.bill.BillTabVO;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;

public class MainGrandMediator implements AppEventListener, BillTabbedPaneTabChangeListener,
		BillTabbedPaneTabChangeListener2, ChangeListener {

	private BillForm mainBillForm;

	private BillListView mainBillListView;

	private MainGrandRelationShip mainGrandRelationShip;// 孙面板处理类

	private MainGrandModel mainGrandModel = null;

	private MainGrandAssist mainGrandAssist = null;

	/**
	 * 处理孙页签切换前的方法
	 */
	@Override
	public boolean beforeTabChanged(BillTabbedPaneTabChangeEvent e) {

		if (this.mainBillForm.getModel().getUiState().equals(UIState.EDIT)) {

			String bodyTabCode = mainBillForm.getBillCardPanel().getCurrentBodyTableCode();

			CircularlyAccessibleValueObject[] childVos = MainGrandUtil.getBodyVOsByTabCode(mainBillForm, bodyTabCode);
			if (childVos != null && childVos.length > 0) {
				Component grandComposite = (Component) this.getMainGrandRelationShip().getBodyTabTOGrandCardComposite()
						.get(bodyTabCode);
				String grandTabCode = ((BillForm) grandComposite).getBillCardPanel().getCurrentBodyTableCode();
				int lastrow = mainBillForm.getBillCardPanel().getBillTable().getSelectedRow();
				// 存储页签切换之前的数据信息
				if (lastrow >= 0) {
					// 初始化lastrow在大于0的情况下存储信息
					SuperVO lastBodyVO = (SuperVO) childVos[lastrow];
					String last_pk_body = getMainGrandAssist().getPkStatusIsEdit(lastBodyVO);
					String lastcardUniqueKey = bodyTabCode + last_pk_body + grandTabCode;

					((BillForm) grandComposite).getBillCardPanel().stopEditing();
					// 获取对应的孙VO的数据
					CircularlyAccessibleValueObject[] lastGrandVos = MainGrandUtil.getBodyVOsByTabCode(
							(BillForm) grandComposite, grandTabCode);
					ArrayList<Object> lastGrandVosList = new ArrayList<Object>();
					if (lastGrandVos != null && lastGrandVos.length > 0) {
						for (CircularlyAccessibleValueObject GrandVo : lastGrandVos) {
							lastGrandVosList.add(GrandVo);
						}
					}
					processDelVo2DelMap(grandComposite, grandTabCode, lastcardUniqueKey);
					this.getMainGrandModel().getBufferCardEditMap().put(lastcardUniqueKey, lastGrandVosList);
				}
			}
		}
		return true;
	}

	/**
	 * 将删除的VO放入到删除的Map中去
	 */
	private void processDelVo2DelMap(Component grandComposite, String grandTabCode, String lastcardUniqueKey) {
		// 获取对应的孙VO删除的数据
		CircularlyAccessibleValueObject[] lastDeleteGrandVos = MainGrandUtil.getBodyChangeVoByTabCode(
				(BillForm) grandComposite, grandTabCode);
		ArrayList<Object> lastDelGrandVosList = new ArrayList<Object>();
		if (lastDeleteGrandVos == null || lastDeleteGrandVos.length == 0) {
			return;
		}
		// 处理界面删除的带有PK的孙表VO
		for (CircularlyAccessibleValueObject lastDeleteGrandVo : lastDeleteGrandVos) {
			try {
				if (lastDeleteGrandVo.getPrimaryKey() != null) {
					if (lastDeleteGrandVo.getStatus() == VOStatus.DELETED) {
						lastDelGrandVosList.add(lastDeleteGrandVo);
					}
				}
			} catch (BusinessException el) {
				Logger.error(el.getMessage(), el);
			}
		}
		// 存入删除的孙表VO信息
		if (lastDelGrandVosList.size() != 0) {
			this.getMainGrandModel().getDelGrandMap().put(lastcardUniqueKey, lastDelGrandVosList);
		}
	}

	/**
	 * 处理孙页签切换后的方法
	 */
	@SuppressWarnings("unused")
	@Override
	public void afterTabChanged(BillTabbedPaneTabChangeEvent event) {
		// 获得当前选中行
		int currentRow = mainBillForm.getBillCardPanel().getBillTable().getSelectedRow();
		String currentbodyTabCode = mainBillForm.getBillCardPanel().getCurrentBodyTableCode();

		// 根据当前子页签获取孙表视图
		BillForm grandBillForm = (BillForm) this.getMainGrandRelationShip().getBodyTabTOGrandCardComposite()
				.get(currentbodyTabCode);
		BillTabVO tab = event.getBtvo();
		if (tab != null) {
			List<Action> actions = grandBillForm.getBodyActionMap().get(tab.getTabcode());
			grandBillForm.getBillCardPanel().addTabAction(IBillItem.BODY, actions);
		}
		String bodyTabCode = mainBillForm.getBillCardPanel().getCurrentBodyTableCode();
		String grandTabCode = grandBillForm.getBillCardPanel().getCurrentBodyTableCode();

		// 孙表的存储数组VOS
		List<Object> grandVOList = new ArrayList<Object>();
		boolean handleRowStatus = false;
		if (this.mainBillForm.getModel().getUiState().equals(UIState.NOT_EDIT)) {

			afterTabChangedNotEdit(currentRow, grandBillForm, grandTabCode);

		} else if (this.mainBillForm.getModel().getUiState().equals(UIState.EDIT)) {

			afterTabChangeStatusEdit(currentRow, grandBillForm, grandTabCode);
		}
	}

	/**
	 * 编辑态页签切换的处理
	 */
	private void afterTabChangeStatusEdit(int currentRow, BillForm grandBillForm, String grandTabCode) {
		List<Object> grandVOList;
		boolean isGrandFirstLoaded = mainGrandAssist.testGrandDataIsLoaded(mainBillForm, currentRow, grandBillForm);
		if (!isGrandFirstLoaded) {// 判断当前是否已经加载、如果没有加载则进行加载
			grandVOList = mainGrandAssist.getGrandCardDataByGrandTab(mainBillForm, currentRow, grandBillForm,
					this.getMainGrandRelationShip());
			if (grandVOList != null) {
				grandBillForm.getBillCardPanel().getBillData()
						.setBodyValueVO(grandTabCode, grandVOList.toArray(new SuperVO[0]));
				CardPanelEventUtil.execlFormula(grandBillForm, grandTabCode);

				BillModel billModel = grandBillForm.getBillCardPanel().getBillModel();
				int rowCount = billModel.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					SuperVO vo = (SuperVO) grandVOList.get(i);
					int voStatus = vo.getStatus();
					if (voStatus == VOStatus.NEW) {
						billModel.setRowState(i, BillModel.ADD);
					} else if (voStatus == VOStatus.UPDATED) {
						billModel.setRowState(i, BillModel.MODIFICATION);
					} else {
						billModel.setRowState(i, BillModel.NORMAL);
					}
				}
			} else {
				grandBillForm.getBillCardPanel().getBillModel(grandTabCode).clearBodyData();
			}
		}
	}

	/**
	 * 非编辑态页签切换
	 */
	private void afterTabChangedNotEdit(int currentRow, BillForm grandBillForm, String grandTabCode) {
		List<Object> grandVOList = mainGrandAssist.showGrandCardDataByGrandTabNotEdit(mainBillForm, currentRow,
				grandBillForm, this.getMainGrandRelationShip());
		if (grandVOList != null) {
			grandBillForm.getBillCardPanel().getBillData()
					.setBodyValueVO(grandTabCode, grandVOList.toArray(new SuperVO[0]));

			CardPanelEventUtil.execlFormula(grandBillForm, grandTabCode);
		} else {
			grandBillForm.getBillCardPanel().getBillModel(grandTabCode).clearBodyData();
		}
	}

	/**
	 * 收发平台事件的方法
	 */
	@Override
	public void handleEvent(AppEvent event) {

		if (event instanceof CardBodyRowChangedEvent) {

			BillCardPanel grandBillCardPanel = ((CardBodyRowChangedEvent) event).getBillCardPanel();
			// 获取选中的行号数组
			int[] grandSelectRows = grandBillCardPanel.getBillTable().getSelectedRows();

			String tableCode = grandBillCardPanel.getCurrentBodyTableCode();

			getMainGrandModel().getSelectGrandRowNum().put(tableCode, grandSelectRows);

		} else if (event instanceof ListBodyRowChangedEvent) {

			// 获取选中的行号数组
			int[] grandSelectRows = ((ListBodyRowChangedEvent) event).getBillListPanel().getBodyTable()
					.getSelectedRows();

			String tableCode = ((ListBodyRowChangedEvent) event).getBillListPanel().getBodyTabbedPane()
					.getSelectedTableCode();

			getMainGrandModel().getSelectGrandRowNum().put(tableCode, grandSelectRows);

		} else if (event instanceof CardBodyAfterEditEvent) {

			int currentGrandRow = ((CardBodyAfterEditEvent) event).getRow();

			String key = ((CardBodyAfterEditEvent) event).getKey();

			grandAddLineAfterEdit(currentGrandRow, key);
		} else if (event instanceof ListBodyTabChangedEvent) {

			// int bodySelectRow = ((nc.ui.pubapp.uif2app.model.BillManageModel)
			// this.getMainBillForm().getModel())
			// .getSelectedRow();
			//
			// List<Object> grandVOList =
			// this.getMainGrandAssist().getGrandListDataByMainRow(this.getMainBillListView(),
			// bodySelectRow, ((ListBodyTabChangedEvent)
			// event).getBillTabbedPane().getSelectedTableCode(),
			// this.getMainBillForm().getBillCardPanel().getBodyTabbedPane().getSelectedTableCode(),
			// this.getMainGrandRelationShip());
			// UserDefineRefUtils.refreshBillListGrandDefRefs((BillListView)
			// event.getSource(), grandVOList);
		}
	}

	/**
	 * 编辑到最后一行自增行的处理
	 */
	private void grandAddLineAfterEdit(int currentGrandRow, String key) {

		String currentbodyTabCode = mainBillForm.getBillCardPanel().getCurrentBodyTableCode();

		BillForm grandBillForm = (BillForm) this.getMainGrandRelationShip().getBodyTabTOGrandCardComposite()
				.get(currentbodyTabCode);

		if (grandBillForm != null && grandBillForm.getModel().getUiState() != UIState.NOT_EDIT) {

			BillCardPanel grandBillCardPanel = grandBillForm.getBillCardPanel();

			int rowCount = grandBillCardPanel.getBillModel().getRowCount() - 1;

			if (mainGrandModel.isGrandAutoAddLine() && currentGrandRow != -1 && currentGrandRow == rowCount
					&& mutiSelectedRow(grandBillCardPanel, key)) {

				getMainGrandAssist().grandAddLine(grandBillForm);
			}
		}
	}

	/**
	 * 参照多选的时候不用自增行
	 */
	private boolean mutiSelectedRow(BillCardPanel grandBillCardPanel, String key) {
		boolean needAddLine = false;
		JComponent component = grandBillCardPanel.getBodyItem(key).getComponent();
		if (component instanceof UIRefPane) {
			String[] refPKs = ((UIRefPane) component).getRefPKs();
			if (refPKs != null && refPKs.length == 1) {
				needAddLine = true;
			}
		} else {
			needAddLine = true;
		}
		return needAddLine;
	}

	// 列表页签切换时加载对应的数据并显示
	@Override
	public void stateChanged(ChangeEvent e) {
		// 获得当前选中行
		int currentRow = mainBillListView.getBillListPanel().getBodyTable().getSelectedRow();
		if (currentRow >= 0) {
			String currentbodyTabCode = mainBillListView.getBillListPanel().getBodyTabbedPane().getSelectedTableCode();
			BillListView grandListView = (BillListView) this.mainGrandRelationShip.getBodyTabTOGrandListComposite()
					.get(currentbodyTabCode);
			AbstractBill aggVO = (AbstractBill) mainBillListView.getModel().getSelectedData();
			String bodyClassName = aggVO.getChildrenVO()[0].getClass().getName();
			List<Object> grandVOList = this.mainGrandAssist.getGrandListDataByMainRow(mainBillListView, currentRow,
					grandListView, bodyClassName, this.mainGrandRelationShip);

			if (grandVOList != null) {
				// 孙面板加载数据并显示
				grandListView.getBillListPanel().getBodyBillModel().setBodyDataVO(grandVOList.toArray(new SuperVO[0]));
				grandListView.getBillListPanel().getBodyBillModel().loadLoadRelationItemValue();
				grandListView.getBillListPanel().getBodyBillModel().execLoadFormula();
			} else {
				grandListView.getBillListPanel().getBodyBillModel().clearBodyData();
			}
		}
	}

	public MainGrandModel getMainGrandModel() {
		return mainGrandModel;
	}

	public void setMainGrandModel(MainGrandModel mainGrandModel) {
		this.mainGrandModel = mainGrandModel;
		// 添加孙表事件监听
		this.mainGrandModel.getGrandModel().addAppEventListener(this);
	}

	public BillForm getMainBillForm() {
		return mainBillForm;
	}

	public BillListView getMainBillListView() {
		return mainBillListView;
	}

	public void setMainBillForm(BillForm mainBillForm) {
		this.mainBillForm = mainBillForm;
	}

	public void setMainBillListView(BillListView mainBillListView) {
		this.mainBillListView = mainBillListView;
	}

	public void setMainGrandRelationShip(MainGrandRelationShip mainGrandRelationShip) {
		this.mainGrandRelationShip = mainGrandRelationShip;
	}

	public MainGrandRelationShip getMainGrandRelationShip() {
		return mainGrandRelationShip;
	}

	public MainGrandAssist getMainGrandAssist() {
		return mainGrandAssist;
	}

	public void setMainGrandAssist(MainGrandAssist mainGrandAssist) {
		this.mainGrandAssist = mainGrandAssist;
	}

}
