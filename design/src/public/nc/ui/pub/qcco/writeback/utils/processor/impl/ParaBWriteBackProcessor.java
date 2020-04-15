package nc.ui.pub.qcco.writeback.utils.processor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Collections;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.hr.utils.InSQLCreator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.ui.pub.qcco.writeback.utils.WriteBackProcessData;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.ParaB;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.Result;
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
import nc.vo.qcco.task.AggTaskHVO;
import nc.vo.qcco.task.TaskBVO;
import nc.vo.qcco.task.TaskRVO;

/**
 * ����������д
 * 
 * @author 91967
 * 
 */
public class ParaBWriteBackProcessor implements IFirstWriteBackProcessor,ISecWriteBackProcessor {
	private CommonUtils utils;
	private BaseDAO baseDao = new BaseDAO();
	@Override
	public void setUtils(CommonUtils utils) {
		this.utils = utils;
	}

	@Override
	public void processFirst(WriteBackProcessData data) throws BusinessException {
		process(data);
	}
	
	@Override
	public void processSec(WriteBackProcessData data) throws BusinessException {
		processSecond(data);
	}

	private void processSecond(WriteBackProcessData processData) throws BusinessException {
		List<Test> testSecList = processData.getSecTestList();
		InSQLCreator insql = new InSQLCreator();
		processData.getSecSampleListMap();
		if(testSecList!=null && testSecList.size() > 0){
			List<Result> secResultList = new ArrayList<>();
			IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
			for(Test test : testSecList){
				//��Ӧ��sample
				Sample sample = utils.getSampleBySample(String.valueOf(test.getAttributeValue("sample_number")));
				if(sample!=null && sample.getAttributeValue("product")!=null && sample.getAttributeValue("product_grade")!=null
						&& sample.getAttributeValue("sampling_point")!=null){
					//��ҵ��׼
					String product = sample.getAttributeValue("product").toString();
					//����
					String product_grade = sample.getAttributeValue("product_grade").toString();
					//�ṹ����
					String sampling_point = sample.getAttributeValue("sampling_point").toString();
					//��������
					String c_contact_type = sample.getAttributeValue("c_contact_type").toString();
					//ȡ�¶ȼ���
					Map<Integer, List<Test>> testParaBMap = processData.getTestParaBListMap();
					//��Ӧ��testʵ��ǰ����
					Map<String,String> stageMap= new HashMap<>();
					String analysis = null;
					if(testParaBMap!=null){
						List<Test> testParaBList = 
								testParaBMap.get(Integer.parseInt(String.valueOf(test.getAttributeValue("test_number"))));
						
						if(testParaBList!=null && testParaBList.size() > 0){
							//ȡanalysis
							analysis = String.valueOf(testParaBList.get(0).getAttributeValue("analysis"));
							//ȡ�¶�
							for(Test testFromTestParaB : testParaBList){
								stageMap.put(String.valueOf(testFromTestParaB.getAttributeValue("reported_name")),
										String.valueOf(testFromTestParaB.getAttributeValue("test_number")));
							}
						}
					}
					if(stageMap!=null && stageMap.size() > 0){
						String stageInsql = insql.getInSQL(stageMap.keySet().toArray(new String[0]));
						String sql = "SELECT "
								+" c.result_type com_result_type, "
								+" c.list_key com_list_key, "
								+" c.reportable com_reportable, "
								+" c.optional com_optional, "
								+" c.displayed com_displayed, "
								+" c.t_short_name com_t_short_name, "
								+" c.has_attributes com_has_attributes, "
								+" spec.* "
								+" FROM "
								+" PRODUCT_SPEC spec "
								+" left join COMPONENT c on  c.analysis = '�̵��������������' and c.name = spec.component "
								+" WHERE "
								+" spec.product='"+product+"' "
								+" AND spec.sampling_point='"+sampling_point+"' "
								+" AND spec.grade='"+product_grade+"' "
								+" AND spec.analysis='�̵��������������' "
								+" AND ( "
								+" c.c_contact_type LIKE '%, "+c_contact_type+"' "
								+" OR  c.c_contact_type LIKE '"+c_contact_type+",%' "
								+" OR  c.c_contact_type LIKE '%, "+c_contact_type+",%' "
								+" OR  c.c_contact_type = '"+c_contact_type+"'"
							    +" OR  spec.component = '�¶�' "
							    +" OR  spec.component = 'ʪ��') "
								+" and stage in ("+stageInsql+")";
						@SuppressWarnings("unchecked")
						List<Map<String,Object>> rsMap = (List<Map<String,Object>>)bs.executeQuery(sql, new MapListProcessor());
						//д��ʱ��
						String writeTimeStr = new UFDateTime().toStdString();
						if(rsMap!=null && rsMap.size() > 0){
							for(Map<String,Object> rs : rsMap){
								Result result = new Result();
								//sample_number
								result.setAttributeValue("sample_number", String.valueOf(test.getAttributeValue("sample_number")));
								//test id 
								result.setAttributeValue("test_number", stageMap.get(String.valueOf(rs.get("stage"))));
								//result id
								result.setAttributeValue("result_number", processData.getMaxResult()+1);
								processData.setMaxResult(processData.getMaxResult()+1);
								//
								result.setAttributeValue("reported_name", rs.get("component"));
								
								//component
								result.setAttributeValue("result_type", rs.get("com_result_type"));
								result.setAttributeValue("list_key", rs.get("com_list_key"));
								result.setAttributeValue("reportable", rs.get("com_reportable"));
								result.setAttributeValue("optional", rs.get("com_optional"));
								result.setAttributeValue("has_attributes", rs.get("com_has_attributes"));
								result.setAttributeValue("displayed", rs.get("com_displayed"));
								result.setAttributeValue("t_short_name", rs.get("com_t_short_name"));

								//PRODUCT_SPEC �ֶ�
								result.setAttributeValue("order_number", rs.get("order_number"));
								result.setAttributeValue("analysis", analysis);
								result.setAttributeValue("name", rs.get("component"));
								result.setAttributeValue("minimum", rs.get("min_value"));
								result.setAttributeValue("maximum", rs.get("max_value"));
								result.setAttributeValue("units", rs.get("units"));
								result.setAttributeValue("round", rs.get("round"));
								result.setAttributeValue("places", rs.get("places"));
								//����: null
								result.setAttributeValue("db_file", null);
								//д��ʱ��
								result.setAttributeValue("changed_on", "to_timestamp('"+ writeTimeStr +"','yyyy-mm-dd hh24:mi:ss.ff')");
								
								//һЩ��̬�ֶ�
								result.setAttributeValue("replicate_count", 0);
								result.setAttributeValue("status", "N");
								result.setAttributeValue("old_status", "N");
								result.setAttributeValue("modified_result", "F");
								result.setAttributeValue("allow_out", "T");
								result.setAttributeValue("in_spec", "T");
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
								result.setAttributeValue("spec_override", "F");
								result.setAttributeValue("places_2", 0);
								result.setAttributeValue("reported_result", "F");
								result.setAttributeValue("reported_rslt_rev", 0);
								result.setAttributeValue("reported_rslt_oos", "F");
								result.setAttributeValue("t_accredited", "T");
								result.setAttributeValue("primary_in_spec", "T");
								result.setAttributeValue("trans_num", 0.00);
								secResultList.add(result);
							}
						}
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
	 * ��ȡInsert���Ƭ��
	 * 
	 * @param fieldMap
	 * @param clazz
	 * @param pkname
	 * @param pkvalue
	 * @param ncPK2LimsPkMap
	 *            ncpk��limsϵͳpk��Ӧ��ϵ
	 * @param ncPK2ObjectMap
	 *            ���α��������NCOBJ
	 * @param testFirstExtends
	 *            sample���һ�λ�д�Ĳ���sql
	 * 
	 * @return fieldValues[0]: �ֶ���Ƭ��<br />
	 *         fieldValues[1-n]��ֵƬ��
	 * @return project ί�е����,���ڸ������project
	 * @throws BusinessException
	 */
	private void process(WriteBackProcessData processData) throws BusinessException {

		// NC����
		List<ISuperVO> srcDataList = new ArrayList<>();
		// ��ȡ���е�ʵ��ǰ����VO
		ISuperVO[] superVOs = getAllTaskRVO(processData);
		Collections.addAll(srcDataList, superVOs);
		if (null == srcDataList || srcDataList.size() <= 0) {
			return ;
		}
		// ��Ҫ��д��LIMS����
		// LIMS ʵ��ǰ����
		List<ParaB> allParaBList = initWriteBackList(srcDataList.size());
		// Ԥ����pk
		List<Integer> pkList = utils.getPrePk(TaskRVO.class, srcDataList.size());
		//����ENTRY_CODE(ѡȡC_PROJ_PARA_A�����ֵ)
        //Ԥ����ENTRY_CODE
        List<Integer> entryCodeList = utils.getPrePk("entry_code","c_proj_task_para_b",srcDataList.size());

		// ������ѭ��
		for (Entry<String, String> map : FirstWriteBackStaticMaping.GRAND_AFTER_MAPPING.entrySet()) {
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
					Object realValue = utils.getRealValue(unDofieldValue, fieldName, TaskRVO.class);
					for (String field : fields) {
						allParaBList.get(i).setAttributeValue(field, realValue);
					}
				}
			}
		}
		for (int i = 0; i < srcDataList.size(); i++) {
				// ��ȡ�ϲ������:
				String fatherPk = (String) (((ISuperVO) srcDataList.get(i)).getAttributeValue("pk_task_b"));

				// ����RULE_TYPE��ֵ
				TaskRVO rvo = ((TaskRVO) srcDataList.get(i));
				allParaBList.get(i).setAttributeValue("spec_rule", dealRuleType(rvo));
				
				// ��д ������
				TaskBVO bvo = (TaskBVO) utils.getNCObjByPK(rvo.getPk_task_b(), TaskBVO.class);
				String analysisName = bvo == null ? null : bvo.getPk_testresultname();
				allParaBList.get(i).setAttributeValue("task_name", analysisName);
				//analysis_version �����汾
				allParaBList.get(i).setAttributeValue("analysis_version", utils.getAnalysisVerionFromName(analysisName));
				// proj_logsamp_seqnum ͨ������ȡ��Ӧ��ί�е��ӱ�
				allParaBList.get(i).setAttributeValue("proj_logsamp_seqnum", 
						getCommissionBFromGroup(rvo.getPk_samplegroup(), bvo == null ? null : bvo.getPk_task_h()));

				// project name
				allParaBList.get(i).setAttributeValue("project", processData.getProject().getAttributeValue("name"));

				// ����
				allParaBList.get(i).setAttributeValue("seq_num", pkList.get(i));
				// ���
				allParaBList.get(i).setAttributeValue("task_seq_num", utils.getLIMSPKByNCPK(fatherPk, TaskBVO.class));
				// ����
				allParaBList.get(i).setAttributeValue("entry_code", entryCodeList.get(i));
			
		}
		processData.setParaBListMap(sortParaB(allParaBList));
	}

	/**
	 * ��ȡ����ʵ��ǰ����
	 * 
	 * @param processData
	 * @return
	 */
	private ISuperVO[] getAllTaskRVO(WriteBackProcessData processData) {
		List<ISuperVO> rsList = new ArrayList<>();
		AggTaskHVO aggvo = processData.getAggTaskHVO();
		ISuperVO[] bvos = aggvo.getChildren(TaskBVO.class);
		if (bvos != null && bvos.length > 0) {
			for (ISuperVO bvo : bvos) {
				TaskBVO cbvo = (TaskBVO) bvo;
				if (cbvo.getPk_task_r() != null && cbvo.getPk_task_r().length > 0) {
					Collections.addAll(rsList, cbvo.getPk_task_r());
				}
			}
		}
		return rsList.toArray(new ISuperVO[0]);
	}

	private String dealRuleType(TaskRVO rvo) {
		if (rvo != null) {
			return dealRuleType(rvo.getStdmaxvalue(), rvo.getStdminvalue());
		}
		return null;
	}

	private String dealRuleType(Object maxValue, Object minValue) {
		/*
		 * ί�е���� RuleType ��Ϊ��Ӧ��б����£� ֻ�����ֵ��LTE_MAX ֻ����Сֵ��GTE_MIN
		 * �����С���У�MNLTELTEMX ��ʪ�ȣ�EMPTY GT_MIN MNLTLTEMX
		 */
		if ((maxValue != null && !"-".equals(String.valueOf(maxValue))) && (minValue == null || "-".equals(String.valueOf(minValue)))) {
			return "LET_MAX";
		} else if ((maxValue == null || "-".equals(String.valueOf(maxValue)))
				&& (minValue != null && !"-".equals(String.valueOf(minValue)))) {
			return "GTE_MIN";
		} else if ((maxValue != null && !"-".equals(String.valueOf(maxValue)) && (minValue != null && !"-".equals(String.valueOf(minValue))))) {
			return "MNLTELTEMX";
		} else {
			return "EMPTY";
		}

		//return null;
	}

	/**
	 * ����� map<c_proj_task.test_number,List<ParaB>>
	 * 
	 * @param allParaAList
	 * @return
	 */
	private Map<Integer, List<ParaB>> sortParaB(List<ParaB> allParaBList) {
		Map<Integer, List<ParaB>> rsListMap = new HashMap<>();
		for (ParaB parab : allParaBList) {
			Integer taskid = (Integer) parab.getAttributeValue("task_seq_num");
			if (rsListMap.containsKey(taskid)) {
				rsListMap.get(taskid).add(parab);
			} else {
				List<ParaB> paraBList = new ArrayList<>();
				paraBList.add(parab);
				rsListMap.put(taskid, paraBList);
			}
		}
		return rsListMap;
	}

	
    private Integer getCommissionBFromGroup(String pk_simpleGroup,String pk_task_h) throws DAOException {
    	Integer pkSimple= null;
		if(pk_simpleGroup!=null && pk_task_h != null){
			String sql = " select cb.PK_COMMISSION_B from QC_COMMISSION_B cb "
						+" left join QC_TASK_H th on cb.PK_COMMISSION_H = th.PK_COMMISSION_H "
						+" where cb.PK_SAMPLEGROUP = '"+pk_simpleGroup+"' and th.PK_task_H = '"+pk_task_h+"' and cb.dr = 0 ";
			String pk_commission_b = (String)baseDao.executeQuery(sql, new ColumnProcessor());
			Object pkSimpleObj = utils.getLIMSPKByNCPK(pk_commission_b, CommissionBVO.class);
			try{
				pkSimple = Integer.valueOf(pkSimpleObj.toString());
			}catch(Exception e){
				pkSimple = null;
			}
		}
		return pkSimple;
	}
    
    private List<ParaB> initWriteBackList(int size) {
		List<ParaB> rsList = new ArrayList<>();
		
		for(int i = 0;i < size ;i++){
			rsList.add(new ParaB());
		}
		
		return rsList;
	}

	
}
