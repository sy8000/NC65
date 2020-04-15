package nc.ui.qcco.task.action;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;

import javax.swing.Action;
import javax.swing.KeyStroke;

import nc.desktop.ui.WorkbenchEnvironment;
import nc.pub.filesystem.newui.FileManageUIFactory;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pubapp.uif2app.model.BillManageModel;
import nc.ui.uif2.NCAction;
import nc.ui.uif2.UIState;
import nc.vo.am.common.util.StringUtils;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pubapp.pattern.model.entity.bill.AbstractBill;
import nc.vo.uap.rbac.FuncSubInfo;
import nc.vo.uap.rbac.profile.FunctionPermProfileManager;

public class FileUploadAction extends NCAction {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MODULE = "am";
	private WindowListener attchAction;
	private BillManageModel model = null;

	public FileUploadAction() {
		super();
		String name = "附件上传";
		setBtnName(name);
		setCode("FileUpload");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('I', Event.CTRL_MASK));
		putValue(Action.SHORT_DESCRIPTION, name + "(Ctrl+I)");
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		if (getModel().getSelectedData() == null) {
			throw new BusinessException("请选择任务单！");
		}

		String userCode = WorkbenchEnvironment.getInstance().getLoginUser().getUser_code();

		FuncSubInfo funcsubinfo = FunctionPermProfileManager.getInstance().getProfile(userCode)
				.getFuncSubInfo(getModel().getContext().getNodeCode());

		UIDialog fileManageDLG = getFileManageDLG(funcsubinfo);
		fileManageDLG.addWindowListener(this.attchAction);
		fileManageDLG.showModal();
	}

	public UIDialog getFileManageDLG(FuncSubInfo funcsubinfo) {
		return FileManageUIFactory.getFileManageDLG(getModel().getContext().getEntranceUI(), getDialogTitle(),
				getRootPath(), funcsubinfo, false);
	}

	public String getDialogTitle() {
		// return NCLangRes4VoTransl.getNCLangRes().getStrByID("ampub_0",
		// "04501000-0303");
		return "附件上传";
	}

	protected String getRootPath() {
		Object selectedObj = getModel().getSelectedData();

		String mainTable = getMainTable(selectedObj);

		String[] partsOfRoot = getPartsOfRoot(mainTable);

		return partsOfRoot[0] + "/" + partsOfRoot[1] + "/" + getPrimaryKey(selectedObj);
	}

	protected String getMainTable(Object selectedObj) {
		String mainTable = null;
		SuperVO mainVO = getMainBaseVO(selectedObj);
		if (mainVO != null)
			mainTable = mainVO.getTableName();
		return mainTable;
	}

	protected String getPrimaryKey(Object selectedObj) {
		String primaryKey = null;
		SuperVO mainVO = getMainBaseVO(selectedObj);
		if (mainVO != null)
			primaryKey = mainVO.getPrimaryKey();
		return primaryKey;
	}

	protected SuperVO getMainBaseVO(Object selectedObj) {
		SuperVO mainVO = null;
		if (selectedObj instanceof SuperVO)
			mainVO = (SuperVO) selectedObj;
		else if (selectedObj instanceof AggregatedValueObject) {
			mainVO = (SuperVO) ((AggregatedValueObject) selectedObj).getParentVO();
		}
		return mainVO;
	}

	protected String[] getPartsOfRoot(String mainTable) {
		String[] rootParts = new String[2];

		String modulePart = "am";

		String objectPart = null;
		if (StringUtils.isNotBlank(mainTable)) {
			String[] splits = mainTable.split("_", 2);
			if (splits.length == 1) {
				objectPart = splits[0];
			} else {
				modulePart = splits[0];
				objectPart = splits[1];
			}
		}
		rootParts[0] = modulePart;
		rootParts[1] = objectPart;
		return rootParts;
	}

	public BillManageModel getModel() {
		return this.model;
	}

	public void setModel(BillManageModel model) {
		this.model = model;
		this.model.addAppEventListener(this);
	}

	public WindowListener getAttchAction() {
		return this.attchAction;
	}

	public void setAttchAction(WindowListener attchAction) {
		this.attchAction = attchAction;
	}

	protected boolean isActionEnable() {
		AbstractBill aggVO = (AbstractBill) this.getModel().getSelectedData();
		if (aggVO == null) {
			return false;
		}
		SuperVO hvo = (SuperVO) aggVO.getParentVO();
		if (hvo == null) {
			return false;
		}
		return this.getModel().getUiState() == UIState.NOT_EDIT;
	}
}
