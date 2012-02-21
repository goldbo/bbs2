package cn.vcity.wbase.common.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import cn.vcity.wbase.common.helper.SpringHelper;
//import cn.vcity.wbase.model.WbaseRole;
//import cn.vcity.wbase.service.WbaseRoleService;

/**
 * @Title: ShowRoleSelectTag.java
 * @Package cn.vcity.wbase.common.taglib
 * @Description: TODO(用一句话描述该文件做什么)
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-1-7 下午03:16:49
 * @version V1.0
 */
public class ShowRoleSelectTag extends BodyTagSupport {
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
		/*WbaseRoleService wbaseRoleService = (WbaseRoleService) SpringHelper
		.getBean("wbaseRoleService");
		List<WbaseRole> RoleList = new ArrayList<WbaseRole>();
		RoleList = wbaseRoleService.getRoleList();
		
		buffer = showRoleSelect(request,RoleList);*/

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
	/*private StringBuffer showRoleSelect(HttpServletRequest request,List<WbaseRole> RoleList) {
		StringBuffer contents = new StringBuffer();
		contents.append("<div>\n");
		if(this.edit){
			contents.append("<select id=\"roleNo\" name=\"roleNo\">\n");
		}else{
			contents.append("<select id=\"roleNo\" name=\"roleNo\" disabled=\"disabled\">\n");
		}
		if(RoleList.size()!=0){
			for(int i=0;i<RoleList.size();i++){
				WbaseRole Role = RoleList.get(i);
				if(this.value!=null){
					if(this.value.equals(Role.getRoleNo())){
						contents.append("<option value=\""+Role.getRoleNo()+"\"  selected=\"selected\" >"+Role.getRoleName()+"</option>\n");
					}else{
						contents.append("<option value=\""+Role.getRoleNo()+"\" >"+Role.getRoleName()+"</option>\n");
					}
				}else{
					contents.append("<option value=\""+Role.getRoleNo()+"\" >"+Role.getRoleName()+"</option>\n");
				}
			}
		}else{
			contents.append("<option value=\"\">暂无角色</option>\n");
		}
		contents.append("</select>\n");
		contents.append("</div>\n");
		
		return contents;
	}*/

}
