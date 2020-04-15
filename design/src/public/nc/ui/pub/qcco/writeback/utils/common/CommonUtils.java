package nc.ui.pub.qcco.writeback.utils.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.logging.Logger;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapListProcessor;
import nc.jdbc.framework.processor.MapProcessor;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.pub.qcco.writeback.utils.WriteBackProcessData;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.ApprovalInfo;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.CProjLoginSample;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.CProjTask;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.ParaA;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.ParaB;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.Project;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.Result;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.Sample;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.Test;
import nc.ui.pub.qcco.writeback.utils.mapping.FirstWriteBackStaticMaping;
import nc.ui.pub.qcco.writeback.utils.mapping.SecWriteBackStaticMaping;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ISuperVO;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.commission.CommissionHVO;
import nc.vo.qcco.commission.CommissionRVO;
import nc.vo.qcco.task.TaskBVO;
import nc.vo.qcco.task.TaskRVO;
import nc.vo.qcco.task.TaskSVO;

import org.apache.commons.lang.StringUtils;


/**
 * 公用方法
 * @author 91967
 *
 */
public class CommonUtils {

	private BaseDAO baseDao = new BaseDAO();
	
	private WriteBackProcessData processData;
	
	//一些缓存 start
	private Map<String,String> analysisToVersion = new HashMap<>();
	
	private Map<String,String> analysisToLab = new HashMap<>();
	
	private Map<String,String> analysisToMethod = new HashMap<>();
	
	private Map<String,Map<String,Object>> nameToAnalysis = new HashMap<>();
	
	private Map<String,List<Map<String,Object>>> analysisToResultComponentMap = new HashMap<>();
	
	private Map<String,Map<String,Object>> analysisNameToTestComponent = new HashMap<>();
	
	private Map<String,String> listKeyToName = new HashMap<>();
	
	//一些缓存 end
	
	//用于SQL转换
	private StringBuilder sb = new StringBuilder();
	/**
	 * 用于一对多的回写
	 * @param splitFields
	 * @return
	 */
	public String[] getWriteBackFields(String[] splitFields) {
		List<String> fieldList = new ArrayList<String>();
		for (String field : splitFields) {
			fieldList.add(field.split("\\.")[1]);
		}
		return fieldList.toArray(new String[0]);
	}
	/**
     * 根据实体和实体数量申请lims的pk
     *
     * @param clazz
     * @param size
     * @return
     * @throws DAOException
     */
    public List<Integer> getPrePk(Class<?> clazz, int size)
            throws DAOException {
        List<Integer> rs = new ArrayList<>();
        // 获取表
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
     * 预申请pk
     * @param tableName
     * @param size
     * @return
     * @throws DAOException
     */
    public List<Integer> getPrePk(String pkFiled,String tableName, int size) throws DAOException {
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
     * 根据实体和实体数量申请lims的pk
     *
     * @param clazz
     * @param size
     * @return
     * @throws DAOException
     */
    public List<Integer> getPrePkExtend(Class<?> clazz, int size)
            throws DAOException {
        List<Integer> rs = new ArrayList<>();
        // 获取表
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
	
	public String dealEscapse(String value){
    	if(value!=null){
    		if(value.contains("'")){
    			value = value.replaceAll("'", "''");
    		}
    	}
    	return value;
    }
	
	/**
     * 根据实体,字段名,参照pk获取参照的值
     *
     * @param clazz
     *            NC实体类
     * @param fieldName
     *            字段名
     * @param fieldValue
     *            参照PK
     * @return 如果该字段是参照,则返回参照的值,如果不是参照,原封不动返回fieldValue
     * @throws BusinessException
     */
    @SuppressWarnings("rawtypes")
	public Object dealRefValue(Class<?> clazz, String fieldName,
                                Object fieldValue) throws BusinessException {
    	//系统参照特殊处理
    	if(isSystemRef(clazz,fieldName)){
    		return dealSystemRef(clazz,fieldName,fieldValue);
    	}
    	//有些父表写入子表的字段,需要把类型改成父表
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
                }else if("已录入".equals(fieldValue)){
                    fieldValue = 1;
                }else {
                    fieldValue = 2;
                }
            }else if(CommissionHVO.class==clazz && "reportlang".equals(fieldName)){
            	//非系统特殊参照 : 报告语言 需要写入编码(非code)
            	return dealNotSystemRefButSpec(clazz,fieldName,fieldValue);
            }/*else if(CommissionHVO.class==clazz && "pk_commissiontype".equals(fieldName)){
            	//非系统特殊参照 : 委托单类型 中文
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
            		//写入code
            		fieldValue = ((Vector) value.get(0)).get(0);
            	}else{
            		//写入name
            		fieldValue = ((Vector) value.get(0)).get(1);
            	}
            }
        } catch (Exception e) {
            Logger.error(e);
            return fieldValue;
        }

        return fieldValue;
    }
    
	private boolean isSystemRef(Class<?> clazz, String fieldName) {
		if(IS_SYSREF_MAP.get(clazz)!=null && IS_SYSREF_MAP.get(clazz).keySet()!=null && IS_SYSREF_MAP.get(clazz).keySet().contains(fieldName.toLowerCase())){
			return true;
		}
		return false;
	}
    
	//系统参照缓存
    private Map<String,String> systemRefCacheMap = new HashMap<>();
    public Object dealSystemRef(Class<?> clazz, String fieldName, Object fieldValue) throws BusinessException {
    	if(IS_SYSREF_MAP.get(clazz)!=null && IS_SYSREF_MAP.get(clazz).keySet()!=null && IS_SYSREF_MAP.get(clazz).get(fieldName.toLowerCase())!=null){
    		String refCode = IS_SYSREF_MAP.get(clazz).get(fieldName.toLowerCase());
    		//缓存大小为10 
    		if(systemRefCacheMap.size() > 10){
    			systemRefCacheMap = new HashMap<>();
    		}
    		if(refCode!=null){
    			
    			if("org_orgs".equals(refCode)){
    				//组织参照 name
    				if(systemRefCacheMap.containsKey(String.valueOf(fieldValue))){
    					return systemRefCacheMap.get(String.valueOf(fieldValue));
    				}
    				String newValue = 
    						(String)baseDao.executeQuery("select name from org_orgs where pk_org = '"+String.valueOf(fieldValue)+"' ", new ColumnProcessor());
    				systemRefCacheMap.put(String.valueOf(fieldValue), newValue);
    				return newValue;
    			}else if("bd_psndoc".equals(refCode)){
    				//人员参照 code
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
    				//部门参照 name
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
    
  //非系统参照缓存
    private Map<String,String> notSystemRefCacheMap = new HashMap<>();
    private Object dealNotSystemRefButSpec(Class<?> clazz, String fieldName, Object fieldValue) throws BusinessException {
    	//缓存大小为100 
		if (notSystemRefCacheMap.size() > 100) {
			notSystemRefCacheMap = new HashMap<>();
		}

		if (CommissionHVO.class == clazz && "reportlang".equals(fieldName)) {
			// 报告语言写入 编码
			if (notSystemRefCacheMap.containsKey(String.valueOf(fieldValue))) {
				return notSystemRefCacheMap.get(String.valueOf(fieldValue));
			}
			String newValue = (String) baseDao.executeQuery("select LIS_NAME from NC_REPORT_LANG WHERE ISENABLE=1 and PK_REPORT_LANG = '" + String.valueOf(fieldValue)
					+ "' ", new ColumnProcessor());
			notSystemRefCacheMap.put(String.valueOf(fieldValue), newValue);
			return newValue;
		}/*else if(CommissionHVO.class==clazz && "pk_commissiontype".equals(fieldName)){
			// 委托单类型写入 编码
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
	

	/**
     * 判断是否系统参照
     * @param clazz
     * @param fieldName
     * @return
     */
    /**
     * 系统参照对照表
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
    /**
     * 参照需要code写入的字段
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
    // lims主键
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
 // lims主键-额外回写的表
    public static Map<Class<?>, String> LIMS_PK_MAP_EXTEND;
    {
        LIMS_PK_MAP_EXTEND = new HashMap<>();
        LIMS_PK_MAP_EXTEND.put(CommissionBVO.class, "Sample.SAMPLE_NUMBER");
    }
    
    /**
     * 处理NC字段的值
     * @param unDofieldValue
     * @return
     * @throws BusinessException 
     */
	public Object getRealValue(Object unDofieldValue,String fieldName,Class<?> clazz) throws BusinessException {
		
		// 处理参照
		Object realValue = 
				dealRefValue(clazz, fieldName, unDofieldValue);

		if (null != realValue && ("creationtime".equals(fieldName) || "modifiedtime".equals(fieldName))) {
			realValue = "to_timestamp('" + realValue + "','yyyy-mm-dd hh24:mi:ss.ff')";
		}
		if (null != realValue && ("productdate".equals(fieldName))) {
			realValue = "to_timestamp('" + realValue + "','yyyy-mm-dd')";
		}
		if (realValue instanceof UFBoolean) {
			if ("Y".equals(String.valueOf(realValue))) {
				realValue = "T";
			} else {
				realValue = "F";
			}
		}
		
		return realValue;
	}
	/**
	 * 通过nc主键获取与之对应的LIMS主键
	 * @param fatherPk
	 * @param clazz
	 * @return
	 */
	public Object getLIMSPKByNCPK(String fatherPk, Class clazz) {
		Object LIMSPk = null;
		if(clazz == CommissionBVO.class){
			ISuperVO[] vos = processData.getAggCommissionHVO().getChildren(CommissionBVO.class);
			
			for(int i = 0; i<vos.length; i++){
				if(vos[i]!=null && fatherPk.equals(vos[i].getPrimaryKey())){
					//LIMS样品组  顺序和委托单子表一致,所以按index 对应
					LIMSPk = (Integer)processData.getLoginSampleList().get(i).getAttributeValue("seq_num");
				}
			}
		}else if(clazz == TaskBVO.class){
			ISuperVO[] vos = processData.getAggTaskHVO().getChildren(TaskBVO.class);
			
			for(int i = 0; i<vos.length; i++){
				if(vos[i]!=null && fatherPk.equals(vos[i].getPrimaryKey())){
					//LIMS样品组  顺序和委托单子表一致,所以按index 对应
					LIMSPk = (Integer)processData.getTaskList().get(i).getAttributeValue("seq_num");
				}
			}
		}
		
		return LIMSPk;
	}
	/**
	 * 根据ncpk 获取ncVO
	 * @param fatherPk
	 * @param processData 
	 * @param class1
	 * @return
	 */
	public ISuperVO getNCObjByPK(String fatherPk, Class clazz) {
		ISuperVO rtn = null;
		if(clazz == CommissionBVO.class){
			ISuperVO[] bvos = 
					processData.getAggCommissionHVO().getChildren(CommissionBVO.class);
			for(ISuperVO bvo : bvos){
				if(bvo!=null && fatherPk.equals(bvo.getPrimaryKey())){
					return bvo;
				}
			}
		}else if(clazz == TaskBVO.class){
			ISuperVO[] bvos = 
					processData.getAggTaskHVO().getChildren(TaskBVO.class);
			for(ISuperVO bvo : bvos){
				if(bvo!=null && fatherPk.equals(bvo.getPrimaryKey())){
					return bvo;
				}
			}
		}
		
		return rtn;
	}
	/**
	 * 根据ncpk 获取ncVO在数组中的index
	 * @param fatherPk
	 * @param processData 
	 * @param class1
	 * @return
	 */
	public Integer getNCObjIndexByPK(String fatherPk, Class clazz) {
		if(clazz == CommissionBVO.class){
			ISuperVO[] bvos = 
					processData.getAggCommissionHVO().getChildren(CommissionBVO.class);
			for(int i = 0 ; i< bvos.length;i++){
				if(bvos[i]!=null && fatherPk.equals(bvos[i].getPrimaryKey())){
					return i;
				}
			}
		}else if(clazz == TaskBVO.class){
			ISuperVO[] bvos = 
					processData.getAggTaskHVO().getChildren(TaskBVO.class);
			for(int i = 0 ; i< bvos.length;i++){
				if(bvos[i]!=null && fatherPk.equals(bvos[i].getPrimaryKey())){
					return i;
				}
			}
		}
		
		
		return -1;
	}
	
	
	
	public void setData(WriteBackProcessData data) {
		this.processData = data;
	}
    
	
	

	/**
	 * 把此数据体转换成lims系统的SQL
	 * @return
	 */
	public List<String> toLIMSSQL(){
		List<String> rs = new ArrayList<>();
		
		//project
		if(processData.getProject()!=null){
			List<SuperVO> temp = new ArrayList<>();
			temp.add(processData.getProject());
			rs.addAll(VOToInserSQL(temp,"project",FirstWriteBackStaticMaping.COMMISSION_HEARD_STATIC_MAP));
		}
		//样品组
		if(processData.getLoginSampleList()!=null && processData.getLoginSampleList().size() > 0){
			List<SuperVO> temp = new ArrayList<>();
			temp.addAll(processData.getLoginSampleList());
			rs.addAll(VOToInserSQL(temp,"c_proj_login_sample",FirstWriteBackStaticMaping.COMMISSION_BODY_STATIC_MAP));
		}
		//实验前
		if(processData.getParaAListMap()!=null && processData.getParaAListMap().size() > 0){
			List<SuperVO> temp = new ArrayList<>();
			for(List<ParaA>  paraaList : processData.getParaAListMap().values()){
				temp.addAll(paraaList);
			}
			rs.addAll(VOToInserSQL(temp,"c_proj_para_a",null));
		}
		//任务
		if(processData.getTaskList()!=null && processData.getTaskList().size() > 0){
			List<SuperVO> temp = new ArrayList<>();
			temp.addAll(processData.getTaskList());
			rs.addAll(VOToInserSQL(temp,"c_proj_task",FirstWriteBackStaticMaping.TASK_BODY_STATIC_MAP));
		}
		//实验后
		if(processData.getParaBListMap()!=null && processData.getParaBListMap().size() > 0){
			List<SuperVO> temp = new ArrayList<>();
			for(List<ParaB> parabList : processData.getParaBListMap().values()){
				temp.addAll(parabList);
			}
			
			rs.addAll(VOToInserSQL(temp,"c_proj_task_para_b",null));
		}
		//sample第一次
		if(processData.getFirstSampleList()!=null && processData.getFirstSampleList().size() > 0){
			List<SuperVO> temp = new ArrayList<>();
			temp.addAll(processData.getFirstSampleList());
			rs.addAll(VOToInserSQL(temp,"sample",FirstWriteBackStaticMaping.SAMPLE_STATIC_MAP));
		}
		//test第一次
		if(processData.getFirstTestList()!=null && processData.getFirstTestList().size() > 0){
			List<SuperVO> temp = new ArrayList<>();
			temp.addAll(processData.getFirstTestList());
			rs.addAll(VOToInserSQL(temp,"test",FirstWriteBackStaticMaping.TEST_STATIC_MAP));
		}
		//result第一次
		if(processData.getFirstResultListMap()!=null && processData.getFirstResultListMap().size() > 0){
			List<SuperVO> temp = new ArrayList<>();
			for(List<Result> resultList : processData.getFirstResultListMap().values()){
				temp.addAll(resultList);
			}
			rs.addAll(VOToInserSQL(temp,"result",FirstWriteBackStaticMaping.TASK_CONDITION_STATIC_MAP));
		}
		
		// sample第二次
		if (processData.getSecSampleListMap() != null && processData.getSecSampleListMap().size() > 0) {
			List<SuperVO> temp = new ArrayList<>();
			for(List<Sample> sampleList : processData.getSecSampleListMap().values()){
				temp.addAll(sampleList);
			}
			
			rs.addAll(VOToInserSQL(temp, "sample",null));
		}
		// test第二次
		if (processData.getSecTestList() != null && processData.getSecTestList().size() > 0) {
			List<SuperVO> temp = new ArrayList<>();
			for(Test test : processData.getSecTestList()){
				temp.add(test);
			}
			
			rs.addAll(VOToInserSQL(temp, "test",null));
		}
		// result第二次
		if (processData.getSecResultList() != null && processData.getSecResultList().size() > 0) {
			List<SuperVO> temp = new ArrayList<>();
			for(Result result : processData.getSecResultList()){
				temp.add(result);
			}
			rs.addAll(VOToInserSQL(temp, "result",SecWriteBackStaticMaping.RESULT_STATIC_MAP));
		}
		// 审批信息-子表
		if (processData.getApprovalList() != null && processData.getApprovalList().size() > 0) {
			List<SuperVO> temp = new ArrayList<>();
			for (ApprovalInfo approval : processData.getApprovalList()) {
				temp.add(approval);
			}
			rs.addAll(VOToInserSQL(temp, "approval_details", null));
		}
		// 审批信息-主表
		if (processData.getApprovalMain() != null) {
			List<SuperVO> temp = new ArrayList<>();
			temp.add(processData.getApprovalMain());
			rs.addAll(VOToInserSQL(temp, "approval", null));
		}
		// test实验前
		if (processData.getTestParaAListMap() != null && processData.getTestParaAListMap().size() > 0) {
			List<SuperVO> temp = new ArrayList<>();
			for (List<Test> testList : processData.getTestParaAListMap().values()) {
				temp.addAll(testList);
			}
			rs.addAll(VOToInserSQL(temp, "test", null));
		}
		// test实验后
		if (processData.getTestParaBListMap() != null && processData.getTestParaBListMap().size() > 0) {
			List<SuperVO> temp = new ArrayList<>();
			for (List<Test> testList : processData.getTestParaBListMap().values()) {
				temp.addAll(testList);
			}
			rs.addAll(VOToInserSQL(temp, "test", null));
		}
		
		return rs;
	}
	
	private List<String> VOToInserSQL(List<SuperVO> voList,String tableName,Map<String,String> staticMap){
		List<String> rsList = new ArrayList<>();
		StringBuilder nameSB = new StringBuilder();
		StringBuilder valueSB = new StringBuilder();
		
		StringBuilder sqlSB = new StringBuilder();
		
		for(SuperVO vo : voList){
			String[] names = vo.getAttributeNames();
			
			for(String name : names){
				if("sample".equals(tableName) && "\"audit\"".equals(name)){
					name = name.toUpperCase();
				}
				nameSB.append(name).append(",");
				
				Object realValue = vo.getAttributeValue(name);
				//可直接add的sqlvalue
				String sqlValue = dealValue4Sql(name,realValue);
				valueSB.append(sqlValue).append(",");
			}
			
			//静态字段写入
			if(staticMap!=null && staticMap.size() > 0){
				for(String staticName : staticMap.keySet()){
					nameSB.append(staticName).append(",");
					valueSB.append(staticMap.get(staticName)).append(",");
				}
			}
			
			
			nameSB.delete(nameSB.length()-1, nameSB.length());
			valueSB.delete(valueSB.length()-1, valueSB.length());
			
			sqlSB.append("INSERT INTO ").append(tableName).append(" (")
            .append(nameSB.toString()).append(")  values (");
			sqlSB.append(valueSB);
			sqlSB.append(" ) ");
			
			rsList.add(sqlSB.toString());
			
			sqlSB.delete(0, sqlSB.length());
			nameSB.delete(0, nameSB.length());
			valueSB.delete(0, valueSB.length());
		}
		
		
		
		
		return rsList;
	}

	/**
	 * 一些sql 的处理
	 * @param name
	 * @param realValue
	 * @return
	 */
	
	private String dealValue4Sql(String name, Object realValue) {
		String rt = null;
		sb.delete(0, sb.length());
		if (realValue != null) {
			if (realValue instanceof Integer || realValue instanceof UFDouble || realValue instanceof Double) {
				if("max_value".equalsIgnoreCase(name) || "min_value".equalsIgnoreCase(name)){
					rt = sb.append("'").append(dealEscapse(String.valueOf(realValue))).append("'").toString();
				}else{
					rt = String.valueOf(realValue) ;
				}
				
			}else{
				if (getTimeColumnSet().contains(name)) {
					rt = String.valueOf(realValue);
				} else {
					rt = sb.append("'").append(dealEscapse(String.valueOf(realValue))).append("'").toString();
				}
			}
		} else {
			rt = "null";
		}
		return rt;
	}
	
	/**
	 * 一些时间字段
	 */
	private Set<String> TIME_COLUMN_SET = new HashSet<>();
	
	private Set<String> getTimeColumnSet(){
		if(null == TIME_COLUMN_SET){
			TIME_COLUMN_SET = new HashSet<>(); 
		}
		TIME_COLUMN_SET.add("changed_on");
		TIME_COLUMN_SET.add("date_created");
		TIME_COLUMN_SET.add("login_date");
		TIME_COLUMN_SET.add("recd_date");
		TIME_COLUMN_SET.add("c_submit_date");
		TIME_COLUMN_SET.add("date_received");
		TIME_COLUMN_SET.add("date_started");
		TIME_COLUMN_SET.add("t_date_enabled");
		TIME_COLUMN_SET.add("c_product_date");
		TIME_COLUMN_SET.add("date_updated");
		TIME_COLUMN_SET.add("date_completed");
		TIME_COLUMN_SET.add("c_customermanager_date");
		TIME_COLUMN_SET.add("c_techsupervisor_date");
		TIME_COLUMN_SET.add("first_entry_on");
		TIME_COLUMN_SET.add("entered_on");
		TIME_COLUMN_SET.add("date_assigned");
		TIME_COLUMN_SET.add("date_approval_due");
		TIME_COLUMN_SET.add("date_approved");
		TIME_COLUMN_SET.add("approval_start");
		TIME_COLUMN_SET.add("approval_complete");
		
		return TIME_COLUMN_SET;
	}
	
	/**
     * 通过分析名,获取分析版本
     * @param analysisName
     * @return
     * @throws DAOException 
     */
    public String getAnalysisVerionFromName(String analysisName) throws DAOException {
    	if(analysisToVersion.containsKey(analysisName)){
    		return analysisToVersion.get(analysisName);
    	}
		String sql = " select version from analysis where name  = '"+analysisName+"'"; 
		Integer ver = (Integer)baseDao.executeQuery(sql, new ColumnProcessor());
		analysisToVersion.put(analysisName, String.valueOf(ver));
		return String.valueOf(ver);
	}
    /**
     * 通过分析名,获取lab
     * @param analysisName
     * @return
     * @throws DAOException 
     */
    public String getLabFromAnalysisName(String analysisName) throws DAOException {
    	if(analysisToLab.containsKey(analysisName)){
    		return analysisToLab.get(analysisName);
    	}
		String sql = " select lab from analysis where name  = '"+analysisName+"'"; 
		String rs = (String)baseDao.executeQuery(sql, new ColumnProcessor());
		analysisToLab.put(analysisName, String.valueOf(rs));
		return String.valueOf(rs);
	}
    
    /**
     * 通过分析名,获取分析方法
     * @param analysisName
     * @return
     * @throws DAOException 
     */
    public String getMethodFromAnalysisName(String analysisName) throws DAOException {
    	if(analysisToMethod.containsKey(analysisName)){
    		return analysisToMethod.get(analysisName);
    	}
		String sql = " select t_analysis_method from analysis where name  = '"+analysisName+"'"; 
		String rs = (String)baseDao.executeQuery(sql, new ColumnProcessor());
		analysisToMethod.put(analysisName, String.valueOf(rs));
		return String.valueOf(rs);
	}
    
    /**
     * 通过分析名,获取分析
     * @param analysisName
     * @return
     * @throws DAOException 
     */
    public Map<String,Object> getAnalysis(String analysisName) throws DAOException {
    	if(nameToAnalysis.containsKey(analysisName)){
    		return nameToAnalysis.get(analysisName);
    	}
		String sql = " select * from analysis where name  = '"+analysisName+"'"; 
		@SuppressWarnings("unchecked")
		Map<String,Object> rs = (Map<String,Object>)baseDao.executeQuery(sql, new MapProcessor());
		nameToAnalysis.put(analysisName, rs);
		return rs;
	}
    
    
    /**
	 * 根据task id 查找测试结果分项
	 * @param valueOf
	 * @return
     * @throws DAOException 
	 */
	public List<Map<String, Object>> getResultCompoentList(String taskId) throws DAOException {
		List<Map<String, Object>> rsList = new ArrayList<>();
		//循环查找task
		CProjTask task = null;
		if(processData.getTaskList()!=null && processData.getTaskList().size() > 0){
			for(CProjTask vo : processData.getTaskList()){
				if(taskId.equals(String.valueOf(vo.getAttributeValue("seq_num")))){
					task = vo;
					break;
				}
			}
		}
		if(task !=null){
			String analysis = String.valueOf(task.getAttributeValue("analysis"));
			if(analysis!=null){
				if(analysisToResultComponentMap.containsKey(analysis)){
					rsList = analysisToResultComponentMap.get(analysis);
				}else{
					String sql = "select COMPONENT.* from COMPONENT "
							+" left join ANALYSIS analysis on analysis.name = component.analysis  "
							+" where ANALYSIS = '"+analysis+"' and analysis.c_test_type='测试结果' ";
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> rs = (List<Map<String,Object>>)baseDao.executeQuery(sql, new MapListProcessor());
					analysisToResultComponentMap.put(analysis, rs);
					rsList = rs;
				}
			}
		}

		return rsList;
	}
	/**
	 * 通过component中的list获取listKey
	 * @param  
	 * @return
	 * @throws DAOException 
	 */
	public String getCListKeyByListKey(String listKey) throws DAOException {
		if(listKeyToName.containsKey(listKey)){
    		return listKeyToName.get(listKey);
    	}
		String sql = " select * from analysis where name  = '"+listKey+"'"; 
		String rs = (String)baseDao.executeQuery(sql, new MapProcessor());
		listKeyToName.put(listKey, rs);
		return rs;
	}
	public Map<String, Object> getCompoentByAnalysisAndName(String analysis, String name) throws DAOException {
		if(analysis!=null){
			if(analysisNameToTestComponent.containsKey(analysis+"::"+name)){
				return analysisNameToTestComponent.get(analysis+"::"+name)==null?new HashMap<String, Object>():analysisNameToTestComponent.get(analysis+"::"+name);
			}else{
				String sql = "select COMPONENT.* from COMPONENT "
						+" left join ANALYSIS analysis on analysis.name = component.analysis  "
						+" where ANALYSIS = '"+analysis+"' and analysis.c_test_type='测试条件' "
						+" and COMPONENT.name = '"+name+"' and rownum = 1 ";
				@SuppressWarnings("unchecked")
				Map<String,Object> rs = (Map<String,Object>)baseDao.executeQuery(sql, new MapProcessor());
				analysisNameToTestComponent.put(analysis+"::"+name, rs);
			}
		}
		return analysisNameToTestComponent.get(analysis+"::"+name)==null?new HashMap<String, Object>():analysisNameToTestComponent.get(analysis+"::"+name);
	}
	//持续时间计算
	public Integer getTestTime(ISuperVO iSuperVO) throws BusinessException {
		if(iSuperVO!=null && iSuperVO.getAttributeValue("pk_task_b")!=null){
			//查询孙表持续时间
			String textvalue = 
					(String) baseDao.executeQuery(
					"select textvalue from qc_task_s where dr = 0 and pk_testconditionitem in ('持续时间','Duration','duration') and  pk_task_b = '"
					+String.valueOf(iSuperVO.getAttributeValue("pk_task_b"))+"' and rownum = 1 ", 
					new ColumnProcessor());
			if(textvalue!=null){
				Double time = null;
				try{
					time = Double.parseDouble(String.valueOf(textvalue));
				}catch(Exception e){
					throw new BusinessException("持续时间转换失败:"+String.valueOf(textvalue));
				}
				//NC中已经进行时间转换,所以回写的时候不需要再转换 tank 2019年10月28日21:36:24
				return (int)Math.ceil(time);
			}
		}
		return 0;
	}
	
	public int changeTime2H(String timeStr,String unit){
		Double time = null;
		try{
			time = Double.parseDouble(timeStr);
		}catch(Exception e){
			Logger.error("持续时间转换失败:"+timeStr);
		}
		if(unit!=null){
			unit = unit.replaceAll(" ", "");
			if("HOURS".equalsIgnoreCase(unit)||"h".equalsIgnoreCase(unit)){
				return (int)Math.ceil(time);
			}else if("MINUTES".equalsIgnoreCase(unit)||"min".equalsIgnoreCase(unit)){
				return (int)Math.ceil(time/60);
			}else if("SECONDS".equalsIgnoreCase(unit)||"s".equalsIgnoreCase(unit)){
				return (int)Math.ceil(time/60/60);
			}else if("MS".equalsIgnoreCase(unit)||"ms".equalsIgnoreCase(unit)){
				return (int)Math.ceil(time/60/60/1000);
			}else if("US".equalsIgnoreCase(unit)||"μs".equalsIgnoreCase(unit)){
				return (int)Math.ceil(time/60/60/1000/1000);
			}else if("DAYS".equalsIgnoreCase(unit)||"days".equalsIgnoreCase(unit)){
				return (int)Math.ceil(time*24);
			}else{
				Logger.error("未识别的时间单位:"+unit);
			}
		}
		return (int)Math.ceil(time);
	}
	
    /**
     * @param  String "A1,A2,A4,B1,C1"
     * @param rowNumList 每组的数量,比如:rowNumList[0]为A组数量,rowNumList[1]为B组数量,rowNumList.size()为分组数量
     * Out : String "A1-A2,A4,B1,C1"
     *
     * @author Tank
     * @date 2019年3月15日10:01:40
     */
    public static String outOrderString4WriteBack(String arraysString) throws Exception {
    	String[] arrays = intoArray(arraysString);
        return out4Writeback(arrays);
    }
    
    /**
     * 
     * @param arraysString String "A1,A2,A4,B1,C1"
     * @return Array {A1,A2,A4,B1,C1}
     */
        private static String[] intoArray(String arraysString) {
    		if(arraysString!=null && arraysString.length() > 0){
    			return arraysString.split(",");
    		}else{
    			return new String[0];
    		}
    	}
        /**
         * * @param  arrays{A1,A2,A4,B1,C1} 不跨组
         * Out : String "A1-A2,A4,B1,C1"
         */
        private static String out4Writeback(String[] arrays) throws Exception {
        	StringBuilder rs = new StringBuilder();
            if (arrays != null && arrays.length > 0) {
            	List<String> sortedList = new ArrayList<>();
            	Collections.addAll(sortedList, arrays);
            	Collections.sort(sortedList, new Comparator<String>(){

    				@Override
    				public int compare(String tab1, String tab2) {
    					try {
    						return getSortNumber(tab1)-getSortNumber(tab2);
    					} catch (Exception e) {
    						e.printStackTrace();
    					} 
    					return 0;
    				}
            		
            	});
            	if(sortedList!=null && sortedList.size() > 0){
            		List<String> groupList = new ArrayList<>();
            		for(int tabIndex = 0 ; tabIndex<sortedList.size();tabIndex++){
            			if(null != sortedList.get(tabIndex) && sortedList.get(tabIndex).replaceAll(" ", "").length() >= 2){
            				String tab = sortedList.get(tabIndex).replaceAll(" ", "");
            				//先进行分组
                			if((groupList.size()<=0 || groupList.get(0).charAt(0)==tab.charAt(0)) &&( (sortedList.size()-1)!=tabIndex)){
                				groupList.add(tab);
                			}else{
                				if((sortedList.size()-1)==tabIndex){
                					groupList.add(tab);
                				}
                				//分组完成开始进行输出
                				for(int i =0;i<groupList.size();i++){
                					if(rs.length() <=0 || rs.charAt(rs.length()-1)==';'||i==0){
                						rs.append(groupList.get(i));
                						if((groupList.size()-1) == i){
                							rs.append(";");
                						}
                					}else if(rs.charAt(rs.length()-1)=='-'){
                						if(getSortNumber(groupList.get(i))-getSortNumber(groupList.get(i-1))==1){
                							if(i==(groupList.size()-1)){
                								rs.append(groupList.get(i)).append(";");
                            				}
                							//挨着的
                							continue;
                						}else{
                							//不挨着
                							rs.append(groupList.get(i)).append(";");
                						}
                					
                					}else{
                						//到这里只有数字了
                						if(getSortNumber(groupList.get(i))-getSortNumber(groupList.get(i-1))==1){
                							//挨着的
                							rs.append("-");
                							if(i==(groupList.size()-1)){
                								rs.append(groupList.get(i)).append(";");
                            				}
                						}else{
                							//不挨着
                							rs.append(";").append(groupList.get(i)).append(";");
                						}
                					}
                				
                				}
                				//输出完成开始下一组
                				groupList.clear();
                				groupList.add(tab);
                				//rs.append(tab);
                			}
            			}
            		}
            	}

            }
            if(rs.length() > 0){
            	rs = rs.delete(rs.length()-1, rs.length());
            }
            return rs.toString();
        }
        /**
         * 
         * @param tab A1,B3.....
         * @return 6001   7003  (ascii A:60 * 100 + 1)
         * @throws Exception 
         */
        private static int getSortNumber(String tab) throws Exception{
        	if (null != tab && tab.replaceAll(" ", "").length() >= 2) {
                tab = tab.replaceAll(" ", "");
                try {
                    int row = tab.charAt(0);
                    int col = Integer.parseInt(tab.substring(1, tab.length()));
                    return row*100+col;
                } catch (Exception e) {
                    throw new Exception("字符非法:" + tab);
                }
            }
        	
        	
        	return 0;
        }
        /**
         * 根据第二次回写的sampleid 查找第二次回写的sample
         * @param attributeValue
         */
		public Sample getSampleBySample(String attributeValue) {
			Map<String, List<Sample>> id2Sampe = processData.getSecSampleListMap();
			if(id2Sampe!=null && id2Sampe.size() > 0){
				Collection<List<Sample>> allSampleList = id2Sampe.values();
				for(List<Sample> sampleList : allSampleList){
					if(sampleList!=null&&sampleList.size() > 0){
						for(Sample sample : sampleList){
							if(sample!=null && sample.getAttributeValue("sample_number")!=null && String.valueOf(sample.getAttributeValue("sample_number")).equals(attributeValue)){
								return sample;
							}
						}
					}
				}
			}
			
			return null;
		}
		/**
		 * 根据group(A,B,C)获取对应的sampleGroup
		 * @param attributeValue
		 * @return
		 */
		public CProjLoginSample getSampleGroupByGroup(String groupStr) {
			List<CProjLoginSample> sampleGroupList = processData.getLoginSampleList();
			if(sampleGroupList!=null){
				for(CProjLoginSample sampleGroup : sampleGroupList){
					if(String.valueOf(sampleGroup.getAttributeValue("sample_group")).equals(groupStr)){
						return sampleGroup;
					}
				}
			}
			return null;
		}
	
/*    public SuperVO writeStaticField(SuperVO vo ,Map<String, String> staticMaping){
    	
    	if(vo!=null && staticMaping!=null && staticMaping.size() > 0){
    		for(String name : staticMaping.keySet()){
    			vo.setAttributeValue(name, staticMaping.get(name));
    		}
    	}
    	return vo;
    }*/
	
    
}
