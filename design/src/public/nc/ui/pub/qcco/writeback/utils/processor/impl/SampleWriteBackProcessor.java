package nc.ui.pub.qcco.writeback.utils.processor.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.ui.pub.qcco.writeback.utils.WriteBackProcessData;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.CProjLoginSample;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.CProjTask;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.Sample;
import nc.ui.pub.qcco.writeback.utils.common.CommonUtils;
import nc.ui.pub.qcco.writeback.utils.mapping.FirstWriteBackStaticMaping;
import nc.ui.pub.qcco.writeback.utils.processor.IFirstWriteBackProcessor;
import nc.ui.pub.qcco.writeback.utils.processor.ISecWriteBackProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFLiteralDate;
import nc.vo.qcco.commission.CommissionHVO;

/**
 * ��Ʒ��һ�κ͵ڶ���д
 * 
 * @author 91967
 * 
 */
public class SampleWriteBackProcessor implements IFirstWriteBackProcessor, ISecWriteBackProcessor {

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
	 * sample ���λ�д
	 * 
	 * @param data
	 * @throws DAOException 
	 */
	private void processSecond(WriteBackProcessData processData) throws DAOException {
		//NC Data
		List<CProjLoginSample> sampleGroupList = processData.getLoginSampleList();
		
		//LIMS Data
		List<CProjTask> taskList = processData.getTaskList();
		
		//CLONED_FROM ����pk
		int cloneFromPK = utils.getPrePk("cloned_from", "sample", 1).get(0)-1;
		int transNumPK = utils.getPrePk("trans_num", "sample", 1).get(0)-1;
		int originalPK = utils.getPrePk("original_sample", "sample", 1).get(0)-1;
		
		//ʱ��ǰ׺
		String timeStr = new UFLiteralDate().toStdString().replaceAll("-", "").substring(2,4);
		//project
		String project = String.valueOf(processData.getProject().getAttributeValue("name"));
		//test_id 
		String textIdPre = timeStr+project.split("-")[2];
		
		//��Ҫ��д��LIMS���� ����*ÿ������
		Map<String,List<Sample>> secSampleListMap = new HashMap<>();
		
		if(sampleGroupList!=null && sampleGroupList.size() > 0){
			for(int i = 0 ;i < sampleGroupList.size() ;i++){
				if(sampleGroupList.get(i)!=null && sampleGroupList.get(i).getAttributeValue("sample_quantity")!=null){
					//��Ʒ���
					String group = String.valueOf(sampleGroupList.get(i).getAttributeValue("sample_group"));
					//ÿ������
					Integer gourpNum = Integer.parseInt(String.valueOf(sampleGroupList.get(i).getAttributeValue("sample_quantity")));
					//�ṹ����
					String structure_type = String.valueOf(sampleGroupList.get(i).getAttributeValue("structure_type"));
					
					//ÿ��list
					List<Sample> sampleList = new ArrayList<>();
					for(int j = 1 ; j <= gourpNum ;j++){
						//��ʼ����sample
						Sample sample = new Sample();
						//SAMPLE.PROJECT	����ί�е���
						sample.setAttributeValue("project", processData.getProject().getAttributeValue("name"));
						//SAMPLE.AUDIT	T

						sample.setAttributeValue("\"audit\"", "T");
						//SAMPLE.C_CONTACT_TYPE	��������

						sample.setAttributeValue("c_contact_type", sampleGroupList.get(i).getAttributeValue("contact_type"));
						//SAMPLE.C_SAMPLE_GROUP	��Ʒ��

						sample.setAttributeValue("c_sample_group", group);
						//SAMPLE.CLONED_FROM	����ʽ����
						if(1!=j){
							sample.setAttributeValue("cloned_from", ++cloneFromPK);
						}else{
							sample.setAttributeValue("cloned_from", 0);
						}
						//SAMPLE.CUSTOMER	�ύ�˹�˾��
						
//						sample.setAttributeValue("customer", taskList.get(i).getAttributeValue("C_SUBMIT_BY"));
						//SAMPLE.DATE_STARTED	null

						sample.setAttributeValue("date_started", null);
						
						//SAMPLE.LOGIN_BY	BACKGROUND

						sample.setAttributeValue("login_by", "BACKGROUND");
						//SAMPLE.OLD_STATUS	C ������:P

						sample.setAttributeValue("old_status", "C");
						//SAMPLE.PRODUCT	���ֵ

						sample.setAttributeValue("product", sampleGroupList.get(i).getAttributeValue("product_standard"));
						//SAMPLE.PRODUCT_VERSION	��Ʒ�汾��Ĭ��1

						sample.setAttributeValue("product_version", 1);
						//SAMPLE.SAMPLE_NUMBER	"������
						//�ж���ֻ��Ʒ��Ҫд�������"

						sample.setAttributeValue("sample_number", processData.getMaxSamplePK()+1);
						processData.setMaxSamplePK(processData.getMaxSamplePK()+1);
						//SAMPLE.SAMPLING_POINT	��Ʒ����

						sample.setAttributeValue("sampling_point", sampleGroupList.get(i).getAttributeValue("production_spec"));
						//SAMPLE.STARTED	F

						sample.setAttributeValue("started", "F");
						//SAMPLE.STATUS	U

						sample.setAttributeValue("status", "U");
						//SAMPLE.T_LOGIN_VERIFIED	T

						sample.setAttributeValue("t_login_verified", "T");
						//SAMPLE.TEMPLATE	HF-MAIN

						sample.setAttributeValue("template", "HF-MAIN");
						//SAMPLE.TEXT_ID	�˴����������ɷ�ʽ���˴�Ϊ�ڶ���д�뷽ʽ��д��ֵΪ��2λ������-��+��Ʒ���

						sample.setAttributeValue("text_id", textIdPre+"-"+group+""+j);
						//SAMPLE.TRANS_NUM	����ʽ���� ,ÿ���һֻ��ƷΪ0
						if(1!=j){
							sample.setAttributeValue("trans_num", ++transNumPK);
						}else{
							sample.setAttributeValue("trans_num", 0);
						}
						
						
						
						//��������
						sample.setAttributeValue("CHANGED_ON", processData.getProject().getAttributeValue("date_created"));
						sample.setAttributeValue("LOGIN_DATE", processData.getProject().getAttributeValue("date_created"));
						
						//����
						sample.setAttributeValue("ORIGINAL_SAMPLE", ++originalPK);
						//�ṹ���͵���̬
						sample.setAttributeValue("PRODUCT_GRADE", structure_type);
						
						//ί�е���λ����
						sample.setAttributeValue("CUSTOMER", processData.getProject().getAttributeValue("customer"));
						
						//SAMPLE.LAB ��������С������ ���ܻ��ж��test,Ҫ��Ӧ�ĸ�?��test�л�д��Ӧ��һ��task
						
						//sample.setAttributeValue("lab", null);
						
						sample.setAttributeValue("C_IS_SEQUNCE", "F");
					
						
						
						sample.setAttributeValue("IN_SPEC", "T");
						sample.setAttributeValue("IN_CAL", "T");
						sample.setAttributeValue("RE_SAMPLE", "F");
						sample.setAttributeValue("ALIQUOT", "F");
						sample.setAttributeValue("PARENT_SAMPLE", 0);
						sample.setAttributeValue("PARENT_ALIQUOT", 0);
						sample.setAttributeValue("SAMPLE_VOLUME", 0);
						sample.setAttributeValue("PREP", "F");
						sample.setAttributeValue("PRIORITY", 0);
						sample.setAttributeValue("STANDARD", "F");
						sample.setAttributeValue("LOT", 0);
						sample.setAttributeValue("PARTIAL_SPEC", "F");
						sample.setAttributeValue("SPEC_TYPE", "NONE");
						sample.setAttributeValue("STAGE", "NONE");
						sample.setAttributeValue("PRIMARY_IN_SPEC", "F");
						sample.setAttributeValue("RELEASED", "F");
						sample.setAttributeValue("IN_CONTROL", "T");
						sample.setAttributeValue("INVESTIGATED", "F");
						sample.setAttributeValue("APPROVED", "F");
						sample.setAttributeValue("READY_FOR_APPROVAL", "F");
						sample.setAttributeValue("APPROVAL_ID", 0);
						sample.setAttributeValue("MODIFIED_RESULTS", "F");
						sample.setAttributeValue("REPORT_NUMBER", 0);
						sample.setAttributeValue("COMPOSITE", "F");
						sample.setAttributeValue("PARENT_COMPOSITE", 0);
						sample.setAttributeValue("PEOPLE", 0);
						sample.setAttributeValue("CHK_ALIQUOT_STATUS", "F");
						sample.setAttributeValue("CHK_ALIQUOT_SPECS", "F");
						sample.setAttributeValue("ALIQUOT_TEMPLATE", "ALIQUOT");
						sample.setAttributeValue("SAMPLE_EVENT", 0);
						sample.setAttributeValue("HAS_FLAGS", "F");
						sample.setAttributeValue("SAMPLED", "F");
						sample.setAttributeValue("NUM_CONTAINERS", 1);
						sample.setAttributeValue("REQD_VOLUME", 0);
						sample.setAttributeValue("COLLECTION_OFFSET", 0);
						sample.setAttributeValue("SIGNED", "F");
						sample.setAttributeValue("ALLOW_CHLD_ALQTS", "F");
						sample.setAttributeValue("CONTRACT_NUMBER", 0);
						sample.setAttributeValue("STORAGE_LOC_NO", 0);
						sample.setAttributeValue("REPORTED_RSLT_OOS", "F");
						sample.setAttributeValue("T_CONTRACT_TESTS", "F");
						//sample.setAttributeValue("C_IS_SEQUNCE", "U");
						
						sampleList.add(sample);
					}
					secSampleListMap.put(String.valueOf(sampleGroupList.get(i).getAttributeValue("seq_num")), sampleList);
					
				}
			}
			processData.setSecSampleListMap(secSampleListMap);
		}
	}


	/**
	 * ��ȡInsert���Ƭ��--Sample������д����
	 *  sample ��һ�λ�д��task��Ӧ,�м���task���м���sample
	 * @param processData processData
	 * @throws BusinessException
	 */
	private void process(WriteBackProcessData processData) throws BusinessException {
		
		// ί�е�
		CommissionHVO hvo = processData.getAggCommissionHVO().getParentVO();
		List<CProjTask> taskList = processData.getTaskList();
		// �޸�����--ί�е�
		UFDateTime modtime = hvo.getModifiedtime() == null ? hvo.getCreationtime() : hvo.getModifiedtime();
		if (modtime == null) {
			modtime = new UFDateTime();
		}
		//��Ҫ��д��LIMS����,������ ��һ�λ�дֻ��дһ��
		List<Sample> firstSampleList = new ArrayList<>();
		firstSampleList.add(new Sample());
		// ����
		List<Integer> prePk = utils.getPrePk("sample_number", "sample", taskList.size());
		processData.setMaxSamplePK(prePk.get(taskList.size()-1));
		//textid 
		String sql = " SELECT  max(to_number(str2)) FROM ( "
        +" SELECT REGEXP_SUBSTR(TEXT_ID,'[^-]+',1,1,'i')          STR1,  "
        +" NVL(REGEXP_SUBSTR(TEXT_ID,'[^-]+',1,2,'i'),'1') str2  "
        +"  FROM sample) origin "
        +" WHERE  regexp_like(str2,'^[[:digit:]]+$') and regexp_like(str1,'^[[:digit:]]+$') ";
		Object maxNumObj = new BaseDAO().executeQuery(sql, new ColumnProcessor());
		int maxNum = 1;
		if (maxNumObj != null) {
			try {
				maxNum = Integer.valueOf(maxNumObj.toString());
			} catch (Exception e) {
				maxNum = 1;
			}
		}
//		if(taskList!=null && taskList.size() > 0){
//			for(int i = 0 ;i<taskList.size() ; i++){
				// �޸�ʱ�� �����ӱ�
				// �޸�����
				
				int i = 0;
				String time = "to_timestamp('" + modtime.toStdString() + "','yyyy-mm-dd hh24:mi:ss.ff')";
				
				firstSampleList.get(i).setAttributeValue("date_started", time);
				firstSampleList.get(i).setAttributeValue("login_date", time);
				firstSampleList.get(i).setAttributeValue("recd_date", time);
				firstSampleList.get(i).setAttributeValue("changed_on", time);
				// Ա����
				String pk_user = hvo.getModifier() == null ? hvo.getCreator() : hvo.getModifier();
				if (pk_user != null) {
					String userCode = String.valueOf(utils.dealSystemRef(CommissionHVO.class, "cuserid", pk_user));
					firstSampleList.get(i).setAttributeValue("login_by", userCode);
					firstSampleList.get(i).setAttributeValue("received_by", userCode);
				}
				Integer pkFirstSample = prePk.get(i);
				firstSampleList.get(i).setAttributeValue("sample_number", pkFirstSample);
				firstSampleList.get(i).setAttributeValue("original_sample", pkFirstSample);
				//SAMPLE.OLD_STATUS	C ������:P

				firstSampleList.get(i).setAttributeValue("old_status", "P");
				//task ������� û��sample_number����ֶ�?
				//taskList.get(i).setAttributeValue("sample_number", pkFirstSample);
				// SAMPLE.TEXT_ID�˴����������ɷ�ʽ�����ڱ����ǵ�һ��д�룬д������ϱ��ɫ�ĸ�ʽ(19-5673)��
				// ��������ǰ����Ʒ���࣬��ʽΪ�����-���ֵ+1��
				firstSampleList.get(i).setAttributeValue("text_id", String.valueOf(modtime.getYear()).substring(2, 4)+"-"+(++maxNum));
				//��һ�λ�д��lab��test��һ�λ�д��ʱ��д��
				
				//date
				String timeComp = "to_timestamp('" + new UFDateTime().toStdString() + "','yyyy-mm-dd hh24:mi:ss.ff')";
				
				firstSampleList.get(i).setAttributeValue("date_completed", timeComp);
				
//			}
//		}
		processData.setFirstSampleList(firstSampleList);
	}
	
	private List<Sample> initData(int size) {
		List<Sample> rsList = new ArrayList<>();
		for(int i = 0;i<size;i++){
			rsList.add(new Sample());
		}
		return rsList;
	}

}
