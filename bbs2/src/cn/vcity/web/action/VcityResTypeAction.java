package cn.vcity.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.vcity.model.VcityResType;
import cn.vcity.model.VcityResource;
import cn.vcity.service.VcityResTypeService;
import cn.vcity.web.form.VcityResTypeForm;
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
public class VcityResTypeAction extends BaseAction {
	
	private VcityResTypeService vcityResTypeService = (VcityResTypeService) SpringHelper
			.getBean("vcityResTypeService");

	private static VcityResTypeForm vcityResTypeForm = null;

	/**
	 * 新增虚拟资源分类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveVcityResType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		vcityResTypeForm = (VcityResTypeForm) form;
		VcityResType entity = new VcityResType();
		String msg = "新增成功!";
		// String todo = request.getParameter("todo");
		String resType = vcityResTypeForm.getResType();
		VcityResType VcityResType = vcityResTypeService
				.findVcityResTypeByResName(resType);
		if (VcityResType == null) {
			entity = (VcityResType) this.formToModel(vcityResTypeForm,
					"cn.vcity.model.VcityResType");

			boolean flag = vcityResTypeService.saveVcityResType(entity);
			msg = flag ? "新增成功!" : "新增失败!";
		} else {
			msg = "该资源分类名称已被使用,新增失败!";

		}

		this.returnJsMsg(response, msg, "window.close();");
		return null;

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

	public ActionForward editVcityResType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		vcityResTypeForm = (VcityResTypeForm) form;
		String todo = request.getParameter("todo");
		String forward = "";

		if (TODO_CREATE.equals(todo)) {
			forward = "create";
		} else if (TODO_UPDATE.equals(todo)) {

			Integer id = vcityResTypeForm.getId();
			VcityResType vcityResType = vcityResTypeService
					.findVcityResTypeById(id);
			vcityResTypeForm = (VcityResTypeForm) this.modelToForm(
					vcityResType, "cn.vcity.web.form.VcityResTypeForm");
			forward = "update";
		} else {
			log.error("createOrUpdatePost－TODO参数传递错误或为空！");
		}

		request.setAttribute("vcityResTypeForm", vcityResTypeForm);
		return mapping.findForward(forward);

	}

	/**
	 * 显示虚拟资源分类分页数据列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showVcityResTypeList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		vcityResTypeForm = (VcityResTypeForm) form;
		List datas = null;
		int total = 0;// 总记录数
		Pager pager = null;// 分页信息
		total = vcityResTypeService.getTotalByOther(vcityResTypeForm);

		try {
			pager = PagerHelper.getPager(request, total);
		} catch (IOException e) {
			e.printStackTrace();
		}

		datas = vcityResTypeService.getVcityResTypeListByOther(
				vcityResTypeForm, pager.getPageSize(), pager.getStartRow());
		
		request.setAttribute("datas", datas);
		request.setAttribute("pager", pager);
		request.setAttribute("vcityResTypeForm", vcityResTypeForm);

		return mapping.findForward("showList");
	}

	/**
	 * 删除虚拟资源分类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteVcityResType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String id = request.getParameter("id");
		Integer delId = null;
		if (id!=null&&!id.equals(""))  {
			delId = Integer.parseInt(id);
		}
			
		vcityResTypeForm = (VcityResTypeForm) form;
		boolean flag = true;
		
//		根据传入ID判断是只删除当前行数据，还是批量删除选中的数据
		if (id != null&&!id.equals("")) {
			flag = vcityResTypeService.deleteVcityResTypeById(delId);
		} else {
			flag = vcityResTypeService.deleteVcityResTypeByIds(vcityResTypeForm
					.getSelectedRow());
		}
		request.setAttribute(Constants.ALERT_MESSAGE, flag == true ? "删除成功！"
				: "删除失败！");
		vcityResTypeForm = new VcityResTypeForm();
		request.setAttribute("vcityResTypeForm", vcityResTypeForm);

		return showVcityResTypeList(mapping, form, request, response);

	}

	/**
	 * 修改资源分类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward updateVcityResType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		vcityResTypeForm = (VcityResTypeForm) form;
		String msg = "修改成功!";
		boolean flag = true;

		VcityResType entity = vcityResTypeService
				.findVcityResTypeById(vcityResTypeForm.getId());
		entity.setResRemark(vcityResTypeForm.getResRemark());
		entity.setResType(vcityResTypeForm.getResType());

		flag = vcityResTypeService.updateVcityResType(entity);

		msg = flag ? "修改成功!" : "修改失败!";
		this.returnJsMsg(response, msg, "window.close();");

		return null;
	}

}
