package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.PresentDao;
import cn.jsprun.domain.Present;
import cn.jsprun.utils.HibernateUtil;

public class PresentDaoImpl implements PresentDao {
	public boolean saveOrUpdatePresent(Present present){
		boolean issuccess=false;
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try {
			ts=session.beginTransaction();
			session.saveOrUpdate(present);
			ts.commit();
			issuccess=true;
		} catch (Exception e) {
			// TODO: handle exception
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return issuccess;
	}
	public List<Present> getAllGift(){
		List<Present> list=null;
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			Query query=session.createQuery("from Present");
			list=query.list();
			ts.commit();
		}
		catch(Exception e){
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return list;
	}
}
