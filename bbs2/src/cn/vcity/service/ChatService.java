package cn.vcity.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.jsprun.dao.ScenetmDao;
import cn.jsprun.domain.Members;
import cn.vcity.dao.ChatDao;
import cn.vcity.dao.VcityScenetmDao;
import cn.vcity.model.VcityChatRoominfo;
import cn.vcity.model.VcityManualchatinfo;
import cn.vcity.wbase.common.conveter.DateConvert;
import cn.vcity.wbase.common.helper.SpringHelper;

public class ChatService {
	private ChatDao chatDao;
	private VcityScenetmDao scenetmDao;
	public ChatDao getChatDao() {
		return chatDao;
	}

	public VcityScenetmDao getScenetmDao() {
		return scenetmDao;
	}


	public void setScenetmDao(VcityScenetmDao scenetmDao) {
		this.scenetmDao = scenetmDao;
	}


	public void setChatDao(ChatDao chatDao) {
		this.chatDao = chatDao;
	}

	public boolean saveChatInfo(List list){
		return chatDao.saveChatInfo(list);
	}
	
	/**
	 * @param taskId
	 * @return
	 * Desc:根据任务ID查找用户
	 * @author csw
	 * Aug 27, 2011
	 */
	public List getUserInfoByTaskId(int taskId){
		return chatDao.getUserInfoByTaskId(taskId);
	}
	
	

	/**
	 * 
	 * @param msg:聊天信息
	 * @param senderID：发送人ID
	 * @param receiverID：接收人ID
	 * @param chatKey：获取当前聊天列表的标识
	 * Desc:保存聊天记录，将记录放在Map中，用于聊天双方页面的显示
	 * 另一边加入到List中，按一定的时间间隔保存到数据库中
	 * @author csw
	 * Jul 2, 2011
	 */
	@SuppressWarnings("unchecked")
	public void inputMsgToMap(String msg,String senderID,String receiverID,String chatKey,int taskId, int isURL){
 		int tempFlag = 1;
		if(ChatUtil.MSGMAP.get(chatKey)!=null&&ChatUtil.MSGMAP.get(chatKey).size()>0){
			List list = ChatUtil.MSGMAP.get(chatKey);
			VcityManualchatinfo vc = (VcityManualchatinfo)list.get(list.size()-1);
			tempFlag = vc.getChatFlag()+1;
		}
		
		VcityManualchatinfo vchat = new VcityManualchatinfo(taskId,DateConvert.DateToStringYMDHMS(new Date()), senderID, receiverID, msg,tempFlag, isURL);
		
		//保存到信息列表中，按一定的时间间隔保存到数据库
		ChatUtil.MSGLIST.add(vchat);
		//保存到Map中，聊天者将从这个变量里取数据显示
		List tempList = new ArrayList();
		if(ChatUtil.MSGMAP.get(chatKey)!=null){
			tempList.addAll(ChatUtil.MSGMAP.get(chatKey));
		}
		tempList.add(vchat);
		ChatUtil.MSGMAP.put(chatKey, tempList);
	}
	
	/**
	 * 获取聊天记录
	 * @param chatKey
	 * @param inx
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getChatMsg(String chatKey,int inx,HttpServletRequest request){
		Members ouser = (Members) request.getSession().getAttribute("userInfo");
		int tempChatFlag = -1 ;
		if(inx==-1){
			ChatUtil.CHATFLAG.put(chatKey, -1);
			//ouser.setChatFlag(inx);
		}
	//	tempChatFlag = ouser.getChatFlag();
		tempChatFlag = ChatUtil.CHATFLAG.get(chatKey);
		List templist = new ArrayList();
		synchronized (ChatUtil.MSGMAP) {
			if(ChatUtil.MSGMAP.get(chatKey)!=null){
				templist.addAll(ChatUtil.MSGMAP.get(chatKey));
			}
		}
		if(templist!=null && !templist.isEmpty()){
			if(templist.size()>0){
				List list = new ArrayList();
				list.addAll(templist);
				int listSize = list.size();
				VcityManualchatinfo cn = (VcityManualchatinfo)list.get(0);
					//以下的inx都替换为tempChatFlag
				if(tempChatFlag<cn.getChatFlag()){
					//ouser.setChatFlag(((VcityManualchatinfo)list.get(list.size()-1)).getChatFlag());//保存返回的最后一条的下标值chatFlag
					Integer temp = ((VcityManualchatinfo)list.get(list.size()-1)).getChatFlag();
					ChatUtil.CHATFLAG.put(chatKey, temp);
					request.getSession().setAttribute("userInfo", ouser);
					return list;
				}else if(tempChatFlag>=((VcityManualchatinfo)list.get(listSize-1)).getChatFlag()){
					//ouser.setChatFlag(((VcityManualchatinfo)list.get(list.size()-1)).getChatFlag());
					Integer temp = ((VcityManualchatinfo)list.get(list.size()-1)).getChatFlag();
					ChatUtil.CHATFLAG.put(chatKey, temp);
					request.getSession().setAttribute("userInfo", ouser);
					return null;
				}else{
					int l = tempChatFlag-cn.getChatFlag()+1;
					if(l<listSize&&l>=0){
						int cf = ((VcityManualchatinfo)list.get(l)).getChatFlag();
						if(cf<=tempChatFlag){
							l = l+tempChatFlag-cf+1;
						}
						//ouser.setChatFlag(((VcityManualchatinfo)list.get(list.size()-1)).getChatFlag());//保存返回的最后一条的下标值chatFlag
						Integer temp = ((VcityManualchatinfo)list.get(list.size()-1)).getChatFlag();
						if (ChatUtil.TEMP.get(chatKey)==null) {
							ChatUtil.TEMP.put(chatKey, 0);
							ChatUtil.ISSAMEUSER.put(chatKey, ouser.getUsername());
						} else {
							if (ChatUtil.ISSAMEUSER.get(chatKey).equals(ouser.getUsername())) {
								ChatUtil.CHATFLAG.put(chatKey, temp);
								ChatUtil.TEMP.clear();
								return null;
							} else {
								ChatUtil.CHATFLAG.put(chatKey, temp);
								ChatUtil.TEMP.clear();
							}
						}
						request.getSession().setAttribute("userInfo", ouser);
						return list.subList(l, listSize);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据任务ID和用户ID查找聊天记录
	 * @param taskId
	 * @param userId
	 * @return
	 */
	public List getChatInfoByTaskId(int taskId,String userId){
		return chatDao.getChatInfoByTaskId(taskId, userId);
	}
	
	public boolean setOnlineUser(String userName,Integer taskId){
		boolean bool = scenetmDao.setOnlineUser(userName,taskId);
		return bool;
	}
	
	public List getOnlineList(Integer taskId){
		List list = new ArrayList();
		list = chatDao.getUserInfoByTaskId(taskId);
		return list;
	}
	
	public int finishCurrentChat(String chatKey){
		int i = 1;
		synchronized (ChatUtil.MSGMAP) {
			if(ChatUtil.MSGMAP.get(chatKey)!=null){
				ChatUtil.MSGMAP.get(chatKey).clear();
				System.out.println(ChatUtil.MSGMAP.get(chatKey));
				i = 0;
			}
		}
		return i;
		
	}
	
	/**
	 * 根据聊天室ID查找参与用户
	 * @param roomId
	 * @return
	 */
	public List getOnlineUsers(Integer roomId){
		List list = new ArrayList();
		list = chatDao.getOnlineUsers(roomId);
		return list;
	}
	
	public boolean setOnlineUsers(String userName,Integer roomId){
		boolean bool = chatDao.setOnlineUsers(userName,roomId);
		return bool;
	}
	
	/**
	 * 
	 * 聊天室模块：保存聊天信息
	 * @param msg:聊天信息
	 * @param senderID：发送人ID
	 * @param receiverID：接收人ID
	 * @param chatKey：获取当前聊天列表的标识
	 * Desc:保存聊天记录，将记录放在Map中，用于聊天双方页面的显示
	 * 另一边加入到List中，按一定的时间间隔保存到数据库中
	 * @author hjb
	 * Jul 2, 2011
	 */
	@SuppressWarnings("unchecked")
	public  void saveChatMsg(String msg,String senderID,String receiverID,String chatKey,Integer roomId){
 	
		VcityChatRoominfo vchat = new VcityChatRoominfo(roomId,DateConvert.DateToStringYMDHMS(new Date()),senderID,receiverID,msg);
		System.out.println("here");
		//保存聊天信息到数据库
		ChatUtil.CHATROOMMSGLIST.add(vchat);
		//保存到Map中，聊天者将从这个变量里取数据显示
		List tempList = new ArrayList();
		if(ChatUtil.CHATROOMMSGMAP.get(chatKey)!=null){
			tempList.addAll(ChatUtil.CHATROOMMSGMAP.get(chatKey));
		}
		tempList.add(vchat);
		ChatUtil.CHATROOMMSGMAP.put(chatKey, tempList);
	}
	
	/**
	 *  聊天室模块：获取聊天记录
	 * @param chatKey
	 * @param inx
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getChatMsgs(String chatKey,Integer index,HttpServletRequest request){
		List templist = new ArrayList();
		synchronized (ChatUtil.CHATROOMMSGMAP) {
			if(ChatUtil.CHATROOMMSGMAP.get(chatKey)!=null){
				templist.addAll(ChatUtil.CHATROOMMSGMAP.get(chatKey));
			}
		}
		int listSize = templist.size();
	//	System.out.println("size:"+listSize);
	//	System.out.println(index);
		if(listSize>0){
			if(listSize>index){
				List list = templist;
				return list.subList(index, listSize);
			}
		}

		return null;
	}
	
}
