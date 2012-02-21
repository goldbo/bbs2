package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.ScenetmDao;
import cn.jsprun.domain.Scenetm;
import cn.jsprun.utils.HibernateUtil;


public class ScenetmDaoImpl implements ScenetmDao {
	public boolean save(Scenetm stm){
			Session session=HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction ts=null;
			try{
				ts=session.beginTransaction();
				session.save(stm);
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
	public boolean update(Scenetm stm){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			session.update(stm);
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
	public List<Scenetm> getScenetmByCondition(String hql, int first, int max) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		List<Scenetm> scenetm=null;
		try{
			ts=session.beginTransaction();
			Query query=session.createQuery(hql);
			if(first!=-1){
				query.setFirstResult(first*max);
				query.setMaxResults(max);
			}
			scenetm=query.list();
			ts.commit();
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.printStackTrace();
		}
		return scenetm;
	}
	public int getSecenetmCountByCondition(String hql) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		int count=0;
		try{
			ts=session.beginTransaction();
			Query query=session.createQuery(hql);
			List list=query.list();
			count=list.size()>0?list.size():0;
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
