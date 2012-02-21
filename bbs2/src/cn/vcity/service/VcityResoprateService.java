package cn.vcity.service;

import java.util.List;

import cn.vcity.dao.VcityResoprateDao;
import cn.vcity.model.VcityResoprate;
import cn.vcity.web.form.VcityResTypeForm;
import cn.vcity.web.form.VcityResoprateForm;

/**
 * 作者：hsw
 * @return 
 * cn.vcity.service.serviceTODO
 * 功能：resoprate业务
 * 时间：2011-8-23
 */
public class VcityResoprateService {

	private VcityResoprateDao vcityResoprateDao ;



	public VcityResoprateDao getVcityResTypeDao() {
		return vcityResoprateDao;
	}

	public void setvcityResoprateDao(VcityResoprateDao vcityResoprateDao) {
		this.vcityResoprateDao = vcityResoprateDao;
	}
	/**
	 * 保存resoprate
	 * @param 
	 * @return
	 */

	public boolean saveVcityResoprate(VcityResoprate entity) {
		
		return vcityResoprateDao.saveVcityResoprate(entity);
	}
	/**
	 * 根据ID查找resoprate
	 * @param 
	 * @return
	 */
	public VcityResoprate findVcityResoprateById(int id) {

		return vcityResoprateDao.findVcityResoprateById(id);
	}
	/**
	 * 修改resoprate
	 * @param 
	 * @return
	 */
	public boolean updateVcityResoprate(VcityResoprate entity) {
		
		return vcityResoprateDao.updateVcityResoprate(entity);
		
	}

	/**
	 * 查找全部resoprate
	 * @param 
	 * @return
	 */
	public List findAllVcityResoprate() {
		return vcityResoprateDao.getVcityResoprateList();
		
		
	}
	public List getVcityResoprateListByOther(VcityResoprateForm vcityResoprateForm,
			int pageSize, int startRow) {

		return vcityResoprateDao.getVcityResoprateListByOther(vcityResoprateForm, pageSize, startRow);
	}	
	/**
	 * 通过name查找resoprate
	 * @param 
	 * @return
	 */	
	public VcityResoprate findVcityResoprateByName(String name){
		return vcityResoprateDao.findVcityResoprateByName(name);
		
	}
	
	public boolean deleteVcityResoprateByIds(int[] ids) {
		
		return vcityResoprateDao.deleteVcityResoprateByIds(ids);
	}

	public boolean deleteVcityResoprateById(int id) {
		
		return vcityResoprateDao.deleteVcityResoprateById(id);
	}
	public int getTotal(){
		
		return vcityResoprateDao.getTotal();
	}
}
