package cn.vcity.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import cn.jsprun.domain.Scenetm;
import cn.vcity.wbase.common.base.BaseDAOHibernate;

public class VcityScenetmDao extends BaseDAOHibernate {

	protected Class getModelClass() {
		return Scenetm.class;
	}

	public Scenetm findScenetmByUsernameTaskId(Integer taskId, String userName) {
		StringBuffer hql = new StringBuffer("select tm from Scenetm tm where taskId="+taskId); 
			if(userName!=null||!userName.equals("")){
				hql.append("and userName='"+userName+"'");
			}
		List list =  super.doFind(hql.toString());
		Scenetm tm = new Scenetm();
		if (list!=null&&list.size()>0) {
			tm = (Scenetm) list.get(0);
		}
		return tm;
	}

	public boolean setOnlineUser(String userName, Integer taskId) {
		boolean bool = true;
		try {
			StringBuffer str = new StringBuffer();
			str.append("update Scenetm tm set tm.lastUpdateTime=UNIX_TIMESTAMP(now())" +
					" where tm.sceneTask.taskId="+taskId+" and tm.userName='"+userName+"'");
			bool = super.doBatchExcute(str.toString());
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			bool = false;
		}
		return bool;
	}
	
}
