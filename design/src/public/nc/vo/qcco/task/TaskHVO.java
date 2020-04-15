package nc.vo.qcco.task;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

/**
 * <b> 此處簡要描述此類功能 </b>
 * <p>
 * 此處添加累的描述信息
 * </p>
 * 創建日期:2019/5/5
 * 
 * @author yonyouBQ
 * @version NCPrj ??
 */

public class TaskHVO extends SuperVO {

	/**
	 * 主表主鍵
	 */
	public String pk_task_h;
	/**
	 * 委託單主鍵
	 */
	public String pk_commission_h;
	/**
	 * 集團
	 */
	public String pk_group;
	/**
	 * 組織
	 */
	public String pk_org;
	/**
	 * 組織版本
	 */
	public String pk_org_v;
	/**
	 * 制單日期
	 */
	public UFDate dbilldate;
	/**
	 * 創建人
	 */
	public String creator;
	/**
	 * 創建時間
	 */
	public UFDateTime creationtime;
	/**
	 * 修改人
	 */
	public String modifier;
	/**
	 * 修改時間
	 */
	public UFDateTime modifiedtime;
	/**
	 * 制單時間
	 */
	public UFDateTime maketime;
	/**
	 * 最後修改時間
	 */
	public UFDateTime lastmaketime;
	/**
	 * 單據ID
	 */
	public String billid;
	/**
	 * 單據號
	 */
	public String billno;
	/**
	 * 業務類型
	 */
	public String busitype;
	/**
	 * 制單人
	 */
	public String billmaker;
	/**
	 * 審批人
	 */
	public String approver;
	/**
	 * 審批狀態
	 */
	public Integer approvestatus;
	/**
	 * 審批批語
	 */
	public String approvenote;
	/**
	 * 審批時間
	 */
	public UFDateTime approvedate;
	/**
	 * 交易類型
	 */
	public String transtype;
	/**
	 * 單據類型
	 */
	public String billtype;
	/**
	 * 交易類型pk
	 */
	public String transtypepk;
	/**
	 * 來源單據類型
	 */
	public String srcbilltype;
	/**
	 * 來源單據id
	 */
	public String srcbillid;
	/**
	 * 修訂枚舉
	 */
	public Integer emendenum;
	/**
	 * 單據版本pk
	 */
	public String billversionpk;
	/**
	 * 自定義項1
	 */
	public String def1;
	/**
	 * 自定義項2
	 */
	public String def2;
	/**
	 * 自定義項3
	 */
	public String def3;
	/**
	 * 自定義項4
	 */
	public String def4;
	/**
	 * 自定義項5
	 */
	public String def5;
	/**
	 * 自定義項6
	 */
	public String def6;
	/**
	 * 自定義項7
	 */
	public String def7;
	/**
	 * 自定義項8
	 */
	public String def8;
	/**
	 * 自定義項9
	 */
	public String def9;
	/**
	 * 自定義項10
	 */
	public String def10;
	/**
	 * 自定義項11
	 */
	public String def11;
	/**
	 * 自定義項12
	 */
	public String def12;
	/**
	 * 自定義項13
	 */
	public String def13;
	/**
	 * 自定義項14
	 */
	public String def14;
	/**
	 * 自定義項15
	 */
	public String def15;
	/**
	 * 自定義項16
	 */
	public String def16;
	/**
	 * 自定義項17
	 */
	public String def17;
	/**
	 * 自定義項18
	 */
	public String def18;
	/**
	 * 自定義項19
	 */
	public String def19;
	/**
	 * 自定義項20
	 */
	public String def20;
	/**
	 * 自定義項21
	 */
	public String def21;
	/**
	 * 自定義項22
	 */
	public String def22;
	/**
	 * 自定義項23
	 */
	public String def23;
	/**
	 * 自定義項24
	 */
	public String def24;
	/**
	 * 自定義項25
	 */
	public String def25;
	/**
	 * 自定義項26
	 */
	public String def26;
	/**
	 * 自定義項27
	 */
	public String def27;
	/**
	 * 自定義項28
	 */
	public String def28;
	/**
	 * 自定義項29
	 */
	public String def29;
	/**
	 * 自定義項30
	 */
	public String def30;
	/**
	 * 自定義項31
	 */
	public String def31;
	/**
	 * 自定義項32
	 */
	public String def32;
	/**
	 * 自定義項33
	 */
	public String def33;
	/**
	 * 自定義項34
	 */
	public String def34;
	/**
	 * 自定義項35
	 */
	public String def35;
	/**
	 * 自定義項36
	 */
	public String def36;
	/**
	 * 自定義項37
	 */
	public String def37;
	/**
	 * 自定義項38
	 */
	public String def38;
	/**
	 * 自定義項39
	 */
	public String def39;
	/**
	 * 自定義項40
	 */
	public String def40;
	/**
	 * 時間戳
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
	 * 屬性 pk_task_h的Getter方法.屬性名：主表主鍵 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getPk_task_h() {
		return this.pk_task_h;
	}

	/**
	 * 屬性pk_task_h的Setter方法.屬性名：主表主鍵 創建日期:2019/5/5
	 * 
	 * @param newPk_task_h
	 *            java.lang.String
	 */
	public void setPk_task_h(String pk_task_h) {
		this.pk_task_h = pk_task_h;
	}

	/**
	 * 屬性 pk_commission_h的Getter方法.屬性名：委託單主鍵 創建日期:2019/5/5
	 * 
	 * @return nc.vo.qcco.commission.CommissionHVO
	 */
	public String getPk_commission_h() {
		return this.pk_commission_h;
	}

	/**
	 * 屬性pk_commission_h的Setter方法.屬性名：委託單主鍵 創建日期:2019/5/5
	 * 
	 * @param newPk_commission_h
	 *            nc.vo.qcco.commission.CommissionHVO
	 */
	public void setPk_commission_h(String pk_commission_h) {
		this.pk_commission_h = pk_commission_h;
	}

	/**
	 * 屬性 pk_group的Getter方法.屬性名：集團 創建日期:2019/5/5
	 * 
	 * @return nc.vo.org.GroupVO
	 */
	public String getPk_group() {
		return this.pk_group;
	}

	/**
	 * 屬性pk_group的Setter方法.屬性名：集團 創建日期:2019/5/5
	 * 
	 * @param newPk_group
	 *            nc.vo.org.GroupVO
	 */
	public void setPk_group(String pk_group) {
		this.pk_group = pk_group;
	}

	/**
	 * 屬性 pk_org的Getter方法.屬性名：組織 創建日期:2019/5/5
	 * 
	 * @return nc.vo.org.OrgVO
	 */
	public String getPk_org() {
		return this.pk_org;
	}

	/**
	 * 屬性pk_org的Setter方法.屬性名：組織 創建日期:2019/5/5
	 * 
	 * @param newPk_org
	 *            nc.vo.org.OrgVO
	 */
	public void setPk_org(String pk_org) {
		this.pk_org = pk_org;
	}

	/**
	 * 屬性 pk_org_v的Getter方法.屬性名：組織版本 創建日期:2019/5/5
	 * 
	 * @return nc.vo.vorg.OrgVersionVO
	 */
	public String getPk_org_v() {
		return this.pk_org_v;
	}

	/**
	 * 屬性pk_org_v的Setter方法.屬性名：組織版本 創建日期:2019/5/5
	 * 
	 * @param newPk_org_v
	 *            nc.vo.vorg.OrgVersionVO
	 */
	public void setPk_org_v(String pk_org_v) {
		this.pk_org_v = pk_org_v;
	}

	/**
	 * 屬性 dbilldate的Getter方法.屬性名：制單日期 創建日期:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDate
	 */
	public UFDate getDbilldate() {
		return this.dbilldate;
	}

	/**
	 * 屬性dbilldate的Setter方法.屬性名：制單日期 創建日期:2019/5/5
	 * 
	 * @param newDbilldate
	 *            nc.vo.pub.lang.UFDate
	 */
	public void setDbilldate(UFDate dbilldate) {
		this.dbilldate = dbilldate;
	}

	/**
	 * 屬性 creator的Getter方法.屬性名：創建人 創建日期:2019/5/5
	 * 
	 * @return nc.vo.sm.UserVO
	 */
	public String getCreator() {
		return this.creator;
	}

	/**
	 * 屬性creator的Setter方法.屬性名：創建人 創建日期:2019/5/5
	 * 
	 * @param newCreator
	 *            nc.vo.sm.UserVO
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * 屬性 creationtime的Getter方法.屬性名：創建時間 創建日期:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getCreationtime() {
		return this.creationtime;
	}

	/**
	 * 屬性creationtime的Setter方法.屬性名：創建時間 創建日期:2019/5/5
	 * 
	 * @param newCreationtime
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setCreationtime(UFDateTime creationtime) {
		this.creationtime = creationtime;
	}

	/**
	 * 屬性 modifier的Getter方法.屬性名：修改人 創建日期:2019/5/5
	 * 
	 * @return nc.vo.sm.UserVO
	 */
	public String getModifier() {
		return this.modifier;
	}

	/**
	 * 屬性modifier的Setter方法.屬性名：修改人 創建日期:2019/5/5
	 * 
	 * @param newModifier
	 *            nc.vo.sm.UserVO
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * 屬性 modifiedtime的Getter方法.屬性名：修改時間 創建日期:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getModifiedtime() {
		return this.modifiedtime;
	}

	/**
	 * 屬性modifiedtime的Setter方法.屬性名：修改時間 創建日期:2019/5/5
	 * 
	 * @param newModifiedtime
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setModifiedtime(UFDateTime modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	/**
	 * 屬性 maketime的Getter方法.屬性名：制單時間 創建日期:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getMaketime() {
		return this.maketime;
	}

	/**
	 * 屬性maketime的Setter方法.屬性名：制單時間 創建日期:2019/5/5
	 * 
	 * @param newMaketime
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setMaketime(UFDateTime maketime) {
		this.maketime = maketime;
	}

	/**
	 * 屬性 lastmaketime的Getter方法.屬性名：最後修改時間 創建日期:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getLastmaketime() {
		return this.lastmaketime;
	}

	/**
	 * 屬性lastmaketime的Setter方法.屬性名：最後修改時間 創建日期:2019/5/5
	 * 
	 * @param newLastmaketime
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setLastmaketime(UFDateTime lastmaketime) {
		this.lastmaketime = lastmaketime;
	}

	/**
	 * 屬性 billid的Getter方法.屬性名：單據ID 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getBillid() {
		return this.getBillversionpk();
	}

	/**
	 * 屬性billid的Setter方法.屬性名：單據ID 創建日期:2019/5/5
	 * 
	 * @param newBillid
	 *            java.lang.String
	 */
	public void setBillid(String billid) {
		this.billversionpk = billid;
	}

	/**
	 * 屬性 billno的Getter方法.屬性名：單據號 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getBillno() {
		return this.billno;
	}

	/**
	 * 屬性billno的Setter方法.屬性名：單據號 創建日期:2019/5/5
	 * 
	 * @param newBillno
	 *            java.lang.String
	 */
	public void setBillno(String billno) {
		this.billno = billno;
	}

	/**
	 * 屬性 busitype的Getter方法.屬性名：業務類型 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getBusitype() {
		return this.busitype;
	}

	/**
	 * 屬性busitype的Setter方法.屬性名：業務類型 創建日期:2019/5/5
	 * 
	 * @param newBusitype
	 *            java.lang.String
	 */
	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}

	/**
	 * 屬性 billmaker的Getter方法.屬性名：制單人 創建日期:2019/5/5
	 * 
	 * @return nc.vo.sm.UserVO
	 */
	public String getBillmaker() {
		return this.billmaker;
	}

	/**
	 * 屬性billmaker的Setter方法.屬性名：制單人 創建日期:2019/5/5
	 * 
	 * @param newBillmaker
	 *            nc.vo.sm.UserVO
	 */
	public void setBillmaker(String billmaker) {
		this.billmaker = billmaker;
	}

	/**
	 * 屬性 approver的Getter方法.屬性名：審批人 創建日期:2019/5/5
	 * 
	 * @return nc.vo.sm.UserVO
	 */
	public String getApprover() {
		return this.approver;
	}

	/**
	 * 屬性approver的Setter方法.屬性名：審批人 創建日期:2019/5/5
	 * 
	 * @param newApprover
	 *            nc.vo.sm.UserVO
	 */
	public void setApprover(String approver) {
		this.approver = approver;
	}

	/**
	 * 屬性 approvestatus的Getter方法.屬性名：審批狀態 創建日期:2019/5/5
	 * 
	 * @return nc.vo.pub.pf.BillStatusEnum
	 */
	public Integer getApprovestatus() {
		return this.approvestatus;
	}

	/**
	 * 屬性approvestatus的Setter方法.屬性名：審批狀態 創建日期:2019/5/5
	 * 
	 * @param newApprovestatus
	 *            nc.vo.pub.pf.BillStatusEnum
	 */
	public void setApprovestatus(Integer approvestatus) {
		this.approvestatus = approvestatus;
	}

	/**
	 * 屬性 approvenote的Getter方法.屬性名：審批批語 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getApprovenote() {
		return this.approvenote;
	}

	/**
	 * 屬性approvenote的Setter方法.屬性名：審批批語 創建日期:2019/5/5
	 * 
	 * @param newApprovenote
	 *            java.lang.String
	 */
	public void setApprovenote(String approvenote) {
		this.approvenote = approvenote;
	}

	/**
	 * 屬性 approvedate的Getter方法.屬性名：審批時間 創建日期:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getApprovedate() {
		return this.approvedate;
	}

	/**
	 * 屬性approvedate的Setter方法.屬性名：審批時間 創建日期:2019/5/5
	 * 
	 * @param newApprovedate
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setApprovedate(UFDateTime approvedate) {
		this.approvedate = approvedate;
	}

	/**
	 * 屬性 transtype的Getter方法.屬性名：交易類型 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getTranstype() {
		return this.transtype;
	}

	/**
	 * 屬性transtype的Setter方法.屬性名：交易類型 創建日期:2019/5/5
	 * 
	 * @param newTranstype
	 *            java.lang.String
	 */
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	/**
	 * 屬性 billtype的Getter方法.屬性名：單據類型 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getBilltype() {
		return this.billtype;
	}

	/**
	 * 屬性billtype的Setter方法.屬性名：單據類型 創建日期:2019/5/5
	 * 
	 * @param newBilltype
	 *            java.lang.String
	 */
	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	/**
	 * 屬性 transtypepk的Getter方法.屬性名：交易類型pk 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getTranstypepk() {
		return this.transtypepk;
	}

	/**
	 * 屬性transtypepk的Setter方法.屬性名：交易類型pk 創建日期:2019/5/5
	 * 
	 * @param newTranstypepk
	 *            java.lang.String
	 */
	public void setTranstypepk(String transtypepk) {
		this.transtypepk = transtypepk;
	}

	/**
	 * 屬性 srcbilltype的Getter方法.屬性名：來源單據類型 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getSrcbilltype() {
		return this.srcbilltype;
	}

	/**
	 * 屬性srcbilltype的Setter方法.屬性名：來源單據類型 創建日期:2019/5/5
	 * 
	 * @param newSrcbilltype
	 *            java.lang.String
	 */
	public void setSrcbilltype(String srcbilltype) {
		this.srcbilltype = srcbilltype;
	}

	/**
	 * 屬性 srcbillid的Getter方法.屬性名：來源單據id 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getSrcbillid() {
		return this.srcbillid;
	}

	/**
	 * 屬性srcbillid的Setter方法.屬性名：來源單據id 創建日期:2019/5/5
	 * 
	 * @param newSrcbillid
	 *            java.lang.String
	 */
	public void setSrcbillid(String srcbillid) {
		this.srcbillid = srcbillid;
	}

	/**
	 * 屬性 emendenum的Getter方法.屬性名：修訂枚舉 創建日期:2019/5/5
	 * 
	 * @return java.lang.Integer
	 */
	public Integer getEmendenum() {
		return this.emendenum;
	}

	/**
	 * 屬性emendenum的Setter方法.屬性名：修訂枚舉 創建日期:2019/5/5
	 * 
	 * @param newEmendenum
	 *            java.lang.Integer
	 */
	public void setEmendenum(Integer emendenum) {
		this.emendenum = emendenum;
	}

	/**
	 * 屬性 billversionpk的Getter方法.屬性名：單據版本pk 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getBillversionpk() {
		return this.billversionpk==null?this.pk_task_h:this.billversionpk;
	}

	/**
	 * 屬性billversionpk的Setter方法.屬性名：單據版本pk 創建日期:2019/5/5
	 * 
	 * @param newBillversionpk
	 *            java.lang.String
	 */
	public void setBillversionpk(String billversionpk) {
		this.billversionpk = billversionpk;
	}

	/**
	 * 屬性 def1的Getter方法.屬性名：自定義項1 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef1() {
		return this.def1;
	}

	/**
	 * 屬性def1的Setter方法.屬性名：自定義項1 創建日期:2019/5/5
	 * 
	 * @param newDef1
	 *            java.lang.String
	 */
	public void setDef1(String def1) {
		this.def1 = def1;
	}

	/**
	 * 屬性 def2的Getter方法.屬性名：自定義項2 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef2() {
		return this.def2;
	}

	/**
	 * 屬性def2的Setter方法.屬性名：自定義項2 創建日期:2019/5/5
	 * 
	 * @param newDef2
	 *            java.lang.String
	 */
	public void setDef2(String def2) {
		this.def2 = def2;
	}

	/**
	 * 屬性 def3的Getter方法.屬性名：自定義項3 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef3() {
		return this.def3;
	}

	/**
	 * 屬性def3的Setter方法.屬性名：自定義項3 創建日期:2019/5/5
	 * 
	 * @param newDef3
	 *            java.lang.String
	 */
	public void setDef3(String def3) {
		this.def3 = def3;
	}

	/**
	 * 屬性 def4的Getter方法.屬性名：自定義項4 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef4() {
		return this.def4;
	}

	/**
	 * 屬性def4的Setter方法.屬性名：自定義項4 創建日期:2019/5/5
	 * 
	 * @param newDef4
	 *            java.lang.String
	 */
	public void setDef4(String def4) {
		this.def4 = def4;
	}

	/**
	 * 屬性 def5的Getter方法.屬性名：自定義項5 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef5() {
		return this.def5;
	}

	/**
	 * 屬性def5的Setter方法.屬性名：自定義項5 創建日期:2019/5/5
	 * 
	 * @param newDef5
	 *            java.lang.String
	 */
	public void setDef5(String def5) {
		this.def5 = def5;
	}

	/**
	 * 屬性 def6的Getter方法.屬性名：自定義項6 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef6() {
		return this.def6;
	}

	/**
	 * 屬性def6的Setter方法.屬性名：自定義項6 創建日期:2019/5/5
	 * 
	 * @param newDef6
	 *            java.lang.String
	 */
	public void setDef6(String def6) {
		this.def6 = def6;
	}

	/**
	 * 屬性 def7的Getter方法.屬性名：自定義項7 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef7() {
		return this.def7;
	}

	/**
	 * 屬性def7的Setter方法.屬性名：自定義項7 創建日期:2019/5/5
	 * 
	 * @param newDef7
	 *            java.lang.String
	 */
	public void setDef7(String def7) {
		this.def7 = def7;
	}

	/**
	 * 屬性 def8的Getter方法.屬性名：自定義項8 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef8() {
		return this.def8;
	}

	/**
	 * 屬性def8的Setter方法.屬性名：自定義項8 創建日期:2019/5/5
	 * 
	 * @param newDef8
	 *            java.lang.String
	 */
	public void setDef8(String def8) {
		this.def8 = def8;
	}

	/**
	 * 屬性 def9的Getter方法.屬性名：自定義項9 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef9() {
		return this.def9;
	}

	/**
	 * 屬性def9的Setter方法.屬性名：自定義項9 創建日期:2019/5/5
	 * 
	 * @param newDef9
	 *            java.lang.String
	 */
	public void setDef9(String def9) {
		this.def9 = def9;
	}

	/**
	 * 屬性 def10的Getter方法.屬性名：自定義項10 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef10() {
		return this.def10;
	}

	/**
	 * 屬性def10的Setter方法.屬性名：自定義項10 創建日期:2019/5/5
	 * 
	 * @param newDef10
	 *            java.lang.String
	 */
	public void setDef10(String def10) {
		this.def10 = def10;
	}

	/**
	 * 屬性 def11的Getter方法.屬性名：自定義項11 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef11() {
		return this.def11;
	}

	/**
	 * 屬性def11的Setter方法.屬性名：自定義項11 創建日期:2019/5/5
	 * 
	 * @param newDef11
	 *            java.lang.String
	 */
	public void setDef11(String def11) {
		this.def11 = def11;
	}

	/**
	 * 屬性 def12的Getter方法.屬性名：自定義項12 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef12() {
		return this.def12;
	}

	/**
	 * 屬性def12的Setter方法.屬性名：自定義項12 創建日期:2019/5/5
	 * 
	 * @param newDef12
	 *            java.lang.String
	 */
	public void setDef12(String def12) {
		this.def12 = def12;
	}

	/**
	 * 屬性 def13的Getter方法.屬性名：自定義項13 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef13() {
		return this.def13;
	}

	/**
	 * 屬性def13的Setter方法.屬性名：自定義項13 創建日期:2019/5/5
	 * 
	 * @param newDef13
	 *            java.lang.String
	 */
	public void setDef13(String def13) {
		this.def13 = def13;
	}

	/**
	 * 屬性 def14的Getter方法.屬性名：自定義項14 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef14() {
		return this.def14;
	}

	/**
	 * 屬性def14的Setter方法.屬性名：自定義項14 創建日期:2019/5/5
	 * 
	 * @param newDef14
	 *            java.lang.String
	 */
	public void setDef14(String def14) {
		this.def14 = def14;
	}

	/**
	 * 屬性 def15的Getter方法.屬性名：自定義項15 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef15() {
		return this.def15;
	}

	/**
	 * 屬性def15的Setter方法.屬性名：自定義項15 創建日期:2019/5/5
	 * 
	 * @param newDef15
	 *            java.lang.String
	 */
	public void setDef15(String def15) {
		this.def15 = def15;
	}

	/**
	 * 屬性 def16的Getter方法.屬性名：自定義項16 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef16() {
		return this.def16;
	}

	/**
	 * 屬性def16的Setter方法.屬性名：自定義項16 創建日期:2019/5/5
	 * 
	 * @param newDef16
	 *            java.lang.String
	 */
	public void setDef16(String def16) {
		this.def16 = def16;
	}

	/**
	 * 屬性 def17的Getter方法.屬性名：自定義項17 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef17() {
		return this.def17;
	}

	/**
	 * 屬性def17的Setter方法.屬性名：自定義項17 創建日期:2019/5/5
	 * 
	 * @param newDef17
	 *            java.lang.String
	 */
	public void setDef17(String def17) {
		this.def17 = def17;
	}

	/**
	 * 屬性 def18的Getter方法.屬性名：自定義項18 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef18() {
		return this.def18;
	}

	/**
	 * 屬性def18的Setter方法.屬性名：自定義項18 創建日期:2019/5/5
	 * 
	 * @param newDef18
	 *            java.lang.String
	 */
	public void setDef18(String def18) {
		this.def18 = def18;
	}

	/**
	 * 屬性 def19的Getter方法.屬性名：自定義項19 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef19() {
		return this.def19;
	}

	/**
	 * 屬性def19的Setter方法.屬性名：自定義項19 創建日期:2019/5/5
	 * 
	 * @param newDef19
	 *            java.lang.String
	 */
	public void setDef19(String def19) {
		this.def19 = def19;
	}

	/**
	 * 屬性 def20的Getter方法.屬性名：自定義項20 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef20() {
		return this.def20;
	}

	/**
	 * 屬性def20的Setter方法.屬性名：自定義項20 創建日期:2019/5/5
	 * 
	 * @param newDef20
	 *            java.lang.String
	 */
	public void setDef20(String def20) {
		this.def20 = def20;
	}

	/**
	 * 屬性 def21的Getter方法.屬性名：自定義項21 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef21() {
		return this.def21;
	}

	/**
	 * 屬性def21的Setter方法.屬性名：自定義項21 創建日期:2019/5/5
	 * 
	 * @param newDef21
	 *            java.lang.String
	 */
	public void setDef21(String def21) {
		this.def21 = def21;
	}

	/**
	 * 屬性 def22的Getter方法.屬性名：自定義項22 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef22() {
		return this.def22;
	}

	/**
	 * 屬性def22的Setter方法.屬性名：自定義項22 創建日期:2019/5/5
	 * 
	 * @param newDef22
	 *            java.lang.String
	 */
	public void setDef22(String def22) {
		this.def22 = def22;
	}

	/**
	 * 屬性 def23的Getter方法.屬性名：自定義項23 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef23() {
		return this.def23;
	}

	/**
	 * 屬性def23的Setter方法.屬性名：自定義項23 創建日期:2019/5/5
	 * 
	 * @param newDef23
	 *            java.lang.String
	 */
	public void setDef23(String def23) {
		this.def23 = def23;
	}

	/**
	 * 屬性 def24的Getter方法.屬性名：自定義項24 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef24() {
		return this.def24;
	}

	/**
	 * 屬性def24的Setter方法.屬性名：自定義項24 創建日期:2019/5/5
	 * 
	 * @param newDef24
	 *            java.lang.String
	 */
	public void setDef24(String def24) {
		this.def24 = def24;
	}

	/**
	 * 屬性 def25的Getter方法.屬性名：自定義項25 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef25() {
		return this.def25;
	}

	/**
	 * 屬性def25的Setter方法.屬性名：自定義項25 創建日期:2019/5/5
	 * 
	 * @param newDef25
	 *            java.lang.String
	 */
	public void setDef25(String def25) {
		this.def25 = def25;
	}

	/**
	 * 屬性 def26的Getter方法.屬性名：自定義項26 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef26() {
		return this.def26;
	}

	/**
	 * 屬性def26的Setter方法.屬性名：自定義項26 創建日期:2019/5/5
	 * 
	 * @param newDef26
	 *            java.lang.String
	 */
	public void setDef26(String def26) {
		this.def26 = def26;
	}

	/**
	 * 屬性 def27的Getter方法.屬性名：自定義項27 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef27() {
		return this.def27;
	}

	/**
	 * 屬性def27的Setter方法.屬性名：自定義項27 創建日期:2019/5/5
	 * 
	 * @param newDef27
	 *            java.lang.String
	 */
	public void setDef27(String def27) {
		this.def27 = def27;
	}

	/**
	 * 屬性 def28的Getter方法.屬性名：自定義項28 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef28() {
		return this.def28;
	}

	/**
	 * 屬性def28的Setter方法.屬性名：自定義項28 創建日期:2019/5/5
	 * 
	 * @param newDef28
	 *            java.lang.String
	 */
	public void setDef28(String def28) {
		this.def28 = def28;
	}

	/**
	 * 屬性 def29的Getter方法.屬性名：自定義項29 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef29() {
		return this.def29;
	}

	/**
	 * 屬性def29的Setter方法.屬性名：自定義項29 創建日期:2019/5/5
	 * 
	 * @param newDef29
	 *            java.lang.String
	 */
	public void setDef29(String def29) {
		this.def29 = def29;
	}

	/**
	 * 屬性 def30的Getter方法.屬性名：自定義項30 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef30() {
		return this.def30;
	}

	/**
	 * 屬性def30的Setter方法.屬性名：自定義項30 創建日期:2019/5/5
	 * 
	 * @param newDef30
	 *            java.lang.String
	 */
	public void setDef30(String def30) {
		this.def30 = def30;
	}

	/**
	 * 屬性 def31的Getter方法.屬性名：自定義項31 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef31() {
		return this.def31;
	}

	/**
	 * 屬性def31的Setter方法.屬性名：自定義項31 創建日期:2019/5/5
	 * 
	 * @param newDef31
	 *            java.lang.String
	 */
	public void setDef31(String def31) {
		this.def31 = def31;
	}

	/**
	 * 屬性 def32的Getter方法.屬性名：自定義項32 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef32() {
		return this.def32;
	}

	/**
	 * 屬性def32的Setter方法.屬性名：自定義項32 創建日期:2019/5/5
	 * 
	 * @param newDef32
	 *            java.lang.String
	 */
	public void setDef32(String def32) {
		this.def32 = def32;
	}

	/**
	 * 屬性 def33的Getter方法.屬性名：自定義項33 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef33() {
		return this.def33;
	}

	/**
	 * 屬性def33的Setter方法.屬性名：自定義項33 創建日期:2019/5/5
	 * 
	 * @param newDef33
	 *            java.lang.String
	 */
	public void setDef33(String def33) {
		this.def33 = def33;
	}

	/**
	 * 屬性 def34的Getter方法.屬性名：自定義項34 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef34() {
		return this.def34;
	}

	/**
	 * 屬性def34的Setter方法.屬性名：自定義項34 創建日期:2019/5/5
	 * 
	 * @param newDef34
	 *            java.lang.String
	 */
	public void setDef34(String def34) {
		this.def34 = def34;
	}

	/**
	 * 屬性 def35的Getter方法.屬性名：自定義項35 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef35() {
		return this.def35;
	}

	/**
	 * 屬性def35的Setter方法.屬性名：自定義項35 創建日期:2019/5/5
	 * 
	 * @param newDef35
	 *            java.lang.String
	 */
	public void setDef35(String def35) {
		this.def35 = def35;
	}

	/**
	 * 屬性 def36的Getter方法.屬性名：自定義項36 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef36() {
		return this.def36;
	}

	/**
	 * 屬性def36的Setter方法.屬性名：自定義項36 創建日期:2019/5/5
	 * 
	 * @param newDef36
	 *            java.lang.String
	 */
	public void setDef36(String def36) {
		this.def36 = def36;
	}

	/**
	 * 屬性 def37的Getter方法.屬性名：自定義項37 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef37() {
		return this.def37;
	}

	/**
	 * 屬性def37的Setter方法.屬性名：自定義項37 創建日期:2019/5/5
	 * 
	 * @param newDef37
	 *            java.lang.String
	 */
	public void setDef37(String def37) {
		this.def37 = def37;
	}

	/**
	 * 屬性 def38的Getter方法.屬性名：自定義項38 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef38() {
		return this.def38;
	}

	/**
	 * 屬性def38的Setter方法.屬性名：自定義項38 創建日期:2019/5/5
	 * 
	 * @param newDef38
	 *            java.lang.String
	 */
	public void setDef38(String def38) {
		this.def38 = def38;
	}

	/**
	 * 屬性 def39的Getter方法.屬性名：自定義項39 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef39() {
		return this.def39;
	}

	/**
	 * 屬性def39的Setter方法.屬性名：自定義項39 創建日期:2019/5/5
	 * 
	 * @param newDef39
	 *            java.lang.String
	 */
	public void setDef39(String def39) {
		this.def39 = def39;
	}

	/**
	 * 屬性 def40的Getter方法.屬性名：自定義項40 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getDef40() {
		return this.def40;
	}

	/**
	 * 屬性def40的Setter方法.屬性名：自定義項40 創建日期:2019/5/5
	 * 
	 * @param newDef40
	 *            java.lang.String
	 */
	public void setDef40(String def40) {
		this.def40 = def40;
	}

	/**
	 * 屬性 生成時間戳的Getter方法.屬性名：時間戳 創建日期:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getTs() {
		return this.ts;
	}

	/**
	 * 屬性生成時間戳的Setter方法.屬性名：時間戳 創建日期:2019/5/5
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
