/** 
* @author kyy
* @version 创建时间：2015-1-22 上午11:39:38 
* 类说明  微信后台用户管理
*/ 
package com.yazuo.weixin.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.weixin.user.dao.UserManagerDao;
import com.yazuo.weixin.user.service.UserManagerService;
import com.yazuo.weixin.user.vo.UserInfoVo;
import com.yazuo.weixin.util.MD5Util;
import com.yazuo.weixin.util.StringUtil;

@Service
public class UserManagerServiceImpl implements UserManagerService{

	private static String defaultPwd = "000000";
	@Resource
	private UserManagerDao userManagerDao;
	
	@Override
	public Map<String, Object> saveUser(UserInfoVo user) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		user.setPassword(MD5Util.MD5(defaultPwd));
		user.setSupperUser(0); // 非超级用户
		// 判断是否已经存在此登录名
		boolean existsUser = isExistsUser(user.getLoginUser().trim());
		if (existsUser) {// 存在
			resultMap.put("flag", 0);
			resultMap.put("message", user.getLoginUser()+"登录名已存在!");
			return resultMap;
		}
		int count = userManagerDao.saveUser(user);
		resultMap.put("flag", count > 0 ? 1 : 0);
		resultMap.put("message", count > 0 ? "添加成功" : "添加失败");
		return resultMap;
	}

	@Override
	public Map<String, Object> editUser(UserInfoVo user) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (!StringUtil.isNullOrEmpty(user.getPassword())) {
			user.setPassword(MD5Util.MD5(user.getPassword()));
		}
		int count = userManagerDao.updateUser(user);
		resultMap.put("flag", count > 0 ? 1 : 0);
		resultMap.put("message", count > 0 ? "修改成功" : "修改失败");
		return resultMap;
	}

	@Override
	public boolean isExistsUser(String loginUser) {
		long count = userManagerDao.getUserByLoginName(loginUser);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Map<String, Object> modifyPwd(String oldPwd, String newPwd, String userName, Integer userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 判断输入的原始密码是否正确
		long count = userManagerDao.getUserByPwd(MD5Util.MD5(oldPwd));
		if (count > 0) { // 正确
			UserInfoVo user = new UserInfoVo();
			user.setPassword(MD5Util.MD5(newPwd));
			user.setId(userId);
			int c = userManagerDao.updateUser(user);
			resultMap.put("flag", c > 0 ? 1 : 0);
			resultMap.put("message", c > 0 ? "修改密码成功" : "修改密码失败");
			return resultMap;
		} else { // 错误
			resultMap.put("flag", 0);
			resultMap.put("message", "输入的原始密码错误");
			return resultMap;
		}
	}

	@Override
	public UserInfoVo getUserById(Integer userId) {
		return userManagerDao.getUserById(userId);
	}

	@Override
	public List<UserInfoVo> getAllUser(String userName, Boolean isEnble) {
		return userManagerDao.getAllUser(userName, isEnble);
	}

	@Override
	public UserInfoVo getUserByLoginNameAndPwd(String loginName, String pwd) {
		String md5Pwd = MD5Util.MD5(pwd);
		return userManagerDao.getUserByLoginNameAndPwd(loginName, md5Pwd);
	}
	
}
