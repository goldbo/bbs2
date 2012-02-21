package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.Appointment;
import cn.jsprun.struts.conference.action.Page;

/********会议管理*******2010-11-03*****hcy ***/
public interface AppointmentDao {

	/****
	 * 查询所有
	 * @return
	 */
	public List<Appointment> getAppointmentAlls();
	
	/***
	 * 根据编号查询
	 * @param appId
	 * @return
	 */
	public Appointment getAppointmentById(Integer appId);
	
	/***
	 * 根据用户查询分页
	 * @param appId
	 * @return
	 */
	public void pageAppointmentByUserName(Page page,String userId);
	
	/***
	 * 添加
	 * @param app
	 * @return
	 */
	public boolean addAppointment(Appointment app);
	
	/***
	 * 修改
	 * @param app
	 * @return
	 */
	public boolean updateAppointment(Appointment app);
	
	/****
	 * 删除
	 * @param app
	 * @return
	 */
	public boolean delAppointment(Appointment app);
	
	/***
	 * 分页
	 * @param page
	 */
	public void findAppointAppointmentByCondition(Page page);
	
	/****
	 * 当前时间以前的会议列表
	 * @param page
	 */
	public void findAppointmentByCondition_1(Page page);
	
	/***
	 * 查询最新的会议
	 * @param page
	 * @return
	 */
	public List findAppointmentByStartTime();
}
