package cn.jsprun.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cn.vcity.model.VcityManualTaskInit;
import cn.vcity.model.VcitySubTask;

/**
 * 场景任务表
 * 
 * @author Administrator
 * 
 */

public class Scenetask implements java.io.Serializable{
	private Integer taskId;// 任务id
	private String taskName;// 任务名称
	private String taskIntro;// 任务介绍
	private String taskTime;// 任务时间
    private String createUser;     //创建用户
	private Integer credit;       //兑换积分
	
	/*二期追加字段    */
	private Integer taskType;       //任务类型
	private String taskBegin;       //任务开始时间
	private String taskEnd;         //任务结束时间
	
	private Set<VcitySubTask> vcitySubTask = new HashSet<VcitySubTask>();    //自动子任务
	private Set<VcityManualTaskInit> manualTaskInit = new HashSet<VcityManualTaskInit>();   //人工任务初始化
	private Set<Scenetm> scenetm = new HashSet<Scenetm>();                 //任务人员关联表
	
	private String taskStatus;      //页面显示－任务状态中文
	
	public Scenetask() {

	}

	public Scenetask(Integer taskId, String taskName, String taskIntro,
			String taskTime,String createUser,Integer credit) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskIntro = taskIntro;
		this.taskTime = taskTime;
		this.createUser=createUser;
		this.credit=credit;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskIntro() {
		return taskIntro;
	}

	public void setTaskIntro(String taskIntro) {
		this.taskIntro = taskIntro;
	}

	public String getTaskTime() {
		return taskTime;
	}

	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public String getTaskBegin() {
		return taskBegin;
	}

	public void setTaskBegin(String taskBegin) {
		this.taskBegin = taskBegin;
	}

	public String getTaskEnd() {
		return taskEnd;
	}

	public void setTaskEnd(String taskEnd) {
		this.taskEnd = taskEnd;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Set<VcitySubTask> getVcitySubTask() {
		return vcitySubTask;
	}

	public void setVcitySubTask(Set<VcitySubTask> vcitySubTask) {
		this.vcitySubTask = vcitySubTask;
	}
	
	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	public Set<VcityManualTaskInit> getManualTaskInit() {
		return manualTaskInit;
	}

	public void setManualTaskInit(Set<VcityManualTaskInit> manualTaskInit) {
		this.manualTaskInit = manualTaskInit;
	}

	public Set<Scenetm> getScenetm() {
		return scenetm;
	}

	public void setScenetm(Set<Scenetm> scenetm) {
		this.scenetm = scenetm;
	}

	public void addVcityTaskChild(VcitySubTask child){
		child.setSceneTask(this);
		getVcitySubTask().add(child);
	}
	
	public void addManualTaskInit(VcityManualTaskInit child){
		child.setScenetask(this);
		getManualTaskInit().add(child);
	}
	
	public void addScenetm(Scenetm child){
		child.setSceneTask(this);
		getScenetm().add(child);
	}
}
