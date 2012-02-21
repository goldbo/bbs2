package cn.vcity.wbase.common.exception;

//
public class BaseServiceException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2895724662862307660L;
	private int errorcode = 200;
	private String message = "";
	
	public BaseServiceException(){		
	}
	public BaseServiceException(String message){
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
