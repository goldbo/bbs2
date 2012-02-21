package cn.jsprun.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.MeetingMemberDao;
import cn.jsprun.domain.MeetingMember;
import cn.jsprun.utils.HibernateUtil;

/********会议成员管理*******2010-11-03*****hcy ***/
public class MeetingMemberDaoImpl implements MeetingMemberDao {

	/**
	 * 添加
	 */
	public boolean addMeetingMember(MeetingMember mm) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean issuccess=false;
		try{
			ts=session.beginTransaction();
			session.save(mm);
			ts.commit();
			issuccess=true;
		}
		catch(HibernateException ex){
			ts.rollback();
		}
		return issuccess;
	}

	/**
	 * 删除
	 */
	public boolean deleteMeetingMember(MeetingMember mm) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean issuccess=false;
		try{
			ts=session.beginTransaction();
			session.delete(mm);
			ts.commit();
			issuccess=true;
		}
		catch(HibernateException ex){
			ts.rollback();
		}
		return issuccess;
	}

	/****
	 * 根据会议编号，查找会议人员
	 */
	public List<MeetingMember> getMeetingMemberByAppId(Integer appId) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		List<MeetingMember> mmList=null;
		try{
			ts=session.beginTransaction();
			String hql="from MeetingMember mm where mm.appointmentId="+appId;
			Query query=session.createQuery(hql);
			mmList = (List<MeetingMember>)query.list();
		}
		catch(HibernateException ex){
           ex.getMessage();
		}
		return mmList;
	}
	
	/****
	 * 根据会议编号和用户编号  查找参加会议的对象
	 * @param appId
	 * @param userId
	 * @return
	 */
	public MeetingMember getMeetingMemberByAppId(Integer appId,Integer userId) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		MeetingMember mm =null;
		try{
			ts=session.beginTransaction();
			String hql="from MeetingMember mm where mm.appointmentId="+appId+" and mm.userId="+userId;
			Query query=session.createQuery(hql);
			List list = query.list();
			if(list!=null)
				if(list.size()!=0)
					mm = (MeetingMember)list.get(0);
		}
		catch(HibernateException ex){
           ex.getMessage();
		}
		return mm;
	}

	/***
	 * 根据会议编号及相应条件来查找相关的集合
	 * @param appId
	 * @return
	 */
	public List<MeetingMember> getMeetingMemberList(int appId,Integer isregister,Integer iskickout) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			String hql="from MeetingMember mm where mm.appointmentId="+appId;
			if(isregister!=null)
				hql += "and mm.isregister="+isregister.intValue();
			if(iskickout!=null)
				hql += "and mm.iskickout="+iskickout.intValue();
			Query query=session.createQuery(hql);
			List list = query.list();
            return  list;
		}
		catch(HibernateException ex){
           ex.getMessage();
           return null;
		}
	}
	
	/***
	 * 修改
	 */
	public boolean updateMeetingMember(MeetingMember mm) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean issuccess=false;
		try{
			ts=session.beginTransaction();
			session.saveOrUpdate(mm);
			ts.commit();
			issuccess=true;
		}
		catch(HibernateException ex){
			ts.rollback();
		}
		return issuccess;
	}
	
	/***
	 * 根据id参会者信息
	 */
	public MeetingMember getMeetingMemberById(Integer mmId) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		MeetingMember mm=null;
		try{
			ts=session.beginTransaction();
			String hql="from MeetingMember app where app.meetingMemberId="+mmId;
			Query query=session.createQuery(hql);
			mm=(MeetingMember)query.list().get(0);
		}
		catch(HibernateException ex){
           ex.getMessage();
		}
		return mm;
	}
}
