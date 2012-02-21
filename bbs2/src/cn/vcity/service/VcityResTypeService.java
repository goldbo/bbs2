package cn.vcity.service;

import java.util.List;

import cn.vcity.dao.VcityResTypeDao;
import cn.vcity.model.VcityResType;
import cn.vcity.web.form.VcityResTypeForm;

/**
 * 虚拟资源分类的service类
 * 
 * @author 黄金波 huangjinbo@wingotech.com
 * @see cn.vcity.wbase.common.base.BaseDAOHibernate
 * @version 1.0
 * @time:2011-7-4 17:16:20
 * 
 */
public class VcityResTypeService {

	private VcityResTypeDao vcityResTypeDao;

	public int getTotalByOther(VcityResTypeForm vcityResTypeForm) {

		return vcityResTypeDao.getTotalByOther(vcityResTypeForm);
	}

	public VcityResTypeDao getVcityResTypeDao() {
		return vcityResTypeDao;
	}

	public void setVcityResTypeDao(VcityResTypeDao vcityResTypeDao) {
		this.vcityResTypeDao = vcityResTypeDao;
	}

	public List getVcityResTypeListByOther(VcityResTypeForm vcityResTypeForm,
			int pageSize, int startRow) {

		return vcityResTypeDao.getVcityResTypeListByOther(vcityResTypeForm,
				pageSize, startRow);
	}

	public VcityResType findVcityResTypeByResName(String resType) {
		
		return vcityResTypeDao.findVcityResTypeByResName(resType);
	}

	public boolean saveVcityResType(VcityResType entity) {
		
		return vcityResTypeDao.saveVcityResType(entity);
	}

	public VcityResType findVcityResTypeById(Integer id) {

		return vcityResTypeDao.findVcityResTypeById(id);
	}

	public boolean updateVcityResType(VcityResType entity) {
		
		return vcityResTypeDao.updateVcityResType(entity);
		
	}

	public boolean deleteVcityResTypeByIds(Integer[] ids) {
		
		return vcityResTypeDao.deleteVcityResTypeByIds(ids);
	}

	public boolean deleteVcityResTypeById(Integer id) {
		
		return vcityResTypeDao.deleteVcityResTypeById(id);
	}

	public List findAllVcityResType() {
		return vcityResTypeDao.findAllVcityResType();
		
	}

}
