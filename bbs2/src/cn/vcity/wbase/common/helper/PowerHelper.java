package cn.vcity.wbase.common.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*import cn.vcity.wbase.model.ViewUserInfo;
import cn.vcity.wbase.model.WbaseOrg;
import cn.vcity.wbase.model.WbaseOrgUser;
import cn.vcity.wbase.model.WbaseResource;
import cn.vcity.wbase.model.WbaseRoleObj;
import cn.vcity.wbase.service.WbaseOrgService;
import cn.vcity.wbase.service.WbaseOrgUserService;
import cn.vcity.wbase.service.WbasePostUserService;
import cn.vcity.wbase.service.WbasePowerService;
import cn.vcity.wbase.service.WbaseResourceService;
import cn.vcity.wbase.service.WbaseRoleObjService;
*/
/**
 * @Title: PowerHelper.java
 * @Package cn.vcity.wbase.common.helper
 * @Description: 系统权限检查
 * @author CN：谢茂盛 EN:SamHsieh 珠海市网佳科技有限公司.
 * @date 2011-4-21 下午03:34:26
 * @version V1.0
 */
public class PowerHelper {

	/**
	 * 检查权限级别
	 * 
	 * @param userPurview
	 * @param optPurview
	 * @return
	 */
	public static boolean checkPower(int userPurview, int optPurview) {
		int purviewValue = (int) Math.pow(2, optPurview);
		return (userPurview & purviewValue) == purviewValue;
	}

	/**
	 * 删权限
	 * 
	 * @param orgNodeNo
	 */
	/*public static void deletePowerByResNodeNo(Long resNodeNo) {
		WbasePowerService wbasePowerService = (WbasePowerService) SpringHelper
				.getBean("wbasePowerService");
		wbasePowerService.deletePowerByResNodeNo(resNodeNo);
	}*/

	/**
	 * 得到用户所属组织集合：包括父组织
	 * 
	 * @param user
	 * @return
	 */
	/*public static List<WbaseOrg> getUserOrgList(ViewUserInfo user) {
		WbaseOrgService wbaseOrgService = (WbaseOrgService) SpringHelper
				.getBean("wbaseOrgService");
		List<WbaseOrg> orgList = new ArrayList<WbaseOrg>();
		WbaseOrg org = wbaseOrgService.findOrgByNodeNo(user.getOrgNodeNo());
		if (org != null) {
			boolean bool = true;
			do {
				orgList.add(org);
				org = wbaseOrgService.findOrgByNodeNo(org.getSubNodeNo());
				bool = org == null ? false : true;
			} while (bool);
		}
		return orgList;
	}*/

	/**
	 * 获取用户所属角色集合
	 * 
	 * @param orgList
	 * @param user
	 * @return
	 */
	/*public static List<WbaseRoleObj> getUserRoleList(List<WbaseOrg> orgList,
			ViewUserInfo user) {
		WbaseRoleObjService wbaseRoleObjService = (WbaseRoleObjService) SpringHelper
				.getBean("wbaseRoleObjService");
		List<WbaseRoleObj> roleObjList = new ArrayList<WbaseRoleObj>();
		// 获取组织所属角色
		if (!orgList.isEmpty()) {
			for (int i = 0; i < orgList.size(); i++) {
				WbaseOrg org = orgList.get(i);
				List<WbaseRoleObj> objList = wbaseRoleObjService
						.findRoleObjByObjNo("org", org.getNodeNo().toString());
				if (!objList.isEmpty()) {
					for (int k = 0; k < objList.size(); k++) {
						WbaseRoleObj obj = (WbaseRoleObj) objList.get(k);
						roleObjList.add(obj);
					}
				}
			}
		}
		// 获取用户所属角色
		List<WbaseRoleObj> objList = wbaseRoleObjService.findRoleObjByObjNo(
				"user", user.getAccount());
		if (!objList.isEmpty()) {
			for (int k = 0; k < objList.size(); k++) {
				WbaseRoleObj obj = (WbaseRoleObj) objList.get(k);
				roleObjList.add(obj);
			}
		}
		return roleObjList;
	}*/

	/**
	 * 得到这些角色权限的并集Map
	 * 
	 * @param roleObjList
	 * @return
	 */
	/*public static Map getPowerList(List<WbaseRoleObj> roleObjList) {
		WbasePowerService wbasePowerService = (WbasePowerService) SpringHelper
				.getBean("wbasePowerService");
		Map powerMap = new HashMap();
		if (roleObjList.size() > 0) {
			for (int i = 0; i < roleObjList.size(); i++) {
				WbaseRoleObj roleObj = (WbaseRoleObj) roleObjList.get(i);
				powerMap = wbasePowerService.findPowerByRole(powerMap, roleObj
						.getRoleNo());
			}
		}
		return powerMap;
	}*/

	/**
	 * 删除角色对象
	 * 
	 * @param objType
	 * @param objNo
	 */
	/*public static void deleteRoleObjByObjNo(String objType, String objNo) {
		WbaseRoleObjService wbaseRoleObjService = (WbaseRoleObjService) SpringHelper
				.getBean("wbaseRoleObjService");
		wbaseRoleObjService.deleteRoleObjByObjNo(objType, objNo);
	}*/

	/**
	 * 删除岗位用户
	 * 
	 * @param userAccount
	 */
	/*public static void deletePostUserByUser(String userAccount) {
		WbasePostUserService wbasePostUserService = (WbasePostUserService) SpringHelper
				.getBean("wbasePostUserService");
		wbasePostUserService.deletePostUserByUser(userAccount);
	}
*/
	/**
	 * 删除岗位用户
	 * 
	 * @param postNo
	 */
	/*public static void deletePostUserByPostNo(String postNo) {
		WbasePostUserService wbasePostUserService = (WbasePostUserService) SpringHelper
				.getBean("wbasePostUserService");
		wbasePostUserService.deletePostUserByPostNo(postNo);
	}*/

	/**
	 * 删除组织用户
	 * 
	 * @param userAccount
	 */
	/*public static void deleteOrgUserByUser(String userAccount) {
		WbaseOrgUserService wbaseOrgUserService = (WbaseOrgUserService) SpringHelper
				.getBean("wbaseOrgUserService");
		wbaseOrgUserService.deleteOrgUserByUser(userAccount);
	}*/

	/**
	 * 删组织用户
	 * 
	 * @param orgNodeNo
	 */
	/*public static void deleteOrgUserByOrgNo(Long orgNodeNo) {
		WbaseOrgUserService wbaseOrgUserService = (WbaseOrgUserService) SpringHelper
				.getBean("wbaseOrgUserService");
		wbaseOrgUserService.deleteOrgUserByOrgNodeNo(orgNodeNo);
	}*/

	/**
	 * 更新组织用户
	 * 
	 * @param userAccount
	 * @param orgNodeNo
	 */
	/*public static void updateOrgUser(String userAccount, Long orgNodeNo) {
		WbaseOrgUserService wbaseOrgUserService = (WbaseOrgUserService) SpringHelper
				.getBean("wbaseOrgUserService");
		WbaseOrgUser orgUser = wbaseOrgUserService
				.findOrgUserByUser(userAccount);
		if (orgUser != null) {
			orgUser.setOrgNodeNo(orgNodeNo);
			wbaseOrgUserService.updateOrgUser(orgUser);
		}
	}*/

	/*public static WbaseResource findResourceByNodeNo(Long nodeNo) {
		WbaseResourceService wbaseResourceService = (WbaseResourceService) SpringHelper
				.getBean("wbaseResourceService");
		WbaseResource res = wbaseResourceService.findResourceByNodeNo(nodeNo);
		return res;
	}*/
}
