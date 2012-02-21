package cn.vcity.wbase.common.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import cn.vcity.wbase.common.conveter.DateConvert;
import cn.vcity.wbase.common.helper.ObjectHelper;

/**
 * 
 * @author samhsieh
 * 
 */

public class BaseAction extends DispatchAction {
	protected Logger logger = Logger.getLogger(getClass());
	
	protected static String TODO_READ = "read";

	protected static String TODO_CREATE = "create";

	protected static String TODO_UPDATE = "update";

	protected static String TODO_DELETE = "delete";
	
	static {
		ConvertUtils.register(new DateConvert(), java.util.Date.class);
	}

	/**
	 * 执行指定的方法
	 * 
	 * @param mapping
	 *            匹配对象
	 * @param form
	 *            表单数据对象
	 * @param request
	 *            页面请求对象
	 * @param response
	 *            响应对象
	 * @return 重定向对象
	 * 
	 * @throws java.lang.Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws java.lang.Exception {
		ActionForward forward = null;
		try {
			forward = beforeExecute(mapping, form, request, response);
			/*
			 * if (forward != null) { return forward; }
			 */
			forward = super.execute(mapping, form, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}

	/**
	 * 在执行execute之前的动作，此方法必须被重载
	 * 
	 * @param mapping
	 *            匹配对象
	 * @param form
	 *            表单数据对象
	 * @param request
	 *            页面请求对象
	 * @param response
	 *            响应对象
	 * @return 重定向对象
	 * 
	 * @throws java.lang.Exception
	 */
	protected ActionForward beforeExecute(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws java.lang.Exception {

//		try {
//			Object todo = request.getParameter("todo");
//			if(todo!=null){
//				System.out.println("action-todo="+todo.toString());
//			}
//			
//		} catch (Exception e) {
//		}
//		
//		ActionForward forward = null;
//		HttpSession session = request.getSession();
//		WbaseUser user = (WbaseUser) session.getAttribute("loginUser");
////		 判断session是否为空
//		 if (user == null){
//			 return mapping.findForward("noSession");
//		 }

		return null;
	}

	protected void reportErrors(HttpServletRequest request, String message,
			boolean resource) {
		if (message == null)
			return;

		ActionMessages ams = new ActionMessages();
		ams.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message,
				resource));

		saveErrors(request, ams);
	}

	/**
	 * 用户对象之间的转换，主要是用于form到model的转换
	 * 
	 * @param srcObject
	 *            actionFrom 对象
	 * @param targetClassName
	 *            model的全类名
	 * @version 1.0
	 * @see cn.vcity.wbase.common.helper.ObjectHelper
	 * @return Object 如:<br>
	 *         DictionaryTypeForm dictionaryTypeForm = (DictionaryTypeForm)
	 *         form; SysDictionaryType dictionaryTypePo = (SysDictionaryType)
	 *         this.formToModel( dictionaryTypeForm,
	 *         "cn.vcity.wbase.model.SysDictionaryType"); <br>
	 *         注意问题： actiofrom 中的属性名与model中的要一致，包括属性的数据类型
	 */
	protected Object formToModel(Object srcObject, String targetClassName) {
		return ObjectHelper.objectCopy(srcObject, targetClassName);
	}

	/**
	 * 用户对象之间的转换，主要是用于model到form的转换
	 * 
	 * @param srcObject
	 *            model 对象
	 * @param targetClassName
	 *            actionForm的全类名
	 * @version 1.0
	 * @see cn.vcity.wbase.common.helper.ObjectHelper
	 * @return Object 如:<br>
	 *         DictionaryTypeForm dictionaryTypeForm = (DictionaryTypeForm)
	 *         form; SysDictionaryType dictionaryTypePo = service_DictionaryType
	 *         .getSysDictionaryTypeById(dictionary_type_id); dictionaryTypeForm =
	 *         (DictionaryTypeForm) this.modelToForm(
	 *         dictionaryTypePo,"cn.vcity.wbase.web.form.DictionaryTypeForm");
	 *         <font color='red'> request.setAttribute("DictionaryTypeForm",
	 *         dictionaryTypeForm);</font> <br>
	 *         注意问题： （1）：actiofrom 中的属性名与model中的要一致，包括属性的数据类型<br>
	 *         （2）：因为在转换过程中actionForm类的引用以改变(转换过程生成了一个新的对象)。 故要重新设置。
	 */
	protected Object modelToForm(Object srcObject, String targetClassName) {
		return ObjectHelper.objectCopy(srcObject, targetClassName);
	}

	/**
	 * @param HttpServletRequest
	 *            request 对象 利用struts本身的机制，设置令牌 现以没有使用。
	 */
	public void saveToken(HttpServletRequest request) {
		super.saveToken(request);
	}

	/**
	 * @param HttpServletRequest
	 *            request 对象 利用struts本身的机制，重新设置令牌 现以没有使用。
	 */
	public void resetToken(HttpServletRequest request) {
		super.resetToken(request);
	}

	/**
	 * @param HttpServletRequest
	 *            request 对象 利用struts本身的机制，检查令牌是否有效 现以没有使用。
	 */
	public boolean isTokenValid(HttpServletRequest request, boolean reset) {
		return super.isTokenValid(request, reset);
	}

	/**
	 * 验证指定名字的token在request和session中是否一致
	 * 
	 * @param HttpServletRequest
	 *            request 对象
	 * @param reset
	 *            是否重置验证过的token
	 * @param tokenName
	 *            要验证的tokenName
	 * @author zmw zhmu11@126.com
	 */
	//@Override
	protected boolean isTokenValid(HttpServletRequest request, boolean reset,
			String tokenName) {
		return super.isTokenValid(request, reset, tokenName);
	}

	/**
	 * 重置指定名字的token
	 * 
	 * @param HttpServletRequest
	 *            request 对象
	 * @param tokenName
	 *            用户自定义的tokenName
	 * @author zmw zhmu11@126.com
	 */
	//@Override
	protected void resetToken(HttpServletRequest request, String tokenName) {
		super.resetToken(request, tokenName);
	}

	/**
	 * 使用自定义的tokenName,设置token
	 * 
	 * @param HttpServletRequest
	 *            request 对象
	 * @param tokenName
	 *            用户自定义的tokenName
	 * @author zmw zhmu11@126.com
	 */
	//@Override
	protected void saveToken(HttpServletRequest request, String tokenName) {
		super.saveToken(request, tokenName);
	}

	/**
	 * 显示系统返回信息
	 * 
	 * @param response
	 * @param message：显示信息
	 * @param extJs:扩展JS代码
	 */
	public void returnJsMsg(HttpServletResponse response, String message,
			String extJs) {
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
		// 以上2句代码一定要写在获取out对象之前，否则提示信息为乱码
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println("<script language='JavaScript'>");
			if(null!=message){
				out.println("alert('"+message+"');");
			}
			out.println(extJs);
			out.println("</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
