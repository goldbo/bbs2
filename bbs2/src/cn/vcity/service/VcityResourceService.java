package cn.vcity.service;

import java.util.List;

import cn.vcity.dao.VcityResourceDao;
import cn.vcity.model.VcityResource;
import cn.vcity.web.form.VcityResourceForm;
import cn.vcity.wbase.common.base.BaseServiceImpl;

/**
 * 虚拟资源管理的service类
 * @author 黄金波  huangjinbo@wingotech.com
 * @see com.wingo.wbase.common.base.BaseDAOHibernate
 * @version 1.0
 * @time:2011-7-1 17:49:20
 *
 */
public class VcityResourceService extends BaseServiceImpl {
	
	/**
	 * Spring IOC注入
	 */
	private VcityResourceDao vcityResourceDao;

	/**
	 * 添加一个虚拟资源
	 * @param entity
	 * @return
	 */
	public boolean saveVcityResource(VcityResource entity){
		return vcityResourceDao.saveVcityResource(entity);
	}
	
	public VcityResourceDao getVcityResourceDao() {
		return vcityResourceDao;
	}

	public void setVcityResourceDao(VcityResourceDao vcityResourceDao) {
		this.vcityResourceDao = vcityResourceDao;
	}

	public int getTotalByOther(VcityResourceForm vcityResourceForm) {
		
		return vcityResourceDao.getTotalByOther(vcityResourceForm) ;
	}

	/**
	 * 条件查询虚拟资源列表
	 * @param vcityResourceForm
	 * @param pageSize
	 * @param startRow
	 * @return
	 */
	public List getVcityResourceListByOther(VcityResourceForm vcityResourceForm, int pageSize, int startRow) {
		
		return vcityResourceDao.getVcityResourceListByOther(vcityResourceForm,pageSize,startRow);
	}

	/**
	 * 根据ID查找虚拟资源
	 * @param id
	 * @return
	 */
	public VcityResource findVcityResourceById(Integer id) {
		
		return vcityResourceDao.findVcityResourceById(id);
	}

	public VcityResource findVcityResourceByResName(String resName) {
		
		return  vcityResourceDao.findVcityResourceByResName(resName);
	}

	public boolean deleteVcityResourceByIds(String[] ids) {
	
		return vcityResourceDao.deleteVcityResourceByIds(ids);
	}

	public void updateVcityResource(VcityResource entity) {
		vcityResourceDao.updateVcityResource(entity);
		
	}

	public boolean deleteVcityResourceById(Integer id) {

		return vcityResourceDao.deleteVcityResourceById(id);
	}
	
	public List findResourceByTaskId(int taskId){
		return vcityResourceDao.findResourceByTaskId(taskId);
	}
	public List findSurveyByTaskId(int taskId){
		return vcityResourceDao.findSurveyByTaskId(taskId);
	}
	/**
	 * 批量保存
	 * @param list
	 * @return
	 */
	public boolean saveBatchObjects(List list){
		return vcityResourceDao.saveBatchObjects(list);
	}
/*	public boolean updateVcityResourceUpOrDown(Integer id,String type){
		return vcityResourceDao.updateVcityResourceUpOrDown(id, type);
	}*/

	public List findResourceOther(Integer taskId) {
		return vcityResourceDao.findResourceOther(taskId);
	}

	public List findSurveyOther(Integer taskId) {
		return vcityResourceDao.findSurveyOther(taskId);
	}

	public List getUserSubListByTaskId(int taskId, String userName) {
		return vcityResourceDao.getUserSubListByTaskId(taskId,userName);
	}

	public List getUserStep(String subTaskResId, String userName) {
		return vcityResourceDao.getUserStep(subTaskResId,userName);
	}
	
	public List getUserSueryResult(Integer subid,String resid,String userName){
		
		return null;
	}

	public List findResourceAll(VcityResourceForm vcityResourceForm) {
		return vcityResourceDao.findResourceAll(vcityResourceForm);
	}
}
