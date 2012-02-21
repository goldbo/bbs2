package cn.jsprun.meeting;

import java.util.Date;

public class UmsUser {
	
	private Integer id;
	private Integer groupID;
	private String account;
	private String name;
	private String password;
	private String email;
	private String headImg;
	private String ip;
	private String loginIp;
	private Date loginTime;
	private Date createTime;
	private int status;
	
	public UmsUser() {
		super();
	}

	public UmsUser(Integer id, Integer groupID, String account, String name,
			String password, String email, String headImg, String ip,
			String loginIp, Date loginTime, Date createTime, int status) {
		super();
		this.id = id;
		this.groupID = groupID;
		this.account = account;
		this.name = name;
		this.password = password;
		this.email = email;
		this.headImg = headImg;
		this.ip = ip;
		this.loginIp = loginIp;
		this.loginTime = loginTime;
		this.createTime = createTime;
		this.status = status;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getGroupID() {
		return groupID;
	}
	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
