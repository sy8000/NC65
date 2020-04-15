package nc.bs.qcco.task.ace.bp;

import nc.impl.pubapp.pattern.data.bill.BillUpdate;
import nc.vo.pub.VOStatus;
import nc.vo.qcco.task.AggTaskHVO;

/**
 * ��׼������˵�BP
 */
public class AceTaskApproveBP {

	/**
	 * ��˶���
	 * 
	 * @param vos
	 * @param script
	 * @return
	 */
	public AggTaskHVO[] approve(AggTaskHVO[] clientBills,
			AggTaskHVO[] originBills) {
		for (AggTaskHVO clientBill : clientBills) {
			clientBill.getParentVO().setStatus(VOStatus.UPDATED);
		}
		BillUpdate<AggTaskHVO> update = new BillUpdate<AggTaskHVO>();
		AggTaskHVO[] returnVos = update.update(clientBills, originBills);
		return returnVos;
	}

}
