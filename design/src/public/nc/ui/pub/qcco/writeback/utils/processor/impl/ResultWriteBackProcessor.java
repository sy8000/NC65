package nc.ui.pub.qcco.writeback.utils.processor.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Collections;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.hr.utils.InSQLCreator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.ui.pub.qcco.writeback.utils.WriteBackProcessData;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.CProjTask;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.Result;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.Sample;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.Test;
import nc.ui.pub.qcco.writeback.utils.common.CommonUtils;
import nc.ui.pub.qcco.writeback.utils.mapping.FirstWriteBackStaticMaping;
import nc.ui.pub.qcco.writeback.utils.mapping.SecWriteBackStaticMaping;
import nc.ui.pub.qcco.writeback.utils.processor.IFirstWriteBackProcessor;
import nc.ui.pub.qcco.writeback.utils.processor.ISecWriteBackProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ISuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.qcco.task.AggTaskHVO;
import nc.vo.qcco.task.TaskBVO;
import nc.vo.qcco.task.TaskSVO;

/**
 * result第一次和第二次回写
 * @author 91967
 *
 */
public class ResultWriteBackProcessor implements IFirstWriteBackProcessor, ISecWriteBackProcessor {

	private CommonUtils utils;
	private BaseDAO baseDao = new BaseDAO();
	@Override
	public void setUtils(CommonUtils utils) {
		this.utils = utils;
	}
	
	@Override
	public void processSec(WriteBackProcessData data) throws BusinessException{
		processSecond(data);
	}

	@Override
	public void processFirst(WriteBackProcessData data) throws BusinessException{
		process(data);
	}

	
	/**
	 * test 二次回写
	 * 
	 * @param data
	 * @throws DAOException
	 */
	private void processSecond(WriteBackProcessData processData) throws DAOException {
		// LIMS Data
		//List<Sample> allSampleList = processData.getAllSecSampleList();
		List<Test> secTestList = processData.getSecTestList();
		

		List<Result> secResultList = new ArrayList<>();

		if (secTestList != null && secTestList.size() > 0) {
			for (int i = 0; i < secTestList.size(); i++) {
				//通过test 获取task,再获取an ,在获取实验结果分项
				List<Map<String,Object>> compoentList = 
						utils.getResultCompoentList(String.valueOf(secTestList.get(i).getAttributeValue("c_task_seq_num")));
				if(compoentList!=null && compoentList.size() > 0){
					for(Map<String,Object> component : compoentList){
						// 开始生成result
						Result result = new Result();
						Map<String, Object> analysis = utils.getAnalysis(String.valueOf(component.get("analysis")));
						result.setAttributeValue("result_number", processData.getMaxResult()+1);
						processData.setMaxResult(processData.getMaxResult()+1);
						
						Integer sampleNumber = Integer.parseInt(String.valueOf(secTestList.get(i).getAttributeValue("sample_number")));
						
						//RESULT.SAMPLE_NUMBER	sample表第二次写入的主键，测试结果有多少行，此处一只样品就有多少行
						result.setAttributeValue("sample_number", sampleNumber);
						
						//RESULT.NAME 测试结果对应的显示值
						result.setAttributeValue("name", component.get("name"));
						
						//RESULT.ENTRY	null				
						result.setAttributeValue("entry", null);
						
						//RESULT.TEST_NUMBER	test表二次写入对应sample_number的test_number				
						result.setAttributeValue("test_number", secTestList.get(i).getAttributeValue("test_number"));
						
						//RESULT.ORDER_NUMBER 测试结果的排序	  			
						result.setAttributeValue("order_number", component.get("order_number"));
						
						//RESULT.ENTERED_ON	default:null			输入时间是什么时间?怎么获取??	第一次写入为写入时的系统时间
						result.setAttributeValue("entered_on", null);
						
						//RESULT.ANALYSIS	 测试结果依据的分析名称（带A的）
						result.setAttributeValue("analysis", component.get("analysis"));
						
						//RESULT.ATTRIBUTE_1	default:null				
						result.setAttributeValue("attribute_1", null);
						
						//report_name
						result.setAttributeValue("reported_name", analysis.get("reported_name"));
						//单位的显示名
						result.setAttributeValue("units", component.get("units"));
						//按条件中对分项的定义取
						result.setAttributeValue("round", component.get("round"));
						//按结果中对分项的定义取
						result.setAttributeValue("places", component.get("places"));
						//
						result.setAttributeValue("minimum", component.get("minimum"));
						//
						result.setAttributeValue("maximum", component.get("maximum"));
						//
						result.setAttributeValue("allow_out", component.get("allow_out"));
						//
						result.setAttributeValue("list_key", component.get("list_key"));
						//
						result.setAttributeValue("reportable", component.get("reportable"));
						//
						result.setAttributeValue("optional", component.get("optional"));
						//
						result.setAttributeValue("has_attributes", component.get("has_attributes"));
						//
						result.setAttributeValue("t_short_name", component.get("t_short_name"));
						//根据数据类型表得到对应的数据类型标志
						result.setAttributeValue("result_type", component.get("result_type"));
						//status
						result.setAttributeValue("status", "N");
						//OLD_STATUS
						result.setAttributeValue("old_status", "N");
						//是否修改了自动带出结果
						result.setAttributeValue("modified_result", "F");
						//RESULT.FORMAT_CALCULATION	数据类型(result_type)是计算类型和数据类型的(K或者L)，FORMAT_CALCULATION的数据为C_ACTUAL_TEST_TIME，
						String result_type = String.valueOf(component.get("result_type"));
						boolean isKOrL = "K".equalsIgnoreCase(result_type) || "L".equalsIgnoreCase(result_type);
						result.setAttributeValue("format_calculation", isKOrL?"C_ACTUAL_TEST_TIME":null);
						
						
						//修改日期
						result.setAttributeValue("changed_on", "to_timestamp('"+ new UFDateTime().toStdString() +"','yyyy-mm-dd hh24:mi:ss.ff')");
						
						//默认名称 select LIST_KEY ,list_entry.name from COMPONENT  left join list_entry on list_key = list where LIST_KEY is not null
						result.setAttributeValue("c_list_key", utils.getCListKeyByListKey(String.valueOf(component.get("list_key"))));
						
						//是否认证 根据analysis.c_certification是否为空进行判断，为空的话为F，不为空的话为T
						
						boolean isemp = analysis.get("c_certification")==null|| analysis.get("c_certification").toString().length() <= 0;
						result.setAttributeValue("t_accredited", isemp?"F":"T");
						
						//一些固定值
						result.setAttributeValue("replicate_count", 0);
						result.setAttributeValue("in_spec", "T");
						result.setAttributeValue("primary_in_spec", "T");
						result.setAttributeValue("uses_instrument", "F");
						result.setAttributeValue("uses_codes", "F");
						result.setAttributeValue("in_cal", "T");
						result.setAttributeValue("auto_calc", "T");
						result.setAttributeValue("allow_cancel", "F");
						result.setAttributeValue("link_size", 0);
						result.setAttributeValue("code_entered", "F");
						result.setAttributeValue("std_reag_sample", 0);
						result.setAttributeValue("factor_value", 0);
						result.setAttributeValue("in_control", "T");
						result.setAttributeValue("displayed", "T");
						result.setAttributeValue("spec_override", "F");
						result.setAttributeValue("places_2", 0);
						result.setAttributeValue("reported_result", "F");
						result.setAttributeValue("reported_rslt_rev", 0);
						result.setAttributeValue("reported_rslt_oos", "F");
						result.setAttributeValue("trans_num", 0);
						
						secResultList.add(result);
					
						
					}
					}
				}
			
			List<Result> secRsList = processData.getSecResultList();
			if(secRsList==null){
				secRsList = new ArrayList<>();
			}
			if(secResultList!=null){
				secRsList.addAll(secResultList);
			}
			processData.setSecResultList(secRsList);
		}
	}

	

	/**
     * 获取Insert语句片断
     *
     * @param processData processData
     * @throws BusinessException
     */
    private void process(WriteBackProcessData processData)
            throws BusinessException {
		// NC数据
		List<ISuperVO> srcDataList = new ArrayList<>();
		// 获取所有的实验条件参数VO
		ISuperVO[] superVOs = getAllTaskSVO(processData);
		Collections.addAll(srcDataList, superVOs);
		if (null == srcDataList || srcDataList.size() <= 0) {
			return ;
		}

		//相关数据
		List<Sample> firstSampleList = processData.getFirstSampleList();
		List<Test> firstTestList = processData.getFirstTestList();
		List<CProjTask> taskList = processData.getTaskList();
		//分析表
		Map<String,Map<String,Object>> analysisMapMap = getAnalysis(taskList);
		//分析类型表 name->code
		Map<String,String> analysisTypeCodeMap = getanalysisTypeCode();
		
		//要回写的LIMS 数据--任务单孙表试验条件
		List<Result> allResultList = initWriteBackList(srcDataList.size());
		
		// 预申请PK
		List<Integer> pkList = utils.getPrePk(TaskSVO.class, srcDataList.size());
		processData.setMaxResult(pkList.get(srcDataList.size()-1));

		// 进行列循环
		for (Entry<String, String> map : FirstWriteBackStaticMaping.GRAND_CONDITION_MAPPING.entrySet()) {
			String fieldName = map.getKey();
			String[] fields = null;
			if (map.getValue().contains(";")) {
				fields = utils.getWriteBackFields(map.getValue().split(";"));
			} else {
				fields = utils.getWriteBackFields(new String[] { map.getValue() });
			}
			if (srcDataList != null && srcDataList.size() > 0) {
				for (int i = 0; i < srcDataList.size(); i++) {
					Object unDofieldValue = srcDataList.get(i).getAttributeValue(fieldName);
					Object realValue = utils.getRealValue(unDofieldValue, fieldName, TaskSVO.class);
					for (String field : fields) {
						allResultList.get(i).setAttributeValue(field, realValue);
					}
				}
			}
		}

		//数值型类型参照主键:
		String pk_result_type_num = 
				(String)baseDao.executeQuery("select pk_result_type from nc_result_type where nc_result_namecn like '%数值%' and rownum = 1", 
						new ColumnProcessor());
		//制单人
		String psncode = (String)baseDao.executeQuery("select USER_CODE from sm_user where CUSERID = '"
				+processData.getAggCommissionHVO().getParentVO().getCreator()+"'", 
				new ColumnProcessor());
		//单位缓存
		@SuppressWarnings("unchecked")
		Map<String, String> unitMap = (Map<String, String>) NCLocator.getInstance().lookup(IUAPQueryBS.class)
				.executeQuery("select NC_UNITS_NAME,UNIT_CODE from nc_units_type", new ResultSetProcessor() {
					private static final long serialVersionUID = 6620032648507337165L;
					Map<String, String> rsMap = new HashMap<>();

					@Override
					public Object handleResultSet(ResultSet rs) throws SQLException {
						while (rs.next()) {
							String key = (rs.getString(1) == null ? null : rs.getString(1).replaceAll(" ", ""));
							String value = (rs.getString(2) == null ? null : rs.getString(2).replaceAll(" ", ""));
							rsMap.put(key, value);
						}
						return rsMap;
					}
				});
		for (int i = 0; i < srcDataList.size(); i++) {
			// 写入主键和父主键(只支持单主键,要多主键,需要参考上面的字段添加逻辑)
			// 获取上层的主键:
			String fatherPk = (String) (((ISuperVO) srcDataList.get(i)).getAttributeValue("pk_task_b"));
			//上次obj的index
			int fatherIndex = utils.getNCObjIndexByPK(fatherPk, TaskBVO.class);
			// 主键
			allResultList.get(i).setAttributeValue("result_number", pkList.get(i));
			//外键们 第一次回写只有一条sample 2019年9月12日10:34:32
			allResultList.get(i).setAttributeValue("sample_number", firstSampleList.get(0).getAttributeValue("sample_number"));
			allResultList.get(i).setAttributeValue("test_number", firstTestList.get(fatherIndex).getAttributeValue("test_number"));
			
			
			
			//flag标记,会在sort完之后清除
			allResultList.get(i).setAttributeValue("task_seq_num", taskList.get(fatherIndex).getAttributeValue("seq_num"));
			//对应的analysis-测试条件
			String resultAnalysis = String.valueOf(taskList.get(fatherIndex).getAttributeValue("analysis"));
			String analysis = null;
			if(resultAnalysis.length() > 2){
				if(resultAnalysis.substring(resultAnalysis.length()-2).equals("_A")){
					analysis = resultAnalysis.substring(0, resultAnalysis.length()-2);
				}else{
					analysis = resultAnalysis.substring(0, resultAnalysis.length()-1);
				}
			}
			
			Map<String,Object> analysisMap = analysisMapMap.get(analysis)==null?new HashMap<String,Object>():analysisMapMap.get(analysis);
			
			allResultList.get(i).setAttributeValue("analysis", analysisMap.get("name"));
			
			
			
			//根据分析方法和name查询component
			Map<String,Object> component = 
					utils.getCompoentByAnalysisAndName(analysis,String.valueOf(allResultList.get(i).getAttributeValue("name")));

			allResultList.get(i).setAttributeValue("list_key", component.get("list_key"));
			//默认名称 select LIST_KEY ,list_entry.name from COMPONENT  left join list_entry on list_key = list where LIST_KEY is not null
			allResultList.get(i).setAttributeValue("c_list_key", utils.getCListKeyByListKey(String.valueOf(component.get("list_key"))));
			
			//是否认证 根据analysis.c_certification是否为空进行判断，为空的话为F，不为空的话为T
			boolean isemp = analysisMap.get("c_certification")==null|| analysisMap.get("c_certification").toString().length() <= 0;
			allResultList.get(i).setAttributeValue("t_accredited", isemp?"F":"T");
			
			//order number
			allResultList.get(i).setAttributeValue("order_number", component.get("order_number"));
			allResultList.get(i).setAttributeValue("reported_name", analysisMap.get("reported_name"));
			allResultList.get(i).setAttributeValue("replicate_count", 0);
			//根据数据类型表得到对应的数据类型标志
			allResultList.get(i).setAttributeValue("result_type", component.get("result_type"));
			//status
			allResultList.get(i).setAttributeValue("status", "E");
			//OLD_STATUS
			allResultList.get(i).setAttributeValue("old_status", "N");
			//是否修改了自动带出结果
			allResultList.get(i).setAttributeValue("modified_result", "F");
			allResultList.get(i).setAttributeValue("round", component.get("round"));
			//默认改成1 2019年9月16日17:15:42
			allResultList.get(i).setAttributeValue("places", 1);
			allResultList.get(i).setAttributeValue("reportable", component.get("reportable"));
			
			String time = "to_timestamp('"+ new UFDateTime().toStdString() +"','yyyy-mm-dd hh24:mi:ss.ff')";
			allResultList.get(i).setAttributeValue("changed_on", time);
			allResultList.get(i).setAttributeValue("entered_on", time);
			allResultList.get(i).setAttributeValue("first_entry_on", time);
			
			//制单人
			allResultList.get(i).setAttributeValue("entered_by", psncode);
			
			
			
			allResultList.get(i).setAttributeValue("has_attributes", component.get("has_attributes"));
			String result_type = String.valueOf(component.get("result_type"));
			allResultList.get(i).setAttributeValue("result_type", result_type==null?null:result_type.substring(0, 1));
			allResultList.get(i).setAttributeValue("entry_type", result_type==null?null:result_type.substring(0, 1));
			
			//RESULT.FORMAT_CALCULATION	数据类型(result_type)是计算类型和数据类型的(K或者L)，FORMAT_CALCULATION的数据为C_ACTUAL_TEST_TIME，
			boolean isKOrL = "K".equalsIgnoreCase(result_type) || "L".equalsIgnoreCase(result_type);
			allResultList.get(i).setAttributeValue("format_calculation", isKOrL?"C_ACTUAL_TEST_TIME":null);
			allResultList.get(i).setAttributeValue("places_2", 0);
			allResultList.get(i).setAttributeValue("reported_result", "F");
			allResultList.get(i).setAttributeValue("reported_rslt_rev", 0);
			allResultList.get(i).setAttributeValue("reported_rslt_oos", "F");
			allResultList.get(i).setAttributeValue("t_short_name", component.get("t_short_name"));
			
			//如果值类型为参照,则为参照,其他情况为文本
			String entryStr = null;
			if(srcDataList.get(i).getAttributeValue("valueways")!=null 
					&& 2 == Integer.parseInt(String.valueOf(srcDataList.get(i).getAttributeValue("valueways")))){
				entryStr = String.valueOf(srcDataList.get(i).getAttributeValue("refvalue"));
			}else{
				entryStr = String.valueOf(srcDataList.get(i).getAttributeValue("textvalue"));
			}
			allResultList.get(i).setAttributeValue("entry", entryStr);
			//如果是数值型,需要回写
			if(pk_result_type_num!=null && 
					pk_result_type_num.equalsIgnoreCase(String.valueOf(srcDataList.get(i).getAttributeValue("pk_valuetype")))){
				String entryStrs = (String)allResultList.get(i).getAttributeValue("entry");
				if(entryStrs==null || "".equals(entryStrs) || "null".equals(entryStrs)||"NULL".equals(entryStrs)){
					entryStrs = null;
				}
				allResultList.get(i).setAttributeValue("numeric_entry", entryStrs);
			}
			//单位转换 2019年10月27日21:36:49
			String unitname = (String)allResultList.get(i).getAttributeValue("units");
			if(unitname!=null){
				String changeName = unitMap.get(unitname.replaceAll(" ", ""));
				if(changeName!=null){
					allResultList.get(i).setAttributeValue("units", changeName);
				}
				
			}
			//英文名:
			allResultList.get(i).setAttributeValue("attribute_1", srcDataList.get(i).getAttributeValue("englishdescription"));
//			allResultList.get(i).setAttributeValue("test", "test");
			
//			allResultList.get(i).setAttributeValue("places", 1);
		}

		processData.setFirstResultListMap(sortResult(allResultList));
	}

	private Map<String, String> getanalysisTypeCode() throws DAOException {
		Map<String, String> rsMap = new HashMap<>();
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rs = 
				(List<Map<String,Object>>)baseDao.executeQuery("select * from nc_analysis_type", new MapListProcessor());
		if(rs!=null && rs.size() > 0){
			for(Map<String,Object> data : rs){
				rsMap.put(String.valueOf(data.get("nc_type_name")).replaceAll(" ", ""), String.valueOf(data.get("nc_type_code")).replaceAll(" ", ""));
			}
		}
		return rsMap;
	}
	/**
	 * key : analysis的name
	 * @param taskList
	 * @return
	 * @throws BusinessException
	 */
	private Map<String, Map<String, Object>> getAnalysis(List<CProjTask> taskList) throws BusinessException {
		Map<String, Map<String, Object>> rsMap = new HashMap<>();
		Set<String> analysisNameSet = new HashSet<>();
		if(taskList!=null && taskList.size() > 0){
			for(CProjTask task : taskList){
				String resultAnalysis = String.valueOf(task.getAttributeValue("analysis")).replaceAll(" ", "");
				String analysis = null;
				if(resultAnalysis.length() > 2){
					if(resultAnalysis.substring(resultAnalysis.length()-2).equals("_A")){
						analysis = resultAnalysis.substring(0, resultAnalysis.length()-2);
					}else{
						analysis = resultAnalysis.substring(0, resultAnalysis.length()-1);
					}
				}
				
				
				analysisNameSet.add(analysis);
			}
			
		}
		InSQLCreator insql = new InSQLCreator();
		String insqlstr = insql.getInSQL(analysisNameSet.toArray(new String[0]));
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rs = 
		(List<Map<String,Object>>)baseDao.executeQuery("select * from analysis where name in ("+insqlstr+")", new MapListProcessor());
		if(rs!=null && rs.size() > 0){
			for(Map<String,Object> data : rs){
				rsMap.put(String.valueOf(data.get("name")).replaceAll(" ", ""), data);
			}
		}
		return rsMap;
	}

	private Map<Integer, List<Result>> sortResult(List<Result> allResultList) {
		Map<Integer, List<Result>> rsListMap = new HashMap<>();
		for (Result result : allResultList) {
			Integer taskid = (Integer) result.getAttributeValue("task_seq_num");
			//此字段不回写只是flag
			result.removeAttributeValue("task_seq_num");
			if (rsListMap.containsKey(taskid)) {
				rsListMap.get(taskid).add(result);
			} else {
				List<Result> resultList = new ArrayList<>();
				resultList.add(result);
				rsListMap.put(taskid, resultList);
			}
		}
		return rsListMap;
	}

	private List<Result> initWriteBackList(int size) {
		List<Result> rsList = new ArrayList<>();
		for(int i = 0;i < size ;i++){
			rsList.add(new Result());
		}
		return rsList;
	}

	private ISuperVO[] getAllTaskSVO(WriteBackProcessData processData) {
		List<ISuperVO> rsList = new ArrayList<>();
		AggTaskHVO aggvo = processData.getAggTaskHVO();
		ISuperVO[] bvos = aggvo.getChildren(TaskBVO.class);
		if (bvos != null && bvos.length > 0) {
			for (ISuperVO bvo : bvos) {
				TaskBVO cbvo = (TaskBVO) bvo;
				if (cbvo.getPk_task_s() != null && cbvo.getPk_task_s().length > 0) {
					Collections.addAll(rsList, cbvo.getPk_task_s());
				}
			}
		}
		return rsList.toArray(new ISuperVO[0]);
	}



}
