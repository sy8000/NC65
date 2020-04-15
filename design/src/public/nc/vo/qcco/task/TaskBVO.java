package nc.vo.qcco.task;

import nc.vo.pub.IVOMeta;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
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

public class TaskBVO extends SuperVO {

	/**
	 * �΄������I
	 */
	public String pk_task_b;
	/**
	 * α��
	 */
	public String uniquekey;
	/**
	 * �΄վ�̖
	 */
	public String taskcode;
	/**
	 * �΄����Q
	 */
	public String taskname;
	/**
	 * �������
	 */
	public Integer runorder;
	/**
	 * ��̖
	 */
	public Integer rowno;
	/**
	 * �yԇ�Y�����Q
	 */
	public String pk_testresultname;
	/**
	 * �yԇ�Y�������Q
	 */
	public String testresultshortname;
	/**
	 * �yԇ�Ŀ
	 */
	public String testitem;
	/**
	 * testnumber
	 */
	public String testnumber;
	/**
	 * ��Ʒ����
	 */
	public String sampleallocation;
	
	/**
	 * ��Ʒ����ԭʼ����
	 */
	public String sampleallocationsource;
	/**
	 * ��Ʒ����
	 */
	public UFDouble samplequantity;
	/**
	 * �˜ʗl��
	 */
	public String standardclause;
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
	 * �όӆΓ����I
	 */
	public String pk_task_h;
	/**
	 * �r�g��
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
	 * ���� pk_task_b��Getter����.���������΄������I ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getPk_task_b() {
		return this.pk_task_b;
	}

	/**
	 * ����pk_task_b��Setter����.���������΄������I ��������:2019/5/5
	 * 
	 * @param newPk_task_b
	 *            java.lang.String
	 */
	public void setPk_task_b(String pk_task_b) {
		this.pk_task_b = pk_task_b;
	}

	/**
	 * ���� taskcode��Getter����.���������΄վ�̖ ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getTaskcode() {
		return this.taskcode;
	}

	/**
	 * ����taskcode��Setter����.���������΄վ�̖ ��������:2019/5/5
	 * 
	 * @param newTaskcode
	 *            java.lang.String
	 */
	public void setTaskcode(String taskcode) {
		this.taskcode = taskcode;
	}

	/**
	 * ���� taskname��Getter����.���������΄����Q ��������:2019/5/5
	 * 
	 * @return nc.vo.bd.defdoc.DefdocVO
	 */
	public String getTaskname() {
		return this.taskname;
	}

	/**
	 * ����taskname��Setter����.���������΄����Q ��������:2019/5/5
	 * 
	 * @param newTaskname
	 *            nc.vo.bd.defdoc.DefdocVO
	 */
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	/**
	 * ���� runorder��Getter����.��������������� ��������:2019/5/5
	 * 
	 * @return java.lang.Integer
	 */
	public Integer getRunorder() {
		return this.runorder;
	}

	/**
	 * ����runorder��Setter����.��������������� ��������:2019/5/5
	 * 
	 * @param newRunorder
	 *            java.lang.Integer
	 */
	public void setRunorder(Integer runorder) {
		this.runorder = runorder;
	}

	/**
	 * ���� rowno��Getter����.����������̖ ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public Integer getRowno() {
		return this.rowno;
	}

	/**
	 * ����rowno��Setter����.����������̖ ��������:2019/5/5
	 * 
	 * @param newRowno
	 *            java.lang.String
	 */
	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}

	/**
	 * ���� pk_testresultname��Getter����.���������yԇ�Y�����Q ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getPk_testresultname() {
		return this.pk_testresultname;
	}

	/**
	 * ����pk_testresultname��Setter����.���������yԇ�Y�����Q ��������:2019/5/5
	 * 
	 * @param newPk_testresultname
	 *            java.lang.String
	 */
	public void setPk_testresultname(String pk_testresultname) {
		this.pk_testresultname = pk_testresultname;
	}

	/**
	 * ���� testresultshortname��Getter����.���������yԇ�Y�������Q ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getTestresultshortname() {
		return this.testresultshortname;
	}

	/**
	 * ����testresultshortname��Setter����.���������yԇ�Y�������Q ��������:2019/5/5
	 * 
	 * @param newTestresultshortname
	 *            java.lang.String
	 */
	public void setTestresultshortname(String testresultshortname) {
		this.testresultshortname = testresultshortname;
	}

	/**
	 * ���� testitem��Getter����.���������yԇ�Ŀ ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getTestitem() {
		return this.testitem;
	}

	/**
	 * ����testitem��Setter����.���������yԇ�Ŀ ��������:2019/5/5
	 * 
	 * @param newTestitem
	 *            java.lang.String
	 */
	public void setTestitem(String testitem) {
		this.testitem = testitem;
	}

	/**
	 * ���� testnumber��Getter����.��������testnumber ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getTestnumber() {
		return this.testnumber;
	}

	/**
	 * ����testnumber��Setter����.��������testnumber ��������:2019/5/5
	 * 
	 * @param newTestnumber
	 *            java.lang.String
	 */
	public void setTestnumber(String testnumber) {
		this.testnumber = testnumber;
	}

	/**
	 * ���� sampleallocation��Getter����.����������Ʒ���� ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getSampleallocation() {
		return this.sampleallocation;
	}

	/**
	 * ����sampleallocation��Setter����.����������Ʒ���� ��������:2019/5/5
	 * 
	 * @param newSampleallocation
	 *            java.lang.String
	 */
	public void setSampleallocation(String sampleallocation) {
		this.sampleallocation = sampleallocation;
	}

	/**
	 * ���� samplequantity��Getter����.����������Ʒ���� ��������:2019/5/5
	 * 
	 * @return nc.vo.pub.lang.UFDouble
	 */
	public UFDouble getSamplequantity() {
		return this.samplequantity;
	}

	/**
	 * ����samplequantity��Setter����.����������Ʒ���� ��������:2019/5/5
	 * 
	 * @param newSamplequantity
	 *            nc.vo.pub.lang.UFDouble
	 */
	public void setSamplequantity(UFDouble samplequantity) {
		this.samplequantity = samplequantity;
	}

	/**
	 * ���� standardclause��Getter����.���������˜ʗl�� ��������:2019/5/5
	 * 
	 * @return java.lang.String
	 */
	public String getStandardclause() {
		return this.standardclause;
	}

	/**
	 * ����standardclause��Setter����.���������˜ʗl�� ��������:2019/5/5
	 * 
	 * @param newStandardclause
	 *            java.lang.String
	 */
	public void setStandardclause(String standardclause) {
		this.standardclause = standardclause;
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
	 * ���� �����ό����I��Getter����.���������ό����I ��������:2019/5/5
	 * 
	 * @return String
	 */
	public String getPk_task_h() {
		return this.pk_task_h;
	}

	/**
	 * ���������ό����I��Setter����.���������ό����I ��������:2019/5/5
	 * 
	 * @param newPk_task_h
	 *            String
	 */
	public void setPk_task_h(String pk_task_h) {
		this.pk_task_h = pk_task_h;
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
		return VOMetaFactory.getInstance().getVOMeta("qcco.task_b");
	}
}
