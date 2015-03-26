/** 
* @author
* @version 创建时间：2015-1-22 上午11:54:48 
* 类说明 
*/ 
package com.yazuo.weixin.user.vo;

import java.util.Date;

public class UserInfoVo {
	private Integer id;
	private Integer userId;
	private String loginUser; // 登录名
	private String userName; // 用户名
	private String mobile; // 手机号
	private String password; // 密码
	private int supperUser; // 是否超级用户； 1 是 ，0否
	private Boolean isEnable; // 是否可用
	private Date insertTime; // 插入时间
	private Date updateTime; // 更新时间
	
	private String oldPassword;
	private String tempEnable;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSupperUser() {
		return supperUser;
	}
	public void setSupperUser(int supperUser) {
		this.supperUser = supperUser;
	}
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getTempEnable() {
		return tempEnable;
	}
	public void setTempEnable(String tempEnable) {
		this.tempEnable = tempEnable;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
