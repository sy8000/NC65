package nc.ui.qcco.commission.ace.handler;

public class CommissionShowTemplate {
	public final static String QUALITY_COMPLAINT_NAME = "����Ͷ��";

	public final static String ROUTINE_TESTING_NAME = "������";

	public final static String CUSTOMER_REQUIREMENTS_NAME = "�ͻ�Ҫ��";

	public final static String PERIODIC_TEST_NAME = "���ڼ���";

	public final static String PRODUCT_DEVELOPMENT_NAME = "��Ʒ����";

	public final static String SAFETY_CERTIFICATION_NAME = "��ȫ��֤";

	public final static String PROCESS_TESTING_NAME = "���̲���";

	/**
	 * ������ĳ��ģ����Ҫ��ʾ���ֶμ���
	 */

	// ����Ͷ��
	static final String[] QUALITY_COMPLAINT_VALUES = { "testaim", "progressneed" };

	// ������
	static final String[] ROUTINE_TESTING_VALUES = { "testaim", "progressneed", "productproperty" };

	// �ͻ�Ҫ��
	static final String[] CUSTOMER_REQUIREMENTS_VALUES = { "progressneed", "customername", "customertype",
			"testrequirement" };

	// ���ڼ���
	static final String[] PERIODIC_TEST_VALUES = { "progressneed", "checkingproperty", "productline", "batchnumber",
			"productdate", "batchserial" };

	// ��Ʒ����
	static final String[] PRODUCT_DEVELOPMENT_VALUES = { "progressneed", "identificationtype" };

	// ��ȫ��֤
	static final String[] SAFETY_CERTIFICATION_VALUES = { "progressneed", "certificationtype" };

	// ���̲���
	static final String[] PROCESS_TESTING_VALUES = { "testaim", "progressneed", "itemnumber" };

	static final String[] ALL_TEMPLATE_FIELD_VALUES = { "testaim", "progressneed", "productproperty", "customername",
			"customertype", "testrequirement", "checkingproperty", "productline", "batchnumber", "productdate",
			"batchserial", "identificationtype", "certificationtype", "itemnumber" };
	static final String[] PK_ALL_TEMPLATE_FIELD_VALUES = { "pk_commission_h.testaim", "pk_commission_h.progressneed", "pk_commission_h.productproperty", "pk_commission_h.customername",
		"pk_commission_h.customertype", "pk_commission_h.testrequirement", "pk_commission_h.checkingproperty", "pk_commission_h.productline", "pk_commission_h.batchnumber", "pk_commission_h.productdate",
		"pk_commission_h.batchserial", "pk_commission_h.identificationtype", "pk_commission_h.certificationtype", "pk_commission_h.itemnumber" };

	/**
	 * ����ģ����:����Ͷ��,������,�ͻ�Ҫ��...��ȡģ��
	 */
	public static String[] getTemplateByName(String tempLateName) {
		switch (tempLateName) {
		case QUALITY_COMPLAINT_NAME:
			return QUALITY_COMPLAINT_VALUES;

		case ROUTINE_TESTING_NAME:
			return ROUTINE_TESTING_VALUES;

		case CUSTOMER_REQUIREMENTS_NAME:
			return CUSTOMER_REQUIREMENTS_VALUES;

		case PERIODIC_TEST_NAME:
			return PERIODIC_TEST_VALUES;

		case PRODUCT_DEVELOPMENT_NAME:
			return PRODUCT_DEVELOPMENT_VALUES;

		case SAFETY_CERTIFICATION_NAME:
			return SAFETY_CERTIFICATION_VALUES;

		case PROCESS_TESTING_NAME:
			return PROCESS_TESTING_VALUES;
		default:
			return null;
		}

	}

	public static String[] getTemplateWithAllField() {
		return ALL_TEMPLATE_FIELD_VALUES;

	}
	public static String[] getTemplateWithAllField2() {
		return PK_ALL_TEMPLATE_FIELD_VALUES;

	}

}
