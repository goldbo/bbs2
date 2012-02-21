package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.Scenetask;

public interface ScenetaskDao {
   public List<Scenetask> getAllScenetask(int first,int max);
   public int getCountByCondition(String hql);
   public List<Scenetask> getByCondition(String hql,int first,int max);
   public boolean saveOrUpdateTask(Scenetask stask);
   public boolean deleteByHql(String hql);
   public Scenetask getScenetaskById(int id);
}
