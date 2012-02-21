package cn.vcity.wbase.common.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * <code>BaseDAOHibernate.java</code>
 * Description:DAO层的抽像基类,所有DAO层接口的实现必须继承该基类.该类继承了Spring提供的HibernateDAOSupport.
 * 实现技术为Hibernate.
 * 
 * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport.
 * @version 1.0
 */

public class BaseDAOHibernate extends HibernateDaoSupport {

	protected Logger logger = Logger.getLogger(getClass());

	/**
	 * 
	 * @return entity class
	 */
	protected Class getModelClass() {
		return null;
	}

	/**
	 * 创建一个新的实体
	 * 
	 * @param entity
	 */
	protected void doCreateObject(Object entity) {

		this.getHibernateTemplate().save(entity);
	}

	/**
	 * 批量保存
	 * @param list
	 * @return
	 */
	protected boolean doBatchCreateObjects(List list){
		try {
			this.getHibernateTemplate().saveOrUpdateAll(list);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 创建一个新的实体
	 * @param entity
	 * @return
	 */
	protected boolean doCreateObjectReturn(Object entity) {
		Object obj = this.getHibernateTemplate().save(entity);
		return obj != null ? true : false;
	}
	
	/**
	 * 创建一个新的实体
	 * @param entity
	 * @return
	 */
	protected Object doCreateObjectReturnId(Object entity) {
		Object obj = this.getHibernateTemplate().save(entity);
		return obj;
	}

	/**
	 * 创建多个实体
	 * 
	 * @param entityList
	 */
	protected void doCreateObjects(List entityList) {

		this.getHibernateTemplate().saveOrUpdateAll(entityList);
	}

	/**
	 * 更新多个实体
	 * 
	 * @param entityList
	 */
	protected void doUpdateObjects(List entityList) {

		this.getHibernateTemplate().saveOrUpdateAll(entityList);
	}

	/**
	 * 更新一个新的实体s
	 * 
	 * @param entity
	 */
	protected void doUpdateObject(Object entity) {

		this.getHibernateTemplate().update(entity);
	}

	/**
	 * 添加或更新一个新的实体
	 * 
	 * @param entity
	 */
	protected void doCreateOrUpdateObject(Object entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 删除一个新的实体
	 * 
	 * @param entity
	 */
	protected void doDeleteObject(Object entity) {

		this.getHibernateTemplate().delete(entity);
	}

	/**
	 * 删除一些实体
	 * 
	 * @param entity
	 */
	protected void doDeleteObjects(List entitys) {

		this.getHibernateTemplate().deleteAll(entitys);
	}

	/**
	 * 批量执行hql语句
	 * 
	 * @param queryString
	 * @return boolean
	 */
	protected boolean doBatchExcute(final String queryString) {
		Boolean b = (Boolean) this.getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createQuery(queryString);
						int retVal = query.executeUpdate();
						return retVal > 0 ? Boolean.TRUE : Boolean.FALSE;
					}

				});
		return b.booleanValue();
	}

	/**
	 * 列出所有的实体
	 * 
	 * @retun List;
	 */
	protected List doListAllObject() {

		List list = this.getHibernateTemplate().find(
				"from " + getModelClass().getName());
		// System.out.println("obj size:"+list.size());
		return list;
	}

	/**
	 * 通过实体的ID查询实体对象
	 * 
	 * @param id
	 * @return Object
	 */
	protected Object doFindObjectById(Integer id) {

		return this.getHibernateTemplate().get(this.getModelClass(), id);
	}

	/**
	 * 通过Long型关键字查询实体对象
	 * 
	 * @param id
	 * @return Object
	 */
	protected Object doFindObjectById(Long id) {

		return this.getHibernateTemplate().get(this.getModelClass(), id);
	}

	/**
	 * 通过实体的UUID查询实体对象
	 * 
	 * @param id
	 * @return Object
	 */
	protected Object doFindObjectById(String id) {

		return this.getHibernateTemplate().get(this.getModelClass(), id);
	}

	/**
	 * 通过实体的属性查询实体对象的List
	 * 
	 * @param queryString
	 *            查询的hql语句，属性使用 ：+属性名称来标示
	 * @param paraNames
	 *            参数名称数组
	 * @param values
	 *            参数值数组
	 * @return Object
	 */
	protected List doFindObjectListByParams(String queryString,
			String[] paraNames, Object[] values) {

		return this.getHibernateTemplate().findByNamedParam(queryString,
				paraNames, values);
	}

	/**
	 * 通过实体的属性查询实体对象的List
	 * 
	 * @param queryString
	 *            查询的hql语句，属性使用 ：+属性名称来标示
	 * @param paraNames
	 *            参数名称
	 * @param values
	 *            参数值
	 * @return Object
	 */
	protected List doFindObjectListByParam(String queryString, String paraName,
			Object value) {

		return this.getHibernateTemplate().findByNamedParam(queryString,
				paraName, value);
	}

	/**
	 * 通过hql语句查询实体对象list
	 * 
	 * @param queryString查询的hql语句
	 * @return 实体对象list
	 */
	protected List doFind(String queryString) {

		return this.getHibernateTemplate().find(queryString);
	}

	/**
	 * 通过hql语句查询实体对象list
	 * 
	 * @param queryString
	 *            查询的hql语句 可以绑定一个条件，使用？来绑定
	 * @param value
	 *            绑定的参数值
	 * @return 实体对象list
	 */
	protected List doFind(String queryString, Object value) {

		return this.getHibernateTemplate().find(queryString, value);
	}

	/**
	 * 通过hql语句查询实体对象list
	 * 
	 * @param queryString
	 *            queryString 查询的hql语句 可以绑定多个条件，使用？来绑定
	 * @param values
	 *            绑定的参数值数组，按照？出现顺序
	 * @return 实体对象list
	 */
	protected List doFind(String queryString, Object[] values) {

		return this.getHibernateTemplate().find(queryString, values);
	}

	/**
	 * 通过hql语句查询实体对象list
	 * 
	 * @param queryString查询的hql语句
	 * @param startRow
	 *            开始行
	 * @param pageSize
	 *            页面记录条数
	 * @return 实体对象list
	 */
	protected List doFind(final String queryString, final int pageSize,
			final int startRow) {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		return hibernateTemplate.executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Query query = session.createQuery(queryString);
				query.setMaxResults(pageSize);
				query.setFirstResult(startRow);
				return query.list();
			}
		});
	}

	/**
	 * 返回第一条记录
	 * 
	 * @param queryString
	 * @return
	 */
	protected List doFind1(final String queryString) {

		HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
		return hibernateTemplate.executeFind(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Query query = session.createQuery(queryString);
				query.setMaxResults(1);
				query.setFirstResult(0);
				return query.list();
			}
		});
	}

	/**
	 * 根据hql语句得到数据的条数. 如<br>
	 * public int getSysDictionaryTypeRows() { String sql = "select count(*)
	 * from SysDictionaryType as d where (d.delFlag='N' or d.delFlag is null or
	 * d.delFlag='')"; int returnValue=super.getRowsByHQL(hql); return
	 * returnValue; }
	 * 
	 * @param hql
	 * @return
	 */
	protected int getRowsByHQL(String hql) {
		List l = new ArrayList();
		int returnValue = 0;
		l = super.getHibernateTemplate().find(hql);
		if (l != null && !l.isEmpty()) {
			 returnValue = ((Integer) l.get(0)).intValue();
			// spring2.5+hibernate3.2.6
			//returnValue = ((Long) l.get(0)).intValue();
		}
		return returnValue;
	}

	protected void doDeleteObjectReal(Object entity) {

		this.getHibernateTemplate().delete(entity);
	}

	/**
	 * 清理数据对象
	 * 
	 */
	protected void cleanUp(Statement st, ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	protected Query createQuery(String hql) {
		return this.getSession().createQuery(hql);
	}
	
	/**
	   * @param sql
	   * @return
	   * Desc:sql查询
	   * @author csw
	   * Sep 3, 2010
	   */
	  protected List doWithSQLQuery(String sql) {
		List list = null;
		Session session = null;
		try {
			session = this.getHibernateTemplate().getSessionFactory()
					.openSession();// this.getSession();
			SQLQuery query = session.createSQLQuery(sql);
			if (query != null) {
				list = query.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	 public List getSQLQuery(String sql) {
	    	List list = null;
	    	Session session = null;
	    	try{
	    		session = getHibernateTemplate().getSessionFactory().openSession();
	    		SQLQuery query = session.createSQLQuery(sql);
	    		if(query != null){
	    			list = query.list();
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		if(session != null){
	    			session.close();
	    		}  		
	    	}
	        return list;
	    }

}