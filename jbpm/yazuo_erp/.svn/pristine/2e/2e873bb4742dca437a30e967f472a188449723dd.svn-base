/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.vo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


public class TraAttachmentVO implements java.io.Serializable{

    private static final Map<String, String> stringToValMap;
    static {
        stringToValMap = new HashMap<String, String>();
        stringToValMap.put("txt", "0");
        stringToValMap.put("doc", "1");
        stringToValMap.put("docx", "2");
        stringToValMap.put("ppt", "3");
        stringToValMap.put("pptx", "4");
        stringToValMap.put("avi", "5");
        stringToValMap.put("jpg", "6");
        stringToValMap.put("mp3", "7");
        stringToValMap.put("others", "10");
    }

    private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TraAttachment";
	public static final String ALIAS_ID = "附件ID";
	public static final String ALIAS_ATTACHMENT_NAME = "附件名称";
	public static final String ALIAS_ATTACHMENT_TYPE = "附件类型";
	public static final String ALIAS_ATTACHMENT_SIZE = "附件大小";
	public static final String ALIAS_ATTACHMENT_PATH = "附件路径";
	public static final String ALIAS_ATTACHMENT_SUFFIX = "附件后缀";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_IS_DOWNLOADABLE = "是否可下载";
	public static final String ALIAS_HOURS = "时长";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";

	//columns START
	private java.lang.Integer id;
	private java.lang.String attachmentName;
	private java.lang.String attachmentType;
	private java.lang.String attachmentSize;
	private java.lang.String attachmentPath;
	private java.lang.String attachmentSuffix;
	private java.lang.String isEnable;
	private java.lang.String isDownloadable;
	private BigDecimal hours;
	private java.lang.String remark;
	private java.lang.Integer insertBy;
	private java.util.Date insertTime;
	private java.lang.Integer updateBy;
	private java.util.Date updateTime;
	//columns END
    private String uri;
    //石山视频 获取某个视频的视频截图(snapshots.list)
    private Object smvpAttachData;
    
	public Object getSmvpAttachData() {
		return smvpAttachData;
	}

	public void setSmvpAttachData(Object smvpAttachData) {
		this.smvpAttachData = smvpAttachData;
	}

	public TraAttachmentVO(){
	}

	public TraAttachmentVO(
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
	public void setAttachmentName(java.lang.String value) {
		this.attachmentName = value;
	}
	
	public java.lang.String getAttachmentName() {
		return this.attachmentName;
	}
	public void setAttachmentType(java.lang.String value) {
		this.attachmentType = value;
	}
	
	public java.lang.String getAttachmentType() {
		return this.attachmentType;
	}
	public void setAttachmentSize(java.lang.String value) {
		this.attachmentSize = value;
	}
	
	public java.lang.String getAttachmentSize() {
		return this.attachmentSize;
	}
	public void setAttachmentPath(java.lang.String value) {
		this.attachmentPath = value;
	}
	
	public java.lang.String getAttachmentPath() {
		return this.attachmentPath;
	}
	public void setAttachmentSuffix(String value) {
        String realVal = stringToValMap.get(value.toLowerCase());
        this.attachmentSuffix = realVal;
        if (realVal == null) {
            this.attachmentSuffix = stringToValMap.get("others");
        }
    }

    public java.lang.String getAttachmentSuffix() {
		return this.attachmentSuffix;
	}
	public void setIsEnable(java.lang.String value) {
		this.isEnable = value;
	}
	
	public java.lang.String getIsEnable() {
		return this.isEnable;
	}
	public void setIsDownloadable(java.lang.String value) {
		this.isDownloadable = value;
	}
	
	public java.lang.String getIsDownloadable() {
		return this.isDownloadable;
	}
	public void setHours(BigDecimal value) {
		this.hours = value;
	}
	
	public BigDecimal getHours() {
		return this.hours;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("AttachmentName",getAttachmentName())
			.append("AttachmentType",getAttachmentType())
			.append("AttachmentSize",getAttachmentSize())
			.append("AttachmentPath",getAttachmentPath())
			.append("AttachmentSuffix",getAttachmentSuffix())
			.append("IsEnable",getIsEnable())
			.append("IsDownloadable",getIsDownloadable())
			.append("Hours",getHours())
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
		if(obj instanceof TraAttachmentVO == false) return false;
		if(this == obj) return true;
		TraAttachmentVO other = (TraAttachmentVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

