package nc.vo.qcco.task;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
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

public class TaskRVO extends SuperVO {

	/**
	 * 參數行主鍵
	 */
	public String pk_task_r;
	/**
	 * 樣品組別
	 */
	public String pk_samplegroup;
	/**
	 * 最小值
	 */
	public UFDouble stdminvalue;
	/**
	 * 最大值
	 */
	public UFDouble stdmaxvalue;
	/**
	 * 單位
	 */
	public String pk_unit;
	/**
	 * 測試標記
	 */
	public UFBoolean testflag;
	/**
	 * 判定標記
	 */
	public UFBoolean judgeflag;
	/**
	 * 測試溫度
	 */
	public String pk_testtemperature;
	/**
	 * 實驗參數名稱
	 */
	public String analysisname;
	/**
	 * 參數項
	 */
	public String pk_component;
	/**
	 * 值類型
	 */
	public String pk_valuetype;
	/**
	 * 上層單據主鍵
	 */
	public String pk_task_b;
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
	 * 屬性 pk_task_r的Getter方法.屬性名：參數行主鍵 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getPk_task_r() {
		return this.pk_task_r;
	}

	/**
	 * 屬性pk_task_r的Setter方法.屬性名：參數行主鍵 創建日期:2019/5/5
	 * 
	 * @param newPk_task_r
	 *            java.lang.String
	 */
	public void setPk_task_r(String pk_task_r) {
		this.pk_task_r = pk_task_r;
	}

	/**
	 * 屬性 pk_samplegroup的Getter方法.屬性名：樣品組別 創建日期:2019/5/5
	 * 
	 * @return nc.vo.bd.defdoc.DefdocVO
	 */
	public String getPk_samplegroup() {
		return this.pk_samplegroup;
	}

	/**
	 * 屬性pk_samplegroup的Setter方法.屬性名：樣品組別 創建日期:2019/5/5
	 * 
	 * @param newPk_samplegroup
	 *            nc.vo.bd.defdoc.DefdocVO
	 */
	public void setPk_samplegroup(String pk_samplegroup) {
		this.pk_samplegroup = pk_samplegroup;
	}

	/**
	 * 屬性 stdminvalue的Getter方法.屬性名：最小值 創建日期:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDouble
	 */
	public UFDouble getStdminvalue() {
		return this.stdminvalue;
	}

	/**
	 * 屬性stdminvalue的Setter方法.屬性名：最小值 創建日期:2019/5/5
	 * 
	 * @param newStdminvalue
	 *            nc.vo.pub.lang.UFDouble
	 */
	public void setStdminvalue(UFDouble stdminvalue) {
		this.stdminvalue = stdminvalue;
	}

	/**
	 * 屬性 stdmaxvalue的Getter方法.屬性名：最大值 創建日期:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDouble
	 */
	public UFDouble getStdmaxvalue() {
		return this.stdmaxvalue;
	}

	/**
	 * 屬性stdmaxvalue的Setter方法.屬性名：最大值 創建日期:2019/5/5
	 * 
	 * @param newStdmaxvalue
	 *            nc.vo.pub.lang.UFDouble
	 */
	public void setStdmaxvalue(UFDouble stdmaxvalue) {
		this.stdmaxvalue = stdmaxvalue;
	}

	/**
	 * 屬性 pk_unit的Getter方法.屬性名：單位 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getPk_unit() {
		return this.pk_unit;
	}

	/**
	 * 屬性pk_unit的Setter方法.屬性名：單位 創建日期:2019/5/5
	 * 
	 * @param newPk_unit
	 *            java.lang.String
	 */
	public void setPk_unit(String pk_unit) {
		this.pk_unit = pk_unit;
	}

	/**
	 * 屬性 testflag的Getter方法.屬性名：測試標記 創建日期:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public UFBoolean getTestflag() {
		return this.testflag;
	}

	/**
	 * 屬性testflag的Setter方法.屬性名：測試標記 創建日期:2019/5/5
	 * 
	 * @param newTestflag
	 *            nc.vo.pub.lang.UFBoolean
	 */
	public void setTestflag(UFBoolean testflag) {
		this.testflag = testflag;
	}

	/**
	 * 屬性 judgeflag的Getter方法.屬性名：判定標記 創建日期:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public UFBoolean getJudgeflag() {
		return this.judgeflag;
	}

	/**
	 * 屬性judgeflag的Setter方法.屬性名：判定標記 創建日期:2019/5/5
	 * 
	 * @param newJudgeflag
	 *            nc.vo.pub.lang.UFBoolean
	 */
	public void setJudgeflag(UFBoolean judgeflag) {
		this.judgeflag = judgeflag;
	}

	/**
	 * 屬性 pk_testtemperature的Getter方法.屬性名：測試溫度 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getPk_testtemperature() {
		return this.pk_testtemperature;
	}

	/**
	 * 屬性pk_testtemperature的Setter方法.屬性名：測試溫度 創建日期:2019/5/5
	 * 
	 * @param newPk_testtemperature
	 *            java.lang.String
	 */
	public void setPk_testtemperature(String pk_testtemperature) {
		this.pk_testtemperature = pk_testtemperature;
	}

	/**
	 * 屬性 analysisname的Getter方法.屬性名：實驗參數名稱 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getAnalysisname() {
		return this.analysisname;
	}

	/**
	 * 屬性analysisname的Setter方法.屬性名：實驗參數名稱 創建日期:2019/5/5
	 * 
	 * @param newAnalysisname
	 *            java.lang.String
	 */
	public void setAnalysisname(String analysisname) {
		this.analysisname = analysisname;
	}

	/**
	 * 屬性 pk_component的Getter方法.屬性名：參數項 創建日期:2019/5/5
	 * 
	 * @return nc.vo.bd.defdoc.DefdocVO
	 */
	public String getPk_component() {
		return this.pk_component;
	}

	/**
	 * 屬性pk_component的Setter方法.屬性名：參數項 創建日期:2019/5/5
	 * 
	 * @param newPk_component
	 *            nc.vo.bd.defdoc.DefdocVO
	 */
	public void setPk_component(String pk_component) {
		this.pk_component = pk_component;
	}

	/**
	 * 屬性 pk_valuetype的Getter方法.屬性名：值類型 創建日期:2019/5/5
	 * 
	 * @return nc.vo.bd.defdoc.DefdocVO
	 */
	public String getPk_valuetype() {
		return this.pk_valuetype;
	}

	/**
	 * 屬性pk_valuetype的Setter方法.屬性名：值類型 創建日期:2019/5/5
	 * 
	 * @param newPk_valuetype
	 *            nc.vo.bd.defdoc.DefdocVO
	 */
	public void setPk_valuetype(String pk_valuetype) {
		this.pk_valuetype = pk_valuetype;
	}

	/**
	 * 屬性 生成上層主鍵的Getter方法.屬性名：上層主鍵 創建日期:2019/5/5
	 * 
	 * @return String
	 */
	public String getPk_task_b() {
		return this.pk_task_b;
	}

	/**
	 * 屬性生成上層主鍵的Setter方法.屬性名：上層主鍵 創建日期:2019/5/5
	 * 
	 * @param newPk_task_b
	 *            String
	 */
	public void setPk_task_b(String pk_task_b) {
		this.pk_task_b = pk_task_b;
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
		return VOMetaFactory.getInstance().getVOMeta("qcco.task_r");
	}
    @Override
    public String getParentPKFieldName() {
        // TODO Auto-generated method stub
        return "pk_task_b";
    }
}
