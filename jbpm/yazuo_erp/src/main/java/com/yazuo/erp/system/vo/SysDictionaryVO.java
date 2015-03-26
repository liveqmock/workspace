/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.system.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


public class SysDictionaryVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "SysDictionary";
	public static final String ALIAS_ID = "字典ID";
	public static final String ALIAS_DICTIONARY_TYPE = "类型";
	public static final String ALIAS_DICTIONARY_NAME = "名称";
	public static final String ALIAS_DICTIONARY_KEY = "键";
	public static final String ALIAS_DICTIONARY_VALUE = "值";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";

	//columns START
	private java.lang.Integer id;
	private java.lang.String dictionaryType;
	private java.lang.String dictionaryName;
	private java.lang.String dictionaryKey;
	private java.lang.String dictionaryValue;
	private java.lang.String remark;
	private java.lang.Integer insertBy;
	private java.util.Date insertTime;
	private java.lang.Integer updateBy;
	private java.util.Date updateTime;
	//columns END

	public SysDictionaryVO(){
	}

	public SysDictionaryVO(
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
	public void setDictionaryType(java.lang.String value) {
		this.dictionaryType = value;
	}
	
	public java.lang.String getDictionaryType() {
		return this.dictionaryType;
	}
	public void setDictionaryName(java.lang.String value) {
		this.dictionaryName = value;
	}
	
	public java.lang.String getDictionaryName() {
		return this.dictionaryName;
	}
	public void setDictionaryKey(java.lang.String value) {
		this.dictionaryKey = value;
	}
	
	public java.lang.String getDictionaryKey() {
		return this.dictionaryKey;
	}
	public void setDictionaryValue(java.lang.String value) {
		this.dictionaryValue = value;
	}
	
	public java.lang.String getDictionaryValue() {
		return this.dictionaryValue;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}
	public void setInsertBy(java.lang.Integer value) {
		this.insertBy = value;
	}
	
	public java.lang.Integer getInsertBy() {
		return this.insertBy;
	}
	public void setInsertTime(java.util.Date value) {
		this.insertTime = value;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
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

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("DictionaryType",getDictionaryType())
			.append("DictionaryName",getDictionaryName())
			.append("DictionaryKey",getDictionaryKey())
			.append("DictionaryValue",getDictionaryValue())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
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
		if(obj instanceof SysDictionaryVO == false) return false;
		if(this == obj) return true;
		SysDictionaryVO other = (SysDictionaryVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

