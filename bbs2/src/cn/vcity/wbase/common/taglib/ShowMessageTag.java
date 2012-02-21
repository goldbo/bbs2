package cn.vcity.wbase.common.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import cn.vcity.wbase.common.Constants;

/**
 * @Title: ShowMessageTag.java
 * @Package cn.vcity.wbase.common.taglib
 * @Description: 显示系统返回信息
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-1-7 下午03:16:49
 * @version V1.0
 */
public class ShowMessageTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8338402559760438525L;

	public int doStartTag() {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		StringBuffer buffer = new StringBuffer();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		try {
			Object objMsg = request.getAttribute(Constants.ALERT_MESSAGE);
			Object extJs = request.getAttribute(Constants.ALERT_EXTJS);
			buffer = showMessage(objMsg, extJs);
			// System.out.print(buffer.toString());
			request.removeAttribute(Constants.ALERT_MESSAGE);
			request.removeAttribute(Constants.ALERT_EXTJS);
			out.println(buffer.toString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * 显示返回信息
	 * 
	 * @param orgList
	 * @return
	 */
	private StringBuffer showMessage(Object objMsg, Object extJs) {
		StringBuffer contents = new StringBuffer();
		if (objMsg == null) {
			contents.append("");
		} else {
			contents.append("<script type=\"text/javascript\">\n");
			contents.append("<!--\n");
			contents.append("alert('" + objMsg.toString() + "');\n");
			if (extJs != null) {
				contents.append(extJs.toString() + "\n");
			}
			contents.append("//-->\n");
			contents.append("</script>\n");
		}
		return contents;
	}

}
