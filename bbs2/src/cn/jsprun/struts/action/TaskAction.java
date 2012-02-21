package cn.jsprun.struts.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Task;
import cn.jsprun.domain.TaskList;
import cn.jsprun.domain.TaskMember;

public class TaskAction extends  BaseAction{
  public ActionForward doAddTask(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	  String option=request.getParameter("option");
	  if("addtasktree".equals(option)){
		 String addtasktreeoption=request.getParameter("addtasktreeoption");
		 if("addchild".equals(addtasktreeoption)){
			  String upid=request.getParameter("changjingid");
			  String taskName=request.getParameter("taskName");
			  String treechild=request.getParameter("treechild");
			  String tree[]=treechild.split(",");
			  if("".equals(upid)){
				  request.setAttribute("message", "请选择父场景");
				  request.setAttribute("return", true);
				  return mapping.findForward("message");
			  }
			  if(tree==null||tree.length==0){
				  request.setAttribute("message", "子场景不能为空");
				  request.setAttribute("return", true);
				  return mapping.findForward("message");
			  }
			  Task task=null;
			  for(int i=0;i<tree.length;i++){
				  if(!tree[i].equals("")){
					  task=new Task();
					  task.setTask(tree[i].trim().replace("\n", ""));
					  task.setUp_id(upid.equals("1")?0:Integer.parseInt(upid));
					  taskService.addTaskTree(task);
					  task=null;
				  }
			  }
			  request.setAttribute("message","添加成功");
				 request.setAttribute("url_forward", "admincp.jsp?action=task&opt=doAddTask&option=addtree");
				 return mapping.findForward("message");
			  
		 }
		 else if("updatechangjing".equals(addtasktreeoption)){
			 String taskName=request.getParameter("taskName");
			 String taskNameid=request.getParameter("changjingid");
			 if("".equals(taskName)){
				 request.setAttribute("message","场景名不能为空");
				  request.setAttribute("imgerror","jfkldsf");
			      return mapping.findForward("message");
			 }
			 String updatechangjing="update jrun_task set task='"+taskName+"' where id="+taskNameid;
			 dataBaseService.runQuery(updatechangjing, true);
			 request.setAttribute("message","修改成功");
			 request.setAttribute("url_forward", "admincp.jsp?action=task&opt=doAddTask&option=addtree");
			 return mapping.findForward("message");
		 }
		 else if("delete".equals(addtasktreeoption)){
			 String taskNameid=request.getParameter("changjingid");
			 if("".equals(taskNameid)){
				 request.setAttribute("message", "没有选择要删除的场景");
				 request.setAttribute("return", true);
				 return mapping.findForward("message");
			 }
			 String deletechild="delete from jrun_task where id="+taskNameid+" or up_id="+taskNameid;
			 dataBaseService.runQuery(deletechild, true);
			 request.setAttribute("message","删除成功");
			 request.setAttribute("url_forward", "admincp.jsp?action=task&opt=doAddTask&option=addtree");
			 return mapping.findForward("message");
		 }
		 request.setAttribute("message","未定义操作");
		 request.setAttribute("url_forward", "admincp.jsp?action=task&opt=doAddTask&option=addtree");
		 return mapping.findForward("message");
	  }
	  else if("asstask".equals(option)){
		  String add=request.getParameter("add");
		  if("no".equals(add)){
			  String getTask="SELECT * FROM jrun_task order by up_id";
			  List<Map<String,String>> list=dataBaseService.executeQuery(getTask);
			  if(list!=null&&list.size()>0){
			  request.setAttribute("taskadd",list);
			  }
			  request.setAttribute("count",1);
			  return mapping.findForward("addTask");
		  }
		  else{
			  String taskName=request.getParameter("taskName");
			  String taskstart=request.getParameter("taskstart");
			  String taskend=request.getParameter("taskend");
			  String taskNumber=request.getParameter("taskNumber");
			  String report_name=request.getParameter("report_name");
			  String [] taskdetial=request.getParameterValues("uptaskhid");
			  String report_url=request.getParameter("report_url");
			  String report_remark=request.getParameter("report_remark");
			  if("".equals(taskName)){
				  request.setAttribute("message","请输入任务名称");
				  request.setAttribute("imgerror","jfkldsf");
			      return mapping.findForward("message");
			  }
			  if("".equals(taskstart)){
				  request.setAttribute("message","请输入任务开始时间");
				  request.setAttribute("imgerror","jfkldsf");
			      return mapping.findForward("message");
			  }
			  if("".equals(taskend)){
				  request.setAttribute("message","请输入任务结束时间");
				  request.setAttribute("imgerror","jfkldsf");
			      return mapping.findForward("message");
			  }
			  if("".equals(taskNumber)){
				  request.setAttribute("message","任务参与人员不能为空");
				  request.setAttribute("imgerror","jfkldsf");
			      return mapping.findForward("message");
			  }
			  if(taskdetial==null||taskdetial.length==0){
				  request.setAttribute("message","任务不能为空");
				  request.setAttribute("imgerror","jfkldsf");
			      return mapping.findForward("message");
			  }
			  TaskList tasklist=new TaskList();
			  tasklist.setEndtime(taskend);
			  tasklist.setSearch(report_name);
			  tasklist.setStarttime(taskstart);
			  tasklist.setTaskname(taskName);
			  tasklist.setReport_url(report_url);
			  tasklist.setReport_remark(report_remark);
			  String tasks="";
			  for(int i=0;i<taskdetial.length;i++){
				  tasks+=taskdetial[i]+",";
			  }
			  tasks=tasks.substring(0,tasks.length()-1);
			  tasklist.setTasks(tasks);
			  if(taskListService.addTaskList(tasklist)){
				  if(taskNumber.lastIndexOf(",")==taskNumber.length()-1){
					  taskNumber=taskNumber.substring(0,taskNumber.length()-1);					  
				  }
				  TaskMember taskmember=new TaskMember();
				  taskmember.setMember(taskNumber);
				  taskmember.setTaskid(tasklist.getId());
				  taskMemberService.addTaskMember(taskmember);
				  try {
						response.sendRedirect("admincp.jsp?action=task&opt=doAddTask&option=taskshow");
					}  catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			  }
			  else{
				  request.setAttribute("message","任务添加失败");
				  request.setAttribute("imgerror","jfkldsf");
			      return mapping.findForward("message");
			  }
		  }
	  }
	  else if("taskshow".equals(option)){
		  String showtask="select * from jrun_tasklist";
		  List<Map<String,String>> tasklist=dataBaseService.executeQuery(showtask);
		  if(tasklist!=null&&tasklist.size()>0){
			  request.setAttribute("tasklist",tasklist);
		  }
		  return mapping.findForward("showtask");
	  }
	  else if("taskdetial".equals(option)){
		  String taskid=request.getParameter("id");
		  String sql="SELECT * From jrun_tasklist where id="+taskid;
		  List<Map<String,String>> taskdetial=dataBaseService.executeQuery(sql);
		  if(taskdetial==null||taskdetial.size()==0){
			  request.setAttribute("message","任务不存在");
			  request.setAttribute("imgerror","jfkldsf");
		      return mapping.findForward("message");
		  }
		  request.setAttribute("tasklist", taskdetial.get(0));
		  String taskids=taskdetial.get(0).get("tasks");
		  if(taskids.indexOf(",")==0){
			  taskids=taskids.substring(1,taskids.length());
		  }
		  String selecttask="select * from jrun_task where id in ("+taskids+")";
		  List<Map<String,String>> task=dataBaseService.executeQuery(selecttask);
		  if(task!=null&&task.size()>0){
			  List<Map<String,String>> newtask=new ArrayList<Map<String,String>>();
			  String[] taskidssplit=taskids.split(",");
			  for(int i=0;i<taskidssplit.length;i++){
			     for(int j=0;j<task.size();j++){
				  
					  if(task.get(j).get("id").equals(taskidssplit[i])){
						  newtask.add(task.get(j));					  
						  break;
					  }
				  }
			  }
			  request.setAttribute("task",task);
			  request.setAttribute("newtask",newtask);
			  request.setAttribute("count",task.size());
		  }
		  else{
			  request.setAttribute("count",0);
		  }
		  String selectmember="select * from jrun_taskmember where taskid="+taskdetial.get(0).get("id");
		  List<Map<String,String>> member=dataBaseService.executeQuery(selectmember);
		  if(member!=null&&member.size()>0){
			  request.setAttribute("member",member.get(0));
		  }	
		  
		  String selecttaskall="select * from jrun_task";
		  List<Map<String,String>> taskall=dataBaseService.executeQuery(selecttaskall);
		  if(taskall!=null&&taskall.size()>0){
			  request.setAttribute("taskall",taskall);
		  }	
		  return mapping.findForward("addTask");
	  }
	  else if("edit".equals(option)){
		  int tasklistid=Integer.parseInt(request.getParameter("tasklistid"));
		  String taskName=request.getParameter("taskName");
		  String taskstart=request.getParameter("taskstart");
		  String taskend=request.getParameter("taskend");
		  String taskNumber=request.getParameter("taskNumber");
		  String report_name=request.getParameter("report_name");
		  String [] taskdetial=request.getParameterValues("uptaskhid");
		  String report_url=request.getParameter("report_url");
		  String report_remark=request.getParameter("report_remark");
		  String taskMemberid=request.getParameter("taskMemberid");
		  if("".equals(taskName)){
			  request.setAttribute("message","请输入任务名称");
			  request.setAttribute("imgerror","jfkldsf");
		      return mapping.findForward("message");
		  }
		  if("".equals(taskstart)){
			  request.setAttribute("message","请输入任务开始时间");
			  request.setAttribute("imgerror","jfkldsf");
		      return mapping.findForward("message");
		  }
		  if("".equals(taskend)){
			  request.setAttribute("message","请输入任务结束时间");
			  request.setAttribute("imgerror","jfkldsf");
		      return mapping.findForward("message");
		  }
		  if("".equals(taskNumber)){
			  request.setAttribute("message","任务参与人员不能为空");
			  request.setAttribute("imgerror","jfkldsf");
		      return mapping.findForward("message");
		  }
		  if(taskdetial==null||taskdetial.length==0){
			  request.setAttribute("message","任务不能为空");
			  request.setAttribute("imgerror","jfkldsf");
		      return mapping.findForward("message");
		  }
		  TaskList tasklist=new TaskList();
		  tasklist.setId(tasklistid);
		  tasklist.setEndtime(taskend);
		  tasklist.setSearch(report_name);
		  tasklist.setStarttime(taskstart);
		  tasklist.setTaskname(taskName);
		  tasklist.setReport_url(report_url);
		  tasklist.setReport_remark(report_remark);
		  String tasks="";
		  for(int i=0;i<taskdetial.length;i++){
			  tasks+=taskdetial[i]+",";
		  }
		  tasks=tasks.substring(0,tasks.length()-1);
		  tasklist.setTasks(tasks);
		  if(taskListService.addTaskList(tasklist)){
			  if(taskNumber.lastIndexOf(",")==taskNumber.length()-1){
				  taskNumber=taskNumber.substring(0,taskNumber.length()-1);					  
			  }
			  TaskMember taskmember=new TaskMember();
			  taskmember.setId(Integer.parseInt(taskMemberid));
			  taskmember.setMember(taskNumber);
			  taskmember.setTaskid(tasklist.getId());
			  taskMemberService.addTaskMember(taskmember);
			  try {
					response.sendRedirect("admincp.jsp?action=task&opt=doAddTask&option=taskshow");
				}  catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		  }
		  else{
			  request.setAttribute("message","任务添加失败");
			  request.setAttribute("imgerror","jfkldsf");
		      return mapping.findForward("message");
		  }
	  }
	  else if("delete".equals(option)){
		  String [] ids=request.getParameterValues("id[]");
		  if(ids==null||ids.length==0){
			  request.setAttribute("message","请选择要删除的任务");
			  request.setAttribute("imgerror","jfkldsf");
		      return mapping.findForward("message");
		  }
		  for(int i=0;i<ids.length;i++){
			  String deletetaskmember="delete from jrun_taskmember where taskid="+ids[i];
			  String deletetasklist="delete from jrun_tasklist where id="+ids[i];
			  dataBaseService.runQuery(deletetaskmember, true);
			  dataBaseService.runQuery(deletetasklist, true);
		  }
		  try {
			request.getRequestDispatcher("admincp.jsp?action=task&opt=doAddTask&option=taskshow").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  else{
		  String getTask="SELECT * FROM jrun_task order by up_id";
		  List<Map<String,String>> list=dataBaseService.executeQuery(getTask);
		  String var="";
		  
		  if(list!=null&&list.size()>0){
			  request.setAttribute("taskadd",list);
			  for(int i=0;i<list.size();i++){
				  Map<String,String> map=list.get(i);
				  if((map.get("up_id")+"").equals("0")){
					  var+="d.add("+map.get("id")+","+(i==0?"-1":"1")+",'"+map.get("task")+"',\"javascript:getHostList("+map.get("id")+",'"+map.get("task")+"')\",'','');\n";
				  }
				  else{
					  var+="d.add("+map.get("id")+","+(Integer.parseInt(map.get("up_id")+""))+",'"+map.get("task")+"',\"javascript:getHostList("+map.get("id")+",'"+map.get("task")+"')\",'','');\n";
				  }
			  }
		  }
		  request.setAttribute("task",var);
		  return mapping.findForward("addtree");
	  }
	  return mapping.findForward("addTask");
  }
  public ActionForward doGetTree(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	  String getTask="SELECT * FROM jrun_task order by up_id";
	  List<Map<String,String>> list=dataBaseService.executeQuery(getTask);
	  String size=request.getParameter("size");
	  String var="";
	  if(list!=null&&list.size()>0){
		  for(int i=0;i<list.size();i++){
			  Map<String,String> map=list.get(i);
			  if((map.get("up_id")+"").equals("0")){
				  var+="d.add("+map.get("id")+","+(i==0?"-1":"1")+",'"+map.get("task")+"',\"javascript:getHostList("+map.get("id")+",'"+map.get("task")+"')\",'','');\n";
			  }
			  else{
				  var+="d.add("+map.get("id")+","+(Integer.parseInt(map.get("up_id")+""))+",'"+map.get("task")+"',\"javascript:getHostList("+map.get("id")+",'"+map.get("task")+"')\",'','');\n";
			  }
		  }
	  }
	  request.setAttribute("size",size);
	  request.setAttribute("task",var);
	  return mapping.findForward("showtree");
  }
  public ActionForward doGetUser(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
	  String getUser="SELECT * FROM jrun_members";
	  List<Map<String,String>> user=dataBaseService.executeQuery(getUser);
	  if(user!=null&&user.size()>0){
		  request.setAttribute("user",user);
	  }
	  String tasklistid=request.getParameter("tasklistid");
	  if(!"".equals(tasklistid)){
		  String getTaskMember="SELECT member FROM jrun_taskmember WHERE taskid="+tasklistid;
		  List<Map<String,String>> getMember=dataBaseService.executeQuery(getTaskMember);
		  if(getMember!=null&&getMember.size()>0){
			  request.setAttribute("getMember",getMember.get(0).get("member"));
		  }
	  }
	  return mapping.findForward("showuser");
  }
}
