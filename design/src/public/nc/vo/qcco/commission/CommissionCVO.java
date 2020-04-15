package nc.vo.qcco.commission;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pubapp.pattern.model.meta.entity.vo.VOMetaFactory;

/**
 * <b> ��̎��Ҫ��������� </b>
 * <p>
 * ��̎����۵�������Ϣ
 * </p>
 * ��������:2019/5/15
 * 
 * @author yonyouBQ
 * @version NCPrj ??
 */

public class CommissionCVO extends SuperVO {

	/**
	 * ׃��ӛ����I
	 */
	public String pk_commission_c;
	/**
	 * ��̖
	 */
	public String rowno;
	/**
	 * ׃����
	 */
	public String modifier;
	/**
	 * ׃���r�g
	 */
	public UFDateTime modifiedtime;
	/**
	 * ׃���Ŀ
	 */
	public String itemname;
	/**
	 * ׃��ǰ
	 */
	public String oldvalue;
	/**
	 * ׃����
	 */
	public String newvalue;
	/**
	 * �όӆΓ����I
	 */
	public String pk_commission_h;
	/**
	 * �r�g��
	 */
	public UFDateTime ts;

	/**
	 * ���� pk_commission_c��Getter����.��������׃��ӛ����I ��������:2019/5/15
	 * 
	 * @return java.lang.String
	 */
	public String getPk_commission_c() {
		return this.pk_commission_c;
	}

	/**
	 * ����pk_commission_c��Setter����.��������׃��ӛ����I ��������:2019/5/15
	 * 
	 * @param newPk_commission_c
	 *            java.lang.String
	 */
	public void setPk_commission_c(String pk_commission_c) {
		this.pk_commission_c = pk_commission_c;
	}

	/**
	 * ���� rowno��Getter����.����������̖ ��������:2019/5/15
	 * 
	 * @return java.lang.String
	 */
	public String getRowno() {
		return this.rowno;
	}

	/**
	 * ����rowno��Setter����.����������̖ ��������:2019/5/15
	 * 
	 * @param newRowno
	 *            java.lang.String
	 */
	public void setRowno(String rowno) {
		this.rowno = rowno;
	}

	/**
	 * ���� modifier��Getter����.��������׃���� ��������:2019/5/15
	 * 
	 * @return nc.vo.sm.UserVO
	 */
	public String getModifier() {
		return this.modifier;
	}

	/**
	 * ����modifier��Setter����.��������׃���� ��������:2019/5/15
	 * 
	 * @param newModifier
	 *            nc.vo.sm.UserVO
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * ���� modifiedtime��Getter����.��������׃���r�g ��������:2019/5/15
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getModifiedtime() {
		return this.modifiedtime;
	}

	/**
	 * ����modifiedtime��Setter����.��������׃���r�g ��������:2019/5/15
	 * 
	 * @param newModifiedtime
	 *            nc.vo.pub.lang.UFDateTime
	 */
	public void setModifiedtime(UFDateTime modifiedtime) {
		this.modifiedtime = modifiedtime;
	}

	/**
	 * ���� itemname��Getter����.��������׃���Ŀ ��������:2019/5/15
	 * 
	 * @return java.lang.String
	 */
	public String getItemname() {
		return this.itemname;
	}

	/**
	 * ����itemname��Setter����.��������׃���Ŀ ��������:2019/5/15
	 * 
	 * @param newItemname
	 *            java.lang.String
	 */
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	/**
	 * ���� oldvalue��Getter����.��������׃��ǰ ��������:2019/5/15
	 * 
	 * @return java.lang.String
	 */
	public String getOldvalue() {
		return this.oldvalue;
	}

	/**
	 * ����oldvalue��Setter����.��������׃��ǰ ��������:2019/5/15
	 * 
	 * @param newOldvalue
	 *            java.lang.String
	 */
	public void setOldvalue(String oldvalue) {
		this.oldvalue = oldvalue;
	}

	/**
	 * ���� newvalue��Getter����.��������׃���� ��������:2019/5/15
	 * 
	 * @return java.lang.String
	 */
	public String getNewvalue() {
		return this.newvalue;
	}

	/**
	 * ����newvalue��Setter����.��������׃���� ��������:2019/5/15
	 * 
	 * @param newNewvalue
	 *            java.lang.String
	 */
	public void setNewvalue(String newvalue) {
		this.newvalue = newvalue;
	}

	/**
	 * ���� �����ό����I��Getter����.���������ό����I ��������:2019/5/15
	 * 
	 * @return String
	 */
	public String getPk_commission_h() {
		return this.pk_commission_h;
	}

	/**
	 * ���������ό����I��Setter����.���������ό����I ��������:2019/5/15
	 * 
	 * @param newPk_commission_h
	 *            String
	 */
	public void setPk_commission_h(String pk_commission_h) {
		this.pk_commission_h = pk_commission_h;
	}

	/**
	 * ���� ���ɕr�g����Getter����.���������r�g�� ��������:2019/5/15
	 * 
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public UFDateTime getTs() {
		return this.ts;
	}

	/**
	 * �������ɕr�g����Setter����.���������r�g�� ��������:2019/5/15
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
