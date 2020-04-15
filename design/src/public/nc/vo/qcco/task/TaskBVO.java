package nc.vo.qcco.task;

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
 * 創建日期:2019/5/5
 * 
 * @author yonyouBQ
 * @version NCPrj ??
 */

public class TaskBVO extends SuperVO {

	/**
	 * 任務行主鍵
	 */
	public String pk_task_b;
	/**
	 * 伪例
	 */
	public String uniquekey;
	/**
	 * 任務編號
	 */
	public String taskcode;
	/**
	 * 任務名稱
	 */
	public String taskname;
	/**
	 * 執行順序
	 */
	public Integer runorder;
	/**
	 * 行號
	 */
	public Integer rowno;
	/**
	 * 測試結果名稱
	 */
	public String pk_testresultname;
	/**
	 * 測試結果短名稱
	 */
	public String testresultshortname;
	/**
	 * 測試項目
	 */
	public String testitem;
	/**
	 * testnumber
	 */
	public String testnumber;
	/**
	 * 樣品分配
	 */
	public String sampleallocation;
	
	/**
	 * 樣品分配原始数据
	 */
	public String sampleallocationsource;
	/**
	 * 樣品數量
	 */
	public UFDouble samplequantity;
	/**
	 * 標準條款
	 */
	public String standardclause;
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
	 * 上層單據主鍵
	 */
	public String pk_task_h;
	/**
	 * 時間戳
	 */
	public UFDateTime ts;
	
	public String getSampleallocationsource() {
		return sampleallocationsource;
	}

	public void setSampleallocationsource(String sampleallocationsource) {
		this.sampleallocationsource = sampleallocationsource;
	}

	public String getUniquekey() {
		return uniquekey;
	}

	public void setUniquekey(String uniquekey) {
		this.uniquekey = uniquekey;
	}

	public Integer Dr;
	public TaskRVO[] pk_task_r;
	public TaskSVO[] pk_task_s;

	public TaskSVO[] getPk_task_s() {
		return pk_task_s;
	}

	public void setPk_task_s(TaskSVO[] pk_task_s) {
		this.pk_task_s = pk_task_s;
	}

	public Integer getDr() {
		return Dr;
	}

	public void setDr(Integer dr) {
		Dr = dr;
	}

	public void setPk_task_r(TaskRVO[] originGrandvos) {
		pk_task_r = originGrandvos;
	}

	public TaskRVO[] getPk_task_r() {
		return pk_task_r;
	}

	/**
	 * 屬性 pk_task_b的Getter方法.屬性名：任務行主鍵 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getPk_task_b() {
		return this.pk_task_b;
	}

	/**
	 * 屬性pk_task_b的Setter方法.屬性名：任務行主鍵 創建日期:2019/5/5
	 * 
	 * @param newPk_task_b
	 *            java.lang.String
	 */
	public void setPk_task_b(String pk_task_b) {
		this.pk_task_b = pk_task_b;
	}

	/**
	 * 屬性 taskcode的Getter方法.屬性名：任務編號 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getTaskcode() {
		return this.taskcode;
	}

	/**
	 * 屬性taskcode的Setter方法.屬性名：任務編號 創建日期:2019/5/5
	 * 
	 * @param newTaskcode
	 *            java.lang.String
	 */
	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}

	/**
	 * 屬性 taskname的Getter方法.屬性名：任務名稱 創建日期:2019/5/5
	 * 
	 * @return nc.vo.bd.defdoc.DefdocVO
	 */
	public String getTaskname() {
		return this.taskname;
	}

	/**
	 * 屬性taskname的Setter方法.屬性名：任務名稱 創建日期:2019/5/5
	 * 
	 * @param newTaskname
	 *            nc.vo.bd.defdoc.DefdocVO
	 */
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	/**
	 * 屬性 runorder的Getter方法.屬性名：執行順序 創建日期:2019/5/5
	 * 
	 * @return java.lang.Integer
	 */
	public Integer getRunorder() {
		return this.runorder;
	}

	/**
	 * 屬性runorder的Setter方法.屬性名：執行順序 創建日期:2019/5/5
	 * 
	 * @param newRunorder
	 *            java.lang.Integer
	 */
	public void setRunorder(Integer runorder) {
		this.runorder = runorder;
	}

	/**
	 * 屬性 rowno的Getter方法.屬性名：行號 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public Integer getRowno() {
		return this.rowno;
	}

	/**
	 * 屬性rowno的Setter方法.屬性名：行號 創建日期:2019/5/5
	 * 
	 * @param newRowno
	 *            java.lang.String
	 */
	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}

	/**
	 * 屬性 pk_testresultname的Getter方法.屬性名：測試結果名稱 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getPk_testresultname() {
		return this.pk_testresultname;
	}

	/**
	 * 屬性pk_testresultname的Setter方法.屬性名：測試結果名稱 創建日期:2019/5/5
	 * 
	 * @param newPk_testresultname
	 *            java.lang.String
	 */
	public void setPk_testresultname(String pk_testresultname) {
		this.pk_testresultname = pk_testresultname;
	}

	/**
	 * 屬性 testresultshortname的Getter方法.屬性名：測試結果短名稱 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getTestresultshortname() {
		return this.testresultshortname;
	}

	/**
	 * 屬性testresultshortname的Setter方法.屬性名：測試結果短名稱 創建日期:2019/5/5
	 * 
	 * @param newTestresultshortname
	 *            java.lang.String
	 */
	public void setTestresultshortname(String testresultshortname) {
		this.testresultshortname = testresultshortname;
	}

	/**
	 * 屬性 testitem的Getter方法.屬性名：測試項目 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getTestitem() {
		return this.testitem;
	}

	/**
	 * 屬性testitem的Setter方法.屬性名：測試項目 創建日期:2019/5/5
	 * 
	 * @param newTestitem
	 *            java.lang.String
	 */
	public void setTestitem(String testitem) {
		this.testitem = testitem;
	}

	/**
	 * 屬性 testnumber的Getter方法.屬性名：testnumber 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getTestnumber() {
		return this.testnumber;
	}

	/**
	 * 屬性testnumber的Setter方法.屬性名：testnumber 創建日期:2019/5/5
	 * 
	 * @param newTestnumber
	 *            java.lang.String
	 */
	public void setTestnumber(String testnumber) {
		this.testnumber = testnumber;
	}

	/**
	 * 屬性 sampleallocation的Getter方法.屬性名：樣品分配 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getSampleallocation() {
		return this.sampleallocation;
	}

	/**
	 * 屬性sampleallocation的Setter方法.屬性名：樣品分配 創建日期:2019/5/5
	 * 
	 * @param newSampleallocation
	 *            java.lang.String
	 */
	public void setSampleallocation(String sampleallocation) {
		this.sampleallocation = sampleallocation;
	}

	/**
	 * 屬性 samplequantity的Getter方法.屬性名：樣品數量 創建日期:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDouble
	 */
	public UFDouble getSamplequantity() {
		return this.samplequantity;
	}

	/**
	 * 屬性samplequantity的Setter方法.屬性名：樣品數量 創建日期:2019/5/5
	 * 
	 * @param newSamplequantity
	 *            nc.vo.pub.lang.UFDouble
	 */
	public void setSamplequantity(UFDouble samplequantity) {
		this.samplequantity = samplequantity;
	}

	/**
	 * 屬性 standardclause的Getter方法.屬性名：標準條款 創建日期:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getStandardclause() {
		return this.standardclause;
	}

	/**
	 * 屬性standardclause的Setter方法.屬性名：標準條款 創建日期:2019/5/5
	 * 
	 * @param newStandardclause
	 *            java.lang.String
	 */
	public void setStandardclause(String standardclause) {
		this.standardclause = standardclause;
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
	 * 屬性 生成上層主鍵的Getter方法.屬性名：上層主鍵 創建日期:2019/5/5
	 * 
	 * @return String
	 */
	public String getPk_task_h() {
		return this.pk_task_h;
	}

	/**
	 * 屬性生成上層主鍵的Setter方法.屬性名：上層主鍵 創建日期:2019/5/5
	 * 
	 * @param newPk_task_h
	 *            String
	 */
	public void setPk_task_h(String pk_task_h) {
		this.pk_task_h = pk_task_h;
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
		return VOMetaFactory.getInstance().getVOMeta("qcco.task_b");
	}
}
