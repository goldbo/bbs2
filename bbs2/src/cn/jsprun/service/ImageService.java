package cn.jsprun.service;

import java.util.List;

import cn.jsprun.dao.ImageDao;
import cn.jsprun.domain.Image;
import cn.jsprun.utils.BeanFactory;

public class ImageService {
	public boolean addImage(Image image){
		return ((ImageDao)BeanFactory.getBean("imageDao")).addImage(image);
	}
	   public boolean deleteImage(int id){
		   return ((ImageDao)BeanFactory.getBean("imageDao")).deleteImage(id);
	   }
	   public boolean updateImage(Image image){
		   return ((ImageDao)BeanFactory.getBean("imageDao")).updateImage(image);
	   }
	   public List<Image> getAllImage(int type){
		   return ((ImageDao)BeanFactory.getBean("imageDao")).getAllImage(type);
	   }
	   public Image getImageById(int id){
		   return ((ImageDao)BeanFactory.getBean("imageDao")).getImageById(id);
	   }
}
