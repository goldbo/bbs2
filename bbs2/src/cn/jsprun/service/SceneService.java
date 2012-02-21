package cn.jsprun.service;
import java.util.List;

import cn.jsprun.dao.SceneDao;
import cn.jsprun.domain.Scene;
import cn.jsprun.utils.BeanFactory;

public class SceneService {
	public List<Scene> getAllScene() {
		return ((SceneDao)BeanFactory.getBean("sceneDao")).getAllScene();
	}
	public boolean saveOrupdateScene(Scene scene){
		return ((SceneDao)BeanFactory.getBean("sceneDao")).saveOrupdateScene(scene);
	}
	public boolean deleteByUpId(int id) {
		return ((SceneDao)BeanFactory.getBean("sceneDao")).deleteByUpId(id);
	}
	public List<Scene> getSceneByCondition(String hql) {
		return ((SceneDao)BeanFactory.getBean("sceneDao")).getSceneByCondition(hql);
	}
	public Scene getSceneById(int id) {
		return ((SceneDao)BeanFactory.getBean("sceneDao")).getSceneById(id);
	}
}
