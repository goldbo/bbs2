package cn.vcity.wbase.common.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import cn.vcity.wbase.common.helper.SpringHelper;
//import cn.vcity.wbase.model.WbasePost;
//import cn.vcity.wbase.service.WbasePostService;

/**
 * @Title: ShowPostSelectTag.java
 * @Package cn.vcity.wbase.common.taglib
 * @Description: TODO(用一句话描述该文件做什么)
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-1-7 下午03:16:49
 * @version V1.0
 */
public class ShowPostSelectTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8338402559760438525L;
	
	private boolean edit;//是否为可编辑
	
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
		/*WbasePostService wbasePostService = (WbasePostService) SpringHelper
		.getBean("wbasePostService");
		List<WbasePost> postList = new ArrayList<WbasePost>();
		postList = wbasePostService.getPostList();
		
		buffer = showPostSelect(request,postList);
*/
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
	 * @param orgList
	 * @return
	 */
	/*private StringBuffer showPostSelect(HttpServletRequest request,List<WbasePost> postList) {
		StringBuffer contents = new StringBuffer();
		contents.append("<div>\n");
		if(this.edit){
			contents.append("<select id=\"postNo\" name=\"postNo\">\n");
		}else{
			contents.append("<select id=\"postNo\" name=\"postNo\" disabled=\"disabled\">\n");
		}
		if(postList.size()!=0){
			for(int i=0;i<postList.size();i++){
				WbasePost post = postList.get(i);
				if(this.value!=null){
					if(this.value.equals(post.getPostNo())){
						contents.append("<option value=\""+post.getPostNo()+"\"  selected=\"selected\" >"+post.getPostName()+"</option>\n");
					}else{
						contents.append("<option value=\""+post.getPostNo()+"\" >"+post.getPostName()+"</option>\n");
					}
				}else{
					contents.append("<option value=\""+post.getPostNo()+"\" >"+post.getPostName()+"</option>\n");
				}
			}
		}else{
			contents.append("<option value=\"\">暂无岗位</option>\n");
		}
		contents.append("</select>\n");
		contents.append("</div>\n");
		
		return contents;
	}*/

}
