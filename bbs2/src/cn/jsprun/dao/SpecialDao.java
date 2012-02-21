package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.Special;

public interface SpecialDao {
    public boolean addSpecial(Special special);
    public Special getSpecialByTid(int tid);
    public boolean deleteById(int id);
    public boolean deleteByTid(int tid);
    public boolean update(Special special);
}
