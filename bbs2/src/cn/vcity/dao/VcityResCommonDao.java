package cn.vcity.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.domain.Faqs;
import cn.jsprun.utils.HibernateUtil;
import cn.vcity.model.VcityAccessTotal;
import cn.vcity.model.VcityResCommon;
import cn.vcity.model.VcityResoprate;
import cn.vcity.wbase.common.base.BaseDAOHibernate;
import cn.vcity.web.form.VcityResourceForm;

/**
 * 产品资源评价Dao类
 * 
 * @author hsw
 * @version 
 * @time:
 * 
 */

public class VcityResCommonDao extends BaseDAOHibernate {
	
	protected Class getModelClass() {
		return VcityResCommon.class;
	}	
	

	
	public List<VcityResoprate> findAllVcityresoprateList(){
		
		return super.doListAllObject();
	}
	/**
	 * 保存点评
	 * 
	 * @author hsw
	 * @version 
	 * @time:2011-8-25
	 * 
	 */	
	public boolean saveVcityResCommon(VcityResCommon entity) {
		boolean flag = false;
			try {
				super.doCreateObject(entity);
				flag = true;
			} catch (Exception e) {
				flag = false;
				e.printStackTrace();
				}
//			}
//		System.out.println(entity.getResId()+"-"+entity.getGrade()+"-"+entity.getCreateTime());
//		return super.doCreateObjectReturn(entity);
		return flag;
	}
	public boolean savaCommon(VcityResCommon entity){
		boolean flag=false;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tr = null;
		try{
			tr = session.beginTransaction();
			session.save(entity);
			tr.commit();
			flag = true ;
		}catch(Exception e){
			if(tr!=null){
				tr.rollback();
			}
			flag = false ;
			e.printStackTrace();
		}	
		return flag;
	}
	
//	public VcityResoprate findVcityResoprateByName(String resoprateName) {
//		
//		String hql = "from VcityResoprate vr where vr.resoprateName=:resoprateName";
//
//		List<VcityResoprate> list = new ArrayList<VcityResoprate>();
//		 list = (List<VcityResoprate>) this.doFindObjectListByParam(hql, "resoprateName", resoprateName);
//		VcityResoprate vcityResoprate = null;
//		if (list != null && !list.isEmpty()) {
//			vcityResoprate = (VcityResoprate) list.get(0);
//		}
//		return (VcityResoprate) vcityResoprate;
//	}
	
	public List showResCommonList(int resid,int size,int start){
		String hql="from VcityResCommon common where common.grade=0 and common.vcityResource.id="+resid;
		List commonList = new ArrayList();
		try {
			commonList =super.doFind(hql,size,start);
		} catch (Exception e) {
			e.printStackTrace();
			}
		return commonList;
	}
	
	public List showResCommonListByOther(VcityResourceForm vcityResourceForm, int pageSize, int startRow,int resid){
		String hql="from VcityResCommon common where common.grade=0 and common.vcityResource.id="+resid+ "  order by common.id desc";
		List commonList = new ArrayList();
		try {
			commonList =super.doFind(hql, pageSize, startRow);
		} catch (Exception e) {
			e.printStackTrace();
			}
		return commonList;
	}
	/**
	 * 获取点评数
	 * 
	 * @author hsw
	 * @version 
	 * @time:2011-8-25
	 * 
	 */		
	public int getTotalByResourceId(int resId,int grade) {

		StringBuffer query = new StringBuffer();
		query.append("from VcityResCommon vc where vc.vcityResource.id=");
		query.append(resId);
		if (grade==0) {
			query.append(" and vc.grade=0");
		}
		if (grade==1) {
			query.append(" and vc.grade=1 ");
		}
		if (grade==2) {
			query.append(" and vc.grade=2 ");
		}
		int total = super.getRowsByHQL("select count(*) " + query.toString());
		return total;

	}


}
