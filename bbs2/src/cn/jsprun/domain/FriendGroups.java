package cn.jsprun.domain;

public class FriendGroups implements java.io.Serializable {

	private Integer id;
	private String groupName;
	private Integer createTime;
	private Integer uid;
	private Integer type;
	private Integer updateFlag;

	public FriendGroups() {

	}

	public FriendGroups(Integer id, String groupName, Integer createTime,
			Integer uid,Integer type, Integer updateFlag) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.createTime = createTime;
		this.uid = uid;
		this.type=type;
		this.updateFlag = updateFlag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(Integer updateFlag) {
		this.updateFlag = updateFlag;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
