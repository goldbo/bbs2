package cn.jsprun.domain;

/**
 * 用户任务执行顺序实体类
 * @author Administrator
 *
 */
public class ScenectOrder {
	private Integer id;
	private String userName;
	private Integer scenectId;

	public ScenectOrder() {

	}

	public ScenectOrder(Integer id, String userName,Integer scenectId) {
		super();
		this.id = id;
		this.userName = userName;
		this.scenectId=scenectId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getScenectId() {
		return scenectId;
	}

	public void setScenectId(Integer scenectId) {
		this.scenectId = scenectId;
	}
}
