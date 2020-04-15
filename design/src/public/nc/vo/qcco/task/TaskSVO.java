package nc.vo.qcco.task;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

/**
 * <b> ��̎��Ҫ��������� </b>
 * <p>
 * ��̎����۵�������Ϣ
 * </p>
 * ��������:2019/5/18
 * 
 * @author yonyouBQ
 * @version NCPrj ??
 */

public class TaskSVO extends SuperVO {

	/**
	 * �yԇ�l�����I
	 */
	public String pk_task_s;
	/**
	 * �yԇ�l���
	 */
	public String pk_testconditionitem;
	/**
	 * �yԇ�l���_����
	 */
	public String pk_testconditionitem_back;
	/**
	 * ��B
	 */
	public String conditionstatus;
	/**
	 * �Ƿ���x
	 */
	public UFBoolean isoptional;
	/**
	 * �Ƿ�Ɉ��
	 */
	public UFBoolean isallow_out;
	/**
	 * �x��
	 */
	public String pk_instrument;
	/**
	 * ֵ���
	 */
	public String pk_valuetype;
	/**
	 * ȡֵ��ʽ
	 */
	public Integer valueways;
	/**
	 * �ı�ֵ
	 */
	public String textvalue;
	/**
	 * ����ֵ
	 */
	public String pk_refvalue;
	/**
	 * ��λ
	 */
	public String unit;
	/**
	 * ��ʽ��ֵ
	 */
	public String formatted_entry;
	/**
	 * ��Сֵ
	 */
	public UFDouble min_limit;
	/**
	 * ���ֵ
	 */
	public UFDouble max_limit;
	/**
	 * Ӣ���f��
	 */
	public String englishdescription;

	/**
	 * ��������
	 */
	public String pk_list_table;
	/**
	 * �όӆΓ����I
	 */
	public String pk_task_b;
	/**
	 * �r�g��
	 */
	public UFDateTime ts;
	public Integer Dr;

	public Integer getDr() {
		return Dr;
	}

	public void setDr(Integer dr) {
		Dr = dr;
	}

	public String getPk_testconditionitem_back() {
		return pk_testconditionitem_back;
	}

	public void setPk_testconditionitem_back(String pk_testconditionitem_back) {
		this.pk_testconditionitem_back = pk_testconditionitem_back;
	}

	/**
	 * ���� pk_task_s��Getter����.���������yԇ�l�����I ��������:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getPk_task_s() {
		return this.pk_task_s;
	}

	/**
	 * ����pk_task_s��Setter����.���������yԇ�l�����I ��������:2019/5/18
	 * 
	 * @param newPk_task_s
	 *            java.lang.String
	 */
	public void setPk_task_s(String pk_task_s) {
		this.pk_task_s = pk_task_s;
	}

	/**
	 * ���� pk_testconditionitem��Getter����.���������yԇ�l��� ��������:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getPk_testconditionitem() {
		return this.pk_testconditionitem;
	}

	/**
	 * ����pk_testconditionitem��Setter����.���������yԇ�l��� ��������:2019/5/18
	 * 
	 * @param newPk_testconditionitem
	 *            java.lang.String
	 */
	public void setPk_testconditionitem(String pk_testconditionitem) {
		this.pk_testconditionitem = pk_testconditionitem;
	}

	/**
	 * ���� conditionstatus��Getter����.����������B ��������:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getConditionstatus() {
		return this.conditionstatus;
	}

	/**
	 * ����conditionstatus��Setter����.����������B ��������:2019/5/18
	 * 
	 * @param newConditionstatus
	 *            java.lang.String
	 */
	public void setConditionstatus(String conditionstatus) {
		this.conditionstatus = conditionstatus;
	}

	/**
	 * ���� isoptional��Getter����.���������Ƿ���x ��������:2019/5/18
	 * 
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public UFBoolean getIsoptional() {
		return this.isoptional;
	}

	/**
	 * ����isoptional��Setter����.���������Ƿ���x ��������:2019/5/5
	 * 
	 * @param newIsoptional
	 *            nc.vo.pub.lang.UFBoolean
	 */
	public void setIsoptional(UFBoolean isoptional) {
		this.isoptional = isoptional;
	}

	/**
	 * ���� isallow_out��Getter����.���������Ƿ�Ɉ�� ��������:2019/5/18
	 * 
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public UFBoolean getIsallow_out() {
		return this.isallow_out;
	}

	/**
	 * ����isallow_out��Setter����.���������Ƿ�Ɉ�� ��������:2019/5/18
	 * 
	 * @param newIsallow_out
	 *            nc.vo.pub.lang.UFBoolean
	 */
	public void setIsallow_out(UFBoolean isallow_out) {
		this.isallow_out = isallow_out;
	}

	/**
	 * ���� pk_instrument��Getter����.���������x�� ��������:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getPk_instrument() {
		return this.pk_instrument;
	}

	/**
	 * ����pk_instrument��Setter����.���������x�� ��������:2019/5/18
	 * 
	 * @param newPk_instrument
	 *            java.lang.String
	 */
	public void setPk_instrument(String pk_instrument) {
		this.pk_instrument = pk_instrument;
	}

	/**
	 * ���� pk_valuetype��Getter����.��������ֵ��� ��������:2019/5/18
	 * 
	 * @return nc.vo.bd.defdoc.DefdocVO
	 */
	public String getPk_valuetype() {
		return this.pk_valuetype;
	}

	/**
	 * ����pk_valuetype��Setter����.��������ֵ��� ��������:2019/5/18
	 * 
	 * @param newPk_valuetype
	 *            nc.vo.bd.defdoc.DefdocVO
	 */
	public void setPk_valuetype(String pk_valuetype) {
		this.pk_valuetype = pk_valuetype;
	}

	/**
	 * ���� valueways��Getter����.��������ȡֵ��ʽ ��������:2019/5/18
	 * 
	 * @return nc.vo.qcco.task.ValueWaysEnum
	 */
	public Integer getValueways() {
		return this.valueways;
	}

	/**
	 * ����valueways��Setter����.��������ȡֵ��ʽ ��������:2019/5/18
	 * 
	 * @param newValueways
	 *            nc.vo.qcco.task.ValueWaysEnum
	 */
	public void setValueways(Integer valueways) {
		this.valueways = valueways;
	}

	/**
	 * ���� textvalue��Getter����.���������ı�ֵ ��������:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getTextvalue() {
		return this.textvalue;
	}

	/**
	 * ����textvalue��Setter����.���������ı�ֵ ��������:2019/5/18
	 * 
	 * @param newTextvalue
	 *            java.lang.String
	 */
	public void setTextvalue(String textvalue) {
		this.textvalue = textvalue;
	}

	/**
	 * ���� pk_refvalue��Getter����.������������ֵ ��������:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getPk_refvalue() {
		return this.pk_refvalue;
	}

	/**
	 * ����pk_refvalue��Setter����.������������ֵ ��������:2019/5/18
	 * 
	 * @param newPk_refvalue
	 *            java.lang.String
	 */
	public void setPk_refvalue(String pk_refvalue) {
		this.pk_refvalue = pk_refvalue;
	}

	/**
	 * ���� unit��Getter����.����������λ ��������:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getUnit() {
		return this.unit;
	}

	/**
	 * ����unit��Setter����.����������λ ��������:2019/5/18
	 * 
	 * @param newUnit
	 *            java.lang.String
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * ���� formatted_entry��Getter����.����������ʽ��ֵ ��������:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getFormatted_entry() {
		return this.formatted_entry;
	}

	/**
	 * ����formatted_entry��Setter����.����������ʽ��ֵ ��������:2019/5/18
	 * 
	 * @param newFormatted_entry
	 *            java.lang.String
	 */
	public void setFormatted_entry(String formatted_entry) {
		this.formatted_entry = formatted_entry;
	}

	/**
	 * ���� min_limit��Getter����.����������Сֵ ��������:2019/5/18
	 * 
	 * @return nc.vo.pub.lang.UFDouble
	 */
	public UFDouble getMin_limit() {
		return this.min_limit;
	}

	/**
	 * ����min_limit��Setter����.����������Сֵ ��������:2019/5/18
	 * 
	 * @param newMin_limit
	 *            nc.vo.pub.lang.UFDouble
	 */
	public void setMin_limit(UFDouble min_limit) {
		this.min_limit = min_limit;
	}

	/**
	 * ���� max_limit��Getter����.�����������ֵ ��������:2019/5/18
	 * 
	 * @return nc.vo.pub.lang.UFDouble
	 */
	public UFDouble getMax_limit() {
		return this.max_limit;
	}

	/**
	 * ����max_limit��Setter����.�����������ֵ ��������:2019/5/18
	 * 
	 * @param newMax_limit
	 *            nc.vo.pub.lang.UFDouble
	 */
	public void setMax_limit(UFDouble max_limit) {
		this.max_limit = max_limit;
	}

	/**
	 * ���� englishdescription��Getter����.��������Ӣ���f�� ��������:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getEnglishdescription() {
		return this.englishdescription;
	}

	/**
	 * ����englishdescription��Setter����.��������Ӣ���f�� ��������:2019/5/18
	 * 
	 * @param newEnglishdescription
	 *            java.lang.String
	 */
	public void setEnglishdescription(String englishdescription) {
		this.englishdescription = englishdescription;
	}

	/**
	 * ���� �����ό����I��Getter����.���������ό����I ��������:2019/5/18
	 * 
	 * @return String
	 */
	public String getPk_task_b() {
		return this.pk_task_b;
	}

	/**
	 * ���������ό����I��Setter����.���������ό����I ��������:2019/5/18
	 * 
	 * @param newPk_task_b
	 *            String
	 */
	public void setPk_task_b(String pk_task_b) {
		this.pk_task_b = pk_task_b;
	}

	/**
	 * ���� ���ɕr�g����Getter����.���������r�g�� ��������:2019/5/18
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getTs() {
		return this.ts;
	}

	/**
	 * �������ɕr�g����Setter����.���������r�g�� ��������:2019/5/18
	 * 
	 * @param newts
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	@Override
	public IVOMeta getMetaData() {
		return VOMetaFactory.getInstance().getVOMeta("qcco.task_s");
	}

	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return "pk_task_b";
	}

	public String getPk_list_table() {
		return pk_list_table;
	}

	public void setPk_list_table(String pk_list_table) {
		this.pk_list_table = pk_list_table;
	}
}
