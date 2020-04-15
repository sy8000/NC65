package nc.itf.qcco;

import java.util.Map;
import java.util.Set;

import nc.bs.dao.DAOException;
import nc.ui.pub.beans.constenum.IConstEnum;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.BusinessException;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.commission.CommissionHVO;

public interface ICommissionMaintain {

	/*
	 * public void delete(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[]
	 * originBills) throws BusinessException;
	 * 
	 * public AggCommissionHVO[] insert(AggCommissionHVO[] clientFullVOs,
	 * AggCommissionHVO[] originBills) throws BusinessException;
	 * 
	 * public AggCommissionHVO[] update(AggCommissionHVO[] clientFullVOs,
	 * AggCommissionHVO[] originBills) throws BusinessException;
	 */
	public AggCommissionHVO[] query(IQueryScheme queryScheme) throws BusinessException;

	public AggCommissionHVO[] save(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[] originBills)
			throws BusinessException;

	public AggCommissionHVO[] unsave(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[] originBills)
			throws BusinessException;

	public AggCommissionHVO[] approve(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[] originBills)
			throws BusinessException;

	public AggCommissionHVO[] unapprove(AggCommissionHVO[] clientFullVOs, AggCommissionHVO[] originBills)
			throws BusinessException;

	public AggCommissionHVO[] insert(AggCommissionHVO[] vos) throws BusinessException;

	public void delete(AggCommissionHVO[] aggCommissionHVOs) throws BusinessException;

	public AggCommissionHVO[] update(AggCommissionHVO[] vos) throws BusinessException;
	
	public IConstEnum[] getRefName(IConstEnum[] o,Map<String,Map<String,String>> refNamePkMap,Map<String,Object[]> realPksMap) throws BusinessException ;

	/**
	 * ��ʽ���沵��
	 * @param parentVO
	 * @param txtMessage
	 */
	public void officialReject(CommissionHVO parentVO, String txtMessage) throws BusinessException;
	/**
	 * ��ʽ����ȷ��
	 * @param parentVO
	 * @param txtMessage
	 */
	public void officialComfirm(CommissionHVO parentVO, String txtMessage) throws BusinessException;
	
	/**
	 * �շѵ�ȷ��
	 * @param parentVO
	 */
	public void payDemandComfirtm(CommissionHVO parentVO) throws BusinessException;
	/**
	 * ���۵�ȷ��
	 * @param commissionHVO
	 */
	public void quotationConfirtm(CommissionHVO commissionHVO) throws BusinessException;
	/**
	 * ���������
	 * @param parentVO
	 */
	public void satisfactComfirtm(CommissionHVO parentVO) throws BusinessException;
	
	/**
	 * ί�е��Ƿ���Ա༭
	 * �����Ѿ��ύ,���ܱ༭
	 * @param 
	 * @return
	 * @throws DAOException 
	 */
	boolean isEditAble(String pk_commission_h);
	
	
	

}
