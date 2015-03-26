package com.yazuo.weixin.vo;

import java.sql.Timestamp;

public class ActivityRecordVO {
private Integer id;
private Integer activityId;
private Boolean isDone;
private Timestamp operateTime;
private String couponId;
private String weixinId;
private String name;
private Integer brandId;
private Integer activityConfigId;
private Timestamp startTime;
private Timestamp endTime;
private Boolean isAward;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getActivityId() {
	return activityId;
}
public void setActivityId(Integer activityId) {
	this.activityId = activityId;
}
public Boolean getIsDone() {
	return isDone;
}
public void setIsDone(Boolean isDone) {
	this.isDone = isDone;
}
public Timestamp getOperateTime() {
	return operateTime;
}
public void setOperateTime(Timestamp operateTime) {
	this.operateTime = operateTime;
}
public String getCouponId() {
	return couponId;
}
public void setCouponId(String couponId) {
	this.couponId = couponId;
}
public String getWeixinId() {
	return weixinId;
}
public void setWeixinId(String weixinId) {
	this.weixinId = weixinId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Integer getBrandId() {
	return brandId;
}
public void setBrandId(Integer brandId) {
	this.brandId = brandId;
}
public Integer getActivityConfigId() {
	return activityConfigId;
}
public void setActivityConfigId(Integer activityConfigId) {
	this.activityConfigId = activityConfigId;
}
public Timestamp getStartTime() {
	return startTime;
}
public void setStartTime(Timestamp startTime) {
	this.startTime = startTime;
}
public Timestamp getEndTime() {
	return endTime;
}
public void setEndTime(Timestamp endTime) {
	this.endTime = endTime;
}
public Boolean getIsAward() {
	return isAward;
}
public void setIsAward(Boolean isAward) {
	this.isAward = isAward;
}

}
