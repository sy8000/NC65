package nc.ui.qcco.commission.refmodel;

import nc.ui.bd.ref.AbstractRefModel;

public class SampleGroupRefModel extends AbstractRefModel {
	public SampleGroupRefModel() {
		super();
		this.setTableName("NC_SAMPLE_GROUP");
		this.setMutilLangNameRef(false);
	}
	private String groupWhere = null;
	public void setGroupWhere(String groupWhere){
		this.groupWhere = groupWhere;
	}
	public java.lang.String[] getFieldCode() {
		return new String[] { "NC_SAMPLE_CODE", "NC_SAMPLE_NAME" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] { "样品组别编码", "样品组别名称" };
	}

	public java.lang.String[] getHiddenFieldCode() {
		return new String[] { "PK_SAMPLE_GROUP" };
	}

	public java.lang.String getPkFieldCode() {
		return "PK_SAMPLE_GROUP";
	}

	public java.lang.String getOrderPart() {
		return "NC_SAMPLE_CODE";
	}

	public int getDefaultFieldCount() {
		return 2;
	}

	public java.lang.String getRefTitle() {
		return "请选择样品组别";
	}

	@Override
	public String getRefNameField() {
		return "NC_SAMPLE_NAME";
	}

	protected String getSql(String strPatch, String[] strFieldCode, String[] hiddenFields, String strTableName,
			String strWherePart, String strGroupField, String strOrderField) {
		return "select NC_SAMPLE_CODE, NC_SAMPLE_NAME, PK_SAMPLE_GROUP from NC_SAMPLE_GROUP WHERE ISENABLE=1 "
			+ (groupWhere==null?"":" and "+ groupWhere)
			+" ORDER BY NC_SAMPLE_CODE";
	}
}
