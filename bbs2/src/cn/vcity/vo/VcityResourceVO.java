package cn.vcity.vo;

public class VcityResourceVO {
	private Integer id;

	private String resName;

	private String resPic;

	private String resKind;

	private String resModel;

	private String resScene;

	private String resRemark;

	private String resLink;

	private Integer resUp;

	private Integer resDown;

	private String resTypeName;
	
	public VcityResourceVO(Integer id, String resName, String resPic,
			String resKind, String resModel, String resScene, String resRemark,
			String resLink, Integer resUp, Integer resDown, String resTypeName) {
		super();
		this.id = id;
		this.resName = resName;
		this.resPic = resPic;
		this.resKind = resKind;
		this.resModel = resModel;
		this.resScene = resScene;
		this.resRemark = resRemark;
		this.resLink = resLink;
		this.resUp = resUp;
		this.resDown = resDown;
		this.resTypeName = resTypeName;
	}

	public VcityResourceVO() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResDown() {
		return resDown;
	}

	public void setResDown(Integer resDown) {
		this.resDown = resDown;
	}

	public String getResLink() {
		return resLink;
	}

	public void setResLink(String resLink) {
		this.resLink = resLink;
	}

	public String getResModel() {
		return resModel;
	}

	public void setResModel(String resModel) {
		this.resModel = resModel;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getResPic() {
		return resPic;
	}

	public void setResPic(String resPic) {
		this.resPic = resPic;
	}

	public String getResRemark() {
		return resRemark;
	}

	public void setResRemark(String resRemark) {
		this.resRemark = resRemark;
	}

	public String getResScene() {
		return resScene;
	}

	public void setResScene(String resScene) {
		this.resScene = resScene;
	}


	public String getResKind() {
		return resKind;
	}

	public void setResKind(String resKind) {
		this.resKind = resKind;
	}

	public String getResTypeName() {
		return resTypeName;
	}

	public void setResTypeName(String resTypeName) {
		this.resTypeName = resTypeName;
	}

	public Integer getResUp() {
		return resUp;
	}

	public void setResUp(Integer resUp) {
		this.resUp = resUp;
	}
}
