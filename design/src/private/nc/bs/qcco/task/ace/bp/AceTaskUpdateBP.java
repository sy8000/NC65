package nc.bs.qcco.task.ace.bp;

import nc.bs.qcco.task.plugin.bpplugin.TaskPluginPoint;
import nc.bs.qcco.task.rule.TaskCheckRule;
import nc.impl.pubapp.pattern.data.bill.template.UpdateBPTemplate;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.impl.pubapp.pattern.rule.processer.CompareAroundProcesser;
import nc.vo.qcco.task.AggTaskHVO;

/**
 * �޸ı����BP
 * 
 */
public class AceTaskUpdateBP {

	public AggTaskHVO[] update(AggTaskHVO[] bills, AggTaskHVO[] originBills) {
		// �����޸�ģ��
		UpdateBPTemplate<AggTaskHVO> bp = new UpdateBPTemplate<AggTaskHVO>(TaskPluginPoint.UPDATE);
		// ִ��ǰ����
		this.addBeforeRule(bp.getAroundProcesser());
		// ִ�к����
		this.addAfterRule(bp.getAroundProcesser());
		return bp.update(bills, originBills);
	}

	private void addAfterRule(CompareAroundProcesser<AggTaskHVO> processer) {
		// TODO �����
		/*
		 * IRule<AggTaskHVO> rule = null; rule = new
		 * nc.bs.pubapp.pub.rule.BillCodeCheckRule();
		 * ((nc.bs.pubapp.pub.rule.BillCodeCheckRule)
		 * rule).setCbilltype("QC08");
		 * ((nc.bs.pubapp.pub.rule.BillCodeCheckRule) rule)
		 * .setCodeItem("billno"); ((nc.bs.pubapp.pub.rule.BillCodeCheckRule)
		 * rule) .setGroupItem("pk_group");
		 * ((nc.bs.pubapp.pub.rule.BillCodeCheckRule)
		 * rule).setOrgItem("pk_org"); processer.addAfterRule(rule);
		 */

	}

	private void addBeforeRule(CompareAroundProcesser<AggTaskHVO> processer) {
		// TODO ǰ����
		IRule<AggTaskHVO> rule = null;
		rule = new nc.bs.pubapp.pub.rule.FillUpdateDataRule();
		processer.addBeforeRule(rule);
		/*
		 * nc.impl.pubapp.pattern.rule.ICompareRule<AggTaskHVO> ruleCom = new
		 * nc.bs.pubapp.pub.rule.UpdateBillCodeRule();
		 * ((nc.bs.pubapp.pub.rule.UpdateBillCodeRule) ruleCom)
		 * .setCbilltype("QC08"); ((nc.bs.pubapp.pub.rule.UpdateBillCodeRule)
		 * ruleCom) .setCodeItem("billno");
		 * ((nc.bs.pubapp.pub.rule.UpdateBillCodeRule) ruleCom)
		 * .setGroupItem("pk_group");
		 * ((nc.bs.pubapp.pub.rule.UpdateBillCodeRule) ruleCom)
		 * .setOrgItem("pk_org"); processer.addBeforeRule(ruleCom);
		 */
		rule = new TaskCheckRule();
		processer.addBeforeRule(rule);
	}

}
