package cn.jsprun.dao;

import java.util.List;

import cn.jsprun.domain.ThemeTemplate;

public interface ThemeTemplateDao {
    public boolean addThemeTemplate(ThemeTemplate template);
    public boolean deleteThemeTemplateById(Integer id);
    public boolean deleteThemeTemplateByUid(Integer uid);
    public List<ThemeTemplate> findThemeTemplate(String type,int id,int page);
    public boolean saveOrUpldate(ThemeTemplate template);
    public ThemeTemplate findThemeTemplateById(int id);
    public int findThemeTemplateCount(String type,int id);
    public boolean updateThemeTemplate(ThemeTemplate temp);
}
