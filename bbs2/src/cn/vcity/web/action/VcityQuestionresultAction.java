package cn.vcity.web.action;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Members;
import cn.jsprun.struts.foreg.actions.TaskAction;
import cn.vcity.model.VcityQuestion;
import cn.vcity.model.VcityQuestionitem;
import cn.vcity.model.VcityQuestionresult;
import cn.vcity.model.VcitySubTask;
import cn.vcity.model.VcitySurey;
import cn.vcity.service.VcityQuestionService;
import cn.vcity.service.VcityQuestionresultService;
import cn.vcity.service.VcitySubTaskService;
import cn.vcity.service.VcitySureyService;
import cn.vcity.service.VcityTaskService;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.web.form.VcityQuestionresultForm;

/**
 * @Title: VcityQuestionresultAction.java
 * @Package cn.vcity.vcity.web.action
 * @Description: 调查问卷的Action类
 * @author LHJ hsw 珠海市网佳科技有限公司.
 * @date 2011-7-4 17:08:25
 * @version V1.0
 */
public class VcityQuestionresultAction extends BaseAction {
	
	private VcityQuestionresultService vcityQuestionresultService = (VcityQuestionresultService) SpringHelper
			.getBean("vcityQuestionresultService");
	private VcitySureyService vcitySureyService = (VcitySureyService) SpringHelper
			.getBean("vcitySureyService");
	private VcityQuestionService vcityQuestionService = (VcityQuestionService) SpringHelper
	.getBean("vcityQuestionService");
	private VcitySubTaskService vcitySubTaskService = (VcitySubTaskService) SpringHelper
	.getBean("vcitySubTaskService");
	private VcityTaskService vcityTaskService = (VcityTaskService) SpringHelper
			.getBean("vcityTaskService");


	private static VcityQuestionresultForm vcityQuestionresultForm = null;

	/**
	 * 回答调查问卷
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public ActionForward saveVcityQuestionresult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, InvocationTargetException {
		vcityQuestionresultForm = (VcityQuestionresultForm) form;
		String msg = "答题完成！";
		String taskType = (String)request.getParameter("subtype");
		Map questionMap = vcityQuestionresultForm.getQuestionMap();
		Iterator iterator = questionMap.entrySet().iterator();
		boolean flag = false;
		Map.Entry entry;
		VcityQuestionresult entity;
		Integer[] selectitemIds = new Integer[]{};
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		String IP = request.getRemoteAddr();
		while (iterator.hasNext()) {
			entry = (Map.Entry)iterator.next();
			VcityQuestion vcityQuestion =((VcityQuestion)entry.getValue());
			if (vcityQuestion.getSelectitemId()!=null) {
				selectitemIds = vcityQuestion.getSelectitemId();     //只保存已回答的问题(页面已检查是否全部回答,此目的防止出错)
			}			
			if(selectitemIds.length > 0){
				for (int i = 0; i < selectitemIds.length; i++) {
					entity = new VcityQuestionresult();
					entity.setAnswerTime(new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()));
					entity.setQuestionId(vcityQuestion.getQuestionId());
					//entity.setResult(selectitemIds[i]);
					entity.setVcityQuestionitem(new VcityQuestionitem(selectitemIds[i]));
					//需要修改,从session中获取userId

					if (uinfo==null) {
						entity.setAnswerUser(IP);
					}else {
						entity.setAnswerUser(uinfo.getUsername());
					}
					
					//人工任务，自动任务..自动为1，人工为2
					if (taskType!=null) {
						if (taskType.equals("1")) {
							entity.setAnswerType(1);
						}else {
							entity.setAnswerType(2);
						}
					} else {
						entity.setAnswerType(1);
					}
					
					//子任务id
					if (request.getParameter("task")==null) {
						if (request.getParameter("surveyId")!=null) {
							entity.setSubTaskId(Integer.parseInt(request.getParameter("surveyId")));
						}

					}else {
						entity.setSubTaskId(Integer.parseInt(request.getParameter("task")));
					}
					
					 flag =vcityQuestionresultService.saveVcityQuestionresult(entity);
				
				}
				selectitemIds = new Integer[]{};
			}
			
			if (flag) {    //更新任务状态
				if (uinfo!=null) {	
					TaskAction taskAction = new TaskAction();
					String subid = request.getParameter("task");
					if (subid!=null&&!subid.equals("")) {
						taskAction.doUserExcuteManualTask(Integer.parseInt(subid),taskType);
					}					
				}
			}
		}
		
		if (request.getParameter("todo")==null) {
			selectQuestionItem(request,1);
			return mapping.findForward("success");
		}else {
//			request.setAttribute(Constants.ALERT_MESSAGE,msg );
			selectQuestionItem(request,2);
//			this.returnJsMsg(response, msg, "window.close();");
			request.setAttribute("isAnswerSuccess", true);
			return mapping.findForward("manualTasksuccess");
		}
		
	}

	//显示已经作答的问题
	private void selectQuestionItem(HttpServletRequest request, int flag) {
		Members uinfo  = (Members) request.getSession().getAttribute("userInfo");
		String userName = "";
		if (uinfo!=null) {
			userName = uinfo.getUsername();
		}
		if (flag==1) {
			List<String> questionIds = new ArrayList<String>();
			VcitySurey vcitySurey = vcitySureyService.findVcitySureyById(Integer.parseInt(request.getParameter("sureyId")));
			Iterator tmpIterator =  vcitySurey.getVcityQuestion().iterator();
			while (tmpIterator.hasNext()) {
				VcityQuestion tmp = (VcityQuestion) tmpIterator.next();
				questionIds.add(String.valueOf(tmp.getQuestionId()));
			}
			Map map = vcityQuestionresultService.findVcityQuestionresultBySureyId(StringUtils.join(questionIds.toArray(), ","),userName,null);
			Iterator questionIterator =  vcitySurey.getVcityQuestion().iterator();
			while (questionIterator.hasNext()) {
				VcityQuestion question = (VcityQuestion)questionIterator.next();
				Iterator itemIterator =question.getVcityQuestionitem().iterator();
				while (itemIterator.hasNext()) {
					VcityQuestionitem item = (VcityQuestionitem)itemIterator.next();
					item.setSelectFlag(map.get(question.getQuestionId().toString() + "," + item.getItemId().toString()));
				}
			}
			request.setAttribute("vcitySurey", vcitySurey);
		
		}else {
			List<String> questionIds = new ArrayList<String>();
//			List questionList = (List)request.getAttribute("questionList");
			List questionList =vcityQuestionService.findVcityQuestionByIds(request.getParameter("sureyId"));
			Iterator tmpIterator =  questionList.iterator();
			while (tmpIterator.hasNext()) {
				VcityQuestion tmp = (VcityQuestion) tmpIterator.next();
				questionIds.add(String.valueOf(tmp.getQuestionId()));
			}
//			Map map = vcityQuestionresultService.findVcityQuestionresultBySureyId(StringUtils.join(questionIds.toArray(), ","));
			Map map = vcityQuestionresultService.findVcityQuestionresultBySureyId(request.getParameter("sureyId"),userName,null);
			Iterator questionIterator =  questionList.iterator();
			while (questionIterator.hasNext()) {
				VcityQuestion question = (VcityQuestion)questionIterator.next();
				Iterator itemIterator =question.getVcityQuestionitem().iterator();
				while (itemIterator.hasNext()) {
					VcityQuestionitem item = (VcityQuestionitem)itemIterator.next();
					item.setSelectFlag(map.get(question.getQuestionId().toString() + "," + item.getItemId().toString()));
					
				}
			}
			request.setAttribute("selectquestionList", questionList);
		}
		
	}
	
	/**
	 * 跳转到调查问卷
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public ActionForward editVcityQuestionresult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		vcityQuestionresultForm = (VcityQuestionresultForm) form;
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		String todo = request.getParameter("todo");
		String forward = "";

		if (TODO_CREATE.equals(todo)) {
			VcitySurey vcitySurey = vcitySureyService.findVcitySureyById(Integer.parseInt(request.getParameter("id")));
			Iterator sureyIterator =vcitySurey.getVcityQuestion().iterator();
			int i=1;
			while (sureyIterator.hasNext()) {
				VcityQuestion question = (VcityQuestion)sureyIterator.next();
				question.setIndex(i++);
//				System.out.println("index:"+question.getIndex());
			}
			
			
			request.setAttribute("vcitySurey", vcitySurey);
			request.setAttribute("subtype", "1");   //none
			forward = "create";
		} else if (TODO_UPDATE.equals(todo)) {
			forward = "update";
		}else if ("manualTask".equals(todo)) {
			List questionList = new ArrayList();
			/*执行自动任务的调查 */
			if (request.getParameter("subtype")!=null) {
				String ids = "";
				if(request.getParameter("subtype").equals("1")){
					
					VcitySurey vcitySurey = vcitySureyService.findVcitySureyById(Integer.parseInt(request.getParameter("surveyid")));
					Iterator sureyIterator =vcitySurey.getVcityQuestion().iterator();
					while (sureyIterator.hasNext()) {
						VcityQuestion question = (VcityQuestion)sureyIterator.next();
						ids += question.getQuestionId()+",";
					}	
					if (!ids.equals("")&&ids.length()>0) {
						ids = ids.substring(0, ids.length()-1);
					}	
				} else{
					ids = request.getParameter("surveyid");
				}
				request.setAttribute("ids", ids);
				request.setAttribute("subtype", request.getParameter("subtype"));
				questionList =vcityQuestionService.findVcityQuestionByIds(ids);
				/* 先判断是否用户已经回答该问卷，若已作答，直接显示结果*/
				VcitySubTask subTask = vcitySubTaskService.findSubTaskByid(Integer.parseInt(request.getParameter("task")));

				if (subTask.getSubTaskState()!=0&&uinfo.getUsername().equals(subTask.getUserName())) {
					questionList = vcityQuestionService.findVcityQuestionByIds(ids);
					Map map = vcityQuestionresultService.findVcityQuestionresultBySureyId(ids,
							uinfo.getUsername(),null);
					Iterator questionIterator = questionList.iterator();					
					while (questionIterator.hasNext()) {
						VcityQuestion question = (VcityQuestion) questionIterator.next();
						Iterator itemIterator = question.getVcityQuestionitem().iterator();
						while (itemIterator.hasNext()) {
							VcityQuestionitem item = (VcityQuestionitem) itemIterator
									.next();
							item.setSelectFlag(map.get(question.getQuestionId().toString()
									+ "," + item.getItemId().toString()));

						}
					}
					request.setAttribute("selectquestionList", questionList);
					return mapping.findForward("manualTasksuccess");
				}
				request.setAttribute("subtask", request.getParameter("task"));
				//用于更新状态
				request.getSession().setAttribute("taskid", request.getParameter("mainTaskId"));
			} else {
				/* 先判断是否用户已经回答该问卷，若已作答，直接显示结果*/
				VcitySubTask subTask = vcitySubTaskService.findSubTaskByid(Integer.parseInt(request.getParameter("task")));

				if (subTask.getSubTaskState()!=0&&uinfo.getUsername().equals(subTask.getUserName())) {
					questionList = vcityQuestionService.findVcityQuestionByIds(request.getParameter("ids"));
					Map map = vcityQuestionresultService.findVcityQuestionresultBySureyId(request.getParameter("ids"),
							uinfo.getUsername(),null);
					Iterator questionIterator = questionList.iterator();					
					while (questionIterator.hasNext()) {
						VcityQuestion question = (VcityQuestion) questionIterator.next();
						Iterator itemIterator = question.getVcityQuestionitem().iterator();
						while (itemIterator.hasNext()) {
							VcityQuestionitem item = (VcityQuestionitem) itemIterator
									.next();
							item.setSelectFlag(map.get(question.getQuestionId().toString()
									+ "," + item.getItemId().toString()));

						}
					}
					request.setAttribute("selectquestionList", questionList);
					return mapping.findForward("manualTasksuccess");
				}
				questionList =vcityQuestionService.findVcityQuestionByIds(request.getParameter("ids"));	
				request.setAttribute("ids", request.getParameter("ids"));
				request.setAttribute("subtype", "2");
				request.setAttribute("subtask", request.getParameter("task"));
			}
			request.getSession().setAttribute("questionList", questionList);
			forward = "manualTask";
		} 
		else {
			log.error("createOrUpdatePost－TODO参数传递错误或为空！");
		}

		return mapping.findForward(forward);
	}
	
	/**
	 * 聊天界面中，显示发送给该用户的所有问卷列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward showUserSurveyResultList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String taskId = request.getParameter("taskId");		
		String userName = request.getParameter("userName");
		userName = java.net.URLDecoder.decode(userName, "UTF-8");
		List userSurveyList = vcityTaskService.getSubTaskListByUserName(userName, Integer.parseInt(taskId));

		request.setAttribute("userSurveyList", userSurveyList);
		return mapping.findForward("userSurveyList");
	}

	/**
	 *显示用户某一问卷的作答结果
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	public ActionForward showUserSurveyResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		String ids = request.getParameter("ids");
		String subtaskid = request.getParameter("subtaskid");
		String userName = request.getParameter("userName");
		userName = java.net.URLDecoder.decode(userName, "UTF-8");
		List questionList = new ArrayList();

		questionList = vcityQuestionService.findVcityQuestionByIds(ids);
		Map map = vcityQuestionresultService.findVcityQuestionresultBySureyId(ids, userName,subtaskid);
		Iterator questionIterator = questionList.iterator();
		
		while (questionIterator.hasNext()) {
			VcityQuestion question = (VcityQuestion) questionIterator.next();
			Iterator itemIterator = question.getVcityQuestionitem().iterator();
			while (itemIterator.hasNext()) {
				VcityQuestionitem item = (VcityQuestionitem) itemIterator
						.next();
				item.setSelectFlag(map.get(question.getQuestionId().toString()
						+ "," + item.getItemId().toString()));

			}
		}
		request.setAttribute("isShowUserResult", true);
		request.setAttribute("isAnswer", map.isEmpty());
		request.setAttribute("selectquestionList", questionList);
		return mapping.findForward("manualTasksuccess");
	}
}
