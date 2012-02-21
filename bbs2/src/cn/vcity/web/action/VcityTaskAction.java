package cn.vcity.web.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Members;
import cn.jsprun.domain.Scenetask;
import cn.jsprun.domain.Scenetm;
import cn.jsprun.struts.foreg.actions.MyManageAction;
import cn.vcity.model.VcityManualTaskInit;
import cn.vcity.model.VcityResource;
import cn.vcity.model.VcitySubTask;
import cn.vcity.model.VcitySurey;
import cn.vcity.service.ChatService;
import cn.vcity.service.VcityInitTaskService;
import cn.vcity.service.VcityResourceService;
import cn.vcity.service.VcityScenetmService;
import cn.vcity.service.VcitySubTaskService;
import cn.vcity.service.VcitySureyService;
import cn.vcity.service.VcityTaskService;
import cn.vcity.wbase.common.Constants;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.helper.PagerHelper;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.wbase.common.vo.page.Pager;
import cn.vcity.web.form.VcityTaskForm;

public class VcityTaskAction extends BaseAction {

	private VcityTaskService vcityTaskService = (VcityTaskService) SpringHelper
			.getBean("vcityTaskService");

	private VcitySubTaskService vcitySubTaskService = (VcitySubTaskService) SpringHelper
			.getBean("vcitySubTaskService");

	private VcityResourceService vcityResourceService = (VcityResourceService) SpringHelper
			.getBean("vcityResourceService");

	private VcitySureyService vcitySureyService = (VcitySureyService) SpringHelper
			.getBean("vcitySureyService");

	private VcityInitTaskService vcityInitTaskService = (VcityInitTaskService) SpringHelper
			.getBean("vcityInitTaskService");

	private VcityScenetmService vcityScenetmService = (VcityScenetmService) SpringHelper
			.getBean("vcityScenetmService");
	private ChatService chatService = (ChatService) SpringHelper.getBean("chatService");
	

	private static VcityTaskForm vcityTaskForm = null;

	/**
	 * 任务管理列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showTaskList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		vcityTaskForm = (VcityTaskForm) form;
		List datas = null;
		int total = 0;
		Pager pager = null;
		String username = null;
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		
		if (uinfo.getGroupid() == 17) {	 //研究员	
			username = uinfo.getUsername();
			
		}
		total = vcityTaskService.findTaskTotalByOther(username,vcityTaskForm);
		try {
			pager = PagerHelper.getPager(request, total);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		datas = vcityTaskService.findTaskListByOther(username,vcityTaskForm, pager.getPageSize(), pager.getStartRow());

		/*String forward = "showTaskList";
		if (request.getParameter("flag") != null) {
			if (request.getParameter("flag").equals("1")) {
				forward = "cusTaskList";
				 uinfo = (Members) request.getSession().getAttribute(
						"userInfo");
				if (uinfo != null) {
					if (uinfo.getGroupid() == 10) {
						total = vcityTaskService.findTaskTotalByOther(uinfo.getUsername(),vcityTaskForm);
						datas = vcityTaskService.findTaskListByOther(uinfo.getUsername(),
								vcityTaskForm, pager.getPageSize(), pager
										.getStartRow());
					}
				}
			}
		}*/

		// 取出每一条记录,将String日期与现在日期作比较得到任务状态
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = df.parse(df.format(new Date()));
		List<Scenetask> dataList = new ArrayList<Scenetask>();
		if (datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				Scenetask task = (Scenetask) datas.get(i);
				if (task.getTaskBegin() != null && task.getTaskEnd() != null
						&& !task.getTaskBegin().equals("")
						&& !task.getTaskEnd().equals("")) {

					Date begin = df.parse(task.getTaskBegin());
					Date end = df.parse(task.getTaskEnd());
					if (nowDate.compareTo(begin) >= 0
							&& nowDate.compareTo(end) <= 0) {
						task.setTaskStatus("进行中");
					}
					if (nowDate.compareTo(end) > 0) {
						task.setTaskStatus("已结束");
					}
					if (nowDate.compareTo(begin) < 0) {
						task.setTaskStatus("未开始");
					}
				}
				dataList.add(task);
			}
		}
		
		request.setAttribute("datas", dataList);
		request.setAttribute("pager", pager);
		request.setAttribute("vcityTaskForm", vcityTaskForm);

		return mapping.findForward("showTaskList");
	}

	public ActionForward editTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		vcityTaskForm = (VcityTaskForm) form;
		String todo = request.getParameter("todo");
		String forward = "";

		if (TODO_CREATE.equals(todo)) {
			forward = "create";
		} else if (TODO_UPDATE.equals(todo)) {
			
			String id = request.getParameter("taskid");
			Scenetask task = new Scenetask();
	
			if (id != null && !id.equals("")) {
				task = vcityTaskService.findTaskById(Integer.parseInt(id));
				List userList = chatService.getUserInfoByTaskId(Integer.parseInt(id));

				vcityTaskForm = (VcityTaskForm) this.modelToForm(task,
						"cn.vcity.web.form.VcityTaskForm");

				// 如果是自动任务,筛选重复自动子任务并且排序
				if (task.getTaskType() == 1) {

					List<VcitySubTask> subTaskList = new ArrayList<VcitySubTask>();
					Iterator<VcitySubTask> iterator = task.getVcitySubTask().iterator();

					String userName = "";
					Set<Integer> subIndexList = new HashSet<Integer>();
					while (iterator.hasNext()) {
						VcitySubTask vac = iterator.next();
						userName = vac.getUserName();
						subIndexList.add(vac.getSubTaskIndex());
					}
					// 将subIndexList组成一串字符串,用于得到页面"添加一项"后所有子任务的ID
					String indexString = "";
					Iterator<Integer> indexIterator = subIndexList.iterator();
					while (indexIterator.hasNext()) {
						Integer index = indexIterator.next();
						indexString += index.toString() + ",";
					}
					if (!indexString.equals("") && indexString.length() > 0) {
						indexString = indexString.substring(0, indexString
								.length() - 1);
					}

					subTaskList = vcityTaskService.getSubTaskListByUserName(
							userName, Integer.parseInt(id));

					vcityTaskForm.setSubTaskList(subTaskList);

					request.setAttribute("indexString", indexString);
				} else { // 如果是人工任务,得到其初始设置列表
					List<VcityManualTaskInit> initList = new ArrayList<VcityManualTaskInit>();
					Iterator<VcityManualTaskInit> iterator = task
							.getManualTaskInit().iterator();
					while (iterator.hasNext()) {
						VcityManualTaskInit init = iterator.next();
						/*if (init.getSubTaskType() == 1) {
							VcityResource resource = vcityResourceService
									.findVcityResourceById(init
											.getSubTaskResId());

							init.setResName(resource.getResName());
						}*/
						if (init.getSubTaskType() == 2) {
							if (init.getSubTaskResId()!=0) {
								VcitySurey survey = vcitySureyService.findVcitySureyById(init.getSubTaskResId());
								init.setResName(survey.getSureyName());
							} else {
								init.setResName("e声网调查");
							}
							
						}
						initList.add(init);
					}
					vcityTaskForm.setInitTaskList(initList);
				}
				forward = "update";
				request.setAttribute("userList", userList);
			}
		} else {
			log.error("createOrUpdatePost－TODO参数传递错误或为空！");
		}
		request.setAttribute("vcityTaskForm", vcityTaskForm);
		return mapping.findForward(forward);

	}

	public ActionForward saveTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		vcityTaskForm = (VcityTaskForm) form;
		String[] users = request.getParameterValues("checkuser");
		String value = request.getParameter("randValue");
		String[] subNo = value.split(",");
		Scenetask entity = new Scenetask();
		entity = (Scenetask) this.formToModel(vcityTaskForm,
				"cn.jsprun.domain.Scenetask");
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		entity.setCreateUser(uinfo.getUsername());   
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		entity.setTaskTime(df.format(new Date()));

		String members = "";

		if (vcityTaskForm.getTaskType() == 1) {
			for (int i = 0; i < users.length; i++) {
				for (int j = 0; j < subNo.length; j++) {
					VcitySubTask vac = new VcitySubTask();
					String no = request.getParameter("value(" + subNo[j]
							+ ").subTaskIndex");
					String desc = request.getParameter("value(" + subNo[j]
							+ ").subTaskDesc");
					String subTaskType = request.getParameter("value("
							+ subNo[j] + ").subTaskType");
					String resId = request.getParameter("value(" + subNo[j]
							+ ").subTaskResId");
					String subTaskURL = request.getParameter("value("
							+ subNo[j] + ").subTaskURL");

					if (no != null && desc != null && !no.equals("")
							&& !desc.equals("")) {
						vac.setSubTaskIndex(Integer.parseInt(no));
						vac.setSubTaskDesc(desc);
						vac.setSubTaskType(Integer.parseInt(subTaskType));
						vac.setSubTaskResId(resId);
						vac.setSubTaskState(0);
						vac.setSubTaskURL(subTaskURL);
						vac.setUserName(users[i]);
						entity.addVcityTaskChild(vac);
					}
				}

			}
		} else {
			/*
			// 人工任务－模型
			String[] resModels = request.getParameterValues("modelId");
			if (resModels != null && resModels.length > 0) {
				for (int i = 0; i < resModels.length; i++) {
					VcityManualTaskInit initTask = new VcityManualTaskInit();
					initTask.setSubTaskResId(Integer.parseInt(resModels[i]));
					initTask.setSubTaskType(1);
					entity.addManualTaskInit(initTask);
				}
			}
			*/
			// 人工任务－调查
			String[] resSurveys = request.getParameterValues("surveyId");

			if (resSurveys != null && resSurveys.length > 0) {
				for (int i = 0; i < resSurveys.length; i++) {
					VcityManualTaskInit initTask = new VcityManualTaskInit();
					initTask.setSubTaskResId(Integer.parseInt(resSurveys[i]));
					initTask.setSubTaskType(2);
					entity.addManualTaskInit(initTask);
				}
			}
		}

		// 向人员任务关联表插入数据
		for (int i = 0; i < users.length; i++) {
			Scenetm scenetm = new Scenetm();
			scenetm.setUserName(users[i]);
			scenetm.setTaskState(Short.parseShort("0"));
			scenetm.setGetCredit(Short.parseShort("0"));
			scenetm.setLastUpdateTime(0);
			entity.addScenetm(scenetm);

			members += users[i] + ",";
		}

		boolean bool = vcityTaskService.saveVcityTask(entity);

		/*
		 * if (bool) {
		 * 
		 * //从E声网取得筛选用户的邮件列表,并发送邮件通知有新任务 if (!members.equals("")) { members =
		 * members.substring(0, members.length()-1);
		 * 
		 * List<Members> meb = MyManageAction.getUserEmailFromE(members); if
		 * (meb != null && meb.size() > 0) { for (Members mebs : meb) {
		 * MyManageAction.sendEmail(request, mebs.getEmail(),
		 * mebs.getUsername(), vcityTaskForm.getTaskName()); } } } }
		 */

		String tempPath = request.getSession().getServletContext().getContextPath();
		response.sendRedirect(tempPath+"/vcityTaskAction.do?action=showTaskList");
		return null;
		//return showTaskList(mapping, form, request, response);

	}

	public ActionForward updateTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		vcityTaskForm = (VcityTaskForm) form;
		String[] users = request.getParameterValues("checkuser");
		String randValue = request.getParameter("randValue");
		String[] subNo = randValue.split(",");

		Scenetask entity = new Scenetask();
		entity = (Scenetask) this.formToModel(vcityTaskForm,
				"cn.jsprun.domain.Scenetask");
		boolean bool = true;
		String members = "";
		if (vcityTaskForm.getTaskType() == 1) {
			if (bool) {
				if (users != null && users.length > 0) {
					for (int i = 0; i < users.length; i++) {
						if (!users[i].equals("")) {

							for (int j = 0; j < subNo.length; j++) {
								VcitySubTask vac = new VcitySubTask();
								String no = request.getParameter("value("
										+ subNo[j] + ").subTaskIndex");
								String desc = request.getParameter("value("
										+ subNo[j] + ").subTaskDesc");
								String subTaskType = request
										.getParameter("value(" + subNo[j]
												+ ").subTaskType");
								String resId = request.getParameter("value("
										+ subNo[j] + ").subTaskResId");
								String subTaskURL = request
										.getParameter("value(" + subNo[j]
												+ ").subTaskURL");

								if (no != null && desc != null
										&& !no.equals("") && !desc.equals("")) {
									vac.setSubTaskIndex(Integer.parseInt(no));
									vac.setSubTaskDesc(desc);
									vac.setSubTaskType(Integer
											.parseInt(subTaskType));
									vac.setSubTaskResId(resId);
									vac.setSubTaskURL(subTaskURL);
									vac.setSubTaskState(0);
									vac.setUserName(users[i]);
									entity.addVcityTaskChild(vac);
								}
							}
							Scenetm scenetm = new Scenetm();
							scenetm.setUserName(users[i]);
							scenetm.setTaskState(Short.parseShort("0"));
							scenetm.setGetCredit(Short.parseShort("0"));
							entity.addScenetm(scenetm);
							members += users[i] + ",";
						}
					}
				}
			}
		} else {
			bool = vcityInitTaskService
					.deleteInitTaskListByTaskId(vcityTaskForm.getTaskId());
			if (bool) {
/*
				// 人工任务－模型
				String[] resModels = request.getParameterValues("modelId");
				if (resModels != null && resModels.length > 0) {
					for (int i = 0; i < resModels.length; i++) {
						VcityManualTaskInit initTask = new VcityManualTaskInit();
						initTask
								.setSubTaskResId(Integer.parseInt(resModels[i]));
						initTask.setSubTaskType(1);
						entity.addManualTaskInit(initTask);
					}
				}
*/
				// 人工任务－调查
				String[] resSurveys = request.getParameterValues("surveyId");
				if (resSurveys != null && resSurveys.length > 0) {
					for (int i = 0; i < resSurveys.length; i++) {
						VcityManualTaskInit initTask = new VcityManualTaskInit();
						initTask.setSubTaskResId(Integer.parseInt(resSurveys[i]));			
						initTask.setSubTaskType(2);
						entity.addManualTaskInit(initTask);
					}
				}
				
				if (users != null && users.length > 0) {
					for (int i = 0; i < users.length; i++) {
						if (!users[i].equals("")) {
							Scenetm scenetm = new Scenetm();
							scenetm.setUserName(users[i]);
							scenetm.setTaskState(Short.parseShort("0"));
							scenetm.setGetCredit(Short.parseShort("0"));
							scenetm.setLastUpdateTime(0);
							entity.addScenetm(scenetm);
							members += users[i] + ",";
						}
					}
				}
			}
		}
		bool = vcityTaskService.updateVcityTask(entity);
		/*
		 * 从E声网取得筛选用户的邮件列表,并发送邮件通知有新任务 
		 * if (bool) { if (!members.equals("")) {
		 * members = members.substring(0, members.length()-1); List<Members>
		 * meb = MyManageAction.getUserEmailFromE(members); if (meb != null &&
		 * meb.size() > 0) { for (Members mebs : meb) {
		 * MyManageAction.sendEmail(request, mebs.getEmail(),
		 * mebs.getUsername(), vcityTaskForm.getTaskName()); } } } }
		 */

		return showTaskList(mapping, form, request, response);
	}

	public ActionForward deleteTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		vcityTaskForm = (VcityTaskForm) form;

		String id = request.getParameter("taskid");
		Integer delId = null;
		if (id != null && !id.equals("")) {
			delId = Integer.parseInt(id);
		}

		boolean flag = true;

		// 根据传入ID判断是只删除当前行数据，还是批量删除选中的数据
		if (id != null && !id.equals("")) {
			flag = vcityTaskService.deleteTaskById(delId);
		} else {
			flag = vcityTaskService.deleteTaskByIds(vcityTaskForm
					.getSelectedRow());
		}
		request.setAttribute(Constants.ALERT_MESSAGE, flag == true ? "删除成功！"
				: "删除失败！");

		request.setAttribute("vcityTaskForm", vcityTaskForm);
		return showTaskList(mapping, vcityTaskForm, request, response);
	}

	/**
	 * 显示任务详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward taskInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		vcityTaskForm = (VcityTaskForm) form;

		Integer taskId = vcityTaskForm.getTaskId();
		Scenetask task = vcityTaskService.findTaskById(taskId);
		String info = request.getParameter("info");

		Iterator iter = task.getVcitySubTask().iterator();

		List userList = chatService.getUserInfoByTaskId(taskId);
		List<Object[]> subTaskList = new ArrayList<Object[]>();

		String users = ""; // 传到页面用于显示"人员列表"内容

		Set<String> modelList = new HashSet<String>(); // 筛选重复模型id
		Set<String> surveyList = new HashSet<String>(); // 筛选重复调查问卷id
		String tempName = "";                           //当为自动任务时,根据某一用户名称筛选子任务
		while (iter.hasNext()) {
			VcitySubTask subTask = (VcitySubTask) iter.next();
			if (subTask.getSubTaskType() == 1) {
				modelList.add(subTask.getSubTaskResId());
				tempName = subTask.getUserName();
			} else if (subTask.getSubTaskType() == 2) {
				//surveyList.add(subTask.getSubTaskResId());
				surveyList.add(subTask.getSubTaskDesc());
			}
		}
		// 根据子任务的资源ID,得到相应的资源名称,组装成字符串用于页面显示
		/*	String modelName = "";
		Iterator moiter = modelList.iterator();

		while (moiter.hasNext()) {
			String modelId = (String) moiter.next();
			VcityResource resource = vcityResourceService
					.findVcityResourceById(Integer.parseInt(modelId));
			modelName += resource.getResName() + "、";
		}*/

		String surveyName = "";
		Iterator suiter = surveyList.iterator();
		while (suiter.hasNext()) {
			surveyName= (String) suiter.next();
			if (task.getTaskType() == 1) {
/*				VcitySurey survey = vcitySureyService
						.findVcitySureyById(Integer.parseInt(surveyId));*/
				surveyName += surveyName + "、";
			} else {
				surveyName = task.getTaskName()+"临时问卷;";
			}
		}
		/*if (!modelName.equals("")) {
			modelName = modelName.substring(0, modelName.length() - 1);
		}*/
		if (!surveyName.equals("")) {
			surveyName = surveyName.substring(0, surveyName.length() - 1);
		}

		Iterator iterator = userList.iterator();
		List list = new ArrayList();
		while (iterator.hasNext()) {
			Object[] o = (Object[]) iterator.next();
			String userName = (String) o[1];
			list = vcityTaskService.getSubTaskListByUserName(userName, taskId);
			users += userName + "、";
			Scenetm tm = vcityScenetmService.findScenetmByUsernameTaskId(
					taskId, userName);

			Object[] obj = new Object[3];
			obj[0] = userName;
			obj[1] = list;
			obj[2] = tm;
			subTaskList.add(obj);
		}
		if (!users.equals("")) {
			users = users.substring(0, users.length() - 1);
		}
		
		List indexList = vcityTaskService.getSubTaskListByUserName(tempName,taskId);

		if (info!=null) {
			if (info.equals("cust")) {	
				request.setAttribute("mainTask", task);
				request.setAttribute("indexList", indexList);
				return mapping.findForward("cusTaskInfo");
			}
		}


		request.setAttribute("indexList", indexList);
		request.setAttribute("modelName", "");
		request.setAttribute("surveyName", surveyName);
		request.setAttribute("subTaskList", subTaskList);
		request.setAttribute("users", users);
		request.setAttribute("mainTask", task);
		if (request.getParameter("flag")!=null) {
			return mapping.findForward("taskInfoAtChat");
		}
		return mapping.findForward("taskInfo");
	}
}
