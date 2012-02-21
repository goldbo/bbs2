package cn.jsprun.foreg.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.CommentDao;
import cn.jsprun.domain.Comment;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.HibernateUtil;

public class CommentService {
	public boolean addComment(Comment comment) {
		return ((CommentDao)BeanFactory.getBean("commentDao")).addComment(comment);
	}

	public Comment queryCommentById(Integer id) {
		return ((CommentDao)BeanFactory.getBean("commentDao")).queryCommentById(id);
	}

	public List<Comment> queryCommentBythreadId(Integer threadid) {
		return ((CommentDao)BeanFactory.getBean("commentDao")).queryCommentBythreadId(threadid);
	}
	public boolean saveOrUpdate(Comment comment){
		return ((CommentDao)BeanFactory.getBean("commentDao")).saveOrUpdate(comment);
	}
}
