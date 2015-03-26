package com.yazuo.api.service.account.user;

import java.util.List;

import com.yazuo.api.service.account.exception.AccountCheckedException;
import com.yazuo.api.service.account.vo.User;

public interface UserWebService {
	/**
	 * 添加或者修改用户信息,新建用户发送短信
	 * 
	 * @param userJson
	 * @return
	 */
	public User addOrUpdateUser(User user) throws AccountCheckedException,
			Exception;

	/**
	 * 添加权限和角色
	 * 
	 * @param userJson
	 * @return
	 */
	public User addOrUpdateRoleRelationAuth(User user)
			throws AccountCheckedException, Exception;

	/**
	 * 添加用户和角色关联关系
	 * 
	 * @param user
	 * @throws AccountCheckedException
	 */
	public void addOrUpdateUserRole(User user) throws AccountCheckedException,
			Exception;

	/**
	 * 删除用户信息,如果用户具有其他产品权限， 只删除该用户在该产品下权限
	 * 
	 * @param userIdList
	 * @return
	 */
	public void deleteUser(List<Integer> userIdList, int productId)
			throws AccountCheckedException, Exception;

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 * @param oldPasswd
	 * @param passwd
	 * @return flag: true: 使用永久密码修改永久密码， false： 使用临时密码修改永久密码
	 */
	public void updateUserPasswd(int userId, String oldPasswd, String passwd,
			boolean flag) throws AccountCheckedException, Exception;

	/**
	 * 重置临时密码
	 * 
	 * @param userId
	 * @return
	 */
	public void resetUserPasswd(int userId) throws AccountCheckedException;

	/**
	 * 添加或者修改用户信息,新建用户发送短信,并且关联用户和角色关联关系
	 * 
	 * @param userJson
	 * @return
	 */
	public User addOrUpdateUserForWifi(User user)
			throws AccountCheckedException, Exception;

}
