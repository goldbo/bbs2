package cn.vcity.model;

public class VcityAccessTotal {

	private Integer id;
	private Integer resId;
	private String accessIp;
	private String accessTime;
	private String userName;
	private VcityResource vcityResource;
	
	public VcityAccessTotal() {
		super();
	}

	public VcityAccessTotal(Integer resId, String accessIp, String accessTime) {
		super();
		this.resId = resId;
		this.accessIp = accessIp;
		this.accessTime = accessTime;
	}

	public String getAccessIp() {
		return accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResId() {
		return resId;
	}

	public void setResId(Integer resId) {
		this.resId = resId;
	}

	public VcityResource getVcityResource() {
		return vcityResource;
	}

	public void setVcityResource(VcityResource vcityResource) {
		this.vcityResource = vcityResource;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}
