package cn.vcity.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.jsprun.domain.Scenetask;
import cn.vcity.dao.VcityChatRoomDao;
import cn.vcity.dao.VcityTaskDao;
import cn.vcity.model.VcityChatRoom;
import cn.vcity.model.VcitySubTask;
import cn.vcity.web.form.VcityChatRoomForm;
import cn.vcity.web.form.VcityTaskForm;

public class VcityChatRoomService {
	private VcityChatRoomDao vcityChatRoomDao;

	public VcityChatRoomDao getVcityChatRoomDao() {
		return vcityChatRoomDao;
	}

	public void setVcityChatRoomDao(VcityChatRoomDao vcityChatRoomDao) {
		this.vcityChatRoomDao = vcityChatRoomDao;
	}

	public Integer findChatRoomTotal(VcityChatRoomForm vcityChatRoomForm) {
		return vcityChatRoomDao.findChatRoomTotal(vcityChatRoomForm);
	}

	public List findChatRoomList(VcityChatRoomForm vcityChatRoomForm, int pageSize, int startRow) {
		return vcityChatRoomDao.findChatRoomList(vcityChatRoomForm,pageSize,startRow);
	}

	public boolean saveVcityChatRoom(VcityChatRoom entity) {
		return vcityChatRoomDao.saveVcityChatRoom(entity);
	}

	public VcityChatRoom findChatRoomById(Integer id) {
		return vcityChatRoomDao.findChatRoomById(id);
	}

	public boolean updateVcityChatRoom(VcityChatRoom entity) {
		return vcityChatRoomDao.updateVcityChatRoom(entity);
	}

	public boolean deleteChatRoomById(Integer id) {
		return vcityChatRoomDao.deleteChatRoomById(id);
	}

	public boolean deleteChatRoomByIds(Integer[] selectedRow) {
		return vcityChatRoomDao.deleteChatRoomByIds(selectedRow);
	}

	public VcityChatRoom findChatRoomByName(String roomName){
		return vcityChatRoomDao.findChatRoomByName(roomName);
	}

	public List findChatUserListById(Integer chatRoomId) {
		return vcityChatRoomDao.findChatUserListById(chatRoomId);
	}

	public  Object[] findChatRoomTotalResearcher(String username) {
		return vcityChatRoomDao.findChatRoomTotalResearcher(username);
	}

	public List findChatRoomListResearcher(String username,Integer isCreate, int pageSize, int startRow) {
		return vcityChatRoomDao.findChatRoomListResearcher(username,isCreate,pageSize,startRow);
	}
}
