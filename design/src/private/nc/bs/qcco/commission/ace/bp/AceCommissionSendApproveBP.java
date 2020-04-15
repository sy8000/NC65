package nc.bs.qcco.commission.ace.bp;

import nc.impl.pubapp.pattern.data.bill.BillUpdate;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.pub.VOStatus;
import nc.vo.pub.pf.BillStatusEnum;

/**
 * ��׼���������BP
 */
public class AceCommissionSendApproveBP {
	/**
	 * ������
	 * 
	 * @param vos
	 *            ����VO����
	 * @param script
	 *            ���ݶ����ű�����
	 * @return �����ĵ���VO����
	 */

	public AggCommissionHVO[] sendApprove(AggCommissionHVO[] clientBills,
			AggCommissionHVO[] originBills) {
		for (AggCommissionHVO clientFullVO : clientBills) {
			clientFullVO.getParentVO().setAttributeValue("${vmObject.billstatus}",
					BillStatusEnum.COMMIT.value());
			clientFullVO.getParentVO().setStatus(VOStatus.UPDATED);
		}
		// ���ݳ־û�
		AggCommissionHVO[] returnVos = new BillUpdate<AggCommissionHVO>().update(
				clientBills, originBills);
		return returnVos;
	}
}