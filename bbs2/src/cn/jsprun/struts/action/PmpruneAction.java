package cn.jsprun.struts.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.utils.Common;
import cn.jsprun.utils.FormDataCheck;

public class PmpruneAction extends BaseAction {
 
	public ActionForward batchPmprune(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String prunesubmit = request.getParameter("prunesubmit");
		if(prunesubmit==null){
			Common.requestforward(response, "admincp.jsp?action=pmprune");
			return null;
		}
		String ignorenew = request.getParameter("ignorenew");   
		String days = request.getParameter("days");         
		String cins = request.getParameter("cins");       
		String users = request.getParameter("users");   
		String srchtype = request.getParameter("srchtype");  
		String srchtxt = request.getParameter("srchtxt");  
		HttpSession session = request.getSession();
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		if (!FormDataCheck.isValueString(days)) {
			String info = getMessage(request, "a_system_prune_pm_range_invalid");
			request.setAttribute("message", info);
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		StringBuffer sqlbuffer = new StringBuffer(" from jrun_pms");
		String where = " where ";
		String and = " ";
		if(ignorenew!=null){
			sqlbuffer.append(where);
			where = " ";
			sqlbuffer.append(and);
			and = " and ";
			sqlbuffer.append("new=0");
		}
		if(FormDataCheck.isNum(days)&&!days.equals("0")){
			sqlbuffer.append(where);
			where = " ";
			sqlbuffer.append(and);
			and = " and ";
			int day = Common.toDigit(days);
			sqlbuffer.append("dateline <="+(timestamp-day*86400));
		}
		if(users!=null && !users.equals("")){
			String uid = "0";
			String biner = cins==null?"":" BINARY ";
			List<Map<String,String>> members = dataBaseService.executeQuery("select uid from jrun_members where "+biner+" username in ('"+Common.addslashes(users)+"')");
			for(Map<String,String>member:members){
				uid += ","+member.get("uid");
			}
			sqlbuffer.append(where);
			where = " ";
			sqlbuffer.append(and);
			and = " and ";
			sqlbuffer.append("(msgfromid IN ("+uid+") AND folder='outbox') OR (msgtoid IN ("+uid+") AND folder='inbox')");
		}
		if(srchtxt!=null && !srchtxt.equals("")){
			srchtxt = srchtxt.replaceAll("\\*", "%");
			srchtxt = srchtxt.toLowerCase();
			sqlbuffer.append(where);
			where = " ";
			sqlbuffer.append(and);
			and = " and ";
			if (Common.matches(srchtxt,"[and|\\+|&|\\s+]") && !Common.matches(srchtxt,"[or|\\|]")) {
				srchtxt = srchtxt.replaceAll("( and |&| )", "+");
				String[] keyword = srchtxt.split("\\+");
				if (srchtype.equals("title")) {
					for (int i = 0; i < keyword.length; i++) {
						sqlbuffer.append("subject like '%" + Common.addslashes(keyword[i].trim())+ "%'");
						sqlbuffer.append(and);
					}
				} else {
					for (int i = 0; i < keyword.length; i++) {
						sqlbuffer.append("message like '%" + Common.addslashes(keyword[i].trim())+ "%'");
						sqlbuffer.append(and);
					}
				}
				int length = sqlbuffer.length();
				sqlbuffer.delete(length-4,length);
			} else {
				srchtxt = srchtxt.replaceAll("( or |\\|)", "+");
				String[] keyword = srchtxt.split("\\+");
				if (srchtype.equals("title")) {
					sqlbuffer.append("(");
					for (int i = 0; i < keyword.length; i++) {
						sqlbuffer.append("subject like '%" + Common.addslashes(keyword[i].trim())+ "%'");
						sqlbuffer.append(" or ");
					}
				} else {
					sqlbuffer.append("(");
					for (int i = 0; i < keyword.length; i++) {
						sqlbuffer.append("message like '%" + Common.addslashes(keyword[i].trim())+ "%'");
						sqlbuffer.append(" or ");
					}
				}
				int length = sqlbuffer.length();
				sqlbuffer.delete(length-4,length);
				sqlbuffer.append(")");
			}
		}
		List<Map<String,String>> pmslist = dataBaseService.executeQuery("select COUNT(*) AS count "+sqlbuffer.toString()); 
		String size = "0";
		if(pmslist!=null && pmslist.size()>0){
			size = pmslist.get(0).get("count");
		}
		pmslist = null;
		String confirmInfo = getMessage(request, "a_system_prune_pm_confirm", size);
		String commitPath = request.getContextPath() + "/pmprune.do?pmpruneaction=deletePmprun";
		request.setAttribute("url_forward", commitPath);
		request.setAttribute("msgtype", "form"); 
		request.setAttribute("message", confirmInfo);
		session.setAttribute("pmidbuffer",sqlbuffer.toString());
		session.setAttribute("countIds", size);
		return mapping.findForward("message");
	}

	public ActionForward deletePmprun(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String sql = (String) session.getAttribute("pmidbuffer");
		if(sql==null){
			Common.requestforward(response, "admincp.jsp?action=pmprune");
			return null;
		}
		session.removeAttribute("pmidbuffer");
		dataBaseService.runQuery("delete "+sql);
		String countIds = (String)session.getAttribute("countIds");
		String info = getMessage(request, "a_system_prune_pm_succeed",countIds);
		request.setAttribute("message", info);
		return mapping.findForward("message");
	}
}
