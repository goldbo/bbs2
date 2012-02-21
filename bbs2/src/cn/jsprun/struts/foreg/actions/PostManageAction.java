package cn.jsprun.struts.foreg.actions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;

import cn.jsprun.domain.Attachments;
import cn.jsprun.domain.Comment;
import cn.jsprun.domain.Forumfields;
import cn.jsprun.domain.Forums;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.PermList;
import cn.jsprun.domain.Polloptions;
import cn.jsprun.domain.Polls;
import cn.jsprun.domain.Posts;
import cn.jsprun.domain.Special;
import cn.jsprun.domain.ThemeTemplate;
import cn.jsprun.domain.Threads;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Cache;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.CookieUtil;
import cn.jsprun.utils.ExportExcel;
import cn.jsprun.utils.FileUploadUtil;
import cn.jsprun.utils.FormDataCheck;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.ImageUtil;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Jspruncode;
import cn.jsprun.utils.Log;
import cn.jsprun.utils.Md5Token;
import java.text.SimpleDateFormat;
public class PostManageAction extends BaseAction {
	public final static String FILEPATHTIME = JspRunConfig.realPath
			+ "forumdata/temp";
	public final static int memeoryBlock = 1572864;
	public static Map<String, String> keyMap = null;
	static {
		keyMap = new HashMap<String, String>();
		keyMap.put("author", "会员名");
		keyMap.put("authorid", "会员编号");
		keyMap.put("subject", "帖子标题");
		keyMap.put("dateline", "时间");
		keyMap.put("message", "帖子内容");
		keyMap.put("useip", "用户IP");
		keyMap.put("rate", "评分总计");
		keyMap.put("ratetimes", "评分次数");
		keyMap.put("status", "帖子状态");
	}

	@SuppressWarnings("unchecked")
	public ActionForward toNewthread(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		short fid = (short) Common.toDigit(request.getParameter("fid"));
		Forums forum = forumService.findById(fid);
		if (forum == null) {
			request.setAttribute("errorInfo", getMessage(request,
					"forum_nonexistence"));
			return mapping.findForward("showMessage");
		}
		request.setAttribute("styleid", forum.getStyleid() > 0 ? forum
				.getStyleid() : null);
		Forumfields forumfield = forumfieldService.findById(fid);
		if ((forum.getSimple() & 1) > 0
				|| forumfield.getRedirect().length() > 0) {
			request.setAttribute("resultInfo", getMessage(request,
					"forum_disablepost"));
			return mapping.findForward("showMessage");
		}
		HttpSession session = request.getSession();
		short groupid = (Short) session.getAttribute("jsprun_groupid");// �ӻỰ�еõ��û����id
		Members member = (Members) session.getAttribute("user");// �ӻỰ�еõ��û�
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		Map<String, String> usergroups = (Map<String, String>) request
				.getAttribute("usergroups");
		int allowpost = Integer.valueOf(usergroups.get("allowpost"));
		String postperm = forumfield.getPostperm();
		List<Map<String, String>> accesslist = dataBaseService
				.executeQuery("select allowpost,allowpostattach from jrun_access where uid='"
						+ jsprun_uid + "' and fid='" + fid + "'");
		Map<String, String> access = accesslist.size() > 0 ? accesslist.get(0)
				: null;
		if (member == null
				&& !((postperm.equals("") && allowpost > 0) || (!postperm
						.equals("") && Common.forumperm(postperm, groupid,
						member != null ? member.getExtgroupids() : "")))) {
			request.setAttribute("show_message", getMessage(request,
					"group_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		} else if (access == null || Common.empty(access.get("allowpost"))) {
			if (postperm.equals("") && allowpost == 0) {
				request.setAttribute("show_message", getMessage(request,
						"group_nopermission", usergroups.get("grouptitle")));
				return mapping.findForward("nopermission");
			} else if (!postperm.equals("")
					&& !Common.forumperm(postperm, groupid,
							member != null ? member.getExtgroupids() : "")) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_forum_newthread_nopermission"));
				return mapping.findForward("showMessage");
			}
		}
		if ("yes".equals(request.getParameter("isblog"))
				&& (Integer.valueOf(usergroups.get("allowuseblog")) == 0 || forum
						.getAllowshare() == 0)) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_newthread_blog_invalid"));
			return mapping.findForward("showMessage");
		}
		int timestamp = (Integer) (request.getAttribute("timestamp"));

		int special = Common.range(Common.intval(request
				.getParameter("special")), 6, 0);
		Map<String, String> settings = ForumInit.settings;
		this.common(request, response, settings, usergroups, forumfield, forum,
				special, access, null);

		byte only = forum.getAllowspecialonly();
		boolean allowpostpoll = (Boolean) request.getAttribute("allowpostpoll");
		boolean allowposttrade = (Boolean) request
				.getAttribute("allowposttrade");
		/*
		 * boolean allowpostreward =
		 * (Boolean)request.getAttribute("allowpostreward"); boolean
		 * allowpostactivity =
		 * (Boolean)request.getAttribute("allowpostactivity"); boolean
		 * allowpostdebate = (Boolean)request.getAttribute("allowpostdebate");
		 * boolean allowpostvideo =
		 * (Boolean)request.getAttribute("allowpostvideo");
		 */
		if (special > 0 && special != 2) {
			if (!((special == 1 && allowpostpoll))) {// ||(special==3&&allowpostreward)||(special==4&&allowpostactivity)||(special==5&&allowpostdebate)||(special==6&&allowpostvideo))){
				request.setAttribute("show_message", getMessage(request,
						"group_nopermission", usergroups.get("grouptitle")));
				return mapping.findForward("nopermission");
			}
		}
		if (only == 1 && special == 0) {
			if (allowpostpoll) {
				special = 1;
			} else if (allowposttrade) {
				special = 2;
			}
			/*
			 * else if(allowpostreward){ special = 3; }else
			 * if(allowpostactivity){ special = 4; }else if(allowpostdebate){
			 * special = 5; }else if(allowpostvideo){ special = 6; }
			 */
			else {
				request.setAttribute("errorInfo", getMessage(request,
						"undefined_action_return"));
				return mapping.findForward("showMessage");
			}
			request.setAttribute("special", special);
		}
		String timeoffset = (String) session.getAttribute("timeoffset");
		usergroups = null;
		String target = null;
		String exgroup=session.getAttribute("exgroup")+"";
		if (special == 2) {
			if (!"17".equals(exgroup)) {
				request.setAttribute("errorInfo", "对不起,你不是研究员,无权发布专题帖");
				return mapping.findForward("showMessage");
			}

			// SimpleDateFormat format =
			// Common.getSimpleDateFormat("yyyy-MM-dd", timeoffset);
			// Map<String, String> trade = new HashMap<String, String>();
			// Calendar calendar = Common.getCalendar(timeoffset);
			// calendar.add(Calendar.MONTH, 1);
			// String expiration_month =
			// format.format(calendar.getTimeInMillis());
			// calendar.add(Calendar.MONTH, -1);
			// calendar.add(Calendar.DATE, 7);
			// request.setAttribute("expiration_7days",
			// format.format(calendar.getTimeInMillis()));
			// calendar.add(Calendar.DATE, 7);
			// request.setAttribute("expiration_14days",
			// format.format(calendar.getTimeInMillis()));
			// request.setAttribute("expiration_month", expiration_month);
			// calendar.add(Calendar.DATE, -14);
			// calendar.add(Calendar.MONTH, 3);
			// request.setAttribute("expiration_3months",
			// format.format(calendar.getTimeInMillis()));
			// calendar.add(Calendar.MONTH, 3);
			// request.setAttribute("expiration_halfyear",
			// format.format(calendar.getTimeInMillis()));
			// calendar.add(Calendar.MONTH, -6);
			// calendar.add(Calendar.YEAR, 1);
			// request.setAttribute("expiration_year",
			// format.format(calendar.getTimeInMillis()));
			// trade.put("amount", "1");
			// trade.put("transport", "2");
			// trade.put("expiration", expiration_month);
			// request.setAttribute("trade", trade);
			// trade=null;
			// String tradetypes=settings.get("tradetypes");
			// if(tradetypes!=null&&tradetypes.length()>0) {
			// Map<String,String>
			// forumtradetypes=dataParse.characterParse(forumfield.getTradetypes(),
			// true);
			// Map<String,String>
			// tradetypesmap=dataParse.characterParse(tradetypes, true);
			// StringBuffer tradetypeselect = new StringBuffer("<select
			// name=\"tradetypeid\"
			// onchange=\"ajaxget(\'post.jsp?action=threadtypes&tradetype=yes&typeid=\'+this.options[this.selectedIndex].value+'&rand='+Math.random(),
			// \'threadtypes\', \'threadtypeswait\')\"><option
			// value=\"0\">&nbsp;</option>");
			// Set<String> typeids=tradetypesmap.keySet();
			// for (String typeid : typeids) {
			// if(forumtradetypes.size()==0 ||
			// forumtradetypes.containsValue(typeid)) {
			// tradetypeselect.append("<option
			// value=\""+typeid+"\">"+Common.strip_tags(tradetypesmap.get(typeid))+"</option>");
			// }
			// }
			// tradetypeselect.append("</select><span
			// id=\"threadtypeswait\"></span>");
			// request.setAttribute("tradetypeselect", tradetypeselect);
			// }
			target = "toPost_newthread_special";
		} else if (special == 4) {
			String activitytype = settings.get("activitytype");
			request.setAttribute("activitytypelist", activitytype != null
					&& !activitytype.trim().equals("") ? activitytype.trim()
					.split("\r\n") : null);
			request.setAttribute("sampletimestamp", Common.gmdate(
					"yyyy-MM-dd HH:mm", timestamp + 86400, timeoffset));
			target = "toPost_newthread_activity";
		} else {
			target = "toPost_newthread";
		}
		request.setAttribute("special", special);
		int page = Common.toDigit(request.getParameter("page"));
		String navigation = request.getParameter("navigation");
		String navtitle = request.getParameter("navtitle");
		String forumName = forum.getName();
		navigation = "&raquo; <a href=\"forumdisplay.jsp?fid=" + fid
				+ (page > 0 ? "&page=" + page : "") + "\">" + forumName
				+ "</a> " + (navigation != null ? navigation : "");
		navtitle = navtitle != null ? navtitle : forumName + " - ";
		if (forum.getType().equals("sub")) {
			Map<String, String> fup = dataBaseService.executeQuery(
					"SELECT name FROM jrun_forums WHERE fid=" + forum.getFup()
							+ " limit 1").get(0);
			String fupforumName = fup.get("name");
			navigation = "&raquo; <a href=\"forumdisplay.jsp?fid="
					+ forum.getFup() + "\">" + fupforumName + "</a> "
					+ navigation;
			navtitle = navtitle + fupforumName + " - ";
		}
		request.setAttribute("navigation", navigation);
		request.setAttribute("navtitle", Common.strip_tags(navtitle));
		Map threadtypes = dataParse.characterParse(forumfield.getThreadtypes(),
				false);
		request.setAttribute("threadtypes", threadtypes);
		int curtypeid = Common.toDigit(request.getParameter("typeid"));
		int modelid = Common.toDigit(request.getParameter("modelid"));
		String typeselect = Common.typeselect(fid, curtypeid, special, modelid,
				null, threadtypes);
		request.setAttribute("typeselect", typeselect);
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		String readperm = request.getParameter("readperm");
		String price = request.getParameter("price");
		request.setAttribute("subject", subject != null ? subject : "");
		request.setAttribute("message", message != null ? message : "");
		request.setAttribute("readperm", readperm != null ? readperm : 0);
		request.setAttribute("price", price != null ? price : 0);
		if (request.getParameter("previewpost") != null) {
			request.setAttribute("message_preview", message);
		}
		List<String> faceimg = new ArrayList<String>();
		for (int i = 0; i < 55; i++) {
			faceimg.add(i + ".gif");
		}
		request.setAttribute("faceimg", faceimg);
		String tempid = request.getParameter("templateid");
		if (tempid != null && !tempid.equals("")) {
			ThemeTemplate temp = themeTemplateService
					.findThemeTemplateById(Integer.parseInt(tempid));
			request.setAttribute("subject", temp != null ? temp.getSubject()
					: "");
			request.setAttribute("message", temp != null ? temp.getMessage()
					: "");
		}
		return mapping.findForward(target);
	}

	@SuppressWarnings("unchecked")
	public ActionForward newthread(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> usergroups = (Map<String, String>) request
				.getAttribute("usergroups");
		short fid = (short) Common.toDigit(request.getParameter("fid"));
		Forums forum = forumService.findById(fid);
		if (forum == null) {
			request.setAttribute("errorInfo", getMessage(request,
					"forum_nonexistence"));
			return mapping.findForward("showMessage");
		}
		request.setAttribute("styleid", forum.getStyleid() > 0 ? forum
				.getStyleid() : null);
		Forumfields forumfield = forumfieldService.findById(fid);
		if ((forum.getSimple() & 1) > 0
				|| forumfield.getRedirect().length() > 0) {
			request.setAttribute("resultInfo", getMessage(request,
					"forum_disablepost"));
			return mapping.findForward("showMessage");
		}
		File tempfile = isMakeDir();
		ModuleConfig ac = (ModuleConfig) request
				.getAttribute(Globals.MODULE_KEY);
		FileUploadUtil fileupload = new FileUploadUtil(tempfile, memeoryBlock,
				ac);
		try {
			fileupload.parse(request, JspRunConfig.CHARSET);
		} catch (IllegalStateException e) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_attachment_toobig"));
			return mapping.findForward("showMessage");
		}
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		short jsprun_groupid = (Short) session.getAttribute("jsprun_groupid");
		Members member = (Members) session.getAttribute("user");
		Map<String, String> settings = ForumInit.settings;
		int allowpost = Integer.valueOf(usergroups.get("allowpost"));
		String postperm = forumfield.getPostperm();
		List<Map<String, String>> accesslist = dataBaseService
				.executeQuery("select allowpost,allowpostattach from jrun_access where uid='"
						+ uid + "' and fid='" + fid + "'");
		Map<String, String> access = accesslist.size() > 0 ? accesslist.get(0)
				: null;
		if (member == null
				&& !((postperm.equals("") && allowpost > 0) || (!postperm
						.equals("") && Common.forumperm(postperm,
						jsprun_groupid, member != null ? member
								.getExtgroupids() : "")))) {
			request.setAttribute("show_message", getMessage(request,
					"group_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		} else if (access == null || Common.empty(access.get("allowpost"))) {
			if (postperm.equals("") && allowpost == 0) {
				request.setAttribute("show_message", getMessage(request,
						"group_nopermission", usergroups.get("grouptitle")));
				return mapping.findForward("nopermission");
			} else if (!postperm.equals("")
					&& !Common.forumperm(postperm, jsprun_groupid,
							member != null ? member.getExtgroupids() : "")) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_forum_newthread_nopermission"));
				return mapping.findForward("showMessage");
			}
		}
		String isblog = fileupload.getParameter("isblog");
		int allowuseblog = Integer.valueOf(usergroups.get("allowuseblog"));
		if ("yes".equals(isblog)
				&& (allowuseblog == 0 || forum.getAllowshare() == 0)) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_newthread_blog_invalid"));
			return mapping.findForward("showMessage");
		}

		int special = Common.range(Common.intval(fileupload
				.getParameter("special")), 6, 0);
		this.common(request, response, settings, usergroups, forumfield, forum,
				special, access, fileupload);
		byte only = forum.getAllowspecialonly();
		boolean allowpostpoll = (Boolean) request.getAttribute("allowpostpoll");
		boolean allowposttrade = (Boolean) request
				.getAttribute("allowposttrade");
		boolean allowpostreward = (Boolean) request
				.getAttribute("allowpostreward");
		boolean allowpostactivity = (Boolean) request
				.getAttribute("allowpostactivity");
		boolean allowpostdebate = (Boolean) request
				.getAttribute("allowpostdebate");
		boolean allowpostvideo = (Boolean) request
				.getAttribute("allowpostvideo");
		if (special > 0) {
			if (!((special == 1 && allowpostpoll)
					|| (special == 2 && allowposttrade)
					|| (special == 3 && allowpostreward)
					|| (special == 4 && allowpostactivity)
					|| (special == 5 && allowpostdebate) || (special == 6 && allowpostvideo))) {
				request.setAttribute("show_message", getMessage(request,
						"group_nopermission", usergroups.get("grouptitle")));
				return mapping.findForward("nopermission");
			}
		}
		if (only == 1 && special == 0) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_allowspecialonly"));
			return mapping.findForward("showMessage");
		}

		String subject = fileupload.getParameter("subject");// 得到帖子主题
		String message = fileupload.getParameter("message");//
		request.setAttribute("message", message);
		String previewpost = fileupload.getParameter("previewpost");
		String remark = fileupload.getParameter("remark");// 得到帖子的评论类型
		String reply = fileupload.getParameter("reply");// 帖子的回复类型
		String[] remarkoptions = fileupload.getParameterValues("remarkoptions");// 帖子的回复选项
		request.setAttribute("remarkoptions", remarkoptions);
		String timeoffset = (String) session.getAttribute("timeoffset");
		String specialstarttime = fileupload.getParameter("starttime");// 专题帖开始时间
		String specialendtime = fileupload.getParameter("endtime");// 专题贴结束时间

		String showtype = fileupload.getParameter("showtype");// 评论选项的显示方式（1横条
		// 2 柱状）

		int starttimeInt = Common.dataToInteger(specialstarttime, timeoffset);
		int endtimeInt = Common.dataToInteger(specialendtime, timeoffset);

		String Allow = fileupload.getParameter("Allow");// 专题帖权限（公开还是授权）
		String isSendEmail = "";
		if ("4".equals(Allow)) {
			isSendEmail = fileupload.getParameter("isSendEmail");
		}
		String permlist = fileupload.getParameter("permlist");// 专题帖权限列表
		String[] plist = new String[] {};
		if (permlist != "" && permlist != null) {
			plist = permlist.split(",");
		}
		request.setAttribute("allow", Allow);
		if (previewpost != null) {
			request.setAttribute("typeid", fileupload.getParameter("typeid"));
			return this.toNewthread(mapping, form, request, response);
		}
		subject = subject == null ? null : subject.trim();
		message = message == null ? null : message.trim();
		if (subject == null || message == null || subject.equals("")
				|| message.equals("")) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_sm_isnull"));
			return mapping.findForward("showMessage");
		}
		subject = Common.htmlspecialchars(subject);
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		String post_invalid = Common.checkpost(subject, message, settings,
				usergroups, resources, locale);
		if (post_invalid != null) {
			request.setAttribute("errorInfo", post_invalid);
			return mapping.findForward("showMessage");
		}
		if (remarkoptions == null || "".equals(remarkoptions)) {
			request.setAttribute("errorInfo", "没有输入评论的选项");
			return mapping.findForward("showMessage");
		}
		if (remarkoptions.length < 1) {
			request.setAttribute("errorInfo", "评论的选项应至少有两项");
			return mapping.findForward("showMessage");
		}
		if (remarkoptions.length > 6) {
			request.setAttribute("errorInfo", "评论的选项最多可以有六项");
			return mapping.findForward("showMessage");
		}
		Map seccodedata = dataParse.characterParse(settings.get("seccodedata"),
				false);
		int minposts = Common.toDigit(String.valueOf(seccodedata
				.get("minposts")));
		seccodedata = null;
		int seccodestatus = Common.range(Common.intval(settings
				.get("seccodestatus")), 255, 0);
		boolean seccodecheck = (seccodestatus & 4) > 0
				&& (minposts <= 0 || member.getPosts() < minposts);
		Map secqaa = dataParse.characterParse(settings.get("secqaa"), false);
		minposts = Common.toDigit(String.valueOf(secqaa.get("minposts")));
		int secqaastatus = (Integer) secqaa.get("status");
		secqaa = null;
		boolean secqaacheck = (secqaastatus & 2) > 0
				&& (minposts <= 0 || member.getPosts() < minposts);
		request.setAttribute("secqaacheck", secqaacheck);

		byte usesig = (byte) Common.range(Common.intval(fileupload
				.getParameter("usesig")), 1, 0);
		int page = Common.toDigit(fileupload.getParameter("page"));
		String seccodeverify = fileupload.getParameter("seccodeverify");
		String secanswer = fileupload.getParameter("secanswer");
		if (fileupload.getParameter("topicsubmit") != null
				&& ((seccodeverify == null && seccodecheck) || (secanswer == null && secqaacheck))) {
			request.setAttribute("subject", subject);
			request.setAttribute("message", message);
			request.setAttribute("usesig", usesig);
			request.setAttribute("path", "post.jsp?action=newthread&fid=" + fid
					+ (page > 0 ? "&page=" + page : "") + "&topicsubmit=yes");
			return mapping.findForward("toPost_seccode");
		}
		if (seccodecheck) {
			if (!seccodeverify
					.equals(request.getSession().getAttribute("rand"))) {
				request.setAttribute("errorInfo", getMessage(request,
						"submit_seccode_invalid"));
				return mapping.findForward("showMessage");
			}
		}
		if (secqaacheck) {
			if (!secanswer.trim().equals(
					request.getSession().getAttribute("answer").toString())) {
				request.setAttribute("errorInfo", getMessage(request,
						"submit_secqaa_invalid"));
				return mapping.findForward("showMessage");
			}
		}
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		String checkflood = checkflood(request, uid, timestamp,
				member != null ? member.getLastpost() : 0, Integer
						.valueOf(settings.get("floodctrl")), Integer
						.valueOf(usergroups.get("disablepostctrl")), Integer
						.valueOf(usergroups.get("maxpostsperhour")));
		if (checkflood != null) {
			request.setAttribute("resultInfo", checkflood);
			return mapping.findForward("showMessage");
		}
		Map extcredits = dataParse.characterParse(settings.get("extcredits"),
				true);

		boolean allowpostattach = (Boolean) request
				.getAttribute("allowpostattach");
		if (allowpostattach) {
			String[] attach = fileupload.getParameterValues("attach[]");
			if (attach != null) {
				Map creditspolicy = dataParse.characterParse(settings
						.get("creditspolicy"), true);
				Map<Integer, Integer> postattachcredits = dataParse
						.characterParse(forumfield.getPostattachcredits(), true);
				Map<Integer, Integer> lowerlimits = (Map<Integer, Integer>) creditspolicy
						.get("lowerlimit");
				boolean checklowerlimits = (lowerlimits != null && lowerlimits
						.size() > 0);
				for (String attachname : attach) {
					if (attachname != null && !attachname.equals("")) {
						String errorMessage = checklowerlimit(request,
								extcredits, postattachcredits,
								checklowerlimits, member, 1);
						if (errorMessage != null) {
							request.setAttribute("errorInfo", errorMessage);
							return mapping.findForward("showMessage");
						}
					}
				}
				lowerlimits = null;
				postattachcredits = null;
				creditspolicy = null;
				attach = null;
			}
		}
		boolean modnewthreads = false;
		if (Common.periodscheck(settings.get("postmodperiods"), Byte
				.valueOf(usergroups.get("disableperiodctrl")), timestamp,
				settings.get("timeoffset"), resources, locale) != null) {
			modnewthreads = true;
		} else {
			int allowdirectpost = Integer.valueOf(usergroups
					.get("allowdirectpost"));
			boolean censormod = censormod(subject + "\t" + message, request);
			modnewthreads = (allowdirectpost == 0 || allowdirectpost == 1)
					&& forum.getModnewposts() > 0 || censormod;
		}
		Map<String, String> censor = (Map<String, String>) request
				.getAttribute("censor");
		String banned = censor.get("banned");
		if (!Common.empty(banned) && Common.matches(message, banned)) {
			request.setAttribute("errorInfo",
					getMessage(request, "word_banned"));
			return mapping.findForward("showMessage");
		}
		if (!Common.empty(banned) && Common.matches(subject, banned)) {
			request.setAttribute("errorInfo",
					getMessage(request, "word_banned"));
			return mapping.findForward("showMessage");
		}
		String filters = censor.get("filter");
		Map<String, Map<String, String>> filtermap = dataParse.characterParse(
				filters, false);
		if (filtermap != null) {
			Map<String, String> findsMap = filtermap.get("find");
			Map<String, String> replaceMap = filtermap.get("replace");
			if (findsMap != null && !findsMap.keySet().isEmpty()) {
				Iterator it = findsMap.keySet().iterator();
				while (it.hasNext()) {
					String id = it.next().toString();
					message = message.replaceAll(findsMap.get(id), replaceMap
							.get(id));
					subject = subject.replaceAll(findsMap.get(id), replaceMap
							.get(id));
				}
			}
		}
		short typeid = (short) Common.range(Common.intval(fileupload
				.getParameter("typeid")), 32767, 0);
		short iconid = (short) Common.range(Common.intval(fileupload
				.getParameter("iconid")), 32767, 0);
		boolean ismoderator = Common.ismoderator(forum.getFid(), member);
		byte displayorder = special == 2 ? 0
				: (byte) (modnewthreads ? -2 : (ismoderator
						&& fileupload.getParameter("sticktopic") != null ? 1
						: 0));
		byte digest = (byte) (ismoderator
				&& fileupload.getParameter("addtodigest") != null ? 1 : 0);
		byte blog = (byte) (allowuseblog == 1 && forum.getAllowshare() > 0
				&& fileupload.getParameter("addtoblog") != null ? 1 : 0);
		short readperm = "1".equals(usergroups.get("allowsetreadperm")) ? (short) Common
				.range(Common.intval(fileupload.getParameter("readperm")), 255,
						0)
				: 0;
		byte isanonymous = (byte) (Common.range(Common.intval(fileupload
				.getParameter("isanonymous")), 255, 0) > 0
				&& (Boolean) request.getAttribute("allowanonymous") ? 1 : 0);
		short price = (short) Common.range(Common.intval(fileupload
				.getParameter("price")), 32767, 0);
		short maxprice = Short.valueOf(usergroups.get("maxprice"));
		price = maxprice > 0 && special == 0 ? (price <= maxprice ? price
				: maxprice) : 0;
		Map threadtypes = dataParse.characterParse(forumfield.getThreadtypes(),
				false);
		if (typeid <= 0 && threadtypes != null
				&& threadtypes.get("required") != null
				&& 1 == (Integer) threadtypes.get("required") && !(special > 0)) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_type_isnull"));
			return mapping.findForward("showMessage");
		}
		double creditstax = Double.valueOf(settings.get("creditstax"));
		if (price > 0 && Math.floor(price * (1d - creditstax)) <= 0) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_net_price_iszero"));
			return mapping.findForward("showMessage");
		}
		short realprice = 0;
		Map pollarray = null;
		Map<String, String> activity = null;
		String affirmpoint = null;
		String negapoint = null;
		int endtime = 0;
		String umpire = null;
		if (special == 1) {// ͶƱ
			String[] options = fileupload.getParameter("polloptions").split(
					"\n");
			List<String> polloptions = new ArrayList<String>();
			if (polloptions != null) {
				for (String obj : options) {
					if (!obj.trim().equals("")) {
						polloptions.add(obj);
					}
				}
			}
			options = null;
			if (polloptions.size() > Integer.valueOf(settings
					.get("maxpolloptions"))) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_poll_option_toomany", settings
								.get("maxpolloptions")));
				return mapping.findForward("showMessage");
			} else if (polloptions.size() < 2) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_poll_inputmore"));
				return mapping.findForward("showMessage");
			}
			pollarray = new HashMap();
			pollarray.put("options", polloptions);
			pollarray.put("multiple",
					fileupload.getParameter("multiplepoll") != null ? 1 : 0);
			pollarray.put("visible",
					fileupload.getParameter("visiblepoll") != null ? 1 : 0);
			String maxchoices = fileupload.getParameter("maxchoices");
			String expiration = fileupload.getParameter("expiration");
			if (expiration != null) {
				expiration = expiration.trim();
			}
			if (Common.matches(maxchoices, "^\\d*$")
					&& Common.matches(expiration, "^\\d*$")) {
				int choices = Common.toDigit(maxchoices);
				choices = choices >= polloptions.size() ? polloptions.size()
						: choices;
				if ((Integer) pollarray.get("multiple") == 0) {
					pollarray.put("maxchoices", 1);
				} else if (maxchoices == null || maxchoices.equals("")) {
					pollarray.put("maxchoices", 0);
				} else if (choices == 1) {
					pollarray.put("multiple", 0);
					pollarray.put("maxchoices", choices);
				} else {
					pollarray.put("maxchoices", choices);
				}
				if ("".equals(expiration) || expiration.equals("0")) {
					pollarray.put("expiration", 0);
				} else {
					pollarray.put("expiration", timestamp + 86400
							* Integer.valueOf(expiration));
				}
			} else {
				request.setAttribute("errorInfo", getMessage(request,
						"poll_maxchoices_expiration_invalid"));
				return mapping.findForward("showMessage");
			}
			polloptions = null;
		} else if (special == 2) {
			if ("".equals(specialstarttime)) {
				request.setAttribute("errorInfo", "请输入开始时间");
				return mapping.findForward("showMessage");
			}
			if ("".equals(specialendtime)) {
				request.setAttribute("errorInfo", "请输入结束时间");
				return mapping.findForward("showMessage");
			}
			if (starttimeInt > endtimeInt) {
				request.setAttribute("errorInfo", "开始时间不能大于结束时间");
				return mapping.findForward("showMessage");
			}
			if ("3".equals(Allow)||"4".equals(Allow)) {

				if (permlist == null || permlist == "") {
					request.setAttribute("errorInfo", "请对会员进行筛选");
					return mapping.findForward("showMessage");
				} else {
					if (plist.length == 0) {
						request.setAttribute("errorInfo", "请对会员进行筛选");
						return mapping.findForward("showMessage");
					}
				}
			}
		}

		else if (special == 3) {// ����
			short rewardprice = (short) Common.toDigit(fileupload
					.getParameter("rewardprice"));
			int minrewardprice = Common.toDigit(usergroups
					.get("minrewardprice"));
			int maxrewardprice = Common.toDigit(usergroups
					.get("maxrewardprice"));
			realprice = (short) (rewardprice + Math.ceil(rewardprice
					* creditstax));
			String extcreditField = "extcredits" + settings.get("creditstrans");
			int extcredit = (Integer) Common.getValues(member, extcreditField);
			if (rewardprice < 1) {
				request.setAttribute("errorInfo", getMessage(request,
						"reward_credits_please"));
				return mapping.findForward("showMessage");
			} else if (rewardprice > 32767) {
				request.setAttribute("errorInfo", getMessage(request,
						"reward_credits_overflow"));
				return mapping.findForward("showMessage");
			} else if (rewardprice < minrewardprice
					|| (maxrewardprice > 0 && rewardprice > maxrewardprice)) {
				request.setAttribute("errorInfo", getMessage(request,
						"reward_credits_between", minrewardprice + "",
						maxrewardprice + ""));
				return mapping.findForward("showMessage");
			} else if (realprice > extcredit) {
				request.setAttribute("errorInfo", getMessage(request,
						"reward_credits_shortage"));
				return mapping.findForward("showMessage");
			}
			price = rewardprice;
			member = (Members) Common.setValues(member, extcreditField, String
					.valueOf(extcredit - realprice));
			memberService.modifyMember(member);
		} else if (special == 4) {// �
			int activitytime = Common.range(Common.intval(fileupload
					.getParameter("activitytime")), 1, 0);
			String starttimefrom = fileupload.getParameter("starttimefrom["
					+ activitytime + "]");
			String starttimeto = fileupload.getParameter("starttimeto");
			String activityclass = fileupload.getParameter("activityclass")
					.trim();
			String activityplace = fileupload.getParameter("activityplace")
					.trim();
			String activityexpiration = fileupload.getParameter(
					"activityexpiration").trim();
			String activitycity = fileupload.getParameter("activitycity")
					.trim();
			if (activitytime == 0 && starttimefrom.equals("")) {
				request.setAttribute("errorInfo", getMessage(request,
						"activity_fromtime_please"));
				return mapping.findForward("showMessage");
			} else if (checkDateFormatAndValite(starttimefrom) < 0) {
				request.setAttribute("errorInfo", getMessage(request,
						"activity_fromtime_error"));
				return mapping.findForward("showMessage");
			}
			int starttime = Common.dataToInteger(starttimefrom, timeoffset);
			int timeto = Common.dataToInteger(starttimeto, timeoffset);
			timeto = timeto > 0 ? timeto : 0;
			if (starttime < timestamp) {
				request.setAttribute("resultInfo", getMessage(request,
						"activity_smaller_current"));
				return mapping.findForward("showMessage");
			} else if (activitytime > 0 && (timeto <= 0 || starttime > timeto)) {
				request.setAttribute("errorInfo", getMessage(request,
						"activity_fromtime_error"));
				return mapping.findForward("showMessage");
			} else if ("".equals(activityclass)) {
				request.setAttribute("errorInfo", getMessage(request,
						"activity_sort_please"));
				return mapping.findForward("showMessage");
			} else if ("".equals(activityplace)) {
				request.setAttribute("errorInfo", getMessage(request,
						"activity_address_please"));
				return mapping.findForward("showMessage");
			} else if (!activityexpiration.equals("")
					&& checkDateFormatAndValite(activityexpiration) < 0) {
				request.setAttribute("errorInfo", getMessage(request,
						"activity_totime_error"));
				return mapping.findForward("showMessage");
			}
			activity = new HashMap<String, String>();
			activity.put("class", Common.cutstr(Common
					.htmlspecialchars(activityclass), 25, null));
			activity.put("starttimefrom", String.valueOf(starttime));
			activity.put("starttimeto", String.valueOf(timeto));
			activity.put("place", Common.cutstr(Common
					.htmlspecialchars(activityplace), 40, null));
			activity.put("cost", Common.range(Common.toDigit(fileupload
					.getParameter("cost")), 16777215, 0)
					+ "");
			activity.put("gender", Common.range(Common.intval(fileupload
					.getParameter("gender")), 2, 0)
					+ "");
			activity.put("number", Common.toDigit(fileupload
					.getParameter("number"))
					+ "");
			if (!activityexpiration.equals("")) {
				activity.put("expiration", String.valueOf(Common.dataToInteger(
						activityexpiration, timeoffset)));
			} else {
				activity.put("expiration", "0");
			}
			if (!activitycity.equals("")) {
				subject += "[" + Common.htmlspecialchars(activitycity) + "]";
			}
		} else if (special == 5) {// ����
			affirmpoint = fileupload.getParameter("affirmpoint");
			negapoint = fileupload.getParameter("negapoint");
			endtime = Common.dataToInteger(fileupload.getParameter("endtime"),
					timeoffset);
			umpire = fileupload.getParameter("umpire");
			if (affirmpoint == null || negapoint == null) {
				request.setAttribute("errorInfo", getMessage(request,
						"debate_position_nofound"));
				return mapping.findForward("showMessage");
			} else if (endtime > 0 && endtime < timestamp) {
				request.setAttribute("errorInfo", getMessage(request,
						"debate_endtime_invalid"));
				return mapping.findForward("showMessage");
			} else if (umpire != null) {
				Members me = memberService.findByName(umpire);
				if (me == null) {
					request.setAttribute("errorInfo", getMessage(request,
							"debate_umpire_invalid", umpire));
					return mapping.findForward("showMessage");
				}
				me = null;
			}
		}
		Map<Integer, String> expiration = threadtypes != null ? (Map<Integer, String>) threadtypes
				.get("expiration")
				: null;
		int typeexpiration = Common.toDigit(fileupload
				.getParameter("typeexpiration"));
		if ("1".equals(expiration != null ? expiration.get(typeid) : "0")
				&& typeexpiration <= 0) {
			expiration = null;
			request.setAttribute("errorInfo", getMessage(request,
					"threadtype_expiration_invalid"));
			return mapping.findForward("showMessage");
		}
		expiration = null;
		Map<Integer, String> specials = threadtypes != null ? (Map<Integer, String>) threadtypes
				.get("special")
				: null;
		typeid = special > 0
				&& "1".equals(specials != null ? specials.get(typeid) : "0") ? 0
				: typeid;
		specials = null;
		Map<Integer, String> optiondata = new TreeMap<Integer, String>();
		if (threadtypes != null && typeid > 0) {
			Common.include(request, response, servlet,
					"/forumdata/cache/threadtype_" + typeid + ".jsp", null);
			Map<String, String> threadtype = (Map<String, String>) request
					.getAttribute("threadtype");
			if (threadtype != null) {
				Map<Integer, Map<String, String>> dtype = dataParse
						.characterParse(threadtype.get("dtype"), true);
				threadtype = null;
				if (dtype != null
						&& dtype.size() > 0
						&& "1".equals(((Map<Integer, String>) threadtypes
								.get("special")).get((int) typeid))
						&& !(forum.getAllowspecialonly() > 0)) {
					Set<Integer> keys = dtype.keySet();
					for (Integer optionid : keys) {
						Map<String, String> option = dtype.get(optionid);
						String title = option.get("title");
						String identifier = option.get("identifier");
						String type = option.get("type");
						int maxlength = Common.toDigit(option.get("maxlength"));
						int maxnum = Common.toDigit(option.get("maxnum"));
						int minnum = Common.toDigit(option.get("minnum"));
						byte required = (byte) Common.range(Common
								.intval(option.get("required")), 1, 0);
						String value = fileupload.getParameter("typeoption["
								+ identifier + "]");
						if (required > 0 && (value == null || value.equals(""))) {
							request.setAttribute("errorInfo", getMessage(
									request, "threadtype_required_invalid",
									title));
							return mapping.findForward("showMessage");
						} else if (value != null
								&& !value.equals("")
								&& (type.equals("number")
										&& !Common.isNum(value) || type
										.equals("email")
										&& !Common.isEmail(value))) {
							request
									.setAttribute("errorInfo", getMessage(
											request,
											"threadtype_format_invalid", title));
							return mapping.findForward("showMessage");
						} else if (value != null && !value.equals("")
								&& maxlength > 0 && value.length() > maxlength) {
							request.setAttribute("errorInfo", getMessage(
									request, "threadtype_toolong_invalid",
									title));
							return mapping.findForward("showMessage");
						} else if (value != null
								&& !value.equals("")
								&& ((option.get("maxnum") != null && Integer
										.valueOf(value) > Integer
										.valueOf(maxnum)) || (option
										.get("minnum") != null && Integer
										.valueOf(value) < Integer
										.valueOf(minnum)))) {
							request.setAttribute("errorInfo", getMessage(
									request, "threadtype_num_invalid", title));
							return mapping.findForward("showMessage");
						} else if (type.equals("checkbox")) {
							String[] values = fileupload
									.getParameterValues("typeoption["
											+ identifier + "]");
							if (values != null) {
								StringBuffer temp = new StringBuffer();
								for (String obj : values) {
									temp.append("\t" + obj);
								}
								value = temp.substring(1);
							}
						}
						optiondata.put(optionid, value);
					}
				}
				dtype = null;
			}
		}
		String author = isanonymous == 0 ? (member != null ? member
				.getUsername() : getMessage(request, "anonymous")) : "";
		byte moderated = (byte) (digest != 0 || displayorder > 0 ? 1 : 0);
		List filelist = getAttach(fileupload);
		String attachmentMessage = this.checkAttachment(request, filelist,
				forumfield);
		if (attachmentMessage != null) {
			request.setAttribute("errorInfo", attachmentMessage);
			return mapping.findForward("showMessage");
		}

		byte subscribed = (byte) (fileupload.getParameter("emailnotify") != null
				&& member != null ? 1 : 0);
		Map supe_pushsetting = dataParse.characterParse(forumfield
				.getSupe_pushsetting(), false);
		byte supe_pushstatus = (byte) ("1".equals(settings.get("super_status"))
				&& supe_pushsetting != null
				&& "1".equals(supe_pushsetting.get("status")) && modnewthreads ? 1
				: 0);
		byte invisible = special == 2 ? 0 : (byte) (modnewthreads ? -2 : 0);

		String msg = "";
		if (special == 2 && "1".equals(isSendEmail) && "4".equals(Allow)) {
			msg = "非常感谢,发表的专题帖须管理员审核后才能显示";
			displayorder = -2;
			invisible = -2;
		}
		supe_pushsetting = null;
		Threads thread = new Threads();
		thread.setFid(fid);
		thread.setReadperm(readperm);
		thread.setPrice(price);
		thread.setIconid(iconid);
		thread.setTypeid(typeid);
		thread.setAuthor(author);
		thread.setAuthorid(member != null ? member.getUid() : 0);
		thread.setSubject(subject);
		thread.setDateline(timestamp);
		thread.setLastpost(timestamp);
		thread.setLastposter(author);
		thread.setDisplayorder(displayorder);
		thread.setDigest(digest);
		thread.setBlog(blog);
		thread.setSpecial((byte) special);
		thread.setAttachment(Byte.valueOf("0"));
		thread.setSubscribed(subscribed);
		thread.setModerated(moderated);
		thread.setSupePushstatus(supe_pushstatus);
		// 新增的字段（回复和评论,显示方式）
		thread.setIsComment(Byte.parseByte(remark));
		thread.setIsReply(Byte.parseByte(reply));
		thread.setShowType(Byte.parseByte(showtype));

		threadService.addThread(thread);
		int tid = thread.getTid();
		Map<String, String> searcharray = dataParse.characterParse(
				((Map<String, String>) request.getAttribute("smilies"))
						.get("searcharray"), false);
		byte bbcodeoff = checkbbcodes(message, Common.range(Common
				.intval(fileupload.getParameter("bbcodeoff")), 1, 0));
		byte smileyoff = checksmilies(message, Common.range(Common
				.intval(fileupload.getParameter("smileyoff")), 1, 0),
				searcharray);
		searcharray = null;
		byte parseurloff = (byte) Common.range(Common.intval(fileupload
				.getParameter("parseurloff")), 1, 0);
		byte tagstatus = (byte) Common.range(Common.intval(settings
				.get("tagstatus")), 255, 0);
		byte htmlon = (byte) ((tagstatus > 0
				&& fileupload.getParameter("tagoff") != null ? 1 : 0) + ("1"
				.equals(usergroups.get("allowhtml"))
				&& fileupload.getParameter("htmlon") != null ? 1 : 0));
		String onlineip = Common.get_onlineip(request);
		short groupid = (Short) session.getAttribute("jsprun_groupid");// �ӻỰ�еõ��û����id
		Posts post = new Posts();
		post.setFid(fid);
		post.setTid(tid);
		post.setFirst(Byte.valueOf("1"));
		post.setAuthor(member != null ? member.getUsername() : getMessage(
				request, "anonymous"));
		post.setAuthorid(member != null ? member.getUid() : 0);
		post.setSubject(subject);
		post.setDateline(timestamp);
		post.setMessage(message);
		post.setUseip(onlineip);
		post.setInvisible(invisible);
		post.setAnonymous(isanonymous);
		post.setUsesig(usesig);
		post.setHtmlon(htmlon);
		post.setBbcodeoff(bbcodeoff);
		post.setSmileyoff(smileyoff);
		post.setParseurloff(parseurloff);
		post.setAttachment((byte) 0);
		post.setGroup(this.getGroup(groupid));
		postService.saveOrupdatePosts(post);

		String[] face = fileupload.getParameterValues("face[]");
		// 新增的用户评论的选择项
		Comment comment = null;
		for (int i = 0; i < remarkoptions.length; i++) {
			comment = new Comment();
			comment.setClickTimes(0);
			comment.setThreadId((int) tid);
			comment.setCommentName(Common.htmlspecialchars(remarkoptions[i]));
			comment.setImg(face[i]);
			commentService.addComment(comment);
			comment = null;
		}

		// 是不是把帖子设置为模板
		String template = fileupload.getParameter("template");
		if ("1".equals(template)) {// 添加模板
			Integer ispublic = Integer.parseInt(fileupload
					.getParameter("ispublic"));// 得到模板的权限
			ThemeTemplate ttm = new ThemeTemplate();
			ttm.setIspublic(ispublic);
			ttm.setMessage(message);
			ttm.setSubject(subject);
			ttm.setUid(member.getUid());
			themeTemplateService.addThemeTemplate(ttm);
		}

		int pid = post.getPid();
		if (allowpostattach) {
			String attamessage = uploadAttachment(request, pid, tid, fid,
					Common.addslashes(message), filelist, forum
							.getDisablewatermark(), fileupload);
			if (!attamessage.equals("")) {
				dataBaseService.runQuery("delete from jrun_posts where pid="
						+ pid, true);
				dataBaseService.runQuery("delete from jrun_threads where tid="
						+ tid, true);
				request.setAttribute("resultInfo", attamessage);
				return mapping.findForward("showMessage");
			}
		}
		filelist = null;
		if (subscribed > 0) {
			dataBaseService.runQuery(
					"REPLACE INTO jrun_subscriptions (uid, tid, lastpost, lastnotify) VALUES ('"
							+ member.getUid() + "', '" + tid + "', '"
							+ timestamp + "', '" + timestamp + "')", true);
		}
		dataBaseService.runQuery(
				"REPLACE INTO jrun_mythreads (uid, tid, dateline, special) VALUES ('"
						+ uid + "', '" + tid + "', '" + timestamp + "', '"
						+ special + "')", true);
		if (special == 1) {
			String reporttype = fileupload.getParameter("reporttype");
			dataBaseService
					.runQuery(
							"INSERT INTO jrun_polls (tid, multiple, visible, maxchoices, expiration,reporttype) VALUES ('"
									+ tid
									+ "', '"
									+ pollarray.get("multiple")
									+ "', '"
									+ pollarray.get("visible")
									+ "', '"
									+ pollarray.get("maxchoices")
									+ "', '"
									+ pollarray.get("expiration")
									+ "','" + reporttype + "')", true);
			StringBuffer sql = new StringBuffer();
			sql
					.append("INSERT INTO jrun_polloptions (tid,votes,displayorder, polloption,voterids) VALUES ");
			boolean flag = false;
			for (String polloptvalue : (List<String>) pollarray.get("options")) {
				if (flag) {
					sql.append(",('"
							+ tid
							+ "','0','0','"
							+ Common.addslashes(Common
									.htmlspecialchars(polloptvalue)) + "','')");
				} else {
					sql.append("('"
							+ tid
							+ "','0','0','"
							+ Common.addslashes(Common
									.htmlspecialchars(polloptvalue)) + "','')");
				}
				flag = true;
			}
			if (flag) {
				dataBaseService.runQuery(sql.toString(), true);
			}
			sql = null;
		} else if (special == 2) {
			Special sp = new Special();
			sp.setEndTime(Integer.parseInt(endtimeInt + ""));
			sp.setPerm(Integer.parseInt(Allow + ""));
			sp.setStartTime(Integer.parseInt(starttimeInt + ""));
			sp.setTid(tid);
			specialService.addSpecial(sp);
			if ("3".equals(Allow) || "4".equals(Allow)) {
				if (plist.length > 0) {
					PermList perml = null;
					for (String pl : plist) {
						perml = new PermList();
						perml.setAccount(pl);
						perml.setEmail("");
						perml.setTid(tid);
						permlistService.addPermList(perml);
						perml = null;
					}
					perml=new PermList();
					perml.setAccount("admin");
					perml.setTid(tid);
					perml.setEmail("");
					permlistService.addPermList(perml);
					perml=null;
				}
			}
		}

		else if (special == 3 && allowpostreward) {
			dataBaseService.runQuery(
					"INSERT INTO jrun_rewardlog (tid, authorid, netamount, dateline) VALUES ('"
							+ tid + "', '" + member.getUid() + "', "
							+ realprice + ", '" + timestamp + "')", true);
		} else if (special == 4 && allowpostactivity) {
			dataBaseService
					.runQuery(
							"INSERT INTO jrun_activities (tid, uid, cost, starttimefrom, starttimeto, place, class, gender, number, expiration) VALUES ('"
									+ tid
									+ "', '"
									+ (member != null ? member.getUid() : 0)
									+ "', '"
									+ activity.get("cost")
									+ "', '"
									+ activity.get("starttimefrom")
									+ "', '"
									+ activity.get("starttimeto")
									+ "', '"
									+ Common.addslashes(activity.get("place"))
									+ "', '"
									+ Common.addslashes(activity.get("class"))
									+ "', '"
									+ activity.get("gender")
									+ "', '"
									+ activity.get("number")
									+ "', '"
									+ activity.get("expiration") + "')", true);
		} else if (special == 5 && allowpostdebate) {
			dataBaseService
					.runQuery(
							"INSERT INTO jrun_debates (tid, uid, starttime, endtime, affirmdebaters, negadebaters, affirmvotes, negavotes, umpire, winner, bestdebater, affirmpoint, negapoint, umpirepoint,affirmvoterids,negavoterids,affirmreplies,negareplies) VALUES ('"
									+ tid
									+ "', '"
									+ member.getUid()
									+ "', '"
									+ timestamp
									+ "', '"
									+ (endtime > 0 ? endtime : 0)
									+ "', '0', '0', '0', '0', '"
									+ umpire
									+ "', '0', '', '"
									+ Common.addslashes(Common
											.htmlspecialchars(affirmpoint))
									+ "', '"
									+ Common.addslashes(Common
											.htmlspecialchars(negapoint))
									+ "', '','','','0','0')", true);
		} else if (special == 6 && allowpostvideo) {

		}
		if (moderated > 0) {
			Common.updatemodlog(member, timestamp, String.valueOf(tid),
					(displayorder > 0 ? "STK" : "DIG"), null, 1, false);
			Common.updatemodworks(settings, uid, timestamp,
					(displayorder > 0 ? "STK" : "DIG"), 1);
		}
		if (threadtypes.get("special") != null
				&& "1".equals(((Map<Integer, String>) threadtypes
						.get("special")).get((int) typeid))
				&& optiondata != null && optiondata.size() > 0) {
			StringBuffer sql = new StringBuffer();
			sql
					.append("INSERT INTO jrun_typeoptionvars (typeid, tid, optionid,value, expiration) VALUES ");
			boolean flag = false;
			typeexpiration = (typeexpiration > 0 ? timestamp + typeexpiration
					: 0);
			Set<Integer> keys = optiondata.keySet();
			for (Integer optionid : keys) {
				if (flag) {
					sql.append(",('" + typeid + "', '" + tid + "', '"
							+ optionid + "', '"
							+ Common.addslashes(optiondata.get(optionid))
							+ "', '" + typeexpiration + "')");
				} else {
					sql.append("('" + typeid + "', '" + tid + "', '" + optionid
							+ "', '"
							+ Common.addslashes(optiondata.get(optionid))
							+ "', '" + typeexpiration + "')");
				}
				flag = true;
			}
			if (flag) {
				dataBaseService.runQuery(sql.toString(), true);
			}
		}
		threadtypes = null;
		String tags = fileupload.getParameter("tags");
		if (tagstatus > 0 && tags != null && !"".equals(tags)) {
			String[] tagarray = tags.split(" ");
			int tagcount = 0;
			for (String tagname : tagarray) {
				tagname = Common.addslashes(tagname.trim());
				int len = Common.strlen(tagname);
				if (len >= 3 && len <= 20) {
					List<Map<String, String>> map = dataBaseService
							.executeQuery("SELECT closed FROM jrun_tags WHERE tagname='"
									+ tagname + "'");
					int isclosed = 0;
					if (map != null && map.size() > 0) {
						isclosed = Integer.valueOf(map.get(0).get("closed"));
						if (isclosed == 0) {
							dataBaseService.runQuery(
									"UPDATE jrun_tags SET total=total+1 WHERE tagname='"
											+ tagname + "'", true);
						}
					} else {
						dataBaseService.runQuery(
								"INSERT INTO jrun_tags (tagname, closed, total) VALUES ('"
										+ tagname + "', 0, 1)", true);
					}
					if (isclosed == 0) {
						dataBaseService.runQuery(
								"INSERT jrun_threadtags (tagname, tid) VALUES ('"
										+ tagname + "', " + tid + ")", true);
					}
					tagcount++;
					if (tagcount > 4) {
						break;
					}
				}
			}
		}

		if (modnewthreads && special != 2) {
			dataBaseService.runQuery(
					"UPDATE jrun_forums SET todayposts=todayposts+1 WHERE fid='"
							+ fid + "'", true);
			if (allowuseblog == 1 && "yes".equals(isblog) && blog > 0) {
				request.setAttribute("successInfo", getMessage(request,
						"post_newthread_mod_blog_succeed"));
				request.setAttribute("requestPath", "blog.jsp?uid="
						+ member.getUid());
				return mapping.findForward("showMessage");
			} else {
				request.setAttribute("successInfo", getMessage(request,
						"post_newthread_mod_succeed"));
				request.setAttribute("requestPath", "forumdisplay.jsp?fid="
						+ fid);
				return mapping.findForward("showMessage");
			}
		} else if (special == 2 && (!"".equals(msg))) {
			dataBaseService.runQuery(
					"UPDATE jrun_forums SET todayposts=todayposts+1 WHERE fid='"
							+ fid + "'", true);
			request.setAttribute("successInfo", msg);
			request.setAttribute("requestPath", "forumdisplay.jsp?fid=" + fid);
			return mapping.findForward("showMessage");
		} else {
			Map creditspolicys = dataParse.characterParse(settings
					.get("creditspolicy"), false);
			Map<Integer, Integer> postcredits = dataParse.characterParse(
					forumfield.getPostcredits(), false);
			if (postcredits == null || postcredits.size() <= 0) {
				postcredits = (Map<Integer, Integer>) creditspolicys
						.get("post");
			}
			if (digest > 0) {
				Map<Integer, Integer> digestcredits = dataParse.characterParse(
						forumfield.getDigestcredits(), false);
				if (digestcredits == null || digestcredits.size() <= 0) {
					digestcredits = (Map<Integer, Integer>) creditspolicys
							.get("digest");
				}
				if (digest > 0) {
					Set<Integer> keys = digestcredits.keySet();
					for (Integer key : keys) {
						postcredits.put(key,
								(postcredits.get(key) != null ? postcredits
										.get(key) : 0)
										+ digestcredits.get(key));
					}
				}
			}
			creditspolicys = null;
			if (member != null) {
				Common.updatepostcredits("+", uid, postcredits, timestamp);
				Common.updatepostcredits(uid, settings.get("creditsformula"));
				Common.updateMember(session, uid);
			}
			postcredits = null;
			String lastpost = tid
					+ "\t"
					+ Common.cutstr(Common.addslashes(subject.replaceAll("\t",
							" ")), 40, null) + "\t" + timestamp + "\t" + author;
			dataBaseService
					.runQuery(
							"UPDATE jrun_forums SET lastpost='"
									+ lastpost
									+ "', threads=threads+1, posts=posts+1, todayposts=todayposts+1 WHERE fid='"
									+ fid + "'", true);
			if (forum.getType().equals("sub")) {
				dataBaseService.runQuery("UPDATE jrun_forums SET lastpost='"
						+ lastpost + "' WHERE fid='" + forum.getFup() + "'",
						true);
			}
			if (allowuseblog == 1 && "yes".equals(isblog) && blog > 0) {
				if (Common
						.isshowsuccess(session, "post_newthread_blog_succeed")) {
					Common.requestforward(response, "blog.jsp?tid=" + tid);
					return null;
				} else {
					request.setAttribute("successInfo", getMessage(request,
							"post_newthread_blog_succeed"));
					request.setAttribute("requestPath", "blog.jsp?tid=" + tid);
					return mapping.findForward("showMessage");
				}
			} else {
				int frombbs = Common
						.toDigit(fileupload.getParameter("frombbs"));
				if (Common.isshowsuccess(session, "post_newthread_succeed")) {
					Common.requestforward(response, "viewthread.jsp?tid=" + tid
							+ (page > 0 ? "&page=" + page : "")
							+ (frombbs > 0 ? "&frombbs=" + frombbs : ""));
					return null;
				}
				request.setAttribute("successInfo", getMessage(request,
						"post_newthread_succeed", fid + ""));
				request.setAttribute("requestPath", "viewthread.jsp?tid=" + tid
						+ (page > 0 ? "&page=" + page : "")
						+ (frombbs > 0 ? "&frombbs=" + frombbs : ""));
				return mapping.findForward("showMessage");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward newtrade(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		short fid = Short.valueOf(request.getParameter("fid"));
		Forums forum = forumService.findById(fid);
		if (forum == null || "group".equals(forum.getType())) {
			request.setAttribute("errorInfo", getMessage(request,
					"forum_nonexistence"));
			return mapping.findForward("showMessage");
		}
		File tempfile = isMakeDir();
		ModuleConfig ac = (ModuleConfig) request
				.getAttribute(Globals.MODULE_KEY);
		FileUploadUtil fileupload = new FileUploadUtil(tempfile, memeoryBlock,
				ac);
		try {
			fileupload.parse(request, JspRunConfig.CHARSET);
		} catch (IllegalStateException e) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_attachment_toobig"));
			return mapping.findForward("showMessage");
		}
		int special = Common.range(Common.intval(fileupload
				.getParameter("special")), 6, 0);
		if (special != 2) {
			request.setAttribute("errorInfo", getMessage(request,
					"undefined_action"));
			return mapping.findForward("showMessage");
		}
		Forumfields forumfield = forumfieldService.findById(fid);
		if ((forum.getSimple() & 1) > 0
				|| forumfield.getRedirect().length() > 0) {
			request.setAttribute("resultInfo", getMessage(request,
					"forum_disablepost"));
			return mapping.findForward("showMessage");
		}
		Map<String, String> usergroups = (Map<String, String>) request
				.getAttribute("usergroups");
		Map<String, String> settings = ForumInit.settings;
		HttpSession session = request.getSession();
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		short jsprun_groupid = (Short) session.getAttribute("jsprun_groupid");
		Members member = (Members) session.getAttribute("user");
		int allowpost = Integer.valueOf(usergroups.get("allowpost"));
		String postperm = forumfield.getPostperm();
		List<Map<String, String>> accesslist = dataBaseService
				.executeQuery("select allowpost,allowpostattach from jrun_access where uid='"
						+ jsprun_uid + "' and fid='" + fid + "'");
		Map<String, String> access = accesslist.size() > 0 ? accesslist.get(0)
				: null;
		if (member == null
				&& !((postperm.equals("") && allowpost > 0) || (!postperm
						.equals("") && Common.forumperm(postperm,
						jsprun_groupid, member != null ? member
								.getExtgroupids() : "")))) {
			request.setAttribute("show_message", getMessage(request,
					"group_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		} else if (access == null || Common.empty(access.get("allowpost"))) {
			if (postperm.equals("") && allowpost == 0) {
				request.setAttribute("show_message", getMessage(request,
						"group_nopermission", usergroups.get("grouptitle")));
				return mapping.findForward("nopermission");
			} else if (!postperm.equals("")
					&& !Common.forumperm(postperm, jsprun_groupid,
							member != null ? member.getExtgroupids() : "")) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_forum_newthread_nopermission"));
				return mapping.findForward("showMessage");
			}
		}
		String isblog = fileupload.getParameter("isblog");
		int allowuseblog = Integer.valueOf(usergroups.get("allowuseblog"));
		if ("yes".equals(isblog)
				&& (allowuseblog == 0 || forum.getAllowshare() == 0)) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_newthread_blog_invalid"));
			return mapping.findForward("showMessage");
		}
		this.common(request, response, settings, usergroups, forumfield, forum,
				special, access, fileupload);
		boolean allowposttrade = (Boolean) request
				.getAttribute("allowposttrade");
		if (!allowposttrade) {
			request.setAttribute("show_message", getMessage(request,
					"group_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		}
		Map extcredits = dataParse.characterParse(settings.get("extcredits"),
				true);
		Map creditspolicy = dataParse.characterParse(settings
				.get("creditspolicy"), true);
		Map<Integer, Integer> postcredits = dataParse.characterParse(forumfield
				.getPostcredits(), true);
		Map<Integer, Integer> lowerlimits = (Map<Integer, Integer>) creditspolicy
				.get("lowerlimit");
		boolean checklowerlimits = (lowerlimits != null && lowerlimits.size() > 0);
		String lowerlimitmessage = this.checklowerlimit(request, extcredits,
				postcredits, checklowerlimits, member, 1);
		if (lowerlimitmessage != null) {
			request.setAttribute("errorInfo", lowerlimitmessage);
			return mapping.findForward("showMessage");
		}
		String subject = fileupload.getParameter("subject");
		String message = fileupload.getParameter("message");
		String previewpost = fileupload.getParameter("previewpost");
		if (previewpost != null) {
			request.setAttribute("typeid", fileupload.getParameter("typeid"));
			return this.toNewthread(mapping, form, request, response);
		}
		if (subject == null || message == null || subject.equals("")
				|| message.equals("")) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_sm_isnull"));
			return mapping.findForward("showMessage");
		}
		subject = Common.htmlspecialchars(subject);
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		String post_invalid = Common.checkpost(subject, message, settings,
				usergroups, resources, locale);
		if (post_invalid != null) {
			request.setAttribute("errorInfo", post_invalid);
			return mapping.findForward("showMessage");
		}
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		String checkflood = checkflood(request, jsprun_uid, timestamp,
				member != null ? member.getLastpost() : 0, Integer
						.valueOf(settings.get("floodctrl")), Integer
						.valueOf(usergroups.get("disablepostctrl")), Integer
						.valueOf(usergroups.get("maxpostsperhour")));
		if (checkflood != null) {
			request.setAttribute("resultInfo", checkflood);
			return mapping.findForward("showMessage");
		}
		String item_prices = fileupload.getParameter("item_price");
		if (item_prices != null && item_prices.matches("^-?\\d+\\.?\\d*$")) {
			item_prices = item_prices.length() > 9 ? "999999.99" : item_prices;
			double costprice = Double.valueOf(item_prices);
			costprice = costprice > 999999.99 ? 999999.99 : costprice;
			item_prices = Common.number_format(costprice, "0.00");
		} else {
			item_prices = "0";
		}
		double item_price = Double.valueOf(item_prices);
		String item_name = fileupload.getParameter("item_name").trim();
		float maxtradeprice = Float.valueOf(usergroups.get("maxtradeprice"));
		float mintradeprice = Float.valueOf(usergroups.get("mintradeprice"));
		int item_number = Common
				.toDigit(fileupload.getParameter("item_number"));
		if (item_name.length() == 0) {
			request.setAttribute("errorInfo", getMessage(request,
					"trade_please_name"));
			return mapping.findForward("showMessage");
		} else if (maxtradeprice > 0
				&& (mintradeprice > item_price || maxtradeprice < item_price)) {
			request.setAttribute("errorInfo", getMessage(request,
					"trade_price_between", mintradeprice + "", maxtradeprice
							+ ""));
			return mapping.findForward("showMessage");
		} else if (maxtradeprice == 0 && mintradeprice > item_price) {
			request.setAttribute("errorInfo", getMessage(request,
					"trade_price_more_than", mintradeprice + ""));
			return mapping.findForward("showMessage");
		} else if (item_number < 1) {
			request.setAttribute("errorInfo", getMessage(request,
					"tread_please_number"));
			return mapping.findForward("showMessage");
		}
		item_number = item_number > 65535 ? 65535 : item_number;
		item_name = Common.cutstr(Common.addslashes(Common
				.htmlspecialchars(item_name)), 100, null);
		boolean allowpostattach = (Boolean) request
				.getAttribute("allowpostattach");
		if (allowpostattach) {
			String[] attach = fileupload.getParameterValues("attach[]");
			if (attach != null) {
				Map<Integer, Integer> postattachcredits = dataParse
						.characterParse(forumfield.getPostattachcredits(), true);
				for (String attachname : attach) {
					if (attachname != null && !attachname.equals("")) {
						String errorMessage = checklowerlimit(request,
								extcredits, postattachcredits,
								checklowerlimits, member, 1);
						if (errorMessage != null) {
							request.setAttribute("errorInfo", errorMessage);
							return mapping.findForward("showMessage");
						}
					}
				}
				lowerlimits = null;
				postattachcredits = null;
				creditspolicy = null;
				attach = null;
			}
		}
		boolean modnewthreads = false;
		if (Common.periodscheck(settings.get("postmodperiods"), Byte
				.valueOf(usergroups.get("disableperiodctrl")), timestamp,
				settings.get("timeoffset"), resources, locale) != null) {
			modnewthreads = true;
		} else {
			int allowdirectpost = Integer.valueOf(usergroups
					.get("allowdirectpost"));
			boolean censormod = censormod(subject + "\t" + message, request);
			modnewthreads = (allowdirectpost == 0 || allowdirectpost == 1)
					&& forum.getModnewposts() > 0 || censormod;
		}
		Map<String, String> censor = (Map<String, String>) request
				.getAttribute("censor");
		String banned = censor.get("banned");
		if (!Common.empty(banned) && Common.matches(message, banned)) {
			request.setAttribute("errorInfo",
					getMessage(request, "word_banned"));
			return mapping.findForward("showMessage");
		}
		if (!Common.empty(banned) && Common.matches(subject, banned)) {
			request.setAttribute("errorInfo",
					getMessage(request, "word_banned"));
			return mapping.findForward("showMessage");
		}
		String filters = censor.get("filter");
		Map<String, Map<String, String>> filtermap = dataParse.characterParse(
				filters, false);
		if (filtermap != null) {
			Map<String, String> findsMap = filtermap.get("find");
			Map<String, String> replaceMap = filtermap.get("replace");
			if (findsMap != null && !findsMap.keySet().isEmpty()) {
				Iterator it = findsMap.keySet().iterator();
				while (it.hasNext()) {
					String id = it.next().toString();
					message = message.replaceAll(findsMap.get(id), replaceMap
							.get(id));
					subject = subject.replaceAll(findsMap.get(id), replaceMap
							.get(id));
				}
			}
		}
		FileItem tradfile = fileupload.getFileItem("tradeattach");
		if (tradfile != null && tradfile.getSize() > 0) {
			List tradfilelist = new ArrayList();
			tradfilelist.add(tradfile);
			tradfilelist.add("1");
			String mess = checkAttachment(request, tradfilelist, forumfield);
			if (mess != null) {
				request.setAttribute("errorInfo", mess);
				return mapping.findForward("showMessage");
			}
		}
		short typeid = (short) Common.range(Common.intval(fileupload
				.getParameter("typeid")), 32767, 0);
		short tradetypeid = (short) Common.range(Common.intval(fileupload
				.getParameter("tradetypeid")), 32767, 0);
		short iconid = (short) Common.range(Common.intval(fileupload
				.getParameter("iconid")), 32767, 0);
		boolean ismoderator = Common.ismoderator(forum.getFid(), member);
		byte displayorder = (byte) (modnewthreads ? -2 : (ismoderator
				&& fileupload.getParameter("sticktopic") != null ? 1 : 0));
		byte digest = (byte) (ismoderator
				&& fileupload.getParameter("addtodigest") != null ? 1 : 0);
		byte blog = (byte) (allowuseblog == 1 && forum.getAllowshare() > 0
				&& fileupload.getParameter("addtoblog") != null ? 1 : 0);
		short readperm = "1".equals(usergroups.get("allowsetreadperm")) ? (short) Common
				.range(Common.intval(fileupload.getParameter("readperm")), 255,
						0)
				: 0;
		byte isanonymous = (byte) (Common.range(Common.intval(fileupload
				.getParameter("isanonymous")), 255, 0) > 0
				&& (Boolean) request.getAttribute("allowanonymous") ? 1 : 0);
		short price = (short) Common.range(Common.intval(fileupload
				.getParameter("price")), 32767, 0);
		short maxprice = Short.valueOf(usergroups.get("maxprice"));
		price = maxprice > 0 && special == 0 ? (price <= maxprice ? price
				: maxprice) : 0;
		Map tradetypes = dataParse.characterParse(forumfield.getTradetypes(),
				false);
		Map<Integer, String> optiondata = new TreeMap<Integer, String>();
		if (tradetypes != null && tradetypeid > 0) {
			Common
					.include(request, response, servlet,
							"/forumdata/cache/threadtype_" + tradetypeid
									+ ".jsp", null);
			Map<String, String> threadtype = (Map<String, String>) request
					.getAttribute("threadtype");
			if (threadtype != null) {
				Map<Integer, Map<String, String>> dtype = dataParse
						.characterParse(threadtype.get("dtype"), true);
				threadtype = null;
				if (dtype != null && dtype.size() > 0
						&& !(forum.getAllowspecialonly() > 0)) {
					Set<Integer> keys = dtype.keySet();
					for (Integer optionid : keys) {
						Map<String, String> option = dtype.get(optionid);
						String title = option.get("title");
						String identifier = option.get("identifier");
						String type = option.get("type");
						int maxlength = Common.toDigit(option.get("maxlength"));
						int maxnum = Common.toDigit(option.get("maxnum"));
						int minnum = Common.toDigit(option.get("minnum"));
						byte required = (byte) Common.range(Common
								.intval(option.get("required")), 1, 0);
						String value = fileupload.getParameter("typeoption["
								+ identifier + "]");
						if (required > 0 && (value == null || value.equals(""))) {
							request.setAttribute("errorInfo", getMessage(
									request, "threadtype_required_invalid",
									title));
							return mapping.findForward("showMessage");
						} else if (value != null
								&& !value.equals("")
								&& (type.equals("number")
										&& !Common.isNum(value) || type
										.equals("email")
										&& !Common.isEmail(value))) {
							request
									.setAttribute("errorInfo", getMessage(
											request,
											"threadtype_format_invalid", title));
							return mapping.findForward("showMessage");
						} else if (value != null && !value.equals("")
								&& maxlength > 0 && value.length() > maxlength) {
							request.setAttribute("errorInfo", getMessage(
									request, "threadtype_toolong_invalid",
									title));
							return mapping.findForward("showMessage");
						} else if (value != null
								&& !value.equals("")
								&& ((option.get("maxnum") != null && Integer
										.valueOf(value) > Integer
										.valueOf(maxnum)) || (option
										.get("minnum") != null && Integer
										.valueOf(value) < Integer
										.valueOf(minnum)))) {
							request.setAttribute("errorInfo", getMessage(
									request, "threadtype_num_invalid", title));
							return mapping.findForward("showMessage");
						}
						optiondata.put(optionid, value);
					}
				}
				dtype = null;
			}
		}
		String counterdesc = Common.addslashes(fileupload
				.getParameter("counterdesc"));
		String aboutcounter = Common.addslashes(fileupload
				.getParameter("aboutcounter"));
		String threadmessage = counterdesc + "\t\t\t" + aboutcounter;
		String author = isanonymous == 0 ? (member != null ? member
				.getUsername() : getMessage(request, "anonymous")) : "";
		int moderated = digest > 0 || displayorder > 0 ? 1 : 0;
		int attachment = Byte.valueOf("0");
		List filelist = getAttach(fileupload);
		String attachmentMessage = this.checkAttachment(request, filelist,
				forumfield);
		if (attachmentMessage != null) {
			request.setAttribute("errorInfo", attachmentMessage);
			return mapping.findForward("showMessage");
		}
		byte subscribed = (byte) (fileupload.getParameter("emailnotify") != null
				&& member != null ? 1 : 0);
		Map supe_pushsetting = dataParse.characterParse(forumfield
				.getSupe_pushsetting(), false);
		byte supe_pushstatus = (byte) ("1".equals(settings.get("super_status"))
				&& supe_pushsetting != null
				&& "1".equals(supe_pushsetting.get("status")) && modnewthreads ? 1
				: 0);
		supe_pushsetting = null;
		int tid = dataBaseService
				.insert(
						"INSERT INTO jrun_threads (fid, readperm, price, iconid, typeid, author, authorid, subject, dateline, lastpost, lastposter, displayorder, digest, blog, special, attachment, subscribed, moderated, supe_pushstatus, replies)VALUES ('"
								+ fid
								+ "', '"
								+ readperm
								+ "', '"
								+ price
								+ "', '"
								+ iconid
								+ "', '"
								+ typeid
								+ "', '"
								+ author
								+ "', '"
								+ jsprun_uid
								+ "', '"
								+ Common.addslashes(subject)
								+ "', '"
								+ timestamp
								+ "', '"
								+ timestamp
								+ "', '"
								+ author
								+ "', '"
								+ displayorder
								+ "', '"
								+ digest
								+ "', '"
								+ blog
								+ "', '"
								+ special
								+ "', '"
								+ attachment
								+ "', '"
								+ subscribed
								+ "', '"
								+ moderated
								+ "', '"
								+ supe_pushstatus
								+ "', '1')", true);

		if (subscribed > 0) {
			dataBaseService
					.runQuery("REPLACE INTO jrun_subscriptions (uid, tid, lastpost, lastnotify) VALUES ('"
							+ jsprun_uid
							+ "', '"
							+ tid
							+ "', '"
							+ timestamp
							+ "', '" + timestamp + "')");
		}
		dataBaseService
				.runQuery("REPLACE INTO jrun_mythreads (uid, tid, dateline, special) VALUES ('"
						+ jsprun_uid
						+ "', '"
						+ tid
						+ "', '"
						+ timestamp
						+ "', '" + special + "')");
		if (moderated > 0) {
			Common.updatemodlog(member, timestamp, String.valueOf(tid),
					(displayorder > 0 ? "STK" : "DIG"), null, 1, false);
			Common.updatemodworks(settings, jsprun_uid, timestamp,
					(displayorder > 0 ? "STK" : "DIG"), 1);
		}
		Map<String, String> searcharray = dataParse.characterParse(
				((Map<String, String>) request.getAttribute("smilies"))
						.get("searcharray"), false);
		byte bbcodeoff = checkbbcodes(message, Common.range(Common
				.intval(fileupload.getParameter("bbcodeoff")), 1, 0));
		byte smileyoff = checksmilies(message, Common.range(Common
				.intval(fileupload.getParameter("smileyoff")), 1, 0),
				searcharray);
		byte parseurloff = (byte) Common.range(Common.intval(fileupload
				.getParameter("parseurloff")), 1, 0);
		byte tagstatus = (byte) Common.range(Common.intval(settings
				.get("tagstatus")), 255, 0);
		byte htmlon = (byte) ((tagstatus > 0
				&& fileupload.getParameter("tagoff") != null ? 1 : 0) + ("1"
				.equals(usergroups.get("allowhtml"))
				&& fileupload.getParameter("htmlon") != null ? 1 : 0));
		byte pinvisible = (byte) (modnewthreads ? -2 : 0);
		byte usesig = (byte) Common.range(Common.intval(fileupload
				.getParameter("usesig")), 1, 0);
		int page = Common.toDigit(fileupload.getParameter("page"));
		String onlineip = Common.get_onlineip(request);
		int pid = dataBaseService
				.insert(
						"INSERT INTO jrun_posts (fid, tid, first, author, authorid, subject, dateline, message, useip, invisible, anonymous, usesig, htmlon, bbcodeoff, smileyoff, parseurloff, attachment) VALUES ('"
								+ fid
								+ "', '"
								+ tid
								+ "', '1', '"
								+ author
								+ "', '"
								+ jsprun_uid
								+ "', '"
								+ Common.addslashes(subject)
								+ "', '"
								+ timestamp
								+ "', '"
								+ threadmessage
								+ "', '"
								+ onlineip
								+ "', '"
								+ pinvisible
								+ "', '"
								+ isanonymous
								+ "', '"
								+ usesig
								+ "', '"
								+ htmlon
								+ "', '"
								+ bbcodeoff
								+ "', '"
								+ smileyoff + "', '" + parseurloff + "', '0')",
						true);
		String tags = fileupload.getParameter("tags");
		if (tagstatus > 0 && tags != null && !"".equals(tags)) {
			String[] tagarray = tags.split(" ");
			int tagcount = 0;
			for (String tagname : tagarray) {
				tagname = tagname.trim();
				int len = Common.strlen(tagname);
				if (len >= 3 && len <= 20) {
					List<Map<String, String>> map = dataBaseService
							.executeQuery("SELECT closed FROM jrun_tags WHERE tagname='"
									+ tagname + "'");
					int isclosed = 0;
					if (map != null && map.size() > 0) {
						isclosed = Integer.valueOf(map.get(0).get("closed"));
						if (isclosed == 0) {
							dataBaseService.runQuery(
									"UPDATE jrun_tags SET total=total+1 WHERE tagname='"
											+ tagname + "'", true);
						}
					} else {
						dataBaseService.runQuery(
								"INSERT INTO jrun_tags (tagname, closed, total) VALUES ('"
										+ tagname + "', 0, 1)", true);
					}
					if (isclosed == 0) {
						dataBaseService.runQuery(
								"INSERT jrun_threadtags (tagname, tid) VALUES ('"
										+ tagname + "', " + tid + ")", true);
					}
					tagcount++;
					if (tagcount > 4) {
						break;
					}
				}
			}
		}
		if (tradetypes != null && optiondata != null && optiondata.size() > 0) {
			StringBuffer sql = new StringBuffer();
			sql
					.append("INSERT INTO jrun_tradeoptionvars (typeid, pid, optionid,value) VALUES ");
			boolean flag = false;
			Set<Integer> keys = optiondata.keySet();
			for (Integer optionid : keys) {
				if (flag) {
					sql.append(",('" + tradetypeid + "', '" + pid + "', '"
							+ optionid + "', '"
							+ Common.addslashes(optiondata.get(optionid))
							+ "')");
				} else {
					sql.append("('" + tradetypeid + "', '" + pid + "', '"
							+ optionid + "', '"
							+ Common.addslashes(optiondata.get(optionid))
							+ "')");
				}
				flag = true;
			}
			if (flag) {
				dataBaseService.runQuery(sql.toString(), true);
			}
		}
		int postage_mail = Common.toDigit(fileupload
				.getParameter("postage_mail"));
		int postage_express = Common.toDigit(fileupload
				.getParameter("postage_express"));
		int postage_ems = Common
				.toDigit(fileupload.getParameter("postage_ems"));
		String item_expiration = fileupload.getParameter("item_expiration");
		String timeoffset = (String) session.getAttribute("timeoffset");
		int expiration = Common.datecheck(item_expiration) ? Common
				.dataToInteger(item_expiration, "yyyy-MM-dd", timeoffset) : 0;
		String item_costprice = fileupload.getParameter("item_costprice");
		String seller = fileupload.getParameter("seller");
		if (item_costprice != null
				&& item_costprice.matches("^-?\\d+\\.?\\d*$")) {
			item_costprice = item_costprice.length() > 9 ? "999999.99"
					: item_costprice;
			double costprice = Double.valueOf(item_costprice);
			costprice = costprice > 999999.99 ? 999999.99 : costprice;
			item_costprice = Common.number_format(costprice, "0.00");
		} else {
			item_costprice = "0";
		}
		postage_mail = postage_mail > 65535 ? 65535 : postage_mail;
		postage_express = postage_express > 65535 ? 65535 : postage_express;
		postage_ems = postage_ems > 65535 ? 65535 : postage_ems;
		String item_locus = fileupload.getParameter("item_locus");
		item_locus = Common.cutstr(Common.addslashes(Common
				.htmlspecialchars(item_locus)), 20, null);
		String aid = "0";
		message = Common.addslashes(message);
		pid = dataBaseService
				.insert(
						"INSERT INTO jrun_posts (fid, tid, first, author, authorid, subject, dateline, message, useip, invisible, anonymous, usesig, htmlon, bbcodeoff, smileyoff, parseurloff, attachment) VALUES ('"
								+ fid
								+ "', '"
								+ tid
								+ "', '0', '"
								+ author
								+ "', '"
								+ jsprun_uid
								+ "', '"
								+ Common.addslashes(subject)
								+ "', '"
								+ timestamp
								+ "', '"
								+ message
								+ "', '"
								+ onlineip
								+ "', '"
								+ pinvisible
								+ "', '"
								+ isanonymous
								+ "', '"
								+ usesig
								+ "', '"
								+ htmlon
								+ "', '"
								+ bbcodeoff
								+ "', '"
								+ smileyoff + "', '" + parseurloff + "', '0')",
						true);
		if (tradfile != null && tradfile.getSize() > 0) {
			String mess = uploadTradeAttachment(request, pid, tid, forum
					.getFid(), tradfile, forum.getDisablewatermark());
			if (!Common.isNum(mess)) {
				request.setAttribute("errorInfo", mess);
				return mapping.findForward("showMessage");
			} else {
				aid = mess;
			}
		}
		dataBaseService
				.runQuery("INSERT INTO jrun_trades (tid, pid, typeid,sellerid,seller,account,subject,price,amount,quality,locus,transport,ordinaryfee,expressfee,emsfee,itemtype,dateline,expiration,lastbuyer,lastupdate,totalitems,tradesum,closed,aid,displayorder,costprice) VALUES"
						+ "('"
						+ tid
						+ "','"
						+ pid
						+ "','"
						+ tradetypeid
						+ "','"
						+ jsprun_uid
						+ "','"
						+ author
						+ "','"
						+ seller
						+ "','"
						+ item_name
						+ "','"
						+ Common.number_format(item_price, "0.00")
						+ "','"
						+ item_number
						+ "','"
						+ fileupload.getParameter("item_quality")
						+ "','"
						+ item_locus
						+ "','"
						+ fileupload.getParameter("transport")
						+ "','"
						+ postage_mail
						+ "','"
						+ postage_express
						+ "','"
						+ postage_ems
						+ "','"
						+ fileupload.getParameter("item_type")
						+ "','"
						+ timestamp
						+ "','"
						+ expiration
						+ "','',"
						+ timestamp
						+ ",'0','0.00','0','"
						+ aid
						+ "','"
						+ displayorder
						+ "','" + item_costprice + "')");
		if (allowpostattach) {
			String attamessage = uploadAttachment(request, pid, tid, fid,
					message, filelist, forum.getDisablewatermark(), fileupload);
			if (!attamessage.equals("")) {
				dataBaseService.runQuery("delete from jrun_posts where pid="
						+ pid, true);
				dataBaseService.runQuery("delete from jrun_threads where tid="
						+ tid, true);
				request.setAttribute("resultInfo", attamessage);
				return mapping.findForward("showMessage");
			}
		}
		if (modnewthreads) {
			dataBaseService.runQuery(
					"UPDATE jrun_forums SET todayposts=todayposts+1 WHERE fid='"
							+ fid + "'", true);
			if (allowuseblog == 1 && "yes".equals(isblog) && blog > 0) {
				request.setAttribute("successInfo", getMessage(request,
						"post_newthread_mod_blog_succeed"));
				request.setAttribute("requestPath", "blog.jsp?uid="
						+ member.getUid());
				return mapping.findForward("showMessage");
			} else {
				request.setAttribute("successInfo", getMessage(request,
						"post_newthread_mod_succeed"));
				request.setAttribute("requestPath", "forumdisplay.jsp?fid="
						+ fid);
				return mapping.findForward("showMessage");
			}
		} else {
			Map creditspolicys = dataParse.characterParse(settings
					.get("creditspolicy"), false);
			if (postcredits == null || postcredits.size() <= 0) {
				postcredits = (Map<Integer, Integer>) creditspolicys
						.get("post");
			}
			if (digest > 0) {
				Map<Integer, Integer> digestcredits = dataParse.characterParse(
						forumfield.getDigestcredits(), false);
				if (digestcredits == null || digestcredits.size() <= 0) {
					digestcredits = (Map<Integer, Integer>) creditspolicys
							.get("digest");
				}
				if (digest > 0) {
					Set<Integer> keys = digestcredits.keySet();
					for (Integer key : keys) {
						postcredits.put(key,
								(postcredits.get(key) != null ? postcredits
										.get(key) : 0)
										+ digestcredits.get(key));
					}
				}
			}
			creditspolicys = null;
			if (member != null) {
				Common.updatepostcredits("+", jsprun_uid, postcredits,
						timestamp);
				Common.updatepostcredits(jsprun_uid, settings
						.get("creditsformula"));
				Common.updateMember(session, jsprun_uid);
			}
			postcredits = null;
			String lastpost = tid
					+ "\t"
					+ Common.cutstr(Common.addslashes(subject.replaceAll("\t",
							" ")), 40, null) + "\t" + timestamp + "\t" + author;
			dataBaseService
					.runQuery(
							"UPDATE jrun_forums SET lastpost='"
									+ lastpost
									+ "', threads=threads+1, posts=posts+1, todayposts=todayposts+1 WHERE fid='"
									+ fid + "'", true);
			if (forum.getType().equals("sub")) {
				dataBaseService.runQuery("UPDATE jrun_forums SET lastpost='"
						+ lastpost + "' WHERE fid=" + forum.getFup(), true);
			}
			if (allowuseblog == 1 && "yes".equals(isblog) && blog > 0) {
				if (Common
						.isshowsuccess(session, "post_newthread_blog_succeed")) {
					Common.requestforward(response, "blog.jsp?tid=" + tid);
					return null;
				} else {
					request.setAttribute("successInfo", getMessage(request,
							"post_newthread_blog_succeed"));
					request.setAttribute("requestPath", "blog.jsp?tid=" + tid);
					return mapping.findForward("showMessage");
				}
			} else {
				int frombbs = Common
						.toDigit(fileupload.getParameter("frombbs"));
				if (Common.isshowsuccess(session, "post_newthread_succeed")) {
					Common.requestforward(response, "viewthread.jsp?tid=" + tid
							+ (page > 0 ? "&page=" + page : "")
							+ (frombbs > 0 ? "&frombbs=" + frombbs : ""));
					return null;
				}
				request.setAttribute("successInfo", getMessage(request,
						"post_newthread_succeed", fid + ""));
				request.setAttribute("requestPath", "viewthread.jsp?tid=" + tid
						+ (page > 0 ? "&page=" + page : "")
						+ (frombbs > 0 ? "&frombbs=" + frombbs : ""));
				return mapping.findForward("showMessage");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward showSmilies(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Program", "no-cache");
		response.setDateHeader("Expirse", 0);
		Map<String, String> settings = ForumInit.settings;
		String typeid = request.getParameter("typeid");
		String currpage = request.getParameter("page");
		int page = 1;
		if (typeid == null) {
			String smile = CookieUtil.getCookie(request, "smile", true,
					settings);
			if (smile != null) {
				String[] smiles = smile.split("D");
				typeid = smiles[0];
				page = Math.max(Common.intval(smiles[1]), 1);
			}
		}
		if (currpage != null) {
			page = Math.max(Common.intval(currpage), 1);
		}
		if (typeid == null) {
			Map<String, String> styles = (Map<String, String>) request
					.getAttribute("styles");
			typeid = styles.get("STYPEID");
		}
		Map<String, Map<String, String>> smileytypeMap = new TreeMap<String, Map<String, String>>();
		List<Map<String, String>> smilies = new ArrayList<Map<String, String>>();
		Map<String, Map<String, String>> smilies_displays = new TreeMap<String, Map<String, String>>();
		Map<String, String> smileytypes = (Map<String, String>) request
				.getAttribute("smileytypes");
		Map<String, String> smilies_display = (Map<String, String>) request
				.getAttribute("smilies_display");
		if (smileytypes != null && smileytypes.size() > 0) {
			Set<String> typeids = smileytypes.keySet();
			for (String obj : typeids) {
				smileytypeMap.put(obj, dataParse.characterParse(smileytypes
						.get(obj), true));
			}
			smilies_displays = dataParse.characterParse(smilies_display
					.get(typeid), true);
		}
		if (smilies_displays != null) {
			Set<String> keys = smilies_displays.keySet();
			for (String key : keys) {
				smilies.add(smilies_displays.get(key));
			}
		}
		smileytypes = null;
		smilies_display = null;
		int smcols = Integer.valueOf(settings.get("smcols"));
		int smrows = Integer.valueOf(settings.get("smrows"));
		int spp = smcols * smrows;
		int num = smilies.size();
		Map<String, Integer> multiInfo = Common.getMultiInfo(num, spp, page);
		page = multiInfo.get("curpage");
		int start_limit = multiInfo.get("start_limit");
		int end_limit = start_limit + spp;
		if (end_limit >= num) {
			end_limit = num;
		}
		CookieUtil.setCookie(request, response, "smile", typeid + "D" + page,
				31536000, true, settings);
		String ajaxtarget = request.getParameter("ajaxtarget");
		Map<String, Object> multi = Common.multi(num, spp, page,
				"post.jsp?action=smilies&stypeid=" + typeid + "&inajax=1", 0,
				5, false, true, ajaxtarget);
		request.setAttribute("multi", multi);
		request
				.setAttribute("smilies", smilies
						.subList(start_limit, end_limit));
		request.setAttribute("typeid", typeid);
		request.setAttribute("editorid", "posteditor");
		request.setAttribute("smsize", spp);
		request.setAttribute("smileytypes", smileytypeMap);
		request.setAttribute("smileytypesize", smileytypeMap.size());
		return mapping.findForward("toPost_smilies");
	}

	@SuppressWarnings("unchecked")
	public ActionForward threadtypes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String typeid = request.getParameter("typeid");
		Common.include(request, response, servlet,
				"/forumdata/cache/threadtype_" + typeid + ".jsp", null);
		String tradetype = request.getParameter("tradetype");
		String table = "";
		String id = "";
		String field = "";
		if (tradetype == null) {
			short fid = (short) Common.toDigit(request.getParameter("fid"));
			if (fid > 0) {
				Forumfields forumfield = forumfieldService.findById(fid);
				Map threadtypes = dataParse.characterParse(forumfield
						.getThreadtypes(), false);
				request.setAttribute("threadtypes", threadtypes);
			}
			String tid = request.getParameter("tid");
			if (tid != null) {
				table = "jrun_typeoptionvars";
				id = tid;
				field = "tid";
			}
		} else {
			String pid = request.getParameter("pid");
			if (pid != null) {
				table = "jrun_tradeoptionvars";
				id = pid;
				field = "pid";
			}
		}
		Map<String, String> threadtype = (Map<String, String>) request
				.getAttribute("threadtype");
		Map<Integer, Map<String, String>> dtype = null;
		if (threadtype != null) {
			dtype = dataParse.characterParse(threadtype.get("dtype"), true);
		}
		request.setAttribute("selecttypeid", Common.toDigit(request
				.getParameter("typeid")));
		request.setAttribute("optionlist", dtype);
		if (!Common.empty(id) && dtype != null && dtype.size() > 0) {
			Map optionlist = new TreeMap();
			Map<String, String> optiondata = new HashMap<String, String>();
			List<Map<String, String>> optionvalues = dataBaseService
					.executeQuery("SELECT optionid,value FROM " + table
							+ " WHERE " + field + "='" + id + "'");
			for (Map<String, String> option : optionvalues) {
				optiondata.put(option.get("optionid"), option.get("value"));
			}
			Set<Integer> dtypes = dtype.keySet();
			for (Integer optionid : dtypes) {
				Map<String, String> option = dtype.get(optionid);
				Map options = new HashMap();
				options.putAll(option);
				if (option.get("type").equals("radio")) {
					Map radiomap = new HashMap();
					radiomap.put(Common.toDigit(optiondata.get(optionid + "")),
							"checked=\"checked\"");
					options.put("value", radiomap);
				} else if (option.get("type").equals("select")) {
					Map selectmap = new HashMap();
					selectmap.put(
							Common.toDigit(optiondata.get(optionid + "")),
							"selected=\"selected\"");
					options.put("value", selectmap);
				} else if (option.get("type").equals("checkbox")) {
					Map checkboxmap = new HashMap();
					String choosevalue = optiondata.get(optionid + "");
					choosevalue = choosevalue == null ? "" : choosevalue;
					String[] values = choosevalue.split("\t");
					for (String value : values) {
						checkboxmap.put(Common.toDigit(value),
								"checked=\"checked\"");
					}
					options.put("value", checkboxmap);
				} else {
					options.put("value", optiondata.get(optionid + ""));
				}
				if (optiondata.get(optionid + "") == null) {
					dataBaseService.runQuery("INSERT INTO " + table
							+ " (typeid, " + field + ", optionid) VALUES ('"
							+ typeid + "', '" + id + "', '" + optionid + "')");
				}
				optionlist.put(optionid, options);
			}
			request.setAttribute("optionlist", optionlist);
		}
		String operate = request.getParameter("operate");
		String forward = null;
		forward = Common.intval(operate) > 0 ? "search_typeoption"
				: "post_typeoption";
		return mapping.findForward(forward);
	}

	@SuppressWarnings("unchecked")
	public ActionForward reply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int tid = Common.toDigit(request.getParameter("tid"));
		short fid = (short) Common.toDigit(request.getParameter("fid"));
		String dateformat = (String) session.getAttribute("dateformat");
		String timeformat = (String) session.getAttribute("timeformat");
		String timeoffset = (String) session.getAttribute("timeoffset");
       
		Forums forum = forumService.findById(fid);
		Threads thread = threadService.findByTid(tid);
		if (thread == null) {
			request.setAttribute("errorInfo", getMessage(request,
					"thread_nonexistence"));
			return mapping.findForward("showMessage");
		}
		if (forum == null) {
			request.setAttribute("errorInfo", getMessage(request,
					"forum_nonexistence"));
			return mapping.findForward("showMessage");
		}
		Forumfields fileds = forumfieldService.findById(fid);
		if ((forum.getSimple() & 1) > 0 || fileds.getRedirect().length() > 0) {
			request.setAttribute("resultInfo", getMessage(request,
					"forum_disablepost"));
			return mapping.findForward("showMessage");
		}
		File tempfile = isMakeDir();
		ModuleConfig ac = (ModuleConfig) request
				.getAttribute(Globals.MODULE_KEY);
		FileUploadUtil fileupload = new FileUploadUtil(tempfile, memeoryBlock,
				ac);
		try {
			fileupload.parse(request, JspRunConfig.CHARSET);
		} catch (IllegalStateException e) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_attachment_toobig"));
			return mapping.findForward("showMessage");
		}
		String extra = fileupload.getParameter("extra");
		request.setAttribute("extra", extra);
		Map<String, String> usergroups = (Map<String, String>) request
				.getAttribute("usergroups");
		Members member = (Members) session.getAttribute("user");
		short groupid = (Short) session.getAttribute("jsprun_groupid");
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		int allowreply = Integer.valueOf(usergroups.get("allowreply"));
		String replyperm = fileds.getReplyperm();
		List<Map<String, String>> accesslist = dataBaseService
				.executeQuery("select allowreply,allowpostattach from jrun_access where uid='"
						+ jsprun_uid + "' and fid='" + fid + "'");
		Map<String, String> access = accesslist.size() > 0 ? accesslist.get(0)
				: null;
		if (member == null
				&& !((replyperm.equals("") && allowreply > 0) || (!replyperm
						.equals("") && Common.forumperm(replyperm, groupid,
						member != null ? member.getExtgroupids() : "")))) {
			request.setAttribute("show_message", getMessage(request,
					"group_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("nopermission");
		} else if (access == null || Common.empty(access.get("allowreply"))) {
			if (replyperm.equals("") && allowreply == 0) {
				request.setAttribute("show_message", getMessage(request,
						"group_nopermission", usergroups.get("grouptitle")));
				return mapping.findForward("nopermission");
			} else if (!replyperm.equals("")
					&& !Common.forumperm(replyperm, groupid,
							member != null ? member.getExtgroupids() : "")) {
				request.setAttribute("show_message", getMessage(request,
						"post_forum_newreply_nopermission"));
				return mapping.findForward("nopermission");
			}
		}
		if (thread.getPrice() > 0 && thread.getSpecial() == 0
				&& jsprun_uid == 0) {
			request.setAttribute("errorInfo", getMessage(request,
					"group_nopermission", usergroups.get("grouptitle")));
			return mapping.findForward("showMessage");
		}
		Map<String, String> settings = ForumInit.settings;
		String addtrade = fileupload.getParameter("addtrade");
		if (thread.getSpecial() == 2) {
			// String tradenum = dataBaseService.executeQuery("SELECT count(*)
			// count FROM jrun_trades WHERE tid='"+tid+"'").get(0).get("count");
			// if((Common.empty(addtrade)||thread.getAuthorid()!=jsprun_uid)&&Common.toDigit(tradenum)==0){
			// request.setAttribute("errorInfo", getMessage(request,
			// "trade_newreply_nopermission"));
			// return mapping.findForward("showMessage");
			// }
		}
		boolean modertar = Common.ismoderator(thread.getFid(), member);
		if ((thread.getClosed() > 0 || checkautoclose(thread, forum))
				&& !modertar) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_thread_closed"));
			return mapping.findForward("showMessage");
		}
		String subjects = fileupload.getParameter("subject");
		String messages = fileupload.getParameter("message");

		int timestamp = (Integer) (request.getAttribute("timestamp"));

		// 新加的
		String addComment = fileupload.getParameter("addcomment");
		// 专题贴的时间判断
		if (thread.getSpecial() == 2) {
			String getPermsql = "select * from jrun_special where tid=" + tid;
			List<Map<String, String>> list = dataBaseService
					.executeQuery(getPermsql);
			if (list != null) {
				Map<String, String> maplist = list.get(0);
				if (Integer.parseInt(maplist.get("endtime")) < timestamp) {
					request.setAttribute("errorInfo",
							"对不起，本专题贴已经结束。不能再发布评论或回复了");
					return mapping.findForward("showMessage");
				}
			}
		}

		String previewpost = fileupload.getParameter("previewpost");
		if (previewpost != null) {
			request.setAttribute("message_preview", messages);
		}
		double ppp = member != null && member.getPpp() > 0 ? member.getPpp()
				: Common.toDigit(settings.get("postperpage"));
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		if (request.getParameter("replysubmit") == null || previewpost != null) {
			String navigation = fileupload.getParameter("navigation");
			String navtitle = fileupload.getParameter("navtitle");
			String forumName = forum.getName();
			navigation = "&raquo; <a href=\"forumdisplay.jsp?fid=" + fid
					+ "\">" + forumName + "</a> "
					+ (navigation != null ? navigation : "")
					+ "&raquo;	<a href=\"viewthread.jsp?tid=" + thread.getTid()
					+ "\">" + thread.getSubject() + "</a>";
			navtitle = thread.getSubject() + " - "
					+ (navtitle != null ? navtitle : forumName + " - ");
			if (forum.getType().equals("sub")) {
				Map<String, String> fup = dataBaseService.executeQuery(
						"SELECT name FROM jrun_forums WHERE fid="
								+ forum.getFup() + " limit 1").get(0);
				String fupforumName = fup.get("name");
				navigation = "&raquo; <a href=\"forumdisplay.jsp?fid="
						+ forum.getFup() + "\">" + fupforumName + "</a> "
						+ navigation;
				navtitle = navtitle + fupforumName + " - ";
			}
			request.setAttribute("navigation", navigation);
			request.setAttribute("navtitle", Common.strip_tags(navtitle));
			request.setAttribute("thread", thread);
			if (thread.getSpecial() == 5) {
				List<Map<String, String>> firststandlist = dataBaseService
						.executeQuery("SELECT stand FROM jrun_debateposts WHERE tid='"
								+ tid
								+ "' AND uid='"
								+ jsprun_uid
								+ "' AND stand<>'0' ORDER BY dateline LIMIT 1");
				if (firststandlist != null && firststandlist.size() > 0) {
					request.setAttribute("stand", firststandlist.get(0).get(
							"stand"));
				}
				firststandlist = null;
			}
			String repquote = fileupload.getParameter("repquote");
			String barkurl = (String) session.getAttribute("boardurl");
			int bannedmessages = Common.toDigit(settings.get("bannedmessages"));
			Map<String, String> bbcodes = (Map<String, String>) request
					.getAttribute("bbcodes");
			Map<String, Map<String, String>> bbcodelist = dataParse
					.characterParse(bbcodes.get("bbcodes"), false);
			String sqlgetComment = "select * from jrun_comment where threadId="
					+ tid;
			List<Map<String, String>> list = dataBaseService
					.executeQuery(sqlgetComment);
			if (list != null) {
				request.setAttribute("mcomment", list);
			} else {
				request.setAttribute("mcomment", null);
			}

			if (repquote != null
					&& (thread.getPrice() <= 0 || thread.getSpecial() > 0)) {
				Posts post = postService.getPostsById(convertInt(repquote));
				if (post == null || post.getTid() != tid) {
					request.setAttribute("errorInfo", getMessage(request,
							"undefined_action_return"));
					return mapping.findForward("showMessage");
				}
				String message = post.getMessage();
				String author = null;
				if (post.getAnonymous() == 1) {
					author = "[i]Anonymous[/i]";
				} else if (post.getAuthor().equals("")) {
					author = "[i]Guest[/i] from" + post.getUseip();
				} else {
					author = "[i]" + post.getAuthor() + "[/i]";
				}
				if (bannedmessages > 0 && post.getAuthorid() > 0) {
					List<Map<String, String>> query = dataBaseService
							.executeQuery("SELECT groupid FROM jrun_members WHERE uid='"
									+ post.getAuthorid() + "'");
					int groupidu = query.size() > 0 ? Common.toDigit(query.get(
							0).get("groupid")) : 0;
					if (groupidu == 0 || groupidu == 4 || groupidu == 5) {
						message = getMessage(request, "post_banned");
					} else if (post.getStatus() > 0) {
						message = getMessage(request, "post_single_banned");
					}
				}
				String temp1 = getMessage(request, "post_edit_regexp1");
				String temp2 = getMessage(request, "post_edit_regexp2");
				if (message.indexOf(temp1) != -1) {
					int index = message.indexOf(temp1);
					int end = message.indexOf(temp2);
					String submessage = message.substring(index, end + 8);
					message = StringUtils.replace(message, submessage, "");
				}
				// message = jspcode.inset(message,bbcodelist).trim();
				SimpleDateFormat simpleDateFormat = Common.getSimpleDateFormat(
						dateformat + " " + timeformat, timeoffset);
				if (forum.getAllowhtml() == 1) {
					message = "[quote] "
							+ getMessage(request, "post_reply_quote", author,
									Common.gmdate(simpleDateFormat, post
											.getDateline())) + " <a href="
							+ barkurl + "redirect.jsp?goto=findpost&pid="
							+ repquote + "&ptid=" + tid
							+ " target=_blank><img src=" + barkurl
							+ "images/common/back.gif></a>" + message
							+ " [/quote]";
				} else {
					message = "[quote] "
							+ getMessage(request, "post_reply_quote", author,
									Common.gmdate(simpleDateFormat, post
											.getDateline())) + " [url="
							+ barkurl + "redirect.jsp?goto=findpost&pid="
							+ repquote + "&ptid=" + tid + "][img]" + barkurl
							+ "images/common/back.gif[/img][/url]" + message
							+ " [/quote]";
				}
				bbcodes = null;
				post.setMessage(message);
				request.setAttribute("message", message);
				request.setAttribute("post", post);
			} else {
				request.setAttribute("message", messages != null ? messages
						: "");
			}
			if (thread.getReplies() <= ppp) {
				List<Map<String, String>> postlist = dataBaseService
						.executeQuery("SELECT p.* "
								+ (bannedmessages > 0 ? ", m.groupid " : "")
								+ "FROM jrun_posts p "
								+ (bannedmessages > 0 ? "LEFT JOIN jrun_members m ON p.authorid=m.uid "
										: "")
								+ "WHERE p.tid='"
								+ tid
								+ "' AND p.invisible='0' "
								+ (thread.getPrice() > 0
										&& thread.getSpecial() == 0 ? "AND p.first = 0"
										: "") + " ORDER BY p.dateline DESC");
				if (postlist != null && postlist.size() > 0) {
					request.setAttribute("thread", thread);
					String smilies_parse = ((Map<String, String>) request
							.getAttribute("smilies_parse"))
							.get("smilies_parse");
					List<Map<String, String>> smilieslist = dataParse
							.characterParse(smilies_parse);
					int parnum = Common.toDigit(settings.get("maxsmilies"));
					for (Map<String, String> post : postlist) {
						String message = null;
						if (bannedmessages > 0
								&& (!Common.empty(post.get("authorid")) && (Common
										.empty(post.get("groupid"))
										|| "4".equals(post.get("groupid")) || "5"
										.equals(post.get("groupid"))))) {
							message = getMessage(request, "post_banned");
						} else if (Common.toDigit(post.get("status")) > 0) {
							message = getMessage(request, "post_single_banned");
						} else {
							message = post.get("message").replace("$",
									Common.SIGNSTRING);
							if (forum.getAllowhtml() <= 0
									&& Common.toDigit(post.get("htmlon")) <= 0) {
								message = Common.htmlspecialchars(message);
								if (forum.getJammer() == 1
										&& !(jsprun_uid + "").equals(post
												.get("authorid"))) {
									int counts = 0;
									while (message.indexOf("\n") != -1
											&& counts < 25) {
										message = StringUtils.replaceOnce(
												message, "\n", jammer());
										counts++;
									}
								}
							}
							Jspruncode jspruncode = new Jspruncode();
							if (forum.getAllowbbcode() > 0
									&& Common.intval(post.get("bbcodeoff")) <= 0) {
								message = jspruncode.parseCodeP(message,
										resources, locale, false);
								message = jspruncode.parseJsprunCode(message,
										resources, locale);
								message = jspruncode.parsetable(message);
								message = jspruncode.parsemedia(message, forum
										.getAllowmediacode() > 0);
								message = jspruncode.parseCustomCode(message,
										bbcodelist);
							}
							message = jspruncode.parseimg(message, forum
									.getAllowimgcode() > 0);
							if (forum.getAllowsmilies() > 0
									&& Common.intval(post.get("smileyoff")) <= 0) {
								message = relacesmile(message, parnum,
										smilieslist);
							}
							if (forum.getAllowbbcode() > 0
									&& Common.intval(post.get("bbcodeoff")) <= 0) {
								message = message
										.replaceAll(
												"(?s)\\s*\\[free\\][\n\r]*(.+?)[\n\r]*\\[/free\\]\\s*",
												"<div class=\"quote\"><h5>"
														+ getMessage(request,
																"jspruncode_free")
														+ ":</h5><blockquote>$1</blockquote></div>");
								message = message
										.replaceAll(
												"(?is)\\[hide=?\\d*\\](.+?)\\[/hide\\]",
												"[b]"
														+ getMessage(request,
																"post_hide_reply_hidden_wap")
														+ "[/b]");
							}
							message = message.replaceAll(
									"\\[attach\\]\\d+\\[/attach\\]", "");
							message = message.replaceAll("\\n", "<br/>");
							int count = jspruncode.getCodecount();
							List<String> codelist = jspruncode.getCodelist();
							for (int i = 0; i < count; i++) {
								String str = codelist.get(i);
								message = StringUtils.replace(message,
										"[\tJSPRUN_CODE_" + i + "\t]", str);
							}
							message = message.replace(Common.SIGNSTRING, "$");
						}
						post.put("message", message);
					}
					request.setAttribute("postlist", postlist);
				} else {
					request.setAttribute("postlist", null);
				}
			} else {
				request.setAttribute("postlist", null);
			}
			if (!Common.empty(addtrade) && thread.getSpecial() == 2
					&& usergroups.get("allowposttrade").equals("1")
					&& thread.getAuthorid() == jsprun_uid) {
				SimpleDateFormat format = Common.getSimpleDateFormat(
						"yyyy-MM-dd", timeoffset);
				Map<String, String> trade = new HashMap<String, String>();
				Calendar calendar = Common.getCalendar(timeoffset);
				calendar.add(Calendar.MONTH, 1);
				String expiration_month = format.format(calendar
						.getTimeInMillis());
				calendar.add(Calendar.MONTH, -1);
				calendar.add(Calendar.DATE, 7);
				request.setAttribute("expiration_7days", format.format(calendar
						.getTimeInMillis()));
				calendar.add(Calendar.DATE, 7);
				request.setAttribute("expiration_14days", format
						.format(calendar.getTimeInMillis()));
				request.setAttribute("expiration_month", expiration_month);
				calendar.add(Calendar.DATE, -14);
				calendar.add(Calendar.MONTH, 3);
				request.setAttribute("expiration_3months", format
						.format(calendar.getTimeInMillis()));
				calendar.add(Calendar.MONTH, 3);
				request.setAttribute("expiration_halfyear", format
						.format(calendar.getTimeInMillis()));
				calendar.add(Calendar.MONTH, -6);
				calendar.add(Calendar.YEAR, 1);
				request.setAttribute("expiration_year", format.format(calendar
						.getTimeInMillis()));
				format = null;
				trade.put("amount", "1");
				trade.put("transport", "2");
				trade.put("expiration", expiration_month);
				request.setAttribute("trade", trade);
				trade = null;
			}
			String readperm = fileupload.getParameter("readperm");
			String price = fileupload.getParameter("price");
			request.setAttribute("subject", subjects != null ? subjects.trim()
					: "");
			request.setAttribute("readperm", readperm != null ? readperm : 0);
			request.setAttribute("price", price != null ? price : 0);
			request.setAttribute("fid", fid);
			request.setAttribute("tid", tid);
			this.common(request, response, settings, usergroups, fileds, forum,
					thread.getSpecial(), access, fileupload);
			if (thread.getSpecial() == 2 && !Common.empty(addtrade)
					&& thread.getAuthorid() == jsprun_uid) {
				String tradetypes = settings.get("tradetypes");
				if (tradetypes != null && tradetypes.length() > 0) {
					Map<String, String> forumtradetypes = dataParse
							.characterParse(fileds.getTradetypes(), true);
					Map<String, String> tradetypesmap = dataParse
							.characterParse(tradetypes, true);
					StringBuffer tradetypeselect = new StringBuffer(
							"<select name=\"tradetypeid\" onchange=\"ajaxget(\'post.jsp?action=threadtypes&tradetype=yes&typeid=\'+this.options[this.selectedIndex].value+'&rand='+Math.random(), \'threadtypes\', \'threadtypeswait\')\"><option value=\"0\">&nbsp;</option>");
					Set<String> typeids = tradetypesmap.keySet();
					for (String typeid : typeids) {
						if (forumtradetypes.size() == 0
								|| forumtradetypes.containsValue(typeid)) {
							tradetypeselect.append("<option value=\""
									+ typeid
									+ "\">"
									+ Common.strip_tags(tradetypesmap
											.get(typeid)) + "</option>");
						}
					}
					tradetypeselect
							.append("</select><span id=\"threadtypeswait\"></span>");
					request.setAttribute("tradetypeselect", tradetypeselect);
				}
				return null;
			} else {
				return mapping.findForward("toPost_newreply");
			}
		} else {
			if (thread.getIsComment() == 1
					&& ("".equals(addComment) || addComment == null)) {
				request.setAttribute("errorInfo", "对不起，你还没有对本贴进行评论。");
				return mapping.findForward("showMessage");
			}
			FileItem tradfile = fileupload.getFileItem("tradeattach");
			if (tradfile != null && tradfile.getSize() > 0) {
				List tradfilelist = new ArrayList();
				tradfilelist.add(tradfile);
				tradfilelist.add("1");
				String mess = checkAttachment(request, tradfilelist, fileds);
				if (mess != null) {
					request.setAttribute("errorInfo", mess);
					return mapping.findForward("showMessage");
				}
			}
			String checkflood = checkflood(request, jsprun_uid, timestamp,
					member != null ? member.getLastpost() : 0, Integer
							.valueOf(settings.get("floodctrl")), Integer
							.valueOf(usergroups.get("disablepostctrl")),
					Integer.valueOf(usergroups.get("maxpostsperhour")));
			if (checkflood != null) {
				request.setAttribute("resultInfo", checkflood);
				return mapping.findForward("showMessage");
			}
			boolean modnewreplies = false;
			if (Common.periodscheck(settings.get("postmodperiods"), Byte
					.valueOf(usergroups.get("disableperiodctrl")), timestamp,
					settings.get("timeoffset"), resources, locale) != null) {
				modnewreplies = true;
			} else {
				int allowdirectpost = Integer.valueOf(usergroups
						.get("allowdirectpost"));
				boolean censormod = censormod(subjects + "\t" + messages,
						request);
				modnewreplies = (allowdirectpost == 0 || allowdirectpost == 2)
						&& forum.getModnewposts() == 2 || censormod;
			}
			Map seccodedata = dataParse.characterParse(settings
					.get("seccodedata"), false);
			int minposts = Common.toDigit(String.valueOf(seccodedata
					.get("minposts")));
			seccodedata = null;
			int seccodestatus = Common.range(Common.intval(settings
					.get("seccodestatus")), 255, 0);
			boolean seccodecheck = (seccodestatus & 4) > 0
					&& (minposts <= 0 || member.getPosts() < minposts);
			Map secqaa = dataParse
					.characterParse(settings.get("secqaa"), false);
			minposts = Common.toDigit(String.valueOf(secqaa.get("minposts")));
			int secqaastatus = (Integer) secqaa.get("status");
			secqaa = null;
			boolean secqaacheck = (secqaastatus & 2) > 0
					&& (minposts <= 0 || member.getPosts() < minposts);
			request.setAttribute("secqaacheck", secqaacheck);
			this.common(request, response, settings, usergroups, fileds, forum,
					thread.getSpecial(), access, fileupload);
			String message = fileupload.getParameter("message");
			String subject = fileupload.getParameter("subject");
			Byte usesig = (byte) Common.range(Common.intval(fileupload
					.getParameter("usesig")), 1, 0);
			String topicsubmit = fileupload.getParameter("replysubmit");
			String seccodeverify = fileupload.getParameter("seccodeverify");
			String secanswer = fileupload.getParameter("secanswer");
			if (topicsubmit != null
					&& ((seccodeverify == null && seccodecheck) || (secanswer == null && secqaacheck))) {
				request.setAttribute("subject", subject);
				request.setAttribute("message", message);
				request.setAttribute("usesig", usesig);
				request.setAttribute("stand", fileupload.getParameter("stand"));
				request.setAttribute("path", "post.jsp?action=reply&amp;fid="
						+ fid + "&amp;tid=" + tid
						+ "&amp;extra=page%3D1&amp;replysubmit=yes");
				return mapping.findForward("toPost_seccode");
			}
			if (seccodecheck) {
				if (!seccodeverify.equals(request.getSession().getAttribute(
						"rand"))) {
					request.setAttribute("errorInfo", getMessage(request,
							"submit_seccode_invalid"));
					return mapping.findForward("showMessage");
				}
			}
			if (secqaacheck) {
				if (!secanswer.trim().equals(
						request.getSession().getAttribute("answer").toString())) {
					request.setAttribute("errorInfo", getMessage(request,
							"submit_secqaa_invalid"));
					return mapping.findForward("showMessage");
				}
			}
			String trade = fileupload.getParameter("trade");
			short tradetypeid = (short) Common.range(Common.intval(fileupload
					.getParameter("tradetypeid")), 32767, 0);
			Map tradetypes = dataParse.characterParse(fileds.getTradetypes(),
					false);
			Map<Integer, String> optiondata = new TreeMap<Integer, String>();
			if (trade != null && trade.equals("yes")
					&& thread.getSpecial() == 2
					&& usergroups.get("allowposttrade").equals("1")) {
				String item_prices = fileupload.getParameter("item_price");
				if (item_prices != null
						&& item_prices.matches("^-?\\d+\\.?\\d*$")) {
					item_prices = item_prices.length() > 9 ? "999999.99"
							: item_prices;
				} else {
					item_prices = "0";
				}
				double item_price = Double.valueOf(item_prices);
				String item_name = fileupload.getParameter("item_name").trim();
				float maxtradeprice = Float.valueOf(usergroups
						.get("maxtradeprice"));
				float mintradeprice = Float.valueOf(usergroups
						.get("mintradeprice"));
				int item_number = Common.toDigit(fileupload
						.getParameter("item_number"));
				if (item_name.length() == 0) {
					request.setAttribute("errorInfo", getMessage(request,
							"trade_please_name"));
					return mapping.findForward("showMessage");
				} else if (maxtradeprice > 0
						&& (mintradeprice > item_price || maxtradeprice < item_price)) {
					request.setAttribute("errorInfo", getMessage(request,
							"trade_price_between", mintradeprice + "",
							maxtradeprice + ""));
					return mapping.findForward("showMessage");
				} else if (maxtradeprice == 0 && mintradeprice > item_price) {
					request.setAttribute("errorInfo", getMessage(request,
							"trade_price_more_than", mintradeprice + ""));
					return mapping.findForward("showMessage");
				} else if (item_number < 1) {
					request.setAttribute("errorInfo", getMessage(request,
							"tread_please_number"));
					return mapping.findForward("showMessage");
				}
				if (tradetypes != null && tradetypeid > 0) {
					Common.include(request, response, servlet,
							"/forumdata/cache/threadtype_" + tradetypeid
									+ ".jsp", null);
					Map<String, String> threadtype = (Map<String, String>) request
							.getAttribute("threadtype");
					if (threadtype != null) {
						Map<Integer, Map<String, String>> dtype = dataParse
								.characterParse(threadtype.get("dtype"), true);
						threadtype = null;
						if (dtype != null && dtype.size() > 0
								&& !(forum.getAllowspecialonly() > 0)) {
							Set<Integer> keys = dtype.keySet();
							for (Integer optionid : keys) {
								Map<String, String> option = dtype
										.get(optionid);
								String title = option.get("title");
								String identifier = option.get("identifier");
								String type = option.get("type");
								int maxlength = Common.toDigit(option
										.get("maxlength"));
								int maxnum = Common.toDigit(option
										.get("maxnum"));
								int minnum = Common.toDigit(option
										.get("minnum"));
								byte required = (byte) Common.range(Common
										.intval(option.get("required")), 1, 0);
								byte unchangeable = (byte) Common.range(Common
										.intval(option.get("unchangeable")), 1,
										0);
								String value = fileupload
										.getParameter("typeoption["
												+ identifier + "]");
								if (required > 0
										&& (value == null || value.equals(""))) {
									request
											.setAttribute(
													"errorInfo",
													getMessage(
															request,
															"threadtype_required_invalid",
															title));
									return mapping.findForward("showMessage");
								} else if (value != null
										&& !value.equals("")
										&& (type.equals("number")
												&& !Common.isNum(value) || type
												.equals("email")
												&& !Common.isEmail(value))) {
									request
											.setAttribute(
													"errorInfo",
													getMessage(
															request,
															"threadtype_format_invalid",
															title));
									return mapping.findForward("showMessage");
								} else if (value != null && !value.equals("")
										&& maxlength > 0
										&& value.length() > maxlength) {
									request
											.setAttribute(
													"errorInfo",
													getMessage(
															request,
															"threadtype_toolong_invalid",
															title));
									return mapping.findForward("showMessage");
								} else if (value != null
										&& !value.equals("")
										&& ((option.get("maxnum") != null && Integer
												.valueOf(value) > Integer
												.valueOf(maxnum)) || (option
												.get("minnum") != null && Integer
												.valueOf(value) < Integer
												.valueOf(minnum)))) {
									request.setAttribute("errorInfo",
											getMessage(request,
													"threadtype_num_invalid",
													title));
									return mapping.findForward("showMessage");
								} else if (value != null && !value.equals("")
										&& unchangeable > 0) {
									request
											.setAttribute(
													"errorInfo",
													getMessage(
															request,
															"threadtype_unchangeable_invalid",
															title));
									return mapping.findForward("showMessage");
								}
								optiondata.put(optionid, value);
							}
						}
						dtype = null;
					}
				}
			}
			String onlineip = Common.get_onlineip(request);
			Posts post = new Posts();
			post.setAttachment(Byte.valueOf("0"));
			post.setAuthor(member != null ? member.getUsername() : getMessage(
					request, "anonymous"));
			post.setAuthorid(member != null ? member.getUid() : 0);
			post.setDateline(timestamp);
			post.setFid(fid);
			post.setFirst(Byte.valueOf("0"));
			post.setInvisible(Byte.valueOf("0"));
			post.setRate(Short.valueOf("0"));
			post.setRatetimes(Short.valueOf("0"));
			post.setStatus(Byte.valueOf("0"));
			post.setTid(tid);
			post.setUseip(onlineip);
			post.setGroup(this.getGroup(groupid));
			String parseurloff = fileupload.getParameter("parseurloff");
			String smileyoff = fileupload.getParameter("smileyoff");
			String bbcodeoff = fileupload.getParameter("bbcodeoff");
			String htmlon = fileupload.getParameter("htmlon");
			String isanonymous = fileupload.getParameter("isanonymous");
			if (parseurloff != null) {
				post.setParseurloff(Byte.valueOf("1"));
			} else {
				post.setParseurloff(Byte.valueOf("-1"));
			}
			if (smileyoff != null) {
				post.setSmileyoff(Byte.valueOf("1"));
			} else {
				post.setSmileyoff(Byte.valueOf("-1"));
			}
			if (bbcodeoff != null) {
				post.setBbcodeoff(Byte.valueOf("1"));
			} else {
				post.setBbcodeoff(Byte.valueOf("-1"));
			}
			if (htmlon != null) {
				post.setHtmlon(Byte.valueOf("1"));
			} else {
				post.setHtmlon(Byte.valueOf("-1"));
			}
			if (isanonymous != null) {
				post.setAnonymous(Byte.valueOf("1"));
			} else {
				post.setAnonymous(Byte.valueOf("-1"));
			}
			post.setUsesig(usesig);
			message = message == null ? "" : message.trim();
			if ((thread.getIsReply().toString().equals("0"))&& Common.empty(message)) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_sm_isnull"));
				return mapping.findForward("showMessage");
			}
			subject = subject == null ? "" : subject;
			subject = Common.htmlspecialchars(subject);
			String checkresult = Common.checkpost(subject, message, settings,
					usergroups, resources, locale);
			if (checkresult != null) {
				request.setAttribute("errorInfo", checkresult);
				return mapping.findForward("showMessage");
			}
			List filelist = getAttach(fileupload);
			checkresult = checkAttachment(request, filelist, fileds);
			if (checkresult != null) {
				request.setAttribute("errorInfo", checkresult);
				return mapping.findForward("showMessage");
			}
			Map<String, String> censor = (Map<String, String>) request
					.getAttribute("censor");
			String banned = censor.get("banned");
			if (!Common.empty(banned) && Common.matches(message, banned)) {
				request.setAttribute("errorInfo", getMessage(request,
						"word_banned"));
				return mapping.findForward("showMessage");
			}
			if (!Common.empty(banned) && Common.matches(subject, banned)) {
				request.setAttribute("errorInfo", getMessage(request,
						"word_banned"));
				return mapping.findForward("showMessage");
			}
			String filters = censor.get("filter");
			Map<String, Map<String, String>> filtermap = dataParse
					.characterParse(filters, false);
			if (filtermap != null) {
				Map<String, String> findsMap = filtermap.get("find");
				Map<String, String> replaceMap = filtermap.get("replace");
				if (findsMap != null && !findsMap.keySet().isEmpty()) {
					Iterator it = findsMap.keySet().iterator();
					while (it.hasNext()) {
						String id = it.next().toString();
						message = message.replaceAll(findsMap.get(id),
								replaceMap.get(id));
						subject = subject.replaceAll(findsMap.get(id),
								replaceMap.get(id));
					}
				}
			}
			if (modnewreplies) {
				post.setInvisible(Byte.valueOf("-2"));
			}
			post.setMessage(message);
			post.setSubject(subject);
			if ((!thread.getIsComment().equals(0)) && addComment != null
					&& !"".equals(addComment)) {
				String updateCommenet = "update jrun_comment set clickTimes=clickTimes+1 where id="
						+ addComment;
				dataBaseService.runQuery(updateCommenet);
			}
			if (!thread.getIsReply().toString().equals("0")) {
				int pid = postService.saveOrupdatePosts(post);

				String attamessage = uploadAttachment(request, pid, tid, fid,
						Common.addslashes(message), filelist, forum
								.getDisablewatermark(), fileupload);
				if (!attamessage.equals("")) {
					dataBaseService.runQuery(
							"delete from jrun_posts where pid=" + pid, true);
					request.setAttribute("resultInfo", attamessage);
					return mapping.findForward("showMessage");
				}
				filelist = null;

				dataBaseService
						.runQuery(
								"REPLACE INTO jrun_myposts (uid, tid, pid, position, dateline, special) VALUES ('"
										+ jsprun_uid
										+ "', '"
										+ tid
										+ "', '"
										+ pid
										+ "', '"
										+ (thread.getReplies() + 1)
										+ "', '"
										+ timestamp
										+ "', '"
										+ thread.getSpecial() + "')", true);

				dataBaseService.runQuery(
						"UPDATE jrun_forums SET todayposts=todayposts+1 WHERE fid='"
								+ fid + "'", true);
				if (modnewreplies) {
					request.setAttribute("successInfo", getMessage(request,
							"post_reply_mod_succeed"));
					request.setAttribute("requestPath", "forumdisplay.jsp?fid="
							+ fid);
					return mapping.findForward("showMessage");
				} else {
					if (member != null) {
						Map<Integer, Integer> postcredits = dataParse
								.characterParse(fileds.getReplycredits(), false);
						if (postcredits == null || postcredits.size() <= 0) {
							Map creditspolicys = dataParse.characterParse(
									settings.get("creditspolicy"), false);
							postcredits = (Map<Integer, Integer>) creditspolicys
									.get("reply");
							creditspolicys = null;
						}
						if (member != null) {
							Common.updatepostcredits("+", jsprun_uid,
									postcredits, timestamp);
							Common.updatepostcredits(jsprun_uid, settings
									.get("creditsformula"));
							Common.updateMember(session, jsprun_uid);
						}
						postcredits = null;
					}
					String author = isanonymous == null ? (member != null ? member
							.getUsername()
							: getMessage(request, "anonymous"))
							: "";
					String lastpost = tid
							+ "\t"
							+ Common.cutstr(Common.addslashes(thread
									.getSubject().replaceAll("\t", " ")), 40,
									null) + "\t" + timestamp + "\t" + author;
					dataBaseService.runQuery(
							"update jrun_threads set lastpost=" + timestamp
									+ ",lastposter='" + author
									+ "',replies=replies+1 where tid='" + tid
									+ "'", true);
					dataBaseService
							.runQuery("update jrun_forums set lastpost='"
									+ lastpost + "',posts=posts+1 where fid='"
									+ fid + "'", true);
					if (forum.getType().equals("sub")) {
						dataBaseService.runQuery(
								"update jrun_forums set lastpost='" + lastpost
										+ "',posts=posts+1 where fid='"
										+ forum.getFup() + "'", true);
					}
				}
				int pages = 1;
				int replaycount = thread.getSpecial() > 0 ? thread.getReplies() + 1
						: thread.getReplies() + 2;
				pages = (int) Math.ceil(replaycount / ppp);
				if (fileupload.getParameter("isblog") != null) {
					if (Common
							.isshowsuccess(session, "post_reply_blog_succeed")) {
						Common.requestforward(response, "blog.jsp?tid=" + tid
								+ "&page=" + pages);
						return null;
					} else {
						request.setAttribute("successInfo", getMessage(request,
								"post_reply_blog_succeed"));
						request.setAttribute("requestPath", "blog.jsp?tid="
								+ tid + "&page=" + pages);
						return mapping.findForward("showMessage");
					}
				} else if (Common.isshowsuccess(session, "post_reply_succeed")) {
					Common.requestforward(response, "viewthread.jsp?tid=" + tid
							+ "&page=" + pages + "&extra=" + extra + "#pid"
							+ pid);
					return null;
				} else {
					request.setAttribute("successInfo", getMessage(request,
							"post_reply_succeed", fid + ""));
					request.setAttribute("requestPath", "viewthread.jsp?tid="
							+ tid + "&page=" + pages + "&extra=" + extra
							+ "#pid" + pid);
					if (trade != null && trade.equals("yes")
							&& thread.getSpecial() == 2
							&& usergroups.get("allowposttrade").equals("1")) {
						request.setAttribute("successInfo", getMessage(request,
								"trade_add_succeed", fid + "", tid + ""));
					}
					return mapping.findForward("showMessage");
				}
			} else {
				Common.requestforward(response, "viewthread.jsp?tid=" + tid);
				return null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward toedit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List<String> faceimg = new ArrayList<String>();
		for (int i = 0; i < 55; i++) {
			faceimg.add(i + ".gif");
		}
		request.setAttribute("faceimg", faceimg);
		HttpSession session = request.getSession();
		int uid = (Integer) (session.getAttribute("jsprun_uid") == null ? 0
				: session.getAttribute("jsprun_uid"));
		if (uid == 0) {
			request.setAttribute("errorInfo", getMessage(request,
					"not_loggedin"));
			return mapping.findForward("showMessage");
		}
		int tid = Common.toDigit(request.getParameter("tid"));
		short fid = (short) Common.toDigit(request.getParameter("fid"));
		int pid = Common.toDigit(request.getParameter("pid"));
		request.setAttribute("page", request.getParameter("page"));
		Map<String, String> settings = ForumInit.settings;
		String edittimelimit = settings.get("edittimelimit");
		Members member = (Members) session.getAttribute("user");
		boolean ismoderator = Common.ismoderator(fid, member);
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		Posts post = postService.getPostsById(pid);
		if (post == null) {
			request.setAttribute("errorInfo", getMessage(request,
					"undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		Threads thread = threadService.findByTid(tid);
		if (thread == null) {
			request.setAttribute("errorInfo", getMessage(request,
					"thread_nonexistence"));
			return mapping.findForward("showMessage");
		}
		Forums forums = forumService.findById(fid);
		if (forums == null) {
			request.setAttribute("errorInfo", getMessage(request,
					"forum_nonexistence"));
			return mapping.findForward("showMessage");
		}
		Forumfields forumfield = forumfieldService.findById(fid);
		if ((forums.getSimple() & 1) > 0
				|| forumfield.getRedirect().length() > 0) {
			request.setAttribute("resultInfo", getMessage(request,
					"forum_disablepost"));
			return mapping.findForward("showMessage");
		}
		String navigation = request.getParameter("navigation");
		String navtitle = request.getParameter("navtitle");
		String forumName = forums.getName();
		navigation = "&raquo; <a href=\"forumdisplay.jsp?fid=" + fid + "\">"
				+ forumName + "</a> " + (navigation != null ? navigation : "")
				+ "&raquo;	<a href=\"viewthread.jsp?tid=" + thread.getTid()
				+ "\">" + thread.getSubject() + "</a>";
		navtitle = thread.getSubject() + " - "
				+ (navtitle != null ? navtitle : forumName + " - ");
		if (forums.getType().equals("sub")) {
			Map<String, String> fup = dataBaseService.executeQuery(
					"SELECT name FROM jrun_forums WHERE fid=" + forums.getFup()
							+ " limit 1").get(0);
			String fupforumName = fup.get("name");
			navigation = "&raquo; <a href=\"forumdisplay.jsp?fid="
					+ forums.getFup() + "\">" + fupforumName + "</a> "
					+ navigation;
			navtitle = navtitle + fupforumName + " - ";
		}
		request.setAttribute("navtitle", Common.strip_tags(navtitle));
		Map<String, String> usergroups = (Map<String, String>) request
				.getAttribute("usergroups");
		Members author = memberService.findMemberById(post.getAuthorid());
		byte jsprun_adminid = (Byte) session.getAttribute("jsprun_adminid");
		byte author_adminid = author == null ? 0 : author.getAdminid();
		if ((!ismoderator
				|| Common.intval(usergroups.get("alloweditpost")) <= 0 || (Common
				.in_array(new String[] { "1", "2", "3" }, author_adminid + "") && jsprun_adminid > author_adminid))
				&& !(forums.getAlloweditpost() > 0 && uid == post.getAuthorid())) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_edit_nopermission"));
			return mapping.findForward("showMessage");
		} else if (thread.getDigest() == -1 && post.getFirst() == 1) {
			request.setAttribute("errorInfo", getMessage(request,
					"special_noaction"));
			return mapping.findForward("showMessage");
		}

		String message = post.getMessage();
		MessageResources mr = getResources(request);
		Locale locale = getLocale(request);
		if (!ismoderator && uid == post.getAuthorid()) {
			boolean modnewthreads = false;
			boolean modnewreplies = false;
			if (Common.periodscheck(settings.get("postmodperiods"), Byte
					.valueOf(usergroups.get("disableperiodctrl")), timestamp,
					settings.get("timeoffset"), mr, locale) != null) {
				modnewthreads = true;
				modnewreplies = true;
			} else {
				int allowdirectpost = Integer.valueOf(usergroups
						.get("allowdirectpost"));
				modnewthreads = (allowdirectpost == 0 || allowdirectpost == 1)
						&& forums.getModnewposts() > 0;
				modnewreplies = (allowdirectpost == 0 || allowdirectpost == 2)
						&& forums.getModnewposts() == 2;
			}
			if (Common.intval(edittimelimit) > 0
					&& (timestamp - post.getDateline()) >= Integer
							.valueOf(edittimelimit) * 60) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_edit_timelimit", edittimelimit));
				return mapping.findForward("showMessage");
			} else if ((post.getFirst() == 1 && modnewthreads)
					|| (post.getFirst() == 0 && modnewreplies)) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_edit_moderate", edittimelimit));
				return mapping.findForward("showMessage");
			}
		}
		Locale newLocal = null;
		Map<Integer, Map<String, Object>> languages = dataParse.characterParse(
				settings.get("languages"), true);
		String lang = null;
		Set<Integer> lang_keys = languages.keySet();
		for (Integer key : lang_keys) {
			Map<String, Object> language = languages.get(key);
			if ((Integer) language.get("default") > 0
					&& (Integer) language.get("available") > 0) {
				lang = (String) language.get("language");
				break;
			}
		}
		if (lang != null) {
			String[] langs = lang.split("_");
			if (langs.length > 1) {
				newLocal = new Locale(langs[0], langs[1]);
			} else {
				newLocal = new Locale(langs[0]);
			}
		}
		String temp1 = null;
		String temp2 = null;
		if (newLocal != null) {
			temp1 = mr.getMessage(newLocal, "post_edit_regexp1");
			temp2 = mr.getMessage(newLocal, "post_edit_regexp2");
		}
		if (temp1 == null) {
			temp1 = getMessage(request, "post_edit_regexp1");
			temp2 = getMessage(request, "post_edit_regexp2");
		}
		int length = temp2.length();
		if (message.indexOf(temp1) != -1) {
			int index = message.indexOf(temp1);
			int end = message.indexOf(temp2);
			if (index > 1) {
				index = index - 1;
			}
			String submessage = message.substring(index, end + length);
			message = StringUtils.replace(message, submessage, "");
			post.setMessage(message);
		}
		String target = null;
		String aid = "0";
		byte special = thread.getSpecial();
		List<Map<String, String>> accesslist = dataBaseService
				.executeQuery("select allowpostattach from jrun_access where uid='"
						+ uid + "' and fid='" + fid + "'");
		Map<String, String> access = accesslist.size() > 0 ? accesslist.get(0)
				: null;
		this.common(request, response, settings, usergroups, forumfield,
				forums, special, access, null);
		if (post.getFirst() == 1) {
			String tagstatus = settings.get("tagstatus");
			if ("1".equals(tagstatus)) {
				List<Map<String, String>> tags = dataBaseService
						.executeQuery("SELECT tagname FROM jrun_threadtags WHERE tid='"
								+ tid + "'");
				if (tags != null && tags.size() > 0) {
					StringBuffer threadtag = new StringBuffer();
					for (Map<String, String> tag : tags) {
						threadtag.append(" " + tag.get("tagname"));
					}
					if (threadtag.length() > 0) {
						request.setAttribute("tags", threadtag.substring(1));
					}
				}
			}
			request.setAttribute("isfirstpost", true);
			request.setAttribute("timestamp", timestamp);
			if (special == 1) {
				Polls polls = pollService.findPollsBytid(tid);
				List<Polloptions> options = optionService
						.findPolloptionsBytid(tid);
				String sparetime = calcSparetime(polls.getExpiration(),
						timestamp);
				request.setAttribute("sparetime", sparetime);
				request.setAttribute("options", options);
				request.setAttribute("polls", polls);
				request.setAttribute("optionsize", options.size());
				options = null;
			} else if (special == 3) {
				String creditstrans = settings.get("creditstrans");
				String extcredits = settings.get("extcredits");
				String creditsunit = getCreditsName(extcredits).get("unit")[convertInt(creditstrans) - 1];
				String creditsName = getCreditsName(extcredits).get("name")[convertInt(creditstrans) - 1];
				request.setAttribute("creditsname", creditsName);
				request.setAttribute("creditsunit", creditsunit);
				int realprice = Math.abs(thread.getPrice());
				request.setAttribute("realprice", realprice);
			} else if (special == 5) {
				List<Map<String, String>> debatelist = dataBaseService
						.executeQuery("select * from jrun_debates as d where d.tid="
								+ tid);
				request.setAttribute("debates", debatelist.get(0));
				debatelist = null;
			} else if (special == 2) {
//                if(){
//                	request.setAttribute("errorInfo","你不是");
//			           return mapping.findForward("showMessage");
//                }
				List<Map<String,String>> mapspecial=dataBaseService.executeQuery("select starttime,endtime,perm from jrun_special where tid="+tid);
				if(mapspecial!=null&&mapspecial.size()>0){
					request.setAttribute("starttime",Integer.parseInt(mapspecial.get(0).get("starttime")));
					request.setAttribute("endtime", Integer.parseInt(mapspecial.get(0).get("endtime")));
					request.setAttribute("perm", mapspecial.get(0).get("perm"));
				}
				List<Map<String,String>> perlist=dataBaseService.executeQuery("select account from jrun_permlist where tid="+tid);
				if(perlist!=null&&perlist.size()>0){
					String username="";
					for(Map<String,String> pl:perlist){
						username=pl.get("account")+","+username;
					}
					request.setAttribute("plist", "".equals(username)?"":username.subSequence(0,username.length()-1));
				}
				String messages = post.getMessage().trim();
				String tmp[] = messages.split("\t\t\t");
				post.setMessage(tmp[0]);
				request.setAttribute("aboutcounter", tmp.length > 1 ? tmp[1]
						: "");
				request.setAttribute("special", 2);
				target = "toPost_editpost_trade";
			} else if (special == 4) {
				String activitytype = settings.get("activitytype");
				request.setAttribute("activitytypelist", activitytype != null
						&& !activitytype.trim().equals("") ? activitytype
						.trim().split("\n") : null);
				List<Map<String, String>> activitslist = dataBaseService
						.executeQuery("select * from jrun_activities as a where a.tid="
								+ tid);
				request.setAttribute("activity", activitslist.get(0));
				activitslist = null;
				target = "toPost_editpost_activity";
			}
		} else {
			request.setAttribute("isfirstpost", false);
			List<Map<String, String>> trades = dataBaseService
					.executeQuery("select t.*,a.isimage,a.attachment,a.thumb,a.remote from jrun_trades t LEFT JOIN jrun_attachments as a ON t.aid=a.aid where t.pid = "
							+ post.getPid() + " and t.tid=" + thread.getTid());
			if (trades.size() > 0) {
				Map<String, String> trade = trades.get(0);
				aid = trade.get("aid");
				Map<String, String> ftpmap = dataParse.characterParse(settings
						.get("ftp"), false);
				String ftpurl = ftpmap.get("attachurl");
				ftpmap = null;
				if (trade.get("remote") != null
						&& trade.get("remote").equals("1")) {
					request.setAttribute("url", ftpurl);
				} else {
					request.setAttribute("url", settings.get("attachurl"));
				}
				String timeoffset = (String) session.getAttribute("timeoffset");
				SimpleDateFormat format = Common.getSimpleDateFormat(
						"yyyy-MM-dd", timeoffset);
				String expiration_month = format.format((Float.valueOf(trade
						.get("expiration"))) * 1000L);
				Calendar calendar = Common.getCalendar(timeoffset);
				calendar.add(Calendar.DATE, 7);
				request.setAttribute("expiration_7days", format.format(calendar
						.getTimeInMillis()));
				calendar.add(Calendar.DATE, 7);
				request.setAttribute("expiration_14days", format
						.format(calendar.getTimeInMillis()));
				request.setAttribute("expiration_month", expiration_month);
				calendar.add(Calendar.DATE, -14);
				calendar.add(Calendar.MONTH, 3);
				request.setAttribute("expiration_3months", format
						.format(calendar.getTimeInMillis()));
				calendar.add(Calendar.MONTH, 3);
				request.setAttribute("expiration_halfyear", format
						.format(calendar.getTimeInMillis()));
				calendar.add(Calendar.MONTH, -6);
				calendar.add(Calendar.YEAR, 1);
				request.setAttribute("expiration_year", format.format(calendar
						.getTimeInMillis()));
				target = "toPost_editpost_trade";
				trade.put("expiration", expiration_month);
				request.setAttribute("trade", trade);
				request.setAttribute("special", 2);
				navigation = navigation
						+ " &raquo; <a href=\"viewthread.jsp?do=tradeinfo&tid="
						+ tid + "&pid=" + pid + "\">" + trade.get("subject")
						+ "</a> ";
				String tradetypes = settings.get("tradetypes");
				if (tradetypes != null && tradetypes.length() > 0) {
					Map<String, String> forumtradetypes = dataParse
							.characterParse(forumfield.getTradetypes(), true);
					Map<String, String> tradetypesmap = dataParse
							.characterParse(tradetypes, true);
					StringBuffer tradetypeselect = new StringBuffer(
							"<select name=\"tradetypeid\" onchange=\"ajaxget(\'post.jsp?action=threadtypes&tradetype=yes&typeid=\'+this.options[this.selectedIndex].value+'&rand='+Math.random(), \'threadtypes\', \'threadtypeswait\')\"><option value=\"0\">&nbsp;</option>");
					Set<String> typeids = tradetypesmap.keySet();
					for (String typeid : typeids) {
						if (forumtradetypes.size() == 0
								|| forumtradetypes.containsValue(typeid)) {
							if (typeid.equals(trade.get("typeid"))) {
								tradetypeselect.append("<option value=\""
										+ typeid
										+ "\" selected=\"selected\">"
										+ Common.strip_tags(tradetypesmap
												.get(typeid)) + "</option>");
							} else {
								tradetypeselect.append("<option value=\""
										+ typeid
										+ "\">"
										+ Common.strip_tags(tradetypesmap
												.get(typeid)) + "</option>");
							}
						}
					}
					tradetypeselect
							.append("</select><span id=\"threadtypeswait\"></span>");
					request.setAttribute("tradetypeselect", tradetypeselect);
				}
			}
		}
		if (post.getAttachment() > 0) {
			List<Map<String, String>> attachmentlist = dataBaseService
					.executeQuery("select * from jrun_attachments as a where a.pid="
							+ post.getPid() + " and aid<>" + aid);
			Map<String, String> ftpmap = dataParse.characterParse(settings
					.get("ftp"), false);
			String ftpurl = ftpmap.get("attachurl");
			for (Map<String, String> attachs : attachmentlist) {
				if (attachs.get("remote").equals("1")) {
					String attachment = attachs.get("attachment");
					attachment = attachs.get("thumb").equals("1") ? ftpurl
							+ "/" + attachment + ".thumb"
							+ attachment.substring(attachment.lastIndexOf("."))
							: ftpurl + "/" + attachment;
					attachs.put("attachment", attachment);
				} else {
					String attachment = attachs.get("attachment");
					attachment = attachs.get("thumb").equals("1") ? settings
							.get("attachurl")
							+ "/"
							+ attachment
							+ ".thumb"
							+ attachment.substring(attachment.lastIndexOf("."))
							: settings.get("attachurl") + "/" + attachment;
					attachs.put("attachment", attachment);
				}
			}
			request.setAttribute("attachmentlist", attachmentlist);
		}
		request.setAttribute("navigation", navigation);
		Map<String, String> attatypemap = new HashMap<String, String>();
		{
			attatypemap.put("text/css", "text.gif");
			attatypemap.put("application/zip", "zip.gif");
			attatypemap.put("text/plain", "text.gif");
			attatypemap.put("image/pjpeg", "image.gif");
		}
		request.setAttribute("attatypemap", attatypemap);
		Map threadtypes = dataParse.characterParse(forumfield.getThreadtypes(),
				false);
		request.setAttribute("threadtypes", threadtypes);
		int curtypeid = thread.getTypeid().intValue();
		int modelid = Common.toDigit(request.getParameter("modelid"));
		String typeselect = Common.typeselect(fid, curtypeid, special, modelid,
				null, threadtypes);
		request.setAttribute("typeselect", typeselect);
		threadtypes = null;
		usergroups = null;
		if (target == null) {
			target = "toPost_editpost";
		}
		String sqlGetComment = "select * from jrun_comment where threadId="
				+ tid;
		List<Map<String, String>> listcomment = dataBaseService
				.executeQuery(sqlGetComment);
		if (listcomment.size() > 0) {
			request.setAttribute("comment", listcomment);
		} else {
			request.setAttribute("comment", null);
		}

		request.setAttribute("post", post);
		request.setAttribute("thread", thread);
		boolean isorigauthor = uid > 0 && uid == post.getAuthorid();
		request.setAttribute("isorigauthor", isorigauthor);

		return mapping.findForward(target);
	}

	@SuppressWarnings("unchecked")
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String dateformat = (String) session.getAttribute("dateformat");
		String timeformat = (String) session.getAttribute("timeformat");
		String timeoffset = (String) session.getAttribute("timeoffset");
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		File tempfile = isMakeDir();
		ModuleConfig ac = (ModuleConfig) request
				.getAttribute(Globals.MODULE_KEY);
		FileUploadUtil fileupload = new FileUploadUtil(tempfile, memeoryBlock,
				ac);
		try {
			fileupload.parse(request, JspRunConfig.CHARSET);
		} catch (IllegalStateException e) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_attachment_toobig"));
			return mapping.findForward("showMessage");
		}
		int pid = Common.toDigit(fileupload.getParameter("pid"));
		Posts post = postService.getPostsById(pid);
		if (post == null) {
			request.setAttribute("errorInfo", getMessage(request,
					"undefined_action"));
			return mapping.findForward("showMessage");
		}
		String special = fileupload.getParameter("special");
		String isfirst = fileupload.getParameter("isfirst");
		Threads thread = threadService.findByTid(post.getTid());
		Members memberss = (Members) session.getAttribute("user");
		int uid = (Integer) session.getAttribute("jsprun_uid");
		boolean ismoderator = Common.ismoderator(post.getFid(), memberss);
		Map<String, String> usergroups = (Map<String, String>) request
				.getAttribute("usergroups");
		Map<String, String> settings = ForumInit.settings;

		Forums forums = forumService.findById(thread.getFid());

		Members authors = memberService.findMemberById(post.getAuthorid());
		byte jsprun_adminid = (Byte) session.getAttribute("jsprun_adminid");
		byte author_adminid = authors == null ? 0 : authors.getAdminid();
		if ((!ismoderator
				|| Common.intval(usergroups.get("alloweditpost")) <= 0 || (Common
				.in_array(new String[] { "1", "2", "3" }, author_adminid + "") && jsprun_adminid > author_adminid))
				&& !(forums.getAlloweditpost() > 0 && uid == post.getAuthorid())) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_edit_nopermission"));
			return mapping.findForward("showMessage");
		} else if (thread.getDigest() == -1 && post.getFirst() == 1) {
			request.setAttribute("errorInfo", getMessage(request,
					"special_noaction"));
			return mapping.findForward("showMessage");
		}

		String isreply = fileupload.getParameter("reply");
		String iscomment = fileupload.getParameter("remark");

		String[] comment = fileupload.getParameterValues("comment");
		String[] face = fileupload.getParameterValues("face[]");
		String[] clicktime = fileupload.getParameterValues("clicktime[]");
		String isfirstpost=fileupload.getParameter("isfirstpost");
		if (comment == null&&"yes".equals(isfirstpost)) {
			request.setAttribute("errorInfo", "请输入评论选项");
			return mapping.findForward("showMessage");
		} else if (comment != null&&comment.length < 2) {
			request.setAttribute("errorInfo", "评论至少要两项");
			return mapping.findForward("showMessage");
		}

		String edittimelimit = settings.get("edittimelimit");
		Locale locale = getLocale(request);
		MessageResources resources = getResources(request);
		String message = fileupload.getParameter("message");
		String subject = fileupload.getParameter("subject");
		if (!ismoderator && uid == post.getAuthorid()) {
			boolean modnewthreads = false;
			boolean modnewreplies = false;
			if (Common.periodscheck(settings.get("postmodperiods"), Byte
					.valueOf(usergroups.get("disableperiodctrl")), timestamp,
					settings.get("timeoffset"), resources, locale) != null) {
				modnewthreads = true;
				modnewreplies = true;
			} else {
				int allowdirectpost = Integer.valueOf(usergroups
						.get("allowdirectpost"));
				boolean censormod = censormod(subject + "\t" + message, request);
				modnewthreads = (allowdirectpost == 0 || allowdirectpost == 1)
						&& forums.getModnewposts() > 0 || censormod;
				modnewreplies = (allowdirectpost == 0 || allowdirectpost == 2)
						&& forums.getModnewposts() == 2 || censormod;
			}
			if (Common.intval(edittimelimit) > 0
					&& (timestamp - post.getDateline()) >= Integer
							.valueOf(edittimelimit) * 60) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_edit_timelimit", edittimelimit));
				return mapping.findForward("showMessage");
			} else if ((post.getFirst() == 1 && modnewthreads)
					|| (post.getFirst() == 0 && modnewreplies)) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_edit_moderate", edittimelimit));
				return mapping.findForward("showMessage");
			}
		}

		Forumfields fields = forumfieldService.findById(thread.getFid());
		if ((forums.getSimple() & 1) > 0 || fields.getRedirect().length() > 0) {
			request.setAttribute("resultInfo", getMessage(request,
					"forum_disablepost"));
			return mapping.findForward("showMessage");
		}
		String delete = fileupload.getParameter("delete");
		if (delete != null) {
			if (post.getAuthorid() != uid) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_edit_nopermission"));
				return mapping.findForward("showMessage");
			}
			if (thread.getSpecial() == 3 && isfirst.equals("true")
					&& thread.getReplies() > 0) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_edit_reward_already_reply"));
				return mapping.findForward("showMessage");
			}
			if (thread.getSpecial() == 3 && thread.getPrice() < 0
					&& isfirst.equals("true")) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_edit_reward_nopermission"));
				return mapping.findForward("showMessage");
			} else if (thread.getSpecial() == 6 && isfirst.equals("true")) {
			}
			Map creditspolicys = dataParse.characterParse(settings
					.get("creditspolicy"), false);
			String credists = isfirst.equals("true") ? fields.getPostcredits()
					: fields.getReplycredits();
			Map<Integer, Integer> postcredits = dataParse.characterParse(
					credists, false);
			String postreply = isfirst.equals("true") ? "post" : "reply";
			String creditsformula = settings.get("creditsformula");
			if (postcredits == null || postcredits.size() <= 0) {
				postcredits = (Map<Integer, Integer>) creditspolicys
						.get(postreply);
			}
			if (uid > 0) {
				Common.updatepostcredits("-", uid, postcredits, timestamp);
				Common.updatepostcredits(uid, creditsformula);
			}
			String creditstrans = settings.get("creditstrans");
			if (isfirst.equals("true") && thread.getSpecial() == 3) {
				dataBaseService.runQuery("UPDATE jrun_members SET extcredits"
						+ creditstrans + "=extcredits" + creditstrans + "+"
						+ thread.getPrice() + " WHERE uid='"
						+ post.getAuthorid() + "'", true);
				dataBaseService.runQuery(
						"DELETE FROM jrun_rewardlog WHERE tid='"
								+ post.getTid() + "'", true);
			}
			int thread_attachment = 0;
			int post_attachment = 0;
			List<Map<String, String>> attalist = dataBaseService
					.executeQuery("SELECT pid, attachment, thumb, remote FROM jrun_attachments WHERE tid='"
							+ post.getTid() + "'");
			String servletpath = servlet.getServletContext().getRealPath("/")
					+ settings.get("attachdir") + "/";
			for (Map<String, String> attach : attalist) {
				if (attach.get("pid").equals(pid + "")) {
					post_attachment++;
					Common.dunlink(attach.get("attachment"), Byte
							.valueOf(attach.get("thumb")), Byte.valueOf(attach
							.get("remote")), servletpath);
				} else {
					thread_attachment = 1;
				}
			}
			if (post_attachment > 0) {
				postcredits = dataParse.characterParse(fields
						.getPostattachcredits(), false);
				if (postcredits == null || postcredits.size() <= 0) {
					postcredits = (Map<Integer, Integer>) creditspolicys
							.get("postattach");
				}
				dataBaseService.runQuery(
						"DELETE FROM jrun_attachments WHERE pid='" + pid + "'",
						true);
				Common
						.updatepostcredits("-", uid, post_attachment,
								postcredits);
				Common.updatepostcredits(uid, creditsformula);
			}
			dataBaseService.runQuery("DELETE FROM jrun_posts WHERE pid='" + pid
					+ "'", true);
			if (thread.getSpecial() == 2) {
				dataBaseService.runQuery("DELETE FROM jrun_trades WHERE pid='"
						+ pid + "'", true);
			}
			if (isfirst.equals("true")) {
				String tablearray[] = new String[] { "threadsmod",
						"relatedthreads", "threads", "debates", "debateposts",
						"polloptions", "polls", "mythreads", "typeoptionvars" };
				for (String table : tablearray) {
					dataBaseService.runQuery("DELETE FROM jrun_" + table
							+ " WHERE tid='" + post.getTid() + "'", true);
				}
				if (thread.getDisplayorder() == 2
						|| thread.getDisplayorder() == 3) {
					Cache.updateCache("forumdisplay");
				}
			} else {
				List<Map<String, String>> postquery = dataBaseService
						.executeQuery("SELECT author, dateline, anonymous FROM jrun_posts WHERE tid='"
								+ post.getTid()
								+ "' AND invisible='0' ORDER BY dateline DESC LIMIT 1");
				Map<String, String> lastpost = postquery.get(0);
				String author = lastpost.get("anonymous").equals("0") ? Common
						.addslashes(lastpost.get("author")) : "";
				dataBaseService.runQuery(
						"UPDATE jrun_threads SET replies=replies-1, attachment='"
								+ thread_attachment + "', lastposter='"
								+ author + "', lastpost='"
								+ lastpost.get("dateline") + "' WHERE tid='"
								+ post.getTid() + "'", true);
			}
			Common.updateforumcount(post.getFid() + "", resources, locale);
			if (Common.isshowsuccess(request.getSession(), "post_edit_succeed")) {
				if (post.getFirst() == 1) {
					Common.requestforward(response, "forumdisplay.jsp?fid="
							+ post.getFid());
				} else {
					Common.requestforward(response, "viewthread.jsp?tid="
							+ thread.getTid() + "&page="
							+ fileupload.getParameter("page"));
				}
				return null;
			} else {
				String mess = "";
				String urls = "";
				if (post.getFirst() == 1) {
					mess = getMessage(request, "post_edit_delete_succeed");
					urls = "forumdisplay.jsp?fid=" + post.getFid();
				} else {
					urls = "viewthread.jsp?tid=" + thread.getTid() + "&page="
							+ fileupload.getParameter("page");
					mess = getMessage(request, "post_edit_succeed", post
							.getFid()
							+ "");
				}
				request.setAttribute("successInfo", mess);
				request.setAttribute("requestPath", urls);
				return mapping.findForward("showMessage");
			}
		}
		short typeid = (short) Common.range(Common.intval(fileupload
				.getParameter("typeid")), 32767, 0);
		Map threadtypes = dataParse.characterParse(fields.getThreadtypes(),
				false);
		if (isfirst.equals("true") && typeid <= 0 && threadtypes != null
				&& threadtypes.get("required") != null
				&& 1 == (Integer) threadtypes.get("required")
				&& !(Integer.valueOf(special) > 0)) {
			request.setAttribute("errorInfo", getMessage(request,
					"post_type_isnull"));
			return mapping.findForward("showMessage");
		}
		boolean allowanonymous = forums.getAllowanonymous() > 0
				|| "1".equals(usergroups.get("allowanonymous"));
		String authoradd = "";
		int isanonymous = Common.empty(fileupload.getParameter("isanonymous")) ? 0
				: 1;
		int oldanonymous = post.getAnonymous();
		if (uid != thread.getAuthorid() && !allowanonymous) {
			if (post.getAnonymous() > 0 && isanonymous == 0) {
				authoradd = post.getAuthor();
			}
		} else {
			authoradd = isanonymous > 0 ? "" : post.getAuthor();
		}
		if (subject == null) {
			subject = "";
		}
		if (message == null) {
			message = "";
		}
		if (fileupload.getParameter("parseurloff") != null) {
			post.setParseurloff((byte) 1);
		} else {
			post.setParseurloff((byte) 0);
		}
		if (fileupload.getParameter("smileyoff") != null) {
			post.setSmileyoff((byte) 1);
		} else {
			post.setSmileyoff((byte) 0);
		}
		if (fileupload.getParameter("bbcodeoff") != null) {
			post.setBbcodeoff((byte) 1);
		} else {
			post.setBbcodeoff((byte) 0);
		}
		if (fileupload.getParameter("htmlon") != null) {
			post.setHtmlon((byte) 1);
		} else {
			post.setHtmlon((byte) 0);
		}
		if (fileupload.getParameter("isanonymous") != null) {
			post.setAnonymous((byte) 1);
		} else {
			post.setAnonymous((byte) 0);
		}
		if (fileupload.getParameter("usesig") != null) {
			post.setUsesig((byte) 1);
		} else {
			post.setUsesig((byte) 0);
		}
		message = message.trim();
		subject = Common.htmlspecialchars(subject);
		String mesuc = Common.checkpost(subject, message, settings, usergroups,
				resources, locale);
		if (mesuc != null) {
			request.setAttribute("errorInfo", mesuc);
			return mapping.findForward("showMessage");
		}
		Map<Integer, String> specials = threadtypes != null ? (Map<Integer, String>) threadtypes
				.get("special")
				: null;
		typeid = Integer.valueOf(special) > 0
				&& "1".equals(specials != null ? specials.get(typeid) : "0") ? 0
				: typeid;
		specials = null;
		thread.setTypeid(typeid);
		short jsprun_groupid = (Short) session.getAttribute("jsprun_groupid");
		MessageResources mr = getResources(request);
		Locale newLocal = null;
		Map<Integer, Map<String, Object>> languages = dataParse.characterParse(
				settings.get("languages"), true);
		String lang = null;
		Set<Integer> lang_keys = languages.keySet();
		for (Integer key : lang_keys) {
			Map<String, Object> language = languages.get(key);
			if ((Integer) language.get("default") > 0
					&& (Integer) language.get("available") > 0) {
				lang = (String) language.get("language");
				break;
			}
		}
		if (lang != null) {
			String[] langs = lang.split("_");
			if (langs.length > 1) {
				newLocal = new Locale(langs[0], langs[1]);
			} else {
				newLocal = new Locale(langs[0]);
			}
		}
		String temp1 = null;
		String temp2 = null;
		String on = null;
		if (newLocal != null) {
			temp1 = mr.getMessage(newLocal, "post_edit_regexp1");
			temp2 = mr.getMessage(newLocal, "post_edit_regexp2");
			on = mr.getMessage(newLocal, "on");
		}
		if (temp1 == null) {
			temp1 = getMessage(request, "post_edit_regexp1");
			temp2 = getMessage(request, "post_edit_regexp2");
			on = getMessage(request, "on");
		}
		int length = temp2.length();
		if (jsprun_groupid != 1 && "1".equals(settings.get("editedby"))) {
			String jsprun_userss = (String) session
					.getAttribute("jsprun_userss");
			if (message.indexOf(temp1) == -1
					&& timestamp - post.getDateline() >= 60) {
				message = message
						+ "\n"
						+ temp1
						+ " "
						+ jsprun_userss
						+ " "
						+ on
						+ " "
						+ Common.gmdate(dateformat + "  " + timeformat,
								timestamp, timeoffset) + " " + temp2;
			} else if (message.indexOf(temp1) != -1) {
				int index = message.indexOf(temp1);
				int end = message.indexOf(temp2);
				String submessage = message.substring(index, end + length);
				message = StringUtils.replace(message, submessage, temp1
						+ " "
						+ jsprun_userss
						+ " "
						+ on
						+ " "
						+ Common.gmdate(dateformat + "  " + timeformat,
								timestamp, timeoffset) + " " + temp2);
			}
		} else if (message.indexOf(temp1) != -1) {
			int index = message.indexOf(temp1);
			int end = message.indexOf(temp2);
			String submessage = message.substring(index, end + length);
			message = StringUtils.replace(message, submessage, "");
		}

		Map<String, String> censor = (Map<String, String>) request
				.getAttribute("censor");
		String banned = censor.get("banned");
		if (!Common.empty(banned) && Common.matches(message, banned)) {
			request.setAttribute("errorInfo",
					getMessage(request, "word_banned"));
			return mapping.findForward("showMessage");
		}
		if (!Common.empty(banned) && Common.matches(subject, banned)) {
			request.setAttribute("errorInfo",
					getMessage(request, "word_banned"));
			return mapping.findForward("showMessage");
		}
		String filters = censor.get("filter");
		Map<String, Map<String, String>> filtermap = dataParse.characterParse(
				filters, false);
		if (filtermap != null) {
			Map<String, String> findsMap = filtermap.get("find");
			Map<String, String> replaceMap = filtermap.get("replace");
			if (findsMap != null && !findsMap.keySet().isEmpty()) {
				Iterator it = findsMap.keySet().iterator();
				while (it.hasNext()) {
					String id = it.next().toString();
					message = message.replaceAll(findsMap.get(id), replaceMap
							.get(id));
					subject = subject.replaceAll(findsMap.get(id), replaceMap
							.get(id));
				}
			}
		}
		List filelist = getAttach(fileupload);
		String succ = checkAttachment(request, filelist, fields);
		if (succ != null) {
			request.setAttribute("errorInfo", succ);
			return mapping.findForward("showMessage");
		}
		FileItem tradfile = fileupload.getFileItem("tradeattach");
		if (tradfile != null && tradfile.getSize() > 0) {
			List tradfilelist = new ArrayList();
			tradfilelist.add(tradfile);
			tradfilelist.add("1");
			String mess = checkAttachment(request, tradfilelist, fields);
			if (mess != null) {
				request.setAttribute("errorInfo", mess);
				return mapping.findForward("showMessage");
			}
		}

		if (comment != null) {
			String arrayid = "";
			for (int i = 0; i < comment.length; i++) {
				if (!"".equals(comment[i])) {
					Comment com = new Comment();
					com.setCommentName(comment[i]);
					com.setImg(face[i]);
					com.setThreadId(thread.getTid());
					com.setClickTimes(Integer.parseInt(clicktime[i]));
					boolean b = commentService.saveOrUpdate(com);
					if (b)
						arrayid = com.getId() + "," + arrayid;
				}
			}
			if (arrayid.length() > 1) {
				arrayid = arrayid.substring(0, arrayid.length() - 1);
				String deletesql = "delete from jrun_comment where threadId="
						+ thread.getTid() + " and id not in(" + arrayid + ")";
				dataBaseService.runQuery(deletesql);
			}
		}

		if (special.equals("1") && isfirst.equals("true")) {
			Polls polls = pollService.findPollsBytid(thread.getTid());
			String expiration = fileupload.getParameter("expiration");
			String polloptionid[] = fileupload
					.getParameterValues("polloptionid[]");
			String displayorder[] = fileupload
					.getParameterValues("displayorder[]");
			String polloption[] = fileupload.getParameterValues("polloption[]");
			String polloptionnew[] = fileupload
					.getParameterValues("polloptionnew");
			if (polloptionid != null && polloptionnew != null) {
				int pollsize = polloptionid.length + polloptionnew.length;
				if (pollsize > convertInt(settings.get("maxpolloptions"))) {
					request.setAttribute("errorInfo", getMessage(request,
							"post_poll_option_toomany", settings
									.get("maxpolloptions")));
					return mapping.findForward("showMessage");
				}
			}
			String temp3 = getMessage(request, "poll_finish");
			if (!expiration.equals(temp3)) {
				if (convertInt(expiration) == 0) {
					polls.setExpiration(0);
					thread.setClosed(0);
				} else {
					int expira = timestamp + convertInt(expiration) * 86400;
					polls.setExpiration(expira);
					thread.setClosed(0);
				}
			}
			if (fileupload.getParameter("visibilitypoll") != null) {
				polls.setVisible((byte) 1);
			} else {
				polls.setVisible((byte) 0);
			}
			if (fileupload.getParameter("multiplepoll") != null) {
				polls.setMultiple((byte) 1);
				polls.setMaxchoices((short) Common.toDigit(fileupload
						.getParameter("maxchoices")));
			} else {
				polls.setMultiple((byte) 0);
			}
			if (fileupload.getParameter("close") != null) {
				thread.setClosed(1);
			} else {
				thread.setClosed(0);
			}
			for (int i = 0; i < polloptionid.length; i++) {
				Polloptions polloptions = optionService
						.findPolloptionsById((short) Common
								.toDigit(polloptionid[i]));
				if (polloption[i].equals("")) {
					optionService.deletePolloptions(polloptions);
				} else {
					polloptions.setDisplayorder((short) Common
							.toDigit(displayorder[i]));
					polloptions.setPolloption(Common
							.htmlspecialchars(polloption[i]));
					optionService.updatePolloptions(polloptions);
				}
			}
			polloptionid = null;
			displayorder = null;
			polloption = null;
			if (polloptionnew != null) {
				String displayordernew[] = fileupload
						.getParameterValues("displayordernew");
				for (int i = 0; i < polloptionnew.length; i++) {
					Polloptions polloptions = new Polloptions();
					polloptions.setDisplayorder(Short
							.valueOf(convertInt(displayordernew[i]) + ""));
					polloptions.setPolloption(Common
							.htmlspecialchars(polloptionnew[i]));
					polloptions.setTid(thread.getTid());
					polloptions.setVotes(0);
					polloptions.setVoterids("");
					optionService.insertPolloptions(polloptions);
				}
				displayordernew = null;
				polloptionnew = null;
			}
			polls.setReporttype(Integer.parseInt(fileupload
					.getParameter("reporttype")));
			pollService.updatePolls(polls);
		} else if (special.equals("3") && isfirst.equals("true")) {
			int rewardprice = Common.toDigit(fileupload
					.getParameter("rewardprice"));
			if (thread.getPrice() > 0) {
				double creditstax = Double.valueOf(settings.get("creditstax"));
				int reprice = (int) (rewardprice + Math.ceil(rewardprice
						* creditstax));
				if (rewardprice <= 0) {
					request.setAttribute("errorInfo", getMessage(request,
							"reward_credits_invalid"));
					return mapping.findForward("showMessage");
				}
				if (rewardprice < thread.getPrice()) {
					request.setAttribute("errorInfo", getMessage(request,
							"reward_credits_fall"));
					return mapping.findForward("showMessage");
				}
				int maxrewardprice = convertInt(usergroups
						.get("maxrewardprice"));
				int minrewardprice = convertInt(usergroups
						.get("minrewardprice"));
				if (rewardprice < minrewardprice
						|| (maxrewardprice > 0 && rewardprice > maxrewardprice)) {
					request.setAttribute("errorInfo", getMessage(request,
							"reward_credits_between", minrewardprice + "",
							maxrewardprice + ""));
					return mapping.findForward("showMessage");
				}
				int addpricetemp = rewardprice - thread.getPrice();
				if (thread.getAuthorid() > 0 && addpricetemp > 0) {
					Members author = memberService.findMemberById(thread
							.getAuthorid());
					String creditstrans = settings.get("creditstrans");
					int extcreit = (Integer) Common.getValues(author,
							"extcredits" + creditstrans);
					if (reprice > extcreit) {
						request.setAttribute("errorInfo", getMessage(request,
								"reward_credits_shortage"));
						return mapping.findForward("showMessage");
					}
					int addprice = (int) (addpricetemp + Math.ceil(addpricetemp
							* creditstax));
					int tempCredits = (Integer) Common.getValues(author,
							"extcredits" + creditstrans);
					author = (Members) Common.setValues(author, "extcredits"
							+ creditstrans, (tempCredits - addprice) + "");
					memberService.modifyMember(author);
				}
				dataBaseService.runQuery(
						"UPDATE jrun_rewardlog SET netamount='" + rewardprice
								+ "' WHERE tid='" + thread.getTid()
								+ "' AND authorid='" + thread.getAuthorid()
								+ "'", true);
				thread.setPrice((short) rewardprice);
			} else {
				thread.setPrice((short) -rewardprice);
			}
		} else if (special.equals("5") && isfirst.equals("true")) {
			String affirmpoint = fileupload.getParameter("affirmpoint");
			String negapoint = fileupload.getParameter("negapoint");
			int endtime = Common.dataToInteger(fileupload
					.getParameter("endtime"), timeoffset);
			endtime = endtime > 0 ? endtime : 0;
			String umpire = fileupload.getParameter("umpire");
			if (Common.empty(affirmpoint) || Common.empty(negapoint)) {
				request.setAttribute("errorInfo", getMessage(request,
						"debate_position_nofound"));
				return mapping.findForward("showMessage");
			}
			if (endtime > 0 && endtime < timestamp) {
				request.setAttribute("errorInfo", getMessage(request,
						"debate_endtime_invalid"));
				return mapping.findForward("showMessage");
			}
			if (!Common.empty(umpire)) {
				Members member = memberService.findByName(umpire);
				if (member == null) {
					request.setAttribute("errorInfo", getMessage(request,
							"debate_umpire_invalid", umpire));
					return mapping.findForward("showMessage");
				}
			}
			dataBaseService.runQuery("UPDATE jrun_debates SET affirmpoint='"
					+ Common.addslashes(Common.htmlspecialchars(affirmpoint))
					+ "', negapoint='"
					+ Common.addslashes(Common.htmlspecialchars(negapoint))
					+ "', endtime='" + endtime + "', umpire='"
					+ Common.addslashes(umpire) + "' WHERE tid='"
					+ thread.getTid() + "' AND uid='"
					+ (Integer) session.getAttribute("jsprun_uid") + "'", true);
		} else if (special.equals("0")) {
			short price = (short) Common.range(Common.intval(fileupload
					.getParameter("price")), 32767, 0);
			short maxprice = Short.valueOf(usergroups.get("maxprice"));
			price = maxprice > 0 ? (price <= maxprice ? price : maxprice) : 0;
			double creditstax = Double.valueOf(settings.get("creditstax"));
			if (price > 0 && Math.floor(price * (1d - creditstax)) <= 0) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_net_price_iszero"));
				return mapping.findForward("showMessage");
			}
			short iconid = (short) Common.range(Common.intval(fileupload
					.getParameter("iconid")), 32767, 0);
			thread.setIconid(iconid);
			thread.setPrice((short) price);
		} else if (special.equals("4") && isfirst.equals("true")) {
			int activitytime = Common.range(Common.intval(fileupload
					.getParameter("activitytime")), 1, 0);
			String starttimefrom = fileupload.getParameter("starttimefrom["
					+ activitytime + "]");
			String starttimeto = fileupload.getParameter("starttimeto");
			String activityclass = fileupload.getParameter("activityclass")
					.trim();
			String activityplace = fileupload.getParameter("activityplace")
					.trim();
			String activityexpiration = fileupload.getParameter(
					"activityexpiration").trim();
			if (activitytime == 0 && "".equals(starttimefrom)) {
				request.setAttribute("errorInfo", getMessage(request,
						"activity_fromtime_please"));
				return mapping.findForward("showMessage");
			} else if (checkDateFormatAndValite(starttimefrom) < 0) {
				request.setAttribute("errorInfo", getMessage(request,
						"activity_fromtime_error"));
				return mapping.findForward("showMessage");
			}
			int starttime = Common.dataToInteger(starttimefrom, timeoffset);
			int timeto = Common.dataToInteger(starttimeto, timeoffset);
			timeto = timeto > 0 ? timeto : 0;
			if (starttime < timestamp) {
				request.setAttribute("resultInfo", getMessage(request,
						"activity_smaller_current"));
				return mapping.findForward("showMessage");
			} else if (activitytime > 0 && (timeto <= 0 || starttime > timeto)) {
				request.setAttribute("errorInfo", getMessage(request,
						"activity_fromtime_error"));
				return mapping.findForward("showMessage");
			} else if ("".equals(activityclass)) {
				request.setAttribute("errorInfo", getMessage(request,
						"activity_sort_please"));
				return mapping.findForward("showMessage");
			} else if ("".equals(activityplace)) {
				request.setAttribute("errorInfo", getMessage(request,
						"activity_address_please"));
				return mapping.findForward("showMessage");
			} else if (!activityexpiration.equals("")
					&& checkDateFormatAndValite(activityexpiration) < 0) {
				request.setAttribute("errorInfo", getMessage(request,
						"activity_totime_error"));
				return mapping.findForward("showMessage");
			}
			int expiration = 0;
			if (!activityexpiration.equals("")) {
				expiration = Common.dataToInteger(activityexpiration,
						timeoffset);
			}
			activityclass = Common.cutstr(Common
					.htmlspecialchars(activityclass), 25, null);
			dataBaseService.runQuery("UPDATE jrun_activities SET cost='"
					+ Common.range(Common.toDigit(fileupload
							.getParameter("cost")), 16777215, 0)
					+ "', starttimefrom='"
					+ starttime
					+ "', starttimeto='"
					+ timeto
					+ "', place='"
					+ Common.addslashes(Common.cutstr(Common
							.htmlspecialchars(activityplace), 40, null))
					+ "', class='"
					+ Common.addslashes(activityclass)
					+ "', gender='"
					+ Common.range(Common.intval(fileupload
							.getParameter("gender")), 2, 0) + "', number='"
					+ Common.toDigit(fileupload.getParameter("number"))
					+ "', expiration='" + expiration + "' WHERE tid='"
					+ post.getTid() + "'", true);
		} else if (special.equals("2") && isfirst.equals("true")) {
			String starttime=fileupload.getParameter("starttime");
			String endtime=fileupload.getParameter("endtime");
			String Allow=fileupload.getParameter("Allow");
			int starttimeInt= Common.dataToInteger(starttime, timeoffset);
			int endtimeInt=Common.dataToInteger(endtime,timeoffset);
			if(starttimeInt>endtimeInt){
				request.setAttribute("errorInfo","开始时间应该小于结束时间");
				return mapping.findForward("showMessage");
			}
			String permlist = fileupload.getParameter("permlist");// 专题帖权限列表
			String[] plist = new String[] {};
			if (permlist != "" && permlist != null) {
				plist = permlist.split(",");
			}
			if ("3".equals(Allow)||"4".equals(Allow)) {

				if (permlist == null || permlist == "") {
					request.setAttribute("errorInfo", "请对会员进行筛选");
					return mapping.findForward("showMessage");
				} else {
					if (plist.length == 0) {
						request.setAttribute("errorInfo", "请对会员进行筛选");
						return mapping.findForward("showMessage");
					}
				}
			}
			dataBaseService.runQuery("delete from jrun_permlist where tid="+post.getTid());
			if ("3".equals(Allow) || "4".equals(Allow)) {
				if (plist.length > 0) {
					PermList perml = null;
					for (String pl : plist) {
						perml = new PermList();
						perml.setAccount(pl);
						perml.setEmail("");
						perml.setTid(post.getTid());
						permlistService.addPermList(perml);
						perml = null;
					}					
				}
			}
			String update="update jrun_special set starttime="+starttimeInt+",endtime="+endtimeInt+",perm="+Allow+" where tid="+post.getTid();
			dataBaseService.runQuery(update,false);
			String aboutcounter = "";// Common.addslashes(fileupload.getParameter("aboutcounter"));
			message = message + "\t\t\t" + aboutcounter;
		}
		if (isfirst.equals("true")) {
			if (subject.equals("") || message.equals("")) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_sm_isnull"));
				return mapping.findForward("showMessage");
			}
			Map<Integer, String> optiondata = new TreeMap<Integer, String>();
			if (threadtypes != null && typeid > 0) {
				Common.include(request, response, servlet,
						"/forumdata/cache/threadtype_" + typeid + ".jsp", null);
				Map<String, String> threadtype = (Map<String, String>) request
						.getAttribute("threadtype");
				if (threadtype != null) {
					Map<Integer, Map<String, String>> dtype = dataParse
							.characterParse(threadtype.get("dtype"), true);
					threadtype = null;
					if (dtype != null
							&& dtype.size() > 0
							&& "1".equals(((Map<Integer, String>) threadtypes
									.get("special")).get((int) typeid))
							&& !(forums.getAllowspecialonly() > 0)) {
						Set<Integer> keys = dtype.keySet();
						for (Integer optionid : keys) {
							Map<String, String> option = dtype.get(optionid);
							String title = option.get("title");
							String identifier = option.get("identifier");
							String type = option.get("type");
							int maxlength = Common.toDigit(option
									.get("maxlength"));
							int maxnum = Common.toDigit(option.get("maxnum"));
							int minnum = Common.toDigit(option.get("minnum"));
							byte required = (byte) Common.range(Common
									.intval(option.get("required")), 1, 0);
							byte unchangeable = (byte) Common.range(Common
									.intval(option.get("unchangeable")), 1, 0);
							String value = fileupload
									.getParameter("typeoption[" + identifier
											+ "]");
							if (required > 0
									&& (value == null || value.equals(""))) {
								request.setAttribute("errorInfo", getMessage(
										request, "threadtype_required_invalid",
										title));
								return mapping.findForward("showMessage");
							} else if (value != null
									&& !value.equals("")
									&& (type.equals("number")
											&& !Common.isNum(value) || type
											.equals("email")
											&& !Common.isEmail(value))) {
								request.setAttribute("errorInfo", getMessage(
										request, "threadtype_format_invalid",
										title));
								return mapping.findForward("showMessage");
							} else if (value != null && !value.equals("")
									&& maxlength > 0
									&& value.length() > maxlength) {
								request.setAttribute("errorInfo", getMessage(
										request, "threadtype_toolong_invalid",
										title));
								return mapping.findForward("showMessage");
							} else if (value != null
									&& !value.equals("")
									&& ((option.get("maxnum") != null && Integer
											.valueOf(value) > Integer
											.valueOf(maxnum)) || (option
											.get("minnum") != null && Integer
											.valueOf(value) < Integer
											.valueOf(minnum)))) {
								request.setAttribute("errorInfo", getMessage(
										request, "threadtype_num_invalid",
										title));
								return mapping.findForward("showMessage");
							} else if (value != null && !value.equals("")
									&& unchangeable > 0) {
								request.setAttribute("errorInfo", getMessage(
										request,
										"threadtype_unchangeable_invalid",
										title));
								return mapping.findForward("showMessage");
							} else if (type.equals("checkbox")) {
								String[] values = fileupload
										.getParameterValues("typeoption["
												+ identifier + "]");
								if (values != null) {
									StringBuffer temp = new StringBuffer();
									for (String obj : values) {
										temp.append("\t" + obj);
									}
									value = temp.substring(1);
								}
							}
							optiondata.put(optionid, value);
						}
					}
					dtype = null;
				}
			}
			if (threadtypes.get("special") != null
					&& "1".equals(((Map<Integer, String>) threadtypes
							.get("special")).get((int) typeid))
					&& optiondata != null && optiondata.size() > 0) {
				Set<Integer> keys = optiondata.keySet();
				for (Integer optionid : keys) {
					dataBaseService.runQuery(
							"UPDATE jrun_typeoptionvars SET value='"
									+ Common.addslashes(optiondata
											.get(optionid)) + "' WHERE tid='"
									+ post.getTid() + "' AND optionid='"
									+ optionid + "'", true);
				}
			}
			threadtypes = null;

			thread.setReadperm((short) Common.toDigit(fileupload
					.getParameter("readperm")));
			thread.setSubject(subject);
			thread.setAuthor(authoradd);
			thread.setIsComment(Byte.parseByte(iscomment));
			thread.setIsReply(Byte.parseByte(isreply));

			int tagstatus = Common.toDigit(settings.get("tagstatus"));
			String tags = fileupload.getParameter("tags");
			if (tagstatus > 0) {
				tags = tags == null ? "" : tags;
				List<Map<String, String>> threadtagary = dataBaseService
						.executeQuery("SELECT tagname FROM jrun_threadtags WHERE tid='"
								+ thread.getTid() + "'");
				List<String> taglist = new ArrayList<String>();
				if (threadtagary != null && threadtagary.size() > 0) {
					for (Map<String, String> tag : threadtagary) {
						taglist.add(tag.get("tagname"));
					}
				}
				String[] tagarray = tags.split(" ");
				int tagcount = 0;
				List<String> threadtagsnew = new ArrayList<String>();
				for (String tagname : tagarray) {
					tagname = tagname.trim();
					int len = Common.strlen(tagname);
					if (len >= 3 && len <= 20) {
						threadtagsnew.add(tagname);
						if (!taglist.contains(tagname)) {
							tagname = Common.addslashes(tagname);
							List<Map<String, String>> map = dataBaseService
									.executeQuery("SELECT closed FROM jrun_tags WHERE tagname='"
											+ tagname + "'");
							int isclosed = 0;
							if (map != null && map.size() > 0) {
								isclosed = Integer.valueOf(map.get(0).get(
										"closed"));
								if (isclosed == 0) {
									dataBaseService.runQuery(
											"UPDATE jrun_tags SET total=total+1 WHERE tagname='"
													+ tagname + "'", true);
								}
							} else {
								dataBaseService.runQuery(
										"INSERT INTO jrun_tags (tagname, closed, total) VALUES ('"
												+ tagname + "', 0, 1)", true);
							}
							if (isclosed == 0) {
								dataBaseService.runQuery(
										"INSERT jrun_threadtags (tagname, tid) VALUES ('"
												+ tagname + "', "
												+ thread.getTid() + ")", true);
							}
						}
					}
					tagcount++;
					if (tagcount > 4) {
						break;
					}
				}
				for (String tagname : taglist) {
					if (!threadtagsnew.contains(tagname)) {
						tagname = Common.addslashes(tagname);
						int size = Integer.valueOf(dataBaseService
								.executeQuery(
										"SELECT count(*) count FROM jrun_threadtags WHERE tagname='"
												+ tagname + "' AND tid!='"
												+ thread.getTid() + "'").get(0)
								.get("count"));
						if (size > 0) {
							dataBaseService
									.runQuery("UPDATE jrun_tags SET total=total-1 WHERE tagname='"
											+ tagname + "'");
						} else {
							dataBaseService
									.runQuery("DELETE FROM jrun_tags WHERE tagname='"
											+ tagname + "'");
						}
						dataBaseService
								.runQuery("DELETE FROM jrun_threadtags WHERE tagname='"
										+ tagname
										+ "' AND tid='"
										+ thread.getTid() + "'");
					}
				}
			}
		} else {
			if (subject.equals("") && message.equals("")) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_sm_isnull"));
				return mapping.findForward("showMessage");
			}
		}
		message = message.replaceAll("\\[attachimg\\]", "[attach]");
		message = message.replaceAll("\\[/attachimg\\]", "[/attach]");
		post.setMessage(message);
		post.setSubject(subject);
		String mapurl = "";
		if (special.equals("2") && isfirst.equals("false")) {
			String isshop = fileupload.getParameter("isshop");
			if (isshop != null && isshop.equals("true")) {
				List<Map<String, String>> trades = dataBaseService
						.executeQuery("SELECT typeid,aid FROM jrun_trades WHERE tid='"
								+ post.getTid() + "' AND pid='" + pid + "'");
				short oldtypeid = Short.valueOf(trades.get(0).get("typeid"));
				short tradetypeid = (short) Common.range(Common
						.intval(fileupload.getParameter("tradetypeid")), 32767,
						0);
				tradetypeid = tradetypeid == 0 ? oldtypeid : tradetypeid;
				Map tradetypes = dataParse.characterParse(fields
						.getTradetypes(), false);
				Map<Integer, String> optiondata = new TreeMap<Integer, String>();
				if (tradetypes != null && tradetypeid > 0) {
					Common.include(request, response, servlet,
							"/forumdata/cache/threadtype_" + tradetypeid
									+ ".jsp", null);
					Map<String, String> threadtype = (Map<String, String>) request
							.getAttribute("threadtype");
					if (threadtype != null) {
						Map<Integer, Map<String, String>> dtype = dataParse
								.characterParse(threadtype.get("dtype"), true);
						threadtype = null;
						if (dtype != null && dtype.size() > 0
								&& !(forums.getAllowspecialonly() > 0)) {
							Set<Integer> keys = dtype.keySet();
							for (Integer optionid : keys) {
								Map<String, String> option = dtype
										.get(optionid);
								String title = option.get("title");
								String identifier = option.get("identifier");
								String type = option.get("type");
								int maxlength = Common.toDigit(option
										.get("maxlength"));
								int maxnum = Common.toDigit(option
										.get("maxnum"));
								int minnum = Common.toDigit(option
										.get("minnum"));
								byte required = (byte) Common.range(Common
										.intval(option.get("required")), 1, 0);
								byte unchangeable = (byte) Common.range(Common
										.intval(option.get("unchangeable")), 1,
										0);
								String value = fileupload
										.getParameter("typeoption["
												+ identifier + "]");
								if (required > 0
										&& (value == null || value.equals(""))) {
									request
											.setAttribute(
													"errorInfo",
													getMessage(
															request,
															"threadtype_required_invalid",
															title));
									return mapping.findForward("showMessage");
								} else if (value != null
										&& !value.equals("")
										&& (type.equals("number")
												&& !Common.isNum(value) || type
												.equals("email")
												&& !Common.isEmail(value))) {
									request
											.setAttribute(
													"errorInfo",
													getMessage(
															request,
															"threadtype_format_invalid",
															title));
									return mapping.findForward("showMessage");
								} else if (value != null && !value.equals("")
										&& maxlength > 0
										&& value.length() > maxlength) {
									request
											.setAttribute(
													"errorInfo",
													getMessage(
															request,
															"threadtype_toolong_invalid",
															title));
									return mapping.findForward("showMessage");
								} else if (value != null
										&& !value.equals("")
										&& ((option.get("maxnum") != null && Integer
												.valueOf(value) > Integer
												.valueOf(maxnum)) || (option
												.get("minnum") != null && Integer
												.valueOf(value) < Integer
												.valueOf(minnum)))) {
									request.setAttribute("errorInfo",
											getMessage(request,
													"threadtype_num_invalid",
													title));
									return mapping.findForward("showMessage");
								} else if (value != null && !value.equals("")
										&& unchangeable > 0) {
									request
											.setAttribute(
													"errorInfo",
													getMessage(
															request,
															"threadtype_unchangeable_invalid",
															title));
									return mapping.findForward("showMessage");
								} else if (type.equals("checkbox")) {
									String[] values = fileupload
											.getParameterValues("typeoption["
													+ identifier + "]");
									if (values != null) {
										StringBuffer temp = new StringBuffer();
										for (String obj : values) {
											temp.append("\t" + obj);
										}
										value = temp.substring(1);
									}
								}
								optiondata.put(optionid, value);
							}
						}
						dtype = null;
					}
				}
				if (tradetypes != null && optiondata != null
						&& optiondata.size() > 0) {
					Set<Integer> keys = optiondata.keySet();
					for (Integer optionid : keys) {
						if (oldtypeid == tradetypeid) {
							dataBaseService.runQuery(
									"UPDATE jrun_tradeoptionvars SET value='"
											+ Common.addslashes(optiondata
													.get(optionid))
											+ "' WHERE pid='" + pid
											+ "' AND optionid='" + optionid
											+ "'", true);
						} else {
							dataBaseService.runQuery(
									"INSERT INTO jrun_tradeoptionvars (typeid, pid, optionid, value) VALUES ('"
											+ tradetypeid
											+ "', '"
											+ pid
											+ "', '"
											+ optionid
											+ "', '"
											+ Common.addslashes(optiondata
													.get(optionid)) + "')",
									true);
						}
					}
				}
				if (oldtypeid != tradetypeid) {
					dataBaseService.runQuery("UPDATE jrun_trades SET typeid='"
							+ tradetypeid + "' WHERE pid='" + pid + "'", true);
				}
				if (trades.size() > 0) {
					String item_prices = fileupload.getParameter("item_price");
					if (item_prices != null
							&& item_prices.matches("^-?\\d+\\.?\\d*$")) {
						item_prices = item_prices.length() > 10 ? "999999.99"
								: item_prices;
						double costprice = Double.valueOf(item_prices);
						costprice = costprice > 999999.99 ? 999999.99
								: costprice;
						item_prices = costprice + "";
					} else {
						item_prices = "0";
					}
					double item_price = Double.valueOf(item_prices);
					String item_name = fileupload.getParameter("item_name")
							.trim();
					float maxtradeprice = Float.valueOf(usergroups
							.get("maxtradeprice"));
					float mintradeprice = Float.valueOf(usergroups
							.get("mintradeprice"));
					int item_number = Common.toDigit(fileupload
							.getParameter("item_number"));
					if (item_name.length() == 0) {
						request.setAttribute("errorInfo", getMessage(request,
								"trade_please_name"));
						return mapping.findForward("showMessage");
					} else if (maxtradeprice > 0
							&& (mintradeprice > item_price || maxtradeprice < item_price)) {
						request.setAttribute("errorInfo", getMessage(request,
								"trade_price_between", mintradeprice + "",
								maxtradeprice + ""));
						return mapping.findForward("showMessage");
					} else if (maxtradeprice == 0 && mintradeprice > item_price) {
						request.setAttribute("errorInfo", getMessage(request,
								"trade_price_more_than", mintradeprice + ""));
						return mapping.findForward("showMessage");
					} else if (item_number < 1) {
						request.setAttribute("errorInfo", getMessage(request,
								"tread_please_number"));
						return mapping.findForward("showMessage");
					}
					item_number = item_number > 65535 ? 65535 : item_number;
					item_name = Common.cutstr(Common.addslashes(Common
							.htmlspecialchars(item_name)), 100, null);
					int postage_mail = Common.toDigit(fileupload
							.getParameter("postage_mail"));
					int postage_express = Common.toDigit(fileupload
							.getParameter("postage_express"));
					int postage_ems = Common.toDigit(fileupload
							.getParameter("postage_ems"));
					postage_mail = postage_mail > 65535 ? 65535 : postage_mail;
					postage_express = postage_express > 65535 ? 65535
							: postage_express;
					postage_ems = postage_ems > 65535 ? 65535 : postage_ems;
					String item_locus = fileupload.getParameter("item_locus");
					item_locus = Common.cutstr(Common.addslashes(Common
							.htmlspecialchars(item_locus)), 20, null);
					String item_expiration = fileupload
							.getParameter("item_expiration");
					int expiration = Common.datecheck(item_expiration) ? Common
							.dataToInteger(item_expiration, "yyyy-MM-dd",
									timeoffset) : 0;
					String item_costprice = fileupload
							.getParameter("item_costprice");
					String seller = fileupload.getParameter("seller");
					if (item_costprice != null && !"".equals(item_costprice)
							&& item_costprice.matches("^-?\\d+\\.?\\d*$")) {
						item_costprice = item_costprice.length() > 10 ? "999999.99"
								: item_costprice;
						double costprice = Double.valueOf(item_costprice);
						costprice = costprice > 999999.99 ? 999999.99
								: costprice;
						item_costprice = Common
								.number_format(costprice, "0.00");
					} else {
						item_costprice = "0.00";
					}
					String item_type = fileupload.getParameter("item_type");
					int closed = expiration > 0
							&& Common.dataToInteger(item_expiration,
									"yyyy-MM-dd", timeoffset) < timestamp ? 1
							: 0;
					String transport = fileupload.getParameter("transport");
					String item_quality = fileupload
							.getParameter("item_quality");
					String tradeaidadd = "";
					if (tradfile != null && tradfile.getSize() > 0) {
						String mess = uploadTradeAttachment(request, pid,
								thread.getTid(), thread.getFid(), tradfile,
								forums.getDisablewatermark());
						if (!Common.isNum(mess)) {
							request.setAttribute("errorInfo", mess);
							return mapping.findForward("showMessage");
						} else {
							tradeaidadd = "aid=" + mess + ",";
							dataBaseService
									.runQuery("delete from jrun_attachments where aid="
											+ trades.get(0).get("aid"));
						}
					}
					String sql = "UPDATE jrun_trades SET " + tradeaidadd
							+ " account='" + seller + "', subject='"
							+ item_name + "', price='" + item_price
							+ "', amount='" + item_number + "', quality='"
							+ item_quality + "', locus='" + item_locus
							+ "',transport='" + transport + "', ordinaryfee='"
							+ postage_mail + "', expressfee='"
							+ postage_express + "', emsfee='" + postage_ems
							+ "', itemtype='" + item_type + "', expiration='"
							+ expiration + "', closed='" + closed
							+ "',costprice='" + item_costprice
							+ "' WHERE tid='" + post.getTid() + "' AND pid='"
							+ pid + "'";
					dataBaseService.runQuery(sql, true);
				}
				mapurl = "viewthread.jsp?do=tradeinfo&tid=" + thread.getTid()
						+ "&pid=" + pid;
			}
		}
		if(isfirst.equals("yes")){
			String showtype = fileupload.getParameter("showtype");
			thread.setShowType(Byte.parseByte(showtype));
		}
		threadService.updateThreads(thread);
		postService.updatePosts(post);
		String attamessage = uploadAttachment(request, pid, thread.getTid(),
				thread.getFid(), Common.addslashes(post.getMessage()),
				filelist, forums.getDisablewatermark(), fileupload);
		filelist = null;
		if (!attamessage.equals("")) {
			request.setAttribute("resultInfo", attamessage);
			return mapping.findForward("showMessage");
		}
		attamessage = updateAttachment(request, pid, thread.getTid(), thread
				.getFid(), post.getAuthorid(), fileupload, fields, forums
				.getDisablewatermark());
		if (!attamessage.equals("")) {
			request.setAttribute("resultInfo", attamessage);
			return mapping.findForward("showMessage");
		}
		if (thread.getLastpost().equals(post.getDateline())
				&& ((oldanonymous <= 0 && thread.getLastposter().equals(
						post.getAuthor())) || (oldanonymous > 0 && ""
						.equals(thread.getLastposter())))
				&& oldanonymous != isanonymous) {
			dataBaseService.runQuery("UPDATE jrun_threads SET lastposter='"
					+ (isanonymous > 0 ? "" : Common.addslashes(post
							.getAuthor())) + "' WHERE tid='" + post.getTid()
					+ "'", true);
		}
		if (post.getAuthorid() != uid && uid != 0) {
			Common.updatemodworks(settings, uid, timestamp, "EDT", 1);
		}
		if (Common.isshowsuccess(request.getSession(), "post_edit_succeed")) {
			if (!mapurl.equals("")) {
				Common.requestforward(response, mapurl);
			} else {
				Common.requestforward(response, "viewthread.jsp?tid="
						+ thread.getTid() + "&page="
						+ fileupload.getParameter("page"));
			}
			return null;
		} else {
			request.setAttribute("successInfo", getMessage(request,
					"post_edit_succeed", post.getFid() + ""));
			request.setAttribute("requestPath", "viewthread.jsp?tid="
					+ thread.getTid() + "&page="
					+ fileupload.getParameter("page"));
			if (!mapurl.equals("")) {
				request.setAttribute("requestPath", mapurl);
			}
			return mapping.findForward("showMessage");
		}
	}

	@SuppressWarnings("unchecked")
	public void common(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> settings,
			Map<String, String> usergroups, Forumfields forumfield,
			Forums forum, int special, Map<String, String> access,
			FileUploadUtil fileupload) {
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		HttpSession session = request.getSession();
		Members member = (Members) session.getAttribute("user");
		MessageResources resources = getResources(request);
		Locale locale = getLocale(request);
		String message = Common.periodscheck(settings.get("postbanperiods"),
				Byte.valueOf(usergroups.get("disableperiodctrl")), timestamp,
				settings.get("timeoffset"), resources, locale);
		if (message != null) {
			request.setAttribute("show_message", message);
			try {
				request.getRequestDispatcher(
						"/templates/default/nopermission.jsp").forward(request,
						response);
			} catch (Exception e) {
			}
		}
		int newbiespan = Common.toDigit(settings.get("newbiespan"));
		byte jsprun_adminid = (Byte) session.getAttribute("jsprun_adminid");
		int lastpost = member != null ? member.getLastpost() : 0;
		if (jsprun_adminid == 0 && newbiespan > 0
				&& (lastpost == 0 || timestamp - lastpost < newbiespan * 3600)) {
			int regdate = member != null ? member.getRegdate() : 0;
			if (timestamp - regdate < newbiespan * 3600) {
				request.setAttribute("errorInfo", getMessage(request,
						"post_newbiespan", String.valueOf(newbiespan)));
				try {
					request.getRequestDispatcher("/showmessage.jsp").forward(
							request, response);
				} catch (Exception e) {
				}
			}
		}
		request.setAttribute("forum", forum);
		request.setAttribute("styleid", forum.getStyleid() > 0 ? forum
				.getStyleid() : null);
		request.setAttribute("editorid", "posteditor");
		String editoroptions = Integer.toBinaryString(Common.range(Common
				.intval(settings.get("editoroptions")), 255, 0));
		editoroptions = editoroptions.length() >= 2 ? editoroptions : "0"
				+ editoroptions;
		String editormodes = fileupload == null ? request
				.getParameter("editormode") : fileupload
				.getParameter("editormode");
		byte editormode = editormodes != null && !editormodes.equals("") ? Byte
				.valueOf(editormodes) : member != null ? member.getEditormode()
				: 0;
		request.setAttribute("editormode", editormode == 2 ? String
				.valueOf(editoroptions.charAt(0)) : editormode);
		request.setAttribute("allowswitcheditor", String.valueOf(editoroptions
				.charAt(1)));
		Map<String, String> faqs = (Map<String, String>) request
				.getAttribute("faqs");
		request.setAttribute("faqs", dataParse.characterParse(faqs.get("faqs"),
				false));
		faqs = null;
		Map<String, String> bbcodes_display = (Map<String, String>) request
				.getAttribute("bbcodes_display");
		request.setAttribute("bbcodes_display", dataParse.characterParse(
				bbcodes_display.get("bbcodes_display"), false));
		bbcodes_display = null;
		boolean advanceeditor = special > 0 ? false : true;
		request.setAttribute("advanceeditor", advanceeditor);
		request.setAttribute("fid", forum.getFid());
		request.setAttribute("special", special);
		String[] fontoptions={"仿宋_GB2312", "黑体", "楷体_GB2312", "宋体", "新宋体","微软雅黑", "Trebuchet MS", "Tahoma", "Arial", "Impact", "Verdana","Times New Roman" };
		request.setAttribute("fontoptions", fontoptions);
		request.setAttribute("coloroptions", Common.COLOR_OPTIONS);
		request.setAttribute("maxattachsize", Integer.valueOf(usergroups
				.get("maxattachsize")) / 1000);
		request.setAttribute("sticktopic", fileupload == null ? request
				.getParameter("sticktopic") : fileupload
				.getParameter("sticktopic"));
		Map extcredit = (Map) dataParse.characterParse(
				settings.get("extcredits"), true).get(
				Integer.valueOf(settings.get("creditstrans")));
		request.setAttribute("extcredit", extcredit);
		int allowpost = Integer.valueOf(usergroups.get("allowpost"));
		boolean allowpostpoll = allowpost > 0
				&& "1".equals(usergroups.get("allowpostpoll"))
				&& (forum.getAllowpostspecial() & 1) > 0;
		boolean allowposttrade = allowpost > 0
				&& "1".equals(usergroups.get("allowposttrade"))
				&& (forum.getAllowpostspecial() & 2) > 0;
		boolean allowpostreward = allowpost > 0
				&& "1".equals(usergroups.get("allowpostreward"))
				&& (forum.getAllowpostspecial() & 4) > 0 && extcredit != null;
		boolean allowpostactivity = allowpost > 0
				&& "1".equals(usergroups.get("allowpostactivity"))
				&& (forum.getAllowpostspecial() & 8) > 0;
		boolean allowpostdebate = allowpost > 0
				&& "1".equals(usergroups.get("allowpostdebate"))
				&& (forum.getAllowpostspecial() & 16) > 0;
		boolean allowpostvideo = allowpost > 0
				&& "1".equals(usergroups.get("allowpostvideo"))
				&& (forum.getAllowpostspecial() & 32) > 0
				&& "1".equals(settings.get("videoopen"));
		boolean allowanonymous = forum.getAllowanonymous() > 0
				|| "1".equals(usergroups.get("allowanonymous"));
		request.setAttribute("allowpostpoll", allowpostpoll);
		request.setAttribute("allowposttrade", allowposttrade);
		request.setAttribute("allowpostreward", allowpostreward);
		request.setAttribute("allowpostactivity", allowpostactivity);
		request.setAttribute("allowpostdebate", allowpostdebate);
		request.setAttribute("allowpostvideo", allowpostvideo);
		request.setAttribute("allowanonymous", allowanonymous);
		boolean allowpostattach = (access != null && !Common.empty(access
				.get("allowpostattach")))
				|| (forumfield.getPostattachperm().equals("") && usergroups
						.get("allowpostattach").compareTo("0") > 0)
				|| (!forumfield.getPostattachperm().equals("") && Common
						.forumperm(forumfield.getPostattachperm(), Short
								.valueOf(usergroups.get("groupid")),
								member != null ? member.getExtgroupids() : ""));
		request.setAttribute("allowpostattach", allowpostattach);
		request.setAttribute("attachextensions", !forumfield
				.getAttachextensions().equals("") ? forumfield
				.getAttachextensions() : usergroups.get("attachextensions"));
		request.setAttribute("ismoderator", Common.ismoderator(forum.getFid(),
				member));
		boolean iscircle = !settings.get("supe_status").equals("0")
				&& !settings.get("supe_circlestatus").equals("0")
				&& forum.getStatus() == 2;
		request.setAttribute("iscircle", iscircle);
		Map seccodedata = dataParse.characterParse(settings.get("seccodedata"),
				false);
		int minposts = Common.toDigit(String.valueOf(seccodedata
				.get("minposts")));
		int seccodestatus = Common.range(Common.intval(settings
				.get("seccodestatus")), 255, 0);
		boolean seccodecheck = (seccodestatus & 4) > 0
				&& (minposts <= 0 || member.getPosts() < minposts);
		request.setAttribute("seccodecheck", seccodecheck);
		request.setAttribute("seccodedata", seccodedata);
		seccodedata = null;
		Map secqaa = dataParse.characterParse(settings.get("secqaa"), false);
		minposts = Common.toDigit(String.valueOf(secqaa.get("minposts")));
		int secqaastatus = (Integer) secqaa.get("status");
		secqaa = null;
		request.setAttribute("secqaacheck", (secqaastatus & 2) > 0
				&& (minposts <= 0 || member.getPosts() < minposts));
		String previewpost = fileupload == null ? request
				.getParameter("previewpost") : fileupload.getParameter("");
		request.setAttribute("previewdisplay", previewpost != null ? ""
				: "none");
		request.setAttribute("showpreview", fileupload == null ? request
				.getParameter("showpreview") : fileupload
				.getParameter("showpreview"));
		Map<String, Map<String, String>> smileytypeMap = new TreeMap<String, Map<String, String>>();
		Map<String, Map<String, Map<String, String>>> smilies_displays = new TreeMap<String, Map<String, Map<String, String>>>();
		Map<String, String> smileytypes = (Map<String, String>) request
				.getAttribute("smileytypes");
		Map<String, String> smilies_display = (Map<String, String>) request
				.getAttribute("smilies_display");
		if (smileytypes != null && smileytypes.size() > 0) {
			Set<String> typeids = smileytypes.keySet();
			for (String typeid : typeids) {
				smileytypeMap.put(typeid, dataParse.characterParse(smileytypes
						.get(typeid), true));
				smilies_displays.put(typeid, dataParse.characterParse(
						smilies_display.get(typeid), true));
			}
		}
		smileytypes = null;
		smilies_display = null;
		request.setAttribute("smileytypeMap",
				smileytypeMap.size() > 0 ? smileytypeMap : null);
		request.setAttribute("smilies_displays",
				smilies_displays.size() > 0 ? smilies_displays : null);
		smileytypeMap = null;
		smilies_displays = null;
	}

	private String calcSparetime(int expiration, int timestamp) {
		int sparetime = expiration - timestamp;
		if (expiration == 0) {
			return 0 + "";
		}
		if (sparetime < 0) {
			return "poll_finish";
		} else {
			int day = sparetime / 86400;
			return day + "";
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, String[]> getCreditsName(String extcredits) {
		Map extcreditMap = dataParse.characterParse(extcredits, true);
		Iterator it = extcreditMap.keySet().iterator();
		String extname[] = new String[8];
		String extunit[] = new String[8];
		while (it.hasNext()) {
			Object key = it.next();
			Map extMap = (Map) extcreditMap.get(key);
			extname[Integer.valueOf(key.toString()) - 1] = (String) extMap
					.get("title")
					+ " ";
			extunit[Integer.valueOf(key.toString()) - 1] = " "
					+ (String) extMap.get("unit");
		}
		Map<String, String[]> creditmap = new HashMap<String, String[]>();
		creditmap.put("name", extname);
		creditmap.put("unit", extunit);
		return creditmap;
	}

	private int convertInt(String s) {
		int count = 0;
		try {
			count = Integer.valueOf(s);
		} catch (Exception e) {
		}
		return count;
	}

	private String checkflood(HttpServletRequest request, int uid,
			int timestamp, int lastpost, int floodctrl, int disablepostctrl,
			int maxpostsperhour) {
		String floodmsg = null;
		if (disablepostctrl == 0 && uid > 0) {
			floodmsg = floodctrl > 0 && (timestamp - floodctrl) <= lastpost ? getMessage(
					request, "post_flood_ctrl_water", floodctrl + "")
					: null;
			if (floodmsg == null && maxpostsperhour > 0) {
				List<Map<String, String>> count = dataBaseService
						.executeQuery("SELECT COUNT(*) count from jrun_posts WHERE authorid='"
								+ uid + "' AND dateline>" + (timestamp - 3600));
				int userposts = (count != null && count.size() > 0 ? Integer
						.valueOf(count.get(0).get("count")) : 0);
				floodmsg = userposts >= maxpostsperhour ? getMessage(request,
						"thread_maxpostsperhour_invalid", maxpostsperhour + "")
						: null;
			}
		}
		return floodmsg;
	}

	@SuppressWarnings("unchecked")
	private boolean censormod(String message, HttpServletRequest request) {
		Map<String, String> censor = (Map<String, String>) request
				.getAttribute("censor");
		String mod = censor.get("mod");
		return !Common.empty(mod) && Common.matches(message, censor.get("mod"));
	}

	@SuppressWarnings("unchecked")
	private String uploadAttachment(HttpServletRequest request, int pid,
			int tid, int fid, String message, List filelist,
			int disablewatermark, FileUploadUtil fileupload) {
		if (filelist.size() > 0) {
			HttpSession session = request.getSession();
			int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
			String uasename = (String) session.getAttribute("jsprun_userss");
			String ip = Common.get_onlineip(request);
			Map<String, String> usergroups = (Map<String, String>) request
					.getAttribute("usergroups");
			Map<String, String> settings = ForumInit.settings;
			String timeoffset = settings.get("timeoffset");
			String attachurl = settings.get("attachurl");
			String attachsave = settings.get("attachsave");
			String authorkey = settings.get("authkey");
			String special = fileupload.getParameter("special");
			Map<String, String> ftpmap = dataParse.characterParse(settings
					.get("ftp"), false);
			int timestamp = (Integer) (request.getAttribute("timestamp"));
			String attachperm[] = fileupload.getParameterValues("attachperm[]");
			String attachprice[] = fileupload
					.getParameterValues("attachprice[]");
			String attachdesc[] = fileupload.getParameterValues("attachdesc[]");
			int length = filelist.size() - 1;
			int[] aids = (int[]) filelist.get(length);
			String searcharray[] = new String[length];
			String pregarray[] = new String[length];
			String replacearray[] = new String[length];
			List<Attachments> attalist = new ArrayList<Attachments>();
			int count = 0;
			if (attachdesc != null) {
				SimpleDateFormat simpleDateFormat_1 = Common
						.getSimpleDateFormat("yyyyMMdd", timeoffset);
				SimpleDateFormat simpleDateFormat_2 = Common
						.getSimpleDateFormat("yyMM", timeoffset);
				SimpleDateFormat simpleDateFormat_3 = Common
						.getSimpleDateFormat("yyMMdd", timeoffset);
				for (int i = 0; i < length; i++) {
					FileItem fileItem = (FileItem) filelist.get(i);
					if (fileItem != null && fileItem.getSize() > 0) {
						String filename = fileItem.getName();
						int start = filename.lastIndexOf('\\');
						filename = filename.substring(start + 1);
						int last = filename.lastIndexOf(".");
						String filetype = filename.substring(last + 1);
						filetype = filetype.toLowerCase();
						filetype = filetype
								.replaceAll(
										"php|phtml|php3|php4|jsp|exe|dll|asp|cer|asa|shtml|shtm|aspx|asax|cgi|fcgi|pl",
										"attach");
						String targetname = Common.gmdate(simpleDateFormat_1,
								timestamp)
								+ Md5Token.getInstance().getLongToken(
										filename
												+ Math.rint(System
														.currentTimeMillis()))
								+ "." + filetype;
						String filedir = "month_"
								+ Common.gmdate(simpleDateFormat_2, timestamp);
						if (attachsave.equals("0")) {
							filedir = "";
						} else if (attachsave.equals("1")) {
							filedir = "forumid_" + fid;
						} else if (attachsave.equals("2")) {
							filedir = "ext_" + filetype;
						} else if (attachsave.equals("4")) {
							filedir = "day_"
									+ Common.gmdate(simpleDateFormat_3,
											timestamp);
						}
						String filesubdir = servlet.getServletContext()
								.getRealPath("/")
								+ "./" + attachurl + "/" + filedir;
						File subdirfile = new File(filesubdir);
						if (!subdirfile.exists()) {
							subdirfile.mkdir();
						}
						String targetpath = servlet.getServletContext()
								.getRealPath("/")
								+ attachurl + "/" + filedir + "/" + targetname;
						if (!FileUploadUtil.write2file(fileItem, new File(
								targetpath))) {
							return getMessage(request,
									"post_attachment_save_error");
						}
						boolean thumflag = writeWaterOrthumb(filetype,
								targetpath, disablewatermark, settings);
						Attachments atta = new Attachments();
						int index = targetpath.indexOf(attachurl);
						String attachment = targetpath.substring(index
								+ attachurl.length() + 1);
						atta.setAttachment(attachment);
						atta.setDateline(timestamp);
						int num = special != null && special.equals("2") ? i - 1
								: i;
						String attadesc = attachdesc.length > num ? attachdesc[num]
								: "";
						if (attadesc.length() > 80) {
							attadesc = attadesc.substring(0, 80);
						}
						atta.setDescription(attadesc);
						atta.setDownloads(0);
						if (filename.length() > 80) {
							filename = filename.substring(0, 80);
						}
						atta.setFilename(filename);
						File files = new File(targetpath);
						atta.setFilesize(Common.toDigit(files.length() + ""));
						atta.setFiletype(getAttachtype(filetype));
						if (filetype.matches("jpg|jpeg|gif|png|bmp")) {
							atta.setIsimage(Byte.valueOf("1"));
						} else {
							atta.setIsimage(Byte.valueOf("0"));
						}
						atta.setPid(pid);
						String maxprice = usergroups.get("maxprice");
						if (attachprice != null) {
							String attaprice = attachprice.length > num ? attachprice[num]
									: "0";
							atta.setPrice((short) Common.range(Common
									.intval(attaprice), Integer
									.valueOf(maxprice), 0));
						} else {
							atta.setPrice(Short.valueOf("0"));
						}
						if (attachperm != null) {
							String attaperm = attachperm.length > num ? attachperm[num]
									: "0";
							atta.setReadperm((short) Common.range(Common
									.intval(attaperm), 255, 0));
						} else {
							atta.setReadperm(Short.valueOf("0"));
						}
						if (thumflag) {
							atta.setThumb(Byte.valueOf("1"));
						} else {
							atta.setThumb(Byte.valueOf("0"));
						}
						atta.setRemote(Byte.valueOf(ftpupload(targetpath, atta,
								ftpmap, authorkey, filetype, uasename, ip,
								timestamp)
								+ ""));
						if (atta.getRemote() == 2) {
							attalist.clear();
							return getMessage(request,
									"post_attachment_remote_save_error");
						}
						atta.setTid(tid);
						atta.setUid(jsprun_uid);
						attalist.add(atta);
						count++;
						searcharray[count - 1] = "\\[local\\]" + aids[i]
								+ "\\[\\/local\\]";
						pregarray[count - 1] = "\\[localimg=\\d{1,3},\\d{1,3}\\]"
								+ aids[i] + "\\[\\/localimg\\]";
						replacearray[count - 1] = "";
					}
				}
				for (int i = 0; i < attalist.size(); i++) {
					int aid = postService.insertAttachments(attalist.get(i));
					replacearray[i] = "[attach]" + aid + "[/attach]";
					;
				}
				if (attalist.size() > 0) {
					dataBaseService.runQuery(
							"update jrun_posts set attachment=1 where pid="
									+ pid, true);
					dataBaseService.runQuery(
							"update jrun_threads set attachment=1 where tid="
									+ tid, true);
				}
				attalist.clear();
				attalist = null;
				for (int i = 0; i < searcharray.length; i++) {
					if (searcharray[i] != null) {
						message = message.replaceAll(searcharray[i],
								replacearray[i]);
						message = message.replaceAll(pregarray[i],
								replacearray[i]);
					}
				}
				dataBaseService.runQuery("update jrun_posts set message='"
						+ message + "' where pid=" + pid, true);
				updateattacredits((short) fid, jsprun_uid, count, settings);
				Common.updateMember(request.getSession(), jsprun_uid);
				filelist = null;
			}
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	private String uploadTradeAttachment(HttpServletRequest request, int pid,
			int tid, int fid, FileItem fileItem, int disablewatermark) {
		HttpSession session = request.getSession();
		int jsprun_uid = (Integer) session.getAttribute("jsprun_uid");
		String uasename = (String) session.getAttribute("jsprun_userss");
		String ip = Common.get_onlineip(request);
		Map<String, String> settings = ForumInit.settings;
		String timeoffset = settings.get("timeoffset");
		String attachurl = settings.get("attachurl");
		String attachsave = settings.get("attachsave");
		String authorkey = settings.get("authkey");
		Map<String, String> ftpmap = dataParse.characterParse(settings
				.get("ftp"), false);
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		if (fileItem != null && fileItem.getSize() > 0) {
			String filename = fileItem.getName();
			int start = filename.lastIndexOf('\\');
			filename = filename.substring(start + 1);
			int last = filename.lastIndexOf(".");
			String filetype = filename.substring(last + 1);
			filetype = filetype.toLowerCase();
			filetype = filetype
					.replaceAll(
							"php|phtml|php3|php4|jsp|exe|dll|asp|cer|asa|shtml|shtm|aspx|asax|cgi|fcgi|pl",
							"attach");
			String targetname = Common
					.gmdate("yyyyMMdd", timestamp, timeoffset)
					+ Md5Token.getInstance().getLongToken(
							filename + Math.rint(System.currentTimeMillis()))
					+ "." + filetype;
			String filedir = "month_"
					+ Common.gmdate("yyMM", timestamp, timeoffset);
			if (attachsave.equals("0")) {
				filedir = "";
			} else if (attachsave.equals("1")) {
				filedir = "forumid_" + fid;
			} else if (attachsave.equals("2")) {
				filedir = "ext_" + filetype;
			} else if (attachsave.equals("4")) {
				filedir = "day_"
						+ Common.gmdate("yyMMdd", timestamp, timeoffset);
			}
			String filesubdir = servlet.getServletContext().getRealPath("/")
					+ "./" + attachurl + "/" + filedir;
			File subdirfile = new File(filesubdir);
			if (!subdirfile.exists()) {
				subdirfile.mkdir();
			}
			String targetpath = servlet.getServletContext().getRealPath("/")
					+ attachurl + "/" + filedir + "/" + targetname;
			if (!FileUploadUtil.write2file(fileItem, new File(targetpath))) {
				return getMessage(request, "post_attachment_save_error");
			}
			boolean thumflag = writeWaterOrthumb(filetype, targetpath,
					disablewatermark, settings);
			Attachments atta = new Attachments();
			int index = targetpath.indexOf(attachurl);
			String attachment = targetpath.substring(index + attachurl.length()
					+ 1);
			atta.setAttachment(attachment);
			atta.setDateline(timestamp);
			atta.setDescription("");
			atta.setDownloads(0);
			if (filename.length() > 80) {
				filename = filename.substring(0, 80);
			}
			atta.setFilename(filename);
			File files = new File(targetpath);
			atta.setFilesize(Common.toDigit(files.length() + ""));
			atta.setFiletype(getAttachtype(filetype));
			if (filetype.matches("jpg|jpeg|gif|png|bmp")) {
				atta.setIsimage(Byte.valueOf("1"));
			} else {
				atta.setIsimage(Byte.valueOf("0"));
			}
			atta.setPid(pid);
			atta.setPrice(Short.valueOf("0"));
			atta.setReadperm(Short.valueOf("0"));
			if (thumflag) {
				atta.setThumb(Byte.valueOf("1"));
			} else {
				atta.setThumb(Byte.valueOf("0"));
			}
			atta.setRemote(Byte.valueOf(ftpupload(targetpath, atta, ftpmap,
					authorkey, filetype, uasename, ip, timestamp)
					+ ""));
			if (atta.getRemote() == 2) {
				return getMessage(request, "post_attachment_remote_save_error");
			}
			atta.setTid(tid);
			atta.setUid(jsprun_uid);
			int aid = postService.insertAttachments(atta);
			updateattacredits((short) fid, jsprun_uid, 1, settings);
			Common.updateMember(request.getSession(), jsprun_uid);
			return aid + "";
		}
		return "0";
	}

	@SuppressWarnings("unchecked")
	private String checkAttachment(HttpServletRequest request, List filelist,
			Forumfields forumfield) {
		if (filelist.size() > 0) {
			int jsprun_uid = (Integer) request.getSession().getAttribute(
					"jsprun_uid");
			Map<String, String> usergroups = (Map<String, String>) request
					.getAttribute("usergroups");
			String attachextensions = forumfield.getAttachextensions().equals(
					"") ? usergroups.get("attachextensions") : forumfield
					.getAttachextensions();
			String maxattachsize = usergroups.get("maxattachsize");
			String maxsizeperday = usergroups.get("maxsizeperday");
			int timestamp = (Integer) (request.getAttribute("timestamp"));
			int size = filelist.size() - 1;
			long sumsize = 0;
			for (int i = 0; i < size; i++) {
				FileItem fileItem = (FileItem) filelist.get(i);
				boolean flag = false;
				long filesize = fileItem != null ? fileItem.getSize() : 0;
				if (filesize > 0) {
					String filename = fileItem.getName();
					sumsize = sumsize + filesize;
					String filetype = filename.substring(filename
							.lastIndexOf(".") + 1);
					filetype = filetype.toLowerCase();
					if (attachextensions != null) {
						String[] options = attachextensions.split(",");
						for (int j = 0; j < options.length; j++) {
							if (filetype.equals(options[j].trim())) {
								flag = true;
							}
						}
					}
					if (attachextensions.equals("")) {
						flag = true;
					}
					if (!flag) {
						filelist = null;
						return getMessage(request,
								"post_attachment_ext_notallowed_post");
					}
				}
			}
			if (Common.toDigit(maxattachsize) != 0 && sumsize != 0) {
				if (sumsize > Common.toDigit(maxattachsize)) {
					filelist = null;
					return getMessage(request, "post_attachment_toobig");
				}
			}
			List<Map<String, String>> sumsizelist = dataBaseService
					.executeQuery("select sum(filesize) as size from jrun_attachments as a where a.uid="
							+ jsprun_uid
							+ " and a.dateline+86400>="
							+ timestamp);
			String todaysize = "0";
			if (sumsizelist != null && sumsizelist.size() > 0) {
				todaysize = sumsizelist.get(0).get("size");
			}
			if (convertInt(maxsizeperday) != 0 && sumsize != 0) {
				if (sumsize > (convertInt(maxsizeperday) - convertInt(todaysize))) {
					filelist = null;
					return getMessage(request, "post_attachment_quota_exceed",
							todaysize);
				}
			}
			List<Map<String, String>> attatype = dataBaseService
					.executeQuery("select extension,maxsize from jrun_attachtypes");
			if (attatype != null && attatype.size() > 0) {
				for (int i = 0; i < size; i++) {
					FileItem fileItem = (FileItem) filelist.get(i);
					long filesize = fileItem != null ? fileItem.getSize() : 0;
					if (filesize > 0) {
						String filename = fileItem.getName();
						String filetype = filename.substring(filename
								.lastIndexOf(".") + 1);
						filetype = filetype.toLowerCase();
						for (Map<String, String> atta : attatype) {
							if (filetype.equals(atta.get("extension")
									.toLowerCase())) {
								if (atta.get("maxsize").equals("0")) {
									filelist = null;
									return getMessage(request,
											"post_attachment_ext_notallowed_post");
								} else if (filesize > Common.toDigit(atta
										.get("maxsize"))) {
									filelist = null;
									return getMessage(request,
											"post_attachment_type_toobig", "."
													+ filetype,
											Common.sizeFormat(Long.valueOf(atta
													.get("maxsize"))));
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private String updateAttachment(HttpServletRequest request, int pid,
			int tid, int fid, int uid, FileUploadUtil fileupload,
			Forumfields forumfield, int disablewatermark) {
		String deleteaid[] = fileupload.getParameterValues("deleteaid[]");
		HttpSession session = request.getSession();
		String uasename = (String) session.getAttribute("jsprun_userss");
		String ip = Common.get_onlineip(request);
		Map<String, String> usergroups = (Map<String, String>) request
				.getAttribute("usergroups");
		String maxattachsize = usergroups.get("maxattachsize");
		Map<String, String> settings = ForumInit.settings;
		String timeoffset = settings.get("timeoffset");
		String attachurl = settings.get("attachurl");
		String attachsave = settings.get("attachsave");
		String authorkey = settings.get("authkey");
		Map<String, String> ftpmap = dataParse.characterParse(settings
				.get("ftp"), false);
		if (deleteaid != null) {
			StringBuffer aidbuffer = new StringBuffer();
			for (String aid : deleteaid) {
				aidbuffer.append("," + aid);
			}
			int post_attachment = 0;
			List<Map<String, String>> attalist = dataBaseService
					.executeQuery("select attachment,thumb,remote from jrun_attachments where aid in ("
							+ aidbuffer.substring(1) + ")");
			String path = servlet.getServletContext().getRealPath("/")
					+ settings.get("attachdir") + "/";
			for (Map<String, String> atta : attalist) {
				post_attachment++;
				Common.dunlink(atta.get("attachment"), Byte.valueOf(atta
						.get("thumb")), Byte.valueOf(atta.get("remote")), path);
			}
			dataBaseService.runQuery(
					"delete from jrun_attachments where aid in ("
							+ aidbuffer.substring(1) + ")", true);
			if (post_attachment > 0) {
				String creditsformula = settings.get("creditsformula");
				Map creditspolicys = dataParse.characterParse(settings
						.get("creditspolicy"), false);
				Map postcredits = dataParse.characterParse(forumfield
						.getPostattachcredits(), false);
				if (postcredits == null || postcredits.size() <= 0) {
					postcredits = (Map<Integer, Integer>) creditspolicys
							.get("postattach");
				}
				Common
						.updatepostcredits("-", uid, post_attachment,
								postcredits);
				Common.updatepostcredits(uid, creditsformula);
			}
		}
		List<Map<String, String>> attalist = dataBaseService
				.executeQuery("select * from  jrun_attachments as a where a.pid="
						+ pid);
		if (attalist != null && attalist.size() > 0) {
			int timestamp = (Integer) (request.getAttribute("timestamp"));
			SimpleDateFormat simpleDateFormat_1 = Common.getSimpleDateFormat(
					"yyyyMMdd", timeoffset);
			SimpleDateFormat simpleDateFormat_2 = Common.getSimpleDateFormat(
					"yyMM", timeoffset);
			SimpleDateFormat simpleDateFormat_3 = Common.getSimpleDateFormat(
					"yyMMdd", timeoffset);
			String attachextensions = forumfield.getAttachextensions().equals(
					"") ? usergroups.get("attachextensions") : forumfield
					.getAttachextensions();
			for (int i = 0; i < attalist.size(); i++) {
				StringBuffer sql = new StringBuffer(
						"update jrun_attachments set ");
				Map<String, String> attament = attalist.get(i);
				String aid = attament.get("aid");
				String attachperm = fileupload.getParameter("attachperm[" + aid
						+ "]");
				String attachprice = fileupload.getParameter("attachprice["
						+ aid + "]");
				String attachdesc = fileupload.getParameter("attachdesc[" + aid
						+ "]");
				FileItem fileitem = fileupload.getFileItem("uploadFile[" + aid
						+ "]");
				if (fileitem != null && fileitem.getSize() > 0) {
					String filename = fileitem.getName();
					int start = filename.lastIndexOf('\\');
					filename = filename.substring(start + 1);
					int last = filename.lastIndexOf(".");
					String filetype = filename.substring(last + 1);
					filetype = filetype.toLowerCase();
					boolean flag = false;
					if (attachextensions != null) {
						String[] options = attachextensions.split(",");
						for (int j = 0; j < options.length; j++) {
							if (filetype.equals(options[j].trim())) {
								flag = true;
							}
						}
					}
					if (attachextensions.equals("")) {
						flag = true;
					}
					if (flag) {
						if (convertInt(maxattachsize) == 0
								|| fileitem.getSize() < convertInt(maxattachsize)) {
							filetype = filetype
									.replaceAll(
											"php|phtml|php3|php4|jsp|exe|dll|asp|cer|asa|shtml|shtm|aspx|asax|cgi|fcgi|pl",
											"attach");
							String targetname = Common.gmdate(
									simpleDateFormat_1, timestamp)
									+ Md5Token
											.getInstance()
											.getLongToken(
													filename
															+ Math
																	.rint(System
																			.currentTimeMillis()))
									+ "." + filetype;
							String filedir = "month_"
									+ Common.gmdate(simpleDateFormat_2,
											timestamp);
							if (attachsave.equals("0")) {
								filedir = "";
							} else if (attachsave.equals("1")) {
								filedir = "forumid_" + fid;
							} else if (attachsave.equals("2")) {
								filedir = "ext_" + filetype;
							} else if (attachsave.equals("4")) {
								filedir = "day_"
										+ Common.gmdate(simpleDateFormat_3,
												timestamp);
							}
							String filesubdir = servlet.getServletContext()
									.getRealPath("/")
									+ attachurl + "/" + filedir;
							File subdirfile = new File(filesubdir);
							if (!subdirfile.exists()) {
								subdirfile.mkdir();
							}
							String targetpath = servlet.getServletContext()
									.getRealPath("/")
									+ attachurl
									+ "/"
									+ filedir
									+ "/"
									+ targetname;
							if (!FileUploadUtil.write2file(fileitem, new File(
									targetpath))) {
								return getMessage(request,
										"post_attachment_save_error");
							}
							boolean thumflag = writeWaterOrthumb(filetype,
									targetpath, disablewatermark, settings);
							String attachment = attament.get("attachment");
							String delpath = servlet.getServletContext()
									.getRealPath("/")
									+ attachment;
							File file = new File(delpath);
							file.delete();
							int index = targetpath.indexOf(attachurl);
							String attachments = targetpath.substring(attachurl
									.length()
									+ index + 1);
							File filesn = new File(targetpath);
							sql.append("attachment='" + attachments
									+ "',dateline='" + timestamp
									+ "',filename='" + filename
									+ "',filesize='" + filesn.length() + "',");
							String filetypes = getAttachtype(filetype);
							sql.append("filetype='" + filetypes + "',");
							byte isimage = 0;
							byte thumb = 0;
							int remote = 0;
							if (filetype.matches("jpg|jpeg|gif|png|bmp")) {
								isimage = 1;
							}
							if (thumflag) {
								thumb = 1;
							}
							Attachments atta = new Attachments();
							atta.setFilesize((int) filesn.length());
							atta.setAttachment(attachments);
							atta.setThumb(thumb);
							remote = ftpupload(targetpath, atta, ftpmap,
									authorkey, filetype, uasename, ip,
									timestamp);
							sql.append("isimage='" + isimage + "',thumb='"
									+ thumb + "',remote='" + remote + "',");
						}
					}
				}
				if (attachperm != null) {
					int readperm = Common.range(Common.intval(attachperm), 255,
							0);
					sql.append("readperm='" + readperm + "',");
				}
				if (attachprice != null) {
					String maxprices = usergroups.get("maxprice");
					int price = Common.range(Common.intval(attachprice),
							Integer.valueOf(maxprices), 0);
					sql.append("price='" + price + "',");
				}
				if (attachdesc != null) {
					if (attachdesc.length() > 80) {
						attachdesc = attachdesc.substring(0, 80);
					}
					sql.append("description='" + attachdesc + "',");
				}
				String updatesql = sql.toString();
				if (updatesql.endsWith(",")) {
					dataBaseService.runQuery(updatesql.substring(0, updatesql
							.length() - 1)
							+ " where aid='" + aid + "'", true);
				}
			}

		} else {
			dataBaseService
					.runQuery("update jrun_posts set attachment=0 where pid="
							+ pid, true);
			List<Map<String, String>> isattalist = dataBaseService
					.executeQuery("select count(*) count from jrun_attachments where tid = '"
							+ tid + "'");
			if (isattalist == null || isattalist.size() <= 0
					|| "0".equals(isattalist.get(0).get("count"))) {
				dataBaseService
						.runQuery(
								"update jrun_threads set attachment=0 where tid="
										+ tid, true);
			}
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	private void updateattacredits(short fid, int uid, int count,
			Map<String, String> settings) {
		Forumfields forumfield = forumfieldService.findById(fid);
		Map<Integer, Integer> postcredits = dataParse.characterParse(forumfield
				.getPostattachcredits(), false);
		if (postcredits == null || postcredits.size() <= 0) {
			Map creditspolicys = dataParse.characterParse(settings
					.get("creditspolicy"), false);
			postcredits = (Map<Integer, Integer>) creditspolicys
					.get("postattach");
			creditspolicys = null;
		}
		if (uid != 0 && count > 0) {
			Common.updatepostcredits("+", uid, count, postcredits);
			Common.updatepostcredits(uid, settings.get("creditsformula"));
		}
		postcredits = null;
	}

	private int checkDateFormatAndValite(String strDateTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date ndate = format.parse(strDateTime);
			String str = format.format(ndate);
			if (str.equals(strDateTime)) {
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	private String checklowerlimit(HttpServletRequest request, Map extcredits,
			Map<Integer, Integer> creditsarray, boolean checklowerlimits,
			Members member, int coef) {
		String message = null;
		if (creditsarray != null && creditsarray.size() > 0 && checklowerlimits) {
			Set<Integer> keys = creditsarray.keySet();
			for (Integer key : keys) {
				int lowerlimit = Integer.parseInt(creditsarray.get(key) + "");
				int addcredits = lowerlimit * coef;
				int extc = (Integer) Common.getValues(member, "extcredits"
						+ key);
				if (addcredits < 0
						&& (extc < lowerlimit || (extc + addcredits) < lowerlimit)) {
					Map extcredit = (Map) extcredits.get(key);
					String title = extcredit.get("title") != null ? extcredit
							.get("title").toString() : "";
					String unit = extcredit.get("unit") != null ? extcredit
							.get("unit").toString() : "";
					if (coef == 1) {
						message = getMessage(request,
								"credits_policy_lowerlimitd", title, lowerlimit
										+ "", unit);
					} else {
						message = getMessage(request,
								"credits_policy_num_lowerlimit", title,
								lowerlimit + "", unit);
					}
					extcredit = null;
					break;
				}
			}
		}
		return message;
	}

	private byte checkbbcodes(String message, long bbcodeoff) {
		return (byte) (bbcodeoff == 0 && !Common.matches(message, "\\[.+\\]") ? -1
				: bbcodeoff);
	}

	private byte checksmilies(String message, long smileyoff,
			Map<String, String> searcharray) {
		boolean flag = false;
		if (searcharray != null) {
			Set<String> keys = searcharray.keySet();
			for (String key : keys) {
				if (message.indexOf(searcharray.get(key)) >= 0) {
					flag = true;
					break;
				}
			}
		}
		return (byte) (smileyoff == 0 && !flag ? -1 : smileyoff);
	}

	public String getAttachtype(String filetype) {
		if (filetype.matches("jpg|jpeg|gif|png|bmp")) {
			return "image/pjpeg";
		} else if (filetype.matches("zip|rar")) {
			return "application/zip";
		} else {
			return "text/plain";
		}
	}

	@SuppressWarnings("unchecked")
	private List getAttach(FileUploadUtil fileupload) {
		List filelist = new ArrayList();
		Map<String, FileItem> items = fileupload.getFileItem();
		if (items != null) {
			Set<String> names = items.keySet();
			int[] arrays = new int[names.size()];
			int count = 0;
			for (String name : names) {
				if (name.indexOf("attachfile") != -1) {
					arrays[count] = Integer.valueOf(name.substring(10));
				}
				count++;
			}
			Arrays.sort(arrays);
			for (int i = 0; i < arrays.length; i++) {
				String key = "attachfile" + arrays[i];
				FileItem fileItem = items.get(key);
				filelist.add(fileItem);
			}
			filelist.add(arrays);
			arrays = null;
		}
		return filelist;
	}

	private int ftpupload(String source, Attachments atta,
			Map<String, String> ftp, String authorkey, String ext,
			String jsprun_userss, String ip, int timestamp) {
		String dest = atta.getAttachment();
		if (ftp.get("on").equals("1")
				&& (Common.empty(ftp.get("allowedexts"))
						&& Common.empty(ftp.get("disallowedexts"))
						|| (Common.empty(ftp.get("allowedexts")) && Common
								.in_array(ftp.get("allowedexts").toString()
										.split("\n"), ext)) || (Common
						.empty(ftp.get("disallowedexts")) && !Common.in_array(
						ftp.get("disallowedexts").toString().split("\n"), ext)))
				&& (Common.empty(ftp.get("minsize")) || atta.getFilesize() >= Integer
						.valueOf(ftp.get("minsize").toString()) * 1024)) {
			FTPClient fc = ftputil.getFTPClient();
			if (!ftputil.connectToServer(fc).equals("")) {
				File file = new File(source);
				if (file.exists()) {
					file.delete();
				}
				File thumbfile = new File(source + ".thumb." + ext);
				if (thumbfile.exists()) {
					thumbfile.delete();
				}
				ftputil.closeFtpConnect(fc);
				return 2;
			}
			String tem[] = dest.split("/");
			if (tem.length > 1) {
				if (!ftputil.dftp_chdir(tem[0], fc)) {
					if (!ftputil.dftp_mkdir(tem[0], fc)) {
						ftputil.closeFtpConnect(fc);
						Log.writelog("errorlog", timestamp, timestamp
								+ "\tFTP\t" + jsprun_userss + "<br />\t" + ip
								+ "|" + ip + "\tMkdir " + tem[0] + "\terror.");
						File file = new File(source);
						if (file.exists()) {
							file.delete();
						}
						File thumbfile = new File(source + ".thumb." + ext);
						if (thumbfile.exists()) {
							thumbfile.delete();
						}
						return 2;
					}
					ftputil.dftp_site(tem[0], fc);
					if (!ftputil.dftp_chdir(tem[0], fc)) {
						ftputil.closeFtpConnect(fc);
						Log.writelog("errorlog", timestamp, timestamp
								+ "\tFTP\t" + jsprun_userss + "<br />\t" + ip
								+ "|" + ip + "\tChdir " + tem[0] + "\terror.");
						File file = new File(source);
						if (file.exists()) {
							file.delete();
						}
						File thumbfile = new File(source + ".thumb." + ext);
						if (thumbfile.exists()) {
							thumbfile.delete();
						}
						return 2;
					}
				}
				dest = tem[1];
			}
			if (ftputil.put(source, dest, fc)) {
				if (atta.getThumb() == 1) {
					if (ftputil.put(source + ".thumb." + ext, dest + ".thumb."
							+ ext, fc)) {
						if (!Common.empty(ftp.get("mirror"))
								&& !ftp.get("mirror").equals("2")) {
							File file = new File(source);
							if (file.exists()) {
								file.delete();
							}
							File thumbfile = new File(source + ".thumb." + ext);
							if (thumbfile.exists()) {
								thumbfile.delete();
							}
						}
						return 1;
					} else {
						ftputil.dftp_delete(dest, fc);
					}
				} else {
					if (!Common.empty(ftp.get("mirror"))
							&& !ftp.get("mirror").equals("2")) {
						File file = new File(source);
						if (file.exists()) {
							file.delete();
						}
					}
					ftputil.closeFtpConnect(fc);
					return 1;
				}
			} else {
				ftputil.closeFtpConnect(fc);
			}
			Log.writelog("errorlog", timestamp, timestamp + "\tFTP\t"
					+ jsprun_userss + "<br />\t" + ip + "|" + ip + "\tUpload "
					+ source + "\terror.");
			if (!Common.empty(ftp.get("mirror"))
					&& ftp.get("mirror").equals("1")) {
				File file = new File(source);
				if (file.exists()) {
					file.delete();
				}
				File thumbfile = new File(source + ".thumb." + ext);
				if (thumbfile.exists()) {
					thumbfile.delete();
				}
				return 2;
			}
		}
		return 0;
	}

	private boolean checkautoclose(Threads thread, Forums forum) {
		int timestamp = (int) (Calendar.getInstance().getTimeInMillis() / 1000);
		int closedby = 0;
		short autoclose = forum.getAutoclose();
		if (autoclose != 0) {
			closedby = autoclose > 0 ? thread.getDateline() : thread
					.getLastpost();
			autoclose = (short) (Math.abs(autoclose) * 86400);
			if (timestamp - closedby > autoclose) {
				return true;
			}
		}
		return false;
	}

	private String jammer() {
		StringBuffer randomstr = new StringBuffer();
		for (int i = 0; i < Common.rand(5, 15); i++) {
			randomstr.append((char) Common.rand(32, 59));
			randomstr.append(" ");
			randomstr.append((char) Common.rand(63, 126));
		}
		return Common.rand(1) > 0 ? "<font style=\"font-size:0px;color:'#fff'\">"
				+ randomstr + "</font><br />"
				: "<br /><span style=\"display:none\">" + randomstr + "</span>";
	}

	private String relacesmile(String message, int parnum,
			List<Map<String, String>> smilieslist) {
		for (Map<String, String> smiles : smilieslist) {
			if (message.indexOf(smiles.get("code") + " ") != -1
					|| message.indexOf(" " + smiles.get("code")) != -1) {
				StringBuffer buffer = new StringBuffer(100);
				buffer.append("<img src='images/smilies/");
				buffer.append(smiles.get("directory"));
				buffer.append("/");
				buffer.append(smiles.get("url"));
				buffer.append("' smilieid='");
				buffer.append(smiles.get("id"));
				buffer.append("' border='0' alt='' /> ");
				message = StringUtils.replace(message, smiles.get("code"),
						buffer.toString(), parnum);
			}
		}
		smilieslist = null;
		return message;
	}

	private boolean writeWaterOrthumb(String filetype, String targetpath,
			int disablewatermark, Map<String, String> settings) {
		String thumbstatus = settings.get("thumbstatus");
		String thumbwidth = settings.get("thumbwidth");
		String thumbheight = settings.get("thumbheight");
		String watermarkstatus = settings.get("watermarkstatus");
		String watermarkminwidth = settings.get("watermarkminwidth");
		String watermarkminheight = settings.get("watermarkminwidth");
		String watermarktype = settings.get("watermarktype");
		String watermarktrans = settings.get("watermarktrans");
		String watermarkquality = settings.get("watermarkquality");
		String watermarktext = settings.get("watermarktext");
		String imagelib = settings.get("imagelib");
		boolean thumflag = false;
		if (filetype.matches("jpg|jpeg|gif|png|bmp")) {
			if (thumbstatus.equals("1")) {
				if (!thumbwidth.equals("0") && !thumbheight.equals("0")) {
					try {
						String msg = ImageUtil.createZoomImage(targetpath,
								targetpath + ".thumb." + filetype, Integer
										.parseInt(thumbwidth), Integer
										.parseInt(thumbheight));
						if (msg == null) {
							thumflag = true;
						}
					} catch (Exception e) {
					}
				}
			} else if (thumbstatus.equals("2")) {
				if (!thumbwidth.equals("0") && !thumbheight.equals("0")) {
					try {
						ImageUtil.createZoomImage(targetpath, targetpath,
								Integer.parseInt(thumbwidth), Integer
										.parseInt(thumbheight));
					} catch (Exception e) {
					}
				}
			}
			if (disablewatermark != 1 && !watermarkstatus.equals("0")) {
				watermarktrans = FormDataCheck.getDoubleString(watermarktrans);
				Double transparencyDouble = Double.valueOf(watermarktrans);
				float transparency = 0f;
				if (transparencyDouble > Float.MAX_VALUE) {
					transparency = Float.MAX_VALUE;
				} else if (transparencyDouble < Float.MIN_VALUE) {
					transparency = Float.MIN_VALUE;
				} else {
					transparency = transparencyDouble.floatValue();
				}
				watermarkquality = FormDataCheck
						.getDoubleString(watermarkquality);
				Double imageQualityDouble = Double.valueOf(watermarkquality);
				float imageQuality = 0f;
				if (imageQualityDouble > Float.MAX_VALUE) {
					imageQuality = Float.MAX_VALUE;
				} else if (imageQualityDouble < Float.MIN_VALUE) {
					imageQuality = Float.MIN_VALUE;
				} else {
					imageQuality = imageQualityDouble.floatValue();
				}
				if (watermarktype.equals("2")) {
					Map watermattextmap = dataParse.characterParse(
							watermarktext, false);
					String watermarktext_fontpath = (String) watermattextmap
							.get("fontpath");
					String fontpath1 = servlet.getServletContext().getRealPath(
							"images/fonts/ch/"
									+ watermattextmap.get("fontpath"));
					String fontpath2 = servlet.getServletContext().getRealPath(
							"images/fonts/en/"
									+ watermattextmap.get("fontpath"));
					File file1 = new File(fontpath1);
					File file2 = new File(fontpath2);
					String watermarktext_fontpath_true = null;
					if (watermarktext_fontpath.endsWith(".ttf")) {
						if (file1.isFile()) {
							watermarktext_fontpath_true = fontpath1;
						} else if (file2.isFile()) {
							watermarktext_fontpath_true = fontpath2;
						}
					}
					int watermarktext_sizeInt = Common.toDigit(watermattextmap
							.get("size").toString());
					int watermarktext_angleInt = Common.toDigit(watermattextmap
							.get("angle").toString());
					String watermarktext_color = (String) watermattextmap
							.get("color");
					int watermarktext_shadowxInt = Common
							.toDigit(watermattextmap.get("shadowx").toString());
					int watermarktext_shadowyInt = Common
							.toDigit(watermattextmap.get("shadowy").toString());
					String watermarktext_shadowcolor = (String) watermattextmap
							.get("shadowcolor");
					String watermarktext_translatex = (String) watermattextmap
							.get("translatex");
					String watermarktext_translatey = (String) watermattextmap
							.get("translatey");
					String watermarktext_skewx = (String) watermattextmap
							.get("skewx");
					String watermarktext_skewy = (String) watermattextmap
							.get("skewy");
					String watertext = (String) watermattextmap.get("text");
					try {
						ImageUtil.createWaterMarkWithCharacter(targetpath,
								targetpath, convertInt(watermarkminwidth),
								watertext, watermarktext_fontpath_true,
								watermarktext_sizeInt, watermarktext_angleInt,
								watermarktext_color, watermarktext_shadowxInt,
								watermarktext_shadowyInt,
								watermarktext_shadowcolor,
								watermarktext_translatex,
								watermarktext_translatey, watermarktext_skewx,
								watermarktext_skewy, imageQuality,
								watermarkstatus, transparency);
					} catch (IOException exception) {
					}
				} else {
					String baseImagePath = "";
					if (watermarktype.equals("1")) {
						baseImagePath = servlet.getServletContext()
								.getRealPath("images/common/watermark.png");
					} else {
						baseImagePath = servlet.getServletContext()
								.getRealPath("images/common/watermark.gif");
					}
					if (imagelib.equals("0")) {
						try {
							ImageUtil.createWaterMarkWithImage(targetpath,
									targetpath, baseImagePath, watermarkstatus,
									transparency, imageQuality,
									convertInt(watermarkminwidth),
									convertInt(watermarkminheight));
						} catch (Exception exception) {
						}
					}
				}
			}
		}
		return thumflag;
	}

	// 导出帖子的方法
	public ActionForward doExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		int tid = Integer.parseInt(request.getParameter("tid"));
		String type = request.getParameter("type");
		if (!"toReport".equals(type)) {
			String exorword = request.getParameter("exorword");
			String sql = "select * from jrun_posts p where p.tid=" + tid
					+ " order by p.pid";
			List<Map<String, String>> list = dataBaseService.executeQuery(sql);
			String[] ids = request.getParameterValues("ids[]");
			if (ids == null || ids.length == 0) {
				request.setAttribute("errorInfo", "请选择要导出数据的字段");
				return mapping.findForward("showMessage");
			}
			if (exorword.equals("Excel")) {
				File file = new File(request.getSession().getServletContext()
						.getRealPath("WEB-INF/book.jpg"));
				String subject = ((Map<String, String>) list.get(0))
						.get("subject")
						+ "";
				response.setCharacterEncoding("utf-8");
				response.setContentType("octets/stream");
				response.addHeader("Content-Disposition",
						"attachment;filename=" + Common.toUtf8String(subject)
								+ ".xls");
				ExportExcel<Posts> ex = new ExportExcel<Posts>();
				String[] headers = new String[ids.length];
				Map mapfile = new HashMap();
				for (int i = 0; i < ids.length; i++) {
					headers[i] = keyMap.get(ids[i]);
					mapfile.put(ids[i], ids[i]);
				}
				List<Posts> dataset = new ArrayList<Posts>();

				try {
					BufferedInputStream bis = new BufferedInputStream(
							new FileInputStream(file));
					byte[] buf = new byte[bis.available()];
					while ((bis.read(buf)) != -1) {
					}
					if (list != null && list.size() > 0) {
						for (Map<String, String> map : list) {
							Posts ps = new Posts();
							ps.setAuthor(map.get("author"));
							ps.setAuthorid(Integer
									.parseInt(map.get("authorid")));
							ps.setDateline(Integer
									.parseInt(map.get("dateline")));
							ps.setMessage(Common.htmlspecialchars(map
									.get("message")));
							ps.setRate(Short.parseShort(map.get("rate")));
							ps.setUseip(map.get("useip"));
							ps.setSubject(map.get("subject"));
							ps.setStatus(Byte.parseByte(map.get("status")));
							ps.setRatetimes(Short.parseShort(map
									.get("ratetimes")));
							ps.setRate(Short.parseShort(map.get("rate")));
							dataset.add(ps);
							ps = null;
						}
					}
					OutputStream out = response.getOutputStream();
					ex.exportExcel(subject, headers, dataset, out, mapfile,
							request);
					out.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				request.setAttribute("list", list);
				request.setAttribute("map", keyMap);
				request.setAttribute("id", ids);
				try {
					request.getRequestDispatcher(
							"templates/default/ExportWord.jsp").forward(
							request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		} else {
			request.setAttribute("tid", tid);
			return mapping.findForward("toReport");
		}

	}

	// 模板编辑的方法
	public ActionForward doEditTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		{
			HttpSession session = request.getSession();
			int uid = Integer.parseInt(session.getAttribute("jsprun_uid") + "");
			Members member = (Members) session.getAttribute("user");
			Map<String, String> usergroups = (Map<String, String>) request
					.getAttribute("usergroups");
			if (uid == 0) {
				request.setAttribute("errorInfo", "对不起，你没有登录，无权修改模板");
				return mapping.findForward("showMessage");
			}
			int tempid = Integer.parseInt(request.getParameter("tempid"));
			ThemeTemplate template = themeTemplateService
					.findThemeTemplateById(tempid);
			if (template == null) {
				request.setAttribute("errorInfo", "对不起，此模板不存在");
				return mapping.findForward("showMessage");
			}
//			template=null;
			String fid=request.getParameter("ffid");
			String special=request.getParameter("special");
			String option = request.getParameter("option");
			if ("show".equals(option)) {
					this.createEdit(request, member);
					request.setAttribute("subject","".equals(template.getSubject())?"":template.getSubject());
					request.setAttribute("message", "".equals(template
							.getMessage()) ? "" : template.getMessage());
					request.setAttribute("tempid",tempid);
					request.setAttribute("ffid", fid);
					request.setAttribute("special",special);
					return mapping.findForward("edittemplate");
			} else{
    				String subject=request.getParameter("subject");
    				String message=request.getParameter("message");
    				if(subject==null||"".equals(subject)){
    					request.setAttribute("errorInfo", "对不起，请输入模板名");
    					return mapping.findForward("showMessage");
    				}
    				if(message==null||"".equals(message)){
    					request.setAttribute("errorInfo", "对不起，请输入模板内容");
    					return mapping.findForward("showMessage");
    				}
    				ThemeTemplate temp=new ThemeTemplate();
    				temp.setId(tempid);
    				temp.setMessage(message);
    				temp.setSubject(subject);
    				temp.setIspublic(template.getIspublic());
    				temp.setUid(uid);
    				template=null;
    				boolean success=themeTemplateService.updateThemeTemplate(temp);
                    if(success){
                    	try {
							request.getRequestDispatcher("viewthread.jsp?action=template&option=show&type=me&fid="+fid+"&special="+special).forward(request,response);
						} catch (ServletException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    	return null;
                    }
                    else{
                    	request.setAttribute("errorInfo", "模板修改失败");
    					return mapping.findForward("showMessage");
                    }
			}
		}

	}
    private void createEdit(HttpServletRequest request,Members member){
    	Map<String, String> settings = ForumInit.settings;
    	String[] fontoptions={"仿宋_GB2312", "黑体", "楷体_GB2312", "宋体", "新宋体","微软雅黑", "Trebuchet MS", "Tahoma", "Arial", "Impact", "Verdana","Times New Roman" };
		request.setAttribute("fontoptions", fontoptions);
		request.setAttribute("editorid", "posteditor");
		String editoroptions = Integer.toBinaryString(Common.range(Common
				.intval(settings.get("editoroptions")), 255, 0));
		editoroptions = editoroptions.length() >= 2 ? editoroptions : "0"
				+ editoroptions;
		String editormodes = null;
		byte editormode = editormodes != null && !editormodes.equals("") ? Byte
				.valueOf(editormodes)
				: member != null ? member.getEditormode() : 0;
		request.setAttribute("editormode", editormode == 2 ? String
				.valueOf(editoroptions.charAt(0)) : editormode);
		request.setAttribute("allowswitcheditor", String
				.valueOf(editoroptions.charAt(1)));
		Map<String, String> faqs = (Map<String, String>) request
				.getAttribute("faqs");
		request.setAttribute("faqs", dataParse.characterParse(faqs
				.get("faqs"), false));
		faqs = null;
		Map<String, String> bbcodes_display = (Map<String, String>) request
				.getAttribute("bbcodes_display");
		request.setAttribute("bbcodes_display", dataParse.characterParse(
				bbcodes_display.get("bbcodes_display"), false));
		request.setAttribute("advanceeditor", true);
		request.setAttribute("coloroptions", Common.COLOR_OPTIONS);
		request.setAttribute("sticktopic",  request.getParameter("sticktopic"));
		
		Map<String, Map<String, String>> smileytypeMap = new TreeMap<String, Map<String, String>>();
		Map<String, Map<String, Map<String, String>>> smilies_displays = new TreeMap<String, Map<String, Map<String, String>>>();
		Map<String, String> smileytypes = (Map<String, String>) request
				.getAttribute("smileytypes");
		Map<String, String> smilies_display = (Map<String, String>) request
				.getAttribute("smilies_display");
		if (smileytypes != null && smileytypes.size() > 0) {
			Set<String> typeids = smileytypes.keySet();
			for (String typeid : typeids) {
				smileytypeMap.put(typeid, dataParse.characterParse(smileytypes
						.get(typeid), true));
				smilies_displays.put(typeid, dataParse.characterParse(
						smilies_display.get(typeid), true));
			}
		}
		smileytypes = null;
		smilies_display = null;
		request.setAttribute("smileytypeMap",
				smileytypeMap.size() > 0 ? smileytypeMap : null);
		request.setAttribute("smilies_displays",
				smilies_displays.size() > 0 ? smilies_displays : null);
    }
    private String getGroup(int groupid){
    	String sql="SELECT grouptitle From jrun_usergroups WHERE groupid="+groupid;
    	List<Map<String,String>> lg=dataBaseService.executeQuery(sql);
    	if(lg==null||lg.size()==0){
    		return "";    		
    	}
    	else{
    		return lg.get(0).get("grouptitle");
    	}
    }
	private File isMakeDir() {
		File file = new File(FILEPATHTIME);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
}