/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.weixin.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 功能菜单类
 * @author kyy
 * @date 2014-7-21 下午5:11:22
 */
@SuppressWarnings("serial")
public class FunctionMenu implements Serializable{

	private Integer id; // 标识
	private Integer merchantId; // 门店id
	private Integer menuType; // 菜单类型  1标示click;  2标示view;
	private String menuName; // 菜单key值
	private String menuValue; // 菜单值
	private String appId; //微信公共账号对应值
	private String appSecret;
	private String parentCode; //标识父子关系（谁是谁的二级菜单）
	private Date insertTime; // 插入的时间
	
	private List<FunctionMenu> childrenList; // 子集菜单
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	public Integer getMenuType() {
		return menuType;
	}
	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuValue() {
		return menuValue;
	}
	public void setMenuValue(String menuValue) {
		this.menuValue = menuValue;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public List<FunctionMenu> getChildrenList() {
		return childrenList;
	}
	public void setChildrenList(List<FunctionMenu> childrenList) {
		this.childrenList = childrenList;
	}
	
	
}
