package cn.vcity.wbase.common.exception;


public class BaseActionException extends Exception{
	private int errorcode = 300;
	private String message = "";
	
	public BaseActionException(){		
	}
	public BaseActionException(String message){	
		setMessage(message);
	}
	public int getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
		
}
