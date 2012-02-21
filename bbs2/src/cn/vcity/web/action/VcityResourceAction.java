package cn.vcity.web.action;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Members;
import cn.jsprun.domain.Scene;
import cn.jsprun.service.SceneService;
import cn.jsprun.utils.BeanFactory;
import cn.vcity.model.VcityAccessTotal;
import cn.vcity.model.VcityResource;
import cn.vcity.model.VcitySubTask;
import cn.vcity.model.VcityTempSurvey;
import cn.vcity.service.VcityResCommonService;
import cn.vcity.service.VcityResTypeService;
import cn.vcity.service.VcityResourceAppService;
import cn.vcity.service.VcityResourceService;
import cn.vcity.service.VcityResourcesService;
import cn.vcity.service.VcitySubTaskService;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.file.UploadUtil;
import cn.vcity.wbase.common.helper.PagerHelper;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.wbase.common.helper.ZipFileHelper;
import cn.vcity.wbase.common.vo.page.Pager;
import cn.vcity.web.form.VcityResourceForm;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @Title: VcityResourceAction.java
 * @Package com.wingo.vcity.web.action
 * @Description: 虚拟资源的Action类
 * @author 黄金波 珠海市网佳科技有限公司.
 * @date 2011-7-2 10：34：50
 * @version V1.0
 */
public class VcityResourceAction extends BaseAction {

	private VcityResourceService vcityResourceService = (VcityResourceService) SpringHelper
			.getBean("vcityResourceService");
	
	private VcityResTypeService vcityResTypeService = (VcityResTypeService) SpringHelper
			.getBean("vcityResTypeService");
	private VcityResourceAppService vcityResourceAppService = (VcityResourceAppService) SpringHelper
	.getBean("vcityResourceAppService");
	
	private VcityResCommonService vcityResCommonService = (VcityResCommonService) SpringHelper
	.getBean("vcityResCommonService");
	
	private VcitySubTaskService vcitySubTaskService = (VcitySubTaskService) SpringHelper.getBean("vcitySubTaskService");
	
	protected SceneService sceneService=(SceneService)BeanFactory.getBean("sceneService");

	private VcityResourcesService vcityResourcesService = (VcityResourcesService) SpringHelper.getBean("vcityResourcesService");

	private static VcityResourceForm vcityResourceForm = null;

	/**
	 * 新增虚拟资源
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws NullPointerException
	 */
	public ActionForward saveVcityResource(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws NullPointerException,
			IOException {
		vcityResourceForm = (VcityResourceForm) form;
	
		/*// 场景ID
		String sceneId = request.getParameter("tasksubid");
		if (sceneId!=null&&!"".equals(sceneId)) {
			vcityResourceForm.setSenceId(Integer.parseInt(sceneId));
		}*/

		// 构建基础上传路径
		String saveDirPath = "/uploadfile/VCity/";
		String saveFilePath = request.getSession().getServletContext()
				.getRealPath("")+ saveDirPath;
		String uuid = UUID.randomUUID().toString();
		
		String imageFileName = "";
		if (vcityResourceForm.getImageFile()!=null
				&&vcityResourceForm.getImageFile().getFileName()!=null
				&&!vcityResourceForm.getImageFile().getFileName().equals("")) {
			imageFileName = vcityResourceForm.getImageFile().getFileName();
			String ext = imageFileName.substring(imageFileName.lastIndexOf(".") + 1); // 获取上传文件的扩展名
			imageFileName =uuid + "." + ext; // 重命名文件
			vcityResourceForm.setResImage(imageFileName);
		}
		
		String modelFileName = "";
		if (vcityResourceForm.getModelFile()!=null&&vcityResourceForm.getModelFile().getFileName()!=null
				&&!vcityResourceForm.getModelFile().getFileName().equals("")) {
			modelFileName = vcityResourceForm.getModelFile().getFileName();
			String ext = modelFileName.substring(modelFileName.lastIndexOf(".") + 1); // 获取上传文件的扩展名	
			modelFileName = uuid + "." + ext; // 重命名文件
			
			vcityResourceForm.setModuleFileName(modelFileName);
		}
		
		String recommendImageFileName = "";
		if (vcityResourceForm.getRecommendImageFile()!=null&&
				vcityResourceForm.getRecommendImageFile().getFileName()!=null
				&&!vcityResourceForm.getRecommendImageFile().getFileName().equals("")) {
			recommendImageFileName = vcityResourceForm.getRecommendImageFile().getFileName();
			String ext = recommendImageFileName.substring(recommendImageFileName.lastIndexOf(".") + 1); // 获取上传文件的扩展名
			recommendImageFileName = uuid + "." + ext; // 重命名文件
			vcityResourceForm.setRecommendImage(recommendImageFileName);
		}

		//上传图片
		/*String imageFileName = UploadUtil.myUploadFile(vcityResourceForm
				.getImageFile(), saveFilePath);
		if(imageFileName!=null){
			vcityResourceForm.setResImage(saveDirPath + imageFileName);
		}*/
		//上传推荐图片
		VcityResource entity = new VcityResource();
		
		entity = (VcityResource) this.formToModel(vcityResourceForm,
				"cn.vcity.model.VcityResource");
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		entity.setCreateUser(uinfo.getUsername());
		boolean flag = vcityResourceService.saveVcityResource(entity);
		
		if (vcityResourceForm.getImageFile()!=null
				&&vcityResourceForm.getImageFile().getFileName()!=null
				&&!vcityResourceForm.getImageFile().getFileName().equals("")) {
			String imageSaveFilePath = saveFilePath+"Application/image/";
			if (vcityResourceForm.getResType()==1) {
				UploadUtil.myUploadFileImage(vcityResourceForm.getImageFile(), imageSaveFilePath,imageFileName);
			} else {
				imageSaveFilePath =  saveFilePath+"Model/image/";
				UploadUtil.myUploadFileImage(vcityResourceForm.getImageFile(), imageSaveFilePath,imageFileName);		
			}
		}
		if (vcityResourceForm.getModelFile()!=null&&vcityResourceForm.getModelFile().getFileName()!=null
				&&!vcityResourceForm.getModelFile().getFileName().equals("")) {
			String modelSaveFilePath = "";
			if (entity.getResType()==1) {
				modelSaveFilePath = saveFilePath+"Application/software/";
			} else {
				modelSaveFilePath = saveFilePath+"Model/Wingo/";
			}
			
			UploadUtil.myUploadFileImage(vcityResourceForm.getModelFile(), modelSaveFilePath,modelFileName);
			String ext = modelFileName.substring(modelFileName.lastIndexOf(".") + 1);
			if (ext.equals("zip")&&vcityResourceForm.getResType()==2) {
				ZipFileHelper.extZipFileList(modelSaveFilePath,uuid,modelFileName);
			}	
		}
		if (vcityResourceForm.getRecommendImageFile()!=null&&
				vcityResourceForm.getRecommendImageFile().getFileName()!=null
				&&!vcityResourceForm.getRecommendImageFile().getFileName().equals("")) {
			String recommendImageSaveFilePath = saveFilePath+"Recommend/image/"; 
			UploadUtil.myUploadFileImage(vcityResourceForm.getImageFile(), recommendImageSaveFilePath,recommendImageFileName);
		}
		
		if (flag) {   //添加一个默认访问
			//VcityResource vcityResource2 =vcityResourceService.findVcityResourceByResName(vcityResourceForm.getResName());
			VcityAccessTotal vcityAccessTotal =new VcityAccessTotal();
			vcityAccessTotal.setAccessIp(request.getRemoteAddr());
			vcityAccessTotal.setAccessTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			vcityAccessTotal.setVcityResource(entity);
			vcityResourcesService.saveAccessTotal(vcityAccessTotal);
		}
		
		/*//往资源应用表增添资源根节点
		VcityResource vcityResource = vcityResourceService.findVcityResourceByResName(vcityResourceForm.getResName());
		VcityResourceApp app = new VcityResourceApp();
		app.setResId(vcityResource.getId());
		app.setParenId(0);
		app.setAppDesc(vcityResourceForm.getResName());
		vcityResourceAppService.saveVcityResourceApp(app);*/
		
		//重定向改变url，防止刷新页面时再次提交
		String tempPath = request.getSession().getServletContext().getContextPath();
		response.sendRedirect(tempPath+"/vcityResourceAction.do?action=showVcityResourceList");
		return null;
		//return showVcityResourceList(mapping, new VcityResourceForm(), request, response);

	}

	/**
	 * 根据todo参数，跳转到新增或修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward editVcityResource(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
	
		vcityResourceForm = (VcityResourceForm) form;
		String todo = vcityResourceForm.getTodo();
		String forward = "";
		// 查找所有的资源分类
		List typeList = vcityResTypeService.findAllVcityResType();

		if (TODO_CREATE.equals(todo)) {
			forward = "create";
		} else if (TODO_UPDATE.equals(todo)) {
			Integer id = vcityResourceForm.getId();
			VcityResource vcityResource = vcityResourceService
					.findVcityResourceById(id);

			/*String typeid = vcityResource.getVcityResType().getId().toString();
			String typeName = vcityResource.getVcityResType().getResType();
			request.setAttribute("typeid", typeid);
			request.setAttribute("typeName", typeName);
			request.setAttribute("resType", vcityResource.getResType());
			if (vcityResourceForm.getFlag().equals("1")) {
				System.out.println("flag is 1");
				request.setAttribute("flag", 1);
			} else {
				request.setAttribute("flag", 0);
			} */
			vcityResourceForm = (VcityResourceForm) this
					.modelToForm(vcityResource,
							"cn.vcity.web.form.VcityResourceForm");

			/*	vcityResourceForm.setProductType(vcityResource.getVcityResType().getId());
			Integer objid = null;
			objid = vcityResource.getSenceId();
			if (objid != null) {
				List list = sceneService
						.getSceneByCondition("from Scene s where objid='"
								+ objid + "'");

				if(list.size()>0){
					Scene scene = (Scene) list.get(0);
					vcityResourceForm.setSenceName(scene.getObjName());

				}
			}
*/
			forward = "update";
		} else {
			log.error("createOrUpdatePost－TODO参数传递错误或为空！");
		}
		

		request.setAttribute("vcityResourceForm", vcityResourceForm);
		request.setAttribute("resKind", vcityResourceForm.getResType());
		request.setAttribute("isUp", vcityResourceForm.getIsRecommend());
		request.setAttribute("typeList", typeList);
		request.setAttribute("selectType", request.getParameter("selectType"));
		return mapping.findForward(forward);

	}

	/**
	 * 显示虚拟资源管理分页数据列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showVcityResourceList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		vcityResourceForm = (VcityResourceForm) form;
		/*System.out.println(request.getHeader("Referer"));
		System.out.println(request.getRequestURI());*/
		List datas = null;
		int total = 0;// 总记录数
		Pager pager = null;// 分页信息
		
		total = vcityResourceService.getTotalByOther(vcityResourceForm);

		try {
			pager = PagerHelper.getPager(request, total);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List productClassList =  vcityResTypeService.findAllVcityResType();

		/*Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		if (uinfo.getGroupid()==17) {
			vcityResourceForm.setUserName(uinfo.getUsername());
		}*/
			
		datas = vcityResourceService.getVcityResourceListByOther(
				vcityResourceForm, pager.getPageSize(), pager.getStartRow());
		
		String isExperience = request.getParameter("flag");
		String temp = request.getParameter("manual");
		String forward = "showList";
		
		//弹出层选择产品体验
		if (isExperience!=null&&!isExperience.equals("")) {
			Integer manual = 0;
			if (temp!=null) {
				if(temp.equals("1"))
					manual = 1;
			}
			request.setAttribute("flag", isExperience);
			request.setAttribute("manual", manual);
			forward = "experience";
		}
	
		request.setAttribute("datas", datas);
		request.setAttribute("pager", pager);
		request.setAttribute("productClassList", productClassList);
		request.setAttribute("vcityResourceForm", vcityResourceForm);

		return mapping.findForward(forward);
	}

	/**
	 * 删除虚拟资源
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward deleteVcityResource(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		vcityResourceForm = (VcityResourceForm) form;
		String id = request.getParameter("id");

		boolean flag = true;

		// 根据传入ID判断是只删除当前行数据，还是批量删除选中的数据
		if (id != null&&!"".equals(id)) {
		/*	List list = vcityResourceAppService.findResAppByResID(Integer.parseInt(id));
			if (list!=null&&list.size()>0) {
				for (int i = 0; i < list.size(); i++) {
					VcityResourceApp app = (VcityResourceApp) list.get(i);
					flag = vcityResourceAppService.deleteResAppEntity(app);
				}
			} */
			VcityResource resource = vcityResourceService.findVcityResourceById(Integer.parseInt(id));
			VcityResource deleteRes = resource;
			flag = vcityResourceService.deleteVcityResourceById(Integer.parseInt(id));
			if (flag) {
				String filePath = request.getSession().getServletContext().getRealPath("")+"\\";
				deleteFileOnDisk(filePath, deleteRes);
			}
						
		} else {
			/*String[] ids = vcityResourceForm.getSelectedRow();
			for (int i = 0; i < ids.length; i++) {
				List list = vcityResourceAppService.findResAppByResID(Integer.parseInt(ids[i]));
				if (list!=null&&list.size()>0) {
					for (int j = 0; j < list.size(); j++) {
						VcityResourceApp app = (VcityResourceApp) list.get(j);
						flag = vcityResourceAppService.deleteResAppEntity(app);
					}
				}
			}*/
	
			flag = vcityResourceService.deleteVcityResourceByIds(vcityResourceForm.getSelectedRow());

		}
		/*request.setAttribute(Constants.ALERT_MESSAGE, flag == true ? "删除成功！"
				: "删除失败！");
		vcityResourceForm.setId(null); */

		String tempPath = request.getSession().getServletContext().getContextPath();
		response.sendRedirect(tempPath+"/vcityResourceAction.do?action=showVcityResourceList");
		return null;

	}

	/**
	 * 修改虚拟资源
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */

	public ActionForward updateVcityResource(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		vcityResourceForm = (VcityResourceForm) form;

		//		 场景ID
	//	String sceneid = request.getParameter("tasksubid");

		try {
			
			VcityResource entity = vcityResourceService.findVcityResourceById(vcityResourceForm.getId());
			String uuid = entity.getResImage().substring(0,entity.getResImage().lastIndexOf("."));
			entity.setResName(vcityResourceForm.getResName().trim());
			entity.setResDesc(vcityResourceForm.getResDesc());
	
//			 构建上传路径
			String saveDirPath = "/uploadfile/VCity/";
			String saveFilePath = request.getSession().getServletContext()
					.getRealPath("")+ saveDirPath;
			String fileName = "";
			
			//修改资源大图片
			if (vcityResourceForm.getImageFile()!=null) {
				String imageSaveFilePath = saveFilePath+"Application/image/";
				fileName = vcityResourceForm.getImageFile().getFileName();
				if (fileName!=null&&!fileName.equals("")) {

					String ext = fileName.substring(fileName.lastIndexOf(".") + 1); // 获取上传文件的扩展名
					fileName = uuid + "." + ext;
					if (vcityResourceForm.getResType()==1) {
						UploadUtil.myUploadFileImage(vcityResourceForm.getImageFile(), imageSaveFilePath,fileName);
					} else {
						imageSaveFilePath = saveFilePath+"Model/image/";
						UploadUtil.myUploadFileImage(vcityResourceForm.getImageFile(), imageSaveFilePath,fileName);
					}
					
					entity.setResImage(fileName);
				}
			}
			
			//修改应用资源
			if (vcityResourceForm.getModelFile()!=null) {
				fileName = vcityResourceForm.getModelFile().getFileName();
				if (fileName!=null&&!fileName.equals("")) {
					String modelSaveFilePath = "";
					String ext = fileName.substring(fileName.lastIndexOf(".") + 1); // 获取上传文件的扩展名
					fileName = uuid + "." + ext;
					if (vcityResourceForm.getResType()==1) {
						modelSaveFilePath = saveFilePath+"Application/software/";	
					} else {
						modelSaveFilePath = saveFilePath+"Model/Wingo/";
					}				
					UploadUtil.myUploadFileImage(vcityResourceForm.getModelFile(), modelSaveFilePath,fileName);
					entity.setModuleFileName(fileName);
					if (ext.equals("zip")&&vcityResourceForm.getResType()==2) {
						ZipFileHelper.extZipFileList(modelSaveFilePath,uuid,fileName);
					}
				}
			}
			
			//修改推荐图片
			if (vcityResourceForm.getRecommendImageFile()!=null) {
				fileName = vcityResourceForm.getRecommendImageFile().getFileName();
				if (fileName!=null&&!fileName.equals("")) {
					String ext = fileName.substring(fileName.lastIndexOf(".") + 1); // 获取上传文件的扩展名
					fileName = uuid + "." + ext;
					String recommendImageSaveFilePath = saveFilePath+"Recommend/image/"; 
					UploadUtil.myUploadFileImage(vcityResourceForm.getRecommendImageFile(), recommendImageSaveFilePath,fileName);
					entity.setRecommendImage(fileName);
				}
			}
			
		/*	//修改资源大图片
			String imageFileName = UploadUtil.myUploadFile(vcityResourceForm
					.getImageFile(), saveFilePath);
			if(imageFileName!=null){
				vcityResourceForm.setResImage(saveDirPath + imageFileName);
				entity.setResImage(saveDirPath + imageFileName);
			}
			//修改推荐图片
			String recommendImageFileName = UploadUtil.myUploadFile(vcityResourceForm
					.getRecommendImageFile(), saveFilePath);
			if(recommendImageFileName!=null){
				vcityResourceForm.setRecommendImage(saveDirPath + recommendImageFileName);
				entity.setRecommendImage(saveDirPath + recommendImageFileName);
			}
			 
			//修改模型
//			String modelFileName = UploadUtil.myUploadFile(vcityResourceForm
//					.getModelFile(), saveFilePath);
//			if(modelFileName!=null){
//				vcityResourceForm.setModuleFileName(saveDirPath + modelFileName);
//				entity.setModuleFileName(saveDirPath + modelFileName);
//			}
			String saveFilePath_model = request.getSession().getServletContext()
			.getRealPath("")+ "/uploadfile/";

			String modelFileName = UploadUtil.myUploadFile(vcityResourceForm
					.getModelFile(), saveFilePath_model);

			if(modelFileName!=null){
				vcityResourceForm.setModuleFileName("/uploadfile/" + modelFileName);
				entity.setModuleFileName("/uploadfile/" + modelFileName);
			}

		*/
			/*if(sceneid!=null&&!sceneid.equals("")){
				entity.setSenceId(Integer.parseInt(sceneid));
			}*/
			
			//VcityResType vcityResType = new VcityResType();
			//vcityResType.setId(vcityResourceForm.getProductType());
		
			if (vcityResourceForm.getIsRecommend()==null||vcityResourceForm.getIsRecommend()==0) {
				entity.setIsRecommend(0);
			} else {
				entity.setIsRecommend(vcityResourceForm.getIsRecommend());
			}
			entity.setResType(vcityResourceForm.getResType());
			entity.setResURL(vcityResourceForm.getResURL());
			vcityResourceService.updateVcityResource(entity);
		//	deleteFileOnDisk(request, deleteRes);   //删除硬盘旧文件
 		} catch (Exception e) {
			e.printStackTrace();
		}

		String tempPath = request.getSession().getServletContext().getContextPath();
		String selectType = request.getParameter("selectType");
		String path = tempPath +"/vcityResourceAction.do?action=showVcityResourceList";
		if (selectType!=null&&!selectType.equals("")) {
			path+= "&resType="+selectType;
		}
		response.sendRedirect(path);
		return null;
	}

	/**
	 * 异步请求,检查资源名称是否已被占用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward checkResNameExists(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");

		String resName = request.getParameter("resName");
		String id = request.getParameter("id");

		VcityResource vcityResource = vcityResourceService
				.findVcityResourceByResName(resName);

		String _return = "";
		if (id == null || id.equals("")) {

			if (vcityResource != null) {
				_return = "1";
			} else {
				_return = "0";
			}
		}

		if (id != null) {
			if (vcityResource != null) {
				if (vcityResource.getId().equals(id)) {
					_return = "0";
				} else {
					_return = "1";
				}
			} else {
				_return = "0";
			}
		}

		try {
			StringBuffer xml = new StringBuffer();

			xml.append("<DataSet>");
			xml.append("<Data>");
			xml.append("<value>" + _return + "</value>");

			xml.append("</Data>");
			xml.append("</DataSet>");
			PrintWriter out;

			out = response.getWriter();
			out.println(xml);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
	

	/**
	 *得到资源应用树列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doGetResourceUseTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List<Scene> scene = sceneService.getAllScene();
		String var = "";
		if (scene != null && scene.size() > 0) {
			for (int i = 0; i < scene.size(); i++) {
				Scene sce = scene.get(i);
				if (sce.getPid() == 0) {
					var += "d.add(" + sce.getId() + "," + (i == 0 ? -1 : 1)
							+ ",'" + sce.getObjName()
							+ "',\"javascript:getHostList(" + sce.getObjId()
							+ ",'" + sce.getObjName() +"')\",'','');\n";
				} else {
					var += "d.add(" + sce.getId() + "," + sce.getPid() + ",'"
							+ sce.getObjName() + "',\"javascript:getHostList("
							+ sce.getObjId()+ ",'" + sce.getObjName() + "')\",'','');\n";
				}
			}
		}
		request.setAttribute("size", request.getParameter("size"));
		request.setAttribute("scene", var);
		return mapping.findForward("tree");
	}
	/**
	 * 显示虚拟资源详细信息
	 * hsw
	 * @param 
	 * @param 
	 * @return 
	 */	
	public ActionForward showVcityResourceInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {


		vcityResourceForm = (VcityResourceForm) form;
		String vrId=request.getParameter("vrId");
		int total1=0;
		int total2=0;
		VcityResource vcityResource =new VcityResource();
		if (vrId!=null&&!vrId.equals("")){
			int id = Integer.parseInt(vrId);
			vcityResource = vcityResourceService.findVcityResourceById(id);
			total1 = vcityResCommonService.getResCommonTotal(id,1);
			total2 = vcityResCommonService.getResCommonTotal(id,2);
		}
		vcityResourceForm = (VcityResourceForm) this.modelToForm(
				vcityResource, "cn.vcity.web.form.VcityResourceForm");
		request.getSession().setAttribute("vcityResource", vcityResource);
		request.setAttribute("IP", request.getRemoteAddr());
		request.setAttribute("total1", total1);
		request.setAttribute("total2", total2);
//		System.out.println(request.getLocalAddr());
//		System.out.println(request.getRemoteAddr());
//		request.setAttribute("datas", datas);
		request.setAttribute("vcityResourceForm", vcityResourceForm);

		return mapping.findForward("showInfo");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Desc:体验模型 
	 * @author csw
	 * Sep 3, 2011
	 * @throws Exception 
	 */
	public ActionForward showResourcesByChat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		vcityResourceForm = (VcityResourceForm) form;
		String userName = vcityResourceForm.getUserName();
		if (userName!=null) {				
			try {
				userName = java.net.URLDecoder.decode(userName, "UTF-8");
				
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		vcityResourceForm.setUserName(userName);
		List resourceList = new ArrayList();
		Integer flag = 1;
		if(request.getParameter("other")!=null){
			resourceList = vcityResourceService.findResourceOther(vcityResourceForm.getTaskId());
			flag = 2;
		} else {
			resourceList = vcityResourceService.findResourceByTaskId(vcityResourceForm.getTaskId());	
		}
		request.setAttribute("flag", flag);
		request.setAttribute("taskId", vcityResourceForm.getTaskId());
		request.setAttribute("resourceList", resourceList);
		return mapping.findForward("showResourceInfo");
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Desc:交流页面 调查问卷
	 * @author csw
	 * Sep 3, 2011
	 */
	public ActionForward showSureyByChat(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		vcityResourceForm = (VcityResourceForm) form;
		List sureyList = new ArrayList();
		Integer flag = 1;
		String userName = request.getParameter("userName");
		try {
			userName = java.net.URLDecoder.decode(userName, "UTF-8");
			vcityResourceForm.setUserName(userName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(request.getParameter("other")!=null){
			sureyList = vcityResourceService.findSurveyOther(vcityResourceForm.getTaskId());
			flag = 2;
		} else {
			sureyList = vcityResourceService.findSurveyByTaskId(vcityResourceForm.getTaskId());
		}
		List tempSurveyList = vcitySubTaskService.findTempSurveyList(vcityResourceForm.getTaskId());
		System.out.println(tempSurveyList.size());
		request.setAttribute("flag", flag);
		request.setAttribute("tempSurveyList", tempSurveyList);
		request.setAttribute("taskId", vcityResourceForm.getTaskId());	
		request.setAttribute("sureyList", sureyList);
		return mapping.findForward("showSureyInfo");
	}
	/**
	 * 体验模型中，保存虚拟资源
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward saveSubTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		vcityResourceForm = (VcityResourceForm) form;
		boolean flag = false; 
		String[] iids = vcityResourceForm.getSelectedRow();
		String str = "";
		if(iids!=null && iids.length>0){
			List list = new ArrayList();
			for(int i=0;i<iids.length;i++){
				String[] test = iids[i].split("%%");
				list.add(vcityResourceForm.getSubTaskMap().get(""+test[0]));
			}
			if(!list.isEmpty()){
				flag = vcityResourceService.saveBatchObjects(list);
			}
			String userName = request.getParameter("userName");
			String taskId = request.getParameter("taskId");
			String basePath = request.getContextPath();
			for(int i=0;i<iids.length;i++){
				String[] test = iids[i].split("%%");
				String resid = test[0];
				List subList =  vcitySubTaskService.findSubModelByChat(taskId,resid,userName);
				if (subList.size()>0) {
					VcitySubTask sub = (VcitySubTask) subList.get(0);
					Integer subTaskId = sub.getId();
					String url = "";
					VcityResource resource = vcityResourceService.findVcityResourceById(Integer.parseInt(resid));
					if (resource.getResType()==1) {
						url = basePath+"/vcity/show3D/show.jsp?file="+resource.getModuleFileName()+"&resid="+resid+"&cTask="+subTaskId;
					}
					if (resource.getResType()==2) {
						url = basePath +"/vcity/produceOrder/produceOrder.jsp?file="+resource.getResURL()+"&resid="+resid+"&cTask="+subTaskId;
					}
					str += "邀请您参与 "+ test[1] +"  模型， <a href=\""+url+"\" target=\"_blank\">进入体验</a>%%"; 
				}		
			}
			if (!str.equals("")) {
				str = str.substring(0,str.length()-2);
			}
			
		}
		request.setAttribute("isSuc", flag);
		request.setAttribute("str", str);
		request.setAttribute("taskId", 100);
		if(!flag){
			return showResourcesByChat(mapping, form, request, response);
		}
		return mapping.findForward("showResourceInfo");
	}
	/**
	 * 交流页面，保存调查问卷
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveSubTaskByQuestion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String title = request.getParameter("title");
		
		vcityResourceForm = (VcityResourceForm) form;
		boolean flag = false; 
		String questionIds = vcityResourceForm.getQuestionIds();
		Integer subTaskId = 0;
		VcitySubTask vcitySubTask = new VcitySubTask();
		if(questionIds!=null && !questionIds.equals("")){
			if (title!=null&&!title.equals("")) {
				VcityTempSurvey tempSurvey = new VcityTempSurvey();
				tempSurvey.setTaskId(vcityResourceForm.getTaskId());
				tempSurvey.setTitle(title);
				tempSurvey.setQuestionIds(questionIds);
				vcitySubTaskService.saveTempSurvey(tempSurvey);
				request.setAttribute("isSaveTemp", true);
			}
			
			vcitySubTask.getSceneTask().setTaskId(vcityResourceForm.getTaskId());
			vcitySubTask.setSubTaskType(2);
			vcitySubTask.setSubTaskDesc("参与临时问卷调查");
			vcitySubTask.setSubTaskResId(questionIds);
			vcitySubTask.setUserName(vcityResourceForm.getUserName());
			vcitySubTask.setSubTaskState(0);
			try {
				vcitySubTaskService.saveSubTask(vcitySubTask);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		request.setAttribute("isSuc", flag);
		request.setAttribute("flag", 0);
		request.setAttribute("questionIds", questionIds);
		request.setAttribute("subTaskId", vcitySubTask.getId());
		if(!flag){
			return showSureyByChat(mapping, form, request, response);
		}
		return mapping.findForward("showSureyInfo");
	}
	
	/**
	 * 新建任务时虚拟资源预览
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward previewResource(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String url = "";
		String basePath = request.getContextPath();
		String resid = request.getParameter("resid");
		VcityResource resource = vcityResourceService.findVcityResourceById(Integer.parseInt(resid));
		if (resource.getResType()==1) {
			url = basePath+"/vcity/show3D/show.jsp?file="+resource.getModuleFileName()+"&resid="+resid;
		}
		if (resource.getResType()==2) {
			url = basePath +"/vcity/produceOrder/produceOrder.jsp?file="+resource.getResURL()+"&resid="+resid;
		}
		response.sendRedirect(url);
		return null;
	}
	
	/**
	 * 显示用户步骤
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward userProduceOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		vcityResourceForm = (VcityResourceForm) form;
		String userName = request.getParameter("userName");
		String taskId = request.getParameter("taskId");
		try {
			userName = java.net.URLDecoder.decode(userName, "UTF-8");
			vcityResourceForm.setUserName(userName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		List userSubList = vcityResourceService.getUserSubListByTaskId(Integer.parseInt(taskId),userName);
		List<VcitySubTask> testSubList = new ArrayList<VcitySubTask>();
		List userStepList = new ArrayList();
		if (userSubList!=null&&userSubList.size()>0) {
			for (int i = 0; i <userSubList.size(); i++) {
				VcitySubTask sub = (VcitySubTask) userSubList.get(i);
				if (sub.getSubTaskType()==1) {
					VcityResource res = vcityResourceService.findVcityResourceById(Integer.parseInt(sub.getSubTaskResId()));
					if (res.getResType()==2) {
						testSubList.add(sub);
					}
					userStepList = vcityResourceService.getUserStep(sub.getSubTaskResId(),sub.getUserName());
				}
			}
		}
		request.setAttribute("userSubList", testSubList);
		request.setAttribute("userStepList", userStepList);
		return mapping.findForward("userProduceOrder");
	}

	/**
	 * 图片压缩test
	 * @param srcURL 原图地址
	 * @param deskURL 缩略图地址
	 * @param comBase 压缩基数
	 * @param scale  压缩限制(宽/高)比例
	 * @throws Exception 
	 */
	public static  void  saveMinPhoto(String srcURL,String deskURL,String fileName,double comBase,double scale) throws Exception{
		/*srcURl 原图地址；deskURL 缩略图地址；comBase 压缩基数；scale 压缩限制(宽/高)比例*/
		java.io.File srcFile = new java.io.File(srcURL);
		Image src = ImageIO.read(srcFile);
		int srcHeight = src.getHeight(null);
		int srcWidth = src.getWidth(null);
		int deskHeight = 0;//缩略图高
		int deskWidth  = 0;//缩略图宽
		File savedir = new File(deskURL);// 构建文件目录以及目录文件
		// 如果目录不存在，则新建
		if (!savedir.exists()) {
			savedir.mkdirs();
		}
		
		double srcScale = (double)srcHeight/srcWidth;
		if((double)srcHeight>comBase || (double)srcWidth>comBase){
			//deskHeight = (int)comBase;
		//	deskWidth = (int)scale;
		     if(srcScale>=scale || 1/srcScale>scale){
		            if(srcScale>=scale){
		                  deskHeight = (int)comBase;
		                  deskWidth  = srcWidth*deskHeight/srcHeight;
		              }else{
		                 deskWidth = (int)comBase ;
		                 deskHeight  = srcHeight*deskWidth/srcWidth;
		               }

		       } else {
		         if((double)srcHeight>comBase){
		            deskHeight = (int)comBase;
		            deskWidth  = srcWidth*deskHeight/srcHeight;
		             } else{
		                 deskWidth = (int)comBase ;
		                 deskHeight  = srcHeight*deskWidth/srcWidth;
		              }

		          }
		      }else {
		          deskHeight = srcHeight;
		          deskWidth  = srcWidth;

		  }
		BufferedImage tag = new BufferedImage(deskWidth, deskHeight,
		    BufferedImage.TYPE_3BYTE_BGR);
		  tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null); //绘制缩小后的图
		  FileOutputStream deskImage = new FileOutputStream(deskURL+fileName); //输出到文件流
		  JPEGImageEncoder encoder = JPEGCodec
		    .createJPEGEncoder(deskImage);
		  encoder.encode(tag); //近JPEG编码
		  deskImage.close();

		} 

	/**
	 * 删除硬盘上存放的文件
	 * @param request
	 * @param resource
	 */
	public static void deleteFileOnDisk(String  filePath,VcityResource resource){
		String downFileName = filePath+resource.getDownFileName();
		String imageName = filePath+resource.getFullImage();
		String [] fileArr = new String[3];
		fileArr[0] = imageName;
		fileArr[1] = downFileName;
		fileArr[2] = "";
		if (resource.getSkinDirectory()!=null) {
			fileArr[2] = filePath+resource.getSkinDirectory();
		}	
		
		ZipFileHelper.deleteFiles(fileArr);
			
	}
}