package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.PermList;

public interface PermListDao {
   public boolean addPermList(PermList permlist);
   public List<PermList> getPermListByTid(int tid);
   public boolean deletePermListByTid(int tid);
}
