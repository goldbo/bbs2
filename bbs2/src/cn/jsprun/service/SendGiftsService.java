package cn.jsprun.service;

import java.util.List;

import cn.jsprun.dao.SendGiftsDao;
import cn.jsprun.domain.SendGifts;
import cn.jsprun.utils.BeanFactory;
import cn.jsprun.vo.Sendgifts_sr;


public class SendGiftsService {
	public boolean saveorupdate(List<SendGifts> list){
		return ((SendGiftsDao)BeanFactory.getBean("sendGiftsDao")).saveorupdate(list);
	}
	public List<Sendgifts_sr> getListByid(String hql,int first,int max) {
		return ((SendGiftsDao)BeanFactory.getBean("sendGiftsDao")).getListByid(hql, first, max);
	}
}
