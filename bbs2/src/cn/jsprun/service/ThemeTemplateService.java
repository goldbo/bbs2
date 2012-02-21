package cn.jsprun.service;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.jsprun.dao.ThemeTemplateDao;
import cn.jsprun.domain.ThemeTemplate;
import cn.jsprun.utils.BeanFactory;


public class ThemeTemplateService {
	public boolean addThemeTemplate(ThemeTemplate template) {
		return ((ThemeTemplateDao)BeanFactory.getBean("themeTemplateDao")).addThemeTemplate(template);
	}

	public boolean deleteThemeTemplateById(Integer id) {
		return ((ThemeTemplateDao)BeanFactory.getBean("themeTemplateDao")).deleteThemeTemplateById(id);
	}

	public boolean deleteThemeTemplateByUid(Integer uid) {
		return ((ThemeTemplateDao)BeanFactory.getBean("themeTemplateDao")).deleteThemeTemplateByUid(uid);
	}

	public List<ThemeTemplate> findThemeTemplate(String type,int id,int page) {
		return ((ThemeTemplateDao)BeanFactory.getBean("themeTemplateDao")).findThemeTemplate(type,id,page);
	}

	public boolean saveOrUpldate(ThemeTemplate template) {
		return ((ThemeTemplateDao)BeanFactory.getBean("themeTemplateDao")).saveOrUpldate(template);
	}
	public ThemeTemplate findThemeTemplateById(int id){
		return ((ThemeTemplateDao)BeanFactory.getBean("themeTemplateDao")).findThemeTemplateById(id);
	}
	public int findThemeTemplateCount(String type,int id){
		return ((ThemeTemplateDao)BeanFactory.getBean("themeTemplateDao")).findThemeTemplateCount(type, id);
	}
	public boolean updateThemeTemplate(ThemeTemplate temp){
		return ((ThemeTemplateDao)BeanFactory.getBean("themeTemplateDao")).updateThemeTemplate(temp);
	}
}
