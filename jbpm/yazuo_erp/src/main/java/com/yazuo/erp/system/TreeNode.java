/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.system;

import java.util.List;

/**
 * 封装树形结构相关内容
 * @author kyy
 * @date 2014-6-5 上午10:53:53
 */
public class TreeNode {

	private String id; // 节点id唯一标识
	private String text; // 节点名称
	private String url; // 访问路径
	private String remark;//资源编码
	private List<TreeNode> childrenList; // 子节点
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<TreeNode> getChildrenList() {
		return childrenList;
	}
	public void setChildrenList(List<TreeNode> childrenList) {
		this.childrenList = childrenList;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
