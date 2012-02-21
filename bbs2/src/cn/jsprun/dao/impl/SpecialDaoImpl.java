package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.SpecialDao;
import cn.jsprun.domain.Special;
import cn.jsprun.utils.HibernateUtil;

public class SpecialDaoImpl implements SpecialDao {

	public boolean addSpecial(Special special) {
		boolean isadd = false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			session.save(special);
			ts.commit();
			isadd = true;
		} catch (HibernateException ex) {
			ts.rollback();
		}
		return isadd;
	}

	public boolean deleteByTid(int tid) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		boolean isdelete=false;
		try {
             ts=session.beginTransaction();
             String hql="delete Special where fid="+tid;
             isdelete=session.createQuery(hql).executeUpdate()>0?true:false;
             ts.commit();
		} catch (HibernateException ex) {
            ex.getMessage();
            ts.rollback();
		}
		return isdelete;
	}

	public boolean deleteById(int id) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean isdelete=false;
		try{
			ts=session.beginTransaction();
			String hql="delete Special where id="+id;
			isdelete=session.createQuery(hql).executeUpdate()>0?true:false;
			ts.commit();
		}
		catch(HibernateException ex){
			ex.getMessage();
			ts.rollback();
		}
		return isdelete;
	}

	public Special getSpecialByTid(int tid) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		Special sp=null;
		try{
			ts=session.beginTransaction();
			String hql="from Special where fid="+tid;
			Query query=session.createQuery(hql);
			List list=query.list();
			sp=list.size()>0?(Special)list.get(0):null;
		}
		catch(HibernateException ex){
			ex.getMessage();
		}
		return null;
	}

	
	
	public boolean update(Special special) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean isupdate=false;
		try{
			ts=session.beginTransaction();
			session.update(special);
			ts.commit();
			isupdate=true;
		}
		catch(HibernateException ex){
			ex.getMessage();
			ts.rollback();
		}
		return isupdate;
	}

}
