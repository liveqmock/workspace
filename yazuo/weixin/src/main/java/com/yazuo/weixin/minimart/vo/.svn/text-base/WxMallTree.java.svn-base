package com.yazuo.weixin.minimart.vo;

import java.util.List;

/**
* @ClassName WxMallTree
* @Description 用于页面展示树级结构
* @author sundongfeng@yazuo.com
* @date 2014-8-7 下午2:26:19
* @version 1.0
*/
public class WxMallTree implements Comparable<WxMallTree>{
	private Integer bigId;
	private Integer subId;
	private String bigName;
	private String subName;
	private Integer bigOrder;
    private Integer smallOrder;
	private List<WxMallTree> subTreeList;//子类
	private List<WxMallGoods> goodList;
	
	public Integer getSubId() {
		return subId;
	}
	public void setSubId(Integer subId) {
		this.subId = subId;
	}
	public String getBigName() {
		return bigName;
	}
	public void setBigName(String bigName) {
		this.bigName = bigName;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public Integer getBigId() {
		return bigId;
	}
	public void setBigId(Integer bigId) {
		this.bigId = bigId;
	}
	public Integer getBigOrder() {
		return bigOrder;
	}
	public void setBigOrder(Integer bigOrder) {
		this.bigOrder = bigOrder;
	}
	public Integer getSmallOrder() {
		return smallOrder;
	}
	public void setSmallOrder(Integer smallOrder) {
		this.smallOrder = smallOrder;
	}
	public List<WxMallTree> getSubTreeList() {
		return subTreeList;
	}
	public void setSubTreeList(List<WxMallTree> subTreeList) {
		this.subTreeList = subTreeList;
	}
	public List<WxMallGoods> getGoodList() {
		return goodList;
	}
	public void setGoodList(List<WxMallGoods> goodList) {
		this.goodList = goodList;
	}
	
	/**
	 * 增加排序功能，按照smallOrder排序
	 */
	public int compareTo(WxMallTree o) {
		Integer aa = this.getSmallOrder();
		Integer bb = o.getSmallOrder();
		String aName = this.getSubName();
		String bName = o.getSubName();
		if(aa!=null && bb!=null){
			if(aa<bb){
				return -1;
			}else if(aa>bb){
				return 1;
			}else{
				return aName.compareTo(bName);
			}
		}else{
			Integer cc = this.getBigOrder();
			Integer dd = o.getBigOrder();
			return cc-dd;
		}
	}
	
}
