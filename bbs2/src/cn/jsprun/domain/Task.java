package cn.jsprun.domain;

public class Task {
	private Integer id;
	private String task;
	private Integer up_id;

	public Task(){
		
	}
	
	public Task(Integer id, String task, Integer up_id) {
		super();
		this.id = id;
		this.task = task;
		this.up_id = up_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Integer getUp_id() {
		return up_id;
	}

	public void setUp_id(Integer up_id) {
		this.up_id = up_id;
	}
}
