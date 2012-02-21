package cn.jsprun.service;

import java.util.List;

import cn.jsprun.dao.RecyclebinDao;
import cn.jsprun.domain.Threadsmod;
import cn.jsprun.struts.form.RecyclebinForm;
import cn.jsprun.utils.BeanFactory;

public class RecyclebinService {

	public Integer deleteDayOld(Integer dayold,String path) {
		return ((RecyclebinDao) BeanFactory.getBean("recyclebinDao")).deleteDayOld(dayold,path);
	}

	public String findByAll(RecyclebinForm recyclebinForm,String timeoffset) {
		return ((RecyclebinDao) BeanFactory.getBean("recyclebinDao")).findByAll(recyclebinForm,timeoffset);
	}

	public Integer deleteArray(List deletelist) {
		if (deletelist != null && deletelist.size() > 0) {
			return ((RecyclebinDao) BeanFactory.getBean("recyclebinDao")).deleteArray(deletelist);
		}
		return 0;
	}
	
	public Integer undeleteArray(List<Threadsmod> updatelist) {
		if (updatelist != null && updatelist.size() > 0) {
			return ((RecyclebinDao) BeanFactory.getBean("recyclebinDao")).undeleteArray(updatelist);
		}

		return 0;
	}

}
