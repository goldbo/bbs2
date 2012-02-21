package cn.vcity.dao;

import java.util.ArrayList;
import java.util.List;


import cn.jsprun.domain.Scenetask;
import cn.vcity.model.VcityResource;
import cn.vcity.model.VcitySubTask;
import cn.vcity.wbase.common.base.BaseDAOHibernate;
import cn.vcity.web.form.VcityTaskForm;

public class VcityTaskDao  extends BaseDAOHibernate {

	protected Class getModelClass() {
		return Scenetask.class;
	}

	public int findTaskTotalByOther(String userName,VcityTaskForm vcityTaskForm) {
		StringBuffer query = new StringBuffer();
		query.append("from Scenetask st where 1=1 \n");
		if (userName!=null) {
			query.append(" and st.createUser='"+userName+"'");
		}
		int total = super.getRowsByHQL("select count(*) " + query.toString());
		return total;
	}

	public List findTaskListByOther(String userName,VcityTaskForm vcityTaskForm,int pageSize,int startRow) {
		StringBuffer query = new StringBuffer();
		query.append("from Scenetask st where 1=1 \n");
		
		if (userName!=null) {
			query.append(" and st.createUser='"+userName+"'");
		}
		query.append(" order by st.id desc");
		List datas = super.doFind(query.toString(), pageSize, startRow);
		return datas;
	}

	public boolean saveVcityTask(Scenetask entity) {
		return super.doCreateObjectReturn(entity);
	}

	public Scenetask findTaskByName(String taskName,String createUser) {
		String hql = "from Scenetask v where v.taskName=:taskName and v.createUser=:createUser";
		String[] params = {"taskName","createUser"};
		Object[] values = {taskName,createUser};
		
		List<Scenetask> doFindObjectListByParam = super.doFindObjectListByParams(hql, params, values);
		List<Scenetask> list = new ArrayList<Scenetask>();
	//	List<Scenetask> doFindObjectListByParam = (List<Scenetask>) this.doFindObjectListByParam(hql, "taskName", taskName);
		list = doFindObjectListByParam;
		Scenetask scenetask = null;
		if (list != null && !list.isEmpty()) {
			scenetask = (Scenetask) list.get(0);
		}
		return (Scenetask) scenetask;
	}

	public Scenetask findTaskById(Integer id) {
		return (Scenetask) super.doFindObjectById(id);
	}

	public void updateTask(Scenetask task) {
		super.doUpdateObject(task);
	}

	public boolean deleteTaskById(Integer id) {
		boolean bool = true;
		try {
			Scenetask task = this.findTaskById(id);
			this.doDeleteObject(task);
		} catch (Exception e) {
			bool = false;
		}
		return bool;
	}

	public boolean deleteTaskByIds(Integer[] ids) {
		boolean bool = true;
		try{
			if (ids!=null&&ids.length>0) {	
				for (int i = 0; i < ids.length; i++) {
					this.deleteTaskById(ids[i]);
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
		}
		return bool;
	}

	public List<VcitySubTask> getSubTaskListByUserName(String userName,Integer taskId) {
		String hql = "from VcitySubTask vs where vs.userName='"+userName+
		"' and vs.sceneTask.taskId="+taskId+
		" order by vs.subTaskIndex,vs.id desc";
		return super.doFind(hql);
	}

	public boolean updateVcityTask(Scenetask entity) {
		boolean bool = true;
		try{
			super.doUpdateObject(entity);
		} catch (Exception e) {
			bool =false;
		}
		
		return bool;
	}

}
