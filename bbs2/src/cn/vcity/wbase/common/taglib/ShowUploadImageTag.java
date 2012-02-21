package cn.vcity.wbase.common.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @Title: ShowUploadImageTag.java
 * @Package cn.vcity.wbase.common.taglib
 * @Description: TODO(用一句话描述该文件做什么)
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-1-7 下午03:16:49
 * @version V1.0
 */
public class ShowUploadImageTag extends BodyTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3790958217798211048L;

	private String propertyId;

	private String propertyName;

	private String propertyValue;

	private String savePath = "/uploadfile/images/";
	
	private String dirType = "1";
	
	private boolean rename = true;

	// private boolean multiple = false;

	public String getDirType() {
		return dirType;
	}

	public void setDirType(String dirType) {
		this.dirType = dirType;
	}
	
	public boolean isRename() {
		return rename;
	}

	public void setRename(boolean rename) {
		this.rename = rename;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public int doStartTag() {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		StringBuffer buffer = new StringBuffer();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		String defImage = "/uploadfile/view_pic.png";
		//String defImagePath =ConfigInfoVO.getProperty("ATTACH_PATH");
//		String defImagePath = "/uploadfile/images/";

		if (this.propertyValue==null) {
			this.propertyValue = defImage;
		}
//		if (this.savePath==null) {
//			this.savePath = defImagePath;
//		}
		buffer = showUploadImage(request);

		try {
			// System.out.print(buffer.toString());
			out.println(buffer.toString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * 上传图片
	 * 
	 * @param
	 * @return
	 */
	private StringBuffer showUploadImage(HttpServletRequest request) {
		StringBuffer contents = new StringBuffer();
		String path = request.getContextPath();
		contents.append("<div id=\"uploadImageDiv\">\n");
		contents.append("<input type=\"hidden\" name=\"" + this.propertyId
				+ "\" value=\"" + this.propertyValue + "\" id=\""
				+ this.propertyId + "\">");
		contents.append("<img alt=\"已上传的图片\" id=\"viewImage\" src=\"" + path
				+ this.propertyValue + "\" width=\"45px\" height=\"45px\" onload=\"javascript:DrawImage(this,45,45);\">\n");

		contents
				.append("<input type=\"button\" value=\"上传图片\" class=\"button1\" onclick=\"uploadImage('"
						+ this.propertyValue
						+ "','"
						+ path
						+ "/platform/upload/uploadImage.jsp?filePath="
						+ this.savePath + "&dirType="+this.dirType+"&rename="+this.rename+"',500,250);\">\n");

		contents
				.append("<SCRIPT type=\"text/javascript\" language=\"javascript\">\n\n");
		contents.append("function uploadImage(obj,url,width, height){\n");
		contents
				.append("var revalue = window.showModalDialog(url,obj,\"dialogWidth=\"+width+\"px;dialogHeight=\"+height+\"px;status:no;\");\n");
		contents.append("if(revalue!=null){\n");
		contents.append("if(revalue!=''){\n");
		// 处理返回的数据,可根据实际业务修改
		contents.append("document.getElementById(\"" + this.propertyId
				+ "\").value = revalue;\n");
		contents.append("document.all.viewImage.src = '" + path
				+ "'+revalue;\n");
		contents.append("}else{\n");
		contents.append("document.getElementById(\"" + this.propertyId
				+ "\").value = '" + this.propertyValue + "';\n");
		contents.append("}\n");
		contents.append("}\n");
		contents.append("}\n");
		contents.append("</SCRIPT>\n");
		contents.append("</div>\n");

		//System.out.println(contents);
		return contents;
	}


}
