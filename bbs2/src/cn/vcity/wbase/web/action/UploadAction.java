package cn.vcity.wbase.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import cn.vcity.wbase.common.Constants;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.file.FileForm;
import cn.vcity.wbase.common.file.UploadFileForm;
import cn.vcity.wbase.common.file.UploadUtil;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.wbase.common.vo.ConfigInfoVO;
import cn.vcity.wbase.service.WbaseFilesService;

/**
 * @Title: UploadAction.java
 * @Package com.wingo.wbase.web.action
 * @Description: TODO(用一句话描述该文件做什么)
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-1-7 下午02:42:14
 * @version V1.0
 */
public class UploadAction extends BaseAction {
	private WbaseFilesService wbaseFilesService = (WbaseFilesService) SpringHelper
			.getBean("wbaseFilesService");

	/**
	 * 上传图片
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadImage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		UploadFileForm fileForm = (UploadFileForm) form;
		// 文件保存系统路径
		String saveDirPath = fileForm.getFilePath();

		// Struts文件上传实例，封装上传文件信息
		FormFile file = fileForm.getFile();
		String fileName = file.getFileName();

		// 获取文件扩展名
		String extensionName = fileName
				.substring(fileName.lastIndexOf(".") + 1);
		String dirType = fileForm.getDirType();
		// 0:不建目录, 1:按天存入目录, 2:按月存入目录, 3:按扩展名存目录.建议使用按天存。
		String fileFolder = "";
		if (dirType.equalsIgnoreCase("1"))
			fileFolder = new SimpleDateFormat("yyyyMMdd").format(new Date());

		if (dirType.equalsIgnoreCase("2"))
			fileFolder = new SimpleDateFormat("yyyyMM").format(new Date());

		if (dirType.equalsIgnoreCase("3"))
			fileFolder = extensionName.toLowerCase();

		if (!dirType.equalsIgnoreCase("0")) {
			saveDirPath = saveDirPath + fileFolder + "/"; // 文件存储的相对路径
		}

		String saveFilePath = request.getSession().getServletContext()
				.getRealPath("")
				+ saveDirPath; // 文件存储在容器中的绝对路径

		fileName = UploadUtil.uploadFile(fileForm, saveFilePath);

		String msg = "上传成功！";
		if (fileName == null || fileName == "") {
			msg = "上传失败！";
			request.setAttribute(Constants.ALERT_MESSAGE, msg);
			return mapping.findForward("uploadImageFailure");
		} else {
			msg = "上传成功！";
			this.returnJsMsg(response, null, "window.returnValue='"
					+ saveDirPath + fileName + "';window.close();");
			return null;
		}
	}

	/**
	 * 已上传的文件处理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward deleteFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Object id = request.getParameter("id");
		if(!id.equals(null)){
			ArrayList<FileForm> fileList = (ArrayList<FileForm>)request.getSession().getAttribute("fileList");
			Integer index = Integer.parseInt(id.toString());
			FileForm file = (FileForm)fileList.get(index);
			
			String ATTACH_PATH = ConfigInfoVO.getProperty("ATTACH_PATH");
			boolean flag = wbaseFilesService.delFromDisk(ATTACH_PATH+file.getFilePath());
			if(flag){
				fileList.remove(file);
				request.getSession(false).setAttribute("fileList", fileList);
			}
		}
		return mapping.findForward("deleteFile");
	}
}
