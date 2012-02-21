package cn.vcity.wbase.common.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.aop.AfterReturningAdvice;

import cn.vcity.wbase.common.base.BaseAction;
/**
 * <code>AddTokenAdvice.java</code>
 * Description:用于表单重复提交问题.
 * @version 1.0
 */
public class AddTokenAdvice implements AfterReturningAdvice {
	
	/** 
	 * Description:用于表单重复提交问题,在所有以init,show开头的action方法中加入struts令牌
	 * @param Object returnObject 
	 * @param Method arg1 
	 * @param Object[] paramObjects 
	 * @param Object targetObject 
	 * @Throws  Throwable 
	 * @return null
	 */
	public void afterReturning(Object returnObject, Method arg1, Object[] paramObjects, Object targetObject) throws Throwable {
		HttpServletRequest request = (HttpServletRequest) paramObjects[2];
		BaseAction thisAction = (BaseAction) targetObject;
		thisAction.saveToken(request);
	}

}
