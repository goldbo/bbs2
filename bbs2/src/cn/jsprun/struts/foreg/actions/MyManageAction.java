package cn.jsprun.struts.foreg.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import cn.jsprun.api.Tenpayapi;
import cn.jsprun.domain.FriendGroups;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Scene;
import cn.jsprun.domain.Scenect;
import cn.jsprun.domain.Scenetask;
import cn.jsprun.domain.Scenetm;
import cn.jsprun.domain.SendGifts;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.struts.conference.action.InvitationTemplate;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.Mail;

public class MyManageAction extends BaseAction {
	@SuppressWarnings("unchecked")
	public ActionForward toMyIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String threadadd = null;
		String postadd = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			postadd = "AND p.fid='" + srchfid + "'";
		} else {
			threadadd = "";
			postadd = "";
		}
		List<Map<String, String>> threadlists = dataBaseService
				.executeQuery("SELECT m.tid, t.subject, t.fid, t.displayorder, t.lastposter, t.lastpost, t.closed ,f.name FROM jrun_mythreads m, jrun_threads t ,jrun_forums f WHERE m.uid="
						+ uid
						+ " AND m.tid=t.tid AND t.fid=f.fid "
						+ threadadd
						+ " ORDER BY m.dateline DESC LIMIT 5");
		this.setAttribute(request, session, threadlists, "threadlists");
		List<Map<String, String>> postlists = dataBaseService
				.executeQuery("SELECT m.tid,m.pid, p.fid, p.invisible,t.subject,t.lastposter,t.lastpost,f.name FROM jrun_myposts m INNER JOIN jrun_posts p ON p.pid=m.pid "
						+ postadd
						+ " INNER JOIN jrun_threads t ON t.tid=m.tid ,jrun_forums f WHERE m.uid="
						+ uid
						+ " AND p.fid=f.fid ORDER BY m.dateline DESC LIMIT 5");
		this.setAttribute(request, session, postlists, "postlists");
		request.removeAttribute("myitem");
		return mapping.findForward("toMy");
	}

	public ActionForward toMyThreads(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String threadadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		} else {
			threadadd = "";
			extrafid = "";
		}
		Map<String, Integer> multi = this.multi(request, response, uid,
				"SELECT COUNT(*) count FROM jrun_mythreads m, jrun_threads t WHERE m.uid="
						+ uid + " " + threadadd + " AND m.tid=t.tid",
				"my.jsp?item=threads" + extrafid);
		List<Map<String, String>> threadlists = dataBaseService
				.executeQuery("SELECT m.tid, t.subject, t.fid, t.displayorder, t.lastposter, t.lastpost, t.closed ,f.name FROM jrun_mythreads m, jrun_threads t ,jrun_forums f WHERE m.uid="
						+ uid
						+ " AND m.tid=t.tid AND t.fid=f.fid "
						+ threadadd
						+ "ORDER BY m.dateline DESC LIMIT "
						+ multi.get("start_limit")
						+ ", "
						+ multi.get("perpage"));
		this.setAttribute(request, session, threadlists, "threadlists");
		return mapping.findForward("toMy");
	}

	public ActionForward toMyPosts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String postadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			postadd = "AND p.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		} else {
			postadd = "";
			extrafid = "";
		}
		Map<String, Integer> multi = this
				.multi(
						request,
						response,
						uid,
						"SELECT COUNT(*) count FROM jrun_myposts m INNER JOIN jrun_posts p ON p.pid=m.pid "
								+ postadd
								+ " INNER JOIN jrun_threads t ON t.tid=m.tid WHERE m.uid = "
								+ uid, "my.jsp?item=posts" + extrafid);
		List<Map<String, String>> postlists = dataBaseService
				.executeQuery("SELECT m.uid, m.tid, m.pid, p.fid, p.invisible, p.dateline,t.subject,t.lastposter,t.lastpost,f.name FROM jrun_myposts m INNER JOIN jrun_posts p ON p.pid=m.pid "
						+ postadd
						+ " INNER JOIN jrun_threads t ON t.tid=m.tid ,jrun_forums f WHERE m.uid="
						+ uid
						+ " AND p.fid=f.fid ORDER BY m.dateline DESC LIMIT "
						+ multi.get("start_limit")
						+ ", "
						+ multi.get("perpage"));
		this.setAttribute(request, session, postlists, "postlists");
		return mapping.findForward("toMy");
	}
    
	
	 public ActionForward toMySpecial(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    {
	        HttpSession session = request.getSession();
	        String jsprun_userss = (new StringBuilder()).append(session.getAttribute("jsprun_userss")).toString();
	        if(!"".equals(jsprun_userss))
	        {
	            List listtid = dataBaseService.executeQuery((new StringBuilder("SELECT tid FROM jrun_permlist WHERE account='")).append(jsprun_userss).append("'").toString());
	            if(listtid != null && listtid.size() > 0)
	            {
	                List speciallists;
	                for(Iterator iterator = listtid.iterator(); iterator.hasNext(); setAttribute(request, session, speciallists, "speciallists"))
	                {
	                    Map maptid = (Map)iterator.next();
	                    speciallists = dataBaseService.executeQuery((new StringBuilder("SELECT m.tid, t.subject, t.fid, t.displayorder, t.lastposter, t.lastpost, t.closed ,f.name FROM jrun_mythreads m, jrun_threads t ,jrun_forums f WHERE t.tid=")).append((String)maptid.get("tid")).append(" AND m.tid=t.tid AND t.fid=f.fid ").append("ORDER BY m.dateline DESC ").toString());
	                }

	            }
	            request.setAttribute("item", "my_special");
	        }
	        return mapping.findForward("toMy");
	    }
	 
	 
	@SuppressWarnings("unchecked")
	public ActionForward toMyFavorites(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String threadadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		} else {
			threadadd = "";
			extrafid = "";
		}
		Map<String, String> settings = ForumInit.settings;
		String favsubmit = request.getParameter("favsubmit");
		String tid = request.getParameter("tid");
		String fid = request.getParameter("fid");
		String type = request.getParameter("type");
		String ftid = "thread".equals(type) || tid != null ? "tid" : "fid";
		type = "thread".equals(type) || tid != null ? "thread" : "forum";
		if ((tid != null || fid != null) && favsubmit == null) {
			Common.setResponseHeader(response);
			List<Map<String, String>> count = dataBaseService
					.executeQuery("SELECT COUNT(*) count FROM jrun_favorites WHERE uid='"
							+ uid + "' AND " + ftid + ">'0'");
			int size = (count != null && count.size() > 0 ? Integer
					.valueOf(count.get(0).get("count")) : 0);
			int maxfavorites = Integer.valueOf(settings.get("maxfavorites"));
			String message = null;
			if (size >= maxfavorites) {
				message = getMessage(request, "favorite_is_full");
			} else {
				String value = "thread".equals(type) || tid != null ? tid : fid;
				count = dataBaseService
						.executeQuery("SELECT COUNT(*) count  FROM jrun_favorites WHERE uid='"
								+ uid + "' AND " + ftid + "=" + value);
				size = (count != null && count.size() > 0 ? Integer
						.valueOf(count.get(0).get("count")) : 0);
				if (size > 0) {
					message = getMessage(request, "favorite_exists");
				} else {
					dataBaseService.runQuery(
							"INSERT INTO jrun_favorites (uid, " + ftid
									+ ") VALUES ('" + uid + "', '" + value
									+ "')", true);
					message = getMessage(request, "favorite_add_succeed");
				}
			}
			try {
				response.getWriter().write(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return mapping.findForward("null");
		} else {
			if (favsubmit != null) {
				String[] delete = request.getParameterValues("delete");
				if (delete != null) {
					StringBuffer ids = new StringBuffer(delete.length);
					for (String id : delete) {
						ids.append(id + ",");
					}
					if (ids.length() > 0) {
						ids.deleteCharAt(ids.length() - 1);
						dataBaseService.runQuery(
								"DELETE FROM jrun_favorites WHERE uid=" + uid
										+ " AND " + ftid + " IN ( " + ids
										+ " )", true);
					}
					delete = null;
					ids = null;
				}
				request.setAttribute("successInfo", getMessage(request,
						"favorite_update_succeed"));
				request.setAttribute("requestPath",
						"my.jsp?item=favorites&type=" + type);
				return mapping.findForward("showMessage");

			} else {
				if ("forum".equals(type)) {
					Map<String, Integer> multi = this.multi(request, response,
							uid,
							"SELECT COUNT(*) count FROM jrun_favorites fav, jrun_forums f WHERE fav.uid = "
									+ uid + " AND fav.fid=f.fid",
							"my.jsp?item=favorites&type=forum");
					List<Map<String, String>> favlists = dataBaseService
							.executeQuery("SELECT f.fid, f.name, f.threads, f.posts, f.todayposts, f.lastpost FROM jrun_favorites fav, jrun_forums f WHERE fav.fid=f.fid AND fav.uid="
									+ uid
									+ " ORDER BY f.lastpost DESC LIMIT "
									+ multi.get("start_limit")
									+ ", "
									+ multi.get("perpage"));
					request.setAttribute("favlists", favlists != null
							&& favlists.size() > 0 ? favlists : null);
					favlists = null;
					return mapping.findForward("toMy");
				} else {
					Map<String, Integer> multi = this
							.multi(
									request,
									response,
									uid,
									"SELECT COUNT(*) count FROM jrun_favorites fav, jrun_threads t	WHERE fav.uid = "
											+ uid
											+ " AND fav.tid=t.tid AND t.displayorder>='0'"
											+ threadadd,
									"my.jsp?item=favorites&type=thread"
											+ extrafid);
					List<Map<String, String>> favlists = dataBaseService
							.executeQuery("SELECT t.tid, t.fid, t.subject, t.replies, t.lastpost, t.lastposter, f.name FROM jrun_favorites fav, jrun_threads t, jrun_forums f WHERE fav.tid=t.tid AND t.displayorder>='0' AND fav.uid="
									+ uid
									+ " AND t.fid=f.fid "
									+ threadadd
									+ " ORDER BY t.lastpost DESC LIMIT "
									+ multi.get("start_limit")
									+ ", "
									+ multi.get("perpage"));
					this.setAttribute(request, session, favlists, "favlists");
					return mapping.findForward("toMy");
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward toMySubscriptions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String threadadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		} else {
			threadadd = "";
			extrafid = "";
		}
		String subadd = request.getParameter("subadd");
		if (subadd != null) {
			Common.setResponseHeader(response);
			List<Map<String, String>> sublists = dataBaseService
					.executeQuery("SELECT price FROM jrun_threads WHERE tid="
							+ subadd);
			if (sublists != null) {
				List<Map<String, String>> paymentlist = dataBaseService
						.executeQuery("SELECT tid FROM jrun_paymentlog WHERE tid='"
								+ subadd + "' AND uid=" + uid);
				if (paymentlist == null) {
					try {
						response.getWriter()
								.write(
										getMessage(request,
												"subscription_nopermission"));
						return null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				paymentlist = null;
				List<Map<String, String>> countlist = dataBaseService
						.executeQuery("SELECT COUNT(*) count FROM jrun_subscriptions WHERE uid="
								+ uid);
				String maxsuscription = ForumInit.settings
						.get("maxsubscriptions");
				if (countlist != null
						&& Integer.valueOf(countlist.get(0).get("count")) >= Integer
								.valueOf(maxsuscription)) {
					try {
						response.getWriter().write(
								getMessage(request, "subscription_is_full"));
						return null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				List<Map<String, String>> subscriptlist = dataBaseService
						.executeQuery("SELECT tid FROM jrun_subscriptions WHERE tid='"
								+ subadd + "' AND uid='" + uid + "' LIMIT 1");
				if (subscriptlist != null && subscriptlist.size() > 0) {
					try {
						response.getWriter().write(
								getMessage(request, "subscription_exists"));
						return null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					dataBaseService.runQuery(
							"INSERT INTO jrun_subscriptions (uid, tid,lastnotify) VALUES ('"
									+ uid + "', '" + subadd + "', '0')", true);
					try {
						response.getWriter()
								.write(
										getMessage(request,
												"subscription_add_succeed"));
						return null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		} else {
			if (request.getParameter("subsubmit") != null) {
				String[] delete = request.getParameterValues("delete");
				if (delete != null) {
					StringBuffer tids = new StringBuffer();
					for (String tid : delete) {
						tids.append(tid + ",");
					}
					int length = tids.length();
					if (length > 0) {
						tids.deleteCharAt(length - 1);
						dataBaseService.runQuery(
								"DELETE FROM jrun_subscriptions WHERE uid="
										+ uid + " AND tid IN ( " + tids + " )",
								true);
					}
				}
				request.setAttribute("successInfo", getMessage(request,
						"subscription_update_succeed"));
				request
						.setAttribute("requestPath",
								"my.jsp?item=subscriptions");
				return mapping.findForward("showMessage");

			} else {
				Map<String, Integer> multi = this
						.multi(
								request,
								response,
								uid,
								"SELECT COUNT(*) count FROM jrun_subscriptions s, jrun_threads t WHERE s.uid = "
										+ uid + " AND s.tid=t.tid " + threadadd,
								"my.jsp?item=subscriptions" + extrafid);
				List<Map<String, String>> sublists = dataBaseService
						.executeQuery("SELECT t.tid, t.fid, t.subject, t.replies, t.lastpost, t.lastposter, f.name FROM jrun_subscriptions s, jrun_threads t, jrun_forums f	WHERE t.tid=s.tid AND t.displayorder>='0' AND f.fid=t.fid AND s.uid="
								+ uid
								+ " "
								+ threadadd
								+ " ORDER BY t.lastpost DESC LIMIT "
								+ multi.get("start_limit")
								+ ", "
								+ multi.get("perpage"));
				this.setAttribute(request, session, sublists, "sublists");
				return mapping.findForward("toMy");
			}
		}
	}

	public ActionForward toMyGrouppermission(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String searchgroupid = request.getParameter("searchgroupid");
		short groupid = 0;
		if (searchgroupid != null) {
			groupid = (short) Common.toDigit(searchgroupid);
		} else {
			HttpSession session = request.getSession();
			groupid = (Short) session.getAttribute("jsprun_groupid");
		}
		this.setExtcredits(request);
		List<Map<String, String>> grouplists = dataBaseService
				.executeQuery("SELECT groupid, type, grouptitle FROM jrun_usergroups ORDER BY (creditshigher<>'0' || creditslower<>'0'), creditslower");
		StringBuffer memberGroups = new StringBuffer();
		StringBuffer systemGroups = new StringBuffer();
		StringBuffer specialGroups = new StringBuffer();
		for (Map<String, String> group : grouplists) {
			String grouptype = group.get("type");
			if ("member".equals(grouptype)) {
				memberGroups
						.append("<li><a href=\"my.jsp?item=grouppermission&amp;type="
								+ grouptype
								+ "&amp;searchgroupid="
								+ group.get("groupid")
								+ "\">"
								+ group.get("grouptitle") + "</a></li>");
			} else if ("system".equals(grouptype)) {
				systemGroups
						.append("<li><a href=\"my.jsp?item=grouppermission&amp;type="
								+ grouptype
								+ "&amp;searchgroupid="
								+ group.get("groupid")
								+ "\">"
								+ group.get("grouptitle") + "</a></li>");
			} else if ("special".equals(grouptype)) {
				specialGroups
						.append("<li><a href=\"my.jsp?item=grouppermission&amp;type="
								+ grouptype
								+ "&amp;searchgroupid="
								+ group.get("groupid")
								+ "\">"
								+ group.get("grouptitle") + "</a></li>");
			}
		}
		request.setAttribute("memberGroups", memberGroups.toString());
		request.setAttribute("systemGroups", systemGroups.toString());
		request.setAttribute("specialGroups", specialGroups.toString());
		grouplists = null;
		List<Map<String, String>> usergroups = dataBaseService
				.executeQuery("SELECT * FROM jrun_usergroups u LEFT JOIN jrun_admingroups a ON u.groupid=a.admingid WHERE u.groupid='"
						+ groupid + "'");
		if (usergroups != null && usergroups.size() > 0) {
			Map<String, String> usergroup = usergroups.get(0);
			usergroup.put("maxattachsize", String.valueOf(Integer
					.valueOf(usergroup.get("maxattachsize")) / 1000));
			usergroup.put("maxsizeperday", String.valueOf(Integer
					.valueOf(usergroup.get("maxsizeperday")) / 1000));
			request.setAttribute("usergroup", usergroup);
			request.setAttribute("type", request.getParameter("type"));
			return mapping.findForward("toMy");
		} else {
			request.setAttribute("errorInfo", getMessage(request,
					"usergroups_nonexistence"));
			return mapping.findForward("showMessage");
		}
	}

	public ActionForward toMyPolls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String threadadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		} else {
			threadadd = "";
			extrafid = "";
		}
		String type = request.getParameter("type");
		if ("poll".equals(type)) {
			Map<String, Integer> multi = this.multi(request, response, uid,
					"SELECT COUNT(*) count FROM jrun_mythreads m,  jrun_threads t WHERE m.uid="
							+ uid + " AND m.special='1' " + threadadd
							+ " AND m.tid=t.tid", "my.jsp?item=polls&type=poll"
							+ extrafid);
			List<Map<String, String>> polllists = dataBaseService
					.executeQuery("SELECT t.tid, t.subject, t.fid, t.displayorder, t.closed, t.lastposter, t.lastpost, f.name	FROM jrun_threads t, jrun_mythreads m ,jrun_forums f WHERE m.uid="
							+ uid
							+ " AND m.tid=t.tid AND t.fid=f.fid AND m.special='1' "
							+ threadadd
							+ " ORDER BY m.dateline DESC LIMIT "
							+ multi.get("start_limit")
							+ ", "
							+ multi.get("perpage"));
			this.setAttribute(request, session, polllists, "polllists");
		} else if ("join".equals(type)) {
			Map<String, Integer> multi = this.multi(request, response, uid,
					"SELECT COUNT(*) count FROM jrun_myposts m, jrun_threads t	WHERE m.uid="
							+ uid + " AND m.special='1' " + threadadd
							+ " AND m.tid=t.tid", "my.jsp?item=polls&type=join"
							+ extrafid);
			List<Map<String, String>> polllists = dataBaseService
					.executeQuery("SELECT m.dateline, t.tid, t.fid, t.subject, t.displayorder, t.closed, f.name FROM jrun_myposts m, jrun_threads t ,jrun_forums f WHERE m.uid="
							+ uid
							+ " AND m.tid=t.tid AND t.fid=f.fid AND m.special='1' "
							+ threadadd
							+ " ORDER BY m.dateline DESC LIMIT "
							+ multi.get("start_limit")
							+ ", "
							+ multi.get("perpage"));
			if (polllists != null && polllists.size() > 0) {
				String timeoffset = (String) session.getAttribute("timeoffset");
				String timeformat = (String) session.getAttribute("timeformat");
				String dateformat = (String) session.getAttribute("dateformat");
				SimpleDateFormat sdf_all = Common.getSimpleDateFormat(
						dateformat + " " + timeformat, timeoffset);
				for (Map<String, String> thread : polllists) {
					thread.put("dateline", Common.gmdate(sdf_all, Integer
							.valueOf(thread.get("dateline"))));
				}
				request.setAttribute("polllists", polllists);
			}
		}
		return mapping.findForward("toMy");
	}

	@SuppressWarnings( { "unchecked", "static-access" })
	public ActionForward toMyPromotion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> settings = ForumInit.settings;
		Map creditspolicys = dataParse.characterParse(settings
				.get("creditspolicy"), false);
		if (Common.empty(creditspolicys.get("promotion_visit"))
				&& Common.empty(creditspolicys.get("promotion_register"))) {
			request.setAttribute("errorInfo", getMessage(request,
					"undefined_action_return"));
			// return mapping.findForward("showMessage");
		}
		this.setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String extrafid = null;
		if (srchfid > 0) {
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		} else {
			extrafid = "";
		}
		request.setAttribute("creditspolicy", creditspolicys);
		return mapping.findForward("toMy");
	}

	public ActionForward toMyActivities(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String threadadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		} else {
			threadadd = "";
			extrafid = "";
		}
		String type = request.getParameter("type");
		String ended = request.getParameter("ended");
		ended = ended != null && (ended.equals("yes") || ended.equals("no")) ? ended
				: "";
		String sign = null;
		String ascadd = null;
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		if ("yes".equals(ended)) {
			sign = " AND starttimefrom<'" + timestamp + "'";
			ascadd = "DESC";
		} else if ("no".equals(ended)) {
			sign = " AND starttimefrom>='" + timestamp + "'";
			ascadd = "";
		} else {
			sign = "";
			ascadd = "";
		}
		if ("orig".equals(type)) {
			Map<String, Integer> multi = this
					.multi(
							request,
							response,
							uid,
							"SELECT COUNT(*) count FROM jrun_activities a LEFT JOIN jrun_threads t USING(tid) WHERE a.uid="
									+ uid
									+ " AND t.special='4' "
									+ threadadd
									+ sign,
							"my.jsp?item=activities&type=orig&ended=" + ended
									+ extrafid);
			List<Map<String, String>> activitylists = dataBaseService
					.executeQuery("SELECT a.tid,a.cost,a.starttimefrom,a.place,a.expiration,t.subject,t.displayorder FROM jrun_activities a LEFT JOIN jrun_threads t USING(tid) WHERE a.uid="
							+ uid
							+ " AND t.special='4' "
							+ threadadd
							+ sign
							+ " ORDER BY starttimefrom "
							+ ascadd
							+ " LIMIT "
							+ multi.get("start_limit")
							+ ", "
							+ multi.get("perpage"));
			if (activitylists != null && activitylists.size() > 0) {
				String timeoffset = (String) session.getAttribute("timeoffset");
				String timeformat = (String) session.getAttribute("timeformat");
				String dateformat = (String) session.getAttribute("dateformat");
				SimpleDateFormat sdf_all = Common.getSimpleDateFormat(
						dateformat + " " + timeformat, timeoffset);
				for (Map<String, String> thread : activitylists) {
					thread.put("starttimefrom", Common.gmdate(sdf_all, Integer
							.valueOf(thread.get("starttimefrom"))));
				}
				request.setAttribute("activitylists", activitylists);
			}
		} else if ("apply".equals(type)) {
			Map<String, Integer> multi = this
					.multi(
							request,
							response,
							uid,
							"SELECT COUNT(*) count FROM jrun_activityapplies aa LEFT JOIN jrun_activities a USING(tid) LEFT JOIN jrun_threads t USING(tid) WHERE aa.uid="
									+ uid + " " + threadadd + sign,
							"my.jsp?item=activities&type=apply&ended=" + ended
									+ extrafid);
			List<Map<String, String>> activitylists = dataBaseService
					.executeQuery("SELECT aa.verified, aa.tid, starttimefrom, a.place, a.cost, t.subject,t.displayorder FROM jrun_activityapplies aa LEFT JOIN jrun_activities a USING(tid) LEFT JOIN jrun_threads t USING(tid) WHERE aa.uid="
							+ uid
							+ " "
							+ threadadd
							+ sign
							+ " ORDER BY starttimefrom "
							+ ascadd
							+ " LIMIT "
							+ multi.get("start_limit")
							+ ", "
							+ multi.get("perpage"));
			if (activitylists != null && activitylists.size() > 0) {
				String timeoffset = (String) session.getAttribute("timeoffset");
				String timeformat = (String) session.getAttribute("timeformat");
				String dateformat = (String) session.getAttribute("dateformat");
				SimpleDateFormat sdf_all = Common.getSimpleDateFormat(
						dateformat + " " + timeformat, timeoffset);
				for (Map<String, String> thread : activitylists) {
					thread.put("starttimefrom", Common.gmdate(sdf_all, Integer
							.valueOf(thread.get("starttimefrom"))));
				}
				request.setAttribute("activitylists", activitylists);
			}
		} else {
			request.setAttribute("errorInfo", getMessage(request,
					"undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		request.setAttribute("ended", ended);
		request.setAttribute("timestamp", timestamp);
		return mapping.findForward("toMy");
	}

	public ActionForward toMyTradestats(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String extrafid = null;
		if (srchfid > 0) {
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		}
		List<Map<String, String>> results = dataBaseService
				.executeQuery("SELECT COUNT(*) AS totalitems, SUM(price) AS tradesum FROM jrun_tradelog WHERE buyerid="
						+ uid
						+ " AND status IN ( "
						+ Tenpayapi.trade_typestatus("successtrades") + " )");
		request.setAttribute("buystats", results.get(0) != null
				&& results.get(0).get("tradesum") != null ? results.get(0)
				: null);
		results = dataBaseService
				.executeQuery("SELECT COUNT(*) AS totalitems, SUM(price) AS tradesum FROM jrun_tradelog WHERE sellerid="
						+ uid
						+ " AND status IN ( "
						+ Tenpayapi.trade_typestatus("successtrades") + " )");
		request.setAttribute("sellstats", results.get(0) != null
				&& results.get(0).get("tradesum") != null ? results.get(0)
				: null);
		Map<String, Integer> attendstatus = new HashMap<String, Integer>();
		results = dataBaseService
				.executeQuery("SELECT status FROM jrun_tradelog WHERE buyerid="
						+ uid + " AND status IN ( "
						+ Tenpayapi.trade_typestatus("buytrades") + " )");
		int buyerattend = results != null ? results.size() : 0;
		if (buyerattend > 0) {
			for (Map<String, String> result : results) {
				String status = result.get("status");
				Integer sum = attendstatus.get(status);
				if (sum != null) {
					attendstatus.put(status, sum + 1);
				} else {
					attendstatus.put(status, 1);
				}
			}
		}
		request.setAttribute("buyerattend", buyerattend);
		results = dataBaseService
				.executeQuery("SELECT status FROM jrun_tradelog WHERE sellerid="
						+ uid
						+ " AND status IN ( "
						+ Tenpayapi.trade_typestatus("selltrades") + " )");
		int sellerattend = results != null ? results.size() : 0;
		if (sellerattend > 0) {
			for (Map<String, String> result : results) {
				String status = result.get("status");
				Integer sum = attendstatus.get(status);
				if (sum != null) {
					attendstatus.put(status, sum + 1);
				} else {
					attendstatus.put(status, 1);
				}
			}
		}
		request.setAttribute("sellerattend", sellerattend);
		request.setAttribute("attendstatus", attendstatus);
		results = dataBaseService
				.executeQuery("SELECT COUNT(*) count FROM jrun_tradelog WHERE buyerid="
						+ uid
						+ " AND status IN ( "
						+ Tenpayapi.trade_typestatus("tradingtrades") + " )");
		request.setAttribute("goodsbuyer", results.get(0).get("count"));
		results = dataBaseService
				.executeQuery("SELECT COUNT(*) count FROM jrun_trades WHERE sellerid="
						+ uid + " AND closed='0'");
		request.setAttribute("goodsseller", results.get(0).get("count"));
		results = dataBaseService
				.executeQuery("SELECT COUNT(*) count FROM jrun_tradelog WHERE buyerid="
						+ uid
						+ " AND status IN ( "
						+ Tenpayapi.trade_typestatus("eccredittrades")
						+ " ) AND (ratestatus=0 OR ratestatus=2)");
		request.setAttribute("eccreditbuyer", results.get(0).get("count"));
		results = dataBaseService
				.executeQuery("SELECT COUNT(*) count FROM jrun_tradelog WHERE sellerid="
						+ uid
						+ " AND status IN ( "
						+ Tenpayapi.trade_typestatus("eccredittrades")
						+ " ) AND (ratestatus=0 OR ratestatus=1)");
		request.setAttribute("eccreditseller", results.get(0).get("count"));
		return mapping.findForward("toMy");
	}

	public ActionForward toMyTrades(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String threadadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		} else {
			threadadd = "";
			extrafid = "";
		}
		String filter = request.getParameter("filter");
		String myitem = request.getAttribute("myitem").toString();
		System.out.println("sss:"+myitem);
		String srchkey = request.getParameter("srchkey");
		int tid = Common.toDigit(request.getParameter("tid"));
		int pid = Common.toDigit(request.getParameter("pid"));
		String sqlfield = "selltrades".equals(myitem) ? "sellerid" : "buyerid";
		String sqlfilter = "";
		String typestatus = "";
		if ("attention".equals(filter)) {
			typestatus = myitem;
		} else if ("eccredit".equals(filter)) {
			typestatus = "eccredittrades";
			sqlfilter += myitem.equals("selltrades") ? " AND (tl.ratestatus=0 OR tl.ratestatus=1) "
					: " AND (tl.ratestatus=0 OR tl.ratestatus=2) ";
		} else if ("all".equals(filter)) {
			typestatus = "";
		} else if ("success".equals(filter)) {
			typestatus = "successtrades";
		} else if ("closed".equals(filter)) {
			typestatus = "closedtrades";
		} else if ("refund".equals(filter)) {
			typestatus = "refundtrades";
		} else if ("unstart".equals(filter)) {
			typestatus = "unstarttrades";
		} else {
			typestatus = "tradingtrades";
			filter = "";
		}
		sqlfilter += typestatus.length() > 0 ? " AND tl.status IN ( "
				+ Tenpayapi.trade_typestatus(typestatus) + " )" : "";
		String sqlkey = null;
		String extrasrchkey = null;
		if (srchkey != null) {
			sqlkey = " AND tl.subject like \'%"
					+ Common.addslashes(srchkey).replace('*', '%') + "%\'";
			extrasrchkey = "&srchkey=" + Common.encode(srchkey);
		} else {
			sqlkey = "";
			extrasrchkey = "";
		}
		String sqltid = tid > 0 ? " AND tl.tid=" + tid
				+ (pid > 0 ? " AND tl.pid=" + pid : "") : "";
		String extratid = tid > 0 ? "&tid=" + tid
				+ (pid > 0 ? "&pid" + pid : "") : "";
		Map<String, Integer> multi = this
				.multi(
						request,
						response,
						uid,
						"SELECT COUNT(*) count FROM jrun_tradelog tl, jrun_threads t WHERE tl.tid=t.tid AND tl."
								+ sqlfield
								+ "="
								+ uid
								+ threadadd
								+ sqltid
								+ sqlkey + sqlfilter, "my.jsp?item=" + myitem
								+ extratid + extrafid
								+ (filter != null ? "&filter=" + filter : "")
								+ extrafid + extrasrchkey);
		List<Map<String, String>> tradelists = dataBaseService
				.executeQuery("SELECT tl.*, tr.aid, t.subject AS threadsubject FROM jrun_tradelog tl, jrun_threads t, jrun_trades tr WHERE tl.tid=t.tid AND tr.pid=tl.pid AND tr.tid=tl.tid AND tl."
						+ sqlfield
						+ "="
						+ uid
						+ threadadd
						+ sqltid
						+ sqlkey
						+ sqlfilter
						+ " ORDER BY tl.lastupdate DESC LIMIT "
						+ multi.get("start_limit")
						+ ", "
						+ multi.get("perpage"));
		if (tradelists != null && tradelists.size() > 0) {
			MessageResources resources = getResources(request);
			Locale locale = getLocale(request);
			String timeoffset = (String) session.getAttribute("timeoffset");
			String timeformat = (String) session.getAttribute("timeformat");
			String dateformat = (String) session.getAttribute("dateformat");
			SimpleDateFormat sdf_all = Common.getSimpleDateFormat(dateformat
					+ " " + timeformat, timeoffset);
			for (Map<String, String> tradelog : tradelists) {
				int status = Integer.valueOf(tradelog.get("status"));
				tradelog.put("lastupdate", Common.gmdate(sdf_all, Integer
						.valueOf(tradelog.get("lastupdate"))));
				tradelog.put("attend", Tenpayapi.trade_typestatus(myitem,
						status) ? "true" : null);
				tradelog.put("status", (String) Tenpayapi.trade_getstatus(
						status, resources, locale));
			}
			request.setAttribute("tradelists", tradelists);
		}
		request.setAttribute("extratid", extratid);
		request.setAttribute("filter", filter);
		request.setAttribute("tid", tid);
		request.setAttribute("srchkey", srchkey);
		request.setAttribute("extrasrchkey", extrasrchkey);
		return mapping.findForward("toMy");
	}

	public ActionForward toMyTradethreads(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String extrafid = null;
		if (srchfid > 0) {
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		} else {
			extrafid = "";
		}
		String srchkey = request.getParameter("srchkey");
		int tid = Common.toDigit(request.getParameter("tid"));
		String sqlkey = null;
		String extrasrchkey = null;
		if (srchkey != null && srchkey.length() > 0) {
			sqlkey = "AND subject like \'%"
					+ Common.addslashes(srchkey).replace('*', '%') + "%\'";
			extrasrchkey = "&srchkey=" + Common.encode(srchkey);
			srchkey = Common.htmlspecialchars(srchkey);
		} else {
			sqlkey = "";
			extrasrchkey = "";
		}
		String sqltid = tid > 0 ? "AND tid=" + tid : "";
		String extratid = tid > 0 ? "&tid=" + tid : "";
		Map<String, Integer> multi = this
				.multi(request, response, uid,
						"SELECT COUNT(*) count FROM jrun_trades WHERE sellerid="
								+ uid + " " + sqltid + " " + sqlkey,
						"my.jsp?item=tradethreads" + extratid + extrafid
								+ extrasrchkey);
		List<Map<String, String>> tradelists = dataBaseService
				.executeQuery("SELECT * FROM jrun_trades WHERE sellerid=" + uid
						+ " " + sqltid + " " + sqlkey
						+ " ORDER BY tradesum DESC, totalitems DESC LIMIT "
						+ multi.get("start_limit") + ", "
						+ multi.get("perpage"));
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		if (tradelists != null && tradelists.size() > 0) {
			String timeoffset = (String) session.getAttribute("timeoffset");
			String timeformat = (String) session.getAttribute("timeformat");
			String dateformat = (String) session.getAttribute("dateformat");
			SimpleDateFormat sdf_all = Common.getSimpleDateFormat(dateformat
					+ " " + timeformat, timeoffset);
			for (Map<String, String> tradethread : tradelists) {
				tradethread.put("lastupdate", Common.gmdate(sdf_all, Integer
						.valueOf(tradethread.get("lastupdate"))));
				tradethread.put("lastbuyererenc", Common.encode(tradethread
						.get("lastbuyer")));
				double expiration = Integer.valueOf(tradethread
						.get("expiration"));
				if (expiration > 0) {
					expiration = (expiration - timestamp) / 86400d;
					if (expiration > 0) {
						tradethread.put("expirationhour", String
								.valueOf((int) Math.floor((expiration - Math
										.floor(expiration)) * 24)));
						tradethread.put("expiration", String.valueOf((int) Math
								.floor(expiration)));
					} else {
						tradethread.put("expiration", "-1");
					}
				}
			}
			request.setAttribute("tradelists", tradelists);
		}
		request.setAttribute("tid", tid);
		request.setAttribute("srchkey", srchkey);
		request.setAttribute("extrasrchkey", extrasrchkey);
		request.setAttribute("extratid", extratid);
		return mapping.findForward("toMy");
	}

	@SuppressWarnings("unchecked")
	public ActionForward toMyReward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String extrafid = null;
		if (srchfid > 0) {
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		} else {
			extrafid = "";
		}
		Map<String, String> settings = ForumInit.settings;
		Map extcredits = dataParse.characterParse(settings.get("extcredits"),
				false);
		String creditstrans = settings.get("creditstrans");
		request.setAttribute("extcredits", extcredits);
		request
				.setAttribute("creditstrans", creditstrans != null
						&& !creditstrans.equals("") ? Integer
						.valueOf(creditstrans) : 0);

		String type = request.getParameter("type");
		String filter = request.getParameter("filter");
		List<Map<String, String>> rewardloglists = null;
		if ("stats".equals(type)) {
			Map<String, String> questions = new HashMap<String, String>();
			Map<String, String> answers = new HashMap<String, String>();
			List<Map<String, String>> results = null;
			Map<String, String> map = null;
			results = dataBaseService
					.executeQuery("SELECT COUNT(*) total, SUM(ABS(netamount)) totalprice FROM jrun_rewardlog WHERE authorid="
							+ uid);
			map = results != null && results.size() > 0 ? results.get(0) : null;
			questions.put("total", map != null ? map.get("total") : "0");
			questions.put("totalprice", map != null
					&& map.get("totalprice") != null ? map.get("totalprice")
					: "0");
			results = dataBaseService
					.executeQuery("SELECT COUNT(*) solved FROM jrun_rewardlog WHERE authorid="
							+ uid + " and answererid>0");
			map = results != null && results.size() > 0 ? results.get(0) : null;
			questions.put("solved", map != null ? map.get("solved") : "0");
			questions.put("percent", number_format(questions.get("total"),
					questions.get("solved")));
			request.setAttribute("questions", questions);
			results = dataBaseService
					.executeQuery("SELECT COUNT(*) total FROM jrun_rewardlog WHERE answererid="
							+ uid);
			map = results != null && results.size() > 0 ? results.get(0) : null;
			answers.put("total", map != null ? map.get("total") : "0");
			results = dataBaseService
					.executeQuery("SELECT COUNT(*) tids, SUM(ABS(t.price)) totalprice FROM jrun_rewardlog r LEFT JOIN jrun_threads t USING(tid) WHERE r.authorid>0 and r.answererid="
							+ uid);
			map = results != null && results.size() > 0 ? results.get(0) : null;
			answers.put("adopted", map != null && map.get("tids") != null ? map
					.get("tids") : "0");
			answers.put("totalprice", map != null
					&& map.get("totalprice") != null ? map.get("totalprice")
					: "0");
			answers.put("percent", number_format(answers.get("total"), answers
					.get("adopted")));
			request.setAttribute("answers", answers);
		} else if ("question".equals(type)) {
			filter = filter != null
					&& (filter.equals("solved") || filter.equals("unsolved")) ? filter
					: "";
			String sqlfilter = filter.equals("") ? "" : (filter
					.equals("solved") ? " AND r.answererid>0"
					: " AND r.answererid=0");
			Map<String, Integer> multi = multi(request, response, uid,
					"SELECT COUNT(*) count FROM jrun_rewardlog r WHERE authorid="
							+ uid + sqlfilter,
					"my.jsp?item=reward&type=question&filter=" + filter);
			rewardloglists = dataBaseService
					.executeQuery("SELECT r.tid, r.answererid, r.dateline,r.netamount, t.subject, t.price, m.uid, m.username, f.fid, f.name FROM jrun_rewardlog r LEFT JOIN jrun_threads t ON t.tid=r.tid	LEFT JOIN jrun_forums f ON f.fid=t.fid LEFT JOIN jrun_members m ON m.uid=r.answererid WHERE r.authorid="
							+ uid
							+ sqlfilter
							+ " ORDER BY r.dateline DESC LIMIT "
							+ multi.get("start_limit")
							+ ", "
							+ multi.get("perpage"));
		} else if ("answer".equals(type)) {
			filter = filter != null
					&& (filter.equals("adopted") || filter.equals("unadopted")) ? filter
					: "";
			String sqlfilter = filter.equals("") ? "" : (filter
					.equals("adopted") ? " AND r.authorid>0"
					: " AND r.authorid=0");
			Map<String, Integer> multi = multi(request, response, uid,
					"SELECT COUNT(*) count FROM jrun_rewardlog r WHERE answererid="
							+ uid + sqlfilter,
					"my.jsp?item=reward&type=answer&filter=" + filter);
			rewardloglists = dataBaseService
					.executeQuery("SELECT r.tid, r.authorid, r.dateline, t.subject, t.price, m.uid, m.username, f.fid, f.name FROM jrun_rewardlog r LEFT JOIN jrun_threads t ON t.tid=r.tid	LEFT JOIN jrun_forums f ON f.fid=t.fid LEFT JOIN jrun_members m ON m.uid=t.authorid	WHERE r.answererid="
							+ uid
							+ sqlfilter
							+ " ORDER BY r.dateline DESC LIMIT "
							+ multi.get("start_limit")
							+ ", "
							+ multi.get("perpage"));
		} else {
			request.setAttribute("errorInfo", getMessage(request,
					"undefined_action_return"));
			return mapping.findForward("showMessage");
		}
		if (rewardloglists != null && rewardloglists.size() > 0) {
			String timeoffset = (String) session.getAttribute("timeoffset");
			String timeformat = (String) session.getAttribute("timeformat");
			String dateformat = (String) session.getAttribute("dateformat");
			SimpleDateFormat sdf_all = Common.getSimpleDateFormat(dateformat
					+ " " + timeformat, timeoffset);
			for (Map<String, String> rewardlog : rewardloglists) {
				rewardlog.put("dateline", Common.gmdate(sdf_all, Integer
						.valueOf(rewardlog.get("dateline"))));
				rewardlog.put("price", String.valueOf(Math.abs(Short
						.valueOf(rewardlog.get("price")))));
			}
			request.setAttribute("rewardloglists", rewardloglists);
		}
		request.setAttribute("filter", filter);
		return mapping.findForward("toMy");
	}

	public ActionForward toMyDebate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		String type = request.getParameter("type");
		this.setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String threadadd = null;
		String extrafid = null;
		if (srchfid > 0) {
			threadadd = "AND t.fid='" + srchfid + "'";
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		} else {
			threadadd = "";
			extrafid = "";
		}
		if ("orig".equals(type)) {
			Map<String, Integer> multi = this.multi(request, response, uid,
					"SELECT COUNT(*) count FROM jrun_mythreads m, jrun_threads t WHERE m.uid="
							+ uid + " AND m.special='5' " + threadadd
							+ " AND m.tid=t.tid",
					"my.jsp?item=debate&type=orig" + extrafid);
			List<Map<String, String>> debatelists = dataBaseService
					.executeQuery("SELECT t.tid, t.subject, t.fid, t.displayorder, t.closed, t.lastposter, t.lastpost, f.name	FROM jrun_threads t, jrun_mythreads m ,jrun_forums f WHERE m.uid="
							+ uid
							+ " AND m.tid=t.tid AND t.fid=f.fid AND m.special='5' "
							+ threadadd
							+ " ORDER BY m.dateline DESC LIMIT "
							+ multi.get("start_limit")
							+ ", "
							+ multi.get("perpage"));
			this.setAttribute(request, session, debatelists, "debatelists");
		} else if ("apply".equals(type)) {
			Map<String, Integer> multi = this.multi(request, response, uid,
					"SELECT COUNT(*) count FROM jrun_myposts m, jrun_threads t	WHERE m.uid="
							+ uid + " AND m.special='5' " + threadadd
							+ " AND m.tid=t.tid",
					"my.jsp?item=debate&type=apply" + extrafid);
			List<Map<String, String>> debatelists = dataBaseService
					.executeQuery("SELECT m.dateline, t.tid, t.fid, t.subject, t.displayorder, t.closed, f.name FROM jrun_myposts m, jrun_threads t ,jrun_forums f WHERE m.uid="
							+ uid
							+ " AND m.tid=t.tid AND t.fid=f.fid AND m.special='5' "
							+ threadadd
							+ " ORDER BY m.dateline DESC LIMIT "
							+ multi.get("start_limit")
							+ ", "
							+ multi.get("perpage"));
			if (debatelists != null && debatelists.size() > 0) {
				String timeoffset = (String) session.getAttribute("timeoffset");
				String timeformat = (String) session.getAttribute("timeformat");
				String dateformat = (String) session.getAttribute("dateformat");
				SimpleDateFormat sdf_all = Common.getSimpleDateFormat(
						dateformat + " " + timeformat, timeoffset);
				for (Map<String, String> debatelist : debatelists) {
					debatelist.put("dateline", Common.gmdate(sdf_all, Integer
							.valueOf(debatelist.get("dateline"))));
				}
				request.setAttribute("debatelists", debatelists);
			}
			debatelists = null;
		}
		return mapping.findForward("toMy");
	}

	public ActionForward toMyVideo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String extrafid = null;
		if (srchfid > 0) {
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		} else {
			extrafid = "";
		}
		Map<String, Integer> multi = this.multi(request, response, uid,
				"SELECT COUNT(*) count FROM jrun_videos WHERE uid=" + uid,
				"my.jsp?item=video");
		List<Map<String, String>> videolists = dataBaseService
				.executeQuery("SELECT * FROM jrun_videos WHERE uid='" + uid
						+ "' ORDER BY dateline DESC LIMIT "
						+ multi.get("start_limit") + ", "
						+ multi.get("perpage"));
		int videonum = videolists.size();
		int colspan = videonum % 2;
		StringBuffer videoendrows = new StringBuffer();
		if (colspan > 0) {
			while ((colspan - 2) < 0) {
				videoendrows.append("<td></td>");
				colspan++;
			}
			videoendrows.append("</tr>");
		}
		if (videolists != null && videolists.size() > 0) {
			String timeoffset = (String) session.getAttribute("timeoffset");
			String timeformat = (String) session.getAttribute("timeformat");
			String dateformat = (String) session.getAttribute("dateformat");
			SimpleDateFormat sdf_all = Common.getSimpleDateFormat(dateformat
					+ " " + timeformat, timeoffset);
			for (Map<String, String> videolist : videolists) {
				videolist.put("dateline", Common.gmdate(sdf_all, Integer
						.valueOf(videolist.get("dateline"))));
			}
			request.setAttribute("videolists", videolists);
		}
		request.setAttribute("videoendrows", videoendrows.toString());
		videolists = null;
		videoendrows = null;
		return mapping.findForward("toMy");
	}

	public ActionForward toMyBuddylist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		this.setExtcredits(request);
		short srchfid = (short) Common.range(Common.intval(request
				.getParameter("srchfid")), 32767, 0);
		String extrafid = null;
		if (srchfid > 0) {
			extrafid = "&amp;srchfid=" + srchfid;
			request.setAttribute("extrafid", extrafid);
		} else {
			extrafid = "";
		}
		if (request.getParameter("buddysubmit") == null) {
			List<Map<String, String>> buddylists = dataBaseService
					.executeQuery("SELECT b.buddyid,b.dateline, b.description,m.username,f.groupName FROM jrun_buddys b,jrun_members m,jrun_friendgroups f WHERE b.uid="
							+ uid
							+ " AND m.uid=b.buddyid AND b.buddygroupid=f.id ORDER BY dateline DESC,f.groupName");
			if (buddylists != null && buddylists.size() > 0) {
				String timeoffset = (String) session.getAttribute("timeoffset");
				String timeformat = (String) session.getAttribute("timeformat");
				String dateformat = (String) session.getAttribute("dateformat");
				SimpleDateFormat sdf_all = Common.getSimpleDateFormat(
						dateformat + " " + timeformat, timeoffset);
				for (Map<String, String> videolist : buddylists) {
					videolist.put("dateline", Common.gmdate(sdf_all, Integer
							.valueOf(videolist.get("dateline"))));
				}
				String sql = "select f.groupName,f.id from jrun_friendgroups f where f.uid="
						+ uid + "  or f.type=1";
				List<Map<String, String>> groupNamelist = dataBaseService
						.executeQuery(sql);
				request.setAttribute("grouplist", groupNamelist);
				request.setAttribute("buddylists", buddylists);
			}
			return mapping.findForward("toMy");
		} else {
			String[] delete = request.getParameterValues("delete");
			if (delete != null) {
				StringBuffer buddyids = new StringBuffer();
				for (String buddyid : delete) {
					buddyids.append(buddyid + ",");
				}
				int length = buddyids.length();
				if (length > 0) {
					buddyids.deleteCharAt(length - 1);
					dataBaseService.runQuery(
							"DELETE FROM jrun_buddys WHERE uid=" + uid
									+ " AND buddyid IN ( " + buddyids + " )",
							true);
				}
			}
			List<Map<String, String>> buddys = dataBaseService
					.executeQuery("SELECT buddyid,description FROM jrun_buddys WHERE uid="
							+ uid);
			if (buddys != null && buddys.size() > 0) {
				for (Map<String, String> buddy : buddys) {
					String description = request.getParameter("description["
							+ buddy.get("buddyid") + "]");
					if (description != null) {
						description = description.length() > 255 ? description
								.substring(0, 255) : description;
						if (!description.equals(buddy.get("description"))) {
							dataBaseService.runQuery(
									"UPDATE jrun_buddys SET description='"
											+ Common.addslashes(description)
											+ "' WHERE uid=" + uid
											+ " AND buddyid="
											+ buddy.get("buddyid"), true);
						}
					}
				}
			}
			String jsprun_userss = session.getAttribute("jsprun_userss")
					.toString();
			String newbuddy = request.getParameter("newbuddy");
			String newbuddyid = request.getParameter("newbuddyid");
			if (newbuddyid != null) {
				Common.setResponseHeader(response);
			}
			if ((newbuddy != null && !"".equals(newbuddy) && !newbuddy
					.equals(jsprun_userss))
					|| (newbuddyid != null && !newbuddyid.equals(uid + ""))) {
				byte jsprun_adminid = (Byte) session
						.getAttribute("jsprun_adminid");
				if (jsprun_adminid == 0) {
					if (buddys != null && buddys.size() > 20) {
						if (newbuddyid != null) {
							try {
								response.getWriter()
										.write(
												getMessage(request,
														"buddy_add_toomany"));
							} catch (IOException e) {
								e.printStackTrace();
							}
							return null;
						} else {
							request.setAttribute("resultInfo", getMessage(
									request, "buddy_add_toomany"));
							return mapping.findForward("showMessage");
						}
					}
				}
				List<Map<String, String>> members = dataBaseService
						.executeQuery("SELECT uid FROM jrun_members WHERE "
								+ (newbuddyid == null ? "username='"
										+ Common.addslashes(newbuddy) + "'"
										: "uid=" + newbuddyid));
				if (members != null && members.size() > 0) {
					String buddyid = members.get(0).get("uid");
					boolean flag = false;
					if (buddys != null && buddys.size() > 0) {
						for (Map<String, String> buddy : buddys) {
							if (buddyid.equals(buddy.get("buddyid"))) {
								flag = true;
							}
						}
					}
					if (flag) {
						if (newbuddyid != null) {
							try {
								response.getWriter()
										.write(
												getMessage(request,
														"buddy_add_invalid"));
							} catch (IOException e) {
								e.printStackTrace();
							}
							return null;
						} else {
							request.setAttribute("resultInfo", getMessage(
									request, "buddy_add_invalid"));
							return mapping.findForward("showMessage");
						}
					} else {
						int timestamp = (Integer) (request
								.getAttribute("timestamp"));
						String newdescription = request
								.getParameter("newdescription");

						dataBaseService
								.runQuery(
										"INSERT INTO jrun_buddys (uid, buddyid, dateline, description,buddygroupid) VALUES ("
												+ uid
												+ ", "
												+ buddyid
												+ ", '"
												+ timestamp
												+ "', '"
												+ (newdescription != null ? (newdescription
														.length() > 255 ? Common
														.addslashes(newdescription
																.substring(0,
																		255))
														: Common
																.addslashes(newdescription))
														: "") + "',1)", true);
					}
				} else {
					if (newbuddyid != null) {
						try {
							response.getWriter()
									.write(
											getMessage(request,
													"username_nonexistence"));
						} catch (IOException e) {
							e.printStackTrace();
						}
						return null;
					} else {
						request.setAttribute("errorInfo", getMessage(request,
								"username_nonexistence"));
						return mapping.findForward("showMessage");
					}
				}
			}
			if (newbuddyid != null) {
				try {
					response.getWriter().write(
							getMessage(request, "buddy_update_succeed"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			} else {
				if (Common.isshowsuccess(session, "buddy_update_succeed")) {
					Common.requestforward(response, "my.jsp?item=buddylist");
					return null;
				} else {
					request.setAttribute("successInfo", getMessage(request,
							"buddy_update_succeed"));
					request
							.setAttribute("requestPath",
									"my.jsp?item=buddylist");
					return mapping.findForward("showMessage");
				}
			}
		}
	}

	/**
	 * 研究员和管理员任务
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward task(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Members member = (Members) session.getAttribute("user");
		String option = request.getParameter("option");
		request.setAttribute("option", option);
		if (option.equals("tasklist")) {
			if (member == null) {
				request.setAttribute("errorInfo", "你还未登录,不能执行此操作");
				return mapping.findForward("showMessage");
			}
			String exgroup=session.getAttribute("exgroup")+"";
			if (member.getUid() != 1 && !"17".equals(exgroup)) {
				request.setAttribute("errorInfo", "对不起,只有研究员和管理员才能查看任务");
				return mapping.findForward("showMessage");
			}
			String hql = "from Scenetask s ";

			if ("17".equals(exgroup)) {
				hql += " where s.createUser='" + member.getUsername() + "'";
			}
			hql += " order by s.taskTime desc";
			int curpage = (request.getParameter("curpage") != null && !""
					.equals(request.getParameter("curpage"))) ? Integer
					.parseInt(request.getParameter("curpage")) : 0;
			List<Scenetask> sctlist = scenetaskService.getByCondition(hql,
					curpage, 12);
			int count = scenetaskService.getCountByCondition(hql);
			int total = count == 0 ? 0 : count % 12 == 0 ? count / 12
					: count / 12 + 1;
			request.setAttribute("curpage", curpage);
			request.setAttribute("count", count);
			request.setAttribute("total", total);
			request.setAttribute("sctlist", sctlist);
		} else if ("addtask".equals(option)) {
			String submit = request.getParameter("submit");
			if (submit == null) {
				request.setAttribute("count", 3);
			} else {
				String taskName = request.getParameter("taskName");
				String taskIntro = request.getParameter("taskIntro");
				//String taskNumber = request.getParameter("taskNumber");
				String[] user=request.getParameterValues("checkuser");
				String credit=request.getParameter("credit");
				String[] taskNo = request.getParameterValues("taskNo");
				if ("".equals(taskName.trim())) {
					request.setAttribute("errorInfo", "任务名称不能为空");
					return mapping.findForward("showMessage");
				}
				if ("".equals(taskIntro.trim())) {
					request.setAttribute("errorInfo", "任务介绍不能为空");
					return mapping.findForward("showMessage");
				}
				if(!Common.isNum(credit)){
					request.setAttribute("errorInfo", "任务积分必须为整数");
					return mapping.findForward("showMessage");
				}
				if (user==null||user.length==0) {
					request.setAttribute("errorInfo", "会员不能为空");
					return mapping.findForward("showMessage");
				}
				if (taskNo == null || taskNo.length == 0) {
					request.setAttribute("errorInfo", "没有添加要执行的任务，任务添加失败");
					return mapping.findForward("showMessage");
				}
				Scenetask stask = new Scenetask();
				stask.setTaskIntro(taskIntro);
				stask.setTaskName(taskName);
				stask.setCredit(Integer.parseInt(credit));
				stask.setCreateUser(member.getUsername());
				stask.setTaskTime((new SimpleDateFormat("yyyy年MM月dd日"))
						.format(new Date()));
				if (scenetaskService.saveOrUpdateTask(stask)) {
					//taskNumber = taskNumber.lastIndexOf(",") == (taskNumber.length() - 1) ? taskNumber.substring(0, taskNumber.length() - 1) : taskNumber;
					//String[] memb = taskNumber.split(",");
					boolean success = true;
					Scenetm stm = null;
					for (int i = 0; i < user.length; i++) {
						stm = new Scenetm();
						stm.setTaskId(stask.getTaskId());
						stm.setTaskTime("");
						stm.setTaskState(Short.parseShort("1"));
						Short getcredit = 0;
						stm.setGetCredit(getcredit);
						stm.setUserName(Common.htmlspecialchars(user[i]));
						if (!scenetmService.save(stm)) {
							scenetmService
									.deleteByHql("delete from Scenetm s where s.taskId="
											+ stask.getTaskId());
							scenetaskService
									.deleteByHql("delete from Scenetask st where st.taskId="
											+ stask.getTaskId());
							success = false;
							break;
						}

						stm = null;
					}
					boolean lastsuccess = true;
					if (success) {
						Scenect sct = null;
						for (int j = 0; j < user.length; j++) {
							for (int i = 0; i < taskNo.length; i++) {
								sct = new Scenect();
								String taskType = request.getParameter("taskType" + (i + 1));
								String surveyName = request.getParameter("surveyName" + (i + 1));
								String surveyUrl = request.getParameter("surveyUrl" + (i + 1));
								String tasksubid = request.getParameter("tasksubid" + (i + 1));
								sct.setTaskType(Short.parseShort(taskType));
								sct.setTaskId(stask.getTaskId());
								sct.setUserName(user[j]);
								boolean issuccess = false;
								//sct.setTaskState(Short.parseShort(1 + ""));
								Short state=1;
								sct.setTaskState(state);
								if (taskType.equals("1")) {
									if (!"".equals(surveyName)
											&& !"".equals(surveyUrl)) {
										sct.setSurveyName(surveyName);
										sct.setSurveyUrl(surveyUrl);
										issuccess = true;
									} else {
										issuccess = false;
									}
								}
								if ("2".equals(taskType)) {
									if (!"".equals(tasksubid)) {
										sct.setObjId(Integer
												.parseInt(tasksubid));
										issuccess = true;
									} else {
										issuccess = false;
									}
								}
								if (issuccess) {
									sct.setTaskNo(Integer.parseInt(taskNo[i]));
									if (!scenectService.saveOrUpdate(sct)) {
										lastsuccess = false;
										break;
									}
								}
							}
						}
						if (lastsuccess) {
							String numbers="";
							for(String nu:user){
								numbers=nu+",";
							}
							List<Members> meb = this
									.getUserEmailFromE(numbers);
							if (meb != null && meb.size() > 0) {
								for (Members mebs : meb) {
									this.sendEmail(request, mebs.getEmail(),
											mebs.getUsername(), taskName);
								}
							}
							request.setAttribute("successInfo", "任务添加成功");
							request.setAttribute("requestPath",
									"my.jsp?item=task&option=tasklist");
							return mapping.findForward("showMessage");
						} else {
							scenetmService
									.deleteByHql("delete from Scenetm s where s.taskId="
											+ stask.getTaskId());
							scenetaskService
									.deleteByHql("delete from Scenetask st where st.taskId="
											+ stask.getTaskId());
							request.setAttribute("errorInfo", "任务添加失败");
							return mapping.findForward("showMessage");
						}
					}
				}
				request.setAttribute("errorInfo", "任务添加失败");
				return mapping.findForward("showMessage");
			}

		} else if ("edittask".equals(option)) {
			String taskid = request.getParameter("taskid");
			if (taskid == null || "".equals(taskid)) {
				request.setAttribute("errorInfo", "没有选中要编辑的任务");
				return mapping.findForward("showMessage");
			}
			String submit = request.getParameter("submit");
			if (submit == null || "".equals(submit)) {
				Scenetask stask = scenetaskService.getScenetaskById(Integer
						.parseInt(taskid));
				if (stask == null) {
					request.setAttribute("errorInfo", "没有该任务");
					return mapping.findForward("showMessage");
				}
				request.setAttribute("scenetask", stask);

				List<Map<String, String>> mapsct = null;
				List<Map<String, String>> lscenect = this.getTask(Integer
						.parseInt(taskid));
				int count = 0;
				if (lscenect != null && lscenect.size() > 0) {
					mapsct = new ArrayList<Map<String, String>>();
					Map<String, String> mct = null;
					for (Map<String, String> scenect : lscenect) {
						mct = new HashMap<String, String>();
						if (scenect.get("subTaskType").equals("1")) {
							mct.put("surveyName", scenect.get("subTaskDesc"));
							mct.put("surveyUrl", scenect.get("subTaskURL"));
						} else {
							List<Scene> lsce = sceneService
									.getSceneByCondition("from Scene s where s.objId="
											+ scenect.get("subTaskResoureceID"));
							mct.put("objName", lsce.size() > 0 ? lsce.get(0)
									.getObjName() : "");
							mct.put("objId", scenect.get("subTaskResoureceID"));
						}
						mct.put("taskNo", scenect.get("subTaskIndex"));
						mct.put("taskType", scenect.get("subTaskType"));
						mapsct.add(mct);
					}
					count = lscenect.size();
					request.setAttribute("lscenect", mapsct);
				}
				request.setAttribute("count", count);
				String person = "";
		//		List<Map<String, String>> ltm = dataBaseService.executeQuery("select distinct userName from jrun_scenect where taskId="+ taskid);
				List<Map<String, String>> ltm = dataBaseService.executeQuery("select distinct userName from vcity_subTask where taskId="+ taskid);
				if (ltm != null && ltm.size() > 0) {
					for (Map<String, String> map : ltm) {
						person = person.equals("") ? map.get("userName")
								: person + "," + map.get("userName");
					}
				}
				request.setAttribute("person", person);
				return mapping.findForward("toMy");
			} else {
				String taskName = request.getParameter("taskName");
				String taskIntro = request.getParameter("taskIntro");
				String credit=request.getParameter("credit");
				String taskNumber = request.getParameter("taskNumber");
				String[] taskNo = request.getParameterValues("taskNo");
				String user = request.getParameter("createUser");
				if ("".equals(taskName.trim())) {
					request.setAttribute("message", "任务名称不能为空");
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if ("".equals(taskIntro.trim())) {
					request.setAttribute("message", "任务介绍不能为空");
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if(!Common.isNum(credit)){
					request.setAttribute("message", "任务介绍不能为空");
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if ("".equals(taskNumber)) {
					request.setAttribute("message", "会员不能为空");
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				if (taskNo == null || taskNo.length == 0) {
					request.setAttribute("message", "没有添加要执行的任务，任务修改失败");
					request.setAttribute("return", true);
					return mapping.findForward("message");
				}
				Scenetask stask = new Scenetask();
				stask.setTaskId(Integer.parseInt(taskid));
				stask.setTaskIntro(taskIntro);
				stask.setTaskName(taskName);
				stask.setCredit(Integer.parseInt(credit));
				stask.setTaskTime(request.getParameter("taskTime"));
				stask.setCreateUser(user);
				;
				scenetaskService.saveOrUpdateTask(stask);

				String[] tmb = taskNumber.split(",");
				for (String name : tmb) {
					if (!this.userExist(name,taskid)) {
						Scenetm stm = new Scenetm();
						Short getcredit = 0;
						stm.setGetCredit(getcredit);
						stm.setTaskId(Integer.parseInt(taskid));
						Short state = 1;
						stm.setTaskState(state);
						stm.setUserName(name);
						stm.setTaskTime("");
						scenetmService.save(stm);
						stm = null;

						for (int i = 0; i < taskNo.length; i++) {
							String taskType = request.getParameter("types"
									+ (i + 1));
							Scenect sct = new Scenect();
							sct.setTaskId(Integer.parseInt(taskid));
							if (taskType.equals("1")) {
								String surveyName = request
										.getParameter("surveyName" + (i + 1));
								String surveyUrl = request
										.getParameter("surveyUrl" + (i + 1));
								sct.setSurveyName(surveyName);
								sct.setSurveyUrl(surveyUrl);
							}
							if (taskType.equals("2")) {
								String tasksubid = request
										.getParameter("tasksubid" + (i + 1));
								sct.setObjId(Integer.parseInt(tasksubid));
							}
							sct.setTaskType(Short.parseShort(taskType + ""));
							sct.setTaskState(state);
							sct.setUserName(name);
							sct.setTaskNo(Integer.parseInt(taskNo[i]));
							scenectService.saveOrUpdate(sct);
							if (i == taskNo.length - 1) {
								List<Members> meb = this
										.getUserEmailFromE(name);
								if (meb != null && meb.size() > 0) {
									for (Members mebs : meb) {
										this.sendEmail(request,
												mebs.getEmail(), mebs
														.getUsername(),
												taskName);
									}
								}
							}
						}
					}
				}
				request.setAttribute("successInfo", "任务编辑完成");
				request.setAttribute("requestPath",
						"my.jsp?item=task&option=tasklist");
				return mapping.findForward("showMessage");
			}
		}
		else if("delete".equals(option)){
			String tid=request.getParameter("tid");
	    	if(tid==null||"".equals(tid)){
				request.setAttribute("message","未选中要删除的任务,请返回");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
	    	dataBaseService.runQuery("delete from jrun_scenetask where taskId="+tid, false);
	    	dataBaseService.runQuery("delete from jrun_scenetm where taskId="+tid, false);
	    	//dataBaseService.runQuery("delete from jrun_scenect where taskId="+tid, false);
	    	dataBaseService.runQuery("delete from vcity_subTask where taskId="+tid, false);
	    	request.setAttribute("successInfo", "任务删除成功");
			request.setAttribute("requestPath",
					"my.jsp?item=task&option=tasklist");
			return mapping.findForward("showMessage");
		}
		return mapping.findForward("toMy");
	}
	
	
	/**
	 * 得到任务详情方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doGetDeitalTask(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String tid=request.getParameter("tid");
		if(tid==null||"".equals(tid)){
			request.setAttribute("message","未选中任务,请返回");
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}		
		Scenetask scenetask=scenetaskService.getScenetaskById(Integer.parseInt(tid));//得到任务说明
		if(scenetask==null){
			request.setAttribute("message","没有该任务,请返回");
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		request.setAttribute("scenetask", scenetask);
		
		/**
		 * 得到任务开始
		 */
		List<Map<String,String>> lscenect=this.getTask(Integer.parseInt(tid));
		List<Map<String,String>> mapsct=null;
		if(lscenect!=null&&lscenect.size()>0){
			mapsct=new ArrayList<Map<String,String>>();
			Map<String,String> mct=null;
			for(Map<String,String> scenect:lscenect){
				mct=new HashMap<String,String>();
				if(scenect.get("subTaskType").equals("1")){
					mct.put("surveyName", scenect.get("subTaskDesc"));
					mct.put("surveyUrl", scenect.get("subTaskURL"));
				}
				else{
					List<Scene> lsce=sceneService.getSceneByCondition("from Scene s where s.objId="+scenect.get("objId"));
					mct.put("objName", lsce.size()>0?lsce.get(0).getObjName():"");
				}	
				mct.put("taskType", scenect.get("subTaskType"));
				mapsct.add(mct);
			}
			request.setAttribute("lscenect",mapsct);
		}		
		/**
		 * 得到任务结束
		 */
		
		List<Map<String,String>> listgetperson=dataBaseService.executeQuery("select userName,taskState,taskTime from jrun_scenetm where taskId="+tid);
		List taskdetial=null;
		if(listgetperson!=null&&listgetperson.size()>0){
			taskdetial=new ArrayList();
			for(Map<String,String> map:listgetperson){
				Map mapdetial=new HashMap();
				mapdetial.put("userName", map.get("userName"));
				mapdetial.put("taskState",map.get("taskState").equals("1")?"未完成":"已完成,<br/>完成时间:"+map.get("taskTime"));
				if(map.get("taskState").equals("2")){
				//	String sql="select sct.taskType,sct.surveyName,sct.surveyUrl,sct.objId from jrun_scenect sct inner join jrun_scenectorder scto on sct.id=scto.scenectId where sct.taskId="+tid+" and scto.userName='"+map.get("userName")+"' and sct.taskState=2 order by scto.id";
					String sql="select sct.subTaskType,sct.subTaskDesc,sct.subTaskURL,sct.subTaskResoureceID " +
							"from vcity_subTask sct inner join jrun_scenectorder scto " +
							"on sct.subTaskID=scto.scenectId where sct.taskId="+tid+" and scto.userName='"+map.get("userName")+"'" +
									" and sct.subTaskState=2 order by scto.id";
					List<Map<String,String>> scto=dataBaseService.executeQuery(sql);
					List<Map<String,String>> lmap=null;
					if(scto!=null&&scto.size()>0){
						lmap=new ArrayList<Map<String,String>>();
						for(Map<String,String> sctomap:scto){
							Map<String,String> maps=new HashMap<String,String>();
							maps.put("taskType", sctomap.get("subTaskType"));
							if(sctomap.get("subTaskType").equals("2")){
								List<Scene> scenelist=sceneService.getSceneByCondition("from Scene s where s.objId="+sctomap.get("subTaskResoureceID"));
								String objName=scenelist.size()>0?scenelist.get(0).getObjName():"";
								maps.put("objName",objName);
							}
							else{
								maps.put("surveyName", "<a href='"+sctomap.get("subTaskURL")+"' target='_blank'>"+sctomap.get("subTaskDesc")+"</a>");
							}
							lmap.add(maps);
							maps=null;
						}
					}
					mapdetial.put("order", lmap);
				}
				taskdetial.add(mapdetial);
				mapdetial=null;
			}
		}
		request.setAttribute("detial",taskdetial);
		return mapping.findForward("taskdetial");
	}
	/**
	 * 判断用户是否已经存在任务列表里
	 * @param username
	 * @return
	 */
	public boolean userExist(String username,String taskid){
		List<Map<String,String>> lstm=dataBaseService.executeQuery("select * from jrun_scenetm where userName='"+username+"' and taskId="+taskid);
		return (lstm==null||lstm.size()==0)?false:true;
	}
	/**
	 * 得到任务
	 * 
	 * @param tid
	 * @return
	 */
	public List<Map<String, String>> getTask(int tid) {
		List<Map<String, String>> lct = dataBaseService
				.executeQuery("select *, count(distinct userName) from vcity_subTask where taskId="
						+ tid + " group by userName");
		List<Map<String, String>> lctlist = null;
		if (lct != null && lct.size() > 0) {
			String username = lct.get(0).get("userName");
			lctlist = dataBaseService
					.executeQuery("select * from vcity_subTask where userName='"
							+ username + "' and taskId=" + tid
							+ " order by subTaskIndex");
		}
		return lctlist;
	}

	/**
	 * 得到场景树列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doGetTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		List<Scene> scene = sceneService.getAllScene();
		String var = "";
		if (scene != null && scene.size() > 0) {
			for (int i = 0; i < scene.size(); i++) {
				Scene sce = scene.get(i);
				if (sce.getPid() == 0) {
					var += "d.add(" + sce.getId() + "," + (i == 0 ? -1 : 1)
							+ ",'" + sce.getObjName()
							+ "',\"javascript:getHostList(" + sce.getObjId()
							+ ",'" + sce.getObjName() + "')\",'','');\n";
				} else {
					var += "d.add(" + sce.getId() + "," + sce.getPid() + ",'"
							+ sce.getObjName() + "',\"javascript:getHostList("
							+ sce.getObjId() + ",'" + sce.getObjName()
							+ "')\",'','');\n";
				}
			}
		}
		request.setAttribute("size", request.getParameter("size"));
		request.setAttribute("scene", var);
		return mapping.findForward("scene");
	}

	/**
	 * 更改好友所在的组
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doUpdateGroupName(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String id = request.getParameter("ids");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if ("".equals(id)) {
			out.write("1");
		} else {
			String[] ids = id.split(",");
			if (ids.length == 0) {
				out.write("1");
			} else {
				String updatesql = "update jrun_buddys set buddygroupid="
						+ ids[0] + " where buddyid=" + ids[1];
				Map<String, String> mapstr = dataBaseService
						.runQuery(updatesql);
				if (mapstr != null && mapstr.size() > 0) {
					out.write("2");
				} else {
					out.write("3");
				}
			}
		}
		return null;
	}

	/**
	 * 好友组管理
	 * 
	 * @param request
	 */
	public ActionForward doManageFriendGroup(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String option = request.getParameter("option");
		HttpSession session = request.getSession();
		String uid = session.getAttribute("jsprun_uid") + "";
		if ("show".equals(option)) {
			String sql = "select f.* from jrun_friendgroups f where f.uid="
					+ uid + "  or f.type=1";
			List<Map<String, String>> grouplist = dataBaseService
					.executeQuery(sql);
			request.setAttribute("grouplist", grouplist);
			String id = request.getParameter("id");
			if (id != null && !"".equals(id)) {
				String getgroup = "select * from jrun_friendGroups f where uid="
						+ uid + " and id=" + id;
				List<Map<String, String>> group = dataBaseService
						.executeQuery(getgroup);
				if (group == null || group.size() == 0) {
					request.setAttribute("errorInfo", "请选择要编辑的好友组");
					return mapping.findForward("showMessage");
				}
				request.setAttribute("group", group.get(0));
			}
		} else if ("add".equals(option)) {
			int timestamp = (Integer) (request.getAttribute("timestamp"));
			String groupName = request.getParameter("groupName");

			if (!"".equals(groupName)) {
				FriendGroups friend = new FriendGroups();
				friend.setCreateTime(timestamp);
				friend.setGroupName(groupName);
				friend.setType(2);
				friend.setUid(Integer.parseInt(uid));
				friend.setUpdateFlag(2);
				boolean issuccess = friendGroupsService.addFriendGroups(friend);
				if (issuccess) {
					try {
						request.getRequestDispatcher(
								"my.jsp?item=managefriendGroup&option=show")
								.forward(request, response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					request.setAttribute("errorInfo", "添加失败");
					return mapping.findForward("showMessage");
				}
			}
			if ("".equals(groupName)) {
				request.setAttribute("errorInfo", "请输入组名");
				return mapping.findForward("showMessage");
			}
		} else if ("edit".equals(option)) {
			String groupName = request.getParameter("groupName");
			if ("".equals(groupName)) {
				request.setAttribute("errorInfo", "请输入组名");
				return mapping.findForward("showMessage");
			}
			String id = request.getParameter("id");
			String createTime = request.getParameter("createTime");
			FriendGroups friend = new FriendGroups();
			friend.setCreateTime(Integer.parseInt(createTime));
			friend.setGroupName(groupName);
			friend.setId(Integer.parseInt(id));
			friend.setType(2);
			friend.setUid(Integer.parseInt(uid));
			friend.setUpdateFlag(2);
			boolean issuccess = friendGroupsService.addFriendGroups(friend);
			if (issuccess) {
				try {
					request.getRequestDispatcher(
							"my.jsp?item=managefriendGroup&option=show")
							.forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				request.setAttribute("errorInfo", "修改失败");
				return mapping.findForward("showMessage");
			}
		} else if ("delete".equals(option)) {
			String did = request.getParameter("did");
			String updatesql = "update jrun_buddys set buddygroupid=1 where buddygroupid="
					+ did;
			dataBaseService.runQuery(updatesql);
			String deletesql = "delete from jrun_friendgroups where id=" + did;
			dataBaseService.execute(deletesql);
			try {
				request.getRequestDispatcher(
						"my.jsp?item=managefriendGroup&option=show").forward(
						request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mapping.findForward("toMy");
	}

	/**
	 * 前台我的礼物相关的功能
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toMyGift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		String option = request.getParameter("option");
		if ("sendgift".equals(option) || "receivegift".equals(option)) {
			int curpage = request.getParameter("curpage") == null ? 0 : Integer
					.parseInt(request.getParameter("curpage"));
			String sql = "select p.name,p.presenturl,s.sendtime,m.username,m.uid from jrun_sendgifts s inner join jrun_present p on s.presentid=p.id inner join jrun_members m on s.receiveid=m.uid where ";
			sql += ("sendgift".equals(option)) ? "s.sendid=" + uid + ""
					: "s.receiveid=" + uid + "";
			List<Map<String, String>> count = dataBaseService.executeQuery(sql);
			sql += " order by s.sendtime desc limit " + (curpage) * 12 + ",12";
			List<Map<String, String>> list = dataBaseService.executeQuery(sql);
			if (list != null && list.size() > 0) {
				int total = count.size() % 12 == 0 ? count.size() / 12 : count
						.size() / 12 + 1;
				request.setAttribute("list", list);
				request.setAttribute("count", count.size());
				request.setAttribute("total", total);
			} else {
				request.setAttribute("count", 0);
				request.setAttribute("total", 0);
			}
			request.setAttribute("curpage", curpage);
			request.setAttribute("item", "gift");
		} else {
			String getgroup = "select f.* from jrun_friendgroups f where f.uid="
					+ uid + "  or f.type=1";
			List<Map<String, String>> grouplist = dataBaseService
					.executeQuery(getgroup);
			request.setAttribute("grouplist", grouplist);
			String getbuddy = "select b.*,m.username from jrun_buddys b inner join jrun_members m on b.buddyid=m.uid  where b.uid="
					+ uid;
			List<Map<String, String>> buddy = dataBaseService
					.executeQuery(getbuddy);
			request.setAttribute("buddy", buddy);
			String sql = "select * from jrun_present order by creattime desc";
			List<Map<String, String>> giftlist = dataBaseService
					.executeQuery(sql);
			request.setAttribute("giftlist", giftlist);
		}
		request.setAttribute("option", option);
		return mapping.findForward("toMy");
	}

	public ActionForward doSendGift(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		int uid = (Integer) session.getAttribute("jsprun_uid");
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		String[] ids = request.getParameterValues("id");
		if (ids == null || ids.length == 0) {
			request.setAttribute("errorInfo", "礼物接收人不能为空");
			return mapping.findForward("showMessage");
		}
		String pid = request.getParameter("pid");
		if (pid == null || "".equals(pid)) {
			request.setAttribute("errorInfo", "请选择要赠送的礼物");
			return mapping.findForward("showMessage");
		}
		String message = request.getParameter("message");
		if ("".equals(message)) {
			request.setAttribute("errorInfo", "请填写祝福语");
			return mapping.findForward("showMessage");
		}
		List<SendGifts> list = new ArrayList<SendGifts>();
		for (int i = 0; i < ids.length; i++) {
			SendGifts send = new SendGifts();
			send.setMessage(message);
			send.setPresentId(Integer.parseInt(pid));
			send.setReceiveId(Integer.parseInt(ids[i]));
			send.setSendTime(timestamp);
			send.setSendId(uid);
			list.add(send);
			send = null;
		}
		boolean b = sendGiftsService.saveorupdate(list);
		if (b) {
			request.setAttribute("successInfo", "礼物赠送成功");
			request.setAttribute("requestPath",
					"my.jsp?item=gift&option=sendgift");
			return mapping.findForward("showMessage");
		} else {
			request.setAttribute("errorInfo", "礼物赠送失败");
			return mapping.findForward("showMessage");
		}
	}

	@SuppressWarnings("unchecked")
	private void setExtcredits(HttpServletRequest request) {
		request.setAttribute("extcredits", dataParse.characterParse(
				ForumInit.settings.get("extcredits"), true));
	}

	@SuppressWarnings("unchecked")
	private Map<String, Integer> multi(HttpServletRequest request,
			HttpServletResponse response, int uid, String sql, String url) {
		HttpSession session = request.getSession();
		Map<String, String> settings = ForumInit.settings;
		Members member = uid > 0 ? (Members) session.getAttribute("user")
				: null;
		List<Map<String, String>> count = dataBaseService.executeQuery(sql);
		int threadcount = Integer.valueOf(count.get(0).get("count"));
		Long threadmaxpages = Long.valueOf(settings.get("threadmaxpages"));
		int tpp = member != null && member.getTpp() > 0 ? member.getTpp()
				: Integer.valueOf(settings.get("topicperpage"));
		int page = Common.range(Common.intval(request.getParameter("page")),
				threadmaxpages.intValue(), 1);
		Map<String, Integer> multiInfo = Common.getMultiInfo(threadcount, tpp,
				page);
		page = multiInfo.get("curpage");
		Map<String, Object> multi = Common.multi(threadcount, tpp, page, url,
				threadmaxpages.intValue(), 10, true, false, null);
		request.setAttribute("multi", multi);
		multiInfo.put("perpage", tpp);
		return multiInfo;
	}

	private String number_format(String total, String num) {
		return Common.number_format((Float.valueOf(total) > 0 ? Float
				.valueOf(num)
				/ Float.valueOf(total) * 100 : 0), "0.00")
				+ "%";
	}

	private void setAttribute(HttpServletRequest request, HttpSession session,
			List<Map<String, String>> lists, String attributeName) {
		if (lists != null && lists.size() > 0) {
			String timeoffset = (String) session.getAttribute("timeoffset");
			String timeformat = (String) session.getAttribute("timeformat");
			String dateformat = (String) session.getAttribute("dateformat");
			SimpleDateFormat sdf_all = Common.getSimpleDateFormat(dateformat
					+ " " + timeformat, timeoffset);
			for (Map<String, String> list : lists) {
				list.put("lastpost", Common.gmdate(sdf_all, Integer
						.valueOf(list.get("lastpost"))));
				list
						.put("lastposterenc", Common.encode(list
								.get("lastposter")));
			}
			request.setAttribute(attributeName, lists);
		}
	}

	/**
	 * 从e声网得到任务参与人员的信息集合
	 * 
	 * @param username
	 * @return
	 */
	public static List<Members> getUserEmailFromE(String username) {
		Connection con = Common.getConnect();
		List<Members> useremail = null;
		if (con != null) {
			String sql = "SELECT customer_account,password,email FROM customer where customer_account=";
			if (sql.indexOf(",") != -1) {
				username = username.lastIndexOf(",") == username.length() - 1 ? username
						.substring(0, username.length() - 1)
						: username;
				String[] mem = username.split(",");
				for (int i = 0; i < mem.length; i++) {
					if (!"".equals(mem[i])) {
						if (i == 0) {
							sql += "'" + mem[i] + "'";
						} else {
							sql += " or customer_account='" + mem[i] + "'";
						}
					}
				}
			}
			else{
				sql+="'"+username+"'";
			}

			PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				if (rs != null) {
					useremail = new ArrayList<Members>();
					while (rs.next()) {
						Members member = new Members();
						member.setUsername(rs.getString("customer_account"));
						member.setPassword(rs.getString("password"));
						member.setEmail(rs.getString("email"));
						useremail.add(member);
						member = null;
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		return useremail;
	}

	/**
	 * 发送邮件
	 * 
	 * @param request
	 * @param email
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean sendEmail(HttpServletRequest request, String email,
			String username, String taskname) {
		Map<String, String> oldSettings = ForumInit.settings;
		Map<String, String> mailparam = dataParse.characterParse(oldSettings
				.get("mail"), false);
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		String timeoffset = (String) oldSettings.get("timeoffset");
		String date = Common.gmdate("yyyy-MM-dd HH:mm:ss", timestamp,
				timeoffset);
		;
		String host = mailparam.get("server");
		int port = Integer.parseInt(mailparam.get("port"));
		String auth = mailparam.get("auth");
		String auth_username = mailparam.get("auth_username");
		String auth_password = mailparam.get("auth_password");
		String test_from = mailparam.get("from");
		String htmlBody = InvitationTemplate.getEmailTemplate(request,
				username, taskname);
		Mail mail = new Mail(host, port, auth, auth_username, auth_password,
				"1");
		String alertmsg = mail.sendMessage(test_from, email,
				"《第一体验俱乐部》提醒：您有新的任务", null, htmlBody);
		if (alertmsg == null) {
			return true;
		} else {
			return false;
		}
	}
}