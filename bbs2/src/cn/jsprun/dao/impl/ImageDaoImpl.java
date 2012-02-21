package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.ImageDao;
import cn.jsprun.domain.Image;
import cn.jsprun.utils.HibernateUtil;

public class ImageDaoImpl implements ImageDao {

	public boolean addImage(Image image) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean isadd=false;
		try{
			ts=session.beginTransaction();
			session.saveOrUpdate(image);
			ts.commit();
			isadd=true;
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.printStackTrace();
		}
		return isadd;
	}

	public boolean deleteImage(int id) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean isdelete=false;
		try{
			ts=session.beginTransaction();
			String hql="delete from Image where id="+id;
			Query query=session.createQuery(hql);
			isdelete=query.executeUpdate()>0?true:false;
			ts.commit();
		}
		catch(HibernateException ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.getMessage();
		}
		return isdelete;
	}

	public List<Image> getAllImage(int type) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		List<Image> list=null;
		try{
			ts=session.beginTransaction();
			String hql="from Image ";
			//当type为1的时候，就查询那些status=1的数据（即查询设置的状态为显示的数据）
			if(type==1){
				hql=hql+"where status=1";
			}
			Query query=session.createQuery(hql);
			list=query.list();
			ts.commit();
		}
		catch(HibernateException ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.getMessage();
		}
		return list;
	}

	public boolean updateImage(Image image) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean isupdate=false;
		try{
			ts=session.beginTransaction();
			session.update(image);
			ts.commit();
			isupdate=true;
		}
		catch(HibernateException ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.getMessage();
		}
		return isupdate;
	}

	public Image getImageById(int id) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		Image image=null;
		try{
			ts=session.beginTransaction();
			image=(Image)session.get(Image.class,id);
			ts.commit();
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.printStackTrace();
		}
		return image;
	}

}
