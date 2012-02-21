package cn.vcity.wbase.common.aop;
/**
 * <code>CheckTokenInterceptor.java</code>
 * Description:用于表单重复提交问题
 * @author 罗留庭 EN:Aly.Luo，开发部-珠海市网佳科技有限公司. Create-Time: 2007-10-11.
 * @version 1.0
 */
import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.struts.action.ActionMapping;

import cn.vcity.wbase.common.base.BaseAction;

/**
 * 
 * 用于表单重复提交问题,在所有以save开头的action方法检查，如果不相等返回错误页面?
 * @see org.aopalliance.intercept.MethodInterceptor
 *
 */

public class CheckTokenInterceptor implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] objs = invocation.getArguments();
		HttpServletRequest request = (HttpServletRequest) objs[2];

		BaseAction thisAction = (BaseAction) invocation.getThis();
		ActionMapping mapping = (ActionMapping) objs[0];
		if(thisAction.isTokenValid(request,true)){
			thisAction.resetToken(request);
			thisAction.saveToken(request);
			return invocation.proceed();
		}else{			
			return  mapping.findForward("reSubmit");
		}		
	}


}