package cn.jsprun.domain;

/**
 * 场景子任务表
 * 
 * @author Administrator
 * 
 */

public class Scenect implements java.io.Serializable{
	private Integer id;
	private Integer taskId;//任务id
	private String userName;
	private Short taskType;//子任务类型（1、调查  2、场景  ）
	private String surveyName;//调查名称
	private String surveyUrl;//调查地址
	private Integer objId;//场景id
	private Integer taskNo;//排序
	private Short taskState;
	
	public Scenect(){
		
	}
	
	public Scenect(Integer id,Integer taskId,String userName, Short taskType, String surveyName,
			String surveyUrl, Integer objId, Integer taskNo,Short taskState) {
		super();
		this.id=id;
		this.taskId = taskId;
		this.userName=userName;
		this.taskType = taskType;
		this.surveyName = surveyName;
		this.surveyUrl = surveyUrl;
		this.objId = objId;
		this.taskNo = taskNo;
		this.taskState=taskState;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Short getTaskType() {
		return taskType;
	}

	public void setTaskType(Short taskType) {
		this.taskType = taskType;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public String getSurveyUrl() {
		return surveyUrl;
	}

	public void setSurveyUrl(String surveyUrl) {
		this.surveyUrl = surveyUrl;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public Integer getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(Integer taskNo) {
		this.taskNo = taskNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Short getTaskState() {
		return taskState;
	}

	public void setTaskState(Short taskState) {
		this.taskState = taskState;
	}
}
