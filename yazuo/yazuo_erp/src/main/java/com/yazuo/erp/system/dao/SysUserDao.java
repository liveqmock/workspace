/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.demo.vo.SQLAdapter;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysGroupVO;
import com.yazuo.erp.system.vo.SysRoleVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @跟mapper xml文件中的方法对应
 * @author yazuo
 * @date 2014-6-3 下午6:25:50
 */
@Repository
public interface SysUserDao {
	/**
	 * 测试执行sql脚本
	 */
	List<Map<String, String>> runSqlScript(SQLAdapter sql);

	Integer saveSysUser(SysUserVO sysUserVO);
	
	/**保存部门及人员关系表*/
	void saveUserAndDeptRelation(List<Map<String, Object>> params);
	
	/**保存与此人员管理关系表*/
	int saveGroupManagersRelation(Map<String, Object> params);
	
	/**保存部门及人员关系表*/
	int saveUserAndRoleRelation(Map<String, Object> params);
	
	int updateSysUser(SysUserVO sysUserVO);
	
	/**修改时删除部门及人员关系表*/
	void deleteUserAndDeptRelation(Integer userId);
	
	/**修改时删除与此人员管理关系表*/
	void deleteGroupManagersRelation(Integer userId);
	
	/**修改时删除部门及人员关系表*/
	void deleteUserAndRoleRelation(Integer userId);
	
	int deleteSysUser(Integer id);
	
	SysUserVO getSysUserById(Integer id);
	
	long isSameUserByPhone(String phone);
	
    Page<Map<String, Object>> getAllSysUsers(Map<String, Object> paramMap);
	 
	Page<SysUserVO> getComplexSysUsers(Map<String, Object> paramMap);
	
	Page<SysUserVO> getComplexSysUsersForBes(Map<String, Object> paramMap);
	
	Page<SysUserVO> getComplexSysUsersByJoin(Map<String, Object> paramMap);
	
	long getSysUserCount (Map<String, Object> paramMap);
	
	int deleteMany (String idStr);
	
	/**根据组织id获取下面的用户*/
	List<Map<String, String>> getUserByGroupId (SysGroupVO sysGroupVO);
	
	/**通过手机号和状态查找用户*/
	SysUserVO getSysUserByTelAndStatus(SysUserVO sysUserVO);
	/**通过手机号和密码查找用户*/
	SysUserVO getSysUserByTelAndPWD(SysUserVO sysUserVO);
	
	/**取选中的部门*/
	List<Map<String, String>> getGroupIdByUserId(Integer userId);
	/**取选中的权限组*/
	List<String> getRoleIdByUserId(Integer userId);
	/**取员工管理关系表*/
	List<Map<String, String>> getManagerByUserId(Map<String, Object> param);
	
	/**判断是否存在师生关系*/
	long judgeExistsEnableRelation(Integer userId);
	
	/**解除师生关系*/
	void deleteTeacherRelation(Integer userId);
	/**
	 *  用户登录成功时查询用户用户的资源 
	 */
	List<Map<String, String>>  getSysResourceByParentIdAndPrivilege(Map<String, Integer> paramMap);
	
	int updateSysUserPWD(SysUserVO sysUserVO);
	
	List<SysRoleVO> selectRoles(int userId);
	
	List<Map<String, Object>> getSysUserByTelAndStatusList(List<String> lists);
	/**
	 * @Description 查询所有的有效用户
	 * @return
	 * @throws 
	 */
	List<SysUserVO> getSysUserList();

	/**
	 * @Description 根据部门ID列表查询老员工列表
	 * @param idList
	 * @return
	 * @throws 
	 */
	List<SysUserVO> getSysUserByGroupIdList(List<Integer> idList);

	/**
	 * @Description 根据职位ID列表查询老员工列表
	 * @param idList
	 * @return
	 * @throws 
	 */
	List<SysUserVO> getSysUserByPositionIdList(List<Integer> idList);

	/**
	 * @Description 查询老员工信息
	 * @param input
	 * @return
	 * @return Page<SysUserVO>
	 * @throws
	 */
	Page<SysUserVO> getFormalSysUserList(Map<String, Object> input);

	/**
	 * @Description 根据用户ID查询详细信息 
	 * @param studentId
	 * @return
	 * @return Map<String,Object>
	 * @throws 
	 */
	Map<String, Object> getUserInfoById(Integer id);

	/**
	 * @Description 判断用户是否具有指定remark权限
	 * @param userId
	 * @param string
	 * @return
	 * @return int
	 * @throws 
	 */
	int checkPermission(@Param("userId")Integer userId, @Param("permissionRemark")String permissionRemark);

	/**
	 * 得到用户列表信息
	 *
	 * @param userIds
	 * @return
	 */
	List<SysUserVO> getUsersByIds(@Param("userIds") List<Integer> userIds);

	/**
	 * 根据职位得到用户信息
	 *
	 * @param positionIds
	 * @return
	 */
	List<SysUserVO> getAllUsersByPositionIds(@Param("positionIds") List<Integer> positionIds);

}
