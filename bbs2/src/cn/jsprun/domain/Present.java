package cn.jsprun.domain;

public class Present {
	private Integer id;
	private String name;
	private String presentUrl;
	private Integer creatTime;
	private Integer creator;
	private String message;

	public Present(){
		
	}

	public Present(Integer id, String name, String presentUrl,
			 Integer creatTime, Integer creator,String message) {
		super();
		this.id = id;
		this.name = name;
		this.presentUrl = presentUrl;		
		this.creatTime = creatTime;
		this.creator = creator;
		this.message=message;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPresentUrl() {
		return presentUrl;
	}

	public void setPresentUrl(String presentUrl) {
		this.presentUrl = presentUrl;
	}

	public Integer getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Integer creatTime) {
		this.creatTime = creatTime;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
