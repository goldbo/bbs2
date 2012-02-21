package cn.jsprun.domain;

import java.io.Serializable;

public class AnswerTemplate implements Serializable {
	private static final long serialVersionUID = 7883093033243273859L;
	private Integer id;
	private String context;
	private Integer creattime;
	private Integer creator;
	
	public AnswerTemplate(){
		
	}
	
	public AnswerTemplate(int id, String context, int creattime, int creator) {
		this.id = id;
		this.context = context;
		this.creattime = creattime;
		this.creator = creator;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Integer getCreattime() {
		return creattime;
	}

	public void setCreattime(Integer creattime) {
		this.creattime = creattime;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}	
	
}
