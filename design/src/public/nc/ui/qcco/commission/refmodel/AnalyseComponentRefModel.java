package nc.ui.qcco.commission.refmodel;

import nc.ui.bd.ref.AbstractRefModel;

public class AnalyseComponentRefModel extends AbstractRefModel {
	public AnalyseComponentRefModel() {
		super();
		this.setTableName("NC_TEST_INIT");
		this.setMutilLangNameRef(false);
	}
	private StringBuilder sb = new StringBuilder();
	// 这是抛开!
	private String pk_ncEnstardCode = "";

	public String getPk_ncEnstardCode() {
		return pk_ncEnstardCode;
	}

	public void setPk_ncEnstardCode(String pk_ncEnstardCode) {
		this.pk_ncEnstardCode = pk_ncEnstardCode;
	}
	
	public java.lang.String[] getFieldCode() {
		return new String[] { "TEST_INIT_CODE", "TEST_INIT_NAME" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] { "参数项编码", "参数项名称" };
	}

	public java.lang.String[] getHiddenFieldCode() {
		return new String[] { "PK_TEST_INIT" };
	}

	public java.lang.String getPkFieldCode() {
		return "PK_TEST_INIT";
	}

	public java.lang.String getOrderPart() {
		return "TEST_INIT_CODE";
	}

	public int getDefaultFieldCount() {
		return 2;
	}

	public java.lang.String getRefTitle() {
		return "请选择参数项";
	}

	protected String getSql(String strPatch, String[] strFieldCode,
			String[] hiddenFields, String strTableName, String strWherePart,
			String strGroupField, String strOrderField) {
		sb.delete(0, sb.length());
			sb.append("select TEST_INIT_CODE, TEST_INIT_NAME, PK_TEST_INIT ,resulttype.NC_RESULT_NAMECN  from NC_TEST_INIT init  inner join NC_BASEN_TYPE type1 on (init.NC_ENSTARD = type1.NC_BBASEN_NAME ");

			if (getPk_ncEnstardCode() != null && !getPk_ncEnstardCode().equals("")) {

				sb.append("and type1.PK_BASEN_TYPE = '").append(getPk_ncEnstardCode()).append("' ");
			}
			sb.append(" )").append(" left join nc_result_type resulttype on init.PK_RESULT_TYPE = resulttype.PK_RESULT_TYPE ");
		
		return sb.toString();
	}
}
