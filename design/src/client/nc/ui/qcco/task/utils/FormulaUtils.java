package nc.ui.qcco.task.utils;

import java.util.Map;
import java.util.Set;

import nc.vo.pub.BusinessException;

public class FormulaUtils {
	// ��ʽ��������
	public static double calFormula(String key, Map<String, String> map) throws BusinessException {
		if (key != null && map != null && map.get(key) != null) {
			// ȡ��ʽ
			String formula = map.get(key);
			if (formula != null) {
				formula = formula.replaceAll("\r\n|\r|\n", "");
				if (formula.contains("return")) {
					// ��ʽ�ֽ� ���� (a+b)/c ����ʽ
					formula = spiltFormula(formula);
					if (formula != null) {
						// ��ʽ���� ����(1+2)/3 ����ʽ
						formula = analysisFormula(formula,map);
						// ��ʽ����
						Double rs = Calculator.calculateRex(formula);
						if(rs==null){
							rs = 0.0d;
						}
						return rs;
					}
				}
			}
		}
		return 0d;
	}

	private static String analysisFormula(String formula, Map<String, String> map) {
		Set<String> keySet = map.keySet();
		for(String key :keySet){
			if(map.get(key)!=null && !"".equals(map.get(key))){
				formula = formula.replaceAll(key, map.get(key));
			}
		}
		return formula;
	}

	private static String spiltFormula(String formula) throws BusinessException {
		int returnIndex = formula.indexOf("return");
		// �ж��Ƿ񷵻�ֵ
		if (returnIndex + 6 <= formula.length()) {
			String resultStr = formula.substring(returnIndex + 6);
			formula = formula.replaceAll("return" + resultStr, "");
			// �жϹ�ʽ���Ƿ��н������
			if (formula.contains(resultStr + "=")) {
				int rsIndex = formula.indexOf(resultStr + "=");
				// �Ƿ��з���ֵ
				if (rsIndex >= 0 && rsIndex < formula.length() - 1) {
					return formula.replaceAll(resultStr + "=", "");
				} else {
					throw new BusinessException("��ʽ��֤ʧ��!δ��⵽����ֵ:[" + formula + "]");
				}
			}
		}
		return null;
	}
}
