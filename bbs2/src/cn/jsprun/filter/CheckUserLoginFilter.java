package cn.jsprun.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.jsprun.domain.Members;
import cn.jsprun.service.DataBaseService;
import cn.jsprun.service.MemberService;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.Md5Token;
import cn.jsprun.utils.StringHelper;

import com.wingo.sso.QueryCrypt;
import com.wingo.sso.SSOAppManager;
import sun.misc.BASE64Encoder;
public class CheckUserLoginFilter implements Filter {

	protected MemberService memberService = (MemberService) BeanFactory
			.getBean("memberService");
	protected DataBaseService dataBaseService = (DataBaseService) BeanFactory
			.getBean("dataBaseService");
	private FilterConfig filterConfig = null;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		

	}

	/**
	 * noFilterURI 参数为不要进行session检查的页面，比如说：index.jsp,等等页面
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		Integer jsprun_uid =  (Integer)request.getSession().getAttribute("jsprun_uid");
	//	System.out.println("uinfo: "+uinfo);	
	//	System.out.println("jsprun_uid: "+jsprun_uid);
		/*System.out.println("jsprun_userss: "+request.getSession().getAttribute("jsprun_userss"));*/
		Object isResearcher =   request.getSession().getAttribute("isResearcher");   //研究员标识
//		检查是否请求二期的内容
		//System.out.println(request.getRequestURI()+"?"+request.getQueryString());
		boolean checkVicty = checkVcityUserPower(request,response);		
		String tempPath = request.getSession().getServletContext().getContextPath();
		String noFilterURI = this.filterConfig.getInitParameter("noFilterURI");
		String[] noFilterURIArr = null;
		if (noFilterURI != null)
			noFilterURIArr = StringHelper.split(noFilterURI, ",");
		boolean noFilter = false;
		if (noFilterURIArr != null && noFilterURIArr.length > 0) {
			for (int i = 0; i < noFilterURIArr.length; i++) {
				String uri = request.getContextPath()+ noFilterURIArr[i];
				if (uri.equals(request.getRequestURI())) {
					noFilter = true;
					break;
				}
			}
		}
			
		if (noFilter) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			if (uinfo == null) // 如果session中没有相关用户
			{
				//得到系统信息，如果为当前系统登录，让session过期
				Cookie[] cookies = request.getCookies();
				String systemInfo = "";
				if (cookies!=null&&cookies.length>0) {
					for (int i = 0; i < cookies.length; i++) {
						Cookie c = cookies[i];					
						if (c.getName().equals("systemInfo")) {
							 systemInfo = c.getValue();
							 System.out.println("systemInfo   "+systemInfo);
							break;
						}
					}
				}
			
				
				SSOAppManager appMgr = new SSOAppManager(request, response);
				if (appMgr.isLogin()&&!systemInfo.equals("bbs")) {// 是否存在cookies值（是否在其他应用登录过）
					System.out.println("loginTrue------------------: "+systemInfo);
					Map<String, String> settings = ForumInit.queryForumSetInfo();
					String ssoid = appMgr.findSSOIdFromCookie();
					String username = QueryCrypt.getInstance().performDecrypt(
							ssoid);
					if (!"".equals(username)) {// 解密cookies值后，获取登录用户名
						  	
						// 查询数据库，验证是否存在该用户名
						HttpSession session = request.getSession();
						Map map = null;
						if (!"admin".equals(username)) {
							map = Common.loginE(username, "");// 调用登录方法查询e声网相应用户
							if (map != null) {
								int questionid = Common.toDigit(request
										.getParameter("questionid"));
								String answer = request.getParameter("answer");
								String secques = Common.quescrypt(questionid,
										answer);
								String sqlGetMember = "SELECT username From jrun_members WHERE username='"
										+ username + "'";
								List<Map<String, String>> lm = dataBaseService
										.executeQuery(sqlGetMember);
								String newpass = Md5Token.getInstance()
										.getLongToken(map.get("password") + "");
								if (lm == null || lm.size() == 0) {
									String[] initcredits = settings.get(
											"initcredits").split(",");
									int credits = Integer
											.valueOf(initcredits[0]);
									int initcredit1 = Integer
											.valueOf(initcredits[1]);
									int initcredit2 = Integer
											.valueOf(initcredits[2]);
									int initcredit3 = Integer
											.valueOf(initcredits[3]);
									int initcredit4 = Integer
											.valueOf(initcredits[4]);
									int initcredit5 = Integer
											.valueOf(initcredits[5]);
									int initcredit6 = Integer
											.valueOf(initcredits[6]);
									int initcredit7 = Integer
											.valueOf(initcredits[7]);
									int initcredit8 = Integer
											.valueOf(initcredits[8]);
									int regverify = Integer.valueOf(settings
											.get("regverify"));
									String bday = request.getParameter("bday");
									String signature = request
											.getParameter("signature");
									String onlineip = Common
											.get_onlineip(request);
									int timestamp = (Integer) (request
											.getAttribute("timestamp"));
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
									bday = Common.datecheck(bday) ? Common
											.dateformat(bday) : "0000-00-00";
									dataBaseService.runQuery(
											"INSERT INTO jrun_regips (ip, count, dateline) VALUES ('"
													+ onlineip + "', '1', '"
													+ timestamp + "')", true);
									int uid = dataBaseService
											.insert(
													"INSERT INTO jrun_members (username, password, secques, gender, adminid, groupid, regip, regdate, lastvisit, lastactivity, posts, credits, extcredits1, extcredits2, extcredits3, extcredits4, extcredits5, extcredits6, extcredits7, extcredits8, email, bday, sigstatus, tpp, ppp, styleid, dateformat, timeformat, pmsound, showemail,newsletter, invisible, timeoffset,salt)VALUES"
															+ " ('"
															+ Common
																	.addslashes(username)
															+ "', '"
															+ newpass
															+ "', '"
															+ secques
															+ "', '0', '0', '"
															+ groupinfo
																	.get("groupid")
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
											+ newpass
											+ "',email='"
											+ map.get("email")
											+ "',regip='"
											+ map.get("register_ip")
											+ "' WHERE username='"
											+ username
											+ "'";
									dataBaseService.runQuery(updateMember,true);
								}
							}

						}
						 Members member=memberService.findByName(username);
							if (member != null && member.getPassword().equals(map==null?member.getPassword():map.get("password")+"")) {
									Short groupid = member.getGroupid();
									String jsprun_userss = member.getUsername();
									Map<String,String> usergroups = (Map<String,String>)request.getAttribute("usergroups");
									String styleid = request.getParameter("styleid");
									if(styleid!=null&&!styleid.equals("")){
										session.setAttribute("styleid", styleid);
									}else{
										styleid=(String)session.getAttribute("styleid");
									}
									String cookietime=cookietime=CookieUtil.getCookie(request, "cookietime",true,settings);
									styleid=styleid==null||styleid.equals("")?(member.getStyleid()!=0?member.getStyleid().toString():settings.get("styleid")):styleid;
									if(cookietime==null){
										cookietime=CookieUtil.getCookie(request, "cookietime",true,settings);
									}
									int time=Common.toDigit(cookietime);
									CookieUtil.setCookie(request, response, "uid", String.valueOf(member.getUid()), time, true,settings);
									CookieUtil.setCookie(request, response,
								"cookietime", String.valueOf(cookietime), 31536000, true,settings);
									CookieUtil.setCookie(request, response, "auth", Md5Token.getInstance().getLongToken(member.getPassword()+"\t"+member.getSecques()+"\t"+member.getUid()), time, true,settings);
									member.setCuster_id(map!= null?Integer.parseInt(map.get("custer_id")+"") :0);
									System.out.println("member.getUserName:---------- "+jsprun_userss);
									System.out.println("member.getUid:---------- "+member.getUid());
									session.setAttribute("jsprun_uid", member.getUid());
									session.setAttribute("jsprun_userss", jsprun_userss);
									session.setAttribute("jsprun_groupid", groupid);
									session.setAttribute("exgroup",member.getExtgroupids());
									session.setAttribute("jsprun_adminid", member.getAdminid());
									session.setAttribute("jsprun_pw", member.getPassword());
									session.setAttribute("formhash", Common.getRandStr(8,false));
									session.setAttribute("user", member);
									session.setAttribute("styleid",styleid);
									request.setAttribute("refresh", "true");
									Common.setDateformat(session, settings);
									session.setAttribute("userInfo", member);
									request.setAttribute("sessionexists", false);
							}
							filterChain.doFilter(servletRequest,
									servletResponse);
						
					}else {
						if (checkVicty) {   //coodie有值，但为空或""
							if (isResearcher!=null) {
								response.sendRedirect(tempPath+"/syslogin.jsp");
								return;
							}
							response.sendRedirect(tempPath+"/vcity/vcityLogin.jsp");
							return;
						}
						filterChain.doFilter(servletRequest, servletResponse);
					}
				} else {
					if (checkVicty) {    //cookie没有值或者系统信息为bbs
						appMgr.logout();
						if (isResearcher!=null) {
							response.sendRedirect(tempPath+"/syslogin.jsp");
							return ;
						}
						response.sendRedirect(tempPath+"/vcity/vcityLogin.jsp");
						return ;
					}
					filterChain.doFilter(servletRequest, servletResponse);
				}
			} else if(uinfo.getGroupid() ==10 || uinfo.getGroupid() == 11){   //验证权限，或为普通会员跳到提示页面
				//boolean isPass = checkVcityUserPower(request,response);
				if (!checkVicty) {
					filterChain.doFilter(servletRequest, servletResponse);
				} else {
					if (request.getRequestURI().equals(tempPath+"/vcity/")) {
						response.sendRedirect("../vcityShowmessage.jsp");
						return ;
					} else {
						response.sendRedirect("vcityShowmessage.jsp");
						return ;
					}			
				}
			}
			else {
				filterChain.doFilter(servletRequest, servletResponse);
			}
		}

	}
	
	/**
	 * 不改动一期的过滤方式，在此基础上增加
	 * 二期的session与权限过滤
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public boolean checkVcityUserPower(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// 过滤权限
		String filterURI = this.filterConfig.getInitParameter("filterURI");
		String[] filterURIArr = null;
		if (filterURI != null)
			filterURIArr = StringHelper.split(filterURI, ",");

		boolean filter = false;
		if (filterURIArr != null && filterURIArr.length > 0) {
			for (int i = 0; i < filterURIArr.length; i++) {

				String uri = request.getContextPath() + filterURIArr[i];
				if (uri.equals(request.getRequestURI())) {
					filter = true;					
					break;
				}
			}
		}
		return filter;

	}
	

	public void destroy() {
		this.filterConfig = null;

	}
}
