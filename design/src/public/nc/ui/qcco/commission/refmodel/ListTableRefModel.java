package nc.ui.qcco.commission.refmodel;

import nc.ui.bd.ref.AbstractRefModel;

import org.apache.commons.lang.StringUtils;

public class ListTableRefModel extends AbstractRefModel {
	

	public ListTableRefModel() {
		super();
		this.setTableName("NC_LIST_TABLE");
		this.setMutilLangNameRef(false);
	}

	public java.lang.String[] getFieldCode() {
		return new String[] { "nc_list_code", "nc_list_name" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] { "参照编码", "参照名称" };
	}

	public java.lang.String[] getHiddenFieldCode() {
		return new String[] { "pk_list_table" };
	}

	public java.lang.String getPkFieldCode() {
		return "pk_list_table";
	}

	public java.lang.String getOrderPart() {
		return "nc_list_code";
	}

	public int getDefaultFieldCount() {
		return 2;
	}

	public java.lang.String getRefTitle() {
		return "请选择参照";
	}
	@Override
	public String getRefNameField() {
		return "nc_list_name";
	}


	protected String getSql(String strPatch, String[] strFieldCode, String[] hiddenFields, String strTableName,
			String strWherePart, String strGroupField, String strOrderField) {
		return "select trim(nc_list_code) as nc_list_code,nc_list_name,pk_list_table  from NC_LIST_TABLE";
	}

	
}
