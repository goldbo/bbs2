package cn.vcity.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import cn.vcity.model.VcityResource;
import cn.vcity.web.form.VcityResourceForm;
import cn.vcity.wbase.common.base.BaseDAOHibernate;

/**
 * 虚拟资源管理的Dao类
 * 
 * @author 黄金波 huangjinbo@wingotech.com
 * @see com.wingo.wbase.common.base.BaseDAOHibernate
 * @version 1.0
 * @time:2011-7-1 17:19:20
 * 
 */
public class VcityResourceDao extends BaseDAOHibernate {

	protected Class getModelClass() {
		return VcityResource.class;
	}

	/**
	 * 保存虚拟资源
	 * 
	 * @param entity
	 *            虚拟资源
	 * @return
	 * @throws SQLException
	 */
	public boolean saveVcityResource(VcityResource entity) {
		return super.doCreateObjectReturn(entity);
	}

	/**
	 * 根据条件查询虚拟资源总数
	 * 
	 * @param vcityResourceForm
	 * @return 记录总数
	 */
	public int getTotalByOther(VcityResourceForm vcityResourceForm) {

		StringBuffer query = new StringBuffer();

		query
				.append("from VcityResource vr left join vr.vcityResType vn where 1=1 \n");

		if (vcityResourceForm.getResName() != null) {
			if(vcityResourceForm.getResName().trim().length()>0){
				query.append(" and vr.resName like '%"
					+ vcityResourceForm.getResName().trim() + "%'");
			}
		}
		if (vcityResourceForm.getProductType() != null) {
			if(vcityResourceForm.getProductType() > 0){
				query.append(" and vn.id ='"
					+ vcityResourceForm.getProductType()+"'");
			}
		}
		if(vcityResourceForm.getId()!=null){
			if(vcityResourceForm.getId()>0){
				query.append(" and vr.id='" + vcityResourceForm.getId() + "'");
			}
		}
		if(vcityResourceForm.getResType()!=null){
			if(vcityResourceForm.getResType()>0){
				query.append(" and vr.resType='" + vcityResourceForm.getResType() + "'");
			}
		}
		if (vcityResourceForm.getUserName()!=null&&!vcityResourceForm.getUserName().equals("")) {
			query.append(" and vr.createUser='" + vcityResourceForm.getUserName() + "'");
		}
		int total = super.getRowsByHQL("select count(*) " + query.toString());

		return total;

	}

	/**
	 * 根据条件查询虚拟资源数据列表
	 * 
	 * @param vcityResourceForm
	 * @param pageSize
	 * @param startRow
	 * @return
	 */
	public List getVcityResourceListByOther(
			VcityResourceForm vcityResourceForm, int pageSize, int startRow) {

		String hql = "select new cn.vcity.model.VcityResource(vr.id,vr.resName,vr.resDesc,vr.senceId,vr.resType," +
				"vr.resImage,vr.recommendImage,vr.moduleFileName,vr.resURL," +
				"vr.isRecommend,vr.createUser,vr.createDate)" +
				" from VcityResource vr left join vr.vcityResType vn"
				+ " where 1=1 \n";
		
		StringBuffer query = new StringBuffer(hql);

		if (vcityResourceForm.getResName() != null) {
			if(vcityResourceForm.getResName().trim().length()>0){
				query.append(" and vr.resName like '%"
					+ vcityResourceForm.getResName().trim() + "%'");
			}
		}
		if (vcityResourceForm.getProductType() != null) {
			if(vcityResourceForm.getProductType() > 0){
				query.append(" and vn.id ='"
					+ vcityResourceForm.getProductType()+"'");
			}
		}
		
		if(vcityResourceForm.getId()!=null){
			if(vcityResourceForm.getId()>0){
				query.append(" and vr.id='" + vcityResourceForm.getId() + "'");
			}
		}
		if(vcityResourceForm.getResType()!=null){
			if(vcityResourceForm.getResType()>0){
				query.append(" and vr.resType='" + vcityResourceForm.getResType() + "'");
			}
		}
		if (vcityResourceForm.getUserName()!=null&&!vcityResourceForm.getUserName().equals("")) {
			query.append(" and vr.createUser='" + vcityResourceForm.getUserName() + "'");
		}
		query.append(" order by vr.id desc");

		List datas = super.doFind(query.toString(), pageSize, startRow);

		return datas;
	}

	/**
	 * 根据ID查找虚拟资源
	 * 
	 * @param id
	 * @return
	 */
	public VcityResource findVcityResourceById(Integer id) {

		return (VcityResource) super.doFindObjectById(id);
	}

	/**
	 * 根据资源名称查找虚拟资源
	 * @param resName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public VcityResource findVcityResourceByResName(String resName) {

		String hql = "from VcityResource v where v.resName=:resName";

		List<VcityResource> list = new ArrayList<VcityResource>();
		List<VcityResource> doFindObjectListByParam = (List<VcityResource>) this
				.doFindObjectListByParam(hql, "resName", resName);
		list = doFindObjectListByParam;
		VcityResource resource = null;
		if (list != null && !list.isEmpty()) {
			resource = (VcityResource) list.get(0);
		}
		return (VcityResource) resource;
	}

	/**
	 * 根据ID数组批量删除虚拟资源
	 * @param ids
	 * @return
	 */
	public boolean deleteVcityResourceByIds(String[] ids) {
		boolean bool = false;
		try {
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					this.deleteVcityResourceById(Integer.parseInt(ids[i]));
				}
			}
			bool = true;
		} catch (Exception e) {
			bool = false;
		}
		return bool;

	}

	/**
	 * 根据ID删除该虚拟资源
	 * @param id
	 * @return
	 */
	public boolean deleteVcityResourceById(Integer id) {
		boolean bool = false;
		try {
			VcityResource vcityResource = this.findVcityResourceById(id);
			this.doDeleteObject(vcityResource);
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
		}
		return bool;

	}

	/**
	 * 更新虚拟资源
	 * @param entity
	 */
	public void updateVcityResource(VcityResource entity) {
		super.doUpdateObject(entity);

	}
	
	public List findVcityResourceByTypeId(Integer id){
		
		List list = new ArrayList();	
		StringBuffer query = new StringBuffer();
		
		query.append("select vr.id from VcityResource vr left join vr.vcityResType vn");
		query.append(" where vr.vcityResType='" + id +"'");

		list = super.doFind(query.toString());

		return list;
	}
	/**
	 * @param taskId
	 * @return
	 * Desc:根据任务ID查找虚拟资源(产品体验)
	 * @author csw
	 * Sep 3, 2011
	 */
	public List findResourceByTaskId(int taskId){
		StringBuffer hql = new StringBuffer();
		hql.append("select new cn.vcity.model.VcityResource(A.id,A.resName,A.resImage,A.resURL) ");
		hql.append(" from VcityResource A,VcityManualtasksetting B ");
		hql.append(" where A.id = B.subTaskResId and B.subTaskType=1");
		hql.append(" and B.taskId="+taskId);
		return super.doFind(hql.toString());
	}

	/**
	 * @param taskId
	 * @return
	 * Desc:根据任务ID查找调查问卷
	 * @author csw
	 * Sep 3, 2011
	 */
	public List findSurveyByTaskId(int taskId){
		StringBuffer hql = new StringBuffer();
		hql.append("select new cn.vcity.model.VcitySurey(A.sureyId,A.sureyName) ");
		hql.append(" from VcitySurey A,VcityManualtasksetting B ");
		hql.append(" where A.sureyId = B.subTaskResId and B.subTaskType=2");
		hql.append(" and B.taskId="+taskId);
		return super.doFind(hql.toString());
	}
	
	/**
	 * 批量保存
	 * @param list
	 * @return
	 */
	public boolean saveBatchObjects(List list){
		return super.doBatchCreateObjects(list);
	}

	public List findResourceOther(Integer taskId) {
		StringBuffer hql = new StringBuffer();
		hql.append("select new cn.vcity.model.VcityResource(A.id,A.resName,A.resImage,A.resURL) ");
		hql.append(" from VcityResource A ");
		hql.append(" where A.id not in(");
		hql.append("select new cn.vcity.model.VcityResource(C.id) ");
		hql.append(" from VcityResource C,VcityManualtasksetting B ");
		hql.append(" where C.id = B.subTaskResId and B.subTaskType=1");
		hql.append(" and B.taskId="+taskId);
		hql.append(")");
		return super.doFind(hql.toString());
	}

	public List findSurveyOther(Integer taskId) {
		StringBuffer hql = new StringBuffer();
		hql.append("select new cn.vcity.model.VcitySurey(C.sureyId,C.sureyName) ");
		hql.append(" from VcitySurey C ");
		hql.append(" where C.sureyId not in (");
		hql.append("select new cn.vcity.model.VcitySurey(A.sureyId) ");
		hql.append(" from VcitySurey A,VcityManualtasksetting B ");
		hql.append(" where A.sureyId = B.subTaskResId and B.subTaskType=2");
		hql.append(" and B.taskId="+taskId);
		hql.append(")");
		return super.doFind(hql.toString());

	}

	public List getUserSubListByTaskId(int taskId, String userName) {
		String hql = "select vs from VcitySubTask vs where vs.userName='"+userName+
		"' and vs.sceneTask.taskId="+taskId;
		return super.doFind(hql);
	}

	public List getUserStep(String subTaskResId, String userName) {
		StringBuffer hql = new StringBuffer();
		hql.append("select r.optName from vcity_produceorder p,vcity_resoprate r");
		hql.append(" where p.code=r.optCode ");
		hql.append(" and p.userName='"+userName+"' ");
		hql.append(" and p.resID="+subTaskResId);
		return super.getSQLQuery(hql.toString());
	}

	public List findResourceAll(VcityResourceForm vcityResourceForm) {
		String hql = "select new cn.vcity.model.VcityResource(vr.id,vr.resName,vr.resDesc,vr.senceId,vr.resType,"
				+ "vr.resImage,vr.recommendImage,vr.moduleFileName,vr.resURL,"
				+ "vr.isRecommend,vr.createUser,vr.createDate)"
				+ " from VcityResource vr left join vr.vcityResType vn"
				+ " where 1=1 \n";

		StringBuffer query = new StringBuffer(hql);

		if (vcityResourceForm.getResName() != null) {
			if (vcityResourceForm.getResName().trim().length() > 0) {
				query.append(" and vr.resName like '%"
						+ vcityResourceForm.getResName().trim() + "%'");
			}
		}
		if (vcityResourceForm.getProductType() != null) {
			if (vcityResourceForm.getProductType() > 0) {
				query.append(" and vn.id ='"
						+ vcityResourceForm.getProductType() + "'");
			}
		}

		if (vcityResourceForm.getId() != null) {
			if (vcityResourceForm.getId() > 0) {
				query.append(" and vr.id='" + vcityResourceForm.getId() + "'");
			}
		}
		if (vcityResourceForm.getResType() != null) {
			if (vcityResourceForm.getResType() > 0) {
				query.append(" and vr.resType='"
						+ vcityResourceForm.getResType() + "'");
			}
		}
		query.append(" order by vr.id desc");

		return super.doFind(query.toString());
	}

}
