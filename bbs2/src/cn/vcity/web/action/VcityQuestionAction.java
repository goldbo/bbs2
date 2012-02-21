package cn.vcity.web.action;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.vcity.model.VcityQuestion;
import cn.vcity.model.VcityQuestionitem;
import cn.vcity.model.VcitySurey;
import cn.vcity.service.VcityQuestionService;
import cn.vcity.service.VcitySureyService;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.web.form.VcityQuestionForm;

/**
 * @Title: VcityResTypeAction.java
 * @Package cn.vcity.vcity.web.action
 * @Description: 调查问卷的Action类
 * @author LHJ 珠海市网佳科技有限公司.
 * @date 2011-7-4 17:08:25
 * @version V1.0
 */
public class VcityQuestionAction extends BaseAction {
	
	private VcityQuestionService vcityQuestionService = (VcityQuestionService) SpringHelper
			.getBean("vcityQuestionService");
	private VcitySureyService vcitySureyService = (VcitySureyService) SpringHelper
			.getBean("vcitySureyService");


	private static VcityQuestionForm vcityQuestionForm = null;

	/**
	 * 新增调查问卷
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public ActionForward saveVcityQuestion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, InvocationTargetException {
		vcityQuestionForm = (VcityQuestionForm) form;
		VcityQuestion entity = new VcityQuestion();
		BeanUtils.copyProperties(entity, vcityQuestionForm);
		entity.addVcityQuestionitems(vcityQuestionForm.getItemMap());
		entity.setVcitySurey(vcitySureyService.findVcitySureyById(vcityQuestionForm.getSurveyId()));

		boolean flag = vcityQuestionService.saveVcityQuestion(entity);
//		msg = flag ? "新增成功!" : "新增失败!";
//		this.returnJsMsg(response, msg, "window.close();");
		return editVcityQuestion(mapping,form,request,response);
	}

	/**
	 * 跳转到新增或修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward editVcityQuestion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		vcityQuestionForm = (VcityQuestionForm) form;
		String forward = "create";
		VcitySurey vcitySurey = vcitySureyService.findVcitySureyById((Integer)(request.getSession().getAttribute("sureyId")));
		
		/*hibernate配置文件的ordey-by参数对问题set起效果，
		 * 对选项set不起效果？暂时解决方案是
		 * 写了一个按问题ID排序的比较器，
		 * 迭代查询出来的Set并添加每一项到新的Set里面去 */
		Set<VcityQuestion> questions = new TreeSet<VcityQuestion>(new   Comparator<Object>(){
	        public   int   compare(Object   o1,   Object   o2)   {
	        	VcityQuestion   u1   =   (VcityQuestion)o1;
	        	VcityQuestion   u2   =   (VcityQuestion)o2;
	            return  u1.getQuestIndex() - u2.getQuestIndex();
	        }
	    });
	
		
		Iterator iterator = vcitySurey.getVcityQuestion().iterator();
		while (iterator.hasNext()) {
			VcityQuestion question = (VcityQuestion) iterator.next();
			Iterator iter = question.getVcityQuestionitem().iterator();
			Set<VcityQuestionitem> items = new TreeSet<VcityQuestionitem>(new   Comparator<Object>(){
		        public   int   compare(Object   o1,   Object   o2)   {
		        	VcityQuestionitem   u1   =   (VcityQuestionitem)o1;
		        	VcityQuestionitem   u2   =   (VcityQuestionitem)o2;
		            return  u1.getItemIndex() - u2.getItemIndex();
		        }
		    });
			while (iter.hasNext()) {
				VcityQuestionitem item = (VcityQuestionitem) iter.next();
				items.add(item);
				
			}
			question.setVcityQuestionitem(items);
			questions.add(question);		
		}
		vcitySurey.setVcityQuestion(questions);   //添加处理后的问题set
		
		List templateQuestionList = vcityQuestionService.findAllTemplateVcityQuestion();
		request.setAttribute("vcitySurey", vcitySurey);
		request.setAttribute("templateQuesitonList", templateQuestionList);
		return mapping.findForward(forward);

	}

	/**
	 * 删除调查问卷
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deleteVcityQuestion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String msg = "删除成功!";
		String id = request.getParameter("id");
		Integer delId = null;
		if (id!=null&&!id.equals(""))  {
			delId = Integer.parseInt(id);
		}
		boolean flag = true;
		
		flag = vcityQuestionService.deleteVcityQuestionById(delId);
		msg = flag == true ? "删除成功！": "删除失败！";
		return editVcityQuestion(mapping,form,request,response);

	}

	/**
	 * 修改问卷
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public ActionForward updateVcityQuestion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, InvocationTargetException {
		
		String id = request.getParameter("id");
		vcityQuestionForm = (VcityQuestionForm) form;
		
		VcityQuestion vcityQuestion = (VcityQuestion)vcityQuestionForm.getQuestion(id);
		vcityQuestion.addVcityQuestionitems(vcityQuestion.getItemMap());

		vcityQuestion.setVcitySurey(vcitySureyService.findVcitySureyById((Integer)request.getSession().getAttribute("sureyId")));

		boolean flag = vcityQuestionService.updateVcityQuestion(vcityQuestion);
//		msg = flag ? "修改成功!" : "修改失败!";
//		this.returnJsMsg(response, msg, "window.close();");
		return editVcityQuestion(mapping,form,request,response);
	}
	
	/**
	 * 修改问卷顺序
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public ActionForward updateVcityQuestionIndex(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, InvocationTargetException {
		String id = request.getParameter("id");
		String upOrDown = request.getParameter("todo");
		vcityQuestionForm = (VcityQuestionForm) form;
		VcityQuestion vcityQuestion = (VcityQuestion)vcityQuestionForm.getQuestion(id);
		vcityQuestion.setVcitySurey(vcitySureyService.findVcitySureyById((Integer)request.getSession().getAttribute("sureyId")));
		boolean flag = vcityQuestionService.updateVcityQuestionIndex(vcityQuestion,upOrDown);
		return editVcityQuestion(mapping,form,request,response);
	}
	
	
	/**
	 * 设置为模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public ActionForward setQuestionTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, InvocationTargetException {
		String msg = "设置为模板成功!";
		String id = request.getParameter("id");
		Integer updateId = null;
		if (id!=null&&!id.equals(""))  {
			updateId = Integer.parseInt(id);
		}
		vcityQuestionForm = (VcityQuestionForm) form;
		boolean flag = true;
		
		flag = vcityQuestionService.setQuestionTemplate(updateId);
		msg = flag == true ? "设置为模板成功！": "设置为模板失败！";
		return editVcityQuestion(mapping,form,request,response);
	}
	
	
	/**
	 * 模板添加到问题
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public ActionForward addTemplateQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, InvocationTargetException {
		String id = request.getParameter("id");
		Integer addId = null;
		if (id!=null&&!id.equals(""))  {
			addId = Integer.parseInt(id);
		}
		VcityQuestion vcityQuestion = vcityQuestionService.findVcityQuestionById(addId);
		vcityQuestion.setIndex(vcityQuestion.getVcityQuestionitem().size());
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				// 配置你可能出现递归的属性
				if (name.equals("vcitySurey") || name.equals("itemMap") || name.equals("vcityQuestion")) {
					return true;
				} else {
					return false;
				}
			}
		});
		JSONObject obj = JSONObject.fromObject(vcityQuestion,config);
		String json = obj.toString();
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 问卷全部问题列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public ActionForward showVcityQuestionList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, InvocationTargetException {
		
		VcitySurey vcitySurey = vcitySureyService.findVcitySureyById((Integer)(request.getSession().getAttribute("sureyId")));
		request.setAttribute("vcitySurey", vcitySurey);
		return mapping.findForward("showList");
	}

}
