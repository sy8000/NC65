package nc.ui.qcco.task.refmodel;



import nc.ui.bd.ref.AbstractRefModel;


public class TaskAnalyseComponentRefModel extends AbstractRefModel {
	

	public TaskAnalyseComponentRefModel() {
		super();
		this.setTableName("(select code,name,pk_ref from "
				+ " ((SELECT DISTINCT test_after_code code, test_after_name name, PK_TEST_AFTER   pk_ref "
				+" FROM  NC_TEST_AFTER) "
				+" union all "
				+" (select NC_COMPONENT_CODE code,NC_COMPONENT_NAME name,PK_COMPONENT_TABLE pk_ref "
				+ " from nc_component_table nct)))");
		this.setMutilLangNameRef(false);
	}

	
	private String analysisName = null;
	
	
	public String getAnalysisName() {
		return analysisName;
	}

	public void setAnalysisName(String analysisName) {
		this.analysisName = analysisName;
	}

	public java.lang.String[] getFieldCode() {
		return new String[] { "code", "name" };
	}

	public java.lang.String[] getFieldName() {
		return new String[] { "参数项编码", "参数项名称" };
	}

	public java.lang.String[] getHiddenFieldCode() {
		return new String[] { "pk_ref" };
	}

	public java.lang.String getPkFieldCode() {
		return "pk_ref";
	}

	public java.lang.String getOrderPart() {
		return "code";
	}

	public int getDefaultFieldCount() {
		return 2;
	}

	public java.lang.String getRefTitle() {
		return "请选择参数项";
	}
	@Override
	public String getRefNameField() {
		return "name";
	}


	protected String getSql(String strPatch, String[] strFieldCode, String[] hiddenFields, String strTableName,
			String strWherePart, String strGroupField, String strOrderField) {
		if(getAnalysisName()!=null){
			//有Analysis 为手工输入,只读取 NC_COMPONENT_NAME的数据 tank 2019年10月14日20:15:26
			return "select code,name,pk_ref from (select PK_COMPONENT_TABLE pk_ref,NC_COMPONENT_CODE code,"
					+ " NC_COMPONENT_NAME name from nc_component_table nct where nct.analysis in ( "
					+" select n.name from nc_analysis_list n where n.name = '"+getAnalysisName()+"'))";
		}else{
			return "select code,name,pk_ref from "
					+ " ((SELECT DISTINCT test_after_code code, test_after_name name, PK_TEST_AFTER   pk_ref "
					+" FROM  NC_TEST_AFTER) "
					+" union all "
					+" (select NC_COMPONENT_CODE code,NC_COMPONENT_NAME name,PK_COMPONENT_TABLE pk_ref "
					+ " from nc_component_table nct)) NC_TEST_AFTER";
		}
	}

	
}
