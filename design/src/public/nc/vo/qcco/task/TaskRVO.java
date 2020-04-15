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
 * ��������:2019/5/5
 * 
 * @author yonyouBQ
 * @version NCPrj ??
 */

public class TaskRVO extends SuperVO {

	/**
	 * ���������I
	 */
	public String pk_task_r;
	/**
	 * ��Ʒ�M�e
	 */
	public String pk_samplegroup;
	/**
	 * ��Сֵ
	 */
	public UFDouble stdminvalue;
	/**
	 * ���ֵ
	 */
	public UFDouble stdmaxvalue;
	/**
	 * ��λ
	 */
	public String pk_unit;
	/**
	 * �yԇ��ӛ
	 */
	public UFBoolean testflag;
	/**
	 * �ж���ӛ
	 */
	public UFBoolean judgeflag;
	/**
	 * �yԇ�ض�
	 */
	public String pk_testtemperature;
	/**
	 * ��򞅢�����Q
	 */
	public String analysisname;
	/**
	 * �����
	 */
	public String pk_component;
	/**
	 * ֵ���
	 */
	public String pk_valuetype;
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
	/**
	 * ���� pk_task_r��Getter����.�����������������I ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getPk_task_r() {
		return this.pk_task_r;
	}

	/**
	 * ����pk_task_r��Setter����.�����������������I ��������:2019/5/5
	 * 
	 * @param newPk_task_r
	 *            java.lang.String
	 */
	public void setPk_task_r(String pk_task_r) {
		this.pk_task_r = pk_task_r;
	}

	/**
	 * ���� pk_samplegroup��Getter����.����������Ʒ�M�e ��������:2019/5/5
	 * 
	 * @return nc.vo.bd.defdoc.DefdocVO
	 */
	public String getPk_samplegroup() {
		return this.pk_samplegroup;
	}

	/**
	 * ����pk_samplegroup��Setter����.����������Ʒ�M�e ��������:2019/5/5
	 * 
	 * @param newPk_samplegroup
	 *            nc.vo.bd.defdoc.DefdocVO
	 */
	public void setPk_samplegroup(String pk_samplegroup) {
		this.pk_samplegroup = pk_samplegroup;
	}

	/**
	 * ���� stdminvalue��Getter����.����������Сֵ ��������:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDouble
	 */
	public UFDouble getStdminvalue() {
		return this.stdminvalue;
	}

	/**
	 * ����stdminvalue��Setter����.����������Сֵ ��������:2019/5/5
	 * 
	 * @param newStdminvalue
	 *            nc.vo.pub.lang.UFDouble
	 */
	public void setStdminvalue(UFDouble stdminvalue) {
		this.stdminvalue = stdminvalue;
	}

	/**
	 * ���� stdmaxvalue��Getter����.�����������ֵ ��������:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDouble
	 */
	public UFDouble getStdmaxvalue() {
		return this.stdmaxvalue;
	}

	/**
	 * ����stdmaxvalue��Setter����.�����������ֵ ��������:2019/5/5
	 * 
	 * @param newStdmaxvalue
	 *            nc.vo.pub.lang.UFDouble
	 */
	public void setStdmaxvalue(UFDouble stdmaxvalue) {
		this.stdmaxvalue = stdmaxvalue;
	}

	/**
	 * ���� pk_unit��Getter����.����������λ ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getPk_unit() {
		return this.pk_unit;
	}

	/**
	 * ����pk_unit��Setter����.����������λ ��������:2019/5/5
	 * 
	 * @param newPk_unit
	 *            java.lang.String
	 */
	public void setPk_unit(String pk_unit) {
		this.pk_unit = pk_unit;
	}

	/**
	 * ���� testflag��Getter����.���������yԇ��ӛ ��������:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public UFBoolean getTestflag() {
		return this.testflag;
	}

	/**
	 * ����testflag��Setter����.���������yԇ��ӛ ��������:2019/5/5
	 * 
	 * @param newTestflag
	 *            nc.vo.pub.lang.UFBoolean
	 */
	public void setTestflag(UFBoolean testflag) {
		this.testflag = testflag;
	}

	/**
	 * ���� judgeflag��Getter����.���������ж���ӛ ��������:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public UFBoolean getJudgeflag() {
		return this.judgeflag;
	}

	/**
	 * ����judgeflag��Setter����.���������ж���ӛ ��������:2019/5/5
	 * 
	 * @param newJudgeflag
	 *            nc.vo.pub.lang.UFBoolean
	 */
	public void setJudgeflag(UFBoolean judgeflag) {
		this.judgeflag = judgeflag;
	}

	/**
	 * ���� pk_testtemperature��Getter����.���������yԇ�ض� ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getPk_testtemperature() {
		return this.pk_testtemperature;
	}

	/**
	 * ����pk_testtemperature��Setter����.���������yԇ�ض� ��������:2019/5/5
	 * 
	 * @param newPk_testtemperature
	 *            java.lang.String
	 */
	public void setPk_testtemperature(String pk_testtemperature) {
		this.pk_testtemperature = pk_testtemperature;
	}

	/**
	 * ���� analysisname��Getter����.����������򞅢�����Q ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getAnalysisname() {
		return this.analysisname;
	}

	/**
	 * ����analysisname��Setter����.����������򞅢�����Q ��������:2019/5/5
	 * 
	 * @param newAnalysisname
	 *            java.lang.String
	 */
	public void setAnalysisname(String analysisname) {
		this.analysisname = analysisname;
	}

	/**
	 * ���� pk_component��Getter����.������������� ��������:2019/5/5
	 * 
	 * @return nc.vo.bd.defdoc.DefdocVO
	 */
	public String getPk_component() {
		return this.pk_component;
	}

	/**
	 * ����pk_component��Setter����.������������� ��������:2019/5/5
	 * 
	 * @param newPk_component
	 *            nc.vo.bd.defdoc.DefdocVO
	 */
	public void setPk_component(String pk_component) {
		this.pk_component = pk_component;
	}

	/**
	 * ���� pk_valuetype��Getter����.��������ֵ��� ��������:2019/5/5
	 * 
	 * @return nc.vo.bd.defdoc.DefdocVO
	 */
	public String getPk_valuetype() {
		return this.pk_valuetype;
	}

	/**
	 * ����pk_valuetype��Setter����.��������ֵ��� ��������:2019/5/5
	 * 
	 * @param newPk_valuetype
	 *            nc.vo.bd.defdoc.DefdocVO
	 */
	public void setPk_valuetype(String pk_valuetype) {
		this.pk_valuetype = pk_valuetype;
	}

	/**
	 * ���� �����ό����I��Getter����.���������ό����I ��������:2019/5/5
	 * 
	 * @return String
	 */
	public String getPk_task_b() {
		return this.pk_task_b;
	}

	/**
	 * ���������ό����I��Setter����.���������ό����I ��������:2019/5/5
	 * 
	 * @param newPk_task_b
	 *            String
	 */
	public void setPk_task_b(String pk_task_b) {
		this.pk_task_b = pk_task_b;
	}

	/**
	 * ���� ���ɕr�g����Getter����.���������r�g�� ��������:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getTs() {
		return this.ts;
	}

	/**
	 * �������ɕr�g����Setter����.���������r�g�� ��������:2019/5/5
	 * 
	 * @param newts
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	@Override
	public IVOMeta getMetaData() {
		return VOMetaFactory.getInstance().getVOMeta("qcco.task_r");
	}
    @Override
    public String getParentPKFieldName() {
        // TODO Auto-generated method stub
        return "pk_task_b";
    }
}
