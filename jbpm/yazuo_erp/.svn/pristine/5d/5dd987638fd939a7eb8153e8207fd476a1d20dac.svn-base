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

package com.yazuo.erp.system.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class SysDocumentDtlOptionVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "填单明细选项";
	public static final String ALIAS_ID = "选项ID";
	public static final String ALIAS_DOCUMENT_DTL_ID = "填单明细ID";
	public static final String ALIAS_OPTION_CONTENT = "选项内容";
	public static final String ALIAS_IS_SELECTED = "是否选择";
	public static final String ALIAS_IS_OPEN_TEXTAREA = "是否开启意见框";
	public static final String ALIAS_TIP = "提示语";
	public static final String ALIAS_COMMENT = "意见";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_DOCUMENT_DTL_ID = "documentDtlId";
	public static final String COLUMN_OPTION_CONTENT = "optionContent";
	public static final String COLUMN_IS_SELECTED = "isSelected";
	public static final String COLUMN_IS_OPEN_TEXTAREA = "isOpenTextarea";
	public static final String COLUMN_TIP = "tip";
	public static final String COLUMN_COMMENT = "comment";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"选项ID";
	private java.lang.Integer documentDtlId; //"填单明细ID";
	private java.lang.String optionContent; //"选项内容";
	private java.lang.String isSelected; //"是否选择";
	private java.lang.String isOpenTextarea; //"是否开启意见框";
	private java.lang.String tip; //"提示语";
	private java.lang.String comment; //"意见";
	private java.lang.String remark; //"备注";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	//columns END

	public SysDocumentDtlOptionVO(){
	}

	public SysDocumentDtlOptionVO(
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
	public void setDocumentDtlId(java.lang.Integer value) {
		this.documentDtlId = value;
	}
	
	public java.lang.Integer getDocumentDtlId() {
		return this.documentDtlId;
	}
	public void setOptionContent(java.lang.String value) {
		this.optionContent = value;
	}
	
	public java.lang.String getOptionContent() {
		return this.optionContent;
	}
	public void setIsSelected(java.lang.String value) {
		this.isSelected = value;
	}
	
	public java.lang.String getIsSelected() {
		return this.isSelected;
	}
	public void setIsOpenTextarea(java.lang.String value) {
		this.isOpenTextarea = value;
	}
	
	public java.lang.String getIsOpenTextarea() {
		return this.isOpenTextarea;
	}
	public void setTip(java.lang.String value) {
		this.tip = value;
	}
	
	public java.lang.String getTip() {
		return this.tip;
	}
	public void setComment(java.lang.String value) {
		this.comment = value;
	}
	
	public java.lang.String getComment() {
		return this.comment;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
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
			.append("DocumentDtlId",getDocumentDtlId())
			.append("OptionContent",getOptionContent())
			.append("IsSelected",getIsSelected())
			.append("IsOpenTextarea",getIsOpenTextarea())
			.append("Tip",getTip())
			.append("Comment",getComment())
			.append("Remark",getRemark())
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
		if(obj instanceof SysDocumentDtlOptionVO == false) return false;
		if(this == obj) return true;
		SysDocumentDtlOptionVO other = (SysDocumentDtlOptionVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

