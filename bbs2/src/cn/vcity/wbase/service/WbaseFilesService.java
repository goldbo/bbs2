package cn.vcity.wbase.service;

import cn.vcity.wbase.common.base.BaseServiceImpl;
import cn.vcity.wbase.dao.WbaseFilesDao;
/**
 * 用户认证SERVICE
 * @author xms
 *
 */
public class WbaseFilesService extends BaseServiceImpl {
	private WbaseFilesDao wbaseFilesDao;

	public WbaseFilesDao getWbaseFilesDao() {
		return wbaseFilesDao;
	}

	public void setWbaseFilesDao(WbaseFilesDao wbaseFilesDao) {
		this.wbaseFilesDao = wbaseFilesDao;
	}
 
	/**
	 * 从数据库删除
	 * @param fileName
	 * @return
	 */
	public boolean delFromDb(String fileName) {
		return wbaseFilesDao.delFromDb(fileName);
	}

	/**
	 * 从磁盘删除
	 * @param fileName
	 * @return
	 */
	public boolean delFromDisk(String fileName) {
		return wbaseFilesDao.delFromDisk(fileName);
	}
	 
}
