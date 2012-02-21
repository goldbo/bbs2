package cn.jsprun.service;

import java.util.List;

import cn.jsprun.dao.ScenetaskDao;
import cn.jsprun.domain.Scenetask;
import cn.jsprun.utils.BeanFactory;


public class ScenetaskService {
	public List<Scenetask> getAllScenetask(int first,int max) {
		return ((ScenetaskDao)BeanFactory.getBean("scenetaskDao")).getAllScenetask(first,max);
	}
	public int getCountByCondition(String hql) {
		return ((ScenetaskDao)BeanFactory.getBean("scenetaskDao")).getCountByCondition(hql);
	}
	public boolean saveOrUpdateTask(Scenetask stask){
		return ((ScenetaskDao)BeanFactory.getBean("scenetaskDao")).saveOrUpdateTask(stask);
	}
	public boolean deleteByHql(String hql){
		return ((ScenetaskDao)BeanFactory.getBean("scenetaskDao")).deleteByHql(hql);
	}
	public Scenetask getScenetaskById(int id) {
		return ((ScenetaskDao)BeanFactory.getBean("scenetaskDao")).getScenetaskById(id);
	}
	public List<Scenetask> getByCondition(String hql,int first,int max){
		return ((ScenetaskDao)BeanFactory.getBean("scenetaskDao")).getByCondition(hql, first, max);
	}
}
