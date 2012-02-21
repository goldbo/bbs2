package cn.vcity.web.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.vcity.model.VcityManualTaskInit;
import cn.vcity.model.VcitySubTask;
import cn.vcity.wbase.common.base.BaseForm;

public class VcityTaskForm extends BaseForm  {

	private static final long serialVersionUID = -6447330959244485670L;
	
	private Integer taskId;// 任务id
	private String taskName;// 任务名称
	private String taskIntro;// 任务介绍
	private String taskTime;// 任务时间
    private String createUser;     //创建用户
	private Integer credit;       //兑换积分
	private Integer taskType;       //任务类型
	private String taskBegin;       //任务开始时间
	private String taskEnd;         //任务结束时间
	
	private Integer[] selectedRow;
	private Map childMap = new HashMap();
	private List<VcitySubTask> subTaskList = new ArrayList<VcitySubTask>();
	private List<VcityManualTaskInit> initTaskList = new ArrayList<VcityManualTaskInit>();
	
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
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getTaskIntro() {
		return taskIntro;
	}
	public void setTaskIntro(String taskIntro) {
		this.taskIntro = taskIntro;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	
	public Integer[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(Integer[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	public List<VcitySubTask> getSubTaskList() {
		return subTaskList;
	}
	public void setSubTaskList(List<VcitySubTask> subTaskList) {
		this.subTaskList = subTaskList;
	}

	public Map<String, VcitySubTask> getChildMap() {
		return childMap;
	}
	public void setChildMap(Map childMap) {
		this.childMap = childMap;
	}
	
	public VcitySubTask getValue(String key){
		if(getChildMap().get(key)==null){
			VcitySubTask vcitySubTask = new VcitySubTask();
			getChildMap().put(key, vcitySubTask);
			return vcitySubTask;
		}
		return (VcitySubTask)getChildMap().get(key);
	}
	
	public void setValue(String key, VcitySubTask vcitySubTask){
		getChildMap().put(key, vcitySubTask);
	}
	public List<VcityManualTaskInit> getInitTaskList() {
		return initTaskList;
	}
	public void setInitTaskList(List<VcityManualTaskInit> initTaskList) {
		this.initTaskList = initTaskList;
	}
}
