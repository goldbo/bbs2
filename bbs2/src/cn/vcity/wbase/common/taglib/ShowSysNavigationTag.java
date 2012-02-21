package cn.vcity.wbase.common.taglib;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import cn.vcity.wbase.common.Constants;
import cn.vcity.wbase.common.SystemCache;
//import cn.vcity.wbase.model.WbaseSysNavigation;

/**
 * @Title: ShowSysNavigationTag.java
 * @Package cn.vcity.wbase.common.taglib
 * @Description: TODO(用一句话描述该文件做什么)
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-1-7 下午03:16:49
 * @version V1.0
 */
public class ShowSysNavigationTag extends BodyTagSupport {
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
		Object navObj = SystemCache.sysCache.get(Constants.WBASE_SYS_NAV);

		//buffer = showSysNavigation(request, navObj);

		try {
			// System.out.print(buffer.toString());
			out.println(buffer.toString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * 显示导航
	 * 
	 * @param navObj
	 * @return
	 */
	/*@SuppressWarnings("unchecked")
	private StringBuffer showSysNavigation(HttpServletRequest request,
			Object navObj) {
		StringBuffer contents = new StringBuffer();

		if (navObj != null) {
			List<WbaseSysNavigation> navList = (List<WbaseSysNavigation>) navObj;
			for (int i = 0; i < navList.size(); i++) {
				WbaseSysNavigation nav = navList.get(i);
				this.target = nav.getNavOpen()==1?"_blank":this.target;
				contents.append("<a target=\"" + this.target + "\" href=\""+nav.getNavLink()+"\" style=\"color: white\">\n");
				contents.append(nav.getNavName());
				contents.append("</a>\n");
			}
		}

		return contents;
	}*/
}
