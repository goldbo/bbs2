package cn.vcity.service;

import cn.vcity.dao.VcityProduceOrderDao;
import cn.vcity.model.VcityProduceOrder;
/**
 * 手机操作的service类
 * 
 * @author hsw
 * @see 
 * @version 1.0
 * @time:2011-9-16 
 * 
 */
public class VcityProduceOrderService {

	private VcityProduceOrderDao vcityProduceOrderDao;

	public VcityProduceOrderDao getVcityProduceOrderDao() {
		return vcityProduceOrderDao;
	}

	public void setVcityProduceOrderDao(VcityProduceOrderDao vcityProduceOrderDao) {
		this.vcityProduceOrderDao = vcityProduceOrderDao;
	}
	
	public boolean saveVcityProduceOrder(VcityProduceOrder entity){
		return vcityProduceOrderDao.saveVcityProduceOrder(entity);
	}
	
}
