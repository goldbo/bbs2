package cn.vcity.service;

import java.util.List;

import cn.vcity.dao.VcityResourceAppDao;
import cn.vcity.model.VcityResourceApp;

public class VcityResourceAppService {
	
	private VcityResourceAppDao vcityResourceAppDao;
	
	public boolean saveVcityResourceApp(VcityResourceApp entity){
		
		return vcityResourceAppDao.saveVcityResourceApp(entity);
	}

	public List<VcityResourceApp> findResAppByResID(Integer resId) {
		return vcityResourceAppDao.findResAppByResID(resId);
	}

	public VcityResourceAppDao getVcityResourceAppDao() {
		return vcityResourceAppDao;
	}

	public void setVcityResourceAppDao(VcityResourceAppDao vcityResourceAppDao) {
		this.vcityResourceAppDao = vcityResourceAppDao;
	}

	public void deleteResAppById(Integer id) {
		vcityResourceAppDao.deleteResAppById(id);
		
	}

	public boolean deleteResAppEntity(VcityResourceApp app) {
		return vcityResourceAppDao.deleteResAppEntity(app);
	}
}
