package cn.jsprun.domain;

import java.util.Date;
/***********会议信息类*****2010-11-03*****hcy *****/
public class Appointment implements java.io.Serializable {
	
      private Integer appointmentId;
      private Integer userId;
      private String appointmentName;
      private Date appointmentStarttime;
      private Date appointmentEndtime;
      private String appointmentDescription;
      private Date addTime;
      private Integer ispassword;
      private String password;
      private Integer numberOfPartizipants;
      private Integer userPid;
      private Integer isEnd;
      private Integer modeType;
      private String imagePath;
      private Integer isPublished;

	public Integer getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Integer isPublished) {
		this.isPublished = isPublished;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	private Members user;
      
      private Members userPresider;
      
      public Appointment(){};
      
      public Appointment(Integer appointment_id,
    		  Integer user_id,
    		  String appointmentname,
    		  Date appointment_starttime,
    		  Date appointment_endtime,
    		  String appointment_description,
    		  Date addTime,
    		  Integer ispassword,
    		  String password,
    		  Integer numberOfPartizipants,
    		  Integer userPid,
    		  Integer isEnd,
    		  Integer modeType,
    		  String imagePath,
    		  Integer isPublished){
	       this.appointmentId = appointment_id;
	       this.userId = user_id;
	       this.appointmentName = appointmentname;
	       this.appointmentStarttime = appointment_starttime;
	       this.appointmentEndtime = appointment_endtime;
	       this.appointmentDescription = appointment_description;
	       this.addTime = addTime;
	       this.ispassword = ispassword;
	       this.password = password;
	       this.numberOfPartizipants = numberOfPartizipants;
	       this.userPid = userPid;
	       this.isEnd = isEnd;
	       this.modeType = modeType;
	       this.imagePath = imagePath;
	       this.isPublished = isPublished;
      };
      
      public Appointment(Integer appointment_id,
    		  Integer user_id,
    		  String appointmentname,
    		  Date appointment_starttime,
    		  Date appointment_endtime,
    		  String appointment_description,
    		  Date addTime,
    		  Integer ispassword,
    		  String password,
    		  Integer numberOfPartizipants,
    		  Integer isEnd,
    		  Integer modeType,
    		  String imagePath,
    		  Members user,
    		  Members userPresider){
	       this.appointmentId = appointment_id;
	       this.userId = user_id;
	       this.appointmentName = appointmentname;
	       this.appointmentStarttime = appointment_starttime;
	       this.appointmentEndtime = appointment_endtime;
	       this.appointmentDescription = appointment_description;
	       this.addTime = addTime;
	       this.ispassword = ispassword;
	       this.password = password;
	       this.numberOfPartizipants = numberOfPartizipants;
	       this.isEnd = isEnd;
	       this.modeType = modeType;
	       this.user = user;
	       this.userPresider = userPresider;
	       this.imagePath = imagePath;
      };
      
      public Integer getIsEnd() {
  		return isEnd;
  	}

  	public void setIsEnd(Integer isEnd) {
  		this.isEnd = isEnd;
  	}
	
	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAppointmentName() {
		return appointmentName;
	}

	public void setAppointmentName(String appointmentName) {
		this.appointmentName = appointmentName;
	}

	public Integer getNumberOfPartizipants() {
		return numberOfPartizipants;
	}

	public void setNumberOfPartizipants(Integer numberOfPartizipants) {
		this.numberOfPartizipants = numberOfPartizipants;
	}

	public Date getAppointmentStarttime() {
		return appointmentStarttime;
	}

	public void setAppointmentStarttime(Date appointmentStarttime) {
		this.appointmentStarttime = appointmentStarttime;
	}

	public Date getAppointmentEndtime() {
		return appointmentEndtime;
	}

	public void setAppointmentEndtime(Date appointmentEndtime) {
		this.appointmentEndtime = appointmentEndtime;
	}

	public String getAppointmentDescription() {
		return appointmentDescription;
	}

	public void setAppointmentDescription(String appointmentDescription) {
		this.appointmentDescription = appointmentDescription;
	}

	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Integer getIspassword() {
		return ispassword;
	}
	public void setIspassword(Integer ispassword) {
		this.ispassword = ispassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Members getUser() {
		return user;
	}
	public void setUser(Members user) {
		this.user = user;
	}

	public Integer getUserPid() {
		return userPid;
	}

	public void setUserPid(Integer userPid) {
		this.userPid = userPid;
	}

	public Members getUserPresider() {
		return userPresider;
	}

	public void setUserPresider(Members userPresider) {
		this.userPresider = userPresider;
	}

	public Integer getModeType() {
		return modeType;
	}

	public void setModeType(Integer modeType) {
		this.modeType = modeType;
	}
      
      
}
