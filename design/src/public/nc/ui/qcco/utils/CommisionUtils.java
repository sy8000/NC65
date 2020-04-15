package nc.ui.qcco.utils;

import java.text.SimpleDateFormat;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;

public class CommisionUtils {
	
	public String getCommissionPreCode(String name) {
		IUAPQueryBS query = NCLocator.getInstance().lookup(IUAPQueryBS.class);

		String maxcode = null;
		try {
			maxcode = (String) query
					.executeQuery(
							"select max(billno) from qc_commission_h where billno like 'A-______-____' and billno not like 'A-______-9999'",
							new ColumnProcessor());
		} catch (BusinessException ex) {
			ExceptionUtils.wrappBusinessException(ex.getMessage());
		}
		maxcode = maxcode == null || maxcode.equals("") ? "0000" : maxcode.substring(maxcode.length() - 4);
		
		SimpleDateFormat dt = new SimpleDateFormat("yyMMdd");
		String seed = name + "-" + dt.format(new UFDate().toDate()) + "-";
		
		String rtn = seed + String.format("%04d", Integer.valueOf(maxcode) + 1);
		
		return rtn;
	}
}
