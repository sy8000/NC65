package nc.ui.qcco.commission.refmodel;

import nc.ui.bd.ref.AbstractRefModel;

public class TestRequirementRefModel extends AbstractRefModel {
	public TestRequirementRefModel() {
		super();
		this.setTableName("NC_TESTREQUEST_TYPE");
		this.setMutilLangNameRef(false);
	}

	public java.lang.String[] getFieldCode() {
		return new String[] { "NC_TESTREQUEST_CODE", "NC_TESTREQUEST_NAME" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] { "�����������", "������������" };
	}

	public java.lang.String[] getHiddenFieldCode() {
		return new String[] { "PK_TESTREQUEST_TYPE" };
	}

	public java.lang.String getPkFieldCode() {
		return "PK_TESTREQUEST_TYPE";
	}

	public java.lang.String getOrderPart() {
		return "NC_TESTREQUEST_CODE";
	}

	public int getDefaultFieldCount() {
		return 2;
	}

	public java.lang.String getRefTitle() {
		return "��ѡ���������";
	}

	protected String getSql(String strPatch, String[] strFieldCode,
			String[] hiddenFields, String strTableName, String strWherePart,
			String strGroupField, String strOrderField) {
		return "select NC_TESTREQUEST_CODE, NC_TESTREQUEST_NAME, PK_TESTREQUEST_TYPE from NC_TESTREQUEST_TYPE WHERE ISENABLE=1";
	}
}
