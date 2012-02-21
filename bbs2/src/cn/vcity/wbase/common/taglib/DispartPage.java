package cn.vcity.wbase.common.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import cn.vcity.wbase.common.helper.DispartPageHTMLHelper;
import cn.vcity.wbase.common.helper.WebHelper;
import cn.vcity.wbase.common.vo.page.Pager;

public class DispartPage extends BodyTagSupport {
	private static final long serialVersionUID = 3828465519331177130L;

	private String formId;

	private String pagerKey = "pager";//      

	private String scope;
	
	public int doStartTag() {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		// Pager pager = (Pager)this.getPagerObject();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		Pager pager = (Pager) WebHelper.getObjectByWebContainer(request, scope,
				pagerKey);
		JspWriter out = pageContext.getOut();
		StringBuffer buf = DispartPageHTMLHelper.getDispartPageHTML(pager, formId);
		try {
			out.println(buf.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return EVAL_PAGE;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getPagerKey() {
		return pagerKey;
	}

	public void setPagerKey(String pagerKey) {
		this.pagerKey = pagerKey;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
