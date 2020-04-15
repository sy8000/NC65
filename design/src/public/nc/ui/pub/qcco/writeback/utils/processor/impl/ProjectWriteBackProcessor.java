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
 * 委托单主表回写 一对一
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
	 * 获取Insert语句片断
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
		//需要回写的数据
		Project project = new Project();
		
		// 进行列循环
		for (Entry<String, String> map : FirstWriteBackStaticMaping.HEAD_MAPPING.entrySet()) {
			//NC端字段名
			String fieldName = map.getKey();

			//LIMS 字段名
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
		//样品行数量,用于回写project.NUM_SAMPLES 字段
		project.setAttributeValue("num_samples", processData.getAggCommissionHVO().getChildren(CommissionBVO.class).length);
		//date_updated为空时,写入创建时间
		if(project.getAttributeValue("date_updated")==null){
			Object realValue = utils.getRealValue(processData.getAggCommissionHVO().getParentVO().getCreationtime(), 
					"creationtime",processData.getAggCommissionHVO().getParentVO().getClass());
			project.setAttributeValue("date_updated", realValue);
		}
		//approval_id 自增
		List<Integer> approvalId = utils.getPrePk("approval_id", "APPROVAL", 1);
		project.setAttributeValue("approval_id", approvalId.get(0));
		//回写标志位
		project.setAttributeValue("c_nc_to_lims", "T");
		
		processData.setProject(project);
		
	}
}
