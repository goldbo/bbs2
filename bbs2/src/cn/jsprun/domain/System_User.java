package cn.jsprun.domain;

public class System_User {
	private String user_account;
	private String group_name;
	private String password;
	private String firstname;
	private String lastname;
	private String company;
	private String department;
	private String email;
	public System_User(){
		
	}
	
	public System_User(String user_account, String group_name, String password,
			String firstname, String lastname, String company,
			String department, String email) {
		super();
		this.user_account = user_account;
		this.group_name = group_name;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.company = company;
		this.department = department;
		this.email = email;
	}
	public String getUser_account() {
		return user_account;
	}
	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
