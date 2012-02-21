package cn.vcity.service;

import java.util.List;

import cn.vcity.dao.VcitySureyDao;
import cn.vcity.model.VcitySurey;
import cn.vcity.web.form.VcitySureyForm;

/**
 * 虚拟资源分类的service类
 * 
 * @author LHJ huangjinbo@wingotech.com
 * @see cn.vcity.wbase.common.base.BaseDAOHibernate
 * @version 1.0
 * @time:2011-7-4 17:16:20
 * 
 */
public class VcitySureyService {

	private VcitySureyDao VcitySureyDao;

	public int getTotalByOther(VcitySureyForm vcitySureyForm) {

		return VcitySureyDao.getTotalByOther(vcitySureyForm);
	}

	public VcitySureyDao getVcitySureyDao() {
		return VcitySureyDao;
	}

	public void setVcitySureyDao(VcitySureyDao VcitySureyDao) {
		this.VcitySureyDao = VcitySureyDao;
	}

	public List getVcitySureyListByOther(VcitySureyForm VcitySureyForm,
			int pageSize, int startRow) {

		return VcitySureyDao.getVcitySureyListByOther(VcitySureyForm,
				pageSize, startRow);
	}

	public VcitySurey findVcitySureyByResName(String resType) {
		
		return VcitySureyDao.findVcitySureyByResName(resType);
	}

	public boolean saveVcitySurey(VcitySurey entity) {
		
		return VcitySureyDao.saveVcitySurey(entity);
	}

	public VcitySurey findVcitySureyById(Integer id) {
		
		return VcitySureyDao.findVcitySureyById(id);
	}
	
	public VcitySurey findVcitySureyAndQuestionById(Integer id) {

		return VcitySureyDao.findVcitySureyAndQuestionById(id);
	}
	
	public boolean updateVcitySurey(VcitySurey entity) {
		
		return VcitySureyDao.updateVcitySurey(entity);
		
	}

	public boolean deleteVcitySureyByIds(Integer[] ids) {
		
		return VcitySureyDao.deleteVcitySureyByIds(ids);
	}

	public boolean deleteVcitySureyById(Integer id) {
		
		return VcitySureyDao.deleteVcitySureyById(id);
	}

	public List findAllVcitySurey() {
		return VcitySureyDao.findAllVcitySurey();
		
	}


	public boolean deleteItemById(Integer itemid){
		return VcitySureyDao.deleteItemById(itemid);
	}
}
