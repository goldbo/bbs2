package cn.jsprun.service;

import java.util.List;

import cn.jsprun.dao.FriendGroupsDao;
import cn.jsprun.domain.FriendGroups;
import cn.jsprun.utils.BeanFactory;

public class FriendGroupsService {
	public List<FriendGroups> getFirendGroupByType(int type){
		return  ((FriendGroupsDao)BeanFactory.getBean("firendGroupsDao")).getFirendGroupByType(type);
	}
	public boolean addFriendGroups(FriendGroups friend){
		return  ((FriendGroupsDao)BeanFactory.getBean("firendGroupsDao")).addFriendGroups(friend);
	}
	public boolean deleteFriendGroupsById(String id){
		return  ((FriendGroupsDao)BeanFactory.getBean("firendGroupsDao")).deleteFriendGroupsById(id);
	}
	public FriendGroups findFriendGroupsById(int id){
		return  ((FriendGroupsDao)BeanFactory.getBean("firendGroupsDao")).findFriendGroupsById(id);
	}
}
