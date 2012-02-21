package cn.vcity.wbase.common.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import cn.vcity.wbase.common.base.OnlineCounter;

public class SessionListener implements HttpSessionAttributeListener {

	public void attributeAdded(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		if (event.getName().equals("LOGIN_IP"))
		  {
		   try
		   {
		      OnlineCounter.doUser(event.getValue(),false);
		      
//		      List list = OnlineCounter.getOnline();
//		    循环list
//				for (int i = 0; i < list.size(); i++) {
//					String[] temp = (String[])list.get(i);
//					 System.out.println("用户"+i+":ip:"+temp[0]+"---"+temp[1]);
//				}
//			System.out.println("在线用户个数："+OnlineCounter.getOnlineAllCount());
			
		   } catch (Exception e)
		   {
		    e.printStackTrace();
		   }
		  }

	}

	public void attributeRemoved(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		 
		  if (event.getName().equals("LOGIN_IP"))
		  {
		   try
		   {
		      OnlineCounter.doUser(event.getValue(),true);
		   } catch (Exception e)
		   {
		    e.printStackTrace();
		   }

		  }


	}

	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub

	}

}
