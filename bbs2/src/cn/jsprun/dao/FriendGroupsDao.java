package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.FriendGroups;

public interface FriendGroupsDao {
	public List<FriendGroups> getFirendGroupByType(int type);
	public boolean addFriendGroups(FriendGroups friend);
	public boolean deleteFriendGroupsById(String id);
	public FriendGroups findFriendGroupsById(int id);
}
