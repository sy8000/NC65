package nc.ui.pub.qcco.writeback.utils.mapping;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 91967
 *
 */
public class SecWriteBackStaticMaping {
	
	/**
     * ���������������̶�ֵ��д
     */
    public static Map<String,String> TASK_CONDITION_STATIC_MAP = new HashMap<>();
    {	
    	//����д��!!!
    	//TASK_CONDITION_STATIC_MAP.put("ENTRY","null");
    	TASK_CONDITION_STATIC_MAP.put("ENTERED_ON","null");
    	TASK_CONDITION_STATIC_MAP.put("ATTRIBUTE_1","null");
    }

    /**
     * sample�����һ�λ�д�̶�ֵ
     */
    public static Map<String,String> SAMPLE_STATIC_MAP = new HashMap<>();
    {
    	//����д��
    	SAMPLE_STATIC_MAP.put("\"AUDIT\"", "'T'");
    	//SAMPLE_STATIC_MAP.put("DATE_STARTED", "null");
    	SAMPLE_STATIC_MAP.put("OLD_STATUS", "'C'");
    	SAMPLE_STATIC_MAP.put("PRODUCT_VERSION", "1");
    	//SAMPLE_STATIC_MAP.put("RECD_DATE", "null");
    	//SAMPLE_STATIC_MAP.put("RECEIVED_BY", "null");
    	SAMPLE_STATIC_MAP.put("STARTED", "'F'");
    	SAMPLE_STATIC_MAP.put("STATUS", "'U'");
    	SAMPLE_STATIC_MAP.put("T_LOGIN_VERIFIED", "'T'");
    	SAMPLE_STATIC_MAP.put("TEMPLATE", "'HF-MAIN'");
    }
    
    /**
     * TEST�����һ�λ�д�̶�ֵ
     */
    public static Map<String,String> TEST_STATIC_MAP = new HashMap<>();
    {	
    	//����д��
    	TEST_STATIC_MAP.put("STATUS", "'I'");
    	//TEST_STATIC_MAP.put("VARIATION", "null");
    	
    }

    public static Map<String,String> RESULT_STATIC_MAP = new HashMap<>();









}
