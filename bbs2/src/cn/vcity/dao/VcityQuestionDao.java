package cn.vcity.dao;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.vcity.model.VcityQuestion;
import cn.vcity.wbase.common.base.BaseDAOHibernate;

/**
 * 问题的Dao类
 * 
 * @author LHJ huangjinbo@wingotech.com
 * @see com.wingo.wbase.common.base.BaseDAOHibernate
 * @version 1.0
 * 
 */
public class VcityQuestionDao extends BaseDAOHibernate {


	protected Class getModelClass() {
		return VcityQuestion.class;
	}


	public boolean saveVcityQuestion(VcityQuestion entity) {
		return super.doCreateObjectReturn(entity);
		/*Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		Object obj = session.save(entity);
		transaction.commit();
		session.close();
		return obj != null ? true : false;*/
	}

	public VcityQuestion findVcityQuestionById(Integer id) {
		
		return (VcityQuestion) super.doFindObjectById(id);
	}

	public List findVcityQuestionByIds(String ids) {
		
		return  super.doFind("from VcityQuestion  v where v.questionId in ("+ids+")");
	}
	
	public List findVcityQuestionBySureyId(Integer sureyId) {
		
		return  super.doFindObjectListByParam("from VcityQuestion  v where v.vcitySurey.sureyId = :sureyId order by v.questIndex" ,"sureyId", sureyId  );
	}
	
	public List findAllTemplateVcityQuestion() {
		return  super.doFind("from VcityQuestion  v where v.isTemplate = 1 order by v.questionId ",10,0 );
	}

	public boolean updateVcityQuestion(VcityQuestion entity) {
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
	
	public boolean updateVcityQuestionIndex(VcityQuestion entity,String upOrDown) {
		Session session = this.getHibernateTemplate().getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		transaction.begin();
		String sql = "",currentSql = "";
		Object[] args = new Object[]{},currentArgs = new Object[]{};
		if(upOrDown.equalsIgnoreCase("DOWN")){
			sql = "update VcityQuestion as q set q.questIndex = q.questIndex - 1 where q.vcitySurey.sureyId = {0} and q.questIndex = {1} ";
			currentSql = "update VcityQuestion as q set q.questIndex = q.questIndex + 1 where q.questionId = {0} ";
			args =  new Object[]{entity.getVcitySurey().getSureyId(),entity.getQuestIndex() + 1};
			currentArgs = new Object[]{entity.getQuestionId()};
		}else if (upOrDown.equalsIgnoreCase("UP")){
			sql = "update VcityQuestion as q set q.questIndex = q.questIndex + 1 where q.vcitySurey.sureyId = {0} and q.questIndex = {1} ";
			currentSql = "update VcityQuestion as q set q.questIndex = q.questIndex - 1 where q.questionId = {0} ";
			args = new Object[]{entity.getVcitySurey().getSureyId(),entity.getQuestIndex() - 1};
			currentArgs = new Object[]{entity.getQuestionId()};
		}
		Query query = session.createQuery(MessageFormat.format(sql, args).toString());
		Query currentQuery = session.createQuery(MessageFormat.format(currentSql, currentArgs).toString());
		query.executeUpdate();
		currentQuery.executeUpdate();
		transaction.commit();
		return true;
	}

	public boolean deleteVcityQuestionByIds(Integer[] ids) {
		boolean bool = false;
		try {
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					this.deleteVcityQuestionById(ids[i]);
				}
			}
			bool = true;
		} catch (Exception e) {
			bool = false;
		}
		return bool;
	}

	public boolean deleteVcityQuestionById(Integer id) {
		boolean bool = false;
		boolean flag = true;
		try {
			VcityQuestion VcityQuestion = this.findVcityQuestionById(id);
			
			if(flag){
				this.doDeleteObject(VcityQuestion);
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
		}
		return bool;
		
	}
	
	public boolean setQuestionTemplate(Integer id) {
		boolean flag = true;
		try {
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			String sql = "update VcityQuestion as q set q.isTemplate = 1 where q.questionId = " + id;
			Query query = session.createQuery(sql);
			query.executeUpdate();
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public List findAllVcityQuestion() {
		return super.doListAllObject();
	}


	public boolean deleteQuestionTemplate(Integer id) {
		boolean flag = true;
		try {
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			String sql = "update VcityQuestion as q set q.isTemplate = 0 where q.questionId = " + id;
			Query query = session.createQuery(sql);
			query.executeUpdate();
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

}
