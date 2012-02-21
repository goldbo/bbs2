package cn.vcity.model;

public class VcityResoprate implements java.io.Serializable {

	private static final long serialVersionUID = 2018887478795035796L;
	private int id ;
	private  String resoprateName;
	private String resoCode;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResoCode() {
		return resoCode;
	}
	public void setResoCode(String resoCode) {
		this.resoCode = resoCode;
	}
	public String getResoprateName() {
		return resoprateName;
	}
	public void setResoprateName(String resoprateName) {
		this.resoprateName = resoprateName;
	}
	public VcityResoprate(String resoprateName, String resoCode) {
		super();
		this.resoprateName = resoprateName;
		this.resoCode = resoCode;
	}
	public VcityResoprate() {
		super();
	}
	
	
	
	
	
}
