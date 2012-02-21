package cn.vcity.wbase.common.module.quartz.job;

import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class BackupTaskJob implements Job {
	Log _log = LogFactory.getLog(BackupTaskJob.class);
	//private SysMrgService sysMrgService = (SysMrgService) SpringHelper
	//.getBean("sysMrgService");
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String triggerName[] = context.getTrigger().getName().split("_");
//		String packageId = triggerName[0];
//		String flag = sysMrgService.excBackupDB();
		_log.info("backup database ready go...");
		//String info = sysMrgService.autoBackupDB();
		//_log.info(sdf.format(new Date().getTime())+info);
	}

}
