package cn.vcity.model;

public class VcityProduceOrder  implements java.io.Serializable {
	private static final long serialVersionUID = -8683245413356973610L;
	
	private Integer id;
	private String userName;				//账户名称
	private String code;					//操作码ID
	private VcityResource vcityResource;	//资源
	private VcitySubTask vcitySubTask;		//子任务
	
	public VcityProduceOrder() {
		super();
	}

	public VcityProduceOrder(String userName, String code, VcityResource vcityResource, VcitySubTask vcitySubTask) {
		super();
		this.userName = userName;
		this.code = code;
		this.vcityResource = vcityResource;
		this.vcitySubTask = vcitySubTask;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public VcityResource getVcityResource() {
		return vcityResource;
	}

	public void setVcityResource(VcityResource vcityResource) {
		this.vcityResource = vcityResource;
	}

	public VcitySubTask getVcitySubTask() {
		return vcitySubTask;
	}

	public void setVcitySubTask(VcitySubTask vcitySubTask) {
		this.vcitySubTask = vcitySubTask;
	}
	
	
}
