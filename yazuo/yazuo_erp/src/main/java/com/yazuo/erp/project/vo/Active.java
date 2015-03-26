package com.yazuo.erp.project.vo;

import java.sql.Date;

public class Active implements java.io.Serializable{
	private static final long serialVersionUID = -3372743458993474505L;
	private Integer activeId;
	private String activeName;
	private Integer activeType;
	private Integer merchantId;
	private String merchantName;
	private Date activeBegin;
	private Date activeEnd;
	private String typeName;
	private Boolean hasProject;

	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Boolean getHasProject() {
		return hasProject;
	}
	public void setHasProject(Boolean hasProject) {
		this.hasProject = hasProject;
	}
	public Integer getActiveId() {
		return activeId;
	}
	public void setActiveId(Integer activeId) {
		this.activeId = activeId;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public Integer getActiveType() {
		return activeType;
	}
	public void setActiveType(Integer activeType) {
		this.activeType = activeType;
	}
	public Integer getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	public Date getActiveBegin() {
		return activeBegin;
	}
	public void setActiveBegin(Date activeBegin) {
		this.activeBegin = activeBegin;
	}
	public Date getActiveEnd() {
		return activeEnd;
	}
	public void setActiveEnd(Date activeEnd) {
		this.activeEnd = activeEnd;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}