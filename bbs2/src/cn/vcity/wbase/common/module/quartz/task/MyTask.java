package cn.vcity.wbase.common.module.quartz.task;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

import cn.vcity.wbase.common.helper.SpringHelper;
//import cn.vcity.wbase.service.ReportService;

public class MyTask extends TimerTask {
/*
	private ReportService reportService = (ReportService) SpringHelper
			.getBean("reportService");
*/
	public void run() {
		/*Date dd = new Date();
		try {
			reportService.onAmline();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("MyTask 正在执行..." + dd.getHours() + ":"
				+ dd.getMinutes() + ":" + dd.getSeconds());
				*/
	}

}
