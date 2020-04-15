package nc.ui.qcco.utils;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Stack;

import nc.vo.pmpub.common.utils.ArrayUtils;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDouble;

import org.apache.commons.lang.StringUtils;

public class Calculator {
    private static String[] digiOprs = { "+", "-", "*", "/", "^" };
    private static String[] bracketOprs = { "(", ")" };
    private static String[] logiOprs = { ">=", "<=", "<>", "==", ">", "<", "!", "=" };
    private static String[] keyWords = LogicalCalculator.getKeyWords();
    private static String[] digits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    public static UFDouble evalExp(String expression) throws BusinessException {
	// ȡ�׸��ؼ���
	String exp = expression.trim();
	char[] expChars = exp.toCharArray();
	// ���ʽΪ��ʱ����0
	if (StringUtils.isEmpty(exp)) {
	    return UFDouble.ZERO_DBL;
	}

	// У��
	validate(exp, expChars);

	Stack<String> exps = new Stack<String>();
	String key = "";
	String digit = "";
	DecimalFormat decimalFormat = new DecimalFormat("#0.00000000");
	for (char chr : expChars) {
	    if (chr == '(') {
		if (!StringUtils.isEmpty(key)) {
		    exps.push(key);
		    key = "";
		}
		exps.push("(");
	    } else if (chr == ')') {
		if (!StringUtils.isEmpty(key)) {
		    exps.push(key);
		    key = "";
		} else if (!StringUtils.isEmpty(digit)) {
		    exps.push(digit);
		    digit = "";
		}
		if (!exps.contains("(")) {
		    throw new BusinessException("���Ų�ƥ��");
		} else {
		    String innerExp = ")";
		    while (exps.peek() != "(") {
			innerExp = exps.pop() + innerExp;
		    }
		    innerExp = exps.pop() + innerExp; // ����"("
		    if (exps.size() > 0 && contains(keyWords, exps.peek())) {
			innerExp = exps.pop() + innerExp; // ����������
			exps.push(LogicalCalculator.eval(innerExp)); // ִ�к���
		    } else {
			exps.push(decimalFormat.format(SimpleCalculator.evalExp(innerExp))); // ִ�м���
		    }
		}
	    } else if (contains(digiOprs, String.valueOf(chr))) {
		// opr
		if ((chr == '+' || chr == '-') && StringUtils.isEmpty(digit) && StringUtils.isEmpty(key)) {
		    digit += chr;
		} else {
		    if (!StringUtils.isEmpty(digit)) {
			exps.push(digit);
			digit = "";
		    } else if (!StringUtils.isEmpty(key)) {
			exps.push(key);
			key = "";
		    }
		    exps.push(String.valueOf(chr));
		}
	    } else if (contains(digits, String.valueOf(chr)) || chr == '.') {
		if (!StringUtils.isEmpty(key)) {
		    exps.push(key);
		    key = "";
		}
		// digit
		digit += chr;
	    } else if (chr != ' ' && chr != '\n' && chr != '\r' && chr != '\t') {
		if (!StringUtils.isEmpty(digit)) {
		    exps.push(digit);
		    digit = "";
		}

		if (chr == ',') {
		    exps.push(",");
		} else {
		    // key
		    key += chr;
		}
	    }
	}

	if (!StringUtils.isEmpty(key)) {
	    exps.push(key);
	    key = "";
	} else if (!StringUtils.isEmpty(digit)) {
	    exps.push(digit);
	    digit = "";
	}

	if (exps.size() > 1) {
	    while (exps.size() > 0) {
		key = exps.pop() + key;
	    }

	    String chkExp = key;
	    for (String opr : ArrayUtils.mergeArrays(digiOprs, bracketOprs, digits, new String[] { "." })) {
		chkExp = chkExp.replace(opr, " ");
	    }

	    if (StringUtils.isEmpty(chkExp.trim())) {
		return new UFDouble(SimpleCalculator.evalExp(key)); // ����ѧ���ʽ��ֱ����ֵ
	    } else {
		return evalExp(key); // ���йؼ��ֵ�Ϊ��Ȼ���߼����ʽ����Ҫ�ݹ���ֵ
	    }
	} else {
	    return new UFDouble(exps.pop());
	}
    }

    private static boolean contains(String[] strArray, String chkChr) {
	for (String str : strArray) {
	    if (str.equals(chkChr)) {
		return true;
	    }
	}

	return false;
    }

    private static void validate(String exp, char[] expChars) throws BusinessException {
	// ������������Ƿ�ƥ��
	int iLeft = 0;
	int iRight = 0;
	for (char chr : expChars) {
	    if (chr == '(') {
		iLeft++;
	    } else if (chr == ')') {
		iRight++;
	    }
	}
	if (iLeft != iRight) {
	    throw new BusinessException("���Ų�ƥ��");
	}

	// �ؼ��ʼ��
	for (String opr : ArrayUtils.mergeArrays(digiOprs, bracketOprs, logiOprs, keyWords, digits, new String[] { ",",
		"." })) {
	    exp = exp.replace(opr, " ");
	}
	exp = exp.trim();
	String[] words = exp.split(" ");
	String msg = "";
	for (String wrd : words) {
	    if (!StringUtils.isEmpty(wrd)) {
		if (!StringUtils.isEmpty(msg)) {
		    msg = msg + "," + wrd;
		} else {
		    msg = wrd;
		}
	    }
	}
	if (!StringUtils.isEmpty(msg)) {
	    throw new BusinessException("�ؼ��ִ���: " + msg);
	}
    }

    public static <T> T[] mergeArrays(T[]... arrays) {
	if (isEmpty(arrays))
	    return null;
	int length = 0;
	for (T[] array : arrays) {
	    if (!isEmpty(array))
		length = getLength(array) + length;
	}
	T[] mergedArray = (T[]) Array.newInstance(arrays.getClass().getComponentType().getComponentType(), length);

	int pos = 0;
	for (T[] array : arrays) {
	    if (!isEmpty(array))
		System.arraycopy(array, 0, mergedArray, pos, getLength(array));
	    pos += getLength(array);
	}

	return mergedArray;
    }

    public static boolean isEmpty(Object[] array) {
	if ((array == null) || (array.length == 0)) {
	    return true;
	}
	return false;
    }

    public static int getLength(Object array) {
	if (array == null) {
	    return 0;
	}
	return Array.getLength(array);
    }

}
