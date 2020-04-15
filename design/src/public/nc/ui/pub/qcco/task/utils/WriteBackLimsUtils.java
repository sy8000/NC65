package nc.ui.pub.qcco.task.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.ColumnListProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapProcessor;
import nc.ui.bd.ref.AbstractRefModel;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ISuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.commission.CommissionHVO;
import nc.vo.qcco.commission.CommissionRVO;
import nc.vo.qcco.task.TaskBVO;
import nc.vo.qcco.task.TaskHVO;
import nc.vo.qcco.task.TaskRVO;
import nc.vo.qcco.task.TaskSVO;

import org.apache.commons.lang.StringUtils;

/**
 * XXX ������ʵ���̲�ס��,���ع������
 * 
 * @author 91967
 *
 */
public class WriteBackLimsUtils {
    private BaseDAO baseDao = new BaseDAO();

    private static Map<String, String> bodySimple2ChildrenMapping;
    // lims����
    public static Map<Class<?>, String> LIMS_PK_MAP;
    {
        LIMS_PK_MAP = new HashMap<>();
        LIMS_PK_MAP.put(CommissionHVO.class, "PROJECT.NAME");
        LIMS_PK_MAP.put(CommissionBVO.class, "C_PROJ_LOGIN_SAMPLE.SEQ_NUM");
        LIMS_PK_MAP.put(CommissionRVO.class, "C_PROJ_PARA_A.SEQ_NUM");

        LIMS_PK_MAP.put(TaskBVO.class, "c_proj_task.SEQ_NUM");
        LIMS_PK_MAP.put(TaskSVO.class, "result.RESULT_NUMBER");
        LIMS_PK_MAP.put(TaskRVO.class, "c_proj_task_para_b.SEQ_NUM");
    }

    // lims����-�����д�ı�
    public static Map<Class<?>, String> LIMS_PK_MAP_EXTEND;
    {
        LIMS_PK_MAP_EXTEND = new HashMap<>();
        LIMS_PK_MAP_EXTEND.put(CommissionBVO.class, "Sample.SAMPLE_NUMBER");


    }
    // NC ���
    public static Map<Class<?>, String> NC_FK_MAP;
    {
        NC_FK_MAP = new HashMap<>();
        NC_FK_MAP.put(CommissionHVO.class, null);
        NC_FK_MAP.put(CommissionBVO.class, "qc_commission_b.pk_commission_h");
        NC_FK_MAP.put(CommissionRVO.class, "qc_commission_r.pk_commission_b");

        NC_FK_MAP.put(TaskBVO.class, "qc_task_b.pk_task_h");
        NC_FK_MAP.put(TaskSVO.class, "qc_task_s.pk_task_b");
        NC_FK_MAP.put(TaskRVO.class, "qc_task_r.pk_task_b");
    }

    // LIMS ���
    public static Map<Class<?>, String> LIMS_FK_MAP;
    {
        LIMS_FK_MAP = new HashMap<>();
        LIMS_FK_MAP.put(CommissionHVO.class, null);
        LIMS_FK_MAP.put(CommissionBVO.class, "C_PROJ_LOGIN_SAMPLE.PROJECT");
        LIMS_FK_MAP.put(CommissionRVO.class, "C_PROJ_PARA_A.proj_logsamp_seqnum");

        LIMS_FK_MAP.put(TaskBVO.class, "c_proj_task.PROJECT");
        LIMS_FK_MAP.put(TaskSVO.class, "result.SAMPLE_NUMBER");
        LIMS_FK_MAP.put(TaskRVO.class, "c_proj_task_para_b.TASK_SEQ_NUM");
    }

    // LIMS ���-�����д�ı�
    public static Map<Class<?>, String> LIMS_FK_MAP_EXTEND;
    {
        LIMS_FK_MAP_EXTEND = new HashMap<>();
        LIMS_FK_MAP_EXTEND.put(CommissionBVO.class, "SAMPLE.PROJECT");

    }

    // ί�е�
    private Map<String, String> headMapping; // qc_commission_h
    private Map<String, String> bodySampleMapping; // qc_commission_b
    private Map<String, String> bodySampleExtendMapping;//qc_commission_b ����Ļ�д

    private Map<String, String> grandBeforeMapping; // qc_commission_r

    // ����
    private Map<String, String> bodyTaskMapping; // qc_task_b
    private Map<String, String> grandConditionMapping; // qc_task_s
    private Map<String, String> grandAfterMapping; // qc_task_r

    // ��¼ÿ��vo���ϲ�����
    public static Map<Class<?>, String> FATHER_PK_KEY_MAP;
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
     * ע��: ʷ������������ϼ�,�������?�����ڵ�,�������?�а�,��������?�����
     * ȡί�е�/���񵥻�дLIMS�Ĳ���SQL
     *
     * @param pk_commission_h
     * @return
     * @throws BusinessException
     */
    public String[] getInsertLIMSSQL(String pk_commission_h)
            throws BusinessException {
        List<String> sqlList = new ArrayList<String>();
        // �������Ӧ��������--����У��
        Map<Class<?>, Integer> class2NumMap = new HashMap<>();
        // �������񵥵�ί�е���ӳ���
        Map<String, String> taskPk2CommissionPkMap = initRelated(pk_commission_h);
        Map<String, Object> ncPK2NCObjMap = new HashMap();
        String[] lists = null;
        List<String> projectList = new ArrayList();
        //sample���һ�λ�д
        String[] sampleFirstExtends = null;
        //test���һ�λ�д
        String[] testFirstExtendArray = null;
        List<String> testFirstExtendList = new ArrayList<>();
        //
        //���ڴ洢����ʱ����     pkFirstSample:��һ�λ�д��sample����
        Map<String,Object> boxMap = new HashMap<>();
        //Integer pkFirstSample = null;

        // �洢nc�е�pk��limsϵͳ��pk�Ķ�Ӧ��ϵ
        Map<String, Object> ncPK2LimsPkMap = new HashMap<>();
        String headCond = "pk_commission_h = '" + pk_commission_h
                + "' and dr = 0 ";
        // ί�е���ͷ
        lists = getInsertSQLByMap(getHeadMapping(), CommissionHVO.class,
                headCond, ncPK2LimsPkMap, ncPK2NCObjMap, taskPk2CommissionPkMap,projectList,testFirstExtendList);
        if (lists == null || lists.length < 1) {
            throw new BusinessException("ί�е�������дʧ��!");
        }
        class2NumMap.put(CommissionHVO.class, lists!=null?(lists.length - 1):0);
        if (lists != null && lists.length > 0) {
            sqlList.addAll(getHeadInsertSQL(lists,pk_commission_h));
        }
        // ��Ʒ��
        lists = getInsertSQLByMap(getBodySampleMapping(), CommissionBVO.class,
                headCond, ncPK2LimsPkMap, ncPK2NCObjMap, taskPk2CommissionPkMap,projectList,testFirstExtendList);
        class2NumMap.put(CommissionBVO.class, lists!=null?(lists.length - 1):0);
        if (lists != null && lists.length > 0) {
            sqlList.addAll(getSampleInsertSQL(lists));
        }
        //�����дSample��
        sampleFirstExtends = getSampleInsertSQLByMap(pk_commission_h,getBodySampleExtendMapping(), CommissionBVO.class,
                headCond, ncPK2LimsPkMap, ncPK2NCObjMap,boxMap);

        // ʵ��ǰ����
        String subCondition = "pk_commission_b in (select pk_commission_b from qc_commission_b where "
                + headCond + ") and dr = 0 ";
        lists = getInsertSQLByMap(getGrandBeforeMapping(), CommissionRVO.class,
                subCondition, ncPK2LimsPkMap, ncPK2NCObjMap,
                taskPk2CommissionPkMap,projectList,testFirstExtendList);
        if (lists != null && class2NumMap.get(CommissionBVO.class) <= 0
                && lists.length > 0) {
            throw new BusinessException("ί�е����[ʵ��ǰ����]������Ч����,����ϵ���ݹ���Ա.");
        }
        class2NumMap.put(CommissionRVO.class, lists!=null?(lists.length - 1):0);
        if (lists != null && lists.length > 0) {
            sqlList.addAll(getBeforeInsertSQL(lists));
        }

        // ������
        subCondition = "pk_task_h in (select pk_task_h from qc_task_h where "
                + headCond + ") and dr = 0 ";
        //
        lists = getInsertSQLByMap(getBodyTaskMapping(), TaskBVO.class,
                subCondition, ncPK2LimsPkMap, ncPK2NCObjMap,
                taskPk2CommissionPkMap,projectList,testFirstExtendList);
        class2NumMap.put(TaskBVO.class, lists!=null?(lists.length - 1):0);
        if (lists != null && lists.length > 0) {
            sqlList.addAll(getTaskInsertSQL(lists,pk_commission_h,ncPK2LimsPkMap));
        }

        // ��������
        subCondition = "pk_task_b in (select pk_task_b from qc_task_b where "
                + " pk_task_h in (select pk_task_h from qc_task_h where "
                + headCond + " ) and dr = 0 ) and dr = 0 ";
        lists = getInsertSQLByMap(getGrandConditionMapping(), TaskSVO.class,
                subCondition, ncPK2LimsPkMap, ncPK2NCObjMap,
                taskPk2CommissionPkMap,projectList,testFirstExtendList);
        if (lists != null && class2NumMap.get(TaskBVO.class) <= 0
                && lists.length > 0) {
            throw new BusinessException("�������[ʵ������]������Ч����,����ϵ���ݹ���Ա.");
        }
        class2NumMap.put(TaskSVO.class, lists!=null?(lists.length - 1):0);
        if (lists != null && lists.length > 0) {
            sqlList.addAll(getConditionInsertSQL(lists));
        }

        // ʵ������
        subCondition = "pk_task_b in (select pk_task_b from qc_task_b where "
                + " pk_task_h in (select pk_task_h from qc_task_h where "
                + headCond + " ) and dr = 0 ) and dr = 0 ";
        lists = getInsertSQLByMap(getGrandAfterMapping(), TaskRVO.class,
                subCondition, ncPK2LimsPkMap, ncPK2NCObjMap,
                taskPk2CommissionPkMap,projectList,testFirstExtendList);
        if (lists != null && class2NumMap.get(TaskBVO.class) <= 0
                && lists.length > 0) {
            throw new BusinessException("�������[ʵ������]������Ч����,����ϵ���ݹ���Ա.");
        }
        class2NumMap.put(TaskRVO.class, lists!=null?(lists.length - 1):0);
        if (lists != null && lists.length > 0) {
            sqlList.addAll(getAfterInsertSQL(lists));
        }
        //����Ļ�дsample��(��һ��)
        if (sampleFirstExtends != null && sampleFirstExtends.length > 0) {
            sqlList.addAll(getSampleExtendInsertSQL(sampleFirstExtends));
        }
        //����Ļ�дtest��(��һ��)
        testFirstExtendArray = testFirstExtendList.toArray(new String[0]);
        if (testFirstExtendArray != null && testFirstExtendArray.length > 0) {
            sqlList.addAll(getTestExtendInsertSQL(testFirstExtendArray,boxMap));
        }
        return sqlList.toArray(new String[0]);
    }
    /**
     * ����Ļ�дtest��(��һ��)
     * @param lists
     * @return
     */
    private List<String> getTestExtendInsertSQL(String[] lists,Map<String,Object> boxMap) {
        List<String> rsList = new ArrayList<>();
        if (lists != null && lists.length > 1) {
        	StringBuilder colNameSB = new StringBuilder(lists[0]);
        	StringBuilder colValSB = new StringBuilder();
            //����̶�ֵ�ֶ�
        	for(String colName : TEST_STATIC_MAP.keySet()){
        		colNameSB.append(",").append(colName);
        		colValSB.append(",").append(TEST_STATIC_MAP.get(colName));
        	}
        	//sample_number
        	if(boxMap.get("pkFirstSample")!=null){
        		colNameSB.append(",").append("sample_number");
        		colValSB.append(",").append(boxMap.get("pkFirstSample"));
        	}else{
        		colNameSB.append(",").append("sample_number");
        		colValSB.append(",").append("null");
        	}
        	
            StringBuilder sqlSB = new StringBuilder();
            StringBuilder temp = new StringBuilder();
            for (int i = 1; i < lists.length; i++) {
                sqlSB.append("INSERT INTO test").append("(")
                        .append(colNameSB.toString()).append(")  values (");
                temp.delete(0, temp.length());
                temp.append(lists[i]).append(colValSB);
                sqlSB.append(temp.toString());
                sqlSB.append(" ) ");
                rsList.add(sqlSB.toString());
                sqlSB.delete(0, sqlSB.length());
            }
        }
        return rsList;
    }

	private Map<String, String> initRelated(String pk_commission_h)
            throws DAOException {
        Map<String, String> rsMap = new HashMap();
        String sql = "select pk_task_h from qc_task_h where pk_commission_h = '"
                + pk_commission_h + "'";
        List<String> pkTaskhList = (List<String>) baseDao.executeQuery(sql,
                new ColumnListProcessor());
        if (pkTaskhList != null && pkTaskhList.size() > 0) {
            for (String pk : pkTaskhList) {
                rsMap.put(pk, pk_commission_h);
            }
        }
        return rsMap;
    }

    /**
     * ʵ������
     *
     * @param lists
     * @param ncPK2LimsPk
     * @param fatherPkList
     * @param selfPkList
     * @return
     * @throws BusinessException 
     */
    private List<String> getAfterInsertSQL(String[] lists) throws BusinessException {
        List<String> rsList = new ArrayList<>();
        StringBuilder colNameSB = new StringBuilder(lists[0]);
        StringBuilder colValSB = new StringBuilder();
   
        //����ENTRY_CODE(ѡȡC_PROJ_PARA_A�����ֵ)
        //Ԥ����ENTRY_CODE
        List<Integer> entryCodeList = getPrePk("entry_code","c_proj_task_para_b",lists.length - 1);
        colNameSB.append(",").append("entry_code");
        if (lists != null && lists.length > 1) {
            StringBuilder sqlSB = new StringBuilder();
            for (int i = 1; i < lists.length; i++) {
                sqlSB.append("INSERT INTO c_proj_task_para_b").append("(")
                        .append(colNameSB.toString()).append(")  values (");
                sqlSB.append(lists[i]).append(",").append(entryCodeList.get(i-1));
                sqlSB.append(" ) ");
                rsList.add(sqlSB.toString());
                sqlSB.delete(0, sqlSB.length());
            }
        }
        return rsList;
    }

    /**
     * ʵ������
     *
     * @param lists
     * @param ncPK2LimsPk
     * @param fatherPkList
     * @param selfPkList
     * @return
     */
    private List<String> getConditionInsertSQL(String[] lists) {
    	List<String> rsList = new ArrayList<>();
        if (lists != null && lists.length > 1) {
        	StringBuilder colNameSB = new StringBuilder(lists[0]);
        	StringBuilder colValSB = new StringBuilder();
            //����̶�ֵ�ֶ�
        	for(String colName : TASK_CONDITION_STATIC_MAP.keySet()){
        		colNameSB.append(",").append(colName);
        		colValSB.append(",").append(TASK_CONDITION_STATIC_MAP.get(colName));
        	}
        	
            StringBuilder sqlSB = new StringBuilder();
            StringBuilder temp = new StringBuilder();
            for (int i = 1; i < lists.length; i++) {
                sqlSB.append("INSERT INTO result").append("(")
                        .append(colNameSB.toString()).append(")  values (");
                temp.delete(0, temp.length());
                temp.append(lists[i]).append(colValSB);
                sqlSB.append(temp.toString());
                sqlSB.append(" ) ");
                rsList.add(sqlSB.toString());
                sqlSB.delete(0, sqlSB.length());
            }
        }
        return rsList;
    }

    /**
     * ������
     *
     * @param lists
     * @param ncPK2LimsPk
     * @param fatherPkList
     * @param selfPkList
     * @return
     */
    @SuppressWarnings("unchecked")
	private List<String> getTaskInsertSQL(String[] lists,String pk_commission_h,Map<String, Object> ncPK2LimsPkMap) {
    	List<String> rsList = new ArrayList<>();
        if (lists != null && lists.length > 1) {
        	StringBuilder colNameSB = new StringBuilder(lists[0]);
        	StringBuilder colValSB = new StringBuilder();
            //����̶�ֵ�ֶ�
        	for(String colName : TASK_BODY_STATIC_MAP.keySet()){
        		colNameSB.append(colName).append(",");
        		colValSB.append(TASK_BODY_STATIC_MAP.get(colName)).append(",");
        	}
        	//project 
        	colNameSB.append("project").append(",");
    		colValSB.append("'").append(String.valueOf(ncPK2LimsPkMap.get(pk_commission_h))).append("',");
        	
        	//colNameSB = colNameSB.delete(colNameSB.length()-1, colNameSB.length());
        	//colValSB = colValSB.delete(colValSB.length()-1, colValSB.length());
        	String sql = "select job.psncode changed_by,taskh.modifiedtime changed_on,"
        				+ " job2.psncode c_submit_by,ch.creationtime c_submit_date from qc_task_h taskh "
        				+ " left join sm_user sm on sm.cuserid = taskh.modifier "
        				+ " left join (select * from bd_psnjob jobinner where ismainjob = 'Y' ) job on rownum = 1 and job.pk_psndoc = sm.pk_psndoc "
        				+ " left join qc_commission_h ch on ch.pk_commission_h = taskh.pk_commission_h "
        				+ " left join sm_user sm2 on sm2.cuserid = ch.creator "
        				+ " left join (select * from bd_psnjob jobinner where ismainjob = 'Y' ) job2 on rownum = 1 and job2.pk_psndoc = sm2.pk_psndoc "
        				+ " where taskh.pk_commission_h = '"+pk_commission_h+"' ";
        	Map<String,String> rs = null;
        	try {
        		rs = (Map<String,String>)baseDao.executeQuery(sql, new MapProcessor());
			} catch (DAOException e) {
				rs = new HashMap<>();;
			}
			if (null != rs) {
				// ί�е��Ƶ���,�Ƶ�ʱ�� �޸���.�޸�ʱ�� realValue =
				// "to_timestamp('"+realValue+"','yyyy-mm-dd hh24:mi:ss.ff')"
				colNameSB.append("changed_by").append(",").append("changed_on").append(",")
					.append("c_submit_by").append(",").append("c_submit_date");

				colValSB.append(" '").append(rs.get("changed_by")).append("', ")
					.append("to_timestamp('"+rs.get("changed_on")+"','yyyy-mm-dd hh24:mi:ss.ff')")
					.append(", '").append(rs.get("c_submit_by")).append("',")
					.append("to_timestamp('"+rs.get("c_submit_date")+"','yyyy-mm-dd hh24:mi:ss.ff') ");
			}
        	
            StringBuilder sqlSB = new StringBuilder();
            StringBuilder temp = new StringBuilder();
            for (int i = 1; i < lists.length; i++) {
                sqlSB.append("INSERT INTO c_proj_task").append("(")
                        .append(colNameSB.toString()).append(")  values (");
                temp.delete(0, temp.length());
                temp.append(lists[i]).append(colValSB);
                sqlSB.append(temp.toString());
                sqlSB.append(" ) ");
                rsList.add(sqlSB.toString());
                sqlSB.delete(0, sqlSB.length());
            }
        }
        return rsList;
    }

    /**
     * ʵ��ǰ����
     *
     * @param lists
     * @param ncPK2LimsPk
     * @param fatherPkList
     * @param selfPkList
     * @return
     * @throws DAOException 
     */
    private List<String> getBeforeInsertSQL(String[] lists) throws DAOException {
        List<String> rsList = new ArrayList<>();
        StringBuilder colNameSB = new StringBuilder(lists[0]);
        StringBuilder colValSB = new StringBuilder();
   
        //����ENTRY_CODE(ѡȡC_PROJ_PARA_A�����ֵ)
        //Ԥ����ENTRY_CODE
        List<Integer> entryCodeList = getPrePk("entry_code","c_proj_para_a",lists.length - 1);
        colNameSB.append(",").append("entry_code");
        if (lists != null && lists.length > 1) {
            StringBuilder sqlSB = new StringBuilder();
            for (int i = 1; i < lists.length; i++) {
                sqlSB.append("INSERT INTO C_PROJ_PARA_A").append("(")
                        .append(colNameSB.toString()).append(")  values (");
                sqlSB.append(lists[i]).append(",").append(entryCodeList.get(i-1));
                sqlSB.append(" ) ");
                rsList.add(sqlSB.toString());
                sqlSB.delete(0, sqlSB.length());
            }
        }
        return rsList;
    }

    /**
     * ��Ʒ��
     *
     * @param lists
     * @param ncPK2LimsPk
     * @param fatherPkList
     * @param selfPkList
     * @return
     */
    private List<String> getSampleInsertSQL(String[] lists) {
    	List<String> rsList = new ArrayList<>();
        if (lists != null && lists.length > 1) {
        	StringBuilder colNameSB = new StringBuilder(lists[0]);
        	StringBuilder colValSB = new StringBuilder();
            //����̶�ֵ�ֶ�
        	for(String colName : COMMISSION_BODY_STATIC_MAP.keySet()){
        		colNameSB.append(",").append(colName);
        		colValSB.append(",").append(COMMISSION_BODY_STATIC_MAP.get(colName));
        	}
        	
            StringBuilder sqlSB = new StringBuilder();
            StringBuilder temp = new StringBuilder();
            for (int i = 1; i < lists.length; i++) {
                sqlSB.append("INSERT INTO C_PROJ_LOGIN_SAMPLE").append("(")
                        .append(colNameSB.toString()).append(")  values (");
                temp.delete(0, temp.length());
                temp.append(lists[i]).append(colValSB);
                sqlSB.append(temp.toString());
                sqlSB.append(" ) ");
                rsList.add(sqlSB.toString());
                sqlSB.delete(0, sqlSB.length());
            }
        }
        return rsList;
    }
    /**
     * Sample ��һ�λ�д
     *
     * @param lists
     * @param ncPK2LimsPk
     * @param fatherPkList
     * @param selfPkList
     * @return
     */
    private List<String> getSampleExtendInsertSQL(String[] lists) {
        List<String> rsList = new ArrayList<>();
        if (lists != null && lists.length > 1) {
        	StringBuilder colNameSB = new StringBuilder(lists[0]);
        	StringBuilder colValSB = new StringBuilder();
            //����̶�ֵ�ֶ�
        	for(String colName : SAMPLE_STATIC_MAP.keySet()){
        		colNameSB.append(",").append(colName);
        		colValSB.append(",").append(SAMPLE_STATIC_MAP.get(colName));
        	}
        	
            StringBuilder sqlSB = new StringBuilder();
            StringBuilder temp = new StringBuilder();
            for (int i = 1; i < lists.length; i++) {
                sqlSB.append("INSERT INTO SAMPLE").append("(")
                        .append(colNameSB.toString()).append(")  values (");
                temp.delete(0, temp.length());
                temp.append(lists[i]).append(colValSB);
                sqlSB.append(temp.toString());
                sqlSB.append(" ) ");
                rsList.add(sqlSB.toString());
                sqlSB.delete(0, sqlSB.length());
            }
        }
        return rsList;
    }
    /**
     * ί�е�����̶�ֵ��д
     */
    private static Map<String,String> COMMISSION_HEARD_STATIC_MAP = new HashMap<>();
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
		COMMISSION_HEARD_STATIC_MAP.put("APPROVAL_ID", "0");
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
    private static Map<String,String> TASK_CONDITION_STATIC_MAP = new HashMap<>();
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
    	
    	
    	
    	//����д��!!!
    	//TASK_CONDITION_STATIC_MAP.put("ENTRY","null");
    	TASK_CONDITION_STATIC_MAP.put("ENTERED_ON","null");
    	TASK_CONDITION_STATIC_MAP.put("ATTRIBUTE_1","null");
    }
    
    
    /**
     * ί�е�����̶�ֵ��д
     */
    private static Map<String,String> COMMISSION_BODY_STATIC_MAP = new HashMap<>();
    {
    	COMMISSION_BODY_STATIC_MAP.put("NEED_LIST","'F'");
    	COMMISSION_BODY_STATIC_MAP.put("HAS_LOGINED_SAMPLE","'F'");
    	
    }
    /**
     * ���񵥱���̶�ֵ��д
     */
    private static Map<String,String> TASK_BODY_STATIC_MAP = new HashMap<>();
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
     * ������Ҫcodeд����ֶ�
     */
    private static Map<Class<?>,Set<String>> NEED_CODE_WRITE_BACK_MAP = new HashMap<>();
    {
		Set<String> COMMISSIONHVO_SET = new HashSet<>();
		COMMISSIONHVO_SET.add("cuserid");
		COMMISSIONHVO_SET.add("pk_maincategory");
		COMMISSIONHVO_SET.add("pk_subcategory");
		COMMISSIONHVO_SET.add("pk_lastcategory");
		COMMISSIONHVO_SET.add("reportlang");
		COMMISSIONHVO_SET.add("productproperty");
		COMMISSIONHVO_SET.add("customername");
		COMMISSIONHVO_SET.add("customertype");
		COMMISSIONHVO_SET.add("testrequirement");
		//COMMISSIONHVO_SET.add("checkingproperty");
		COMMISSIONHVO_SET.add("productline");
		//COMMISSIONHVO_SET.add("batchnumber");
		COMMISSIONHVO_SET.add("productdate");
		COMMISSIONHVO_SET.add("batchserial");
		COMMISSIONHVO_SET.add("identificationtype");
		//COMMISSIONHVO_SET.add("certificationtype");
		NEED_CODE_WRITE_BACK_MAP.put(CommissionHVO.class, COMMISSIONHVO_SET);

    }
    /**
     * ί�е���ͷ
     *
     * @param lists
     * @param ncPK2LimsPk
     * @param fatherPkList
     * @param selfPkList
     * @return
     * @throws BusinessException 
     */
    private List<String> getHeadInsertSQL(String[] lists,String pk_commission_h) throws BusinessException {
        List<String> rsList = new ArrayList();
        if (lists != null && lists.length > 1) {
        	StringBuilder colNameSB = new StringBuilder(lists[0]);
        	StringBuilder colValSB = new StringBuilder();
        	//��ѯ��Ʒ������,���ڻ�дproject.NUM_SAMPLES �ֶ�
        	UFDouble bNum = new UFDouble(baseDao.executeQuery(
        			"select sum(quantity) from qc_commission_b where pk_commission_h = '"+pk_commission_h+"' and dr = 0 ", 
        			new ColumnProcessor()).toString());
        	if(bNum == null){
        		bNum = UFDouble.ZERO_DBL;
        	}
        	colNameSB.append(",").append("num_samples");
    		colValSB.append(",").append(bNum.toDouble());
            //����̶�ֵ�ֶ�
        	for(String colName : COMMISSION_HEARD_STATIC_MAP.keySet()){
        		colNameSB.append(",").append(colName);
        		colValSB.append(",").append(COMMISSION_HEARD_STATIC_MAP.get(colName));
        	}
        	
            StringBuilder sqlSB = new StringBuilder();
            StringBuilder temp = new StringBuilder();
            for (int i = 1; i < lists.length; i++) {
                sqlSB.append("INSERT INTO PROJECT").append("(")
                        .append(colNameSB.toString()).append(")  values (");
                temp.delete(0, temp.length());
                temp.append(lists[i]).append(colValSB);
                sqlSB.append(temp.toString());
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
     * @param ncPK2LimsPkMap
     *            ncpk��limsϵͳpk��Ӧ��ϵ
     * @param ncPK2ObjectMap
     *            ���α��������NCOBJ
     * @param testFirstExtends sample���һ�λ�д�Ĳ���sql
     * 
     * @return fieldValues[0]: �ֶ���Ƭ��<br />
     *         fieldValues[1-n]��ֵƬ��
     * @return project ί�е����,���ڸ������project
     * @throws BusinessException
     */
    private String[] getInsertSQLByMap(Map<String, String> fieldMap,
                                       Class<?> clazz, String condition,
                                       Map<String, Object> ncPK2LimsPkMap,
                                       Map<String, Object> ncPK2NCObjMap,
                                       Map<String, String> taskPk2CommissionPkMap,List<String> projectList,List<String> testFirstExtendList)
            throws BusinessException {

        // ����ĳ���ֶε�ֵ,һ�����ݶ�Ӧarray�е�һ��
        StringBuilder[] fieldValues = null;
        IUAPQueryBS query = NCLocator.getInstance().lookup(IUAPQueryBS.class);
        List<?> srcData = (List<?>) query.retrieveByClause(clazz, condition);
        List<Integer> pkList = null;
        if (null == srcData || srcData.size() <= 0) {
            return null;
        }
        List<Integer> test_numberList = null;
        //List<Integer> sample_numberList = null;

        if (CommissionHVO.class != clazz) {
            // Ԥ����pk-����ί�е�֮��(ί�е���������billNo)
            pkList = getPrePk(clazz, srcData.size());
        }

        if(TaskBVO.class == clazz){
            //�����ӱ���Ҫ����test_number��sample_number
            test_numberList = getPrePk("test_number","test",srcData.size());
            //sample_numberList = getPrePk("sample_number","sample",srcData.size());
            //test����ռλ
            /*for(Integer pktest : test_numberList){
            	baseDao.executeUpdate("insert into test (test_number) values("+pktest+")");
            }*/
        }
        
        fieldValues = new StringBuilder[srcData.size() + 1];
        // ����ƴ���ֶ���
        StringBuilder insertFields = new StringBuilder();
        // ������ѭ��
        for (Entry<String, String> map : fieldMap.entrySet()) {
            String fieldName = map.getKey();

            String[] fields = null;
            if (map.getValue().contains(";")) {
                fields = getWriteBackFields(map.getValue().split(";"));
            } else {
                fields = getWriteBackFields(new String[] { map.getValue() });
            }
            // times�Ǵ���һ�Զ��ϵ��
            int times = fields.length;
            for (String field : fields) {
                insertFields.append(field).append(",");
            }

            if (srcData != null && srcData.size() > 0) {

                int row = 1;
                for (Object data : srcData) {
                    ISuperVO vo = (ISuperVO) data;
                    Object unDofieldValue = vo.getAttributeValue(fieldName);
                    // �������
                    Object realValue = dealRefValue(clazz, fieldName,
                            unDofieldValue);

                    if (null == fieldValues[row]) {
                        fieldValues[row] = new StringBuilder();
                    }
                    if(null!=realValue &&("creationtime".equals(fieldName)||"modifiedtime".equals(fieldName))){
                        realValue = "to_timestamp('"+realValue+"','yyyy-mm-dd hh24:mi:ss.ff')";}
                    if(null!=realValue &&("productdate".equals(fieldName))){
                        realValue = "to_timestamp('"+realValue+"','yyyy-mm-dd')";}
                    for (int j = 0; j < times; j++) {
                        if (realValue != null) {
                            if (realValue instanceof Integer || realValue instanceof UFDouble || realValue instanceof Double) {
                                fieldValues[row].append(realValue).append(",");
                            }else if(realValue instanceof UFBoolean){
                            	if("Y".equals(String.valueOf(realValue))){
                            		fieldValues[row].append("'")
                                    .append("T")
                                    .append("',");
                            	}else{
                            		fieldValues[row].append("'")
                                    .append("F")
                                    .append("',");
                            	}
                            	
                            }else {
                            	if(null!=realValue &&("creationtime".equals(fieldName)||"modifiedtime".equals(fieldName)||"productdate".equals(fieldName))){
                                    fieldValues[row]
                                            .append(String.valueOf(realValue))
                                            .append(",");
                                }else{
                                    fieldValues[row].append("'")
                                            .append(dealEscapse(String.valueOf(realValue)))
                                            .append("',");
                                }

                            }
                        } else {
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
        if (fieldValues != null) {
            rsString = new String[fieldValues.length];
            for (int i = 0; i < fieldValues.length; i++) {
                if (CommissionHVO.class != clazz) {
                    // д�������͸�����(ֻ֧�ֵ�����,Ҫ������,��Ҫ�ο�������ֶ�����߼�)
                    if (0 == i) {
                        
                        if (CommissionRVO.class == clazz) {
                        	// ����ί�е��ӱ��ֶ���Ҫд�����
                            Map<String, String> LimsFiled2ncFieldMap = getBodySimple2ChildrenMapping();
                            for (String limsField : LimsFiled2ncFieldMap.keySet()) {
                                fieldValues[i]
                                        .append(getWriteBackFields(new String[] { limsField })[0])
                                        .append(",");
                            }
                            //RULE_TYPE д��
                            fieldValues[i].append("rule_type").append(",");
                            // ANALYSIS_VERSION д��
                            fieldValues[i].append("analysis_version").append(",");
                        }
                        fieldValues[i].append(
                                getWriteBackFields(new String[] { LIMS_PK_MAP
                                        .get(clazz) })[0]).append(",");
                        if(TaskRVO.class==clazz){
                        	//RULE_TYPE д��
                            fieldValues[i].append("spec_rule").append(",");
                           
                            fieldValues[i].append("task_name").append(",");
                            fieldValues[i].append("analysis_version").append(",");
                            fieldValues[i].append("proj_logsamp_seqnum").append(",");
                        }
                        
                        if(TaskBVO.class==clazz){
                            //����������ӱ�,��Ҫ��дtestnumber
                            fieldValues[i]
                                    .append("test_number").append(",");
                            /*fieldValues[i]
                                    .append("sample_number");*/
                        }else{
                            fieldValues[i]
                                    .append(getWriteBackFields(new String[] { LIMS_FK_MAP
                                            .get(clazz) })[0]);
                        }
                        //ʵ��ǰ�����,��Ҫ��д��ί�е���billno
                        if(CommissionRVO.class == clazz || TaskRVO.class == clazz){
                            fieldValues[i]
                                    .append(",").append("project");
                        }

                    } else {
                        // ��ȡ�ϲ������:
                        String fatherPk = (String) (((ISuperVO) srcData
                                .get(i - 1)).getAttributeValue(getWriteBackFields(new String[] { NC_FK_MAP
                                .get(clazz) })[0]));

                        String realfatherPk = getRealFatherPk(clazz, fatherPk,
                                taskPk2CommissionPkMap);

                        // ����ί�е��ӱ��ֶ���Ҫд�����
                        if (CommissionRVO.class == clazz) {
                            Map<String, String> LimsFiled2ncFieldMap = getBodySimple2ChildrenMapping();
                            for (String limsField : LimsFiled2ncFieldMap.keySet()) {
                                // �ϲ��NCVO ��Ҫ��д���ӱ���ֶ�
                                String ncField = LimsFiled2ncFieldMap
                                        .get(limsField);
                                // ��ȡ�ϲ������
                                ISuperVO fatherObj = (ISuperVO) ncPK2NCObjMap
                                        .get(realfatherPk);
                                // ��ȡ��Ҫ��д��ֵ
                                Object oldFieldValue = fatherObj == null ? null
                                        : fatherObj.getAttributeValue(ncField);
                                // �������
                                Object realValue = dealRefValue(clazz, ncField,
                                        oldFieldValue);
                                if (null == realValue
                                        || realValue instanceof Integer|| realValue instanceof UFDouble || realValue instanceof Double) {
                                	
                                    fieldValues[i].append(realValue)
                                            .append(",");
                                } else if(realValue instanceof UFBoolean){
                                	if("Y".equals(String.valueOf(realValue))){
                                		fieldValues[i].append("'")
                                        .append("T")
                                        .append("',");
                                	}else{
                                		fieldValues[i].append("'")
                                        .append("F")
                                        .append("',");
                                	}
                                	
                                }else {
                                    fieldValues[i].append("'")
                                            .append(dealEscapse(String.valueOf(realValue)))
                                            .append("',");
                                }
                            }
                            // ����RULE_TYPE��ֵ
                            CommissionRVO rvo = ((CommissionRVO) srcData.get(i - 1));
							fieldValues[i].append("'").append(dealRuleType(rvo)).append("',");
							//�ͻ�������ȷ,��ʱ��д 1
							fieldValues[i].append("").append(1).append(",");
                        }
                        if (null == pkList.get(i - 1)
                                || pkList.get(i - 1) instanceof Integer ) {
                            // pk
                            fieldValues[i].append(pkList.get(i - 1))
                                    .append(",");
                        } else {
                            // pk
                            fieldValues[i].append("'").append(pkList.get(i - 1))
                                    .append("',");
                        }
                        if(TaskRVO.class == clazz){
                        	// ����RULE_TYPE��ֵ
                        	TaskRVO rvo = ((TaskRVO) srcData.get(i - 1));
							fieldValues[i].append("'").append(dealRuleType(rvo)).append("',");
							// ��д ������
							TaskBVO bvo =  (TaskBVO)ncPK2NCObjMap.get(rvo.getPk_task_b());
							String analysisName = bvo==null?null:bvo.getPk_testresultname();
							fieldValues[i].append("'").append(analysisName).append("',");
							// �����汾
							fieldValues[i].append("").append(getAnalysisVerionFromName(analysisName)).append(",");
							//proj_logsamp_seqnum ͨ������ȡ��Ӧ��ί�е��ӱ�
							fieldValues[i].append("").append(getCommissionBFromGroup(rvo.getPk_samplegroup(),bvo==null?null:bvo.getPk_task_h(),ncPK2LimsPkMap)).append(",");
                        }
                        if(TaskBVO.class == clazz){
                        	//��һ�λ�дtest��
                            firstWriteBackTest(ncPK2NCObjMap,srcData.get(i - 1),test_numberList.get(i - 1),testFirstExtendList,i);
                        }


                        //�����ӱ���Ҫ�����дtest_number
                        if(TaskBVO.class == clazz){
                            if (null == test_numberList.get(i - 1)
                                    || test_numberList.get(i - 1) instanceof Integer) {
                                // test_numberList
                                fieldValues[i].append(test_numberList.get(i - 1))
                                        .append(",");
                                
                            } else {
                                // test_numberList
                                fieldValues[i].append("'").append(test_numberList.get(i - 1))
                                        .append("',");
                            }

                            /*if (null == sample_numberList.get(i - 1)
                                    || sample_numberList.get(i - 1) instanceof Integer) {
                                // sample_numberList
                                fieldValues[i].append(sample_numberList.get(i - 1))
                                        .append("");
                            } else {
                                // sample_numberList
                                fieldValues[i].append("'").append(sample_numberList.get(i - 1))
                                        .append("'");
                            }*/
                        }else{
                            if (null == ncPK2LimsPkMap.get(realfatherPk)
                                    || ncPK2LimsPkMap.get(realfatherPk) instanceof Integer) {
                                // ���
                                fieldValues[i].append(ncPK2LimsPkMap.get(realfatherPk));
                            } else {
                                // pk
                                // ���
                                fieldValues[i].append("'").append(ncPK2LimsPkMap.get(realfatherPk)).append("'");
                            }
                        }
                        //ʵ��ǰ�����,��Ҫ��д��ί�е���billno
                        if(CommissionRVO.class == clazz || TaskRVO.class == clazz){
                            fieldValues[i]
                                    .append(",").append("'").append(projectList.get(0)).append("'");
                        }

                        // ��¼����
                        ncPK2LimsPkMap
                                .put(((ISuperVO) srcData.get(i - 1))
                                        .getPrimaryKey(), pkList.get(i - 1));
                        // ��¼�洢��obj
                        ncPK2NCObjMap
                                .put(((ISuperVO) srcData.get(i - 1))
                                        .getPrimaryKey(), srcData.get(i - 1));

                    }

                } else {
                    // ί�е���������������billno,û�и�����
                    if (i > 0) {
                        //��¼����
                        ncPK2LimsPkMap
                                .put(((ISuperVO) srcData.get(i - 1))
                                        .getPrimaryKey(), ((ISuperVO) srcData
                                        .get(i - 1))
                                        .getAttributeValue("billno"));

                        projectList.add(String.valueOf(((ISuperVO) srcData
                                .get(i - 1))
                                .getAttributeValue("billno")));

                        // ��¼�洢��obj
                        ncPK2NCObjMap
                                .put(((ISuperVO) srcData.get(i - 1))
                                        .getPrimaryKey(), srcData.get(i - 1));
                    }
                    fieldValues[i].setLength(fieldValues[i].length() - 1);
                }

                rsString[i] = fieldValues[i].toString();
            }

        }
        return rsString;
    }
    private Integer getCommissionBFromGroup(String pk_simpleGroup,String pk_task_h, Map<String, Object> ncPK2LimsPkMap) throws DAOException {
    	Integer pkSimple= null;
		if(pk_simpleGroup!=null && pk_task_h != null){
			String sql = " select cb.PK_COMMISSION_B from QC_COMMISSION_B cb "
						+" left join QC_TASK_H th on cb.PK_COMMISSION_H = th.PK_COMMISSION_H "
						+" where cb.PK_SAMPLEGROUP = '"+pk_simpleGroup+"' and th.PK_task_H = '"+pk_task_h+"' and cb.dr = 0 ";
			String pk_commission_b = (String)baseDao.executeQuery(sql, new ColumnProcessor());
			Object pkSimpleObj = ncPK2LimsPkMap.get(pk_commission_b);
			try{
				pkSimple = Integer.valueOf(pkSimpleObj.toString());
			}catch(Exception e){
				pkSimple = null;
			}
		}
		return pkSimple;
	}
	/**
     * ͨ��������,��ȡ�����汾
     * @param analysisName
     * @return
     * @throws DAOException 
     */
    private String getAnalysisVerionFromName(String analysisName) throws DAOException {
		String sql = " select VERSION from nc_analysis_list where name  = '"+analysisName+"'"; 
		Integer ver = (Integer)baseDao.executeQuery(sql, new ColumnProcessor());
		return String.valueOf(ver);
	}
	/**
     * ��һ�λ�дTest��
     * @param object 
     * @param object
     * @param pk_test Ԥ�����test����
     * @param pk_firstSample ��һ�λ�д��sample������
     * @throws BusinessException 
     */
    private void firstWriteBackTest(Map<String, Object> ncPK2NCObjMap, Object taskBVOObj, Integer pk_test,List<String> testFirstExtends,int i) throws BusinessException {
		if(taskBVOObj!=null && taskBVOObj instanceof TaskBVO){
			StringBuilder colNameSb = new StringBuilder();
			StringBuilder colValueSb = new StringBuilder();
			TaskBVO taskBvo = (TaskBVO)taskBVOObj;
			TaskHVO taskHvo = (TaskHVO)ncPK2NCObjMap.get(taskBvo.getPk_task_h());
			//��ѯhvo
			if(taskHvo==null){
				taskHvo = (TaskHVO)(baseDao.executeQuery("select * from qc_task_h where pk_task_h = '"+taskBvo.getPk_task_h()+"'", new BeanProcessor(TaskHVO.class)));
				ncPK2NCObjMap.put(taskBvo.getPk_task_h(), taskHvo);
				if(taskHvo == null){
					throw new BusinessException("���ݴ���:δ�ҵ�����������Ϣ!����ϵ����Ա!");
				}
			}
			//����
			colNameSb.append("test_number");
			colValueSb.append(pk_test);
			colNameSb.append(", ").append("original_test");
			colValueSb.append(", ").append(pk_test).append(" ");
			
			//���񵥴���ʱ��
			UFDateTime creatTime = taskHvo.getCreationtime();
			colNameSb.append(", ").append("date_received");
			colValueSb.append(", ").append("to_timestamp('"+creatTime+"','yyyy-mm-dd hh24:mi:ss.ff')");
			colNameSb.append(", ").append("date_started");
			colValueSb.append(", ").append("to_timestamp('"+creatTime+"','yyyy-mm-dd hh24:mi:ss.ff')");
			colNameSb.append(", ").append("t_date_enabled");
			colValueSb.append(", ").append("to_timestamp('"+creatTime+"','yyyy-mm-dd hh24:mi:ss.ff')");
			
			//����޸�ʱ��
			UFDateTime modifyTime = taskHvo.getModifiedtime()==null?creatTime:taskHvo.getModifiedtime();
			colNameSb.append(", ").append("changed_on");
			colValueSb.append(", ").append("to_timestamp('"+modifyTime+"','yyyy-mm-dd hh24:mi:ss.ff')");
			
			//������Խ������
			colNameSb.append(", ").append("analysis");
			colValueSb.append(", '").append(taskBvo.getPk_testresultname()).append("' ");
			
			//���Խ��������
			colNameSb.append(", ").append("common_name");
			colValueSb.append(", '").append(taskBvo.getTestresultshortname()).append("' ");
			
			//������Ŀ
			colNameSb.append(", ").append("reported_name");
			colValueSb.append(", '").append(taskBvo.getTestitem()).append("' ");
			
			//�����汾
			//������Ŀ
			colNameSb.append(", ").append("version");
			colValueSb.append(", '").append(getAnalysisVerionFromName(taskBvo.getPk_testresultname())).append("' ");
			
			if(testFirstExtends.size() <= 0){
				//����������
				testFirstExtends.add(colNameSb.toString());
			}
			testFirstExtends.add(colValueSb.toString());
		}
	}

	private String dealRuleType(CommissionRVO rvo) {
		if(rvo!=null){
			return dealRuleType(rvo.getStdmaxvalue(),rvo.getStdminvalue());
		}
		return null;
	}
	private String dealRuleType(TaskRVO rvo) {
		if(rvo!=null){
			return dealRuleType(rvo.getStdmaxvalue(),rvo.getStdminvalue());
		}
		return null;
	}
	
	private String dealRuleType(Object maxValue,Object minValue){
		/*
		 * ί�е���� RuleType ��Ϊ��Ӧ��б����£�
		 * ֻ�����ֵ��LTE_MAX
		 * ֻ����Сֵ��GTE_MIN
		 * �����С���У�MNLTELTEMX
		 * ��ʪ�ȣ�EMPTY
		 * GT_MIN
		 * MNLTLTEMX
		 */
		if ((maxValue != null &&  "-".equals(String.valueOf(maxValue))) 
				&& (minValue == null || "-".equals(String.valueOf(minValue)))) {
			return "LET_MAX";
		} else if ( (maxValue == null || "-".equals(String.valueOf(maxValue))) 
				&& (minValue != null && !"-".equals(String.valueOf(minValue)) )) {
			return "GTE_MAX";
		} else if ((maxValue != null && !"-".equals(String.valueOf(maxValue))    
				&& (minValue != null && !"-".equals(String.valueOf(minValue))))) {
			return "MNLTELTEMX";
		} else if((maxValue==null || "-".equals(String.valueOf(maxValue))) 
				&& (minValue!=null || "-".equals(String.valueOf(minValue)))){
			return "EMPTY";
		}
    	
		return null;
	}
	private String dealEscapse(String value){
    	if(value!=null){
    		if(value.contains("'")){
    			value = value.replaceAll("'", "''");
    		}
    	}
    	return value;
    }
    /**
     * ��ȡInsert���Ƭ��--Sample������д����
     *
     * @param fieldMap
     * @param clazz
     * @param pkname
     * @param pkvalue
     * @param ncPK2LimsPkMap
     *            ncpk��limsϵͳpk��Ӧ��ϵ
     * @param ncPK2ObjectMap
     *            ���α��������NCOBJ
     * @return fieldValues[0]: �ֶ���Ƭ��<br />
     *         fieldValues[1-n]��ֵƬ��
     * @throws BusinessException
     */
    private String[] getSampleInsertSQLByMap(String pk_commisssion_h,Map<String, String> fieldMap,
                                             Class<?> clazz, String condition,Map<String, Object> ncPK2LimsPkMap,
                                             Map<String, Object> ncPK2NCObjMap,Map<String,Object> boxMap)
            throws BusinessException {
    	//ί�е�
    	CommissionHVO hvo = (CommissionHVO)ncPK2NCObjMap.get(pk_commisssion_h);
    	String[] rsArray = new String[2];
    	StringBuilder rowName = new StringBuilder();
    	StringBuilder rowValue = new StringBuilder();
    	//�޸�����
    	UFDateTime modtime = hvo.getModifiedtime()==null?hvo.getCreationtime():hvo.getModifiedtime();
    	if(modtime==null){
    		modtime = new UFDateTime();
    	}
    	rowName.append("changed_on");
    	rowValue.append("to_timestamp('"+modtime.toStdString()+"','yyyy-mm-dd hh24:mi:ss.ff')");
    	rowName.append(", ").append("date_started");
    	rowValue.append(", ").append("to_timestamp('"+modtime.toStdString()+"','yyyy-mm-dd hh24:mi:ss.ff')");
    	rowName.append(", ").append("login_date");
    	rowValue.append(", ").append("to_timestamp('"+modtime.toStdString()+"','yyyy-mm-dd hh24:mi:ss.ff')");
    	rowName.append(", ").append("recd_date");
    	rowValue.append(", ").append("to_timestamp('"+modtime.toStdString()+"','yyyy-mm-dd hh24:mi:ss.ff')");

    	//Ա����
    	String pk_user = hvo.getModifier()==null?hvo.getCreator():hvo.getModifier();
    	if(pk_user!=null){
    		String userCode = String.valueOf(dealSystemRef(CommissionHVO.class, "cuserid", pk_user));
        	rowName.append(", ").append("login_by");
        	rowValue.append(", '").append(userCode).append("' ");
        	rowName.append(", ").append("received_by");
        	rowValue.append(", '").append(userCode).append("' ");
    	}
    	
    	
    	//����
    	List<Integer> prePk = getPrePk("sample_number", "sample", 1);
    	Integer pkFirstSample = prePk.get(0);
    	boxMap.put("pkFirstSample", pkFirstSample);
    	rowName.append(", ").append("sample_number");
    	rowValue.append(", ").append(pkFirstSample).append(" ");
    	rowName.append(", ").append("original_sample");
    	rowValue.append(", ").append(pkFirstSample).append(" ");
    	
    	//SAMPLE.TEXT_ID�˴����������ɷ�ʽ�����ڱ����ǵ�һ��д�룬д������ϱ��ɫ�ĸ�ʽ(19-5673)��
    	//��������ǰ����Ʒ���࣬��ʽΪ�����-���ֵ+1��
    	
    	String sql = "select max(str2) from (SELECT REGEXP_SUBSTR(TEXT_ID,'[^-]+',1,1,'i')  STR1, "
    			+ " nvl(REGEXP_SUBSTR(TEXT_ID,'[^-]+',1,2,'i'),'1')  str2 FROM sample) origin "
    			+ " where str1 = '"+String.valueOf(modtime.getYear()).substring(2, 4)+"' order by str2 desc ";
    	Object maxNumObj = baseDao.executeQuery(sql, new ColumnProcessor());
    	int maxNum = 1;
    	if(maxNumObj!=null){
    		try{
    			maxNum = Integer.valueOf(maxNumObj.toString());
    		}catch(Exception e){
    			maxNum = 1 ;
    		}
    	}
    	rowName.append(", ").append("text_id");
    	rowValue.append(", '").append(String.valueOf(modtime.getYear()).substring(2, 4)).append("-").append(maxNum+1).append("' ");
    	
    	
    	rsArray[0] = rowName.toString();
    	rsArray[1] = rowValue.toString();
    	return rsArray;
        /*// ����ĳ���ֶε�ֵ,һ�����ݶ�Ӧarray�е�һ��
        StringBuilder[] fieldValues = null;
        IUAPQueryBS query = NCLocator.getInstance().lookup(IUAPQueryBS.class);
        List<?> srcData = (List<?>) query.retrieveByClause(clazz, condition);
        List<Integer> pkList = null;
        if (CommissionHVO.class != clazz) {
            // Ԥ����pk-����ί�е�֮��(ί�е���������billNo)
            pkList = getPrePkExtend(clazz, srcData.size());
        }
        if (null == srcData || srcData.size() <= 0) {
            return null;
        }
        fieldValues = new StringBuilder[srcData.size() + 1];
        // ����ƴ���ֶ���
        StringBuilder insertFields = new StringBuilder();
        // ������ѭ��
        for (Entry<String, String> map : fieldMap.entrySet()) {
            String fieldName = map.getKey();

            String[] fields = null;
            if (map.getValue().contains(";")) {
                fields = getWriteBackFields(map.getValue().split(";"));
            } else {
                fields = getWriteBackFields(new String[] { map.getValue() });
            }
            // times�Ǵ���һ�Զ��ϵ��
            int times = fields.length;
            for (String field : fields) {
                insertFields.append(field).append(",");
            }

            if (srcData != null && srcData.size() > 0) {

                int row = 1;
                for (Object data : srcData) {
                    ISuperVO vo = (ISuperVO) data;
                    Object unDofieldValue = vo.getAttributeValue(fieldName);
                    // �������
                    Object realValue = dealRefValue(clazz, fieldName,
                            unDofieldValue);
                    if (null == fieldValues[row]) {
                        fieldValues[row] = new StringBuilder();
                    }
                    for (int j = 0; j < times; j++) {
                        if (realValue != null) {
                            if (realValue instanceof Integer || realValue instanceof UFDouble || realValue instanceof Double) {
                                fieldValues[row].append(realValue).append(",");
                            } else if(realValue instanceof UFBoolean){
                            	if("Y".equals(String.valueOf(realValue))){
                            		fieldValues[row].append("'")
                                    .append("T")
                                    .append("',");
                            	}else{
                            		fieldValues[row].append("'")
                                    .append("F")
                                    .append("',");
                            	}
                            	
                            }else {
                                fieldValues[row].append("'")
                                        .append(dealEscapse(String.valueOf(realValue)))
                                        .append("',");
                            }
                        } else {
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
        if (fieldValues != null) {
            rsString = new String[fieldValues.length];
            for (int i = 0; i < fieldValues.length; i++) {
                if (CommissionHVO.class != clazz) {
                    // д�������͸�����(ֻ֧�ֵ�����,Ҫ������,��Ҫ�ο�������ֶ�����߼�)
                    if (0 == i) {
                        // ����ί�е��ӱ��ֶ���Ҫд�����
                        if (CommissionRVO.class == clazz) {
                            Map<String, String> LimsFiled2ncFieldMap = getBodySimple2ChildrenMapping();
                            for (String limsField : LimsFiled2ncFieldMap
                                    .keySet()) {
                                fieldValues[i]
                                        .append(getWriteBackFields(new String[] { limsField })[0])
                                        .append(",");
                            }
                        }
                        fieldValues[i].append(
                                getWriteBackFields(new String[] { LIMS_PK_MAP_EXTEND
                                        .get(clazz) })[0]).append(",");
                        fieldValues[i]
                                .append(getWriteBackFields(new String[] { LIMS_FK_MAP_EXTEND
                                        .get(clazz) })[0]);

                    } else {
                        // ��ȡ�ϲ������:
                        String fatherPk = (String) (((ISuperVO) srcData
                                .get(i - 1)).getAttributeValue(getWriteBackFields(new String[] { NC_FK_MAP
                                .get(clazz) })[0]));

                        String realfatherPk = fatherPk;

                        // ����ί�е��ӱ��ֶ���Ҫд�����
                        if (CommissionRVO.class == clazz) {
                            Map<String, String> LimsFiled2ncFieldMap = getBodySimple2ChildrenMapping();
                            for (String limsField : LimsFiled2ncFieldMap
                                    .keySet()) {
                                // �ϲ��NCVO ��Ҫ��д���ӱ���ֶ�
                                String ncField = LimsFiled2ncFieldMap
                                        .get(limsField);
                                // ��ȡ�ϲ������
                                ISuperVO fatherObj = (ISuperVO) ncPK2NCObjMap
                                        .get(realfatherPk);
                                // ��ȡ��Ҫ��д��ֵ
                                Object oldFieldValue = fatherObj == null ? null
                                        : fatherObj.getAttributeValue(ncField);
                                // �������
                                Object realValue = dealRefValue(clazz, ncField,
                                        oldFieldValue);
                                if (null == realValue
                                        || realValue instanceof Integer || realValue instanceof UFDouble || realValue instanceof Double) {
                                    fieldValues[i].append(realValue)
                                            .append(",");
                                } else if(realValue instanceof UFBoolean){
                                	if("Y".equals(String.valueOf(realValue))){
                                		fieldValues[i].append("'")
                                        .append("T")
                                        .append("',");
                                	}else{
                                		fieldValues[i].append("'")
                                        .append("F")
                                        .append("',");
                                	}
                                	
                                }else {
                                    fieldValues[i].append("'")
                                            .append(dealEscapse(String.valueOf(realValue)))
                                            .append("',");
                                }
                            }
                        }
                        if (null == pkList.get(i - 1)
                                || pkList.get(i - 1) instanceof Integer) {
                            // pk
                            fieldValues[i].append(pkList.get(i - 1))
                                    .append(",");
                        } else {
                            // pk
                            fieldValues[i].append("'").append(pkList.get(i - 1))
                                    .append("',");
                        }

                        if (null == ncPK2LimsPkMap.get(realfatherPk)
                                || ncPK2LimsPkMap.get(realfatherPk) instanceof Integer) {
                            // ���
                            fieldValues[i].append(ncPK2LimsPkMap.get(realfatherPk));
                        } else {
                            // pk
                            // ���
                            fieldValues[i].append("'").append(ncPK2LimsPkMap.get(realfatherPk)).append("'");
                        }



                    }

                } else {
                    // ί�е���������������billno,û�и�����
                    if (i > 0) {

                    }
                    fieldValues[i].setLength(fieldValues[i].length() - 1);
                }

                rsString[i] = fieldValues[i].toString();
            }

        }*/
        
    }


    /**
     * ����ʵ���ʵ����������lims��pk
     *
     * @param clazz
     * @param size
     * @return
     * @throws DAOException
     */
    private List<Integer> getPrePk(Class<?> clazz, int size)
            throws DAOException {
        List<Integer> rs = new ArrayList<>();
        // ��ȡ��
        String tableName = null;
        String pk_filed = null;
        if (LIMS_PK_MAP.get(clazz) != null) {
            tableName = LIMS_PK_MAP.get(clazz).split("\\.")[0];
            pk_filed = LIMS_PK_MAP.get(clazz).split("\\.")[1];
        }
        String pkFileld = null;
        if(TaskSVO.class == clazz){
            pkFileld = "RESULT_NUMBER";
        }else{
            pkFileld = "SEQ_NUM";
        }
        if (tableName != null) {
            String sql = "select MAX("+pkFileld+") from " + tableName;
            Integer startNum = (Integer) baseDao.executeQuery(sql,
                    new ColumnProcessor());
            if (startNum != null && startNum >= 0) {
                for (int i = 1; i <= size; i++) {
                    rs.add(startNum + i);
                }
            }
        }
        return rs;
    }


    /**
     * Ԥ����pk
     * @param tableName
     * @param size
     * @return
     * @throws DAOException
     */
    private List<Integer> getPrePk(String pkFiled,String tableName, int size) throws DAOException {
        List<Integer> rs = new ArrayList<>();

        if (tableName != null) {
            String sql = "select max("+pkFiled+")+1  from " + tableName;
            Integer startNum = (Integer) baseDao.executeQuery(sql,
                    new ColumnProcessor());
            if (startNum != null && startNum >= 0) {
                for (int i = 1; i <= size; i++) {
                    rs.add(startNum + i);
                }
            }
        }
        return rs;
    }

    /**
     * ����ʵ���ʵ����������lims��pk
     *
     * @param clazz
     * @param size
     * @return
     * @throws DAOException
     */
    private List<Integer> getPrePkExtend(Class<?> clazz, int size)
            throws DAOException {
        List<Integer> rs = new ArrayList<>();
        // ��ȡ��
        String tableName = null;
        String pk_filed = null;
        if (LIMS_PK_MAP_EXTEND.get(clazz) != null) {
            tableName = LIMS_PK_MAP_EXTEND.get(clazz).split("\\.")[0];
            pk_filed = LIMS_PK_MAP_EXTEND.get(clazz).split("\\.")[1];
        }
        if (tableName != null) {
            String sql = "select max("+pk_filed+") from " + tableName;
            Integer startNum = (Integer) baseDao.executeQuery(sql,
                    new ColumnProcessor());
            if (startNum != null && startNum >= 0) {
                for (int i = 1; i <= size; i++) {
                    rs.add(startNum + i);
                }
            }
        }
        return rs;
    }

    // //�����������,��Ҫ�������񵥱�ͷ����ת����ί�е���������
    private String getRealFatherPk(Class<?> clazz, String fatherPk,
                                   Map<String, String> taskPk2CommissionPkMap) {
        if (TaskBVO.class == clazz) {
            fatherPk = taskPk2CommissionPkMap.get(fatherPk);
        }
        return fatherPk;
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
            
            headMapping.put("pk_commissiontype", "project.C_APPLY_TYPE;project.template");// ί�е�����
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
            headMapping.put("identificationtype",
                    "project.c_identification_type");// ��Ʒ��������
            headMapping
                    .put("certificationtype", "project.c_certification_type");// ��֤����
            headMapping.put("itemnumber", "project.c_item_number");// ��Ŀ��
            headMapping.put("creationtime", "project.date_created");// �Ƶ�ʱ��
            headMapping.put("modifiedtime", "project.date_updated");// �Ƶ�ʱ��
        }

        return headMapping;
    }

    /**
     * ί�е��ӱ�
     *
     * @return
     */
    public Map<String, String> getBodySampleMapping() {
        if (bodySampleMapping == null) {
            bodySampleMapping = new HashMap<String, String>();

            bodySampleMapping.put("pk_productserial", "C_PROJ_LOGIN_SAMPLE.product_series");// ��Ʒϵ��
            bodySampleMapping.put("pk_enterprisestandard", "C_PROJ_LOGIN_SAMPLE.product_standard");// ��ҵ��׼
            bodySampleMapping.put("typeno", "C_PROJ_LOGIN_SAMPLE.prodname");// ����ͺ�
            bodySampleMapping.put("pk_productspec", "C_PROJ_LOGIN_SAMPLE.production_spec");// ����
            bodySampleMapping.put("pk_structuretype", "C_PROJ_LOGIN_SAMPLE.structure_type");// �ṹ����
            bodySampleMapping.put("contacttype", "C_PROJ_LOGIN_SAMPLE.contact_type");// ��������
            bodySampleMapping.put("quantity", "C_PROJ_LOGIN_SAMPLE.sample_quantity");// ��Ʒ����
            bodySampleMapping.put("manufacturer", "C_PROJ_LOGIN_SAMPLE.manufacturer");// ������
            bodySampleMapping.put("pk_contactbrand", "C_PROJ_LOGIN_SAMPLE.contact_brand");// �����ƺ�
            bodySampleMapping.put("contactmodel", "C_PROJ_LOGIN_SAMPLE.contact_model");// �����ͺ�
            bodySampleMapping.put("productstage", "C_PROJ_LOGIN_SAMPLE.product_stage");// �¶�
            bodySampleMapping.put("pk_samplegroup", "C_PROJ_LOGIN_SAMPLE.sample_group");// ��Ʒ���
            bodySampleMapping.put("otherinfo", "C_PROJ_LOGIN_SAMPLE.other_req");// ������Ϣ
        }

        return bodySampleMapping;
    }

    /**
     * ί�е��ӱ�-�����дSample��
     *
     * @return
     */
    public Map<String, String> getBodySampleExtendMapping() {
        if (bodySampleExtendMapping == null) {
            bodySampleExtendMapping = new HashMap<String, String>();

            bodySampleExtendMapping.put("pk_productserial", "Sample.PRODUCT");// ��Ʒϵ��
            bodySampleExtendMapping.put("pk_structuretype", "Sample.PRODUCT_GRADE");// �ṹ����


        }

        return bodySampleExtendMapping;
    }

    /**
     * ί�е��ӱ�->���Ļ�д�ֶ�
     *
     * @return
     */
    public Map<String, String> getBodySimple2ChildrenMapping() {
        if (bodySimple2ChildrenMapping == null) {
            bodySimple2ChildrenMapping = new HashMap<String, String>();
            bodySimple2ChildrenMapping.put("c_proj_para_a.product_standard",
                    "pk_enterprisestandard");// ��ҵ��׼
            bodySimple2ChildrenMapping.put("c_proj_para_a.production_spec",
                    "pk_productspec");// ����
            bodySimple2ChildrenMapping.put("C_PROJ_PARA_A.structure_type",
                    "pk_structuretype");// �ṹ����
            bodySimple2ChildrenMapping.put("C_PROJ_PARA_A.stage",
                    "productstage");// �¶�
            
            bodySimple2ChildrenMapping.put("c_proj_para_a.prodname",
                    "typeno");// ����ͺ� 
            bodySimple2ChildrenMapping.put("c_proj_para_a.contact_type",
                    "contacttype");// ��������	contacttype
            
        }

        return bodySimple2ChildrenMapping;
    }

    public Map<String, String> getGrandBeforeMapping() {
        if (grandBeforeMapping == null) {
            grandBeforeMapping = new HashMap<String, String>();

            grandBeforeMapping.put("analysisname", "C_PROJ_PARA_A.analysis");// ʵ��ǰ��������
            grandBeforeMapping.put("pk_samplegroup",
                    "C_PROJ_PARA_A.sample_group");// ��Ʒ���
            grandBeforeMapping.put("pk_component", "C_PROJ_PARA_A.component");// ������
            grandBeforeMapping.put("stdmaxvalue", "C_PROJ_PARA_A.max_value");// ���ֵ
            grandBeforeMapping.put("stdminvalue", "C_PROJ_PARA_A.min_value");// ��Сֵ
            grandBeforeMapping.put("unitname", "C_PROJ_PARA_A.units");// ��λ
            grandBeforeMapping.put("judgeflag", "C_PROJ_PARA_A.check_spec");// �Ƿ��ж�
            grandBeforeMapping.put("testflag", "C_PROJ_PARA_A.is_added");// �Ƿ����
            grandBeforeMapping.put("rowno", "C_PROJ_PARA_A.order_number");// ���

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
            bodyTaskMapping.put("sampleallocationsource", "c_proj_task.assigned_sample");// ��Ʒ����
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
            //grandConditionMapping.put("textvalue", "result.entry");// ֵ //TODO
            // ����յ�����,�ݻ�,��Ϊ������ֵ��Ҫ�ϲ�
            grandConditionMapping.put("refvalue;", "result.entry");// ֵ
            grandConditionMapping.put("unit", "result.units");// ��λ
            grandConditionMapping.put("formatted_entry", "result.formatted_entry");// ��ʽ��ֵ
            grandConditionMapping.put("min_limit", "result.min_limit");// ��Сֵ
            grandConditionMapping.put("max_limit", "result.max_limit");// ���ֵ
        }

        return grandConditionMapping;
    }

    /**
     * ί�е����
     *
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

    /**
     * ����ʵ��,�ֶ���,����pk��ȡ���յ�ֵ
     *
     * @param clazz
     *            NCʵ����
     * @param fieldName
     *            �ֶ���
     * @param fieldValue
     *            ����PK
     * @return ������ֶ��ǲ���,�򷵻ز��յ�ֵ,������ǲ���,ԭ�ⲻ������fieldValue
     * @throws BusinessException
     */
    private Object dealRefValue(Class<?> clazz, String fieldName,
                                Object fieldValue) throws BusinessException {
    	//ϵͳ�������⴦��
    	if(isSystemRef(clazz,fieldName)){
    		return dealSystemRef(clazz,fieldName,fieldValue);
    	}
    	//��Щ����д���ӱ���ֶ�,��Ҫ�����͸ĳɸ���
    	if(clazz==CommissionRVO.class){
    		switch(fieldName){
    		case "pk_enterprisestandard":
    			clazz=CommissionBVO.class;
    			break;
    		case "pk_productspec":
    			clazz=CommissionBVO.class;
    			break;
    		case "pk_structuretype":
    			clazz=CommissionBVO.class;
    			break;
    		default: break;
    		}
    	}
    	
    	
        String strSQL = "select reftype from md_property ppt " + "inner join md_class cls on cls.id = ppt.classid "
                + "inner join pub_billtemplet_b btb on btb.metadataproperty = 'qcco.' || cls.name || '.' || ppt.name "
                + "where btb.reftype is not null and cls.fullclassname = '" + clazz.getName() + "' and ppt.name='"
                + fieldName + "'";

        String refType = (String) baseDao.executeQuery(strSQL, new ColumnProcessor());

        if (!StringUtils.isEmpty(refType)) {
            refType = refType.replace("<", "").replace(">", "").split(",")[0].trim();
        }
        
        try {
            if(TaskSVO.class==clazz && "conditionstatus".equals(fieldName)){
                if(null == fieldValue){
                    fieldValue = 0;
                }else if("��¼��".equals(fieldValue)){
                    fieldValue = 1;
                }else {
                    fieldValue = 2;
                }
            }else if(CommissionHVO.class==clazz && "reportlang".equals(fieldName)){
            	//��ϵͳ������� : �������� ��Ҫд�����(��code)
            	return dealNotSystemRefButSpec(clazz,fieldName,fieldValue);
            }/*else if(CommissionHVO.class==clazz && "pk_commissiontype".equals(fieldName)){
            	//��ϵͳ������� : ί�е����� ����
            	return dealNotSystemRefButSpec(clazz,fieldName,fieldValue);
            }*/
            
            if(null == refType){
                return fieldValue;
            }
            Class<?> refModalClass = Class.forName(refType);

            AbstractRefModel refModel = null;
            try {
                refModel = (AbstractRefModel) refModalClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new BusinessException(e.getMessage());
            }

            Vector value = null;
            if (refModel != null) {
                value = refModel.matchPkData((String) fieldValue);
            }

            if (value != null) {
            	if(NEED_CODE_WRITE_BACK_MAP.get(clazz)!=null && NEED_CODE_WRITE_BACK_MAP.get(clazz).contains(fieldName.toLowerCase())){
            		//д��code
            		fieldValue = ((Vector) value.get(0)).get(0);
            	}else{
            		//д��name
            		fieldValue = ((Vector) value.get(0)).get(1);
            	}
            }
        } catch (Exception e) {
            Logger.error(e);
            return fieldValue;
        }

        return fieldValue;
    }
    //��ϵͳ���ջ���
    private Map<String,String> notSystemRefCacheMap = new HashMap<>();
    private Object dealNotSystemRefButSpec(Class<?> clazz, String fieldName, Object fieldValue) throws BusinessException {
    	//�����СΪ100 
		if (notSystemRefCacheMap.size() > 100) {
			notSystemRefCacheMap = new HashMap<>();
		}

		if (CommissionHVO.class == clazz && "reportlang".equals(fieldName)) {
			// ��������д�� ����
			if (notSystemRefCacheMap.containsKey(String.valueOf(fieldValue))) {
				return notSystemRefCacheMap.get(String.valueOf(fieldValue));
			}
			String newValue = (String) baseDao.executeQuery("select LIS_NAME from NC_REPORT_LANG WHERE ISENABLE=1 and PK_REPORT_LANG = '" + String.valueOf(fieldValue)
					+ "' ", new ColumnProcessor());
			notSystemRefCacheMap.put(String.valueOf(fieldValue), newValue);
			return newValue;
		}/*else if(CommissionHVO.class==clazz && "pk_commissiontype".equals(fieldName)){
			// ί�е�����д�� ����
			if (notSystemRefCacheMap.containsKey(String.valueOf(fieldValue))) {
				return notSystemRefCacheMap.get(String.valueOf(fieldValue));
			}
			String newValue = (String) baseDao.executeQuery("select NAME from NC_PROJ_TYPE WHERE ISENABLE=1 and PK_PROJ_TYPE = '"
					+ String.valueOf(fieldValue) + "' ", new ColumnProcessor());
			notSystemRefCacheMap.put(String.valueOf(fieldValue), newValue);
			return newValue;
		}*/
		return fieldValue;
		
	}
	//ϵͳ���ջ���
    private Map<String,String> systemRefCacheMap = new HashMap<>();
    private Object dealSystemRef(Class<?> clazz, String fieldName, Object fieldValue) throws BusinessException {
    	if(IS_SYSREF_MAP.get(clazz)!=null && IS_SYSREF_MAP.get(clazz).keySet()!=null && IS_SYSREF_MAP.get(clazz).get(fieldName.toLowerCase())!=null){
    		String refCode = IS_SYSREF_MAP.get(clazz).get(fieldName.toLowerCase());
    		//�����СΪ10 
    		if(systemRefCacheMap.size() > 10){
    			systemRefCacheMap = new HashMap<>();
    		}
    		if(refCode!=null){
    			
    			if("org_orgs".equals(refCode)){
    				//��֯���� name
    				if(systemRefCacheMap.containsKey(String.valueOf(fieldValue))){
    					return systemRefCacheMap.get(String.valueOf(fieldValue));
    				}
    				String newValue = 
    						(String)baseDao.executeQuery("select name from org_orgs where pk_org = '"+String.valueOf(fieldValue)+"' ", new ColumnProcessor());
    				systemRefCacheMap.put(String.valueOf(fieldValue), newValue);
    				return newValue;
    			}else if("bd_psndoc".equals(refCode)){
    				//��Ա���� code
    				if(systemRefCacheMap.containsKey(String.valueOf(fieldValue))){
    					return systemRefCacheMap.get(String.valueOf(fieldValue));
    				}
    				String sql = "select nvl(job.psncode,sm.user_code) psncode "
    						+" from  sm_user sm  "
    						+" left join (select * from bd_psnjob jobinner where ismainjob = 'Y' ) job on rownum = 1 and job.pk_psndoc = sm.pk_psndoc   "
    						+" where sm.cuserid = '"+String.valueOf(fieldValue)+"' or sm.pk_psndoc = '"+String.valueOf(fieldValue)+"'";
    				String newValue = 
    						(String)baseDao.executeQuery(sql, new ColumnProcessor());
    				systemRefCacheMap.put(String.valueOf(fieldValue), newValue);
    				return newValue;
    			}else if("org_dept".equals(refCode)){
    				//���Ų��� name
    				if(systemRefCacheMap.containsKey(String.valueOf(fieldValue))){
    					return systemRefCacheMap.get(String.valueOf(fieldValue));
    				}
    				String newValue = 
    						(String)baseDao.executeQuery("select name from org_dept where pk_dept = '"+String.valueOf(fieldValue)+"' ", new ColumnProcessor());
    				systemRefCacheMap.put(String.valueOf(fieldValue), newValue);
    				return newValue;
    			}
    		}
    	}
		return fieldValue;
	}

	/**
     * �ж��Ƿ�ϵͳ����
     * @param clazz
     * @param fieldName
     * @return
     */
    /**
     * ϵͳ���ն��ձ�
     */
    private static Map<Class<?>,HashMap<String,String>> IS_SYSREF_MAP = new HashMap<>();
    {
    	HashMap<String,String> COMMISSIONHVO_MAP = new HashMap<>();
    	COMMISSIONHVO_MAP.put("pk_payer","org_orgs");
    	COMMISSIONHVO_MAP.put("cuserid","bd_psndoc");
    	COMMISSIONHVO_MAP.put("pk_owner","org_orgs");
    	COMMISSIONHVO_MAP.put("pk_dept","org_dept");
		IS_SYSREF_MAP.put(CommissionHVO.class, COMMISSIONHVO_MAP);

    }
	private boolean isSystemRef(Class<?> clazz, String fieldName) {
		if(IS_SYSREF_MAP.get(clazz)!=null && IS_SYSREF_MAP.get(clazz).keySet()!=null && IS_SYSREF_MAP.get(clazz).keySet().contains(fieldName.toLowerCase())){
			return true;
		}
		return false;
	}
	/**
     * sample�����һ�λ�д�̶�ֵ
     */
    private static Map<String,String> SAMPLE_STATIC_MAP = new HashMap<>();
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
    	SAMPLE_STATIC_MAP.put("OLD_STATUS", "'I'");
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
    	SAMPLE_STATIC_MAP.put("STATUS", "'P'");
    	SAMPLE_STATIC_MAP.put("STORAGE_LOC_NO", "0.00");
    	SAMPLE_STATIC_MAP.put("T_CONTRACT_TESTS", "'F'");
    	SAMPLE_STATIC_MAP.put("T_LOGIN_VERIFIED", "'F'");
    	SAMPLE_STATIC_MAP.put("TEMPLATE", "'HF-CONDITION'");
    	SAMPLE_STATIC_MAP.put("TRANS_NUM", "0.00");
    	
    	
    	
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
    private static Map<String,String> TEST_STATIC_MAP = new HashMap<>();
    {
    	TEST_STATIC_MAP.put("CHARGE_ENTRY", "0.00");
    	TEST_STATIC_MAP.put("REPLICATE_COUNT", "1.00");
    	TEST_STATIC_MAP.put("STATUS", "'P'");
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
    	TEST_STATIC_MAP.put("T_TURNAROUND_CHARG", "0.00");
    	TEST_STATIC_MAP.put("T_TURNAROUND_MET", "'F'");
    	TEST_STATIC_MAP.put("TRANS_NUM", "0.00");
    	
    	
    	//����д��
    	TEST_STATIC_MAP.put("STATUS", "'I'");
    	TEST_STATIC_MAP.put("VARIATION", "null");
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
