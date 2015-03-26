/** 
* @author kyy
* @version 创建时间：2015-1-22 上午11:39:38 
* 类说明  微信后台用户管理
*/ 
package com.yazuo.weixin.user.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.user.vo.UserInfoVo;
import com.yazuo.weixin.util.StringUtil;

@Repository
public class UserManagerDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	/**保存用户信息*/
	public int saveUser(UserInfoVo user) {
		String sql = "insert into weixin.weixin_user_info(login_user,user_name, mobile,password,supper_user,is_enable,insert_time,update_time)values(?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, user.getLoginUser(),user.getUserName(), user.getMobile(),user.getPassword(),user.getSupperUser(),user.getIsEnable(),new Date(),new Date());
	}
	
	/**修改信息*/
	public int updateUser(UserInfoVo user){
		StringBuilder sql = new StringBuilder("update weixin.weixin_user_info set ");
		sql.append(" update_time=?");
		if (!StringUtil.isNullOrEmpty(user.getLoginUser())) {
			sql.append(", login_user='").append(user.getLoginUser()).append("'");
		}
		if (!StringUtil.isNullOrEmpty(user.getUserName())) {
			sql.append(", user_name='").append(user.getUserName()).append("'");
		}
		if (!StringUtil.isNullOrEmpty(user.getMobile())) {
			sql.append(", mobile='").append(user.getMobile()).append("'");
		}
		if (!StringUtil.isNullOrEmpty(user.getPassword())) {
			sql.append(", password='").append(user.getPassword()).append("'");
		}
		if (!StringUtil.isNullOrEmpty(String.valueOf(user.getIsEnable()))) {
			sql.append(", is_enable=").append(user.getIsEnable());
		}
		sql.append(" where id = ?");
		return jdbcTemplate.update(sql.toString(), new Date(), user.getId());
	}
	
	/**根据登录名判断是否已存在*/
	public long getUserByLoginName(String loginName) {
		String sql = "select count(*) from weixin.weixin_user_info where login_user=?";
		return jdbcTemplate.queryForLong(sql, loginName);
	}
	
	/**判断修改密码中输入的原始密码是否正确*/
	public long getUserByPwd(String pwd) {
		String sql = "select count(*) from weixin.weixin_user_info where password=?";
		return jdbcTemplate.queryForLong(sql, pwd);
	}
	
	/**取某个人信息*/
	public UserInfoVo getUserById(Integer userId){
		String sql = "select * from weixin.weixin_user_info where id=?";
		List<UserInfoVo> list = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<UserInfoVo>(UserInfoVo.class));
		if (list !=null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**取所有用户*/
	public List<UserInfoVo> getAllUser(String userName, Boolean isEnble){
		String sql = "select * from weixin.weixin_user_info where 1=1";
		if (!StringUtil.isNullOrEmpty(userName)) {
			sql +=" and user_name like '%"+userName+"%'";
		}
		if (isEnble!=null) {
			sql +=" and is_enable="+isEnble;
		}
		return jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<UserInfoVo>(UserInfoVo.class));
	}
	
	/**根据用户名和密码取用户信息*/
	public UserInfoVo getUserByLoginNameAndPwd(String loginName, String pwd) {
		String sql = "select * from weixin.weixin_user_info where login_user=? and password=? and is_enable=true";
		List<UserInfoVo> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserInfoVo>(UserInfoVo.class), loginName, pwd);
		if (list !=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
}
