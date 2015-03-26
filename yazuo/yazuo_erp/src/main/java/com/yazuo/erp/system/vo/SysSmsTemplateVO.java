package com.yazuo.erp.system.vo;

import java.util.Arrays;
import java.util.Date;

/**
 * 短信模板功能
 */
public class SysSmsTemplateVO {
    public static enum TPL_TYPE{
        MONTHLY("1","月报");

        TPL_TYPE(String val,String title) {
            this.val = val;
            this.title = title;
        }
        private String val;
        private String title;

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
    private Integer id;
    private String title;
    private String content;
    private String tplType;
    private Date createdTime;
    private String[] roleTypes;//商户联系人
    private String[] userTypes;//商户负责人
    private Integer[] groupIds;//部门
    private Integer[] userIds;//员工
    private Integer[] positionIds;//职位

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTplType() {
        return tplType;
    }

    public void setTplType(String tplType) {
        this.tplType = tplType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String[] getRoleTypes() {
        return roleTypes;
    }

    public void setRoleTypes(String[] roleTypes) {
        this.roleTypes = roleTypes;
    }

    public String[] getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(String[] userTypes) {
        this.userTypes = userTypes;
    }

    public Integer[] getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(Integer[] groupIds) {
        this.groupIds = groupIds;
    }

    public Integer[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Integer[] userIds) {
        this.userIds = userIds;
    }

    public Integer[] getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(Integer[] positionIds) {
        this.positionIds = positionIds;
    }

    @Override
    public String toString() {
        return "SysSmsTemplate{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdTime=" + createdTime +
                ", roleTypes=" + Arrays.toString(roleTypes) +
                ", userTypes=" + Arrays.toString(userTypes) +
                ", groupIds=" + Arrays.toString(groupIds) +
                ", userIds=" + Arrays.toString(userIds) +
                ", positionIds=" + Arrays.toString(positionIds) +
                '}';
    }
}
