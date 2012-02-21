package cn.vcity.web.action;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Scenetask;
import cn.vcity.model.VcitySubTask;
import cn.vcity.service.VcityTaskService;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.web.form.VcitySubTaskForm;

public class VcitySubTaskAction extends BaseAction {
	
	private static VcityTaskService vcityTaskService =  (VcityTaskService)SpringHelper
			.getBean("vcityTaskService");
	private static VcitySubTaskForm vcitySubTaskForm = null;
	
	public static ActionForward editSubTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		System.out.println("editSubTask...");
		vcitySubTaskForm = (VcitySubTaskForm)form;
		Integer id = (Integer)request.getAttribute("taskId");
		System.out.println("id       "+id);
		request.setAttribute("taskId", id);
		request.setAttribute("vcitySubTaskForm", vcitySubTaskForm);
		return mapping.findForward("createSubTask");
		
	}
	
	public static ActionForward saveSubTask(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		vcitySubTaskForm = (VcitySubTaskForm)form;
		System.out.println("主任务ID："+vcitySubTaskForm.getTaskId());
		
		Map childMap = vcitySubTaskForm.getChildMap();
		
		Scenetask task =  vcityTaskService.findTaskById(vcitySubTaskForm.getTaskId());
		//task.addVcityTaskChild(childMap);
		vcityTaskService.updateTask(task);
		
		Iterator iterator = childMap.entrySet().iterator();
		Map.Entry entry;
		while (iterator.hasNext()) {
			entry = (Map.Entry)iterator.next();
			VcitySubTask subTask = (VcitySubTask)entry.getValue();
			System.out.print(subTask.getSubTaskIndex());
			System.out.println("       "+subTask.getSubTaskDesc());
		}
		return null;
	}
}
