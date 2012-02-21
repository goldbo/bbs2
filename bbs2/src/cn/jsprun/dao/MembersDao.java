package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.Access;
import cn.jsprun.domain.Forums;
import cn.jsprun.domain.Medals;
import cn.jsprun.domain.Memberfields;
import cn.jsprun.domain.Members;
import cn.jsprun.domain.Onlinetime;
import cn.jsprun.domain.Pms;
import cn.jsprun.domain.Profilefields;
import cn.jsprun.domain.Ranks;
import cn.jsprun.domain.Styles;
import cn.jsprun.domain.Usergroups;
import cn.jsprun.domain.Validating;

public interface MembersDao {

	public Members findMemberById(int memberId);

	public Members findMemberByName(String userName);

	public Members findByNameAndGroups(String userName, String groupIds);

	public boolean insertMember(Members member);

	public boolean modifyMember(Members member);

	public boolean deleteMember(Members member);

	public boolean deleteValidating(short id);
	
	public void resetUserCredits(String columeName,Integer resetValue) throws Exception;
	
	public boolean insertValidating(Validating validating);

	public boolean modifyValidating(Validating validating);

	public int modifyAllValidating(byte status);

	public Validating findValidatingById(int id);

	public List<Validating> findAllValidatings();

	public boolean insertProfile(Profilefields profile);

	public List<Profilefields> findAllProfilefields();

	public boolean deleteProfile(Short fieldId);

	public boolean modifyProfile(Profilefields profile);

	public Profilefields findProfileById(Short fieldId);

	public boolean insertRanks(Ranks rank);

	public boolean modifyRanks(Ranks rank);

	public boolean deleteRanks(Ranks rank);

	public Ranks findRanksById(short rankId);

	public List<Ranks> findAllRanks();

	public boolean insertAccess(Access access);

	public boolean modifyAccess(Access access);

	public boolean deleteAccess(Access access);

	public List<Access> findAccessByUserId(int userId);

	public Access findAccessByFid(short fid, int uid);

	public boolean insertMemberfields(Memberfields memberfields);

	public boolean modifyMemberfields(Memberfields memberfields);

	public boolean deleteMemberfields(Memberfields memberfields);

	public Memberfields findMemberfieldsById(int memberFieldsId);

	public boolean insertOnlineTime(Onlinetime onlinetime);

	public boolean modifyOnlineTime(Onlinetime onlinetime);

	public boolean deleteOnlineTime(Onlinetime onlinetime);

	public Onlinetime findOnlinetimeById(int onlinetimeId);

	public List<Styles> findAllStyles();

	public boolean insertPms(Pms pms);

	public List<Forums> findForumsByType();

	public boolean deleteSpacecaches(int uid);

	public List<Medals> findMedalsByAvailable(byte available);
 
	public List<Usergroups> findUsergroupByExt();
 
	public boolean deleteValidating(Validating validate);
	 
	public List<Members> findMembersByGroupid(Short groupid);
 
	public List findMembersByInId(int lowerId,int upperId);
	 
	public boolean insertMemberByJDBC(Members member);
 
	public List<Members> findMembersByHql(String hql,int startrow,int maxrows);
 
	public int findMembersCount();
	 
	public boolean updateMembersByHql(String hql);
	 
	public List<Profilefields> findprofilefieldByAvaliable(byte avaliable);
	
	public List<Members> findByProperty(String propertyName, Object value);
	
	public List<Members> getMemberListWithMemberIdList(List<Integer> memberIdList);
 
	public int findMemberCountByHql(String hql);
 
	public Integer getAdminCount();
 
	public Integer getMembersCount();
	 
	public Integer getMembersaddtoday();
	 
	public Integer getMemnonpost();
	
	public Members getLastMember();
 
	public List<Members> getMembersByNames(List<String> membersNames);
 
	public List<Members> getAllMembers();
	 
	public void updateMembers(List<Members> memberList);
 
	public void updateMembers(String hql);
	
	public List<Members> getAllMembersByExtgroupids(String extgroupids);
}
