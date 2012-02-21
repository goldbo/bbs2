package cn.jsprun.service;
import cn.jsprun.domain.Task;
import cn.jsprun.dao.TaskDao;
import cn.jsprun.utils.BeanFactory;
public class TaskService {
	public boolean addTaskTree(Task task) {
		return ((TaskDao)(BeanFactory.getBean("taskDao"))).addTaskTree(task);
	}
}
