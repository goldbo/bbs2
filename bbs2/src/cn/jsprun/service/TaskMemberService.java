package cn.jsprun.service;
import cn.jsprun.dao.TaskMemberDao;
import cn.jsprun.domain.TaskMember;
import cn.jsprun.utils.BeanFactory;

public class TaskMemberService {
	public boolean addTaskMember(TaskMember tm) {
		return ((TaskMemberDao)(BeanFactory.getBean("taskMemberDao"))).addTaskMember(tm);
	}
}
