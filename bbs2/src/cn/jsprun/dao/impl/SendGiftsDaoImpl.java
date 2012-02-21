package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.SendGiftsDao;
import cn.jsprun.domain.SendGifts;
import cn.jsprun.utils.HibernateUtil;
import cn.jsprun.vo.Sendgifts_sr;

public class SendGiftsDaoImpl implements SendGiftsDao {
	public boolean saveorupdate(List<SendGifts> list) {
		boolean issuccess = true;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			for (SendGifts s : list) {
				session.saveOrUpdate(s);
			}
			ts.commit();
		} catch (Exception e) {
			if (ts != null) {
				ts.rollback();
			}
			issuccess = false;
			e.printStackTrace();
		}
		return issuccess;
	}

	public List<Sendgifts_sr> getListByid(String hql,int first,int max) {
		List<Sendgifts_sr> list=null;
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try {			
			ts=session.beginTransaction();
			Query query=session.createQuery(hql);
			if(max!=0){
				query.setFirstResult(first*max);
				query.setMaxResults(max);
			}
			list=(List<Sendgifts_sr>)query.list();
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(ts!=null){
				ts.rollback();
			}
			e.printStackTrace();
		}
		return list;
	}
}
