package cn.jsprun.domain;

public class Comment implements java.io.Serializable{
	private static final long serialVersionUID = -8754146119498048496L;
	private Integer id;
	private Integer threadId;
	private String commentName;
	private Integer clickTimes;
	private String img;

	public Comment(){
		
	}
	
	public Comment(Integer id, Integer threadId, String commentName,
			Integer clickTimes,String img) {
		this.id = id;
		this.threadId = threadId;
		this.commentName = commentName;
		this.clickTimes = clickTimes;
		this.img=img;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getThreadId() {
		return threadId;
	}

	public void setThreadId(Integer threadId) {
		this.threadId = threadId;
	}

	public String getCommentName() {
		return commentName;
	}

	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}

	public Integer getClickTimes() {
		return clickTimes;
	}

	public void setClickTimes(Integer clickTimes) {
		this.clickTimes = clickTimes;
	}
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
