package cn.vcity.web.form;

import cn.vcity.wbase.common.base.BaseForm;

public class VcityResoprateForm extends BaseForm {

	private static final long serialVersionUID = 2018887478795035796L;
	private int id ;
	private  String resoprateName;
	private String resoCode;
	private int [] selectedRow;
	
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
	public int[] getSelectedRow() {
		return selectedRow;
	}
	public void setSelectedRow(int[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	
	
}
