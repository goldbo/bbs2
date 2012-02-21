package cn.jsprun.struts.conference.action;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.utils.HibernateUtil;

public class PageUtil{

	public int pageCount(String hql) throws Exception {
		int sql_index = hql.toLowerCase().indexOf("from");
		int sql_orderby = hql.toLowerCase().indexOf("order by");
		String countStr = "";
		if (sql_orderby > 0) {
			countStr = "select count(app.appointmentId) "
					+ hql.substring(sql_index, sql_orderby);
		} else {
			countStr = "select count(app.appointmentId) " + hql.substring(sql_index);
		}
		
		return Integer.parseInt(queryForUniqueObject(countStr).toString());
	}
	
	public Object queryForUniqueObject(String hql) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Object obj = session.createQuery(hql).uniqueResult();
		tx.commit();		
		return obj;
	}

	public void pageList(String hql,Page page)throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery(hql);
		if(page.getCurrentPage()==0)
		{
			page.setCurrentPage(1);
		}
		query.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		page.setDataList(query.list());
		tx.commit();
    }
}
