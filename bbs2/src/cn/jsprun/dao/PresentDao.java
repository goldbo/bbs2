package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.Present;

public interface PresentDao {
   public boolean saveOrUpdatePresent(Present present);
   public List<Present> getAllGift();
}
