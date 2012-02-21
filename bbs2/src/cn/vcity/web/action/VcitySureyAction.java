package cn.vcity.web.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import cn.jsprun.domain.Members;
import cn.vcity.model.VcityQuestion;
import cn.vcity.model.VcityQuestionitem;
import cn.vcity.model.VcitySurey;
import cn.vcity.service.VcityQuestionService;
import cn.vcity.service.VcitySureyService;
import cn.vcity.wbase.common.Constants;
import cn.vcity.wbase.common.base.BaseAction;
import cn.vcity.wbase.common.helper.PagerHelper;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.wbase.common.vo.page.Pager;
import cn.vcity.web.form.VcitySureyForm;

/**
 * @Title: VcityResTypeAction.java
 * @Package cn.vcity.vcity.web.action
 * @Description: 调查问卷的Action类
 * @author LHJ 珠海市网佳科技有限公司.
 * @date 2011-7-4 17:08:25
 * @version V1.0
 */
public class VcitySureyAction extends BaseAction {
	
	private VcityQuestionService vcityQuestionService = (VcityQuestionService) SpringHelper
	.getBean("vcityQuestionService");
	private VcitySureyService vcitySureyService = (VcitySureyService) SpringHelper
	.getBean("vcitySureyService");

	private static VcitySureyForm vcitySureyForm = null;

	/**
	 * 新增调查问卷
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveVcitySurey(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String forward = "create";
		vcitySureyForm = (VcitySureyForm) form;
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		VcitySurey entity = new VcitySurey();
		String msg = "新增成功!";
		// String todo = request.getParameter("todo");
		entity = (VcitySurey) this.formToModel(vcitySureyForm,"cn.vcity.model.VcitySurey");
		if(request.getSession(false).getAttribute("sureyId") == null){
			entity.setSureyId(null);
		}
		entity.setCreateUser(uinfo.getUsername());
		boolean flag = vcitySureyService.saveVcitySurey(entity);
//		msg = flag ? "新增成功!" : "新增失败!";
//		this.returnJsMsg(response, msg, "window.close();");
		request.getSession().setAttribute("sureyId", entity.getSureyId());
		if(flag){
			VcitySurey vcitySurey = vcitySureyService.findVcitySureyAndQuestionById(entity.getSureyId());
			List templateQuestionList = vcityQuestionService.findAllTemplateVcityQuestion();
			
			Iterator temIterator = templateQuestionList.iterator();
			while (temIterator.hasNext()){
				VcityQuestion temQuestion = (VcityQuestion)temIterator.next();
				Iterator itemIterator = temQuestion.getVcityQuestionitem().iterator();
				while(itemIterator.hasNext()){
					VcityQuestionitem item = (VcityQuestionitem)itemIterator.next();
				}								
			}
			
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
		
			request.getSession().setAttribute("vcitySurey", vcitySurey);
			request.getSession().setAttribute("templateQuesitonList", templateQuestionList);
			forward = "createQuestion";
		}
		return mapping.findForward(forward);
	}

	/**
	 * 根据todo参数，跳转到新增或修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward editVcitySurey(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		vcitySureyForm = (VcitySureyForm) form;
		String todo = request.getParameter("todo");
		String forward = "";
		HttpSession session;

		if (TODO_CREATE.equals(todo)) {
			session = request.getSession(false);
			session.removeAttribute("sureyId");
			session.removeAttribute("vcitySurey");
			request.removeAttribute("vcitySurey");
			forward = "create";
		} else if (TODO_UPDATE.equals(todo)) {
			String id = request.getParameter("id");
			Integer updId = null;
			if (id!=null&&!id.equals(""))  {
				updId = Integer.parseInt(id);
			}
			request.getSession().setAttribute("sureyId", updId);
			VcitySurey vcitySurey = vcitySureyService.findVcitySureyById(updId);
			
			
			request.setAttribute("vcitySurey", vcitySurey);
			forward = "update";
		} else {
			log.error("createOrUpdatePost－TODO参数传递错误或为空！");
		}

		return mapping.findForward(forward);

	}

	/**
	 * 显示调查问卷分页数据列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward showVcitySureyList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		vcitySureyForm = (VcitySureyForm) form;
		
		List datas = null;
		int total = 0;// 总记录数
		Pager pager = null;// 分页信息
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");
		if (uinfo.getGroupid()==17) {		
			//vcitySureyForm.setCreateUser(uinfo.getUsername());
		}
		total = vcitySureyService.getTotalByOther(vcitySureyForm);
		
		try {
			pager = PagerHelper.getPager(request, total);
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		datas = vcitySureyService.getVcitySureyListByOther(
					vcitySureyForm, pager.getPageSize(), pager.getStartRow());
		
		
		String forward = "showList";
		
		//这是创建任务时候获取问卷列表接口－开始
		String flag = request.getParameter("flag");
		if (flag!=null) {
			Integer manual = 0;
			if (request.getParameter("manual")!=null) {
				manual = 1;
			}
			request.setAttribute("manual", manual);
			request.setAttribute("flag",flag);
			forward = "chooseList";
		}
		//－结束
		
		request.setAttribute("datas", datas);
		request.setAttribute("pager", pager);
		request.setAttribute("vcitySureyForm", vcitySureyForm);

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
	public ActionForward deleteVcitySurey(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String id = request.getParameter("id");
		Integer delId = null;
		if (id!=null&&!id.equals("")&& !"undefined".equals(id))  {
			delId = Integer.parseInt(id);
		}
			
	
		vcitySureyForm = (VcitySureyForm) form;
		boolean flag = true;
		
//		根据传入ID判断是只删除当前行数据，还是批量删除选中的数据
		if (id != null&&!id.equals("")&& !"undefined".equals(id)) {
			flag = vcitySureyService.deleteVcitySureyById(delId);
		} else {
			flag = vcitySureyService.deleteVcitySureyByIds(vcitySureyForm.getSelectedRow());
		}
//		request.setAttribute(Constants.ALERT_MESSAGE, flag == true ? "删除成功！"
//				: "删除失败！");
		vcitySureyForm = new VcitySureyForm();
		request.setAttribute("VcitySureyForm", vcitySureyForm);

		return showVcitySureyList(mapping, form, request, response);

	}

	/**
	 * 修改资源分类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */

	public ActionForward updateVcitySurey(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IllegalAccessException, InvocationTargetException {

		vcitySureyForm = (VcitySureyForm) form;
		String msg = "修改成功!";
		boolean flag = true;
		VcitySurey entity = new VcitySurey();
		BeanUtils.copyProperties(entity, vcitySureyForm);
		flag = vcitySureyService.updateVcitySurey(entity);

		msg = flag ? "修改成功!" : "修改失败!";
		this.returnJsMsg(response, msg, "window.close();");

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
		String id = request.getParameter("id");
		Integer sureyId = null;
		if (id!=null&&!id.equals(""))  {
			sureyId = Integer.parseInt(id);
		}
		VcitySurey vcitySurey = vcitySureyService.findVcitySureyById(sureyId);
		request.setAttribute("vcitySurey", vcitySurey);
		request.setAttribute("show", "3");
		return mapping.findForward("showQuestionList");
	}

	/**
	 * 新建任务时调查预览
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward previewSurvey(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		String id = request.getParameter("id");
		Integer updId = null;
		if (id!=null&&!id.equals(""))  {
			updId = Integer.parseInt(id);
		}
		VcitySurey vcitySurey = vcitySureyService.findVcitySureyById(updId);
		request.setAttribute("vcitySurey", vcitySurey);
		
		return mapping.findForward("preview");
	}
}
