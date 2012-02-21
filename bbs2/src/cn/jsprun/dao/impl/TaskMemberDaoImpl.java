package cn.jsprun.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.TaskMemberDao;
import cn.jsprun.domain.TaskMember;
import cn.jsprun.utils.HibernateUtil;
public class TaskMemberDaoImpl implements TaskMemberDao {

	public boolean addTaskMember(TaskMember tm) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean issuccess=false;
		try{
			ts=session.beginTransaction();
			session.saveOrUpdate(tm);
			ts.commit();
			issuccess=true;
		}
		catch(Exception ex){
			
		}
		return issuccess;
	}

}
