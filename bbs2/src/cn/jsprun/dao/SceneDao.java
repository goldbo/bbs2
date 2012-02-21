package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.Scene;

public interface SceneDao {
   public List<Scene> getAllScene();
   public boolean saveOrupdateScene(Scene scene);
   public boolean deleteByUpId(int id);
   public List<Scene> getSceneByCondition(String hql);
   public Scene getSceneById(int id);
}
