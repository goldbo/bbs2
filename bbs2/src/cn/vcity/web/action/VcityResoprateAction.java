package cn.vcity.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import cn.vcity.wbase.common.Constants;
import cn.vcity.model.VcityResoprate;
import cn.vcity.service.VcityResoprateService;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.helper.PagerHelper;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.wbase.common.vo.page.Pager;
import cn.vcity.web.form.VcityResoprateForm;

public class VcityResoprateAction extends BaseAction {

	private VcityResoprateService vcityResoprateService = (VcityResoprateService) SpringHelper.getBean("vcityResoprateService");

	private static VcityResoprateForm vcityResoprateForm = null;



public ActionForward showVcityResoprateList(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) {
	vcityResoprateForm = (VcityResoprateForm) form;
	List list = null;
	int total = 0;
	Pager pager = null;
	total = vcityResoprateService.getTotal();
//	System.out.println("total: is "+total);
	try {
		pager = PagerHelper.getPager(request, total);
	} catch (IOException e) {
		e.printStackTrace();
	}
	list = vcityResoprateService.getVcityResoprateListByOther(
			vcityResoprateForm, pager.getPageSize(), pager.getStartRow());
//	System.out.println("ResoprateList : "+list);
	request.setAttribute("ResoprateList", list);
	request.setAttribute("pager", pager);
	request.setAttribute("vcityResoprateForm", vcityResoprateForm);
	
	return mapping.findForward("resoprateList");
}

/**
* 根据todo参数，跳转到新增或修改页面
* 
* @param 
* @param 
* @param 
* @param 
* @return
*/

public ActionForward editVcityResoprate(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {

	vcityResoprateForm = (VcityResoprateForm) form;
	String todo = request.getParameter("todo");
	String forward = "";
	
	if (TODO_CREATE.equals(todo)) {
		forward = "newresoprate";
	} else if (TODO_UPDATE.equals(todo)) {
	
		int id = vcityResoprateForm.getId();
		VcityResoprate vcityResoprate = vcityResoprateService
				.findVcityResoprateById(id);
		vcityResoprateForm = (VcityResoprateForm) this.modelToForm(
				vcityResoprate, "cn.vcity.web.form.VcityResoprateForm");
		forward = "updateresoprate";
	} else {
		log.error("createOrUpdatePost－TODO参数传递错误或为空！");
	}
	
	request.setAttribute("vcityResoprateForm", vcityResoprateForm);
	return mapping.findForward(forward);

}

/**
* 删除
* 

* @param 
* @return
*/
public ActionForward deleteVcityResoprate(ActionMapping mapping,
	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) {

	String id = request.getParameter("id");
	
	vcityResoprateForm = (VcityResoprateForm) form;
	boolean flag = true;
	
	if (id != null) {
		flag = vcityResoprateService.deleteVcityResoprateById(Integer.valueOf(id));
	} else {
		flag = vcityResoprateService.deleteVcityResoprateByIds(vcityResoprateForm
				.getSelectedRow());
	}
	request.setAttribute(Constants.ALERT_MESSAGE, flag == true ? "删除成功！"
			: "删除失败！");
	vcityResoprateForm = new VcityResoprateForm();
	request.setAttribute("vcityResoprateForm", vcityResoprateForm);
	
	return showVcityResoprateList(mapping, form, request, response);

}

/**
* 新增
* 
* @param 

* @return
*/
public ActionForward saveVcityResoprate(ActionMapping mapping,
	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) {

	vcityResoprateForm = (VcityResoprateForm) form;
	VcityResoprate entity = new VcityResoprate();
	String msg = "新增成功!";
	
	String name = vcityResoprateForm.getResoprateName();
	VcityResoprate VcityResoprate = vcityResoprateService.findVcityResoprateByName(name);
	if (VcityResoprate == null) {
		VcityResoprate vResoprate =new VcityResoprate();
		vResoprate.setResoprateName(vcityResoprateForm.getResoprateName());
		vResoprate.setResoCode(vcityResoprateForm.getResoCode());
//		entity = (VcityResoprate) this.formToModel(vcityResoprateForm,
//				"cn.vcity.model.VcityResoprate");
	
		boolean flag = vcityResoprateService.saveVcityResoprate(vResoprate);
		msg = flag ? "新增成功!" : "新增失败!";
	} else {
		msg = "该名称已被使用,新增失败!";
	
	}
	
	this.returnJsMsg(response, msg, "window.close();");
	return null;


}


/**
* 修改
* 

* @param 
* @return
*/

public ActionForward updateVcityResoprate(ActionMapping mapping,
	ActionForm form, HttpServletRequest request,
	HttpServletResponse response) {

	vcityResoprateForm = (VcityResoprateForm) form;
	String msg = "修改成功!";
	boolean flag = true;
	
	VcityResoprate entity = vcityResoprateService
			.findVcityResoprateById(vcityResoprateForm.getId());
	entity.setResoprateName(vcityResoprateForm.getResoprateName());
	entity.setResoCode(vcityResoprateForm.getResoCode());
	
	flag = vcityResoprateService.updateVcityResoprate(entity);
	
	msg = flag ? "修改成功!" : "修改失败!";
	this.returnJsMsg(response, msg, "window.close();");
	
	return null;
}	
}
