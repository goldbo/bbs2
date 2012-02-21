package cn.vcity.service;

import java.util.List;

import cn.jsprun.domain.Scenetask;
import cn.vcity.dao.VcityTaskDao;
import cn.vcity.model.VcitySubTask;
import cn.vcity.web.form.VcityTaskForm;

public class VcityTaskService {
	private VcityTaskDao vcityTaskDao;

	public VcityTaskDao getVcityTaskDao() {
		return vcityTaskDao;
	}

	public void setVcityTaskDao(VcityTaskDao vcityTaskDao) {
		this.vcityTaskDao = vcityTaskDao;
	}

	public int findTaskTotalByOther(String userName,VcityTaskForm vcityTaskForm) {
		
		return vcityTaskDao.findTaskTotalByOther(userName,vcityTaskForm);
	}

	public List findTaskListByOther(String userName,VcityTaskForm vcityTaskForm,int pageSize,int startRow) {
		return vcityTaskDao.findTaskListByOther(userName,vcityTaskForm,pageSize,startRow);
	}

	public boolean saveVcityTask(Scenetask entity) {
		return vcityTaskDao.saveVcityTask(entity);
	}

	/*public Scenetask findTaskByName(String taskName) {
		return vcityTaskDao.findTaskByName(taskName);
	}*/
	
	/**
	 * 异步检查任务名称是否已经存在
	 * @param id  
	 * @param taskName
	 * @return
	 */
	public Integer checkTaskExistByName(Integer id,String taskName,String userName) {
		Integer _return = 0;
		Scenetask task =  vcityTaskDao.findTaskByName(taskName,userName);
		if (id==null) {
			if (task!=null) {
				_return = 1;
			}
		} else {
			if (task!=null) {
				if (task.getTaskId().toString().equals(id.toString())) {
					_return = 0;
				} else {
					_return = 1;
				}
			}
		}
		return _return;	
	}


	public Scenetask findTaskById(Integer id) {
		return vcityTaskDao.findTaskById(id);
	}

	public void updateTask(Scenetask task) {
		vcityTaskDao.updateTask(task);
		
	}

	public boolean deleteTaskById(Integer id) {
		return vcityTaskDao.deleteTaskById(id);
	}

	public boolean deleteTaskByIds(Integer[] ids) {

		return vcityTaskDao.deleteTaskByIds(ids);
	}

	public List<VcitySubTask> getSubTaskListByUserName(String userName,Integer taskId) {

		return vcityTaskDao.getSubTaskListByUserName(userName,taskId);
	}

	public boolean updateVcityTask(Scenetask entity) {
		return vcityTaskDao.updateVcityTask(entity);
	}
}
