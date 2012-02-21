package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.SendGifts;
import cn.jsprun.vo.Sendgifts_sr;

public interface SendGiftsDao {
   public boolean saveorupdate(List<SendGifts> list);
   public List<Sendgifts_sr> getListByid(String hql,int first,int max);
}
