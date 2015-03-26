/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Date;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

public class TraPptDtlVO implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	// alias
	public static final String TABLE_ALIAS = "TraPptDtl";
	public static final String ALIAS_ID = "PPT详情ID";
	public static final String ALIAS_PPT_ID = "PPT的ID";
	public static final String ALIAS_SORT = "排序";
	public static final String ALIAS_PPT_DTL_NAME = "文件名称";
	public static final String ALIAS_ORIGINAL_FILE_NAME = "原始文件名";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";

	// columns START
	private java.lang.Integer id;
	private java.lang.Integer pptId;
	private java.lang.Integer sort;
	private java.lang.String pptDtlName;
	private java.lang.String originalFileName;
	private java.lang.Integer insertBy;
	private Date insertTime;
    // columns END
    private String uri;

	public TraPptDtlVO() {
	}

	public TraPptDtlVO(java.lang.Integer id) {
		this.id = id;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setPptId(java.lang.Integer value) {
		this.pptId = value;
	}

	public java.lang.Integer getPptId() {
		return this.pptId;
	}

	public void setSort(java.lang.Integer value) {
		this.sort = value;
	}

	public java.lang.Integer getSort() {
		return this.sort;
	}

	public void setPptDtlName(java.lang.String value) {
		this.pptDtlName = value;
	}

	public java.lang.String getPptDtlName() {
		return this.pptDtlName;
	}

	public java.lang.String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(java.lang.String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public void setInsertBy(java.lang.Integer value) {
		this.insertBy = value;
	}

	public java.lang.Integer getInsertBy() {
		return this.insertBy;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId()).append("PptId", getPptId())
				.append("Sort", getSort()).append("PptDtlName", getPptDtlName())
				.append("OriginalFileName", getOriginalFileName()).append("InsertBy", getInsertBy())
				.append("InsertTime", getInsertTime()).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof TraPptDtlVO == false)
			return false;
		if (this == obj)
			return true;
		TraPptDtlVO other = (TraPptDtlVO) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
}
