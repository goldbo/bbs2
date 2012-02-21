package cn.jsprun.domain;
import java.io.Serializable;

public class ThemeTemplate implements Serializable{
	private static final long serialVersionUID = 5170020541721831667L;
	private Integer id;
	private Integer uid;
	private Integer ispublic;
	private String subject;
	private String message;

	public ThemeTemplate(){
		
	}
	
	public ThemeTemplate(Integer id, Integer uid, Integer ispublic,
			String subject, String message) {
		super();
		this.id = id;
		this.uid = uid;
		this.ispublic = ispublic;
		this.subject = subject;
		this.message = message;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getIspublic() {
		return ispublic;
	}
	public void setIspublic(Integer ispublic) {
		this.ispublic = ispublic;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
