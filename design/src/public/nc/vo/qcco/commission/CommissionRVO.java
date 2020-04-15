package nc.vo.qcco.commission;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

/**
 * <b> �˴���Ҫ�������๦�� </b>
 * <p>
 * �˴�����۵�������Ϣ
 * </p>
 * ��������:2019/4/8
 *
 * @author yonyouBQ
 * @version NCPrj ??
 */

public class CommissionRVO extends SuperVO {

    /**
     * ����������
     */
    public String pk_commission_r;
    /**
     * �к�
     */
    public String rowno;
    /**
     * ʵ��ǰ��������
     */
    public String analysisname;
    /**
     * ��Ʒ���
     */
    public String pk_samplegroup;
    /**
     * ������
     */
    public String pk_component;
    /**
     * ֵ����
     */
    public String pk_valuetype;
    /**
     * ���ֵ
     */
    public String stdmaxvalue;
    /**
     * ��Сֵ
     */
    public String stdminvalue;
    /**
     * ��λ
     */
    public String unitname;
    /**
     * �Ƿ��ж�
     */
    public UFBoolean judgeflag;
    /**
     * �Ƿ����
     */
    public UFBoolean testflag;
    /**
     * �����¶�
     */
    public String productstage;
    /**
     * �ϲ㵥������
     */
    public String pk_commission_b;

    /**
     *ϵͳ����
     */
    public UFBoolean isautogeneration;
    /**
     * ʱ���
     */
    public UFDateTime ts;

    /**
     * ���� pk_commission_r��Getter����.������������������ ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getPk_commission_r() {
        return this.pk_commission_r;
    }

    /**
     * ����pk_commission_r��Setter����.������������������ ��������:2019/4/8
     *
     * @param newPk_commission_r
     *            java.lang.String
     */
    public void setPk_commission_r(String pk_commission_r) {
        this.pk_commission_r = pk_commission_r;
    }

    /**
     * ���� rowno��Getter����.���������к� ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getRowno() {
        return this.rowno;
    }

    /**
     * ����rowno��Setter����.���������к� ��������:2019/4/8
     *
     * @param newRowno
     *            java.lang.String
     */
    public void setRowno(String rowno) {
        this.rowno = rowno;
    }

    /**
     * ���� analysisname��Getter����.��������ʵ��ǰ�������� ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getAnalysisname() {
        return this.analysisname;
    }

    /**
     * ����analysisname��Setter����.��������ʵ��ǰ�������� ��������:2019/4/8
     *
     * @param newAnalysisname
     *            java.lang.String
     */
    public void setAnalysisname(String analysisname) {
        this.analysisname = analysisname;
    }

    /**
     * ���� pk_samplegroup��Getter����.����������Ʒ��� ��������:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_samplegroup() {
        return this.pk_samplegroup;
    }

    /**
     * ����pk_samplegroup��Setter����.����������Ʒ��� ��������:2019/4/8
     *
     * @param newPk_samplegroup
     *            nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_samplegroup(String pk_samplegroup) {
        this.pk_samplegroup = pk_samplegroup;
    }

    /**
     * ���� pk_component��Getter����.�������������� ��������:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_component() {
        return this.pk_component;
    }

    /**
     * ����pk_component��Setter����.�������������� ��������:2019/4/8
     *
     * @param newPk_component
     *            nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_component(String pk_component) {
        this.pk_component = pk_component;
    }

    /**
     * ���� pk_valuetype��Getter����.��������ֵ���� ��������:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_valuetype() {
        return this.pk_valuetype;
    }

    /**
     * ����pk_valuetype��Setter����.��������ֵ���� ��������:2019/4/8
     *
     * @param newPk_valuetype
     *            nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_valuetype(String pk_valuetype) {
        this.pk_valuetype = pk_valuetype;
    }

    /**
     * ���� stdmaxvalue��Getter����.�����������ֵ ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getStdmaxvalue() {
        return this.stdmaxvalue;
    }

    /**
     * ����stdmaxvalue��Setter����.�����������ֵ ��������:2019/4/8
     *
     * @param newStdmaxvalue
     *            java.lang.String
     */
    public void setStdmaxvalue(String stdmaxvalue) {
        this.stdmaxvalue = stdmaxvalue;
    }

    /**
     * ���� stdminvalue��Getter����.����������Сֵ ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getStdminvalue() {
        return this.stdminvalue;
    }

    /**
     * ����stdminvalue��Setter����.����������Сֵ ��������:2019/4/8
     *
     * @param newStdminvalue
     *            java.lang.String
     */
    public void setStdminvalue(String stdminvalue) {
        this.stdminvalue = stdminvalue;
    }

    /**
     * ���� unitname��Getter����.����������λ ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getUnitname() {
        return this.unitname;
    }

    /**
     * ����unitname��Setter����.����������λ ��������:2019/4/8
     *
     * @param newUnitname
     *            java.lang.String
     */
    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    /**
     * ���� judgeflag��Getter����.���������Ƿ��ж� ��������:2019/4/8
     *
     * @return nc.vo.pub.lang.UFBoolean
     */
    public UFBoolean getJudgeflag() {
        return this.judgeflag;
    }

    /**
     * ����judgeflag��Setter����.���������Ƿ��ж� ��������:2019/4/8
     *
     * @param newJudgeflag
     *            nc.vo.pub.lang.UFBoolean
     */
    public void setJudgeflag(UFBoolean judgeflag) {
        this.judgeflag = judgeflag;
    }

    /**
     * ���� testflag��Getter����.���������Ƿ���� ��������:2019/4/8
     *
     * @return nc.vo.pub.lang.UFBoolean
     */
    public UFBoolean getTestflag() {
        return this.testflag;
    }

    /**
     * ����testflag��Setter����.���������Ƿ���� ��������:2019/4/8
     *
     * @param newTestflag
     *            nc.vo.pub.lang.UFBoolean
     */
    public void setTestflag(UFBoolean testflag) {
        this.testflag = testflag;
    }

    /**
     * ���� productstage��Getter����.�������������¶� ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getProductstage() {
        return this.productstage;
    }

    /**
     * ����productstage��Setter����.�������������¶� ��������:2019/4/8
     *
     * @param newProductstage
     *            java.lang.String
     */
    public void setProductstage(String productstage) {
        this.productstage = productstage;
    }


    /**
     * ���� isautogeneration��Getter����.��������ϵͳ����
     *  ��������:2019-4-21
     * @return nc.vo.pub.lang.UFBoolean
     */
    public UFBoolean getIsautogeneration() {
        return this.isautogeneration;
    }

    /**
     * ����isautogeneration��Setter����.��������ϵͳ����
     * ��������:2019-4-21
     * @param newIsautogeneration nc.vo.pub.lang.UFBoolean
     */
    public void setIsautogeneration (UFBoolean isautogeneration) {
        this.isautogeneration=isautogeneration;
    }

    /**
     * ���� �����ϲ�������Getter����.���������ϲ����� ��������:2019/4/8
     *
     * @return String
     */
    public String getPk_commission_b() {
        return this.pk_commission_b;
    }

    /**
     * ���������ϲ�������Setter����.���������ϲ����� ��������:2019/4/8
     *
     * @param newPk_commission_b
     *            String
     */
    public void setPk_commission_b(String pk_commission_b) {
        this.pk_commission_b = pk_commission_b;
    }

    /**
     * ���� ����ʱ�����Getter����.��������ʱ��� ��������:2019/4/8
     *
     * @return nc.vo.pub.lang.UFDateTime
     */
    public UFDateTime getTs() {
        return this.ts;
    }

    /**
     * ��������ʱ�����Setter����.��������ʱ��� ��������:2019/4/8
     *
     * @param newts
     *            nc.vo.pub.lang.UFDateTime
     */
    public void setTs(UFDateTime ts) {
        this.ts = ts;
    }

    @Override
    public IVOMeta getMetaData() {
        return VOMetaFactory.getInstance().getVOMeta("qcco.commission_r");
    }

    public String getParentPKFieldName() {
        return "pk_commission_b";
    }
}
