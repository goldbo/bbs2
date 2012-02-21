package cn.jsprun.service;

import java.util.List;

import cn.jsprun.dao.impl.PermListDaoImpl;
import cn.jsprun.domain.PermList;
import cn.jsprun.utils.BeanFactory;

public class PermListService {
	public boolean addPermList(PermList permlist) {
		return ((PermListDaoImpl) BeanFactory.getBean("permlistDao"))
				.addPermList(permlist);
	}

	public List<PermList> getPermListByTid(int tid) {
		return ((PermListDaoImpl) BeanFactory.getBean("permlistDao"))
				.getPermListByTid(tid);
	}

	public boolean deletePermListByTid(int tid) {
		return ((PermListDaoImpl) BeanFactory.getBean("permlistDao"))
				.deletePermListByTid(tid);
	}
}
