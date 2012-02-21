package cn.jsprun.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.AppointmentDao;
import cn.jsprun.domain.Appointment;
import cn.jsprun.struts.conference.action.Page;
import cn.jsprun.struts.conference.action.PageUtil;
import cn.jsprun.utils.HibernateUtil;

/********会议管理*******2010-11-03*****hcy ***/
public class AppointmentDaoImpl extends PageUtil implements AppointmentDao {

	/**
	 * 添加
	 */
	public boolean addAppointment(Appointment app) {
		
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean issuccess=false;
		try{
			ts=session.beginTransaction();
			session.save(app);
			ts.commit();
			issuccess=true;
		}
		catch(HibernateException ex){
			ex.printStackTrace();
			ts.rollback();
		}
		return issuccess;
	}

	/***
	 * 删除
	 */
	public boolean delAppointment(Appointment app) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean issuccess=false;
		try{
			session.clear();//清除缓存
			//session.refresh(app); //重新加载刷新对象
			ts=session.beginTransaction();
			session.delete(app);
			ts.commit();
			issuccess=true;
		}
		catch(HibernateException ex){
			ex.printStackTrace();
			ts.rollback();
		}
		return issuccess;
	}

	/***
	 * 获取所有数据
	 */
	public List<Appointment> getAppointmentAlls() {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		List<Appointment> appList=null;
		try{
			ts=session.beginTransaction();
			String hql="from Appointment app";
			Query query=session.createQuery(hql);
			appList = (List<Appointment>)query.list();
		}
		catch(HibernateException ex){
           ex.getMessage();
		}
		return appList;
	}

	/***
	 * 根据id查找会议
	 */
	public Appointment getAppointmentById(Integer appId) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		Appointment app=null;
		try{
			ts=session.beginTransaction();
			String hql="from Appointment app where app.appointmentId="+appId;
			Query query=session.createQuery(hql);
			app=(Appointment)query.list().get(0);
		}
		catch(HibernateException ex){
           ex.getMessage();
		}
		return app;
	}

	/***
	 * 根据议员名称 查找该议员相关的会议  分页
	 */
	public void pageAppointmentByUserName(Page page,String userId) {
		try {
			String hql = " from Appointment app where app.userId='"+userId+"' or app.userPid='"+userId+"' order by app.appointmentStarttime desc";
			page.setRecordTotal(pageCount(hql.toString()));
	        pageList(hql.toString(), page);
		} catch (Exception e) {
			e.printStackTrace();
			page.setRecordTotal(0);
		}
	}

	/**
	 * 更新会议
	 */
	public boolean updateAppointment(Appointment app) {
		Session session=HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction ts=null;
		boolean issuccess=false;
		try{
			ts=session.beginTransaction();
			session.saveOrUpdate(app);
			ts.commit();
			issuccess=true;
		}
		catch(HibernateException ex){
			ts.rollback();
		}
		return issuccess;
	}
	
	/*************
	 * 内部jsp分页
	 * @param page
	 * @param email
	 */
	public void findAppointAppointmentByCondition(Page page) {
		try {
			String hql = "from Appointment app order by app.appointmentStarttime desc";
			page.setRecordTotal(pageCount(hql.toString()));
	        pageList(hql.toString(), page);
		} catch (Exception e) {
			e.printStackTrace();
			page.setRecordTotal(0);
		}
	}
	
	/***
	 * 查询最新的会议
	 * @param page
	 * @return
	 */
	public List findAppointmentByStartTime(){
		//获取当天时间的第一个时间（如：00:00:01）
		Date startDate = new Date();
		//startDate.setHours(0);
		//startDate.setMinutes(0);
		//startDate.setSeconds(1);
		Timestamp startStamp = new Timestamp(startDate.getTime());
		String hql = "from Appointment app where app.appointmentEndtime >= '"+startStamp+"' and  app.isEnd=0 order by app.appointmentStarttime desc";

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		List list = query.list();
		tx.commit();
		return list;
	}
	
	/****
	 * 当前时间以前的会议列表(历史会议)
	 * @param page
	 */
	public void findAppointmentByCondition_1(Page page){
		try {
			//获取当天时间的第一个时间（如：00:00:01）
			Date startDate = new Date();
			//startDate.setHours(0);
			//startDate.setMinutes(0);
			//startDate.setSeconds(1);
			Timestamp startStamp = new Timestamp(startDate.getTime());
			
			String hql = "from Appointment app where app.appointmentEndtime < '"+startStamp+"' or app.isEnd=1 order by app.appointmentStarttime desc";
			
			page.setRecordTotal(pageCount(hql.toString()));
			
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			if(page.getCurrentPage()==0)
			{
				page.setCurrentPage(1);
			}
			query.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize());
			query.setMaxResults(page.getPageSize());
			
			
			//query.setTimestamp("starttime", startStamp);
			page.setDataList(query.list());
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			page.setRecordTotal(0);
		}
	}
}
