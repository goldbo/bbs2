package cn.jsprun.service;

import java.util.List;

import cn.jsprun.dao.ScenectOrderDao;
import cn.jsprun.domain.ScenectOrder;
import cn.jsprun.utils.BeanFactory;

public class ScenectOrderService {
	public boolean saveOrUpdate(ScenectOrder sctorder) {
		return ((ScenectOrderDao) BeanFactory.getBean("scenectOrderDao"))
				.saveOrUpdate(sctorder);
	}

	public List<ScenectOrder> getByHql(String hql) {
		return ((ScenectOrderDao) BeanFactory.getBean("scenectOrderDao"))
				.getByHql(hql);
	}

	public int getByHqlCount(String hql) {
		return ((ScenectOrderDao) BeanFactory.getBean("scenectOrderDao"))
				.getByHqlCount(hql);
	}
}
