package cn.vcity.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.vcity.model.VcityQuestionresult;
import cn.vcity.wbase.common.base.BaseDAOHibernate;

/**
 * 问题的Dao类
 * 
 * @author LHJ
 * @see com.wingo.wbase.common.base.BaseDAOHibernate
 * @version 1.0
 * 
 */
public class VcityQuestionresultDao extends BaseDAOHibernate {


	protected Class getModelClass() {
		return VcityQuestionresult.class;
	}


	public boolean saveVcityQuestionresult(VcityQuestionresult entity) {
		return super.doCreateObjectReturn(entity);
	}
	
	public Map findVcityQuestionresultBySureyId(String sureyId,String userName,String subtaskid) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT item.itemId, item.questionId, result.id FROM vcity_questionitem item LEFT OUTER JOIN vcity_questionresult result");
		sql.append("\n ON item.questionId = result.questionId AND item.itemId = result.result");
		sql.append("\n and result.answerTime=(select max(answerTime) FROM vcity_questionitem item2 LEFT OUTER JOIN vcity_questionresult result2");
		sql.append("\n ON item2.questionId = result2.questionId AND item2.itemId = result2.result ");
			/* 可能用户有多次作答，查找最后一次的答案 */
			sql.append("\n WHERE item2.questionId IN (" + sureyId + ")");
			
			if (userName!=null&&!userName.equals("")) {
				sql.append("\n and result2.answerUser ='"+userName+"' ");
			}
			if (subtaskid!=null&&!subtaskid.equals("")) {
				sql.append("\n and result2.subTaskID ='"+subtaskid+"' ");
			}
			sql.append(")");
		
		sql.append("\n WHERE item.questionId IN (" + sureyId + ")");
		if (userName!=null&&!userName.equals("")) {
			sql.append("\n and result.answerUser ='"+userName+"' ");
		}
		
		Query query = session.createSQLQuery(sql.toString());
		List list = query.list();
		Map resultMap = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[])list.get(i);
			Integer itemId = (Integer)obj[0];
			Integer questionId = (Integer)obj[1];
			resultMap.put(questionId.toString() + "," + itemId.toString(), obj[2]);
		}
		return resultMap;
	}

	public VcityQuestionresult findVcityQuestionresultById(Integer id) {
		
		return (VcityQuestionresult) super.doFindObjectById(id);
	}
	
	public boolean updateVcityQuestionresult(VcityQuestionresult entity) {
		try {
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			session.update(entity);
			transaction.commit();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean deleteVcityQuestionresultByIds(Integer[] ids) {
		boolean bool = false;
		try {
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					this.deleteVcityQuestionresultById(ids[i]);
				}
			}
			bool = true;
		} catch (Exception e) {
			bool = false;
		}
		return bool;
	}

	public boolean deleteVcityQuestionresultById(Integer id) {
		boolean bool = false;
		boolean flag = true;
		try {
			VcityQuestionresult VcityQuestionresult = this.findVcityQuestionresultById(id);
			
			if(flag){
				this.doDeleteObject(VcityQuestionresult);
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
		}
		return bool;
		
	}
	
	public List findAllVcityQuestionresult() {
		return super.doListAllObject();
	}

	/**
	 * 同一任务可能有回答多个问卷，查找列表
	 * @param taskId
	 * @param userName
	 * @return 不同的作答时间
	 */
	public List findUserSurveyResultList(Integer taskId, String userName) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT answerTime FROM vcity_questionresult qu ");
		
		sql.append("\n WHERE qu.answerType=2 ");
		if (userName!=null&&!userName.equals("")) {
			sql.append("\n and qu.answerUser ='"+userName+"' ");
		}
		sql.append("group by qu.answerTime");
		Query query = session.createSQLQuery(sql.toString());
		List list = query.list();
		return null;
	}

}
