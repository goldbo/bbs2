package cn.vcity.wbase.common.taglib;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import cn.vcity.wbase.common.Constants;
import cn.vcity.wbase.common.SystemCache;
import cn.vcity.wbase.common.helper.PowerHelper;
//import cn.vcity.wbase.model.WbaseResource;

/**
 * @Title: ShowMenuTag.java
 * @Package cn.vcity.wbase.common.taglib
 * @Description: TODO(用一句话描述该文件做什么)
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-1-7 下午03:16:49
 * @version V1.0
 */
public class ShowMenuTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8338402559760438525L;

	private String type;// horizontal:横向菜单 vertical：纵向菜单

	private String target;// Target for URL

	private boolean viewChild;

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

	public boolean isViewChild() {
		return viewChild;
	}

	public void setViewChild(boolean viewChild) {
		this.viewChild = viewChild;
	}

	public int doStartTag() {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		StringBuffer buffer = new StringBuffer();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		// 权限MAP
		HttpSession session = request.getSession();
		Map powerMap = (Map) session.getAttribute(Constants.ROLE_POWER);
		Map resCache = SystemCache.resCache;

		if (this.viewChild) {// 显示子菜单，未完成
			buffer = ("horizontal".equals(this.type)) ? showHorizontalMenu(
					request, resCache, powerMap) : showVerticalMenu(request,
					resCache, powerMap);
		} else {// 只显示1级菜单
			buffer = ("horizontal".equals(this.type)) ? showHorizontalMenu(
					request, resCache, powerMap) : showVerticalMenu(request,
					resCache, powerMap);
		}

		try {
			// System.out.print(buffer.toString());
			out.println(buffer.toString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * 显示纵向菜单
	 * 
	 * @param resList
	 * @return
	 */
	private StringBuffer showVerticalMenu(HttpServletRequest request, Map map,
			Map powerMap) {
		StringBuffer contents = new StringBuffer();
		String path = request.getContextPath();
		contents.append("<div class=\"vnavbox\">\n");
		contents
				.append("<table align='center' width='100%' border='0' cellspacing='5' cellpadding='0'>\n");
		int k = 1;
		/*
		for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			// Object key = entry.getKey();
			Object val = entry.getValue();
			/*WbaseResource info = (WbaseResource) val;
			if (info.getNodeNo() != 0 && info.getSubNodeNo() == 0) {
				// 检查权限
				Object resNoObj = powerMap.get(info.getResNo());
				if (resNoObj != null) {
					Integer powerLevel = Integer.parseInt(resNoObj.toString());
					if (PowerHelper.checkPower(powerLevel, 5)) {
						contents.append("<tr>\n");
						contents.append("<td>\n");
						contents
								.append("<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0'  class='menu_btn_off' onClick=\"menu_OC("
										+ k
										+ ",'"
										+ info.getResName()
										+ "','"
										+ path
										+ info.getResPath()
										+ "',"
										+ info.getIsTarget()
										+ ")\" id='MTID_"
										+ k + "'>\n");
						contents.append("<tr>\n");
						contents
								.append("<td width='60' align='center'><img src='"
										+ path
										+ info.getResPic()
										+ "' width='41px' height='29px' id='MPID_"
										+ k + "' onload=\"javascript:DrawImage(this,41,29);\"></td>\n");
						contents.append("<td>" + info.getResName() + "</td>\n");
						contents.append("</tr>\n");
						contents.append("</table></td>\n");
						contents.append("</tr>\n");
						k++;
					}
				}
			}
		}
		contents.append("</table>\n");
		contents.append("</div>\n");

		contents.append("<script>\n");
		contents.append("function menu_OC(id,pname,resPath,isTarget){\n");
		contents.append("for(var i=1; i<" + k + "; i++){\n");
		contents
				.append("document.getElementById('MTID_'+i).className='menu_btn_off';\n");
		contents.append("}\n");
		contents
				.append("document.getElementById('MTID_'+id).className='menu_btn_on';\n");
		contents.append("if(isTarget){;\n");
		contents.append("window.open(resPath,'','');\n");
		contents.append("}else{;\n");
		contents.append("parent." + this.target + ".location.href=resPath;\n");
		contents.append("}}\n");
		contents.append("</script>\n");
		*/
		// System.out.println(contents.toString());
		return contents;
	}

	/**
	 * 显示横向菜单
	 * 
	 * @param resList
	 * @return
	 */
	private StringBuffer showHorizontalMenu(HttpServletRequest request,
			Map map, Map powerMap) {
		StringBuffer contents = new StringBuffer();
		String path = request.getContextPath();

		contents.append("<div class=\"hnavbox\">\n");
		contents.append("<ul class=\"hnav\">");
		//WbaseResource info = new WbaseResource();
		// for (int max = resList.size(), i = 0; i < max; i++) {
		// info = (WbaseResource) resList.get(i);
		// if (info.getNodeNo() != 0) {
		// // 检查权限
		// Object resNoObj = powerMap.get(info.getResNo());
		// if (resNoObj != null) {
		// Integer powerLevel = Integer.parseInt(resNoObj.toString());
		// if (checkPower(powerLevel, 5)) {
		// // contents.append("<li>");
		// contents.append("<a href='" + path + info.getResPath()
		// + "' target='" + this.target + "'>"
		// + info.getResName() + "</a>");
		// contents.append("&nbsp;&nbsp;&nbsp");
		// }
		// }
		//
		// }
		// }
		contents.append("</ul>\n");
		contents.append("</div>\n");

		return contents;
	}
}
