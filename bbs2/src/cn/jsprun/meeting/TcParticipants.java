package cn.jsprun.meeting;

public class TcParticipants {
	private Integer id;
	private Integer userID;
	private Integer sex;
	private Integer creator;
	
	public TcParticipants() {
		super();
	}
	public TcParticipants(Integer id, Integer userID, Integer sex,
			Integer creator) {
		super();
		this.id = id;
		this.userID = userID;
		this.sex = sex;
		this.creator = creator;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
}
