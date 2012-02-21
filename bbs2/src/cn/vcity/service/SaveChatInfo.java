package cn.vcity.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import cn.vcity.wbase.common.helper.SpringHelper;

/**
 * @Title: SystemInitLoadPlugIn.java
 * @Package com.wingo.wbase.common.plugin
 * @Description: 系统相关信息初始缓存
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-4-20 上午10:39:36
 * @version V1.0
 */
public class SaveChatInfo implements PlugIn {

	ChatService chatService = (ChatService) SpringHelper.getBean("chatService");
	
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public void init(ActionServlet servlet, ModuleConfig config)
			throws ServletException {
		
		PublicSendThd1 t=new PublicSendThd1();
		t.start();
	}
	
	class PublicSendThd1 extends Thread {
		private long sendTime = System.currentTimeMillis();

		public void run() {

			while (true) {
				try {
					Thread.currentThread().sleep(1000);
					if(System.currentTimeMillis()-sendTime>ChatUtil.INTERVAL){
						if (!ChatUtil.MSGLIST.isEmpty()) {
							List list1 = new ArrayList();
							list1.addAll(ChatUtil.MSGLIST);
							
							if(chatService.saveChatInfo(list1)){
								ChatUtil.MSGLIST.clear();
							}
						}
						
						//聊天室信息保存
						if (!ChatUtil.CHATROOMMSGLIST.isEmpty()) {
							List list1 = new ArrayList();
							list1.addAll(ChatUtil.CHATROOMMSGLIST);
							
							if(chatService.saveChatInfo(list1)){
								ChatUtil.CHATROOMMSGLIST.clear();
							}
						}
						sendTime = System.currentTimeMillis();
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}

		}
	}

}
