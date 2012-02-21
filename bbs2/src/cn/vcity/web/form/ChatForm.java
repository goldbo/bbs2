package cn.vcity.web.form;

import org.apache.struts.action.ActionForm;

public class ChatForm extends ActionForm {

	private static final long serialVersionUID = -630181109078755962L;

	private int userType ;
	private String chatKey;
	private String recieverID;
	
	private String userName;
	
	private int taskId;
	

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getRecieverID() {
		return recieverID;
	}

	public void setRecieverID(String recieverID) {
		this.recieverID = recieverID;
	}

	public String getChatKey() {
		return chatKey;
	}

	public void setChatKey(String chatKey) {
		this.chatKey = chatKey;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
