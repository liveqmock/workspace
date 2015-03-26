package com.yazuo.erp.video.vo;

public class VideoCat implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private Integer videoCatId;
	private Integer catVal;
	private String catDesc;
	private java.sql.Timestamp insertTime;
	
	public Integer getVideoCatId() {
		return videoCatId;
	}

	public void setVideoCatId(Integer videoCatId) {
		this.videoCatId = videoCatId;
	}
	
	public Integer getCatVal() {
		return catVal;
	}

	public void setCatVal(Integer catVal) {
		this.catVal = catVal;
	}
	
	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}
	
	@org.codehaus.jackson.map.annotate.JsonSerialize(using = com.yazuo.util.CustomDateSerializer.class)
	public java.sql.Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(java.sql.Timestamp insertTime) {
		this.insertTime = insertTime;
	}
}
