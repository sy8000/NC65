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
 * ����������Ĺ�����
 */
public class MainGrandAssist {

	private MainGrandModel mainGrandModel = null;

	/**
	 * �б�״̬�¸�����ѡ����VO��ȡ��Ӧ���������
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
	 * �б�״̬�¸�����ѡ����VO��ȡ��Ӧ��������� ssx added for overload
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
	 * ������ҳǩ���¶�Ӧ���������Ϣ(�������б����)
	 */
	public void updateGrandListByBodyTab(ListGrandPanelComposite listcombo) {

		String currentBodyTab = ((BillListView) listcombo.getMainPanel()).getBillListPanel().getChildListPanel()
				.getTableCode();
		Component grandComposite = (Component) listcombo.getMaingrandrelationship().getBodyTabTOGrandListComposite()
				.get(currentBodyTab);
		float dividerLocation = listcombo.getListCardSplitPane().getDividerLocation();
		// �������ʾʱ��ȡ����dividerLocation���������ģ����Ա���uiDividerLocation
		boolean isDisplay = listcombo.getGrandPanel().isVisible();
		if (isDisplay && grandComposite == null) {
			listcombo.setUiDividerLocation(dividerLocation / listcombo.getListCardSplitPane().getHeight());
		}
		this.listBodyTabChange(listcombo, currentBodyTab);

	}

	// �б�����������Ƿ���ʾ�����Ϣ
	private void listBodyTabChange(ListGrandPanelComposite listcombo, String currentBodyTab) {
		// ��ȡ��������ʾ״̬
		boolean isDisplay = this.getMainGrandModel().isHandleListCardIsShow();
		// ��ȡ�����ļ��е���Ϣ
		Component grandComposite = (Component) listcombo.getMaingrandrelationship().getBodyTabTOGrandListComposite()
				.get(currentBodyTab);

		if (grandComposite == null) {
			((ListGrandPanel) listcombo.getGrandPanel()).setVisible(false);
		} else {
			if (isDisplay == true) {// ���������ʾ�������ʾ�����
				listcombo.getListCardSplitPane().setDividerLocation(listcombo.getUiDividerLocation());
				((ListGrandPanel) listcombo.getGrandPanel()).setVisible(true);
				((CardLayout) listcombo.switchGrandPanel.getLayout()).show(listcombo.switchGrandPanel, currentBodyTab);
			}
		}
	}

	/**
	 * ������ҳǩ���¶�Ӧ���������Ϣ(�����￨Ƭ���)
	 * 
	 * @param listcombo
	 */
	public void updateGrandCardByBodyTab(CardGrandPanelComposite cardcombo) {

		String currentBodyTab = ((BillForm) cardcombo.getMainPanel()).getBillCardPanel().getCurrentBodyTableCode();
		// �ָ��ߵ�ǰλ��
		Component grandComposite = (Component) cardcombo.getMaingrandrelationship().getBodyTabTOGrandCardComposite()
				.get(currentBodyTab);
		float dividerLocation = cardcombo.getListCardSplitPane().getDividerLocation();
		// �������ʾʱ��ȡ����dividerLocation���������ģ����Ա���uiDividerLocation
		boolean isDisplay = cardcombo.getGrandPanel().isVisible();
		if (isDisplay && grandComposite == null) {
			cardcombo.setUiDividerLocation(dividerLocation / cardcombo.getListCardSplitPane().getHeight());
		}
		this.cardBodyTabChange(cardcombo, currentBodyTab);
	}

	// ��Ƭ����ҳǩ�л���ʾ����
	private void cardBodyTabChange(CardGrandPanelComposite cardcombo, String currentBodyTab) {
		// ��ȡ�����ļ��е���Ϣ
		Component grandComposite = (Component) cardcombo.getMaingrandrelationship().getBodyTabTOGrandCardComposite()
				.get(currentBodyTab);

		// ��ȡ��������ʾ״̬
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
	 * �༭״̬�¿�Ƭ������ѡ����VO��ȡ��Ӧ���������ͬʱ�����������
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
		// ��ʾ��ǰѡ�б����е�����
		SuperVO currBodyVO = null;
		if (currentRow >= 0) {
			currBodyVO = (SuperVO) childVOs[currentRow];
		}
		String pk_body = this.getPkStatusIsEdit(currBodyVO);
		// ��̥������,�ͱ���������! Tank 2019��4��10��23:16:27
		RowNumUtil.getRowNumUtil().generateRowNo();
		/*
		 * if (pk_body == null) { String precolum =
		 * RowNumUtil.getRowNumUtil().generateRowNo();
		 * this.addPreColumBillItemToBody(mainbillform, currentRow, bodyTabCode,
		 * precolum); currBodyVO.setAttributeValue("precolumn", precolum);
		 * pk_body = (String) currBodyVO.getAttributeValue("precolumn"); }
		 */
		String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;
		// ��ȡ���ǰҳǩ�µ�VOList
		ArrayList<Object> grandVOList = this.getGrandListStatusIsEdit(cardUniqueKey);

		this.getMainGrandModel().getTestAddFirstMap().put(cardUniqueKey, "1");
		return grandVOList;
	}

	/**
	 * ����״̬�¿�Ƭ������ѡ����VO��ȡ��Ӧ���������ͬʱ�����������
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
		// ����״̬����ʾ����
		Map<String, ArrayList<Object>> grandAllObjectMap = new HashMap<String, ArrayList<Object>>();

		String className = MainGrandUtil.getClassNameByTabCode(mainbillform, bodyTabCode);
		// �Ƶ�����pk�Ѿ�����
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
		// α��
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
		// �����߼�ֱ�Ӵӻ����ж�ȡ���VO���ݲ�����
		if (!ArrayUtils.isEmpty(grandTabCodes)) {
			for (String grandTabCode : grandTabCodes) {
				ArrayList<Object> grandObjectList = new ArrayList<Object>();
				// ��װΨһ��ʶkey
				String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;
				grandObjectList = this.getMainGrandModel().getBufferCardAddMap().get(cardUniqueKey);
				grandAllObjectMap.put(grandTabCode, grandObjectList);
			}
		}
		return grandAllObjectMap;
	}

	/**
	 * ��Ƭ�Ǳ༭״̬�¸�����ѡ����VO��ȡ��Ӧ���������
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
	 * ��Ƭ����ʱ�л���ҳǩ�洢������������
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
				// ��װ��ʾ��
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
	 * ��Ƭ�༭ʱ�л���ҳǩ�洢������������
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
				// ���ݵ�ǰ���ҳǩ��ȡ��ҳǩ�µ��������VO
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
				// ���ݻ��浽�༭map��
				this.getMainGrandModel().getBufferCardEditMap().put(uniqueKey, grandVOList);
			}
		}
	}

	/**
	 * ��Ƭ����״̬����л�ҳǩ������ѡ����VO��ȡ��Ӧ���������
	 */
	public List<Object> showGrandCardDataByGrandTab(BillForm mainbillform, int currentRow, BillForm grandbillform) {

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();

		String grandTabCode = grandbillform.getBillCardPanel().getCurrentBodyTableCode();

		String pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(currentRow, "precolumn");
		// �ڱ༭����Map�д洢��Ϣ�� ��װΨһ��ʶkey
		String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;
		// ��ȡ���ǰҳǩ�µ�VOList
		ArrayList<Object> grandObjectList = new ArrayList<Object>();
		grandObjectList = this.getMainGrandModel().getBufferCardAddMap().get(cardUniqueKey);
		if (grandObjectList == null) {
			grandObjectList = new ArrayList<Object>();
		}
		return grandObjectList;
	}

	/**
	 * ��Ƭ�Ǳ༭״̬����л�ҳǩ������ѡ����VO��ȡ��Ӧ���������
	 */
	public List<Object> showGrandCardDataByGrandTabNotEdit(BillForm mainbillform, int currentRow,
			BillForm grandbillform, MainGrandRelationShip mainGrandRelationShip) {

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();

		CircularlyAccessibleValueObject[] childBodyVos = MainGrandUtil.getBodyVOsByTabCode(mainbillform, bodyTabCode);
		if (childBodyVos == null || childBodyVos.length == 0) {
			return null;
		}
		// ��ȡ���ǰҳǩ�µ�VOList
		ArrayList<Object> grandVOList = new ArrayList<Object>();
		if (currentRow >= 0) {
			SuperVO currBodyVO = (SuperVO) childBodyVos[currentRow];
			String pk_body = "";
			if (currBodyVO != null && currBodyVO.getPrimaryKey() != null) {
				pk_body = currBodyVO.getPrimaryKey();
			}
			String grandTabCode = grandbillform.getBillCardPanel().getCurrentBodyTableCode();
			// ����Map�д洢��Ϣ�� ��װΨһ��ʶkey
			String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;
			// ��ȡ���ǰҳǩ�µ�VOList
			grandVOList = this.getMainGrandModel().getQueryDataMap().get(cardUniqueKey);
		}
		return grandVOList;
	}

	/**
	 * �ж�����������Ƿ����
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
		boolean flag = false;// ��ʶ�Ƿ��Ѿ�����
		if (currBodyVO != null) {
			// ��ȡ������VO����ֵ
			if (currBodyVO.getPrimaryKey() != null) {
				pk_body = currBodyVO.getPrimaryKey().trim();
			} else {
				// ��ȡ��ǰ��α��ֵ
				pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(currentRow, "precolumn");
			}
			// �ڱ༭����Map�д洢��Ϣ�� ��װΨһ��ʶkey
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
	 * ��Ƭ�༭״̬�¸����������ҳǩ��ȡ��Ӧ���������
	 */
	public List<Object> getGrandCardDataByGrandTab(BillForm mainbillform, int currentRow, BillForm grandbillform,
			MainGrandRelationShip mainGrandRelationShip) {

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		CircularlyAccessibleValueObject[] childrenVOs = MainGrandUtil.getBodyVOsByTabCode(mainbillform, bodyTabCode);
		if (childrenVOs == null || childrenVOs.length == 0) {
			return null;
		}
		String grandTabCode = grandbillform.getBillCardPanel().getCurrentBodyTableCode();
		// ��ʾ��ǰѡ�б����е�����
		SuperVO currBodyVO = null;
		if (currentRow >= 0) {
			currBodyVO = (SuperVO) childrenVOs[currentRow];
		}
		// ��ȡ���ǰҳǩ�µ�VOList
		ArrayList<Object> grandVOList = new ArrayList<Object>();
		if (currBodyVO != null) {
			String pk_body = this.getPkStatusIsEdit(currBodyVO);
			String cardUniqueKey = bodyTabCode + pk_body + grandTabCode;

			grandVOList = this.getGrandListStatusIsEdit(cardUniqueKey);
			// ��ʶ�Ѿ��������
			this.getMainGrandModel().getTestAddFirstMap().put(cardUniqueKey, "1");
		}
		return grandVOList;
	}

	/**
	 * ��Ƭ�༭״̬ɾ��ѡ������й���ɾ����Ӧ�������Ϣ
	 */
	public void delGrandCardDataByMainRowEdit(BillForm mainbillform, int deleline, BillForm grandbillform) {
		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		CircularlyAccessibleValueObject[] childrenVOs = MainGrandUtil.getBodyVOsByTabCode(mainbillform, bodyTabCode);
		if (childrenVOs == null || childrenVOs.length == 0) {
			return;
		}
		// ��ȡ��ǰ��ҳǩ
		SuperVO lastBodyVO = (SuperVO) childrenVOs[deleline];
		String del_pk_body = this.getPkStatusIsEdit(lastBodyVO);
		String lastcardUniqueKey = "";
		String[] tabCodes = grandbillform.getBillCardPanel().getBillData().getBodyTableCodes();
		if (!ArrayUtils.isEmpty(tabCodes)) {
			for (String grandTabCode : tabCodes) {
				lastcardUniqueKey = bodyTabCode + del_pk_body + grandTabCode;
				ArrayList<Object> delGrandVOList = this.getMainGrandModel().getBufferCardEditMap()
						.get(lastcardUniqueKey);
				// ��ʼ����VO��״̬Ϊɾ��״̬(�˴����������建��)
				if (delGrandVOList != null && delGrandVOList.size() != 0) {
					for (Object grandVoObject : delGrandVOList) {
						((CircularlyAccessibleValueObject) grandVoObject).setStatus(VOStatus.DELETED);
					}
				}
				// ��ս�������
				grandbillform.getBillCardPanel().getBillData().getBillModel(grandTabCode).clearBodyData();
			}
		}
	}

	/**
	 * ��Ƭ����״̬ɾ��ѡ������й���ɾ����Ӧ�������Ϣ
	 */
	public void delGrandCardDataByMainRowAdd(BillForm mainbillform, int deleline, BillForm grandbillform) {
		// ��ȡ��ǰ��ҳǩ
		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		String del_pk_body = (String) mainbillform.getBillCardPanel().getBillModel().getValueAt(deleline, "precolumn");
		if (grandbillform != null) {
			// ��װΨһ��ʶkey ��ǰ��ҳǩ+ɾ�����б�ʶ+��ǰ��ҳǩ
			String[] tabCodes = grandbillform.getBillCardPanel().getBillData().getBodyTableCodes();
			if (!ArrayUtils.isEmpty(tabCodes)) {
				for (String grandTabCode : tabCodes) {
					String lastcardUniqueKey = bodyTabCode + del_pk_body + grandTabCode;

					// ����״̬���û���Ϊ��
					if (this.getMainGrandModel().getBufferCardAddMap().get(lastcardUniqueKey) != null) {
						this.getMainGrandModel().getBufferCardAddMap().get(lastcardUniqueKey).clear();
					}
					// ��ս�������
					grandbillform.getBillCardPanel().getBillData().getBillModel(grandTabCode).clearBodyData();
				}
			}
		}
	}

	/**
	 * ����״̬�»�����һ�ε�����
	 */
	private void putDataIntoAddMap(RowChangeBean rowChangeBean, MainGrandRelationShip maingrandrelationship,
			IGrandValidationService grandValidationService, MainGrandBlankFilter filter) {

		BillForm mainbillform = rowChangeBean.getMainForm();
		BillForm grandbillform = rowChangeBean.getGrandForm();
		int lastrow = rowChangeBean.getLastRow();

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		String className = MainGrandUtil.getClassNameByTabCode(mainbillform, bodyTabCode);
		// ��ȡ������ҳǩ
		String[] tabCodes = null;
		tabCodes = grandbillform.getBillCardPanel().getBillData().getBodyTableCodes();

		// �洢��һ�ε�ѡ���С�����ʾ��ǰѡ���е�����
		if (lastrow >= 0) {
			// �Ƶ�����pk�Ѿ�����
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
			// α��
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
	 * ����״̬�»�����һ�ε�����
	 */
	private void putDataIntoAddMapForCurrentRow(RowChangeBean rowChangeBean, MainGrandRelationShip maingrandrelationship) {

		BillForm mainbillform = rowChangeBean.getMainForm();
		BillForm grandbillform = rowChangeBean.getGrandForm();
		int currentRow = rowChangeBean.getCurrentRow();

		String bodyTabCode = mainbillform.getBillCardPanel().getCurrentBodyTableCode();
		// ��ȡ������ҳǩ
		String[] tabCodes = null;
		tabCodes = grandbillform.getBillCardPanel().getBillData().getBodyTableCodes();
		// �洢��һ�ε�ѡ���С�����ʾ��ǰѡ���е�����
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
	 * �޸�״̬�´洢��һ�ε�����
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
	 * ���ķǿչ���
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
						// �ѹ����Ƴ���������ݣ��ŵ�delGrandMap
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
	 * �޸�״̬�´洢��һ�ε�����
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
	 * ���pk(VOû�еĻ���ȥ���α��)
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
		//mod �����һ�����ֶ�����Ψһ��ʶһ�� Ares.tank 2019��7��2��22:39:18
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
	 * ����ɾ����VO
	 */
	private void processDelVOs(BillForm grandbillform, String grandTabCode, String cardUniqueKey) {

		CircularlyAccessibleValueObject[] lastDeleteGrandVos = MainGrandUtil.getBodyChangeVoByTabCode(grandbillform,
				grandTabCode);
		ArrayList<Object> lastDelGrandVosList = new ArrayList<Object>();
		if (lastDeleteGrandVos == null || lastDeleteGrandVos.length == 0) {
			return;
		}
		// �������ɾ���Ĵ���PK�����VO
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
		// ����ɾ�������VO��Ϣ
		if (lastDelGrandVosList.size() != 0) {
			this.getMainGrandModel().getDelGrandMap().put(cardUniqueKey, lastDelGrandVosList);
		}
	}

	/**
	 * ���õ�ǰ�������
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
	 * �������Ϣ���õ��ӱ�������ȥ
	 */
	public CircularlyAccessibleValueObject setGrandToChild(String tableCode, CircularlyAccessibleValueObject childVO,
			List<?> grandVOList) {

		NCObject ncObject = NCObject.newInstance(childVO);
		IBean bean = ncObject.getRelatedBean();
		// ��ȡ�ӱ��ж�Ӧ��ʵ��������б�
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
	 * �༭̬���������� <li>���༭��������������ݣ�Ȼ����ɾ��������ʱ���У�����ɾ��</li> <li>
	 * ���༭������û��������ݣ����ɾ�����棬���ɾ���������У���Ϊ��</li> <li>
	 * ���༭������û��������ݣ�ɾ��������Ҳû�У���ֱ�ӴӲ�ѯ������ȡ</li>
	 */
	private ArrayList<Object> getGrandListStatusIsEdit(String cardUniqueKey) {
		ArrayList<Object> grandVOList = null;
		// ��ѯ��Ӧҳǩ���ݲ����õ����ҳǩ
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
	 * �ж��Ƿ�Ҫ�������л��¼��Ĵ����������ȥ
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
	 * ��������״̬�������״̬
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
	 * ��������д���(����AddLine��ť�����������)
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
	 * ��α�м��뵽��������
	 */
	public void addPreColumBillItemToBody(BillForm mainBillForm, int row, String tabcode, String value) {
		BillModel billModel = mainBillForm.getBillCardPanel().getBillModel(tabcode);
		billModel.setValueAt(value, row, "precolumn");
	}

	/**
	 * �Կ�Ƭ������й���α��
	 */
	public void constructPreColumBillItem(BillForm mainBillForm) {
		// ���α�в���
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
