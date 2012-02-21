package cn.vcity.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.vcity.model.VcityResType;
import cn.vcity.model.VcityResource;
import cn.vcity.model.VcityResourceApp;
import cn.vcity.service.VcityResTypeService;
import cn.vcity.service.VcityResoprateService;
import cn.vcity.service.VcityResourceAppService;
import cn.vcity.web.form.VcityResTypeForm;
import cn.vcity.web.form.VcityResoprateForm;
import cn.vcity.web.form.VcityResourceAppForm;
import cn.vcity.wbase.common.Constants;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.helper.PagerHelper;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.wbase.common.vo.page.Pager;

/**
 * @Title: VcityResTypeAction.java
 * @Package cn.vcity.vcity.web.action
 * @Description: 虚拟资源分类的Action类
 * @author 黄金波 珠海市网佳科技有限公司.
 * @date 2011-7-4 17:08:25
 * @version V1.0
 */
public class VcityResourceAppAction extends BaseAction {
	
	private static VcityResourceAppService vcityResourceAppService = (VcityResourceAppService) SpringHelper
						.getBean("vcityResourceAppService");
	private static VcityResoprateService vcityResoprateService = (VcityResoprateService) SpringHelper.getBean("vcityResoprateService");


	private static VcityResourceAppForm vcityResAppForm = null;

	/**
	 * 增加虚拟资源应用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward addResApp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		vcityResAppForm = (VcityResourceAppForm) form;
		Integer resId = vcityResAppForm.getResId();
		Integer parenId = vcityResAppForm.getParenId();
		String appDesc = java.net.URLDecoder.decode(request.getParameter("appDesc"), "UTF-8");
		String appCode = request.getParameter("appCode");
		String parenDesc = request.getParameter("parenDesc");

				VcityResourceApp app = new VcityResourceApp();
				app.setParenId(parenId);
				app.setResId(resId);
				app.setAppDesc(appDesc);
				app.setAppCode(appCode);
				
				vcityResourceAppService.saveVcityResourceApp(app);
	
		vcityResAppForm.setAppCode(appCode);
		vcityResAppForm.setAppDesc(parenDesc);
		return	doGetResourceUseTree(mapping, vcityResAppForm, request, response);	
	}
	
	
	/**
	 *得到资源应用树列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public static ActionForward doGetResourceUseTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		VcityResourceAppForm vcityResAppForm = (VcityResourceAppForm) form;
		int total = 0;
		Pager pager = null;
		total = vcityResoprateService.getTotal();
		try {
			pager = PagerHelper.getPager(request, total);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List oprateList = vcityResoprateService.getVcityResoprateListByOther(
				new VcityResoprateForm(), pager.getPageSize(), pager.getStartRow());
		String resId = request.getParameter("resid");
		String look = request.getParameter("look");
		String forward = "resApptree";
		if(look!=null&&!look.equals(""))  forward = "readOnlyTree";
		if (resId!=null&&!resId.equals("")) {
			vcityResAppForm.setResId(Integer.parseInt(resId));
		}
		List <VcityResourceApp> appList = vcityResourceAppService.findResAppByResID(vcityResAppForm.getResId());

		String var = "";
		if (appList != null && appList.size() > 0) {
			for (int i = 0; i < appList.size(); i++) {
				VcityResourceApp resApp = appList.get(i);
				if (resApp.getParenId() == 0) {
					var += "d.add(" + resApp.getId()+ "," + (i == 0 ? -1 : 1)
							+ ",'" + resApp.getAppDesc()
							+ "',\"javascript:getHostList(" +resApp.getParenId()+","+ resApp.getId()
							+ "," + resApp.getResId() +",'" + resApp.getAppDesc() +"')\",'','');\n";
				} else {
					var += "d.add("+ resApp.getId() + "," + resApp.getParenId() + ",'"
							+ resApp.getAppDesc() + "',\"javascript:getHostList(" +resApp.getParenId()+","
							+ resApp.getId()
							+ "," + resApp.getResId() +",'" + resApp.getAppDesc() + "')\",'','');\n";
				}
			}
		}

		request.setAttribute("size", request.getParameter("size"));
		request.setAttribute("ResoprateList", oprateList);
		request.setAttribute("pager", pager);
		request.setAttribute("scene", var);
		request.setAttribute("vcityResAppForm", vcityResAppForm);
		return mapping.findForward(forward);
	}
	
	public static ActionForward deleteResApp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		vcityResAppForm = (VcityResourceAppForm) form;
		vcityResourceAppService.deleteResAppById(vcityResAppForm.getId());
		vcityResAppForm.setParenId(null);
		return doGetResourceUseTree(mapping, form, request, response);
		
	} 
	
}