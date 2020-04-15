package nc.vo.qcco.commission;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

/**
 * <b> ��̎��Ҫ��������� </b>
 * <p>
 * ��̎����۵�������Ϣ
 * </p>
 * ��������:2019/4/8
 *
 * @author yonyouBQ
 * @version NCPrj ??
 */

public class CommissionBVO extends SuperVO {

    /**
     * ��Ʒ�����I
     */
    public String pk_commission_b;
    /**
     * ��̖
     */
    public String rowno;
    
    /**
     * uniqueid
     */
    public String uniqueid;
    /**
     * �aƷϵ��
     */
    public String pk_productserial;
    /**
     * ��I�˜�
     */
    public String pk_enterprisestandard;
    /**
     * Ҏ����̖
     */
    public String typeno;
    /**
     * Ҏ��̖
     */
    public String pk_productspec;
    /**
     * �Y�����
     */
    public String pk_structuretype;
    /**
     * �|�c���
     */
    public String contacttype;
    /**
     * ��Ʒ����
     */
    public UFDouble quantity;
    /**
     * �u����
     */
    public String manufacturer;
    /**
     * �|�c��̖
     */
    public String pk_contactbrand;
    /**
     * �|�c��̖
     */
    public String contactmodel;
    /**
     * �ض�
     */
    public String productstage;
    /**
     * ��Ʒ�M�e
     */
    public String pk_samplegroup;
    /**
     * ���ǰ����
     */
    public String analysisref;
    /**
     * ������Ϣ
     */
    public String otherinfo;

    /**
     * �Զ�����1
     */
    public String def1;
    /**
     * �Զ�����2
     */
    public String def2;
    /**
     * �Զ�����3
     */
    public String def3;
    /**
     * �Զ�����4
     */
    public String def4;
    /**
     * �Զ�����5
     */
    public String def5;
    /**
     * �Զ�����6
     */
    public String def6;
    /**
     * �Զ�����7
     */
    public String def7;
    /**
     * �Զ�����8
     */
    public String def8;
    /**
     * �Զ�����9
     */
    public String def9;
    /**
     * �Զ�����10
     */
    public String def10;
    /**
     * �Զ�����11
     */
    public String def11;
    /**
     * �Զ�����12
     */
    public String def12;
    /**
     * �Զ�����13
     */
    public String def13;
    /**
     * �Զ�����14
     */
    public String def14;
    /**
     * �Զ�����15
     */
    public String def15;
    /**
     * �Զ�����16
     */
    public String def16;
    /**
     * �Զ�����17
     */
    public String def17;
    /**
     * �Զ�����18
     */
    public String def18;
    /**
     * �Զ�����19
     */
    public String def19;
    /**
     * �Զ�����20
     */
    public String def20;

    /**
     * �όӆΓ����I
     */
    public String pk_commission_h;
    /**
     * �r�g��
     */
    public UFDateTime ts;
    
    

    public String getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}

	public CommissionRVO[] pk_commission_r;

    /**
     * ���� pk_commission_b��Getter����.����������Ʒ�����I ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getPk_commission_b() {
        return this.pk_commission_b;
    }

    /**
     * ����pk_commission_b��Setter����.����������Ʒ�����I ��������:2019/4/8
     *
     * @param newPk_commission_b java.lang.String
     */
    public void setPk_commission_b(String pk_commission_b) {
        this.pk_commission_b = pk_commission_b;
    }

    /**
     * ���� rowno��Getter����.����������̖ ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getRowno() {
        return this.rowno;
    }

    /**
     * ����rowno��Setter����.����������̖ ��������:2019/4/8
     *
     * @param newRowno java.lang.String
     */
    public void setRowno(String rowno) {
        this.rowno = rowno;
    }

    /**
     * ���� pk_productserial��Getter����.���������aƷϵ�� ��������:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_productserial() {
        return this.pk_productserial;
    }

    /**
     * ����pk_productserial��Setter����.���������aƷϵ�� ��������:2019/4/8
     *
     * @param newPk_productserial nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_productserial(String pk_productserial) {
        this.pk_productserial = pk_productserial;
    }

    /**
     * ���� pk_enterprisestandard��Getter����.����������I�˜� ��������:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_enterprisestandard() {
        return this.pk_enterprisestandard;
    }

    /**
     * ����pk_enterprisestandard��Setter����.����������I�˜� ��������:2019/4/8
     *
     * @param newPk_enterprisestandard nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_enterprisestandard(String pk_enterprisestandard) {
        this.pk_enterprisestandard = pk_enterprisestandard;
    }

    /**
     * ���� typeno��Getter����.��������Ҏ����̖ ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getTypeno() {
        return this.typeno;
    }

    /**
     * ����typeno��Setter����.��������Ҏ����̖ ��������:2019/4/8
     *
     * @param newTypeno java.lang.String
     */
    public void setTypeno(String typeno) {
        this.typeno = typeno;
    }

    /**
     * ���� pk_productspec��Getter����.��������Ҏ��̖ ��������:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_productspec() {
        return this.pk_productspec;
    }

    /**
     * ����pk_productspec��Setter����.��������Ҏ��̖ ��������:2019/4/8
     *
     * @param newPk_productspec nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_productspec(String pk_productspec) {
        this.pk_productspec = pk_productspec;
    }

    /**
     * ���� pk_structuretype��Getter����.���������Y����� ��������:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_structuretype() {
        return this.pk_structuretype;
    }

    /**
     * ����pk_structuretype��Setter����.���������Y����� ��������:2019/4/8
     *
     * @param newPk_structuretype nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_structuretype(String pk_structuretype) {
        this.pk_structuretype = pk_structuretype;
    }

    /**
     * ���� contacttype��Getter����.���������|�c��� ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getContacttype() {
        return this.contacttype;
    }

    /**
     * ����contacttype��Setter����.���������|�c��� ��������:2019/4/8
     *
     * @param newContacttype java.lang.String
     */
    public void setContacttype(String contacttype) {
        this.contacttype = contacttype;
    }

    /**
     * ���� quantity��Getter����.����������Ʒ���� ��������:2019/4/8
     *
     * @return nc.vo.pub.lang.UFDouble
     */
    public UFDouble getQuantity() {
        return this.quantity;
    }

    /**
     * ����quantity��Setter����.����������Ʒ���� ��������:2019/4/8
     *
     * @param newQuantity nc.vo.pub.lang.UFDouble
     */
    public void setQuantity(UFDouble quantity) {
        this.quantity = quantity;
    }

    /**
     * ���� manufacturer��Getter����.���������u���� ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getManufacturer() {
        return this.manufacturer;
    }

    /**
     * ����manufacturer��Setter����.���������u���� ��������:2019/4/8
     *
     * @param newManufacturer java.lang.String
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * ���� pk_contactbrand��Getter����.���������|�c��̖ ��������:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_contactbrand() {
        return this.pk_contactbrand;
    }

    /**
     * ����pk_contactbrand��Setter����.���������|�c��̖ ��������:2019/4/8
     *
     * @param newPk_contactbrand nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_contactbrand(String pk_contactbrand) {
        this.pk_contactbrand = pk_contactbrand;
    }

    /**
     * ���� contactmodel��Getter����.���������|�c��̖ ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getContactmodel() {
        return this.contactmodel;
    }

    /**
     * ����contactmodel��Setter����.���������|�c��̖ ��������:2019/4/8
     *
     * @param newContactmodel java.lang.String
     */
    public void setContactmodel(String contactmodel) {
        this.contactmodel = contactmodel;
    }

    /**
     * ���� productstage��Getter����.���������ض� ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getProductstage() {
        return this.productstage;
    }

    /**
     * ����productstage��Setter����.���������ض� ��������:2019/4/8
     *
     * @param newProductstage java.lang.String
     */
    public void setProductstage(String productstage) {
        this.productstage = productstage;
    }

    /**
     * ���� pk_samplegroup��Getter����.����������Ʒ�M�e ��������:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_samplegroup() {
        return this.pk_samplegroup;
    }

    /**
     * ����pk_samplegroup��Setter����.����������Ʒ�M�e ��������:2019/4/8
     *
     * @param newPk_samplegroup nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_samplegroup(String pk_samplegroup) {
        this.pk_samplegroup = pk_samplegroup;
    }

    /**
     * ���� analysisref��Getter����.�����������ǰ���� ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getAnalysisref() {
        return this.analysisref;
    }

    /**
     * ����analysisref��Setter����.�����������ǰ���� ��������:2019/4/8
     *
     * @param newAnalysisref java.lang.String
     */
    public void setAnalysisref(String analysisref) {
        this.analysisref = analysisref;
    }

    /**
     * ���� otherinfo��Getter����.��������������Ϣ ��������:2019/4/8
     *
     * @return java.lang.String
     */
    public String getOtherinfo() {
        return this.otherinfo;
    }

    /**
     * ����otherinfo��Setter����.��������������Ϣ ��������:2019/4/8
     *
     * @param newOtherinfo java.lang.String
     */
    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
    }


    /**
     * ���� def1��Getter����.���������Զ�����1
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef1() {
        return this.def1;
    }

    /**
     * ����def1��Setter����.���������Զ�����1
     * ��������:2019-4-21
     *
     * @param newDef1 java.lang.String
     */
    public void setDef1(String def1) {
        this.def1 = def1;
    }

    /**
     * ���� def2��Getter����.���������Զ�����2
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef2() {
        return this.def2;
    }

    /**
     * ����def2��Setter����.���������Զ�����2
     * ��������:2019-4-21
     *
     * @param newDef2 java.lang.String
     */
    public void setDef2(String def2) {
        this.def2 = def2;
    }

    /**
     * ���� def3��Getter����.���������Զ�����3
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef3() {
        return this.def3;
    }

    /**
     * ����def3��Setter����.���������Զ�����3
     * ��������:2019-4-21
     *
     * @param newDef3 java.lang.String
     */
    public void setDef3(String def3) {
        this.def3 = def3;
    }

    /**
     * ���� def4��Getter����.���������Զ�����4
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef4() {
        return this.def4;
    }

    /**
     * ����def4��Setter����.���������Զ�����4
     * ��������:2019-4-21
     *
     * @param newDef4 java.lang.String
     */
    public void setDef4(String def4) {
        this.def4 = def4;
    }

    /**
     * ���� def5��Getter����.���������Զ�����5
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef5() {
        return this.def5;
    }

    /**
     * ����def5��Setter����.���������Զ�����5
     * ��������:2019-4-21
     *
     * @param newDef5 java.lang.String
     */
    public void setDef5(String def5) {
        this.def5 = def5;
    }

    /**
     * ���� def6��Getter����.���������Զ�����6
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef6() {
        return this.def6;
    }

    /**
     * ����def6��Setter����.���������Զ�����6
     * ��������:2019-4-21
     *
     * @param newDef6 java.lang.String
     */
    public void setDef6(String def6) {
        this.def6 = def6;
    }

    /**
     * ���� def7��Getter����.���������Զ�����7
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef7() {
        return this.def7;
    }

    /**
     * ����def7��Setter����.���������Զ�����7
     * ��������:2019-4-21
     *
     * @param newDef7 java.lang.String
     */
    public void setDef7(String def7) {
        this.def7 = def7;
    }

    /**
     * ���� def8��Getter����.���������Զ�����8
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef8() {
        return this.def8;
    }

    /**
     * ����def8��Setter����.���������Զ�����8
     * ��������:2019-4-21
     *
     * @param newDef8 java.lang.String
     */
    public void setDef8(String def8) {
        this.def8 = def8;
    }

    /**
     * ���� def9��Getter����.���������Զ�����9
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef9() {
        return this.def9;
    }

    /**
     * ����def9��Setter����.���������Զ�����9
     * ��������:2019-4-21
     *
     * @param newDef9 java.lang.String
     */
    public void setDef9(String def9) {
        this.def9 = def9;
    }

    /**
     * ���� def10��Getter����.���������Զ�����10
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef10() {
        return this.def10;
    }

    /**
     * ����def10��Setter����.���������Զ�����10
     * ��������:2019-4-21
     *
     * @param newDef10 java.lang.String
     */
    public void setDef10(String def10) {
        this.def10 = def10;
    }

    /**
     * ���� def11��Getter����.���������Զ�����11
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef11() {
        return this.def11;
    }

    /**
     * ����def11��Setter����.���������Զ�����11
     * ��������:2019-4-21
     *
     * @param newDef11 java.lang.String
     */
    public void setDef11(String def11) {
        this.def11 = def11;
    }

    /**
     * ���� def12��Getter����.���������Զ�����12
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef12() {
        return this.def12;
    }

    /**
     * ����def12��Setter����.���������Զ�����12
     * ��������:2019-4-21
     *
     * @param newDef12 java.lang.String
     */
    public void setDef12(String def12) {
        this.def12 = def12;
    }

    /**
     * ���� def13��Getter����.���������Զ�����13
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef13() {
        return this.def13;
    }

    /**
     * ����def13��Setter����.���������Զ�����13
     * ��������:2019-4-21
     *
     * @param newDef13 java.lang.String
     */
    public void setDef13(String def13) {
        this.def13 = def13;
    }

    /**
     * ���� def14��Getter����.���������Զ�����14
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef14() {
        return this.def14;
    }

    /**
     * ����def14��Setter����.���������Զ�����14
     * ��������:2019-4-21
     *
     * @param newDef14 java.lang.String
     */
    public void setDef14(String def14) {
        this.def14 = def14;
    }

    /**
     * ���� def15��Getter����.���������Զ�����15
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef15() {
        return this.def15;
    }

    /**
     * ����def15��Setter����.���������Զ�����15
     * ��������:2019-4-21
     *
     * @param newDef15 java.lang.String
     */
    public void setDef15(String def15) {
        this.def15 = def15;
    }

    /**
     * ���� def16��Getter����.���������Զ�����16
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef16() {
        return this.def16;
    }

    /**
     * ����def16��Setter����.���������Զ�����16
     * ��������:2019-4-21
     *
     * @param newDef16 java.lang.String
     */
    public void setDef16(String def16) {
        this.def16 = def16;
    }

    /**
     * ���� def17��Getter����.���������Զ�����17
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef17() {
        return this.def17;
    }

    /**
     * ����def17��Setter����.���������Զ�����17
     * ��������:2019-4-21
     *
     * @param newDef17 java.lang.String
     */
    public void setDef17(String def17) {
        this.def17 = def17;
    }

    /**
     * ���� def18��Getter����.���������Զ�����18
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef18() {
        return this.def18;
    }

    /**
     * ����def18��Setter����.���������Զ�����18
     * ��������:2019-4-21
     *
     * @param newDef18 java.lang.String
     */
    public void setDef18(String def18) {
        this.def18 = def18;
    }

    /**
     * ���� def19��Getter����.���������Զ�����19
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef19() {
        return this.def19;
    }

    /**
     * ����def19��Setter����.���������Զ�����19
     * ��������:2019-4-21
     *
     * @param newDef19 java.lang.String
     */
    public void setDef19(String def19) {
        this.def19 = def19;
    }

    /**
     * ���� def20��Getter����.���������Զ�����20
     * ��������:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef20() {
        return this.def20;
    }

    /**
     * ����def20��Setter����.���������Զ�����20
     * ��������:2019-4-21
     *
     * @param newDef20 java.lang.String
     */
    public void setDef20(String def20) {
        this.def20 = def20;
    }


    /**
     * ���� �����ό����I��Getter����.���������ό����I ��������:2019/4/8
     *
     * @return String
     */
    public String getPk_commission_h() {
        return this.pk_commission_h;
    }

    /**
     * ���������ό����I��Setter����.���������ό����I ��������:2019/4/8
     *
     * @param newPk_commission_h String
     */
    public void setPk_commission_h(String pk_commission_h) {
        this.pk_commission_h = pk_commission_h;
    }

    /**
     * ���� ���ɕr�g����Getter����.���������r�g�� ��������:2019/4/8
     *
     * @return nc.vo.pub.lang.UFDateTime
     */
    public UFDateTime getTs() {
        return this.ts;
    }

    /**
     * �������ɕr�g����Setter����.���������r�g�� ��������:2019/4/8
     *
     * @param newts nc.vo.pub.lang.UFDateTime
     */
    public void setTs(UFDateTime ts) {
        this.ts = ts;
    }

    @Override
    public IVOMeta getMetaData() {
        return VOMetaFactory.getInstance().getVOMeta("qcco.commission_b");
    }

    public CommissionRVO[] getPk_commission_r() {
        return pk_commission_r;
    }

    public void setPk_commission_r(CommissionRVO[] pk_commission_r) {
        this.pk_commission_r = pk_commission_r;
    }

    public String getParentPKFieldName() {
        return "pk_commission_h";
    }
}
