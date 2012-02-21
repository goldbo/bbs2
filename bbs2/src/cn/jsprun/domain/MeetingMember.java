package cn.jsprun.domain;

import java.util.Date;

import cn.jsprun.vo.space.Users;

/*****参会表（用户和会议关系表）****2010-11-03*****hcy ***/
public class MeetingMember implements java.io.Serializable  {
	
	private Integer meetingMemberId;
	private Integer userId;
	private Integer appointmentId;
	private Integer isfiltrate;
	private Integer isregister;
	private Integer iskickout;
	private Integer isscreenout;
	private Date addTime;
	
	
	private Members user;
	private Appointment appointment;
	
	public MeetingMember(){};

	public MeetingMember(Integer meetingMemberId,Integer userId,Integer appointmentId,
			Integer isfiltrate,Integer isregister,Integer iskickout
			,Integer isscreenout,Date addTime){
		this.meetingMemberId = meetingMemberId;
		this.userId = userId;
		this.appointmentId =appointmentId;
		this.isfiltrate = isfiltrate;
		this.isregister = isregister;
		this.iskickout = iskickout;
		this.isscreenout = isscreenout;
		this.addTime = addTime;
		
	};
	
	public MeetingMember(Integer meetingMemberId,Integer userId,Integer appointmentId,
			Integer isfiltrate,Integer isregister,Integer iskickout
			,Integer isscreenout,Date addTime,Members user,Appointment appointment){
		this.meetingMemberId = meetingMemberId;
		this.userId = userId;
		this.appointmentId =appointmentId;
		this.isfiltrate = isfiltrate;
		this.isregister = isregister;
		this.iskickout = iskickout;
		this.isscreenout = isscreenout;
		this.addTime = addTime;
		this.user = user;
		this.appointment = appointment;
	};

	/**
	 * @return the user
	 */
	public Members getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Members user) {
		this.user = user;
	}

	/**
	 * @return the appointment
	 */
	public Appointment getAppointment() {
		return appointment;
	}

	/**
	 * @param appointment the appointment to set
	 */
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	
	/**
	 * @return the meetingMemberId
	 */
	public Integer getMeetingMemberId() {
		return meetingMemberId;
	}

	/**
	 * @param meetingMemberId the meetingMemberId to set
	 */
	public void setMeetingMemberId(Integer meetingMemberId) {
		this.meetingMemberId = meetingMemberId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the appointmentId
	 */
	public Integer getAppointmentId() {
		return appointmentId;
	}

	/**
	 * @param appointmentId the appointmentId to set
	 */
	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Integer getIsfiltrate() {
		return isfiltrate;
	}
	public void setIsfiltrate(Integer isfiltrate) {
		this.isfiltrate = isfiltrate;
	}
	public Integer getIsregister() {
		return isregister;
	}
	public void setIsregister(Integer isregister) {
		this.isregister = isregister;
	}
	public Integer getIskickout() {
		return iskickout;
	}
	public void setIskickout(Integer iskickout) {
		this.iskickout = iskickout;
	}
	public Integer getIsscreenout() {
		return isscreenout;
	}
	public void setIsscreenout(Integer isscreenout) {
		this.isscreenout = isscreenout;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	

}
