package cn.jsprun.meeting;

import java.util.Date;

public class TcRoom {
	private Integer id;
	private String roomName;
	private Integer roomMaxUsers;
	private Integer autoMic;
	private Integer visitor;
	private String rtmptURL;
	private String note;
	private Date createTime;
	private Integer hostID;
	private String roomImageURL;
	private Integer status;
	private String period;
	private Integer roomTest;
	private String participantsIDs;
	private Integer creator;
	private Integer roomMaxVideos;

	public TcRoom() {
		super();
	}

	public TcRoom(Integer id, String roomName, Integer roomMaxUsers,
			Integer autoMic, Integer visitor, String rtmptURL, String note,
			Date createTime, Integer hostID, String roomImageURL,
			Integer status, String period, Integer roomTest,
			String participantsIDs, Integer creator, Integer roomMaxVideos) {
		super();
		this.id = id;
		this.roomName = roomName;
		this.roomMaxUsers = roomMaxUsers;
		this.autoMic = autoMic;
		this.visitor = visitor;
		this.rtmptURL = rtmptURL;
		this.note = note;
		this.createTime = createTime;
		this.hostID = hostID;
		this.roomImageURL = roomImageURL;
		this.status = status;
		this.period = period;
		this.roomTest = roomTest;
		this.participantsIDs = participantsIDs;
		this.creator = creator;
		this.roomMaxVideos = roomMaxVideos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Integer getRoomMaxUsers() {
		return roomMaxUsers;
	}

	public void setRoomMaxUsers(Integer roomMaxUsers) {
		this.roomMaxUsers = roomMaxUsers;
	}

	public Integer getAutoMic() {
		return autoMic;
	}

	public void setAutoMic(Integer autoMic) {
		this.autoMic = autoMic;
	}

	public Integer getVisitor() {
		return visitor;
	}

	public void setVisitor(Integer visitor) {
		this.visitor = visitor;
	}

	public String getRtmptURL() {
		return rtmptURL;
	}

	public void setRtmptURL(String rtmptURL) {
		this.rtmptURL = rtmptURL;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getHostID() {
		return hostID;
	}

	public void setHostID(Integer hostID) {
		this.hostID = hostID;
	}

	public String getRoomImageURL() {
		return roomImageURL;
	}

	public void setRoomImageURL(String roomImageURL) {
		this.roomImageURL = roomImageURL;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Integer getRoomTest() {
		return roomTest;
	}

	public void setRoomTest(Integer roomTest) {
		this.roomTest = roomTest;
	}

	public String getParticipantsIDs() {
		return participantsIDs;
	}

	public void setParticipantsIDs(String participantsIDs) {
		this.participantsIDs = participantsIDs;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Integer getRoomMaxVideos() {
		return roomMaxVideos;
	}

	public void setRoomMaxVideos(Integer roomMaxVideos) {
		this.roomMaxVideos = roomMaxVideos;
	}
}
