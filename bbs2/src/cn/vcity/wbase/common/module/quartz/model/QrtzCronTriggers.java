package cn.vcity.wbase.common.module.quartz.model;

public class QrtzCronTriggers implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1459162690661151044L;
	
	String trigger_name ; 
	String trigger_group ; 
	String cron_expression ; 
	String time_zone_id =null ;
	
	public String getCron_expression() {
		return cron_expression;
	}
	public void setCron_expression(String cron_expression) {
		this.cron_expression = cron_expression;
	}
	public String getTime_zone_id() {
		return time_zone_id;
	}
	public void setTime_zone_id(String time_zone_id) {
		this.time_zone_id = time_zone_id;
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
	
}
