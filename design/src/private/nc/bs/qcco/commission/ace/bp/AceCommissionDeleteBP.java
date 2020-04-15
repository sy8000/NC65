package nc.bs.qcco.commission.ace.bp;

import nc.bs.qcco.commission.plugin.bpplugin.CommissionPluginPoint;
import nc.impl.pubapp.pattern.data.bill.template.DeleteBPTemplate;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.impl.pubapp.pattern.rule.processer.AroundProcesser;
import nc.vo.qcco.commission.AggCommissionHVO;

/**
 * ��׼����ɾ��BP
 */
public class AceCommissionDeleteBP {

	public void delete(AggCommissionHVO[] bills) {

		DeleteBPTemplate<AggCommissionHVO> bp = new DeleteBPTemplate<AggCommissionHVO>(CommissionPluginPoint.DELETE);
		// // ����ִ��ǰ����
		// this.addBeforeRule(bp.getAroundProcesser());
		// // ����ִ�к�ҵ�����
		// this.addAfterRule(bp.getAroundProcesser());
		bp.delete(bills);
	}

	private void addBeforeRule(AroundProcesser<AggCommissionHVO> processer) {
		// TODO ǰ����
		IRule<AggCommissionHVO> rule = null;
		rule = new nc.bs.pubapp.pub.rule.BillDeleteStatusCheckRule();
		processer.addBeforeRule(rule);
	}

	/**
	 * ɾ����ҵ�����
	 * 
	 * @param processer
	 */
	private void addAfterRule(AroundProcesser<AggCommissionHVO> processer) {
		// TODO �����

	}
}
