package cn.jsprun.struts.action;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Members;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.Log;
import cn.jsprun.utils.Md5Token;

public class AdmincpAction extends BaseAction {
	/*@SuppressWarnings("unchecked")
	public ActionForward admincp1(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String admin_password=request.getParameter("admin_password");
		Map<String,String> settings=ForumInit.settings;
		HttpSession session = request.getSession();
		
		byte adminid =request.getParameter("adminid")!=null?Byte.parseByte(request.getParameter("adminid")):Byte.parseByte(session.getAttribute("jsprun_adminid")+"");
		int cpaccess = 10;
		int errorcount=0;
		String onlineip = Common.get_onlineip(request);
		int uid = (Integer) session.getAttribute("jsprun_uid");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
		if (adminid <= 0) {			
			cpaccess = -1;
		} else{
			String adminipaccess = settings.get("adminipaccess");
			if (!"".equals(adminipaccess)&&adminid == 1 &&!Common.ipaccess(onlineip,adminipaccess)) {
				cpaccess=2;
			}else{
				String addonlineip = Common.toDigit(settings.get("admincp_checkip"))>0? " AND ip='"+onlineip+"'" : "";
				List<Map<String,String>> errorcounts=dataBaseService.executeQuery("SELECT errorcount FROM jrun_adminsessions WHERE uid='"+uid+"'"+addonlineip+" AND dateline>'"+(timestamp-1800)+"'");
				if(errorcounts!=null&&errorcounts.size()>0){
					errorcount=Integer.valueOf(errorcounts.get(0).get("errorcount"));
					if(errorcount==-1){
						dataBaseService.runQuery("UPDATE jrun_adminsessions SET dateline='"+timestamp+"' WHERE uid='"+uid+"'",true);
						cpaccess = 3;
					} else if (errorcount <=3) {
						cpaccess = 1;
					}else{
						cpaccess = 0;
					}
				}else{
					dataBaseService.runQuery("DELETE FROM jrun_adminsessions WHERE uid='"+uid+"' OR dateline<'"+(timestamp-1800)+"'",true);
					dataBaseService.runQuery("INSERT INTO jrun_adminsessions (uid, ip, dateline, errorcount) VALUES ('"+uid+"', '"+onlineip+"', '"+timestamp+"', '0')",true);
					cpaccess = 1;
				}
			}
		}
		String jsprun_userss=(String)session.getAttribute("jsprun_userss");
		if(jsprun_userss==null){
			jsprun_userss="";
		}
		String action=request.getParameter("action");
		Members user  = (Members)session.getAttribute("user");
		if(action!=null&&!("|main|header|menu|illegallog|ratelog|modslog|medalslog|creditslog|banlog|cplog|errorlog|".contains("|"+action+"|"))){
			String extra=null;
			switch(cpaccess){
				case 0:
					extra="PERMISSION DENIED";
					break;
				case 1:
					extra="AUTHENTIFICATION(ERROR #"+errorcount+")";
					break;
				case 2:
					extra="IP ACCESS DENIED";
					break;
				case 3:
					StringBuffer extraBuffer=new StringBuffer();
					Map<String,String[]> map=request.getParameterMap();
					if(map!=null&&map.size()>0){
						Set<String> keys=map.keySet();
						for(String key:keys){
							if(!("|action|adminaction|sid|formhash|admin_password|".contains("|"+key+"|"))){
								String[] values=map.get(key);
								if(values!=null){
									int length=values.length;
									if(length>1){
										extraBuffer.append("; "+key+"=Array(");
										for(int i=0;i<length;i++){
											extraBuffer.append(i+"="+Common.cutstr(values[i], 15, null)+"; ");
										}
										extraBuffer.append(")");
									}else if(!"".equals(values[0])){
										extraBuffer.append("; "+key+"=");
										extraBuffer.append(Common.cutstr(values[0], 15, null));
									}
								}
							}
						}
					}
					extra=extraBuffer.length()>2?Common.htmlspecialchars(extraBuffer.substring(2)):"";
					break;
			}
			Log.writelog("cplog", timestamp, timestamp + "\t" + jsprun_userss + "\t" + adminid + "\t" + onlineip + "\t" + action + "\t"+("home".equals(action)? "" :extra));
		}
		if(cpaccess==-1){
			this.loginmsg(request, new StringBuffer(""), null, "dirlogin", null, null);
			return mapping.findForward("toAdmincp");
		}
		else if(cpaccess==0){
			CookieUtil.clearCookies(request, response, settings);
			this.loginmsg(request, new StringBuffer(getMessage(request, "noaccess")), null, null, null, null);
			return mapping.findForward("toAdmincp");
		}else if(cpaccess==1){
			String password = admin_password!=null?Md5Token.getInstance().getLongToken(admin_password):"";
			if(!password.equals(session.getAttribute("jsprun_pw"))){
				if(admin_password!=null){
					dataBaseService.runQuery("UPDATE jrun_adminsessions SET errorcount=errorcount+1 WHERE uid='"+uid+"'",true);
					Log.writelog("cplog", timestamp, timestamp + "\t" + jsprun_userss + "\t" + adminid + "\t" + onlineip + "\t" + action + "\tAUTHENTIFICATION(PASSWORD)");
				}
				loginmsg(request,null, null, "login", (String)session.getAttribute("jsprun_sid"),(String)session.getAttribute("jsprun_userss"));
				return mapping.findForward("toAdmincp");
			}else{
				dataBaseService.runQuery("UPDATE jrun_adminsessions SET errorcount='-1' WHERE uid='"+uid+"'",true);
				String extra=(String)request.getAttribute("extra");
				this.loginmsg(request, new StringBuffer(getMessage(request, "login_succeed")), "admincp.jsp?"+extra, null, null, null);
				String url_forward=request.getParameter("url_forward");
				if(!Common.empty(url_forward)) {
					try {
						response.getWriter().write("<meta http-equiv=refresh content=\"0;URL="+url_forward+"\">");
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				}
				return mapping.findForward("toAdmincp");
			}
		}else if(cpaccess==2){
			this.loginmsg(request, new StringBuffer(getMessage(request, "noaccess_ip")), null, null, null, null);
			return mapping.findForward("toAdmincp");
		}
		request.setAttribute("isfounder", Common.isFounder(settings, user));
		session.setAttribute("members", user);
		
		String frames=request.getParameter("frames");
		String sid=request.getParameter("sid");
		String url=null;
		if((action==null||"yes".equals(frames))&&sid==null){
			url="/admin/index.jsp";
		}else if("header".equals(action)){
			url="/admin/header.jsp";
		}else if("menu".equals(action)){
			url="/admin/menu.jsp";
		}else{
			url="admin/page/main.jsp";
		}
		if(usergroups.get("radminid").equals("1")){
			List<Map<String,String>> dactionarray = dataBaseService.executeQuery("SELECT disabledactions FROM jrun_adminactions WHERE admingid='"+usergroups.get("groupid")+"'");
			if(dactionarray!=null&&dactionarray.size()>0){
				Map<String,String> dactionarrayMap = dataParse.characterParse(dactionarray.get(0).get("disabledactions"),false);
				Set<String> actionskey = dactionarrayMap.keySet();
				for(String key:actionskey){
					String keys[] = key.split(":");
					for(String s:keys){
						if(s.equals(action)){
							request.setAttribute("message_key","action_noaccess");
							return mapping.findForward("message");
						}
					}
				}
			}
		}
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	*/
	
	/**
	 * 一期的管理员后台登录跳转方式
	 */
	 public ActionForward admincp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    {
	        String admin_password = request.getParameter("admin_password");
	        Map settings = ForumInit.settings;
	        HttpSession session = request.getSession();
	        byte adminid = request.getParameter("adminid") == null ? Byte.parseByte((new StringBuilder()).append(session.getAttribute("jsprun_adminid")).toString()) : Byte.parseByte(request.getParameter("adminid"));
	        int cpaccess = 10;
	        int errorcount = 0;
	        String onlineip = Common.get_onlineip(request);
	        int uid = ((Integer)session.getAttribute("jsprun_uid")).intValue();
	        int timestamp = ((Integer)request.getAttribute("timestamp")).intValue();
	        Map usergroups = (Map)request.getAttribute("usergroups");
	        if(adminid <= 0)
	            cpaccess = -1;
	        else
	        if((new StringBuilder()).append(session.getAttribute("houtai")).toString().equals(""))
	        {
	            String adminipaccess = (String)settings.get("adminipaccess");
	            if(!"".equals(adminipaccess) && adminid == 1 && !Common.ipaccess(onlineip, adminipaccess))
	            {
	                cpaccess = 2;
	            } else
	            {
	                String addonlineip = Common.toDigit((String)settings.get("admincp_checkip")) <= 0 ? "" : (new StringBuilder(" AND ip='")).append(onlineip).append("'").toString();
	                List errorcounts = dataBaseService.executeQuery((new StringBuilder("SELECT errorcount FROM jrun_adminsessions WHERE uid='")).append(uid).append("'").append(addonlineip).append(" AND dateline>'").append(timestamp - 1800).append("'").toString());
	                if(errorcounts != null && errorcounts.size() > 0)
	                {
	                    errorcount = Integer.valueOf((String)((Map)errorcounts.get(0)).get("errorcount")).intValue();
	                    if(errorcount == -1)
	                    {
	                        dataBaseService.runQuery((new StringBuilder("UPDATE jrun_adminsessions SET dateline='")).append(timestamp).append("' WHERE uid='").append(uid).append("'").toString(), true);
	                        cpaccess = 3;
	                    } else
	                    if(errorcount <= 3)
	                        cpaccess = 1;
	                    else
	                        cpaccess = 0;
	                } else
	                {
	                    dataBaseService.runQuery((new StringBuilder("DELETE FROM jrun_adminsessions WHERE uid='")).append(uid).append("' OR dateline<'").append(timestamp - 1800).append("'").toString(), true);
	                    dataBaseService.runQuery((new StringBuilder("INSERT INTO jrun_adminsessions (uid, ip, dateline, errorcount) VALUES ('")).append(uid).append("', '").append(onlineip).append("', '").append(timestamp).append("', '0')").toString(), true);
	                    cpaccess = 1;
	                }
	            }
	        }
	        String jsprun_userss = (String)session.getAttribute("jsprun_userss");
	        if(jsprun_userss == null)
	            jsprun_userss = "";
	        String action = request.getParameter("action");
	        Members user = (Members)session.getAttribute("user");
	        if(action != null && !"|main|header|menu|illegallog|ratelog|modslog|medalslog|creditslog|banlog|cplog|errorlog|".contains((new StringBuilder("|")).append(action).append("|").toString()))
	        {
	            String extra = null;
	            switch(cpaccess)
	            {
	            default:
	                break;

	            case 0: // '\0'
	                extra = "PERMISSION DENIED";
	                break;

	            case 1: // '\001'
	                extra = (new StringBuilder("AUTHENTIFICATION(ERROR #")).append(errorcount).append(")").toString();
	                break;

	            case 2: // '\002'
	                extra = "IP ACCESS DENIED";
	                break;

	            case 3: // '\003'
	                StringBuffer extraBuffer = new StringBuffer();
	                Map map = request.getParameterMap();
	                if(map != null && map.size() > 0)
	                {
	                    Set keys = map.keySet();
	                    for(Iterator iterator = keys.iterator(); iterator.hasNext();)
	                    {
	                        String key = (String)iterator.next();
	                        if(!"|action|adminaction|sid|formhash|admin_password|".contains((new StringBuilder("|")).append(key).append("|").toString()))
	                        {
	                            String values[] = (String[])map.get(key);
	                            if(values != null)
	                            {
	                                int length = values.length;
	                                if(length > 1)
	                                {
	                                    extraBuffer.append((new StringBuilder("; ")).append(key).append("=Array(").toString());
	                                    for(int i = 0; i < length; i++)
	                                        extraBuffer.append((new StringBuilder(String.valueOf(i))).append("=").append(Common.cutstr(values[i], 15, null)).append("; ").toString());

	                                    extraBuffer.append(")");
	                                } else
	                                if(!"".equals(values[0]))
	                                {
	                                    extraBuffer.append((new StringBuilder("; ")).append(key).append("=").toString());
	                                    extraBuffer.append(Common.cutstr(values[0], 15, null));
	                                }
	                            }
	                        }
	                    }

	                }
	                extra = extraBuffer.length() <= 2 ? "" : Common.htmlspecialchars(extraBuffer.substring(2));
	                break;
	            }
	            Log.writelog("cplog", timestamp, (new StringBuilder(String.valueOf(timestamp))).append("\t").append(jsprun_userss).append("\t").append(adminid).append("\t").append(onlineip).append("\t").append(action).append("\t").append("home".equals(action) ? "" : extra).toString());
	        }
	        if(cpaccess == -1)
	        {
	            loginmsg(request, new StringBuffer(""), null, "dirlogin", null, null);
	            return mapping.findForward("toAdmincp");
	        }
	        if(cpaccess == 0)
	        {
	            CookieUtil.clearCookies(request, response, settings);
	            loginmsg(request, new StringBuffer(getMessage(request, "noaccess", new String[0])), null, null, null, null);
	            return mapping.findForward("toAdmincp");
	        }
	        if(cpaccess == 1)
	        {
	            String password = admin_password == null ? "" : Md5Token.getInstance().getLongToken(admin_password);
	            if(!password.equals(session.getAttribute("jsprun_pw")))
	            {
	                if(admin_password != null)
	                {
	                    dataBaseService.runQuery((new StringBuilder("UPDATE jrun_adminsessions SET errorcount=errorcount+1 WHERE uid='")).append(uid).append("'").toString(), true);
	                    Log.writelog("cplog", timestamp, (new StringBuilder(String.valueOf(timestamp))).append("\t").append(jsprun_userss).append("\t").append(adminid).append("\t").append(onlineip).append("\t").append(action).append("\tAUTHENTIFICATION(PASSWORD)").toString());
	                }
	                loginmsg(request, null, null, "login", (String)session.getAttribute("jsprun_sid"), (String)session.getAttribute("jsprun_userss"));
	                return mapping.findForward("toAdmincp");
	            }
	            dataBaseService.runQuery((new StringBuilder("UPDATE jrun_adminsessions SET errorcount='-1' WHERE uid='")).append(uid).append("'").toString(), true);
	            String extra = (String)request.getAttribute("extra");
	            loginmsg(request, new StringBuffer(getMessage(request, "login_succeed", new String[0])), (new StringBuilder("admincp.jsp?")).append(extra).toString(), null, null, null);
	            String url_forward = request.getParameter("url_forward");
	            if(!Common.empty(url_forward))
	            {
	                try
	                {
	                    response.getWriter().write((new StringBuilder("<meta http-equiv=refresh content=\"0;URL=")).append(url_forward).append("\">").toString());
	                }
	                catch(IOException e)
	                {
	                    e.printStackTrace();
	                }
	                return null;
	            } else
	            {
	                return mapping.findForward("toAdmincp");
	            }
	        }
	        if(cpaccess == 2)
	        {
	            loginmsg(request, new StringBuffer(getMessage(request, "noaccess_ip", new String[0])), null, null, null, null);
	            return mapping.findForward("toAdmincp");
	        }
	        request.setAttribute("isfounder", Boolean.valueOf(Common.isFounder(settings, user)));
	        session.setAttribute("members", user);
	        String frames = request.getParameter("frames");
	        String sid = request.getParameter("sid");
	        String url = null;
	        if((action == null || "yes".equals(frames)) && sid == null)
	            url = "/admin/index.jsp";
	        else
	        if("header".equals(action))
	            url = "/admin/header.jsp";
	        else
	        if("menu".equals(action))
	            url = "/admin/menu.jsp";
	        else
	            url = "admin/page/main.jsp";
	        if(((String)usergroups.get("radminid")).equals("1"))
	        {
	            List dactionarray = dataBaseService.executeQuery((new StringBuilder("SELECT disabledactions FROM jrun_adminactions WHERE admingid='")).append((String)usergroups.get("groupid")).append("'").toString());
	            if(dactionarray != null && dactionarray.size() > 0)
	            {
	                Map dactionarrayMap = dataParse.characterParse((String)((Map)dactionarray.get(0)).get("disabledactions"), false);
	                Set actionskey = dactionarrayMap.keySet();
	                for(Iterator iterator1 = actionskey.iterator(); iterator1.hasNext();)
	                {
	                    String key = (String)iterator1.next();
	                    String keys[] = key.split(":");
	                    String as[];
	                    int k = (as = keys).length;
	                    for(int j = 0; j < k; j++)
	                    {
	                        String s = as[j];
	                        if(s.equals(action))
	                        {
	                            request.setAttribute("message_key", "action_noaccess");
	                            return mapping.findForward("message");
	                        }
	                    }

	                }

	            }
	        }
	        try
	        {
	            request.getRequestDispatcher(url).forward(request, response);
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	        return null;
	    }
	
	
	
	public ActionForward logout(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Map<String,String> settings=ForumInit.settings;
		CookieUtil.clearCookies(request, response,settings);
		session.removeAttribute("members");
		session.removeAttribute("jsprun_uid");
		session.removeAttribute("jsprun_userss");
		session.removeAttribute("jsprun_groupid");
		session.removeAttribute("exgroup");
		session.removeAttribute("jsprun_adminid");
		session.removeAttribute("jsprun_pw");
		session.removeAttribute("formhash");
		session.removeAttribute("user");
		session.removeAttribute("styleid");
		session.removeAttribute("houtai");
		dataBaseService.runQuery("DELETE FROM jrun_adminsessions WHERE uid='"+session.getAttribute("jsprun_uid")+"'",true);
		this.loginmsg(request, new StringBuffer(getMessage(request, "logout_succeed_admincp")), "index.jsp", null, null, null);
		return mapping.findForward("toAdmincp");
	}
	
	public ActionForward houtailogin(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		Map<String,String> settings=ForumInit.settings;
		String name=request.getParameter("adminname");		
		String admin_password=request.getParameter("admin_password");
		if(name!=null){
			if("".equals(name)){
				request.setAttribute("message","请输入账号");
				request.setAttribute("imgerror","jfkldsf");
				return mapping.findForward("message");
			}
			if("".equals(admin_password)){
				request.setAttribute("message","请输入密码");
				request.setAttribute("imgerror","jfkldsf");
				return mapping.findForward("message");
			}
			Members member=memberService.findByName(name);
			if(member!=null){
				if(member.getPassword().equals(Md5Token.getInstance().getLongToken(admin_password))){
					if(member.getAdminid()==1||member.getAdminid()==2||member.getAdminid()==3){
						String cookietime=CookieUtil.getCookie(request, "cookietime",true,settings);
						int time=Common.toDigit(cookietime);
						String styleid = request.getParameter("styleid");
						if(styleid!=null&&!styleid.equals("")){
							session.setAttribute("styleid", styleid);
						}else{
							styleid=(String)session.getAttribute("styleid");
						}
						styleid=styleid==null||styleid.equals("")?(member.getStyleid()!=0?member.getStyleid().toString():settings.get("styleid")):styleid;
						CookieUtil.setCookie(request, response, "uid", String.valueOf(member.getUid()), time, true,settings);
						CookieUtil.setCookie(request, response, "cookietime", String.valueOf(cookietime), 31536000, true,settings);
						CookieUtil.setCookie(request, response, "auth", Md5Token.getInstance().getLongToken(member.getPassword()+"\t"+member.getSecques()+"\t"+member.getUid()), time, true,settings);
						session.setAttribute("jsprun_uid", member.getUid());
						session.setAttribute("jsprun_userss", member.getUsername());
						session.setAttribute("jsprun_groupid", member.getGroupid());
						session.setAttribute("exgroup",member.getExtgroupids());
						session.setAttribute("jsprun_adminid", member.getAdminid());
						session.setAttribute("jsprun_pw", member.getPassword());
						session.setAttribute("formhash", Common.getRandStr(8,false));
						session.setAttribute("user", member);
						session.setAttribute("styleid",styleid);
						request.setAttribute("refresh", "true");
						Common.setDateformat(session, settings);
						request.setAttribute("sessionexists", false);
						session.setAttribute("houtai","aaaa");
						String extra=(String)request.getAttribute("extra");
						this.loginmsg(request, new StringBuffer(getMessage(request, "login_succeed")), "admincp.jsp?adminid="+session.getAttribute("jsprun_adminid"), null, null, null);					
						return mapping.findForward("toAdmincp");
					}
					else{
						request.setAttribute("message","你没有权限");
						request.setAttribute("imgerror","jfkldsf");
						return mapping.findForward("message");
					}
				}
				else{
					request.setAttribute("message","密码错误");
					request.setAttribute("imgerror","jfkldsf");
					return mapping.findForward("message");
				}
			}
			else{
				request.setAttribute("message","用户名错误");
				request.setAttribute("imgerror","jfkldsf");
				return mapping.findForward("message");
			}
		}
		return null;
	}
	
	private void loginmsg(HttpServletRequest request,StringBuffer message,String url_forward,String msgtype,String jsprun_sid,String jsprun_user){
		HttpSession session=request.getSession();
		if(message==null){
			message=new StringBuffer();
		}
		if(url_forward==null){
			url_forward="";
		}
		if(msgtype==null){
			msgtype="message";
		}
		if("message".equals(msgtype)){
			message.insert(0, "<tr><td>&nbsp;</td><td align='center' colspan='3' >");
			if(!"".equals(url_forward)){
				message.append("<br /><br /><a href='"+url_forward+"'>"+getMessage(request, "message_redirect")+"</a>");
				message.append("<script>setTimeout(\"redirect('"+url_forward+"');\", 1250);</script><br /><br /><br /></td><td>&nbsp;</td></tr>");
			}else{
				message.append("<br /><br /><br />");
			}
		}
		else if("dirlogin".equals(msgtype)){
			String adminaction=request.getParameter("adminaction");
			String frames=request.getParameter("frames");
			String extra=(String)request.getAttribute("extra");
			if(extra==null){
				extra="";
			}
			extra = adminaction!=null && frames==null? "frames=yes"+("".equals(extra)?"":"&"+extra):"?"+extra;
			message.append("<form id=\"loginform\" method=\"post\" name=\"login\" action=\"logging.jsp?action=login&loginsubmit=true\"><div align=\"center\"><br/><br/><input type=\"hidden\" name=\"referer\" value=\"admincp.jsp\"/><input type=\"hidden\" name=\"cookietime\" value=\"2592000\" /><input type=\"hidden\" name=\"accessing\" value=\"logging\" /><input type=\"hidden\" name=\"loginfield\" value=\"username\" />用户名:&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"text\" id=\"username\" name=\"username\" style='width:150px' maxlength=\"40\" tabindex=\"1\"	value=\"用户名\" onclick=\"this.value = ''\" /><br/><br/>密&nbsp;&nbsp;&nbsp;&nbsp;码:&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"password\" id=\"password\" name=\"password\"  style='width:150px'	tabindex=\"2\"	onkeypress=\"if((event.keyCode ? event.keyCode : event.charCode) == 13) $('loginform').submit()\" /><br/><br/><button name=\"userlogin\" type=\"submit\" value=\"true\" class='button'>登录</button></div></form>");
		}
		else{
			String adminaction=request.getParameter("adminaction");
			String frames=request.getParameter("frames");
			String extra=(String)request.getAttribute("extra");
			if(extra==null){
				extra="";
			}
			extra = adminaction!=null && frames==null? "?frames=yes"+("".equals(extra)?"":"&"+extra):"?"+extra;
			message.append("<form method='post' name='login' action='admincp.jsp"+extra+"'><input type='hidden' name='sid' value='"+jsprun_sid+"'><input type='hidden' name='frames' value='yes'><input type='hidden' name='url_forward' value='"+url_forward+"'><tr><td>&nbsp;</td><td align='right'>"+getMessage(request, "username")+":</td><td>"+jsprun_user+"</td><td><a href='logging.jsp?action=logout&formhash="+Common.getRandStr(8, false)+"&referer=index.jsp'>"+getMessage(request, "menu_logout")+"</a></td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td align='right'>"+getMessage(request, "password")+":</td><td><input type='password' name='admin_password' size='25'></td><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td class='line1'>&nbsp;</td><td class='line1' align='center'><input type='submit' class='button' value='"+getMessage(request, "submit")+"' /><script language='JavaScript'>document.login.admin_password.focus();</script></td><td class='line1'>&nbsp;</td><td>&nbsp;</td></tr></form>");
		}
		request.setAttribute("message", message.toString());
	}
}