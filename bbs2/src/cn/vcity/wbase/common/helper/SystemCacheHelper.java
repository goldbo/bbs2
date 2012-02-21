package cn.vcity.wbase.common.helper;

import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.vcity.wbase.common.Constants;
import cn.vcity.wbase.common.SystemCache;
/*import cn.vcity.wbase.model.WbaseOrg;
import cn.vcity.wbase.model.WbaseResource;
import cn.vcity.wbase.model.WbaseSysCompany;
import cn.vcity.wbase.model.WbaseSysMessages;
import cn.vcity.wbase.model.WbaseSysNavigation;
import cn.vcity.wbase.model.WbaseSysShortcut;
import cn.vcity.wbase.service.WbaseOrgService;
import cn.vcity.wbase.service.WbaseResourceService;
import cn.vcity.wbase.service.WbaseSysCompanyService;
import cn.vcity.wbase.service.WbaseSysMessagesService;
import cn.vcity.wbase.service.WbaseSysNavigationService;
import cn.vcity.wbase.service.WbaseSysShortcutService;
*/
/**
 * @Title: SystemCacheHelper.java
 * @Package cn.vcity.wbase.common.plugin
 * @Description: 更新数据字典缓存
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-4-20 上午10:39:36
 * @version V1.0
 */
public class SystemCacheHelper {
	/**
	 * 更新组织缓存
	 * 
	 * @param dictCode
	 */
	@SuppressWarnings("unchecked")
	/*public static void updateOrgCache() {
		WbaseOrgService wbaseOrgService = (WbaseOrgService) SpringHelper
				.getBean("wbaseOrgService");
		Map orgCache = Collections.synchronizedMap(new LinkedHashMap());
		List<WbaseOrg> orgList = wbaseOrgService.getOrgList();
		if (orgList.isEmpty()) {// 无数据
			// 初始化组织对象顶层节点
			WbaseOrg org = new WbaseOrg();
			org.setNodeNo(Long.valueOf(0));
			org.setOrgNo("organization");
			org.setOrgName("珠海市网佳科技有限公司");
			org.setSubNodeNo(Long.valueOf(-1));
			org.setOrgLevel(1);
			org.setIsFlag(1);
			org.setViewOrder(0);
			org.setCreateDate(new Date());
			wbaseOrgService.createOrg(org);
		} else {
			for (int max = orgList.size(), i = 0; i < max; i++) {
				WbaseOrg org = (WbaseOrg) orgList.get(i);
				orgCache.put(org.getNodeNo(), org);
			}
		}
		SystemCache.orgCache = orgCache;

	}*/

	/**
	 * 集合删除组织缓存
	 * 
	 * @param orgNo[]
	 */
	public static void deleteOrgCache(Long nodeNo[]) {
		for (int i = 0; i < nodeNo.length; i++) {
			SystemCache.orgCache.remove(nodeNo[i]);// 清除
		}
	}

	/**
	 * 删除组织缓存
	 * 
	 * @param orgNo
	 */
	public static void deleteOrgCache(Long nodeNo) {
		SystemCache.orgCache.remove(nodeNo);// 清除
	}

	/**
	 * 更新资源缓存
	 * 
	 * @param dictCode
	 */
	@SuppressWarnings("unchecked")
	/*public static void updateResCache() {
		WbaseResourceService wbaseResourceService = (WbaseResourceService) SpringHelper
				.getBean("wbaseResourceService");
		// 系统菜单缓存
		List<WbaseResource> resList = wbaseResourceService.getResourceList();
		Map resCache = Collections.synchronizedMap(new LinkedHashMap());
		if (resList.isEmpty()) {// 无数据
			WbaseResource res = new WbaseResource();
			res.setNodeNo(Long.valueOf(0));
			res.setResName("系统资源");
			res.setResNo("system_res");
			res.setSubNodeNo(Long.valueOf(-1));
			res.setResLevel(1);
			res.setIsFlag(1);
			res.setIsTarget(1);
			res.setViewOrder(0);
			wbaseResourceService.createResource(res);
		}
		for (int max = resList.size(), i = 0; i < max; i++) {
			WbaseResource res = (WbaseResource) resList.get(i);
			resCache.put(res.getNodeNo(), res);
		}
		SystemCache.resCache = resCache;

	}*/

	/**
	 * 集合删除资源缓存
	 * 
	 * @param ResNo[]
	 */
	public static void deleteResCache(Long nodeNo[]) {
		for (int i = 0; i < nodeNo.length; i++) {
			SystemCache.resCache.remove(nodeNo[i]);// 清除
		}
	}

	/**
	 * 删除资源缓存
	 * 
	 * @param ResNo
	 */
	public static void deleteResCache(Long nodeNo) {
		SystemCache.resCache.remove(nodeNo);// 清除
	}

	/**
	 * 更新快捷菜单缓存
	 * 
	 * @param dictCode
	 */
	@SuppressWarnings("unchecked")
	/*public static void updateCutCache() {
		WbaseSysShortcutService wbaseSysShortcutService = (WbaseSysShortcutService) SpringHelper
				.getBean("wbaseSysShortcutService");
		// 快捷菜单缓存
		List<WbaseSysShortcut> cutList = wbaseSysShortcutService
				.getSysShortcut();
		Map cutCache = Collections.synchronizedMap(new LinkedHashMap());
		for (int max = cutList.size(), i = 0; i < max; i++) {
			WbaseSysShortcut cut = (WbaseSysShortcut) cutList.get(i);
			cutCache.put(cut.getId(), cut);
		}
		SystemCache.cutCache = cutCache;

	}*/

	/**
	 * 集合删除快捷菜单缓存
	 * 
	 * @param ids[]
	 */
	public static void deletecutCache(String ids[]) {
		for (int i = 0; i < ids.length; i++) {
			SystemCache.cutCache.remove(ids[i]);// 清除
		}
	}

	/**
	 * 删除快捷菜单缓存
	 * 
	 * @param id
	 */
	/*public static void deletecutCacheByNodeNo(Long nodeNo) {
		WbaseSysShortcutService wbaseSysShortcutService = (WbaseSysShortcutService) SpringHelper
				.getBean("wbaseSysShortcutService");
		wbaseSysShortcutService.deleteCutByNodeNo(nodeNo);
		Map cutCache = SystemCache.cutCache;
		for (Iterator iter = cutCache.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			// Object key = entry.getKey();
			Object val = entry.getValue();
			WbaseSysShortcut info = (WbaseSysShortcut) val;
			if (nodeNo.equals(info.getCutNodeNo())) {
				SystemCache.cutCache.remove(info.getId());
			}
		}

	}*/

	/**
	 * 删除快捷菜单缓存
	 * 
	 * @param id
	 */
	public static void deletecutCache(String id) {
		SystemCache.cutCache.remove(id);// 清除
	}

	/**
	 * 更新企业信息缓存
	 * 
	 * @param dictCode
	 */
	//@SuppressWarnings("unchecked")
	/*public static void updateSysCompanyCache() {
		WbaseSysCompanyService wbaseSysCompanyService = (WbaseSysCompanyService) SpringHelper
				.getBean("wbaseSysCompanyService");
		// 快捷菜单缓存
		WbaseSysCompany com = wbaseSysCompanyService.getSysCompany();
		Map sysCache = SystemCache.sysCache;
		sysCache.put(Constants.WBASE_SYS_COM, com);
		SystemCache.sysCache = sysCache;
	}
*/
	/**
	 * 更新系统消息缓存
	 * 
	 * @param dictCode
	 */
	//@SuppressWarnings("unchecked")
	/*public static void updateSysMessagesCache() {
		// TODO 初始化 系统消息
		Map sysCache = SystemCache.sysCache;
		WbaseSysMessagesService wbaseSysMessagesService = (WbaseSysMessagesService) SpringHelper
				.getBean("wbaseSysMessagesService");
		List<WbaseSysMessages> msgList = wbaseSysMessagesService
				.getSysMessagesList();
		sysCache.put(Constants.WBASE_SYS_MSG, msgList);

	}*/
	
	/**
	 * 更新系统底部导航缓存
	 * 
	 * @param dictCode
	 */
//	@SuppressWarnings("unchecked")
	/*public static void updateSysNavigationCache() {
		// TODO 初始化 系统消息
		Map sysCache = SystemCache.sysCache;
		WbaseSysNavigationService wbaseSysNavigationService = (WbaseSysNavigationService) SpringHelper
				.getBean("wbaseSysNavigationService");
		List<WbaseSysNavigation> msgList = wbaseSysNavigationService
				.getSysNavigationList();
		sysCache.put(Constants.WBASE_SYS_NAV, msgList);

	}*/
}
