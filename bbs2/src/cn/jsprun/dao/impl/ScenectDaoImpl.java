package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.ScenectDao;
import cn.jsprun.domain.Scenect;
import cn.jsprun.utils.HibernateUtil;


public class ScenectDaoImpl implements ScenectDao {
	 public boolean saveOrUpdate(Scenect sct){
		 Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		 Transaction ts=null;
		 try{
			 ts=session.beginTransaction();
			 session.saveOrUpdate(sct);
			 ts.commit();
			 return true;
		 }
		 catch(Exception ex){
			 if(ts!=null){
				 ts.rollback();
			 }
			 ex.printStackTrace();
			 return false;
		 }
	 }

	public List<Scenect> getScenectByCondition(String hql,int first,int max) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		List<Scenect> scenect=null;
		try{
			ts=session.beginTransaction();
			Query query=session.createQuery(hql);
			if(first!=-1){
				query.setFirstResult(first*max);
				query.setMaxResults(max);
			}
			scenect=query.list();	
			ts.commit();
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.printStackTrace();
		}
		return scenect;
	}
	public int getScenectCountByCondition(String hql){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		int count=0;
		try{
			ts=session.beginTransaction();
			Query query=session.createQuery(hql);
			count=query.list().size()>0?query.list().size():0;	
			ts.commit();
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.printStackTrace();
		}
		return count;
	}
}
