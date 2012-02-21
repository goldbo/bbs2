package cn.vcity.model;

public class VcityResCommon implements java.io.Serializable  {

	private static final long serialVersionUID = 2018887478795035796L;
	
	private int id;	//
	private int resId; 			//资源ID
	private int grade; 			//评价等级
	private String userName; 	//用户账号名
	private String createTime;  //评价时间
	private String ip; 			//评价IP
	private String common; 		//内容
	private VcityResource vcityResource;
	

	public VcityResCommon() {
		super();
	}

	public String getCommon() {
		return common;
	}

	public void setCommon(String common) {
		this.common = common;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getResId() {
		return resId;
	}

	public void setResId(int resId) {
		this.resId = resId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	public VcityResource getVcityResource() {
		return vcityResource;
	}

	public void setVcityResource(VcityResource vcityResource) {
		this.vcityResource = vcityResource;
	}

	public VcityResCommon(int resId, int grade, String createTime, String ip) {
		super();
		this.resId = resId;
		this.grade = grade;
		this.createTime = createTime;
		this.ip = ip;
	}

	public VcityResCommon(int resId, int grade, String userName, String createTime, String ip, String common) {
		super();
		this.resId = resId;
		this.grade = grade;
		this.userName = userName;
		this.createTime = createTime;
		this.ip = ip;
		this.common = common;
	}

	public VcityResCommon(int resId, int grade, String userName, String createTime, String ip, String common, VcityResource vcityResource) {
		super();
		this.resId = resId;
		this.grade = grade;
		this.userName = userName;
		this.createTime = createTime;
		this.ip = ip;
		this.common = common;
		this.vcityResource = vcityResource;
	}
	
	
}
