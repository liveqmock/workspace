package com.yazuo.weixin.vo;

import java.sql.Timestamp;

/**
 * SERVICE_CONFIG 服务配置
 * 
 * @author gaoshan
 * 
 */
public class ServiceConfigVO {
	/**
	 * 服务ID
	 */
	public Integer serviceId;
	/**
	 * 服务URL
	 */
	public String url;
	/**
	 * 商户ID
	 */
	public Integer brandId;
	/**
	 * 内容1
	 */
	public String content1;
	/**
	 * 内容2
	 */
	public String content2;
	/**
	 * 内容3
	 */
	public String content3;
	/**
	 * 创建时间
	 */
	public Timestamp insertTime;
	/**
	 * 最后修改时间
	 */
	public Timestamp updateTime;

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getContent1() {
		return content1;
	}

	public void setContent1(String content1) {
		this.content1 = content1;
	}

	public String getContent2() {
		return content2;
	}

	public void setContent2(String content2) {
		this.content2 = content2;
	}

	public String getContent3() {
		return content3;
	}

	public void setContent3(String content3) {
		this.content3 = content3;
	}

	public Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "ServiceConfigVO [serviceId=" + serviceId + ", url=" + url + ", brandId=" + brandId + ", content1=" + content1 + ", content2=" + content2
				+ ", content3=" + content3 + ", insertTime=" + insertTime + ", updateTime=" + updateTime + "]";
	}

}
