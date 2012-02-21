package cn.jsprun.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.TaskDao;
import cn.jsprun.domain.Task;
import cn.jsprun.utils.HibernateUtil;

public class TaskDaoImpl implements TaskDao {

	public boolean addTaskTree(Task task) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		boolean success = false;
		try {
			ts = session.beginTransaction();
			session.save(task);
			ts.commit();
			success = true;
		} catch (Exception ex) {
			ts.rollback();
		}
		return success;
	}

}
