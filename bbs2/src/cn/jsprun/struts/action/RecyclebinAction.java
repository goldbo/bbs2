package cn.jsprun.struts.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import cn.jsprun.domain.Members;
import cn.jsprun.struts.form.PageForm;
import cn.jsprun.struts.form.RecyclebinForm;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;

public class RecyclebinAction extends BaseAction {

	public ActionForward fromRecyclebin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String searchsubmit = request.getParameter("searchsubmit");
		if(searchsubmit==null){
			Common.requestforward(response, "admincp.jsp?action=recyclebin");
			return null;
		}
		HttpSession session = request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		StringBuffer tids = new StringBuffer();
		RecyclebinForm rf = (RecyclebinForm) form;
		String sql = recyclebinService.findByAll(rf,timeoffset);
		List<Map<String,String>> counts = dataBaseService.executeQuery("select t.tid "+sql);
		int totalSize = counts.size();
		int totalPage = 1;
		int pageSize = 10; 
		int startid = 0;
		int currentpage = 1;
		if (totalSize > pageSize) {
			if (totalSize % pageSize == 0) {
				totalPage = (int) ((double) totalSize / (double) pageSize);
			} else {
				totalPage = (int) (1.0d + (double) totalSize / (double) pageSize);
			}
		} else {
			totalPage = 1;
		}
		if (currentpage < 0) {
		} else {
			if (currentpage > totalPage) {
				currentpage = totalPage;
			}
			startid = pageSize * (currentpage - 1);
		}
		List<Map<String,String>> list = dataBaseService.executeQuery("select t.tid,t.fid,t.authorid,t.author,t.subject,t.views,t.replies,t.dateline,m.uid,m.username,m.dateline as mdateline,f.name,p.message "+sql+" limit "+startid+","+pageSize);
		for (Map<String,String> tid:list) {
			tids.append(tid.get("tid"));
			tids.append(",");
			tid.put("message", Common.htmlspecialchars(tid.get("message")));
		}
		request.setAttribute(PageForm.CURRENTPAGE, currentpage); 
		request.setAttribute(PageForm.TOTALPAGE,totalPage);
		request.setAttribute(PageForm.TOTALSIZE, totalSize);
		request.setAttribute("hiddentids", tids);
		request.setAttribute("showlist", list);
		request.getSession().setAttribute("recyclebinform", rf);
		request.setAttribute("notfirst", "notfirst");
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Members member = (Members)session.getAttribute("user");
		request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",rf.getInforum()+""));
		return mapping.findForward("toRecyclebin");
	}

	public ActionForward pageRecyclebin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		int currentpage = 1;
		String page = request.getParameter("page");
		currentpage = page == null || page.equals("") ? 1 : Integer.valueOf(page);
		StringBuffer tids = new StringBuffer();
		RecyclebinForm rf = (RecyclebinForm)request.getSession().getAttribute("recyclebinform");
		if(rf==null){
			Common.requestforward(response, "admincp.jsp?action=recyclebin");
			return null;
		}
		HttpSession session = request.getSession();
		String timeoffset=(String)session.getAttribute("timeoffset");
		String sql = recyclebinService.findByAll(rf,timeoffset);
		List<Map<String,String>> counts = dataBaseService.executeQuery("select t.tid "+sql);
		int totalSize = counts.size();
		int totalPage = 1;
		int pageSize = 10; 
		int startid = 0;
		if (totalSize > pageSize) {
			if (totalSize % pageSize == 0) {
				totalPage = (int) ((double) totalSize / (double) pageSize);
			} else {
				totalPage = (int) (1.0d + (double) totalSize / (double) pageSize);
			}
		} else {
			totalPage = 1;
		}
		if (currentpage < 0) {
		} else {
			if (currentpage > totalPage) {
				currentpage = totalPage;
			}
			startid = pageSize * (currentpage - 1);
		}
		List<Map<String,String>> list = dataBaseService.executeQuery("select t.tid,t.fid,t.authorid,t.author,t.subject,t.views,t.replies,t.dateline,m.uid,m.username,m.dateline as mdateline,f.name,p.message "+sql+" limit "+startid+","+pageSize);
		for (Map<String,String> tid:list) {
			tids.append(tid.get("tid"));
			tids.append(",");
			tid.put("message", Common.htmlspecialchars(tid.get("message")));
		}
		short groupid = (Short)session.getAttribute("jsprun_groupid");
		Members member = (Members)session.getAttribute("user");
		request.setAttribute("forumselect", Common.forumselect(false, false,groupid,member!=null?member.getExtgroupids():"",rf.getInforum()+""));
		request.setAttribute(PageForm.CURRENTPAGE, currentpage);
		request.setAttribute(PageForm.TOTALPAGE,totalPage);
		request.setAttribute(PageForm.TOTALSIZE, totalSize);
		request.setAttribute("hiddentids", tids);
		request.setAttribute("showlist", list);
		request.setAttribute("notfirst", "notfirst");
		return mapping.findForward("toRecyclebin");
	}

	@SuppressWarnings("unchecked")
	public ActionForward deleteDayOld(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String rbsubmit = request.getParameter("rbsubmit");
		if(rbsubmit==null){
			Common.requestforward(response, "admincp.jsp?action=recyclebin");
			return null;
		}
		Integer deleteNumber = -1;
		String dayold = request.getParameter("days");
		Integer days = 0;
		try {
			days = Integer.valueOf(dayold);
			if (days < 0) {
				days = 0;
			}
		} catch (NumberFormatException nfe) {
			days = 0;
		}
		Map<String,String> settings = ForumInit.settings;
		String path = JspRunConfig.realPath+settings.get("attachdir")+"/";
		deleteNumber = recyclebinService.deleteDayOld(days,path);
		String success = getMessage(request, "a_post_recyclebin_succeed",deleteNumber+"","0");
		request.setAttribute("message", success);
		return mapping.findForward("message");
	}

	@SuppressWarnings("unchecked")
	public ActionForward batchRecyclebin(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String rbsubmit = request.getParameter("rbsubmit");
		if(rbsubmit==null){
			Common.requestforward(response, "admincp.jsp?action=recyclebin");
			return null;
		}
		HttpSession session = request.getSession();
		Members members = (Members)session.getAttribute("members");
		Integer deleteNumber = 0;
		Integer undeleteNumber = 0;
		List<Integer> deletelist = new ArrayList<Integer>();
		String hiddenTids = request.getParameter("hiddentids");
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		int uid = (Integer)session.getAttribute("jsprun_uid");
		Map<String,String> settings = ForumInit.settings;
		String[] tids = hiddenTids.split(",");
		List<String> fids = new ArrayList<String>();
		StringBuffer undeletebuffer = new StringBuffer("0");
		if (tids != null && tids.length > 0) {
			for (int i = 0; i < tids.length; i++) {
				String value = request.getParameter(getMod(tids[i]));
				if (value != null && !value.equals("")) {
					if (value.equals("delete")) {
						deletelist.add(Integer.valueOf(tids[i]));
						List<Map<String,String>> attachlist = dataBaseService.executeQuery("select attachment,thumb,remote from jrun_attachments where tid="+tids[i]);
						if(attachlist!=null && attachlist.size()>0){
							String path = JspRunConfig.realPath+settings.get("attachdir")+"/";
							for(Map<String,String> attach: attachlist){
								Common.dunlink(attach.get("attachment"),Byte.valueOf(attach.get("thumb")),Byte.valueOf(attach.get("remote")), path);
							}
						}
					}
					if (value.equals("undelete")) {
						undeleteNumber++;
						undeletebuffer.append(","+tids[i]);
						List<Map<String,String>> threadlist = dataBaseService.executeQuery("select replies,fid from jrun_threads where tid="+tids[i]);
						fids.add(threadlist.get(0).get("fid"));
						int replies = Common.toDigit(threadlist.get(0).get("replies"))+1;
						threadlist = null;
						dataBaseService.runQuery("INSERT INTO jrun_threadsmod (tid, uid, username, dateline, action, expiration, status,magicid) VALUES('"+tids[i]+"','"+uid+"','"+members.getUsername()+"','"+timestamp+"','UDL','0','1','0')");
						Common.updatemodworks(settings, uid, timestamp, "UDL", replies);
					}
					if (value.equals("ignore")) {

					}
				}
			}
			dataBaseService.runQuery("UPDATE jrun_posts SET invisible='0' WHERE tid IN ("+undeletebuffer.toString()+")");
			dataBaseService.runQuery("UPDATE jrun_threads SET displayorder='0', moderated='1' WHERE tid IN ("+undeletebuffer.toString()+")");
			List<Map<String,String>> threadlist = dataBaseService.executeQuery("select fid,first,authorid from jrun_posts where tid in ("+undeletebuffer.toString()+")");
			Map creditspolicys=dataParse.characterParse(settings.get("creditspolicy"),false);
			Map<Integer, Integer> postcredits=(Map<Integer,Integer>)creditspolicys.get("reply");
			Map<Integer, Integer> threadcredits=(Map<Integer,Integer>)creditspolicys.get("post");
			String creditsformula = settings.get("creditsformula");
			for(Map<String,String> thread:threadlist){
				if(thread.get("first").equals("1")){
					Common.updatepostcredits("+", Common.toDigit(thread.get("authorid")), threadcredits, timestamp,1,creditsformula);
				}else{
					Common.updatepostcredits("+", Common.toDigit(thread.get("authorid")), postcredits, timestamp,1,creditsformula);
				}
			}
			settings = null;threadlist=null;postcredits=null;threadcredits=null;
			deleteNumber = recyclebinService.deleteArray(deletelist);
			MessageResources resources = getResources(request);
			Locale locale = getLocale(request);
			for(String fid:fids){
				Common.updateforumcount(fid,resources,locale);
			}
			deletelist=null;
		}
		String success = getMessage(request, "a_post_recyclebin_succeed", deleteNumber+"",undeleteNumber+"");
		request.setAttribute("message", success);
		return mapping.findForward("message");
	}

	private String getMod(String tids) {
		StringBuffer sb = new StringBuffer("mod[");
		sb.append(tids);
		sb.append("]");
		return sb.toString();
	}
}
