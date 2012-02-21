package cn.vcity.wbase.common.module.quartz.model;

public class QrtzJobDetails implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7498880579320522831L;

	String job_name = "";
	String job_group = "instance_one";
	String job_class_name = "";
	Integer is_durable = 1 ;
	Integer is_volatile = 0 ;
	Integer is_stateful = 1 ;
	Integer requests_recovery = 1 ;
	
	public Integer getIs_durable() {
		return is_durable;
	}
	public void setIs_durable(Integer is_durable) {
		this.is_durable = is_durable;
	}
	public Integer getIs_stateful() {
		return is_stateful;
	}
	public void setIs_stateful(Integer is_stateful) {
		this.is_stateful = is_stateful;
	}
	public Integer getIs_volatile() {
		return is_volatile;
	}
	public void setIs_volatile(Integer is_volatile) {
		this.is_volatile = is_volatile;
	}
	public String getJob_class_name() {
		return job_class_name;
	}
	public void setJob_class_name(String job_class_name) {
		this.job_class_name = job_class_name;
	}
	public String getJob_group() {
		return job_group;
	}
	public void setJob_group(String job_group) {
		this.job_group = job_group;
	}
	public String getJob_name() {
		return job_name;
	}
	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}
	public Integer getRequests_recovery() {
		return requests_recovery;
	}
	public void setRequests_recovery(Integer requests_recovery) {
		this.requests_recovery = requests_recovery;
	}
	
	
}
