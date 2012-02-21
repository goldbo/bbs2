package cn.vcity.wbase.common.exception;

public class DaoException extends Exception{
	private int errorcode = 300;
	private String message = "dao error!";
	
	public DaoException(){
	}
	public DaoException(String message){
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
