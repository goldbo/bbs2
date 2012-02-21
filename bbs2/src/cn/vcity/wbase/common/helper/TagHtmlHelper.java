
package cn.vcity.wbase.common.helper;



/**
 * 
 * 公用Web Container处理方法。<br/>
 * 实现背景：<br>
 * 当时设计此类，主要是用于标签的HTML生成的帮助类<br>
 * 后来各标签的HTML生成的帮助类都成了一个一个java文件，此类所留下的方法不多.<br>
 * @author 罗留庭 EN:Aly.Luo，开发部-珠海市网佳科技有限公司. Create-Time: 2007-9-8.
 * @version 1.0 
 */
public class TagHtmlHelper {
	/**
	 *  用于xloadTree节点的生成
	 * @param orgName 节点名称
	 * @param action  所执行的动作
	 * @param src 加裁子的URL
	 * @param icon 图标
	 * @param openIcon 打开时的图标
	 * @return  String <br>
	 * 如:生成orgName<br>
	 *   SysOrg orgTreePo = (SysOrg) orgList.get(i);<br>
        String action = "javascript:doChangeMain('" + orgTreePo.getOrgId()
            + "','" + orgTreePo.getOrgName() + "','" + orgTreePo.getOrgType()
            + "')";<br>
        String icon = urlBase + "/images/xloadtree/folder.png";<br>
        String openIcon = urlBase + "/images/xloadtree/openfolder.png";<br>
        String src = urlBase + "/orgTree.do?orgId=" + orgTreePo.getOrgId();<br>
        sbXML.append(TagHtmlHelper.makeItemXML( orgTreePo.getOrgName(),action,src, icon,openIcon));<br>
	 */
	public static String makeItemXML(String orgName, String action, String src, String icon, String openIcon)
	{
		 String strXML = "\t";
		    strXML += "<tree text=\"" + orgName + "\"";
		    strXML += " action=\"" + action + "\"";
		    if (src != null) {
		      strXML += " src=\"" + src + "\"";
		    }
		    if (icon != null) {
		      strXML += " icon=\"" + icon + "\"";
		    }
		    if (openIcon != null) {
		      strXML += " openIcon=\"" + openIcon + "\"";
		    }
		    strXML += "/>\n";
		    return strXML;
}
}
