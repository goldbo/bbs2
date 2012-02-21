package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.CommentDao;
import cn.jsprun.domain.Comment;
import cn.jsprun.utils.HibernateUtil;


public class CommentDaoImpl implements CommentDao {

	public boolean addComment(Comment comment) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean issuccess=false;
		try{
			ts=session.beginTransaction();
			session.save(comment);
			ts.commit();
			issuccess= true;
		}
		catch(HibernateException ex){
			ex.getMessage();
			ts.rollback();
			issuccess=false;
		}
		return issuccess;
	}

	public Comment queryCommentById(Integer id) {
		Comment comment=null;
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			comment=(Comment)session.get(Comment.class,id);
			ts.commit();
		}
		catch(HibernateException ex){
			ts.rollback();
		}
		return comment;
	}

	public List<Comment> queryCommentBythreadId(Integer threadid) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		List<Comment> list=null;
		Transaction ts=null;
		try{
			String hql="from Comment c where c.threadId="+threadid;
			ts=session.beginTransaction();
			Query query=session.createQuery(hql);
			list=(List<Comment>)query.list();
			ts.commit();
		}
		catch(HibernateException ex){
			ts.rollback();
			list=null;
		}
		return list;
	}
    public boolean saveOrUpdate(Comment comment){
    	Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean issuccess=false;
		try{
			ts=session.beginTransaction();
			session.saveOrUpdate(comment);
			ts.commit();
			issuccess= true;
		}
		catch(HibernateException ex){
			System.out.println(ex.getMessage());
			ts.rollback();
			issuccess=false;
		}
		return issuccess;
    }
}
