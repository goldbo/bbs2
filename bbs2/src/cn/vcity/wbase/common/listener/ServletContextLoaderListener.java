package cn.vcity.wbase.common.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import cn.vcity.wbase.common.helper.SpringHelper;
/**
 * 
 * Appliation 侦听器。 <br/>
 * 作用：<br>
 * 把web应用上下文保存到SpringHelper静态变量中。
 * @author 罗留庭 EN:Aly.Luo，开发部-珠海市网佳科技有限公司. Create-Time: 2007-9-8.
 * @version 1.0 
 */
public class ServletContextLoaderListener implements ServletContextListener{
	

	Logger logger = Logger.getLogger(ServletContextLoaderListener.class);

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		
		ServletContext servletContext = servletContextEvent.getServletContext();
		SpringHelper.bindSessionContext(servletContext);
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		
	}

}
