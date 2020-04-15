package nc.ui.qcco.commission.refmodel;

import nc.ui.bd.ref.AbstractRefModel;

import org.apache.commons.lang.StringUtils;

public class ProductPointRefModel extends AbstractRefModel {
	private String pk_basprod_type;
	private String pk_basen_type;

	public ProductPointRefModel() {
		super();
		this.setTableName("NC_BASPROD_POINT");
		this.setMutilLangNameRef(false);
	}

	public java.lang.String[] getFieldCode() {
		return new String[] { "NC_BASPRODPOINT_CODE", "NC_BASPRODPOINT_NAME" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] { "规格号编码", "规格号名称" };
	}

	public java.lang.String[] getHiddenFieldCode() {
		return new String[] { "PK_BASPROD_POINT" };
	}

	public java.lang.String getPkFieldCode() {
		return "PK_BASPROD_POINT";
	}

	public java.lang.String getOrderPart() {
		return "NC_BASPRODPOINT_CODE";
	}

	public int getDefaultFieldCount() {
		return 2;
	}

	public java.lang.String getRefTitle() {
		return "请选择规格号";
	}

	protected String getSql(String strPatch, String[] strFieldCode, String[] hiddenFields, String strTableName,
			String strWherePart, String strGroupField, String strOrderField) {
		return "SELECT TRIM(BP.NC_BASPRODPOINT_CODE) NC_BASPRODPOINT_CODE, TRIM(BP.NC_BASPRODPOINT_NAME) NC_BASPRODPOINT_NAME, BP.PK_BASPROD_POINT"
				+ " FROM NC_SAMPLE_INFO SI INNER JOIN NC_BASPROD_POINT BP ON BP.PK_BASPROD_POINT = SI.PK_BASPROD_POINT"
				+ " WHERE 1=1  "
				+ (StringUtils.isEmpty(getPk_basprod_type()) ? "" : ("AND SI.PK_BASPROD_TYPE = '"
						+ getPk_basprod_type() + "'"))
				+ (StringUtils.isEmpty(getPk_basen_type()) ? ""
						: (" AND SI.PK_BASEN_TYPE = '" + getPk_basen_type() + "'"))
				+ " GROUP BY BP.NC_BASPRODPOINT_CODE, BP.NC_BASPRODPOINT_NAME, BP.PK_BASPROD_POINT "
				+ " ORDER BY CAST(BP.NC_BASPRODPOINT_CODE AS NUMBER)";
	}

	public String getPk_basprod_type() {
		return pk_basprod_type;
	}

	public void setPk_basprod_type(String pk_basprod_type) {
		this.pk_basprod_type = pk_basprod_type;
	}

	public String getPk_basen_type() {
		return pk_basen_type;
	}

	public void setPk_basen_type(String pk_basen_type) {
		this.pk_basen_type = pk_basen_type;
	}
}
