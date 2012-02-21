package cn.jsprun.service;

import java.util.List;

import cn.jsprun.dao.MeetingMemberDao;
import cn.jsprun.domain.MeetingMember;
import cn.jsprun.utils.BeanFactory;

public class MeetingMemberService {
	/**
	 * 根据会议编号，查询会议人员
	 * @return
	 */
   public List<MeetingMember> getMeetingMemberByAppId(Integer appId){
	   return ((MeetingMemberDao)BeanFactory.getBean("meetingMemberDao")).getMeetingMemberByAppId(appId);
   }
   
   /***
    * 删除
    * @return
    */
   public boolean deleteMeetingMember(MeetingMember mm){
	   return ((MeetingMemberDao)BeanFactory.getBean("meetingMemberDao")).deleteMeetingMember(mm);
   }
   
   /***
    * 添加
    * @return
    */
   public boolean addMeetingMember(MeetingMember mm){
	   return ((MeetingMemberDao)BeanFactory.getBean("meetingMemberDao")).addMeetingMember(mm);
   }
   
   /***
    * 修改
    * @return
    */
   public boolean updateMeetingMember(MeetingMember mm){
	   return ((MeetingMemberDao)BeanFactory.getBean("meetingMemberDao")).updateMeetingMember(mm);
   }
   
	/****
	 * 根据会议编号和用户编号  查找参加会议的对象
	 * @param appId
	 * @param userId
	 * @return
	 */
	public MeetingMember getMeetingMemberByAppId(Integer appId,Integer userId){
		return ((MeetingMemberDao)BeanFactory.getBean("meetingMemberDao")).getMeetingMemberByAppId(appId, userId);
	}
	
	/***
	 * 根据id参会者信息
	 */
	public MeetingMember getMeetingMemberById(Integer mmId) {
		return ((MeetingMemberDao)BeanFactory.getBean("meetingMemberDao")).getMeetingMemberById(mmId);
	}
	
	/***
	 * 根据会议编号及相应条件来查找相关的集合
	 * @param appId
	 * @return
	 */
	public List<MeetingMember> getMeetingMemberList(int appId,Integer isregister,Integer iskickout) {
		return ((MeetingMemberDao)BeanFactory.getBean("meetingMemberDao")).getMeetingMemberList(appId, isregister, iskickout);
	}
}
