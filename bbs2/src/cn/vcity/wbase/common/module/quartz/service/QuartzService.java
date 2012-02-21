package cn.vcity.wbase.common.module.quartz.service;

import cn.vcity.wbase.common.base.BaseServiceImpl;
import cn.vcity.wbase.common.module.quartz.dao.QuartzDAO;
import cn.vcity.wbase.common.module.quartz.model.QrtzCronTriggers;
import cn.vcity.wbase.common.module.quartz.model.QrtzJobDetails;
import cn.vcity.wbase.common.module.quartz.model.QrtzTriggers;
/**
 * 调度器SERVICE
 * @author samuel
 *
 */
public class QuartzService extends BaseServiceImpl {
	private QuartzDAO quartzDAO;

	public void setQuartzDAO(QuartzDAO quartzDAO) {
		this.quartzDAO = quartzDAO;
	}
	
	/**
	 * 查询作业是否存在
	 * @param job_name
	 * @return
	 */
	public boolean getQuartzJob(String job_name){
		return quartzDAO.getQuartzJob(job_name);
	}
	
	/**
	 * 新增调度器作业
	 * @param qrtzJobDetails
	 * @param qrtzTriggers
	 * @param qrtzCronTriggers
	 * @return
	 */
	public String setQuartzJob(QrtzTriggers qrtzTriggers,QrtzCronTriggers qrtzCronTriggers) {
		return quartzDAO.setQuartzJob(qrtzTriggers,qrtzCronTriggers);
	}

	/**
	 * 增加定时任务
	 * @param qrtzJobDetails
	 */
	public void createQuartzJob(QrtzJobDetails qrtzJobDetails) {
		quartzDAO.createQuartzJob(qrtzJobDetails);
	}
	
	/**
	 * 删除调度器作业
	 * @param id
	 * @return
	 */
	public boolean deleteQuartz(String id){
		return quartzDAO.deleteQuartz(id);
	}

}
