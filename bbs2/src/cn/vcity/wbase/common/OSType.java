package cn.vcity.wbase.common;

/**
 * 判断操作系统的类型，默认为true
 */

import java.net.URL;

public class OSType 
{
	public static boolean isLinux(){
		
		boolean isLinux = true; 
		URL resource = OSType.class.getResource("OSType.class"); 
		String classPath = resource.getPath(); 	
		String className = OSType.class.getName().replace(".", "/") + ".class"; 
		String classesPath = classPath.substring(0, classPath.indexOf(className)); 
		if( System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1 && 
				classesPath.startsWith("/") )
		{
			classesPath = classesPath.substring(1); 
			isLinux = false;
		}
		return isLinux;
	}
}
