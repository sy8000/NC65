package nc.ui.qcco.commission.refmodel;

import java.util.Vector;

import nc.ui.bd.ref.AbstractRefModel;

import org.apache.commons.lang.StringUtils;

public class ProductContactRefModel extends AbstractRefModel {
	private String pk_basprod_type;
	private String pk_basen_type;
	private String pk_basprod_point;
	private String pk_basprod_struct;

	public ProductContactRefModel() {
		super();
		this.setTableName("NC_BASPROD_CONTACT");
		this.setMutilLangNameRef(false);
	}

	public java.lang.String[] getFieldCode() {
		return new String[] { "NC_BASPRODCONTACT_CODE", "NC_BASPRODCONTACT_NAME" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] { "触点类型编码", "触点类型名称" };
	}

	public java.lang.String[] getHiddenFieldCode() {
		return new String[] { "PK_BASPROD_CONTACT" };
	}

	public java.lang.String getPkFieldCode() {
		return "PK_BASPROD_CONTACT";
	}

	public java.lang.String getOrderPart() {
		return "NC_BASPRODCONTACT_CODE";
	}

	public int getDefaultFieldCount() {
		return 2;
	}

	public java.lang.String getRefTitle() {
		return "请选择触点类型";
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Vector getData() {
		Vector data = super.getData();
		if (data != null && data.size() > 1) {
			return null;
		}
		Vector result = new Vector();
		if (data != null && data.size() > 0 && data.get(0) != null && ((Vector) data.get(0)).get(1) != null) {
			// 把data分解
			Vector data0 = (Vector) data.get(0);

			String dataCode = (String) data0.get(0);
			String codes = (String) data0.get(1);
			String pk = (String) data0.get(2);
			String[] codeArray = codes.replaceAll(" ", "").split(",");
			for (String code : codeArray) {
				Vector line = new Vector(3);
				line.add(code);
				line.add(code);
				line.add(code);

				result.add(line);
			}
		}
		// 处理下拉数据
		return result;
	};

	@Override
	public Vector matchData(String field, String value) {
		Vector vector = super.matchData(field, value);
		if (vector == null) {
			vector = new Vector();
			Vector line = new Vector(1, 3);
			line.add(value);
			line.add(value);
			line.add(value);
			vector.add(line);
		}
		return vector;
	}

	protected String getSql(String strPatch, String[] strFieldCode, String[] hiddenFields, String strTableName,
			String strWherePart, String strGroupField, String strOrderField) {
		String sql = "SELECT TRIM(BP.NC_BASPRODCONTACT_CODE) NC_BASPRODCONTACT_CODE, TRIM(BP.NC_BASPRODCONTACT_NAME) NC_BASPRODCONTACT_NAME, BP.PK_BASPROD_CONTACT "
				+ " FROM NC_SAMPLE_INFO SI INNER JOIN NC_BASPROD_CONTACT BP ON BP.PK_BASPROD_CONTACT = SI.PK_BASPROD_CONTACT "
				+ " WHERE 1=1 "
				+ (StringUtils.isEmpty(getPk_basprod_type()) ? "" : (" AND SI.PK_BASPROD_TYPE = '"
						+ getPk_basprod_type() + "'"))
				+ (StringUtils.isEmpty(getPk_basen_type()) ? ""
						: (" AND SI.PK_BASEN_TYPE = '" + getPk_basen_type() + "'"))
				+ (StringUtils.isEmpty(getPk_basprod_point()) ? "" : (" AND SI.PK_BASPROD_POINT = '"
						+ getPk_basprod_point() + "'"))
				+ (StringUtils.isEmpty(getPk_basprod_struct()) ? "" : (" AND SI.PK_BASPROD_STRUCT = '"
						+ getPk_basprod_struct() + "' "))
				+ " GROUP BY BP.NC_BASPRODCONTACT_CODE, BP.NC_BASPRODCONTACT_NAME, BP.PK_BASPROD_CONTACT "
				+ " ORDER BY CAST(BP.NC_BASPRODCONTACT_CODE AS NUMBER)";

		return sql;
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

	public String getPk_basprod_struct() {
		return pk_basprod_struct;
	}

	public void setPk_basprod_struct(String pk_basprod_struct) {
		this.pk_basprod_struct = pk_basprod_struct;
	}
}
