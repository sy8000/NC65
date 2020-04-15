package nc.vo.qcco.qccommission;

import nc.md.model.IEnumValue;
import nc.md.model.impl.MDEnum;

public class DocStates extends MDEnum {

	
	public DocStates(IEnumValue enumValue) {
		super(enumValue);
	}
	//委托单创建
	public static final DocStates COMISSION_CREATE = MDEnum.valueOf(DocStates.class, Integer.valueOf(1));
	//客户主管审
	public static final DocStates CLIENT_APPR = MDEnum.valueOf(DocStates.class, Integer.valueOf(2));
	//技术主管审
	public static final DocStates TECH_APPR = MDEnum.valueOf(DocStates.class, Integer.valueOf(3));
	//报价单生成
	public static final DocStates QUOTATION_GENERATION = MDEnum.valueOf(DocStates.class, Integer.valueOf(4));
	//报价单确认
	public static final DocStates QUOTATION_CONFIRMATION = MDEnum.valueOf(DocStates.class, Integer.valueOf(5));
	//样品待接收
	public static final DocStates SAMPLE_RECEIVED = MDEnum.valueOf(DocStates.class, Integer.valueOf(6));
	//计费单生成
	public static final DocStates GENERATE_BILLING = MDEnum.valueOf(DocStates.class, Integer.valueOf(7));
	//报告待确认
	public static final DocStates REPORT_CONFIRMED = MDEnum.valueOf(DocStates.class, Integer.valueOf(8));
	//满意度评价
	public static final DocStates SATISFACTION_EVALUATION = MDEnum.valueOf(DocStates.class, Integer.valueOf(9));
	//流程结束
	public static final DocStates END_PROCESS = MDEnum.valueOf(DocStates.class, Integer.valueOf(10));
	//其他状态_1
	public static final DocStates OTHER_STATUS_1 = MDEnum.valueOf(DocStates.class, Integer.valueOf(11));
	//其他状态_2
	public static final DocStates OTHER_STATUS_2 = MDEnum.valueOf(DocStates.class, Integer.valueOf(12));
	//其他状态_3
	public static final DocStates OTHER_STATUS_3 = MDEnum.valueOf(DocStates.class, Integer.valueOf(13));
}
