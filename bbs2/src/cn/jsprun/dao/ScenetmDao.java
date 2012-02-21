package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.Scenetm;

public interface ScenetmDao {
    public boolean save(Scenetm stm);
    public boolean update(Scenetm stm);
    public boolean deleteByHql(String hql);
    public List<Scenetm> getScenetmByCondition(String hql,int first,int max);
    public int getSecenetmCountByCondition(String hql);
}
