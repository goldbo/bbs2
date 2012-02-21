package cn.vcity.dao;

import java.util.List;

import cn.vcity.model.VcityManualTaskInit;
import cn.vcity.model.VcitySubTask;
import cn.vcity.wbase.common.base.BaseDAOHibernate;

public class VcityInitTaskDao  extends BaseDAOHibernate {

	protected Class getModelClass() {
		return VcityManualTaskInit.class;
	}

	public boolean deleteInitTaskListByTaskId(Integer taskId) {
		boolean bool = true;
		try{
			List<VcitySubTask> list = this.doFind("from VcityManualTaskInit vs where vs.scenetask.taskId="+taskId);
			super.doDeleteObjects(list);
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
		}
		return bool;
	}

}
