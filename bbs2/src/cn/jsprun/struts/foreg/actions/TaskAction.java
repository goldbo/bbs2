package cn.jsprun.struts.foreg.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Members;
import cn.jsprun.domain.Scene;
import cn.jsprun.domain.ScenectOrder;
import cn.jsprun.struts.action.BaseAction;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.Md5Token;

/**
 *前台任务操作的Action
 * @author Administrator
 *
 */

public class TaskAction extends BaseAction {
	
	/**
	 * 得到用户的任务
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
    public ActionForward doGetMyTask(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws IOException{
    	HttpSession session=request.getSession();
    	int jsprun_uid=(Integer)session.getAttribute("jsprun_uid");
    	PrintWriter out=response.getWriter();
    	if(jsprun_uid==0){
    		out.println("<script>parent.closetask()</script>");
    		return null;
    	}
    	Members member=(Members)session.getAttribute("user");
    	List<Map<String,String>> listtask=dataBaseService.executeQuery("select stask.*,stm.taskState,stm.taskTime as time from jrun_scenetask stask inner join jrun_scenetm stm on stask.taskId=stm.taskId where stm.userName='"+member.getUsername()+"' order by stask.taskTime desc");
    	request.setAttribute("listtask", listtask);
    	return mapping.findForward("mytasklist");
    }
    
    /**
     * 得到任务的详情
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException 
     */
    public ActionForward doGetTaskDetial(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws IOException{
    	String tid=request.getParameter("tid");
    	HttpSession session=request.getSession();
    	PrintWriter out=response.getWriter();
    	int jsprun_uid=(Integer)session.getAttribute("jsprun_uid");
    	if(jsprun_uid==0){
    		out.println("<script>parent.closetask()</script>");
    		return null;
    	}
    	else if("".equals(tid)){
    		out.println("<script>parent.closetask()</script>");
    		return null;
    	}
    	else{
    		Members member=(Members)session.getAttribute("user");
        //	List<Map<String,String>> listtask=dataBaseService.executeQuery("select * from jrun_scenect  where taskId="+tid+" and userName='"+member.getUsername()+"' order by taskNo,id");
    		List<Map<String,String>> listtask=dataBaseService.executeQuery("select * from vcity_subTask  where taskId="+tid+" and userName='"+member.getUsername()+"' order by subTaskIndex,subTaskID");
        	List<Map<String,String>> taskDetial=null;
        	List<Map<String,String>> task=dataBaseService.executeQuery("select * from jrun_scenetask where taskId="+tid);
        	if(listtask!=null&&listtask.size()>0){
        		taskDetial=new ArrayList<Map<String,String>>();
    			Map<String,String> mct=null;
    			for(Map<String,String> scenect:listtask){
    				mct=new HashMap<String,String>();
					mct.put("subTaskID", scenect.get("subTaskID"));    //子任务ID
    				if(scenect.get("subTaskType").equals("2")){
    					mct.put("surveyName", scenect.get("subTaskDesc"));
    					mct.put("surveyUrl", scenect.get("subTaskURL"));
    					mct.put("subTaskResoureceID", scenect.get("subTaskResoureceID"));     //调查ID
    				}
    				else{
    					/*List<Map<String,String>> res = dataBaseService.executeQuery("select * from vcity_vresources res where res.resId="+scenect.get("subTaskResoureceID"));
    					Map<String,String> o  = res.get(0);
    					if (task.get(0).get("taskType").equals("2")) {
    						mct.put("objName",o.get("resName"));
    						mct.put("resType", o.get("resType"));
    						mct.put("moduleFileName", o.get("moduleFileName"));
    						mct.put("resURL", o.get("resURL"));
    						mct.put("resId", o.get("resId"));
						} else {
	    					String objid = o.get("senceID");
	    					objid = objid.substring(0, 3);*/
	    					//List<Scene> lsce=sceneService.getSceneByCondition("from Scene s where s.objId="+scenect.get("subTaskResoureceID"));	    					
    					//mct.put("objName", lsce.size()>0?lsce.get(0).getObjName():"");
    					mct.put("objName", scenect.get("subTaskDesc").substring(3));
						//}
    				}	
    				mct.put("id", scenect.get("id"));
    				mct.put("taskType", scenect.get("subTaskType"));
    				mct.put("taskState", scenect.get("subTaskState"));
    				taskDetial.add(mct);
    			}
    			session.setAttribute("tempTaskid", tid);
    			request.setAttribute("taskDetial",taskDetial);
    		}
        	      	
        	if(task!=null&&task.size()>0){
        		List<Map<String,String>> taskState=dataBaseService.executeQuery("select taskState,getCredit from jrun_scenetm where taskId="+tid+" and userName='"+member.getUsername()+"'");
        		if(taskState!=null&&taskState.size()>0){
        			request.setAttribute("state",taskState.get(0).get("taskState"));
        			request.setAttribute("getCredit",taskState.get(0).get("getCredit"));
        		}
        		request.setAttribute("task",task.get(0));
        	}
        	return mapping.findForward("taskDetial");
    	}
    }
    
    /**
     * 用户点击执行任务后保存执行任务标识的方法
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doExcuteTask(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	HttpSession session=request.getSession();
    	PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String tid=request.getParameter("tid");
    	if("".equals(tid))
    	{
    		out.print("1");    		
    	}
    	else{
    		session.setAttribute("taskid", tid);
    		out.print("2");
    	}
    	return null;
    }
    /**
     * 用于用户执行完场景任务后的任务状态修改
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doUserExcuteTask(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	HttpSession session=request.getSession();
    	String taskid=(String)session.getAttribute("taskid");
    	int jsprun_uid=(Integer)session.getAttribute("jsprun_uid");
    	String objid=request.getParameter("objId");
    	/*if (request.getParameter("isRenGong")!=null) {
    		taskid = (String)session.getAttribute("tempTaskid");
    	}*/
    	PrintWriter out=null;
    	try {
			out=response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(objid!=null&&!"".equals(objid)){
    		String sql="select eventType,eventUrl from jrun_scene where objId="+objid;
    		List<Map<String,String>> lty=dataBaseService.executeQuery(sql);
    		if(lty!=null&&lty.size()>0){
    			out.print(lty.get(0).get("eventType")+","+lty.get(0).get("eventUrl"));
    		}
    		else{
    			out.print("");
    		}
    	}
    	if((taskid!=null&&!"".equals(taskid))&&jsprun_uid>0){
    		Members member=(Members)session.getAttribute("user");
    		//String sql="select * from jrun_scenect where taskId="+taskid+" and userName='"+member.getUsername()+"' and taskState=1";
    		String sql="select * from vcity_subTask where taskId="+taskid+" and userName='"+member.getUsername()+"' and subTaskState=0";
    		
    	/*	//此时为调查
    		if(("".equals(objid)||objid==null)&&!"".equals(request.getParameter("ctid"))){    			
    			//sql+=" and id="+Integer.parseInt(request.getParameter("ctid"));
    			sql+=" and subTaskID="+Integer.parseInt(request.getParameter("ctid"));
    		}
    		//此时为场景
    		else{
    			//sql+=" and objId="+objid+"";
    			sql+=" and subTaskResoureceID="+objid+"";
    		} */
    		
    		List<Map<String,String>> scenectList=dataBaseService.executeQuery(sql);

    		if(scenectList!=null&&scenectList.size()>0){
    			for (int i = 0; i < scenectList.size(); i++) {
    				Map<String,String> map=scenectList.get(i);
    				String subType = map.get("subTaskType");
    				String subid = map.get("subTaskID");
    				String resid = map.get("subTaskResoureceID");

    				if (subType.equals("1")&&resid.equals(objid)) {     //子任务为场景
						//List<Map<String,String>> resource=dataBaseService.executeQuery("select * from vcity_vresources re where re.resId='"+resid+"' ");
						//String subobjid = resource.get(0).get("senceID");
						//subobjid = subobjid.substring(0, 3);      //得到虚拟资源挂载的最上级场景
						//if (objid.equals(subobjid)) {
							String update="update vcity_subTask set subTaskState=1 where subTaskID="+subid;
			    			dataBaseService.runQuery(update, false);
						//} 
			    			
			    			//这是人工任务时候的
						/*if (request.getParameter("cTask").equals(subid)) {
							System.out.println(subid);
							String update1="update vcity_subTask set subTaskState=1 where subTaskID="+subid;
			    			dataBaseService.runQuery(update1, false);
						}*/
					} 
    						
				}
    			String checkno="select * from vcity_subTask where taskId="+taskid+" and userName='"+member.getUsername()+"' and subTaskState=0";
    			List<Map<String,String>> checkmap=dataBaseService.executeQuery(checkno);
    			if(!(checkmap!=null&&checkmap.size()>0)){
    				String updatetask="update jrun_scenetm set taskState=2,taskTime='"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' where taskId="+taskid+" and userName='"+member.getUsername()+"'";
    				dataBaseService.runQuery(updatetask,false);
    			}
    		}
    	}
    	return null;
    }
    
    /**
     * 点击E声网链接的时候就更新该e声网调查子任务的状态
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward updateEsayingSurveyState(ActionMapping mapping,ActionForm form,
    		HttpServletRequest request,HttpServletResponse response){
    	String subid = request.getParameter("subid");
    	doUserExcuteManualTask(Integer.parseInt(subid),null);
    	return null;
    }
    	   
    
    /**
     * 用于用户执行完调查任务后的任务状态修改
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public void doUserExcuteManualTask(Integer subid,String taskType) {

		String sql = "select * from vcity_subTask where subTaskID=" + subid
				+ " and subTaskState=0";

		List<Map<String, String>> subList = dataBaseService.executeQuery(sql);
		if (subList != null && subList.size() > 0) {
			Map<String, String> map = subList.get(0);
			String taskId = map.get("taskId");
			String userName = map.get("userName");
			String update = "update vcity_subTask set subTaskState=1 where subTaskID="
					+ subid;
			dataBaseService.runQuery(update, false);
			String checkno = "select * from vcity_subTask where taskId="
					+ taskId + " and userName='" + userName
					+ "' and subTaskState=0";
			List<Map<String, String>> checkmap = dataBaseService
					.executeQuery(checkno);
			//&&taskType!=null&&taskType.equals("1") //备用
			//如果taskType为人工任务，不更新主任务状态，研究员在聊天窗口控制
			if (!(checkmap != null && checkmap.size() > 0)) {
				String updatetask = "update jrun_scenetm set taskState=2,taskTime='"
						+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())
						+ "' where taskId="
						+ taskId
						+ " and userName='"
						+ userName + "'";
				dataBaseService.runQuery(updatetask, false);
			}

		}
	}
    
    
    /**
     * 积分兑换方法
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doGetCredit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	HttpSession session=request.getSession();
    	
    	int jsprun_uid=(Integer)session.getAttribute("jsprun_uid");
    	PrintWriter out=null;
    	try {
			out=response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(jsprun_uid>0){
    		Members member=(Members)session.getAttribute("user");
    		String custer_id=member.getCuster_id()+"";
    		if("".equals(custer_id)){
    			out.println("对不起,系统繁忙");
    		}
    		else{
    			String taskId=request.getParameter("tid");
    			if(Common.isNum(taskId)){
    				String sql="select stk.taskName,stk.credit from jrun_scenetm stm inner join jrun_scenetask stk where stm.userName='"+member.getUsername()+"' and stk.taskId="+taskId+" and stm.taskState=2";
    				List<Map<String,String>> list=dataBaseService.executeQuery(sql);
    				if(list!=null&&list.size()>0){
    					Md5Token md5=Md5Token.getInstance();
    					String content=Common.getCredit(member.getCuster_id(),list.get(0).get("credit"),"第一体验俱乐部‘"+list.get(0).get("taskName")+"’任务兑奖积分");
    					content=content.substring(1,content.length()-1);
    					String[] spfirst=content.split(",");
    					boolean isok=false;
    					String result="";
    					for(int i=0;i<spfirst.length;i++){
    						spfirst[i]=spfirst[i].replace("\"","");
    						String[] spsec=spfirst[i].split(":");
    						if("result".equals(spsec[0])&&"1".equals(spsec[1])){
    							isok=true;
    						}
    						if("reason".equals(spsec[0])){
    							result=spsec[1];
    						}
    					}
    					if(!isok){
    						try {
								out.println(new String(result.getBytes(),"utf-8"));
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
    					}
    					else{
    						String update="update jrun_scenetm set getCredit=1 where taskId="+taskId+" and userName='"+member.getUsername()+"' and taskState=2";
    						dataBaseService.runQuery(update,false);
    						out.println("兑换积分成功");
    					}
    				}
    				else{
    					out.print("该任务没有完成,不能兑换积分");
    				}
    			}
    			else{
    				out.println("没有选中所要兑换积分的任务");
    			}
    		}
    	}
    	else{
    		out.println("你没有登录,无法兑换积分");
    	}
    	return null;
    }
}
