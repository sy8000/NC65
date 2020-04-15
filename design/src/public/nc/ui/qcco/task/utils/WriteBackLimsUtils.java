package nc.ui.qcco.task.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ISuperVO;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.commission.CommissionHVO;
import nc.vo.qcco.commission.CommissionRVO;
import nc.vo.qcco.task.TaskBVO;
import nc.vo.qcco.task.TaskRVO;
import nc.vo.qcco.task.TaskSVO;

public class WriteBackLimsUtils {
	private BaseDAO baseDao = new BaseDAO();
	//lims主键
	public static Map<Class<?>,String>LIMS_PK_MAP;
	{
		LIMS_PK_MAP = new HashMap<>();
		LIMS_PK_MAP.put(CommissionHVO.class,"PROJECT.NAME");
		LIMS_PK_MAP.put(CommissionBVO.class,"C_PROJ_LOGIN_SAMPLE.SEQ_NUM");
		LIMS_PK_MAP.put(CommissionRVO.class,"C_PROJ_PARA_A.SEQ_NUM");
		
		LIMS_PK_MAP.put(TaskBVO.class,"c_proj_task.SEQ_NUM");
		LIMS_PK_MAP.put(TaskSVO.class,"result.RESULT_NUMBER");
		LIMS_PK_MAP.put(TaskRVO.class,"c_proj_task_para_b.SEQ_NUM");
		
	}
	
	public final static String[] LIMS_FK_SAMPLE = new String[] { "C_PROJ_LOGIN_SAMPLE", "PROJECT" };// C_PROJ_LOGIN_SAMPLE.PROJECT

	public final static String[] LIMS_FK_BEFORE = new String[] { "C_PROJ_PARA_A", "PROJECT" }; // C_PROJ_PARA_A.PROJECT

	public final static String[] LIMS_FK_TASK = new String[] { "c_proj_task", "PROJECT" }; // c_proj_task.PROJECT

	public final static String[] LIMS_FK_CONDITION_TASK = new String[] { "result", "TEST_NUMBER" }; // result.TEST_NUMBER
	public final static String[] LIMS_FK_CONDITION_SAMPLE = new String[] { "result", "SAMPLE_NUMBER" }; // result.SAMPLE_NUMBER

	public final static String[] LIMS_FK_AFTER = new String[] { "c_proj_task_para_b", "TASK_SEQ_NUM" };// c_proj_task_para_b.TASK_SEQ_NUM

	// 委托单
	private Map<String, String> headMapping; // qc_commission_h
	private Map<String, String> bodySampleMapping; // qc_commission_b
	private Map<String, String> grandBeforeMapping; // qc_commission_r

	// 任务单
	private Map<String, String> bodyTaskMapping; // qc_task_b
	private Map<String, String> grandConditionMapping; // qc_task_s
	private Map<String, String> grandAfterMapping; // qc_task_r
	
	//记录每个vo的上层主键
	public static Map<Class<?>,String> FATHER_PK_KEY_MAP;
	{
		FATHER_PK_KEY_MAP = new HashMap();

		FATHER_PK_KEY_MAP.put(CommissionHVO.class, null);
		FATHER_PK_KEY_MAP.put(CommissionBVO.class, "pk_commission_h");
		FATHER_PK_KEY_MAP.put(CommissionRVO.class, "pk_commission_b");
		FATHER_PK_KEY_MAP.put(TaskBVO.class, "pk_task_h");
		FATHER_PK_KEY_MAP.put(TaskSVO.class, "pk_task_b");
		FATHER_PK_KEY_MAP.put(TaskRVO.class, "pk_task_b");
	}

	/**
	 * 取委托单/任务单回写LIMS的插入SQL
	 * 
	 * @param pk_commission_h
	 * @return
	 * @throws BusinessException
	 */
	public String[] getInsertLIMSSQL(String pk_commission_h) throws BusinessException {
		List<String> sqlList = new ArrayList<String>();
		//主子孙对应的数据量--用来校验
		Map<Class<?>,Integer> class2NumMap = new HashMap<>();
		String[] lists = null;

		//存储nc中的pk和lims系统的pk的对应关系
		Map<String,Object> ncPK2LimsPkMap = new HashMap<>();
		String headCond = "pk_commission_h = '" + pk_commission_h + "' and dr = 0 ";
		// 委托单表头
		lists = getInsertSQLByMap(getHeadMapping(), CommissionHVO.class, headCond,ncPK2LimsPkMap);
		if(lists==null || lists.length < 1){
			throw new BusinessException("委托单主档回写失败!");
		}
		class2NumMap.put(CommissionHVO.class, lists.length-1);
		sqlList.addAll(getHeadInsertSQL(lists));
		
		// 样品行
		lists = getInsertSQLByMap(getBodySampleMapping(), CommissionBVO.class, headCond,ncPK2LimsPkMap);
		class2NumMap.put(CommissionBVO.class, lists.length-1);
		sqlList.addAll(getSampleInsertSQL(lists));
		
		// 实验前参数
		String subCondition = "pk_commission_b in (select pk_commission_b from qc_commission_b where " 
				+ headCond + ") and dr = 0 ";
		lists = getInsertSQLByMap(getGrandAfterMapping(), CommissionRVO.class, subCondition,ncPK2LimsPkMap);
		if(lists!=null && class2NumMap.get(CommissionBVO.class) <=0 &&lists.length > 0){
			throw new BusinessException("委托单孙表[实验前参数]发现无效数据,请联系数据管理员.");
		}
		class2NumMap.put(CommissionRVO.class, lists.length-1);
		sqlList.addAll(getBeforeInsertSQL(lists));

		
		// 任务行
		subCondition = "pk_task_h in (select pk_task_h from qc_task_h where " + headCond + ") and dr = 0 ";
		//
		lists = getInsertSQLByMap(getBodyTaskMapping(), TaskBVO.class, subCondition,ncPK2LimsPkMap);
		class2NumMap.put(TaskBVO.class, lists.length-1);
		sqlList.addAll(getTaskInsertSQL(lists));
	
		
		// 实验条件
		subCondition = "pk_task_b in (select pk_task_b from qc_task_b where "
				+ " pk_task_h in (select pk_task_h from qc_task_h where " + headCond + " ) and dr = 0 ) and dr = 0 ";
		lists = getInsertSQLByMap(getGrandConditionMapping(), TaskSVO.class, subCondition,ncPK2LimsPkMap);
		if(lists!=null && class2NumMap.get(TaskBVO.class) <=0 &&lists.length > 0){
			throw new BusinessException("任务单孙表[实验条件]发现无效数据,请联系数据管理员.");
		}
		class2NumMap.put(TaskSVO.class, lists.length-1);
		sqlList.addAll(getConditionInsertSQL(lists));

		
		// 实验后参数
		subCondition = "pk_task_b in (select pk_task_b from qc_task_b where "
				+ " pk_task_h in (select pk_task_h from qc_task_h where " + headCond + " ) and dr = 0 ) and dr = 0 ";
		lists = getInsertSQLByMap(getGrandAfterMapping(), TaskRVO.class, subCondition,ncPK2LimsPkMap);
		if(lists!=null && class2NumMap.get(TaskBVO.class) <=0 &&lists.length > 0){
			throw new BusinessException("任务单孙表[实验后参数]发现无效数据,请联系数据管理员.");
		}
		class2NumMap.put(TaskRVO.class, lists.length-1);
		sqlList.addAll(getAfterInsertSQL(lists));

		
		return sqlList.toArray(new String[0]);
	}

	/**
	 * 实验后参数
	 * @param lists
	 * @param ncPK2LimsPk 
	 * @param fatherPkList 
	 * @param selfPkList 
	 * @return
	 */
	private List<String> getAfterInsertSQL(String[] lists) {
		List<String> rsList = new ArrayList();
		if(lists!=null && lists.length > 1){
			StringBuilder sqlSB = new StringBuilder();
			for(int i = 1;i<lists.length;i++){
				sqlSB.append("INSERT INTO c_proj_task_para_b").append("(").append(lists[0]).append(")  values (");
				sqlSB.append(lists[i]);
				sqlSB.append(" ) ");
				rsList.add(sqlSB.toString());
				sqlSB.delete(0, sqlSB.length());
			}
		}
		return rsList;
	}

	/**
	 * 实验条件
	 * @param lists
	 * @param ncPK2LimsPk 
	 * @param fatherPkList 
	 * @param selfPkList 
	 * @return
	 */
	private List<String> getConditionInsertSQL(String[] lists) {
		List<String> rsList = new ArrayList();
		if(lists!=null && lists.length > 1){
			StringBuilder sqlSB = new StringBuilder();
			for(int i = 1;i<lists.length;i++){
				sqlSB.append("INSERT INTO result").append("(").append(lists[0]).append(")  values (");
				sqlSB.append(lists[i]);
				sqlSB.append(" ) ");
				rsList.add(sqlSB.toString());
				sqlSB.delete(0, sqlSB.length());
			}
		}
		return rsList;
	}

	/**
	 * 任务行
	 * @param lists
	 * @param ncPK2LimsPk 
	 * @param fatherPkList 
	 * @param selfPkList 
	 * @return
	 */
	private List<String> getTaskInsertSQL(String[] lists) {
		List<String> rsList = new ArrayList();
		if(lists!=null && lists.length > 1){
			StringBuilder sqlSB = new StringBuilder();
			for(int i = 1;i<lists.length;i++){
				sqlSB.append("INSERT INTO c_proj_task").append("(").append(lists[0]).append(")  values (");
				sqlSB.append(lists[i]);
				sqlSB.append(" ) ");
				rsList.add(sqlSB.toString());
				sqlSB.delete(0, sqlSB.length());
			}
		}
		return rsList;
	}

	/**
	 * 实验前参数
	 * @param lists
	 * @param ncPK2LimsPk 
	 * @param fatherPkList 
	 * @param selfPkList 
	 * @return
	 */
	private List<String> getBeforeInsertSQL(String[] lists) {
		List<String> rsList = new ArrayList();
		if(lists!=null && lists.length > 1){
			StringBuilder sqlSB = new StringBuilder();
			for(int i = 1;i<lists.length;i++){
				sqlSB.append("INSERT INTO C_PROJ_PARA_A").append("(").append(lists[0]).append(")  values (");
				sqlSB.append(lists[i]);
				sqlSB.append(" ) ");
				rsList.add(sqlSB.toString());
				sqlSB.delete(0, sqlSB.length());
			}
		}
		return rsList;
	}
	
	/**
	 * 样品行
	 * @param lists
	 * @param ncPK2LimsPk 
	 * @param fatherPkList 
	 * @param selfPkList 
	 * @return
	 */
	private List<String> getSampleInsertSQL(String[] lists) {
		List<String> rsList = new ArrayList();
		if(lists!=null && lists.length > 1){
			StringBuilder sqlSB = new StringBuilder();
			for(int i = 1;i<lists.length;i++){
				sqlSB.append("INSERT INTO C_PROJ_LOGIN_SAMPLE").append("(").append(lists[0]).append(")  values (");
				sqlSB.append(lists[i]);
				sqlSB.append(" ) ");
				rsList.add(sqlSB.toString());
				sqlSB.delete(0, sqlSB.length());
			}
		}
		return rsList;
	}
	
	/**
	 * 委托单表头
	 * @param lists
	 * @param ncPK2LimsPk 
	 * @param fatherPkList 
	 * @param selfPkList 
	 * @return
	 */
	private List<String> getHeadInsertSQL(String[] lists) {
		List<String> rsList = new ArrayList();
		if(lists!=null && lists.length > 1){
			//先 生成好pk
			
			StringBuilder sqlSB = new StringBuilder();
			for(int i = 1;i<lists.length;i++){
				sqlSB.append("INSERT INTO PROJECT").append("(").append(lists[0]).append(")  values (");
				sqlSB.append(lists[i]);
				sqlSB.append(" ) ");
				rsList.add(sqlSB.toString());
				sqlSB.delete(0, sqlSB.length());
			}
		}
		return rsList;
	}

	/**
	 * 获取Insert语句片断
	 * 
	 * @param fieldMap
	 * @param clazz
	 * @param pkname
	 * @param pkvalue
	 * @param ncPK2LimsPkMap ncpk和lims系统pk对应关系
	 * @return fieldValues[0]: 字段名片断<br />
	 *         fieldValues[1-n]：值片断
	 * @throws BusinessException
	 */
	private String[] getInsertSQLByMap(Map<String, String> fieldMap, Class<?> clazz, 
			String condition,Map<String,Object> ncPK2LimsPkMap)
			throws BusinessException {
		
		//用于某个字段的值,一行数据对应array中的一行
		StringBuilder[] fieldValues = null;
		IUAPQueryBS query = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		List<?> srcData = (List<?>)query.retrieveByClause(clazz, condition);
		List<Integer> pkList = null;
		if(CommissionHVO.class!=clazz){
			//预申请pk-出来委托单之外(委托单的主键是billNo)
			pkList = getPrePk(clazz,srcData.size());
		}
		
		fieldValues = new StringBuilder[srcData.size() + 1];
		//用于拼接字段名
		StringBuilder insertFields = new StringBuilder();
		//进行列循环
		for (Entry<String, String> map : fieldMap.entrySet()) {
			String fieldName = map.getKey();

			String[] fields = null;
			if (map.getValue().contains(";")) {
				fields = getWriteBackFields(map.getValue().split(";"));
			} else {
				fields = getWriteBackFields(new String[] { map.getValue() });
			}
			//times是处理一对多关系的
			int times = fields.length;
			for (String field : fields) {
				insertFields.append(field).append(",");
			}
			
			if (srcData != null && srcData.size() > 0) {
				
				int row = 1;
				for (Object data : srcData) {
					ISuperVO vo = (ISuperVO) data;
					Object fieldValue = vo.getAttributeValue(fieldName);
					if(null == fieldValues[row]){
						fieldValues[row] = new StringBuilder();
					}
					for(int j = 0 ; j<times;j++){
						if(fieldValue!=null){
							if(fieldValue instanceof Integer){
								fieldValues[row].append(fieldValue).append(",") ;
							}else{
								fieldValues[row] .append("'").append(String.valueOf(fieldValue)).append("',") ;
							}
						}else{
							fieldValues[row].append("null").append(",");
						}
					}
					row++;
				}
			}
		}

		if (srcData != null && srcData.size() > 0) {
			fieldValues[0] = insertFields;
		}
		String[] rsString = null;
		if(fieldValues!=null){
			rsString = new String[fieldValues.length];
			for(int i = 0;i<fieldValues.length ;i++){
				if(CommissionHVO.class!=clazz){
					//写入主键(只支持单主键,要多主键,需要参考上面的字段添加逻辑)
					if(0==i){
						fieldValues[i].append(getWriteBackFields(new String[] {LIMS_PK_MAP.get(clazz)})[0]);
					}else{
						fieldValues[i].append(pkList.get(i-1));
						//记录主键
						ncPK2LimsPkMap.put(((ISuperVO)srcData.get(i-1)).getPrimaryKey(), pkList.get(i-1));
					}
					
				}else{
					//委托单的主键就是他的billno
					if(i>0){
						ncPK2LimsPkMap.put(((ISuperVO)srcData.get(i-1)).getPrimaryKey(),
								((ISuperVO)srcData.get(i-1)).getAttributeValue("billno"));
					}
					fieldValues[i].setLength(fieldValues[i].length()-1);
				}
				
				rsString[i] = fieldValues[i].toString();
			}
			
		}
		return rsString;
	}
	
	/**
	 * 根据实体和实体数量申请lims的pk
	 * @param clazz
	 * @param size
	 * @return
	 * @throws DAOException 
	 */
	private List<Integer> getPrePk(Class<?> clazz, int size) throws DAOException {
		List<Integer> rs = new ArrayList<>();
		//获取表
		String tableName = null;
		if(LIMS_PK_MAP.get(clazz)!=null){
			tableName = LIMS_PK_MAP.get(clazz).split("\\.")[0];
		}
		if(tableName!=null){
			String sql = "select count(*) from "+tableName;
			Integer startNum = (Integer)baseDao.executeQuery(sql, new ColumnProcessor());
			if(startNum!=null && startNum >= 0){
				for(int i = 1;i<=size;i++){
					rs.add(startNum+i);
				}
			}
		}
		return rs;
	}

	private String[] getWriteBackFields(String[] splitFields) {
		List<String> fieldList = new ArrayList<String>();
		for (String field : splitFields) {
			fieldList.add(field.split("\\.")[1]);
		}
		return fieldList.toArray(new String[0]);
	}

	public Map<String, String> getHeadMapping() {
		if (headMapping == null) {
			headMapping = new HashMap<String, String>();

			headMapping.put("pk_commissiontype", "project.template");// 委托单类型
			headMapping.put("billno", "project.name");// 委托单编号
			headMapping.put("billname", "project.title");// 委托单名称
			headMapping.put("pk_owner", "project.customer");// 委托单位
			headMapping.put("pk_dept", "project.c_user_department");// 部门
			headMapping.put("pk_payer", "project.t_source_customer");// 付费单位
			headMapping.put("contract", "project.customer_contact");// 联系人
			headMapping.put("cuserid", "project.owner;project.created_by;");// 创建人
			headMapping.put("email", "project.c_email_address");// Email
			headMapping.put("teleno", "project.c_phone_number");// 联系电话
			headMapping.put("pk_maincategory", "project.c_product_type");// 产品大类
			headMapping.put("pk_subcategory", "project.c_prod_type_c1");// 二级分类
			headMapping.put("pk_lastcategory", "project.c_prod_type_c2");// 三级分类
			headMapping.put("reportformat", "project.c_coa_format");// 报告格式
			headMapping.put("reportlang", "project.c_coa_language");// 报告语言
			headMapping.put("managersendflag", "project.c_mail_lab_approval");// 实验室主管审核发送邮件
			headMapping.put("taskbeginsendflag", "project.c_mail_task_end");// 任务开始发送邮件
			headMapping.put("taskendsendflag", "project.c_mail_task_start");// 任务结束发送邮件
			headMapping.put("reportsendflag", "project.c_mail_coa_sign");// 报告签发发送邮件
			headMapping.put("savetotemplateflag", "project.c_is_template");// 是否保存为模板
			headMapping.put("receiptsendflag", "project.c_mail_charge");// 计费单发送给客户邮件提醒
			headMapping.put("quotaionsendflag", "project.c_mail_quotes");// 报价单发送给客户邮件提醒
			headMapping.put("testaim", "project.c_test_purpose");// 测试目的
			headMapping.put("progressneed", "project.description");// 进度要求
			headMapping.put("sampledealtype", "project.c_retain_handle");// 检后样品处理
			headMapping.put("productproperty", "project.c_product_property");// 产品属性
			headMapping.put("customername", "project.c_terminal_client");// 客户名称
			headMapping.put("customertype", "project.c_client_type");// 客户类型
			headMapping.put("testrequirement", "project.c_product_requirement");// 测试需求
			headMapping.put("checkingproperty", "project.c_checking_property");// 检测性质
			headMapping.put("productline", "project.c_product_line");// 生产产线
			headMapping.put("batchnumber", "project.c_batch_number");// 生产批量
			headMapping.put("productdate", "project.c_product_date");// 生产日期
			headMapping.put("batchserial", "project.c_batch_serial");// 生产批号
			headMapping.put("identificationtype", "project.c_identification_type");// 产品鉴定类型
			headMapping.put("certificationtype", "project.c_certification_type");// 认证类型
			headMapping.put("itemnumber", "project.c_item_number");// 项目号
			headMapping.put("modifiedtime", "project.date_createdproject.date_updated");// 制单时间
		}

		return headMapping;
	}
/**
 * 委托单子表
 * @return
 */
	public Map<String, String> getBodySampleMapping() {
		if (bodySampleMapping == null) {
			bodySampleMapping = new HashMap<String, String>();

			bodySampleMapping.put("pk_productserial", "C_PROJ_LOGIN_SAMPLE.product_series");// 产品系列
			bodySampleMapping.put("pk_enterprisestandard",
					"C_PROJ_LOGIN_SAMPLE.product_standard;c_proj_para_a.product_standard;");// 企业标准
			bodySampleMapping.put("typeno", "C_PROJ_LOGIN_SAMPLE.prodname;");// 规格型号
			bodySampleMapping.put("pk_productspec",
					"C_PROJ_LOGIN_SAMPLE.production_spec;c_proj_para_a.production_spec;");// 规格号
			bodySampleMapping.put("pk_structuretype",
					"C_PROJ_LOGIN_SAMPLE.structure_type;C_PROJ_PARA_A.structure_type;");// 结构类型
			bodySampleMapping.put("contacttype", "C_PROJ_LOGIN_SAMPLE.contact_type");// 触点类型
			bodySampleMapping.put("quantity", "C_PROJ_LOGIN_SAMPLE.sample_quantity");// 样品数量
			bodySampleMapping.put("manufacturer", "C_PROJ_LOGIN_SAMPLE.manufacturer");// 制造商
			bodySampleMapping.put("pk_contactbrand", "C_PROJ_LOGIN_SAMPLE.contact_brand");// 触点牌号
			bodySampleMapping.put("contactmodel", "C_PROJ_LOGIN_SAMPLE.contact_model");// 触点型号
			bodySampleMapping.put("productstage", "C_PROJ_LOGIN_SAMPLE.product_stage;C_PROJ_PARA_A.stage;");// 温度
			bodySampleMapping.put("pk_samplegroup", "C_PROJ_LOGIN_SAMPLE.sample_group");// 样品组别
			bodySampleMapping.put("otherinfo", "C_PROJ_LOGIN_SAMPLE.other_req");// 其他信息
		}

		return bodySampleMapping;
	}

	public Map<String, String> getGrandBeforeMapping() {
		if (grandBeforeMapping == null) {
			grandBeforeMapping = new HashMap<String, String>();

			grandBeforeMapping.put("analysisname", "C_PROJ_PARA_A.analysis");// 实验前参数名称
			grandBeforeMapping.put("pk_samplegroup", "C_PROJ_PARA_A.sample_group");// 样品组别
			grandBeforeMapping.put("pk_component", "C_PROJ_PARA_A.component");// 参数项
			grandBeforeMapping.put("stdmaxvalue", "C_PROJ_PARA_A.max_value");// 最大值
			grandBeforeMapping.put("stdminvalue", "C_PROJ_PARA_A.min_value");// 最小值
			grandBeforeMapping.put("unitname", "C_PROJ_PARA_A.units");// 单位
			grandBeforeMapping.put("judgeflag", "C_PROJ_PARA_A.check_spec");// 是否判定
			grandBeforeMapping.put("testflag", "C_PROJ_PARA_A.is_added");// 是否测试
			grandBeforeMapping.put("productstage", "C_PROJ_PARA_A.stage");// 测试温度

		}

		return grandBeforeMapping;
	}

	public Map<String, String> getBodyTaskMapping() {
		if (bodyTaskMapping == null) {
			bodyTaskMapping = new HashMap<String, String>();

			bodyTaskMapping.put("taskcode", "c_proj_task.task_ID");// 任务编号
			bodyTaskMapping.put("testitem", "c_proj_task.task_reported_name");// 测试项目
			bodyTaskMapping.put("pk_testresultname", "c_proj_task.analysis");// 测试结果名称
			bodyTaskMapping.put("runorder", "c_proj_task.order_number");// 顺序
			bodyTaskMapping.put("sampleallocation", "c_proj_task.assigned_sample_display");// 样品分配
			bodyTaskMapping.put("samplequantity", "c_proj_task.assigned_sample_quantity");// 样品数量
		}

		return bodyTaskMapping;
	}

	public Map<String, String> getGrandConditionMapping() {
		if (grandConditionMapping == null) {
			grandConditionMapping = new HashMap<String, String>();

			grandConditionMapping.put("pk_testconditionitem", "result.name");// 测试条件项
			grandConditionMapping.put("conditionstatus", "result.status");// 状态
			grandConditionMapping.put("isoptional", "result.optional");// 是否可选
			grandConditionMapping.put("isallow_out", "result.allow_out");// 是否可报告
			grandConditionMapping.put("textvalue;refvalue;", "result.entry");// 值
			grandConditionMapping.put("unit", "result.units");// 单位
			grandConditionMapping.put("formatted_entry", "result.formatted_entry");// 格式化值
			grandConditionMapping.put("min_limit", "result.min_limit");// 最小值
			grandConditionMapping.put("max_limit", "result.max_limit");// 最大值
		}

		return grandConditionMapping;
	}
	/**
	 * 委托单孙表
	 * @return
	 */
	public Map<String, String> getGrandAfterMapping() {
		if (grandAfterMapping == null) {
			grandAfterMapping = new HashMap<String, String>();

			grandAfterMapping.put("analysisname", "c_proj_task_para_b.analysis");// 实验参数名称
			grandAfterMapping.put("pk_samplegroup", "c_proj_task_para_b.sample_group");// 样品组别
			grandAfterMapping.put("pk_component", "c_proj_task_para_b.component");// 参数项
			grandAfterMapping.put("stdmaxvalue", "c_proj_task_para_b.max_value");// 最大值
			grandAfterMapping.put("stdminvalue", "c_proj_task_para_b.min_value");// 最小值
			grandAfterMapping.put("pk_unit", "c_proj_task_para_b.units");// 单位
			grandAfterMapping.put("judgeflag", "c_proj_task_para_b.check_spec");// 是否判定
			grandAfterMapping.put("testflag", "c_proj_task_para_b.is_added");// 是否测试
			grandAfterMapping.put("pk_testtemperature", "c_proj_task_para_b.stage");// 测试温度

		}

		return grandAfterMapping;
	}
}
