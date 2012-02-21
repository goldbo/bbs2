package cn.vcity.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.vcity.wbase.common.base.BaseDAOHibernate;

public class StatisticsDao extends BaseDAOHibernate {
	private static final Log log = LogFactory.getLog(StatisticsDao.class);
	
	/**
	 * 调查结果统计
	 * @param surveyId
	 * @return
	 */
	public List getCountPeopleList(String questionIds,int surveyId) {
		StringBuffer sb = new StringBuffer("select count(distinct answerUser) ,substring(answertime,1,10) ");
			sb.append("from vcity_questionresult ");
				sb.append("where 1=1 ");
				sb.append(" and questionId in("+questionIds+")");
				//sb.append(" and subtaskid="+surveyId);
				sb.append(" group by substring(answertime,1,10)");
				System.out.println(sb);
		return super.getSQLQuery(sb.toString());
	}
	
	/**
	 * 社区访问IP统计
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List getAccessTotalList(String beginTime, String endTime) {
		StringBuffer sb = new StringBuffer("select accessTime,count(distinct accessIp),count(accessIp) ");
			sb.append("from vcity_accesstotal ");
			sb.append("where 1=1 ");
			if (beginTime!=null&&!beginTime.equals("")) {
				sb.append(" and accessTime>='"+beginTime+"'");
			}
			if (endTime!=null&&!endTime.equals("")) {
				sb.append(" and accessTime<='"+endTime+"'");
			}
			sb.append(" group by accessTime");
		return super.getSQLQuery(sb.toString());
	}

	/**
	 * 得到时间段内的访问日期列表
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List getLoginTimeList(String beginTime, String endTime) {
		StringBuffer sb = new StringBuffer("SELECT DISTINCT accessTime FROM vcity_accesstotal ");
		sb.append(" where userName IS NOT NULL AND userName <>'' and resId=-1 ");
		if (beginTime!=null&&!beginTime.equals("")) {
			sb.append(" and accessTime>='"+beginTime+"'");
		}
		if (endTime!=null&&!endTime.equals("")) {
			sb.append(" and accessTime<='"+endTime+"'");
		}
		sb.append(" ORDER BY accessTime DESC ");
		return super.getSQLQuery(sb.toString());
	}

	/**
	 * 根据访问日期得到登录用户列表
	 * @param loginTime
	 * @return
	 */
	public List getLoginMemberList(String loginTime) {
		StringBuffer sb = new StringBuffer("SELECT DISTINCT userName,accessIp,accessTime FROM vcity_accesstotal ");
		sb.append(" WHERE userName IS NOT NULL AND userName <>'' and resId=-1 ");
		if (loginTime!=null&&!loginTime.equals("")) {
			sb.append(" AND accessTime='"+loginTime+"' ");
		}
		return super.getSQLQuery(sb.toString());
	}

	/**
	 * 得到时间段内访问IP列表
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List getIPList(String beginTime, String endTime) {
		StringBuffer sb = new StringBuffer("select distinct accessIp ");
		sb.append("from vcity_accesstotal ");
		sb.append("where accessIp is not null and accessIp<>'' ");
		if (beginTime!=null&&!beginTime.equals("")) {
			sb.append(" and accessTime>='"+beginTime+"'");
		}
		if (endTime!=null&&!endTime.equals("")) {
			sb.append(" and accessTime<='"+endTime+"'");
		}
		return super.getSQLQuery(sb.toString());
	}

	public List getDownloadTotal(String beginTime, String endTime, String resType) {
		StringBuffer sb = new StringBuffer("SELECT  vr.resname,COUNT(down.resId) FROM vcity_downloadinfo down ");
		sb.append("LEFT JOIN vcity_vresources vr ON vr.resId=down.resId ");
		sb.append("WHERE vr.resType='"+resType+"' ");
		

		if (beginTime!=null&&!beginTime.equals("")) {
			sb.append(" and downloadTime>='"+beginTime+"'");
		}
		if (endTime!=null&&!endTime.equals("")) {
			sb.append(" and downloadTime<='"+endTime+"'");
		}
		sb.append(" GROUP BY down.resId ");
		return super.getSQLQuery(sb.toString());
	}

	public List getPraiseDegreeTotal(String beginTime, String endTime, String resType) {
		
		StringBuffer sql = new StringBuffer("SELECT a.resName,SUM(a.a1) as 'up',SUM(a.a2) as 'down' FROM (");
			sql.append("SELECT vr.resName,COUNT(com.resid) AS 'a1',0 AS 'a2' " +
				"FROM vcity_rescommon com,vcity_vresources vr WHERE com.resid = vr.resId " +
				"AND com.class=1 ");
			if (beginTime!=null&&!beginTime.equals("")) {
				sql.append(" and com.createTime>='"+beginTime+"'");
			}
			if (endTime!=null&&!endTime.equals("")) {
				sql.append(" and com.createTime<='"+endTime+"'");
			}
			sql.append(" AND vr.resType="+resType+" GROUP BY com.resid ");
			sql.append(" UNION ");
				
			sql.append("SELECT vr.resName,0 AS 'a1',COUNT(com.resid) AS 'a2' " +
					"FROM vcity_rescommon com,vcity_vresources vr WHERE com.resid = vr.resId " +
					"AND com.class=2 ");
				if (beginTime!=null&&!beginTime.equals("")) {
					sql.append(" and com.createTime>='"+beginTime+"'");
				}
				if (endTime!=null&&!endTime.equals("")) {
					sql.append(" and com.createTime<='"+endTime+"'");
				}
				sql.append(" AND vr.resType="+resType+" GROUP BY com.resid ");
				sql.append(") as a "); 
				sql.append(" GROUP BY a.resName");
				System.out.println(sql);
				

		return super.getSQLQuery(sql.toString());
	}

	public List getLoginMemberList(String beginTime, String endTime) {
		
		StringBuffer sb = new StringBuffer("SELECT DISTINCT userName,accessIp,accessTime FROM vcity_accesstotal ");
		sb.append(" WHERE userName IS NOT NULL AND userName <>'' and resId=-1");
		if (beginTime!=null&&!beginTime.equals("")) {
			sb.append(" AND accessTime>='"+beginTime+"' ");
		}
		if (endTime!=null&&!endTime.equals("")) {
			sb.append(" AND accessTime<='"+endTime+"' ");
		}
		return super.getSQLQuery(sb.toString());
	}	
}
