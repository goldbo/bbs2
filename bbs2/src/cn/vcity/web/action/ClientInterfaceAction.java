package cn.vcity.web.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.vcity.model.VcityDownloadInfo;
import cn.vcity.model.VcityResource;
import cn.vcity.service.VcityResourceService;
import cn.vcity.service.VcityResourcesService;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.helper.PagerHelper;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.wbase.common.vo.page.Pager;
import cn.vcity.web.form.VcityResourceForm;

public class ClientInterfaceAction extends BaseAction {
	private VcityResourcesService vcityResourcesService = (VcityResourcesService) SpringHelper
			.getBean("vcityResourcesService");

	private VcityResourceService vcityResourceService = (VcityResourceService) SpringHelper
			.getBean("vcityResourceService");
	

	private static VcityResourceForm vcityResourceForm = null;

	/**
	 * 客户端接口--分页、条件列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward resourceList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		vcityResourceForm = (VcityResourceForm) form;
	
		List datas = null;

		int total = 0;// 总记录数
		Pager pager = null;// 分页信息
		total = vcityResourcesService.getTotalByOther(vcityResourceForm);
		try {
			pager = PagerHelper.getPager(request, 6, total);
		} catch (IOException e) {
			e.printStackTrace();
		}
		datas = vcityResourceService.getVcityResourceListByOther(
				vcityResourceForm, pager.getPageSize(), pager.getStartRow());
		request.setAttribute("pager", pager);
		request.setAttribute("datas", datas);
		request.setAttribute("vcityResourceForm", vcityResourceForm);
		return mapping.findForward("clientResourcetList");
	}

	/**
	 * 若资源类型为皮肤时执行该方法，返回存放皮肤目录的所有文件名
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward unZipFileList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String dir = request.getParameter("dir");
		String tempPath = request.getSession().getServletContext().getRealPath("")+"\\";
		List fileList = getFileList(dir,tempPath+dir);
		request.setAttribute("fileList", fileList);
		return mapping.findForward("clientDirFileList");
	}
	
	/**
	 * 得到指定文件夹下的所有文件的路径列表（没有子文件夹）
	 * @param filePath   文件夹路径
	 * @return
	 */
	public static List getFileList(String dir,String filePath){
		List<String> fileList = new ArrayList<String>();
		File file = new File(filePath);
		if (file.exists()&&file.isDirectory()) {
			File subFile[] = file.listFiles();
			for (int i = 0; i < subFile.length; i++) {
				fileList.add(dir+"\\"+subFile[i].getName());			
			}
		}
		return fileList;
	}
	
	/**
	 * 客户端下载统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward clientSaveDownloadInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String resId = request.getParameter("resId");
		if (resId!=null&&!resId.equals("")) {
			VcityDownloadInfo downloadInfo  = new VcityDownloadInfo ();
			downloadInfo.setDownloadIp(request.getRemoteAddr());
			downloadInfo.setDownloadTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			downloadInfo.setDownloadType(2);
			downloadInfo.setVcityResource(new VcityResource(Integer.parseInt(resId)));
			vcityResourcesService.saveDownloadInfo(downloadInfo);
		}
		
		return null;
	}
	
	/**
	 * 返回json格式的资源列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward resourceListJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		vcityResourceForm = (VcityResourceForm) form;
	
		List datas = null;
System.out.println("here");
		int total = 0;// 总记录数
		Pager pager = null;// 分页信息
		total = vcityResourcesService.getTotalByOther(vcityResourceForm);
		try {
			pager = PagerHelper.getPager(request, 6, total);
		} catch (IOException e) {
			e.printStackTrace();
		}
		datas = vcityResourceService.getVcityResourceListByOther(
				vcityResourceForm, pager.getPageSize(), pager.getStartRow());
		JSONArray jsonArray = JSONArray.fromObject(datas);
		JSONObject  jsonArray2 = JSONObject.fromObject(pager);   
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(jsonArray2);
			out.println(jsonArray);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		//request.setAttribute("jsonArray", jsonArray);
		return null;
	}
	
	/**
	 * 返回json格式的单个资源信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward resourceInfoJson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		vcityResourceForm = (VcityResourceForm) form;
		VcityResource vcityResource = vcityResourceService.findVcityResourceById(vcityResourceForm.getId());
		JSONObject  jsonArray = JSONObject.fromObject(vcityResource);    
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(jsonArray);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		request.setAttribute("jsonArray", jsonArray);
		return null;
		//return mapping.findForward("clientJson");
	}
}
