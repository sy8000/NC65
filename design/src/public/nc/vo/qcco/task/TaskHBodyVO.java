package nc.vo.qcco.task;

import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.pub.ValidationException;

public class TaskHBodyVO extends SuperVO {
	private String reportName;// ������Ŀ
	private String projectName;// ��׼����
	private String testresultname;//���Խ������
	private String testresultshortname;//���Խ��������
	private String unique;//Ψһ��ʶ
	private String testlistName;
	
	public String getTestlistName() {
		return testlistName;
	}

	public void setTestlistName(String testlistName) {
		this.testlistName = testlistName;
	}

	private TaskBodyVO taskBodyVO;
	
	public TaskBodyVO getTaskBodyVO() {
		return taskBodyVO;
	}

	public void setTaskBodyVO(TaskBodyVO taskBodyVO) {
		this.taskBodyVO = taskBodyVO;
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}

	public String getTestresultname() {
		return testresultname;
	}

	public void setTestresultname(String testresultname) {
		this.testresultname = testresultname;
	}

	public String getTestresultshortname() {
		return testresultshortname;
	}

	public void setTestresultshortname(String testresultshortname) {
		this.testresultshortname = testresultshortname;
	}



	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}


	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	

}
