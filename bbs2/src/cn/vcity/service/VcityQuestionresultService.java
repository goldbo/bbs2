package cn.vcity.service;

import java.util.List;
import java.util.Map;

import cn.vcity.dao.VcityQuestionresultDao;
import cn.vcity.model.VcityQuestionresult;

/**
 * 问题的service类
 * 
 * @author LHJ huangjinbo@wingotech.com
 * @see cn.vcity.wbase.common.base.BaseDAOHibernate
 * @version 1.0
 * @time:2011-7-4 17:16:20
 * 
 */
public class VcityQuestionresultService {

	private VcityQuestionresultDao VcityQuestionresultDao;

	public VcityQuestionresultDao getVcityQuestionresultDao() {
		return VcityQuestionresultDao;
	}

	public void setVcityQuestionresultDao(VcityQuestionresultDao VcityQuestionresultDao) {
		this.VcityQuestionresultDao = VcityQuestionresultDao;
	}

	public boolean saveVcityQuestionresult(VcityQuestionresult entity) {
		
		return VcityQuestionresultDao.saveVcityQuestionresult(entity);
	}
	
	/**
	 * 得到用户作答结果 
	 * @param sureyId   题目串ids
	 * @param userName   用户名
	 * @param subtaskid    子任务id
	 * @return
	 */
	public Map findVcityQuestionresultBySureyId(String sureyId,String userName,String subtaskid) {
		
		return VcityQuestionresultDao.findVcityQuestionresultBySureyId(sureyId,userName,subtaskid);
	}

	public VcityQuestionresult findVcityQuestionresultById(Integer id) {
		
		return VcityQuestionresultDao.findVcityQuestionresultById(id);
	}
	

	public boolean updateVcityQuestionresult(VcityQuestionresult entity) {
		
		return VcityQuestionresultDao.updateVcityQuestionresult(entity);
		
	}
	
	public boolean deleteVcityQuestionresultByIds(Integer[] ids) {
		
		return VcityQuestionresultDao.deleteVcityQuestionresultByIds(ids);
	}

	public boolean deleteVcityQuestionresultById(Integer id) {
		
		return VcityQuestionresultDao.deleteVcityQuestionresultById(id);
	}

	public List findUserSurveyResultList(Integer taskId, String userName) {
		return VcityQuestionresultDao.findUserSurveyResultList(taskId,userName);
	}

}
