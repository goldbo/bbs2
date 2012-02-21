package cn.jsprun.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.hql.ast.QuerySyntaxException;

import cn.jsprun.dao.RecyclebinDao;
import cn.jsprun.domain.Threadsmod;
import cn.jsprun.service.DataBaseService;
import cn.jsprun.struts.form.RecyclebinForm;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.utils.Common;
import cn.jsprun.utils.HibernateUtil;

public class RecyclebinDaoImple implements RecyclebinDao {
	public Integer deleteDayOld(Integer dayold,String path) {
		int count = 0;
		int time = Common.time()-dayold*86400;
		List list = null;
		StringBuffer querySB = new StringBuffer( "select t.tid from Threads as t where t.displayorder =-1 ");
		Transaction tr = null;
		Query query = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			if (dayold == 0) {
				tr = session.beginTransaction();
				query = session.createQuery(querySB.toString());
				list = query.list();
				tr.commit();
			}else if (dayold > 0) {
				String sql = "select t.tid from Threads as t, Threadsmod as mod where t.tid=mod.id.tid and mod.id.action = 'DEL' and mod.id.dateline <= :moddateline and t.displayorder=-1";
					list = new ArrayList();
					tr = session.beginTransaction();
					query = session.createQuery(sql);
					query.setParameter("moddateline", time);
					list = query.list();
					tr.commit();
					count = list.size();
			}
		} catch (HibernateException he) {
			if (tr != null && tr.isActive())
				tr.rollback();
			tr = null;
			he.printStackTrace();
			count = 0;
		}

		if (list != null && list.size() > 0) {
			count = list.size();
			DataBaseService dataBaseService=(DataBaseService) BeanFactory.getBean("dataBaseService");
			for(int i=0;i<count;i++){
				List<Map<String,String>> attachlist = dataBaseService.executeQuery("select attachment,thumb,remote from jrun_attachments where tid="+list.get(i));
				if(attachlist!=null && attachlist.size()>0){
					for(Map<String,String> attach: attachlist){
						Common.dunlink(attach.get("attachment"),Byte.valueOf(attach.get("thumb")),Byte.valueOf(attach.get("remote")), path);
					}
				}
			}
			dataBaseService=null;
			deleteArray(list);
		}
		return count;
	}

	public String findByAll(RecyclebinForm recyclebinForm,String timeoffset) {
		StringBuffer querStr = new StringBuffer("FROM jrun_threads as t LEFT JOIN jrun_posts as p ON p.tid=t.tid AND p.first='1' LEFT JOIN jrun_threadsmod as m ON t.tid=m.tid LEFT JOIN jrun_forums as f ON t.fid=f.fid WHERE t.displayorder=-1");
			if (recyclebinForm.getInforum() > 0) {
				querStr.append(" AND t.fid = "+recyclebinForm.getInforum());
			}
			if (recyclebinForm.getAuthors() != null&& !recyclebinForm.getAuthors().equals("")) {
				String[] authors = recyclebinForm.getAuthors().split(",");
				String author = "";
				for(int i=0;i<authors.length;i++){
					author += ",'"+Common.addslashes(authors[i])+"'";
				}
				if(author.length()>0){
					author = author.substring(1);
				}
				querStr.append(" AND t.author in ("+author+")");
			}

			if (recyclebinForm.getKeywords() != null&& !recyclebinForm.getKeywords().equals("")) {
				String[]keywords = recyclebinForm.getKeywords().split(","); 
				String sqlkeywords =""; String or = "";
				for(int i=0;i<keywords.length;i++){
					sqlkeywords += or+"t.subject LIKE '%"+Common.addslashes(keywords[i])+"%'";
					or = " OR ";
				}
				querStr.append(" AND ("+sqlkeywords+")");
			}

			if (recyclebinForm.getAdmins() != null&& !recyclebinForm.getAdmins().equals("")) {
				String[] authors = recyclebinForm.getAdmins().split(",");
				String author = "";
				for(int i=0;i<authors.length;i++){
					author += ",'"+Common.addslashes(authors[i])+"'";
				}
				if(author.length()>0){
					author = author.substring(1);
				}
				querStr.append(" AND m.username in ("+author+")");
			}

			if (Common.datecheck(recyclebinForm.getPstarttime())) {
				int time = Common.dataToInteger(recyclebinForm.getPstarttime(),"yyyy-MM-dd",timeoffset);
				querStr.append(" AND t.dateline > "+time);
			}

			if (Common.datecheck(recyclebinForm.getPendtime())) {
				int time = Common.dataToInteger(recyclebinForm.getPendtime(),"yyyy-MM-dd",timeoffset);
				querStr.append(" AND t.dateline < "+time);
			}

			if (Common.datecheck(recyclebinForm.getMstarttime())) {
				int time = Common.dataToInteger(recyclebinForm.getMstarttime(),"yyyy-MM-dd",timeoffset);
				querStr.append(" AND m.dateline > "+time);
			}
			if (Common.datecheck(recyclebinForm.getMendtime())) {
				int time = Common.dataToInteger(recyclebinForm.getMendtime(),"yyyy-MM-dd",timeoffset);
				querStr.append(" AND m.dateline < "+time);
			}
			querStr.append(" GROUP BY t.tid ORDER BY t.dateline DESC ");
		return querStr.toString();
	}
	public Integer deleteArray(List deletelist) {
		Integer num = 0;
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			try {
				Query query = session.createQuery("select pid from Posts as p where p.tid in (:tids)");
				query.setParameterList("tids", deletelist,new org.hibernate.type.IntegerType());
				List list = query.list();
				if(list!=null && list.size()>0){
					query = session.createQuery("delete from Ratelog as r where r.id.pid in (:pids)");
					query.setParameterList("pids", list,new org.hibernate.type.IntegerType());
					query.executeUpdate();
				}
				query = session.createQuery("delete from Threads as t where t.tid in (:tids)");
				query.setParameterList("tids", deletelist,new org.hibernate.type.IntegerType());
				num = query.executeUpdate();
				query = session.createQuery("delete from Posts as p where p.tid in (:tids)");
				query.setParameterList("tids", deletelist,new org.hibernate.type.IntegerType());
				query.executeUpdate();
				query = session.createQuery("delete from Threadsmod as m where m.id.tid in (:tids)");
				query.setParameterList("tids", deletelist,new org.hibernate.type.IntegerType());
				query.executeUpdate();
				query = session.createQuery("delete from Polloptions as a where a.tid in (:tids)");
				query.setParameterList("tids", deletelist,new org.hibernate.type.IntegerType());
				query.executeUpdate();
				query = session.createQuery("delete from Polls as a where a.tid in (:tids)");
				query.setParameterList("tids", deletelist,new org.hibernate.type.IntegerType());
				query.executeUpdate();
				query = session.createQuery("delete from Rewardlog as a where a.id.tid in (:tids)");
				query.setParameterList("tids", deletelist,new org.hibernate.type.IntegerType());
				query.executeUpdate();
				query = session.createQuery("delete from Attachments as a where a.tid in (:tids)");
				query.setParameterList("tids", deletelist,new org.hibernate.type.IntegerType());
				query.executeUpdate();
				query = session.createQuery("delete from Trades as a where a.id.tid in (:tids)");
				query.setParameterList("tids", deletelist,new org.hibernate.type.IntegerType());
				query.executeUpdate();
				query = session.createQuery("delete from Myposts as a where a.id.tid in (:tids)");
				query.setParameterList("tids", deletelist,new org.hibernate.type.IntegerType());
				query.executeUpdate();
				
				tr.commit();
			} catch (QuerySyntaxException qu) {
				qu.printStackTrace();
			}
		} catch (HibernateException he) {
			if (tr != null && tr.isActive())
				tr.rollback();
			tr = null;
			he.printStackTrace();
			num = 0;

		}
		return num;
	}

	public int undeleteArray(List<Threadsmod> updatelist) {
		Integer num = 0;
		Integer[] tids = new Integer[updatelist.size()];
		Transaction tr = null;
		try {
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			tr = session.beginTransaction();
			try {
				for (int i = 0; i < updatelist.size(); i++) {
					tids[i] = updatelist.get(i).getId().getTid();
					session.save(updatelist.get(i));
				}
		
				Query query = session.createQuery("update Threads as t set t.displayorder=0,t.moderated=1 where t.tid in (:tids)");
				query.setParameterList("tids", tids);
				num = query.executeUpdate();
		
				query = session.createQuery("update Posts as p set p.invisible = 0 where p.tid in (:tids)");
				query.setParameterList("tids", tids);
				query.executeUpdate();
				tr.commit();
			} catch (QuerySyntaxException qu) {
				qu.printStackTrace();
			}
		} catch (HibernateException he) {
			if (tr != null)
				tr.rollback();
			tr = null;
			he.printStackTrace();
			num = 0;
		}
		return num;
	}
}
