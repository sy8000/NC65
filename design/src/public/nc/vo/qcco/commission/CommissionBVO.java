package nc.vo.qcco.commission;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

/**
 * <b> 此處簡要描述此類功能 </b>
 * <p>
 * 此處添加累的描述信息
 * </p>
 * 創建日期:2019/4/8
 *
 * @author yonyouBQ
 * @version NCPrj ??
 */

public class CommissionBVO extends SuperVO {

    /**
     * 樣品行主鍵
     */
    public String pk_commission_b;
    /**
     * 行號
     */
    public String rowno;
    
    /**
     * uniqueid
     */
    public String uniqueid;
    /**
     * 產品系列
     */
    public String pk_productserial;
    /**
     * 企業標準
     */
    public String pk_enterprisestandard;
    /**
     * 規格型號
     */
    public String typeno;
    /**
     * 規格號
     */
    public String pk_productspec;
    /**
     * 結構類型
     */
    public String pk_structuretype;
    /**
     * 觸點類型
     */
    public String contacttype;
    /**
     * 樣品數量
     */
    public UFDouble quantity;
    /**
     * 製造商
     */
    public String manufacturer;
    /**
     * 觸點牌號
     */
    public String pk_contactbrand;
    /**
     * 觸點型號
     */
    public String contactmodel;
    /**
     * 溫度
     */
    public String productstage;
    /**
     * 樣品組別
     */
    public String pk_samplegroup;
    /**
     * 實驗前參數
     */
    public String analysisref;
    /**
     * 其他信息
     */
    public String otherinfo;

    /**
     * 自定义项1
     */
    public String def1;
    /**
     * 自定义项2
     */
    public String def2;
    /**
     * 自定义项3
     */
    public String def3;
    /**
     * 自定义项4
     */
    public String def4;
    /**
     * 自定义项5
     */
    public String def5;
    /**
     * 自定义项6
     */
    public String def6;
    /**
     * 自定义项7
     */
    public String def7;
    /**
     * 自定义项8
     */
    public String def8;
    /**
     * 自定义项9
     */
    public String def9;
    /**
     * 自定义项10
     */
    public String def10;
    /**
     * 自定义项11
     */
    public String def11;
    /**
     * 自定义项12
     */
    public String def12;
    /**
     * 自定义项13
     */
    public String def13;
    /**
     * 自定义项14
     */
    public String def14;
    /**
     * 自定义项15
     */
    public String def15;
    /**
     * 自定义项16
     */
    public String def16;
    /**
     * 自定义项17
     */
    public String def17;
    /**
     * 自定义项18
     */
    public String def18;
    /**
     * 自定义项19
     */
    public String def19;
    /**
     * 自定义项20
     */
    public String def20;

    /**
     * 上層單據主鍵
     */
    public String pk_commission_h;
    /**
     * 時間戳
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
     * 屬性 pk_commission_b的Getter方法.屬性名：樣品行主鍵 創建日期:2019/4/8
     *
     * @return java.lang.String
     */
    public String getPk_commission_b() {
        return this.pk_commission_b;
    }

    /**
     * 屬性pk_commission_b的Setter方法.屬性名：樣品行主鍵 創建日期:2019/4/8
     *
     * @param newPk_commission_b java.lang.String
     */
    public void setPk_commission_b(String pk_commission_b) {
        this.pk_commission_b = pk_commission_b;
    }

    /**
     * 屬性 rowno的Getter方法.屬性名：行號 創建日期:2019/4/8
     *
     * @return java.lang.String
     */
    public String getRowno() {
        return this.rowno;
    }

    /**
     * 屬性rowno的Setter方法.屬性名：行號 創建日期:2019/4/8
     *
     * @param newRowno java.lang.String
     */
    public void setRowno(String rowno) {
        this.rowno = rowno;
    }

    /**
     * 屬性 pk_productserial的Getter方法.屬性名：產品系列 創建日期:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_productserial() {
        return this.pk_productserial;
    }

    /**
     * 屬性pk_productserial的Setter方法.屬性名：產品系列 創建日期:2019/4/8
     *
     * @param newPk_productserial nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_productserial(String pk_productserial) {
        this.pk_productserial = pk_productserial;
    }

    /**
     * 屬性 pk_enterprisestandard的Getter方法.屬性名：企業標準 創建日期:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_enterprisestandard() {
        return this.pk_enterprisestandard;
    }

    /**
     * 屬性pk_enterprisestandard的Setter方法.屬性名：企業標準 創建日期:2019/4/8
     *
     * @param newPk_enterprisestandard nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_enterprisestandard(String pk_enterprisestandard) {
        this.pk_enterprisestandard = pk_enterprisestandard;
    }

    /**
     * 屬性 typeno的Getter方法.屬性名：規格型號 創建日期:2019/4/8
     *
     * @return java.lang.String
     */
    public String getTypeno() {
        return this.typeno;
    }

    /**
     * 屬性typeno的Setter方法.屬性名：規格型號 創建日期:2019/4/8
     *
     * @param newTypeno java.lang.String
     */
    public void setTypeno(String typeno) {
        this.typeno = typeno;
    }

    /**
     * 屬性 pk_productspec的Getter方法.屬性名：規格號 創建日期:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_productspec() {
        return this.pk_productspec;
    }

    /**
     * 屬性pk_productspec的Setter方法.屬性名：規格號 創建日期:2019/4/8
     *
     * @param newPk_productspec nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_productspec(String pk_productspec) {
        this.pk_productspec = pk_productspec;
    }

    /**
     * 屬性 pk_structuretype的Getter方法.屬性名：結構類型 創建日期:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_structuretype() {
        return this.pk_structuretype;
    }

    /**
     * 屬性pk_structuretype的Setter方法.屬性名：結構類型 創建日期:2019/4/8
     *
     * @param newPk_structuretype nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_structuretype(String pk_structuretype) {
        this.pk_structuretype = pk_structuretype;
    }

    /**
     * 屬性 contacttype的Getter方法.屬性名：觸點類型 創建日期:2019/4/8
     *
     * @return java.lang.String
     */
    public String getContacttype() {
        return this.contacttype;
    }

    /**
     * 屬性contacttype的Setter方法.屬性名：觸點類型 創建日期:2019/4/8
     *
     * @param newContacttype java.lang.String
     */
    public void setContacttype(String contacttype) {
        this.contacttype = contacttype;
    }

    /**
     * 屬性 quantity的Getter方法.屬性名：樣品數量 創建日期:2019/4/8
     *
     * @return nc.vo.pub.lang.UFDouble
     */
    public UFDouble getQuantity() {
        return this.quantity;
    }

    /**
     * 屬性quantity的Setter方法.屬性名：樣品數量 創建日期:2019/4/8
     *
     * @param newQuantity nc.vo.pub.lang.UFDouble
     */
    public void setQuantity(UFDouble quantity) {
        this.quantity = quantity;
    }

    /**
     * 屬性 manufacturer的Getter方法.屬性名：製造商 創建日期:2019/4/8
     *
     * @return java.lang.String
     */
    public String getManufacturer() {
        return this.manufacturer;
    }

    /**
     * 屬性manufacturer的Setter方法.屬性名：製造商 創建日期:2019/4/8
     *
     * @param newManufacturer java.lang.String
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * 屬性 pk_contactbrand的Getter方法.屬性名：觸點牌號 創建日期:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_contactbrand() {
        return this.pk_contactbrand;
    }

    /**
     * 屬性pk_contactbrand的Setter方法.屬性名：觸點牌號 創建日期:2019/4/8
     *
     * @param newPk_contactbrand nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_contactbrand(String pk_contactbrand) {
        this.pk_contactbrand = pk_contactbrand;
    }

    /**
     * 屬性 contactmodel的Getter方法.屬性名：觸點型號 創建日期:2019/4/8
     *
     * @return java.lang.String
     */
    public String getContactmodel() {
        return this.contactmodel;
    }

    /**
     * 屬性contactmodel的Setter方法.屬性名：觸點型號 創建日期:2019/4/8
     *
     * @param newContactmodel java.lang.String
     */
    public void setContactmodel(String contactmodel) {
        this.contactmodel = contactmodel;
    }

    /**
     * 屬性 productstage的Getter方法.屬性名：溫度 創建日期:2019/4/8
     *
     * @return java.lang.String
     */
    public String getProductstage() {
        return this.productstage;
    }

    /**
     * 屬性productstage的Setter方法.屬性名：溫度 創建日期:2019/4/8
     *
     * @param newProductstage java.lang.String
     */
    public void setProductstage(String productstage) {
        this.productstage = productstage;
    }

    /**
     * 屬性 pk_samplegroup的Getter方法.屬性名：樣品組別 創建日期:2019/4/8
     *
     * @return nc.vo.bd.defdoc.DefdocVO
     */
    public String getPk_samplegroup() {
        return this.pk_samplegroup;
    }

    /**
     * 屬性pk_samplegroup的Setter方法.屬性名：樣品組別 創建日期:2019/4/8
     *
     * @param newPk_samplegroup nc.vo.bd.defdoc.DefdocVO
     */
    public void setPk_samplegroup(String pk_samplegroup) {
        this.pk_samplegroup = pk_samplegroup;
    }

    /**
     * 屬性 analysisref的Getter方法.屬性名：實驗前參數 創建日期:2019/4/8
     *
     * @return java.lang.String
     */
    public String getAnalysisref() {
        return this.analysisref;
    }

    /**
     * 屬性analysisref的Setter方法.屬性名：實驗前參數 創建日期:2019/4/8
     *
     * @param newAnalysisref java.lang.String
     */
    public void setAnalysisref(String analysisref) {
        this.analysisref = analysisref;
    }

    /**
     * 屬性 otherinfo的Getter方法.屬性名：其他信息 創建日期:2019/4/8
     *
     * @return java.lang.String
     */
    public String getOtherinfo() {
        return this.otherinfo;
    }

    /**
     * 屬性otherinfo的Setter方法.屬性名：其他信息 創建日期:2019/4/8
     *
     * @param newOtherinfo java.lang.String
     */
    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
    }


    /**
     * 属性 def1的Getter方法.属性名：自定义项1
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef1() {
        return this.def1;
    }

    /**
     * 属性def1的Setter方法.属性名：自定义项1
     * 创建日期:2019-4-21
     *
     * @param newDef1 java.lang.String
     */
    public void setDef1(String def1) {
        this.def1 = def1;
    }

    /**
     * 属性 def2的Getter方法.属性名：自定义项2
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef2() {
        return this.def2;
    }

    /**
     * 属性def2的Setter方法.属性名：自定义项2
     * 创建日期:2019-4-21
     *
     * @param newDef2 java.lang.String
     */
    public void setDef2(String def2) {
        this.def2 = def2;
    }

    /**
     * 属性 def3的Getter方法.属性名：自定义项3
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef3() {
        return this.def3;
    }

    /**
     * 属性def3的Setter方法.属性名：自定义项3
     * 创建日期:2019-4-21
     *
     * @param newDef3 java.lang.String
     */
    public void setDef3(String def3) {
        this.def3 = def3;
    }

    /**
     * 属性 def4的Getter方法.属性名：自定义项4
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef4() {
        return this.def4;
    }

    /**
     * 属性def4的Setter方法.属性名：自定义项4
     * 创建日期:2019-4-21
     *
     * @param newDef4 java.lang.String
     */
    public void setDef4(String def4) {
        this.def4 = def4;
    }

    /**
     * 属性 def5的Getter方法.属性名：自定义项5
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef5() {
        return this.def5;
    }

    /**
     * 属性def5的Setter方法.属性名：自定义项5
     * 创建日期:2019-4-21
     *
     * @param newDef5 java.lang.String
     */
    public void setDef5(String def5) {
        this.def5 = def5;
    }

    /**
     * 属性 def6的Getter方法.属性名：自定义项6
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef6() {
        return this.def6;
    }

    /**
     * 属性def6的Setter方法.属性名：自定义项6
     * 创建日期:2019-4-21
     *
     * @param newDef6 java.lang.String
     */
    public void setDef6(String def6) {
        this.def6 = def6;
    }

    /**
     * 属性 def7的Getter方法.属性名：自定义项7
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef7() {
        return this.def7;
    }

    /**
     * 属性def7的Setter方法.属性名：自定义项7
     * 创建日期:2019-4-21
     *
     * @param newDef7 java.lang.String
     */
    public void setDef7(String def7) {
        this.def7 = def7;
    }

    /**
     * 属性 def8的Getter方法.属性名：自定义项8
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef8() {
        return this.def8;
    }

    /**
     * 属性def8的Setter方法.属性名：自定义项8
     * 创建日期:2019-4-21
     *
     * @param newDef8 java.lang.String
     */
    public void setDef8(String def8) {
        this.def8 = def8;
    }

    /**
     * 属性 def9的Getter方法.属性名：自定义项9
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef9() {
        return this.def9;
    }

    /**
     * 属性def9的Setter方法.属性名：自定义项9
     * 创建日期:2019-4-21
     *
     * @param newDef9 java.lang.String
     */
    public void setDef9(String def9) {
        this.def9 = def9;
    }

    /**
     * 属性 def10的Getter方法.属性名：自定义项10
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef10() {
        return this.def10;
    }

    /**
     * 属性def10的Setter方法.属性名：自定义项10
     * 创建日期:2019-4-21
     *
     * @param newDef10 java.lang.String
     */
    public void setDef10(String def10) {
        this.def10 = def10;
    }

    /**
     * 属性 def11的Getter方法.属性名：自定义项11
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef11() {
        return this.def11;
    }

    /**
     * 属性def11的Setter方法.属性名：自定义项11
     * 创建日期:2019-4-21
     *
     * @param newDef11 java.lang.String
     */
    public void setDef11(String def11) {
        this.def11 = def11;
    }

    /**
     * 属性 def12的Getter方法.属性名：自定义项12
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef12() {
        return this.def12;
    }

    /**
     * 属性def12的Setter方法.属性名：自定义项12
     * 创建日期:2019-4-21
     *
     * @param newDef12 java.lang.String
     */
    public void setDef12(String def12) {
        this.def12 = def12;
    }

    /**
     * 属性 def13的Getter方法.属性名：自定义项13
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef13() {
        return this.def13;
    }

    /**
     * 属性def13的Setter方法.属性名：自定义项13
     * 创建日期:2019-4-21
     *
     * @param newDef13 java.lang.String
     */
    public void setDef13(String def13) {
        this.def13 = def13;
    }

    /**
     * 属性 def14的Getter方法.属性名：自定义项14
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef14() {
        return this.def14;
    }

    /**
     * 属性def14的Setter方法.属性名：自定义项14
     * 创建日期:2019-4-21
     *
     * @param newDef14 java.lang.String
     */
    public void setDef14(String def14) {
        this.def14 = def14;
    }

    /**
     * 属性 def15的Getter方法.属性名：自定义项15
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef15() {
        return this.def15;
    }

    /**
     * 属性def15的Setter方法.属性名：自定义项15
     * 创建日期:2019-4-21
     *
     * @param newDef15 java.lang.String
     */
    public void setDef15(String def15) {
        this.def15 = def15;
    }

    /**
     * 属性 def16的Getter方法.属性名：自定义项16
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef16() {
        return this.def16;
    }

    /**
     * 属性def16的Setter方法.属性名：自定义项16
     * 创建日期:2019-4-21
     *
     * @param newDef16 java.lang.String
     */
    public void setDef16(String def16) {
        this.def16 = def16;
    }

    /**
     * 属性 def17的Getter方法.属性名：自定义项17
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef17() {
        return this.def17;
    }

    /**
     * 属性def17的Setter方法.属性名：自定义项17
     * 创建日期:2019-4-21
     *
     * @param newDef17 java.lang.String
     */
    public void setDef17(String def17) {
        this.def17 = def17;
    }

    /**
     * 属性 def18的Getter方法.属性名：自定义项18
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef18() {
        return this.def18;
    }

    /**
     * 属性def18的Setter方法.属性名：自定义项18
     * 创建日期:2019-4-21
     *
     * @param newDef18 java.lang.String
     */
    public void setDef18(String def18) {
        this.def18 = def18;
    }

    /**
     * 属性 def19的Getter方法.属性名：自定义项19
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef19() {
        return this.def19;
    }

    /**
     * 属性def19的Setter方法.属性名：自定义项19
     * 创建日期:2019-4-21
     *
     * @param newDef19 java.lang.String
     */
    public void setDef19(String def19) {
        this.def19 = def19;
    }

    /**
     * 属性 def20的Getter方法.属性名：自定义项20
     * 创建日期:2019-4-21
     *
     * @return java.lang.String
     */
    public String getDef20() {
        return this.def20;
    }

    /**
     * 属性def20的Setter方法.属性名：自定义项20
     * 创建日期:2019-4-21
     *
     * @param newDef20 java.lang.String
     */
    public void setDef20(String def20) {
        this.def20 = def20;
    }


    /**
     * 屬性 生成上層主鍵的Getter方法.屬性名：上層主鍵 創建日期:2019/4/8
     *
     * @return String
     */
    public String getPk_commission_h() {
        return this.pk_commission_h;
    }

    /**
     * 屬性生成上層主鍵的Setter方法.屬性名：上層主鍵 創建日期:2019/4/8
     *
     * @param newPk_commission_h String
     */
    public void setPk_commission_h(String pk_commission_h) {
        this.pk_commission_h = pk_commission_h;
    }

    /**
     * 屬性 生成時間戳的Getter方法.屬性名：時間戳 創建日期:2019/4/8
     *
     * @return nc.vo.pub.lang.UFDateTime
     */
    public UFDateTime getTs() {
        return this.ts;
    }

    /**
     * 屬性生成時間戳的Setter方法.屬性名：時間戳 創建日期:2019/4/8
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
