package cn.vcity.wbase.common.taglib;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import cn.vcity.wbase.common.Constants;
import cn.vcity.wbase.common.SystemCache;
import cn.vcity.wbase.common.helper.PowerHelper;
//import cn.vcity.wbase.model.ViewUserInfo;
//import cn.vcity.wbase.model.WbaseResource;
//import cn.vcity.wbase.model.WbaseSysShortcut;

/**
 * @Title: ShowShortCutTag.java
 * @Package cn.vcity.wbase.common.taglib
 * @Description: TODO(用一句话描述该文件做什么)
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-1-7 下午03:16:49
 * @version V1.0
 */
public class ShowShortCutTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8338402559760438525L;

	private String type;// horizontal:横向菜单 vertical：纵向菜单

	private String target;// Target for URL

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int doStartTag() {

		return EVAL_BODY_INCLUDE;
	}

	@SuppressWarnings("unchecked")
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		StringBuffer buffer = new StringBuffer();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		// 权限MAP
		HttpSession session = request.getSession();
		Map powerMap = (Map) session.getAttribute(Constants.ROLE_POWER);
		Map cutCache = SystemCache.cutCache;
		Map resCache = SystemCache.resCache;

		/*ViewUserInfo user = (ViewUserInfo) session
				.getAttribute(Constants.LOGIN_USER);

		Map userMap = Collections.synchronizedMap(new LinkedHashMap());
		Map defaultMap = Collections.synchronizedMap(new LinkedHashMap());

		for (Iterator iter = cutCache.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			WbaseSysShortcut cut = (WbaseSysShortcut) val;
			if (user.getAccount().equals(cut.getCutUid())) {
				userMap.put(key, val);
			}
			if (Constants.WBASE_SYS_ADMIN.equals(cut.getCutUid())) {
				defaultMap.put(key, val);
			}
		}

		buffer = showHorizontalMenu(request,
				userMap.isEmpty() == true ? defaultMap : userMap, resCache,
				powerMap);*/

		try {
			// System.out.print(buffer.toString());
			out.println(buffer.toString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * 显示横向菜单
	 * 
	 * @param resList
	 * @return
	 */
	/*private StringBuffer showHorizontalMenu(HttpServletRequest request,
			Map cutMap, Map resMap, Map powerMap) {
		StringBuffer contents = new StringBuffer();
		String path = request.getContextPath();

		for (Iterator iter = cutMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			// Object key = entry.getKey();
			Object val = entry.getValue();
			WbaseSysShortcut info = (WbaseSysShortcut) val;
			if (!resMap.isEmpty() && !powerMap.isEmpty()) {
				// 检查权限
				Object resObj = resMap.get(info.getCutNodeNo());
				if (resObj != null) {
					WbaseResource res = (WbaseResource) resObj;
					Object resNoObj = powerMap.get(res.getResNo());
					if (resNoObj != null) {
						Integer powerLevel = Integer.parseInt(resNoObj
								.toString());
						if (res.getIsTarget() == 1) {
							this.target = "_blank";
						}
						if (PowerHelper.checkPower(powerLevel, 5)) {
							contents.append("<li>\n");
							contents.append("<a target=\"" + this.target
									+ "\" href=\"" + path + res.getResPath()
									+ "\">\n");
							contents
									.append("<img border=\"0\" width=\"11px\"	height=\"13px\" src=\""
											+ path
											+ res.getResPic()
											+ "\" onload=\"javascript:DrawImage(this,11,13);\"/>\n");
							contents.append(res.getResName());
							contents.append("</a>\n");
							contents.append("</li>\n");
							contents.append("<li class=\"Line\"></li>\n");
						}
					}
				}
			}
		}

		return contents;
	}*/

}
