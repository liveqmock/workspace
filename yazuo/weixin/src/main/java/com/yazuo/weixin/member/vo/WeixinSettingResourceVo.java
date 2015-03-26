package com.yazuo.weixin.member.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class WeixinSettingResourceVo implements Serializable {

	private Integer id;
	private String name; // 名称
	private Integer type; // 名称对应值
	private Boolean isEnable; // 是否可用
	private String description; // 描述
	private Date insertTime; // 创建时间
	private Date updateTime; // 修改时间
	private String tempEnable; // 临时接收boolean类型
	private String resourceUrl; // 该配置跳转的地址
	private String resourceSource; // 标识是哪里的
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getTempEnable() {
		return tempEnable;
	}
	public void setTempEnable(String tempEnable) {
		this.tempEnable = tempEnable;
	}
	public String getResourceUrl() {
		return resourceUrl;
	}
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	public String getResourceSource() {
		return resourceSource;
	}
	public void setResourceSource(String resourceSource) {
		this.resourceSource = resourceSource;
	}
}
