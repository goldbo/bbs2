package cn.vcity.wbase.common;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <code>SystemCache.java</code> Description:系统缓存.
 * 
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class SystemCache {
	public static Map sysCache = Collections
			.synchronizedMap(new LinkedHashMap());

	public static Map orgCache = Collections
			.synchronizedMap(new LinkedHashMap());

	public static Map resCache = Collections
			.synchronizedMap(new LinkedHashMap());

	public static Map cutCache = Collections
			.synchronizedMap(new LinkedHashMap());

}