package cn.vcity.wbase.common.taglib;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import cn.vcity.wbase.common.SystemCache;
//import cn.vcity.wbase.model.WbaseOrg;

/**
 * @Title: ShowTreeTag.java
 * @Package cn.vcity.wbase.common.taglib
 * @Description: TODO(用一句话描述该文件做什么)
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-1-7 下午03:16:49
 * @version V1.0
 */
public class ShowOrgTreeTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8338402559760438525L;

	private boolean edit;// 是否为可编辑树

	private String type;// 树类型：org组织树，orgUser组织用户树

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
		Map orgCache = SystemCache.orgCache;
		buffer = showTree(request, orgCache);

		try {
//			System.out.print(buffer.toString());
			out.println(buffer.toString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return EVAL_PAGE;
	}

	/**
	 * 显示组织架构树
	 * 
	 * @param orgList
	 * @return
	 */
	private StringBuffer showTree(HttpServletRequest request, Map map) {
		StringBuffer contents = new StringBuffer();
		String path = request.getContextPath();
		contents.append("<div class=\"dtree\">\n");
		contents.append("<script type=\"text/javascript\">\n");
		contents.append("<!--\n");

		contents.append("var orgTree = new dTree('orgTree');\n");// create a
		if (this.edit) {
			contents.append("orgTree.config.check=true;\n");// 添加复选框
		}
		// array in
		// javascript
		/*for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			//Object key = entry.getKey();
			Object val = entry.getValue();

			WbaseOrg info = (WbaseOrg)val;
			// orgTree.add(1, 0, 'My node', 'node.html', 'node title',
			// 'mainframe', 'img/musicfolder.gif');
			// orgTree.add(0,-1,'My example tree');
			// define elements of array
			contents.append("orgTree.add(");
			contents.append(info.getNodeNo());// 节点ID
			contents.append(",");
			contents.append(info.getSubNodeNo());// 节点父ID
			contents.append(",'");
			contents.append(info.getOrgName());// 节点名称
			contents.append("','");
			// Url for the node
			if ("org".equals(this.type)) {
				contents.append(path
						+ "/UserAction.do?action=showOrgUserList&orgNodeNo="
						+ info.getNodeNo());
			} else if ("orgUser".equals(this.type)) {
				contents.append(path
						+ "/OrgAction.do?action=editOrg&todo=create");
			} else {
				contents.append(path);
			}
			contents.append("','");
			contents.append(info.getOrgName());// Title for the node
			contents.append("','");
			contents.append(this.target);// Target for the node
			contents.append("');\n");
			// contents.append("img/musicfolder.gif");//Image file to use as the
			// icon. Uses default if not specified
			// contents.append("','");
			// contents.append("img/musicfolder.gif");//Image file to use as the
			// open icon. Uses default if not specified
			// contents.append("',");
			// contents.append("false");//Boolean Is the node open
			// contents.append(");\n");
		}*/
		contents.append("document.write(orgTree);\n");

		contents.append("//-->\n");
		contents.append("</script>\n");
		contents.append("</div>\n");

		return contents;
	}

}
