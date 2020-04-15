package nc.vo.qcco.task;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
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

public class TaskHVO extends SuperVO {

	/**
	 * �������I
	 */
	public String pk_task_h;
	/**
	 * ίӚ�����I
	 */
	public String pk_commission_h;
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
	 * �Ɔ�����
	 */
	public UFDate dbilldate;
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
	 * �ƆΕr�g
	 */
	public UFDateTime maketime;
	/**
	 * �����޸ĕr�g
	 */
	public UFDateTime lastmaketime;
	/**
	 * �Γ�ID
	 */
	public String billid;
	/**
	 * �Γ�̖
	 */
	public String billno;
	/**
	 * �I�����
	 */
	public String busitype;
	/**
	 * �Ɔ���
	 */
	public String billmaker;
	/**
	 * ������
	 */
	public String approver;
	/**
	 * ������B
	 */
	public Integer approvestatus;
	/**
	 * �������Z
	 */
	public String approvenote;
	/**
	 * �����r�g
	 */
	public UFDateTime approvedate;
	/**
	 * �������
	 */
	public String transtype;
	/**
	 * �Γ����
	 */
	public String billtype;
	/**
	 * �������pk
	 */
	public String transtypepk;
	/**
	 * ��Դ�Γ����
	 */
	public String srcbilltype;
	/**
	 * ��Դ�Γ�id
	 */
	public String srcbillid;
	/**
	 * ��ӆö�e
	 */
	public Integer emendenum;
	/**
	 * �Γ��汾pk
	 */
	public String billversionpk;
	/**
	 * �Զ��x�1
	 */
	public String def1;
	/**
	 * �Զ��x�2
	 */
	public String def2;
	/**
	 * �Զ��x�3
	 */
	public String def3;
	/**
	 * �Զ��x�4
	 */
	public String def4;
	/**
	 * �Զ��x�5
	 */
	public String def5;
	/**
	 * �Զ��x�6
	 */
	public String def6;
	/**
	 * �Զ��x�7
	 */
	public String def7;
	/**
	 * �Զ��x�8
	 */
	public String def8;
	/**
	 * �Զ��x�9
	 */
	public String def9;
	/**
	 * �Զ��x�10
	 */
	public String def10;
	/**
	 * �Զ��x�11
	 */
	public String def11;
	/**
	 * �Զ��x�12
	 */
	public String def12;
	/**
	 * �Զ��x�13
	 */
	public String def13;
	/**
	 * �Զ��x�14
	 */
	public String def14;
	/**
	 * �Զ��x�15
	 */
	public String def15;
	/**
	 * �Զ��x�16
	 */
	public String def16;
	/**
	 * �Զ��x�17
	 */
	public String def17;
	/**
	 * �Զ��x�18
	 */
	public String def18;
	/**
	 * �Զ��x�19
	 */
	public String def19;
	/**
	 * �Զ��x�20
	 */
	public String def20;
	/**
	 * �Զ��x�21
	 */
	public String def21;
	/**
	 * �Զ��x�22
	 */
	public String def22;
	/**
	 * �Զ��x�23
	 */
	public String def23;
	/**
	 * �Զ��x�24
	 */
	public String def24;
	/**
	 * �Զ��x�25
	 */
	public String def25;
	/**
	 * �Զ��x�26
	 */
	public String def26;
	/**
	 * �Զ��x�27
	 */
	public String def27;
	/**
	 * �Զ��x�28
	 */
	public String def28;
	/**
	 * �Զ��x�29
	 */
	public String def29;
	/**
	 * �Զ��x�30
	 */
	public String def30;
	/**
	 * �Զ��x�31
	 */
	public String def31;
	/**
	 * �Զ��x�32
	 */
	public String def32;
	/**
	 * �Զ��x�33
	 */
	public String def33;
	/**
	 * �Զ��x�34
	 */
	public String def34;
	/**
	 * �Զ��x�35
	 */
	public String def35;
	/**
	 * �Զ��x�36
	 */
	public String def36;
	/**
	 * �Զ��x�37
	 */
	public String def37;
	/**
	 * �Զ��x�38
	 */
	public String def38;
	/**
	 * �Զ��x�39
	 */
	public String def39;
	/**
	 * �Զ��x�40
	 */
	public String def40;
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
	 * ���� pk_task_h��Getter����.���������������I ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getPk_task_h() {
		return this.pk_task_h;
	}

	/**
	 * ����pk_task_h��Setter����.���������������I ��������:2019/5/5
	 * 
	 * @param newPk_task_h
	 *            java.lang.String
	 */
	public void setPk_task_h(String pk_task_h) {
		this.pk_task_h = pk_task_h;
	}

	/**
	 * ���� pk_commission_h��Getter����.��������ίӚ�����I ��������:2019/5/5
	 * 
	 * @return nc.vo.qcco.commission.CommissionHVO
	 */
	public String getPk_commission_h() {
		return this.pk_commission_h;
	}

	/**
	 * ����pk_commission_h��Setter����.��������ίӚ�����I ��������:2019/5/5
	 * 
	 * @param newPk_commission_h
	 *            nc.vo.qcco.commission.CommissionHVO
	 */
	public void setPk_commission_h(String pk_commission_h) {
		this.pk_commission_h = pk_commission_h;
	}

	/**
	 * ���� pk_group��Getter����.�����������F ��������:2019/5/5
	 * 
	 * @return nc.vo.org.GroupVO
	 */
	public String getPk_group() {
		return this.pk_group;
	}

	/**
	 * ����pk_group��Setter����.�����������F ��������:2019/5/5
	 * 
	 * @param newPk_group
	 *            nc.vo.org.GroupVO
	 */
	public void setPk_group(String pk_group) {
		this.pk_group = pk_group;
	}

	/**
	 * ���� pk_org��Getter����.���������M�� ��������:2019/5/5
	 * 
	 * @return nc.vo.org.OrgVO
	 */
	public String getPk_org() {
		return this.pk_org;
	}

	/**
	 * ����pk_org��Setter����.���������M�� ��������:2019/5/5
	 * 
	 * @param newPk_org
	 *            nc.vo.org.OrgVO
	 */
	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}

	/**
	 * ���� pk_org_v��Getter����.���������M���汾 ��������:2019/5/5
	 * 
	 * @return nc.vo.vorg.OrgVersionVO
	 */
	public String getPk_org_v() {
		return this.pk_org_v;
	}

	/**
	 * ����pk_org_v��Setter����.���������M���汾 ��������:2019/5/5
	 * 
	 * @param newPk_org_v
	 *            nc.vo.vorg.OrgVersionVO
	 */
	public void setPk_org_v(String pk_org_v) {
		this.pk_org_v = pk_org_v;
	}

	/**
	 * ���� dbilldate��Getter����.���������Ɔ����� ��������:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDate
	 */
	public UFDate getDbilldate() {
		return this.dbilldate;
	}

	/**
	 * ����dbilldate��Setter����.���������Ɔ����� ��������:2019/5/5
	 * 
	 * @param newDbilldate
	 *            nc.vo.pub.lang.UFDate
	 */
	public void setDbilldate(UFDate dbilldate) {
		this.dbilldate = dbilldate;
	}

	/**
	 * ���� creator��Getter����.�������������� ��������:2019/5/5
	 * 
	 * @return nc.vo.sm.UserVO
	 */
	public String getCreator() {
		return this.creator;
	}

	/**
	 * ����creator��Setter����.�������������� ��������:2019/5/5
	 * 
	 * @param newCreator
	 *            nc.vo.sm.UserVO
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * ���� creationtime��Getter����.�������������r�g ��������:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getCreationtime() {
		return this.creationtime;
	}

	/**
	 * ����creationtime��Setter����.�������������r�g ��������:2019/5/5
	 * 
	 * @param newCreationtime
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setCreationtime(UFDateTime creationtime) {
		this.creationtime = creationtime;
	}

	/**
	 * ���� modifier��Getter����.���������޸��� ��������:2019/5/5
	 * 
	 * @return nc.vo.sm.UserVO
	 */
	public String getModifier() {
		return this.modifier;
	}

	/**
	 * ����modifier��Setter����.���������޸��� ��������:2019/5/5
	 * 
	 * @param newModifier
	 *            nc.vo.sm.UserVO
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * ���� modifiedtime��Getter����.���������޸ĕr�g ��������:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getModifiedtime() {
		return this.modifiedtime;
	}

	/**
	 * ����modifiedtime��Setter����.���������޸ĕr�g ��������:2019/5/5
	 * 
	 * @param newModifiedtime
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setModifiedtime(UFDateTime modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	/**
	 * ���� maketime��Getter����.���������ƆΕr�g ��������:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getMaketime() {
		return this.maketime;
	}

	/**
	 * ����maketime��Setter����.���������ƆΕr�g ��������:2019/5/5
	 * 
	 * @param newMaketime
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setMaketime(UFDateTime maketime) {
		this.maketime = maketime;
	}

	/**
	 * ���� lastmaketime��Getter����.�������������޸ĕr�g ��������:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getLastmaketime() {
		return this.lastmaketime;
	}

	/**
	 * ����lastmaketime��Setter����.�������������޸ĕr�g ��������:2019/5/5
	 * 
	 * @param newLastmaketime
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setLastmaketime(UFDateTime lastmaketime) {
		this.lastmaketime = lastmaketime;
	}

	/**
	 * ���� billid��Getter����.���������Γ�ID ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getBillid() {
		return this.getBillversionpk();
	}

	/**
	 * ����billid��Setter����.���������Γ�ID ��������:2019/5/5
	 * 
	 * @param newBillid
	 *            java.lang.String
	 */
	public void setBillid(String billid) {
		this.billversionpk = billid;
	}

	/**
	 * ���� billno��Getter����.���������Γ�̖ ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getBillno() {
		return this.billno;
	}

	/**
	 * ����billno��Setter����.���������Γ�̖ ��������:2019/5/5
	 * 
	 * @param newBillno
	 *            java.lang.String
	 */
	public void setBillno(String billno) {
		this.billno = billno;
	}

	/**
	 * ���� busitype��Getter����.���������I����� ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getBusitype() {
		return this.busitype;
	}

	/**
	 * ����busitype��Setter����.���������I����� ��������:2019/5/5
	 * 
	 * @param newBusitype
	 *            java.lang.String
	 */
	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}

	/**
	 * ���� billmaker��Getter����.���������Ɔ��� ��������:2019/5/5
	 * 
	 * @return nc.vo.sm.UserVO
	 */
	public String getBillmaker() {
		return this.billmaker;
	}

	/**
	 * ����billmaker��Setter����.���������Ɔ��� ��������:2019/5/5
	 * 
	 * @param newBillmaker
	 *            nc.vo.sm.UserVO
	 */
	public void setBillmaker(String billmaker) {
		this.billmaker = billmaker;
	}

	/**
	 * ���� approver��Getter����.�������������� ��������:2019/5/5
	 * 
	 * @return nc.vo.sm.UserVO
	 */
	public String getApprover() {
		return this.approver;
	}

	/**
	 * ����approver��Setter����.�������������� ��������:2019/5/5
	 * 
	 * @param newApprover
	 *            nc.vo.sm.UserVO
	 */
	public void setApprover(String approver) {
		this.approver = approver;
	}

	/**
	 * ���� approvestatus��Getter����.��������������B ��������:2019/5/5
	 * 
	 * @return nc.vo.pub.pf.BillStatusEnum
	 */
	public Integer getApprovestatus() {
		return this.approvestatus;
	}

	/**
	 * ����approvestatus��Setter����.��������������B ��������:2019/5/5
	 * 
	 * @param newApprovestatus
	 *            nc.vo.pub.pf.BillStatusEnum
	 */
	public void setApprovestatus(Integer approvestatus) {
		this.approvestatus = approvestatus;
	}

	/**
	 * ���� approvenote��Getter����.���������������Z ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getApprovenote() {
		return this.approvenote;
	}

	/**
	 * ����approvenote��Setter����.���������������Z ��������:2019/5/5
	 * 
	 * @param newApprovenote
	 *            java.lang.String
	 */
	public void setApprovenote(String approvenote) {
		this.approvenote = approvenote;
	}

	/**
	 * ���� approvedate��Getter����.�������������r�g ��������:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getApprovedate() {
		return this.approvedate;
	}

	/**
	 * ����approvedate��Setter����.�������������r�g ��������:2019/5/5
	 * 
	 * @param newApprovedate
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setApprovedate(UFDateTime approvedate) {
		this.approvedate = approvedate;
	}

	/**
	 * ���� transtype��Getter����.��������������� ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getTranstype() {
		return this.transtype;
	}

	/**
	 * ����transtype��Setter����.��������������� ��������:2019/5/5
	 * 
	 * @param newTranstype
	 *            java.lang.String
	 */
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	/**
	 * ���� billtype��Getter����.���������Γ���� ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getBilltype() {
		return this.billtype;
	}

	/**
	 * ����billtype��Setter����.���������Γ���� ��������:2019/5/5
	 * 
	 * @param newBilltype
	 *            java.lang.String
	 */
	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	/**
	 * ���� transtypepk��Getter����.���������������pk ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getTranstypepk() {
		return this.transtypepk;
	}

	/**
	 * ����transtypepk��Setter����.���������������pk ��������:2019/5/5
	 * 
	 * @param newTranstypepk
	 *            java.lang.String
	 */
	public void setTranstypepk(String transtypepk) {
		this.transtypepk = transtypepk;
	}

	/**
	 * ���� srcbilltype��Getter����.����������Դ�Γ���� ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getSrcbilltype() {
		return this.srcbilltype;
	}

	/**
	 * ����srcbilltype��Setter����.����������Դ�Γ���� ��������:2019/5/5
	 * 
	 * @param newSrcbilltype
	 *            java.lang.String
	 */
	public void setSrcbilltype(String srcbilltype) {
		this.srcbilltype = srcbilltype;
	}

	/**
	 * ���� srcbillid��Getter����.����������Դ�Γ�id ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getSrcbillid() {
		return this.srcbillid;
	}

	/**
	 * ����srcbillid��Setter����.����������Դ�Γ�id ��������:2019/5/5
	 * 
	 * @param newSrcbillid
	 *            java.lang.String
	 */
	public void setSrcbillid(String srcbillid) {
		this.srcbillid = srcbillid;
	}

	/**
	 * ���� emendenum��Getter����.����������ӆö�e ��������:2019/5/5
	 * 
	 * @return java.lang.Integer
	 */
	public Integer getEmendenum() {
		return this.emendenum;
	}

	/**
	 * ����emendenum��Setter����.����������ӆö�e ��������:2019/5/5
	 * 
	 * @param newEmendenum
	 *            java.lang.Integer
	 */
	public void setEmendenum(Integer emendenum) {
		this.emendenum = emendenum;
	}

	/**
	 * ���� billversionpk��Getter����.���������Γ��汾pk ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getBillversionpk() {
		return this.billversionpk==null?this.pk_task_h:this.billversionpk;
	}

	/**
	 * ����billversionpk��Setter����.���������Γ��汾pk ��������:2019/5/5
	 * 
	 * @param newBillversionpk
	 *            java.lang.String
	 */
	public void setBillversionpk(String billversionpk) {
		this.billversionpk = billversionpk;
	}

	/**
	 * ���� def1��Getter����.���������Զ��x�1 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef1() {
		return this.def1;
	}

	/**
	 * ����def1��Setter����.���������Զ��x�1 ��������:2019/5/5
	 * 
	 * @param newDef1
	 *            java.lang.String
	 */
	public void setDef1(String def1) {
		this.def1 = def1;
	}

	/**
	 * ���� def2��Getter����.���������Զ��x�2 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef2() {
		return this.def2;
	}

	/**
	 * ����def2��Setter����.���������Զ��x�2 ��������:2019/5/5
	 * 
	 * @param newDef2
	 *            java.lang.String
	 */
	public void setDef2(String def2) {
		this.def2 = def2;
	}

	/**
	 * ���� def3��Getter����.���������Զ��x�3 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef3() {
		return this.def3;
	}

	/**
	 * ����def3��Setter����.���������Զ��x�3 ��������:2019/5/5
	 * 
	 * @param newDef3
	 *            java.lang.String
	 */
	public void setDef3(String def3) {
		this.def3 = def3;
	}

	/**
	 * ���� def4��Getter����.���������Զ��x�4 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef4() {
		return this.def4;
	}

	/**
	 * ����def4��Setter����.���������Զ��x�4 ��������:2019/5/5
	 * 
	 * @param newDef4
	 *            java.lang.String
	 */
	public void setDef4(String def4) {
		this.def4 = def4;
	}

	/**
	 * ���� def5��Getter����.���������Զ��x�5 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef5() {
		return this.def5;
	}

	/**
	 * ����def5��Setter����.���������Զ��x�5 ��������:2019/5/5
	 * 
	 * @param newDef5
	 *            java.lang.String
	 */
	public void setDef5(String def5) {
		this.def5 = def5;
	}

	/**
	 * ���� def6��Getter����.���������Զ��x�6 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef6() {
		return this.def6;
	}

	/**
	 * ����def6��Setter����.���������Զ��x�6 ��������:2019/5/5
	 * 
	 * @param newDef6
	 *            java.lang.String
	 */
	public void setDef6(String def6) {
		this.def6 = def6;
	}

	/**
	 * ���� def7��Getter����.���������Զ��x�7 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef7() {
		return this.def7;
	}

	/**
	 * ����def7��Setter����.���������Զ��x�7 ��������:2019/5/5
	 * 
	 * @param newDef7
	 *            java.lang.String
	 */
	public void setDef7(String def7) {
		this.def7 = def7;
	}

	/**
	 * ���� def8��Getter����.���������Զ��x�8 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef8() {
		return this.def8;
	}

	/**
	 * ����def8��Setter����.���������Զ��x�8 ��������:2019/5/5
	 * 
	 * @param newDef8
	 *            java.lang.String
	 */
	public void setDef8(String def8) {
		this.def8 = def8;
	}

	/**
	 * ���� def9��Getter����.���������Զ��x�9 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef9() {
		return this.def9;
	}

	/**
	 * ����def9��Setter����.���������Զ��x�9 ��������:2019/5/5
	 * 
	 * @param newDef9
	 *            java.lang.String
	 */
	public void setDef9(String def9) {
		this.def9 = def9;
	}

	/**
	 * ���� def10��Getter����.���������Զ��x�10 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef10() {
		return this.def10;
	}

	/**
	 * ����def10��Setter����.���������Զ��x�10 ��������:2019/5/5
	 * 
	 * @param newDef10
	 *            java.lang.String
	 */
	public void setDef10(String def10) {
		this.def10 = def10;
	}

	/**
	 * ���� def11��Getter����.���������Զ��x�11 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef11() {
		return this.def11;
	}

	/**
	 * ����def11��Setter����.���������Զ��x�11 ��������:2019/5/5
	 * 
	 * @param newDef11
	 *            java.lang.String
	 */
	public void setDef11(String def11) {
		this.def11 = def11;
	}

	/**
	 * ���� def12��Getter����.���������Զ��x�12 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef12() {
		return this.def12;
	}

	/**
	 * ����def12��Setter����.���������Զ��x�12 ��������:2019/5/5
	 * 
	 * @param newDef12
	 *            java.lang.String
	 */
	public void setDef12(String def12) {
		this.def12 = def12;
	}

	/**
	 * ���� def13��Getter����.���������Զ��x�13 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef13() {
		return this.def13;
	}

	/**
	 * ����def13��Setter����.���������Զ��x�13 ��������:2019/5/5
	 * 
	 * @param newDef13
	 *            java.lang.String
	 */
	public void setDef13(String def13) {
		this.def13 = def13;
	}

	/**
	 * ���� def14��Getter����.���������Զ��x�14 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef14() {
		return this.def14;
	}

	/**
	 * ����def14��Setter����.���������Զ��x�14 ��������:2019/5/5
	 * 
	 * @param newDef14
	 *            java.lang.String
	 */
	public void setDef14(String def14) {
		this.def14 = def14;
	}

	/**
	 * ���� def15��Getter����.���������Զ��x�15 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef15() {
		return this.def15;
	}

	/**
	 * ����def15��Setter����.���������Զ��x�15 ��������:2019/5/5
	 * 
	 * @param newDef15
	 *            java.lang.String
	 */
	public void setDef15(String def15) {
		this.def15 = def15;
	}

	/**
	 * ���� def16��Getter����.���������Զ��x�16 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef16() {
		return this.def16;
	}

	/**
	 * ����def16��Setter����.���������Զ��x�16 ��������:2019/5/5
	 * 
	 * @param newDef16
	 *            java.lang.String
	 */
	public void setDef16(String def16) {
		this.def16 = def16;
	}

	/**
	 * ���� def17��Getter����.���������Զ��x�17 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef17() {
		return this.def17;
	}

	/**
	 * ����def17��Setter����.���������Զ��x�17 ��������:2019/5/5
	 * 
	 * @param newDef17
	 *            java.lang.String
	 */
	public void setDef17(String def17) {
		this.def17 = def17;
	}

	/**
	 * ���� def18��Getter����.���������Զ��x�18 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef18() {
		return this.def18;
	}

	/**
	 * ����def18��Setter����.���������Զ��x�18 ��������:2019/5/5
	 * 
	 * @param newDef18
	 *            java.lang.String
	 */
	public void setDef18(String def18) {
		this.def18 = def18;
	}

	/**
	 * ���� def19��Getter����.���������Զ��x�19 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef19() {
		return this.def19;
	}

	/**
	 * ����def19��Setter����.���������Զ��x�19 ��������:2019/5/5
	 * 
	 * @param newDef19
	 *            java.lang.String
	 */
	public void setDef19(String def19) {
		this.def19 = def19;
	}

	/**
	 * ���� def20��Getter����.���������Զ��x�20 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef20() {
		return this.def20;
	}

	/**
	 * ����def20��Setter����.���������Զ��x�20 ��������:2019/5/5
	 * 
	 * @param newDef20
	 *            java.lang.String
	 */
	public void setDef20(String def20) {
		this.def20 = def20;
	}

	/**
	 * ���� def21��Getter����.���������Զ��x�21 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef21() {
		return this.def21;
	}

	/**
	 * ����def21��Setter����.���������Զ��x�21 ��������:2019/5/5
	 * 
	 * @param newDef21
	 *            java.lang.String
	 */
	public void setDef21(String def21) {
		this.def21 = def21;
	}

	/**
	 * ���� def22��Getter����.���������Զ��x�22 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef22() {
		return this.def22;
	}

	/**
	 * ����def22��Setter����.���������Զ��x�22 ��������:2019/5/5
	 * 
	 * @param newDef22
	 *            java.lang.String
	 */
	public void setDef22(String def22) {
		this.def22 = def22;
	}

	/**
	 * ���� def23��Getter����.���������Զ��x�23 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef23() {
		return this.def23;
	}

	/**
	 * ����def23��Setter����.���������Զ��x�23 ��������:2019/5/5
	 * 
	 * @param newDef23
	 *            java.lang.String
	 */
	public void setDef23(String def23) {
		this.def23 = def23;
	}

	/**
	 * ���� def24��Getter����.���������Զ��x�24 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef24() {
		return this.def24;
	}

	/**
	 * ����def24��Setter����.���������Զ��x�24 ��������:2019/5/5
	 * 
	 * @param newDef24
	 *            java.lang.String
	 */
	public void setDef24(String def24) {
		this.def24 = def24;
	}

	/**
	 * ���� def25��Getter����.���������Զ��x�25 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef25() {
		return this.def25;
	}

	/**
	 * ����def25��Setter����.���������Զ��x�25 ��������:2019/5/5
	 * 
	 * @param newDef25
	 *            java.lang.String
	 */
	public void setDef25(String def25) {
		this.def25 = def25;
	}

	/**
	 * ���� def26��Getter����.���������Զ��x�26 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef26() {
		return this.def26;
	}

	/**
	 * ����def26��Setter����.���������Զ��x�26 ��������:2019/5/5
	 * 
	 * @param newDef26
	 *            java.lang.String
	 */
	public void setDef26(String def26) {
		this.def26 = def26;
	}

	/**
	 * ���� def27��Getter����.���������Զ��x�27 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef27() {
		return this.def27;
	}

	/**
	 * ����def27��Setter����.���������Զ��x�27 ��������:2019/5/5
	 * 
	 * @param newDef27
	 *            java.lang.String
	 */
	public void setDef27(String def27) {
		this.def27 = def27;
	}

	/**
	 * ���� def28��Getter����.���������Զ��x�28 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef28() {
		return this.def28;
	}

	/**
	 * ����def28��Setter����.���������Զ��x�28 ��������:2019/5/5
	 * 
	 * @param newDef28
	 *            java.lang.String
	 */
	public void setDef28(String def28) {
		this.def28 = def28;
	}

	/**
	 * ���� def29��Getter����.���������Զ��x�29 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef29() {
		return this.def29;
	}

	/**
	 * ����def29��Setter����.���������Զ��x�29 ��������:2019/5/5
	 * 
	 * @param newDef29
	 *            java.lang.String
	 */
	public void setDef29(String def29) {
		this.def29 = def29;
	}

	/**
	 * ���� def30��Getter����.���������Զ��x�30 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef30() {
		return this.def30;
	}

	/**
	 * ����def30��Setter����.���������Զ��x�30 ��������:2019/5/5
	 * 
	 * @param newDef30
	 *            java.lang.String
	 */
	public void setDef30(String def30) {
		this.def30 = def30;
	}

	/**
	 * ���� def31��Getter����.���������Զ��x�31 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef31() {
		return this.def31;
	}

	/**
	 * ����def31��Setter����.���������Զ��x�31 ��������:2019/5/5
	 * 
	 * @param newDef31
	 *            java.lang.String
	 */
	public void setDef31(String def31) {
		this.def31 = def31;
	}

	/**
	 * ���� def32��Getter����.���������Զ��x�32 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef32() {
		return this.def32;
	}

	/**
	 * ����def32��Setter����.���������Զ��x�32 ��������:2019/5/5
	 * 
	 * @param newDef32
	 *            java.lang.String
	 */
	public void setDef32(String def32) {
		this.def32 = def32;
	}

	/**
	 * ���� def33��Getter����.���������Զ��x�33 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef33() {
		return this.def33;
	}

	/**
	 * ����def33��Setter����.���������Զ��x�33 ��������:2019/5/5
	 * 
	 * @param newDef33
	 *            java.lang.String
	 */
	public void setDef33(String def33) {
		this.def33 = def33;
	}

	/**
	 * ���� def34��Getter����.���������Զ��x�34 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef34() {
		return this.def34;
	}

	/**
	 * ����def34��Setter����.���������Զ��x�34 ��������:2019/5/5
	 * 
	 * @param newDef34
	 *            java.lang.String
	 */
	public void setDef34(String def34) {
		this.def34 = def34;
	}

	/**
	 * ���� def35��Getter����.���������Զ��x�35 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef35() {
		return this.def35;
	}

	/**
	 * ����def35��Setter����.���������Զ��x�35 ��������:2019/5/5
	 * 
	 * @param newDef35
	 *            java.lang.String
	 */
	public void setDef35(String def35) {
		this.def35 = def35;
	}

	/**
	 * ���� def36��Getter����.���������Զ��x�36 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef36() {
		return this.def36;
	}

	/**
	 * ����def36��Setter����.���������Զ��x�36 ��������:2019/5/5
	 * 
	 * @param newDef36
	 *            java.lang.String
	 */
	public void setDef36(String def36) {
		this.def36 = def36;
	}

	/**
	 * ���� def37��Getter����.���������Զ��x�37 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef37() {
		return this.def37;
	}

	/**
	 * ����def37��Setter����.���������Զ��x�37 ��������:2019/5/5
	 * 
	 * @param newDef37
	 *            java.lang.String
	 */
	public void setDef37(String def37) {
		this.def37 = def37;
	}

	/**
	 * ���� def38��Getter����.���������Զ��x�38 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef38() {
		return this.def38;
	}

	/**
	 * ����def38��Setter����.���������Զ��x�38 ��������:2019/5/5
	 * 
	 * @param newDef38
	 *            java.lang.String
	 */
	public void setDef38(String def38) {
		this.def38 = def38;
	}

	/**
	 * ���� def39��Getter����.���������Զ��x�39 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef39() {
		return this.def39;
	}

	/**
	 * ����def39��Setter����.���������Զ��x�39 ��������:2019/5/5
	 * 
	 * @param newDef39
	 *            java.lang.String
	 */
	public void setDef39(String def39) {
		this.def39 = def39;
	}

	/**
	 * ���� def40��Getter����.���������Զ��x�40 ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef40() {
		return this.def40;
	}

	/**
	 * ����def40��Setter����.���������Զ��x�40 ��������:2019/5/5
	 * 
	 * @param newDef40
	 *            java.lang.String
	 */
	public void setDef40(String def40) {
		this.def40 = def40;
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
		return VOMetaFactory.getInstance().getVOMeta("qcco.task_h");
	}
}
