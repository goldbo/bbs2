package cn.vcity.wbase.common.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.jsprun.domain.Members;
import cn.vcity.wbase.common.Constants;
import cn.vcity.wbase.common.helper.StringHelper;

/**
 * 
 * 公用用户过滤器失效时的处理。<br/> 实现背景：<br>
 * 当用户有事离开系统一段时间，此时间超过session的最大时长，那第此次会话以失效。<br>
 * 现在用户回来了，他并不知道session以失效（因为他不知道session的存在）<br>
 * 此时用户点某一个联接，而这个联接中的action要用到当前用户的session信息，如果不进行拦截。<br>
 * 那么系统将报空指针异常。<br>
 * 此过滤器用于过滤所有用户请求，如果session以失效，那么拦截并报当前会话失效提示。<br>
 * 并显示重新登陆页或退出至登陆页，如果用户登陆成功，系统将转入用户刚才点的联接
 * 
 * @version 1.0
 */
public class CommonSessionInvalide implements Filter {

	private FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

	}

	/**
	 * noFilterURI 参数为不要进行session检查的页面，比如说：index.jsp,等等页面。
	 */
	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		Members uinfo = (Members) request.getSession().getAttribute("userInfo");

		String noFilterURI = this.filterConfig.getInitParameter("noFilterURI");
		
		String[] noFilterURIArr = null;
		if (noFilterURI != null)
			noFilterURIArr = StringHelper.split(noFilterURI, ",");
		boolean noFilter = false;
		if (noFilterURIArr != null && noFilterURIArr.length > 0) {
			for (int i = 0; i < noFilterURIArr.length; i++) {

				String uri = request.getContextPath() + noFilterURIArr[i];
				System.out.println(uri);
				if (!uri.equals(request.getRequestURI())) {
					System.out.println("request: "+request.getRequestURI());
					noFilter = true;
					break;
				}
			}
		}
		if (noFilter) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			if (uinfo == null) {
				System.out.println("I am here");
			/*	response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");// 防止弹出的信息出现乱码
				// 以上2句代码一定要写在获取out对象之前，否则提示信息为乱码
				PrintWriter out;
				try {
					out = response.getWriter();
					out.println("<script language='JavaScript'>");
					out.println("alert('登录系统后,方可进行相关操作！');");
					out.println("document.location.href='"
							+ request.getContextPath() + "/exit.jsp';");
					out.println("</script>");
					out.flush();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} */
				// response.sendRedirect(request.getContextPath()+"/exit.jsp");
				 response.sendRedirect(request.getContextPath()+"/logging.jsp?action=login");
			} else {
//				String todo = obj.toString();
//				Map powerMap = (Map) session.getAttribute(Constants.ROLE_POWER);
//				Integer powerLevel = Integer.parseInt(powerMap.get(this.resNo)
//						.toString());
//				if (checkPower(powerLevel, this.type)) {
	//
//				}
				
				filterChain.doFilter(servletRequest, servletResponse);
			}
		}

	}

	public void destroy() {
		this.filterConfig = null;

	}
}