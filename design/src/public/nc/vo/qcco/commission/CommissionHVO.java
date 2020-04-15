package nc.vo.qcco.commission;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFLiteralDate;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

/**
 * <b> ��̎��Ҫ��������� </b>
 * <p>
 * ��̎����۵�������Ϣ
 * </p>
 * ��������:2019/2/25
 *
 * @author yonyouBQ
 * @version NCPrj ??
 */

public class CommissionHVO extends SuperVO {

    /**
     * �������I
     */
    public String pk_commission_h;
    /**
     * ������
     */
    public String creator;
    /**
     * �����r�g
     */
    public UFDateTime creationtime;
    /**
     * �޸���
     */
    public String modifier;
    /**
     * �޸ĕr�g
     */
    public UFDateTime modifiedtime;
    /**
     * �����޸ĕr�g
     */
    public UFDateTime lastmaketime;
    /**
     * �Ɔ�����
     */
    public UFDate dmakedate;
    /**
     * ���F
     */
    public String pk_group;
    /**
     * �M��
     */
    public String pk_org;
    /**
     * �M���汾
     */
    public String pk_org_v;
    /**
     * ίӚ�����
     */
    public String pk_commissiontype;
    /**
     * ίӚ�ξ��aǰ�Y
     */
    public String codeprefix;
    /**
     * ίӚ�ξ��a
     */
    public String billno;
    /**
     * ίӚ�����Q
     */
    public String billname;
    /**
     * ίӚ��λ
     */
    public String pk_owner;
    /**
     * ���T
     */
    public String pk_dept;
    /**
     * ���M��λ
     */
    public String pk_payer;
    /**
     * �M��
     */
    public String contract;
    /**
     * �����]��
     */
    public String email;
    /**
     * �M�Ԓ
     */
    public String teleno;
    /**
     * �aƷ���
     */
    public String pk_maincategory;
    /**
     * �������
     */
    public String pk_subcategory;
    /**
     * �������
     */
    public String pk_lastcategory;
    /**
     * �Ɔ���
     */
    public String cuserid;
    /**
     * ����ʽ
     */
    public String reportformat;
    /**
     * ����Z��
     */
    public String reportlang;
    /**
     * ���܌��˰l���]��
     */
    public UFBoolean managersendflag;
    /**
     * �΄��_ʼ�l���]��
     */
    public UFBoolean taskbeginsendflag;
    /**
     * �΄սY������]��
     */
    public UFBoolean taskendsendflag;
    /**
     * ��溞�l�l���]��
     */
    public UFBoolean reportsendflag;
    /**
     * �Ƿ񱣴��ģ��
     */
    public UFBoolean savetotemplateflag;
    /**
     * Ӌ�M���]������
     */
    public UFBoolean receiptsendflag;
    /**
     * ��r���]������
     */
    public UFBoolean quotaionsendflag;
    /**
     * �yԇĿ��
     */
    public String testaim;
    /**
     * �M��Ҫ��
     */
    public String progressneed;
    /**
     * �z���Ʒ̎��
     */
    public String sampledealtype;
    /**
     * �aƷ����
     */
    public String productproperty;
    /**
     * �͑����Q
     */
    public String customername;
    /**
     * �͑����
     */
    public String customertype;
    /**
     * �yԇ����
     */
    public String testrequirement;
    /**
     * �z�y���|
     */
    public String checkingproperty;
    /**
     * ���a�a��
     */
    public String productline;
    /**
     * ���a����
     */
    public String batchnumber;
    /**
     * ���a����
     */
    public UFLiteralDate productdate;
    /**
     * ���a��̖
     */
    public String batchserial;
    /**
     * �aƷ�b�����
     */
    public String identificationtype;
    /**
     * �J�C���
     */
    public String certificationtype;
    /**
     * �Ŀ̖
     */
    public String itemnumber;
    
    /**
     * ��B
     */
    private String docstatus;

    /**
     *�Զ�����1
     */
    public String def1;
    /**
     *�Զ�����2
     */
    public String def2;
    /**
     *�Զ�����3
     */
    public String def3;
    /**
     *�Զ�����4
     */
    public String def4;
    /**
     *�Զ�����5
     */
    public String def5;
    /**
     *�Զ�����6
     */
    public String def6;
    /**
     *�Զ�����7
     */
    public String def7;
    /**
     *�Զ�����8
     */
    public String def8;
    /**
     *�Զ�����9
     */
    public String def9;
    /**
     *�Զ�����10
     */
    public String def10;
    /**
     *�Զ�����11
     */
    public String def11;
    /**
     *�Զ�����12
     */
    public String def12;
    /**
     *�Զ�����13
     */
    public String def13;
    /**
     *�Զ�����14
     */
    public String def14;
    /**
     *�Զ�����15
     */
    public String def15;
    /**
     *�Զ�����16
     */
    public String def16;
    /**
     *�Զ�����17
     */
    public String def17;
    /**
     *�Զ�����18
     */
    public String def18;
    /**
     *�Զ�����19
     */
    public String def19;
    /**
     *�Զ�����20
     */
    public String def20;
    /**
     *�Զ�����21
     */
    public String def21;
    /**
     *�Զ�����22
     */
    public String def22;
    /**
     *�Զ�����23
     */
    public String def23;
    /**
     *�Զ�����24
     */
    public String def24;
    /**
     *�Զ�����25
     */
    public String def25;
    /**
     *�Զ�����26
     */
    public String def26;
    /**
     *�Զ�����27
     */
    public String def27;
    /**
     *�Զ�����28
     */
    public String def28;
    /**
     *�Զ�����29
     */
    public String def29;
    /**
     *�Զ�����30
     */
    public String def30;
    /**
     *�Զ�����31
     */
    public String def31;
    /**
     *�Զ�����32
     */
    public String def32;
    /**
     *�Զ�����33
     */
    public String def33;
    /**
     *�Զ�����34
     */
    public String def34;
    /**
     *�Զ�����35
     */
    public String def35;
    /**
     *�Զ�����36
     */
    public String def36;
    /**
     *�Զ�����37
     */
    public String def37;
    /**
     *�Զ�����38
     */
    public String def38;
    /**
     *�Զ�����39
     */
    public String def39;
    /**
     *�Զ�����40
     */
    public String def40;
    /**
     * �r�g��
     */
    public UFDateTime ts;
    
    

    public String getDocstatus() {
		return docstatus;
	}

	public void setDocstatus(String docstatus) {
		this.docstatus = docstatus;
	}

	/**
     * ���� pk_commission_h��Getter����.���������������I ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getPk_commission_h() {
        return this.pk_commission_h;
    }

    /**
     * ����pk_commission_h��Setter����.���������������I ��������:2019/2/25
     *
     * @param newPk_commission_h
     *            java.lang.String
     */
    public void setPk_commission_h(String pk_commission_h) {
        this.pk_commission_h = pk_commission_h;
    }

    /**
     * ���� creator��Getter����.�������������� ��������:2019/2/25
     *
     * @return nc.vo.sm.UserVO
     */
    public String getCreator() {
        return this.creator;
    }

    /**
     * ����creator��Setter����.�������������� ��������:2019/2/25
     *
     * @param newCreator
     *            nc.vo.sm.UserVO
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * ���� creationtime��Getter����.�������������r�g ��������:2019/2/25
     *
     * @return nc.vo.pub.lang.UFDateTime
     */
    public UFDateTime getCreationtime() {
        return this.creationtime;
    }

    /**
     * ����creationtime��Setter����.�������������r�g ��������:2019/2/25
     *
     * @param newCreationtime
     *            nc.vo.pub.lang.UFDateTime
     */
    public void setCreationtime(UFDateTime creationtime) {
        this.creationtime = creationtime;
    }

    /**
     * ���� modifier��Getter����.���������޸��� ��������:2019/2/25
     *
     * @return nc.vo.sm.UserVO
     */
    public String getModifier() {
        return this.modifier;
    }

    /**
     * ����modifier��Setter����.���������޸��� ��������:2019/2/25
     *
     * @param newModifier
     *            nc.vo.sm.UserVO
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * ���� modifiedtime��Getter����.���������޸ĕr�g ��������:2019/2/25
     *
     * @return nc.vo.pub.lang.UFDateTime
     */
    public UFDateTime getModifiedtime() {
        return this.modifiedtime;
    }

    /**
     * ����modifiedtime��Setter����.���������޸ĕr�g ��������:2019/2/25
     *
     * @param newModifiedtime
     *            nc.vo.pub.lang.UFDateTime
     */
    public void setModifiedtime(UFDateTime modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    /**
     * ���� lastmaketime��Getter����.�������������޸ĕr�g ��������:2019/2/25
     *
     * @return nc.vo.pub.lang.UFDateTime
     */
    public UFDateTime getLastmaketime() {
        return this.lastmaketime;
    }

    /**
     * ����lastmaketime��Setter����.�������������޸ĕr�g ��������:2019/2/25
     *
     * @param newLastmaketime
     *            nc.vo.pub.lang.UFDateTime
     */
    public void setLastmaketime(UFDateTime lastmaketime) {
        this.lastmaketime = lastmaketime;
    }

    /**
     * ���� dmakedate��Getter����.���������Ɔ����� ��������:2019/2/25
     *
     * @return nc.vo.pub.lang.UFDate
     */
    public UFDate getDmakedate() {
        return this.dmakedate;
    }

    /**
     * ����dmakedate��Setter����.���������Ɔ����� ��������:2019/2/25
     *
     * @param newDmakedate
     *            nc.vo.pub.lang.UFDate
     */
    public void setDmakedate(UFDate dmakedate) {
        this.dmakedate = dmakedate;
    }

    /**
     * ���� pk_group��Getter����.�����������F ��������:2019/2/25
     *
     * @return nc.vo.org.GroupVO
     */
    public String getPk_group() {
        return this.pk_group;
    }

    /**
     * ����pk_group��Setter����.�����������F ��������:2019/2/25
     *
     * @param newPk_group
     *            nc.vo.org.GroupVO
     */
    public void setPk_group(String pk_group) {
        this.pk_group = pk_group;
    }

    /**
     * ���� pk_org��Getter����.���������M�� ��������:2019/2/25
     *
     * @return nc.vo.org.OrgVO
     */
    public String getPk_org() {
        return this.pk_org;
    }

    /**
     * ����pk_org��Setter����.���������M�� ��������:2019/2/25
     *
     * @param newPk_org
     *            nc.vo.org.OrgVO
     */
    public void setPk_org(String pk_org) {
        this.pk_org = pk_org;
    }

    /**
     * ���� pk_org_v��Getter����.���������M���汾 ��������:2019/2/25
     *
     * @return nc.vo.vorg.OrgVersionVO
     */
    public String getPk_org_v() {
        return this.pk_org_v;
    }

    /**
     * ����pk_org_v��Setter����.���������M���汾 ��������:2019/2/25
     *
     * @param newPk_org_v
     *            nc.vo.vorg.OrgVersionVO
     */
    public void setPk_org_v(String pk_org_v) {
        this.pk_org_v = pk_org_v;
    }

    /**
     * ���� pk_commissiontype��Getter����.��������ίӚ����� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getPk_commissiontype() {
        return this.pk_commissiontype;
    }

    /**
     * ����pk_commissiontype��Setter����.��������ίӚ����� ��������:2019/2/25
     *
     * @param newPk_commissiontype
     *            java.lang.String
     */
    public void setPk_commissiontype(String pk_commissiontype) {
        this.pk_commissiontype = pk_commissiontype;
    }

    /**
     * ���� codeprefix��Getter����.��������ίӚ�ξ��aǰ�Y ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getCodeprefix() {
        return this.codeprefix;
    }

    /**
     * ����codeprefix��Setter����.��������ίӚ�ξ��aǰ�Y ��������:2019/2/25
     *
     * @param newCodeprefix
     *            java.lang.String
     */
    public void setCodeprefix(String codeprefix) {
        this.codeprefix = codeprefix;
    }

    /**
     * ���� billno��Getter����.��������ίӚ�ξ��a ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getBillno() {
        return this.billno;
    }

    /**
     * ����billno��Setter����.��������ίӚ�ξ��a ��������:2019/2/25
     *
     * @param newBillno
     *            java.lang.String
     */
    public void setBillno(String billno) {
        this.billno = billno;
    }

    /**
     * ���� billname��Getter����.��������ίӚ�����Q ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getBillname() {
        return this.billname;
    }

    /**
     * ����billname��Setter����.��������ίӚ�����Q ��������:2019/2/25
     *
     * @param newBillname
     *            java.lang.String
     */
    public void setBillname(String billname) {
        this.billname = billname;
    }

    /**
     * ���� pk_owner��Getter����.��������ίӚ��λ ��������:2019/2/25
     *
     * @return nc.vo.org.OrgVO
     */
    public String getPk_owner() {
        return this.pk_owner;
    }

    /**
     * ����pk_owner��Setter����.��������ίӚ��λ ��������:2019/2/25
     *
     * @param newPk_owner
     *            nc.vo.org.OrgVO
     */
    public void setPk_owner(String pk_owner) {
        this.pk_owner = pk_owner;
    }

    /**
     * ���� pk_dept��Getter����.�����������T ��������:2019/2/25
     *
     * @return nc.vo.org.DeptVO
     */
    public String getPk_dept() {
        return this.pk_dept;
    }

    /**
     * ����pk_dept��Setter����.�����������T ��������:2019/2/25
     *
     * @param newPk_dept
     *            nc.vo.org.DeptVO
     */
    public void setPk_dept(String pk_dept) {
        this.pk_dept = pk_dept;
    }

    /**
     * ���� pk_payer��Getter����.�����������M��λ ��������:2019/2/25
     *
     * @return nc.vo.org.OrgVO
     */
    public String getPk_payer() {
        return this.pk_payer;
    }

    /**
     * ����pk_payer��Setter����.�����������M��λ ��������:2019/2/25
     *
     * @param newPk_payer
     *            nc.vo.org.OrgVO
     */
    public void setPk_payer(String pk_payer) {
        this.pk_payer = pk_payer;
    }

    /**
     * ���� contract��Getter����.���������M�� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getContract() {
        return this.contract;
    }

    /**
     * ����contract��Setter����.���������M�� ��������:2019/2/25
     *
     * @param newContract
     *            java.lang.String
     */
    public void setContract(String contract) {
        this.contract = contract;
    }

    /**
     * ���� email��Getter����.�������������]�� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * ����email��Setter����.�������������]�� ��������:2019/2/25
     *
     * @param newEmail
     *            java.lang.String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * ���� teleno��Getter����.���������M�Ԓ ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getTeleno() {
        return this.teleno;
    }

    /**
     * ����teleno��Setter����.���������M�Ԓ ��������:2019/2/25
     *
     * @param newTeleno
     *            java.lang.String
     */
    public void setTeleno(String teleno) {
        this.teleno = teleno;
    }

    /**
     * ���� pk_maincategory��Getter����.���������aƷ��� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getPk_maincategory() {
        return this.pk_maincategory;
    }

    /**
     * ����pk_maincategory��Setter����.���������aƷ��� ��������:2019/2/25
     *
     * @param newPk_maincategory
     *            java.lang.String
     */
    public void setPk_maincategory(String pk_maincategory) {
        this.pk_maincategory = pk_maincategory;
    }

    /**
     * ���� pk_subcategory��Getter����.��������������� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getPk_subcategory() {
        return this.pk_subcategory;
    }

    /**
     * ����pk_subcategory��Setter����.��������������� ��������:2019/2/25
     *
     * @param newPk_subcategory
     *            java.lang.String
     */
    public void setPk_subcategory(String pk_subcategory) {
        this.pk_subcategory = pk_subcategory;
    }

    /**
     * ���� pk_lastcategory��Getter����.��������������� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getPk_lastcategory() {
        return this.pk_lastcategory;
    }

    /**
     * ����pk_lastcategory��Setter����.��������������� ��������:2019/2/25
     *
     * @param newPk_lastcategory
     *            java.lang.String
     */
    public void setPk_lastcategory(String pk_lastcategory) {
        this.pk_lastcategory = pk_lastcategory;
    }

    /**
     * ���� cuserid��Getter����.���������Ɔ��� ��������:2019/2/25
     *
     * @return nc.vo.bd.psn.PsndocVO
     */
    public String getCuserid() {
        return this.cuserid;
    }

    /**
     * ����cuserid��Setter����.���������Ɔ��� ��������:2019/2/25
     *
     * @param newCuserid
     *            nc.vo.bd.psn.PsndocVO
     */
    public void setCuserid(String cuserid) {
        this.cuserid = cuserid;
    }

    /**
     * ���� reportformat��Getter����.������������ʽ ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getReportformat() {
        return this.reportformat;
    }

    /**
     * ����reportformat��Setter����.������������ʽ ��������:2019/2/25
     *
     * @param newReportformat
     *            java.lang.String
     */
    public void setReportformat(String reportformat) {
        this.reportformat = reportformat;
    }

    /**
     * ���� reportlang��Getter����.������������Z�� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getReportlang() {
        return this.reportlang;
    }

    /**
     * ����reportlang��Setter����.������������Z�� ��������:2019/2/25
     *
     * @param newReportlang
     *            java.lang.String
     */
    public void setReportlang(String reportlang) {
        this.reportlang = reportlang;
    }

    /**
     * ���� managersendflag��Getter����.�����������܌��˰l���]�� ��������:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getManagersendflag() {
        return this.managersendflag;
    }

    /**
     * ����managersendflag��Setter����.�����������܌��˰l���]�� ��������:2019/2/25
     *
     * @param newManagersendflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setManagersendflag(UFBoolean managersendflag) {
        this.managersendflag = managersendflag;
    }

    /**
     * ���� taskbeginsendflag��Getter����.���������΄��_ʼ�l���]�� ��������:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getTaskbeginsendflag() {
        return this.taskbeginsendflag;
    }

    /**
     * ����taskbeginsendflag��Setter����.���������΄��_ʼ�l���]�� ��������:2019/2/25
     *
     * @param newTaskbeginsendflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setTaskbeginsendflag(UFBoolean taskbeginsendflag) {
        this.taskbeginsendflag = taskbeginsendflag;
    }

    /**
     * ���� taskendsendflag��Getter����.���������΄սY������]�� ��������:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getTaskendsendflag() {
        return this.taskendsendflag;
    }

    /**
     * ����taskendsendflag��Setter����.���������΄սY������]�� ��������:2019/2/25
     *
     * @param newTaskendsendflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setTaskendsendflag(UFBoolean taskendsendflag) {
        this.taskendsendflag = taskendsendflag;
    }

    /**
     * ���� reportsendflag��Getter����.����������溞�l�l���]�� ��������:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getReportsendflag() {
        return this.reportsendflag;
    }

    /**
     * ����reportsendflag��Setter����.����������溞�l�l���]�� ��������:2019/2/25
     *
     * @param newReportsendflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setReportsendflag(UFBoolean reportsendflag) {
        this.reportsendflag = reportsendflag;
    }

    /**
     * ���� savetotemplateflag��Getter����.���������Ƿ񱣴��ģ�� ��������:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getSavetotemplateflag() {
        return this.savetotemplateflag;
    }

    /**
     * ����savetotemplateflag��Setter����.���������Ƿ񱣴��ģ�� ��������:2019/2/25
     *
     * @param newSavetotemplateflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setSavetotemplateflag(UFBoolean savetotemplateflag) {
        this.savetotemplateflag = savetotemplateflag;
    }

    /**
     * ���� receiptsendflag��Getter����.��������Ӌ�M���]������ ��������:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getReceiptsendflag() {
        return this.receiptsendflag;
    }

    /**
     * ����receiptsendflag��Setter����.��������Ӌ�M���]������ ��������:2019/2/25
     *
     * @param newReceiptsendflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setReceiptsendflag(UFBoolean receiptsendflag) {
        this.receiptsendflag = receiptsendflag;
    }

    /**
     * ���� quotaionsendflag��Getter����.����������r���]������ ��������:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getQuotaionsendflag() {
        return this.quotaionsendflag;
    }

    /**
     * ����quotaionsendflag��Setter����.����������r���]������ ��������:2019/2/25
     *
     * @param newQuotaionsendflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setQuotaionsendflag(UFBoolean quotaionsendflag) {
        this.quotaionsendflag = quotaionsendflag;
    }

    /**
     * ���� testaim��Getter����.���������yԇĿ�� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getTestaim() {
        return this.testaim;
    }

    /**
     * ����testaim��Setter����.���������yԇĿ�� ��������:2019/2/25
     *
     * @param newTestaim
     *            java.lang.String
     */
    public void setTestaim(String testaim) {
        this.testaim = testaim;
    }

    /**
     * ���� progressneed��Getter����.���������M��Ҫ�� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getProgressneed() {
        return this.progressneed;
    }

    /**
     * ����progressneed��Setter����.���������M��Ҫ�� ��������:2019/2/25
     *
     * @param newProgressneed
     *            java.lang.String
     */
    public void setProgressneed(String progressneed) {
        this.progressneed = progressneed;
    }

    /**
     * ���� sampledealtype��Getter����.���������z���Ʒ̎�� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getSampledealtype() {
        return this.sampledealtype;
    }

    /**
     * ����sampledealtype��Setter����.���������z���Ʒ̎�� ��������:2019/2/25
     *
     * @param newSampledealtype
     *            java.lang.String
     */
    public void setSampledealtype(String sampledealtype) {
        this.sampledealtype = sampledealtype;
    }

    /**
     * ���� productproperty��Getter����.���������aƷ���� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getProductproperty() {
        return this.productproperty;
    }

    /**
     * ����productproperty��Setter����.���������aƷ���� ��������:2019/2/25
     *
     * @param newProductproperty
     *            java.lang.String
     */
    public void setProductproperty(String productproperty) {
        this.productproperty = productproperty;
    }

    /**
     * ���� customername��Getter����.���������͑����Q ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getCustomername() {
        return this.customername;
    }

    /**
     * ����customername��Setter����.���������͑����Q ��������:2019/2/25
     *
     * @param newCustomername
     *            java.lang.String
     */
    public void setCustomername(String customername) {
        this.customername = customername;
    }

    /**
     * ���� customertype��Getter����.���������͑���� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getCustomertype() {
        return this.customertype;
    }

    /**
     * ����customertype��Setter����.���������͑���� ��������:2019/2/25
     *
     * @param newCustomertype
     *            java.lang.String
     */
    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    /**
     * ���� testrequirement��Getter����.���������yԇ���� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getTestrequirement() {
        return this.testrequirement;
    }

    /**
     * ����testrequirement��Setter����.���������yԇ���� ��������:2019/2/25
     *
     * @param newTestrequirement
     *            java.lang.String
     */
    public void setTestrequirement(String testrequirement) {
        this.testrequirement = testrequirement;
    }

    /**
     * ���� checkingproperty��Getter����.���������z�y���| ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getCheckingproperty() {
        return this.checkingproperty;
    }

    /**
     * ����checkingproperty��Setter����.���������z�y���| ��������:2019/2/25
     *
     * @param newCheckingproperty
     *            java.lang.String
     */
    public void setCheckingproperty(String checkingproperty) {
        this.checkingproperty = checkingproperty;
    }

    /**
     * ���� productline��Getter����.�����������a�a�� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getProductline() {
        return this.productline;
    }

    /**
     * ����productline��Setter����.�����������a�a�� ��������:2019/2/25
     *
     * @param newProductline
     *            java.lang.String
     */
    public void setProductline(String productline) {
        this.productline = productline;
    }

    /**
     * ���� batchnumber��Getter����.�����������a���� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getBatchnumber() {
        return this.batchnumber;
    }

    /**
     * ����batchnumber��Setter����.�����������a���� ��������:2019/2/25
     *
     * @param newBatchnumber
     *            java.lang.String
     */
    public void setBatchnumber(String batchnumber) {
        this.batchnumber = batchnumber;
    }

    /**
     * ���� productdate��Getter����.�����������a���� ��������:2019/2/25
     *
     * @return nc.vo.pub.lang.UFLiteralDate
     */
    public UFLiteralDate getProductdate() {
        return this.productdate;
    }

    /**
     * ����productdate��Setter����.�����������a���� ��������:2019/2/25
     *
     * @param newProductdate
     *            nc.vo.pub.lang.UFLiteralDate
     */
    public void setProductdate(UFLiteralDate productdate) {
        this.productdate = productdate;
    }

    /**
     * ���� batchserial��Getter����.�����������a��̖ ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getBatchserial() {
        return this.batchserial;
    }

    /**
     * ����batchserial��Setter����.�����������a��̖ ��������:2019/2/25
     *
     * @param newBatchserial
     *            java.lang.String
     */
    public void setBatchserial(String batchserial) {
        this.batchserial = batchserial;
    }

    /**
     * ���� identificationtype��Getter����.���������aƷ�b����� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getIdentificationtype() {
        return this.identificationtype;
    }

    /**
     * ����identificationtype��Setter����.���������aƷ�b����� ��������:2019/2/25
     *
     * @param newIdentificationtype
     *            java.lang.String
     */
    public void setIdentificationtype(String identificationtype) {
        this.identificationtype = identificationtype;
    }

    /**
     * ���� certificationtype��Getter����.���������J�C��� ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getCertificationtype() {
        return this.certificationtype;
    }

    /**
     * ����certificationtype��Setter����.���������J�C��� ��������:2019/2/25
     *
     * @param newCertificationtype
     *            java.lang.String
     */
    public void setCertificationtype(String certificationtype) {
        this.certificationtype = certificationtype;
    }

    /**
     * ���� itemnumber��Getter����.���������Ŀ̖ ��������:2019/2/25
     *
     * @return java.lang.String
     */
    public String getItemnumber() {
        return this.itemnumber;
    }

    /**
     * ����itemnumber��Setter����.���������Ŀ̖ ��������:2019/2/25
     *
     * @param newItemnumber
     *            java.lang.String
     */
    public void setItemnumber(String itemnumber) {
        this.itemnumber = itemnumber;
    }


    /**
     * ���� def1��Getter����.���������Զ�����1
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef1() {
        return this.def1;
    }

    /**
     * ����def1��Setter����.���������Զ�����1
     * ��������:2019-4-21
     * @param newDef1 java.lang.String
     */
    public void setDef1 ( String def1) {
        this.def1=def1;
    }

    /**
     * ���� def2��Getter����.���������Զ�����2
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef2() {
        return this.def2;
    }

    /**
     * ����def2��Setter����.���������Զ�����2
     * ��������:2019-4-21
     * @param newDef2 java.lang.String
     */
    public void setDef2 ( String def2) {
        this.def2=def2;
    }

    /**
     * ���� def3��Getter����.���������Զ�����3
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef3() {
        return this.def3;
    }

    /**
     * ����def3��Setter����.���������Զ�����3
     * ��������:2019-4-21
     * @param newDef3 java.lang.String
     */
    public void setDef3 ( String def3) {
        this.def3=def3;
    }

    /**
     * ���� def4��Getter����.���������Զ�����4
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef4() {
        return this.def4;
    }

    /**
     * ����def4��Setter����.���������Զ�����4
     * ��������:2019-4-21
     * @param newDef4 java.lang.String
     */
    public void setDef4 ( String def4) {
        this.def4=def4;
    }

    /**
     * ���� def5��Getter����.���������Զ�����5
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef5() {
        return this.def5;
    }

    /**
     * ����def5��Setter����.���������Զ�����5
     * ��������:2019-4-21
     * @param newDef5 java.lang.String
     */
    public void setDef5 ( String def5) {
        this.def5=def5;
    }

    /**
     * ���� def6��Getter����.���������Զ�����6
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef6() {
        return this.def6;
    }

    /**
     * ����def6��Setter����.���������Զ�����6
     * ��������:2019-4-21
     * @param newDef6 java.lang.String
     */
    public void setDef6 ( String def6) {
        this.def6=def6;
    }

    /**
     * ���� def7��Getter����.���������Զ�����7
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef7() {
        return this.def7;
    }

    /**
     * ����def7��Setter����.���������Զ�����7
     * ��������:2019-4-21
     * @param newDef7 java.lang.String
     */
    public void setDef7 ( String def7) {
        this.def7=def7;
    }

    /**
     * ���� def8��Getter����.���������Զ�����8
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef8() {
        return this.def8;
    }

    /**
     * ����def8��Setter����.���������Զ�����8
     * ��������:2019-4-21
     * @param newDef8 java.lang.String
     */
    public void setDef8 ( String def8) {
        this.def8=def8;
    }

    /**
     * ���� def9��Getter����.���������Զ�����9
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef9() {
        return this.def9;
    }

    /**
     * ����def9��Setter����.���������Զ�����9
     * ��������:2019-4-21
     * @param newDef9 java.lang.String
     */
    public void setDef9 ( String def9) {
        this.def9=def9;
    }

    /**
     * ���� def10��Getter����.���������Զ�����10
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef10() {
        return this.def10;
    }

    /**
     * ����def10��Setter����.���������Զ�����10
     * ��������:2019-4-21
     * @param newDef10 java.lang.String
     */
    public void setDef10 ( String def10) {
        this.def10=def10;
    }

    /**
     * ���� def11��Getter����.���������Զ�����11
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef11() {
        return this.def11;
    }

    /**
     * ����def11��Setter����.���������Զ�����11
     * ��������:2019-4-21
     * @param newDef11 java.lang.String
     */
    public void setDef11 ( String def11) {
        this.def11=def11;
    }

    /**
     * ���� def12��Getter����.���������Զ�����12
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef12() {
        return this.def12;
    }

    /**
     * ����def12��Setter����.���������Զ�����12
     * ��������:2019-4-21
     * @param newDef12 java.lang.String
     */
    public void setDef12 ( String def12) {
        this.def12=def12;
    }

    /**
     * ���� def13��Getter����.���������Զ�����13
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef13() {
        return this.def13;
    }

    /**
     * ����def13��Setter����.���������Զ�����13
     * ��������:2019-4-21
     * @param newDef13 java.lang.String
     */
    public void setDef13 ( String def13) {
        this.def13=def13;
    }

    /**
     * ���� def14��Getter����.���������Զ�����14
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef14() {
        return this.def14;
    }

    /**
     * ����def14��Setter����.���������Զ�����14
     * ��������:2019-4-21
     * @param newDef14 java.lang.String
     */
    public void setDef14 ( String def14) {
        this.def14=def14;
    }

    /**
     * ���� def15��Getter����.���������Զ�����15
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef15() {
        return this.def15;
    }

    /**
     * ����def15��Setter����.���������Զ�����15
     * ��������:2019-4-21
     * @param newDef15 java.lang.String
     */
    public void setDef15 ( String def15) {
        this.def15=def15;
    }

    /**
     * ���� def16��Getter����.���������Զ�����16
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef16() {
        return this.def16;
    }

    /**
     * ����def16��Setter����.���������Զ�����16
     * ��������:2019-4-21
     * @param newDef16 java.lang.String
     */
    public void setDef16 ( String def16) {
        this.def16=def16;
    }

    /**
     * ���� def17��Getter����.���������Զ�����17
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef17() {
        return this.def17;
    }

    /**
     * ����def17��Setter����.���������Զ�����17
     * ��������:2019-4-21
     * @param newDef17 java.lang.String
     */
    public void setDef17 ( String def17) {
        this.def17=def17;
    }

    /**
     * ���� def18��Getter����.���������Զ�����18
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef18() {
        return this.def18;
    }

    /**
     * ����def18��Setter����.���������Զ�����18
     * ��������:2019-4-21
     * @param newDef18 java.lang.String
     */
    public void setDef18 ( String def18) {
        this.def18=def18;
    }

    /**
     * ���� def19��Getter����.���������Զ�����19
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef19() {
        return this.def19;
    }

    /**
     * ����def19��Setter����.���������Զ�����19
     * ��������:2019-4-21
     * @param newDef19 java.lang.String
     */
    public void setDef19 ( String def19) {
        this.def19=def19;
    }

    /**
     * ���� def20��Getter����.���������Զ�����20
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef20() {
        return this.def20;
    }

    /**
     * ����def20��Setter����.���������Զ�����20
     * ��������:2019-4-21
     * @param newDef20 java.lang.String
     */
    public void setDef20 ( String def20) {
        this.def20=def20;
    }

    /**
     * ���� def21��Getter����.���������Զ�����21
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef21() {
        return this.def21;
    }

    /**
     * ����def21��Setter����.���������Զ�����21
     * ��������:2019-4-21
     * @param newDef21 java.lang.String
     */
    public void setDef21 ( String def21) {
        this.def21=def21;
    }

    /**
     * ���� def22��Getter����.���������Զ�����22
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef22() {
        return this.def22;
    }

    /**
     * ����def22��Setter����.���������Զ�����22
     * ��������:2019-4-21
     * @param newDef22 java.lang.String
     */
    public void setDef22 ( String def22) {
        this.def22=def22;
    }

    /**
     * ���� def23��Getter����.���������Զ�����23
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef23() {
        return this.def23;
    }

    /**
     * ����def23��Setter����.���������Զ�����23
     * ��������:2019-4-21
     * @param newDef23 java.lang.String
     */
    public void setDef23 ( String def23) {
        this.def23=def23;
    }

    /**
     * ���� def24��Getter����.���������Զ�����24
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef24() {
        return this.def24;
    }

    /**
     * ����def24��Setter����.���������Զ�����24
     * ��������:2019-4-21
     * @param newDef24 java.lang.String
     */
    public void setDef24 ( String def24) {
        this.def24=def24;
    }

    /**
     * ���� def25��Getter����.���������Զ�����25
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef25() {
        return this.def25;
    }

    /**
     * ����def25��Setter����.���������Զ�����25
     * ��������:2019-4-21
     * @param newDef25 java.lang.String
     */
    public void setDef25 ( String def25) {
        this.def25=def25;
    }

    /**
     * ���� def26��Getter����.���������Զ�����26
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef26() {
        return this.def26;
    }

    /**
     * ����def26��Setter����.���������Զ�����26
     * ��������:2019-4-21
     * @param newDef26 java.lang.String
     */
    public void setDef26 ( String def26) {
        this.def26=def26;
    }

    /**
     * ���� def27��Getter����.���������Զ�����27
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef27() {
        return this.def27;
    }

    /**
     * ����def27��Setter����.���������Զ�����27
     * ��������:2019-4-21
     * @param newDef27 java.lang.String
     */
    public void setDef27 ( String def27) {
        this.def27=def27;
    }

    /**
     * ���� def28��Getter����.���������Զ�����28
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef28() {
        return this.def28;
    }

    /**
     * ����def28��Setter����.���������Զ�����28
     * ��������:2019-4-21
     * @param newDef28 java.lang.String
     */
    public void setDef28 ( String def28) {
        this.def28=def28;
    }

    /**
     * ���� def29��Getter����.���������Զ�����29
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef29() {
        return this.def29;
    }

    /**
     * ����def29��Setter����.���������Զ�����29
     * ��������:2019-4-21
     * @param newDef29 java.lang.String
     */
    public void setDef29 ( String def29) {
        this.def29=def29;
    }

    /**
     * ���� def30��Getter����.���������Զ�����30
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef30() {
        return this.def30;
    }

    /**
     * ����def30��Setter����.���������Զ�����30
     * ��������:2019-4-21
     * @param newDef30 java.lang.String
     */
    public void setDef30 ( String def30) {
        this.def30=def30;
    }

    /**
     * ���� def31��Getter����.���������Զ�����31
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef31() {
        return this.def31;
    }

    /**
     * ����def31��Setter����.���������Զ�����31
     * ��������:2019-4-21
     * @param newDef31 java.lang.String
     */
    public void setDef31 ( String def31) {
        this.def31=def31;
    }

    /**
     * ���� def32��Getter����.���������Զ�����32
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef32() {
        return this.def32;
    }

    /**
     * ����def32��Setter����.���������Զ�����32
     * ��������:2019-4-21
     * @param newDef32 java.lang.String
     */
    public void setDef32 ( String def32) {
        this.def32=def32;
    }

    /**
     * ���� def33��Getter����.���������Զ�����33
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef33() {
        return this.def33;
    }

    /**
     * ����def33��Setter����.���������Զ�����33
     * ��������:2019-4-21
     * @param newDef33 java.lang.String
     */
    public void setDef33 ( String def33) {
        this.def33=def33;
    }

    /**
     * ���� def34��Getter����.���������Զ�����34
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef34() {
        return this.def34;
    }

    /**
     * ����def34��Setter����.���������Զ�����34
     * ��������:2019-4-21
     * @param newDef34 java.lang.String
     */
    public void setDef34 ( String def34) {
        this.def34=def34;
    }

    /**
     * ���� def35��Getter����.���������Զ�����35
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef35() {
        return this.def35;
    }

    /**
     * ����def35��Setter����.���������Զ�����35
     * ��������:2019-4-21
     * @param newDef35 java.lang.String
     */
    public void setDef35 ( String def35) {
        this.def35=def35;
    }

    /**
     * ���� def36��Getter����.���������Զ�����36
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef36() {
        return this.def36;
    }

    /**
     * ����def36��Setter����.���������Զ�����36
     * ��������:2019-4-21
     * @param newDef36 java.lang.String
     */
    public void setDef36 ( String def36) {
        this.def36=def36;
    }

    /**
     * ���� def37��Getter����.���������Զ�����37
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef37() {
        return this.def37;
    }

    /**
     * ����def37��Setter����.���������Զ�����37
     * ��������:2019-4-21
     * @param newDef37 java.lang.String
     */
    public void setDef37 ( String def37) {
        this.def37=def37;
    }

    /**
     * ���� def38��Getter����.���������Զ�����38
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef38() {
        return this.def38;
    }

    /**
     * ����def38��Setter����.���������Զ�����38
     * ��������:2019-4-21
     * @param newDef38 java.lang.String
     */
    public void setDef38 ( String def38) {
        this.def38=def38;
    }

    /**
     * ���� def39��Getter����.���������Զ�����39
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef39() {
        return this.def39;
    }

    /**
     * ����def39��Setter����.���������Զ�����39
     * ��������:2019-4-21
     * @param newDef39 java.lang.String
     */
    public void setDef39 ( String def39) {
        this.def39=def39;
    }

    /**
     * ���� def40��Getter����.���������Զ�����40
     *  ��������:2019-4-21
     * @return java.lang.String
     */
    public String getDef40() {
        return this.def40;
    }

    /**
     * ����def40��Setter����.���������Զ�����40
     * ��������:2019-4-21
     * @param newDef40 java.lang.String
     */
    public void setDef40 ( String def40) {
        this.def40=def40;
    }


    /**
     * ���� ���ɕr�g����Getter����.���������r�g�� ��������:2019/2/25
     *
     * @return nc.vo.pub.lang.UFDateTime
     */
    public UFDateTime getTs() {
        return this.ts;
    }

    /**
     * �������ɕr�g����Setter����.���������r�g�� ��������:2019/2/25
     *
     * @param newts
     *            nc.vo.pub.lang.UFDateTime
     */
    public void setTs(UFDateTime ts) {
        this.ts = ts;
    }

    @Override
    public IVOMeta getMetaData() {
        return VOMetaFactory.getInstance().getVOMeta("qcco.commission_h");
    }
}
