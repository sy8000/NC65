package nc.ui.pub.qcco.writeback.utils.processor;

import nc.ui.pub.qcco.writeback.utils.WriteBackProcessData;
import nc.ui.pub.qcco.writeback.utils.common.CommonUtils;
import nc.vo.pub.BusinessException;


/**
 * �ڶ��λ�д�ӿ�
 * @author 91967
 *
 */
public interface ISecWriteBackProcessor {

	void processSec(WriteBackProcessData data) throws BusinessException;
	
	void setUtils(CommonUtils utils);
	
	
	
	
	
	
}
