package cn.vcity.dao;

import java.util.ArrayList;
import java.util.List;

import cn.vcity.model.VcityResource;
import cn.vcity.model.VcityResourceApp;
import cn.vcity.wbase.common.base.BaseDAOHibernate;

public class VcityResourceAppDao extends BaseDAOHibernate {
	
	protected Class getModelClass() {
		return VcityResourceApp.class;
	}
	
	public boolean saveVcityResourceApp(VcityResourceApp entity) {
		return super.doCreateObjectReturn(entity);
	}

	public List<VcityResourceApp> findResAppByResID(Integer resId) {
		String hql = "from VcityResourceApp v where v.resId=:resId order by v.id asc";

		List<VcityResourceApp> doFindObjectListByParam = (List<VcityResourceApp>) this
				.doFindObjectListByParam(hql, "resId", resId);

		return doFindObjectListByParam;
	}

	public boolean deleteResAppById(Integer id) {
		VcityResourceApp entity = this.findEntityById(id);
		List list = this.findEntityByParenId(id);
		boolean flag = true;
		try{
			if (list!=null&&list.size()>0) {
				for (int i = 0; i < list.size(); i++) {
					VcityResourceApp child = (VcityResourceApp) list.get(i);
					super.doDeleteObject(child);
				}
			}
		
			if (flag) {
				super.doDeleteObject(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag =false;
		}
		return flag;
	}
	
	public VcityResourceApp findEntityById(Integer id){
		return (VcityResourceApp) super.doFindObjectById(id);
	}
	
	private List findEntityByParenId(Integer id){
		String hql = "from VcityResourceApp v where  v.parenId=:parenId";
		List<VcityResourceApp> doFindObjectListByParam = (List<VcityResourceApp>) this
		.doFindObjectListByParam(hql, "parenId", id);
		return doFindObjectListByParam;
	}

	public boolean deleteResAppEntity(VcityResourceApp app) {
		boolean flag = true;
		try{
			super.doDeleteObject(app);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
}
