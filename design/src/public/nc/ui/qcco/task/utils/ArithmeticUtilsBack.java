package nc.ui.qcco.task.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @Title: com.zly.ArithmeticUtils.java
 * @desc: ������������
 * @author Tank
 * @date 2019��4��7��16:36:46
 */
public class ArithmeticUtilsBack {
    public static final int FORMAT_MAX_LENGTH = 500;// ���ʽ��󳤶�����
    public static final int RESULT_DECIMAL_MAX_LENGTH = 8;// ���С������󳤶�����
    public static final Map<Character, Integer> symLvMap = new HashMap<Character, Integer>();// �������ȼ�map
    static {
        symLvMap.put('=', 0);
        symLvMap.put('-', 1);
        symLvMap.put('+', 1);
        symLvMap.put('*', 2);
        symLvMap.put('/', 2);
        symLvMap.put('(', 3);
        symLvMap.put(')', 1);
        // symLvMap.put('^', 3);
        // symLvMap.put('%', 3);
    }

    /**
     *
     * @Title: checkFormat
     * @Desc: �����ʽ��ʽ�Ƿ���ȷ
     *
     * @param str ���ʽ
     * @return true���ʽ��ȷ��false���ʽ����
     *
     */
    public static boolean checkFormat(String str) {
        // У���Ƿ��ԡ�=����β
        if ('=' != str.charAt(str.length() - 1)) {
            return false;
        }
        // У�鿪ͷ�Ƿ�Ϊ���ֻ��ߡ�(��
        if (!(isCharNum(str.charAt(0)) || str.charAt(0) == '(')) {
            return false;
        }
        char c;
        // У��
        for (int i = 1; i < str.length() - 1; i++) {
            c = str.charAt(i);
            if (!isCorrectChar(c)) {// �ַ����Ϸ�
                return false;
            }
            if (!(isCharNum(c))) {
                if (c == '-' || c == '+' || c == '*' || c == '/') {
                    if (c == '-' && str.charAt(i - 1) == '(') {// 1*(-2+3)�����
                        continue;
                    }
                    if (!(isCharNum(str.charAt(i - 1)) || str.charAt(i - 1) == ')')) {// ������ǰһ���������ֻ��ߡ�����ʱ
                        return false;
                    }
                }
                if (c == '.') {
                    if (!isCharNum(str.charAt(i - 1)) || !isCharNum(str.charAt(i + 1))) {// У�顰.����ǰ���Ƿ�λ����
                        return false;
                    }
                }
            }
        }
        return isBracketCouple(str);// У�������Ƿ����
    }

    /**
     *
     * @Title: change2StandardFormat
     * @Desc: ������ʽ��ʽΪ��׼��ʽ����2(-1+2)(3+4)��Ϊ2*(0-1+2)*(3+4)
     *
     * @param str
     * @return ��׼���ʽ
     *
     */
    public static String change2StandardFormat(String str) {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (i != 0 && c == '(' && (isCharNum(str.charAt(i - 1)) || str.charAt(i - 1) == ')')) {
                sb.append("*(");
                continue;
            }
            if (c == '-' && str.charAt(i - 1) == '(') {
                sb.append("0-");
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     *
     * @Title: isBracketCouple
     * @Desc: У�������Ƿ����
     * @param str
     * @return ����
     *
     */
    public static boolean isBracketCouple(String str) {
        LinkedList<Character> stack = new LinkedList<>();
        for (char c : str.toCharArray()) {
            if (c == '(') {
                stack.add(c);
            } else if (c == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.removeLast();
            }
        }
        if (stack.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     *
     * @Title: formatResult
     * @Desc: �������������ʾ
     *
     * @param str ������
     * @return �淶�ļ�����
     *
     */
    public static String formatResult(String str) {
        String[] ss = str.split("\\.");
        if (Integer.parseInt(ss[1]) == 0) {
            return ss[0];// ����
        }
        String s1 = new StringBuilder(ss[1]).reverse().toString();
        int start = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != '0') {
                start = i;
                break;
            }
        }
        return ss[0] + "." + new StringBuilder(s1.substring(start, s1.length())).reverse();
    }

    /**
     *
     * @Title: isCorrectChar
     * @Desc: У���ַ��Ƿ�Ϸ�
     *
     * @param c
     * @return ����
     *
     */
    public static boolean isCorrectChar(Character c) {
        if (('0' <= c && c <= '9') || c == '-' || c == '+' || c == '*' || c == '/' || c == '(' || c == ')'
                || c == '.') {
            return true;
        }
        return false;
    }

    /**
     *
     * @Title: isCharNum
     * @Desc: �ж��Ƿ�Ϊ����
     *
     * @param c
     * @return
     *
     */
    public static boolean isCharNum(Character c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        return false;

    }

    /**
     *
     * @Title: plus
     * @Desc: ��
     *
     * @param num1
     * @param num2
     * @return ������
     *
     */
    public static double plus(double num1, double num2) {
        return num1 + num2;
    }

    /**
     *
     * @Title: reduce
     * @Desc: ��
     *
     * @param num1
     * @param num2
     * @return ������
     *
     */
    public static double reduce(double num1, double num2) {
        return num1 - num2;
    }

    /**
     *
     * @Title: multiply
     * @Desc: ��
     *
     * @param num1
     * @param num2
     * @return ������
     *
     */
    public static double multiply(double num1, double num2) {
        return num1 * num2;

    }

    /**
     *
     * @Title: divide
     * @Desc: ��
     *
     * @param num1
     * @param num2
     * @return ������
     *
     */
    public static double divide(double num1, double num2) {
        return num1 / num2;
    }
}
