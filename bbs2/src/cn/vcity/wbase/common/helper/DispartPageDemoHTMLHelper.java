package cn.vcity.wbase.common.helper;

import cn.vcity.wbase.common.vo.page.Pager;

public class DispartPageDemoHTMLHelper {


	public static StringBuffer getDispartPageHTML(Pager pager, String formId) {
		StringBuffer buf = new StringBuffer();
//		buf.append("<div align='right'>");
		if (pager.getTotalRows() > pager.getPageSize()) {
			if (pager.getCurrentPage() > 1
					&& pager.getCurrentPage() < pager.getTotalPages()) {
//				buf.append("共有<font color='#0000FF'>"
//								+ pager.getTotalRows()
//								+ "</font>条记录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				buf.append("<a href='javascript:void(0)' onClick=\"goDispartPage('first',"
						+ pager.getCurrentPage() + ",'" + formId
						+ "');return false; \">首页</a>\n");

				// 上一页按钮
				buf.append("<a href='javascript:void(0)' onClick=\"goDispartPage('previous',"
						+ pager.getCurrentPage() + ",'" + formId
						+ "');return false; \">上页</a>\n");

				// 下一页按钮
				buf.append("<a href='javascript:void(0)' onClick=\"goDispartPage('next',"
						+ pager.getCurrentPage() + ",'" + formId
						+ "');return false; \">下页</a>\n");

				// 最后一页按钮
				buf.append("<a href='javascript:void(0)' onClick=\"goDispartPage('last',"
						+ pager.getCurrentPage() + ",'" + formId
						+ "');return false; \">尾页</a>\n");

				// 显示本页的小文本框
				buf.append("<span>转到</span><input type='text' size='4' name='currentPage' value='"
								+ pager.getCurrentPage()
								+ "' size='4' id='currentPage' ><span>页</span>\n");

				// 显示跳转按钮
				buf.append("<a href='javascript:void(0)' onClick=\"goDispartPage('go',"
						+ pager.getCurrentPage() + ",'" + formId
						+ "');return false; \">确定</a>\n");
//				buf.append("&nbsp;第" + pager.getCurrentPage() + "页/共"
//						+ pager.getTotalPages() + "页");
			} else {
				if (pager.getCurrentPage() == 1) {
//					buf.append("共有<font color='#0000FF'>"
//									+ pager.getTotalRows()
//									+ "</font>条记录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					// 下一页按钮
					buf.append("<a href='javascript:void(0)' onClick=\"goDispartPage('next',"
							+ pager.getCurrentPage() + ",'" + formId
							+ "');return false; \">下页</a>\n");

					// 最后一页按钮
					buf.append("<a href='javascript:void(0)' onClick=\"goDispartPage('last',"
							+ pager.getCurrentPage() + ",'" + formId
							+ "');return false; \">尾页</a>\n");

					// 显示本页的小文本框
					buf.append("<span>转到</span><input type='text' size='4' name='currentPage' value='"
									+ pager.getCurrentPage()
									+ "' id='currentPage' ><span>页</span>\n");

					// 显示跳转按钮
					buf.append("<a href='javascript:void(0)' onClick=\"goDispartPage('go',"
							+ pager.getCurrentPage() + ",'" + formId
							+ "');return false; \">确定</a>\n");
//					buf.append("&nbsp;第" + pager.getCurrentPage() + "页/共"
//							+ pager.getTotalPages() + "页");
				}
				if (pager.getCurrentPage() == pager.getTotalPages()) {
//					buf.append("共有<font color='#0000FF'>"
//									+ pager.getTotalRows()
//									+ "</font>条记录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					buf.append("<a href='javascript:void(0)' onClick=\"goDispartPage('first',"
							+ pager.getCurrentPage() + ",'" + formId
							+ "');return false; \">首页</a>\n");

					// 上一页按钮
					buf.append("<a href='javascript:void(0)' onClick=\"goDispartPage('previous',"
									+ pager.getCurrentPage()
									+ ",'"
									+ formId
									+ "');return false; \">上页</a>\n");

					// 显示本页的小文本框
					buf.append("<span>转到</span><input type='text' name='currentPage' value='"
									+ pager.getCurrentPage()
									+ "' size='4' id='currentPage' ><span>页</span>"
									+"<a href='javascript:void(0)' onClick=\"goDispartPage('go',"
									+ pager.getCurrentPage() + ",'" + formId
									+ "');return false; \">确定</a>\n");

//					// 显示跳转按钮
//					buf.append("<a href='javascript:void(0)' onClick=\"goDispartPage('go',"
//							+ pager.getCurrentPage() + ",'" + formId
//							+ "');return false; \">跳转</a>\n");
//					buf.append("&nbsp;第" + pager.getCurrentPage() + "页/共"
//							+ pager.getTotalPages() + "页");

				}
			}
		} else {
			// pager.setCurrentPage(1);
		}
//		buf.append("</div>\n");
//		buf.append("</td>\n");
//		buf.append("</tr>\n");
//		buf.append("</table>\n");
		//System.out.println(buf.toString());
		return buf;
	}
}
