package cn.vcity.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.vcity.wbase.common.base.BaseDAOHibernate;

public class ChatDao extends BaseDAOHibernate {
	private static final Log log = LogFactory.getLog(ChatDao.class);

	public boolean saveChatInfo(List list) {
		boolean flag = false;
		try {
			super.getHibernateTemplate().saveOrUpdateAll(list);		
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * @param taskId
	 * @return
	 * Desc:根据任务ID查找用户
	 * @author csw
	 * Aug 27, 2011
	 */
	public List getUserInfoByTaskId(int taskId){
		/*String hql = "select A.uid,B.userName,CASE WHEN (SELECT UNIX_TIMESTAMP(now())-B.lastUpdateTime<6) then 1 else 0 end as 'lastUpdateTime'" +
				" from jrun_members A left join jrun_scenetm B on A.username = B.userName " +
				" where B.taskId ="+taskId+
				" order by B.lastUpdateTime desc";*/
		String hql = "select B.id,B.userName,CASE WHEN (SELECT UNIX_TIMESTAMP(now())-B.lastUpdateTime<6) then 1 else 0 end as 'lastUpdateTime'" +
		" from jrun_scenetm B " +
		" where B.taskId ="+taskId+
		" order by B.lastUpdateTime desc";
		return super.getSQLQuery(hql);
	//	return super.doFind(hql);
	}
	
	/**
	 * 根据任务ID和用户ID查找聊天记录
	 * @param taskId
	 * @param userId
	 * @return
	 */
	public List getChatInfoByTaskId(int taskId,String userId){
		String hql = "from VcityManualchatinfo where 1=1 ";
		if(taskId!=0){
			hql += " and taskId="+taskId;
		}
		if(userId!=null && !userId.equals("")){
			hql += " and (chatFrom = '"+userId+"' or charTo = '"+userId+"' )";
		}
		return super.doFind(hql);
	}
	
	/**
	 * 根据聊天室ID查找参与用户
	 * @param roomId
	 * @return
	 */
	public List getOnlineUsers(Integer roomId) {
		String hql = "select B.id,B.userName,CASE WHEN (SELECT UNIX_TIMESTAMP(now())-B.lastUpdateTime<6) then 1 else 0 end as 'lastUpdateTime'" +
		" from vcity_chatuser B " +
		" where B.chatRoomId ="+roomId+
		" order by B.lastUpdateTime desc";
		return super.getSQLQuery(hql);
	}
	
	/**
	 * 更新在线状态
	 * @param userName
	 * @param roomId
	 * @return
	 */
	public boolean setOnlineUsers(String userName, Integer roomId) {
		boolean bool = true;
		try {
			StringBuffer str = new StringBuffer();
			str.append("update VcityChatUser tm set tm.lastUpdateTime=UNIX_TIMESTAMP(now())" +
					" where tm.vcityChatRoom.id="+roomId+" and tm.username='"+userName+"'");
			bool = super.doBatchExcute(str.toString());
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			bool = false;
		}
		return bool;

	}
}
