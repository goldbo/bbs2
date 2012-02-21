package cn.jsprun.dao;
import java.util.List;

import cn.jsprun.domain.AnswerTemplate;


public interface AnswerTemplateDao {
   public boolean addTemplate(AnswerTemplate at);
   public boolean updateTemplate(AnswerTemplate at);
   public boolean deleteTemplate(int id);
   public AnswerTemplate getAnswerTemplateByCreator(int creator);
   public AnswerTemplate getAnswerTemplateById(int id);
}
