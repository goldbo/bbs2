package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.ScenectOrderDao;
import cn.jsprun.domain.ScenectOrder;
import cn.jsprun.utils.HibernateUtil;

public class ScenectOrderDaoImpl implements ScenectOrderDao {

	
	/**
	 * 根据hql来查找相应的数据
	 */
	public List<ScenectOrder> getByHql(String hql) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		List<ScenectOrder> list=null;
		try{
			ts=session.beginTransaction();
			Query query=session.createQuery(hql);
			list=query.list();
			ts.commit();
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据传进来的hql来查找相应记录有多少条
	 */
	public int getByHqlCount(String hql) {
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

	/**
	 * 执行保存或更新
	 */
	public boolean saveOrUpdate(ScenectOrder sctorder) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			session.saveOrUpdate(sctorder);
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

}
