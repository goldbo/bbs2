package cn.vcity.wbase.common.file;


import cn.vcity.wbase.common.base.BaseForm;

public class FileForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4361358780534308939L;
 
	private String fileName = "";
	
	private String filePath = "";
	
	private String fileExt = "";
	
	private String fileSize = "0";
	
	private String createTime = "";
	
	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
 
}
