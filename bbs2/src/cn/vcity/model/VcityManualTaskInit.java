package cn.vcity.model;

import cn.jsprun.domain.Scenetask;

public class VcityManualTaskInit implements java.io.Serializable {

	private static final long serialVersionUID = -4140030087321834313L;
	
	private Integer id;
	private Scenetask scenetask;
	private Integer subTaskType;
	private Integer subTaskResId;
	
	private String resName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Scenetask getScenetask() {
		return scenetask;
	}
	public void setScenetask(Scenetask scenetask) {
		this.scenetask = scenetask;
	}
	public Integer getSubTaskResId() {
		return subTaskResId;
	}
	public void setSubTaskResId(Integer subTaskResId) {
		this.subTaskResId = subTaskResId;
	}
	public Integer getSubTaskType() {
		return subTaskType;
	}
	public void setSubTaskType(Integer subTaskType) {
		this.subTaskType = subTaskType;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}

}
