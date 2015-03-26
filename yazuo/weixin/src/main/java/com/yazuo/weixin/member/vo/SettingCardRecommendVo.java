package com.yazuo.weixin.member.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class SettingCardRecommendVo implements Serializable {

	private Integer id;
	private Integer brandId; // 商户id
	private Integer cardTypeId; //卡类型id
	private Date insertTime; // 创建时间
	private Integer dataSource; // 来源
	
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
	public Integer getCardTypeId() {
		return cardTypeId;
	}
	public void setCardTypeId(Integer cardTypeId) {
		this.cardTypeId = cardTypeId;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Integer getDataSource() {
		return dataSource;
	}
	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}

}
