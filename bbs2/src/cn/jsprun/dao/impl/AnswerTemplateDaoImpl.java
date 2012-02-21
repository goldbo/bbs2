package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.AnswerTemplateDao;
import cn.jsprun.domain.AnswerTemplate;
import cn.jsprun.utils.HibernateUtil;

public class AnswerTemplateDaoImpl implements AnswerTemplateDao {

	public boolean addTemplate(AnswerTemplate at) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean issuccess=false;
		try{
			ts=session.beginTransaction();
			session.save(at);
			ts.commit();
			issuccess=true;
		}
		catch(HibernateException ex){
			ts.rollback();
		}
		return issuccess;
	}
	public boolean updateTemplate(AnswerTemplate at) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean issuccess=false;
		try{
			ts=session.beginTransaction();
			session.update(at);
			ts.commit();
			issuccess=true;
		}
		catch(HibernateException ex){
			ts.rollback();
		}
		return issuccess;
	}
	public boolean deleteTemplate(int id) {
		// TODO Auto-generated method stub
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean isdelete=false;
		try{
			ts=session.beginTransaction();
			AnswerTemplate at=this.getAnswerTemplateById(id);
			if(at!=null){
				session.delete(at);
			}
		}
		catch(HibernateException ex){
			
		}
		return false;
	}

	public AnswerTemplate getAnswerTemplateByCreator(int creator) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		AnswerTemplate at=null;
		try{
			ts=session.beginTransaction();
			String hql="from AnswerTemplate where creator="+creator;
			Query query=session.createQuery(hql);
			at=(AnswerTemplate)query.list().get(0);
		}
		catch(HibernateException ex){
           ex.getMessage();
		}
		return at;
	}

	public AnswerTemplate getAnswerTemplateById(int id) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		AnswerTemplate at=null;
		try{
			ts=session.beginTransaction();			
			at=(AnswerTemplate)session.get(AnswerTemplate.class,id);
		}
		catch(HibernateException ex){
           ex.getMessage();
		}
		return at;
	}

}
