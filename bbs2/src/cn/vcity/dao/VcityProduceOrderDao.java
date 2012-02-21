package cn.vcity.dao;

import cn.vcity.model.VcityProduceOrder;
import cn.vcity.wbase.common.base.BaseDAOHibernate;
/**
 * 手机操作的Dao类
 * 
 * @author hsw
 * @see com.wingo.wbase.common.base.BaseDAOHibernate
 * @version 1.0
 * 
 */
public class VcityProduceOrderDao extends BaseDAOHibernate {
//	private VcityResourceDao vcityResourceDao;

	protected Class getModelClass() {
		return VcityProduceOrder.class;
	}

	public boolean saveVcityProduceOrder(VcityProduceOrder entity ){
		boolean flag = false;
		try {
			super.doCreateObject(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
}
