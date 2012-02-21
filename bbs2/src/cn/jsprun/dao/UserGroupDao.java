package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.Admingroups;
import cn.jsprun.domain.Projects;
import cn.jsprun.domain.Ranks;
import cn.jsprun.domain.Usergroups;

public interface UserGroupDao {

	public List<Usergroups> findAllGroups();

	public Usergroups findUserGroupByCredits(int credits);

	public boolean modifyUserGroup(Usergroups userGroup);

	public boolean insertUserGroup(Usergroups userGroup);

	public boolean deleteUserGroup(Short groupId);

	public List<Usergroups> findUserGroupsByType(String type);

	public List<Usergroups> getUsergroupsList(List<Short> usergroupIdList);

	public List<Usergroups> findAdminGroups();

	public Admingroups findAdminGroupById(Short adminGid);

	public boolean modifyAdminGroup(Admingroups adminGroup);

	public Usergroups findUserGroupById(Short userGroupId);

	public List<Ranks> findAllRanks();

	public boolean insertRanks(Ranks rank);

	public boolean modifyRanks(Ranks rank);

	public boolean deleteRanks(Ranks rank);

	public boolean insertProjects(Projects projects);

	public boolean modifyProjects(Projects projects);

	public boolean deleteProjects(Projects projects);

	public List<Projects> findAllProjects();

	public Projects findProjectsById(Short projectsId);
 
	public List<Usergroups> findUserGroupByAddMember();
	 
	public List<Usergroups> findUsergroupInCredits(int Credits,short groupid);
	 
	public boolean deleteUsergroups(Usergroups usergroup);
	 
	public void updateUsergroups(List<Usergroups> updateUsergroupList);
	 
	public List<Usergroups> findUsergropsByHql(String hql);
}
