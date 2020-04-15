package nc.vo.qcco.commission;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFLiteralDate;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

/**
 * <b> 此處簡要描述此類功能 </b>
 * <p>
 * 此處添加累的描述信息
 * </p>
 * 創建日期:2019/2/25
 *
 * @author yonyouBQ
 * @version NCPrj ??
 */

public class CommissionHVO extends SuperVO {

    /**
     * 主表主鍵
     */
    public String pk_commission_h;
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
     * 最後修改時間
     */
    public UFDateTime lastmaketime;
    /**
     * 制單日期
     */
    public UFDate dmakedate;
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
     * 委託單類型
     */
    public String pk_commissiontype;
    /**
     * 委託單編碼前綴
     */
    public String codeprefix;
    /**
     * 委託單編碼
     */
    public String billno;
    /**
     * 委託單名稱
     */
    public String billname;
    /**
     * 委託單位
     */
    public String pk_owner;
    /**
     * 部門
     */
    public String pk_dept;
    /**
     * 付費單位
     */
    public String pk_payer;
    /**
     * 聯繫人
     */
    public String contract;
    /**
     * 电子郵件
     */
    public String email;
    /**
     * 聯繫電話
     */
    public String teleno;
    /**
     * 產品大類
     */
    public String pk_maincategory;
    /**
     * 二級分類
     */
    public String pk_subcategory;
    /**
     * 三級分類
     */
    public String pk_lastcategory;
    /**
     * 制單人
     */
    public String cuserid;
    /**
     * 報告格式
     */
    public String reportformat;
    /**
     * 報告語言
     */
    public String reportlang;
    /**
     * 主管審核發送郵件
     */
    public UFBoolean managersendflag;
    /**
     * 任務開始發送郵件
     */
    public UFBoolean taskbeginsendflag;
    /**
     * 任務結束髮送郵件
     */
    public UFBoolean taskendsendflag;
    /**
     * 報告簽發發送郵件
     */
    public UFBoolean reportsendflag;
    /**
     * 是否保存為模板
     */
    public UFBoolean savetotemplateflag;
    /**
     * 計費單郵件提醒
     */
    public UFBoolean receiptsendflag;
    /**
     * 報價單郵件提醒
     */
    public UFBoolean quotaionsendflag;
    /**
     * 測試目的
     */
    public String testaim;
    /**
     * 進度要求
     */
    public String progressneed;
    /**
     * 檢后樣品處理
     */
    public String sampledealtype;
    /**
     * 產品屬性
     */
    public String productproperty;
    /**
     * 客戶名稱
     */
    public String customername;
    /**
     * 客戶類型
     */
    public String customertype;
    /**
     * 測試需求
     */
    public String testrequirement;
    /**
     * 檢測性質
     */
    public String checkingproperty;
    /**
     * 生產產線
     */
    public String productline;
    /**
     * 生產批量
     */
    public String batchnumber;
    /**
     * 生產日期
     */
    public UFLiteralDate productdate;
    /**
     * 生產批號
     */
    public String batchserial;
    /**
     * 產品鑒定類型
     */
    public String identificationtype;
    /**
     * 認證類型
     */
    public String certificationtype;
    /**
     * 項目號
     */
    public String itemnumber;
    
    /**
     * 狀態
     */
    private String docstatus;

    /**
     *自定义项1
     */
    public String def1;
    /**
     *自定义项2
     */
    public String def2;
    /**
     *自定义项3
     */
    public String def3;
    /**
     *自定义项4
     */
    public String def4;
    /**
     *自定义项5
     */
    public String def5;
    /**
     *自定义项6
     */
    public String def6;
    /**
     *自定义项7
     */
    public String def7;
    /**
     *自定义项8
     */
    public String def8;
    /**
     *自定义项9
     */
    public String def9;
    /**
     *自定义项10
     */
    public String def10;
    /**
     *自定义项11
     */
    public String def11;
    /**
     *自定义项12
     */
    public String def12;
    /**
     *自定义项13
     */
    public String def13;
    /**
     *自定义项14
     */
    public String def14;
    /**
     *自定义项15
     */
    public String def15;
    /**
     *自定义项16
     */
    public String def16;
    /**
     *自定义项17
     */
    public String def17;
    /**
     *自定义项18
     */
    public String def18;
    /**
     *自定义项19
     */
    public String def19;
    /**
     *自定义项20
     */
    public String def20;
    /**
     *自定义项21
     */
    public String def21;
    /**
     *自定义项22
     */
    public String def22;
    /**
     *自定义项23
     */
    public String def23;
    /**
     *自定义项24
     */
    public String def24;
    /**
     *自定义项25
     */
    public String def25;
    /**
     *自定义项26
     */
    public String def26;
    /**
     *自定义项27
     */
    public String def27;
    /**
     *自定义项28
     */
    public String def28;
    /**
     *自定义项29
     */
    public String def29;
    /**
     *自定义项30
     */
    public String def30;
    /**
     *自定义项31
     */
    public String def31;
    /**
     *自定义项32
     */
    public String def32;
    /**
     *自定义项33
     */
    public String def33;
    /**
     *自定义项34
     */
    public String def34;
    /**
     *自定义项35
     */
    public String def35;
    /**
     *自定义项36
     */
    public String def36;
    /**
     *自定义项37
     */
    public String def37;
    /**
     *自定义项38
     */
    public String def38;
    /**
     *自定义项39
     */
    public String def39;
    /**
     *自定义项40
     */
    public String def40;
    /**
     * 時間戳
     */
    public UFDateTime ts;
    
    

    public String getDocstatus() {
		return docstatus;
	}

	public void setDocstatus(String docstatus) {
		this.docstatus = docstatus;
	}

	/**
     * 屬性 pk_commission_h的Getter方法.屬性名：主表主鍵 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getPk_commission_h() {
        return this.pk_commission_h;
    }

    /**
     * 屬性pk_commission_h的Setter方法.屬性名：主表主鍵 創建日期:2019/2/25
     *
     * @param newPk_commission_h
     *            java.lang.String
     */
    public void setPk_commission_h(String pk_commission_h) {
        this.pk_commission_h = pk_commission_h;
    }

    /**
     * 屬性 creator的Getter方法.屬性名：創建人 創建日期:2019/2/25
     *
     * @return nc.vo.sm.UserVO
     */
    public String getCreator() {
        return this.creator;
    }

    /**
     * 屬性creator的Setter方法.屬性名：創建人 創建日期:2019/2/25
     *
     * @param newCreator
     *            nc.vo.sm.UserVO
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 屬性 creationtime的Getter方法.屬性名：創建時間 創建日期:2019/2/25
     *
     * @return nc.vo.pub.lang.UFDateTime
     */
    public UFDateTime getCreationtime() {
        return this.creationtime;
    }

    /**
     * 屬性creationtime的Setter方法.屬性名：創建時間 創建日期:2019/2/25
     *
     * @param newCreationtime
     *            nc.vo.pub.lang.UFDateTime
     */
    public void setCreationtime(UFDateTime creationtime) {
        this.creationtime = creationtime;
    }

    /**
     * 屬性 modifier的Getter方法.屬性名：修改人 創建日期:2019/2/25
     *
     * @return nc.vo.sm.UserVO
     */
    public String getModifier() {
        return this.modifier;
    }

    /**
     * 屬性modifier的Setter方法.屬性名：修改人 創建日期:2019/2/25
     *
     * @param newModifier
     *            nc.vo.sm.UserVO
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 屬性 modifiedtime的Getter方法.屬性名：修改時間 創建日期:2019/2/25
     *
     * @return nc.vo.pub.lang.UFDateTime
     */
    public UFDateTime getModifiedtime() {
        return this.modifiedtime;
    }

    /**
     * 屬性modifiedtime的Setter方法.屬性名：修改時間 創建日期:2019/2/25
     *
     * @param newModifiedtime
     *            nc.vo.pub.lang.UFDateTime
     */
    public void setModifiedtime(UFDateTime modifiedtime) {
        this.modifiedtime = modifiedtime;
    }

    /**
     * 屬性 lastmaketime的Getter方法.屬性名：最後修改時間 創建日期:2019/2/25
     *
     * @return nc.vo.pub.lang.UFDateTime
     */
    public UFDateTime getLastmaketime() {
        return this.lastmaketime;
    }

    /**
     * 屬性lastmaketime的Setter方法.屬性名：最後修改時間 創建日期:2019/2/25
     *
     * @param newLastmaketime
     *            nc.vo.pub.lang.UFDateTime
     */
    public void setLastmaketime(UFDateTime lastmaketime) {
        this.lastmaketime = lastmaketime;
    }

    /**
     * 屬性 dmakedate的Getter方法.屬性名：制單日期 創建日期:2019/2/25
     *
     * @return nc.vo.pub.lang.UFDate
     */
    public UFDate getDmakedate() {
        return this.dmakedate;
    }

    /**
     * 屬性dmakedate的Setter方法.屬性名：制單日期 創建日期:2019/2/25
     *
     * @param newDmakedate
     *            nc.vo.pub.lang.UFDate
     */
    public void setDmakedate(UFDate dmakedate) {
        this.dmakedate = dmakedate;
    }

    /**
     * 屬性 pk_group的Getter方法.屬性名：集團 創建日期:2019/2/25
     *
     * @return nc.vo.org.GroupVO
     */
    public String getPk_group() {
        return this.pk_group;
    }

    /**
     * 屬性pk_group的Setter方法.屬性名：集團 創建日期:2019/2/25
     *
     * @param newPk_group
     *            nc.vo.org.GroupVO
     */
    public void setPk_group(String pk_group) {
        this.pk_group = pk_group;
    }

    /**
     * 屬性 pk_org的Getter方法.屬性名：組織 創建日期:2019/2/25
     *
     * @return nc.vo.org.OrgVO
     */
    public String getPk_org() {
        return this.pk_org;
    }

    /**
     * 屬性pk_org的Setter方法.屬性名：組織 創建日期:2019/2/25
     *
     * @param newPk_org
     *            nc.vo.org.OrgVO
     */
    public void setPk_org(String pk_org) {
        this.pk_org = pk_org;
    }

    /**
     * 屬性 pk_org_v的Getter方法.屬性名：組織版本 創建日期:2019/2/25
     *
     * @return nc.vo.vorg.OrgVersionVO
     */
    public String getPk_org_v() {
        return this.pk_org_v;
    }

    /**
     * 屬性pk_org_v的Setter方法.屬性名：組織版本 創建日期:2019/2/25
     *
     * @param newPk_org_v
     *            nc.vo.vorg.OrgVersionVO
     */
    public void setPk_org_v(String pk_org_v) {
        this.pk_org_v = pk_org_v;
    }

    /**
     * 屬性 pk_commissiontype的Getter方法.屬性名：委託單類型 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getPk_commissiontype() {
        return this.pk_commissiontype;
    }

    /**
     * 屬性pk_commissiontype的Setter方法.屬性名：委託單類型 創建日期:2019/2/25
     *
     * @param newPk_commissiontype
     *            java.lang.String
     */
    public void setPk_commissiontype(String pk_commissiontype) {
        this.pk_commissiontype = pk_commissiontype;
    }

    /**
     * 屬性 codeprefix的Getter方法.屬性名：委託單編碼前綴 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getCodeprefix() {
        return this.codeprefix;
    }

    /**
     * 屬性codeprefix的Setter方法.屬性名：委託單編碼前綴 創建日期:2019/2/25
     *
     * @param newCodeprefix
     *            java.lang.String
     */
    public void setCodeprefix(String codeprefix) {
        this.codeprefix = codeprefix;
    }

    /**
     * 屬性 billno的Getter方法.屬性名：委託單編碼 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getBillno() {
        return this.billno;
    }

    /**
     * 屬性billno的Setter方法.屬性名：委託單編碼 創建日期:2019/2/25
     *
     * @param newBillno
     *            java.lang.String
     */
    public void setBillno(String billno) {
        this.billno = billno;
    }

    /**
     * 屬性 billname的Getter方法.屬性名：委託單名稱 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getBillname() {
        return this.billname;
    }

    /**
     * 屬性billname的Setter方法.屬性名：委託單名稱 創建日期:2019/2/25
     *
     * @param newBillname
     *            java.lang.String
     */
    public void setBillname(String billname) {
        this.billname = billname;
    }

    /**
     * 屬性 pk_owner的Getter方法.屬性名：委託單位 創建日期:2019/2/25
     *
     * @return nc.vo.org.OrgVO
     */
    public String getPk_owner() {
        return this.pk_owner;
    }

    /**
     * 屬性pk_owner的Setter方法.屬性名：委託單位 創建日期:2019/2/25
     *
     * @param newPk_owner
     *            nc.vo.org.OrgVO
     */
    public void setPk_owner(String pk_owner) {
        this.pk_owner = pk_owner;
    }

    /**
     * 屬性 pk_dept的Getter方法.屬性名：部門 創建日期:2019/2/25
     *
     * @return nc.vo.org.DeptVO
     */
    public String getPk_dept() {
        return this.pk_dept;
    }

    /**
     * 屬性pk_dept的Setter方法.屬性名：部門 創建日期:2019/2/25
     *
     * @param newPk_dept
     *            nc.vo.org.DeptVO
     */
    public void setPk_dept(String pk_dept) {
        this.pk_dept = pk_dept;
    }

    /**
     * 屬性 pk_payer的Getter方法.屬性名：付費單位 創建日期:2019/2/25
     *
     * @return nc.vo.org.OrgVO
     */
    public String getPk_payer() {
        return this.pk_payer;
    }

    /**
     * 屬性pk_payer的Setter方法.屬性名：付費單位 創建日期:2019/2/25
     *
     * @param newPk_payer
     *            nc.vo.org.OrgVO
     */
    public void setPk_payer(String pk_payer) {
        this.pk_payer = pk_payer;
    }

    /**
     * 屬性 contract的Getter方法.屬性名：聯繫人 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getContract() {
        return this.contract;
    }

    /**
     * 屬性contract的Setter方法.屬性名：聯繫人 創建日期:2019/2/25
     *
     * @param newContract
     *            java.lang.String
     */
    public void setContract(String contract) {
        this.contract = contract;
    }

    /**
     * 屬性 email的Getter方法.屬性名：电子郵件 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * 屬性email的Setter方法.屬性名：电子郵件 創建日期:2019/2/25
     *
     * @param newEmail
     *            java.lang.String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 屬性 teleno的Getter方法.屬性名：聯繫電話 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getTeleno() {
        return this.teleno;
    }

    /**
     * 屬性teleno的Setter方法.屬性名：聯繫電話 創建日期:2019/2/25
     *
     * @param newTeleno
     *            java.lang.String
     */
    public void setTeleno(String teleno) {
        this.teleno = teleno;
    }

    /**
     * 屬性 pk_maincategory的Getter方法.屬性名：產品大類 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getPk_maincategory() {
        return this.pk_maincategory;
    }

    /**
     * 屬性pk_maincategory的Setter方法.屬性名：產品大類 創建日期:2019/2/25
     *
     * @param newPk_maincategory
     *            java.lang.String
     */
    public void setPk_maincategory(String pk_maincategory) {
        this.pk_maincategory = pk_maincategory;
    }

    /**
     * 屬性 pk_subcategory的Getter方法.屬性名：二級分類 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getPk_subcategory() {
        return this.pk_subcategory;
    }

    /**
     * 屬性pk_subcategory的Setter方法.屬性名：二級分類 創建日期:2019/2/25
     *
     * @param newPk_subcategory
     *            java.lang.String
     */
    public void setPk_subcategory(String pk_subcategory) {
        this.pk_subcategory = pk_subcategory;
    }

    /**
     * 屬性 pk_lastcategory的Getter方法.屬性名：三級分類 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getPk_lastcategory() {
        return this.pk_lastcategory;
    }

    /**
     * 屬性pk_lastcategory的Setter方法.屬性名：三級分類 創建日期:2019/2/25
     *
     * @param newPk_lastcategory
     *            java.lang.String
     */
    public void setPk_lastcategory(String pk_lastcategory) {
        this.pk_lastcategory = pk_lastcategory;
    }

    /**
     * 屬性 cuserid的Getter方法.屬性名：制單人 創建日期:2019/2/25
     *
     * @return nc.vo.bd.psn.PsndocVO
     */
    public String getCuserid() {
        return this.cuserid;
    }

    /**
     * 屬性cuserid的Setter方法.屬性名：制單人 創建日期:2019/2/25
     *
     * @param newCuserid
     *            nc.vo.bd.psn.PsndocVO
     */
    public void setCuserid(String cuserid) {
        this.cuserid = cuserid;
    }

    /**
     * 屬性 reportformat的Getter方法.屬性名：報告格式 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getReportformat() {
        return this.reportformat;
    }

    /**
     * 屬性reportformat的Setter方法.屬性名：報告格式 創建日期:2019/2/25
     *
     * @param newReportformat
     *            java.lang.String
     */
    public void setReportformat(String reportformat) {
        this.reportformat = reportformat;
    }

    /**
     * 屬性 reportlang的Getter方法.屬性名：報告語言 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getReportlang() {
        return this.reportlang;
    }

    /**
     * 屬性reportlang的Setter方法.屬性名：報告語言 創建日期:2019/2/25
     *
     * @param newReportlang
     *            java.lang.String
     */
    public void setReportlang(String reportlang) {
        this.reportlang = reportlang;
    }

    /**
     * 屬性 managersendflag的Getter方法.屬性名：主管審核發送郵件 創建日期:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getManagersendflag() {
        return this.managersendflag;
    }

    /**
     * 屬性managersendflag的Setter方法.屬性名：主管審核發送郵件 創建日期:2019/2/25
     *
     * @param newManagersendflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setManagersendflag(UFBoolean managersendflag) {
        this.managersendflag = managersendflag;
    }

    /**
     * 屬性 taskbeginsendflag的Getter方法.屬性名：任務開始發送郵件 創建日期:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getTaskbeginsendflag() {
        return this.taskbeginsendflag;
    }

    /**
     * 屬性taskbeginsendflag的Setter方法.屬性名：任務開始發送郵件 創建日期:2019/2/25
     *
     * @param newTaskbeginsendflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setTaskbeginsendflag(UFBoolean taskbeginsendflag) {
        this.taskbeginsendflag = taskbeginsendflag;
    }

    /**
     * 屬性 taskendsendflag的Getter方法.屬性名：任務結束髮送郵件 創建日期:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getTaskendsendflag() {
        return this.taskendsendflag;
    }

    /**
     * 屬性taskendsendflag的Setter方法.屬性名：任務結束髮送郵件 創建日期:2019/2/25
     *
     * @param newTaskendsendflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setTaskendsendflag(UFBoolean taskendsendflag) {
        this.taskendsendflag = taskendsendflag;
    }

    /**
     * 屬性 reportsendflag的Getter方法.屬性名：報告簽發發送郵件 創建日期:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getReportsendflag() {
        return this.reportsendflag;
    }

    /**
     * 屬性reportsendflag的Setter方法.屬性名：報告簽發發送郵件 創建日期:2019/2/25
     *
     * @param newReportsendflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setReportsendflag(UFBoolean reportsendflag) {
        this.reportsendflag = reportsendflag;
    }

    /**
     * 屬性 savetotemplateflag的Getter方法.屬性名：是否保存為模板 創建日期:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getSavetotemplateflag() {
        return this.savetotemplateflag;
    }

    /**
     * 屬性savetotemplateflag的Setter方法.屬性名：是否保存為模板 創建日期:2019/2/25
     *
     * @param newSavetotemplateflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setSavetotemplateflag(UFBoolean savetotemplateflag) {
        this.savetotemplateflag = savetotemplateflag;
    }

    /**
     * 屬性 receiptsendflag的Getter方法.屬性名：計費單郵件提醒 創建日期:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getReceiptsendflag() {
        return this.receiptsendflag;
    }

    /**
     * 屬性receiptsendflag的Setter方法.屬性名：計費單郵件提醒 創建日期:2019/2/25
     *
     * @param newReceiptsendflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setReceiptsendflag(UFBoolean receiptsendflag) {
        this.receiptsendflag = receiptsendflag;
    }

    /**
     * 屬性 quotaionsendflag的Getter方法.屬性名：報價單郵件提醒 創建日期:2019/2/25
     *
     * @return nc.vo.pub.lang.UFUFBoolean
     */
    public UFBoolean getQuotaionsendflag() {
        return this.quotaionsendflag;
    }

    /**
     * 屬性quotaionsendflag的Setter方法.屬性名：報價單郵件提醒 創建日期:2019/2/25
     *
     * @param newQuotaionsendflag
     *            nc.vo.pub.lang.UFUFBoolean
     */
    public void setQuotaionsendflag(UFBoolean quotaionsendflag) {
        this.quotaionsendflag = quotaionsendflag;
    }

    /**
     * 屬性 testaim的Getter方法.屬性名：測試目的 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getTestaim() {
        return this.testaim;
    }

    /**
     * 屬性testaim的Setter方法.屬性名：測試目的 創建日期:2019/2/25
     *
     * @param newTestaim
     *            java.lang.String
     */
    public void setTestaim(String testaim) {
        this.testaim = testaim;
    }

    /**
     * 屬性 progressneed的Getter方法.屬性名：進度要求 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getProgressneed() {
        return this.progressneed;
    }

    /**
     * 屬性progressneed的Setter方法.屬性名：進度要求 創建日期:2019/2/25
     *
     * @param newProgressneed
     *            java.lang.String
     */
    public void setProgressneed(String progressneed) {
        this.progressneed = progressneed;
    }

    /**
     * 屬性 sampledealtype的Getter方法.屬性名：檢后樣品處理 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getSampledealtype() {
        return this.sampledealtype;
    }

    /**
     * 屬性sampledealtype的Setter方法.屬性名：檢后樣品處理 創建日期:2019/2/25
     *
     * @param newSampledealtype
     *            java.lang.String
     */
    public void setSampledealtype(String sampledealtype) {
        this.sampledealtype = sampledealtype;
    }

    /**
     * 屬性 productproperty的Getter方法.屬性名：產品屬性 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getProductproperty() {
        return this.productproperty;
    }

    /**
     * 屬性productproperty的Setter方法.屬性名：產品屬性 創建日期:2019/2/25
     *
     * @param newProductproperty
     *            java.lang.String
     */
    public void setProductproperty(String productproperty) {
        this.productproperty = productproperty;
    }

    /**
     * 屬性 customername的Getter方法.屬性名：客戶名稱 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getCustomername() {
        return this.customername;
    }

    /**
     * 屬性customername的Setter方法.屬性名：客戶名稱 創建日期:2019/2/25
     *
     * @param newCustomername
     *            java.lang.String
     */
    public void setCustomername(String customername) {
        this.customername = customername;
    }

    /**
     * 屬性 customertype的Getter方法.屬性名：客戶類型 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getCustomertype() {
        return this.customertype;
    }

    /**
     * 屬性customertype的Setter方法.屬性名：客戶類型 創建日期:2019/2/25
     *
     * @param newCustomertype
     *            java.lang.String
     */
    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    /**
     * 屬性 testrequirement的Getter方法.屬性名：測試需求 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getTestrequirement() {
        return this.testrequirement;
    }

    /**
     * 屬性testrequirement的Setter方法.屬性名：測試需求 創建日期:2019/2/25
     *
     * @param newTestrequirement
     *            java.lang.String
     */
    public void setTestrequirement(String testrequirement) {
        this.testrequirement = testrequirement;
    }

    /**
     * 屬性 checkingproperty的Getter方法.屬性名：檢測性質 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getCheckingproperty() {
        return this.checkingproperty;
    }

    /**
     * 屬性checkingproperty的Setter方法.屬性名：檢測性質 創建日期:2019/2/25
     *
     * @param newCheckingproperty
     *            java.lang.String
     */
    public void setCheckingproperty(String checkingproperty) {
        this.checkingproperty = checkingproperty;
    }

    /**
     * 屬性 productline的Getter方法.屬性名：生產產線 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getProductline() {
        return this.productline;
    }

    /**
     * 屬性productline的Setter方法.屬性名：生產產線 創建日期:2019/2/25
     *
     * @param newProductline
     *            java.lang.String
     */
    public void setProductline(String productline) {
        this.productline = productline;
    }

    /**
     * 屬性 batchnumber的Getter方法.屬性名：生產批量 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getBatchnumber() {
        return this.batchnumber;
    }

    /**
     * 屬性batchnumber的Setter方法.屬性名：生產批量 創建日期:2019/2/25
     *
     * @param newBatchnumber
     *            java.lang.String
     */
    public void setBatchnumber(String batchnumber) {
        this.batchnumber = batchnumber;
    }

    /**
     * 屬性 productdate的Getter方法.屬性名：生產日期 創建日期:2019/2/25
     *
     * @return nc.vo.pub.lang.UFLiteralDate
     */
    public UFLiteralDate getProductdate() {
        return this.productdate;
    }

    /**
     * 屬性productdate的Setter方法.屬性名：生產日期 創建日期:2019/2/25
     *
     * @param newProductdate
     *            nc.vo.pub.lang.UFLiteralDate
     */
    public void setProductdate(UFLiteralDate productdate) {
        this.productdate = productdate;
    }

    /**
     * 屬性 batchserial的Getter方法.屬性名：生產批號 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getBatchserial() {
        return this.batchserial;
    }

    /**
     * 屬性batchserial的Setter方法.屬性名：生產批號 創建日期:2019/2/25
     *
     * @param newBatchserial
     *            java.lang.String
     */
    public void setBatchserial(String batchserial) {
        this.batchserial = batchserial;
    }

    /**
     * 屬性 identificationtype的Getter方法.屬性名：產品鑒定類型 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getIdentificationtype() {
        return this.identificationtype;
    }

    /**
     * 屬性identificationtype的Setter方法.屬性名：產品鑒定類型 創建日期:2019/2/25
     *
     * @param newIdentificationtype
     *            java.lang.String
     */
    public void setIdentificationtype(String identificationtype) {
        this.identificationtype = identificationtype;
    }

    /**
     * 屬性 certificationtype的Getter方法.屬性名：認證類型 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getCertificationtype() {
        return this.certificationtype;
    }

    /**
     * 屬性certificationtype的Setter方法.屬性名：認證類型 創建日期:2019/2/25
     *
     * @param newCertificationtype
     *            java.lang.String
     */
    public void setCertificationtype(String certificationtype) {
        this.certificationtype = certificationtype;
    }

    /**
     * 屬性 itemnumber的Getter方法.屬性名：項目號 創建日期:2019/2/25
     *
     * @return java.lang.String
     */
    public String getItemnumber() {
        return this.itemnumber;
    }

    /**
     * 屬性itemnumber的Setter方法.屬性名：項目號 創建日期:2019/2/25
     *
     * @param newItemnumber
     *            java.lang.String
     */
    public void setItemnumber(String itemnumber) {
        this.itemnumber = itemnumber;
    }


    /**
     * 属性 def1的Getter方法.属性名：自定义项1
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef1() {
        return this.def1;
    }

    /**
     * 属性def1的Setter方法.属性名：自定义项1
     * 创建日期:2019-4-21
     * @param newDef1 java.lang.String
     */
    public void setDef1 ( String def1) {
        this.def1=def1;
    }

    /**
     * 属性 def2的Getter方法.属性名：自定义项2
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef2() {
        return this.def2;
    }

    /**
     * 属性def2的Setter方法.属性名：自定义项2
     * 创建日期:2019-4-21
     * @param newDef2 java.lang.String
     */
    public void setDef2 ( String def2) {
        this.def2=def2;
    }

    /**
     * 属性 def3的Getter方法.属性名：自定义项3
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef3() {
        return this.def3;
    }

    /**
     * 属性def3的Setter方法.属性名：自定义项3
     * 创建日期:2019-4-21
     * @param newDef3 java.lang.String
     */
    public void setDef3 ( String def3) {
        this.def3=def3;
    }

    /**
     * 属性 def4的Getter方法.属性名：自定义项4
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef4() {
        return this.def4;
    }

    /**
     * 属性def4的Setter方法.属性名：自定义项4
     * 创建日期:2019-4-21
     * @param newDef4 java.lang.String
     */
    public void setDef4 ( String def4) {
        this.def4=def4;
    }

    /**
     * 属性 def5的Getter方法.属性名：自定义项5
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef5() {
        return this.def5;
    }

    /**
     * 属性def5的Setter方法.属性名：自定义项5
     * 创建日期:2019-4-21
     * @param newDef5 java.lang.String
     */
    public void setDef5 ( String def5) {
        this.def5=def5;
    }

    /**
     * 属性 def6的Getter方法.属性名：自定义项6
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef6() {
        return this.def6;
    }

    /**
     * 属性def6的Setter方法.属性名：自定义项6
     * 创建日期:2019-4-21
     * @param newDef6 java.lang.String
     */
    public void setDef6 ( String def6) {
        this.def6=def6;
    }

    /**
     * 属性 def7的Getter方法.属性名：自定义项7
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef7() {
        return this.def7;
    }

    /**
     * 属性def7的Setter方法.属性名：自定义项7
     * 创建日期:2019-4-21
     * @param newDef7 java.lang.String
     */
    public void setDef7 ( String def7) {
        this.def7=def7;
    }

    /**
     * 属性 def8的Getter方法.属性名：自定义项8
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef8() {
        return this.def8;
    }

    /**
     * 属性def8的Setter方法.属性名：自定义项8
     * 创建日期:2019-4-21
     * @param newDef8 java.lang.String
     */
    public void setDef8 ( String def8) {
        this.def8=def8;
    }

    /**
     * 属性 def9的Getter方法.属性名：自定义项9
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef9() {
        return this.def9;
    }

    /**
     * 属性def9的Setter方法.属性名：自定义项9
     * 创建日期:2019-4-21
     * @param newDef9 java.lang.String
     */
    public void setDef9 ( String def9) {
        this.def9=def9;
    }

    /**
     * 属性 def10的Getter方法.属性名：自定义项10
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef10() {
        return this.def10;
    }

    /**
     * 属性def10的Setter方法.属性名：自定义项10
     * 创建日期:2019-4-21
     * @param newDef10 java.lang.String
     */
    public void setDef10 ( String def10) {
        this.def10=def10;
    }

    /**
     * 属性 def11的Getter方法.属性名：自定义项11
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef11() {
        return this.def11;
    }

    /**
     * 属性def11的Setter方法.属性名：自定义项11
     * 创建日期:2019-4-21
     * @param newDef11 java.lang.String
     */
    public void setDef11 ( String def11) {
        this.def11=def11;
    }

    /**
     * 属性 def12的Getter方法.属性名：自定义项12
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef12() {
        return this.def12;
    }

    /**
     * 属性def12的Setter方法.属性名：自定义项12
     * 创建日期:2019-4-21
     * @param newDef12 java.lang.String
     */
    public void setDef12 ( String def12) {
        this.def12=def12;
    }

    /**
     * 属性 def13的Getter方法.属性名：自定义项13
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef13() {
        return this.def13;
    }

    /**
     * 属性def13的Setter方法.属性名：自定义项13
     * 创建日期:2019-4-21
     * @param newDef13 java.lang.String
     */
    public void setDef13 ( String def13) {
        this.def13=def13;
    }

    /**
     * 属性 def14的Getter方法.属性名：自定义项14
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef14() {
        return this.def14;
    }

    /**
     * 属性def14的Setter方法.属性名：自定义项14
     * 创建日期:2019-4-21
     * @param newDef14 java.lang.String
     */
    public void setDef14 ( String def14) {
        this.def14=def14;
    }

    /**
     * 属性 def15的Getter方法.属性名：自定义项15
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef15() {
        return this.def15;
    }

    /**
     * 属性def15的Setter方法.属性名：自定义项15
     * 创建日期:2019-4-21
     * @param newDef15 java.lang.String
     */
    public void setDef15 ( String def15) {
        this.def15=def15;
    }

    /**
     * 属性 def16的Getter方法.属性名：自定义项16
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef16() {
        return this.def16;
    }

    /**
     * 属性def16的Setter方法.属性名：自定义项16
     * 创建日期:2019-4-21
     * @param newDef16 java.lang.String
     */
    public void setDef16 ( String def16) {
        this.def16=def16;
    }

    /**
     * 属性 def17的Getter方法.属性名：自定义项17
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef17() {
        return this.def17;
    }

    /**
     * 属性def17的Setter方法.属性名：自定义项17
     * 创建日期:2019-4-21
     * @param newDef17 java.lang.String
     */
    public void setDef17 ( String def17) {
        this.def17=def17;
    }

    /**
     * 属性 def18的Getter方法.属性名：自定义项18
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef18() {
        return this.def18;
    }

    /**
     * 属性def18的Setter方法.属性名：自定义项18
     * 创建日期:2019-4-21
     * @param newDef18 java.lang.String
     */
    public void setDef18 ( String def18) {
        this.def18=def18;
    }

    /**
     * 属性 def19的Getter方法.属性名：自定义项19
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef19() {
        return this.def19;
    }

    /**
     * 属性def19的Setter方法.属性名：自定义项19
     * 创建日期:2019-4-21
     * @param newDef19 java.lang.String
     */
    public void setDef19 ( String def19) {
        this.def19=def19;
    }

    /**
     * 属性 def20的Getter方法.属性名：自定义项20
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef20() {
        return this.def20;
    }

    /**
     * 属性def20的Setter方法.属性名：自定义项20
     * 创建日期:2019-4-21
     * @param newDef20 java.lang.String
     */
    public void setDef20 ( String def20) {
        this.def20=def20;
    }

    /**
     * 属性 def21的Getter方法.属性名：自定义项21
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef21() {
        return this.def21;
    }

    /**
     * 属性def21的Setter方法.属性名：自定义项21
     * 创建日期:2019-4-21
     * @param newDef21 java.lang.String
     */
    public void setDef21 ( String def21) {
        this.def21=def21;
    }

    /**
     * 属性 def22的Getter方法.属性名：自定义项22
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef22() {
        return this.def22;
    }

    /**
     * 属性def22的Setter方法.属性名：自定义项22
     * 创建日期:2019-4-21
     * @param newDef22 java.lang.String
     */
    public void setDef22 ( String def22) {
        this.def22=def22;
    }

    /**
     * 属性 def23的Getter方法.属性名：自定义项23
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef23() {
        return this.def23;
    }

    /**
     * 属性def23的Setter方法.属性名：自定义项23
     * 创建日期:2019-4-21
     * @param newDef23 java.lang.String
     */
    public void setDef23 ( String def23) {
        this.def23=def23;
    }

    /**
     * 属性 def24的Getter方法.属性名：自定义项24
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef24() {
        return this.def24;
    }

    /**
     * 属性def24的Setter方法.属性名：自定义项24
     * 创建日期:2019-4-21
     * @param newDef24 java.lang.String
     */
    public void setDef24 ( String def24) {
        this.def24=def24;
    }

    /**
     * 属性 def25的Getter方法.属性名：自定义项25
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef25() {
        return this.def25;
    }

    /**
     * 属性def25的Setter方法.属性名：自定义项25
     * 创建日期:2019-4-21
     * @param newDef25 java.lang.String
     */
    public void setDef25 ( String def25) {
        this.def25=def25;
    }

    /**
     * 属性 def26的Getter方法.属性名：自定义项26
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef26() {
        return this.def26;
    }

    /**
     * 属性def26的Setter方法.属性名：自定义项26
     * 创建日期:2019-4-21
     * @param newDef26 java.lang.String
     */
    public void setDef26 ( String def26) {
        this.def26=def26;
    }

    /**
     * 属性 def27的Getter方法.属性名：自定义项27
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef27() {
        return this.def27;
    }

    /**
     * 属性def27的Setter方法.属性名：自定义项27
     * 创建日期:2019-4-21
     * @param newDef27 java.lang.String
     */
    public void setDef27 ( String def27) {
        this.def27=def27;
    }

    /**
     * 属性 def28的Getter方法.属性名：自定义项28
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef28() {
        return this.def28;
    }

    /**
     * 属性def28的Setter方法.属性名：自定义项28
     * 创建日期:2019-4-21
     * @param newDef28 java.lang.String
     */
    public void setDef28 ( String def28) {
        this.def28=def28;
    }

    /**
     * 属性 def29的Getter方法.属性名：自定义项29
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef29() {
        return this.def29;
    }

    /**
     * 属性def29的Setter方法.属性名：自定义项29
     * 创建日期:2019-4-21
     * @param newDef29 java.lang.String
     */
    public void setDef29 ( String def29) {
        this.def29=def29;
    }

    /**
     * 属性 def30的Getter方法.属性名：自定义项30
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef30() {
        return this.def30;
    }

    /**
     * 属性def30的Setter方法.属性名：自定义项30
     * 创建日期:2019-4-21
     * @param newDef30 java.lang.String
     */
    public void setDef30 ( String def30) {
        this.def30=def30;
    }

    /**
     * 属性 def31的Getter方法.属性名：自定义项31
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef31() {
        return this.def31;
    }

    /**
     * 属性def31的Setter方法.属性名：自定义项31
     * 创建日期:2019-4-21
     * @param newDef31 java.lang.String
     */
    public void setDef31 ( String def31) {
        this.def31=def31;
    }

    /**
     * 属性 def32的Getter方法.属性名：自定义项32
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef32() {
        return this.def32;
    }

    /**
     * 属性def32的Setter方法.属性名：自定义项32
     * 创建日期:2019-4-21
     * @param newDef32 java.lang.String
     */
    public void setDef32 ( String def32) {
        this.def32=def32;
    }

    /**
     * 属性 def33的Getter方法.属性名：自定义项33
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef33() {
        return this.def33;
    }

    /**
     * 属性def33的Setter方法.属性名：自定义项33
     * 创建日期:2019-4-21
     * @param newDef33 java.lang.String
     */
    public void setDef33 ( String def33) {
        this.def33=def33;
    }

    /**
     * 属性 def34的Getter方法.属性名：自定义项34
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef34() {
        return this.def34;
    }

    /**
     * 属性def34的Setter方法.属性名：自定义项34
     * 创建日期:2019-4-21
     * @param newDef34 java.lang.String
     */
    public void setDef34 ( String def34) {
        this.def34=def34;
    }

    /**
     * 属性 def35的Getter方法.属性名：自定义项35
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef35() {
        return this.def35;
    }

    /**
     * 属性def35的Setter方法.属性名：自定义项35
     * 创建日期:2019-4-21
     * @param newDef35 java.lang.String
     */
    public void setDef35 ( String def35) {
        this.def35=def35;
    }

    /**
     * 属性 def36的Getter方法.属性名：自定义项36
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef36() {
        return this.def36;
    }

    /**
     * 属性def36的Setter方法.属性名：自定义项36
     * 创建日期:2019-4-21
     * @param newDef36 java.lang.String
     */
    public void setDef36 ( String def36) {
        this.def36=def36;
    }

    /**
     * 属性 def37的Getter方法.属性名：自定义项37
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef37() {
        return this.def37;
    }

    /**
     * 属性def37的Setter方法.属性名：自定义项37
     * 创建日期:2019-4-21
     * @param newDef37 java.lang.String
     */
    public void setDef37 ( String def37) {
        this.def37=def37;
    }

    /**
     * 属性 def38的Getter方法.属性名：自定义项38
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef38() {
        return this.def38;
    }

    /**
     * 属性def38的Setter方法.属性名：自定义项38
     * 创建日期:2019-4-21
     * @param newDef38 java.lang.String
     */
    public void setDef38 ( String def38) {
        this.def38=def38;
    }

    /**
     * 属性 def39的Getter方法.属性名：自定义项39
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef39() {
        return this.def39;
    }

    /**
     * 属性def39的Setter方法.属性名：自定义项39
     * 创建日期:2019-4-21
     * @param newDef39 java.lang.String
     */
    public void setDef39 ( String def39) {
        this.def39=def39;
    }

    /**
     * 属性 def40的Getter方法.属性名：自定义项40
     *  创建日期:2019-4-21
     * @return java.lang.String
     */
    public String getDef40() {
        return this.def40;
    }

    /**
     * 属性def40的Setter方法.属性名：自定义项40
     * 创建日期:2019-4-21
     * @param newDef40 java.lang.String
     */
    public void setDef40 ( String def40) {
        this.def40=def40;
    }


    /**
     * 屬性 生成時間戳的Getter方法.屬性名：時間戳 創建日期:2019/2/25
     *
     * @return nc.vo.pub.lang.UFDateTime
     */
    public UFDateTime getTs() {
        return this.ts;
    }

    /**
     * 屬性生成時間戳的Setter方法.屬性名：時間戳 創建日期:2019/2/25
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
