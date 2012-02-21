package cn.jsprun.vo;

public class Sendgifts_sr {
	private Integer id;
	private String present;
	private String message;
	private Integer sendTime;
    private String sendNmae;
    private String receiveName;
    
    public Sendgifts_sr(){
    	
    }
	public Sendgifts_sr(Integer id, String present, String message, Integer sendTime,
			String sendNmae, String receiveName) {
		super();
		this.id = id;
		this.present = present;
		this.message = message;
		this.sendTime = sendTime;
		this.sendNmae = sendNmae;
		this.receiveName = receiveName;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getSendNmae() {
		return sendNmae;
	}
	public void setSendNmae(String sendNmae) {
		this.sendNmae = sendNmae;
	}
	public String getReceiveName() {
		return receiveName;
	}
	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}
	public String getPresent() {
		return present;
	}
	public void setPresent(String present) {
		this.present = present;
	}
}
