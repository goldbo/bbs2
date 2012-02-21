package cn.jsprun.domain;

public class TaskMember {
	private Integer id;
	private Integer taskid;
	private String member;

	public TaskMember(Integer id, Integer taskid, String member) {
		super();
		this.id = id;
		this.taskid = taskid;
		this.member = member;
	}

	public TaskMember() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTaskid() {
		return taskid;
	}

	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}
}
