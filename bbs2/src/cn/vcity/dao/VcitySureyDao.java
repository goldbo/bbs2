package cn.vcity.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.vcity.model.VcityQuestionitem;
import cn.vcity.model.VcitySurey;
import cn.vcity.wbase.common.base.BaseDAOHibernate;
import cn.vcity.web.form.VcitySureyForm;

/**
 * 虚拟资源分类的Dao类
 * 
 * @author LHJ huangjinbo@wingotech.com
 * @see com.wingo.wbase.common.base.BaseDAOHibernate
 * @version 1.0
 * 
 */
public class VcitySureyDao extends BaseDAOHibernate {


	protected Class getModelClass() {
		return VcitySurey.class;
	}

	public int getTotalByOther(VcitySureyForm VcitySureyForm) {

		StringBuffer query = new StringBuffer();
		query.append("from VcitySurey vt where 1=1 \n");
		if (VcitySureyForm.getSureyName() != null&&!VcitySureyForm.getSureyName().equals("")) {
			query.append(" and sureyName like '%"
					+ VcitySureyForm.getSureyName().trim() + "%'");
		}
		if (VcitySureyForm.getCreateUser()!=null&&!VcitySureyForm.getCreateUser().equals("")) {
			query.append(" and vt.createUser ='"+ VcitySureyForm.getCreateUser()+"'");
		}
		int total = super.getRowsByHQL("select count(*) " + query.toString());
		return total;

	}

	public List getVcitySureyListByOther(VcitySureyForm VcitySureyForm,
			int pageSize, int startRow) {
		StringBuffer query = new StringBuffer();
		query.append("from VcitySurey vt where 1=1 \n");
		if (VcitySureyForm.getSureyName() != null&&!VcitySureyForm.getSureyName().equals("")) {
			query.append(" and sureyName like '%"
					+ VcitySureyForm.getSureyName().trim() + "%'");
		}
		if (VcitySureyForm.getCreateUser()!=null&&!VcitySureyForm.getCreateUser().equals("")) {
			query.append(" and vt.createUser ='"+ VcitySureyForm.getCreateUser()+"'");
		}
		query.append(" order by vt.sureyId desc ");
		List datas = super.doFind(query.toString(), pageSize, startRow);
		return datas;
	}

	@SuppressWarnings("unchecked")
	public VcitySurey findVcitySureyByResName(String sureyName) {

		String hql = "from VcitySurey vt where vt.sureyName=:sureyName";

		List<VcitySurey> list = new ArrayList<VcitySurey>();
		List<VcitySurey> doFindObjectListByParam = (List<VcitySurey>) this
				.doFindObjectListByParam(hql, "sureyName", sureyName);
		list = doFindObjectListByParam;
		VcitySurey VcitySurey = null;
		if (list != null && !list.isEmpty()) {
			VcitySurey = (VcitySurey) list.get(0);
		}
		return (VcitySurey) VcitySurey;
	}

	public boolean saveVcitySurey(VcitySurey entity) {
		boolean flag = true;
		try {
			Session session = this.getHibernateTemplate().getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(entity);
			transaction.commit();
		} catch (RuntimeException e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public VcitySurey findVcitySureyById(Integer id) {
		VcitySurey vcitySurey = (VcitySurey)super.doFindObjectById(id);
		return vcitySurey;
	}
	
	public VcitySurey findVcitySureyAndQuestionById(Integer id) {
		VcitySurey vcitySurey = (VcitySurey)super.doFindObjectById(id);
		Hibernate.initialize(vcitySurey.getVcityQuestion());
		this.getHibernateTemplate().getSessionFactory().getCurrentSession().clear();
		return vcitySurey;
	}

	public boolean updateVcitySurey(VcitySurey entity) {
		try {
			super.doUpdateObject(entity);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean deleteVcitySureyByIds(Integer[] ids) {
		boolean bool = false;
		try {
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					this.deleteVcitySureyById(ids[i]);
				}
			}
			bool = true;
		} catch (Exception e) {
			bool = false;
		}
		return bool;
	}

	public boolean deleteVcitySureyById(Integer id) {
		boolean bool = false;
		boolean flag = true;
		try {
			VcitySurey VcitySurey = this.findVcitySureyById(id);

			
			/*List list = vcityResourceDao.findVcityResourceByTypeId(id);
			if (list.size() > 0 || list != null) {

				for (int i = 0; i < list.size(); i++) {
					flag = vcityResourceDao.deleteVcityResourceById((Integer) list.get(i));
				}
			}*/
			if(flag){
				this.doDeleteObject(VcitySurey);
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
		}
		return bool;

	}

	public List findAllVcitySurey() {

		return super.doListAllObject();
	}


	public boolean deleteItemById(Integer itemid) {
		boolean back = true;
		VcityQuestionitem item = (VcityQuestionitem) this.getHibernateTemplate().get(VcityQuestionitem.class, itemid);
		try {
			super.doDeleteObject(item);
		}catch (Exception e) {
			back = false;
		}
		return back;
	}

}
