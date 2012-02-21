package cn.jsprun.domain;

/**
 * 场景物件配置表 时间：2010-11-30
 * 
 * @author Administrator
 * 
 */

public class Scene implements java.io.Serializable {

	private Integer id;// 场景物件id
	private Integer pid;// 场景物件父级id
	private String objName;// 物件名称
	private Integer objId;// 物件id
	private String images;// 场景图片
	private String sceneName;// 场景名称
	private Integer eventType;// 场景事件类型
	private String eventUrl;// 场景事件url

	public Scene() {

	}

	public Scene(Integer id, Integer pid, String objName, Integer objId,
			String images, String sceneName, Integer eventType, String eventUrl) {
		super();
		this.id = id;
		this.pid = pid;
		this.objName = objName;
		this.objId = objId;
		this.images = images;
		this.sceneName = sceneName;
		this.eventType = eventType;
		this.eventUrl = eventUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public Integer getObjId() {
		return objId;
	}

	public void setObjId(Integer objId) {
		this.objId = objId;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}

	public Integer getEventType() {
		return eventType;
	}

	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}

	public String getEventUrl() {
		return eventUrl;
	}

	public void setEventUrl(String eventUrl) {
		this.eventUrl = eventUrl;
	}

}
