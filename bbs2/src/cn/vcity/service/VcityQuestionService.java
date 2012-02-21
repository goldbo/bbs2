package cn.vcity.service;

import java.util.List;

import cn.vcity.dao.VcityQuestionDao;
import cn.vcity.model.VcityQuestion;

/**
 * 问题的service类
 * 
 * @author LHJ huangjinbo@wingotech.com
 * @see cn.vcity.wbase.common.base.BaseDAOHibernate
 * @version 1.0
 * @time:2011-7-4 17:16:20
 * 
 */
public class VcityQuestionService {

	private VcityQuestionDao VcityQuestionDao;

	public VcityQuestionDao getVcityQuestionDao() {
		return VcityQuestionDao;
	}

	public void setVcityQuestionDao(VcityQuestionDao VcityQuestionDao) {
		this.VcityQuestionDao = VcityQuestionDao;
	}

	public boolean saveVcityQuestion(VcityQuestion entity) {
		
		return VcityQuestionDao.saveVcityQuestion(entity);
	}

	public VcityQuestion findVcityQuestionById(Integer id) {
		
		return VcityQuestionDao.findVcityQuestionById(id);
	}
	
	public List findVcityQuestionBySureyId(Integer sureyId) {
		
		return VcityQuestionDao.findVcityQuestionBySureyId(sureyId);
	}
	
	public List findAllTemplateVcityQuestion() {
		return VcityQuestionDao.findAllTemplateVcityQuestion();
	}

	public boolean deleteQuestionTemplate(Integer id){
		return VcityQuestionDao.deleteQuestionTemplate(id);
		
	}
	public boolean updateVcityQuestion(VcityQuestion entity) {
		
		return VcityQuestionDao.updateVcityQuestion(entity);
		
	}
	
	public boolean updateVcityQuestionIndex(VcityQuestion entity,String upOrDown) {
		
		return VcityQuestionDao.updateVcityQuestionIndex(entity, upOrDown);
		
	}

	public boolean deleteVcityQuestionByIds(Integer[] ids) {
		
		return VcityQuestionDao.deleteVcityQuestionByIds(ids);
	}

	public boolean deleteVcityQuestionById(Integer id) {
		
		return VcityQuestionDao.deleteVcityQuestionById(id);
	}
	
	public boolean setQuestionTemplate(Integer id) {
		
		return VcityQuestionDao.setQuestionTemplate(id);
	}

	public List findAllVcityQuestion() {
		return VcityQuestionDao.findAllVcityQuestion();
		
	}

	public List findVcityQuestionByIds(String ids){
		
		return VcityQuestionDao.findVcityQuestionByIds(ids);
	}	
}
