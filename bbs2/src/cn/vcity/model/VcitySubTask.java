package cn.vcity.model;

import cn.jsprun.domain.Scenetask;

public class VcitySubTask implements java.io.Serializable  {

	private static final long serialVersionUID = -8683245413356973610L;
	
	private Integer id;
	//private Integer taskId;          //所属任务ID
	private String userName;          //用户账户
	private Integer subTaskType;      //子任务类型
	private String subTaskDesc;       //子任务描述
	private String subTaskURL;        //子任务URL	
	private String subTaskResId;      //子任务资源ID
	private Integer subTaskState;     //子任务完成状态
	private Integer subTaskIndex;     //子任务顺序
	private Integer flag;            //是否存为模板
	private Scenetask sceneTask = new Scenetask();     //所属任务ID
	
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSubTaskDesc() {
		return subTaskDesc;
	}
	public void setSubTaskDesc(String subTaskDesc) {
		this.subTaskDesc = subTaskDesc;
	}
	public Integer getSubTaskIndex() {
		return subTaskIndex;
	}
	public void setSubTaskIndex(Integer subTaskIndex) {
		this.subTaskIndex = subTaskIndex;
	}
	public String getSubTaskResId() {
		return subTaskResId;
	}
	public void setSubTaskResId(String subTaskResId) {
		this.subTaskResId = subTaskResId;
	}
	public Integer getSubTaskState() {
		return subTaskState;
	}
	public void setSubTaskState(Integer subTaskState) {
		this.subTaskState = subTaskState;
	}
	public Integer getSubTaskType() {
		return subTaskType;
	}
	public void setSubTaskType(Integer subTaskType) {
		this.subTaskType = subTaskType;
	}
	public String getSubTaskURL() {
		return subTaskURL;
	}
	public void setSubTaskURL(String subTaskURL) {
		this.subTaskURL = subTaskURL;
	}

	public String getUserName() {
		return userName;
	}
	public Scenetask getSceneTask() {
		return sceneTask;
	}
	public void setSceneTask(Scenetask sceneTask) {
		this.sceneTask = sceneTask;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
