package cn.jsprun.service;

import cn.jsprun.dao.impl.SpecialDaoImpl;
import cn.jsprun.domain.Special;
import cn.jsprun.utils.BeanFactory;

public class SpecialService {
	public boolean addSpecial(Special special){
		return ((SpecialDaoImpl)BeanFactory.getBean("specialDao")).addSpecial(special);
	}
    public Special getSpecialByFid(int tid){
    	return ((SpecialDaoImpl)BeanFactory.getBean("specialDao")).getSpecialByTid(tid);
    }
    public boolean deleteById(int id){
    	return ((SpecialDaoImpl)BeanFactory.getBean("specialDao")).deleteById(id);
    }
    public boolean deleteByFid(int tid){
    	return ((SpecialDaoImpl)BeanFactory.getBean("specialDao")).deleteByTid(tid);
    }
    public boolean update(Special special){
    	return ((SpecialDaoImpl)BeanFactory.getBean("specialDao")).update(special);
    }
}
