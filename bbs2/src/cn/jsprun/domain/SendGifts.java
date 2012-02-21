package cn.jsprun.domain;

public class SendGifts {
	private Integer id;
	private Integer sendId;
	private Integer receiveId;
	private Integer presentId;
	private String message;
	private Integer sendTime;

	public SendGifts(){
		
	}
	
	public SendGifts(Integer id, Integer sendId, Integer receiveId,
			Integer presentId, String message, Integer sendTime) {
		super();
		this.id = id;
		this.sendId = sendId;
		this.receiveId = receiveId;
		this.presentId = presentId;
		this.message = message;
		this.sendTime = sendTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSendId() {
		return sendId;
	}

	public void setSendId(Integer sendId) {
		this.sendId = sendId;
	}

	public Integer getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Integer receiveId) {
		this.receiveId = receiveId;
	}

	public Integer getPresentId() {
		return presentId;
	}

	public void setPresentId(Integer presentId) {
		this.presentId = presentId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getSendTime() {
		return sendTime;
	}

	public void setSendTime(Integer sendTime) {
		this.sendTime = sendTime;
	}

}
