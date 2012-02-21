package cn.vcity.wbase.common.module.quartz.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.vcity.wbase.common.base.BaseDAOHibernate;
import cn.vcity.wbase.common.module.quartz.model.QrtzCronTriggers;
import cn.vcity.wbase.common.module.quartz.model.QrtzJobDetails;
import cn.vcity.wbase.common.module.quartz.model.QrtzTriggers;

/**
 * 调度器DAO
 * 
 * @author samuel
 */

public class QuartzDAO extends BaseDAOHibernate {
	private static final Log log = LogFactory.getLog(QuartzDAO.class);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void createQuartzJob(QrtzJobDetails qrtzJobDetails) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = super.getSession().connection();

			String jobSql = "INSERT INTO qrtz_job_details(job_name, job_group, job_class_name, is_durable, is_volatile, is_stateful, requests_recovery) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?) ";
			stmt = conn.prepareStatement(jobSql);
			stmt.setString(1, qrtzJobDetails.getJob_name());
			stmt.setString(2, qrtzJobDetails.getJob_group());
			stmt.setString(3, qrtzJobDetails.getJob_class_name());
			stmt.setInt(4, qrtzJobDetails.getIs_durable());
			stmt.setInt(5, qrtzJobDetails.getIs_volatile());
			stmt.setInt(6, qrtzJobDetails.getIs_stateful());
			stmt.setInt(7, qrtzJobDetails.getRequests_recovery());
			stmt.executeUpdate();
			conn.commit();
			
			log.info(qrtzJobDetails.getJob_name()+"作业新增成功!" + sdf.format(new Date().getTime()));

		} catch (Exception ex) {
			log.info(qrtzJobDetails.getJob_name()+"作业新增成功!" + sdf.format(new Date().getTime()));
			try {
				conn.rollback();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			ex.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public String setQuartzJob(QrtzTriggers qrtzTriggers, QrtzCronTriggers qrtzCronTriggers) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String flag = "";
		try {
			conn = super.getSession().connection();
			
			String delCronSql = "DELETE FROM qrtz_cron_triggers WHERE TRIGGER_NAME=? ";
			stmt = conn.prepareStatement(delCronSql);
			stmt.setString(1, qrtzTriggers.getTrigger_name());
			stmt.executeUpdate();
			conn.commit();

			String delTriggerSql = "DELETE FROM qrtz_triggers where TRIGGER_NAME =? ";
			stmt = conn.prepareStatement(delTriggerSql);
			stmt.setString(1, qrtzTriggers.getTrigger_name());
			stmt.executeUpdate();
			conn.commit();

			String triggerSql = "INSERT INTO qrtz_triggers (trigger_name, trigger_group, job_name, job_group, is_volatile, next_fire_time, trigger_state,trigger_type, start_time, misfire_instr) "
					+ " VALUES (?,?, ?, ?, ?,?, ?,?, ?, ?)";
			stmt = conn.prepareStatement(triggerSql);
			stmt.setString(1, qrtzTriggers.getTrigger_name());
			stmt.setString(2, qrtzTriggers.getTrigger_group());
			stmt.setString(3, qrtzTriggers.getJob_name());
			stmt.setString(4, qrtzTriggers.getJob_group());
			stmt.setInt(5, qrtzTriggers.getIs_volatile());
			stmt.setInt(6, qrtzTriggers.getNext_fire_time());
			stmt.setString(7, qrtzTriggers.getTrigger_state());
			stmt.setString(8, qrtzTriggers.getTrigger_type());
			stmt.setInt(9, qrtzTriggers.getStart_time());
			stmt.setInt(10, qrtzTriggers.getMisfire_instr());
			stmt.executeUpdate();
			conn.commit();

			String cronSql = "INSERT INTO qrtz_cron_triggers (TRIGGER_NAME , TRIGGER_GROUP , CRON_EXPRESSION , TIME_ZONE_ID ) "
					+ " VALUES (?, ?, ?, ?) ";
			stmt = conn.prepareStatement(cronSql);
			stmt.setString(1, qrtzCronTriggers.getTrigger_name());
			stmt.setString(2, qrtzCronTriggers.getTrigger_group());
			stmt.setString(3, qrtzCronTriggers.getCron_expression());
			stmt.setString(4, qrtzCronTriggers.getTime_zone_id());
			stmt.executeUpdate();
			conn.commit();
			
			flag = "计划任务设置成功！";
			log.info(qrtzTriggers.getJob_name()+"计划任务设置成功!" + sdf.format(new Date().getTime()));

		} catch (Exception ex) {
			flag = "计划任务设置失败！";
			log.info(qrtzTriggers.getJob_name()+"计划任务设置失败!" + sdf.format(new Date().getTime()));
			try {
				conn.rollback();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			ex.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return flag;
	}

	public boolean deleteQuartz(String id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			conn = super.getSession().connection();
			String cronSql = "DELETE FROM qrtz_cron_triggers WHERE TRIGGER_NAME=? ";
			stmt = conn.prepareStatement(cronSql);
			stmt.setString(1, id);
			stmt.executeUpdate();

			String triggerSql = "DELETE FROM qrtz_triggers where TRIGGER_NAME =? ";
			stmt = conn.prepareStatement(triggerSql);
			stmt.setString(1, id);
			stmt.executeUpdate();
			
			flag =true;
			log.info("计划任务删除成功!" + sdf.format(new Date().getTime()));

		} catch (Exception ex) {
			flag = false;
			log.info("计划任务删除失败!" + sdf.format(new Date().getTime()));
			try {
				conn.rollback();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			ex.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return flag;
	}
	
	public boolean getQuartzJob(String job_name){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean flag = false;
		try {
			conn = super.getSession().connection();

			String jobSql = "SELECT * FROM qrtz_job_details WHERE  job_name=? ";
			stmt = conn.prepareStatement(jobSql);
			stmt.setString(1, job_name);
			rs=stmt.executeQuery();
			if(rs.next()){
				flag =true;
				log.info("计划任务设置成功!" + sdf.format(new Date().getTime()));
			}else{
				flag = false;
			}
		} catch (Exception ex) {
			flag = false;
			log.info("计划任务设置失败!" + sdf.format(new Date().getTime()));
			try {
				conn.rollback();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			ex.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return flag;
	}
}