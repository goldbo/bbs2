package cn.vcity.dao;

import java.util.ArrayList;
import java.util.List;

import cn.vcity.model.VcityResType;
import cn.vcity.web.form.VcityResTypeForm;
import cn.vcity.wbase.common.base.BaseDAOHibernate;

/**
 * 虚拟资源分类的Dao类
 * 
 * @author 黄金波 huangjinbo@wingotech.com
 * @see com.wingo.wbase.common.base.BaseDAOHibernate
 * @version 1.0
 * @time:2011-7-4 17:16:20
 * 
 */
public class VcityResTypeDao extends BaseDAOHibernate {

	private VcityResourceDao vcityResourceDao;

	protected Class getModelClass() {
		return VcityResType.class;
	}

	public int getTotalByOther(VcityResTypeForm vcityResTypeForm) {

		StringBuffer query = new StringBuffer();
		query.append("from VcityResType vt where 1=1 \n");
		if (vcityResTypeForm.getResType() != null&&!vcityResTypeForm.getResType().equals("")) {
			query.append(" and resType like '%"
					+ vcityResTypeForm.getResType().trim() + "%'");
		}
		int total = super.getRowsByHQL("select count(*) " + query.toString());
		return total;

	}

	public List getVcityResTypeListByOther(VcityResTypeForm vcityResTypeForm,
			int pageSize, int startRow) {
		StringBuffer query = new StringBuffer();
		query.append("from VcityResType vt where 1=1 \n");
		if (vcityResTypeForm.getResType() != null&&!vcityResTypeForm.getResType().equals("")) {
			query.append(" and resType like '%"
					+ vcityResTypeForm.getResType().trim() + "%'");
		}
		
		List datas = super.doFind(query.toString(), pageSize, startRow);
		return datas;
	}

	@SuppressWarnings("unchecked")
	public VcityResType findVcityResTypeByResName(String resType) {

		String hql = "from VcityResType vt where vt.resType=:resType";

		List<VcityResType> list = new ArrayList<VcityResType>();
		List<VcityResType> doFindObjectListByParam = (List<VcityResType>) this
				.doFindObjectListByParam(hql, "resType", resType);
		list = doFindObjectListByParam;
		VcityResType vcityResType = null;
		if (list != null && !list.isEmpty()) {
			vcityResType = (VcityResType) list.get(0);
		}
		return (VcityResType) vcityResType;
	}

	public boolean saveVcityResType(VcityResType entity) {

		return super.doCreateObjectReturn(entity);
	}

	public VcityResType findVcityResTypeById(Integer id) {

		return (VcityResType) super.doFindObjectById(id);
	}

	public boolean updateVcityResType(VcityResType entity) {
		try {
			super.doUpdateObject(entity);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean deleteVcityResTypeByIds(Integer[] ids) {
		boolean bool = false;
		try {
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					this.deleteVcityResTypeById(ids[i]);
				}
			}
			bool = true;
		} catch (Exception e) {
			bool = false;
		}
		return bool;
	}

	public boolean deleteVcityResTypeById(Integer id) {
		boolean bool = false;
		boolean flag = true;
		try {
			VcityResType vcityResType = this.findVcityResTypeById(id);

/*			
			List list = vcityResourceDao.findVcityResourceByTypeId(id);
			if (list.size() > 0 || list != null) {

				for (int i = 0; i < list.size(); i++) {
					flag = vcityResourceDao.deleteVcityResourceById((Integer) list.get(i));
				}
			}*/
			if(flag){
				this.doDeleteObject(vcityResType);
				bool = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
		}
		return bool;

	}

	public List findAllVcityResType() {

		return super.doListAllObject();
	}

	public VcityResourceDao getVcityResourceDao() {
		return vcityResourceDao;
	}

	public void setVcityResourceDao(VcityResourceDao vcityResourceDao) {
		this.vcityResourceDao = vcityResourceDao;
	}

}
