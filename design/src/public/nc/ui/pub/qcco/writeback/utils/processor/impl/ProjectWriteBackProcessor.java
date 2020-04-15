package nc.ui.pub.qcco.writeback.utils.processor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import nc.ui.pub.qcco.writeback.utils.WriteBackProcessData;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.Project;
import nc.ui.pub.qcco.writeback.utils.common.CommonUtils;
import nc.ui.pub.qcco.writeback.utils.mapping.FirstWriteBackStaticMaping;
import nc.ui.pub.qcco.writeback.utils.processor.IFirstWriteBackProcessor;
import nc.vo.fts.commission.util.CommissionVOUtil;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ISuperVO;
import nc.vo.qcco.commission.CommissionBVO;

/**
 * ί�е������д һ��һ
 * 
 * @author 91967
 * 
 */
public class ProjectWriteBackProcessor implements IFirstWriteBackProcessor {

	private CommonUtils utils;

	@Override
	public void setUtils(CommonUtils utils) {
		this.utils = utils;
	}

	@Override
	public void processFirst(WriteBackProcessData data) throws BusinessException {

		processData(data);
	}

	/**
	 * ��ȡInsert���Ƭ��
	 * 
	 * @param processData processData 
	 * @throws BusinessException
	 */
	private void processData(WriteBackProcessData processData) throws BusinessException {

		List<ISuperVO> srcData = new ArrayList<>();
		srcData.add(processData.getAggCommissionHVO().getParentVO());
		if (null == srcData || srcData.size() <= 0) {
			return ;
		}
		//��Ҫ��д������
		Project project = new Project();
		
		// ������ѭ��
		for (Entry<String, String> map : FirstWriteBackStaticMaping.HEAD_MAPPING.entrySet()) {
			//NC���ֶ���
			String fieldName = map.getKey();

			//LIMS �ֶ���
			String[] fields = null;
			if (map.getValue().contains(";")) {
				fields = utils.getWriteBackFields(map.getValue().split(";"));
			} else {
				fields = utils.getWriteBackFields(new String[] { map.getValue() });
			}
			if (srcData != null && srcData.size() > 0) {
				for (ISuperVO vo : srcData) {
					Object unDofieldValue = vo.getAttributeValue(fieldName);
					
					Object realValue = utils.getRealValue(unDofieldValue, fieldName,processData.getAggCommissionHVO().getParentVO().getClass());
					
					for (String field : fields) {
						project.setAttributeValue(field, realValue);
					}
					
				}
			}
		}
		//��Ʒ������,���ڻ�дproject.NUM_SAMPLES �ֶ�
		project.setAttributeValue("num_samples", processData.getAggCommissionHVO().getChildren(CommissionBVO.class).length);
		//date_updatedΪ��ʱ,д�봴��ʱ��
		if(project.getAttributeValue("date_updated")==null){
			Object realValue = utils.getRealValue(processData.getAggCommissionHVO().getParentVO().getCreationtime(), 
					"creationtime",processData.getAggCommissionHVO().getParentVO().getClass());
			project.setAttributeValue("date_updated", realValue);
		}
		//approval_id ����
		List<Integer> approvalId = utils.getPrePk("approval_id", "APPROVAL", 1);
		project.setAttributeValue("approval_id", approvalId.get(0));
		//��д��־λ
		project.setAttributeValue("c_nc_to_lims", "T");
		
		processData.setProject(project);
		
	}
}
