package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.ScenetaskDao;
import cn.jsprun.domain.Scenetask;
import cn.jsprun.utils.HibernateUtil;

public class ScenetaskDaoImpl implements ScenetaskDao {

	public List<Scenetask> getAllScenetask(int first,int max) {
		List<Scenetask> scenetask=null;
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			Query query=session.createQuery("from Scenetask s order by s.taskTime desc");
			query.setFirstResult(first*max);
			query.setMaxResults(max);
			scenetask=query.list();
			ts.commit();
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.printStackTrace();
		}
		return scenetask;
	}
	public List<Scenetask> getByCondition(String hql,int first,int max){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		List<Scenetask> list=null;
		try{
			ts=session.beginTransaction();
			Query query=session.createQuery(hql);
			query.setFirstResult(first*max);
			query.setMaxResults(max);
			list=query.list();
			ts.commit();
		}
		catch(Exception ex){
			if(ts==null){
				ts.rollback();
			}
			ex.printStackTrace();
		}
		return list;
	}
	public int getCountByCondition(String hql) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			Query query=session.createQuery(hql);
			List list=query.list();
			int count=list.size()>0?list.size():0;
			ts.commit();
			return count;
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.printStackTrace();
			return 0;
		}
	}
	
	public boolean saveOrUpdateTask(Scenetask stask){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			session.saveOrUpdate(stask);
			ts.commit();
			return true;
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			return false;
		}
	}
	public boolean deleteByHql(String hql){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		int count=0;
		try{
			ts=session.beginTransaction();
			Query query=session.createQuery(hql);
			count=query.executeUpdate();
			ts.commit();
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.printStackTrace();
		}
		return count>0?true:false;
	}
	public Scenetask getScenetaskById(int id) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		Scenetask scenetask=null;
		try{
			ts=session.beginTransaction();
			scenetask=(Scenetask)session.get(Scenetask.class, id);
			ts.commit();
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.printStackTrace();
		}
		return scenetask;
	}
}
