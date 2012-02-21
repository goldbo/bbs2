package cn.jsprun.domain;
import java.io.Serializable;

public class Image implements Serializable{
	private static final long serialVersionUID = 5874456074709229480L;
	private Integer id;
	private String imagePath;
	private String url;
	private Integer imageType;
	private String remark;
	private Integer status;
	public Image(Integer id, String imagePath, String url, Integer imageType,
			String remark,Integer status) {
		super();
		this.id = id;
		this.imagePath = imagePath;
		this.url = url;
		this.imageType = imageType;
		this.remark = remark;
		this.status=status;
	}
	public Image(){
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getImageType() {
		return imageType;
	}
	public void setImageType(Integer imageType) {
		this.imageType = imageType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
