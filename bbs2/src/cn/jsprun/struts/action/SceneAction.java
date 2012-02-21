package cn.jsprun.struts.action;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;

import cn.jsprun.domain.Members;
import cn.jsprun.domain.Scene;
import cn.jsprun.domain.Scenect;
import cn.jsprun.domain.Scenetask;
import cn.jsprun.domain.Scenetm;
import cn.jsprun.meeting.ConnectionDBE;
import cn.jsprun.meeting.UmsUser;
import cn.jsprun.struts.conference.action.InvitationTemplate;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.FileUploadUtil;
import cn.jsprun.utils.ForumInit;
import cn.jsprun.utils.JspRunConfig;
import cn.jsprun.utils.Mail;

/**
 * 场景任务物件Action
 * 
 * @author Administrator
 * 
 */

public class SceneAction extends BaseAction {
	
	public final static String FILEPATHTIME = JspRunConfig.realPath+"forumdata/temp";
	public final static int memeoryBlock = 1572864;

	/**
	 * 场景物件配置方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward doScene(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String options = request.getParameter("opt");

		if (options.equals("update")) {
			String taskName = request.getParameter("taskName");
			String objid = request.getParameter("objid");
			String taskid = request.getParameter("taskid");
			String pid = request.getParameter("pid");
			String image=request.getParameter("images");
			String sceneName=request.getParameter("sceneName");
			String eventType=request.getParameter("eventType");
			String eventUrl=request.getParameter("eventUrl");
			if (taskName == null || "".equals(taskName)) {
				request.setAttribute("message", "场景物件名不能为空");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			Integer objID = 0;
			try {
				objID = Integer.valueOf(objid);
			} catch (Exception ex) {
				request.setAttribute("message", "场景物件ID必须不为空且必须是整数");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}

//			if (this.checkobjid(Integer.parseInt(taskid), objID)) {
//				request.setAttribute("message", "场景物件ID已存在,请重新输入场景物件ID");
//				request.setAttribute("return", true);
//				return mapping.findForward("message");
//			}     
			
//			if(!"0".equals(eventType)){
//				Pattern p=Pattern.compile("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
//				Matcher m=p.matcher(eventUrl);
//				if(!m.find()){
//					request.setAttribute("message", "事件地址错误");
//					request.setAttribute("return", true);
//					return mapping.findForward("message");
//				}
//			}
			
			Scene scene = new Scene();
			scene.setId(Integer.parseInt(taskid));
			scene.setObjId(objID);
			scene.setObjName(taskName);
			scene.setPid(Integer.parseInt(pid));
			scene.setImages(image);
			scene.setSceneName(sceneName);
			scene.setEventType(Integer.parseInt(eventType));
			scene.setEventUrl(eventUrl);
			if (sceneService.saveOrupdateScene(scene)) {
				request.setAttribute("message", "场景修改成功");
				request.setAttribute("url_forward",
						"admincp.jsp?action=scene&option=doScene");
				return mapping.findForward("message");
			} else {
				request.setAttribute("message", "场景物件修改失败");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
		} else if (options.equals("addchild")) {
//			String taskid = request.getParameter("taskid");
//			String[] newtaskName = request.getParameterValues("newtaskName");
//			String[] newobjid = request.getParameterValues("newobjid");
//			String[] image=request.getParameterValues("image");
//			if ("".equals(taskid)) {
//				request.setAttribute("message", "没有选中场景");
//				request.setAttribute("return", true);
//				return mapping.findForward("message");
//			}
//			if (newtaskName == null || newtaskName.length == 0) {
//				request.setAttribute("message", "没有输入要添加的子场景");
//				request.setAttribute("return", true);
//				return mapping.findForward("message");
//			}
//			Scene scene = null;
//			for (int i = 0; i < newtaskName.length; i++) {
//				if (!"".equals(newtaskName[i]) && !"".equals(newobjid[i])) {
//					try {
//						int objID = Integer.valueOf(newobjid[i]);
//						if (!this.checkobjid(0, objID)) {
//							scene = new Scene();
//							scene.setObjId(objID);
//							scene.setObjName(newtaskName[i]);
//							scene.setPid(Integer.parseInt(taskid));
//							scene.setImages(image[i]);
//							scene.setEventType(0);
//							scene.setEventUrl("");
//							scene.setSceneName("");
//							sceneService.saveOrupdateScene(scene);
//						}
//					} catch (Exception ex) {
//                          ex.printStackTrace();
//					}
//				}
//
//			}
//			response.sendRedirect("admincp.jsp?action=scene&option=doScene");
//			return null;
			String taskName = request.getParameter("taskName");
			String objid = request.getParameter("objid");
			String taskid = request.getParameter("taskid");
			String image=request.getParameter("images");
			String sceneName=request.getParameter("sceneName");
			String eventType=request.getParameter("eventType");
			String eventUrl=request.getParameter("eventUrl");
			if (taskName == null || "".equals(taskName)) {
				request.setAttribute("message", "场景物件名不能为空");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			Integer objID = 0;
			try {
				objID = Integer.valueOf(objid);
			} catch (Exception ex) {
				request.setAttribute("message", "场景物件ID必须不为空且必须是整数");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}

//			if (this.checkobjid(0, objID)) {
//				request.setAttribute("message", "场景物件ID已存在,请重新输入场景物件ID");
//				request.setAttribute("return", true);
//				return mapping.findForward("message");
//			}     
			
//			if(!"0".equals(eventType)){
//				Pattern p=Pattern.compile("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
//				Matcher m=p.matcher(eventUrl);
//				if(!m.find()){
//					request.setAttribute("message", "事件地址错误");
//					request.setAttribute("return", true);
//					return mapping.findForward("message");
//				}
//			}
			
			Scene scene = new Scene();
			scene.setObjName(taskName);
			scene.setPid(Integer.parseInt(taskid));
			scene.setImages(image);
			scene.setSceneName(sceneName);
			scene.setEventType(Integer.parseInt(eventType));
			scene.setEventUrl(eventUrl);
			scene.setObjId(objID);
			if (sceneService.saveOrupdateScene(scene)) {
				request.setAttribute("message", "子场景添加成功");
				request.setAttribute("url_forward",
						"admincp.jsp?action=scene&option=doScene");
				return mapping.findForward("message");
			} else {
				request.setAttribute("message", "子场景添加失败");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
		} else if ("delete".equals(options)) {
			String taskid = request.getParameter("taskid");
			if ("".equals(taskid)) {
				request.setAttribute("message", "没有选中要删除的场景");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			List<Map<String,String>> sceneimage=dataBaseService.executeQuery("select images from jrun_scene where id="+taskid+" or pid="+taskid);
			if (sceneService.deleteByUpId(Integer.parseInt(taskid))) {
				if(sceneimage!=null&&sceneimage.size()>0){
					File filep=null;
					for(Map<String,String> mapp:sceneimage){
						filep=new File(servlet.getServletContext().getRealPath("/"+mapp.get("images")));
						if(filep.exists()){
							filep.delete();
						}
						filep=null;
					}
				}
				request.setAttribute("message", "删除场景物件成功");
				request.setAttribute("url_forward",
						"admincp.jsp?action=scene&option=doScene");
				return mapping.findForward("message");
			} else {
				request.setAttribute("message", "删除场景物件失败");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
		} else {
			List<Scene> scene = sceneService.getAllScene();
			String var = "";
			if (scene != null && scene.size() > 0) {
				for (int i = 0; i < scene.size(); i++) {
					Scene sce = scene.get(i);
					if (sce.getPid() == 0) {
						var += "d.add(" + sce.getId() + "," + (i == 0 ? -1 : 1)
								+ ",'" + sce.getObjName()
								+ "',\"javascript:getHostList(" + sce.getId()
								+ ",'" + sce.getObjName() + "','"
								+ sce.getObjId() + "','" + sce.getPid()
								+ "','"+sce.getImages()+"','"+sce.getSceneName()+"','"+sce.getEventType()+"','"+sce.getEventUrl()+"')\",'','');\n";
					} else {
						var += "d.add(" + sce.getId() + "," + sce.getPid()
								+ ",'" + sce.getObjName()
								+ "',\"javascript:getHostList(" + sce.getId()
								+ ",'" + sce.getObjName() + "','"
								+ sce.getObjId() + "','" + sce.getPid()
								+ "','"+sce.getImages()+"','"+sce.getSceneName()+"','"+sce.getEventType()+"','"+sce.getEventUrl()+"')\",'','');\n";
					}
				}
			}
			request.setAttribute("scene", var);
			return mapping.findForward("scenelist");
		}
	}	
	
	/**
	 * 任务管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doTask(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		 String opt=request.getParameter("opt");
		 HttpSession session=request.getSession();
		 Members member=(Members)session.getAttribute("user");
		 if(member==null){
			 request.setAttribute("message","对不起,你没有登录,不能执行此操作");
			 request.setAttribute("return", true);
			 return mapping.findForward("message");
		 }
		 if("addTask".equals(opt)){
			 String submit=request.getParameter("send");
			 if(submit==null||"".equals(submit)){
				 request.setAttribute("count", 3);
				 return mapping.findForward("taskAdd");
			 }
			 else{
				 String taskName=request.getParameter("taskName");
				 String taskIntro=request.getParameter("taskIntro");
				 String credit=request.getParameter("credit");
				 //String taskNumber=request.getParameter("taskNumber");
				 String[] user=request.getParameterValues("checkuser");
				 String[] taskNo=request.getParameterValues("taskNo");
				 if("".equals(taskName.trim())){
					 request.setAttribute("message","任务名称不能为空");
					 request.setAttribute("return", true);
					 return mapping.findForward("message");
				 }
				 if("".equals(taskIntro.trim())){
					 request.setAttribute("message","任务介绍不能为空");
					 request.setAttribute("return", true);
					 return mapping.findForward("message");
				 }
				 if(!Common.isNum(credit)){
					 request.setAttribute("message","任务积分必须为整数");
					 request.setAttribute("return", true);
					 return mapping.findForward("message");
				 }
				 
				 //if("".equals(taskNumber)){
				 if(user==null||user.length==0){
					 request.setAttribute("message","会员不能为空");
					 request.setAttribute("return", true);
					 return mapping.findForward("message");
				 }
				 if(taskNo==null||taskNo.length==0){
					 request.setAttribute("message","没有添加要执行的任务，任务添加失败");
					 request.setAttribute("return", true);
					 return mapping.findForward("message");
				 }
				 Scenetask stask=new Scenetask();
				 stask.setTaskIntro(taskIntro);
				 stask.setTaskName(taskName);
				 stask.setCredit(Integer.parseInt(credit));
				 stask.setCreateUser(member.getUsername());
				 stask.setTaskTime((new SimpleDateFormat("yyyy年MM月dd日")).format(new Date()));
				 if(scenetaskService.saveOrUpdateTask(stask)){
					 //taskNumber=taskNumber.lastIndexOf(",")==(taskNumber.length()-1)?taskNumber.substring(0,taskNumber.length()-1):taskNumber;
					//String[] memb=taskNumber.split(",");
					boolean success=true;
					Scenetm stm=null;
					for(int i=0;i<user.length;i++){
						stm=new Scenetm();
						stm.setTaskId(stask.getTaskId());						
						stm.setTaskTime("");
						stm.setTaskState(Short.parseShort("1"));
						Short getcredit=0;
						stm.setGetCredit(getcredit);
						stm.setUserName(Common.htmlspecialchars(user[i]));
						if(!scenetmService.save(stm)){
							scenetmService.deleteByHql("delete from Scenetm s where s.taskId="+stask.getTaskId());
							scenetaskService.deleteByHql("delete from Scenetask st where st.taskId="+stask.getTaskId());
							success=false;
							break;
						}
						
						stm=null;
					}
					boolean lastsuccess=true;
					if(success){
						Scenect sct=null;
						for(int j=0;j<user.length;j++){
						    for(int i=0;i<taskNo.length;i++){
						    	sct=new Scenect();
								String taskType=request.getParameter("taskType"+(i+1));
								String surveyName=request.getParameter("surveyName"+(i+1));
								String surveyUrl=request.getParameter("surveyUrl"+(i+1));
								String tasksubid=request.getParameter("tasksubid"+(i+1));
								sct.setTaskType(Short.parseShort(taskType));
								sct.setTaskId(stask.getTaskId());
								sct.setUserName(user[j]);
								boolean issuccess=false;
								sct.setTaskState(Short.parseShort(1+""));
								if(taskType.equals("1")){
									if(!"".equals(surveyName)&&!"".equals(surveyUrl)){
										sct.setSurveyName(surveyName);
										sct.setSurveyUrl(surveyUrl);	
										issuccess=true;
									}
									else{
										issuccess=false;
									}
								}
								if("2".equals(taskType)){
									if(!"".equals(tasksubid)){
										sct.setObjId(Integer.parseInt(tasksubid));
										issuccess=true;
									}
									else{
										issuccess=false;
									}
								}
								if(issuccess){
									sct.setTaskNo(Integer.parseInt(taskNo[i]));
									if(!scenectService.saveOrUpdate(sct)){
										lastsuccess=false;
										break;
									}
								}
						    }
						}
						if(lastsuccess){
							String numbers="";
							for(String nu:user){
								numbers=nu+",";
							}
							List<Members> meb=this.getUserEmailFromE(numbers);
							if(meb!=null&&meb.size()>0){
								for(Members mebs:meb){
									this.sendEmail(request, mebs.getEmail(), mebs.getUsername(),taskName);
								}
							}
							request.setAttribute("message", "任务添加成功");
							request.setAttribute("url_forward",
									"admincp.jsp?action=scene&option=doTask");
							return mapping.findForward("message");
						}
						else{
							 scenetmService.deleteByHql("delete from Scenetm s where s.taskId="+stask.getTaskId());
							 scenetaskService.deleteByHql("delete from Scenetask st where st.taskId="+stask.getTaskId());
							 request.setAttribute("message","任务添加失败");
							 request.setAttribute("return", true);
							 return mapping.findForward("message");
						}
					}
				 }				 
				 request.setAttribute("message","任务添加失败");
				 request.setAttribute("return", true);
				 return mapping.findForward("message");
			 }
		 }
		 else{
			 int curpage=request.getParameter("curpage")==null?0:Integer.parseInt(request.getParameter("curpage"));
			 List<Scenetask> scenetask=scenetaskService.getAllScenetask(curpage,10);
			 request.setAttribute("scenetask", scenetask);
			 int count=scenetaskService.getCountByCondition("from Scenetask");
			 request.setAttribute("count", count);
			 request.setAttribute("total", count%1==0?count/10:count/10+1);
			 request.setAttribute("curpage",curpage);
			 return mapping.findForward("tasklist");
		 }
	 }
	
	/**
	 * 任务详情方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doDetial(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
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
		

		String txtName=request.getParameter("txtName");//得到搜索框里的用户名
		String state=request.getParameter("state");//得到搜索框的活动状态
		request.setAttribute("tid", tid);
		int curpage=request.getParameter("curpage")==null?0:Integer.parseInt(request.getParameter("curpage"));	//得到当前页
		this.getTaskState(request, txtName, state, Integer.parseInt(tid), curpage, 10);
		return mapping.findForward("detial");
	}
	
	
	/**
	 *得到场景树列表
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
		String forward = "tree";
		if (scene != null && scene.size() > 0) {
			for (int i = 0; i < scene.size(); i++) {
				Scene sce = scene.get(i);
				if (sce.getPid() == 0) {
					var += "d.add(" + sce.getId() + "," + (i == 0 ? -1 : 1)
							+ ",'" + sce.getObjName()
							+ "',\"javascript:getHostList(" + sce.getObjId()
							+ ",'" + sce.getObjName() +"')\",'','');\n";
				} else {
					var += "d.add(" + sce.getId() + "," + sce.getPid() + ",'"
							+ sce.getObjName() + "',\"javascript:getHostList("
							+ sce.getObjId()+ ",'" + sce.getObjName() + "')\",'','');\n";
				}
			}
		}
		request.setAttribute("size", request.getParameter("size"));
		request.setAttribute("scene", var);
		if (request.getParameter("flag")!=null) {
			request.setAttribute("isManual", request.getParameter("isManual"));  //是否为人工任务
			request.setAttribute("num", request.getParameter("flag"));
			forward = "tree_task";
		}
		return mapping.findForward(forward);
	}

	/**
	 * 任务的编辑方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		String tid=request.getParameter("tid");
		if(tid==null||"".equals(tid)){
			request.setAttribute("message","未选中要编辑的任务,请返回");
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		String opt=request.getParameter("opt");
		if(opt==null||"".equals(opt)){
			Scenetask stask=scenetaskService.getScenetaskById(Integer.parseInt(tid));
			if(stask==null){
				request.setAttribute("message","没有该任务,请返回");
				request.setAttribute("return", true);
				return mapping.findForward("message");
			}
			request.setAttribute("scenetask", stask);
			
			List<Map<String,String>> mapsct=null;
			List<Map<String,String>> lscenect=this.getTask(Integer.parseInt(tid));
			int count=0;
			if(lscenect!=null&&lscenect.size()>0){
				mapsct=new ArrayList<Map<String,String>>();
				Map<String,String> mct=null;
				for(Map<String,String> scenect:lscenect){
					mct=new HashMap<String,String>();
					if(scenect.get("taskType").equals("1")){
						mct.put("surveyName", scenect.get("surveyName"));
						mct.put("surveyUrl", scenect.get("surveyUrl"));
					}
					else{
						List<Scene> lsce=sceneService.getSceneByCondition("from Scene s where s.objId="+scenect.get("objId"));
						mct.put("objName", lsce.size()>0?lsce.get(0).getObjName():"");
						mct.put("objId", scenect.get("objId"));
					}	
					mct.put("taskNo", scenect.get("taskNo"));
					mct.put("taskType", scenect.get("taskType"));
					mapsct.add(mct);
				}
				count=lscenect.size();
				request.setAttribute("lscenect",mapsct);
			}
			request.setAttribute("count",count);
			//String person="";
		//	List<Map<String,String>> ltm=dataBaseService.executeQuery("select distinct userName from jrun_scenect where taskId="+tid);
			List<Map<String,String>> ltm=dataBaseService.executeQuery("select distinct userName from vcity_subTask where taskId="+tid);
//			if(ltm!=null&&ltm.size()>0){
//				for(Map<String,String> map:ltm){
//					person=person.equals("")?map.get("userName"):person+","+map.get("userName");
//				}
//			}
			request.setAttribute("ltm", ltm);
			return mapping.findForward("taskAdd");
		}
		else{
			 String taskName=request.getParameter("taskName");
			 String taskIntro=request.getParameter("taskIntro");
			 String credit=request.getParameter("credit");
			 //String taskNumber=request.getParameter("taskNumber");
			 String memberuser[]=request.getParameterValues("checkuser");
			 String[] taskNo=request.getParameterValues("taskNo");
			 String user=request.getParameter("createUser");
			 String emailtype=request.getParameter("emailtype");//邮件发送类型
			 if("".equals(taskName.trim())){
				 request.setAttribute("message","任务名称不能为空");
				 request.setAttribute("return", true);
				 return mapping.findForward("message");
			 }
			 if("".equals(taskIntro.trim())){
				 request.setAttribute("message","任务介绍不能为空");
				 request.setAttribute("return", true);
				 return mapping.findForward("message");
			 }
			 if(!Common.isNum(credit)){
				 request.setAttribute("message","任务积分必须为整数");
				 request.setAttribute("return", true);
				 return mapping.findForward("message");
			 }
			 //if("".equals(taskNumber)){
			 if(memberuser==null||memberuser.length==0){
				 request.setAttribute("message","会员不能为空");
				 request.setAttribute("return", true);
				 return mapping.findForward("message");
			 }
			 if(taskNo==null||taskNo.length==0){
				 request.setAttribute("message","没有添加要执行的任务，任务修改失败");
				 request.setAttribute("return", true);
				 return mapping.findForward("message");
			 } 
			 Scenetask stask=new Scenetask();
			 stask.setTaskId(Integer.parseInt(tid));
			 stask.setTaskIntro(taskIntro);
			 stask.setTaskName(taskName);
			 stask.setTaskTime(request.getParameter("taskTime"));
			 stask.setCreateUser(user);
			 stask.setCredit(Integer.parseInt(credit));
			 scenetaskService.saveOrUpdateTask(stask);
			 
			 //String[] tmb=taskNumber.split(",");
//			 String ids="";
			 List<String> lid=new ArrayList<String>();
			 for(String name:memberuser){
				 String haveUser=this.userExist(name,tid);//得到用户id	
				 //如果id=0。则表示在数据库中没有该用户，需要把他添加到数据中
				 if("".equals(haveUser)){
					 lid.add(name);
					 Scenetm stm=new Scenetm();
					 Short getcredit=0;
					 stm.setGetCredit(getcredit);
					 stm.setTaskId(Integer.parseInt(tid));
					 Short state=1;
					 stm.setTaskState(state);
					 stm.setUserName(name);
					 stm.setTaskTime("");
					 scenetmService.save(stm);						 
					 stm=null;
					 
					 for(int i=0;i<taskNo.length;i++){
						 String taskType=request.getParameter("types"+(i+1));
						 Scenect sct=new Scenect();
						 sct.setTaskId(Integer.parseInt(tid));
						 if(taskType.equals("1")){
							 String surveyName=request.getParameter("surveyName"+(i+1));
							 String surveyUrl=request.getParameter("surveyUrl"+(i+1));
							 sct.setSurveyName(surveyName);
							 sct.setSurveyUrl(surveyUrl);							 
						 }
						 if(taskType.equals("2")){
							 String tasksubid=request.getParameter("tasksubid"+(i+1));
							 sct.setObjId(Integer.parseInt(tasksubid));
						 }
						 sct.setTaskType(Short.parseShort(taskType+""));
						 sct.setTaskState(state);
						 sct.setUserName(name);
						 sct.setTaskNo(Integer.parseInt(taskNo[i]));
						 scenectService.saveOrUpdate(sct);
						 //判断是否是给新增人员发送邮件
						 if(i==taskNo.length-1&&"2".equals(emailtype)){
							 List<Members> meb=this.getUserEmailFromE(name);
							 if(meb!=null&&meb.size()>0){
								for(Members mebs:meb){
									this.sendEmail(request, mebs.getEmail(), mebs.getUsername(),taskName);
								}
							 }
						 }
					 }
				 }
				 else{
					 lid.add(haveUser);
				 }
				 //给全部人员发送邮件
				 if("1".equals(emailtype)){
					 List<Members> meb=this.getUserEmailFromE(name);
					 if(meb!=null&&meb.size()>0){
						for(Members mebs:meb){
							this.sendEmail(request, mebs.getEmail(), mebs.getUsername(),taskName);
						}
					 }
				 }
				 
			 }	 
			 if(lid!=null&&lid.size()>0){
				 String where="";
				 for(int i=0;i<lid.size();i++){
					 String lname=lid.get(i);
					 if(!"".equals(lname)){
						 if(i==0){
							 where=" userName <> '"+lname+"'";
						 }
						 else{
							 where+=" and userName <> '"+lname+"'";
						 }
					 }
				 }
				 String deletetm="DELETE FROM jrun_scenetm WHERE ( "+where+" ) and taskId="+tid;
				// String deletect="DELETE FROM jrun_scenect WHERE ( "+where+" ) and taskId="+tid;
				 String deletect="DELETE FROM vcity_subTask WHERE ( "+where+" ) and taskId="+tid;
				 dataBaseService.runQuery(deletetm,false);
				 dataBaseService.runQuery(deletect,false);
			 }
			 request.setAttribute("message", "任务编辑完成");
			 request.setAttribute("url_forward",
					"admincp.jsp?action=scene&option=doTask");
			 return mapping.findForward("message");
		}
	}
	
	/**
	 * 根据用户名和任务来得到用户完成任务的步骤
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward doGetOrder(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession();
		Members member=(Members)session.getAttribute("user");
		if(member==null){
			request.setAttribute("message","请登录后在执行该操作");
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		String taskId=request.getParameter("taskid");
		String username=request.getParameter("username");
		if("".equals(taskId)){
			request.setAttribute("message","请选择要查看的任务");
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
		if("".equals(username)){
			request.setAttribute("message","请选择要查看的会员");
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
	//	String sql="select sct.taskType,sct.surveyName,sct.surveyUrl,sct.objId from jrun_scenect sct inner join jrun_scenectorder scto on sct.id=scto.scenectId where sct.taskId="+taskId+" and scto.userName='"+username+"' and sct.taskState=2 order by scto.id";
		String sql="select sct.subTaskType,sct.subTaskDesc,sct.subTaskURL,sct.subTaskResoureceID " +
				" from vcity_subTask sct inner join jrun_scenectorder scto on sct.subTaskID=scto.scenectId " +
				"where sct.taskId="+taskId+" and scto.userName='"+username+"' and sct.subTaskState=2 order by scto.id";
		List<Map<String,String>> scto=dataBaseService.executeQuery(sql);
		List<Map<String,String>> lmap=null;
		if(scto!=null&&scto.size()>0){
			lmap=new ArrayList<Map<String,String>>();
			for(Map<String,String> sctomap:scto){
				Map<String,String> map=new HashMap<String,String>();
				map.put("taskType", sctomap.get("subTaskType"));
				if(sctomap.get("subTaskType").equals("2")){
					List<Scene> scenelist=sceneService.getSceneByCondition("from Scene s where s.objId="+sctomap.get("subTaskResoureceID"));
					String objName=scenelist.size()>0?scenelist.get(0).getObjName():"";
					map.put("objName",objName);
				}
				else{
					map.put("surveyName", "<a href='"+sctomap.get("subTaskURL")+"' target='_blank'>"+sctomap.get("subTaskDesc")+"</a>");
				}
				lmap.add(map);
				map=null;
			}
		}
		request.setAttribute("order",lmap);
		return mapping.findForward("toorder");
	}
	
	
	/**
	 * 判断输入的objid是否重复
	 * @param id
	 * @param objid
	 * @return
	 */
	public boolean checkobjid(int id, int objid) {
		String sql = "select id from jrun_scene where objId=" + objid;
		if (id != 0) {
			sql += " and id<>" + id;
		}
		List<Map<String, String>> checkobjid = dataBaseService
				.executeQuery(sql);
		return checkobjid != null && checkobjid.size() > 0 ? true : false;
	}
	
	/**
	 * 判断用户是否已经存在任务列表里
	 * @param username
	 * @return
	 */
	public String userExist(String username,String tid){
		List<Map<String,String>> lstm=dataBaseService.executeQuery("select userName from jrun_scenetm where userName='"+username+"' and taskId="+tid);
		return (lstm==null||lstm.size()==0)?"":lstm.get(0).get("userName");
	}
	
	
	/**
	 * 从e声网得到任务参与人员的信息集合
	 * @param username
	 * @return
	 */
	public List<Members> getUserEmailFromE(String username){
		Connection con=Common.getConnect();
		List<Members> useremail=null;
		if(con!=null){
			String sql="SELECT customer_account,password,email FROM customer where customer_account=";
			if(sql.indexOf(",")!=-1){
				username=username.lastIndexOf(",")==username.length()-1?username.substring(0,username.length()-1):username;
				String[] mem=username.split(",");
				for(int i=0;i<mem.length;i++){
					if(!"".equals(mem[i])){
						if(i==0){
							sql+="'"+mem[i]+"'";
						}
						else{
							sql+=" or customer_account='"+mem[i]+"'";
						}
					}
				}
			}
			else{
				sql+="'"+username+"'";
			}
			PreparedStatement pstmt=null;
			try{
				pstmt=con.prepareStatement(sql);
				ResultSet rs=pstmt.executeQuery();
				if(rs!=null){
					useremail=new ArrayList<Members>();
					while(rs.next()){
						Members member=new Members();
						member.setUsername(rs.getString("customer_account"));
						member.setPassword(rs.getString("password"));
						member.setEmail(rs.getString("email"));
						useremail.add(member);
						member=null;
					}
				}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
		return useremail;
	}
	
	/**
	 * 发送邮件
	 * @param request
	 * @param email
	 * @param username
	 * @param password
	 * @return
	 */
	public  boolean sendEmail(HttpServletRequest request,String email,String username,String taskname){
		Map<String,String> oldSettings=ForumInit.settings;
	    Map<String,String> mailparam=dataParse.characterParse(oldSettings.get("mail"), false);
		int timestamp = (Integer)(request.getAttribute("timestamp"));
		String timeoffset=(String)oldSettings.get("timeoffset");
		String date=Common.gmdate("yyyy-MM-dd HH:mm:ss", timestamp, timeoffset);;
		String host = mailparam.get("server");
		int port = Integer.parseInt(mailparam.get("port"));
		String auth = mailparam.get("auth");
		String auth_username = mailparam.get("auth_username");
		String auth_password = mailparam.get("auth_password");
		String test_from = mailparam.get("from");
		String htmlBody = InvitationTemplate.getEmailTemplate(request, username,taskname);
		Mail mail=new Mail(host, port, auth, auth_username, auth_password,"1");
		String alertmsg = mail.sendMessage(test_from, email, "《第一体验俱乐部》提醒：您有新的任务",null, htmlBody);
		if(alertmsg==null){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 得到用户任务的任务
	 * @param username
	 * @return
	 */
	public void getTaskState(HttpServletRequest request,String username,String state,int taskid,int curpage,int max){
		String hql="from Scenetm where taskId="+taskid;
		if(username!=null&&!"".equals(username)){
			hql+=" and userName like '%"+username+"%'";
			request.setAttribute("txtName", username);
		}
		if(state!=null&&!"0".equals(state)){
			hql+=" and taskState="+state;
			request.setAttribute("state",state);
		}
		else{
			request.setAttribute("state",0);
		}
		List<Map<String,Object>> Ulist=null;
		List<Scenetm> scenetm=scenetmService.getScenetmByCondition(hql, curpage, max);
		request.setAttribute("curpage",curpage);
		int count=scenetmService.getSecenetmCountByCondition(hql);
		request.setAttribute("total", count%10==0?count/10:count/10+1);
		request.setAttribute("count", count);
		
		String uname="";
		if(scenetm!=null&&scenetm.size()>0){
			Ulist=new ArrayList<Map<String,Object>>();
			Map<String,Object> map=null;
			for(Scenetm sct:scenetm){
				map=new HashMap<String,Object>();
			    uname=sct.getUserName();
				map.put("username", uname);
				map.put("taskstate",sct.getTaskState()+"");
				map.put("taskTime", sct.getTaskTime());
				Ulist.add(map);
			}
			request.setAttribute("taskid",taskid);
		}
		List<Map<String,String>> mapsct=null;
		List<Map<String,String>> lscenect=this.getTask(taskid);
		if(lscenect!=null&&lscenect.size()>0){
			mapsct=new ArrayList<Map<String,String>>();
			Map<String,String> mct=null;
			for(Map<String,String> scenect:lscenect){
				mct=new HashMap<String,String>();
				if(scenect.get("taskType").equals("1")){
					mct.put("surveyName", scenect.get("surveyName"));
					mct.put("surveyUrl", scenect.get("surveyUrl"));
				}
				else{
					List<Scene> lsce=sceneService.getSceneByCondition("from Scene s where s.objId="+scenect.get("objId"));
					mct.put("objName", lsce.size()>0?lsce.get(0).getObjName():"");
				}	
				mct.put("taskType", scenect.get("taskType"));
				mapsct.add(mct);
			}
			request.setAttribute("lscenect",mapsct);
		}
		request.setAttribute("Ulist", Ulist);
	}
	
	/**
	 * 得到任务
	 * @param tid
	 * @return
	 */
    public List<Map<String,String>> getTask(int tid){
    	List<Map<String,String>> lct=dataBaseService.executeQuery("select *, count(distinct userName) from jrun_scenect where taskId="+tid+" group by userName");
    	List<Map<String,String>> lctlist=null;
    	if(lct!=null&&lct.size()>0){
    		String username=lct.get(0).get("userName");
    	//	 lctlist=dataBaseService.executeQuery("select * from jrun_scenect where userName='"+username+"' and taskId="+tid+" order by taskNo");
    		lctlist=dataBaseService.executeQuery("select * from vcity_subTask where userName='"+username+"' and taskId="+tid+" order by subTaskIndex");
    	}
    	return lctlist;
    }
    
    /**
     * 删除任务
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	String tid=request.getParameter("tid");
    	if(tid==null||"".equals(tid)){
			request.setAttribute("message","未选中要删除的任务,请返回");
			request.setAttribute("return", true);
			return mapping.findForward("message");
		}
    	dataBaseService.runQuery("delete from jrun_scenetask where taskId="+tid, false);
    	dataBaseService.runQuery("delete from jrun_scenetm where taskId="+tid, false);
    //	dataBaseService.runQuery("delete from jrun_scenect where taskId="+tid, false);
    	dataBaseService.runQuery("delete from vcity_subTask where taskId="+tid, false);
    	request.setAttribute("message", "任务删除完成");
		 request.setAttribute("url_forward",
				"admincp.jsp?action=scene&option=doTask");
		 return mapping.findForward("message");
    	
    }
    
    /**
     * 得到用户
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doGetUser(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	 List<UmsUser> list=ConnectionDBE.getUmsUserList();
    	 String taskId=request.getParameter("taskId");
    	 if(taskId!=null&&!"".equals(taskId)){
    		 String sql="select userName from jrun_scenetm where taskId="+taskId;
    		 List<Map<String,String>> listmember=dataBaseService.executeQuery(sql);
    		 if(listmember!=null&&listmember.size()>0){
    			 String getMember="";
    			 for(Map<String,String> map:listmember){
    				 getMember+="".equals(getMember)?map.get("userName"):","+map.get("userName");
    			 }
    			 request.setAttribute("getMember",getMember);
    		 }
    	 }
    	 
    	 request.setAttribute("user", list);
    	return mapping.findForward("userlist");
    }
    
    /**
     * 场景图片上传
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward uploadImage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
    	File tempfile = isMakeDir();
		ModuleConfig ac =(ModuleConfig) request.getAttribute(Globals.MODULE_KEY);
		FileUploadUtil fileupload = new FileUploadUtil(tempfile,memeoryBlock,ac);
		try{
			fileupload.parse(request, JspRunConfig.CHARSET);
		}catch(IllegalStateException e){
			request.setAttribute("message", getMessage(request, "post_attachment_toobig"));
			return mapping.findForward("message");
		}		
		String objid=fileupload.getParameter("objid");
		request.setAttribute("filepic", fileupload.getParameter("filepic"));
		request.setAttribute("objid",objid);
		Map<String, String> settings = ForumInit.settings;
		String timeoffset = settings.get("timeoffset");
		String attachurl = settings.get("attachurl");
		int timestamp = (Integer) (request.getAttribute("timestamp"));
		FileItem fileItem = fileupload.getFileItem("image");
		if (fileItem != null && fileItem.getSize() > 0) {
			String filename = fileItem.getName();
			int start = filename.lastIndexOf('\\');
			filename = filename.substring(start + 1);
			int last = filename.lastIndexOf(".");
			String filetype = filename.substring(last + 1);
			filetype = filetype.toLowerCase();
			if (filetype.matches("jpg|jpeg|gif|png|bmp")) {
				String targetname = objid+ "." + filetype;
				String filedir = "month_"
						+ Common.gmdate("yyMM", timestamp, timeoffset);
				filedir = "scene";
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
				if (!FileUploadUtil.write2file(fileItem, new File(targetpath))) {
					
				}
				
				
//				if(!"".equals(objid)){//此时为新增节点
//					List<Map<String,String>> sceneimage=dataBaseService.executeQuery("select images from jrun_scene where objId="+objid);
//					System.out.print("select images from jrun_scene where objId="+objid);
//					if(sceneimage!=null&&sceneimage.size()>0){
//						File imagefile=new File(servlet.getServletContext().getRealPath("/")+sceneimage.get(0).get("images"));
//						if(imagefile.exists()){
//							imagefile.delete();
//						}
//					}
//				}
				request.setAttribute("url", attachurl + "/" + filedir + "/" + targetname);
			} 
		} 
		
		return mapping.findForward("uploadimage");
    }
    private File isMakeDir(){
		File file = new File(FILEPATHTIME);
		if(!file.exists()){
			file.mkdirs();
		}
		return file;
	}
}
