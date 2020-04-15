package nc.ui.qcco.commission.refmodel;

import nc.ui.bd.ref.AbstractRefModel;

import org.apache.commons.lang.StringUtils;

public class ProductStructRefModel extends AbstractRefModel {
	private String pk_basprod_type;
	private String pk_basen_type;
	private String pk_basprod_point;

	public ProductStructRefModel() {
		super();
		this.setTableName("NC_BASPROD_STRUCT");
		this.setMutilLangNameRef(false);
	}

	public java.lang.String[] getFieldCode() {
		return new String[] { "NC_BASPRODSTRUCT_CODE", "NC_BASPRODSTRUCT_NAME" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] { "结构类型编码", "结构类型名称" };
	}

	public java.lang.String[] getHiddenFieldCode() {
		return new String[] { "PK_BASPROD_STRUCT" };
	}

	public java.lang.String getPkFieldCode() {
		return "PK_BASPROD_STRUCT";
	}

	public java.lang.String getOrderPart() {
		return "NC_BASPRODSTRUCT_CODE";
	}

	public int getDefaultFieldCount() {
		return 2;
	}

	public java.lang.String getRefTitle() {
		return "请选择结构类型";
	}

	protected String getSql(String strPatch, String[] strFieldCode, String[] hiddenFields, String strTableName,
			String strWherePart, String strGroupField, String strOrderField) {
		return "SELECT TRIM(BP.NC_BASPRODSTRUCT_CODE) NC_BASPRODSTRUCT_CODE, TRIM(BP.NC_BASPRODSTRUCT_NAME) NC_BASPRODSTRUCT_NAME, BP.PK_BASPROD_STRUCT "
				+ " FROM NC_SAMPLE_INFO SI INNER JOIN NC_BASPROD_STRUCT BP ON BP.PK_BASPROD_STRUCT = SI.PK_BASPROD_STRUCT "
				+ " WHERE 1=1 "
				+ (StringUtils.isEmpty(getPk_basprod_type()) ? "" : ("AND SI.PK_BASPROD_TYPE = '"
						+ getPk_basprod_type() + "'"))
				+ (StringUtils.isEmpty(getPk_basen_type()) ? ""
						: (" AND SI.PK_BASEN_TYPE = '" + getPk_basen_type() + "'"))
				+ (StringUtils.isEmpty(getPk_basprod_point()) ? "" : (" AND SI.PK_BASPROD_POINT = '"
						+ getPk_basprod_point() + "' "))
				+ " GROUP BY BP.NC_BASPRODSTRUCT_CODE, BP.NC_BASPRODSTRUCT_NAME, BP.PK_BASPROD_STRUCT "
				+ " ORDER BY CAST(BP.NC_BASPRODSTRUCT_CODE AS NUMBER)";
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

	public String getPk_basprod_point() {
		return pk_basprod_point;
	}

	public void setPk_basprod_point(String pk_basprod_point) {
		this.pk_basprod_point = pk_basprod_point;
	}
}
