package cn.vcity.wbase.common.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @Title: ShowUploadFilesTag.java
 * @Package cn.vcity.wbase.common.taglib
 * @Description: TODO(用一句话描述该文件做什么)
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-1-7 下午03:16:49
 * @version V1.0
 */
public class ShowUploadFileTag extends BodyTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6096025788693932L;

	// [file_size_limit]设置单个文件上传最大值,默认10 单位 MB
	// 设置允许上传的文件类型
	// [file_types]文件类型*.gif,*.png,*.jpg,*bpm等*.doc,*.xls.*.pdf等*.flv等
	// [file_types_description]描述
	// [file_upload_limit]一次上传文件个数控制

	private String file_size_limit = "10";//M

	private String file_types = "*.*";

	private String file_types_description = "";

	private String file_upload_limit = "0";

	public String getFile_size_limit() {
		return file_size_limit;
	}

	public void setFile_size_limit(String file_size_limit) {
		this.file_size_limit = file_size_limit;
	}

	public String getFile_types() {
		return file_types;
	}

	public void setFile_types(String file_types) {
		this.file_types = file_types;
	}

	public String getFile_types_description() {
		return file_types_description;
	}

	public void setFile_types_description(String file_types_description) {
		this.file_types_description = file_types_description;
	}

	public String getFile_upload_limit() {
		return file_upload_limit;
	}

	public void setFile_upload_limit(String file_upload_limit) {
		this.file_upload_limit = file_upload_limit;
	}

	public int doStartTag() {

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		StringBuffer buffer = new StringBuffer();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		request.getSession().removeAttribute("fileList");
		try {
			buffer = showUploadFiles(request);
			// System.out.print(buffer.toString());
			out.println(buffer.toString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * 上传多文件
	 * 
	 * @param
	 * @return
	 */
	private StringBuffer showUploadFiles(HttpServletRequest request) {
		StringBuffer contents = new StringBuffer();
		String path = request.getContextPath();
		contents.append("<div class=\"uploadFilesDiv\">\n");
		contents
				.append("<input type=\"button\" value=\"上传文件\" class=\"button1\" onclick=\"uploadFiles('"
						+ path
						+ "/platform/upload/uploadFile.jsp',600,500);\">\n");
		contents
				.append("<SCRIPT type=\"text/javascript\" language=\"javascript\">\n\n");
		contents.append("function uploadFiles(url,width, height){\n");
		contents.append("var obj = new Object();\n");
		contents.append("obj.file_size_limit = '"+this.file_size_limit+" MB';\n");
		contents.append("obj.file_types = '"+this.file_types+"';\n");
		contents.append("obj.file_types_description = '"+this.file_types_description+"';\n");
		contents.append("obj.file_upload_limit = '"+this.file_upload_limit+"';\n");
		contents
				.append("var revalue = window.showModalDialog(url,obj,\"dialogWidth=\"+width+\"px;dialogHeight=\"+height+\"px;status:no;\");\n");
		contents.append("}\n");
		contents.append("</SCRIPT>\n");
		contents.append("</div>\n");

		// System.out.println(contents);
		return contents;
	}
}
