package cn.vcity.web.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Appointment;
import cn.jsprun.domain.Members;
import cn.jsprun.struts.conference.action.InvitationTemplate;
import cn.jsprun.struts.foreg.actions.MyManageAction;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.DataParse;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.Mail;
import cn.vcity.model.VcityChatRoom;
import cn.vcity.model.VcityChatUser;
import cn.vcity.service.VcityChatRoomService;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.helper.PagerHelper;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.wbase.common.vo.page.Pager;
import cn.vcity.web.form.VcityChatRoomForm;


public class VcityChatRoomAction extends BaseAction {

	
	private VcityChatRoomService vcityChatRoomService = (VcityChatRoomService) SpringHelper.getBean("vcityChatRoomService");
	protected static DataParse dataParse = (DataParse) BeanFactory.getBean("dataParse");
	private static VcityChatRoomForm vcityChatRoomForm = null;

	/**
	 * 聊天室管理列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showChatRoomList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		vcityChatRoomForm = (VcityChatRoomForm) form;
		String forward = "showList_admin";
		String isCreate  = request.getParameter("isCreate");   //是否有查询条件
		Integer flag = null;
		if (isCreate!=null&&!isCreate.equals("")) {
			flag = Integer.parseInt(isCreate); 
		}
		
		//是否发布成功,用于跳回到列表的时候提示信息
		if (request.getQueryString().indexOf("isPublish")>0) {
			request.setAttribute("isPublish", true);
		}
		List datas = null;
		int total = 0;
		Pager pager = null;
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		total = vcityChatRoomService.findChatRoomTotal(vcityChatRoomForm);
		
		if (uinfo.getGroupid() == 17) {	 //研究员	
			 Object[] o  = vcityChatRoomService.findChatRoomTotalResearcher(uinfo.getUsername());
			if (flag==null) {
				total = (Integer) o[2];
			} else if (flag==1) {
				total = (Integer) o[0];
			} else {
				total = (Integer) o[1];
			}

			request.setAttribute("totalAll", o[2]);
			request.setAttribute("totalMy", o[0]);
			request.setAttribute("totalOther", o[1]);
		}
		
		try {
			pager = PagerHelper.getPager(request, total);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		datas = vcityChatRoomService.findChatRoomList(vcityChatRoomForm, pager.getPageSize(), pager.getStartRow());
		if (uinfo.getGroupid() == 17) {	 //研究员	
			datas = vcityChatRoomService.findChatRoomListResearcher(uinfo.getUsername(),flag, pager.getPageSize(), pager.getStartRow());
			forward = "showChatRoomList";
		}
		
		// 取出每一条记录,将String日期与现在日期作比较得到任务状态
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date nowDate = df.parse(df.format(new Date()));
		List<VcityChatRoomForm> dataList = new ArrayList<VcityChatRoomForm>();
		if (datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				VcityChatRoom chat = (VcityChatRoom) datas.get(i);
				 VcityChatRoomForm chatRoomForm = (VcityChatRoomForm)this.modelToForm(chat, 
						 "cn.vcity.web.form.VcityChatRoomForm");
				if (chat.getBeginTime() != null && chat.getEndTime() != null
						&& !chat.getBeginTime().equals("")
						&& ! chat.getEndTime().equals("")) {

					Date begin = df.parse(chat.getBeginTime());
					Date end = df.parse(chat.getEndTime());
					if (nowDate.compareTo(begin) >= 0
							&& nowDate.compareTo(end) <= 0) {
						chatRoomForm.setChatStatus(1);
					}
					if (nowDate.compareTo(end) > 0) {
						chatRoomForm.setChatStatus(2);
					}
					if (nowDate.compareTo(begin) < 0) {
						chatRoomForm.setChatStatus(0);
					}
				}
				dataList.add(chatRoomForm);
			}

		}
		
		request.setAttribute("isCreate", isCreate);
		request.setAttribute("datas", dataList);
		request.setAttribute("pager", pager);

		return mapping.findForward(forward);
	}

	/**
	 * 
	 * 跳转到添加聊天室页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toAddChatRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		return mapping.findForward("toAddChatroom");

	}

	/**
	 * 新增聊天室保存
	 */
	public ActionForward saveChatRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		vcityChatRoomForm = (VcityChatRoomForm) form;
		String[] users = request.getParameterValues("checkuser");

		VcityChatRoom entity = new VcityChatRoom();
		entity = (VcityChatRoom) this.formToModel(vcityChatRoomForm,
				"cn.vcity.model.VcityChatRoom");
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		entity.setCreatedUser(uinfo.getUsername());
		entity.setIsPublish(0);

		String members = "";

		// 向人员任务关联表插入数据
		for (int i = 0; i < users.length; i++) {
			VcityChatUser chatUser = new VcityChatUser();
			chatUser.setVcityChatRoom(entity);
			chatUser.setUsername(users[i]);
			chatUser.setLastUpdateTime(0);
			entity.addVcityChatUser(chatUser);
			members += users[i] + ",";
		}
		entity.addVcityChatUser(new VcityChatUser(uinfo.getUsername(),0,entity));    //本人
		boolean bool = vcityChatRoomService.saveVcityChatRoom(entity);

		/*
		 * if (bool) {
		 * 
		 * //从E声网取得筛选用户的邮件列表,并发送邮件通知有新任务 if (!members.equals("")) { members =
		 * members.substring(0, members.length()-1);
		 * 
		 * List<Members> meb = MyManageAction.getUserEmailFromE(members); if
		 * (meb != null && meb.size() > 0) { for (Members mebs : meb) {
		 * MyManageAction.sendEmail(request, mebs.getEmail(),
		 * mebs.getUsername(), vcityTaskForm.getTaskName()); } } } }
		 */

		String tempPath = request.getSession().getServletContext().getContextPath();
		response.sendRedirect(tempPath+"/vcityChatRoomAction.do?action=showChatRoomList");
		return null;
		//return showTaskList(mapping, form, request, response);

	}

	/**
	 * 跳转到修改聊天室页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toUpdateChatRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		vcityChatRoomForm = (VcityChatRoomForm) form;
		VcityChatRoom chatRoom = vcityChatRoomService.findChatRoomById(vcityChatRoomForm.getId());
		
		if (chatRoom!=null) {
			vcityChatRoomForm = (VcityChatRoomForm)this.modelToForm(chatRoom, 
			 	"cn.vcity.web.form.VcityChatRoomForm");
		}
		request.setAttribute("vcityChatRoomForm", vcityChatRoomForm);
		
		return mapping.findForward("toUpdateChatRoom");
	}
	
	/**
	 * 更新聊天室
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateChatRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		vcityChatRoomForm = (VcityChatRoomForm) form;
		String[] users = request.getParameterValues("checkuser");
		
		VcityChatRoom entity = vcityChatRoomService.findChatRoomById(vcityChatRoomForm.getId());
		entity.setBeginTime(vcityChatRoomForm.getBeginTime());
		entity.setEndTime(vcityChatRoomForm.getEndTime());
		entity.setDescribe(vcityChatRoomForm.getDescribe());
		entity.setRoomName(vcityChatRoomForm.getRoomName());

		String members = "";

		// 向人员任务关联表插入数据
		if(users!=null){
			for (int i = 0; i < users.length; i++) {
				VcityChatUser chatUser = new VcityChatUser();
				chatUser.setVcityChatRoom(entity);
				chatUser.setUsername(users[i]);
				chatUser.setLastUpdateTime(0);
				entity.addVcityChatUser(chatUser);
				members += users[i] + ",";
			}
		}
		
		boolean bool = vcityChatRoomService.updateVcityChatRoom(entity);
		/*
		 * 从E声网取得筛选用户的邮件列表,并发送邮件通知有新任务 
		 * if (bool) { if (!members.equals("")) {
		 * members = members.substring(0, members.length()-1); List<Members>
		 * meb = MyManageAction.getUserEmailFromE(members); if (meb != null &&
		 * meb.size() > 0) { for (Members mebs : meb) {
		 * MyManageAction.sendEmail(request, mebs.getEmail(),
		 * mebs.getUsername(), vcityTaskForm.getTaskName()); } } } }
		 */

		String tempPath = request.getSession().getServletContext().getContextPath();
		response.sendRedirect(tempPath+"/vcityChatRoomAction.do?action=showChatRoomList");
		return null;
	}

	/**
	 * 删除聊天室
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteChatRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		vcityChatRoomForm = (VcityChatRoomForm) form;
		String id = request.getParameter("id");
		boolean flag = true;

		// 根据传入ID判断是只删除当前行数据，还是批量删除选中的数据
		if (id != null && !id.equals("")) {
			flag = vcityChatRoomService.deleteChatRoomById(Integer.parseInt(id));
		} else {
			flag = vcityChatRoomService.deleteChatRoomByIds(vcityChatRoomForm
					.getSelectedRow());
		}
		
		String tempPath = request.getSession().getServletContext().getContextPath();
		response.sendRedirect(tempPath+"/vcityChatRoomAction.do?action=showChatRoomList");
		return null;
	}
	
	/**
	 * 发布该聊天室,并发送邮件通知所有参与人员
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward publishChatRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		vcityChatRoomForm = (VcityChatRoomForm) form;
		String members = "";
		
		VcityChatRoom entity = vcityChatRoomService.findChatRoomById(vcityChatRoomForm.getId());
		if (entity!=null) {
				entity.setIsPublish(1);
				vcityChatRoomService.updateVcityChatRoom(entity);
				Iterator iterator = entity.getVcityChatUser().iterator();
				while (iterator.hasNext()) {
					VcityChatUser chatUser = (VcityChatUser) iterator.next();
					members += chatUser.getUsername()+",";
					
				}
			
				  
				 //从E声网取得筛选用户的邮件列表,并发送邮件通知有新任务
				if (!members.equals("")) {
					StringBuffer emailArray = new StringBuffer();
					  members = members.substring(0, members.length()-1); List<Members>
					  meb = MyManageAction.getUserEmailFromE(members);
					  if (meb != null &&meb.size() > 0) {
						  for (Members mebs : meb) {
							  emailArray.append(mebs.getEmail()+",");
							//  SendMail(request, mebs.getEmail(), entity);
							//  MyManageAction.sendEmail(request, mebs.getEmail(), mebs.getUsername(), entity.getRoomName());
						  }
					  }
					  emailArray = emailArray.deleteCharAt(emailArray.length()-1);
					  SendMail(request, emailArray.toString(), entity);
				 }
				
		 } 
			 	
		String tempPath = request.getSession().getServletContext().getContextPath();
		response.sendRedirect(tempPath+"/vcityChatRoomAction.do?action=showChatRoomList&isPublish=true");
		return null;
	}
	
    /**
     *  发送邮件
     * @param request
     * @param emailArray
     * @return
     */
	public boolean SendMail(HttpServletRequest request,String emailArray,VcityChatRoom chatRoom){
		Map<String,String> oldSettings=ForumInit.settings;
		 SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Map<String,String> mailparam=dataParse.characterParse(oldSettings.get("mail"), false);
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String timeoffset=(String)oldSettings.get("timeoffset");
		String date=Common.gmdate("yyyy-MM-dd HH:mm:ss", timestamp, timeoffset);;
		String host = mailparam.get("server");
		int port = Integer.parseInt(mailparam.get("port"));
		String auth = mailparam.get("auth");
		String auth_username = mailparam.get("auth_username");
		String auth_password = mailparam.get("auth_password");
		String test_from = mailparam.get("from");
		String test_to = emailArray;
		
		//Appointment app = appointmentService.getAppointmentById(new Integer(appId));
		String title = "在线座谈邀请";//+ app.getAppointmentName();
		String baseUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		baseUrl += "chatAction.do?action=toChatRoom&id="+chatRoom.getId();
		String message = chatRoom.getRoomName()+"<br>会议内容: "+chatRoom.getDescribe();
		
		message+="<br>温馨提示：会议已发布，请准时参加会议";
		
		String htmlBody = InvitationTemplate.getEmailTemplate(request,auth_username, 
				message, 
				baseUrl, "canhui", 
				chatRoom.getBeginTime(),
				chatRoom.getEndTime());
		Mail mail=new Mail(host, port, auth, auth_username, auth_password,"1");
		String alertmsg = mail.sendMessage(test_from, test_to, title,null, htmlBody);
		if(alertmsg==null){
			return true;
		}
		else{
			return false;
		}
	}
}
