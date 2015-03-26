/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.TreeNode;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 用户相关操作
 * 
 * @author kyy
 * @date 2014-6-5 下午5:30:17
 */
public interface SysUserService {

	/**保存用户*/
	Map<String, Object> saveSysUser(SysUserVO sysUserVO, String [] deptIdArray,List<Integer> roleIdArray, List<Integer> groupList,List<Map<String, Integer>> userList, Integer currentUserId);
	
	/**修改用户*/
	Map<String, Object> updateSysUser(SysUserVO sysUserVO, String [] deptIdArray, Integer currentUserId);
	
	/**删除用户*/

	int deleteSysUser(Integer id);

	/** 根据id获取用户信息 */
	SysUserVO getSysUserById(Integer id);

	/** 根据手机号判断是否重复 */
	long isSameUserByPhone(String phone);

	/** 列表 */
	 Page<Map<String, Object>> getAllSysUsers(Map<String, Object> paramMap);

	/** 列表总数量 */
	long getSysUserCount(Map<String, Object> paramMap);

	/** 批量删除用户 */
	Map<String, Object> deleteMany(String [] idStr);

	/** 根据组织id获取下面的人员 */
	List<Map<String, String>> getUserByGroupId(Integer groupId);
	
	/**取选中的部门*/
	List<Map<String, String>> getGroupIdByUserId(Integer userId);
	/**取选中的权限组*/
	List<String> getRoleIdByUserId(Integer userId);
	/**取员工管理关系表*/
	List<Map<String, String>> getManagerByUserId(Map<String, Object> paramMap);
	
	/**判断是否存在师生关系*/
	long judgeExistsEnableRelation(Integer userId);
	
	/**上传用户头像*/
	public Object uploadImage(MultipartFile[] myfiles, HttpServletRequest request) throws IOException;
	
	public boolean moveFile(String orignPath, HttpServletRequest request); 
	
	/**编辑员工管理关系*/
	public int editManagerRelation(Integer id, List<Integer> groupList, List<Map<String, Integer>> userList, Integer currentUserId, boolean isUpdate);
	
	/**编辑权限组关系*/
	public int editRoleRelation(Integer id, String [] roleIdArray, Integer currentUserId, boolean isUpdate);

	/********************** 以下是用户登录有关的方法 *******************/

	/**
	 * Description:根据手机号和状态查找用户信息
	 */
	SysUserVO getSysUserByTel(String tel);

	/**
	 * Description:根据手机号更新用户信息
	 */
	void updateUser(String tel, SysUserVO user);

	/**
	 * Description:根据手机号和密码查询用户
	 */
	SysUserVO getSysUserByTelAndPWD( SysUserVO user);
	/**
	 * 
	 * @Description 为用户增加资源
	 */
	void addResourceForUser(SysUserVO sysUserVO);
	
	/**
	 * @Description :判断密码是否相等
	 * note: 此方法有AOP拦截执行前对用户输入的密码加密
	 */
	public Boolean toVerifyPassword(String string, String string2);
	/**
	 * 用户登录成功时查询所有用户的拥有的资源， 返回一个大的list， 具体导航菜单由前端来处理
	 */
	 List<SysResourceVO> getAllUserResourceByPrivilege(Integer userId);
	/**
	 * 用户登录成功时查询用户用户的资源 
	 */
	public TreeNode getAllNodeForCurrentUser(Integer userId);
	/**
	 * @Description ：更改密码并发送到手机
	 */
	void updatePWDAndSendToTel(SysUserVO dbUser);
	
	/**
	 * @Description 更新密码
	 */
    void updatePWD(SysUserVO dbUser);

	/**
	 * 测试方法，添加用户对应的mac地址
	 */
	int saveMacAndUser(String tel, List<String> macs, int sessionUserId);

	/**
	 * 按用户查找白名单
	 */
	List<String> getSysWhiteLists(SysUserVO user);

	/**
	 * 通过用户id删除白名单表
	 */
	int deleteSysWhiteByUser(Integer userId);

	/**
	 * 返回复杂的user对象，包含角色和职位
	 */
	Page<SysUserVO> getComplexSysUsers(Map<String, Object> paramMap);
	/**
	 * 用户登录成功时查询所有用户的拥有的资源， 返回一个大的list， 具体导航菜单由前端来处理
	 * @param sysResourceVO [treeCode]
	 */
	List<SysUserVO>  getAllUsersByResourceCode(SysResourceVO sysResourceVO);
	/**
	 * //记录首次登陆时间和最后一次登陆时间和登录次数
	 */
	void saveCalculatedLoginTimeAndFrequency(SysUserVO dbUser);


	Boolean checkIfExistsUserResource(Integer userId, String remark);

	List<Map<String, Object>> getUsersByResourceCode(SysResourceVO sysResourceVO);

	Page<SysUserVO> getComplexSysUsersForBes(Map<String, Object> paramMap);

    List<SysUserVO> getSysUserList();

	List<SysUserVO> getAllUsersByPositionIds(List<Integer> positionIds);

	List<SysUserVO> getUsersByIds(List<Integer> userIds);
}
