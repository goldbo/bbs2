package cn.vcity.wbase.common.helper;


import javax.servlet.http.HttpServletRequest;

public class WebHelper {
	
	/**
	 * 在web环境中根据属性名来取对象
	 * @param request
	 * @param scope 对象所在的范围(request,session,application,page)
	 * @param key 属性名
	 * @return Object
	 */
	public static Object getObjectByWebContainer(HttpServletRequest request,String scope,String key)
	{
		 Object returnValue= new Object();
		 if(scope==null) scope="";
		 if(scope.equals("request"))
		 {
			 returnValue=request.getAttribute(key);
			 return returnValue;
		 }
		 else if(scope.equals("session"))
		 {
			 returnValue=request.getSession().getAttribute(key);
			 return returnValue;
		 }
		 else if(scope.equals("application"))
		 {
			 returnValue=request.getSession().getServletContext().getAttribute(key);
			 return returnValue;
		 }
		 else if(scope.length()==0)
		 {
			 returnValue=request.getAttribute(key);
			 	if(returnValue!=null) return returnValue;
			 returnValue=request.getSession().getAttribute(key);
			 	if(returnValue!=null) return returnValue;
			 returnValue=request.getSession().getServletContext().getAttribute(key);
			 	if(returnValue!=null) return returnValue;
		 }
		return null;
	}
	
	
}




