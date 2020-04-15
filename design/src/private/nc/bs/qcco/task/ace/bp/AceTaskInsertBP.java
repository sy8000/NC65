package nc.bs.qcco.task.ace.bp;

import nc.bs.qcco.task.plugin.bpplugin.TaskPluginPoint;
import nc.bs.qcco.task.rule.TaskCheckRule;
import nc.impl.pubapp.pattern.rule.IRule;
import nc.impl.pubapp.pattern.rule.processer.AroundProcesser;
import nc.util.mmpub.dpub.gc.GCInsertBPTemplate;
import nc.vo.qcco.task.AggTaskHVO;

/**
 * ��׼��������BP
 */
public class AceTaskInsertBP {

	public AggTaskHVO[] insert(AggTaskHVO[] bills) {
		GCInsertBPTemplate<AggTaskHVO> bp = new GCInsertBPTemplate<AggTaskHVO>(TaskPluginPoint.INSERT);
		this.addBeforeRule(bp.getAroundProcesser());
		this.addAfterRule(bp.getAroundProcesser());
		return bp.insert(bills);

	}

	/**
	 * ���������
	 * 
	 * @param processor
	 */
	private void addAfterRule(AroundProcesser<AggTaskHVO> processor) {
		// TODO ���������
		/*
		 * IRule<AggTaskHVO> rule = null; rule = new
		 * nc.bs.pubapp.pub.rule.BillCodeCheckRule();
		 * ((nc.bs.pubapp.pub.rule.BillCodeCheckRule)
		 * rule).setCbilltype("QC08");
		 * ((nc.bs.pubapp.pub.rule.BillCodeCheckRule) rule)
		 * .setCodeItem("billno"); ((nc.bs.pubapp.pub.rule.BillCodeCheckRule)
		 * rule) .setGroupItem("pk_group");
		 * ((nc.bs.pubapp.pub.rule.BillCodeCheckRule)
		 * rule).setOrgItem("pk_org"); processor.addAfterRule(rule);
		 */
	}

	/**
	 * ����ǰ����
	 * 
	 * @param processor
	 */
	private void addBeforeRule(AroundProcesser<AggTaskHVO> processer) {
		// TODO ����ǰ����
		IRule<AggTaskHVO> rule = null;
		rule = new nc.bs.pubapp.pub.rule.FillInsertDataRule();
		processer.addBeforeRule(rule);
		/*
		 * rule = new nc.bs.pubapp.pub.rule.CreateBillCodeRule();
		 * ((nc.bs.pubapp.pub.rule.CreateBillCodeRule)
		 * rule).setCbilltype("QC08");
		 * ((nc.bs.pubapp.pub.rule.CreateBillCodeRule) rule)
		 * .setCodeItem("billno"); ((nc.bs.pubapp.pub.rule.CreateBillCodeRule)
		 * rule) .setGroupItem("pk_group");
		 * ((nc.bs.pubapp.pub.rule.CreateBillCodeRule)
		 * rule).setOrgItem("pk_org"); processer.addBeforeRule(rule);
		 */
		rule = new TaskCheckRule();
		processer.addBeforeRule(rule);
	}
}
