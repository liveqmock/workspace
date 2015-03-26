package com.yazuo.weixin.minimart.vo;

import java.util.Date;

/**
* @ClassName WxTemplate
* @Description 微信模版
* @author sundongfeng@yazuo.com
* @date 2014-9-15 下午6:19:48
* @version 1.0
*/
public class WxTemplate {
	private Integer id;
	private String name;
	private String templateId;
	private String type;
	private Integer brandId;
	private Date createTime;
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
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
