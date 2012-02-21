package cn.vcity.dao;


import java.util.List;

import cn.vcity.model.VcitySubTask;
import cn.vcity.model.VcityTempSurvey;
import cn.vcity.wbase.common.base.BaseDAOHibernate;

public class VcitySubTaskDao extends BaseDAOHibernate {

	protected Class getModelClass() {
		return VcitySubTask.class;
	}

	public void saveSubTask(VcitySubTask vac) {
		super.doCreateObject(vac);
	}

	public List findSubTaskListByTaskId(Integer taskId) {
		
		return super.getSQLQuery("select *, count(distinct userName) from vcity_subTask where taskId="+taskId+" group by userName");
	}
	
	public boolean updateVcitySubTask(VcitySubTask entity) {
		try {
			super.doUpdateObject(entity);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public VcitySubTask findSubTaskByid(Integer id){
		VcitySubTask vcitySubTask =(VcitySubTask)super.doFindObjectById(id);
		return vcitySubTask;
	}

	public void deleteSubTaskByEntity(VcitySubTask entity) {
		super.doDeleteObject(entity);
		
	}

	public boolean deleteSubTaskListByTaskId(Integer taskId) {
		boolean bool = true;
		try{
			List<VcitySubTask> list = this.doFind("from VcitySubTask vs where vs.sceneTask.taskId="+taskId);
			super.doDeleteObjects(list);
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
		}
		return bool;
	}

	public List findSubModelByChat(String taskId, String resId, String userName) {
		List<VcitySubTask> list = this.doFind("from VcitySubTask vs where vs.sceneTask.taskId="+taskId+
				" and vs.subTaskResId='"+resId+"' and vs.userName='"+userName+"' and vs.subTaskType=1");
		return list;
	}

	public List findSubSurveyByChat(Integer taskId, String resId, String userName) {
		List<VcitySubTask> list = this.doFind("from VcitySubTask vs where vs.sceneTask.taskId="+taskId+
				" and vs.subTaskResId='"+resId+"' and vs.userName='"+userName+"' and vs.subTaskType=2");
		return list;
	}

	public void saveTempSurvey(VcityTempSurvey tempSurvey) {
		super.doCreateObject(tempSurvey);
		
	}

	public List findTempSurveyList(Integer taskId) {
		String hql = "from VcityTempSurvey temp where temp.taskId='"+taskId+"'";
		
		return super.doFind(hql);
	} 

}
