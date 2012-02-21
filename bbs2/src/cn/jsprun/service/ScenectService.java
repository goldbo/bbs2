package cn.jsprun.service;

import java.util.List;

import cn.jsprun.dao.ScenectDao;
import cn.jsprun.domain.Scenect;
import cn.jsprun.utils.BeanFactory;

public class ScenectService {
	public boolean saveOrUpdate(Scenect sct) {
		return ((ScenectDao) BeanFactory.getBean("scenectDao"))
				.saveOrUpdate(sct);
	}

	public List<Scenect> getScenectByCondition(String hql, int first, int max) {
		return ((ScenectDao) BeanFactory.getBean("scenectDao"))
				.getScenectByCondition(hql, first, max);
	}

	public int getScenectCountByCondition(String hql) {
		return ((ScenectDao) BeanFactory.getBean("scenectDao"))
				.getScenectCountByCondition(hql);
	}
}
