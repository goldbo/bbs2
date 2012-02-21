package cn.vcity.wbase.common.file;

import org.apache.struts.upload.FormFile;

import cn.vcity.wbase.common.base.BaseForm;

public class UploadFileForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4075223053815648779L;

	private FormFile file;
	
	private boolean rename = true;
	
	private String dirType = "1"; 
	// 0:不建目录 1:按天存入目录 2:按月存入目录 3:按扩展名存目录 建议使用按天存

	private String fileName = "";

	private String filePath = "";

	private String createTime = "";
	
	private String[] selectedRow;

	public String[] getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}

	public boolean isRename() {
		return rename;
	}

	public void setRename(boolean rename) {
		this.rename = rename;
	}

	public String getDirType() {
		return dirType;
	}

	public void setDirType(String dirType) {
		this.dirType = dirType;
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

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}

}
