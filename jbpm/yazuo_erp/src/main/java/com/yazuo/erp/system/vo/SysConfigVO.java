/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author erp team
 * @date 
 */
public class SysConfigVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "系统配置";
	public static final String ALIAS_ID = "配置ID";
	public static final String ALIAS_CONFIG_NAME = "名称";
	public static final String ALIAS_CONFIG_KEY = "键";
	public static final String ALIAS_CONFIG_VALUE = "值";
	public static final String ALIAS_DESCRIPTION = "描述";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_CONFIG_NAME = "configName";
	public static final String COLUMN_CONFIG_KEY = "configKey";
	public static final String COLUMN_CONFIG_VALUE = "configValue";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"配置ID";
	private java.lang.String configName; //"名称";
	private java.lang.String configKey; //"键";
	private java.lang.String configValue; //"值";
	private java.lang.String description; //"描述";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	//columns END

	public SysConfigVO(){
	}

	public SysConfigVO(
		java.lang.Integer id
	){
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	public void setConfigName(java.lang.String value) {
		this.configName = value;
	}
	
	public java.lang.String getConfigName() {
		return this.configName;
	}
	public void setConfigKey(java.lang.String value) {
		this.configKey = value;
	}
	
	public java.lang.String getConfigKey() {
		return this.configKey;
	}
	public void setConfigValue(java.lang.String value) {
		this.configValue = value;
	}
	
	public java.lang.String getConfigValue() {
		return this.configValue;
	}
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	
	public java.lang.String getDescription() {
		return this.description;
	}
	public void setUpdateBy(java.lang.Integer value) {
		this.updateBy = value;
	}
	
	public java.lang.Integer getUpdateBy() {
		return this.updateBy;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	private String sortColumns;
	public String getSortColumns() {
		return sortColumns;
	}
	
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("ConfigName",getConfigName())
			.append("ConfigKey",getConfigKey())
			.append("ConfigValue",getConfigValue())
			.append("Description",getDescription())
			.append("UpdateBy",getUpdateBy())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof SysConfigVO == false) return false;
		if(this == obj) return true;
		SysConfigVO other = (SysConfigVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

