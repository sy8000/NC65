package nc.ui.pub.qcco.writeback.utils.processor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Collections;

import nc.ui.pub.qcco.writeback.utils.WriteBackProcessData;
import nc.ui.pub.qcco.writeback.utils.LIMSVO.CProjLoginSample;
import nc.ui.pub.qcco.writeback.utils.common.CommonUtils;
import nc.ui.pub.qcco.writeback.utils.mapping.FirstWriteBackStaticMaping;
import nc.ui.pub.qcco.writeback.utils.processor.IFirstWriteBackProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ISuperVO;
import nc.vo.qcco.commission.CommissionBVO;

/**
 * 样品组回写
 * 
 * @author 91967
 * 
 */
public class SampleGroupWriteBackProcessor implements IFirstWriteBackProcessor {

	private CommonUtils utils;

	@Override
	public void setUtils(CommonUtils utils) {
		this.utils = utils;
	}

	@Override
	public void processFirst(WriteBackProcessData data) throws BusinessException {
		process(data);
	}

	/**
	 * 获取Insert语句片断
	 * 
	 * @param processData processData 
	 * @throws BusinessException
	 */
	private void process(WriteBackProcessData processData) throws BusinessException {

		//NC数据
		List<ISuperVO> srcDataList = new ArrayList<>();
		ISuperVO[] superVOs = processData.getAggCommissionHVO().getChildren(CommissionBVO.class);
		Collections.addAll(srcDataList, superVOs);
		if (null == srcDataList || srcDataList.size() <= 0) {
			return ;
		}
		
		//需要回写的LIMS数据
		List<CProjLoginSample> loginSampleList = initWriteBackList(srcDataList.size());
		
		// 预申请pk
		List<Integer> pkList = utils.getPrePk(CommissionBVO.class, srcDataList.size());

		// 进行列循环
		for (Entry<String, String> map : FirstWriteBackStaticMaping.BODY_SAMPLE_MAPPING.entrySet()) {
			String fieldName = map.getKey();

			String[] fields = null;
			if (map.getValue().contains(";")) {
				fields = utils.getWriteBackFields(map.getValue().split(";"));
			} else {
				fields = utils.getWriteBackFields(new String[] { map.getValue() });
			}
			if (srcDataList != null && srcDataList.size() > 0) {
				for (int i = 0 ;i < srcDataList.size();i++) {
					Object unDofieldValue = srcDataList.get(i).getAttributeValue(fieldName);

					Object realValue = utils.getRealValue(unDofieldValue, fieldName, CommissionBVO.class);

					for (String field : fields) {
						loginSampleList.get(i).setAttributeValue(field, realValue);
					}

				}
			}
		}
		//回写主外键
		if (srcDataList != null) {
			for (int i = 0; i < srcDataList.size(); i++) {
				
				loginSampleList.get(i).setAttributeValue("seq_num", pkList.get(i));
				
				loginSampleList.get(i).setAttributeValue("project", processData.getProject().getAttributeValue("name"));

			}

		}
		processData.setLoginSampleList(loginSampleList);
	}
	/**
	 * 有几条委托单子表,就有几条CProjLoginSample
	 * @return
	 */
	private List<CProjLoginSample> initWriteBackList(Integer commissionBNum) {
		List<CProjLoginSample> rsList = new ArrayList<>();
		
		for(int i = 0;i < commissionBNum ;i++){
			rsList.add(new CProjLoginSample());
		}
		
		return rsList;
	}

}
