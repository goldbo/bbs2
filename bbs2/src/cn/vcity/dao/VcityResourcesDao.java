package cn.vcity.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.utils.HibernateUtil;
import cn.vcity.model.VcityAccessTotal;
import cn.vcity.model.VcityDownloadInfo;
import cn.vcity.model.VcityResType;
import cn.vcity.model.VcityResource;
import cn.vcity.wbase.common.base.BaseDAOHibernate;
import cn.vcity.web.form.VcityResourceForm;

public class VcityResourcesDao extends BaseDAOHibernate {
	protected Class getModelClass() {
		return VcityResource.class;
	}
	/**
	 * 查询最热资源
	 * 
	 * @param  (int first,int max)
	 *           
	 * @return List
	 * @throws Exception
	 */	
	public List findHotResources (Integer type){
		String hqlString="select new cn.vcity.model.VcityResource (vr.id,vr.resName,vr.resDesc,vr.senceId,vr.resType," +
			"vr.resImage,vr.recommendImage,vr.moduleFileName,vr.resURL,vr.isRecommend,count(vcityAccessTotal.accessIp)) "+
			"from VcityResource vr,VcityAccessTotal vcityAccessTotal "+
			"where vcityAccessTotal.vcityResource=vr ";
		if (type!=null&&type!=0) {
			hqlString += " and vr.resType="+type;
		}
		hqlString +=" group by vcityAccessTotal.vcityResource order by count(vcityAccessTotal.accessIp) desc ";
		
		List list =null;
		try {
			list=super.doFind(hqlString,5,0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List findResourcesType(){
		
		return null;
	}
	public List findNewResources(){
//		Session session =null;
//		Query query = null;
//		Transaction tr = null;
//		List<VcityResource> resourceList = new ArrayList<VcityResource>();
//		try {
//			 session = HibernateUtil.getSessionFactory().getCurrentSession();
//			tr = session.beginTransaction();
//			query = session.createSQLQuery(hql);
//			resourceList = query.list();
//			session.flush();
//			tr.commit();
//		} catch (HibernateException he) {
//			if (tr != null)
//				tr.rollback();
//			tr = null;
//			he.printStackTrace();
//		}
		return null;
	}
	/**
	 * 查询资源
	 * 
	 * @param (String hql, int start, int maxrow)
	 *           
	 * @return List
	 * @throws Exception
	 */	
	public List findResourcesByOthers(String hql, int start, int maxrow){
		try {		
			return super.doFind(hql, maxrow, start);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
				
		}
	
	/**
	 * 查询资源总数
	 * 
	 * @param 
	 *           
	 * @return int
	 * @throws Exception
	 */	
	public int getTotalByOther(VcityResourceForm vcityResourceForm){
		StringBuffer query = new StringBuffer();

		query.append("from VcityResource vr left join vr.vcityResType vn where 1=1 \n");
		if (vcityResourceForm.getResName() != null) {
			if(vcityResourceForm.getResName().trim().length()>0){
				query.append(" and vr.resName like '%"
					+ vcityResourceForm.getResName().trim() + "%'");
			}
		}
		if (vcityResourceForm.getProductType() != null) {
			if(vcityResourceForm.getProductType() > 0){
				query.append(" and vn.id ='"
					+ vcityResourceForm.getProductType()+"'");
			}
		}
		
		if(vcityResourceForm.getId()!=null){
			if(vcityResourceForm.getId()>0){
				query.append(" and vr.id='" + vcityResourceForm.getId() + "'");
			}
		}
		if(vcityResourceForm.getResType()!=null){
			if(vcityResourceForm.getResType()>0){
				query.append(" and vr.resType='" + vcityResourceForm.getResType() + "'");
			}
		}
		int total = super.getRowsByHQL("select count(*) " + query.toString());

		return total;
	}
	/**
	 * 根据id查询资源
	 * 
	 * @param id
	 *           
	 * @return VcityResType
	 * @throws Exception
	 */		
	public VcityResource findVcityResourceByid(int id) {

		return (VcityResource)super.doFindObjectById(id);
	}
	/**
	 * 保存访问统计
	 * 
	 * @param VcityAccessTotal
	 *           
	 * @return boolean
	 * @throws Exception
	 */	
	public boolean saveAccessTotal(VcityAccessTotal vcityAccessTotal){
		Boolean flag=false;
		try {
			super.doCreateObject(vcityAccessTotal);
			flag=true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 根据typeid查询资源
	 * 
	 * @param int typeid
	 *           
	 * @return List
	 * @throws Exception
	 */	
	public List findResourcesByType(int typeid){
		String hql = "from VcityResource v where v.vcityResType.id=:resType";
		try {
		List typeList =  super.doFindObjectListByParam(hql, "resType", typeid);
		
			
			return typeList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
				
		}
	public int getResComonTotal(Integer resid) {
		//int total = super.getRowsByHQL("select count(*) " + query.toString());
		return 0;
	}
	public void saveDownloadInfo(VcityDownloadInfo downloadInfo) {
		super.doCreateObject(downloadInfo);
		
	}
	public VcityAccessTotal findAccessTotalByDateUserName(String date, String username) {
		String hql = "from VcityAccessTotal total where total.userName='"+username+"' " +
				"and total.accessTime='"+date+"' and total.vcityResource.id=-1";
		List list = super.doFind(hql);
		if (list!=null) {
			if (list.size()>0) {
				return new VcityAccessTotal();
			}
		}
		return null;
	}
	public VcityAccessTotal findAccessTotalByDateIPResid(String date, Integer id, String remoteAddr) {
		String hql = "from VcityAccessTotal total where total.accessIp='"+remoteAddr+"' " +
		"and total.vcityResource.id="+id+ " and total.accessTime='"+date+"'" ;
		List list = super.doFind(hql);
		if (list!=null) {
			if (list.size()>0) {
				return new VcityAccessTotal();
			}
		}
		return null;
	}
	
	
}
