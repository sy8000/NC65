package nc.vo.qcco.qccommission;

import nc.md.model.IEnumValue;
import nc.md.model.impl.MDEnum;

public class DocStates extends MDEnum {

	
	public DocStates(IEnumValue enumValue) {
		super(enumValue);
	}
	//ί�е�����
	public static final DocStates COMISSION_CREATE = MDEnum.valueOf(DocStates.class, Integer.valueOf(1));
	//�ͻ�������
	public static final DocStates CLIENT_APPR = MDEnum.valueOf(DocStates.class, Integer.valueOf(2));
	//����������
	public static final DocStates TECH_APPR = MDEnum.valueOf(DocStates.class, Integer.valueOf(3));
	//���۵�����
	public static final DocStates QUOTATION_GENERATION = MDEnum.valueOf(DocStates.class, Integer.valueOf(4));
	//���۵�ȷ��
	public static final DocStates QUOTATION_CONFIRMATION = MDEnum.valueOf(DocStates.class, Integer.valueOf(5));
	//��Ʒ������
	public static final DocStates SAMPLE_RECEIVED = MDEnum.valueOf(DocStates.class, Integer.valueOf(6));
	//�Ʒѵ�����
	public static final DocStates GENERATE_BILLING = MDEnum.valueOf(DocStates.class, Integer.valueOf(7));
	//�����ȷ��
	public static final DocStates REPORT_CONFIRMED = MDEnum.valueOf(DocStates.class, Integer.valueOf(8));
	//���������
	public static final DocStates SATISFACTION_EVALUATION = MDEnum.valueOf(DocStates.class, Integer.valueOf(9));
	//���̽���
	public static final DocStates END_PROCESS = MDEnum.valueOf(DocStates.class, Integer.valueOf(10));
	//����״̬_1
	public static final DocStates OTHER_STATUS_1 = MDEnum.valueOf(DocStates.class, Integer.valueOf(11));
	//����״̬_2
	public static final DocStates OTHER_STATUS_2 = MDEnum.valueOf(DocStates.class, Integer.valueOf(12));
	//����״̬_3
	public static final DocStates OTHER_STATUS_3 = MDEnum.valueOf(DocStates.class, Integer.valueOf(13));
}
