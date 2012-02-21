package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.SceneDao;
import cn.jsprun.domain.Scene;
import cn.jsprun.utils.HibernateUtil;

public class SceneDaoImpl implements SceneDao {

	public List<Scene> getAllScene() {
		
		List<Scene> list=null;
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			Query query=session.createQuery("from Scene");
			list=query.list();
			ts.commit();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	public boolean saveOrupdateScene(Scene scene){
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			session.saveOrUpdate(scene);
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
	public boolean deleteByUpId(int id) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			String hql="delete Scene s where s.id="+id+" or s.pid="+id;
			Query query=session.createQuery(hql);
			int count= query.executeUpdate();
			ts.commit();
			return count>0?true:false;
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			return false;
		}
	}
	public List<Scene> getSceneByCondition(String hql) {
		Session session=HibernateUtil.getSession();
		Transaction ts=null;
		List<Scene> scene=null;
		try{
			ts=session.beginTransaction();
			Query query=session.createQuery(hql);
			scene=query.list();
			ts.commit();
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.printStackTrace();
		}
		return scene;
	}
	public Scene getSceneById(int id) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		Scene scene=null;
		try{
			ts=session.beginTransaction();
			 scene=(Scene)session.get(Scene.class, id);
			 ts.commit();
		}
		catch(Exception ex){
			if(ts!=null){
				ts.rollback();
			}
			ex.printStackTrace();
		}
		return scene;
	}
}
