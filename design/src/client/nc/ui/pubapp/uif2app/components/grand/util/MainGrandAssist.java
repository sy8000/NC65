package nc.ui.pubapp.uif2app.components.grand.util;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.Action;

import nc.bs.logging.Logger;
import nc.md.data.access.NCObject;
import nc.md.model.IAttribute;
import nc.md.model.IBean;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillScrollPane;
import nc.ui.pubapp.uif2app.components.grand.CardGrandPanel;
import nc.ui.pubapp.uif2app.components.grand.CardGrandPanelComposite;
import nc.ui.pubapp.uif2app.components.grand.ListGrandPanel;
import nc.ui.pubapp.uif2app.components.grand.ListGrandPanelComposite;
import nc.ui.pubapp.uif2app.components.grand.MainGrandBlankFilter;
import nc.ui.pubapp.uif2app.components.grand.MainGrandRelationShip;
import nc.ui.pubapp.uif2app.components.grand.action.AbstractGrandBodyTableExtendAction;
import nc.ui.pubapp.uif2app.components.grand.model.IGrandValidationService;
import nc.ui.pubapp.uif2app.components.grand.model.MainGrandModel;
import nc.ui.pubapp.uif2app.view.BillForm;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.UIState;
import nc.ui.uif2.editor.BillListView;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.ValidationException;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.task.TaskBVO;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 处理主子孙的工具类
 */
public class MainGrandAssist {

	private MainGrandModel mainGrandModel = null;

	/**
	 * 列表状态下根据子选择行VO获取对应的孙表数据
	 */
	public List<Object> getGrandListDataByMainRow(BillListView mainlistview, int currentRow,
			BillListView grandlistview, String bodyClassName, MainGrandRelationShip maingrandrelationship) {
		if (currentRow != -1) {
			SuperVO bodyVO = (SuperVO) mainlistview.getBillListPanel().getBodyBillModel()
					.getBodyValueRowVO(currentRow, bodyClassName);
			String bodyTabCode = mainlistview.getBillListPanel().getChildListPanel().getTableCode();
			if (grandlistview != null) {

				String grandTabCode = grandlistview.getBillListPanel().getChildListPanel().getTableCode();
				String pk_body = bodyVO.getPrimaryKey();
				String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;
				ArrayList<Object> grandObjectList = this.getMainGrandModel().getQueryDataMap().get(cardUniqueKey);
				return grandObjectList;
			}
		}
		return null;
	}

	/**
	 * 列表状态下根据子选择行VO获取对应的孙表数据 ssx added for overload
	 */
	public List<Object> getGrandListDataByMainRow(BillListView mainlistview, int currentRow, String grandTabCode,
			String bodyClassName, MainGrandRelationShip maingrandrelationship) {
		if (currentRow != -1) {
			SuperVO bodyVO = (SuperVO) mainlistview.getBillListPanel().getBodyBillModel()
					.getBodyValueRowVO(currentRow, bodyClassName);
			String bodyTabCode = mainlistview.getBillListPanel().getBodyTabbedPane().getSelectedTableCode();
			String pk_body = bodyVO.getPrimaryKey();
			String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;
			ArrayList<Object> grandObjectList = this.getMainGrandModel().getQueryDataMap().get(cardUniqueKey);
			return grandObjectList;
		}

		return null;
	}

	/**
	 * 根据子页签更新对应的孙面板信息(主子孙列表情况)
	 */
	public void updateGrandListByBodyTab(ListGrandPanelComposite listcombo) {

		String currentBodyTab = ((BillListView) listcombo.getMainPanel()).getBillListPanel().getChildListPanel()
				.getTableCode();
		Component grandComposite = (Component) listcombo.getMaingrandrelationship().getBodyTabTOGrandListComposite()
				.get(currentBodyTab);
		float dividerLocation = listcombo.getListCardSplitPane().getDividerLocation();
		// 当孙表显示时，取到的dividerLocation才是正常的，可以保存uiDividerLocation
		boolean isDisplay = listcombo.getGrandPanel().isVisible();
		if (isDisplay && grandComposite == null) {
			listcombo.setUiDividerLocation(dividerLocation / listcombo.getListCardSplitPane().getHeight());
		}
		this.listBodyTabChange(listcombo, currentBodyTab);

	}

	// 列表界面条件下是否显示孙表信息
	private void listBodyTabChange(ListGrandPanelComposite listcombo, String currentBodyTab) {
		// 获取孙面板的显示状态
		boolean isDisplay = this.getMainGrandModel().isHandleListCardIsShow();
		// 读取配置文件中的信息
		Component grandComposite = (Component) listcombo.getMaingrandrelationship().getBodyTabTOGrandListComposite()
				.get(currentBodyTab);

		if (grandComposite == null) {
			((ListGrandPanel) listcombo.getGrandPanel()).setVisible(false);
		} else {
			if (isDisplay == true) {// 如果可以显示则进行显示孙面板
				listcombo.getListCardSplitPane().setDividerLocation(listcombo.getUiDividerLocation());
				((ListGrandPanel) listcombo.getGrandPanel()).setVisible(true);
				((CardLayout) listcombo.switchGrandPanel.getLayout()).show(listcombo.switchGrandPanel, currentBodyTab);
			}
		}
	}

	/**
	 * 根据子页签更新对应的孙面板信息(主子孙卡片情况)
	 * 
	 * @param listcombo
	 */
	public void updateGrandCardByBodyTab(CardGrandPanelComposite cardcombo) {

		String currentBodyTab = ((BillForm) cardcombo.getMainPanel()).getBillCardPanel().getCurrentBodyTableCode();
		// 分割线当前位置
		Component grandComposite = (Component) cardcombo.getMaingrandrelationship().getBodyTabTOGrandCardComposite()
				.get(currentBodyTab);
		float dividerLocation = cardcombo.getListCardSplitPane().getDividerLocation();
		// 当孙表显示时，取到的dividerLocation才是正常的，可以保存uiDividerLocation
		boolean isDisplay = cardcombo.getGrandPanel().isVisible();
		if (isDisplay && grandComposite == null) {
			cardcombo.setUiDividerLocation(dividerLocation / cardcombo.getListCardSplitPane().getHeight());
		}
		this.cardBodyTabChange(cardcombo, currentBodyTab);
	}

	// 卡片界面页签切换显示条件
	private void cardBodyTabChange(CardGrandPanelComposite cardcombo, String currentBodyTab) {
		// 读取配置文件中的信息
		Component grandComposite = (Component) cardcombo.getMaingrandrelationship().getBodyTabTOGrandCardComposite()
				.get(currentBodyTab);

		// 获取孙面板的显示状态
		boolean isDisplay = this.getMainGrandModel().isHandleListCardIsShow();
		if (grandComposite == null) {
			((CardGrandPanel) cardcombo.getGrandPanel()).setVisible(false);
		} else {
			if (isDisplay == true) {
				cardcombo.getModel().getCardGrandEnumState();
				((CardGrandPanel) cardcombo.getGrandPanel()).setVisible(true);
				cardcombo.getListCardSplitPane().setDividerLocation(cardcombo.getUiDividerLocation());
				((CardLayout) cardcombo.switchGrandCardPanel.getLayout()).show(cardcombo.switchGrandCardPanel,
						currentBodyTab);
			}
		}
	}

	/**
	 * 编辑状态下卡片根据子选择行VO获取对应的孙表数据同时缓存孙表数据
	 */
	public List<Object> getGrandCardDataByMainRow(RowChangeBean rowChangeBean,
			MainGrandRelationShip maingrandrelationship, IGrandValidationService grandValidationService,
			MainGrandBlankFilter filter) {

		BillForm mainbillform = rowChangeBean.getMainForm();
		BillForm grandbillform = rowChangeBean.getGrandForm();
		int currentRow = rowChangeBean.getCurrentRow();
		grandbillform.getBillCardPanel().stopEditing();
		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		CircularlyAccessibleValueObject[] childVOs = MainGrandUtil.getBodyVOsByTabCode(mainbillform, bodyTabCode);

		if (childVOs == null || childVOs.length == 0) {
			return null;
		}
		if (!this.isNeedToRowChange(mainbillform)) {
			this.putDataIntoEditMap(rowChangeBean, grandValidationService, filter);
		} else {
			this.putDataIntoEditMapForCurrentLine(rowChangeBean);
		}

		String grandTabCode = grandbillform.getBillCardPanel().getCurrentBodyTableCode();
		// 显示当前选中表体行的数据
		SuperVO currBodyVO = null;
		if (currentRow >= 0) {
			currBodyVO = (SuperVO) childVOs[currentRow];
		}
		String pk_body = this.getPkStatusIsEdit(currBodyVO);
		// 车胎都爆了,就别抢银行了! Tank 2019年4月10日23:16:27
		RowNumUtil.getRowNumUtil().generateRowNo();
		/*
		 * if (pk_body == null) { String precolum =
		 * RowNumUtil.getRowNumUtil().generateRowNo();
		 * this.addPreColumBillItemToBody(mainbillform, currentRow, bodyTabCode,
		 * precolum); currBodyVO.setAttributeValue("precolumn", precolum);
		 * pk_body = (String) currBodyVO.getAttributeValue("precolumn"); }
		 */
		String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;
		// 存取孙表当前页签下的VOList
		ArrayList<Object> grandVOList = this.getGrandListStatusIsEdit(cardUniqueKey);

		this.getMainGrandModel().getTestAddFirstMap().put(cardUniqueKey, "1");
		return grandVOList;
	}

	/**
	 * 新增状态下卡片根据子选择行VO获取对应的孙表数据同时缓存孙表数据
	 */
	public Map<String, ArrayList<Object>> getGrandCardDataByMainRowAdd(RowChangeBean rowChangeBean,
			MainGrandRelationShip maingrandrelationship, IGrandValidationService grandValidationService,
			MainGrandBlankFilter filter) {

		BillForm mainbillform = rowChangeBean.getMainForm();
		BillForm grandbillform = rowChangeBean.getGrandForm();
		int currentRow = rowChangeBean.getCurrentRow();
		grandbillform.getBillCardPanel().stopEditing();
		if (!this.isNeedToRowChange(mainbillform)) {
			this.putDataIntoAddMap(rowChangeBean, maingrandrelationship, grandValidationService, filter);
		} else {
			this.putDataIntoAddMapForCurrentRow(rowChangeBean, maingrandrelationship);
		}
		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		String[] grandTabCodes = null;
		grandTabCodes = grandbillform.getBillCardPanel().getBillData().getBodyTableCodes();
		// 新增状态下显示数据
		Map<String, ArrayList<Object>> grandAllObjectMap = new HashMap<String, ArrayList<Object>>();

		String className = MainGrandUtil.getClassNameByTabCode(mainbillform, bodyTabCode);
		// 推单场景pk已经存在
		String pk_body = "";
		try {
			CircularlyAccessibleValueObject bodyValueRowVO = mainbillform.getBillCardPanel().getBillModel()
					.getBodyValueRowVO(currentRow, className);
			if (null != bodyTabCode) {
				pk_body = bodyValueRowVO.getPrimaryKey();
			}
		} catch (BusinessException e1) {
			Logger.error(e1.getMessage(), e1);
		}
		// 伪列
		if (pk_body == null) {
			if("nc.vo.qcco.commission.CommissionBVO".equals(className)){
				pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(currentRow, "uniqueid");
				//mainbillform.getBillCardPanel().getBodyValueAt(currentRow, "uniqueid")
			}else if("nc.vo.qcco.task.TaskBVO".equals(className)){
				pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(currentRow, "uniquekey");
			}else{
				pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(currentRow, "precolumn");
			}
			
		}
		if (pk_body == null) {
			this.addPreColumBillItemToBody(mainbillform, currentRow, bodyTabCode, RowNumUtil.getRowNumUtil()
					.generateRowNo());
			pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(currentRow, "precolumn");
		}
		// 处理逻辑直接从缓存中读取孙表VO数据并返回
		if (!ArrayUtils.isEmpty(grandTabCodes)) {
			for (String grandTabCode : grandTabCodes) {
				ArrayList<Object> grandObjectList = new ArrayList<Object>();
				// 组装唯一标识key
				String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;
				grandObjectList = this.getMainGrandModel().getBufferCardAddMap().get(cardUniqueKey);
				grandAllObjectMap.put(grandTabCode, grandObjectList);
			}
		}
		return grandAllObjectMap;
	}

	/**
	 * 卡片非编辑状态下根据子选择行VO获取对应的孙表数据
	 */
	public List<Object> showGrandCardDataByMainRow(RowChangeBean rowChangeBean,
			MainGrandRelationShip maingrandrelationship) {
		BillForm mainbillform = rowChangeBean.getMainForm();
		BillForm grandbillform = rowChangeBean.getGrandForm();
		int currentRow = rowChangeBean.getCurrentRow();

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		CircularlyAccessibleValueObject[] bodyVOs = MainGrandUtil.getBodyVOsByTabCode(mainbillform, bodyTabCode);
		if (bodyVOs == null || bodyVOs.length == 0) {
			return null;
		}
		String grandTabCode = grandbillform.getBillCardPanel().getCurrentBodyTableCode();

		ArrayList<Object> grandObjectList = new ArrayList<Object>();
		if (currentRow >= 0) {
			SuperVO currBodyVO = (SuperVO) bodyVOs[currentRow];
			String pk_body = this.getPkStatusIsEdit(currBodyVO);
			String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;
			grandObjectList = this.getMainGrandModel().getQueryDataMap().get(cardUniqueKey);
		}
		return grandObjectList;
	}

	/**
	 * 卡片新增时切换子页签存储新增缓存数据
	 */
	public void showMainCardDataByGrandTab(BillForm mainBillForm, int currentRow,
			MainGrandRelationShip maingrandrelationship) {
		if (mainBillForm.getModel().getUiState().equals(UIState.ADD)) {
			String currentBodyTabCode = mainBillForm.getBillCardPanel().getCurrentBodyTableCode();
			Component grandComposite = (Component) maingrandrelationship.getBodyTabTOGrandCardComposite().get(
					currentBodyTabCode);
			if (grandComposite == null) {
				return;
			}
			String[] currentGrandTabCodes = ((BillForm) grandComposite).getBillCardPanel().getBillData()
					.getBodyTableCodes();
			String pk_body = (String) mainBillForm.getBillCardPanel().getBillModel()
					.getValueAt(currentRow, "precolumn");
			for (String currentGrandTabCode : currentGrandTabCodes) {
				// 组装标示符
				String uniqueKey = currentBodyTabCode + pk_body + currentGrandTabCode;
				((BillForm) grandComposite).getBillCardPanel().stopEditing();
				CircularlyAccessibleValueObject[] grandVos = MainGrandUtil.getBodyVOsByTabCode(
						(BillForm) grandComposite, currentGrandTabCode);
				if (grandVos == null || grandVos.length == 0) {
					continue;
				}
				ArrayList<Object> grandVOList = new ArrayList<Object>();
				for (Object grandObj : grandVos) {
					grandVOList.add(grandObj);
				}
				this.getMainGrandModel().getBufferCardAddMap().put(uniqueKey, grandVOList);
			}
		}
	}

	/**
	 * 卡片编辑时切换子页签存储新增缓存数据
	 */
	public void showMainCardDataByGrandTabEdit(BillForm mainBillForm, int currentRow,
			MainGrandRelationShip maingrandrelationship) {
		if (mainBillForm.getModel().getUiState().equals(UIState.EDIT)) {
			String currentBodyTabCode = mainBillForm.getBillCardPanel().getCurrentBodyTableCode();
			CircularlyAccessibleValueObject[] childrenVOs = MainGrandUtil.getBodyVOsByTabCode(mainBillForm,
					currentBodyTabCode);
			if (childrenVOs == null || childrenVOs.length == 0) {
				return;
			}
			Component grandComposite = (Component) maingrandrelationship.getBodyTabTOGrandCardComposite().get(
					currentBodyTabCode);
			if (grandComposite != null) {
				String currentGrandTabCode = ((BillForm) grandComposite).getBillCardPanel().getCurrentBodyTableCode();
				SuperVO currBodyVO = null;
				if (currentRow >= 0) {
					currBodyVO = (SuperVO) childrenVOs[currentRow];
				}
				String pk_body = this.getPkStatusIsEdit(currBodyVO);
				String uniqueKey = currentBodyTabCode + pk_body + currentGrandTabCode;
				((BillForm) grandComposite).getBillCardPanel().stopEditing();
				// 根据当前孙表页签获取改页签下的孙表数据VO
				CircularlyAccessibleValueObject[] grandVos = MainGrandUtil.getBodyVOsByTabCode(
						(BillForm) grandComposite, currentGrandTabCode);
				this.processDelVOs((BillForm) grandComposite, currentGrandTabCode, uniqueKey);
				if (grandVos == null || grandVos.length == 0) {
					return;
				}
				ArrayList<Object> grandVOList = new ArrayList<Object>();
				for (Object grandObj : grandVos) {
					grandVOList.add(grandObj);
				}
				// 数据缓存到编辑map中
				this.getMainGrandModel().getBufferCardEditMap().put(uniqueKey, grandVOList);
			}
		}
	}

	/**
	 * 卡片新增状态孙表切换页签根据子选择行VO获取对应的孙表数据
	 */
	public List<Object> showGrandCardDataByGrandTab(BillForm mainbillform, int currentRow, BillForm grandbillform) {

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();

		String grandTabCode = grandbillform.getBillCardPanel().getCurrentBodyTableCode();

		String pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(currentRow, "precolumn");
		// 在编辑缓存Map中存储信息、 组装唯一标识key
		String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;
		// 存取孙表当前页签下的VOList
		ArrayList<Object> grandObjectList = new ArrayList<Object>();
		grandObjectList = this.getMainGrandModel().getBufferCardAddMap().get(cardUniqueKey);
		if (grandObjectList == null) {
			grandObjectList = new ArrayList<Object>();
		}
		return grandObjectList;
	}

	/**
	 * 卡片非编辑状态孙表切换页签根据子选择行VO获取对应的孙表数据
	 */
	public List<Object> showGrandCardDataByGrandTabNotEdit(BillForm mainbillform, int currentRow,
			BillForm grandbillform, MainGrandRelationShip mainGrandRelationShip) {

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();

		CircularlyAccessibleValueObject[] childBodyVos = MainGrandUtil.getBodyVOsByTabCode(mainbillform, bodyTabCode);
		if (childBodyVos == null || childBodyVos.length == 0) {
			return null;
		}
		// 存取孙表当前页签下的VOList
		ArrayList<Object> grandVOList = new ArrayList<Object>();
		if (currentRow >= 0) {
			SuperVO currBodyVO = (SuperVO) childBodyVos[currentRow];
			String pk_body = "";
			if (currBodyVO != null && currBodyVO.getPrimaryKey() != null) {
				pk_body = currBodyVO.getPrimaryKey();
			}
			String grandTabCode = grandbillform.getBillCardPanel().getCurrentBodyTableCode();
			// 缓存Map中存储信息、 组装唯一标识key
			String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;
			// 获取孙表当前页签下的VOList
			grandVOList = this.getMainGrandModel().getQueryDataMap().get(cardUniqueKey);
		}
		return grandVOList;
	}

	/**
	 * 判断孙面板数据是否加载
	 */
	public boolean testGrandDataIsLoaded(BillForm mainbillform, int currentRow, BillForm grandbillform) {
		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		String grandTabCode = grandbillform.getBillCardPanel().getCurrentBodyTableCode();
		AbstractBill aggVO = (AbstractBill) mainbillform.getBillCardPanel().getBillData().getBillObjectByMetaData();
		SuperVO currBodyVO = null;
		if (currentRow >= 0) {
			currBodyVO = (SuperVO) aggVO.getChildrenVO()[currentRow];
		}
		String pk_body = "";
		boolean flag = false;// 标识是否已经加载
		if (currBodyVO != null) {
			// 获取表体行VO主键值
			if (currBodyVO.getPrimaryKey() != null) {
				pk_body = currBodyVO.getPrimaryKey().trim();
			} else {
				// 获取当前的伪列值
				pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(currentRow, "precolumn");
			}
			// 在编辑缓存Map中存储信息、 组装唯一标识key
			String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;
			if (this.getMainGrandModel().getTestAddFirstMap().get(cardUniqueKey) == null) {
				flag = false;
			} else {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 卡片编辑状态下根据孙面板切页签获取对应的孙表数据
	 */
	public List<Object> getGrandCardDataByGrandTab(BillForm mainbillform, int currentRow, BillForm grandbillform,
			MainGrandRelationShip mainGrandRelationShip) {

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		CircularlyAccessibleValueObject[] childrenVOs = MainGrandUtil.getBodyVOsByTabCode(mainbillform, bodyTabCode);
		if (childrenVOs == null || childrenVOs.length == 0) {
			return null;
		}
		String grandTabCode = grandbillform.getBillCardPanel().getCurrentBodyTableCode();
		// 显示当前选中表体行的数据
		SuperVO currBodyVO = null;
		if (currentRow >= 0) {
			currBodyVO = (SuperVO) childrenVOs[currentRow];
		}
		// 存取孙表当前页签下的VOList
		ArrayList<Object> grandVOList = new ArrayList<Object>();
		if (currBodyVO != null) {
			String pk_body = this.getPkStatusIsEdit(currBodyVO);
			String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;

			grandVOList = this.getGrandListStatusIsEdit(cardUniqueKey);
			// 标识已经查过缓存
			this.getMainGrandModel().getTestAddFirstMap().put(cardUniqueKey, "1");
		}
		return grandVOList;
	}

	/**
	 * 卡片编辑状态删除选择表体行关联删除对应的孙表信息
	 */
	public void delGrandCardDataByMainRowEdit(BillForm mainbillform, int deleline, BillForm grandbillform) {
		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		CircularlyAccessibleValueObject[] childrenVOs = MainGrandUtil.getBodyVOsByTabCode(mainbillform, bodyTabCode);
		if (childrenVOs == null || childrenVOs.length == 0) {
			return;
		}
		// 获取当前孙页签
		SuperVO lastBodyVO = (SuperVO) childrenVOs[deleline];
		String del_pk_body = this.getPkStatusIsEdit(lastBodyVO);
		String lastcardUniqueKey = "";
		String[] tabCodes = grandbillform.getBillCardPanel().getBillData().getBodyTableCodes();
		if (!ArrayUtils.isEmpty(tabCodes)) {
			for (String grandTabCode : tabCodes) {
				lastcardUniqueKey = bodyTabCode + del_pk_body + grandTabCode;
				ArrayList<Object> delGrandVOList = this.getMainGrandModel().getBufferCardEditMap()
						.get(lastcardUniqueKey);
				// 开始置孙VO的状态为删除状态(此处不清除孙面板缓存)
				if (delGrandVOList != null && delGrandVOList.size() != 0) {
					for (Object grandVoObject : delGrandVOList) {
						((CircularlyAccessibleValueObject) grandVoObject).setStatus(VOStatus.DELETED);
					}
				}
				// 清空界面数据
				grandbillform.getBillCardPanel().getBillData().getBillModel(grandTabCode).clearBodyData();
			}
		}
	}

	/**
	 * 卡片新增状态删除选择表体行关联删除对应的孙表信息
	 */
	public void delGrandCardDataByMainRowAdd(BillForm mainbillform, int deleline, BillForm grandbillform) {
		// 获取当前子页签
		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		String del_pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(deleline, "precolumn");
		if (grandbillform != null) {
			// 组装唯一标识key 当前子页签+删除子行标识+当前孙页签
			String[] tabCodes = grandbillform.getBillCardPanel().getBillData().getBodyTableCodes();
			if (!ArrayUtils.isEmpty(tabCodes)) {
				for (String grandTabCode : tabCodes) {
					String lastcardUniqueKey = bodyTabCode + del_pk_body + grandTabCode;

					// 新增状态下置缓存为空
					if (this.getMainGrandModel().getBufferCardAddMap().get(lastcardUniqueKey) != null) {
						this.getMainGrandModel().getBufferCardAddMap().get(lastcardUniqueKey).clear();
					}
					// 清空界面数据
					grandbillform.getBillCardPanel().getBillData().getBillModel(grandTabCode).clearBodyData();
				}
			}
		}
	}

	/**
	 * 新增状态下缓存上一次的数据
	 */
	private void putDataIntoAddMap(RowChangeBean rowChangeBean, MainGrandRelationShip maingrandrelationship,
			IGrandValidationService grandValidationService, MainGrandBlankFilter filter) {

		BillForm mainbillform = rowChangeBean.getMainForm();
		BillForm grandbillform = rowChangeBean.getGrandForm();
		int lastrow = rowChangeBean.getLastRow();

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		String className = MainGrandUtil.getClassNameByTabCode(mainbillform, bodyTabCode);
		// 获取所有孙页签
		String[] tabCodes = null;
		tabCodes = grandbillform.getBillCardPanel().getBillData().getBodyTableCodes();

		// 存储上一次的选中行、再显示当前选中行的数据
		if (lastrow >= 0) {
			// 推单场景pk已经存在
			String last_pk_body = null;
			try {
				CircularlyAccessibleValueObject bodyValueRowVO = mainbillform.getBillCardPanel().getBillModel()
						.getBodyValueRowVO(lastrow, className);
				if (null != bodyTabCode) {
					last_pk_body = bodyValueRowVO.getPrimaryKey();
				}
			} catch (BusinessException e1) {
				Logger.error(e1.getMessage(), e1);
			}
			// 伪列
			if (last_pk_body == null) {
				if("nc.vo.qcco.commission.CommissionBVO".equals(className)){
					last_pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(lastrow, "uniqueid");
				}else if("nc.vo.qcco.task.TaskBVO".equals(className)){
					last_pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(lastrow, "uniquekey");
				}else{
					last_pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(lastrow, "precolumn");
				}
				
			}
			if (last_pk_body == null) {
				this.addPreColumBillItemToBody(mainbillform, lastrow, bodyTabCode, RowNumUtil.getRowNumUtil()
						.generateRowNo());
				last_pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(lastrow, "precolumn");
			}
			String lastcardUniqueKey = "";
			if (!ArrayUtils.isEmpty(tabCodes)) {
				for (String grandTabCode : tabCodes) {
					lastcardUniqueKey = bodyTabCode + last_pk_body + grandTabCode;
					CircularlyAccessibleValueObject[] lastGrandVos = MainGrandUtil.getBodyVOsByTabCode(grandbillform,
							grandTabCode);
					if (lastGrandVos == null || lastGrandVos.length == 0) {
						getMainGrandModel().getBufferCardAddMap().remove(lastcardUniqueKey);
						continue;
					}
					ArrayList<Object> lastGrandVosList = new ArrayList<Object>();
					for (CircularlyAccessibleValueObject grandVo : lastGrandVos) {
						lastGrandVosList.add(grandVo);
					}
					this.getMainGrandModel().getBufferCardAddMap().put(lastcardUniqueKey, lastGrandVosList);
				}
				HashMap<String, ArrayList<Object>> statusMap = this.getMainGrandModel().getBufferCardAddMap();
				if (null != filter && filter.getGrandFilterMap() != null) {
					this.filterNotNullGrand(rowChangeBean, filter, statusMap);
				}
				if (lastrow >= 0 && lastrow != rowChangeBean.getCurrentRow()) {
					try {
						grandValidationService.validationNullForGrand(grandbillform);
					} catch (ValidationException e) {
						ShowStatusBarMsgUtil.showErrorMsgWithClear(e.getMessage(), e.getMessage(), grandbillform
								.getModel().getContext());
						Logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}

	/**
	 * 新增状态下缓存上一次的数据
	 */
	private void putDataIntoAddMapForCurrentRow(RowChangeBean rowChangeBean, MainGrandRelationShip maingrandrelationship) {

		BillForm mainbillform = rowChangeBean.getMainForm();
		BillForm grandbillform = rowChangeBean.getGrandForm();
		int currentRow = rowChangeBean.getCurrentRow();

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		// 获取所有孙页签
		String[] tabCodes = null;
		tabCodes = grandbillform.getBillCardPanel().getBillData().getBodyTableCodes();
		// 存储上一次的选中行、再显示当前选中行的数据
		if (currentRow >= 0) {
			String last_pk_body = (String) mainbillform.getBillCardPanel().getBillModel()
					.getValueAt(currentRow, "precolumn");
			if (last_pk_body == null) {
				this.addPreColumBillItemToBody(mainbillform, currentRow, bodyTabCode, RowNumUtil.getRowNumUtil()
						.generateRowNo());
				last_pk_body = (String) mainbillform.getBillCardPanel().getBillModel()
						.getValueAt(currentRow, "precolumn");
			}
			String lastcardUniqueKey = "";
			if (!ArrayUtils.isEmpty(tabCodes)) {
				for (String grandTabCode : tabCodes) {
					lastcardUniqueKey = bodyTabCode + last_pk_body + grandTabCode;
					CircularlyAccessibleValueObject[] lastGrandVos = MainGrandUtil.getBodyVOsByTabCode(grandbillform,
							grandTabCode);
					if (lastGrandVos == null || lastGrandVos.length == 0) {
						getMainGrandModel().getBufferCardAddMap().remove(lastcardUniqueKey);
						continue;
					}
					ArrayList<Object> lastGrandVosList = new ArrayList<Object>();
					for (CircularlyAccessibleValueObject grandVo : lastGrandVos) {
						lastGrandVosList.add(grandVo);
					}
					this.getMainGrandModel().getBufferCardAddMap().put(lastcardUniqueKey, lastGrandVosList);
				}
			}
		}
	}

	/**
	 * 修改状态下存储上一次的数据
	 */
	private void putDataIntoEditMap(RowChangeBean rowChangeBean, IGrandValidationService grandValidationService,
			MainGrandBlankFilter filter) {

		BillForm mainbillform = rowChangeBean.getMainForm();
		BillForm grandbillform = rowChangeBean.getGrandForm();
		int lastrow = rowChangeBean.getLastRow();

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		CircularlyAccessibleValueObject[] childVOs = MainGrandUtil.getBodyVOsByTabCode(mainbillform, bodyTabCode);
		if (childVOs == null || childVOs.length == 0) {
			return;
		}
		String grandTabCode = grandbillform.getBillCardPanel().getCurrentBodyTableCode();
		if (lastrow >= 0) {

			SuperVO lastBodyVO = (SuperVO) childVOs[lastrow];
			String last_pk_body = this.getPkStatusIsEdit(lastBodyVO);
			String lastcardUniqueKey = bodyTabCode + last_pk_body + grandTabCode;
			ArrayList<Object> lastGrandVosList = new ArrayList<Object>();
			CircularlyAccessibleValueObject[] lastGrandVos = MainGrandUtil.getBodyVOsByTabCode(grandbillform,
					grandTabCode);
			this.processDelVOs(grandbillform, grandTabCode, lastcardUniqueKey);
			if (lastGrandVos == null) {
				return;
			}
			for (CircularlyAccessibleValueObject grandVo : lastGrandVos) {
				lastGrandVosList.add(grandVo);
			}
			this.getMainGrandModel().getBufferCardEditMap().put(lastcardUniqueKey, lastGrandVosList);
			HashMap<String, ArrayList<Object>> statusMap = this.getMainGrandModel().getBufferCardEditMap();
			if (null != filter && filter.getGrandFilterMap() != null) {
				this.filterNotNullGrand(rowChangeBean, filter, statusMap);
			}
			if (lastrow != rowChangeBean.getCurrentRow()) {
				try {
					grandValidationService.validationNullForGrand(grandbillform);
				} catch (ValidationException e) {
					ShowStatusBarMsgUtil.showErrorMsgWithClear(e.getMessage(), e.getMessage(), grandbillform.getModel()
							.getContext());
					Logger.error(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 孙表的非空过滤
	 */
	private void filterNotNullGrand(RowChangeBean rowChangeBean, MainGrandBlankFilter filter,
			HashMap<String, ArrayList<Object>> statusMap) {

		BillForm mainbillform = rowChangeBean.getMainForm();
		BillForm grandbillform = rowChangeBean.getGrandForm();
		int lastrow = rowChangeBean.getLastRow();

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		CircularlyAccessibleValueObject[] childVOs = MainGrandUtil.getBodyVOsByTabCode(mainbillform, bodyTabCode);
		if (childVOs == null || childVOs.length == 0) {
			return;
		}
		String[] grandTabCodes = grandbillform.getBillCardPanel().getBillData().getBodyTableCodes();
		if (lastrow >= 0) {
			SuperVO lastBodyVO = (SuperVO) childVOs[lastrow];
			String last_pk_body = this.getPkStatusIsEdit(lastBodyVO);
			for (String grandTabCode : grandTabCodes) {
				String cardUniqueKey = bodyTabCode + last_pk_body + grandTabCode;
				List<Object> grandGrandList = statusMap.get(cardUniqueKey);
				List<Object> grandRowNumList = filter.filterMultiBlankGrandData(grandGrandList, grandTabCode);
				if (grandRowNumList != null && grandRowNumList.size() > 0) {
					BillScrollPane currentPanel = grandbillform.getBillCardPanel().getBodyPanel(grandTabCode);
					int[] nums = new int[grandRowNumList.size()];
					for (int i = 0; i < grandRowNumList.size(); i++) {
						nums[i] = ((Integer) grandRowNumList.get(i)).intValue();
						// 把过滤移除的孙表数据，放到delGrandMap
						if (null == this.getMainGrandModel().getDelGrandMap().get(cardUniqueKey)) {
							this.getMainGrandModel().getDelGrandMap().put(cardUniqueKey, new ArrayList<Object>());
						}
						this.getMainGrandModel().getDelGrandMap().get(cardUniqueKey).add(grandGrandList.get(nums[i]));
					}
					currentPanel.delLine(nums);
					CircularlyAccessibleValueObject[] grandVOs = MainGrandUtil.getBodyVOsByTabCode(grandbillform,
							grandTabCode);
					ArrayList<Object> lastGrandVosList = new ArrayList<Object>();
					for (CircularlyAccessibleValueObject grandVO : grandVOs) {
						lastGrandVosList.add(grandVO);
					}
					if (mainbillform.getModel().getUiState() == UIState.ADD) {

						this.getMainGrandModel().getBufferCardAddMap().put(cardUniqueKey, lastGrandVosList);
					} else {

						this.getMainGrandModel().getBufferCardEditMap().put(cardUniqueKey, lastGrandVosList);
					}
				}
			}
		}
	}

	/**
	 * 修改状态下存储上一次的数据
	 */
	private void putDataIntoEditMapForCurrentLine(RowChangeBean rowChangeBean) {

		BillForm mainbillform = rowChangeBean.getMainForm();
		BillForm grandbillform = rowChangeBean.getGrandForm();
		int currentRow = rowChangeBean.getCurrentRow();

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		CircularlyAccessibleValueObject[] childVOs = MainGrandUtil.getBodyVOsByTabCode(mainbillform, bodyTabCode);
		if (childVOs == null || childVOs.length == 0) {
			return;
		}
		String grandTabCode = grandbillform.getBillCardPanel().getCurrentBodyTableCode();
		if (currentRow >= 0) {
			SuperVO lastBodyVO = (SuperVO) childVOs[currentRow];
			String last_pk_body = this.getPkStatusIsEdit(lastBodyVO);
			String lastcardUniqueKey = bodyTabCode + last_pk_body + grandTabCode;
			ArrayList<Object> lastGrandVosList = new ArrayList<Object>();
			CircularlyAccessibleValueObject[] lastGrandVos = MainGrandUtil.getBodyVOsByTabCode(grandbillform,
					grandTabCode);
			this.processDelVOs(grandbillform, grandTabCode, lastcardUniqueKey);
			if (lastGrandVos == null || lastGrandVos.length == 0) {
				return;
			}
			for (CircularlyAccessibleValueObject grandVo : lastGrandVos) {
				lastGrandVosList.add(grandVo);
			}
			this.getMainGrandModel().getBufferCardEditMap().put(lastcardUniqueKey, lastGrandVosList);
		}
	}

	/**
	 * 获得pk(VO没有的话就去获得伪列)
	 */
	public String getPkStatusIsEdit(CircularlyAccessibleValueObject changeChild) {
		if (changeChild == null) {
			return null;
		}
		String children_pk = null;
		try {
			children_pk = changeChild.getPrimaryKey();
		} catch (BusinessException e) {
			Logger.error(e.getMessage(), e);
		}
		//mod 添加了一个新字段用来唯一标识一行 Ares.tank 2019年7月2日22:39:18
		if (changeChild instanceof TaskBVO && children_pk == null) {
			children_pk = (String) changeChild.getAttributeValue("uniquekey");
		}else if(changeChild instanceof CommissionBVO && children_pk == null){
			children_pk = (String) changeChild.getAttributeValue("uniqueid"); 
			/*if(children_pk==null){
				String uuid = UUID.randomUUID().toString();	
				uuid = uuid.replace("-", "");
				changeChild.setAttributeValue("uniqueid",uuid);
				children_pk = (String) changeChild.getAttributeValue("uniqueid"); 
			}*/
		}
		if (children_pk == null) {
			children_pk = (String) changeChild.getAttributeValue("precolumn");
			if (children_pk == null) {
				children_pk = (String) changeChild.getAttributeValue("rowno");
			}
		}
		return children_pk;
	}

	/**
	 * 处理删除的VO
	 */
	private void processDelVOs(BillForm grandbillform, String grandTabCode, String cardUniqueKey) {

		CircularlyAccessibleValueObject[] lastDeleteGrandVos = MainGrandUtil.getBodyChangeVoByTabCode(grandbillform,
				grandTabCode);
		ArrayList<Object> lastDelGrandVosList = new ArrayList<Object>();
		if (lastDeleteGrandVos == null || lastDeleteGrandVos.length == 0) {
			return;
		}
		// 处理界面删除的带有PK的孙表VO
		for (CircularlyAccessibleValueObject lastDeleteGrandVo : lastDeleteGrandVos) {
			try {
				lastDeleteGrandVo.getAttributeValue("precolumn");
				// if (lastDeleteGrandVo.getPrimaryKey() != null) {
				if (lastDeleteGrandVo.getStatus() == VOStatus.DELETED) {
					lastDelGrandVosList.add(lastDeleteGrandVo);
				}
				// }
			} catch (Exception e) {
				Logger.error(e.getMessage(), e);
			}
		}
		// 存入删除的孙表VO信息
		if (lastDelGrandVosList.size() != 0) {
			this.getMainGrandModel().getDelGrandMap().put(cardUniqueKey, lastDelGrandVosList);
		}
	}

	/**
	 * 设置当前孙表数据
	 * 
	 * @param mainPanel
	 */
	public ArrayList<Object> setGrand2CurrentPanel(CardGrandPanelComposite mainPanel) {

		ArrayList<Object> queryList = null;

		BillForm mainBillForm = (BillForm) mainPanel.getMainPanel();
		String currentbodyTabCode = mainBillForm.getBillCardPanel().getCurrentBodyTableCode();
		BillForm grandBillForm = (BillForm) mainPanel.getMaingrandrelationship().getBodyTabTOGrandCardComposite()
				.get(currentbodyTabCode);
		if (grandBillForm != null) {
			String className = MainGrandUtil.getClassNameByTabCode(mainBillForm, currentbodyTabCode);
			CircularlyAccessibleValueObject[] childBodyVos = null;
			if (className != null) {
				childBodyVos = mainBillForm.getBillCardPanel().getBillModel(currentbodyTabCode)
						.getBodyValueVOs(className);
			}
			if (childBodyVos == null || childBodyVos.length == 0) {
				return null;
			}
			String currentgrandBodyTabCode = grandBillForm.getBillCardPanel().getCurrentBodyTableCode();
			int rowNum = mainBillForm.getBillCardPanel().getBillTable().getSelectedRow();
			if (rowNum >= 0) {
				SuperVO selectedChild = (SuperVO) childBodyVos[rowNum];
				String cardUniqueKey = currentbodyTabCode + selectedChild.getPrimaryKey() + currentgrandBodyTabCode;
				queryList = this.getMainGrandModel().getQueryDataMap().get(cardUniqueKey);
			}
		}
		return queryList;
	}

	/**
	 * 将孙表信息设置到子表属性中去
	 */
	public CircularlyAccessibleValueObject setGrandToChild(String tableCode, CircularlyAccessibleValueObject childVO,
			List<?> grandVOList) {

		NCObject ncObject = NCObject.newInstance(childVO);
		IBean bean = ncObject.getRelatedBean();
		// 获取子表中对应孙实体的属性列表
		List<IAttribute> attrList = MainGrandBusiUtil.getInstance().queryChildAttr(bean);
		if (grandVOList.size() != 0) {
			for (IAttribute attr : attrList) {
				if (attr.getName().equals(tableCode)) {
					Object obj = grandVOList.get(0);
					attr.getAccessStrategy().setValue(childVO, attr, grandVOList.toArray(ArrayUtil.toArray(obj)));
				}
			}
		}
		return childVO;
	}

	/**
	 * 编辑态获得孙表数据 <li>当编辑缓存中有孙表数据，然后检查删除缓存中时候有，有则删除</li> <li>
	 * 当编辑缓存中没有孙表数据，检查删除缓存，如果删除缓存中有，则为空</li> <li>
	 * 当编辑缓存中没有孙表数据，删除缓存中也没有，则直接从查询缓存中取</li>
	 */
	private ArrayList<Object> getGrandListStatusIsEdit(String cardUniqueKey) {
		ArrayList<Object> grandVOList = null;
		// 查询对应页签数据并设置到孙表页签
		ArrayList<Object> grandObjectList = new ArrayList<Object>();
		grandObjectList = this.mainGrandModel.getBufferCardEditMap().get(cardUniqueKey);
		if (grandObjectList != null && grandObjectList.size() != 0) {
			ArrayList<Object> delList = this.mainGrandModel.getDelGrandMap().get(cardUniqueKey);
			if (delList != null && delList.size() != 0) {
				grandObjectList = MainGrandUtil.processList(grandObjectList,
						this.mainGrandModel.getDelGrandMap().get(cardUniqueKey));
			}
			grandVOList = grandObjectList;
		} else {
			if (!this.getMainGrandModel().getDelGrandMap().isEmpty()
					&& this.getMainGrandModel().getDelGrandMap().get(cardUniqueKey) != null) {
				grandVOList = new ArrayList<Object>();
			} else {
				grandVOList = this.getMainGrandModel().getQueryDataMap().get(cardUniqueKey);
			}
			this.getMainGrandModel().getBufferCardEditMap().put(cardUniqueKey, grandVOList);
		}
		return grandVOList;
	}

	/**
	 * 判断是否要进入行切换事件的处理机制里面去
	 */
	private boolean isNeedToRowChange(BillForm mainForm) {

		boolean needToChangeRow = false;

		List<Action> actionList = mainForm.getBodyLineActions();
		for (Action action : actionList) {
			if (action instanceof AbstractGrandBodyTableExtendAction) {

				String orgPk = ((AbstractGrandBodyTableExtendAction) action).getOrgSelectChildPk();
				String newPk = MainGrandUtil.getPkFormPanel(mainForm);
				if (StringUtils.isEmpty(orgPk) || StringUtils.isEmpty(newPk)) {
					continue;
				}
				if (orgPk.equals(newPk)) {
					needToChangeRow = true;
				}
				((AbstractGrandBodyTableExtendAction) action).setOrgSelectChildPk(null);
			}
		}
		return needToChangeRow;
	}

	/**
	 * 根据主表状态设置孙表状态
	 */
	public void setGrandStatusByMainForm(CardGrandPanelComposite zhuzisuncard) {

		BillForm mainPanel = (BillForm) zhuzisuncard.getMainPanel();
		String[] tabCodes = mainPanel.getBillCardPanel().getBillData().getBodyTableCodes();
		UIState mainStatus = mainPanel.getModel().getUiState();
		for (String tabCode : tabCodes) {
			BillForm grandForm = (BillForm) zhuzisuncard.getMaingrandrelationship().getBodyTabTOGrandCardComposite()
					.get(tabCode);
			if (grandForm != null) {
				grandForm.getModel().setUiState(mainStatus);
			}
		}

	}

	/**
	 * 孙表自增行处理(调用AddLine按钮完成自增操作)
	 */
	public void grandAddLine(BillForm grandBillForm) {
		BillCardPanel billCardPanel = grandBillForm.getBillCardPanel();
		String currentGrandTabCode = billCardPanel.getCurrentBodyTableCode();
		BillScrollPane currentPanel = billCardPanel.getBodyPanel(currentGrandTabCode);
		if (currentPanel.getBillTableAction(0) != null) {
			ActionEvent ae = new ActionEvent(currentPanel.getTable(), BillScrollPane.ADDLINE, "AddLine");
			currentPanel.getBillTableAction(BillScrollPane.ADDLINE).actionPerformed(ae);
		}
	}

	/**
	 * 把伪列加入到面板表体上
	 */
	public void addPreColumBillItemToBody(BillForm mainBillForm, int row, String tabcode, String value) {
		BillModel billModel = mainBillForm.getBillCardPanel().getBillModel(tabcode);
		billModel.setValueAt(value, row, "precolumn");
	}

	/**
	 * 对卡片表体进行构造伪列
	 */
	public void constructPreColumBillItem(BillForm mainBillForm) {
		// 添加伪列操作
		BillItem[] bodyItems = mainBillForm.getBillCardPanel().getBillModel().getBodyItems();
		BillItem newItem = new BillItem();
		BillItem[] newItems = Arrays.copyOf(bodyItems, bodyItems.length + 1);
		newItem.setKey("precolumn");
		newItem.setShow(false);
		newItem.setNull(false);
		newItem.setShowOrder(bodyItems.length + 1);
		newItems[bodyItems.length] = newItem;
		BillModel billModel = mainBillForm.getBillCardPanel().getBillModel();
		billModel.setBodyItems(newItems);
		mainBillForm.getBillCardPanel().getBodyPanel().setTableModel(billModel);
	}

	public MainGrandModel getMainGrandModel() {
		return this.mainGrandModel;
	}

	public void setMainGrandModel(MainGrandModel mainGrandModel) {
		this.mainGrandModel = mainGrandModel;
	}
}
