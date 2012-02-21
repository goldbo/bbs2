package cn.jsprun.service;

import java.util.List;

import cn.jsprun.dao.PresentDao;
import cn.jsprun.domain.Present;
import cn.jsprun.utils.BeanFactory;


public class PresentService {
	public boolean saveOrUpdatePresent(Present present){
		return ((PresentDao)BeanFactory.getBean("presentDao")).saveOrUpdatePresent(present);
	}
	public List<Present> getAllGift(){
		return ((PresentDao)BeanFactory.getBean("presentDao")).getAllGift();
	}
}
