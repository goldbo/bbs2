package cn.vcity.service;

import java.util.Iterator;
import java.util.List;

import cn.vcity.dao.VcityResourcesDao;
import cn.vcity.model.VcityAccessTotal;
import cn.vcity.model.VcityDownloadInfo;
import cn.vcity.model.VcityResCommon;
import cn.vcity.model.VcityResType;
import cn.vcity.model.VcityResoprate;
import cn.vcity.model.VcityResource;
import cn.vcity.web.form.VcityResourceForm;
/**
 * 虚拟资源管理的service类
 * @author hsw
 * @see com.wingo.wbase.common.base.BaseDAOHibernate
 * @version 
 * @time:2011-8-29
 *
 */
public class VcityResourcesService {
	
	private VcityResourcesDao vcityResourcesDao;

	public VcityResourcesDao getVcityResourcesDao() {
		return vcityResourcesDao;
	}

	public void setVcityResourcesDao(VcityResourcesDao vcityResourcesDao) {
		this.vcityResourcesDao = vcityResourcesDao;
	}
	
	
	/**
	 * 根据查找最热门D虚拟资源
	 * @param 
	 * @return
	 */	
	public List findHotResources(Integer type){
		
		List resourcesList =vcityResourcesDao.findHotResources(type);
		return resourcesList;
	}
	
	/**
	 * 根据查找最新D虚拟资源
	 * @param 
	 * @return
	 */	
	public List findNewResources(VcityResourceForm vcityResourceFrom){
		String hql="from VcityResource vr where 1=1";
		/*String hql="select new cn.vcity.model.VcityResource (vr.id,vr.resName,vr.resDesc,vr.senceId,vr.resType," +
		"vr.resImage,vr.recommendImage,vr.moduleFileName,vr.resURL,vr.isRecommend,vr.vcityResType,count(vcityAccessTotal.vcityResource.id)) "+
		"from VcityResource vr,VcityAccessTotal vcityAccessTotal left join vr.vcityResType "+
		"where vcityAccessTotal.vcityResource=vr ";*/
		if (vcityResourceFrom.getResType()!=null) {
			hql += " and vr.resType="+vcityResourceFrom.getResType();
		}
		hql += "  order by vr.id desc";
		List resourcesList = vcityResourcesDao.findResourcesByOthers(hql, 0, 10);
		return resourcesList;
		
	}
	
	
	/**
	 * 根据查找推荐高D虚拟资源
	 * @param 
	 * @return
	 */	
	public List findRecommendResources(Integer type){
		String hql="select new cn.vcity.model.VcityResource (vr.id,vr.resName,vr.resDesc,vr.senceId,vr.resType," +
		"vr.resImage,vr.recommendImage,vr.moduleFileName,vr.resURL,vr.isRecommend,count(vcityAccessTotal.accessIp)) "+
		"from VcityResource vr,VcityAccessTotal vcityAccessTotal "+
		"where vcityAccessTotal.vcityResource=vr ";
		//String hql="from VcityResource vr where 1=1";
		if (type!=null&&type!=0) {
			hql += " and vr.resType="+type;
		}
		hql += "group by vcityAccessTotal.vcityResource order by vr.isRecommend desc";
		List resourcesList =vcityResourcesDao.findResourcesByOthers(hql, 0, 1);
		return resourcesList;
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

		return vcityResourcesDao.findVcityResourceByid(id);
	}
	

	public int getTotalByOther(VcityResourceForm vcityResourceForm){
		
		return vcityResourcesDao.getTotalByOther(vcityResourceForm);
	}
	
	
	/**
	 * 保存访问统计
	 * @param VcityAccessTotal
	 * @return boolean
	 */	
	public boolean saveAccessTotal(VcityAccessTotal vcityAccessTotal){
		return vcityResourcesDao.saveAccessTotal(vcityAccessTotal);
	}
	
	/**
	 * 根据typeid查询资源
	 * @param typeid
	 * @return List
	 */		
	public List findResourcesByType(int typeid){
		
		return vcityResourcesDao.findResourcesByType(typeid);
	}

	public int getResComonTotal(Integer resid) {
		return vcityResourcesDao.getResComonTotal(resid);
	}

	public void saveDownloadInfo(VcityDownloadInfo downloadInfo) {
		vcityResourcesDao.saveDownloadInfo(downloadInfo);
		
	}

	public VcityAccessTotal findAccessTotalByDateUserName(String date, String username) {
		return vcityResourcesDao.findAccessTotalByDateUserName(date,username);
	}

	public VcityAccessTotal findAccessTotalByDateIPResid(String date, Integer id, String remoteAddr) {
		return vcityResourcesDao.findAccessTotalByDateIPResid(date,id,remoteAddr);
	}
}
