package cn.vcity.wbase.common.file;

import java.util.ArrayList;
import java.util.List;

import cn.vcity.wbase.common.base.BaseForm;

public class MultiUploadFileForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7740919481059809186L;

	private List<UploadFileForm> myFiles;

	public MultiUploadFileForm() {
		myFiles = new ArrayList<UploadFileForm>();
		myFiles.add(new UploadFileForm());
	}

	// 注意这个方法的定义 不加中间的循环是会出错的

	public UploadFileForm getUploadFile(int index) {
		int size = myFiles.size();
		if (index > size - 1) {
			for (int i = 0; i < index - size + 1; i++) {
				myFiles.add(new UploadFileForm());
			}
		}
		return (UploadFileForm) myFiles.get(index);
	}

	public List<UploadFileForm> getMyFiles() {
		return myFiles;
	}

	public void setMyFiles(List<UploadFileForm> myFiles) {
		this.myFiles = myFiles;
	}

}
