package cn.vcity.wbase.common.plugin;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import cn.vcity.wbase.common.SystemCache;
import cn.vcity.wbase.common.helper.SpringHelper;
//import cn.vcity.wbase.model.WbaseOrg;
//import cn.vcity.wbase.service.WbaseOrgService;

/**
 * @Title: OrgInitLoadPlugIn.java
 * @Package cn.vcity.wbase.common.plugin
 * @Description: 组织架构树初始缓存
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-4-20 上午10:39:36
 * @version V1.0
 */
public class OrgInitLoadPlugIn implements PlugIn {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public void init(ActionServlet servlet, ModuleConfig config)
			throws ServletException {
		// TODO Auto-generated method stub
		/*WbaseOrgService wbaseOrgService = (WbaseOrgService) SpringHelper
				.getBean("wbaseOrgService");
		Map orgCache = Collections.synchronizedMap(new LinkedHashMap());
		List<WbaseOrg> orgList = wbaseOrgService.getOrgList();
		if (orgList.isEmpty()) {//无数据
			//初始化组织对象顶层节点
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
		SystemCache.orgCache = orgCache;*/
	}

}
