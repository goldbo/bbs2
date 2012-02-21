package cn.jsprun.domain;

/**
 * 场景任务人员关联表
 * 
 * @author Administrator
 * 
 */

public class Scenetm implements java.io.Serializable{
	private Integer id;
	private Integer taskId;
	private String userName;
	private Short taskState;
	private Short getCredit;
	private String taskTime;
	
	private Integer lastUpdateTime = 0;
	
	private Scenetask sceneTask;

	public Scenetm() {

	}

	public Scenetm(Integer id, Integer taskId,Short taskState, String userName,
			String taskTime,Short getCredit) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.userName = userName;
		this.taskState=taskState;
		this.taskTime = taskTime;
		this.getCredit=getCredit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

	public Short getTaskState() {
		return taskState;
	}

	public void setTaskState(Short taskState) {
		this.taskState = taskState;
	}

	public Short getGetCredit() {
		return getCredit;
	}

	public void setGetCredit(Short getCredit) {
		this.getCredit = getCredit;
	}

	public Scenetask getSceneTask() {
		return sceneTask;
	}

	public void setSceneTask(Scenetask sceneTask) {
		this.sceneTask = sceneTask;
	}

	public Integer getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Integer lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
