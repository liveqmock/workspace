/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.system.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yazuo.erp.system.TreeNode;

import java.util.*;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

public class SysUserVO implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// alias
	public static final String TABLE_ALIAS = "SysUser";
	public static final String ALIAS_ID = "用户ID";
	public static final String ALIAS_USER_NAME = "用户名称";
	public static final String ALIAS_TEL = "手机号";
	public static final String ALIAS_PASSWORD = "密码";
	public static final String ALIAS_GENDER_TYPE = "性别";
	public static final String ALIAS_USER_IMAGE = "员工图片";
	public static final String ALIAS_BIRTHDAY = "生日";
	public static final String ALIAS_POSITION_ID = "职位ID";
	public static final String ALIAS_IS_FORMAL = "转正标志";
	public static final String ALIAS_LOGIN_TIME = "登录时间";
	public static final String ALIAS_LOGIN_FREQUENCY = "登录次数";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";

	// columns START
	private java.lang.Integer id;
	private java.lang.String userName;
	private java.lang.String tel;
	private java.lang.String password;
	private java.lang.String genderType;
	private java.lang.String userImage;
	private java.lang.String birthday;
	private java.lang.Integer positionId;
	private java.lang.String isFormal;
	private java.util.Date loginTime;
	private java.lang.Integer loginFrequency;
	private java.lang.String isEnable;
	private java.lang.String remark;
	private java.lang.Integer insertBy;
	private java.util.Date insertTime;
	private java.lang.Integer updateBy;
	private java.util.Date updateTime;
	private java.util.Date firstLoginTime;
	private java.util.Date lastLoginTime;

	private List<Integer> roleIds; // 权限组
	private Integer groupId; // 部门
	private String groupName; // 部门名称
	private List<Integer> group; // 管理的部门
	private List<Map<String, Integer>> exceptUser; // 不管理的人员
	private String fileName; // 上传文件返回的文件名
	private List<String> macs;// 用户输入的MAC地址
	private boolean hasCourse;
	private String sessionId;// 当前登录用户的sessionId
	private String userImagePath;// 用户登录成功后保存所有用户图片的相对路径,不包括文件名
	private SysPositionVO position;
	private List<SysRoleVO> listRoles;
	private Object smvp;// 石山视频分类
	private String oldPassword; // 输入的原始密码
	private String newPassword; // 新密码
	private int coursewareCount;// 正在学习的课件数

	public java.util.Date getFirstLoginTime() {
		return firstLoginTime;
	}

	public void setFirstLoginTime(java.util.Date firstLoginTime) {
		this.firstLoginTime = firstLoginTime;
	}

	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public int getCoursewareCount() {
		return coursewareCount;
	}

	public void setCoursewareCount(int count) { 
		this.coursewareCount = count;
	}

	public Object getSmvp() {
		return smvp;
	}

	public void setSmvp(Object smvp) {
		this.smvp = smvp;
	}

	public String getUserImagePath() {
		return userImagePath;
	}

	public void setUserImagePath(String userImagePath) {
		this.userImagePath = userImagePath;
	}

	public SysPositionVO getPosition() {
		return position;
	}

	public void setPosition(SysPositionVO position) {
		this.position = position;
	}

	public List<SysRoleVO> getListRoles() {
		return listRoles;
	}

	public void setListRoles(List<SysRoleVO> listRoles) {
		this.listRoles = listRoles;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public boolean isHasCourse() {
		return hasCourse;
	}

	public void setHasCourse(boolean hasCourse) {
		this.hasCourse = hasCourse;
	}

	public List<String> getMacs() {
		return macs;
	}

	public void setMacs(List<String> macs) {
		this.macs = macs;
	}

	/*
	 * 用户关联的资源
	 */
	private TreeNode treeNode;
	/*
	 * 用户关联的资源
	 */
	private List<SysResourceVO> listPrivilege;

	public List<SysResourceVO> getListPrivilege() {
		return listPrivilege;
	}

	public void setListPrivilege(List<SysResourceVO> listPrivilege) {
		this.listPrivilege = listPrivilege;
	}

	public TreeNode getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(TreeNode treeNode) {
		this.treeNode = treeNode;
	}

	public SysUserVO() {
	}

	public SysUserVO(java.lang.Integer id) {
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setUserName(java.lang.String value) {
		this.userName = value;
	}

	public java.lang.String getUserName() {
		return this.userName;
	}

	public void setTel(java.lang.String value) {
		this.tel = value;
	}

	public java.lang.String getTel() {
		return this.tel;
	}

	public void setPassword(java.lang.String value) {
		this.password = value;
	}

	public java.lang.String getPassword() {
		return this.password;
	}

	public void setGenderType(java.lang.String value) {
		this.genderType = value;
	}

	public java.lang.String getGenderType() {
		return this.genderType;
	}

	public void setUserImage(java.lang.String value) {
		this.userImage = value;
	}

	public java.lang.String getUserImage() {
		return this.userImage;
	}

	public void setBirthday(java.lang.String value) {
		this.birthday = value;
	}

	public java.lang.String getBirthday() {
		return this.birthday;
	}

	public void setPositionId(java.lang.Integer value) {
		this.positionId = value;
	}

	public java.lang.Integer getPositionId() {
		return this.positionId;
	}

	public void setIsFormal(java.lang.String value) {
		this.isFormal = value;
	}

	public java.lang.String getIsFormal() {
		return this.isFormal;
	}

	public void setLoginTime(java.util.Date value) {
		this.loginTime = value;
	}

	public java.util.Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginFrequency(java.lang.Integer value) {
		this.loginFrequency = value;
	}

	public java.lang.Integer getLoginFrequency() {
		return this.loginFrequency;
	}

	public void setIsEnable(java.lang.String value) {
		this.isEnable = value;
	}

	public java.lang.String getIsEnable() {
		return this.isEnable;
	}

	public void setRemark(java.lang.String value) {
		this.remark = value;
	}

	public java.lang.String getRemark() {
		return this.remark;
	}

	public void setInsertBy(java.lang.Integer value) {
		this.insertBy = value;
	}

	public java.lang.Integer getInsertBy() {
		return this.insertBy;
	}

	public void setInsertTime(java.util.Date value) {
		this.insertTime = value;
	}

	public java.util.Date getInsertTime() {
		return this.insertTime;
	}

	public void setUpdateBy(java.lang.Integer value) {
		this.updateBy = value;
	}

	public java.lang.Integer getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public List<Integer> getGroup() {
		return group;
	}

	public void setGroup(List<Integer> group) {
		this.group = group;
	}

	public List<Map<String, Integer>> getExceptUser() {
		return exceptUser;
	}

	public void setExceptUser(List<Map<String, Integer>> exceptUser) {
		this.exceptUser = exceptUser;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId()).append("UserName", getUserName())
				.append("Tel", getTel()).append("Password", getPassword()).append("GenderType", getGenderType())
				.append("UserImage", getUserImage()).append("Birthday", getBirthday()).append("PositionId", getPositionId())
				.append("IsFormal", getIsFormal()).append("LoginTime", getLoginTime())
				.append("LoginFrequency", getLoginFrequency()).append("IsEnable", getIsEnable()).append("Remark", getRemark())
				.append("InsertBy", getInsertBy()).append("InsertTime", getInsertTime()).append("UpdateBy", getUpdateBy())
				.append("UpdateTime", getUpdateTime()).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof SysUserVO == false)
			return false;
		if (this == obj)
			return true;
		SysUserVO other = (SysUserVO) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
}
