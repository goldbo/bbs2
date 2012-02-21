package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.PermListDao;
import cn.jsprun.domain.PermList;
import cn.jsprun.utils.HibernateUtil;




public class PermListDaoImpl implements PermListDao {

	public boolean addPermList(PermList permlist) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean isadd=false;
		try{
			ts=session.beginTransaction();
			session.save(permlist);
			ts.commit();
			isadd=true;
		}
		catch(HibernateException ex){
			System.out.println(ex.getMessage());
			ts.rollback();
		}
		return isadd;
	}

	public boolean deletePermListByTid(int tid) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean isdelete=false;
		try{
			ts=session.beginTransaction();
			String hql="delete PermList where tid="+tid;
			isdelete=session.createQuery(hql).executeUpdate()>0?true:false;
			ts.commit();
		}
		catch(HibernateException ex){
			ex.getMessage();
			ts.rollback();
		}
		return isdelete;
	}

	public List<PermList> getPermListByTid(int tid) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		List<PermList> list=null;
		try{
			ts=session.beginTransaction();
			String hql="from PermList where tid="+tid;
			Query query=session.createQuery(hql);
			list=(List<PermList>)query.list();
			ts.commit();
		}
		catch(HibernateException ex){
			ex.getMessage();
			ts.rollback();
		}
		return list;
	}

}
