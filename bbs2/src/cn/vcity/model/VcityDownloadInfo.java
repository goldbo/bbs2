package cn.vcity.model;


public class VcityDownloadInfo {
	private Integer id;
	private Integer resId;
	private Integer downloadType;    //下载类型，1为页面，2为客户端
	private String downloadIp;
	private String downloadTime;
	private VcityResource vcityResource;
	
	public String getDownloadIp() {
		return downloadIp;
	}
	public void setDownloadIp(String downloadIp) {
		this.downloadIp = downloadIp;
	}
	public String getDownloadTime() {
		return downloadTime;
	}
	public void setDownloadTime(String downloadTime) {
		this.downloadTime = downloadTime;
	}
	public Integer getDownloadType() {
		return downloadType;
	}
	public void setDownloadType(Integer downloadType) {
		this.downloadType = downloadType;
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
}
