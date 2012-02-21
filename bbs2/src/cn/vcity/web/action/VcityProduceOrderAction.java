package cn.vcity.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Members;
import cn.jsprun.struts.foreg.actions.MyManageAction;
import cn.vcity.model.VcityProduceOrder;
import cn.vcity.model.VcityResource;
import cn.vcity.model.VcitySubTask;
import cn.vcity.service.VcityProduceOrderService;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.helper.SpringHelper;

/**
 * @author Administrator
 *
 */
public class VcityProduceOrderAction extends BaseAction {
	private VcityProduceOrderService vcityProduceOrderService =  (VcityProduceOrderService)SpringHelper
	.getBean("vcityProduceOrderService");
	public ActionForward savaProduceOrder (ActionMapping mapping,ActionForm form ,HttpServletRequest request,HttpServletResponse response){
		String resIdString = request.getParameter("resId");
		String userIdString = request.getParameter("userId");
		String cTaskIdString =request.getParameter("cTaskId");
		String actionIdString =request.getParameter("actionId");
		VcityResource vcityResource =new VcityResource();
		VcitySubTask vcitySubTask =new VcitySubTask();
		if (resIdString!=null) {
			vcityResource.setId(Integer.parseInt(resIdString));
		}
		if (cTaskIdString!=null) {
			vcitySubTask.setId(Integer.parseInt(cTaskIdString));
		}
		VcityProduceOrder vcityProduceOrder=new VcityProduceOrder();
		vcityProduceOrder.setVcityResource(vcityResource);
		vcityProduceOrder.setVcitySubTask(vcitySubTask);
		vcityProduceOrder.setCode(actionIdString);
		vcityProduceOrder.setUserName(userIdString);
//		List<Members> meb = MyManageAction.getUserEmailFromE(members);
//		if (meb != null && meb.size() > 0) {
//			for (Members mebs : meb) {
//				MyManageAction.sendEmail(request, mebs.getEmail(),
//						mebs.getUsername(), vcityTaskForm.getTaskName());
//			}
//		}
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		vcityProduceOrder.setUserName(uinfo.getUsername());
		
		vcityProduceOrderService.saveVcityProduceOrder(vcityProduceOrder);
		System.out.println("VcityProduceOrderAction");
//		resId="+resId+"&userId="+userId+"&cTaskId="+cTaskId+"&actionId="+actionId+"&Reload="+rannum
		return null;
	}
	
}
