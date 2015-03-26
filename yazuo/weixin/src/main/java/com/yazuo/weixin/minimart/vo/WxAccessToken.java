package com.yazuo.weixin.minimart.vo;


/**
* @ClassName WxAccessToken
* @Description  access_token 类
* @author sundongfeng@yazuo.com
* @date 2014-9-8 上午10:59:12
* @version 1.0
*/
public class WxAccessToken implements java.io.Serializable{
	 /** serialVersionUID**/
	private static final long serialVersionUID = 7565218655682935179L;
	private Integer id;
	 private String accessToken;
	 private Integer expiresIn;
	 private Long createTime; //毫秒数
	 private Integer brandId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	/*@CacheKeyMethod
	public String getCacheKey(){  
		return brandId.toString();
	}*/
}
