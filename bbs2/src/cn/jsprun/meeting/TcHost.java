package cn.jsprun.meeting;

public class TcHost {
	private Integer id;
	private Integer userID;
	private Integer sex;
	private String remark;
	private Integer creator;
	
	public TcHost(Integer id, Integer userID, Integer sex, String remark,
			Integer creator) {
		super();
		this.id = id;
		this.userID = userID;
		this.sex = sex;
		this.remark = remark;
		this.creator = creator;
	}
	public TcHost() {
		super();
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getCreator() {
		return creator;
	}
	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	
	
}
