package nc.ui.qcco.commission.ace.serviceproxy;

import java.util.HashMap;
import java.util.Map;

import nc.bs.framework.common.NCLocator;
import nc.desktop.ui.WorkbenchEnvironment;
import nc.itf.pubapp.uif2app.components.grand.IGrandAggVosQueryService;
import nc.itf.qcco.ICommissionMaintain;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pubapp.uif2app.actions.IDataOperationService;
import nc.ui.pubapp.uif2app.components.grand.model.AbstractGrandQueryService;
import nc.ui.pubapp.uif2app.query2.model.IQueryService;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pubapp.pattern.model.entity.bill.IBill;
import nc.vo.pubapp.query2.sql.process.QuerySchemeProcessor;
import nc.vo.qcco.commission.AggCommissionHVO;

/**
 * ʾ�����ݵĲ�������
 * 
 * @author author
 * @version tempProject version
 */
public class AceCommissionMaintainProxy extends AbstractGrandQueryService implements IDataOperationService,
		IQueryService {
	@Override
	public Object[] queryByQueryScheme(IQueryScheme queryScheme) throws Exception {
		// ICommissionMaintain query = NCLocator.getInstance().lookup(
		// ICommissionMaintain.class);
		// return query.query(queryScheme);
		//���ݹ���
		filter(queryScheme);
		IGrandAggVosQueryService query = NCLocator.getInstance().lookup(IGrandAggVosQueryService.class);
		Map<String, CircularlyAccessibleValueObject> relationShip = super.getGrandTabAndVOMap();
		AggCommissionHVO[] result = (AggCommissionHVO[]) query.query(queryScheme, null,
				(HashMap<String, CircularlyAccessibleValueObject>) relationShip, AggCommissionHVO.class);
		return result;
	}

	/**
	 * tank
	 * �ͻ�ϣ��ÿ����ֻ�ܿ���������뷢��������ĵ��� 
	 * @param queryScheme
	 */
	private void filter(IQueryScheme queryScheme) {
		QuerySchemeProcessor qry = new QuerySchemeProcessor(queryScheme);
		String pk_user = WorkbenchEnvironment.getInstance().getLoginUser().getPrimaryKey();
		qry.appendWhere(" and (creator='"+pk_user+"' "
				+ " or pk_commission_h in(select pk_commission_h from qc_task_h where pk_task_h "
				+ " in(select distinct billversionpk from pub_workflownote where checkman = '"+pk_user+"') )) ");
	}

	@Override
	public IBill[] insert(IBill[] value) throws BusinessException {
		ICommissionMaintain operator = NCLocator.getInstance().lookup(ICommissionMaintain.class);
		AggCommissionHVO[] vos = (AggCommissionHVO[]) operator.insert((AggCommissionHVO[]) value);
		return vos;
	}

	@Override
	public IBill[] update(IBill[] value) throws BusinessException {
		ICommissionMaintain operator = NCLocator.getInstance().lookup(ICommissionMaintain.class);
		AggCommissionHVO[] vos = (AggCommissionHVO[]) operator.update((AggCommissionHVO[]) value);
		return vos;
	}

	@Override
	public IBill[] delete(IBill[] value) throws BusinessException {

		ICommissionMaintain operator = NCLocator.getInstance().lookup(ICommissionMaintain.class);
		operator.delete((AggCommissionHVO[]) value);
		return value;
	}

}