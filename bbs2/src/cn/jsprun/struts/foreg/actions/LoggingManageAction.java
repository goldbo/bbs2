package cn.jsprun.struts.foreg.actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Members;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ConnectionDBBlog;
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Log;
import cn.jsprun.utils.Md5Token;

import com.wingo.sso.SSOAppManager;

public class LoggingManageAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward toLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> settings = ForumInit.settings;
		HttpSession session = request.getSession();
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		if (jsprun_uid > 0) {
			request.setAttribute("successInfo", getMessage(request,
					"login_succeed_3", (String) session
							.getAttribute("jsprun_userss")));
			request.setAttribute("requestPath", settings.get("indexname"));
			return mapping.findForward("showMessage");
		}
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		boolean seccodecheck = (Integer.valueOf(settings.get("seccodestatus")) & 2) > 0;
		if (seccodecheck
				&& loginCheck(Common.get_onlineip(request), timestamp) > 0) {
			request.setAttribute("seccodedata", dataParse.characterParse(
					settings.get("seccodedata"), false));
		}
		request.setAttribute("seccodecheck", seccodecheck);
		String timeoffset = (String) session.getAttribute("timeoffset");
		String timeformat = (String) session.getAttribute("timeformat");
		String dateformat = (String) session.getAttribute("dateformat");
		int offset = timeoffset.compareTo("0");
		request.setAttribute("timestamp", timestamp);
		request.setAttribute("thetimenow", "(GMT "
				+ (offset >= 0 ? (offset == 0 ? "" : "+" + timeoffset)
						: timeoffset)
				+ ") "
				+ Common.gmdate(dateformat + " " + timeformat, timestamp,
						timeoffset));
		request.setAttribute("forumStyles", dataParse.characterParse(settings
				.get("forumStyles"), true));
		String cookietimes = CookieUtil.getCookie(request, "cookietime", true,
				settings);
		int cookietime = cookietimes != null ? Common.toDigit(cookietimes) : -1;
		request.setAttribute("cookietime", cookietime > -1 ? cookietime
				: 2592000);
		String referer = request.getParameter("referer");
		
		if (referer == null) {
			referer = request.getHeader("Referer");
			referer = referer != null ? referer.substring(referer
					.lastIndexOf("/") + 1) : "";
		}

		request.setAttribute("referer", Common.matches(referer,
				"(logging|register)") ? "" : referer);
		String systemtype=request.getParameter("type");
		if(systemtype==null||"".equals(systemtype)){
			return mapping.findForward("toLogin");
		}
		else{
			return mapping.findForward("systemLogin");
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		HttpSession session = request.getSession();
		Md5Token md5 = Md5Token.getInstance();
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		boolean isfastsuccess = Common.isshowsuccess(session, "login_succeed");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
        String pwd=password;
		if (jsprun_uid > 0) {
			if (isfastsuccess) {
				Common.requestforward(response, settings.get("indexname"));
				return null;
			} else {
				request.setAttribute("successInfo", getMessage(request,
						"login_succeed_3", (String) session
								.getAttribute("jsprun_userss")));
				request.setAttribute("requestPath", settings.get("indexname"));
				return mapping.findForward("showMessage");
			}
		}
		String referer = request.getParameter("referer");
		if (referer == null) {
			referer = request.getHeader("Referer");
		} else if (referer.equals("")) {
			referer = settings.get("indexname");
		}

		String onlineip = Common.get_onlineip(request);
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		int loginperm = loginCheck(onlineip, timestamp);
		if (loginperm <= 0) {
			request.setAttribute("resultInfo", getMessage(request,
					"login_strike"));
			return mapping.findForward("showMessage");
		}
		String styleid = request.getParameter("styleid");
		if (styleid != null && !styleid.equals("")) {
			session.setAttribute("styleid", styleid);
		} else {
			styleid = (String) session.getAttribute("styleid");
		}
		boolean seccodecheck = (Integer.valueOf(settings.get("seccodestatus")) & 2) > 0;
		if (seccodecheck && loginperm > 0) {
			request.setAttribute("seccodedata", dataParse.characterParse(
					settings.get("seccodedata"), false));
		}
		String seccodeverify = request.getParameter("seccodeverify");
		boolean seccodemiss = (seccodecheck && seccodeverify == null)
				|| (seccodecheck && seccodeverify != null && seccodeverify
						.equals("")) ? true : false;
		if (seccodecheck && !seccodemiss) {
			if (!seccodeverify.equals(session.getAttribute("rand"))) {
				request.setAttribute("errorInfo", getMessage(request,
						"submit_seccode_invalid"));
				return mapping.findForward("showMessage");
			}
		}
		String loginauth = request.getParameter("loginauth");
		int questionid = Common.toDigit(request.getParameter("questionid"));
		String answer = request.getParameter("answer");
		String cookietime = request.getParameter("cookietime");
		String loginmode = request.getParameter("loginmode");
		String secques = Common.quescrypt(questionid, answer);
		Map map = null;
		if (!"administrator".equals(username)) {
			map = Common.loginE(username,md5.getLongToken(password));// 调用登录方法查询e声网相应用户
			if (map == null) {
				loginFailed(loginperm, onlineip, timestamp);
				request.setAttribute("successInfo", getMessage(request,
						"login_invalid"));
				request.setAttribute("requestPath",
						"logging.jsp?action=login&referer=" + referer);
				return mapping.findForward("showMessage");
			} else {
				String newpass = md5.getLongToken(password);
				String sqlGetMember = "SELECT username From jrun_members WHERE username='"+ username + "'";
				List<Map<String, String>> lm = dataBaseService
						.executeQuery(sqlGetMember);
				if (lm == null || lm.size() == 0) {
					String[] initcredits = settings.get("initcredits").split(
							",");
					int credits = Integer.valueOf(initcredits[0]);
					int initcredit1 = Integer.valueOf(initcredits[1]);
					int initcredit2 = Integer.valueOf(initcredits[2]);
					int initcredit3 = Integer.valueOf(initcredits[3]);
					int initcredit4 = Integer.valueOf(initcredits[4]);
					int initcredit5 = Integer.valueOf(initcredits[5]);
					int initcredit6 = Integer.valueOf(initcredits[6]);
					int initcredit7 = Integer.valueOf(initcredits[7]);
					int initcredit8 = Integer.valueOf(initcredits[8]);
					int regverify = Integer.valueOf(settings.get("regverify"));
					String bday = request.getParameter("bday");
					String signature = request.getParameter("signature");
					Map<String, String> groupinfo = dataBaseService
							.executeQuery(
									"SELECT groupid, allownickname, allowcstatus, allowavatar, allowcusbbcode, allowsigbbcode, allowsigimgcode, maxsigsize FROM jrun_usergroups WHERE "
											+ (regverify > 0 ? "groupid='8'"
													: "creditshigher <= "
															+ credits
															+ " AND "
															+ credits
															+ "< creditslower LIMIT 1"))
							.get(0);
					int maxsigsize = Integer.valueOf(groupinfo
							.get("maxsigsize"));
					byte sigstatus = Byte.valueOf("0");
					bday = Common.datecheck(bday) ? Common.dateformat(bday)
							: "0000-00-00";
					dataBaseService
							.runQuery(
									"INSERT INTO jrun_regips (ip, count, dateline) VALUES ('"
											+ onlineip + "', '1', '"
											+ timestamp + "')", true);
					int uid = dataBaseService
							.insert(
									"INSERT INTO jrun_members (username, password, secques, gender, adminid, groupid, regip, regdate, lastvisit, lastactivity, posts, credits, extcredits1, extcredits2, extcredits3, extcredits4, extcredits5, extcredits6, extcredits7, extcredits8, email, bday, sigstatus, tpp, ppp, styleid, dateformat, timeformat, pmsound, showemail,newsletter, invisible, timeoffset,salt)VALUES"
											+ " ('"
											+ Common.addslashes(username)
											+ "', '"
											+ newpass
											+ "', '"
											+ secques
											+ "', '0', '0', '"
											+ groupinfo.get("groupid")
											+ "', '"
											+ onlineip
											+ "', '"
											+ timestamp
											+ "', '"
											+ timestamp
											+ "', '"
											+ timestamp
											+ "', '0', "
											+ credits
											+ ","
											+ initcredit1
											+ ","
											+ initcredit2
											+ ","
											+ initcredit3
											+ ","
											+ initcredit4
											+ ","
											+ initcredit5
											+ ","
											+ initcredit6
											+ ","
											+ initcredit7
											+ ","
											+ initcredit8
											+ ", '"
											+ map.get("email")
											+ "', '"
											+ bday
											+ "', '"
											+ sigstatus
											+ "', '0', '0', '0', '0', '0', '0', '0', '1', '0', '9999','0')",
									true);
					if (uid > 0) {
						dataBaseService
								.runQuery(
										"INSERT INTO jrun_memberfields (uid, nickname, site,alipay,icq, qq, yahoo, msn, taobao, location, customstatus, medals, avatar, avatarwidth, avatarheight, bio, sightml,ignorepm,groupterms,authstr,spacename,buyercredit,sellercredit)"
												+ " VALUES ('"
												+ uid
												+ "', '', '', '', '', '', '', '', '', '', '', '', '', 0, 0, '', '', '', '','','',0,0)",
										true);
					}
				} else {
					String updateMember = "UPDATE jrun_members SET password='"+ newpass + "',email='" + map.get("email")+ "',regip='" + map.get("register_ip")+ "',groupid=10 WHERE username='" + username + "'";
					dataBaseService.runQuery(updateMember,true);
				}

			}
		}
		Members member = null;
		if ("uid".equals(request.getParameter("loginfield"))) {
			member = memberService.findMemberById(Common.toDigit(username));
		} else {
			member = memberService.findByName(username);
		}
		if (loginauth != null) {
			password = loginauth;
		} else {
			password = password != null ? md5.getLongToken(password) : "";
		}
		if (member != null) {
			if (member.getSecques().equals(secques) && !seccodemiss) {
				Short groupid = member.getGroupid();
				String jsprun_userss = member.getUsername();
				Map<String, String> usergroups = (Map<String, String>) request
						.getAttribute("usergroups");
				if ("1".equals(usergroups.get("allowinvisible"))
						&& loginmode != null && loginmode.equals("invisible")
						|| loginmode != null && loginmode.equals("normal")) {
					member.setInvisible(Byte.valueOf(loginmode
							.equals("invisible") ? "1" : "0"));
					memberService.modifyMember(member);
				}
				styleid = styleid == null || styleid.equals("") ? (member
						.getStyleid() != 0 ? member.getStyleid().toString()
						: settings.get("styleid")) : styleid;
				if (cookietime == null) {
					cookietime = CookieUtil.getCookie(request, "cookietime",
							true, settings);
				}
				int time = Common.toDigit(cookietime);
				HttpSession mapsession = request.getSession();
				//CookieUtil.setCookie(request, response, "uid", String.valueOf(member.getUid()), time, true, settings);
				//CookieUtil.setCookie(request, response, "cookietime", String.valueOf(cookietime), 31536000, true, settings);
				//CookieUtil.setCookie(request, response, "auth", Md5Token.getInstance().getLongToken(member.getPassword() + "\t"+ member.getSecques() + "\t"+ member.getUid()), time, true,settings);
				member.setCuster_id(map!= null?Integer.parseInt(map.get("custer_id")+"") :0);
				session.setAttribute("jsprun_uid", member.getUid());
				session.setAttribute("jsprun_userss", jsprun_userss);
				session.setAttribute("jsprun_groupid", groupid);
				session.setAttribute("exgroup", 10);//member.getExtgroupids());
				session.setAttribute("jsprun_adminid", member.getAdminid());
				session.setAttribute("jsprun_pw", member.getPassword());
				session.setAttribute("formhash", Common.getRandStr(8, false));
				
				member.setNpwd(pwd);
				session.setAttribute("user", member);
				session.setAttribute("styleid", styleid);
				request.setAttribute("refresh", "true");
				Common.setDateformat(session, settings);
				request.setAttribute("sessionexists", false);
				SSOAppManager appMgr = new SSOAppManager(request, response);
				//mapsession.setAttribute("userInfo", member);    //用于？
				
				 //增加的,要放在Common.setDateformat(session, settings)之后??
				session.setAttribute("userInfo", member);    

				//写入cooki,与e声网同步登录  
				appMgr.setCookies(username); 
				//写入系统信息
				Cookie cookie=new Cookie("systemInfo","bbs");
			    cookie.setMaxAge(60*60*2);
			     response.addCookie(cookie);
				
				if (settings.get("passport_status").equals("shopex")
						&& !settings.get("passport_shopex").equals("0")) {
					if (groupid == 8) {
						request
								.setAttribute("successInfo", getMessage(
										request,
										"login_succeed_inactive_member",
										jsprun_userss));
						request.setAttribute("requestPath", "memcp.jsp");
						return mapping.findForward("showMessage");
					} else {
						if (isfastsuccess) {
							Common.updatesession(request, settings);
							Common.requestforward(response, referer);
							return null;
						} else {
							request.setAttribute("successInfo", getMessage(
									request, "login_succeed_3", jsprun_userss));
							request.setAttribute("requestPath", referer);
							return mapping.findForward("showMessage");
						}
					}
				} else {
					if (groupid == 8) {
						request
								.setAttribute("successInfo", getMessage(
										request,
										"login_succeed_inactive_member",
										jsprun_userss));
						request.setAttribute("requestPath", "memcp.jsp");
						return mapping.findForward("showMessage");
					} else {
						if (isfastsuccess) {
							Common.updatesession(request, settings);
							Common.requestforward(response, referer);
							return null;
						} else {
							request.setAttribute("successInfo", getMessage(
									request, "login_succeed_3", jsprun_userss));
							request.setAttribute("requestPath", referer);
							return mapping.findForward("showMessage");
						}
					}
				}
			} else if (secques == null || secques.equals("") || seccodemiss) {
				if (!member.getSecques().equals("")) {
					request.setAttribute("login_secques", getMessage(request,
							"login_secques"));
				}
				request.setAttribute("username", member.getUsername());
				request.setAttribute("cookietime", cookietime);
				request.setAttribute("loginmode", loginmode);
				request.setAttribute("styleid", styleid);
				request.setAttribute("loginauth", member.getPassword());
				request.setAttribute("seccodecheck", seccodecheck);
				return mapping.findForward("toLogin_secques");
			}
		}
		Log.writelog("illegallog", timestamp, timestamp
				+ "\t"
				+ (member != null ? member.getUsername() : username)
				+ "\t"
				+ password
				+ "\t"
				+ (secques != null && !secques.equals("") ? "Ques #"
						+ questionid : "") + "\t" + onlineip);
		loginFailed(loginperm, onlineip, timestamp);
		request.setAttribute("successInfo",
				getMessage(request, "login_invalid"));
		request.setAttribute("requestPath", "logging.jsp?action=login&referer="
				+ referer);
		return mapping.findForward("showMessage");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward login_b(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		HttpSession session = request.getSession();
		Md5Token md5 = Md5Token.getInstance();
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		boolean isfastsuccess = Common.isshowsuccess(session, "login_succeed");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
        String pwd=password;
		if (jsprun_uid > 0) {
			if (isfastsuccess) {
				Common.requestforward(response, settings.get("indexname"));
				return null;
			} else {
				request.setAttribute("msg", "login ok");
				return mapping.findForward("ts");
			}
		}
		String referer = request.getParameter("referer");
		if (referer == null) {
			referer = request.getHeader("Referer");
		} else if (referer.equals("")) {
			referer = settings.get("indexname");
		}
		String onlineip = Common.get_onlineip(request);
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		int loginperm = loginCheck(onlineip, timestamp);
		if (loginperm <= 0) {
			request.setAttribute("msg", "您已登录了5次，账户已锁定，15分钟后再登录");
			return mapping.findForward("ts");
		}
		String styleid = request.getParameter("styleid");
		if (styleid != null && !styleid.equals("")) {
			session.setAttribute("styleid", styleid);
		} else {
			styleid = (String) session.getAttribute("styleid");
		}
		boolean seccodecheck = (Integer.valueOf(settings.get("seccodestatus")) & 2) > 0;
		if (seccodecheck && loginperm > 0) {
			request.setAttribute("seccodedata", dataParse.characterParse(
					settings.get("seccodedata"), false));
		}
		String seccodeverify = request.getParameter("seccodeverify");
		boolean seccodemiss = (seccodecheck && seccodeverify == null)
				|| (seccodecheck && seccodeverify != null && seccodeverify
						.equals("")) ? true : false;
		if (seccodecheck && !seccodemiss) {
			if (!seccodeverify.equals(session.getAttribute("rand"))) {
				request.setAttribute("msg", "验证码错误");
				return mapping.findForward("ts");
			}
		}
		String loginauth = request.getParameter("loginauth");
		int questionid = Common.toDigit(request.getParameter("questionid"));
		String answer = request.getParameter("answer");
		String cookietime = request.getParameter("cookietime");
		String loginmode = request.getParameter("loginmode");
		String secques = Common.quescrypt(questionid, answer);
		Map map = null;
		if (!"administrator".equals(username)) {
			map = Common.loginE(username,md5.getLongToken(password));// 调用登录方法查询e声网相应用户
			if (map == null) {
				loginFailed(loginperm, onlineip, timestamp);
				request.setAttribute("msg", "用户名或密码错误，请重新登录");
				return mapping.findForward("ts");
			} else {
				String newpass = md5.getLongToken(password);
				String sqlGetMember = "SELECT username From jrun_members WHERE username='"+ username + "'";
				List<Map<String, String>> lm = dataBaseService
						.executeQuery(sqlGetMember);
				if (lm == null || lm.size() == 0) {
					String[] initcredits = settings.get("initcredits").split(
							",");
					int credits = Integer.valueOf(initcredits[0]);
					int initcredit1 = Integer.valueOf(initcredits[1]);
					int initcredit2 = Integer.valueOf(initcredits[2]);
					int initcredit3 = Integer.valueOf(initcredits[3]);
					int initcredit4 = Integer.valueOf(initcredits[4]);
					int initcredit5 = Integer.valueOf(initcredits[5]);
					int initcredit6 = Integer.valueOf(initcredits[6]);
					int initcredit7 = Integer.valueOf(initcredits[7]);
					int initcredit8 = Integer.valueOf(initcredits[8]);
					int regverify = Integer.valueOf(settings.get("regverify"));
					String bday = request.getParameter("bday");
					String signature = request.getParameter("signature");
					Map<String, String> groupinfo = dataBaseService
							.executeQuery(
									"SELECT groupid, allownickname, allowcstatus, allowavatar, allowcusbbcode, allowsigbbcode, allowsigimgcode, maxsigsize FROM jrun_usergroups WHERE "
											+ (regverify > 0 ? "groupid='8'"
													: "creditshigher <= "
															+ credits
															+ " AND "
															+ credits
															+ "< creditslower LIMIT 1"))
							.get(0);
					int maxsigsize = Integer.valueOf(groupinfo
							.get("maxsigsize"));
					byte sigstatus = Byte.valueOf("0");
					bday = Common.datecheck(bday) ? Common.dateformat(bday)
							: "0000-00-00";
					dataBaseService
							.runQuery(
									"INSERT INTO jrun_regips (ip, count, dateline) VALUES ('"
											+ onlineip + "', '1', '"
											+ timestamp + "')", true);
					int uid = dataBaseService
							.insert(
									"INSERT INTO jrun_members (username, password, secques, gender, adminid, groupid, regip, regdate, lastvisit, lastactivity, posts, credits, extcredits1, extcredits2, extcredits3, extcredits4, extcredits5, extcredits6, extcredits7, extcredits8, email, bday, sigstatus, tpp, ppp, styleid, dateformat, timeformat, pmsound, showemail,newsletter, invisible, timeoffset,salt)VALUES"
											+ " ('"
											+ Common.addslashes(username)
											+ "', '"
											+ newpass
											+ "', '"
											+ secques
											+ "', '0', '0', '"
											+ groupinfo.get("groupid")
											+ "', '"
											+ onlineip
											+ "', '"
											+ timestamp
											+ "', '"
											+ timestamp
											+ "', '"
											+ timestamp
											+ "', '0', "
											+ credits
											+ ","
											+ initcredit1
											+ ","
											+ initcredit2
											+ ","
											+ initcredit3
											+ ","
											+ initcredit4
											+ ","
											+ initcredit5
											+ ","
											+ initcredit6
											+ ","
											+ initcredit7
											+ ","
											+ initcredit8
											+ ", '"
											+ map.get("email")
											+ "', '"
											+ bday
											+ "', '"
											+ sigstatus
											+ "', '0', '0', '0', '0', '0', '0', '0', '1', '0', '9999','0')",
									true);
					if (uid > 0) {
						dataBaseService
								.runQuery(
										"INSERT INTO jrun_memberfields (uid, nickname, site,alipay,icq, qq, yahoo, msn, taobao, location, customstatus, medals, avatar, avatarwidth, avatarheight, bio, sightml,ignorepm,groupterms,authstr,spacename,buyercredit,sellercredit)"
												+ " VALUES ('"
												+ uid
												+ "', '', '', '', '', '', '', '', '', '', '', '', '', 0, 0, '', '', '', '','','',0,0)",
										true);
					}
				} else {
					String updateMember = "UPDATE jrun_members SET password='"+ newpass + "',email='" + map.get("email")+ "',regip='" + map.get("register_ip")+ "',groupid=10 WHERE username='" + username + "'";
					dataBaseService.runQuery(updateMember,true);
				}

			}
		}
		Members member = null;
		if ("uid".equals(request.getParameter("loginfield"))) {
			member = memberService.findMemberById(Common.toDigit(username));
		} else {
			member = memberService.findByName(username);
		}
		if (loginauth != null) {
			password = loginauth;
		} else {
			password = password != null ? md5.getLongToken(password) : "";
		}
		if (member != null) {
			if (member.getSecques().equals(secques) && !seccodemiss) {
				Short groupid = member.getGroupid();
				String jsprun_userss = member.getUsername();
				Map<String, String> usergroups = (Map<String, String>) request
						.getAttribute("usergroups");
				if ("1".equals(usergroups.get("allowinvisible"))
						&& loginmode != null && loginmode.equals("invisible")
						|| loginmode != null && loginmode.equals("normal")) {
					member.setInvisible(Byte.valueOf(loginmode
							.equals("invisible") ? "1" : "0"));
					memberService.modifyMember(member);
				}
				styleid = styleid == null || styleid.equals("") ? (member
						.getStyleid() != 0 ? member.getStyleid().toString()
						: settings.get("styleid")) : styleid;
				if (cookietime == null) {
					cookietime = CookieUtil.getCookie(request, "cookietime",
							true, settings);
				}
				int time = Common.toDigit(cookietime);
				HttpSession mapsession = request.getSession();
				//CookieUtil.setCookie(request, response, "uid", String.valueOf(member.getUid()), time, true, settings);
				//CookieUtil.setCookie(request, response, "cookietime", String.valueOf(cookietime), 31536000, true, settings);
				//CookieUtil.setCookie(request, response, "auth", Md5Token.getInstance().getLongToken(member.getPassword() + "\t"+ member.getSecques() + "\t"+ member.getUid()), time, true,settings);
				member.setCuster_id(map!= null?Integer.parseInt(map.get("custer_id")+"") :0);
				session.setAttribute("jsprun_uid", member.getUid());
				session.setAttribute("jsprun_userss", jsprun_userss);
				session.setAttribute("jsprun_groupid", groupid);
				session.setAttribute("exgroup", 10);//member.getExtgroupids());
				session.setAttribute("jsprun_adminid", member.getAdminid());
				session.setAttribute("jsprun_pw", member.getPassword());
				session.setAttribute("formhash", Common.getRandStr(8, false));
				member.setNpwd(pwd);
				session.setAttribute("user", member);
				session.setAttribute("styleid", styleid);
				
				request.setAttribute("refresh", "true");
				Common.setDateformat(session, settings);
				request.setAttribute("sessionexists", false);
				SSOAppManager appMgr = new SSOAppManager(request, response);
				mapsession.setAttribute("userInfo", member);

				appMgr.setCookies(username);
				
				
				if (settings.get("passport_status").equals("shopex")
						&& !settings.get("passport_shopex").equals("0")) {
					if (groupid == 8) {
						request.setAttribute("msg", "login ok");
						return mapping.findForward("ts");
					} else {
						if (isfastsuccess) {
							Common.updatesession(request, settings);
							Common.requestforward(response, referer);
							return null;
						} else {
							request.setAttribute("msg", "login ok");
							return mapping.findForward("ts");
						}
					}
				} else {
					if (groupid == 8) {
						request.setAttribute("msg", "login ok");
						return mapping.findForward("ts");
					} else {
						if (isfastsuccess) {
							Common.updatesession(request, settings);
							Common.requestforward(response, referer);
							return null;
						} else {
							request.setAttribute("msg", "login ok");
							return mapping.findForward("ts");
						}
					}
				}
			} else if (secques == null || secques.equals("") || seccodemiss) {
				if (!member.getSecques().equals("")) {
					request.setAttribute("login_secques", getMessage(request,
							"login_secques"));
				}
				request.setAttribute("username", member.getUsername());
				request.setAttribute("cookietime", cookietime);
				request.setAttribute("loginmode", loginmode);
				request.setAttribute("styleid", styleid);
				request.setAttribute("loginauth", member.getPassword());
				request.setAttribute("seccodecheck", seccodecheck);
				request.setAttribute("msg", "对不起，请先登录系统");
				return mapping.findForward("toLogin_secques");
			}
		}
		Log.writelog("illegallog", timestamp, timestamp
				+ "\t"
				+ (member != null ? member.getUsername() : username)
				+ "\t"
				+ password
				+ "\t"
				+ (secques != null && !secques.equals("") ? "Ques #"
						+ questionid : "") + "\t" + onlineip);
		loginFailed(loginperm, onlineip, timestamp);
		request.setAttribute("msg", "login ok");
		return mapping.findForward("ts");
	}

	@SuppressWarnings("unchecked")
	public ActionForward Systemlogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> settings = ForumInit.settings;
		HttpSession session = request.getSession();
		Md5Token md5 = Md5Token.getInstance();
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		boolean isfastsuccess = Common.isshowsuccess(session, "login_succeed");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
        String pwd=password;
		if (jsprun_uid > 0) {
			if (isfastsuccess) {
				Common.requestforward(response, settings.get("indexname"));
				return null;
			} else {
				request.setAttribute("successInfo", getMessage(request,
						"login_succeed_3", (String) session
								.getAttribute("jsprun_userss")));
				request.setAttribute("requestPath", settings.get("indexname"));
				return mapping.findForward("showMessage");
			}
		}
		String referer = request.getParameter("referer");
		referer = settings.get("indexname");
		String onlineip = Common.get_onlineip(request);
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		int loginperm = loginCheck(onlineip, timestamp);
		if (loginperm <= 0) {
			request.setAttribute("resultInfo", getMessage(request,
					"login_strike"));
			return mapping.findForward("showMessage");
		}
		String styleid = request.getParameter("styleid");
		if (styleid != null && !styleid.equals("")) {
			session.setAttribute("styleid", styleid);
		} else {
			styleid = (String) session.getAttribute("styleid");
		}
		boolean seccodecheck = (Integer.valueOf(settings.get("seccodestatus")) & 2) > 0;
		if (seccodecheck && loginperm > 0) {
			request.setAttribute("seccodedata", dataParse.characterParse(
					settings.get("seccodedata"), false));
		}
		String seccodeverify = request.getParameter("seccodeverify");
		boolean seccodemiss = (seccodecheck && seccodeverify == null)
				|| (seccodecheck && seccodeverify != null && seccodeverify
						.equals("")) ? true : false;
		if (seccodecheck && !seccodemiss) {
			if (!seccodeverify.equals(session.getAttribute("rand"))) {
				request.setAttribute("errorInfo", getMessage(request,
						"submit_seccode_invalid"));
				return mapping.findForward("showMessage");
			}
		}
		String loginauth = request.getParameter("loginauth");
		int questionid = Common.toDigit(request.getParameter("questionid"));
		String answer = request.getParameter("answer");
		String cookietime = request.getParameter("cookietime");
		String loginmode = request.getParameter("loginmode");
		String secques = Common.quescrypt(questionid, answer);
		Map map = null;
		
		String nettype=request.getParameter("nettype");
		map = Common.SysteLogin(username,md5.getLongToken(password));// 调用登录方法查询e声网相应用户
		
		
		if (!"administrator".equals(username)) {
			if (map == null) {
				loginFailed(loginperm, onlineip, timestamp);
				request.setAttribute("successInfo", getMessage(request,
						"login_invalid"));
				request.setAttribute("requestPath",
						"systemlogin.jsp?referer=" + referer);
				return mapping.findForward("showMessage");
			} else {
				String newpass = md5.getLongToken(password);
				String sqlGetMember = "SELECT username From jrun_members WHERE username='"
						+ username + "'";
				List<Map<String, String>> lm = dataBaseService
						.executeQuery(sqlGetMember);
				if (lm == null || lm.size() == 0) {					
					//向博客添加一个新研究员用户begin
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");				
					String sql = "INSERT INTO wp_users (user_login,user_pass,user_nicename,user_email,user_url,user_registered,user_activation_key," +
							"user_status,display_name) VALUES ('" +
							Common.addslashes(username)+"','$P$B2baia3xnSuHteuq72IsIUBkN.8fkI0','"+Common.addslashes(username)+"'," +
									"'"+map.get("email")+"','',NOW(),'',0,'"+Common.addslashes(username)+"')";
					boolean flag = ConnectionDBBlog.addBlogUser(sql);
					System.out.println("isSuccess:"+flag);
					Integer bloguid = ConnectionDBBlog.getBlogUserByAccount(Common.addslashes(username));
					if (bloguid!=null) {
						String[] metaKeyValue = {"first_name","last_name","nickname","description","rich_editing","comment_shortcuts"
								,"admin_color","use_ssl","show_admin_bar_front","show_admin_bar_admin","aim","yim"
								,"jabber","wp_capabilities","wp_user_level"};
						String[] metaValue = {Common.addslashes(username),Common.addslashes(username),Common.addslashes(username),"","true","false",
								"fresh","0","true","false","","","","a:1:{s:6:\"author\";s:1:\"1\";}","2"};

						ConnectionDBBlog.addBlogUserMeta(bloguid,metaKeyValue,metaValue);
					}  //end
					
					
					String[] initcredits = settings.get("initcredits").split(
							",");
					int credits = Integer.valueOf(initcredits[0]);
					int initcredit1 = Integer.valueOf(initcredits[1]);
					int initcredit2 = Integer.valueOf(initcredits[2]);
					int initcredit3 = Integer.valueOf(initcredits[3]);
					int initcredit4 = Integer.valueOf(initcredits[4]);
					int initcredit5 = Integer.valueOf(initcredits[5]);
					int initcredit6 = Integer.valueOf(initcredits[6]);
					int initcredit7 = Integer.valueOf(initcredits[7]);
					int initcredit8 = Integer.valueOf(initcredits[8]);
					int regverify = Integer.valueOf(settings.get("regverify"));
					String bday = request.getParameter("bday");
					String signature = request.getParameter("signature");
					Map<String, String> groupinfo = dataBaseService
							.executeQuery(
									"SELECT groupid, allownickname, allowcstatus, allowavatar, allowcusbbcode, allowsigbbcode, allowsigimgcode, maxsigsize FROM jrun_usergroups WHERE "
											+ (regverify > 0 ? "groupid='8'"
													: "creditshigher <= "
															+ credits
															+ " AND "
															+ credits
															+ "< creditslower LIMIT 1"))
							.get(0);
					int maxsigsize = Integer.valueOf(groupinfo
							.get("maxsigsize"));
					byte sigstatus = Byte.valueOf("0");
					bday = Common.datecheck(bday) ? Common.dateformat(bday)
							: "0000-00-00";
					dataBaseService
							.runQuery(
									"INSERT INTO jrun_regips (ip, count, dateline) VALUES ('"
											+ onlineip + "', '1', '"
											+ timestamp + "')", true);
					int uid = dataBaseService
							.insert(
									"INSERT INTO jrun_members (username, password, secques, gender, adminid, groupid, regip, regdate, lastvisit, lastactivity, posts, credits, extcredits1, extcredits2, extcredits3, extcredits4, extcredits5, extcredits6, extcredits7, extcredits8, email, bday, sigstatus, tpp, ppp, styleid, dateformat, timeformat, pmsound, showemail,newsletter, invisible, timeoffset,salt)VALUES"
											+ " ('"
											+ Common.addslashes(username)
											+ "', '"
											+ newpass
											+ "', '"
											+ secques
											+ "', '0', '0', '"
											+ 17
											+ "', '"
											+ onlineip
											+ "', '"
											+ timestamp
											+ "', '"
											+ timestamp
											+ "', '"
											+ timestamp
											+ "', '0', "
											+ credits
											+ ","
											+ initcredit1
											+ ","
											+ initcredit2
											+ ","
											+ initcredit3
											+ ","
											+ initcredit4
											+ ","
											+ initcredit5
											+ ","
											+ initcredit6
											+ ","
											+ initcredit7
											+ ","
											+ initcredit8
											+ ", '"
											+ map.get("email")
											+ "', '"
											+ bday
											+ "', '"
											+ sigstatus
											+ "', '0', '0', '0', '0', '0', '0', '0', '1', '0', '9999','0')",
									true);
					
					
					if (uid > 0) {
						
						dataBaseService
								.runQuery(
										"INSERT INTO jrun_memberfields (uid, nickname, site,alipay,icq, qq, yahoo, msn, taobao, location, customstatus, medals, avatar, avatarwidth, avatarheight, bio, sightml,ignorepm,groupterms,authstr,spacename,buyercredit,sellercredit)"
												+ " VALUES ('"
												+ uid
												+ "', '', '', '', '', '', '', '', '', '', '', '', '', 0, 0, '', '', '', '','','',0,0)",
										true);
						
					}
				} else {
					String updateMember = "UPDATE jrun_members SET password='"
							+ newpass + "',email='" + map.get("email")+",groupid=17"
							+ "',groupid=17 WHERE username='" + username + "'";
					dataBaseService.runQuery(updateMember,true);
				}
			}
		} else {
			referer="admincp.jsp";
		}
		Members member = null;
		if ("uid".equals(request.getParameter("loginfield"))) {
			member = memberService.findMemberById(Common.toDigit(username));
		} else {
			member = memberService.findByName(username);
		}
		if (loginauth != null) {
			password = loginauth;
		} else {
			password = password != null ? md5.getLongToken(password) : "";
		}
		if (member != null) {
			if (member.getSecques().equals(secques) && !seccodemiss) {
				Short groupid = member.getGroupid();
				String jsprun_userss = member.getUsername();
				Map<String, String> usergroups = (Map<String, String>) request
						.getAttribute("usergroups");
				if ("1".equals(usergroups.get("allowinvisible"))
						&& loginmode != null && loginmode.equals("invisible")
						|| loginmode != null && loginmode.equals("normal")) {
					member.setInvisible(Byte.valueOf(loginmode
							.equals("invisible") ? "1" : "0"));
					memberService.modifyMember(member);
				}
				styleid = styleid == null || styleid.equals("") ? (member
						.getStyleid() != 0 ? member.getStyleid().toString()
						: settings.get("styleid")) : styleid;
				if (cookietime == null) {
					cookietime = CookieUtil.getCookie(request, "cookietime",
							true, settings);
				}
				int time = Common.toDigit(cookietime);
				HttpSession mapsession = request.getSession();
				//CookieUtil.setCookie(request, response, "uid", String.valueOf(member.getUid()), time, true, settings);
				//CookieUtil.setCookie(request, response, "cookietime", String.valueOf(cookietime), 31536000, true, settings);
				//CookieUtil.setCookie(request, response, "auth", Md5Token.getInstance().getLongToken(member.getPassword() + "\t"+ member.getSecques() + "\t"+ member.getUid()), time, true,settings);
				session.setAttribute("jsprun_uid", member.getUid());
				session.setAttribute("jsprun_userss", jsprun_userss);
				session.setAttribute("jsprun_groupid", groupid);
				session.setAttribute("exgroup", "17");
				session.setAttribute("jsprun_adminid", member.getAdminid());
				session.setAttribute("jsprun_pw", member.getPassword());
				session.setAttribute("formhash", Common.getRandStr(8, false));
				member.setNpwd(pwd);
				if("2".equals(nettype)){
					session.setAttribute("nettype", "2");
				}
				session.setAttribute("user", member);
				session.setAttribute("styleid", styleid);
				request.setAttribute("refresh", "true");
				Common.setDateformat(session, settings);
				request.setAttribute("sessionexists", false);
				SSOAppManager appMgr = new SSOAppManager(request, response);
				appMgr.setCookies(username);
				//mapsession.setAttribute("userInfo", member);   
				 //增加的,要放在Common.setDateformat(session, settings)之后??
				session.setAttribute("userInfo", member);

//				向cookie写入系统信息
				Cookie cookie=new Cookie("systemInfo","bbs");
			    cookie.setMaxAge(60*60*2);
			     response.addCookie(cookie);
				
				if (member.getGroupid()==17) {   //如果系研究员，添加标识，二期 session失效后跳转到正确登录页
					HttpSession isResearcher = request.getSession();
					isResearcher.setAttribute("isResearcher", true);
					isResearcher.setMaxInactiveInterval(60*60*2);
				}
								
				if (settings.get("passport_status").equals("shopex")
						&& !settings.get("passport_shopex").equals("0")) {
					if (groupid == 8) {
						request
								.setAttribute("successInfo", getMessage(
										request,
										"login_succeed_inactive_member",
										jsprun_userss));
						request.setAttribute("requestPath", "memcp.jsp");
						return mapping.findForward("showMessage");
					} else {
						if (isfastsuccess) {
							Common.updatesession(request, settings);
							Common.requestforward(response, referer);
							return null;
						} else {
							request.setAttribute("successInfo", getMessage(
									request, "login_succeed_3", jsprun_userss));
							request.setAttribute("requestPath", referer);
							return mapping.findForward("showMessage");
						}
					}
				} else {
					if (groupid == 8) {
						request
								.setAttribute("successInfo", getMessage(
										request,
										"login_succeed_inactive_member",
										jsprun_userss));
						request.setAttribute("requestPath", "memcp.jsp");
						return mapping.findForward("showMessage");
					} else {
						if (isfastsuccess) {
							Common.updatesession(request, settings);
							Common.requestforward(response, referer);
							return null;
						} else {
							request.setAttribute("successInfo", getMessage(
									request, "login_succeed_3", jsprun_userss));
							request.setAttribute("requestPath", referer);
							return mapping.findForward("showMessage");
						}
					}
				}
			} else if (secques == null || secques.equals("") || seccodemiss) {
				if (!member.getSecques().equals("")) {
					request.setAttribute("login_secques", getMessage(request,
							"login_secques"));
				}
				request.setAttribute("username", member.getUsername());
				request.setAttribute("cookietime", cookietime);
				request.setAttribute("loginmode", loginmode);
				request.setAttribute("styleid", styleid);
				request.setAttribute("loginauth", member.getPassword());
				request.setAttribute("seccodecheck", seccodecheck);
				return mapping.findForward("toLogin_secques");
			}
		}
		Log.writelog("illegallog", timestamp, timestamp
				+ "\t"
				+ (member != null ? member.getUsername() : username)
				+ "\t"
				+ password
				+ "\t"
				+ (secques != null && !secques.equals("") ? "Ques #"
						+ questionid : "") + "\t" + onlineip);
		loginFailed(loginperm, onlineip, timestamp);
		request.setAttribute("successInfo",
				getMessage(request, "login_invalid"));
		request.setAttribute("requestPath",
				"systemlogin.jsp?referer=" + referer);
		return mapping.findForward("showMessage");
	
	}

	@SuppressWarnings("unchecked")
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Map<String, String> settings = ForumInit.settings;

//		if (request.getParameter("formhash").equals(
//				session.getAttribute("formhash"))) {
			CookieUtil.clearCookies(request, response, settings);
			session.setAttribute("styleid", settings.get("styleid"));
//		}
		session.removeAttribute("members");
		session.removeAttribute("taskid");
		String referer = request.getParameter("referer");
//		if (referer == null) {
//			referer = request.getHeader("Referer");
//		}
//		if (referer == null || referer.length() == 0 || referer.equals("null")) {
			referer = settings.get("indexname");
//		}
		session.removeAttribute("userInfo");
		if (session.getAttribute("isResearcher")!=null) {
			session.removeAttribute("isResearcher"); //移除研究员标识
		}
		Cookie cookie = new Cookie("systemInfo", null); 
		cookie.setMaxAge(0);      //删除系统信息cookie
		response.addCookie(cookie); 
		
		SSOAppManager appMgr = new SSOAppManager(request, response);
		appMgr.logout();
		if (Common.isshowsuccess(session, "logout_succeed")) {
			Common.requestforward(response, referer);
			return null;
		}		
		else {
			request.setAttribute("successInfo", getMessage(request,
					"logout_succeed"));
			request.setAttribute("requestPath", referer);
			return mapping.findForward("showMessage");
		}
	}
	public ActionForward logoutbyvirtual(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Map<String, String> settings = ForumInit.settings;

		if (request.getParameter("formhash").equals(
				session.getAttribute("formhash"))) {
			CookieUtil.clearCookies(request, response, settings);
			session.setAttribute("styleid", settings.get("styleid"));
		}
		session.removeAttribute("members");		
		session.removeAttribute("userInfo");
		SSOAppManager appMgr = new SSOAppManager(request, response);
		appMgr.logout();
		Properties properties = new Properties();
		InputStream fis;
		try {
			fis = new FileInputStream(JspRunConfig.realPath
					+ "config.properties");
			properties.load(fis);
			fis.close();		
			String url=properties.getProperty("virtualurl");;
			PrintWriter out=response.getWriter();
			out.println("<script>location.href='"+url+"/logging.jsp?option=success';</script>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try{
//			request.getRequestDispatcher("../logging.jsp?option=success").forward(request,response);
//		}
//		catch(Exception ex){
//			
//		}
		return null;
	}
	private void loginFailed(int permission, String onlineip, int timestamp) {
		switch (permission) {
		case 1:
			dataBaseService.runQuery(
					"REPLACE INTO jrun_failedlogins (ip, count, lastupdate) VALUES ('"
							+ onlineip + "', '1', '" + timestamp + "')", true);
			break;
		case 2:
			dataBaseService
					.runQuery(
							"UPDATE jrun_failedlogins SET count=count+1, lastupdate='"
									+ timestamp + "' WHERE ip='" + onlineip
									+ "'", true);
			break;
		case 3:
			dataBaseService
					.runQuery(
							"UPDATE jrun_failedlogins SET count='1', lastupdate='"
									+ timestamp + "' WHERE ip='" + onlineip
									+ "'", true);
			dataBaseService.runQuery(
					"DELETE FROM jrun_failedlogins WHERE lastupdate<"
							+ (timestamp - 901), true);
			break;
		}
	}

	private int loginCheck(String onlineip, int timestamp) {
		List<Map<String, String>> failedlogins = dataBaseService
				.executeQuery("SELECT count, lastupdate FROM jrun_failedlogins WHERE ip='"
						+ onlineip + "'");
		if (failedlogins != null && failedlogins.size() > 0) {
			Map<String, String> failedlogin = failedlogins.get(0);
			if (timestamp - Integer.parseInt(failedlogin.get("lastupdate")) > 900) {
				return 3;
			} else if (Integer.parseInt(failedlogin.get("count")) < 5) {
				return 2;
			} else {
				return 0;
			}
		} else {
			return 1;
		}
	}
}