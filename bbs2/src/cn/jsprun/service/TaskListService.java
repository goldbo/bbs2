package cn.jsprun.service;
import cn.jsprun.dao.TaskListDao;
import cn.jsprun.domain.TaskList;
import cn.jsprun.utils.BeanFactory;
public class TaskListService {
	public boolean addTaskList(TaskList task) {
		return ((TaskListDao)(BeanFactory.getBean("taskListDao"))).addTaskList(task);
	}
}
