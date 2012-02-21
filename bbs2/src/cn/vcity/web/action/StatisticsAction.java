package cn.vcity.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Members;
import cn.jsprun.service.DataBaseService;
import cn.jsprun.utils.BeanFactory;
import cn.vcity.model.VcityQuestion;
import cn.vcity.model.VcitySurey;
import cn.vcity.service.StatisticsService;
import cn.vcity.service.VcitySureyService;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.helper.PagerHelper;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.wbase.common.helper.ipparse.IPSeeker;
import cn.vcity.wbase.common.vo.page.Pager;
import cn.vcity.web.form.VcitySureyForm;

public class StatisticsAction extends BaseAction {
	
	private VcitySureyService vcitySureyService = (VcitySureyService) SpringHelper
			.getBean("vcitySureyService");
	private StatisticsService statisticsService = (StatisticsService) SpringHelper
		.getBean("statisticsService");
	protected DataBaseService dataBaseService = (DataBaseService) BeanFactory.getBean("dataBaseService");
	
	/**
	 * 显示调查问卷分页数据列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showStatisticsSurveyList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		VcitySureyForm vcitySureyForm = (VcitySureyForm)form;
		List datas = null;
		int total = 0;// 总记录数
		Pager pager = null;// 分页信息
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		if (uinfo.getGroupid()==17) {
			
		}
		total = vcitySureyService.getTotalByOther(vcitySureyForm);
		
		try {
			pager = PagerHelper.getPager(request, total);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		datas = vcitySureyService.getVcitySureyListByOther(
				vcitySureyForm, pager.getPageSize(), pager.getStartRow());
		
		request.setAttribute("datas", datas);
		request.setAttribute("pager", pager);

		return mapping.findForward("showSurveyList");
	}
	
	/**
	 * 显示该调查结果
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showSurveyResultTotal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		VcitySurey vcitySurvey = vcitySureyService.findVcitySureyById(Integer.parseInt(request.getParameter("id")));
		//该ID要修改为子任务ID
		Iterator iter = vcitySurvey.getVcityQuestion().iterator();
		List<Integer> questionIdList = new ArrayList<Integer>();
		while (iter.hasNext()) {
			VcityQuestion question = (VcityQuestion)iter.next();
			questionIdList.add(question.getQuestionId());
		}
		String questionIds =  "";
		for (int i = 0; i < questionIdList.size(); i++) {
			questionIds += questionIdList.get(i)+",";
		}
		if (questionIds.length()>0) {
			questionIds = questionIds.substring(0, questionIds.length()-1);
		}
		System.out.println(questionIds);
		List countPeopleList = statisticsService.getCountPeopleList(questionIds,Integer.parseInt(request.getParameter("id")));

		List<String> dateList = new ArrayList<String>();
		List<Integer> peopleList = new ArrayList<Integer>();
		if (countPeopleList.size()>0) {
			for (int i = 0; i < countPeopleList.size(); i++) {
				Object[] o = (Object[]) countPeopleList.get(i);
				dateList.add("'"+o[1].toString()+"'");
				peopleList.add(Integer.parseInt(o[0].toString()));
		
			}

		}
		request.setAttribute("vcitySurvey", vcitySurvey);
		request.setAttribute("dateList", dateList);
		request.setAttribute("peopleList", peopleList);

		return mapping.findForward("surveyResult");
	}
	
	/**
	 * 社区访问统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showAccessTotal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		List accessTotalList = statisticsService.getAccessTotalList(beginTime,endTime);
		List<String> dateList = new ArrayList<String>();
		List<Integer> IPList = new ArrayList<Integer>();
		
		if (accessTotalList.size()>0) {
			for (int i = 0; i < accessTotalList.size(); i++) {
				Object[] o = (Object[]) accessTotalList.get(i);
				dateList.add("'"+o[0].toString().substring(5)+"'");
				IPList.add(Integer.parseInt(o[1].toString()));
		
			}
			Collections.reverse(accessTotalList);
		}
		request.setAttribute("dateList", dateList);
		request.setAttribute("IPList", IPList);
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("accessTotalList", accessTotalList);
		return mapping.findForward("accessTotalResult");
	}
	
	/**
	 * 社区会员登录信息统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showMemberLoginTotal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		List datas = new ArrayList();
		List<String> dateList = new ArrayList<String>();
		List<Integer> userList = new ArrayList<Integer>();
		List LoginTimeList = statisticsService.getLoginTimeList(beginTime,endTime);
		
		if (LoginTimeList.size()>0) {
			for (int i = 0; i < LoginTimeList.size(); i++) {
				String loginTime = (String) LoginTimeList.get(i);
				List loginMemberList =  statisticsService.getLoginMemberList(loginTime);
				if (loginMemberList.size()>0) {
					Object[] o = new Object[2];
					o[0] = loginTime;
					o[1] = loginMemberList;
					dateList.add("'"+loginTime.substring(5)+"'");
					userList.add(loginMemberList.size());
					datas.add(o);
				}
			}
			Collections.reverse(dateList);
			Collections.reverse(userList);
		}	
		
		request.setAttribute("datas",datas);
		request.setAttribute("dateList",dateList);
		request.setAttribute("userList",userList);
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		return mapping.findForward("memberLoginTotal");
	}
	
	/**
	 * 统计IP归属地
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showIPTotal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String path =request.getSession().getServletContext().getRealPath("");
		IPSeeker ipseeker = new IPSeeker("QQWry.Dat", path+"/vcity/");
		Map<String, Integer> provincesMap = new HashMap<String, Integer>();
		List<String> provincesList = new ArrayList<String>();
		List<Integer> IPTotalList = new ArrayList<Integer>();
		
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		List IPList =  statisticsService.getIPList(beginTime,endTime);

		if (IPList.size()>0) {
			for (int i = 0; i < IPList.size(); i++) {
				String accessIP = (String) IPList.get(i);
				String province = ipseeker.getCountry(accessIP);
				if (provincesMap.get(province)!=null) {
					Integer count = provincesMap.get(province);
					provincesMap.put(province, count+1);
				} else {
					provincesMap.put(province, 1);
				}				
			}
		}
		Iterator iter = provincesMap.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			
			Integer count = provincesMap.get(key);
			provincesList.add("'"+key+"'");
			IPTotalList.add(count);
		
		}
	/*	Properties prop = new Properties();
		InputStream inputStream = new FileInputStream(path+"/WEB-INF/province.properties");
		prop.load(inputStream);
		Enumeration enum1 = prop.propertyNames();  
        while (enum1.hasMoreElements()) {  
	       String strKey = (String) enum1.nextElement();  
	       provincesMap.put(strKey, 0);
	       String strValue = prop.getProperty(strKey);  
	       System.out.println(strKey + "=" + strValue);  
        }*/ 

		request.setAttribute("provincesList", provincesList);
		request.setAttribute("IPTotalList", IPTotalList);
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		return mapping.findForward("provinceIPTotalResult");
	}
	
	/**
	 * 下载统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadTotal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String resType = request.getParameter("resType");
		
		List datas = statisticsService.getDownloadTotal(beginTime,endTime,resType);
		List<String> nameList = new ArrayList<String>();
		List<Object> totalList = new ArrayList<Object>();
		if (datas.size()>0) {
			for (int i = 0; i < datas.size(); i++) {
				Object[] o = (Object[]) datas.get(i);
				nameList.add("'"+o[0].toString()+"'");
				totalList.add(o[1]);
			}
		}
		request.setAttribute("nameList", nameList);
		request.setAttribute("totalList", totalList);
		request.setAttribute("resType", resType);
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		 return mapping.findForward("downloadTotalResult");
	}
	
	/**
	 * 好评度统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward praiseDegreeTotal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String resType = request.getParameter("resType");
		
		/*抛出异常：No Dialect mapping for JDBC type:3
		 * 未找到解决方法，使用1期的查询方法
		*/
		StringBuffer sql = new StringBuffer("SELECT a.resName as 'resName',SUM(a.a1) as 'up',SUM(a.a2) as 'down' FROM (");
		sql.append("SELECT vr.resName,COUNT(com.resid) AS 'a1',0 AS 'a2' " +
			"FROM vcity_rescommon com,vcity_vresources vr WHERE com.resid = vr.resId " +
			"AND com.class=1 ");
		if (beginTime!=null&&!beginTime.equals("")) {
			sql.append(" and com.createTime>='"+beginTime+"'");
		}
		if (endTime!=null&&!endTime.equals("")) {
			sql.append(" and com.createTime<='"+endTime+"'");
		}
		sql.append(" AND vr.resType="+resType+" GROUP BY com.resid ");
		sql.append(" UNION ");
			
		sql.append("SELECT vr.resName,0 AS 'a1',COUNT(com.resid) AS 'a2' " +
				"FROM vcity_rescommon com,vcity_vresources vr WHERE com.resid = vr.resId " +
				"AND com.class=2 ");
			if (beginTime!=null&&!beginTime.equals("")) {
				sql.append(" and com.createTime>='"+beginTime+"'");
			}
			if (endTime!=null&&!endTime.equals("")) {
				sql.append(" and com.createTime<='"+endTime+"'");
			}
			sql.append(" AND vr.resType="+resType+" GROUP BY com.resid ");
			sql.append(") as a "); 
			sql.append(" GROUP BY a.resName");
		
			
		List<String> nameList = new ArrayList<String>();
		List<Integer> upList = new ArrayList<Integer>();
		List<Integer> downList = new ArrayList<Integer>();
		List<Map<String,String>> datas = dataBaseService.executeQuery(sql.toString());
		for(Map<String,String> data:datas){
			nameList.add("'"+data.get("resName")+"'");
			upList.add(Integer.parseInt(data.get("up")));
			downList.add(Integer.parseInt(data.get("down")));
		}
		//List datas = statisticsService.getPraiseDegreeTotal(beginTime,endTime,resType);  //报错
		
		request.setAttribute("resType", resType);
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("nameList", nameList);
		request.setAttribute("upList", upList);
		request.setAttribute("downList", downList);
		return mapping.findForward("praiseDegreeTotalResult");
	}
	
}
