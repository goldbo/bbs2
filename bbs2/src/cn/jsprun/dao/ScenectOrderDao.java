package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.ScenectOrder;

public interface ScenectOrderDao {
   public boolean saveOrUpdate(ScenectOrder sctorder);
   public List<ScenectOrder> getByHql(String hql);
   public int getByHqlCount(String hql);
}
