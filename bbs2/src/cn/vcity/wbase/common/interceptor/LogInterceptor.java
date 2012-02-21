package cn.vcity.wbase.common.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;

import cn.vcity.wbase.common.Constants;
/*import cn.vcity.wbase.web.action.SystemLogin;
import cn.vcity.wbase.web.action.OrgAction;
import cn.vcity.wbase.web.action.PostAction;
import cn.vcity.wbase.web.action.PowerAction;
import cn.vcity.wbase.web.action.ResourceAction;
import cn.vcity.wbase.web.action.RoleAction;
import cn.vcity.wbase.web.action.UploadAction;
import cn.vcity.wbase.web.action.UserAction;*/

/**
 * @Title: LogInterceptor.java
 * @Package cn.vcity.wbase.common.interceptor
 * @Description: TODO(用一句话描述该文件做什么)
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-4-4 上午10:28:50
 * @version V1.0
 */
public class LogInterceptor implements MethodInterceptor {

	protected Logger logger = Logger.getLogger(getClass());

	public Object invoke(MethodInvocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		Object[] object = invocation.getArguments();
		HttpServletRequest request = null ;
		ActionMapping mapping = null;
		Object returnObject = new Object();
		// 获取ACTION名称
		try {
			// 通过invocation.getArguments()可以获取代理对象的参数
			// 代理的参数是ActionMapping, ActionForm ,HttpServletRequest ,
			// HttpServletResponse 四个
			// 只不过，这里根据实际情况，我们只需要使用HttpServletRequest,ActionMapping罢了
			// 因为这里你要通过request获取session和通过mapping跳转页面
			// 因为 Object[] args = invocation.getArguments(); 规定,返回的必须是一个数组
			// 所以,没办法,只能迭代把要用的找出来
			// 而我们在XML配置的对象都是Struts Action
			for (int i = 0; i < object.length; i++) {
				if (object[i] instanceof HttpServletRequest)
					request = (HttpServletRequest) object[i];
				if (object[i] instanceof ActionMapping)
					mapping = (ActionMapping) object[i];
			}
			// 记录用户 create delete update 操作日志
			// System.out.println("操作的用户名为:" + user);
	        // System.out.println("操作者的IP为:" + ip);
	        // System.out.println("操作的时间为:"
	        // + new java.sql.Date(System.currentTimeMillis()));
	        // System.out.println("操作Action 的名称:" + actionName);
	        // System.out.println("操作的方法为:" + method);
	        // System.out.println("参数有:" + parameters);
	        // System.out.println("操作的方法结果为:" + result);
			HttpSession session = request.getSession();
			Object obj = session.getAttribute(Constants.LOGIN_USER);
			if (session == null || obj == null) {
				
			}
			//拦截到的ACTION
			Object invoObj = invocation.getThis().getClass();
			String actionName = invocation.getThis().getClass().getName();//操作Action 的名称
			Object op = request.getParameter("action");
			String method = op==null?"":op.toString();//操作的方法
			String ip = request.getRemoteAddr();//操作者的IP
			Date date = new Date(System.currentTimeMillis());//操作的时间
			logger.info(invoObj);
			logger.info(op);
			logger.info(method);
			logger.info(ip);
			logger.info(date);
			
			/*//需要日志记录的ACTION
			Object loginAction = SystemLogin.class;//登录日志
			Object orgAction = OrgAction.class;//组织管理日志
			Object postAction = PostAction.class;//岗位管理日志
			Object powerAction = PowerAction.class;//权限管理日志
			Object resourceAction = ResourceAction.class;//资源管理日志
			Object roleAction = RoleAction.class;//角色管理日志
			Object userAction = UserAction.class;//用户管理日志
			Object uploadAction = UploadAction.class;//上传日志
			logger.info(userAction);
			if(invoObj.equals(userAction)) {
				logger.info("相等");
			}*/
			
			
			returnObject = invocation.proceed();
			return returnObject;
		} catch (Throwable throwable) {
			return mapping.findForward("exit");
		}
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
			if (null != message) {
				out.println("alert('" + message + "');");
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
