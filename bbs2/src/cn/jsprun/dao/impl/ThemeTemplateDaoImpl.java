package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.ThemeTemplateDao;
import cn.jsprun.domain.ThemeTemplate;
import cn.jsprun.utils.HibernateUtil;

public class ThemeTemplateDaoImpl implements ThemeTemplateDao {

	public boolean addThemeTemplate(ThemeTemplate template) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try {
			ts=session.beginTransaction();
			session.save(template);
			ts.commit();
			return true;
		} catch (Exception e) {
			if(ts!=null){
				ts.rollback();
			}
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean deleteThemeTemplateById(Integer id) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean delete=false;
		try {
			ts=session.beginTransaction();
			String hql="delete ThemeTemplate where id="+id;
			Query query=session.createQuery(hql);
			delete=query.executeUpdate()>0?true:false;
			ts.commit();
		} catch (Exception e) {
			if(ts!=null){
				ts.rollback();
			}
			delete=false;
			System.out.println(e.getMessage());
		}
		return delete;
	}

	public boolean deleteThemeTemplateByUid(Integer uid) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean delete=false;
		try {
			ts=session.beginTransaction();
			String hql="delete ThemeTemplate where uid="+uid;
			Query query=session.createQuery(hql);
			delete=query.executeUpdate()>0?true:false;
			ts.commit();
		} catch (Exception e) {
			if(ts!=null){
				ts.rollback();
			}
			delete=false;
			System.out.println(e.getMessage());
		}
		return delete;
	}

	public List<ThemeTemplate> findThemeTemplate(String type,int id,int page) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		List<ThemeTemplate> list=null;
		try {
			String hql="from ThemeTemplate tt";
			if("me".equals(type)){
				hql=hql+" where tt.uid="+id;
			}
			if("other".equals(type)){
				hql=hql+" where tt.ispublic=0 and tt.uid<>"+id;
			}
			if("all".equals(type)){
				hql=hql+" where tt.ispublic=0 or tt.uid="+id;
			}
			ts=session.beginTransaction();
			Query query=session.createQuery(hql);
			query.setFirstResult((page-1)*10);
			query.setMaxResults(10);
			list=(List<ThemeTemplate>)query.list();
			ts.commit();
		} catch (Exception e) {
			if(ts!=null){
				ts.rollback();
			}
			System.out.println(e.getMessage());
		}
		return list;
	}
    
	public int findThemeTemplateCount(String type,int id) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try {
			String hql="from ThemeTemplate tt";
			if("me".equals(type)){
				hql=hql+" where tt.uid="+id;
			}
			if("other".equals(type)){
				hql=hql+" where tt.ispublic=0 and tt.uid<>"+id;
			}
			if("all".equals(type)){
				hql=hql+" where tt.ispublic=0 or tt.uid="+id;
			}
			ts=session.beginTransaction();
			Query query=session.createQuery(hql);			
			int count=query.list().size();
			ts.commit();
			return count;
		} catch (Exception e) {
			if(ts!=null){
				ts.rollback();
			}
			System.out.println(e.getMessage());
			return 0;
		}
	}
	
	public ThemeTemplate findThemeTemplateById(int id){
		ThemeTemplate temp=null;
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try {
			ts=session.beginTransaction();
			temp=(ThemeTemplate)session.get(ThemeTemplate.class,id);
			ts.commit();
		} catch (Exception e) {
			if(ts!=null){
				ts.rollback();
			}
			//System.out.println(e.getMessage());
		}
		return temp;
	}
	
	public boolean saveOrUpldate(ThemeTemplate template) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try {
			ts=session.beginTransaction();
			session.saveOrUpdate(template);
			ts.commit();
			return true;
		} catch (Exception e) {
            if(ts!=null){
            	ts.rollback();
            }
            System.out.println(e.getMessage());
            return false;
		}
	}
	public boolean updateThemeTemplate(ThemeTemplate temp){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try {
			ts=session.beginTransaction();
            session.update(temp);
            ts.commit();
            return true;
		} catch (Exception e) {
			if(ts!=null){
				ts.rollback();
			}
			System.out.println(e.getMessage());
			return false;
		}
	}

}
