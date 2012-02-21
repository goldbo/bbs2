package cn.vcity.service;

import java.util.List;

import cn.vcity.dao.StatisticsDao;


public class StatisticsService {
	private StatisticsDao statisticsDao;

	public StatisticsDao getStatisticsDao() {
		return statisticsDao;
	}

	public void setStatisticsDao(StatisticsDao statisticsDao) {
		this.statisticsDao = statisticsDao;
	}

	public List getCountPeopleList(String questionIds,int surveyId) {
		return statisticsDao.getCountPeopleList(questionIds,surveyId);
	}

	public List getAccessTotalList(String beginTime, String endTime) {
		return statisticsDao.getAccessTotalList(beginTime,endTime);
	}

	public List getLoginTimeList(String beginTime, String endTime) {
		return statisticsDao.getLoginTimeList(beginTime,endTime);
	}

	public List getLoginMemberList(String loginTime) {
		return statisticsDao.getLoginMemberList(loginTime);
	}

	public List getIPList(String beginTime, String endTime) {
		return statisticsDao.getIPList(beginTime,endTime);
	}

	public List getDownloadTotal(String beginTime, String endTime, String resType) {
		return statisticsDao.getDownloadTotal(beginTime,endTime,resType);
	}

	public List getPraiseDegreeTotal(String beginTime, String endTime, String resType) {
		return statisticsDao.getPraiseDegreeTotal(beginTime,endTime,resType);
	}

	public List getLoginMemberList(String beginTime, String endTime) {
		return statisticsDao.getLoginMemberList(beginTime,endTime);
	}
}
