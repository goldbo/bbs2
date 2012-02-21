package cn.vcity.wbase.common.plugin;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import cn.vcity.wbase.common.SystemCache;
import cn.vcity.wbase.common.helper.SpringHelper;
/*import cn.vcity.wbase.model.WbaseResource;
import cn.vcity.wbase.model.WbaseSysShortcut;
import cn.vcity.wbase.service.WbaseResourceService;
import cn.vcity.wbase.service.WbaseSysShortcutService;*/

/**
 * @Title: ResInitLoadPlugIn.java
 * @Package cn.vcity.wbase.common.plugin
 * @Description: 资源树初始缓存
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-4-20 上午10:39:36
 * @version V1.0
 */
public class ResInitLoadPlugIn implements PlugIn {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public void init(ActionServlet servlet, ModuleConfig config)
			throws ServletException {
		// TODO Auto-generated method stub
		/*WbaseResourceService wbaseResourceService = (WbaseResourceService) SpringHelper
				.getBean("wbaseResourceService");
		WbaseSysShortcutService wbaseSysShortcutService = (WbaseSysShortcutService) SpringHelper
				.getBean("wbaseSysShortcutService");
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

		// 快捷菜单缓存
		List<WbaseSysShortcut> cutList = wbaseSysShortcutService
				.getSysShortcut();
		Map cutCache = Collections.synchronizedMap(new LinkedHashMap());
		for (int max = cutList.size(), i = 0; i < max; i++) {
			WbaseSysShortcut cut = (WbaseSysShortcut) cutList.get(i);
			cutCache.put(cut.getCutNodeNo(), cut);//以KEY为资源节点编号存储
		}
		SystemCache.cutCache = cutCache;*/

	}

}
