package nc.ui.qcco.task.utils;

import java.util.Map;
import java.util.Set;

import nc.vo.pub.BusinessException;

public class FormulaUtils {
	// 公式解析计算
	public static double calFormula(String key, Map<String, String> map) throws BusinessException {
		if (key != null && map != null && map.get(key) != null) {
			// 取公式
			String formula = map.get(key);
			if (formula != null) {
				formula = formula.replaceAll("\r\n|\r|\n", "");
				if (formula.contains("return")) {
					// 公式分解 返回 (a+b)/c 的形式
					formula = spiltFormula(formula);
					if (formula != null) {
						// 公式解析 返回(1+2)/3 的形式
						formula = analysisFormula(formula,map);
						// 公式计算
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
		// 判断是否返回值
		if (returnIndex + 6 <= formula.length()) {
			String resultStr = formula.substring(returnIndex + 6);
			formula = formula.replaceAll("return" + resultStr, "");
			// 判断公式中是否有结果计算
			if (formula.contains(resultStr + "=")) {
				int rsIndex = formula.indexOf(resultStr + "=");
				// 是否有返回值
				if (rsIndex >= 0 && rsIndex < formula.length() - 1) {
					return formula.replaceAll(resultStr + "=", "");
				} else {
					throw new BusinessException("公式验证失败!未检测到返回值:[" + formula + "]");
				}
			}
		}
		return null;
	}
}
