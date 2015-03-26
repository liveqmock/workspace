/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.base.SendMessageVoid;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.TreeNode;
import com.yazuo.erp.system.dao.SysPositionDao;
import com.yazuo.erp.system.dao.SysResourceDao;
import com.yazuo.erp.system.dao.SysRoleDao;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.dao.SysWhiteListDao;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysGroupVO;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.system.vo.SysWhiteListVO;
import com.yazuo.erp.train.service.StudentManagementService;
import com.yazuo.util.DeviceTokenUtil;

/**
 * 用户相关操作
 * 
 * @Description TODO
 * @author kyy
 * @date 2014-6-5 下午5:52:18
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

	private static final Log LOG = LogFactory.getLog(SysUserServiceImpl.class);
	/** 临时存放用户上传图片 */
	final static String tempImgLocationPath = DeviceTokenUtil.getPropertiesValue("userTempPhotoPaht");
	/** 最终存放用户上传图片 */
	final static String realImgLocationPath = DeviceTokenUtil.getPropertiesValue("userPhotoPath");
	/** 限制上传文件大小 */
	final static long LIMIT_FILE_SIZE = 2 * 1024 * 1024;

	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysRoleDao sysRoleDao;
	@Resource
	private SysPositionDao sysPositionDao;
	@Resource
	private SysResourceDao sysResourceDao;
	@Resource
	private SendMessageVoid sendMessageVoid;
	@Resource
	private SysWhiteListDao sysWhiteListDao;
	
	@Resource
	private StudentManagementService studentManagementService;

	@Override
	public Map<String, Object> saveSysUser(SysUserVO sysUserVO, String[] deptIdArray, List<Integer> roleIdArray,
			List<Integer> groupList, List<Map<String, Integer>> userList, Integer currentUserId) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 验证mac地址唯一 //TODO 目前没有这个需求 2014-8-14 14:48:22
		// Map<String, Object> mapMac = new HashMap<String, Object>();
		// mapMac.put("mac", sysUserVO.getMacs().get(0));
		// List<SysWhiteListVO> list = sysWhiteListDao.getSysWhiteLists(mapMac);
		// if(list!=null&& list.size()>=1){
		// map.put("flag", 0);
		// map.put("message", "物理地址被使用!");
		// return map;
		// }

		long count = sysUserDao.isSameUserByPhone(sysUserVO.getTel());
		if (count > 0) { // 存在
			map.put("flag", 0);
			map.put("message", "该用户已存在!");
		} else {
			// 保存用户信息
			sysUserDao.saveSysUser(sysUserVO);
			Integer id = sysUserVO.getId();
			// 保存关联关系
			saveOrUpdateRelation(id, currentUserId, deptIdArray, roleIdArray, groupList, userList);

			this.saveMacAndUser(sysUserVO.getTel(), sysUserVO.getMacs(), currentUserId);
			
			// start, updated by gaoshan, 2014-09-18
			// 新学员创建时，根据所属职位默认创建学习进度
			String isFormal = sysUserVO.getIsFormal();
			if (null != isFormal && "0".equals(isFormal)) {
				studentManagementService.saveTraLearningProgress(sysUserVO,currentUserId);
			}
			// end, updated by gaoshan, 2014-09-18
			
			map.put("flag", 1);
			map.put("message", "保存用户信息成功!");
		}

		return map;
	}

	// 保存或修改时，保存与用户的关联关系
	private void saveOrUpdateRelation(Integer userId, Integer currentUserId, String[] deptIdArray, List<Integer> roleIdArray,
			List<Integer> groupList, List<Map<String, Integer>> userList) {
		// 保存用户与组织的关联关系
		List<Map<String, Object>> deptList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < deptIdArray.length; i++) {
			// 获取当前登录信息
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("insertBy", currentUserId);
			params.put("insertTime", new Date());
			params.put("groupId", Integer.parseInt(deptIdArray[i]));
			deptList.add(params);
		}
		sysUserDao.saveUserAndDeptRelation(deptList);
		// 保存用户与角色的关联关系
		String[] rarray = new String[roleIdArray.size()];
		for (int i = 0; i < roleIdArray.size(); i++) {
			rarray[i] = roleIdArray.get(i) + "";
		}
		editRoleRelation(userId, rarray, currentUserId, false);
		// 保存管理的组id
		if ((groupList != null && groupList.size() > 0) || (userList != null && userList.size() > 0)) {
			editManagerRelation(userId, groupList, userList, currentUserId, false);
		}
	}

	@Override
	public Map<String, Object> updateSysUser(SysUserVO sysUserVO, String[] deptIdArray, Integer currentUserId) {
		Map<String, Object> map = new HashMap<String, Object>();
		SysUserVO sysuserVODB = sysUserDao.getSysUserById(sysUserVO.getId());

		// 验证mac地址唯一 //TODO 目前没有这个需求 2014-8-14 14:48:22
		// Map<String, Object> mapUser = new HashMap<String, Object>();
		// mapUser.put("userId", sysUserVO.getId());
		// List<SysWhiteListVO> listForUserMacs =
		// sysWhiteListDao.getSysWhiteLists(mapUser);
		// if(listForUserMacs!=null&&listForUserMacs.size()>0){
		// SysWhiteListVO sysWhiteListVO = listForUserMacs.get(0);
		// String userInputMac = sysUserVO.getMacs().get(0);
		// if(!userInputMac.equals(sysWhiteListVO.getMac())){//更改了
		// Map<String, Object> mapMac = new HashMap<String, Object>();
		// mapMac.put("mac", userInputMac);
		// List<SysWhiteListVO> list = sysWhiteListDao.getSysWhiteLists(mapMac);
		// if(list!=null&& list.size()>=1){
		// SysWhiteListVO sysWhiteListVO1 = list.get(0);
		// map.put("flag", 0);
		// map.put("message", "物理地址被使用!");
		// return map;
		// }
		// }
		// }
		
		long count = sysUserDao.isSameUserByPhone(sysUserVO.getTel());
		if (!sysuserVODB.getTel().equals(sysUserVO.getTel())) {// 如果更改了手机号
			if (count == 1) { // 存在
				map.put("flag", 0);
				map.put("message", "该用户已存在!");
				return map;
			}
		}
		if (deptIdArray!=null && deptIdArray.length>0) {
			// 删除用户与组织关联关系
			sysUserDao.deleteUserAndDeptRelation(sysUserVO.getId());
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < deptIdArray.length; i++) {
				// 获取当前登录信息
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", sysUserVO.getId());
				params.put("insertBy", currentUserId);
				params.put("insertTime", new Date());
				params.put("groupId", Integer.parseInt(deptIdArray[i]));
				list.add(params);
			}
			// 保存用户与组织的关联关系
			sysUserDao.saveUserAndDeptRelation(list);
		}
		// start, updated by gaoshan, 2014-09-19
		// 新学员，所属职位进行修改时，如果新老职位所对应的课程不同，则原学习进度置为4-异常终止，并建立新的学习进度
		String isFormal = sysUserVO.getIsFormal();
		if (null != isFormal && "0".equals(isFormal)) {//新学员
			if(sysuserVODB.getPositionId() != sysUserVO.getPositionId()){//职位变更
				studentManagementService.updateTraLearningProgressToAbnormalTermination(sysUserVO,sysuserVODB.getPositionId(),currentUserId);
			}
		}
		// end, updated by gaoshan, 2014-09-19
		
		// 修改基本信息
		sysUserDao.updateSysUser(sysUserVO);
		// 处理mac相关, 先删除所有，在添加
		if (sysUserVO.getMacs()!=null && sysUserVO.getMacs().size() >0) {
			this.deleteSysWhiteByUser(sysUserVO.getId());
			this.saveMacAndUser(sysUserVO.getTel(), sysUserVO.getMacs(), currentUserId);
		}
		map.put("flag", 1);
		map.put("message", "修改信息成功!");

		return map;
	}

	/**
	 * 修改权限组与用户的关系
	 * 
	 * @param id
	 *            用户id
	 * @param roleIdArray
	 *            选择的权限组
	 * @param currentUserId
	 *            当前登录的用户id
	 */
	@Override
	public int editRoleRelation(Integer id, String[] roleIdArray, Integer currentUserId, boolean isUpdate) {
		if (isUpdate) {
			// 删除用户与角色关联关系
			sysUserDao.deleteUserAndRoleRelation(id);
		}
		// 保存用户与角色的关联关系
		List<Map<String, Object>> roleList = new ArrayList<Map<String, Object>>();
		for (int m = 0; m < roleIdArray.length; m++) {
			// 获取当前登录信息
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", id);
			params.put("insertBy", currentUserId);
			params.put("insertTime", new Date());
			params.put("roleId", Integer.parseInt(roleIdArray[m]));
			roleList.add(params);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", roleList);
		return sysUserDao.saveUserAndRoleRelation(map);
	}

	/**
	 * 修改用户管理关系
	 * 
	 * @param id
	 *            用户id
	 * @param roleIdArray
	 *            选择的权限组
	 * @param currentUserId
	 *            当前登录的用户id
	 */
	@Override
	public int editManagerRelation(Integer id, List<Integer> groupList, List<Map<String, Integer>> userList,
			Integer currentUserId, boolean isUpdate) {
		if (isUpdate) {
			// 删除用户与管理员工关联关系
			sysUserDao.deleteGroupManagersRelation(id);
		}
		// 保存管理的组id
		List<Map<String, Object>> managerList = new ArrayList<Map<String,Object>>();
		if (groupList!=null && groupList.size() > 0) {
			if (groupList!=null && groupList.size() > 0) {
				for (int j=0; j < groupList.size(); j++) {
					String groupId = String.valueOf(groupList.get(j));
					if (groupId.equals("0")) { // 根目录剔除
						continue;
					}
					// 获取当前登录信息
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("insertBy", currentUserId);
					params.put("insertTime", new Date());
					params.put("baseUserId", id);
					params.put("groupId", Integer.parseInt(groupId));
					params.put("managerType", Constant.MANAGER_GROUP);
					managerList.add(params);
				}
			}
		}
		// 不管理的人员
		if (userList != null && userList.size() > 0) {
			for (int i = 0; i < userList.size(); i++) {
				Map<String, Integer> m = userList.get(i);
				if (m.get("belongToGroup").equals("0")) { // 根目录剔除
					continue;
				}
				// 获取当前登录信息
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("userId", m.get("id"));
				params.put("insertBy", currentUserId);
				params.put("insertTime", new Date());
				params.put("baseUserId", id);
				params.put("groupId", m.get("belongToGroup"));
				params.put("managerType", Constant.MANAGER_MINUS__USER);
				managerList.add(params);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", managerList);
		if (managerList != null && managerList.size() > 0)
			return sysUserDao.saveGroupManagersRelation(map);
		else
			return 1;
	}

	@Override
	public int deleteSysUser(Integer id) {
		// 解除师生关系
		sysUserDao.deleteTeacherRelation(id);
		// 删除用户对应的角色
		sysUserDao.deleteUserAndRoleRelation(id);
		// 删除用户
		return sysUserDao.deleteSysUser(id);
	}

	@Override
	public SysUserVO getSysUserById(Integer id) {
		return sysUserDao.getSysUserById(id);
	}

	@Override
	public long isSameUserByPhone(String phone) {
		return sysUserDao.isSameUserByPhone(phone);
	}

	@Override
	public Page<Map<String, Object>> getAllSysUsers(Map<String, Object> paramMap) {
		return sysUserDao.getAllSysUsers(paramMap);
	}

	@Override
	public Page<SysUserVO> getComplexSysUsers(Map<String, Object> paramMap) {
		return sysUserDao.getComplexSysUsers(paramMap);
		// return sysUserDao.getComplexSysUsersByJoin(paramMap);
	}

	@Override
	public long getSysUserCount(Map<String, Object> paramMap) {
		return sysUserDao.getSysUserCount(paramMap);
	}

	@Override
	@Deprecated
	public Map<String, Object> deleteMany(String[] idStr) {
		// Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < idStr.length; i++) {
			// 判断用户是否与学生老师有关系
		}
		return null;
	}

	@Override
	public List<Map<String, String>> getUserByGroupId(Integer groupId) {
		SysGroupVO sysGroupVO = new SysGroupVO();
		sysGroupVO.setGroupId(groupId);
		return sysUserDao.getUserByGroupId(sysGroupVO);
	}

	@Override
	public List<Map<String, String>> getGroupIdByUserId(Integer userId) {
		return sysUserDao.getGroupIdByUserId(userId);
	}

	@Override
	public List<String> getRoleIdByUserId(Integer userId) {
		return sysUserDao.getRoleIdByUserId(userId);
	}

	@Override
	public List<Map<String, String>> getManagerByUserId(Map<String, Object> paramMap) {
		return sysUserDao.getManagerByUserId(paramMap);
	}

	@Override
	public long judgeExistsEnableRelation(Integer userId) {
		return sysUserDao.judgeExistsEnableRelation(userId);
	}

	/**
	 * 上传用户头像到服务器的临时文件 //如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解
	 * //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解
	 * 
	 * @return upload file folder path.
	 */
	public Object uploadImage(MultipartFile[] myfiles, HttpServletRequest request) throws IOException {
		Object obj = FileUploaderUtil.uploadFile(myfiles, "userTempPhotoPaht", "", LIMIT_FILE_SIZE, request);
		return obj;
	}

	@Override
	public boolean moveFile(String fileName, HttpServletRequest request) {
		String orignPath = request.getSession().getServletContext().getRealPath(tempImgLocationPath) + "/" + fileName;
		String destPath = request.getSession().getServletContext().getRealPath(realImgLocationPath);
		File orignFile = new File(orignPath); // 源文件
		File destFile = new File(destPath); // 目标文件夹
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		return orignFile.renameTo(new File(destFile, orignFile.getName()));
	}

	public static String getTempimglocationpath() {
		return tempImgLocationPath;
	}

	public static String getRealimglocationpath() {
		return realImgLocationPath;
	}

	/********************** 以下是用户登录有关的方法 *******************/

	public SysUserVO getSysUserByTel(String tel) {
		SysUserVO user = new SysUserVO();
		user.setTel(tel);
		user.setIsEnable(Constant.IS_ENABLE);
		return sysUserDao.getSysUserByTelAndStatus(user);
	}

	public void updateUser(String tel, SysUserVO user) {
		SysUserVO sysUserVO = this.getSysUserByTel(tel);
		sysUserVO.setPassword(user.getPassword());
		updatePWD(sysUserVO);
	}

	/**
	 * @Description : 通过用户名密码查询用户
	 */
	public SysUserVO getSysUserByTelAndPWD(SysUserVO user) {
		return sysUserDao.getSysUserByTelAndStatus(user);
		// // 如果用户输入的密码和数据库密码一致，查到
		// if(this.toVerifyPassword(user.getPassword(), dbUser.getPassword())){
		// return dbUser;
		// }else{
		// return null;
		// }
	}

	/**
	 * @Description :判断密码是否相等 note: 此方法有AOP拦截执行前对用户输入的密码加密
	 */
	public Boolean toVerifyPassword(String inputPassword, String dbPassword) {
		if (dbPassword.equals(inputPassword)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void saveCalculatedLoginTimeAndFrequency(SysUserVO dbUser) {
		Date date = new Date();
		if(dbUser.getLoginFrequency()==null || dbUser.getLoginFrequency().equals(0)){
			dbUser.setFirstLoginTime(date);
		}
		dbUser.setLastLoginTime(date);
		dbUser.setLoginFrequency(dbUser.getLoginFrequency().intValue() + 1);
		dbUser.setUpdateTime(new Date());
		dbUser.setUpdateBy(dbUser.getId());
		this.sysUserDao.updateSysUser(dbUser);
	}

	@Deprecated
	// method not use
	public void addResourceForUser(SysUserVO sysUserVO) {
		Map<String, Object> resourceMap = new HashMap<String, Object>();
		for (int i = 1; i < 4; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("level", i);
			map.put("userId", sysUserVO.getId());
			map.put("sortColumns", "sort");
			List<SysResourceVO> list = sysResourceDao.getSysResourcesByLevel(map);
			if (i == 1) {
				resourceMap.put(Constant.LEVEL1, list);
			} else if (i == 2) {
				resourceMap.put(Constant.LEVEL2, list);
			} else if (i == 3) {
				resourceMap.put(Constant.LEVEL3, list);
			}
		}
		// sysUserVO.setResourceMap(resourceMap);
	}

	public List<SysResourceVO> getAllUserResourceByPrivilege(Integer userId) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("userId", userId);
		return this.sysResourceDao.getAllUserResourceByPrivilege(parameterMap);
	}
	
	/**
	 * 是否存在对应的资源
	 */
	@Override
	public Boolean checkIfExistsUserResource(Integer userId, String remark) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("userId", userId);
		parameterMap.put("remark", remark);
	    List<SysResourceVO> userResourceByPrivilege = this.sysResourceDao.getAllUserResourceByPrivilege(parameterMap);
	    if(userResourceByPrivilege.size()>0){
	    	return true;
	    }
	    return false;
	}

	public TreeNode getAllNodeForCurrentUser(Integer userId) {
		TreeNode node = new TreeNode();
		node.setId("0");
		node.setText("根目录");
		node.setUrl("");
		getChildrenNode(node, userId);
		return node;
	}

	private void getChildrenNode(TreeNode parentNode, Integer userId) {
		Map<String, Integer> parameterMap = new HashMap<String, Integer>();
		parameterMap.put("parentId", Integer.parseInt(parentNode.getId()));
		parameterMap.put("userId", userId);
		List<Map<String, String>> pList = sysUserDao.getSysResourceByParentIdAndPrivilege(parameterMap);
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for (Map<String, String> map : pList) {
			TreeNode node = new TreeNode();
			node.setId(String.valueOf(map.get("id")));
			node.setText(map.get("resource_name"));
			node.setUrl(map.get("url"));
			getChildrenNode(node, userId);
			treeList.add(node);
		}
		if (treeList.size() > 0)
			parentNode.setChildrenList(treeList);
	}

	@Override
	public void updatePWDAndSendToTel(SysUserVO dbUser) {
		String password = dbUser.getPassword();
		updatePWD(dbUser);
		sendMessageVoid.sendMessage("密码已经重置。账号：" + dbUser.getTel() + "；密码：" + password + "你的手机号：" + dbUser.getTel(),
				dbUser.getTel(),  LogFactory.getLog("userpassword"));
	}

	@Override
	public void updatePWD(SysUserVO dbUser) {
		// 此方法被AOP拦截，对密码加密
		sysUserDao.updateSysUserPWD(dbUser);
	}

	@Override
	public int saveMacAndUser(String tel, List<String> macs, int sessionUserId) {
		SysUserVO user = this.getSysUserByTel(tel);
		int userId = user.getId();
		List<SysWhiteListVO> list = new ArrayList<SysWhiteListVO>();
		for (String mac : macs) {
			SysWhiteListVO sysWhiteListVO = new SysWhiteListVO();
			sysWhiteListVO.setInsertBy(sessionUserId);
			sysWhiteListVO.setIp("user input");
			sysWhiteListVO.setMac(mac);
			sysWhiteListVO.setUserId(userId);
			list.add(sysWhiteListVO);
		}
		return sysWhiteListDao.saveSysWhiteList(list);
	}

	@Override
	public List<String> getSysWhiteLists(SysUserVO user) {
		int userId = user.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<SysWhiteListVO> list = sysWhiteListDao.getSysWhiteLists(map);
		List<String> listMacs = new ArrayList<String>();
		for (SysWhiteListVO sysWhiteListVO : list) {
			listMacs.add(sysWhiteListVO.getMac());
		}
		return listMacs;
	}

	@Override
	public int deleteSysWhiteByUser(Integer userId) {
		return sysWhiteListDao.deleteSysWhiteByUser(userId);
	}

	/**
	 * 用户登录成功时查询所有用户的拥有的资源， 返回一个大的list， 具体导航菜单由前端来处理
	 * 
	 * @param sysResourceVO
	 *            [treeCode]
	 */
	@Override
	public List<SysUserVO> getAllUsersByResourceCode(SysResourceVO sysResourceVO) {
		return this.sysResourceDao.getAllUsersByResourceCode(sysResourceVO);
	}
}
