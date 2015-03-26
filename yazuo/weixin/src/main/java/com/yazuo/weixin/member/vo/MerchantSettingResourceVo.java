package com.yazuo.weixin.member.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class MerchantSettingResourceVo implements Serializable {

	private Integer id;
	private Integer brandId; // 商户id
	private Integer resourceId; //设置资料表id
	private Date insertTime; // 创建时间
	private String[] resourceArray; // 选择设置资源信息
	private Integer dataSource; // 来源  14是wifi设置的；1是微信设置的
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public String[] getResourceArray() {
		return resourceArray;
	}
	public void setResourceArray(String[] resourceArray) {
		this.resourceArray = resourceArray;
	}
	public Integer getDataSource() {
		return dataSource;
	}
	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}
}
