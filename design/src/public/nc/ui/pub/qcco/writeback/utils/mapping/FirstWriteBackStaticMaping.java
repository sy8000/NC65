package nc.ui.pub.qcco.writeback.utils.mapping;

import java.util.HashMap;
import java.util.Map;

/**
 * ��һ�λ�д�Ĺ̶��ֶ�
 * ��һ�λ�д NC-LIMS maping
 * @author 91967
 *
 */
public class FirstWriteBackStaticMaping {
	
	/**
	 * ί�е�����ӳ���ֶ�
	 */
    public static Map<String, String> HEAD_MAPPING; // qc_commission_h
    {
    	HEAD_MAPPING = new HashMap<String, String>();
    	HEAD_MAPPING.put("pk_commissiontype", "project.C_APPLY_TYPE;project.template");// ί�е�����
    	HEAD_MAPPING.put("billno", "project.name");// ί�е����
		HEAD_MAPPING.put("billname", "project.title");// ί�е�����
		HEAD_MAPPING.put("pk_owner", "project.customer");// ί�е�λ
		HEAD_MAPPING.put("pk_dept", "project.c_user_department");// ����
		HEAD_MAPPING.put("pk_payer", "project.t_source_customer");// ���ѵ�λ
		HEAD_MAPPING.put("contract", "project.customer_contact");// ��ϵ��
		HEAD_MAPPING.put("cuserid", "project.owner;project.created_by;");// ������
		HEAD_MAPPING.put("email", "project.c_email_address");// Email
		HEAD_MAPPING.put("teleno", "project.c_phone_number");// ��ϵ�绰
		HEAD_MAPPING.put("pk_maincategory", "project.c_product_type");// ��Ʒ����
		HEAD_MAPPING.put("pk_subcategory", "project.c_prod_type_c1");// ��������
		HEAD_MAPPING.put("pk_lastcategory", "project.c_prod_type_c2");// ��������
		HEAD_MAPPING.put("reportformat", "project.c_coa_format");// �����ʽ
		HEAD_MAPPING.put("reportlang", "project.c_coa_language");// ��������
		HEAD_MAPPING.put("taskbeginsendflag", "project.c_mail_task_end");// ����ʼ�����ʼ�
		HEAD_MAPPING.put("taskendsendflag", "project.c_mail_task_start");// ������������ʼ�
		HEAD_MAPPING.put("reportsendflag", "project.c_mail_coa_sign");// ����ǩ�������ʼ�
		HEAD_MAPPING.put("savetotemplateflag", "project.c_is_template");// �Ƿ񱣴�Ϊģ��
		HEAD_MAPPING.put("receiptsendflag", "project.c_mail_charge");// �Ʒѵ����͸��ͻ��ʼ�����
		HEAD_MAPPING.put("quotaionsendflag", "project.c_mail_quotes");// ���۵����͸��ͻ��ʼ�����
		HEAD_MAPPING.put("testaim", "project.c_test_purpose");// ����Ŀ��
		HEAD_MAPPING.put("progressneed", "project.description");// ����Ҫ��
		HEAD_MAPPING.put("sampledealtype", "project.c_retain_handle");// �����Ʒ����
		HEAD_MAPPING.put("productproperty", "project.c_product_property");// ��Ʒ����
		HEAD_MAPPING.put("customername", "project.c_terminal_client");// �ͻ�����
		HEAD_MAPPING.put("customertype", "project.c_client_type");// �ͻ�����
		HEAD_MAPPING.put("testrequirement", "project.c_product_requirement");// ��������
		HEAD_MAPPING.put("checkingproperty", "project.c_checking_property");// �������
		HEAD_MAPPING.put("productline", "project.c_product_line");// ��������
		HEAD_MAPPING.put("batchnumber", "project.c_batch_number");// ��������
		HEAD_MAPPING.put("productdate", "project.c_product_date");// ��������
		HEAD_MAPPING.put("batchserial", "project.c_batch_serial");// ��������
		HEAD_MAPPING.put("identificationtype", "project.c_identification_type");// ��Ʒ��������
		HEAD_MAPPING.put("certificationtype", "project.c_certification_type");// ��֤����
		HEAD_MAPPING.put("itemnumber", "project.c_item_number");// ��Ŀ��
		HEAD_MAPPING.put("creationtime", "project.date_created");// �Ƶ�ʱ��
		HEAD_MAPPING.put("modifiedtime", "project.date_updated");// �Ƶ�ʱ��
	}
    
    /**
     *  ί�е��ӱ� ӳ���ֶ�
     */
    public static Map<String, String> BODY_SAMPLE_MAPPING; // qc_commission_b
    {
    	BODY_SAMPLE_MAPPING = new HashMap<String, String>();

        BODY_SAMPLE_MAPPING.put("pk_productserial", "C_PROJ_LOGIN_SAMPLE.product_series");// ��Ʒϵ��
        BODY_SAMPLE_MAPPING.put("pk_enterprisestandard", "C_PROJ_LOGIN_SAMPLE.product_standard");// ��ҵ��׼
        BODY_SAMPLE_MAPPING.put("typeno", "C_PROJ_LOGIN_SAMPLE.prodname");// ����ͺ�
        BODY_SAMPLE_MAPPING.put("pk_productspec", "C_PROJ_LOGIN_SAMPLE.production_spec");// ����
        BODY_SAMPLE_MAPPING.put("pk_structuretype", "C_PROJ_LOGIN_SAMPLE.structure_type");// �ṹ����
        BODY_SAMPLE_MAPPING.put("contacttype", "C_PROJ_LOGIN_SAMPLE.contact_type");// ��������
        BODY_SAMPLE_MAPPING.put("quantity", "C_PROJ_LOGIN_SAMPLE.sample_quantity");// ��Ʒ����
        BODY_SAMPLE_MAPPING.put("manufacturer", "C_PROJ_LOGIN_SAMPLE.manufacturer");// ������
        BODY_SAMPLE_MAPPING.put("pk_contactbrand", "C_PROJ_LOGIN_SAMPLE.contact_brand");// �����ƺ�
        BODY_SAMPLE_MAPPING.put("contactmodel", "C_PROJ_LOGIN_SAMPLE.contact_model");// �����ͺ�
        BODY_SAMPLE_MAPPING.put("productstage", "C_PROJ_LOGIN_SAMPLE.product_stage");// �¶�
        BODY_SAMPLE_MAPPING.put("pk_samplegroup", "C_PROJ_LOGIN_SAMPLE.sample_group");// ��Ʒ���
        BODY_SAMPLE_MAPPING.put("otherinfo", "C_PROJ_LOGIN_SAMPLE.other_req");// ������Ϣ
    }

    /**
     * ί�е��ӱ�-�����дSample��
     *
     * @return
     */
    public static Map<String, String> BODY_SAMPLE_EXTEND_MAPPING;//qc_commission_b ����Ļ�д
    {
    	BODY_SAMPLE_EXTEND_MAPPING = new HashMap<String, String>();

        BODY_SAMPLE_EXTEND_MAPPING.put("pk_productserial", "Sample.PRODUCT");// ��Ʒϵ��
        BODY_SAMPLE_EXTEND_MAPPING.put("pk_structuretype", "Sample.PRODUCT_GRADE");// �ṹ����
    }

    /**
     * ί�е��ӱ�->���Ļ�д�ֶ�
     *
     * @return
     */
    public static Map<String, String> BODY_SIMPLE_2_CHILDREN_MAPPING;
    {
		BODY_SIMPLE_2_CHILDREN_MAPPING = new HashMap<String, String>();
		BODY_SIMPLE_2_CHILDREN_MAPPING.put("c_proj_para_a.product_standard", "pk_enterprisestandard");// ��ҵ��׼
		BODY_SIMPLE_2_CHILDREN_MAPPING.put("c_proj_para_a.production_spec", "pk_productspec");// ����
		BODY_SIMPLE_2_CHILDREN_MAPPING.put("C_PROJ_PARA_A.structure_type", "pk_structuretype");// �ṹ����
		//BODY_SIMPLE_2_CHILDREN_MAPPING.put("C_PROJ_PARA_A.stage", "productstage");// �¶�

		BODY_SIMPLE_2_CHILDREN_MAPPING.put("c_proj_para_a.prodname", "typeno");// ����ͺ�
		BODY_SIMPLE_2_CHILDREN_MAPPING.put("c_proj_para_a.contact_type", "contacttype");// �������� // contacttype
    }

    /**
     * ʵ��ǰ����
     */
    public static Map<String, String> GRAND_BEFORE_MAPPING; // qc_commission_r
	{
		GRAND_BEFORE_MAPPING = new HashMap<String, String>();

		GRAND_BEFORE_MAPPING.put("analysisname", "C_PROJ_PARA_A.analysis");// ʵ��ǰ��������
		GRAND_BEFORE_MAPPING.put("pk_samplegroup", "C_PROJ_PARA_A.sample_group");// ��Ʒ���
		GRAND_BEFORE_MAPPING.put("pk_component", "C_PROJ_PARA_A.component");// ������
		GRAND_BEFORE_MAPPING.put("stdmaxvalue", "C_PROJ_PARA_A.max_value");// ���ֵ
		GRAND_BEFORE_MAPPING.put("stdminvalue", "C_PROJ_PARA_A.min_value");// ��Сֵ
		GRAND_BEFORE_MAPPING.put("unitname", "C_PROJ_PARA_A.units");// ��λ
		GRAND_BEFORE_MAPPING.put("judgeflag", "C_PROJ_PARA_A.check_spec");// �Ƿ��ж�
		GRAND_BEFORE_MAPPING.put("testflag", "C_PROJ_PARA_A.is_added");// �Ƿ����
		GRAND_BEFORE_MAPPING.put("rowno", "C_PROJ_PARA_A.order_number");// ���
		GRAND_BEFORE_MAPPING.put("productstage", "C_PROJ_PARA_A.stage");// �¶�
	}
    
    /**
     * �����ӱ�
     */
	public static Map<String, String> BODY_TASK_MAPPING; // qc_task_b
    {
    	BODY_TASK_MAPPING = new HashMap<String, String>();
        BODY_TASK_MAPPING.put("taskcode", "c_proj_task.task_ID");// ������
        BODY_TASK_MAPPING.put("testitem", "c_proj_task.task_reported_name");// ������Ŀ
        BODY_TASK_MAPPING.put("pk_testresultname", "c_proj_task.analysis");// ���Խ������
        BODY_TASK_MAPPING.put("runorder", "c_proj_task.order_number");// ˳��
        //BODY_TASK_MAPPING.put("sampleallocation", "c_proj_task.assigned_sample_display");// ��Ʒ���� ��д��ʱ���ܿ���,��Ҫ��������
        BODY_TASK_MAPPING.put("sampleallocationsource", "c_proj_task.assigned_sample");// ��Ʒ����
        BODY_TASK_MAPPING.put("samplequantity", "c_proj_task.assigned_sample_quantity");// ��Ʒ����
    }
    
    /**
     * ������� ��������
     */
    public static Map<String, String> GRAND_CONDITION_MAPPING; // qc_task_s
    {
    	GRAND_CONDITION_MAPPING = new HashMap<String, String>();

        GRAND_CONDITION_MAPPING.put("pk_testconditionitem", "result.name");// ����������
        GRAND_CONDITION_MAPPING.put("conditionstatus", "result.status");// ״̬
        GRAND_CONDITION_MAPPING.put("isoptional", "result.optional");// �Ƿ��ѡ
        GRAND_CONDITION_MAPPING.put("isallow_out", "result.allow_out");// �Ƿ�ɱ���
        //grandConditionMapping.put("textvalue", "result.entry");// ֵ //TODO
        // ����յ�����,�ݻ�,��Ϊ������ֵ��Ҫ�ϲ�
        GRAND_CONDITION_MAPPING.put("refvalue;", "result.entry");// ֵ
        GRAND_CONDITION_MAPPING.put("unit", "result.units");// ��λ
        GRAND_CONDITION_MAPPING.put("formatted_entry", "result.formatted_entry");// ��ʽ��ֵ
        GRAND_CONDITION_MAPPING.put("min_limit", "result.min_limit");// ��Сֵ
        GRAND_CONDITION_MAPPING.put("max_limit", "result.max_limit");// ���ֵ
    }

    /**
     * ������� ��������
     *
     * @return
     */
    public static Map<String, String> GRAND_AFTER_MAPPING; // qc_task_r
    {
    	GRAND_AFTER_MAPPING = new HashMap<String, String>();
		GRAND_AFTER_MAPPING.put("analysisname", "c_proj_task_para_b.analysis");// ʵ���������
		GRAND_AFTER_MAPPING.put("pk_samplegroup", "c_proj_task_para_b.sample_group");// ��Ʒ���
		GRAND_AFTER_MAPPING.put("pk_component", "c_proj_task_para_b.component");// ������
		GRAND_AFTER_MAPPING.put("stdmaxvalue", "c_proj_task_para_b.max_value");// ���ֵ
		GRAND_AFTER_MAPPING.put("stdminvalue", "c_proj_task_para_b.min_value");// ��Сֵ
		GRAND_AFTER_MAPPING.put("pk_unit", "c_proj_task_para_b.units");// ��λ
		GRAND_AFTER_MAPPING.put("judgeflag", "c_proj_task_para_b.check_spec");// �Ƿ��ж�
		GRAND_AFTER_MAPPING.put("testflag", "c_proj_task_para_b.is_added");// �Ƿ����
		GRAND_AFTER_MAPPING.put("pk_testtemperature", "c_proj_task_para_b.stage");// �����¶�
    }


	/**
     * ί�е�����̶�ֵ��д
     */
    public static Map<String,String> COMMISSION_HEARD_STATIC_MAP = new HashMap<>();
    {
    	COMMISSION_HEARD_STATIC_MAP.put("C_MAIL_LAB_APPROVAL","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("TEMPLATE_VERSION","1");
    	COMMISSION_HEARD_STATIC_MAP.put("STATUS","'U'");
    	COMMISSION_HEARD_STATIC_MAP.put("OLD_STATUS","'I'");
    	COMMISSION_HEARD_STATIC_MAP.put("COST_FACTOR","0.0000");
    	COMMISSION_HEARD_STATIC_MAP.put("GROUP_NAME","'DEFAULT'");
    	COMMISSION_HEARD_STATIC_MAP.put("CLOSED","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("SAMPLE_TEMPLATE","'HF-MAIN'");
    	COMMISSION_HEARD_STATIC_MAP.put("STABILITY","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("USE_GROUP_LOGIN","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("USE_GRID_LOGIN","'T'");
    	COMMISSION_HEARD_STATIC_MAP.put("ALIQUOT","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("SIGNED","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("T_COA_TEMPLATE","'HF_COA_DEFAULT'");
    	COMMISSION_HEARD_STATIC_MAP.put("T_INVOICE_NUMBER","0");
    	COMMISSION_HEARD_STATIC_MAP.put("T_LOGIN_VERIF_REQD","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("T_LOGIN_VERIFIED","'T'");
    	COMMISSION_HEARD_STATIC_MAP.put("T_PRE_INVOICE_NUMBER","0");
    	COMMISSION_HEARD_STATIC_MAP.put("APPROVED","'T'");
    	COMMISSION_HEARD_STATIC_MAP.put("APPROVAL_GROUP","'PROJECT'");
    	COMMISSION_HEARD_STATIC_MAP.put("READY_FOR_APPROVAL","'T'");
		//COMMISSION_HEARD_STATIC_MAP.put("APPROVAL_ID", "0");
		COMMISSION_HEARD_STATIC_MAP.put("NUM_U", "0");
		COMMISSION_HEARD_STATIC_MAP.put("NUM_I", "0");
		COMMISSION_HEARD_STATIC_MAP.put("NUM_P", "0");
		COMMISSION_HEARD_STATIC_MAP.put("NUM_C", "0");
		COMMISSION_HEARD_STATIC_MAP.put("NUM_A", "0");
		COMMISSION_HEARD_STATIC_MAP.put("NUM_R", "0");
		COMMISSION_HEARD_STATIC_MAP.put("NUM_X", "0");
    	COMMISSION_HEARD_STATIC_MAP.put("APPROVAL_ROUTING","'HF'");
    	COMMISSION_HEARD_STATIC_MAP.put("C_QUOTES_CREATED","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("C_QUOTES_VERIFYED","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("C_PRIORITY_LEVEL","3");
    	COMMISSION_HEARD_STATIC_MAP.put("C_TOTAL_CHARGE","0.00");
    	COMMISSION_HEARD_STATIC_MAP.put("HAS_ANSWER_SET","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("C_RPT_AUTHORIZED","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("C_INVOICE_CREATED","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("C_INVOICE_VERIFYED","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("C_RPT_REPORT_NUMBER","0");
    	COMMISSION_HEARD_STATIC_MAP.put("C_ALLTASK_COA_AUTHORIZED","'F'");
    	COMMISSION_HEARD_STATIC_MAP.put("C_RPT_CNAS_LOGO","'T'");
    	COMMISSION_HEARD_STATIC_MAP.put("C_NEED_MESSAGE","'F'");
    	
    }

    /**
     * ί�е�����̶�ֵ��д
     */
    public static Map<String,String> COMMISSION_BODY_STATIC_MAP = new HashMap<>();
    {
    	COMMISSION_BODY_STATIC_MAP.put("NEED_LIST","'F'");
    	COMMISSION_BODY_STATIC_MAP.put("HAS_LOGINED_SAMPLE","'F'");
    	
    }
    
    /**
     * ���񵥱���̶�ֵ��д
     */
    public static Map<String,String> TASK_BODY_STATIC_MAP = new HashMap<>();
    {
    	TASK_BODY_STATIC_MAP.put("ACTUAL_BASE_FEE", "0");
    	TASK_BODY_STATIC_MAP.put("ACTUAL_SURCHARGE", "0");
    	TASK_BODY_STATIC_MAP.put("ACTUAL_TEST_FEE", "0");
    	TASK_BODY_STATIC_MAP.put("ACTUAL_TEST_QTY", "0");
    	TASK_BODY_STATIC_MAP.put("ACTUAL_TEST_TIME", "0");
    	TASK_BODY_STATIC_MAP.put("ACTUAL_TOTAL_COST", "0");
    	TASK_BODY_STATIC_MAP.put("ADDITIONAL_WORK_HOURS", "0");
    	TASK_BODY_STATIC_MAP.put("ANALYSIS_VERSION", "0");
    	TASK_BODY_STATIC_MAP.put("BASE_FEE", "0");
    	TASK_BODY_STATIC_MAP.put("IF_PASS", "'F'");
    	TASK_BODY_STATIC_MAP.put("IS_ENTERED", "'T'");
    	TASK_BODY_STATIC_MAP.put("IS_RECHECK", "'F'");	
    	TASK_BODY_STATIC_MAP.put("QUOTES", "0");
    	TASK_BODY_STATIC_MAP.put("READY_FOR_DRAFT", "'F'");
    	TASK_BODY_STATIC_MAP.put("READY_FOR_REVIEW", "'F'");
    	TASK_BODY_STATIC_MAP.put("REDATE", "0");
    	TASK_BODY_STATIC_MAP.put("REPORT_NUMBER", "0");
    	TASK_BODY_STATIC_MAP.put("RPT_AUTHORIZED", "'F'");
		TASK_BODY_STATIC_MAP.put("STATUS", "'T'");
		TASK_BODY_STATIC_MAP.put("SURCHARGE", "0");
		TASK_BODY_STATIC_MAP.put("TEST_FEE", "0");
		TASK_BODY_STATIC_MAP.put("TEST_QUANTITY", "0");
    }
    
    /**
     * ���������������̶�ֵ��д
     */
    public static Map<String,String> TASK_CONDITION_STATIC_MAP = new HashMap<>();
    {
    	TASK_CONDITION_STATIC_MAP.put("INSTRUMENT","null");
    	//TASK_CONDITION_STATIC_MAP.put("RESULT_NUMBER","0.00"); ��Ϊ������ô����0.0?
    	TASK_CONDITION_STATIC_MAP.put("C_CERTIFICATIONS","null");
    	TASK_CONDITION_STATIC_MAP.put("USES_CODES","'F'");
    	TASK_CONDITION_STATIC_MAP.put("AUTO_CALC","'T'");
    	TASK_CONDITION_STATIC_MAP.put("ALLOW_CANCEL","'F'");
    	TASK_CONDITION_STATIC_MAP.put("IN_SPEC","'T'");
    	TASK_CONDITION_STATIC_MAP.put("CODE_ENTERED","'F'");
    	TASK_CONDITION_STATIC_MAP.put("DATE_REVIEWED","null");
    	TASK_CONDITION_STATIC_MAP.put("REVIEWER","null");
    	TASK_CONDITION_STATIC_MAP.put("STD_REAG_SAMPLE","0.00");
    	TASK_CONDITION_STATIC_MAP.put("DISPLAYED","'T'");
    	TASK_CONDITION_STATIC_MAP.put("ENTRY_QUALIFIER","null");
    	TASK_CONDITION_STATIC_MAP.put("USES_INSTRUMENT","'F'");
    	TASK_CONDITION_STATIC_MAP.put("IN_CAL","'T'");
    	TASK_CONDITION_STATIC_MAP.put("LINK_SIZE","0");
    	TASK_CONDITION_STATIC_MAP.put("LINK_DATE","null");
    	TASK_CONDITION_STATIC_MAP.put("FACTOR_VALUE","0");
    	TASK_CONDITION_STATIC_MAP.put("FACTOR_OPERATOR","null");
    	//TASK_CONDITION_STATIC_MAP.put("MIN_LIMIT","null");
    	//TASK_CONDITION_STATIC_MAP.put("MAX_LIMIT","null");
    	TASK_CONDITION_STATIC_MAP.put("ALIAS_NAME","null");
    	TASK_CONDITION_STATIC_MAP.put("CONTROL_1","null");
    	TASK_CONDITION_STATIC_MAP.put("CONTROL_2","null");
    	TASK_CONDITION_STATIC_MAP.put("IN_CONTROL","'T'");
    	TASK_CONDITION_STATIC_MAP.put("PRIMARY_IN_SPEC","'T'");
    	TASK_CONDITION_STATIC_MAP.put("SPEC_OVERRIDE","'F'");
    	TASK_CONDITION_STATIC_MAP.put("BATCH","null");
    	TASK_CONDITION_STATIC_MAP.put("DOUBLE_ENTRY_CHK","null");
    	TASK_CONDITION_STATIC_MAP.put("FIRST_ENTRY","null");
    	TASK_CONDITION_STATIC_MAP.put("FIRST_ENTRY_BY","null");
    	TASK_CONDITION_STATIC_MAP.put("HI_CONTROL","null");
    	TASK_CONDITION_STATIC_MAP.put("LO_CONTROL","null");
    	TASK_CONDITION_STATIC_MAP.put("TEXT_LIMIT","null");
    	TASK_CONDITION_STATIC_MAP.put("ATTRIBUTE_2","null");
    	TASK_CONDITION_STATIC_MAP.put("ATTRIBUTE_3","null");
    	TASK_CONDITION_STATIC_MAP.put("ATTRIBUTE_4","null");
    	TASK_CONDITION_STATIC_MAP.put("CHART_COMMENT","null");
    	TASK_CONDITION_STATIC_MAP.put("T_ACCREDITED_ID","null");
    	TASK_CONDITION_STATIC_MAP.put("TRANS_NUM","0");
    }
	
    /**
     * sample�����һ�λ�д�̶�ֵ
     */
    public static Map<String,String> SAMPLE_STATIC_MAP = new HashMap<>();
    {
    	SAMPLE_STATIC_MAP.put("ALIQUOT", "'F'");
    	SAMPLE_STATIC_MAP.put("ALIQUOT_TEMPLATE", "'ALIQUOT'");
    	SAMPLE_STATIC_MAP.put("ALLOW_CHLD_ALQTS", "'F'");
    	SAMPLE_STATIC_MAP.put("APPROVAL_ID", "0.00");
    	SAMPLE_STATIC_MAP.put("APPROVED", "'F'");
    	SAMPLE_STATIC_MAP.put("\"AUDIT\"", "'F'");
    	SAMPLE_STATIC_MAP.put("C_IS_SEQUNCE", "'F'");
    	SAMPLE_STATIC_MAP.put("CHK_ALIQUOT_SPECS", "'F'");
    	SAMPLE_STATIC_MAP.put("CHK_ALIQUOT_STATUS", "'F'");
    	SAMPLE_STATIC_MAP.put("CLONED_FROM", "0.00");
    	SAMPLE_STATIC_MAP.put("COLLECTION_OFFSET", "0.00");
    	SAMPLE_STATIC_MAP.put("COMPOSITE", "'F'");
    	SAMPLE_STATIC_MAP.put("CONTRACT_NUMBER", "0.00");
    	SAMPLE_STATIC_MAP.put("HAS_FLAGS", "'F'");
    	SAMPLE_STATIC_MAP.put("IN_CAL", "'T'");
    	SAMPLE_STATIC_MAP.put("IN_CONTROL", "'T'");
    	SAMPLE_STATIC_MAP.put("IN_SPEC", "'T'");
    	SAMPLE_STATIC_MAP.put("INVESTIGATED", "'F'");
    	SAMPLE_STATIC_MAP.put("LOT", "0.00");
    	SAMPLE_STATIC_MAP.put("MODIFIED_RESULTS", "'F'");
    	SAMPLE_STATIC_MAP.put("NUM_CONTAINERS", "1.00");
    	//SAMPLE_STATIC_MAP.put("OLD_STATUS", "'I'");
    	SAMPLE_STATIC_MAP.put("PARENT_ALIQUOT", "0.00");
    	SAMPLE_STATIC_MAP.put("PARENT_COMPOSITE", "0.00");
    	SAMPLE_STATIC_MAP.put("PARENT_SAMPLE", "0.00");
    	SAMPLE_STATIC_MAP.put("PARTIAL_SPEC", "'F'");
    	SAMPLE_STATIC_MAP.put("PEOPLE", "0.00");
    	SAMPLE_STATIC_MAP.put("PREP", "'F'");
    	SAMPLE_STATIC_MAP.put("PRIMARY_IN_SPEC", "'T'");
    	SAMPLE_STATIC_MAP.put("PRIORITY", "0.00");
    	SAMPLE_STATIC_MAP.put("PRODUCT_VERSION", "0.00");
    	SAMPLE_STATIC_MAP.put("RE_SAMPLE", "'F'");
    	SAMPLE_STATIC_MAP.put("READY_FOR_APPROVAL", "'F'");
    	SAMPLE_STATIC_MAP.put("RELEASED", "'F'");
    	SAMPLE_STATIC_MAP.put("REPORT_NUMBER", "0.00");
    	SAMPLE_STATIC_MAP.put("REPORTED_RSLT_OOS", "'F'");
    	SAMPLE_STATIC_MAP.put("REQD_VOLUME", "0.00");
    	SAMPLE_STATIC_MAP.put("SAMPLE_EVENT", "0.00");
    	SAMPLE_STATIC_MAP.put("SAMPLE_VOLUME", "0.00");
    	SAMPLE_STATIC_MAP.put("SAMPLED", "'F'");
    	SAMPLE_STATIC_MAP.put("SIGNED", "'F'");
    	SAMPLE_STATIC_MAP.put("SPEC_TYPE", "'NONE'");
    	SAMPLE_STATIC_MAP.put("STAGE", "'NONE'");
    	SAMPLE_STATIC_MAP.put("STANDARD", "'F'");
    	SAMPLE_STATIC_MAP.put("STARTED", "'T'");
    	//SAMPLE_STATIC_MAP.put("STATUS", "'C'");
    	SAMPLE_STATIC_MAP.put("STORAGE_LOC_NO", "0.00");
    	SAMPLE_STATIC_MAP.put("T_CONTRACT_TESTS", "'F'");
    	SAMPLE_STATIC_MAP.put("T_LOGIN_VERIFIED", "'F'");
    	SAMPLE_STATIC_MAP.put("TEMPLATE", "'HF-CONDITION'");
    	SAMPLE_STATIC_MAP.put("TRANS_NUM", "0.00");
    }
    
    /**
     * TEST�����һ�λ�д�̶�ֵ
     */
    public static Map<String,String> TEST_STATIC_MAP = new HashMap<>();
    {
    	TEST_STATIC_MAP.put("CHARGE_ENTRY", "0.00");
    	TEST_STATIC_MAP.put("REPLICATE_COUNT", "1.00");
    	TEST_STATIC_MAP.put("STATUS", "'C'");
    	TEST_STATIC_MAP.put("OLD_STATUS", "'I'");
    	TEST_STATIC_MAP.put("PREP", "'F'");
    	TEST_STATIC_MAP.put("C_TASK_SEQ_NUM", "0.00");
    	TEST_STATIC_MAP.put("C_IF_ARRANGED", "'F'");
    	TEST_STATIC_MAP.put("C_ARRANGE_TYPE", "'A��'");
    	TEST_STATIC_MAP.put("C_TEST_TYPE", "'��������'");
    	TEST_STATIC_MAP.put("C_APPLY_REVIEW", "'F'");
    	TEST_STATIC_MAP.put("C_BASE_PARA_TEMP", "'T'");
    	TEST_STATIC_MAP.put("C_ARRANGE_SEQ_NUM", "0.00");
    	TEST_STATIC_MAP.put("C_TASK_STATUS", "'0'");
    	TEST_STATIC_MAP.put("C_TEST_CYCLE", "0.00");
    	TEST_STATIC_MAP.put("C_FAILURE_CYCLE", "0.00");
    	TEST_STATIC_MAP.put("REPLICATE_TEST", "'F'");
    	TEST_STATIC_MAP.put("TEST_PRIORITY", "0.00");
    	TEST_STATIC_MAP.put("IN_SPEC", "'T'");
    	TEST_STATIC_MAP.put("IN_CAL", "'T'");
    	TEST_STATIC_MAP.put("TEST_LOCATION", "'DEFAULT'");
    	TEST_STATIC_MAP.put("ORDER_NUMBER", "1.00");
    	TEST_STATIC_MAP.put("GROUP_NAME", "'DEFAULT'");
    	TEST_STATIC_MAP.put("RESOLVE_REQD", "'F'");
    	TEST_STATIC_MAP.put("STAGE", "'NONE'");
    	TEST_STATIC_MAP.put("PRIMARY_IN_SPEC", "'T'");
    	TEST_STATIC_MAP.put("IN_CONTROL", "'T'");
    	TEST_STATIC_MAP.put("VARIATION", "''");
    	TEST_STATIC_MAP.put("RE_TESTED", "'F'");
    	TEST_STATIC_MAP.put("MODIFIED_RESULTS", "'F'");
    	TEST_STATIC_MAP.put("ALIQUOTED_TO", "0.00");
    	TEST_STATIC_MAP.put("ON_WORKSHEET", "'F'");
    	TEST_STATIC_MAP.put("ALIQUOT_GROUP", "'DEFAULT'");
    	TEST_STATIC_MAP.put("ANALYSIS_COUNT", "0.00");
    	TEST_STATIC_MAP.put("BATCH_ORIGINAL_TEST", "0.00");
    	TEST_STATIC_MAP.put("BATCH_PARENT_TEST", "0.00");
    	TEST_STATIC_MAP.put("BATCH_SIBLING_TEST", "0.00");
    	TEST_STATIC_MAP.put("CHILD_OUT_SPEC", "'F'");
    	TEST_STATIC_MAP.put("CROSS_SAMPLE", "'F'");
    	TEST_STATIC_MAP.put("DISPLAY_RESULTS", "'T'");
    	TEST_STATIC_MAP.put("DOUBLE_ENTRY", "'F'");
    	TEST_STATIC_MAP.put("PARENT_TEST", "0.00");
    	TEST_STATIC_MAP.put("RELEASED", "'F'");
    	TEST_STATIC_MAP.put("SIGNED", "'F'");
    	TEST_STATIC_MAP.put("SPLIT_REPLICATES", "'F'");
    	TEST_STATIC_MAP.put("TEST_SEQUENCE_NO", "0.00");
    	TEST_STATIC_MAP.put("CNTRCT_QTE_ITEM_NO", "0.00");
    	TEST_STATIC_MAP.put("DOUBLE_BLIND", "'F'");
    	TEST_STATIC_MAP.put("INVOICE_NUMBER", "0.00");
    	TEST_STATIC_MAP.put("PRE_INVOICE_NUMBER", "0.00");
    	TEST_STATIC_MAP.put("REPORTED_RSLT_OOS", "'F'");
    	TEST_STATIC_MAP.put("T_CHARGE_GROUP", "0.00");
    	TEST_STATIC_MAP.put("T_NEEDS_LOCATION", "'F'");
    	TEST_STATIC_MAP.put("T_PREP_TEST", "0.00");
    	TEST_STATIC_MAP.put("T_QC_REFERENCE", "0.00");
    	TEST_STATIC_MAP.put("T_TURNAROUND_ACTUA", "0.00");
    	TEST_STATIC_MAP.put("T_TURNAROUND_CHARG", "1");
    	TEST_STATIC_MAP.put("T_TURNAROUND_MET", "'F'");
    	TEST_STATIC_MAP.put("TRANS_NUM", "0.00");
    	
    }
	
    

    
    
    
    
    
    
}
