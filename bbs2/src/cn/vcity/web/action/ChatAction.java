package cn.vcity.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Members;
import cn.jsprun.domain.Scenetask;
import cn.vcity.model.VcityChatRoom;
import cn.vcity.model.VcityChatUser;
import cn.vcity.service.ChatService;
import cn.vcity.service.VcityChatRoomService;
import cn.vcity.service.VcityTaskService;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.web.form.ChatForm;
import cn.vcity.web.form.VcityChatRoomForm;

/**
 * @Title: OrgAction.java
 * @Package com.wingo.wbase.web.action
 * @Description: TODO(用一句话描述该文件做什么)
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-1-7 下午02:42:14
 * @version V1.0
 */
public class ChatAction extends BaseAction {

	private ChatService chatService = (ChatService) SpringHelper.getBean("chatService");
	private VcityTaskService vcityTaskService = (VcityTaskService) SpringHelper.getBean("vcityTaskService");
	private VcityChatRoomService vcityChatRoomService = (VcityChatRoomService) SpringHelper.getBean("vcityChatRoomService");
	
	/**
	 * Desc:跳转到交流页面
	 * @author csw
	 * Jul 2, 2011
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toChat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ChatForm chatForm = (ChatForm)form;
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		Scenetask task = vcityTaskService.findTaskById(chatForm.getTaskId());
		List userList = chatService.getUserInfoByTaskId(chatForm.getTaskId());
		if(uinfo!=null){
			//研究员或管理员
			if(uinfo.getGroupid()==17||uinfo.getGroupid()==1){				
				request.setAttribute("userList", userList);
				request.setAttribute("taskid", chatForm.getTaskId());
				request.setAttribute("task", task);
				return mapping.findForward("success");
			}
			//普通用户
			if(uinfo.getGroupid()==10){
				Integer chatFlag = 0;  //使用scenetm的id作为发送人的ID和聊天MAP的标识
				for (int i = 0; i < userList.size(); i++) {
					Object[] o = (Object[]) userList.get(i);
					if (uinfo.getUsername().equals(o[1])) {
						chatFlag = (Integer) o[0];
						break;
					}
				}
				System.out.println(chatFlag);
				request.setAttribute("taskid", chatForm.getTaskId());
				request.setAttribute("senderID", chatFlag);
				request.setAttribute("task", task);
				return mapping.findForward("taskChat_cust");
			}
			
		} else {
			String tempPath = request.getSession().getServletContext().getContextPath();
			response.sendRedirect(tempPath+"/logging.jsp?action=login");
		}
		return null;
	}
	
	/**
	 * 进入聊天室 
	 */
	public ActionForward toChatRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String id = request.getParameter("id");
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		boolean isLogin = true;
	
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = df.parse(df.format(new Date()));
		 VcityChatRoomForm chatRoomForm = new VcityChatRoomForm();
		VcityChatRoom chatRoom = vcityChatRoomService.findChatRoomById(Integer.parseInt(id));
		
		if (chatRoom!=null) {
			  chatRoomForm = (VcityChatRoomForm)this.modelToForm(chatRoom, 
			 		"cn.vcity.web.form.VcityChatRoomForm");
			Date begin = df.parse(chatRoom.getBeginTime());
			Date end = df.parse(chatRoom.getEndTime());
			
			if (nowDate.compareTo(end) > 0) {   //已结束
				request.setAttribute("message", "该会议已经结束！");
				return mapping.findForward("showChatMessage");
			}
			if (nowDate.compareTo(begin) >= 0
					&& nowDate.compareTo(end) <= 0) {
				chatRoomForm.setChatStatus(1);
			}
			if (nowDate.compareTo(begin) < 0) {  //未开始
				request.setAttribute("message", "该会议还没开始！");
				return mapping.findForward("showChatMessage");
			}
		}
		if (uinfo==null) {
			request.setAttribute("isLogin", false);
			return mapping.findForward("toChatRoom");
		} else {
			List<String> users = new ArrayList<String>();
			Iterator iterator = chatRoom.getVcityChatUser().iterator();
			while (iterator.hasNext()) {
				VcityChatUser chatUser = (VcityChatUser) iterator.next();
				users.add(chatUser.getUsername());
			}
			if (!users.contains(uinfo.getUsername())) {
				request.setAttribute("message", "对不起，你不属于该会议！");
				return mapping.findForward("showChatMessage");
			}
		}
		List userList = vcityChatRoomService.findChatUserListById(Integer.parseInt(id));
		
		
		request.setAttribute("chatRoom", chatRoomForm);
		request.setAttribute("userList", userList);
		request.setAttribute("isLogin", isLogin);
		return mapping.findForward("toChatRoom");
	}
	
	/**
	 * 聊天室登录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toChatRoomLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return mapping.findForward("toChatRoomLogin");
	}
}
