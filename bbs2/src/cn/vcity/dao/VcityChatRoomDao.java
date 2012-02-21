package cn.vcity.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.vcity.model.VcityChatRoom;
import cn.vcity.model.VcityChatUser;
import cn.vcity.wbase.common.base.BaseDAOHibernate;
import cn.vcity.web.form.VcityChatRoomForm;

public class VcityChatRoomDao  extends BaseDAOHibernate {

	protected Class getModelClass() {
		return VcityChatRoom.class;
	}

	/**
	 * 查找聊天室数据总数
	 * @param vcityChatRoomForm
	 * @return
	 */
	public Integer findChatRoomTotal(VcityChatRoomForm vcityChatRoomForm) {
		StringBuffer hql = new StringBuffer("from VcityChatRoom room ");
		if (vcityChatRoomForm.getCreatedUser()!=null&&!vcityChatRoomForm.getCreatedUser().equals("")) {
			hql.append(" where room.createdUser='"+vcityChatRoomForm.getCreatedUser()+"'");
		}
		int total = super.getRowsByHQL("select count(*) " + hql.toString());
		return total;
	}

	/**
	 * 查找聊天室数据列表
	 * @param vcityChatRoomForm
	 * @param pageSize
	 * @param startRow
	 * @return
	 */
	public List findChatRoomList(VcityChatRoomForm vcityChatRoomForm, int pageSize, int startRow) {
		StringBuffer hql = new StringBuffer("from VcityChatRoom room");
		if (vcityChatRoomForm.getCreatedUser()!=null&&!vcityChatRoomForm.getCreatedUser().equals("")) {
			hql.append(" where room.createdUser='"+vcityChatRoomForm.getCreatedUser()+"'");
		}
		hql.append(" order by room.id desc");
		return super.doFind(hql.toString(), pageSize, startRow);
	}

	/**
	 * 保存聊天室
	 * @param entity
	 * @return
	 */
	public boolean saveVcityChatRoom(VcityChatRoom entity) {
		return super.doCreateObjectReturn(entity);
	}

	/**
	 * 根据ID查找聊天室
	 * @param id
	 * @return
	 */
	public VcityChatRoom findChatRoomById(Integer id) {
		return (VcityChatRoom)super.doFindObjectById(id);
	}

	/**
	 * 更新聊天室
	 * @param entity
	 * @return
	 */
	public boolean updateVcityChatRoom(VcityChatRoom entity) {
		boolean isSuccess = true;
		try {
			super.doUpdateObject(entity);
		}catch (Exception e) {
			isSuccess = false;
			e.printStackTrace();
		}
		 return isSuccess;
	}

	/**
	 * 单个删除
	 * @return
	 */
	public boolean deleteChatRoomById(Integer id) {
		boolean bool = true;
		try {
			VcityChatRoom chatRoom = this.findChatRoomById(id);
			this.doDeleteObject(chatRoom);
		} catch (Exception e) {
			bool = false;
		}
		return bool;
	}

	/**
	 * 批量删除
	 * @param selectedRow
	 * @return
	 */
	public boolean deleteChatRoomByIds(Integer[] ids) {
		boolean bool = true;
		try{
			if (ids!=null&&ids.length>0) {	
				for (int i = 0; i < ids.length; i++) {
					this.deleteChatRoomById(ids[i]);
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
		}
		return bool;
	}

	/**
	 * 根据聊天室名称查找信息
	 * @param roomName
	 * @return
	 */
	public VcityChatRoom findChatRoomByName(String roomName) {

		String hql = "from VcityChatRoom room where room.roomName=:roomName";

		List<VcityChatRoom> list = new ArrayList<VcityChatRoom>();
		List<VcityChatRoom> doFindObjectListByParam = (List<VcityChatRoom>) this
				.doFindObjectListByParam(hql, "roomName", roomName);
		list = doFindObjectListByParam;
		VcityChatRoom chatRoom = null;
		if (list != null && !list.isEmpty()) {
			chatRoom = (VcityChatRoom) list.get(0);
		}
		return (VcityChatRoom) chatRoom;
	}

	/**
	 * 根据聊天室ID查找所有的参与用户
	 * @param id
	 * @return
	 */
	public List findChatUserListById(Integer chatRoomId) {
		String hql = "select B.id,B.userName,CASE WHEN (SELECT UNIX_TIMESTAMP(now())-B.lastUpdateTime<6) then 1 else 0 end as 'lastUpdateTime'" +
		" from vcity_chatuser B " +
		" where B.chatRoomId ="+chatRoomId+
		" order by B.lastUpdateTime desc";
		return super.getSQLQuery(hql);
	}

	/**
	 * 查找研究员的文字交流会议列表记录个数(包括自己创建与被邀请参与的)
	 * @param username   研究员
	 *  @param isCreate  查询条件：创建或者被邀请
	 * @return
	 */
	public Object[] findChatRoomTotalResearcher(String username) {
		StringBuffer hql = new StringBuffer("from  VcityChatUser u where u.username='"+username+"' ");
		List list = super.doFind(hql.toString());
		Object[] o = new Object[3];
		int totalA = 0;  //自己创建的会议
		int totalB = 0;  //被邀请的会议
		int totalC = 0;  //全部
		if (list!=null) {
			for (int i = 0; i < list.size(); i++) {
				VcityChatUser user = (VcityChatUser)list.get(i);
					if (username.equals(user.getVcityChatRoom().getCreatedUser())) {
						totalA +=1;
						totalC +=1;
					} else if (!username.equals(user.getVcityChatRoom().getCreatedUser())&&user.getVcityChatRoom().getIsPublish()==1) {
						totalB +=1;
						totalC +=1;
					}  
								
				}
			}
		o[0] = totalA;
		o[1] = totalB;
		o[2] = totalC;
		return o;
	}
		

	/**
	 * 查找研究员的文字交流会议列表记录列表(包括自己创建与被邀请参与的)
	 * @param username
	 *  @param isCreate  查询条件：1为创建 0为被邀请 null为全部
	 * @return
	 */
	public List findChatRoomListResearcher(String username,Integer isCreate, int pageSize, int startRow) {
		StringBuffer hql = new StringBuffer("from  VcityChatUser u where u.username='"+username+"' ");
		List list = super.doFind(hql.toString());
		List<VcityChatRoom> datasA = new ArrayList<VcityChatRoom>();
		List<VcityChatRoom> datasB = new ArrayList<VcityChatRoom>();
		List<VcityChatRoom> datasC = new ArrayList<VcityChatRoom>();
		if (list!=null) {
			Collections.reverse(list);
			for (int i = 0; i < list.size(); i++) {
				VcityChatUser user = (VcityChatUser)list.get(i);
				if (isCreate!=null) {  //查询条件不为空				
					if (isCreate==1&&username.equals(user.getVcityChatRoom().getCreatedUser())) {
						datasA.add(user.getVcityChatRoom());
					} else if (isCreate==0&&!username.equals(user.getVcityChatRoom().getCreatedUser())) {
						if (user.getVcityChatRoom().getIsPublish()==1) {
							datasB.add(user.getVcityChatRoom());
						}				
					} 
				} else {  //没有查询条件
					if (username.equals(user.getVcityChatRoom().getCreatedUser())) {
						datasC.add(user.getVcityChatRoom());
					}
					if (!username.equals(user.getVcityChatRoom().getCreatedUser())&&user.getVcityChatRoom().getIsPublish()==1) {
						datasC.add(user.getVcityChatRoom());
					}
				}
			}
			return isCreate==null?returnList(datasC,pageSize,startRow):isCreate==1?returnList(datasA,pageSize,startRow):returnList(datasB,pageSize,startRow);
		}
		
		return null;
	}

	//返回列表
	public static List returnList(List datas,Integer pageSize,Integer startRow){
		if (datas.size()<=pageSize) {
			return datas;
		}
		if (datas.size()-startRow<pageSize) {
			return datas.subList(startRow, datas.size());
		} else {
			return datas.subList(startRow, startRow+pageSize);
		}
	}
}
