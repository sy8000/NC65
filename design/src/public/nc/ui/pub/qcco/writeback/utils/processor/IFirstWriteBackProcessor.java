package nc.ui.pub.qcco.writeback.utils.processor;

import nc.ui.pub.qcco.writeback.utils.WriteBackProcessData;
import nc.ui.pub.qcco.writeback.utils.common.CommonUtils;
import nc.vo.pub.BusinessException;

/**
 * 第一次回写处理接口
 * @author 91967
 *
 */
public interface IFirstWriteBackProcessor {
	
	

	void processFirst(WriteBackProcessData data) throws BusinessException;
	
	void setUtils(CommonUtils utils);
}
