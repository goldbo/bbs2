package cn.vcity.web.form;

import java.util.HashSet;
import java.util.Set;

import cn.vcity.model.VcityChatUser;
import cn.vcity.wbase.common.base.BaseForm;



/**
 * VcityChatRoom generated by MyEclipse Persistence Tools
 */

public class VcityChatRoomForm extends BaseForm {

     private Integer id;
     private String roomName;
     private String describe;
     private String createdUser;
     private String beginTime;
     private String endTime;
     private Integer isPublish;  //0为未发布,1为已发布
     private Set<VcityChatUser> vcityChatUser = new HashSet<VcityChatUser>();
     private Integer chatStatus;   //0为未开始,1为进行中,2为已结束
     
     private Integer[] selectedRow;

    /** default constructor */
    public VcityChatRoomForm() {
    }


    public VcityChatRoomForm(Integer id, String roomName, String describe, 
    		String createdUser, String beginTime, String endTime) {
		this.id = id;
		this.roomName = roomName;
		this.describe = describe;
		this.createdUser = createdUser;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}


	public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomName() {
        return this.roomName;
    }
    
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDescribe() {
        return this.describe;
    }
    
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCreatedUser() {
        return this.createdUser;
    }
    
    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getBeginTime() {
        return this.beginTime;
    }
    
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


	public Integer getChatStatus() {
		return chatStatus;
	}


	public void setChatStatus(Integer chatStatus) {
		this.chatStatus = chatStatus;
	}


	public Set<VcityChatUser> getVcityChatUser() {
		return vcityChatUser;
	}


	public void setVcityChatUser(Set<VcityChatUser> vcityChatUser) {
		this.vcityChatUser = vcityChatUser;
	}


	public Integer[] getSelectedRow() {
		return selectedRow;
	}


	public void setSelectedRow(Integer[] selectedRow) {
		this.selectedRow = selectedRow;
	}


	public Integer getIsPublish() {
		return isPublish;
	}


	public void setIsPublish(Integer isPublish) {
		this.isPublish = isPublish;
	}

}