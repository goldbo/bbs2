package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.Image;

public interface ImageDao {
   public boolean addImage(Image image);
   public boolean deleteImage(int id);
   public boolean updateImage(Image image);
   public List<Image> getAllImage(int type);
   public Image getImageById(int id);
}
