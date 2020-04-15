package nc.ui.pub.qcco.writeback.utils.processor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.ui.pub.qcco.writeback.utils.WriteBackProcessData;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.CProjLoginSample;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.CProjTask;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.Sample;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.Test;
import nc.ui.pub.qcco.writeback.utils.common.CommonUtils;
import nc.ui.pub.qcco.writeback.utils.mapping.FirstWriteBackStaticMaping;
import nc.ui.pub.qcco.writeback.utils.processor.IFirstWriteBackProcessor;
import nc.ui.pub.qcco.writeback.utils.processor.ISecWriteBackProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ISuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.commission.CommissionRVO;
import nc.vo.qcco.task.TaskBVO;
import nc.vo.qcco.task.TaskHVO;
import nc.vo.qcco.task.TaskRVO;

/**
 * test第一次和第二次回写
 * 
 * @author 91967
 * 
 */
public class TestWriteBackProcessor implements IFirstWriteBackProcessor, ISecWriteBackProcessor {

	private CommonUtils utils;

	@Override
	public void setUtils(CommonUtils utils) {
		this.utils = utils;
	}

	@Override
	public void processSec(WriteBackProcessData data) throws BusinessException {
		processSecond(data);
	}

	@Override
	public void processFirst(WriteBackProcessData data) throws BusinessException {
		process(data);
	}

	/**
	 * test 二次回写
	 * 
	 * @param data
	 * @throws BusinessException 
	 */
	private void processSecond(WriteBackProcessData processData) throws BusinessException {
		// LIMS Data
		List<Sample> allSampleList = processData.getAllSecSampleList();
		// 需要回写的LIMS
		// test第二次回写,task每分配一个样品就产生一个test
		List<Test> secTestList = new ArrayList<>();

		
		//创建日期
		String dateCreateDate = "to_timestamp('"+
				processData.getAggCommissionHVO().getParentVO().getCreationtime().getEndDate().toStdString()
					+"','yyyy-mm-dd hh24:mi:ss.ff')";
		
		
		if (allSampleList != null && allSampleList.size() > 0) {
			Map<String,Object> sampleCacheMap = new HashMap<>();
			for (int i = 0; i < allSampleList.size(); i++) {
				//第二次回写的sample
				Sample sample = allSampleList.get(i);
				if(sample==null){
					continue;
				}
				//查找对应的sample_group
				CProjLoginSample sampleGroup = 
						(CProjLoginSample)utils.getSampleGroupByGroup((String)sample.getAttributeValue("c_sample_group"));
				List<CProjTask> taskList = processData.getTaskFromSampleSec(sample);
				//回写实验前后参数
				if(sampleGroup!=null && sampleGroup.getAttributeValue("product_stage")!=null){
					writeBackParaA(sampleGroup,sample,processData,sampleCacheMap,taskList);
				}
				int labCount = 1;
				if(taskList!=null && taskList.size() > 0){
					if(taskList.size() > 1){
						//当一个样品不止一条task时,序列为true
						allSampleList.get(i).setAttributeValue("c_is_sequnce", "T");
					}
					for(CProjTask task : taskList){
						Test test = new Test();
						// TEST.TEST_NUMBER 根据sample_number主键自增
						test.setAttributeValue("test_number", processData.getMaxTestPK()+1);
						processData.setMaxTestPK(processData.getMaxTestPK()+1);
						
						// TEST.SAMPLE_NUMBER
						// 取sample表二次插入的sample_number，一个任务有多少只样品，便有多少行
						test.setAttributeValue("sample_number", Integer.parseInt(String.valueOf(sample.getAttributeValue("sample_number"))));

						// TEST.STATUS I
						test.setAttributeValue("status", "I");

						// TEST.C_TASK_SEQ_NUM c_proj_task表任务表任务对应的主键
						test.setAttributeValue("c_task_seq_num", task.getAttributeValue("seq_num"));

						// TEST.C_TASK_ID 任务表c_proj_task.task_id
						test.setAttributeValue("c_task_id", task.getAttributeValue("task_id"));

						
						// TEST.test类型 
						test.setAttributeValue("c_test_type", "测试结果");

						// TEST.ORDER_NUMBER 任务排序
						test.setAttributeValue("order_number", task.getAttributeValue("order_number"));

						// TEST.LAB  测试小组名称
						test.setAttributeValue("lab", utils.getLabFromAnalysisName(String.valueOf(task.getAttributeValue("analysis"))));
						if(1 == labCount){
							sample.setAttributeValue("lab", utils.getLabFromAnalysisName(String.valueOf(task.getAttributeValue("analysis"))));
							labCount++;
						}
						
						// TEST.VARIATION default:null
						test.setAttributeValue("variation", null);

						// TEST.T_ANALYSIS_METHOD 分析方法(如IEC61810-7)
						test.setAttributeValue("t_analysis_method", utils.getMethodFromAnalysisName(String.valueOf(task.getAttributeValue("analysis"))));
						//任务单表体测试结果名称
						test.setAttributeValue("analysis", String.valueOf(task.getAttributeValue("analysis")));
						Map<String, Object> analysis = utils.getAnalysis(String.valueOf(task.getAttributeValue("analysis")));
						//分析版本
						test.setAttributeValue("version", analysis.get("version"));
						//任务单表体测试项目
						test.setAttributeValue("reported_name", task.getAttributeValue("task_reported_name"));
						//自增
						test.setAttributeValue("original_test", processData.getMaxTestPK()+1);
						//最后修改时间
						test.setAttributeValue("changed_on", task.getAttributeValue("changed_on"));
						//制单日期
						test.setAttributeValue("t_date_enabled", dateCreateDate);
						//短名称
						test.setAttributeValue("common_name", analysis.get("common_name"));
						//task.removeAttributeValue("shot_name_for_test");
						//排程类别
						test.setAttributeValue("c_arrange_type", analysis.get("c_arrange_type"));

						
						//一些固定值
						test.setAttributeValue("analysis_count", 0);
						test.setAttributeValue("replicate_count", 1);
						test.setAttributeValue("old_status", "I");
						test.setAttributeValue("batch_parent_test", 0);
						test.setAttributeValue("batch_sibling_test", 0);
						test.setAttributeValue("parent_test", 0);
						test.setAttributeValue("date_received", null);
						test.setAttributeValue("date_started", null);
						test.setAttributeValue("assigned_operator", null);
						test.setAttributeValue("prep", "F");
						test.setAttributeValue("prep_date", null);
						test.setAttributeValue("prep_by", null);
						test.setAttributeValue("date_completed", null);
						test.setAttributeValue("date_reviewed", null);
						test.setAttributeValue("reviewer", null);
						test.setAttributeValue("replicate_test", "F");
						test.setAttributeValue("test_priority", 0);
						test.setAttributeValue("in_spec", "T");
						test.setAttributeValue("in_cal", "T");
						test.setAttributeValue("test_location", "DEFAULT");
						test.setAttributeValue("resolve_reqd", "F");
						test.setAttributeValue("stage", "NONE");
						test.setAttributeValue("primary_in_spec", "T");
						test.setAttributeValue("in_control", "T");
						test.setAttributeValue("re_tested", "F");
						test.setAttributeValue("modified_results", "F");
						test.setAttributeValue("on_worksheet", "F");
						test.setAttributeValue("aliquoted_to", 0);
						test.setAttributeValue("display_results", "T");
						test.setAttributeValue("split_replicates", "F");
						test.setAttributeValue("cross_sample", "F");
						test.setAttributeValue("released", "F");
						test.setAttributeValue("aliquot_group", "DEFAULT");
						test.setAttributeValue("double_entry", "F");
						test.setAttributeValue("child_out_spec", "F");
						test.setAttributeValue("charge_entry", 0);
						test.setAttributeValue("signed", "F");
						test.setAttributeValue("batch_original_test", 0);
						test.setAttributeValue("test_sequence_no", 0);
						test.setAttributeValue("invoice_number", 0);
						test.setAttributeValue("cntrct_qte_item_no", 0);
						test.setAttributeValue("reported_rslt_oos", "F");
						test.setAttributeValue("double_blind", "F");
						test.setAttributeValue("pre_invoice_number", 0);
						test.setAttributeValue("t_charge_group", 0);
						test.setAttributeValue("t_needs_location", "F");
						test.setAttributeValue("t_prep_test", 0);
						test.setAttributeValue("t_qc_reference", 0);
						test.setAttributeValue("t_turnaround_actua", 0);
						test.setAttributeValue("t_turnaround_charg", 0);
						test.setAttributeValue("t_turnaround_met", "F");
						test.setAttributeValue("trans_num", 0);
						test.setAttributeValue("c_if_arranged", "F");
						test.setAttributeValue("c_arrange_seq_num", 0);
						test.setAttributeValue("c_apply_review", "F");
						test.setAttributeValue("c_task_status", 0);
						test.setAttributeValue("c_test_cycle", 0);
						test.setAttributeValue("c_failure_cycle", 0);
						test.setAttributeValue("c_base_para_temp", "T");
						test.setAttributeValue("group_name", "DEFAULT");
						
						
						secTestList.add(test);
						writeBackParaB(test,sample,sampleGroup,processData,sampleCacheMap);
					}
				}
			}
			processData.setSecTestList(secTestList);
		}
	}
	
	/**
	 * 
	 * @param sampleGroup
	 * @param sample
	 * @param processData
	 * @param sampleCacheMap
	 *            <sample_number::缓存名,缓存值>
	 * @param taskList
	 * @throws BusinessException 
	 */
	private void writeBackParaA(CProjLoginSample sampleGroup, Sample sample, WriteBackProcessData processData,
			Map<String, Object> sampleCacheMap, List<CProjTask> taskList) throws BusinessException {
		// <第二次写入的sample_number,<对应的实验前参数list>>
		Map<Integer, List<Test>> rsMap = new HashMap<>();
		// 拆分温度字段
		String[] stages = String.valueOf(sampleGroup.getAttributeValue("product_stage")).split(",");
		//二级分类
		/*String c_prod_type_c1 = String.valueOf(processData.getProject().getAttributeValue("c_prod_type_c1"));
		//description
		String description = String.valueOf(sampleGroup.getAttributeValue("product_series"));
		//production_spec
		String production_spec = String.valueOf(sampleGroup.getAttributeValue("production_spec"));
		//product_standard
		String product_standard = String.valueOf(sampleGroup.getAttributeValue("product_standard"));*/
		// ORDER_NUMBER
		int orderNum = (taskList == null ? 1 : taskList.size() + 1);
		IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		/*String analysisSql = "SELECT DISTINCT pgs.ANALYSIS analysis_name "
				+" FROM product p,  product_grade pg, prod_grade_STAGE pgs "
				+" WHERE p.c_prod_type_c1 LIKE '"+c_prod_type_c1+"' "
				+" AND p.description = '"+description+"' "
				+" AND pg.sampling_point = '"+production_spec+"' "
				+" AND p.active = 'T' "
				+" AND removed = 'F' "
				+" AND pgs.order_number = 1 "
				+" AND p.name = pg.product "
				+" AND pgs.product = p.name "
				+" AND pgs.sampling_point = pg.sampling_point "
				+" AND pgs.GRADE = pg.grade "
				+" AND pgs.product = '"+product_standard+"' ";*/
		
		//ANALYSIS mod 改为直接从孙表获取 2020年4月5日14:40:2
		String analysis = null;
		ISuperVO[] bvos = processData.getAggCommissionHVO().getChildren(CommissionBVO.class);
		if(bvos!=null && bvos.length > 0){
			CommissionRVO[] rvos = ((CommissionBVO)processData.getAggCommissionHVO().getChildren(CommissionBVO.class)[0]).getPk_commission_r();
			if(rvos!=null && rvos.length > 0){
				analysis = rvos[0].getAnalysisname();
			}
		}
		
		//analysis_method
		String analysis_method = (String)bs.executeQuery(
				"select t_analysis_method from analysis where name = '"+String.valueOf(analysis)+"'", 
				new ColumnProcessor());
		String common_name = (String)bs.executeQuery(
				"select common_name from analysis where name = '"+String.valueOf(analysis)+"'", 
				new ColumnProcessor());
		//REPLICATE_COUNT
		int replicate_count = 1;
		//回写时间
		String time = "to_timestamp('" + new UFDateTime().toStdString() + "','yyyy-mm-dd hh24:mi:ss.ff')";
				
		if (stages != null && stages.length > 0) {
			for (String stage : stages) {
				Test test = new Test();
				test.setAttributeValue("reported_name", stage);
				test.setAttributeValue("c_condition", stage);
				//
				test.setAttributeValue("sample_number", sample.getAttributeValue("sample_number"));
				// TEST.TEST_NUMBER 根据sample_number主键自增
				test.setAttributeValue("test_number", processData.getMaxTestPK() + 1);
				test.setAttributeValue("original_test", processData.getMaxTestPK() + 1);
				
				processData.setMaxTestPK(processData.getMaxTestPK() + 1);
				// orderNum
				test.setAttributeValue("order_number", orderNum++);
				//ANALYSIS
				test.setAttributeValue("analysis", analysis);
				//
				test.setAttributeValue("replicate_count", replicate_count++);
				test.setAttributeValue("parent_test", 0);
				//回写时间
				test.setAttributeValue("changed_on", time);
				test.setAttributeValue("t_date_enabled", time);
				//t_method
				test.setAttributeValue("t_analysis_method", analysis_method);
				//common_name
				test.setAttributeValue("common_name", common_name);
				// 静态字段
				test.setAttributeValue("version", 1);
				test.setAttributeValue("analysis_count", 0);
				test.setAttributeValue("group_name", "DEFAULT");
				test.setAttributeValue("status", "U");
				test.setAttributeValue("old_status", "I");
				test.setAttributeValue("batch_parent_test", 0);
				test.setAttributeValue("batch_sibling_test", 0);
				test.setAttributeValue("prep", "F");
				test.setAttributeValue("replicate_test", "T");
				test.setAttributeValue("test_priority", 0);
				test.setAttributeValue("in_spec", "T");
				test.setAttributeValue("in_cal", "T");
				test.setAttributeValue("test_location", "DEFAULT");
				test.setAttributeValue("lab", "01产品实验室");
				test.setAttributeValue("resolve_reqd", "F");
				test.setAttributeValue("stage", "NONE");
				test.setAttributeValue("PRIMARY_IN_SPEC", "T");
				test.setAttributeValue("in_control", "T");
				test.setAttributeValue("re_tested", "F");
				test.setAttributeValue("modified_results", "F");
				test.setAttributeValue("aliquoted_to", 0);
				test.setAttributeValue("on_worksheet", "F");
				test.setAttributeValue("display_results", "T");
				test.setAttributeValue("aliquot_group", "DEFAULT");
				test.setAttributeValue("split_replicates", "F");
				test.setAttributeValue("cross_sample", "F");
				test.setAttributeValue("released", "F");
				test.setAttributeValue("double_entry", "F");
				test.setAttributeValue("child_out_spec", "F");
				test.setAttributeValue("charge_entry", 0);
				test.setAttributeValue("signed", "F");
				test.setAttributeValue("batch_original_test", 0);
				test.setAttributeValue("test_sequence_no", 0);
				test.setAttributeValue("invoice_number", 0);
				test.setAttributeValue("cntrct_qte_item_no", 0);
				test.setAttributeValue("reported_rslt_oos", "F");
				test.setAttributeValue("double_blind", "F");
				test.setAttributeValue("pre_invoice_number", 0);
				test.setAttributeValue("t_charge_group", 0);
				test.setAttributeValue("t_container_group", "TYPE_2");
				test.setAttributeValue("t_needs_location", "F");
				test.setAttributeValue("t_prep_test", 0);
				test.setAttributeValue("t_qc_reference", 0);
				test.setAttributeValue("t_report_header", "NON_REPORT");
				test.setAttributeValue("t_turnaround_actua", 0);
				test.setAttributeValue("t_turnaround_charg", 0);
				test.setAttributeValue("t_turnaround_met", "F");
				test.setAttributeValue("c_task_seq_num", 0);
				test.setAttributeValue("trans_num", 0);
				test.setAttributeValue("c_if_arranged", "F");
				test.setAttributeValue("c_arrange_seq_num", 0);
				test.setAttributeValue("c_apply_review", "F");
				test.setAttributeValue("c_arrange_type", "C类");
				test.setAttributeValue("c_task_status", 0);
				test.setAttributeValue("c_test_cycle", 0);
				test.setAttributeValue("c_failure_cycle", 0);
				test.setAttributeValue("c_base_para_temp", "T");
				test.setAttributeValue("c_test_type", "试验前参数");

				// 回写
				List<Test> testList = rsMap.get((Integer) sample.getAttributeValue("sample_number"));
				if (testList == null) {
					testList = new ArrayList<>();
				}
				testList.add(test);
				rsMap.put((Integer) sample.getAttributeValue("sample_number"), testList);
			}
		}
		// orderNum缓存
		sampleCacheMap.put(String.valueOf(sample.getAttributeValue("sample_number")) + "::orderNum", orderNum);
		//set回写数据
		Map<Integer, List<Test>> rbMap = processData.getTestParaAListMap();
		if (rbMap == null) {
			rbMap = new HashMap<>();
		}
		Set<Integer> sampleSet = rsMap.keySet();
		for (Integer sample_number : sampleSet) {
			List<Test> dateList = rbMap.get(sample_number);
			if (dateList == null) {
				dateList = new ArrayList<>();
			}
			dateList.addAll(rsMap.get(sample_number) == null ? new ArrayList<Test>() : rsMap.get(sample_number));
			rbMap.put(sample_number, dateList);
		}
		processData.setTestParaAListMap(rbMap);
	}

	/**
	 * 
	 * @param testSource
	 * @param sample
	 * @param sampleGroup
	 * @param processData
	 * @param sampleCacheMap
	 *            <sample_number::缓存名,缓存值>
	 * @throws BusinessException 
	 */
	private void writeBackParaB(Test testSource, Sample sample, CProjLoginSample sampleGroup, WriteBackProcessData processData,
			Map<String, Object> sampleCacheMap) throws BusinessException {
		// <第二次写入的test_number,<对应的实验后参数list>>
		Map<Integer, List<Test>> rsMap = new HashMap<>();
		// orderNum
		int orderNum = sampleCacheMap.get(String.valueOf(sample.getAttributeValue("sample_number")) + "::orderNum") == null ? 1 : Integer
				.parseInt(sampleCacheMap.get(String.valueOf(sample.getAttributeValue("sample_number")) + "::orderNum").toString());
		// 拆分温度字段
		String[] stages = String.valueOf(sampleGroup.getAttributeValue("product_stage")).split(",");
		// 二级分类
		/*String c_prod_type_c1 = String.valueOf(processData.getProject().getAttributeValue("c_prod_type_c1"));
		// description
		String description = String.valueOf(sampleGroup.getAttributeValue("product_series"));
		// production_spec
		String production_spec = String.valueOf(sampleGroup.getAttributeValue("production_spec"));
		// product_standard
		String product_standard = String.valueOf(sampleGroup.getAttributeValue("product_standard"));*/
		// ORDER_NUMBER
		IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		/*String analysisSql = "SELECT DISTINCT pgs.ANALYSIS analysis_name "
						+" FROM product p,  product_grade pg, prod_grade_STAGE pgs "
						+" WHERE p.c_prod_type_c1 LIKE '"+c_prod_type_c1+"' "
						+" AND p.description = '"+description+"' "
						+" AND pg.sampling_point = '"+production_spec+"' "
						+" AND p.active = 'T' "
						+" AND removed = 'F' "
						+" AND pgs.order_number = 2 "
						+" AND p.name = pg.product "
						+" AND pgs.product = p.name "
						+" AND pgs.sampling_point = pg.sampling_point "
						+" AND pgs.GRADE = pg.grade "
						+" AND pgs.product = '"+product_standard+"' ";*/
				
		//ANALYSIS mod 从孙表直接取
		String analysis = null;
		ISuperVO[] bvos = processData.getAggTaskHVO().getChildren(TaskBVO.class);
		if(bvos!=null && bvos.length > 0){
			TaskRVO[] rvos = ((TaskBVO)processData.getAggTaskHVO().getChildren(TaskBVO.class)[0]).getPk_task_r();
			if(rvos!=null && rvos.length > 0){
				analysis = rvos[0].getAnalysisname();
			}
		}
		//analysis_method
		String analysis_method = (String) bs.executeQuery(
				"select t_analysis_method from analysis where name = '" + String.valueOf(analysis) + "'", new ColumnProcessor());
		String common_name = (String)bs.executeQuery(
				"select common_name from analysis where name = '"+String.valueOf(analysis)+"'", 
				new ColumnProcessor());
		//REPLICATE_COUNT
		int replicate_count = 1;
		//回写时间
		String time = "to_timestamp('" + new UFDateTime().toStdString() + "','yyyy-mm-dd hh24:mi:ss.ff')";
		if (stages != null && stages.length > 0) {
			for (String stage : stages) {
				Test test = new Test();
				test.setAttributeValue("reported_name", stage);
				test.setAttributeValue("c_condition", stage);
				//
				test.setAttributeValue("sample_number", sample.getAttributeValue("sample_number"));
				// TEST.TEST_NUMBER 根据sample_number主键自增
				test.setAttributeValue("test_number", processData.getMaxTestPK() + 1);
				processData.setMaxTestPK(processData.getMaxTestPK() + 1);
				// orderNum
				test.setAttributeValue("order_number", orderNum++);
				//ANALYSIS
				test.setAttributeValue("analysis", analysis);
				test.setAttributeValue("replicate_count", replicate_count++);
				test.setAttributeValue("parent_test", testSource.getAttributeValue("test_number"));
				test.setAttributeValue("original_test", testSource.getAttributeValue("test_number"));
				//回写时间
				test.setAttributeValue("changed_on", time);
				test.setAttributeValue("t_date_enabled", time);
				//t_method
				test.setAttributeValue("t_analysis_method", analysis_method);
				//common_name
				test.setAttributeValue("common_name", common_name);
				// 静态字段
				test.setAttributeValue("version", 1);
				test.setAttributeValue("analysis_count", 0);
				test.setAttributeValue("group_name", "DEFAULT");
				test.setAttributeValue("status", "U");
				test.setAttributeValue("old_status", "I");
				test.setAttributeValue("batch_parent_test", 0);
				test.setAttributeValue("batch_sibling_test", 0);
				test.setAttributeValue("prep", "F");
				test.setAttributeValue("replicate_test", "T");
				test.setAttributeValue("test_priority", 0);
				test.setAttributeValue("in_spec", "T");
				test.setAttributeValue("in_cal", "T");
				test.setAttributeValue("test_location", "DEFAULT");
				test.setAttributeValue("lab", "01产品实验室");
				test.setAttributeValue("resolve_reqd", "F");
				test.setAttributeValue("stage", "NONE");
				test.setAttributeValue("PRIMARY_IN_SPEC", "T");
				test.setAttributeValue("in_control", "T");
				test.setAttributeValue("re_tested", "F");
				test.setAttributeValue("modified_results", "F");
				test.setAttributeValue("aliquoted_to", 0);
				test.setAttributeValue("on_worksheet", "F");
				test.setAttributeValue("display_results", "T");
				test.setAttributeValue("aliquot_group", "DEFAULT");
				test.setAttributeValue("split_replicates", "F");
				test.setAttributeValue("cross_sample", "F");
				test.setAttributeValue("released", "F");
				test.setAttributeValue("double_entry", "F");
				test.setAttributeValue("child_out_spec", "F");
				test.setAttributeValue("charge_entry", 0);
				test.setAttributeValue("signed", "F");
				test.setAttributeValue("batch_original_test", 0);
				test.setAttributeValue("test_sequence_no", 0);
				test.setAttributeValue("invoice_number", 0);
				test.setAttributeValue("cntrct_qte_item_no", 0);
				test.setAttributeValue("reported_rslt_oos", "F");
				test.setAttributeValue("double_blind", "F");
				test.setAttributeValue("pre_invoice_number", 0);
				test.setAttributeValue("t_charge_group", 0);
				test.setAttributeValue("t_container_group", "TYPE_2");
				test.setAttributeValue("t_needs_location", "F");
				test.setAttributeValue("t_prep_test", 0);
				test.setAttributeValue("t_qc_reference", 0);
				test.setAttributeValue("t_report_header", "NON_REPORT");
				test.setAttributeValue("t_turnaround_actua", 0);
				test.setAttributeValue("t_turnaround_charg", 0);
				test.setAttributeValue("t_turnaround_met", "F");
				test.setAttributeValue("c_task_seq_num", 0);
				test.setAttributeValue("trans_num", 0);
				test.setAttributeValue("c_if_arranged", "F");
				test.setAttributeValue("c_arrange_seq_num", 0);
				test.setAttributeValue("c_apply_review", "F");
				test.setAttributeValue("c_arrange_type", "C类");
				test.setAttributeValue("c_task_status", 0);
				test.setAttributeValue("c_test_cycle", 0);
				test.setAttributeValue("c_failure_cycle", 0);
				test.setAttributeValue("c_base_para_temp", "T");
				test.setAttributeValue("c_test_type", "试验后参数");

				// 回写
				List<Test> testList = rsMap.get((Integer) testSource.getAttributeValue("test_number"));
				if (testList == null) {
					testList = new ArrayList<>();
				}
				testList.add(test);
				rsMap.put((Integer) testSource.getAttributeValue("test_number"), testList);
			}
		}
		// orderNum缓存
		sampleCacheMap.put(String.valueOf(sample.getAttributeValue("sample_number")) + "::orderNum", orderNum);
		//set回写数据
		Map<Integer, List<Test>> rbMap =  processData.getTestParaBListMap();
		if(rbMap==null){
			rbMap = new HashMap<>();
		}
		Set<Integer> testSet = rsMap.keySet();
		for(Integer test_number : testSet){
			List<Test> dateList = rbMap.get(test_number);
			if(dateList==null){
				dateList= new ArrayList<>();
			}
			dateList.addAll(rsMap.get(test_number)==null?new ArrayList<Test>():rsMap.get(test_number));
			rbMap.put(test_number, dateList);
		}
		processData.setTestParaBListMap(rbMap);
	}
	/**
	 * 第一次回写Test表
	 * 
	 * @param object
	 * @param object
	 * @param pk_test
	 *            预申请的test主键
	 * @param pk_firstSample
	 *            第一次回写的sample表主键
	 * @throws BusinessException
	 */
	private void process(WriteBackProcessData processData) throws BusinessException {
		// NC数据
		ISuperVO[] bvos = processData.getAggTaskHVO().getChildren(TaskBVO.class);
		TaskHVO taskHvo = processData.getAggTaskHVO().getParentVO();

		// sample数据
		List<Sample> firstSampleList = processData.getFirstSampleList();
		// task 数据
		List<CProjTask> taskList = processData.getTaskList();

		// 需要回写的LIMS数据 --test 第一次回写, 一个sample对应一个test 都对应一条task任务
		List<Test> firstTestList = initData(bvos.length);

		// 预申请PK
		List<Integer> test_numberList = utils.getPrePk("test_number", "test", processData.getTaskList().size());
		processData.setMaxTestPK(test_numberList.get(processData.getTaskList().size() - 1));

		for (int i = 0; i < bvos.length; i++) {
			TaskBVO taskBvo = (TaskBVO) bvos[i];

			// 主键 外键
			firstTestList.get(i).setAttributeValue("test_number", test_numberList.get(i));
			firstTestList.get(i).setAttributeValue("original_test", test_numberList.get(i));
			//需求变更第一次生成的sample只有一条
			firstTestList.get(i).setAttributeValue("sample_number", firstSampleList.get(0).getAttributeValue("sample_number"));

			// task 任务关联
			taskList.get(i).setAttributeValue("test_number", test_numberList.get(i));

			// 任务单创建时间
			UFDateTime creatTime = taskHvo.getCreationtime();
			if(null==creatTime){
				creatTime = processData.getAggCommissionHVO().getParentVO().getCreationtime();
			}
			String time = "to_timestamp('" + creatTime + "','yyyy-mm-dd hh24:mi:ss.ff')";
			firstTestList.get(i).setAttributeValue("date_received", time);
			firstTestList.get(i).setAttributeValue("date_started", time);
			
			firstTestList.get(i).setAttributeValue("t_date_enabled", "to_timestamp('" + creatTime.getEndDate().toStdString() + "','yyyy-mm-dd hh24:mi:ss.ff')");
			firstTestList.get(i).setAttributeValue("date_completed", "to_timestamp('" + new UFDateTime().toStdString() + "','yyyy-mm-dd hh24:mi:ss.ff')");
			
			// 最后修改时间
			UFDateTime modifyTime = taskHvo.getModifiedtime() == null ? creatTime : taskHvo.getModifiedtime();
			firstTestList.get(i).setAttributeValue("changed_on", "to_timestamp('" + modifyTime + "','yyyy-mm-dd hh24:mi:ss.ff')");

			String analysis = taskBvo.getStandardclause();
			// 表体测试结果名称
			firstTestList.get(i).setAttributeValue("analysis", analysis);

			// 测试结果短名称
			firstTestList.get(i).setAttributeValue("common_name", taskBvo.getTestresultshortname());

			// 测试项目
			firstTestList.get(i).setAttributeValue("reported_name", taskBvo.getTestitem());

			// 分析版本
			firstTestList.get(i).setAttributeValue("version", utils.getAnalysisVerionFromName(analysis));
			//lab
			firstTestList.get(i).setAttributeValue("lab", utils.getLabFromAnalysisName(analysis));
			if(0==i){
				firstSampleList.get(0).setAttributeValue("lab",utils.getLabFromAnalysisName(analysis));
				
			}
			//分析方法
			firstTestList.get(i).setAttributeValue("t_analysis_method", utils.getMethodFromAnalysisName(analysis));
			firstSampleList.get(0).setAttributeValue("status","C");
			
		}
		processData.setFirstTestList(firstTestList);
	}

	private List<Test> initData(int length) {
		List<Test> rsList = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			rsList.add(new Test());
		}
		return rsList;
	}

}
