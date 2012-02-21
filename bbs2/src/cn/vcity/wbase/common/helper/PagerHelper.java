
package cn.vcity.wbase.common.helper;
/**
 * 
 * 分页时生成Pager的帮助类<br/>
 * @author samhsieh
 * @version 1.0 
 */
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.vcity.wbase.common.Constants;
import cn.vcity.wbase.common.vo.ConfigInfoVO;
import cn.vcity.wbase.common.vo.page.Pager;

public class PagerHelper {
	
	/**
	 * 
	 * @param request
	 * @param totalRows  要分页的数据总条数
	 * @return
	 * @throws IOException 
	 */
	public static Pager getPager(HttpServletRequest request,int totalRows) throws IOException
	{
        int ints = 0;
        String objStr = request.getParameter("pageSize");
        if(objStr!=null&&objStr.length()>0){
        	ints = Integer.parseInt(objStr);
        }else{
         HttpSession session = request.getSession();
            String screen_height = (String) session.getAttribute(Constants.SCREEN_HEIGHT);
            String pageSize = "";
            int int_screen_height = 0;
            if(screen_height!=null&&screen_height.length()>0){
          	  int_screen_height = Integer.parseInt(screen_height);
          	  if(int_screen_height<600){
          		  pageSize = ConfigInfoVO.getProperty("PAGE_SIZE_MIN_600");
                }else if(int_screen_height<700){
              	  pageSize = ConfigInfoVO.getProperty("PAGE_SIZE_H600_H700");
                }else if(int_screen_height<800){
              	  pageSize = ConfigInfoVO.getProperty("PAGE_SIZE_H700_H800");
                }else if(int_screen_height<900){
              	  pageSize = ConfigInfoVO.getProperty("PAGE_SIZE_H800_H900");
                }else{
              	  pageSize = ConfigInfoVO.getProperty("PAGE_SIZE_MAX_H900");
                }
            }else{
          	  pageSize = ConfigInfoVO.getProperty("PAGE_SIZE");
            }
        	ints = Integer.parseInt(pageSize);
        }
        int pageSize=0;
        pageSize=ints;
		Pager pager=new Pager(pageSize,totalRows);
		String currentPage=request.getParameter("currentPage");
		if(currentPage==null||currentPage.length()==0)
		{
			currentPage="1";
		}
		if(currentPage!=null)
		{
			pager.refresh(Integer.parseInt(currentPage));
		}
		String pagerMethod=request.getParameter("pageMethod");
		if(pagerMethod!=null)
		{
			if(pagerMethod.equalsIgnoreCase("first"))
				pager.first();
			else if(pagerMethod.equalsIgnoreCase("previous"))
				pager.previous();
			else if(pagerMethod.equalsIgnoreCase("next"))
				pager.next();
			else if(pagerMethod.equalsIgnoreCase("last"))
				pager.last();
			else if(pagerMethod.equalsIgnoreCase("go"))
				pager.go(Integer.parseInt(currentPage));
		}

		return pager;
	}
	

	public static Pager getPager(HttpServletRequest request,int newpagesize,int totalRows) throws IOException
	{
        int ints = 0;
        String objStr = request.getParameter("pageSize");
        if(objStr!=null&&objStr.length()>0){
        	newpagesize = Integer.parseInt(objStr);
        }

        int pageSize=0;
        pageSize=newpagesize;
		Pager pager=new Pager(pageSize,totalRows);
		String currentPage=request.getParameter("currentPage");
		if(currentPage==null||currentPage.length()==0)
		{
			currentPage="1";
		}
		if(currentPage!=null)
		{
			pager.refresh(Integer.parseInt(currentPage));
		}
		String pagerMethod=request.getParameter("pageMethod");
		if(pagerMethod!=null)
		{
			if(pagerMethod.equalsIgnoreCase("first"))
				pager.first();
			else if(pagerMethod.equalsIgnoreCase("previous"))
				pager.previous();
			else if(pagerMethod.equalsIgnoreCase("next"))
				pager.next();
			else if(pagerMethod.equalsIgnoreCase("last"))
				pager.last();
			else if(pagerMethod.equalsIgnoreCase("go"))
				pager.go(Integer.parseInt(currentPage));
		}

		return pager;
	}	
}
