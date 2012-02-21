package cn.jsprun.struts.conference.action;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;

import cn.jsprun.domain.Appointment;
import cn.jsprun.domain.MeetingMember;
import cn.jsprun.domain.Members;
import cn.jsprun.meeting.ConnectionDBE;
import cn.jsprun.meeting.TcHost;
import cn.jsprun.meeting.TcParticipants;
import cn.jsprun.meeting.TcRoom;
import cn.jsprun.meeting.UmsUser;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.FileUploadUtil;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Mail;
import cn.jsprun.utils.Md5Token;

public class AppointmentAction extends BaseAction{
	
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static String FILEPATHTIME = JspRunConfig.realPath+"forumdata/temp";
	public final static int memeoryBlock = 1572865;
    int num = 1;
    /****
     * 分页
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward findAppointmentByCondition(ActionMapping mapping,ActionForm form,
    		HttpServletRequest request,HttpServletResponse response){
    	 num = 1;
 		  String num_str = request.getParameter("num");
 		  if(num_str!= null)
 			  num = new Integer(num_str).intValue();
 		  Page page = new Page();
 		  page.setPageSize(15);
 	      page.setCurrentPage(num);
 	      appointmentService.findAppointAppointmentByCondition(page);
 	      List<Appointment> appList = page.getDataList();
 	     request.setAttribute("nowDate", new Date());
 	     request.setAttribute("appList",appList);
 	     request.setAttribute("page", page);
 	     request.setAttribute("num", num);
     //  return mapping.findForward("appointment_list");
 	    return mapping.findForward("appointment_mmlist");
    }
    
	/***
	 * 根据研究员名称 查找该议员相关的会议  分页
	 */
    public ActionForward pageAppointmentByUserName(ActionMapping mapping,ActionForm form,
    		HttpServletRequest request,HttpServletResponse response){
    	 int uid=(Integer)request.getSession().getAttribute("jsprun_uid");
    	 if(uid==0){
    		 request.setAttribute("resultInfo","你还没有登录,不能执行此操作");
    		 return mapping.findForward("showMessage");
    	 }
    	 Members user = (Members)request.getSession().getAttribute("user");
    	 num = 1;
 		  String num_str = request.getParameter("num");
 		  if(num_str!= null)
 			  num = new Integer(num_str).intValue();
 		  Page page = new Page();
 		  page.setPageSize(15);
 	      page.setCurrentPage(num);
 	      appointmentService.pageAppointmentByUserName(page, user.getUid()+"");
 	      List<Appointment> appList = page.getDataList();
 	     
 	     request.setAttribute("nowDate", new Date());
 	     request.setAttribute("appList",appList);
 	     request.setAttribute("page", page);
 	     request.setAttribute("num", num);
 	     if("17".equals(user.getGroupid().toString())){
 	    	return mapping.findForward("appointment_mmlist");
 	     }else {
 	    	 return findAppointmentByCondition(mapping, form, request, response);
 	     }
       
    }
    
    
    /**
     * 查找所有
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
	public ActionForward getAppointmentList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		  List<Appointment> appList = appointmentService.getAppointmentAlls();
		  
		return mapping.findForward("");
	}
	
	/****
	 * 根据id查找会议信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAppointmentById(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
	    String appId = request.getParameter("appId");	
		Appointment app = appointmentService.getAppointmentById(new Integer(appId));
		List<MeetingMember> mmList = meetingMemberService.getMeetingMemberByAppId(new Integer(appId));
		if(mmList!=null)
		  request.setAttribute("numSize", mmList.size());
		List<String> chatContentList = ConnectionDBE.getTcChatByRoomId(appId);
		request.setAttribute("chatContentList", chatContentList);
		request.setAttribute("mmList", mmList);
		request.setAttribute("app", app);
		return mapping.findForward("meeting_info");
	}
	
	/***
	 * 跳转到添加会议
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toAddAppointment(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		int uid=(Integer)request.getSession().getAttribute("jsprun_uid");
	   	 if(uid==0){
	   		 request.setAttribute("resultInfo","你还没有登录,不能执行此操作");
	   		 return mapping.findForward("showMessage");
	   	 }
		
		Members user = (Members)request.getSession().getAttribute("user");
		String yanjiuyuan=request.getSession().getAttribute("jsprun_groupid")+"";
		if("17".equals(yanjiuyuan)){
			return mapping.findForward("appointment_mmadd");//研究员
		}else
			//return mapping.findForward("appointment_add");   //原来是跳转到不同页面，先屏蔽
			return mapping.findForward("appointment_mmadd");
	}
	
	/****
	 * 获取所有用户列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getUserList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		//List<Members> userList = memberService.getAllMembersByExtgroupids(null);
		String appId = request.getParameter("appId");
		List<UmsUser> userList = ConnectionDBE.getUmsUserList();
		String type = request.getSession().getAttribute("jsprun_groupid")+""; //判断是管理员还是研究员
		 Members user = (Members)request.getSession().getAttribute("user");
  	     //type = user.getExtgroupids();
  	     
  	     //查询该会议，已选择的参会者
  	     if(appId!=null){
  	    	 String getMember = "";
  	    	 List <MeetingMember> mmList = meetingMemberService.getMeetingMemberByAppId(new Integer(appId));
  	    	 for(MeetingMember mm:mmList){
  	    		getMember += mm.getUser().getUsername()+",";
  	    	 }
  	    	request.setAttribute("getMember", getMember);
  	     }
  	    //查询所有的用户
		request.setAttribute("userList", userList);
		 if("17".equals(type)){
			 return mapping.findForward("searchUser_mmsearch");//研究员
	    }
	    else 
	    	return mapping.findForward("searchUser");
	    
	}
	
	/******
	 * 执行添加操作
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward addAppointment(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		 int uid=(Integer)request.getSession().getAttribute("jsprun_uid");
    	 if(uid==0){
    		 request.setAttribute("resultInfo","你还没有登录,不能执行此操作");
    		 return mapping.findForward("showMessage");
    	 }
    	 
		String type = request.getSession().getAttribute("jsprun_groupid")+""; //判断是管理员还是研究员
		try {
	 		 String appName = request.getParameter("appName");
	 	     String startTime = request.getParameter("startTime");
	 	     String endTime = request.getParameter("endTime");
	 	     String zhuchi_user = request.getParameter("zhuchi_user");
	 	     String [] array_canyuuser = request.getParameterValues("canyu_user");//request.getParameter("canyu_user"); 
	 	     String desc = request.getParameter("desc");
	 	     String modeType = request.getParameter("modeType");
	 	     String filepic = request.getParameter("filepic");
	 	     if (filepic==null||filepic.equals("")) { //如果没有上传会议图片，添加默认图片
	 	    	filepic = "attachments/defaultMeetingPic/defaultPic.gif";
			}
	    	 Date startDate = sf.parse(startTime+":00");
	  	     Date endDate = sf.parse(endTime+":00");
	  	     Members user = (Members)request.getSession().getAttribute("user");
	  	    	 
	  	     String []array_zhuchiuser = zhuchi_user.split(",");
	  	   //  String []array_canyuuser = canyu_user!=null?canyu_user.split(","):null;
	  	     if(array_zhuchiuser==null){
	  	    	 request.setAttribute("msg", "请选择主持人");
	  	    	 return toAddAppointment(mapping, form, request, response);
	  	     }
	  	     if(array_zhuchiuser.length>1){
	  	    	request.setAttribute("msg", "一个会议只能一个主持人");
	  	    	 return toAddAppointment(mapping, form, request, response);
	  	     }
	  	   
	  	     Members zhuchiUser = memberService.findByName(array_zhuchiuser[0]);
	  	     
	  	     if(zhuchiUser==null){
	  	    	zhuchiUser = new Members();
	  	    	//向本地添加数据
	  	  	    Map map = Common.loginE(array_zhuchiuser[0], "");// 调用登录方法查询e声网相应用户
	  	  	    if(map==null){
	  	  	        request.setAttribute("msg", "<font color='red'>主持人在e声网中没有该用户,请重新选择</font>");
	  	  	        return toAddAppointment(mapping, form, request, response);
	  	  	    }else{
	  	  	    	zhuchiUser = addMembers(map.get("custer_account").toString(), map.get("password").toString(), map.get("email").toString(), "17");
	  	  	    }
	  	     }
	  	   
	  	     //判断视频会议数据库是否存在该用户,不存在则添加
	  	     UmsUser umsUser = ConnectionDBE.getUmsUserByAccount(array_zhuchiuser[0]);
	  	     if(umsUser==null){
	  	    	umsUser = new UmsUser();
	  	    	umsUser.setGroupID(3);
	  	    	umsUser.setAccount(zhuchiUser.getUsername());
	  	    	umsUser.setPassword("E10ADC3949BA59ABBE56E057F20F883E");//默认密码是123456
	  	    	umsUser.setName(zhuchiUser.getUsername());
	  	    	umsUser.setEmail(zhuchiUser.getEmail());
	  	    	umsUser.setIp(request.getRemoteAddr());
	  	    	umsUser.setCreateTime(new Date());
	  	    	umsUser.setStatus(0);
	  	    	umsUser.setLoginTime(new Date());
	  	    	ConnectionDBE.addUmsUser(umsUser);
	  	    	umsUser = ConnectionDBE.getUmsUserByAccount(array_zhuchiuser[0]);
	  	     }
	  	     TcHost th = ConnectionDBE.getTcHostByUserId(umsUser.getId()+"");
	  	     //添加关联表(主持人)
	  	     if(umsUser.getGroupID().intValue()==3){
		  	     if(th==null){
		  	    	th = new TcHost();
		  	    	th.setCreator(0);
		  	    	th.setRemark("");
		  	    	//int sex = zhuchiUser.getGender().intValue();
		  	    	//th.setSex(sex==1?0:1);
		  	    	th.setSex(0);
		  	    	th.setUserID(umsUser.getId());
		  	    	ConnectionDBE.addTcHost(th);
		  	     }
	  	     }
             //添加房间
	  	     TcRoom room = new TcRoom();
	  	     room.setRoomName(appName);
	  	     room.setRoomMaxUsers(20);
	  	     room.setStatus(1);
	  	     room.setRoomTest(1);
	  	     room.setVisitor(0);
	  	     room.setAutoMic(1);
	  	     room.setRtmptURL("");
	  	     room.setNote("欢迎您参加本次会议！！");
	  	     room.setRoomImageURL(filepic);
	  	     room.setPeriod(sf.format(startDate));
	  	     room.setCreateTime(new Date());
	  	     room.setHostID(umsUser.getId());
	  	     room.setStatus(1);
	  	     room.setCreator(1);
	  	     room.setRoomMaxVideos(0);//视频数（0为：25个）
	  	     
	  	     //保存会议数据
		  	 Appointment app = new Appointment();
	  	     app.setAppointmentName(appName);
	  	     app.setAppointmentStarttime(startDate);
	  	     app.setAppointmentEndtime(endDate);
	  	     app.setAddTime(new Date());
	  	     app.setUserId(user.getUid());
	  	     app.setAppointmentDescription(desc);
	  	     app.setNumberOfPartizipants(25);
	  	     app.setUserPid(new Integer(zhuchiUser.getUid()));
	  	     app.setIsEnd(0);
	  	     app.setModeType(new Integer(modeType));
	  	     app.setImagePath(filepic);
	  	     if("0".equals(modeType))
	  	    	app.setIsPublished(new Integer(1));//当为内部会议时，默认已发布
	  	     else
	  	    	app.setIsPublished(new Integer(0));//其它会议模式时，为未发布
	  	     appointmentService.addAppointment(app);
	  	     //添加向主持人发送邮件
	  	     SendMail(request, zhuchiUser.getEmail(), app.getAppointmentId()+"", app.getUserPid().toString(), "canhui");
	  	     
	  	     //添加参会者信息
	  	     String participantsIDs = "";
	  	     int num = 0;
	  	    MeetingMember mm_app = null;
	  	    if(array_canyuuser!=null){
	  	     for(String userName:array_canyuuser){
	  	    	Members canyuUser = memberService.findByName(userName);
	  	    	if(canyuUser==null){
	  	    		//向本地添加数据
	  	    		
	  	    	    Map map = Common.loginE(userName, "");// 调用登录方法查询e声网相应用户
	 	  	  	    if(map==null){
	 	  	  	    	continue;
	 	  	  	        //request.setAttribute("msg", "<font color='red'>参与人在e声网中没有该用户,请重新选择</font>");
	 	  	  	       // return toAddAppointment(mapping, form, request, response);
	 	  	  	    }else{
	 	  	  	    	canyuUser = addMembers(map.get("custer_account").toString(), map.get("password").toString(), map.get("email").toString(), null);
	 	  	  	    }
	  	    	}
	  	     	mm_app = new MeetingMember();
			  	mm_app.setAddTime(new Date());
			  	mm_app.setAppointmentId(app.getAppointmentId());
			  	mm_app.setIsfiltrate(0);
				if("0".equals(modeType))
			  		mm_app.setIskickout(1);
			  	else
			  		mm_app.setIskickout(0);
				if("0".equals(modeType))
			  		mm_app.setIsregister(1);
			  	else
			  		mm_app.setIsregister(0);
			    mm_app.setIsscreenout(0);
			  	mm_app.setUserId(canyuUser.getUid());
		  	    meetingMemberService.addMeetingMember(mm_app);
		  	    
	  	        //添加参会者(视频会议)
	  	    	 umsUser = null;
	  		  	 umsUser = ConnectionDBE.getUmsUserByAccount(userName);
		  	     if(umsUser==null){
		  	    	umsUser = new UmsUser();
		  	    	umsUser.setGroupID(4);
		  	    	umsUser.setAccount(userName);
		  	    	umsUser.setPassword("E10ADC3949BA59ABBE56E057F20F883E");//默认密码都为123456
		  	    	umsUser.setName(userName);
		  	    	umsUser.setEmail(null);
		  	    	umsUser.setIp(request.getRemoteAddr());
		  	    	umsUser.setCreateTime(new Date());
		  	    	umsUser.setStatus(0);
		  	    	umsUser.setLoginTime(new Date());
		  	    	ConnectionDBE.addUmsUser(umsUser);
		  	    	umsUser = ConnectionDBE.getUmsUserByAccount(userName);
		  	     }
		  	     
		     	 //添加关联表(参会者)
		  	     TcParticipants tp = ConnectionDBE.getTcParticipantsByUserId(umsUser.getId()+"");
		  	     if(umsUser.getGroupID().intValue()==4){
			  	     if(tp==null){
			  	    	tp = new TcParticipants();
			  	    	tp.setCreator(0);
			  	    	//int sex = canyuUser.getGender().intValue();
			  	    	//th.setSex(sex==1?0:1);
			  	    	tp.setSex(0);
			  	    	tp.setUserID(umsUser.getId());
			  	    	ConnectionDBE.addTcParticipants(tp);
			  	     }
		  	     }
		  	     if(num==array_canyuuser.length-1)
		  	    	participantsIDs += umsUser.getId()+"";
		  	     else
		  	    	participantsIDs += umsUser.getId()+",";
		  	     num++;
	  	       //添加向参会者发送邮件
	  	    	SendMail(request, canyuUser.getEmail(), app.getAppointmentId()+"", mm_app.getUserId().toString(), "baoming");
	  	     }
	  	    }
	  	     //入库操作
	  	     room.setParticipantsIDs(participantsIDs);
	  	     ConnectionDBE.addTcRoom(room);
	  	     
	  	   request.setAttribute("msg", "<font color='blue'>添加会议成功</font>");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "<font color='red'>添加会议失败</font>");
		}
		String tempPath = request.getSession().getServletContext().getContextPath();
	    if("17".equals(type)){	  	
	    	//return pageAppointmentByUserName(mapping, form, request, response);//研究员
	    	response.sendRedirect(tempPath+"/appointment.do?opt=pageAppointmentByUserName");   //完全跳转
	    }
	    else 
	    	//return findAppointmentByCondition(mapping, form, request, response);
	    	response.sendRedirect(tempPath+"/appointment.do?opt=findAppointmentByCondition");
	    return null;
	}
	
	/*****
	 * 跳转到修改会议页
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toUpdateAppointment(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		 int uid=(Integer)request.getSession().getAttribute("jsprun_uid");
    	 if(uid==0){
    		 request.setAttribute("resultInfo","你还没有登录,不能执行此操作");
    		 return mapping.findForward("showMessage");
    	 }
		Members user = (Members)request.getSession().getAttribute("user");
		String appId = request.getParameter("appId");
		Appointment app = appointmentService.getAppointmentById(new Integer(appId));
		List<MeetingMember> mmList = meetingMemberService.getMeetingMemberByAppId(new Integer(appId));
		
		/*String canyu_user = "";
		for(MeetingMember mm:mmList){
			canyu_user += mm.getUser().getUsername()+",";
		}*/
		request.setAttribute("mmList", mmList);
		//request.setAttribute("canyu_user", canyu_user);
		request.setAttribute("app", app);
		
		if("17".equals(request.getSession().getAttribute("jsprun_groupid")+"")){
			return mapping.findForward("appointment_mmupdate");//研究员
		}else  return mapping.findForward("appointment_mmupdate");
			//return mapping.findForward("appointment_update");
	}
	
	/***
	 * 执行修改操作
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateAppointment(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		 int uid=(Integer)request.getSession().getAttribute("jsprun_uid");
    	 if(uid==0){
    		 request.setAttribute("resultInfo","你还没有登录,不能执行此操作");
    		 return mapping.findForward("showMessage");
    	 }
		try {
	    	 String appName = request.getParameter("appName");
		     String startTime = request.getParameter("startTime");
		     String endTime = request.getParameter("endTime");
		     String zhuchi_user = request.getParameter("zhuchi_user");
		     String [] array_canyuuser = request.getParameterValues("canyu_user");// request.getParameter("canyu_user");
		     String desc = request.getParameter("desc");
		     String modeType = request.getParameter("modeType");
		     String appId = request.getParameter("appId");
		     String filepic = request.getParameter("filepic");
	    	 Date startDate = sf.parse(startTime+":00");
	  	     Date endDate = sf.parse(endTime+":00 ");
	  	     String emailtype = request.getParameter("emailtype");
	  	     Members user = (Members)request.getSession().getAttribute("user");
	  	     
	  	     String []array_zhuchiuser = zhuchi_user.split(",");
	  	   //  String []array_canyuuser = canyu_user!=null?canyu_user.split(","):null;
	  	     if(array_zhuchiuser==null){
	  	    	 request.setAttribute("msg", "请选择主持人");
	  	    	 return toAddAppointment(mapping, form, request, response);
	  	     }
	  	     if(array_zhuchiuser.length>1){
	  	    	request.setAttribute("msg", "一个会议只能一个主持人");
	  	    	 return toAddAppointment(mapping, form, request, response);
	  	     }
	  	    
	  	     Members zhuchiUser = memberService.findByName(array_zhuchiuser[0]);
	  	     if(zhuchiUser==null){
	  	    	zhuchiUser = new Members();
	  	    	//向本地添加数据
	  	  	    Map map = Common.loginE(array_zhuchiuser[0], "");// 调用登录方法查询e声网相应用户
	  	  	    if(map==null){
	  	  	        request.setAttribute("msg", "<font color='red'>主持人在e声网中没有该用户,请重新选择</font>");
	  	  	        return toAddAppointment(mapping, form, request, response);
	  	  	    }else{
	  	  	    	zhuchiUser = addMembers(map.get("custer_account").toString(), map.get("password").toString(), map.get("email").toString(), "17");
	  	  	    }
	  	     }
	  	     
	  	     //修改会议
	  	     Appointment app = appointmentService.getAppointmentById(new Integer(appId));
	  	     app.setAppointmentName(appName);
	  	     app.setAppointmentStarttime(startDate);
	  	     app.setAppointmentEndtime(endDate);
	  	     app.setAddTime(new Date());
	  	     app.setUserId(user.getUid());
	  	     app.setAppointmentDescription(desc);
	  	     app.setNumberOfPartizipants(25);
	  	     app.setModeType(new Integer(modeType));
	  	     if("0".equals(modeType))
	  	    	app.setIsPublished(new Integer(1));//当为内部会议时，默认已发布
	  	     else
	  	    	app.setIsPublished(new Integer(0));//其它会议模式时，为未发布
	  	     
	  	     if(!filepic.equals(app.getImagePath())){
	  		    if(app.getImagePath()!=null&&!"".equals(app.getImagePath())){
		  	        String path =JspRunConfig.realPath+ app.getImagePath();
		  	    	File file = new File(path);
		  			if(file.exists()){
		  				file.delete();
		  			}
		   	     }
	  	      }
	  	     app.setImagePath(filepic);
	  	     
	  	   UmsUser umsUser = null;	  	   
	  	     if(zhuchiUser.getUid().intValue()!=app.getUserPid().intValue()){
	  	    	 //如果切换主持人，则向取消主持人发送邮件信息
	  	    	SendMail(request, zhuchiUser.getEmail(), appId, app.getUserPid().toString(), "cancelMeeting");
	  	     }else{
	  	    	//发送邮件的代码
	  		   if(emailtype!=null&&"1".equals(emailtype)){//表示emailtype的值==1时，表示全部发送。等于2时，表示只发送新增人员
	  			   SendMail(request, zhuchiUser.getEmail(), appId, app.getUserPid().toString(),"canhui");
	  		   }
	  	     }
	  	
	  	     //判断视频会议数据库是否存在该用户,不存在则添加
	  	     umsUser = ConnectionDBE.getUmsUserByAccount(array_zhuchiuser[0]);
	  	     if(umsUser==null){
	  	    	umsUser = new UmsUser();
	  	    	umsUser.setGroupID(3);
	  	    	umsUser.setAccount(zhuchiUser.getUsername());
	  	    	umsUser.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
	  	    	umsUser.setName(zhuchiUser.getUsername());
	  	    	umsUser.setEmail(zhuchiUser.getEmail());
	  	    	umsUser.setIp(request.getRemoteAddr());
	  	    	umsUser.setCreateTime(new Date());
	  	    	umsUser.setStatus(0);
	  	    	umsUser.setLoginTime(new Date());
	  	    	ConnectionDBE.addUmsUser(umsUser);
	  	    	umsUser = ConnectionDBE.getUmsUserByAccount(array_zhuchiuser[0]);
	  	     }
	  	     
	  	    TcHost th = ConnectionDBE.getTcHostByUserId(umsUser.getId()+"");
	  	     //添加关联表(主持人)
	  	     if(umsUser.getGroupID().intValue()==3){
		  	     if(th==null){
		  	    	th = new TcHost();
		  	    	th.setCreator(0);
		  	    	th.setRemark("");
		  	    	//int sex = zhuchiUser.getGender().intValue();
		  	    	//th.setSex(sex==1?0:1);
		  	    	th.setSex(0);
		  	    	th.setUserID(umsUser.getId());
		  	    	ConnectionDBE.addTcHost(th);
		  	     }
	  	     }
	  	     
             //修改房间
	  	     TcRoom room = ConnectionDBE.getTcRoomById(appId);
	  	     if(room!=null){
	  	    	room.setRoomName(appName);
	  	    	room.setNote("欢迎您参加本次会议！！");
		  	    room.setPeriod(sf.format(startDate));
		  	    room.setHostID(umsUser.getId());
	  	     }
	  	     
	  	     
	  	     app.setUserPid(new Integer(zhuchiUser.getUid()));
	  	     appointmentService.updateAppointment(app);
	  	     
	  	    //删除原来的参会者
		   List <MeetingMember> mmList = meetingMemberService.getMeetingMemberByAppId(new Integer(appId));
		   ArrayList<String> mmUserList = new ArrayList<String>();//表示新添加以后的成员(原有的和新添加的一起)
		   ArrayList<String> cancelmmUserList = new ArrayList<String>();//表示已取消的会议成员
		   ArrayList<String> newmmUserList = new ArrayList<String>();//新添加的成员
		   //原来添加的成员和新成员
		   if(array_canyuuser!=null){
			   for(int i=0;i<array_canyuuser.length;i++){
				   boolean flg = false;
				   if(!"".equals(array_canyuuser[i])){
					   for(MeetingMember mm:mmList){
						   if(array_canyuuser[i].endsWith(mm.getUser().getUsername())){
							   mmUserList.add(array_canyuuser[i]);
							   flg = true;
						   }
					   }
				   }
				   if(!flg){
					   mmUserList.add(array_canyuuser[i]);
				   }
			   }
		   }
		   
		   //已取消的会议成员
		   for(MeetingMember mm:mmList){
			   boolean flg = false;
			   for(String userId:mmUserList){
				   if(userId.equals(mm.getUser().getUsername())){
					  flg = true;
				   }
			   }
			   if(!flg){
				   cancelmmUserList.add(mm.getUser().getUsername());
			   }
		   }
		   
		 //新增的成员
		   for(String userId:mmUserList){
			   boolean flg = false;
			   for(MeetingMember mm:mmList){
				   if(userId.equals(mm.getUser().getUsername())){
					  flg = true;
				   }
			   }
			   if(!flg){
				   newmmUserList.add(userId);
			   }
		   }
		   
		   //删除取消人员
		   MeetingMember mm_cancel = null;
		   for(String userName:cancelmmUserList){
			   Members canyuUser = memberService.findByName(userName);
			   mm_cancel = meetingMemberService.getMeetingMemberByAppId(app.getAppointmentId(), canyuUser.getUid());
			   meetingMemberService.deleteMeetingMember(mm_cancel);
			   //向取消用户者发送邮件
			   SendMail(request, canyuUser.getEmail(), app.getAppointmentId()+"", mm_cancel.getUserId().toString(),"cancelMeeting");
		   }
		   
		   //添加新成员
		   MeetingMember mm_app  = null;
		   for(String userName:newmmUserList){
			   Members canyuUser = memberService.findByName(userName);
	  	    	if(canyuUser==null){
	  	    		//向本地添加数据
	  	    	    Map map = Common.loginE(userName, "");// 调用登录方法查询e声网相应用户
	 	  	  	    if(map==null){
	 	  	  	        request.setAttribute("msg", "<font color='red'>参与人在e声网中没有该用户,请重新选择</font>");
	 	  	  	        return toAddAppointment(mapping, form, request, response);
	 	  	  	    }else{
	 	  	  	    	canyuUser = addMembers(map.get("custer_account").toString(), map.get("password").toString(), map.get("email").toString(), null);
	 	  	  	    }
		 	  	  	
	  	    	}	  	    	
	  	    	mm_app = new MeetingMember();
  			  	mm_app.setAddTime(new Date());
  			  	mm_app.setAppointmentId(app.getAppointmentId());
  			  	mm_app.setIsfiltrate(0);
  			    if("0".equals(modeType))
			  		mm_app.setIskickout(1);
			  	else
			  		mm_app.setIskickout(0);
				if("0".equals(modeType))
			  		mm_app.setIsregister(1);
			  	else
			  		mm_app.setIsregister(0);
  			  	mm_app.setIsscreenout(0);
  			  	mm_app.setUserId(canyuUser.getUid());
  		  	    meetingMemberService.addMeetingMember(mm_app);
	  	    	
	  	      //添加参会者(视频会议)
	  	    	 umsUser = null;
	  		  	 umsUser = ConnectionDBE.getUmsUserByAccount(userName);
		  	     if(umsUser==null){
		  	    	umsUser = new UmsUser();
		  	    	umsUser.setGroupID(4);
		  	    	umsUser.setAccount(userName);
		  	    	umsUser.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
		  	    	umsUser.setName(userName);
		  	    	umsUser.setEmail(null);
		  	    	umsUser.setIp(request.getRemoteAddr());
		  	    	umsUser.setCreateTime(new Date());
		  	    	umsUser.setStatus(0);
		  	    	umsUser.setLoginTime(new Date());
		  	    	ConnectionDBE.addUmsUser(umsUser);
		  	    	umsUser = ConnectionDBE.getUmsUserByAccount(userName);
		  	     }
		  	     
		     	 //添加关联表(参会者)
		  	     TcParticipants tp = ConnectionDBE.getTcParticipantsByUserId(umsUser.getId()+"");
		  	     if(umsUser.getGroupID().intValue()==4){
			  	     if(tp==null){
			  	    	tp = new TcParticipants();
			  	    	tp.setCreator(0);
			  	    	//int sex = canyuUser.getGender().intValue();
			  	    	//th.setSex(sex==1?0:1);
			  	    	tp.setSex(0);
			  	    	tp.setUserID(umsUser.getId());
			  	    	ConnectionDBE.addTcParticipants(tp);
			  	     }
		  	     }
		   }
		   
		   String participantsIDs = "";//针对在视频会议的邀请者的id属性来创建
		   int num = 0;
		   Members mm  = null;
		   for(String userId:mmUserList){
			   mm = memberService.findByName(userId);
			   mm_app = meetingMemberService.getMeetingMemberByAppId(app.getAppointmentId(), mm.getUid());
			   umsUser = ConnectionDBE.getUmsUserByAccount(userId);
			   if(umsUser!=null){
				   if(num==mmUserList.size()-1)
					   participantsIDs+=umsUser.getId();
				   else
					   participantsIDs+=umsUser.getId()+",";
			   }
			   //添加向参会者发送邮件,发送邮件放到下一步
	  	      // SendMail(request, mm.getEmail(), app.getAppointmentId()+"", mm_app.getUserId().toString(),"baoming");
	  	       num++;
		   }
		   //发送邮件的代码
		   if(emailtype!=null&&"1".equals(emailtype)){//表示emailtype的值==1时，表示全部发送。等于2时，表示只发送新增人员
			   for(String userId:mmUserList){
				   mm = memberService.findByName(userId);
				   mm_app = meetingMemberService.getMeetingMemberByAppId(app.getAppointmentId(), mm.getUid());
				   //添加向参会者发送邮件
		  	       SendMail(request, mm.getEmail(), app.getAppointmentId()+"", mm_app.getUserId().toString(),"baoming");
			   }
		   }else if("2".equals(emailtype)){
			   for(String userId:newmmUserList){
				   mm = memberService.findByName(userId);
				   mm_app = meetingMemberService.getMeetingMemberByAppId(app.getAppointmentId(), mm.getUid());
				   //添加向参会者发送邮件
		  	       SendMail(request, mm.getEmail(), app.getAppointmentId()+"", mm_app.getUserId().toString(),"baoming");
			   }
		   }
		  
		   //入库操作
		   if(room!=null){
			   room.setParticipantsIDs(participantsIDs);
			   ConnectionDBE.updateTcRoom(room);
		   }
	  	     request.setAttribute("msg", "<font color='blue'>修改会议成功</font>");
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "<font color='red'>修改会议失败</font>");
			}
		return toUpdateAppointment(mapping, form, request, response);
	}
	
	/****
	 * 删除会议
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward deleteAppointment(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception {
		 int uid=(Integer)request.getSession().getAttribute("jsprun_uid");
    	 if(uid==0){
    		 request.setAttribute("resultInfo","你还没有登录,不能执行此操作");
    		 return mapping.findForward("showMessage");
    	 }
    	 
		String appId = request.getParameter("appId");
		String type = request.getParameter("sendType");
		//查找会议
		Appointment app = appointmentService.getAppointmentById(new Integer(appId));
		//查找会议成员
		List <MeetingMember> mmList = meetingMemberService.getMeetingMemberByAppId(new Integer(appId));
		
		Members user = (Members)request.getSession().getAttribute("user");
		try {
			if(mmList!=null){
				for(MeetingMember mm :mmList){
					meetingMemberService.deleteMeetingMember(mm);
					if("0".equals(type))//0表示未开展和正在进行的会议，需要进行邮件通知;1表示已结束的会议，不进行邮件通知
						SendMail(request, mm.getUser().getEmail(), app.getAppointmentId()+"", mm.getUser().getUid().toString(),"cancelMeeting");
				}
			}
			 if(app.getImagePath()!=null&&!"".equals(app.getImagePath())){
		  	        String path =JspRunConfig.realPath+ app.getImagePath();
		  	    	File file = new File(path);
		  			if(file.exists()){
		  				file.delete();
		  			}
		   	  }
			 ConnectionDBE.delTcRoom(appId);
			 appointmentService.delAppointment(app);
			 request.setAttribute("msg", "<font color='blue'>删除会议成功</font>");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "<font color='red'>删除会议失败</font>");
		}
		String tempPath = request.getSession().getServletContext().getContextPath();
		if("17".equals(request.getSession().getAttribute("jsprun_groupid")+"")){	
			response.sendRedirect(tempPath+"/appointment.do?opt=pageAppointmentByUserName");
			return null;
			//return pageAppointmentByUserName(mapping, form, request, response);//研究员
		}else 
			response.sendRedirect(tempPath+"/appointment.do?opt=findAppointmentByCondition");
			//return findAppointmentByCondition(mapping, form, request, response);
		return null;
	}
	
	/**
	 * 发布会议
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward isPublishedAppointment(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String yanjiuyuan=request.getSession().getAttribute("jsprun_groupid")+"";
		String appId = request.getParameter("appId");
		Appointment app = appointmentService.getAppointmentById(new Integer(appId));
		app.setIsPublished(1);
		if(appointmentService.updateAppointment(app)){
			//正式发布以后，向已邀请、报名且筛选后的人员进行邮件发送
			List <MeetingMember> mmList = meetingMemberService.getMeetingMemberByAppId(new Integer(appId));
			if(mmList!=null){
				for(MeetingMember mm : mmList){
					if(mm.getIsregister().intValue()==1&&mm.getIskickout().intValue()==1){
						 SendMail(request, mm.getUser().getEmail(), app.getAppointmentId()+"", mm.getUser().getUid().toString(),"fabu");	
					}
				}
			}
			request.setAttribute("msg", "<font color='blue'>发布会议成功</font>");
		}else
			request.setAttribute("msg", "<font color='red'>发布会议失败</font>");
		if("17".equals(yanjiuyuan)){
			return pageAppointmentByUserName(mapping, form, request, response);//研究员
		}else
			return findAppointmentByCondition(mapping, form, request, response);
	}
	
	   /************
	    * 转到报名页(邮件模式链接报名)
	    * @param mapping
	    * @param form
	    * @param request
	    * @param response
	    * @return
	    */
	   public ActionForward toSignupAppointmentMember(ActionMapping mapping,ActionForm form,
	    		HttpServletRequest request,HttpServletResponse response){
		   String appId = request.getParameter("appId");
		   String userId = request.getParameter("userId");
		   Appointment app =  appointmentService.getAppointmentById(new Integer(appId));
		   List<MeetingMember> mmList = meetingMemberService.getMeetingMemberByAppId(new Integer(appId));
			String canyu_user = "";
			for(MeetingMember mm:mmList){
				canyu_user += mm.getUser().getUsername()+",";
			}
		   request.setAttribute("canyu_user", canyu_user);
		   request.setAttribute("app", app);
		   request.setAttribute("userId", userId);
		   return mapping.findForward("signupAppointment_exterior");
	   }
	   
	   /********
	    * 进行报名  exterior(邮件模式链接报名)
	    * @param mapping
	    * @param form
	    * @param request
	    * @param response
	    * @return
	    */
	   public ActionForward signupAppointmentMember(ActionMapping mapping,ActionForm form,
	    		HttpServletRequest request,HttpServletResponse response){
		   String userId = request.getParameter("userId");
		   String appId = request.getParameter("appId");
		   try {
			   MeetingMember mm = null;
			   if(userId!=null){
				 mm = meetingMemberService.getMeetingMemberByAppId(new Integer(appId), new Integer(userId));
			   }
			   if(mm!=null){
				   if(mm.getIsregister()!=1){
					   mm.setIsregister(1);
					   meetingMemberService.updateMeetingMember(mm);
					   request.setAttribute("msg", "<font color='blue'>尊敬的会员，感谢您报名本次会议!!</font>");
				   }else{
					   request.setAttribute("msg", "<font color='blue'>尊敬的会员，你已在本次会议中报名!!</font>");
				   }
			   }else{
				   request.setAttribute("msg", "<font color='red'>尊敬的会员，您未被该会议邀请，感谢您的关注!!</font>");
			   }
			} catch (Exception e) {
			   e.printStackTrace();
			   request.setAttribute("msg", "<font color='red'>系统出现错误，报名失败</font>");
			}
		   return toSignupAppointmentMember(mapping, form, request, response);
	   }
	   
	   /***
	    *  对于公开会议、指定会议的进行报名的操作处理方法(非邮件模式链接报名)
	    * @param mapping
	    * @param form
	    * @param request
	    * @param response
	    * @return
	    */
	   public ActionForward signupAppointmentMember_gongKai(ActionMapping mapping,ActionForm form,
	    		HttpServletRequest request,HttpServletResponse response){
		  
		   /*int uid=(Integer)request.getSession().getAttribute("jsprun_uid");
		   if(uid==0){
	    		 request.setAttribute("resultInfo","你还没有登录,不能执行此操作");
	    		 return mapping.findForward("showMessage");
	    	 }*/
		   try {
			   Members user = (Members)request.getSession().getAttribute("user");
			   if(user!=null){//是否登录
				   String appId = request.getParameter("appId");
				   Appointment app = appointmentService.getAppointmentById(new Integer(appId));
				   MeetingMember mm = null;
				   mm = meetingMemberService.getMeetingMemberByAppId(new Integer(appId), user.getUid());
				   if(mm==null){
					   if(app.getModeType()==2){//会议为非公开模式时，执行以下代码
						   mm = new MeetingMember();
						   mm.setUserId(user.getUid());
						   mm.setAppointmentId(new Integer(appId));
						   mm.setAddTime(new Date());
						   mm.setIsregister(1);
						   mm.setIskickout(0);
						   mm.setIsfiltrate(0);
						   mm.setIsscreenout(0);
						   if(meetingMemberService.addMeetingMember(mm)){
							 //添加参会者(视频会议)
							   UmsUser umsUser = null;
					  		  	 umsUser = ConnectionDBE.getUmsUserByAccount(user.getUsername());
						  	     if(umsUser==null){
						  	    	umsUser = new UmsUser();
						  	    	umsUser.setGroupID(4);
						  	    	umsUser.setAccount(user.getUsername());
						  	    	umsUser.setPassword("E10ADC3949BA59ABBE56E057F20F883E");//默认密码都为123456
						  	    	umsUser.setName(user.getUsername());
						  	    	umsUser.setEmail(null);
						  	    	umsUser.setIp(request.getRemoteAddr());
						  	    	umsUser.setCreateTime(new Date());
						  	    	umsUser.setStatus(0);
						  	    	umsUser.setLoginTime(new Date());
						  	    	ConnectionDBE.addUmsUser(umsUser);
						  	    	umsUser = ConnectionDBE.getUmsUserByAccount(user.getUsername());
						  	     }
						  	     
						     	 //添加关联表(参会者)
						  	     TcParticipants tp = ConnectionDBE.getTcParticipantsByUserId(umsUser.getId()+"");
						  	     if(umsUser.getGroupID().intValue()==4){
							  	     if(tp==null){
							  	    	tp = new TcParticipants();
							  	    	tp.setCreator(0);
							  	    	tp.setSex(0);
							  	    	tp.setUserID(umsUser.getId());
							  	    	ConnectionDBE.addTcParticipants(tp);
							  	     }
						  	     }
						  	  TcRoom room = ConnectionDBE.getTcRoomById(appId);
						  	  String participantsIDs = room.getParticipantsIDs()+","+umsUser.getId();
							   //入库操作
							   if(room!=null){
								   room.setParticipantsIDs(participantsIDs);
								   ConnectionDBE.updateTcRoom(room);
							   }
							   request.setAttribute("msg", "报名成功，感谢您的关注！");
						   }else
								request.setAttribute("msg", "报名失败，感谢您的关注！");
					   }else{
						   request.setAttribute("msg", "您没有被邀请，感谢您的关注！");
					   }
				   }else{
					   if(mm.getIsregister()!=1){
						   mm.setIsregister(1);
						   meetingMemberService.updateMeetingMember(mm);
						   request.setAttribute("msg", "报名成功，感谢您的关注！");
					   }else{
						   request.setAttribute("msg", "该会议您已提交了报名，感谢您的关注！");
					   }
				   }
			   }else{
				  request.setAttribute("msg", "对不起，请先登录系统!!");
			   }
			} catch (Exception e) {
				e.printStackTrace();
			}
		   //return findConditionByAppointmentStarttime(mapping, form, request, response);
			return mapping.findForward("ts");
	   }
	   
	   /***
	    * 进入会员筛选页面
	    * @param mapping
	    * @param form
	    * @param request
	    * @param response
	    * @return
	    */
	   public ActionForward toKickoutAppointmentMember(ActionMapping mapping,ActionForm form,
	    		HttpServletRequest request,HttpServletResponse response){
		   int uid=(Integer)request.getSession().getAttribute("jsprun_uid");
		   if(uid==0){
	    		 request.setAttribute("resultInfo","你还没有登录,不能执行此操作");
	    		 return mapping.findForward("showMessage");
	    	 }
		   String appId = request.getParameter("appId");
		   try {
			   Appointment app = appointmentService.getAppointmentById(new Integer(appId));
				List<MeetingMember> mmList = meetingMemberService.getMeetingMemberByAppId(new Integer(appId));
				String canyu_user = "";
				if(mmList!=null){
					for(MeetingMember mm:mmList){
						canyu_user += mm.getUser().getUsername()+",";
					} 
				}
				//得到已报名的成员集合
				mmList = meetingMemberService.getMeetingMemberList(new Integer(appId).intValue(), 1, null);
				request.setAttribute("mmList", mmList);
				request.setAttribute("app", app);
				request.setAttribute("canyu_user", canyu_user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		  
		   if("17".equals(request.getSession().getAttribute("jsprun_groupid")+"")){
			   return mapping.findForward("appointment_mmkickout");//研究员
			}else 
				return mapping.findForward("appointment_mmkickout");
				// return mapping.findForward("appointment_kickout");     //old
	   }
	   
	   /***
	    * 执行会员筛选操作
	    * @param mapping
	    * @param form
	    * @param request
	    * @param response
	    * @return
	    */
	   public ActionForward doKickoutAppointmentMember(ActionMapping mapping,ActionForm form,
	    		HttpServletRequest request,HttpServletResponse response){
		   int uid=(Integer)request.getSession().getAttribute("jsprun_uid");
		   if(uid==0){
	    		 request.setAttribute("resultInfo","你还没有登录,不能执行此操作");
	    		 return mapping.findForward("showMessage");
	       }
		   try {
			   String [] mmIds = request.getParameterValues("mmId");
			   String appId = request.getParameter("appId");
			   List <MeetingMember> mmList = meetingMemberService.getMeetingMemberList(new Integer(appId).intValue(), 1, 1);
			   ArrayList<String> mmUserList = new ArrayList<String>();//表示新添加以后的成员(原有的和新添加的一起)
			   ArrayList<String> cancelmmUserList = new ArrayList<String>();//表示已取消
			   ArrayList<String> newmmUserList = new ArrayList<String>();//新添加
			   //原来添加的成员和新成员
			   if(mmIds!=null){
				   for(int i=0;i<mmIds.length;i++){
					   boolean flg = false;
					   if(!"".equals(mmIds[i])){
						   for(MeetingMember mm:mmList){
							   if(mmIds[i].endsWith(mm.getMeetingMemberId().intValue()+"")){
								   mmUserList.add(mmIds[i]);
								   flg = true;
							   }
						   }
					   }
					   if(!flg){
						   mmUserList.add(mmIds[i]);
					   }
				   }
			   }
			   
			   //已取消
			   for(MeetingMember mm:mmList){
				   boolean flg = false;
				   for(String userId:mmUserList){
					   if(userId.equals(mm.getMeetingMemberId().intValue()+"")){
						  flg = true;
					   }
				   }
				   if(!flg){
					   cancelmmUserList.add(mm.getMeetingMemberId().intValue()+"");
				   }
			   }
			   
			 //新增
			   for(String userId:mmUserList){
				   boolean flg = false;
				   for(MeetingMember mm:mmList){
					   if(userId.equals(mm.getUser().getUsername())){
						  flg = true;
					   }
				   }
				   if(!flg){
					   newmmUserList.add(userId);
				   }
			   }
			   
			   //对已取消的进行处理
			   for(String mmId:cancelmmUserList){
				   MeetingMember mm = meetingMemberService.getMeetingMemberById(new Integer(mmId));
				   mm.setIskickout(0);
				   meetingMemberService.updateMeetingMember(mm);
			   }
			   
			   //对新增的进行处理
			   for(String mmId:newmmUserList){
				   MeetingMember mm = meetingMemberService.getMeetingMemberById(new Integer(mmId));
				   mm.setIskickout(1);
				   meetingMemberService.updateMeetingMember(mm);
			   }
			   request.setAttribute("msg", "<font color='blue'>筛选会员成功</font>");
			} catch (Exception e) {
				e.printStackTrace();
				 request.setAttribute("msg", "<font color='red'>筛选会员失败</font>");
			}
			
	      return toKickoutAppointmentMember(mapping, form, request, response);
	   }
	   
	/****
	 * 前台页显示会议列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward findConditionByAppointmentStarttime(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		try {
			num = 1;
	 		  String num_str = request.getParameter("num");
	 		  if(num_str!= null)
	 			  num = new Integer(num_str).intValue();
	 		  //获取当前时间的会议或开完的会议集合
	 		  Page page = new Page();
	 		  page.setPageSize(4);
	 	      page.setCurrentPage(num);
	 	      appointmentService.findAppointmentByCondition_1(page);
	 	      List<Appointment> appList = page.getDataList();
	 	      
	 	      //获取当前时间以后的会议集合
	 	      List<Appointment> newAppList = appointmentService.findAppointmentByStartTime();
	 	     request.setAttribute("newAppList", newAppList);
	 	     request.setAttribute("appList",appList);
	 	     request.setAttribute("page", page);
	 	     request.setAttribute("num", num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("meeting_list");
	}
	
	/****
	 * 登录会议室
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward loginAppointment(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		  String appId = request.getParameter("appId");
		  Appointment app = appointmentService.getAppointmentById(new Integer(appId));
		 Members user = (Members)request.getSession().getAttribute("user");
		 if(user!=null){//是否登录
			 if(app.getIsPublished()==1){//判断会议是否已发布
				 //会议开始时间
				 Date startDate = app.getAppointmentStarttime();
				 startDate.setMinutes((startDate.getMinutes()-30));//会议开始时间的前30分钟到会议结束时间 段可以进入会议室
				 //当前时间
				 Calendar cc = Calendar.getInstance();
				 cc.setTime(new Date());
				 //判断当前时间是否在  会议开始时间前30分 到会议结束时间前 进入会议室
				 if(cc.getTime().after(startDate)&&(cc.getTime().after(app.getAppointmentEndtime())==false)){
					if(app.getIsEnd()!=1){
						 if(user.getUid().intValue()==app.getUserPid().intValue()){//是否主持人
							 request.setAttribute("msg", "ok");
							 request.setAttribute("roomId", appId);
							 request.setAttribute("account", user.getUsername());
						 }else{
							 MeetingMember mm = meetingMemberService.getMeetingMemberByAppId(new Integer(appId), user.getUid());
							 if(mm!=null){
								 if(app.getModeType().intValue()!=0){//会议非内部会议时，运行以下代码
									 if(mm.getIsregister()!=0){//是否报名
										 if(mm.getIskickout()!=0){//判断是否被筛选的人员参与会议
											 request.setAttribute("msg","ok");
											 request.setAttribute("roomId", appId);
											 request.setAttribute("account", user.getUsername());
										 }else{
											 request.setAttribute("msg", "对不起，您没有资格参加该会议， 感谢您的关注!!");
										 }
									 }else{
										 request.setAttribute("msg", "对不起，您没有对该会议进行报名，感谢您的关注!!");
									 }
								 }else{//内部会议，直接登录
									 request.setAttribute("msg","ok");
									 request.setAttribute("roomId", appId);
									 request.setAttribute("account", user.getUsername());
								 }
							 }else{
								 request.setAttribute("msg", "对不起，您没有获得本会议的邀请，感谢您的关注!!");
							 }
						 }
					 }else{
						 request.setAttribute("msg", "对不起，该会议已结束，感谢您的关注!!");
					}
				 }else{	
					 request.setAttribute("msg", "对不起，在会议开始前30分钟允许进入，感谢您的关注!!");
				 }
			 }else{
				 request.setAttribute("msg", "对不起， 该会议还没有发布,感谢您的关注!!");
			 }
		 }else{
			 request.setAttribute("msg", "对不起，请先登录系统!!");
		 }
		return mapping.findForward("ts");
	}
	
    /**
     *  发送邮件
     * @param request
     * @param emailArray
     * @return
     */
	public boolean SendMail(HttpServletRequest request,String emailArray,String appId,String userId,String type){
		Map<String,String> oldSettings=ForumInit.settings;
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
		
		Appointment app = appointmentService.getAppointmentById(new Integer(appId));
		String title = "在线座谈邀请";//+ app.getAppointmentName();
		String baseUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
		String message = app.getAppointmentName()+"<br>会议内容: "+app.getAppointmentDescription();
		if("baoming".equals(type)){
			baseUrl = baseUrl+ "index_meeting.jsp?action=findConditionByAppointmentStarttime";//"index_meeting.jsp?action=toSignupAppointmentMember&appId="+appId+"&userId="+userId;
			message+="<br>温馨提示：您已被该会议邀请，如需参加会议，请尽快报名";
		}else if("canhui".equals(type)){
			message+="<br>温馨提示：你已被该会议设置为主持人角色，请准时主持会议";
		}else if("updateMeeting".equals(type)){
			message+="<br>温馨提示：会议已改变，请准时参加会议";
		}else if("cancelMeeting".equals(type)){
			message+="<br>温馨提示：会议已取消";
		}else if("fabu".equals(type)){
			message+="<br>温馨提示：会议已发布，请准时参加会议";
		}
		String htmlBody = InvitationTemplate.getEmailTemplate(request,auth_username, 
				message, 
				baseUrl, type, 
				sf.format(app.getAppointmentStarttime()),
				sf.format(app.getAppointmentEndtime()));
		Mail mail=new Mail(host, port, auth, auth_username, auth_password,"1");
		String alertmsg = mail.sendMessage(test_from, test_to, title,null, htmlBody);
		if(alertmsg==null){
			return true;
		}
		else{
			return false;
		}
	}
	
	/****
	 * 跳转到会议咨询页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toEntername(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		 String appId = request.getParameter("appId");	
	     Appointment app = appointmentService.getAppointmentById(new Integer(appId));
		  request.setAttribute("app", app);	
		return mapping.findForward("entername");
	}
	
	/***
	 * 发送给主持人，邮件咨询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doEntername(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
	     try {
	    		Map<String,String> oldSettings=ForumInit.settings;
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
	    		String test_to = request.getParameter("zhuchiEmail");
	    		String sendEmail = request.getParameter("sendEmail");
	    		
	    		//处理乱码
	    		String title = java.net.URLDecoder.decode(request.getParameter("title"),"UTF-8");
	    		title = java.net.URLDecoder.decode(title,"utf-8");
	    		String htmlBody = java.net.URLDecoder.decode(request.getParameter("htmlBody"),"UTF-8");
	    		htmlBody = java.net.URLDecoder.decode(htmlBody,"utf-8");
	    		htmlBody +="<br />信息来自："+sendEmail;
	    		htmlBody +="<br /<br />注意：<font color='red' >此消息有系统发送，请勿直接回复!</font>";
	    		Mail mail=new Mail(host, port, auth, auth_username, auth_password,"1");
	    		String alertmsg = htmlBody+mail.sendMessage(test_from, test_to, title,null, htmlBody);
	    		request.setAttribute("message", "<font color='blue' size='14px;'>发送邮件成功</font>");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "<font color='red' size='14px;'>发送邮件失败</font>");
		}
		
		return toEntername(mapping, form, request, response);
	}
	
	/***
	 * 跳转到查看详细会议介绍
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toAppointmentDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		String appId = request.getParameter("appId");	
		Appointment app = appointmentService.getAppointmentById(new Integer(appId));
		List<MeetingMember> mmList = meetingMemberService.getMeetingMemberList(new Integer(appId).intValue(), 1, 1);//getMeetingMemberByAppId(new Integer(appId));
		if(mmList!=null)
		  request.setAttribute("numSize", mmList.size());
		request.setAttribute("mmList", mmList);
		request.setAttribute("app", app);
		return mapping.findForward("appointment_detail");
	}
	
	/***
	 * 添加用户表数据
	 * @param custer_account
	 * @param password
	 * @param email
	 * @param register_ip
	 * @param type
	 * @return
	 */
	public Members addMembers(String custer_account,String password,String email,String type){
			Members zhuchiUser= new Members();
			zhuchiUser.setUsername(custer_account);
	        zhuchiUser.setPassword(password);
	        zhuchiUser.setEmail(email);
	        zhuchiUser.setRegip("");
	        zhuchiUser.setSecques("");
	        zhuchiUser.setGender(new Byte("0"));
	        zhuchiUser.setAdminid(new Byte("0"));
	       
	    	zhuchiUser.setGroupexpiry(0);
	    	if(type!=null){
	    		zhuchiUser.setGroupid(new Short("17"));
	    		zhuchiUser.setExtgroupids("17");
	    	}else{
	    		zhuchiUser.setExtgroupids("");
	    		zhuchiUser.setGroupid(new Short("10"));
	    	}
	    	zhuchiUser.setLastpost(0);
	    	zhuchiUser.setPosts(0);
	    	zhuchiUser.setDigestposts(0);
	    	zhuchiUser.setOltime(new Short("0"));
	    	zhuchiUser.setPageviews(0);
	    	zhuchiUser.setCredits(0);
	    	zhuchiUser.setExtcredits1(0);
	    	zhuchiUser.setSigstatus(new Byte("0"));
	    	zhuchiUser.setTpp(new Short("0"));	
	    	zhuchiUser.setPpp(new Short("0"));
	    	zhuchiUser.setStyleid(new Short("0"));
	    	zhuchiUser.setDateformat(new Byte("0"));
	    	zhuchiUser.setTimeformat(new Byte("0"));
	    	zhuchiUser.setPmsound(new Byte("0"));
	    	zhuchiUser.setShowemail(new Byte("0"));
	    	zhuchiUser.setNewsletter(new Byte("1"));
	    	zhuchiUser.setInvisible(new Byte("0"));
	    	zhuchiUser.setTimeoffset("9999");
	    	zhuchiUser.setNewpm(new Byte("0"));
	    	zhuchiUser.setAccessmasks(new Byte("0"));
	    	zhuchiUser.setEditormode(new Byte("2"));
	    	zhuchiUser.setCustomshow(new Byte("26"));
	    	zhuchiUser.setXspacestatus(new Byte("0"));
	    	zhuchiUser.setSalt("0");
	    	memberService.insertMember(zhuchiUser);
	    	zhuchiUser = memberService.findByName(custer_account);
	    	
	    	if(zhuchiUser.getUid()>0){
	    		dataBaseService
				.runQuery(
						"INSERT INTO jrun_memberfields (uid, nickname, site,alipay,icq, qq, yahoo, msn, taobao, location, customstatus, medals, avatar, avatarwidth, avatarheight, bio, sightml,ignorepm,groupterms,authstr,spacename,buyercredit,sellercredit)"
								+ " VALUES ('"
								+ zhuchiUser.getUid()
								+ "', '', '', '', '', '', '', '', '', '', '', '', '', 0, 0, '', '', '', '','','',0,0)",
						true);
	    	}
	    	return zhuchiUser;
	}
		
	//以下是：图片上传的方法
	public ActionForward uploadFileImage(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		//处理图片上传
		File tempfile = isMakeDir();
  	    ModuleConfig ac =(ModuleConfig) request.getAttribute(Globals.MODULE_KEY);
		FileUploadUtil fileupload = new FileUploadUtil(tempfile,memeoryBlock,ac);
		try{
			fileupload.parse(request, JspRunConfig.CHARSET);
		}catch(IllegalStateException e){
			request.setAttribute("message", getMessage(request, "post_attachment_toobig"));
		}
		
		FileItem fileItem=fileupload.getFileItem("filepic");	
		if(fileItem!=null){
			if(fileItem.getFieldName()!=null&&!"".equals(fileItem.getFieldName())){
			    String result=this.uploadImage(request, fileItem);
				if(result.equals("no")){
					request.setAttribute("message","<font color='red'>没有上传图片</font>");
				}
				else if(result.equals("error")){
					request.setAttribute("message","<font color='red'>请上传图片文件</font>");
				}
				else if(result.equals("fail")){
					request.setAttribute("message","<font color='red'>图片上传失败</font>");
				}else{
					request.setAttribute("result", result);
					request.setAttribute("message", "<font color='blue'>上传图片成功</font>");
				}
			}
		}
		Members user = (Members)request.getSession().getAttribute("user");
		if("17".equals(request.getSession().getAttribute("exgroup")+"")){
			return mapping.findForward("uploadFile_mmupload");
		}else
		    return mapping.findForward("uploadFile");
	}
	
	private File isMakeDir(){
		File file = new File(FILEPATHTIME);
		if(!file.exists()){
			file.mkdirs();
		}
		return file;
	}
	private String uploadImage(HttpServletRequest request,FileItem fileItem) {
		String result="";
		Map<String, String> settings = ForumInit.settings;
		String timeoffset = settings.get("timeoffset");
		String attachurl = settings.get("attachurl");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		if(fileItem!=null && fileItem.getSize()>0){
			String filename = fileItem.getName();
			int start = filename.lastIndexOf('\\');
			filename = filename.substring(start + 1);
			int last = filename.lastIndexOf(".");
			String filetype = filename.substring(last + 1);
			filetype = filetype.toLowerCase();
			if (filetype.matches("jpg|jpeg|gif|png|bmp")) {
				String targetname = Common.gmdate("yyyyMMdd", timestamp,timeoffset)+ Md5Token.getInstance().getLongToken(filename + Math.rint(System.currentTimeMillis()))+ "." + filetype;
				String filedir = "month_" + Common.gmdate("yyMM",timestamp,timeoffset);
				filedir = "day_"+ Common.gmdate("yyMMdd",timestamp,timeoffset);
				String filesubdir = servlet.getServletContext().getRealPath("/")+ "./"+attachurl + "/" + filedir;
				File subdirfile = new File(filesubdir);
				if (!subdirfile.exists()) {
					subdirfile.mkdir();
				}
				String targetpath = servlet.getServletContext().getRealPath("/")+attachurl + "/" + filedir + "/" + targetname;
				if(!FileUploadUtil.write2file(fileItem, new File(targetpath))){
					return "fail";
				}
				result=attachurl + "/" + filedir + "/" + targetname;
			}
			else {
				result= "error";
			}					
		}
		else{
			result= "no";			
		}
		return result;
	}
	
	/****
	 * 结束会议
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward closeMeeting(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		String appId = request.getParameter("appId");
		try {
			Appointment app = appointmentService.getAppointmentById(new Integer(appId));
			app.setIsEnd(1);
			appointmentService.updateAppointment(app);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return findConditionByAppointmentStarttime(mapping, form, request, response);
	}
	
	/***
	 * 后台(管理员和研究员)查看会议详细信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAppointmentInfoById(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		 int uid=(Integer)request.getSession().getAttribute("jsprun_uid");
    	 if(uid==0){
    		 request.setAttribute("resultInfo","你还没有登录,不能执行此操作");
    		 return mapping.findForward("showMessage");
    	 }
    	 
    	 String appId = request.getParameter("appId");	
    	 String type = request.getSession().getAttribute("jsprun_groupid")+"";
    	 try {
			Appointment app = appointmentService.getAppointmentById(new Integer(appId));
			List<MeetingMember> mmList = meetingMemberService.getMeetingMemberList(new Integer(appId).intValue(), 1, 1);//getMeetingMemberByAppId(new Integer(appId));
			String canyu_user = "";
			if(mmList!=null){
				for(MeetingMember mm:mmList){
					canyu_user += mm.getUser().getUsername()+",";
				} 
			}
			//获取聊天内容及视频下载地址
			List<String> videoUrlList = ConnectionDBE.getTcRecordingByRoomId(appId);
			List<String> chatContentList = ConnectionDBE.getTcChatByRoomId(appId);
			
			Members user = (Members)request.getSession().getAttribute("user");
	  	    //type = user.getExtgroupids();
	  	    request.setAttribute("videoUrlList", videoUrlList);
	  	    request.setAttribute("chatContentList", chatContentList);
			request.setAttribute("canyu_user", canyu_user);
			request.setAttribute("app", app);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if("17".equals(type)){//研究员
			return mapping.findForward("appointmentInfo_mminfo");
		}else 
			return mapping.findForward("appointmentInfo_mminfo");
			//return mapping.findForward("appointmentInfo");  //old
	}
}
