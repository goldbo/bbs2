package cn.vcity.wbase.common.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import org.apache.struts.upload.FormFile;

import cn.vcity.web.form.VcityResourceForm;

public class UploadUtil {

	// 数据库上传
	private static final String DATABASE_DEST = "database";

	// 文件上传
	private static final String FILE_DEST = "file";

	// 最大限制
	private static final int MAX_SIZE = 1024 * 1024 * 30;

	// 文件类型
	private static final String[] TYPES = { "xls" };// { ".jpg", ".gif",

	// ".zip",".rar" };

	// 文件保存
	public static void saveFile(String fileName, byte[] fileData, int size,

	String dest) throws FileNotFoundException, IOException {
		// 文件大小检查
		if (!checkSize(size)) {

			throw new IOException(size + " is too large !");

		}
		// 文件类型检查
		// if (!checkType(fileName)) {
		//
		// throw new IOException("Unvaildate type !");
		//
		// }
		// 保存到数据库
		if (dest.equals(DATABASE_DEST)) {

			saveToDb(fileName, fileData);

		}
		// 文件保存
		if (dest.equals(FILE_DEST)) {

			saveToFile(fileName, fileData);

		}

	}

	private static void saveToDb(String fileName, byte[] fileData) {

	}

	// 文件保存
	private static void saveToFile(String fileName, byte[] fileData)

	throws FileNotFoundException, IOException {

		/**
		 * 
		 * FileOutputStream(File file) 创建一个向指定 File 对象表示的文件中写入数据的文件输出流。
		 * FileOutputStream(File file, boolean append) 创建一个向指定 File
		 * 对象表示的文件中写入数据的文件输出流。
		 */
		OutputStream o = new FileOutputStream(fileName);

		/**
		 * write(byte[] b) 将 b.length 个字节从指定 byte 数组写入此文件输出流中。
		 * o.write(fileData);
		 */
		try {
			o.write(fileData);
			o.close();
		} catch (Exception e) {
			o.close();
		}

	}

	// 删除文件
	public static void delFile(String fileName, String dest)

	throws NullPointerException, SecurityException {

		if (dest.equals(DATABASE_DEST)) {

			delFromDb(fileName);

		}

		if (dest.equals(FILE_DEST)) {

			delFromFile(fileName);

		}

	}

	private static void delFromDb(String fileName) {

	}

	// 文件夹文件删除
	private static void delFromFile(String fileName)

	throws NullPointerException, SecurityException {

		File file = new File(fileName);

		// delete()
		// 删除此抽象路径名表示的文件或目录。
		// exists()
		// 测试此抽象路径名表示的文件或目录是否存在。
		if (file.exists())

			file.delete();

	}

	// 检查文件大小
	private static boolean checkSize(int size) {

		if (size > MAX_SIZE)

			return false;

		return true;

	}

	// 检查文件类型
	public static boolean checkType(String fileName) {

		for (int i = 0; i < TYPES.length; i++) {

			// toLowerCase()
			// 使用默认语言环境的规则将此 String 中的所有字符都转换为小写。
			if (fileName.toLowerCase().endsWith(TYPES[i])) {

				return true;

			}

		}

		return false;

	}

	public static String uploadFile(UploadFileForm fileForm, String saveFilePath)
			throws NullPointerException, IOException {
		// Struts文件上传实例，封装上传文件信息
		FormFile file = fileForm.getFile();
		String fileName = "";
		File savedir = new File(saveFilePath);// 构建文件目录以及目录文件

		// 如果目录不存在，则新建
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		if (file != null) {
			// 上传文件类型
			// String contentType = file.getContentType();
			// 上传文件名称
			fileName = file.getFileName();

			// 上传文件大小
			int fileSize = file.getFileSize();
			// 上传文件字节
			byte[] fileDate = file.getFileData();

			if (fileSize > 0) {

				// 获取上传文件的扩展名
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

				if (fileForm.isRename()) {
					// 用UUID产生文件名
					fileName = UUID.randomUUID().toString() + "." + ext; // 重命名文件
				}
				// 文件保存
				saveFile(saveFilePath + fileName, fileDate, fileSize, "file");

				file.destroy();

			} else {
				return null;
			}
		}
		return fileName;
	}

	/**
	 * 为了样式显示，参照上面写的虚拟资源上传
	 */
	public static String myUploadFile(FormFile fileForm, String saveFilePath)
			throws NullPointerException, IOException {
		// Struts文件上传实例，封装上传文件信息
		FormFile file = fileForm;
		String fileName = "";
		File savedir = new File(saveFilePath);// 构建文件目录以及目录文件

		// 如果目录不存在，则新建
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		if (file != null) {
			// 上传文件类型
			// String contentType = file.getContentType();
			// 上传文件名称
			fileName = file.getFileName();

			// 上传文件大小
			int fileSize = file.getFileSize();
			// 上传文件字节
			byte[] fileDate = file.getFileData();

			if (fileSize > 0) {

				// 获取上传文件的扩展名
				String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

				
					// 用UUID产生文件名
				fileName = UUID.randomUUID().toString() + "." + ext; // 重命名文件
				
				// 文件保存
				saveFile(saveFilePath + fileName, fileDate, fileSize, "file");

				file.destroy();

			} else {
				return null;
			}
		}
		return fileName;
	}
	
	//上传资源图片
	public static String myUploadFileImage(FormFile fileForm, String saveFilePath,String saveFileName)
			throws NullPointerException, IOException {
		// Struts文件上传实例，封装上传文件信息
		FormFile file = fileForm;
		String fileName = "";
		File savedir = new File(saveFilePath);// 构建文件目录以及目录文件

		// 如果目录不存在，则新建
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		if (file != null) {
			// 上传文件类型
			// String contentType = file.getContentType();
			// 上传文件名称
			fileName = file.getFileName();

			// 上传文件大小
			int fileSize = file.getFileSize();
			// 上传文件字节
			byte[] fileDate = file.getFileData();

			if (fileSize > 0) {
				// 文件保存
				saveFileName = saveFilePath+saveFileName;
				File isExistsFile = new File(saveFileName);
				if (isExistsFile.exists()) {
					isExistsFile.delete();
				}
				saveFile(saveFileName, fileDate, fileSize, "file");

				file.destroy();

			} else {
				return null;
			}
		}
		return fileName;
	}
}
