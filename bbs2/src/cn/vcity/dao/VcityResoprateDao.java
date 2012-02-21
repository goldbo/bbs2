package cn.vcity.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.utils.HibernateUtil;
import cn.vcity.model.VcityResType;
import cn.vcity.model.VcityResoprate;
import cn.vcity.model.VcityResource;
import cn.vcity.wbase.common.base.BaseDAOHibernate;
import cn.vcity.web.form.VcityResTypeForm;
import cn.vcity.web.form.VcityResoprateForm;

public class VcityResoprateDao extends BaseDAOHibernate {
	
	protected Class getModelClass() {
		return VcityResoprate.class;
	}	
	

	
	public List<VcityResoprate> getVcityResoprateList (){
		StringBuffer sql = new StringBuffer();
		sql.append("from VcityResoprate vc left join vc.resoprateName ");
		List list = super.doFind(sql.toString());
		return list;
		
	}
	public List<VcityResoprate> findAllVcityresoprateList(){
		
		return super.doListAllObject();
	}
	
	public boolean saveVcityResoprate(VcityResoprate entity) {
		boolean flag = false;
		if (entity != null) {
//			Session session = null;
//			Transaction tran = null;
//			entity.setId(10);
			try {
//				session = HibernateUtil.getSessionFactory().getCurrentSession();
//				tran = session.beginTransaction();
//				session.save(entity);
				 super.doCreateObjectReturn(entity);
				flag = true;
//				tran.commit();
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
//				if(tran!=null){
//					tran.rollback();
				}
				
			}
		
		return flag;
//		return super.doCreateObjectReturn(entity);
	}

	public VcityResoprate findVcityResoprateById(int id) {

		return (VcityResoprate) super.doFindObjectById(id);
	}
	public List getVcityResoprateListByOther(VcityResoprateForm vcityResoprateForm,
			int pageSize, int startRow) {
		StringBuffer query = new StringBuffer();
		query.append("from VcityResoprate vr where 1=1 \n");
		if (vcityResoprateForm.getResoprateName() != null) {
			query.append(" and resoprateName like '%"
					+ vcityResoprateForm.getResoprateName().trim() + "%'");
		}
		
		List datas = super.doFind(query.toString(), pageSize, startRow);

		return datas;
	}

	public boolean updateVcityResoprate(VcityResoprate entity) {
		try {
			super.doUpdateObject(entity);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean deleteVcityResoprateByIds(int[] ids) {
		boolean bool = false;
		try {
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					this.deleteVcityResoprateById(ids[i]);
				}
			}
			bool = true;
		} catch (Exception e) {
			bool = false;
		}
		return bool;
	}
	
	public boolean deleteVcityResoprateById(int id){
		boolean flag = false ;
		VcityResoprate resoprate =(VcityResoprate) super.doFindObjectById(id);
		try {
			super.doDeleteObject(resoprate);
			flag = true ;
		} catch (Exception e) {
			// TODO: handle exception
			flag = false ;
		}		
		return flag;
		
	}
	
	public VcityResoprate findVcityResoprateByName(String resoprateName) {
		
		String hql = "from VcityResoprate vr where vr.resoprateName=:resoprateName";

		List<VcityResoprate> list = new ArrayList<VcityResoprate>();
		 list = (List<VcityResoprate>) this.doFindObjectListByParam(hql, "resoprateName", resoprateName);
		VcityResoprate vcityResoprate = null;
		if (list != null && !list.isEmpty()) {
			vcityResoprate = (VcityResoprate) list.get(0);
		}
		return (VcityResoprate) vcityResoprate;
	}
	
	public int getTotal() {

		StringBuffer query = new StringBuffer();
		query.append("from VcityResoprate vr where 1=1 \n");
		int total = super.getRowsByHQL("select count(*) " + query.toString());
		return total;

	}
}
