package cn.vcity.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Members;
import cn.vcity.model.VcityAccessTotal;
import cn.vcity.model.VcityDownloadInfo;
import cn.vcity.model.VcityResCommon;
import cn.vcity.model.VcityResource;
import cn.vcity.service.VcityResCommonService;
import cn.vcity.service.VcityResTypeService;
import cn.vcity.service.VcityResourceService;
import cn.vcity.service.VcityResourcesService;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.helper.PagerHelper;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.wbase.common.vo.page.Pager;
import cn.vcity.web.form.VcityResourceForm;
/**
 * @Title: 
 * @Package com.wingo.vcity.web.action
 * @Description: 虚拟资源的Action类
 * @author hsw
  * @version V2.0
 */
public class VcityResourcesAction extends BaseAction {
	
	private VcityResourcesService vcityResourcesService = (VcityResourcesService) SpringHelper.getBean("vcityResourcesService");
	private VcityResourceService vcityResourceService = (VcityResourceService) SpringHelper.getBean("vcityResourceService");
	private VcityResTypeService vcityResTypeService = (VcityResTypeService) SpringHelper.getBean("vcityResTypeService");
	private VcityResCommonService vcityResCommonService = (VcityResCommonService) SpringHelper.getBean("vcityResCommonService");
	private static VcityResourceForm vcityResourceForm = null;
	
	/**
	 * 产品中心首页
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showVcityResourcesIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		
		/* 当访问体验中心时，如果用户已登录，记录一条登录访问数据，用于统计*/
		if (uinfo!=null) {
			String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			VcityAccessTotal accessTotal = vcityResourcesService.findAccessTotalByDateUserName(date,uinfo.getUsername());
			if (accessTotal==null) {
				VcityAccessTotal vcityAccessTotal =new VcityAccessTotal();
				vcityAccessTotal.setAccessTime(date);
				vcityAccessTotal.setAccessIp(request.getRemoteAddr());
				vcityAccessTotal.setVcityResource(new VcityResource(-1));
				vcityAccessTotal.setUserName(uinfo.getUsername());
				vcityResourcesService.saveAccessTotal(vcityAccessTotal);
			}			
		}
						
		vcityResourceForm = (VcityResourceForm)form;
		//最热门应用与皮肤
		List hotAppList=vcityResourcesService.findHotResources(1);
		List hotSkinList=vcityResourcesService.findHotResources(2);
		//推荐度最高的应用与皮肤
		List recommendAppList =vcityResourcesService.findRecommendResources(1);

		List recommendSkinList =vcityResourcesService.findRecommendResources(2);
	//	List newList = vcityResourcesService.findNewResources();  //最新资源
		for (int i = 0; i < hotAppList.size(); i++) {
			
			VcityResource vcityResource =(VcityResource)hotAppList.get(i);
			vcityResource.setUp(vcityResCommonService.getResCommonTotal(vcityResource.getId(), 1));
			vcityResource.setDown(vcityResCommonService.getResCommonTotal(vcityResource.getId(), 2));
			hotAppList.set(i, vcityResource);
		}

		for (int i = 0; i < hotSkinList.size(); i++) {
			VcityResource vcityResource =(VcityResource)hotSkinList.get(i);
			vcityResource.setUp(vcityResCommonService.getResCommonTotal(vcityResource.getId(), 1));
			vcityResource.setDown(vcityResCommonService.getResCommonTotal(vcityResource.getId(), 2));
			hotSkinList.set(i, vcityResource);
		}
	/*	for (int i = 0; i < newList.size(); i++) {
			VcityResource vcityResource =(VcityResource)newList.get(i);
			vcityResource.setUp(vcityResCommonService.getResCommonTotal(vcityResource.getId(), 1));
			vcityResource.setDown(vcityResCommonService.getResCommonTotal(vcityResource.getId(), 2));
			newList.set(i, vcityResource);
		}*/
		for (int i = 0; i < recommendAppList.size(); i++) {
			VcityResource vcityResource =(VcityResource)recommendAppList.get(i);
			vcityResource.setUp(vcityResCommonService.getResCommonTotal(vcityResource.getId(), 1));
			vcityResource.setDown(vcityResCommonService.getResCommonTotal(vcityResource.getId(), 2));
			recommendAppList.set(i, vcityResource);
		}
		for (int i = 0; i < recommendSkinList.size(); i++) {
			VcityResource vcityResource =(VcityResource)recommendSkinList.get(i);
			vcityResource.setUp(vcityResCommonService.getResCommonTotal(vcityResource.getId(), 1));
			vcityResource.setDown(vcityResCommonService.getResCommonTotal(vcityResource.getId(), 2));
			recommendSkinList.set(i, vcityResource);
		}
		
		request.setAttribute("hotAppList", hotAppList);
		request.setAttribute("hotSkinList", hotSkinList);
		request.setAttribute("recommendAppList", recommendAppList);
		request.setAttribute("recommendSkinList", recommendSkinList);

		return mapping.findForward("index");
		
	}
	
	/**
	 * 显示某产品详细信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showVcityResourceInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		
	

		vcityResourceForm = (VcityResourceForm) form;
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		List hotList = vcityResourcesService.findHotResources(vcityResourceForm.getResType());
		VcityResource resource = vcityResourcesService.findVcityResourceByid(vcityResourceForm.getId());
		if (resource==null) {
			//跳到通用404页面
		}
		
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		/*记录访问该资源的IP地址 */
		VcityAccessTotal accessTotal = vcityResourcesService.findAccessTotalByDateIPResid(date,vcityResourceForm.getId(),request.getRemoteAddr() );
		if (accessTotal==null) {
			VcityAccessTotal vcityAccessTotal =new VcityAccessTotal();
			vcityAccessTotal.setAccessTime(date);
			vcityAccessTotal.setAccessIp(request.getRemoteAddr());
			vcityAccessTotal.setVcityResource(new VcityResource(vcityResourceForm.getId()));
			vcityResourcesService.saveAccessTotal(vcityAccessTotal);
		}	
		
		resource.setUp(vcityResCommonService.getResCommonTotal(vcityResourceForm.getId(), 1));
		resource.setDown(vcityResCommonService.getResCommonTotal(vcityResourceForm.getId(), 2));

		/*List resourceDatas = vcityResourceService.getVcityResourceListByOther(
				vcityResourceForm, 10, 0);*/
		VcityResourceForm resourceForm = new VcityResourceForm();
		resourceForm.setResType(vcityResourceForm.getResType());
	//	List allDatas = vcityResourceService.findResourceAll(resourceForm);
		List allDatas = vcityResourcesService.findNewResources(vcityResourceForm); 
		// 页面传过来的随机数，防止刷新后重复提交
		String newRadomID = (String) request.getParameter("radomID");
		String oldRadomID = (String) request.getSession().getAttribute("radomID");

		if (request.getParameter("todo") != null
				&& request.getParameter("todo").equals("saveCommon")) {
			
			VcityResCommon vcityResCommon = new VcityResCommon();
			vcityResCommon.setIp(request.getRemoteAddr());
			vcityResCommon.setGrade(0);
			vcityResCommon.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			if (uinfo != null) {
				vcityResCommon.setUserName(uinfo.getUsername());
			}

			if (vcityResourceForm.getFlag().length() > 0
					&& !newRadomID.equals(oldRadomID)) {
				vcityResCommon.setCommon(vcityResourceForm.getFlag());
				vcityResCommon.setVcityResource(resource);

				vcityResCommonService.saveVcityResCommon(vcityResCommon);
				request.getSession().setAttribute("radomID", newRadomID);
			}
			vcityResourceForm.setFlag("");
		}
		int total = vcityResCommonService.getResCommonTotal(vcityResourceForm.getId(), 0);
		Pager pager = null;
		try {
			pager = PagerHelper.getPager(request, 15, total);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List commonList = vcityResCommonService.showVcityResComonList(vcityResourceForm.getId(), pager.getPageSize(), pager.getStartRow());
		
		request.getSession().setAttribute("resource", resource);
		request.setAttribute("total", total);
		request.setAttribute("allDatas", allDatas);
		request.setAttribute("commonList", commonList);
	//	request.setAttribute("datas", resourceDatas);
		request.setAttribute("hotList", hotList);
		request.setAttribute("IP", request.getRemoteAddr());
		request.setAttribute("vcityResourceForm", vcityResourceForm);
		request.setAttribute("pager", pager);
		return mapping.findForward("info");

	}
	
	/**
	 * 产品列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showVcityResourcesList(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		List typeList = vcityResTypeService.findAllVcityResType();
		vcityResourceForm = (VcityResourceForm) form;
		
		List datas = null;
		List allDatas = null;
		int total = 0;// 总记录数
		Pager pager = null;// 分页信息
		total = vcityResourcesService.getTotalByOther(vcityResourceForm);
		try {
			pager = PagerHelper.getPager(request,6, total);
		} catch (IOException e) {
			e.printStackTrace();
		}
		datas = vcityResourceService.getVcityResourceListByOther(
				vcityResourceForm, pager.getPageSize(), pager.getStartRow());
		//allDatas = vcityResourceService.findResourceAll(vcityResourceForm);
		allDatas = vcityResourcesService.findNewResources(vcityResourceForm);    //最新10条资源
		
		List hotList = vcityResourcesService.findHotResources(vcityResourceForm.getResType());
		request.setAttribute("datas", datas);
		request.setAttribute("allDatas", allDatas);
		request.setAttribute("pager", pager);
		request.setAttribute("vcityResourceForm", vcityResourceForm);
		request.setAttribute("typeList", typeList);
		request.setAttribute("hotList", hotList);
		return mapping.findForward("list");
	}
	
	/**
	 * 手机对比列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward VcityResourcesCompareList(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		vcityResourceForm = (VcityResourceForm)form;
		List datas = null;
		List typeList =null;
		int total = 0;// 总记录数
		Pager pager = null;// 分页信息
		total = vcityResourcesService.getTotalByOther(vcityResourceForm);
		try {
			pager = PagerHelper.getPager(request,8, total);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		datas = vcityResourceService.getVcityResourceListByOther(
				vcityResourceForm, pager.getPageSize(), pager.getStartRow());
		request.setAttribute("datas", datas);
		request.setAttribute("pager", pager);
		String typeString="";
		String resid="";
		resid=request.getParameter("resid");
		typeString= request.getParameter("type");
		if (typeString!=null&&!typeString.equals("")) {
			typeList = vcityResourcesService.findResourcesByType(Integer.parseInt(typeString));
			request.getSession().setAttribute("typeList", typeList);
		}
		if (resid!=null&&!resid.equals("")) {
			VcityResource resource=vcityResourcesService.findVcityResourceByid(Integer.parseInt(resid));
			request.getSession().setAttribute("resource", resource);
		}
		request.setAttribute("vcityResourceForm", vcityResourceForm);
		return mapping.findForward("compareList");
		
	}
	/**
	 * 与某一款手机进行对比
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward VcityResourcesCompare(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		vcityResourceForm = (VcityResourceForm)form;
		String resid2="";
		String resid="";
		resid=request.getParameter("resid");
		resid2= request.getParameter("resid2");
		if (resid2!=null&&!resid2.equals("")) {
			VcityResource compareresource = vcityResourcesService.findVcityResourceByid(Integer.parseInt(resid2));
			request.getSession().setAttribute("compareresource", compareresource);
		}
		if (resid!=null&&!resid.equals("")) {
			VcityResource resource=vcityResourcesService.findVcityResourceByid(Integer.parseInt(resid));
			request.getSession().setAttribute("resource", resource);
		}
		
		return mapping.findForward("compare");	
	}
	/**
	 * 跳转到客户端下载页
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDownClient(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("downloadClient");
	}
	
	/**
	 *  体验客户端下载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward downClient(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		String fileName = "e声网应用体验平台_60.exe";
		String downType = request.getParameter("downType");   //下载类型   1为入门 2为中级 3为专业
		if (downType!=null) {
			if (downType.equals("2")) {
				fileName = "e声网应用体验平台_150.exe";
			}
			if (downType.equals("3")) {
				fileName = "e声网应用体验平台_300.exe";
			}
		}
		
		String filePath = request.getSession().getServletContext().getRealPath("")+"/uploadfile/VCity/client/"+fileName;		
		File file = new File(filePath);
		if (file.exists()) {
			outputFile(request, response,filePath,fileName);	
		} else{
			System.out.println("找不到文件!");
		}
		return null;
	}
	
	/**
	 * 下载资源
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	
	public ActionForward downloadResource(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
		vcityResourceForm = (VcityResourceForm) form;
		VcityResource vcityResource = vcityResourcesService.findVcityResourceByid(vcityResourceForm.getId());

		//新增下载记录
		VcityDownloadInfo downloadInfo = new VcityDownloadInfo();
		downloadInfo.setDownloadIp(request.getRemoteAddr());
		downloadInfo.setDownloadTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		downloadInfo.setDownloadType(1);
		downloadInfo.setVcityResource(vcityResource);
		vcityResourcesService.saveDownloadInfo(downloadInfo);
		
		String filePath = request.getSession().getServletContext().getRealPath("")+"/";
		
		if (vcityResource.getModuleFileName()!=null&&!vcityResource.getModuleFileName().equals("")) {
			if (vcityResource.getResType()==1) {
				filePath = filePath+vcityResource.getViewAppFileName();
			} else {
				filePath = filePath+vcityResource.getViewSkinFileName();
			}
			File file = new File(filePath);
			if (file.exists()) {   //文件存在
				String ext = vcityResource.getModuleFileName();
				String fileName = vcityResource.getResName()+ "."+ext.substring(ext.lastIndexOf(".")+1);
				outputFile(request, response,filePath,fileName);				
			} else {
				System.out.println("找不到文件！");
			}
		}	
		return null;
	}
	
	/**
	 * 导出文件
	 * @param request
	 * @param response
	 * @param filePath
	 * @param fileName
	 */
	public static void outputFile(HttpServletRequest request,HttpServletResponse response,
			String filePath,String fileName){
		BufferedInputStream bufferedInputStream = null;
		OutputStream  bufferedOutputStream = null;
		try {

			// 由于自带的文件下载选择框，使用的是application/x-www-form-urlencoded格式编码，
			// 需要把把字符串转成application/x-www-form-urlencoded
			// 格式，这里采用UTF-8编码将字符串转到urlencoded 格式
			String stringFileName = "";
			String agent = request.getHeader("User-Agent");
			boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
			if (isMSIE) {
				stringFileName = java.net.URLEncoder.encode(fileName,"UTF-8");  //IE
			} else {
				stringFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");  //fireFox
			}
			response.reset();
			// 这里contentType的字符编码要与上面一致，才不会出现乱码
			response.setContentType("application/x-msdownload;charset=UTF-8");// 通用的MIME设置
			// stringFileName
			response.setHeader("Content-Disposition", "attachment; filename="+ stringFileName);
			bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath));
			bufferedOutputStream = new BufferedOutputStream(response
					.getOutputStream());
			int read = 0;
			byte[] buffer = new byte[8192];
			while ((read = bufferedInputStream.read(buffer, 0, buffer.length)) != -1) {
				bufferedOutputStream.write(buffer, 0, read);				
			}
			
			bufferedOutputStream.flush();

		} catch (Exception e) {
			//e.printStackTrace();   //IE浏览器保存后点击取消后不捕获异常
		} finally {
			try {
				if (bufferedInputStream != null)
					bufferedInputStream.close();
				if (bufferedOutputStream != null)
					bufferedOutputStream.close();
			} catch (IOException ioe2) {
				//ioe2.printStackTrace();
			}
		}
	}
	
	/**
	 * 3D场景弹出-资源列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showResourceList3D(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) {
		vcityResourceForm = (VcityResourceForm) form;
		
		List datas = null;
		int total = 0;// 总记录数
		Pager pager = null;// 分页信息
		total = vcityResourcesService.getTotalByOther(vcityResourceForm);
		try {
			pager = PagerHelper.getPager(request,6, total);
		} catch (IOException e) {
			e.printStackTrace();
		}
		datas = vcityResourceService.getVcityResourceListByOther(
				vcityResourceForm, pager.getPageSize(), pager.getStartRow());
		
		request.setAttribute("datas", datas);
		request.setAttribute("pager", pager);
		request.setAttribute("vcityResourceForm", vcityResourceForm);
		return mapping.findForward("list3D");
	}
	
	/**
	 * 3D场景弹出-显示某产品详细信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showResourceInfo3D(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		
		vcityResourceForm = (VcityResourceForm) form;
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		VcityResource resource = vcityResourcesService.findVcityResourceByid(vcityResourceForm.getId());
		if (resource==null) {
			//跳到通用404页面
		}
		
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		/*记录访问该资源的IP地址 */
		VcityAccessTotal accessTotal = vcityResourcesService.findAccessTotalByDateIPResid(date,vcityResourceForm.getId(),request.getRemoteAddr() );
		if (accessTotal==null) {
			VcityAccessTotal vcityAccessTotal =new VcityAccessTotal();
			vcityAccessTotal.setAccessTime(date);
			vcityAccessTotal.setAccessIp(request.getRemoteAddr());
			vcityAccessTotal.setVcityResource(new VcityResource(vcityResourceForm.getId()));
			vcityResourcesService.saveAccessTotal(vcityAccessTotal);
		}	
		
		resource.setUp(vcityResCommonService.getResCommonTotal(vcityResourceForm.getId(), 1));
		resource.setDown(vcityResCommonService.getResCommonTotal(vcityResourceForm.getId(), 2));
 
		// 页面传过来的随机数，防止刷新后重复提交
		String newRadomID = (String) request.getParameter("radomID");
		String oldRadomID = (String) request.getSession().getAttribute("radomID");

		if (request.getParameter("todo") != null
				&& request.getParameter("todo").equals("saveCommon")) {
			
			VcityResCommon vcityResCommon = new VcityResCommon();
			vcityResCommon.setIp(request.getRemoteAddr());
			vcityResCommon.setGrade(0);
			vcityResCommon.setCreateTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			if (uinfo != null) {
				vcityResCommon.setUserName(uinfo.getUsername());
			}

			if (vcityResourceForm.getFlag().length() > 0
					&& !newRadomID.equals(oldRadomID)) {
				vcityResCommon.setCommon(vcityResourceForm.getFlag());
				vcityResCommon.setVcityResource(resource);

				vcityResCommonService.saveVcityResCommon(vcityResCommon);
				request.getSession().setAttribute("radomID", newRadomID);
			}
			vcityResourceForm.setFlag("");
		}
		int total = vcityResCommonService.getResCommonTotal(vcityResourceForm.getId(), 0);
		Pager pager = null;
		try {
			pager = PagerHelper.getPager(request, 15, total);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List commonList = vcityResCommonService.showVcityResComonList(vcityResourceForm.getId(), pager.getPageSize(), pager.getStartRow());
		
		request.getSession().setAttribute("resource", resource);
		request.setAttribute("total", total);
		request.setAttribute("commonList", commonList);
		request.setAttribute("IP", request.getRemoteAddr());
		request.setAttribute("vcityResourceForm", vcityResourceForm);
		request.setAttribute("pager", pager);
		return mapping.findForward("info3D");

	}
}
