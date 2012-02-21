package cn.vcity.wbase.common.module.quartz.model;

public class QrtzTriggers implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3879374218380050273L;
	String trigger_name;
	String trigger_group = "instance_one";
	String job_name;
	String job_group = "instance_one";
	Integer is_volatile = 0 ;
	Integer next_fire_time = 1 ;
	String trigger_state = "WAITING";
	String trigger_type = "CRON";
	Integer start_time = 1 ;
	Integer misfire_instr = 2 ;
	
	public Integer getIs_volatile() {
		return is_volatile;
	}
	public void setIs_volatile(Integer is_volatile) {
		this.is_volatile = is_volatile;
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
	public Integer getMisfire_instr() {
		return misfire_instr;
	}
	public void setMisfire_instr(Integer misfire_instr) {
		this.misfire_instr = misfire_instr;
	}
	public Integer getNext_fire_time() {
		return next_fire_time;
	}
	public void setNext_fire_time(Integer next_fire_time) {
		this.next_fire_time = next_fire_time;
	}
	public Integer getStart_time() {
		return start_time;
	}
	public void setStart_time(Integer start_time) {
		this.start_time = start_time;
	}
	public String getTrigger_group() {
		return trigger_group;
	}
	public void setTrigger_group(String trigger_group) {
		this.trigger_group = trigger_group;
	}
	public String getTrigger_name() {
		return trigger_name;
	}
	public void setTrigger_name(String trigger_name) {
		this.trigger_name = trigger_name;
	}
	public String getTrigger_state() {
		return trigger_state;
	}
	public void setTrigger_state(String trigger_state) {
		this.trigger_state = trigger_state;
	}
	public String getTrigger_type() {
		return trigger_type;
	}
	public void setTrigger_type(String trigger_type) {
		this.trigger_type = trigger_type;
	}
	
	
}
