package nc.vo.qcco.task;

import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.ValidationException;

public class TaskBodyVO extends SuperVO {
	private String testList;// test list
	private String reportName;// ������Ŀ
	private String accordstandard;// ���Ա�׼
	private String projectType;// ��Ŀ����
	private String projectName;// ��׼����
	private String detailDescription;// ��ϸ˵��
	private String cbplan; //
	private String pk_sunlist;
	

	public String getPk_sunlist() {
		return pk_sunlist;
	}

	public void setPk_sunlist(String pk_sunlist) {
		this.pk_sunlist = pk_sunlist;
	}

	public String getAccordstandard() {
		return accordstandard;
	}

	public void setAccordstandard(String accordstandard) {
		this.accordstandard = accordstandard;
	}

	public String getTestList() {
		return testList;
	}

	public void setTestList(String testList) {
		this.testList = testList;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDetailDescription() {
		return detailDescription;
	}

	public void setDetailDescription(String detailDescription) {
		this.detailDescription = detailDescription;
	}

	public String getCbplan() {
		return cbplan;
	}

	public void setCbplan(String cbplan) {
		this.cbplan = cbplan;
	}

}
