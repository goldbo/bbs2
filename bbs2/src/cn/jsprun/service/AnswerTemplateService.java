package cn.jsprun.service;

import cn.jsprun.dao.impl.AnswerTemplateDaoImpl;
import cn.jsprun.domain.AnswerTemplate;
import cn.jsprun.utils.BeanFactory;

public class AnswerTemplateService {
	public boolean addTemplate(AnswerTemplate at) {
		return ((AnswerTemplateDaoImpl) BeanFactory
				.getBean("answertemplateDao")).addTemplate(at);
	}

	public boolean updateTemplate(AnswerTemplate at) {
		return ((AnswerTemplateDaoImpl) BeanFactory
				.getBean("answertemplateDao")).updateTemplate(at);
	}

	public boolean deleteTemplate(int id) {
		return ((AnswerTemplateDaoImpl) BeanFactory
				.getBean("answertemplateDao")).deleteTemplate(id);
	}

	public AnswerTemplate getAnswerTemplateByCreator(int creator) {
		return ((AnswerTemplateDaoImpl) BeanFactory
				.getBean("answertemplateDao"))
				.getAnswerTemplateByCreator(creator);
	}

	public AnswerTemplate getAnswerTemplateById(int id) {
		return ((AnswerTemplateDaoImpl) BeanFactory
				.getBean("answertemplateDao")).getAnswerTemplateById(id);
	}
}
