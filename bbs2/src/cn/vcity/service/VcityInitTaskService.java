package cn.vcity.service;

import cn.vcity.dao.VcityInitTaskDao;

public class VcityInitTaskService{
	private VcityInitTaskDao vcityInitTaskDao;

	public VcityInitTaskDao getVcityInitTaskDao() {
		return vcityInitTaskDao;
	}


	public void setVcityInitTaskDao(VcityInitTaskDao vcityInitTaskDao) {
		this.vcityInitTaskDao = vcityInitTaskDao;
	}

	public boolean deleteInitTaskListByTaskId(Integer taskId) {
		return vcityInitTaskDao.deleteInitTaskListByTaskId(taskId);
	}
}
