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
 * 創建日期:2019/5/18
 * 
 * @author yonyouBQ
 * @version NCPrj ??
 */

public class TaskSVO extends SuperVO {

	/**
	 * 測試條件主鍵
	 */
	public String pk_task_s;
	/**
	 * 測試條件項
	 */
	public String pk_testconditionitem;
	/**
	 * 測試條件項_主键
	 */
	public String pk_testconditionitem_back;
	/**
	 * 狀態
	 */
	public String conditionstatus;
	/**
	 * 是否可選
	 */
	public UFBoolean isoptional;
	/**
	 * 是否可報告
	 */
	public UFBoolean isallow_out;
	/**
	 * 儀器
	 */
	public String pk_instrument;
	/**
	 * 值類型
	 */
	public String pk_valuetype;
	/**
	 * 取值方式
	 */
	public Integer valueways;
	/**
	 * 文本值
	 */
	public String textvalue;
	/**
	 * 參照值
	 */
	public String pk_refvalue;
	/**
	 * 單位
	 */
	public String unit;
	/**
	 * 格式化值
	 */
	public String formatted_entry;
	/**
	 * 最小值
	 */
	public UFDouble min_limit;
	/**
	 * 最大值
	 */
	public UFDouble max_limit;
	/**
	 * 英文說明
	 */
	public String englishdescription;

	/**
	 * 参照类型
	 */
	public String pk_list_table;
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

	public String getPk_testconditionitem_back() {
		return pk_testconditionitem_back;
	}

	public void setPk_testconditionitem_back(String pk_testconditionitem_back) {
		this.pk_testconditionitem_back = pk_testconditionitem_back;
	}

	/**
	 * 屬性 pk_task_s的Getter方法.屬性名：測試條件主鍵 創建日期:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getPk_task_s() {
		return this.pk_task_s;
	}

	/**
	 * 屬性pk_task_s的Setter方法.屬性名：測試條件主鍵 創建日期:2019/5/18
	 * 
	 * @param newPk_task_s
	 *            java.lang.String
	 */
	public void setPk_task_s(String pk_task_s) {
		this.pk_task_s = pk_task_s;
	}

	/**
	 * 屬性 pk_testconditionitem的Getter方法.屬性名：測試條件項 創建日期:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getPk_testconditionitem() {
		return this.pk_testconditionitem;
	}

	/**
	 * 屬性pk_testconditionitem的Setter方法.屬性名：測試條件項 創建日期:2019/5/18
	 * 
	 * @param newPk_testconditionitem
	 *            java.lang.String
	 */
	public void setPk_testconditionitem(String pk_testconditionitem) {
		this.pk_testconditionitem = pk_testconditionitem;
	}

	/**
	 * 屬性 conditionstatus的Getter方法.屬性名：狀態 創建日期:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getConditionstatus() {
		return this.conditionstatus;
	}

	/**
	 * 屬性conditionstatus的Setter方法.屬性名：狀態 創建日期:2019/5/18
	 * 
	 * @param newConditionstatus
	 *            java.lang.String
	 */
	public void setConditionstatus(String conditionstatus) {
		this.conditionstatus = conditionstatus;
	}

	/**
	 * 屬性 isoptional的Getter方法.屬性名：是否可選 創建日期:2019/5/18
	 * 
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public UFBoolean getIsoptional() {
		return this.isoptional;
	}

	/**
	 * 屬性isoptional的Setter方法.屬性名：是否可選 創建日期:2019/5/5
	 * 
	 * @param newIsoptional
	 *            nc.vo.pub.lang.UFBoolean
	 */
	public void setIsoptional(UFBoolean isoptional) {
		this.isoptional = isoptional;
	}

	/**
	 * 屬性 isallow_out的Getter方法.屬性名：是否可報告 創建日期:2019/5/18
	 * 
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public UFBoolean getIsallow_out() {
		return this.isallow_out;
	}

	/**
	 * 屬性isallow_out的Setter方法.屬性名：是否可報告 創建日期:2019/5/18
	 * 
	 * @param newIsallow_out
	 *            nc.vo.pub.lang.UFBoolean
	 */
	public void setIsallow_out(UFBoolean isallow_out) {
		this.isallow_out = isallow_out;
	}

	/**
	 * 屬性 pk_instrument的Getter方法.屬性名：儀器 創建日期:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getPk_instrument() {
		return this.pk_instrument;
	}

	/**
	 * 屬性pk_instrument的Setter方法.屬性名：儀器 創建日期:2019/5/18
	 * 
	 * @param newPk_instrument
	 *            java.lang.String
	 */
	public void setPk_instrument(String pk_instrument) {
		this.pk_instrument = pk_instrument;
	}

	/**
	 * 屬性 pk_valuetype的Getter方法.屬性名：值類型 創建日期:2019/5/18
	 * 
	 * @return nc.vo.bd.defdoc.DefdocVO
	 */
	public String getPk_valuetype() {
		return this.pk_valuetype;
	}

	/**
	 * 屬性pk_valuetype的Setter方法.屬性名：值類型 創建日期:2019/5/18
	 * 
	 * @param newPk_valuetype
	 *            nc.vo.bd.defdoc.DefdocVO
	 */
	public void setPk_valuetype(String pk_valuetype) {
		this.pk_valuetype = pk_valuetype;
	}

	/**
	 * 屬性 valueways的Getter方法.屬性名：取值方式 創建日期:2019/5/18
	 * 
	 * @return nc.vo.qcco.task.ValueWaysEnum
	 */
	public Integer getValueways() {
		return this.valueways;
	}

	/**
	 * 屬性valueways的Setter方法.屬性名：取值方式 創建日期:2019/5/18
	 * 
	 * @param newValueways
	 *            nc.vo.qcco.task.ValueWaysEnum
	 */
	public void setValueways(Integer valueways) {
		this.valueways = valueways;
	}

	/**
	 * 屬性 textvalue的Getter方法.屬性名：文本值 創建日期:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getTextvalue() {
		return this.textvalue;
	}

	/**
	 * 屬性textvalue的Setter方法.屬性名：文本值 創建日期:2019/5/18
	 * 
	 * @param newTextvalue
	 *            java.lang.String
	 */
	public void setTextvalue(String textvalue) {
		this.textvalue = textvalue;
	}

	/**
	 * 屬性 pk_refvalue的Getter方法.屬性名：參照值 創建日期:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getPk_refvalue() {
		return this.pk_refvalue;
	}

	/**
	 * 屬性pk_refvalue的Setter方法.屬性名：參照值 創建日期:2019/5/18
	 * 
	 * @param newPk_refvalue
	 *            java.lang.String
	 */
	public void setPk_refvalue(String pk_refvalue) {
		this.pk_refvalue = pk_refvalue;
	}

	/**
	 * 屬性 unit的Getter方法.屬性名：單位 創建日期:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getUnit() {
		return this.unit;
	}

	/**
	 * 屬性unit的Setter方法.屬性名：單位 創建日期:2019/5/18
	 * 
	 * @param newUnit
	 *            java.lang.String
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 屬性 formatted_entry的Getter方法.屬性名：格式化值 創建日期:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getFormatted_entry() {
		return this.formatted_entry;
	}

	/**
	 * 屬性formatted_entry的Setter方法.屬性名：格式化值 創建日期:2019/5/18
	 * 
	 * @param newFormatted_entry
	 *            java.lang.String
	 */
	public void setFormatted_entry(String formatted_entry) {
		this.formatted_entry = formatted_entry;
	}

	/**
	 * 屬性 min_limit的Getter方法.屬性名：最小值 創建日期:2019/5/18
	 * 
	 * @return nc.vo.pub.lang.UFDouble
	 */
	public UFDouble getMin_limit() {
		return this.min_limit;
	}

	/**
	 * 屬性min_limit的Setter方法.屬性名：最小值 創建日期:2019/5/18
	 * 
	 * @param newMin_limit
	 *            nc.vo.pub.lang.UFDouble
	 */
	public void setMin_limit(UFDouble min_limit) {
		this.min_limit = min_limit;
	}

	/**
	 * 屬性 max_limit的Getter方法.屬性名：最大值 創建日期:2019/5/18
	 * 
	 * @return nc.vo.pub.lang.UFDouble
	 */
	public UFDouble getMax_limit() {
		return this.max_limit;
	}

	/**
	 * 屬性max_limit的Setter方法.屬性名：最大值 創建日期:2019/5/18
	 * 
	 * @param newMax_limit
	 *            nc.vo.pub.lang.UFDouble
	 */
	public void setMax_limit(UFDouble max_limit) {
		this.max_limit = max_limit;
	}

	/**
	 * 屬性 englishdescription的Getter方法.屬性名：英文說明 創建日期:2019/5/18
	 * 
	 * @return java.lang.String
	 */
	public String getEnglishdescription() {
		return this.englishdescription;
	}

	/**
	 * 屬性englishdescription的Setter方法.屬性名：英文說明 創建日期:2019/5/18
	 * 
	 * @param newEnglishdescription
	 *            java.lang.String
	 */
	public void setEnglishdescription(String englishdescription) {
		this.englishdescription = englishdescription;
	}

	/**
	 * 屬性 生成上層主鍵的Getter方法.屬性名：上層主鍵 創建日期:2019/5/18
	 * 
	 * @return String
	 */
	public String getPk_task_b() {
		return this.pk_task_b;
	}

	/**
	 * 屬性生成上層主鍵的Setter方法.屬性名：上層主鍵 創建日期:2019/5/18
	 * 
	 * @param newPk_task_b
	 *            String
	 */
	public void setPk_task_b(String pk_task_b) {
		this.pk_task_b = pk_task_b;
	}

	/**
	 * 屬性 生成時間戳的Getter方法.屬性名：時間戳 創建日期:2019/5/18
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getTs() {
		return this.ts;
	}

	/**
	 * 屬性生成時間戳的Setter方法.屬性名：時間戳 創建日期:2019/5/18
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
