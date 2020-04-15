package nc.ui.qcco.commission.refmodel;

import nc.ui.bd.ref.AbstractRefModel;

import org.apache.commons.lang.StringUtils;

public class ValueTypeRefModel extends AbstractRefModel {
	

	public ValueTypeRefModel() {
		super();
		this.setTableName("nc_result_type");
		this.setMutilLangNameRef(false);
	}

	public java.lang.String[] getFieldCode() {
		return new String[] { "nc_result_code", "nc_result_namecn" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] { "值类型编码", "值类型名称" };
	}

	public java.lang.String[] getHiddenFieldCode() {
		return new String[] { "pk_result_type" };
	}

	public java.lang.String getPkFieldCode() {
		return "pk_result_type";
	}

	public java.lang.String getOrderPart() {
		return "nc_result_code";
	}

	public int getDefaultFieldCount() {
		return 2;
	}

	public java.lang.String getRefTitle() {
		return "请选择值类型";
	}
	@Override
	public String getRefNameField() {
		return "nc_result_namecn";
	}


	protected String getSql(String strPatch, String[] strFieldCode, String[] hiddenFields, String strTableName,
			String strWherePart, String strGroupField, String strOrderField) {
		return "select distinct trim(nc_result_code)as nc_result_code,nc_result_namecn,pk_result_type from nc_result_type";
	}

	
}
