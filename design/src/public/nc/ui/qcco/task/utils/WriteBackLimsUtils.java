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
	//lims����
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

	// ί�е�
	private Map<String, String> headMapping; // qc_commission_h
	private Map<String, String> bodySampleMapping; // qc_commission_b
	private Map<String, String> grandBeforeMapping; // qc_commission_r

	// ����
	private Map<String, String> bodyTaskMapping; // qc_task_b
	private Map<String, String> grandConditionMapping; // qc_task_s
	private Map<String, String> grandAfterMapping; // qc_task_r
	
	//��¼ÿ��vo���ϲ�����
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
	 * ȡί�е�/���񵥻�дLIMS�Ĳ���SQL
	 * 
	 * @param pk_commission_h
	 * @return
	 * @throws BusinessException
	 */
	public String[] getInsertLIMSSQL(String pk_commission_h) throws BusinessException {
		List<String> sqlList = new ArrayList<String>();
		//�������Ӧ��������--����У��
		Map<Class<?>,Integer> class2NumMap = new HashMap<>();
		String[] lists = null;

		//�洢nc�е�pk��limsϵͳ��pk�Ķ�Ӧ��ϵ
		Map<String,Object> ncPK2LimsPkMap = new HashMap<>();
		String headCond = "pk_commission_h = '" + pk_commission_h + "' and dr = 0 ";
		// ί�е���ͷ
		lists = getInsertSQLByMap(getHeadMapping(), CommissionHVO.class, headCond,ncPK2LimsPkMap);
		if(lists==null || lists.length < 1){
			throw new BusinessException("ί�е�������дʧ��!");
		}
		class2NumMap.put(CommissionHVO.class, lists.length-1);
		sqlList.addAll(getHeadInsertSQL(lists));
		
		// ��Ʒ��
		lists = getInsertSQLByMap(getBodySampleMapping(), CommissionBVO.class, headCond,ncPK2LimsPkMap);
		class2NumMap.put(CommissionBVO.class, lists.length-1);
		sqlList.addAll(getSampleInsertSQL(lists));
		
		// ʵ��ǰ����
		String subCondition = "pk_commission_b in (select pk_commission_b from qc_commission_b where " 
				+ headCond + ") and dr = 0 ";
		lists = getInsertSQLByMap(getGrandAfterMapping(), CommissionRVO.class, subCondition,ncPK2LimsPkMap);
		if(lists!=null && class2NumMap.get(CommissionBVO.class) <=0 &&lists.length > 0){
			throw new BusinessException("ί�е����[ʵ��ǰ����]������Ч����,����ϵ���ݹ���Ա.");
		}
		class2NumMap.put(CommissionRVO.class, lists.length-1);
		sqlList.addAll(getBeforeInsertSQL(lists));

		
		// ������
		subCondition = "pk_task_h in (select pk_task_h from qc_task_h where " + headCond + ") and dr = 0 ";
		//
		lists = getInsertSQLByMap(getBodyTaskMapping(), TaskBVO.class, subCondition,ncPK2LimsPkMap);
		class2NumMap.put(TaskBVO.class, lists.length-1);
		sqlList.addAll(getTaskInsertSQL(lists));
	
		
		// ʵ������
		subCondition = "pk_task_b in (select pk_task_b from qc_task_b where "
				+ " pk_task_h in (select pk_task_h from qc_task_h where " + headCond + " ) and dr = 0 ) and dr = 0 ";
		lists = getInsertSQLByMap(getGrandConditionMapping(), TaskSVO.class, subCondition,ncPK2LimsPkMap);
		if(lists!=null && class2NumMap.get(TaskBVO.class) <=0 &&lists.length > 0){
			throw new BusinessException("�������[ʵ������]������Ч����,����ϵ���ݹ���Ա.");
		}
		class2NumMap.put(TaskSVO.class, lists.length-1);
		sqlList.addAll(getConditionInsertSQL(lists));

		
		// ʵ������
		subCondition = "pk_task_b in (select pk_task_b from qc_task_b where "
				+ " pk_task_h in (select pk_task_h from qc_task_h where " + headCond + " ) and dr = 0 ) and dr = 0 ";
		lists = getInsertSQLByMap(getGrandAfterMapping(), TaskRVO.class, subCondition,ncPK2LimsPkMap);
		if(lists!=null && class2NumMap.get(TaskBVO.class) <=0 &&lists.length > 0){
			throw new BusinessException("�������[ʵ������]������Ч����,����ϵ���ݹ���Ա.");
		}
		class2NumMap.put(TaskRVO.class, lists.length-1);
		sqlList.addAll(getAfterInsertSQL(lists));

		
		return sqlList.toArray(new String[0]);
	}

	/**
	 * ʵ������
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
	 * ʵ������
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
	 * ������
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
	 * ʵ��ǰ����
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
	 * ��Ʒ��
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
	 * ί�е���ͷ
	 * @param lists
	 * @param ncPK2LimsPk 
	 * @param fatherPkList 
	 * @param selfPkList 
	 * @return
	 */
	private List<String> getHeadInsertSQL(String[] lists) {
		List<String> rsList = new ArrayList();
		if(lists!=null && lists.length > 1){
			//�� ���ɺ�pk
			
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
	 * ��ȡInsert���Ƭ��
	 * 
	 * @param fieldMap
	 * @param clazz
	 * @param pkname
	 * @param pkvalue
	 * @param ncPK2LimsPkMap ncpk��limsϵͳpk��Ӧ��ϵ
	 * @return fieldValues[0]: �ֶ���Ƭ��<br />
	 *         fieldValues[1-n]��ֵƬ��
	 * @throws BusinessException
	 */
	private String[] getInsertSQLByMap(Map<String, String> fieldMap, Class<?> clazz, 
			String condition,Map<String,Object> ncPK2LimsPkMap)
			throws BusinessException {
		
		//����ĳ���ֶε�ֵ,һ�����ݶ�Ӧarray�е�һ��
		StringBuilder[] fieldValues = null;
		IUAPQueryBS query = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		List<?> srcData = (List<?>)query.retrieveByClause(clazz, condition);
		List<Integer> pkList = null;
		if(CommissionHVO.class!=clazz){
			//Ԥ����pk-����ί�е�֮��(ί�е���������billNo)
			pkList = getPrePk(clazz,srcData.size());
		}
		
		fieldValues = new StringBuilder[srcData.size() + 1];
		//����ƴ���ֶ���
		StringBuilder insertFields = new StringBuilder();
		//������ѭ��
		for (Entry<String, String> map : fieldMap.entrySet()) {
			String fieldName = map.getKey();

			String[] fields = null;
			if (map.getValue().contains(";")) {
				fields = getWriteBackFields(map.getValue().split(";"));
			} else {
				fields = getWriteBackFields(new String[] { map.getValue() });
			}
			//times�Ǵ���һ�Զ��ϵ��
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
					//д������(ֻ֧�ֵ�����,Ҫ������,��Ҫ�ο�������ֶ�����߼�)
					if(0==i){
						fieldValues[i].append(getWriteBackFields(new String[] {LIMS_PK_MAP.get(clazz)})[0]);
					}else{
						fieldValues[i].append(pkList.get(i-1));
						//��¼����
						ncPK2LimsPkMap.put(((ISuperVO)srcData.get(i-1)).getPrimaryKey(), pkList.get(i-1));
					}
					
				}else{
					//ί�е���������������billno
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
	 * ����ʵ���ʵ����������lims��pk
	 * @param clazz
	 * @param size
	 * @return
	 * @throws DAOException 
	 */
	private List<Integer> getPrePk(Class<?> clazz, int size) throws DAOException {
		List<Integer> rs = new ArrayList<>();
		//��ȡ��
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

			headMapping.put("pk_commissiontype", "project.template");// ί�е�����
			headMapping.put("billno", "project.name");// ί�е����
			headMapping.put("billname", "project.title");// ί�е�����
			headMapping.put("pk_owner", "project.customer");// ί�е�λ
			headMapping.put("pk_dept", "project.c_user_department");// ����
			headMapping.put("pk_payer", "project.t_source_customer");// ���ѵ�λ
			headMapping.put("contract", "project.customer_contact");// ��ϵ��
			headMapping.put("cuserid", "project.owner;project.created_by;");// ������
			headMapping.put("email", "project.c_email_address");// Email
			headMapping.put("teleno", "project.c_phone_number");// ��ϵ�绰
			headMapping.put("pk_maincategory", "project.c_product_type");// ��Ʒ����
			headMapping.put("pk_subcategory", "project.c_prod_type_c1");// ��������
			headMapping.put("pk_lastcategory", "project.c_prod_type_c2");// ��������
			headMapping.put("reportformat", "project.c_coa_format");// �����ʽ
			headMapping.put("reportlang", "project.c_coa_language");// ��������
			headMapping.put("managersendflag", "project.c_mail_lab_approval");// ʵ����������˷����ʼ�
			headMapping.put("taskbeginsendflag", "project.c_mail_task_end");// ����ʼ�����ʼ�
			headMapping.put("taskendsendflag", "project.c_mail_task_start");// ������������ʼ�
			headMapping.put("reportsendflag", "project.c_mail_coa_sign");// ����ǩ�������ʼ�
			headMapping.put("savetotemplateflag", "project.c_is_template");// �Ƿ񱣴�Ϊģ��
			headMapping.put("receiptsendflag", "project.c_mail_charge");// �Ʒѵ����͸��ͻ��ʼ�����
			headMapping.put("quotaionsendflag", "project.c_mail_quotes");// ���۵����͸��ͻ��ʼ�����
			headMapping.put("testaim", "project.c_test_purpose");// ����Ŀ��
			headMapping.put("progressneed", "project.description");// ����Ҫ��
			headMapping.put("sampledealtype", "project.c_retain_handle");// �����Ʒ����
			headMapping.put("productproperty", "project.c_product_property");// ��Ʒ����
			headMapping.put("customername", "project.c_terminal_client");// �ͻ�����
			headMapping.put("customertype", "project.c_client_type");// �ͻ�����
			headMapping.put("testrequirement", "project.c_product_requirement");// ��������
			headMapping.put("checkingproperty", "project.c_checking_property");// �������
			headMapping.put("productline", "project.c_product_line");// ��������
			headMapping.put("batchnumber", "project.c_batch_number");// ��������
			headMapping.put("productdate", "project.c_product_date");// ��������
			headMapping.put("batchserial", "project.c_batch_serial");// ��������
			headMapping.put("identificationtype", "project.c_identification_type");// ��Ʒ��������
			headMapping.put("certificationtype", "project.c_certification_type");// ��֤����
			headMapping.put("itemnumber", "project.c_item_number");// ��Ŀ��
			headMapping.put("modifiedtime", "project.date_createdproject.date_updated");// �Ƶ�ʱ��
		}

		return headMapping;
	}
/**
 * ί�е��ӱ�
 * @return
 */
	public Map<String, String> getBodySampleMapping() {
		if (bodySampleMapping == null) {
			bodySampleMapping = new HashMap<String, String>();

			bodySampleMapping.put("pk_productserial", "C_PROJ_LOGIN_SAMPLE.product_series");// ��Ʒϵ��
			bodySampleMapping.put("pk_enterprisestandard",
					"C_PROJ_LOGIN_SAMPLE.product_standard;c_proj_para_a.product_standard;");// ��ҵ��׼
			bodySampleMapping.put("typeno", "C_PROJ_LOGIN_SAMPLE.prodname;");// ����ͺ�
			bodySampleMapping.put("pk_productspec",
					"C_PROJ_LOGIN_SAMPLE.production_spec;c_proj_para_a.production_spec;");// ����
			bodySampleMapping.put("pk_structuretype",
					"C_PROJ_LOGIN_SAMPLE.structure_type;C_PROJ_PARA_A.structure_type;");// �ṹ����
			bodySampleMapping.put("contacttype", "C_PROJ_LOGIN_SAMPLE.contact_type");// ��������
			bodySampleMapping.put("quantity", "C_PROJ_LOGIN_SAMPLE.sample_quantity");// ��Ʒ����
			bodySampleMapping.put("manufacturer", "C_PROJ_LOGIN_SAMPLE.manufacturer");// ������
			bodySampleMapping.put("pk_contactbrand", "C_PROJ_LOGIN_SAMPLE.contact_brand");// �����ƺ�
			bodySampleMapping.put("contactmodel", "C_PROJ_LOGIN_SAMPLE.contact_model");// �����ͺ�
			bodySampleMapping.put("productstage", "C_PROJ_LOGIN_SAMPLE.product_stage;C_PROJ_PARA_A.stage;");// �¶�
			bodySampleMapping.put("pk_samplegroup", "C_PROJ_LOGIN_SAMPLE.sample_group");// ��Ʒ���
			bodySampleMapping.put("otherinfo", "C_PROJ_LOGIN_SAMPLE.other_req");// ������Ϣ
		}

		return bodySampleMapping;
	}

	public Map<String, String> getGrandBeforeMapping() {
		if (grandBeforeMapping == null) {
			grandBeforeMapping = new HashMap<String, String>();

			grandBeforeMapping.put("analysisname", "C_PROJ_PARA_A.analysis");// ʵ��ǰ��������
			grandBeforeMapping.put("pk_samplegroup", "C_PROJ_PARA_A.sample_group");// ��Ʒ���
			grandBeforeMapping.put("pk_component", "C_PROJ_PARA_A.component");// ������
			grandBeforeMapping.put("stdmaxvalue", "C_PROJ_PARA_A.max_value");// ���ֵ
			grandBeforeMapping.put("stdminvalue", "C_PROJ_PARA_A.min_value");// ��Сֵ
			grandBeforeMapping.put("unitname", "C_PROJ_PARA_A.units");// ��λ
			grandBeforeMapping.put("judgeflag", "C_PROJ_PARA_A.check_spec");// �Ƿ��ж�
			grandBeforeMapping.put("testflag", "C_PROJ_PARA_A.is_added");// �Ƿ����
			grandBeforeMapping.put("productstage", "C_PROJ_PARA_A.stage");// �����¶�

		}

		return grandBeforeMapping;
	}

	public Map<String, String> getBodyTaskMapping() {
		if (bodyTaskMapping == null) {
			bodyTaskMapping = new HashMap<String, String>();

			bodyTaskMapping.put("taskcode", "c_proj_task.task_ID");// ������
			bodyTaskMapping.put("testitem", "c_proj_task.task_reported_name");// ������Ŀ
			bodyTaskMapping.put("pk_testresultname", "c_proj_task.analysis");// ���Խ������
			bodyTaskMapping.put("runorder", "c_proj_task.order_number");// ˳��
			bodyTaskMapping.put("sampleallocation", "c_proj_task.assigned_sample_display");// ��Ʒ����
			bodyTaskMapping.put("samplequantity", "c_proj_task.assigned_sample_quantity");// ��Ʒ����
		}

		return bodyTaskMapping;
	}

	public Map<String, String> getGrandConditionMapping() {
		if (grandConditionMapping == null) {
			grandConditionMapping = new HashMap<String, String>();

			grandConditionMapping.put("pk_testconditionitem", "result.name");// ����������
			grandConditionMapping.put("conditionstatus", "result.status");// ״̬
			grandConditionMapping.put("isoptional", "result.optional");// �Ƿ��ѡ
			grandConditionMapping.put("isallow_out", "result.allow_out");// �Ƿ�ɱ���
			grandConditionMapping.put("textvalue;refvalue;", "result.entry");// ֵ
			grandConditionMapping.put("unit", "result.units");// ��λ
			grandConditionMapping.put("formatted_entry", "result.formatted_entry");// ��ʽ��ֵ
			grandConditionMapping.put("min_limit", "result.min_limit");// ��Сֵ
			grandConditionMapping.put("max_limit", "result.max_limit");// ���ֵ
		}

		return grandConditionMapping;
	}
	/**
	 * ί�е����
	 * @return
	 */
	public Map<String, String> getGrandAfterMapping() {
		if (grandAfterMapping == null) {
			grandAfterMapping = new HashMap<String, String>();

			grandAfterMapping.put("analysisname", "c_proj_task_para_b.analysis");// ʵ���������
			grandAfterMapping.put("pk_samplegroup", "c_proj_task_para_b.sample_group");// ��Ʒ���
			grandAfterMapping.put("pk_component", "c_proj_task_para_b.component");// ������
			grandAfterMapping.put("stdmaxvalue", "c_proj_task_para_b.max_value");// ���ֵ
			grandAfterMapping.put("stdminvalue", "c_proj_task_para_b.min_value");// ��Сֵ
			grandAfterMapping.put("pk_unit", "c_proj_task_para_b.units");// ��λ
			grandAfterMapping.put("judgeflag", "c_proj_task_para_b.check_spec");// �Ƿ��ж�
			grandAfterMapping.put("testflag", "c_proj_task_para_b.is_added");// �Ƿ����
			grandAfterMapping.put("pk_testtemperature", "c_proj_task_para_b.stage");// �����¶�

		}

		return grandAfterMapping;
	}
}
