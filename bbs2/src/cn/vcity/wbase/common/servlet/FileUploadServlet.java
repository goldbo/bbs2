package cn.vcity.wbase.common.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.vcity.wbase.common.file.FileForm;
import cn.vcity.wbase.common.vo.ConfigInfoVO;

public class FileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = -7825355637448948879L;

	// 定义存储所上传的文件的 集合信息
	public static List<FileForm> fileList = new ArrayList<FileForm>();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 设置上传目录
		String fileFolder = new SimpleDateFormat("yyyyMMdd").format(new Date());

		// 获取自定义值信息
		String user_id = request.getParameter("user_id");
		String pass_id = request.getParameter("pass_id");
		System.out.println(user_id+"------"+pass_id);
		// 获取系统上传文件保存路径配置信息
		String ATTACH_PATH = "";
		try {
			ATTACH_PATH = ConfigInfoVO.getProperty("ATTACH_PATH");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 文件保存路径
		String path = ATTACH_PATH + "\\" + fileFolder + "\\";

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		// 磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存缓冲区，超过后写入临时文件
		factory.setSizeThreshold(10240000);
		// 设置临时目录
		factory.setRepository(file);
		// 创建一个ServletFileUpload 实例
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置单个文件的最大上传值
		upload.setFileSizeMax(10002400000l);
		// 设置整个request的最大值
		upload.setSizeMax(10002400000l);
		upload.setHeaderEncoding("UTF-8");

		try {
			List<?> items = upload.parseRequest(request);
			FileItem item = null;
			for (int i = 0; i < items.size(); i++) {
				item = (FileItem) items.get(i);
				// 保存文件
				if (!item.isFormField() && item.getName().length() > 0) {

					FileForm fileForm = new FileForm();
					// 取得字段名
					// String name = item.getFieldName();
					// 取得文件路径名
					String value = item.getName();
					// 为了屏蔽各个浏览器提供的路径不同异常
					int start = value.lastIndexOf("\\");
					// 取得原始文件名
					String fileName = value.substring(start + 1);
					//取得文件大小
					Integer _K = 1024;
					Integer _M = _K*1024;
					Long bitnum = item.getSize();
					String fileSize = "0";
					if(bitnum<_M){
						if(bitnum<_K){
							fileSize = bitnum+"B";
						}else{
							fileSize = (bitnum/_K)+"K";
						}
						
					}else{
						fileSize = (100*bitnum/_M)/100+"M";
					}
					
					// 获取文件扩展名
					String extensionName = fileName.substring(fileName
							.lastIndexOf(".") + 1);
					// 用UUID产生文件名
					String uuidFileName = UUID.randomUUID().toString() + "."
							+ extensionName;

					fileForm.setFileName(fileName);
					fileForm.setCreateTime(sdf.format(new Date()));
					fileForm.setFileExt(extensionName);
					fileForm.setFileSize(fileSize);
					fileForm.setFilePath("/" + fileFolder + "/" + uuidFileName);

					fileList.add(fileForm);

					File saveFile = new File(path, uuidFileName);
					item.write(saveFile);
				}
			}
			// 返回所保存的文件到 session 以便于其他模块提取
			request.getSession(false).setAttribute("fileList", fileList);
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
