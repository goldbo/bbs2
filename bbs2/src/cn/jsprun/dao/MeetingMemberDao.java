package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.MeetingMember;

/********会议成员管理*******2010-11-03*****hcy ***/
public interface MeetingMemberDao {
	
	/**
	 * 根据会议编号，查询会议人员
	 * @return
	 */
   public List<MeetingMember> getMeetingMemberByAppId(Integer appId);
   
   /***
    * 删除
    * @return
    */
   public boolean deleteMeetingMember(MeetingMember mm);
   
   /***
    * 添加
    * @return
    */
   public boolean addMeetingMember(MeetingMember mm);
   
   /***
    * 修改
    * @return
    */
   public boolean updateMeetingMember(MeetingMember mm);
   
	/****
	 * 根据会议编号和用户编号  查找参加会议的对象
	 * @param appId
	 * @param userId
	 * @return
	 */
	public MeetingMember getMeetingMemberByAppId(Integer appId,Integer userId);
	
	/***
	 * 根据id参会者信息
	 */
	public MeetingMember getMeetingMemberById(Integer mmId) ;
	
	/***
	 * 根据会议编号及相应条件来查找相关的集合
	 * @param appId
	 * @return
	 */
	public List<MeetingMember> getMeetingMemberList(int appId,Integer isregister,Integer iskickout) ;
}
