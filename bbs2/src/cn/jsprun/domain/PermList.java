package cn.jsprun.domain;

public class PermList implements java.io.Serializable{
	private static final long serialVersionUID = 4578195197402292593L;
	private Integer id;
	private Integer tid;
	private String account;
	private String email;
	private Integer issendemail;
	
	public PermList(Integer id, Integer tid, String account, String email,Integer issendemail) {
		super();
		this.id = id;
		this.tid = tid;
		this.account = account;
		this.email = email;
		this.issendemail=issendemail;
	}
	public PermList(){
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getIssendemail() {
		return issendemail;
	}
	public void setIssendemail(Integer issendemail) {
		this.issendemail = issendemail;
	}
}
