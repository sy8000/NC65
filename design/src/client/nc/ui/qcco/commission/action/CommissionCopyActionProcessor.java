package nc.ui.qcco.commission.action;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.jdbc.framework.processor.MapProcessor;
import nc.ui.pubapp.uif2app.actions.intf.ICopyActionProcessor;
import nc.ui.qcco.commission.refmodel.CommissionCodePrefixRefModel;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFLiteralDate;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.qcco.commission.AggCommissionHVO;
import nc.vo.qcco.commission.CommissionBVO;
import nc.vo.qcco.commission.CommissionHVO;
import nc.vo.qcco.commission.CommissionRVO;
import nc.vo.uif2.LoginContext;

import org.apache.commons.lang.StringUtils;

public class CommissionCopyActionProcessor implements ICopyActionProcessor<AggCommissionHVO> {

	@Override
	public void processVOAfterCopy(AggCommissionHVO billVO, LoginContext context) {
		processHeadVO(billVO, context);
		// 子表和孙表的关联通过主键,所以在保存的时候在清除
		//processBodyVO(billVO);
	}

	public static void processBodyVO(AggCommissionHVO billVO) {
		CircularlyAccessibleValueObject[] childVOs = billVO.getAllChildrenVO();

		if (childVOs != null && childVOs.length > 0) {
			for (CircularlyAccessibleValueObject childVO : childVOs) {
					String uuid = UUID.randomUUID().toString();
					uuid = uuid.replace("-", "");
				((CommissionBVO) childVO).setAttributeValue("uniqueid", uuid);;
			}
		}
	}

	private void processHeadVO(AggCommissionHVO billVO, LoginContext context) {
		CommissionHVO headVO = (CommissionHVO) billVO.getParent();
		IUAPQueryBS query = NCLocator.getInstance().lookup(IUAPQueryBS.class);

		String maxcode = null;
		String pk_psndoc = null;
		String pk_psnorg = null;
		String pk_dept = null;
		String psnName = null;
		String psnEmail = null;
		String psnPhone = null;
		try {
			// 人员档案
			pk_psndoc = (String) query.executeQuery("select pk_psndoc from sm_user where cuserid = '"
					+ InvocationInfoProxy.getInstance().getUserId() + "'", new ColumnProcessor());
			// 人员所在组织
			pk_psnorg = (String) query.executeQuery("select pk_org from sm_user where cuserid = '"
					+ InvocationInfoProxy.getInstance().getUserId() + "'", new ColumnProcessor());
			// 员工所在所在部门
			if (!StringUtils.isEmpty(pk_psndoc)) {
				pk_dept = (String) query.executeQuery("select pk_dept from bd_psnjob where pk_psndoc = '" + pk_psndoc
						+ "' and '" + new UFLiteralDate().toString()
						+ "' between indutydate and isnull(enddutydate, '9999-12-31')", new ColumnProcessor());

				Map vals = (Map) query.executeQuery(
						"select name, email, isnull(officephone, mobile) teleno from bd_psndoc where pk_psndoc =  '"
								+ pk_psndoc + "'", new MapProcessor());
				psnName = (String) vals.get("name");
				psnEmail = (String) vals.get("email");
				psnPhone = (String) vals.get("teleno");
			}
		} catch (BusinessException ex) {
			ExceptionUtils.wrappBusinessException(ex.getMessage());
		}

		headVO.setAttributeValue("pk_owner", pk_psnorg);
		headVO.setAttributeValue("pk_payer", pk_psnorg);
		headVO.setAttributeValue("pk_dept", pk_dept);
		headVO.setAttributeValue("cuserid", pk_psndoc);
		headVO.setAttributeValue("contract", psnName);
		headVO.setAttributeValue("email", psnEmail);
		headVO.setAttributeValue("teleno", psnPhone);

		CommissionCodePrefixRefModel refmodel = new CommissionCodePrefixRefModel();
		String name = (String) ((Vector) refmodel.matchPkData(headVO.getCodeprefix()).get(0)).get(1);
		SimpleDateFormat dt = new SimpleDateFormat("yyMMdd");
		String seed = name + "-" + dt.format(new UFDate().toDate()) + "-";

		try {
			maxcode = (String) query.executeQuery(
					"select max(billno) from qc_commission_h where billno like 'A-______-____' and billno not like 'A-______-9999' and dr = 0", 
					new ColumnProcessor());
		} catch (BusinessException ex) {
			ExceptionUtils.wrappBusinessException(ex.getMessage());
		}
		maxcode = maxcode == null || maxcode.equals("") ? "0000" : maxcode.substring(maxcode.length() - 4);
		headVO.setAttributeValue("billno", seed + String.format("%04d", Integer.valueOf(maxcode) + 1));

		headVO.setBillname(null);
		headVO.setStatus(VOStatus.NEW);
		headVO.setPk_commission_h(null);
	}
}
