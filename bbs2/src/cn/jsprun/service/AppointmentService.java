package cn.jsprun.service;

import java.util.List;

import cn.jsprun.dao.AppointmentDao;
import cn.jsprun.domain.Appointment;
import cn.jsprun.struts.conference.action.Page;
import cn.jsprun.utils.BeanFactory;

/********会议管理*******2010-11-03*****hcy ***/
public class AppointmentService {

	/****
	 * 查询所有
	 * @return
	 */
	public List<Appointment> getAppointmentAlls(){
		return ((AppointmentDao)BeanFactory.getBean("appointmentDao")).getAppointmentAlls();
	}
	
	/***
	 * 根据编号查询
	 * @param appId
	 * @return
	 */
	public Appointment getAppointmentById(Integer appId){
		return ((AppointmentDao)BeanFactory.getBean("appointmentDao")).getAppointmentById(appId);
	}
	
	/***
	 * 根据用户查询分页
	 * @param appId
	 * @return
	 */
	public void pageAppointmentByUserName(Page page,String userId){
		((AppointmentDao)BeanFactory.getBean("appointmentDao")).pageAppointmentByUserName(page,userId);
	}
	
	/***
	 * 添加
	 * @param app
	 * @return
	 */
	public boolean addAppointment(Appointment app){
		return ((AppointmentDao)BeanFactory.getBean("appointmentDao")).addAppointment(app);
	}
	
	/***
	 * 修改
	 * @param app
	 * @return
	 */
	public boolean updateAppointment(Appointment app){
		return ((AppointmentDao)BeanFactory.getBean("appointmentDao")).updateAppointment(app);
	}
	
	/****
	 * 删除
	 * @param app
	 * @return
	 */
	public boolean delAppointment(Appointment app){
		return ((AppointmentDao)BeanFactory.getBean("appointmentDao")).delAppointment(app);
	}
	
	/********
	 * 分页
	 * @param page
	 */
	public void findAppointAppointmentByCondition(Page page) {
		((AppointmentDao)BeanFactory.getBean("appointmentDao")).findAppointAppointmentByCondition(page);
	}
	
	/****
	 * 当前时间以前的会议列表
	 * @param page
	 */
	public void findAppointmentByCondition_1(Page page){
		((AppointmentDao)BeanFactory.getBean("appointmentDao")).findAppointmentByCondition_1(page);
	}
	
	/***
	 * 查询最新的会议
	 * @param page
	 * @return
	 */
	public List findAppointmentByStartTime(){
		return ((AppointmentDao)BeanFactory.getBean("appointmentDao")).findAppointmentByStartTime();
	}
}
