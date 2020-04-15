package nc.ui.qcco.commission.refmodel;

import nc.ui.bd.ref.AbstractRefModel;

import org.apache.commons.lang.StringUtils;

public class ProductSerialRefModel extends AbstractRefModel {
	private String firstClassCode;
	private String secondClassCode;
	private String thirdClassCode;

	public ProductSerialRefModel() {
		super();
		this.setTableName("NC_BASPROD_TYPE");
		this.setMutilLangNameRef(false);
	}

	public java.lang.String[] getFieldCode() {
		return new String[] { "NC_BASPRODTYPE_CODE", "NC_BASPRODTYPE_NAME" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] { "产品系列编码", "产品系列名称" };
	}

	public java.lang.String[] getHiddenFieldCode() {
		return new String[] { "PK_BASPROD_TYPE" };
	}

	public java.lang.String getPkFieldCode() {
		return "PK_BASPROD_TYPE";
	}

	public java.lang.String getOrderPart() {
		return "NC_BASPRODTYPE_CODE";
	}

	public int getDefaultFieldCount() {
		return 2;
	}

	public java.lang.String getRefTitle() {
		return "请选择产品系列";
	}

	protected String getSql(String strPatch, String[] strFieldCode, String[] hiddenFields, String strTableName,
			String strWherePart, String strGroupField, String strOrderField) {
		String strProdType = "1=1 "
				+ (StringUtils.isEmpty(this.getFirstClassCode()) ? "" : " and PROD_TYPE='" + this.getFirstClassCode()
						+ "' ")
				+ (StringUtils.isEmpty(this.getSecondClassCode()) ? "" : " and C_PROD_TYPE_C1='"
						+ this.getSecondClassCode() + "' ")
				+ (StringUtils.isEmpty(this.getThirdClassCode()) ? "" : " and P_NAME='" + this.getThirdClassCode()
						+ "' ");
		return "select TRIM(PT.NC_BASPRODTYPE_CODE) NC_BASPRODTYPE_CODE, TRIM(PT.NC_BASPRODTYPE_NAME) NC_BASPRODTYPE_NAME, PT.PK_BASPROD_TYPE "
				+ " from NC_SAMPLE_INFO SI "
				+ " INNER JOIN NC_BASPROD_TYPE PT ON PT.PK_BASPROD_TYPE = SI.PK_BASPROD_TYPE "
				+ " WHERE SI.PK_PROD_TYPE IN (SELECT PK_PROD_TYPE FROM NC_PROD_TYPE WHERE "
				+ strProdType
				+ ") "
				+ " GROUP BY PT.NC_BASPRODTYPE_CODE, PT.NC_BASPRODTYPE_NAME, PT.PK_BASPROD_TYPE "
				+ " ORDER BY CAST(NC_BASPRODTYPE_CODE AS NUMBER)";
	}

	public String getFirstClassCode() {
		return firstClassCode;
	}

	public void setFirstClassCode(String firstClassCode) {
		this.firstClassCode = firstClassCode;
	}

	public String getSecondClassCode() {
		return secondClassCode;
	}

	public void setSecondClassCode(String secondClassCode) {
		this.secondClassCode = secondClassCode;
	}

	public String getThirdClassCode() {
		return thirdClassCode;
	}

	public void setThirdClassCode(String thirdClassCode) {
		this.thirdClassCode = thirdClassCode;
	}
}
