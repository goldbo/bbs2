package cn.vcity.service;

import java.util.List;

import cn.vcity.dao.VcitySubTaskDao;
import cn.vcity.model.VcitySubTask;
import cn.vcity.model.VcityTempSurvey;

public class VcitySubTaskService {
	private VcitySubTaskDao vcitySubTaskDao;

	public VcitySubTaskDao getVcitySubTaskDao() {
		return vcitySubTaskDao;
	}

	public void setVcitySubTaskDao(VcitySubTaskDao vcitySubTaskDao) {
		this.vcitySubTaskDao = vcitySubTaskDao;
	}

	public void saveSubTask(VcitySubTask vac) {
		vcitySubTaskDao.saveSubTask(vac);
	}
	public boolean updateVcitySubTask(VcitySubTask entity) {
		return vcitySubTaskDao.updateVcitySubTask(entity);
	}
	
	public List findSubTaskListByTaskId(Integer taskId) {
		return vcitySubTaskDao.findSubTaskListByTaskId(taskId);
	}

	public void deleteSubTaskByEntity(VcitySubTask entity) {
		vcitySubTaskDao.deleteSubTaskByEntity(entity);
	}

	public boolean deleteSubTaskListByTaskId(Integer taskId) {
		return vcitySubTaskDao.deleteSubTaskListByTaskId(taskId);		
	}

	public List findSubModelByChat(String taskId, String resId, String userName) {
		return vcitySubTaskDao.findSubModelByChat(taskId,resId,userName);
	}
	
	public List findSubSurveyByChat(Integer taskId, String resId, String userName) {
		return vcitySubTaskDao.findSubSurveyByChat(taskId,resId,userName);
	}
	
	public VcitySubTask findSubTaskByid(Integer id){
		
		return vcitySubTaskDao.findSubTaskByid(id);
	}

	public void saveTempSurvey(VcityTempSurvey tempSurvey) {
		vcitySubTaskDao.saveTempSurvey(tempSurvey);
	}

	public List findTempSurveyList(Integer taskId) {
		
		return vcitySubTaskDao.findTempSurveyList(taskId);
	}
	
}
