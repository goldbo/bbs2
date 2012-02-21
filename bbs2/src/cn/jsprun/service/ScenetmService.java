package cn.jsprun.service;

import java.util.List;

import cn.jsprun.dao.ScenetmDao;
import cn.jsprun.domain.Scenetm;
import cn.jsprun.utils.BeanFactory;

public class ScenetmService {
	public boolean save(Scenetm stm) {
		return ((ScenetmDao) BeanFactory.getBean("scenetmDao"))
				.save(stm);
	}
	public boolean update(Scenetm stm) {
		return ((ScenetmDao) BeanFactory.getBean("scenetmDao"))
				.update(stm);
	}
	public boolean deleteByHql(String hql){
		return ((ScenetmDao) BeanFactory.getBean("scenetmDao")).deleteByHql(hql);
	}
	public List<Scenetm> getScenetmByCondition(String hql, int first, int max) {
		return ((ScenetmDao) BeanFactory.getBean("scenetmDao")).getScenetmByCondition(hql, first, max);
	}
	public int getSecenetmCountByCondition(String hql) {
		return ((ScenetmDao) BeanFactory.getBean("scenetmDao")).getSecenetmCountByCondition(hql);
	}
}
