package cn.jsprun.domain;

public class TaskList {
	private Integer id;
	private String taskname;
	private String starttime;
	private String endtime;
	private String search;
	private String tasks;
    private String report_url;
    private String report_remark;
    
	
	public String getReport_url() {
		return report_url;
	}

	public void setReport_url(String report_url) {
		this.report_url = report_url;
	}

	public String getReport_remark() {
		return report_remark;
	}

	public void setReport_remark(String report_remark) {
		this.report_remark = report_remark;
	}

	public TaskList(Integer id, String tasknam, String starttime,
			String endtime, String search, String tasks) {
		super();
		this.id = id;
		this.taskname = tasknam;
		this.starttime = starttime;
		this.endtime = endtime;
		this.search = search;
		this.tasks = tasks;
	}

	public TaskList() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getTasks() {
		return tasks;
	}

	public void setTasks(String tasks) {
		this.tasks = tasks;
	}
}
