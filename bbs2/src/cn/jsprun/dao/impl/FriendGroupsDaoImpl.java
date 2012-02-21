package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.FriendGroupsDao;
import cn.jsprun.domain.FriendGroups;
import cn.jsprun.utils.HibernateUtil;

public class FriendGroupsDaoImpl implements FriendGroupsDao{
	
	/**
	 * 根据类型来查询相应的好友组
	 */
    public List<FriendGroups> getFirendGroupByType(int type){
    	List<FriendGroups> list=null;
    	Session session=HibernateUtil.getSessionFactory().getCurrentSession();
    	Transaction ts=null;
    	try{
    		ts=session.beginTransaction();
    		String hql="from FriendGroups f where f.type=:type";
    		Query query=session.createQuery(hql);
    		query.setInteger("type",type);
    		list=query.list();
    		ts.commit();
    	}
    	catch(Exception ex){
    		if(ts!=null){
    			ts.rollback();
    		}
    		System.out.println(ex.getMessage());
    	}
    	return list;
    }
    public boolean addFriendGroups(FriendGroups friend){
    	Session session=HibernateUtil.getSessionFactory().getCurrentSession();
    	Transaction ts=null;
    	boolean issuccess=false;
    	try{
    	    ts=session.beginTransaction();
    	    session.saveOrUpdate(friend);
    	    ts.commit();
    	    issuccess=true;
    	}
    	catch(Exception ex){
    		if(ts!=null){
    			ts.rollback();
    		}
    		System.out.println(ex.getMessage());
    	}
    	
    	return issuccess;
    }
    public boolean deleteFriendGroupsById(String id){
    	Session session=HibernateUtil.getSessionFactory().getCurrentSession();
    	Transaction ts=null;
    	boolean issuccess=false;
    	try {
			ts=session.beginTransaction();
			String hql="delete FriendGroups where id in ("+id+")";
			Query query=session.createQuery(hql);
			issuccess=query.executeUpdate()>0?true:false;
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(ts!=null){
				ts.rollback();
			}
			System.out.println(e.getMessage());
		}
		return issuccess;
    }
    public FriendGroups findFriendGroupsById(int id){
    	Session session=HibernateUtil.getSessionFactory().getCurrentSession();
    	Transaction ts=null;
    	FriendGroups friend=null;
    	try {
			ts=session.beginTransaction();
			friend=(FriendGroups)session.get(FriendGroups.class,id);
			ts.commit();
		} catch (Exception e) {
			// TODO: handle exception
			if(ts!=null){
				ts.rollback();
			}
			System.out.println(e.getMessage());
		}
		return friend;
    }
}
