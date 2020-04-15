package nc.vo.qcco.task;

import nc.md.model.IEnumValue;
import nc.md.model.impl.MDEnum;

public class ValueWaysEnum extends MDEnum {

	public ValueWaysEnum(IEnumValue enumValue) {
		super(enumValue);
	}

	/**
	 * 手工填写
	 */
	public static final ValueWaysEnum MNU = MDEnum.valueOf(ValueWaysEnum.class, Integer.valueOf(1));

	/**
	 * 参照选择
	 */
	public static final ValueWaysEnum REF = MDEnum.valueOf(ValueWaysEnum.class, Integer.valueOf(2));

	/**
	 * 自动带出
	 */
	public static final ValueWaysEnum AUTO = MDEnum.valueOf(ValueWaysEnum.class, Integer.valueOf(3));
}
