package cn.vcity.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import cn.vcity.dao.VcityResCommonDao;
import cn.vcity.model.VcityAccessTotal;
import cn.vcity.model.VcityResCommon;
import cn.vcity.model.VcityResource;
import cn.vcity.wbase.common.helper.SpringHelper;
import cn.vcity.web.form.VcityResourceForm;
/**
 * 产品资源评价Service类
 * 
 * @author hsw
 * @version 
 * @time:
 * 
 */
public class VcityResCommonService {
	private VcityResourcesService vcityResourcesService;
	private VcityResourceService vcityResourceService;
	private VcityResCommonDao vcityResCommonDao;

	public VcityResCommonDao getVcityResCommonDao() {
		return vcityResCommonDao;
	}
	public void setVcityResCommonDao(VcityResCommonDao vcityResCommonDao) {
		this.vcityResCommonDao = vcityResCommonDao;
	}
	/**
	 * 保存点评
	 * 
	 * @author hsw
	 * @version 
	 * @time:2011-8-25
	 * 
	 */	
	public boolean saveVcityResCommon(int resId,String ip,String userName,int grade){
			
		String time =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		VcityResCommon vcityResCommon =new VcityResCommon();
		VcityResource vcityResource = new VcityResource();
		vcityResource.setId(resId);
		vcityResCommon.setVcityResource(vcityResource);
		vcityResCommon.setIp(ip);
		vcityResCommon.setGrade(grade);
		vcityResCommon.setCreateTime(time);
		vcityResCommon.setCommon("");
		vcityResCommon.setUserName(userName);
		return vcityResCommonDao.saveVcityResCommon(vcityResCommon);
		
	}
	public boolean saveVcityResCommon(VcityResCommon vcityResCommon){
		/*VcityResource vcityResource2 =vcityResourceService.findVcityResourceById(vcityResCommon.getVcityResource().getId());
		VcityAccessTotal vcityAccessTotal =new VcityAccessTotal();
		vcityAccessTotal.setAccessTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		vcityAccessTotal.setAccessIp(vcityResCommon.getIp());
		vcityAccessTotal.setVcityResource(vcityResource2);
		vcityResourcesService.saveAccessTotal(vcityAccessTotal);*/
		return vcityResCommonDao.saveVcityResCommon(vcityResCommon);
		
	}
	/**
	 * 根据资源id获取点评
	 * 
	 * @author hsw
	 * @version 
	 * @time:2011-9-20
	 * 
	 */	
	public List showVcityResComonList(int resid,int size,int start){
		List commonList=vcityResCommonDao.showResCommonList(resid,size,start);
		return commonList;
	}
	public List showResCommonListByOther(VcityResourceForm vcityResourceForm, int pageSize, int startRow,int resid){
		List commonList=vcityResCommonDao.showResCommonListByOther(vcityResourceForm, pageSize, startRow, resid);
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
	public int getResCommonTotal(int resId,int grade ){
		
		return vcityResCommonDao.getTotalByResourceId(resId, grade);
	}

	public VcityResourceService getVcityResourceService() {
		return vcityResourceService;
	}

	public void setVcityResourceService(VcityResourceService vcityResourceService) {
		this.vcityResourceService = vcityResourceService;
	}

	public VcityResourcesService getVcityResourcesService() {
		return vcityResourcesService;
	}

	public void setVcityResourcesService(VcityResourcesService vcityResourcesService) {
		this.vcityResourcesService = vcityResourcesService;
	}


}
