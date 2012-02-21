package cn.vcity.model;

public class VcityTempSurvey {
	private Integer id;
	private Integer taskId;
	private String title;
	private String questionIds;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQuestionIds() {
		return questionIds;
	}
	public void setQuestionIds(String questionIds) {
		this.questionIds = questionIds;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
