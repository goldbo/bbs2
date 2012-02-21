package cn.vcity.model;

public class VcityResourceApp  implements java.io.Serializable {

	private static final long serialVersionUID = 4646040520097715307L;
	
	private Integer id;
	private Integer parenId;
	private Integer resId;
	private String appDesc;
	private String appCode;
	
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getAppDesc() {
		return appDesc;
	}
	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParenId() {
		return parenId;
	}
	public void setParenId(Integer parenId) {
		this.parenId = parenId;
	}
	public Integer getResId() {
		return resId;
	}
	public void setResId(Integer resId) {
		this.resId = resId;
	}

}
