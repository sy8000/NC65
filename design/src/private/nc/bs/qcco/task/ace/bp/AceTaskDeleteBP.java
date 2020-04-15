package nc.bs.qcco.task.ace.bp;

import nc.bs.qcco.task.plugin.bpplugin.TaskPluginPoint;
import nc.vo.qcco.task.AggTaskHVO;

import nc.impl.pubapp.pattern.data.bill.template.DeleteBPTemplate;
import nc.impl.pubapp.pattern.rule.processer.AroundProcesser;
import nc.impl.pubapp.pattern.rule.IRule;


/**
 * ��׼����ɾ��BP
 */
public class AceTaskDeleteBP {

	public void delete(AggTaskHVO[] bills) {

		DeleteBPTemplate<AggTaskHVO> bp = new DeleteBPTemplate<AggTaskHVO>(
				TaskPluginPoint.DELETE);
		// ����ִ��ǰ����
		this.addBeforeRule(bp.getAroundProcesser());
		// ����ִ�к�ҵ�����
		this.addAfterRule(bp.getAroundProcesser());
		bp.delete(bills);
	}

	private void addBeforeRule(AroundProcesser<AggTaskHVO> processer) {
		// TODO ǰ����
		IRule<AggTaskHVO> rule = null;
		rule = new nc.bs.pubapp.pub.rule.BillDeleteStatusCheckRule();
		processer.addBeforeRule(rule);
	}

	/**
	 * ɾ����ҵ�����
	 * 
	 * @param processer
	 */
	private void addAfterRule(AroundProcesser<AggTaskHVO> processer) {
		// TODO �����

	}
}
