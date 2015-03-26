/** 
* @author kyy
* @version 创建时间：2015-1-22 上午11:39:38 
* 类说明  微信后台用户管理
*/ 
package com.yazuo.weixin.user.service;

import java.util.List;
import java.util.Map;

import com.yazuo.weixin.user.vo.UserInfoVo;

public interface UserManagerService {

	/**保存用户信息*/
	public Map<String, Object> saveUser(UserInfoVo user);
	
	/**修改用户信息*/
	public Map<String, Object> editUser(UserInfoVo user);
	
	/**判断某个登录名是否已经存在*/
	public boolean isExistsUser(String loginUser);
	
	/**修改密码*/
	public Map<String, Object> modifyPwd(String oldPwd, String newPwd, String userName, Integer userId);
	
	/**取某个用户信息*/
	public UserInfoVo getUserById(Integer userId);
	
	/**取所有用户*/
	public List<UserInfoVo> getAllUser(String userName,Boolean isEnble);
	
	/**判断输入用户名和密码是否比配*/
	public UserInfoVo getUserByLoginNameAndPwd(String loginName, String pwd);
}
