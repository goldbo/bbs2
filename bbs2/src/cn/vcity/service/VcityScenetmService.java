package cn.vcity.service;

import cn.jsprun.domain.Scenetm;
import cn.vcity.dao.VcityScenetmDao;

public class VcityScenetmService {
	private VcityScenetmDao scenetmDao;

	public VcityScenetmDao getScenetmDao() {
		return scenetmDao;
	}

	public void setScenetmDao(VcityScenetmDao scenetmDao) {
		this.scenetmDao = scenetmDao;
	}

	public Scenetm findScenetmByUsernameTaskId(Integer taskId, String userName) {
		return scenetmDao.findScenetmByUsernameTaskId(taskId,userName);
	}

	public boolean setOnlineUser(String userName, Integer taskId) {
		return scenetmDao.setOnlineUser(userName,taskId);
	}
}
