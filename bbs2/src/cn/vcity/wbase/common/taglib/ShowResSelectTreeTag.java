package cn.vcity.wbase.common.taglib;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import cn.vcity.wbase.common.SystemCache;
//import cn.vcity.wbase.model.WbaseResource;

/**
 * @Title: ShowResSelectTreeTag.java
 * @Package cn.vcity.wbase.common.taglib
 * @Description: TODO(用一句话描述该文件做什么)
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-1-7 下午03:16:49
 * @version V1.0
 */
public class ShowResSelectTreeTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8338402559760438525L;

	private boolean edit;// 是否为可编辑树

	private String type;// 树类型：org组织树，orgUser组织用户树

	private String target;// Target for URL

	private boolean isSingle; // 是否为单选

	private Long propertyValue;

	private String propertyId;

	private String propertyName;

	private boolean readOnly = false;

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public Long getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Long propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

	public boolean getIsSingle() {
		return isSingle;
	}

	public void setIsSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}

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

	public boolean isEdit() {
		return edit;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public int doStartTag() {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		StringBuffer buffer = new StringBuffer();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		Map orgCache = SystemCache.resCache;
		String resName = "";
		/*if (!orgCache.isEmpty()) {
			if (this.propertyValue == null) {
				Object resObj = orgCache.get(Long.valueOf("0"));
				if (resObj != null) {
					WbaseResource res = (WbaseResource) resObj;
					resName = res.getResName();
				}
			} else if (this.propertyValue == -1) {
				resName = "已经是最顶层节点";
			} else {
				Object resObj = orgCache.get(this.propertyValue);
				if (resObj != null) {
					WbaseResource res = (WbaseResource) resObj;
					if (res == null) {
						Object resObj2 = orgCache.get(Long.valueOf("0"));
						if (resObj2 != null) {
							res = (WbaseResource) resObj;
							this.propertyValue = res.getNodeNo();
							resName = res.getResName();
						}
					} else {
						resName = res.getResName();
					}
				}
			}
		}*/

		buffer = showResSelect(request, resName);

		try {
			// System.out.print(buffer.toString());
			out.println(buffer.toString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * 显示组织架构树 //目前只做了单选，还需要做多选的情况
	 * 
	 * @param org
	 * @return
	 */
	private StringBuffer showResSelect(HttpServletRequest request,
			String orgName) {
		StringBuffer contents = new StringBuffer();
		String path = request.getContextPath();
		contents.append("<div class=\"subResNameDiv\">\n");
		contents.append("<input type=\"hidden\" name=\"" + this.propertyId
				+ "\" value=\"" + this.propertyValue + "\" id=\""
				+ this.propertyId + "\">");
		contents.append("<input type=\"text\" size=\"18\" id=\""
				+ this.propertyName + "\" value=\"" + orgName + "\" ");
		if (this.readOnly || this.propertyValue == -1) {
			contents.append("readonly=\"readonly\"");
		}
		contents.append(">\n");

		if (!this.readOnly && this.propertyValue != -1) {
			contents
					.append("<input type=\"button\" value=\"...\" class=\"select_btn\"  onclick=\"resSelect('"
							+ this.propertyValue
							+ "','"
							+ path
							+ "/platform/resource/resSelect/resSelect.jsp?edit="
							+ this.edit
							+ "&isSingle="
							+ this.isSingle
							+ "',500,400);\">\n");
		}

		contents
				.append("<SCRIPT type=\"text/javascript\" language=\"javascript\">\n\n");
		contents.append("function resSelect(obj,url,width, height){\n");
		contents
				.append("var revalue = window.showModalDialog(url,obj,\"dialogWidth=\"+width+\"px;dialogHeight=\"+height+\"px;status:no;\");\n");
		contents.append("if(revalue!=null){\n");
		contents.append("if(revalue.length>0){\n");
		// 处理返回的数据,可根据实际业务修改
		contents.append("document.getElementById(\"" + this.propertyId
				+ "\").value = revalue[0];\n");
		contents.append("document.getElementById(\"" + this.propertyName
				+ "\").value = revalue[1];\n");
		contents.append("}else{\n");
		contents.append("alert(\"请选择资源!!\");\n");
		contents.append("}\n");
		contents.append("}\n");
		contents.append("}\n");
		contents.append("</SCRIPT>\n");
		contents.append("</div>\n");

		// System.out.println(contents);
		return contents;
	}

}
