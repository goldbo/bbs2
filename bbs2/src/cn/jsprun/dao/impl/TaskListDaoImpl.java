package cn.jsprun.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.TaskListDao;
import cn.jsprun.domain.TaskList;
import cn.jsprun.utils.HibernateUtil;
public class TaskListDaoImpl implements TaskListDao {

	public boolean addTaskList(TaskList task) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean success=false;
		try{
			ts=session.beginTransaction();
			session.saveOrUpdate(task);
			ts.commit();
			success=true;
		}
		catch(Exception ex){
			ts.rollback();
		}
		return success;
	}

}
