package nc.bs.qcco.task.rule;

import nc.impl.pubapp.pattern.rule.IRule;
import nc.vo.pub.ISuperVO;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.qcco.task.AggTaskHVO;
import nc.vo.qcco.task.TaskBVO;

public class TaskCheckRule implements IRule<AggTaskHVO> {

	@Override
	public void process(AggTaskHVO[] aggvos) {
		if (aggvos != null && aggvos.length > 0) {
			for (AggTaskHVO aggvo : aggvos) {
				checkSerialNo(aggvo);
			}
		}

	}

	private void checkSerialNo(AggTaskHVO aggvo) {
		if (aggvo != null) {
			ISuperVO[] bodyVOs = aggvo.getChildren(TaskBVO.class);
			if (bodyVOs != null && bodyVOs.length > 0) {
				for (int i = 0; i < bodyVOs.length; i++) {
					for (int j = i + 1; j < bodyVOs.length; j++) {
						if (bodyVOs[i]!=null && bodyVOs[i].getAttributeValue("runorder")!=null &&
								((Integer) bodyVOs[i].getAttributeValue("runorder")).equals((Integer) bodyVOs[j]
								.getAttributeValue("runorder"))) {
							ExceptionUtils.wrappBusinessException("执行顺序 [" + bodyVOs[i].getAttributeValue("runorder")
									+ "] 重复，请调整后重试。");
						}
					}
				}
			}
		}
	}

}
