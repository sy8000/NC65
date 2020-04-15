package nc.vo.qcco.commission;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

/**
 * <b> 此處簡要描述此類功能 </b>
 * <p>
 * 此處添加累的描述信息
 * </p>
 * 創建日期:2019/5/15
 * 
 * @author yonyouBQ
 * @version NCPrj ??
 */

public class CommissionCVO extends SuperVO {

	/**
	 * 變更記錄主鍵
	 */
	public String pk_commission_c;
	/**
	 * 行號
	 */
	public String rowno;
	/**
	 * 變更人
	 */
	public String modifier;
	/**
	 * 變更時間
	 */
	public UFDateTime modifiedtime;
	/**
	 * 變更項目
	 */
	public String itemname;
	/**
	 * 變更前
	 */
	public String oldvalue;
	/**
	 * 變更后
	 */
	public String newvalue;
	/**
	 * 上層單據主鍵
	 */
	public String pk_commission_h;
	/**
	 * 時間戳
	 */
	public UFDateTime ts;

	/**
	 * 屬性 pk_commission_c的Getter方法.屬性名：變更記錄主鍵 創建日期:2019/5/15
	 * 
	 * @return java.lang.String
	 */
	public String getPk_commission_c() {
		return this.pk_commission_c;
	}

	/**
	 * 屬性pk_commission_c的Setter方法.屬性名：變更記錄主鍵 創建日期:2019/5/15
	 * 
	 * @param newPk_commission_c
	 *            java.lang.String
	 */
	public void setPk_commission_c(String pk_commission_c) {
		this.pk_commission_c = pk_commission_c;
	}

	/**
	 * 屬性 rowno的Getter方法.屬性名：行號 創建日期:2019/5/15
	 * 
	 * @return java.lang.String
	 */
	public String getRowno() {
		return this.rowno;
	}

	/**
	 * 屬性rowno的Setter方法.屬性名：行號 創建日期:2019/5/15
	 * 
	 * @param newRowno
	 *            java.lang.String
	 */
	public void setRowno(String rowno) {
		this.rowno = rowno;
	}

	/**
	 * 屬性 modifier的Getter方法.屬性名：變更人 創建日期:2019/5/15
	 * 
	 * @return nc.vo.sm.UserVO
	 */
	public String getModifier() {
		return this.modifier;
	}

	/**
	 * 屬性modifier的Setter方法.屬性名：變更人 創建日期:2019/5/15
	 * 
	 * @param newModifier
	 *            nc.vo.sm.UserVO
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * 屬性 modifiedtime的Getter方法.屬性名：變更時間 創建日期:2019/5/15
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getModifiedtime() {
		return this.modifiedtime;
	}

	/**
	 * 屬性modifiedtime的Setter方法.屬性名：變更時間 創建日期:2019/5/15
	 * 
	 * @param newModifiedtime
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setModifiedtime(UFDateTime modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	/**
	 * 屬性 itemname的Getter方法.屬性名：變更項目 創建日期:2019/5/15
	 * 
	 * @return java.lang.String
	 */
	public String getItemname() {
		return this.itemname;
	}

	/**
	 * 屬性itemname的Setter方法.屬性名：變更項目 創建日期:2019/5/15
	 * 
	 * @param newItemname
	 *            java.lang.String
	 */
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	/**
	 * 屬性 oldvalue的Getter方法.屬性名：變更前 創建日期:2019/5/15
	 * 
	 * @return java.lang.String
	 */
	public String getOldvalue() {
		return this.oldvalue;
	}

	/**
	 * 屬性oldvalue的Setter方法.屬性名：變更前 創建日期:2019/5/15
	 * 
	 * @param newOldvalue
	 *            java.lang.String
	 */
	public void setOldvalue(String oldvalue) {
		this.oldvalue = oldvalue;
	}

	/**
	 * 屬性 newvalue的Getter方法.屬性名：變更后 創建日期:2019/5/15
	 * 
	 * @return java.lang.String
	 */
	public String getNewvalue() {
		return this.newvalue;
	}

	/**
	 * 屬性newvalue的Setter方法.屬性名：變更后 創建日期:2019/5/15
	 * 
	 * @param newNewvalue
	 *            java.lang.String
	 */
	public void setNewvalue(String newvalue) {
		this.newvalue = newvalue;
	}

	/**
	 * 屬性 生成上層主鍵的Getter方法.屬性名：上層主鍵 創建日期:2019/5/15
	 * 
	 * @return String
	 */
	public String getPk_commission_h() {
		return this.pk_commission_h;
	}

	/**
	 * 屬性生成上層主鍵的Setter方法.屬性名：上層主鍵 創建日期:2019/5/15
	 * 
	 * @param newPk_commission_h
	 *            String
	 */
	public void setPk_commission_h(String pk_commission_h) {
		this.pk_commission_h = pk_commission_h;
	}

	/**
	 * 屬性 生成時間戳的Getter方法.屬性名：時間戳 創建日期:2019/5/15
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getTs() {
		return this.ts;
	}

	/**
	 * 屬性生成時間戳的Setter方法.屬性名：時間戳 創建日期:2019/5/15
	 * 
	 * @param newts
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setTs(UFDateTime ts) {
		this.ts = ts;
	}

	@Override
	public IVOMeta getMetaData() {
		return VOMetaFactory.getInstance().getVOMeta("qcco.commission_c");
	}

	public String getParentPKFieldName() {
		return "pk_commission_h";
	}
}
