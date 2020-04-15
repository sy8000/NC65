package nc.ui.pub.qcco.writeback.utils.processor.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Collections;

import nc.bs.framework.common.NCLocator;
import nc.hr.utils.InSQLCreator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.jdbc.framework.processor.ResultSetProcessor;
import nc.ui.pub.qcco.writeback.utils.WriteBackProcessData;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.ParaA;
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
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.commission.CommissionRVO;

/**
 * 实验前参数回写
 * 
 * @author 91967
 * 
 */
public class ParaAWriteBackProcessor implements IFirstWriteBackProcessor,ISecWriteBackProcessor {

	private CommonUtils utils;

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
	/**
	 * 二次回写,从PRODUCT_SPEC 到result
	 * @param data
	 * @throws BusinessException 
	 */
	private void processSecond(WriteBackProcessData processData) throws BusinessException {
		Collection<List<Sample>> sampleSecList = processData.getSecSampleListMap().values();
		InSQLCreator insql = new InSQLCreator();
		processData.getSecSampleListMap();
		if(sampleSecList!=null && sampleSecList.size() > 0){
			List<Result> secResultList = new ArrayList<>();
			IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
			for(List<Sample> sampleList : sampleSecList){
				for(Sample sample : sampleList){
					if(sample!=null && sample.getAttributeValue("product")!=null && sample.getAttributeValue("product_grade")!=null
							&& sample.getAttributeValue("sampling_point")!=null){
						//企业标准
						String product = sample.getAttributeValue("product").toString();
						//规格号
						String product_grade = sample.getAttributeValue("product_grade").toString();
						//结构类型
						String sampling_point = sample.getAttributeValue("sampling_point").toString();
						//触点类型
						String c_contact_type = sample.getAttributeValue("c_contact_type").toString();
						//取温度集合
						Map<Integer, List<Test>> testParaAMap = processData.getTestParaAListMap();
						//对应的test实验前参数
						Map<String,String> stageMap= new HashMap<>();
						String analysis = null;
						List<Test> testParaAList = null;
						if(testParaAMap!=null){
							testParaAList= testParaAMap.get(Integer.parseInt(String.valueOf(sample.getAttributeValue("sample_number"))));
							
							if(testParaAList!=null && testParaAList.size() > 0){
								//取analysis
								analysis = String.valueOf(testParaAList.get(0).getAttributeValue("analysis"));
								//取温度
								for(Test testFromTestParaA : testParaAList){
									stageMap.put(String.valueOf(testFromTestParaA.getAttributeValue("reported_name")),
											String.valueOf(testFromTestParaA.getAttributeValue("test_number")));
								}
							}
						}
						if(stageMap!=null && stageMap.size() > 0 && testParaAList!=null && testParaAList.size() > 0) {
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
									+" left join COMPONENT c on  c.analysis = spec.analysis and c.name = spec.component "
									+" WHERE "
									+" spec.product='"+product+"' "
									+" AND spec.sampling_point='"+sampling_point+"' "
									+" AND spec.grade='"+product_grade+"' "
									+" AND spec.analysis='继电器企标初始参数' "
									+" AND ( "
									+" c.c_contact_type LIKE '%, "+c_contact_type+"' "
									+" OR  c.c_contact_type LIKE '"+c_contact_type+",%' "
									+" OR  c.c_contact_type LIKE '%, "+c_contact_type+",%' "
									+" OR  c.c_contact_type = '"+c_contact_type+"'"
									+ "or spec.component = '温度' "
									+" or spec.component = '湿度') "
									+" and stage in ("+stageInsql+")";
							@SuppressWarnings("unchecked")
							List<Map<String,Object>> rsMap = (List<Map<String,Object>>)bs.executeQuery(sql, new MapListProcessor());
							//写入时间
							String writeTimeStr = new UFDateTime().toStdString();
							
							 processData.getTestParaAListMap();
							if (rsMap != null && rsMap.size() > 0) {
								for (Map<String, Object> rs : rsMap) {
									for (Test testParaa : testParaAList) {
										Result result = new Result();
										// sample_number
										result.setAttributeValue("sample_number", String.valueOf(sample.getAttributeValue("sample_number")));
										// test id
										// result.setAttributeValue("test_number",
										// test.getAttributeValue("test_number"));
										// mod tank 2019年11月25日17:15:05
										// 改成test里相同analysis的实验前/后参数的test_number
										result.setAttributeValue("test_number", testParaa.getAttributeValue("test_number"));
										// result id
										result.setAttributeValue("result_number", processData.getMaxResult() + 1);
										processData.setMaxResult(processData.getMaxResult() + 1);
										//
										result.setAttributeValue("reported_name", rs.get("component"));

										// component
										result.setAttributeValue("result_type", rs.get("com_result_type"));
										result.setAttributeValue("list_key", rs.get("com_list_key"));
										result.setAttributeValue("reportable", rs.get("com_reportable"));
										result.setAttributeValue("optional", rs.get("com_optional"));
										result.setAttributeValue("has_attributes", rs.get("com_has_attributes"));
										result.setAttributeValue("displayed", rs.get("com_displayed"));
										result.setAttributeValue("t_short_name", rs.get("com_t_short_name"));

										// PRODUCT_SPEC 字段
										result.setAttributeValue("order_number", rs.get("order_number"));
										result.setAttributeValue("analysis", analysis);
										result.setAttributeValue("name", rs.get("component"));
										result.setAttributeValue("minimum", rs.get("min_value"));
										result.setAttributeValue("maximum", rs.get("max_value"));
										result.setAttributeValue("units", rs.get("units"));
										result.setAttributeValue("round", rs.get("round"));
										result.setAttributeValue("places", rs.get("places"));
										// 附件: null
										result.setAttributeValue("db_file", null);
										// 写入时间
										result.setAttributeValue("changed_on", "to_timestamp('" + writeTimeStr
												+ "','yyyy-mm-dd hh24:mi:ss.ff')");

										// 一些静态字段
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
	 * @param processData
	 *            processData
	 * @throws BusinessException
	 */
	private void process(WriteBackProcessData processData) throws BusinessException {

		// NC数据
		List<ISuperVO> srcDataList = new ArrayList<>();
		// 获取所有的实验前参数VO
		ISuperVO[] superVOs = getAllCommissionRVO(processData);
		Collections.addAll(srcDataList, superVOs);
		if (null == srcDataList || srcDataList.size() <= 0) {
			return;
		}

		// 需要回写的LIMS数据
		// LIMS 实验前参数
		List<ParaA> allParaAList = initWriteBackList(srcDataList.size());

		// 预申请pk
		List<Integer> pkList = utils.getPrePk(CommissionRVO.class, srcDataList.size());
		//处理ENTRY_CODE(选取C_PROJ_PARA_A的最大值)
        //预申请ENTRY_CODE
        List<Integer> entryCodeList = utils.getPrePk("entry_code","c_proj_para_a",srcDataList.size());
		
		
		// 进行列循环
		for (Entry<String, String> map : FirstWriteBackStaticMaping.GRAND_BEFORE_MAPPING.entrySet()) {
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
					Object realValue = utils.getRealValue(unDofieldValue, fieldName, CommissionRVO.class);
					for (String field : fields) {
						allParaAList.get(i).setAttributeValue(field, realValue);
					}
				}
			}
		}
		//获取各个组别对应的实验前参数的数量
		Map<String,Integer> numberMap = getGroupParaANum(processData.getAggCommissionHVO().getPrimaryKey());
		//单位缓存
		@SuppressWarnings("unchecked")
		Map<String,String> unitMap = (Map<String, String>) NCLocator.getInstance().lookup(IUAPQueryBS.class)
					.executeQuery("select NC_UNITS_NAME,UNIT_CODE from nc_units_type", 
							new ResultSetProcessor() {
								private static final long serialVersionUID = 6620032648507337165L;
								Map<String,String> rsMap = new HashMap<>();
								@Override
								public Object handleResultSet(ResultSet rs) throws SQLException {
									while(rs.next()){
										String key = (rs.getString(1)==null?null:rs.getString(1).replaceAll(" ", ""));
										String value = (rs.getString(2)==null?null:rs.getString(2).replaceAll(" ", ""));
										rsMap.put(key, value);
									}
									return rsMap;
								}
							});
		for (int i = 0; i < srcDataList.size(); i++) {
			// 写入主键和父主键(只支持单主键,要多主键,需要参考上面的字段添加逻辑)

			// 获取上层的主键:
			String fatherPk = (String) (((ISuperVO) srcDataList.get(i)).getAttributeValue("pk_commission_b"));

			// 处理委托单子表字段需要写到孙表
			for (String limsField : FirstWriteBackStaticMaping.BODY_SIMPLE_2_CHILDREN_MAPPING.keySet()) {
				// 上层的NCVO 需要回写到子表的字段
				String ncField = FirstWriteBackStaticMaping.BODY_SIMPLE_2_CHILDREN_MAPPING.get(limsField);
				// 获取上层的数据
				ISuperVO fatherObj = (ISuperVO) utils.getNCObjByPK(fatherPk, CommissionBVO.class);

				// 获取需要回写的值
				Object oldFieldValue = fatherObj == null ? null : fatherObj.getAttributeValue(ncField);
				// 处理参照
				Object realValue = utils.getRealValue(oldFieldValue, ncField, CommissionBVO.class);

				allParaAList.get(i).setAttributeValue(utils.getWriteBackFields(new String[] { limsField })[0], realValue);
			}
			// 处理RULE_TYPE的值
			CommissionRVO rvo = ((CommissionRVO) srcDataList.get(i));
			allParaAList.get(i).setAttributeValue("rule_type", dealRuleType(rvo));
			allParaAList.get(i).setAttributeValue("analysis_version", 1);
			// 主键
			allParaAList.get(i).setAttributeValue("seq_num", pkList.get(i));
			// 外键
			allParaAList.get(i).setAttributeValue("proj_logsamp_seqnum", utils.getLIMSPKByNCPK(fatherPk, CommissionBVO.class));
			// project name
			allParaAList.get(i).setAttributeValue("project", processData.getProject().getAttributeValue("name"));
			// entry_code
			allParaAList.get(i).setAttributeValue("entry_code", entryCodeList.get(i));
			//order_number 不同组别公用一个序号
			int ordernum = 0;
			if(allParaAList.get(i).getAttributeValue("order_number")!=null){
				ordernum = Integer.parseInt(String.valueOf(allParaAList.get(i).getAttributeValue("order_number")));
			}
			if("B".equalsIgnoreCase(String.valueOf(allParaAList.get(i).getAttributeValue("sample_group")))){
				allParaAList.get(i).setAttributeValue("order_number",ordernum+numberMap.get("A"));
			}else if("C".equalsIgnoreCase(String.valueOf(allParaAList.get(i).getAttributeValue("sample_group")))){
				allParaAList.get(i).setAttributeValue("order_number",ordernum+numberMap.get("A")+numberMap.get("B"));
			}else if("D".equalsIgnoreCase(String.valueOf(allParaAList.get(i).getAttributeValue("sample_group")))){
				allParaAList.get(i).setAttributeValue("order_number",ordernum+numberMap.get("A")+numberMap.get("B")+numberMap.get("C"));
			}
			//单位转换 2019年10月20日20:28:50
			String unitname = (String)allParaAList.get(i).getAttributeValue("units");
			if(unitname!=null){
				String changeName = unitMap.get(unitname.replaceAll(" ", ""));
				if(changeName!=null){
					allParaAList.get(i).setAttributeValue("units", changeName);
				}
				
			}
		}
		processData.setParaAListMap(sortParaA(allParaAList));
	}

	/**
	 * 获取各个组别对应的实验前参数的数量
	 * @param pk_commission_h
	 * @return
	 * @throws BusinessException 
	 */
	private Map<String, Integer> getGroupParaANum(String pk_commission_h) throws BusinessException {
		Map<String, Integer> rs = new HashMap<>();
		String sql = " select sg.NC_SAMPLE_NAME sggroup,max(to_number(r.ROWNO)) num from QC_COMMISSION_R  r "
				+" left join QC_COMMISSION_B b on b.PK_COMMISSION_B = r.PK_COMMISSION_B "
				+" left join NC_SAMPLE_GROUP sg on sg.PK_SAMPLE_GROUP = b.PK_SAMPLEGROUP "
				+" where r.PK_COMMISSION_B in (select PK_COMMISSION_B  "
				+" from QC_COMMISSION_B  where PK_COMMISSION_H = '"+pk_commission_h+"')  "
				+" GROUP by sg.NC_SAMPLE_NAME ";
		IUAPQueryBS bs = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> rsList = 
				(List<Map<String,Object>>)bs.executeQuery(sql, new MapListProcessor());
		if(rsList!=null && rsList.size() > 0){
			for(Map<String,Object> rsMap : rsList){
				if(rsMap!=null && rsMap.get("sggroup")!=null){
					if(rsMap.get("num")!=null){
						rs.put(String.valueOf(rsMap.get("sggroup")), Integer.parseInt(String.valueOf(rsMap.get("num"))));
					}else{
						rs.put(String.valueOf(rsMap.get("sggroup")), 0);
					}
					
				}
			}
		}
		return rs;
	}

	private List<ParaA> initWriteBackList(int size) {
		List<ParaA> rsList = new ArrayList<>();
		
		for(int i = 0;i < size ;i++){
			rsList.add(new ParaA());
		}
		
		return rsList;
	}

	/**
	 * 整理成 map<c_proj_login_sample.seq_num,List<ParaA>>
	 * 
	 * @param allParaAList
	 * @return
	 */
	private Map<Integer, List<ParaA>> sortParaA(List<ParaA> allParaAList) {
		Map<Integer, List<ParaA>> rsListMap = new HashMap<>();
		for (ParaA paraa : allParaAList) {
			Integer sampleId = (Integer) paraa.getAttributeValue("proj_logsamp_seqnum");
			if (rsListMap.containsKey(sampleId)) {
				rsListMap.get(sampleId).add(paraa);
			} else {
				List<ParaA> paraAList = new ArrayList<>();
				paraAList.add(paraa);
				rsListMap.put(sampleId, paraAList);
			}
		}
		return rsListMap;
	}

	/**
	 * 获取所有的实验前参数
	 * 
	 * @param processData
	 * @return
	 */
	private ISuperVO[] getAllCommissionRVO(WriteBackProcessData processData) {
		List<ISuperVO> rsList = new ArrayList<>();
		AggCommissionHVO aggvo = processData.getAggCommissionHVO();
		ISuperVO[] bvos = aggvo.getChildren(CommissionBVO.class);
		if (bvos != null && bvos.length > 0) {
			for (ISuperVO bvo : bvos) {
				CommissionBVO cbvo = (CommissionBVO) bvo;
				if (cbvo.getPk_commission_r() != null && cbvo.getPk_commission_r().length > 0) {
					Collections.addAll(rsList, cbvo.getPk_commission_r());
				}
			}
		}
		return rsList.toArray(new ISuperVO[0]);
	}

	/**
	 * RuleType 字段
	 * 
	 * @param rvo
	 * @return
	 */
	private String dealRuleType(CommissionRVO rvo) {
		if (rvo != null) {
			return dealRuleType(rvo.getStdmaxvalue(), rvo.getStdminvalue());
		}
		return null;
	}

	private String dealRuleType(Object maxValue, Object minValue) {
		/*
		 * 委托单孙表 RuleType 此为对应项，列表如下： 
		 * 针对number数据类型，只有max_value有数据时，rule_type数据为LET_MAX，
		 * 只有min_value有数据时，rule_type数据为GET_MIN，
		 *  max_value和min_value都有数据时，rule_type数据为MNLTELTEMX，
		 *  component列的数据为“温度”或者“湿度”时，rule_type数据为EMPTY，
		 *  number类型的其他情况，rule_type数据为EMPTY
		 */
		if ((maxValue != null && !"-".equals(String.valueOf(maxValue))) && (minValue == null || "-".equals(String.valueOf(minValue)))) {
			return "LET_MAX";
		} else if ((maxValue == null || "-".equals(String.valueOf(maxValue)))
				&& (minValue != null && !"-".equals(String.valueOf(minValue)))) {
			return "GTE_MIN";
		} else if ((maxValue != null && !"-".equals(String.valueOf(maxValue)) && (minValue != null && !"-".equals(String.valueOf(minValue))))) {
			return "MNLTELTEMX";
		} else{
			return "EMPTY";
		}

		//return null;
	}

}
