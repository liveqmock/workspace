package com.yazuo.api.service.account.user;

import java.util.Set;

import com.yazuo.api.service.account.exception.AccountCheckedException;
import com.yazuo.api.service.account.vo.LoginUser;
import com.yazuo.api.service.account.vo.authority.AuthorityVo;
import com.yazuo.api.service.account.vo.role.RoleVo;
import com.yazuo.api.service.account.vo.userInfo.UserInfoVo;

/**
 * 用戶查詢呢相關接口
 * 
 * @ClassName: UserAuthWebService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author libin
 * @date 2015-1-26 下午5:35:24
 * 
 */
public interface UserAuthWebService {
	/**
	 * 获得用户的所有产品列表, 从数据库取数据
	 * 
	 * @param userId
	 * @param productId
	 * @return
	 * @throws AccountCheckedException
	 * @throws Exception
	 */
	public Set<Integer> getAuthProduct(int userId, int productId)
			throws AccountCheckedException, Exception;

	/**
	 * 从缓存获取用户产品权限,
	 * 
	 * @Title: getUserProduct
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param userId
	 * @param @param productId
	 * @param @return
	 * @param @throws AccountCheckedException
	 * @param @throws Exception 设定文件
	 * @return Set<Authority> 返回类型
	 * @auth libin
	 * @throws
	 */
	public Set<AuthorityVo> getUserAuth(int userId, int productId)
			throws AccountCheckedException, Exception;

	/**
	 * 获得用户信息
	 * 
	 * @Title: getUser
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param userId
	 * @param @param type
	 * @param @return
	 * @param @throws AccountCheckedException
	 * @param @throws Exception 设定文件
	 * @return UserInfoVo 返回类型
	 * @auth libin
	 * @throws
	 */
	public UserInfoVo getUser(String userId, int type)
			throws AccountCheckedException, Exception;

	/**
	 * 
	 * @Title: getUserAuths
	 * @Description: 获得用户权限ID
	 * @param @param userId
	 * @param @return
	 * @param @throws AccountCheckedException
	 * @param @throws Exception 设定文件
	 * @return Set<AuthorityVo> 返回类型
	 * @auth libin
	 * @throws
	 */
	public Set<AuthorityVo> getUserAuths(int userId)
			throws AccountCheckedException, Exception;

	/**
	 * 
	 * @Title: getUserRoles
	 * @Description: 获得用户角色
	 * @param @param userId
	 * @param @return
	 * @param @throws AccountCheckedException
	 * @param @throws Exception 设定文件
	 * @return Set<RoleVo> 返回类型
	 * @auth libin
	 * @throws
	 */
	public Set<RoleVo> getUserRoles(int userId) throws AccountCheckedException,
			Exception;

	/**
	 * 
	 * 获得用户门店
	 * 
	 */
	public Set<Integer> getUserMerchants(int userId)
			throws AccountCheckedException, Exception;

	/**
	 * 用户登录返回用户信息
	 * 
	 * @param userName
	 * @param password
	 * @param flag
	 * @return
	 * @throws AccountCheckedException
	 * @throws Exception
	 */
	public LoginUser login(String userName, String password, int flag)
			throws AccountCheckedException, Exception;

	/**
	 * 同步数据库数据到dubbo服务
	 * 
	 * @Title: resetCache
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @throws AccountCheckedException
	 * @param @throws Exception 设定文件
	 * @return void 返回类型
	 * @auth libin
	 * @throws
	 */
	public void resetCache() throws AccountCheckedException, Exception;

}
