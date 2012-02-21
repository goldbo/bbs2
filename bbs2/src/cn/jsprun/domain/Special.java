package cn.jsprun.domain;

import java.io.Serializable;

public class Special implements Serializable {
	private static final long serialVersionUID = 2532356134365954854L;
	private Integer id;
	private Integer tid;
	private Integer perm;
	private Integer startTime;
	private Integer endTime;
	
	public Special(){
		
	}
	
	public Special(int id, int tid, int perm, int startTime, int endTime) {
		super();
		this.id = id;
		this.tid = tid;
		this.perm = perm;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public Integer getPerm() {
		return perm;
	}

	public void setPerm(Integer perm) {
		this.perm = perm;
	}

	public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}	
}
