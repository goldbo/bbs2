package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.Scenect;

public interface ScenectDao {
  public boolean saveOrUpdate(Scenect sct);
  public List<Scenect> getScenectByCondition(String hql,int first,int max);
  public int getScenectCountByCondition(String hql);
}
