package cn.vcity.model;

/**
 * VcityManualtasksetting entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class VcityManualtasksetting implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer taskId;
	private Integer subTaskType;
	private Integer subTaskResId;

	// Constructors

	/** default constructor */
	public VcityManualtasksetting() {
	}

	/** full constructor */
	public VcityManualtasksetting(Integer taskId, Integer subTaskType,
			Integer subTaskResId) {
		this.taskId = taskId;
		this.subTaskType = subTaskType;
		this.subTaskResId = subTaskResId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getSubTaskType() {
		return this.subTaskType;
	}

	public void setSubTaskType(Integer subTaskType) {
		this.subTaskType = subTaskType;
	}

	public Integer getSubTaskResId() {
		return this.subTaskResId;
	}

	public void setSubTaskResId(Integer subTaskResId) {
		this.subTaskResId = subTaskResId;
	}

}