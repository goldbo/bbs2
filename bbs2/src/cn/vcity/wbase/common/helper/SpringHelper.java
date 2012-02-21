package cn.vcity.wbase.common.helper;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

public  class SpringHelper {
	
	
	private static ServletContext servletContext = null;
	
	
	public static void bindSessionContext(ServletContext servletContext) {		
		if(SpringHelper.servletContext == null){
			SpringHelper.servletContext = servletContext;
		}
	}
	/**
	 * 从spring容器中得到Bean
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName){
//		System.out.println(servletContext);
		//System.out.println(servletContext+"---"+beanName);
		WebApplicationContext context = (WebApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return context.getBean(beanName);
	}
}
