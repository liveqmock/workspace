package com.yazuo.erp.video.vo;

import org.springframework.web.multipart.MultipartFile;

public class Video implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private Integer videoId;
	private String videoName;
	private String videoDesc;
	private java.sql.Timestamp uploadTime;
	private String presenter;
	private String videoPath;
	private java.sql.Timestamp insertTime;
	private Integer videoCatId;
	private MultipartFile file;
	
	private Integer catVal;
	private String catDesc;
	//存放上传临时文件的目录
	private String tempFilePath;
	public String getTempFilePath() {
		return tempFilePath;
	}

	public void setTempFilePath(String tempFilePath) {
		this.tempFilePath = tempFilePath;
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

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	
	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	
	public String getVideoDesc() {
		return videoDesc;
	}

	public void setVideoDesc(String videoDesc) {
		this.videoDesc = videoDesc;
	}
	
	@org.codehaus.jackson.map.annotate.JsonSerialize(using = com.yazuo.util.CustomDateSerializer.class)
	public java.sql.Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(java.sql.Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	public String getPresenter() {
		return presenter;
	}

	public void setPresenter(String presenter) {
		this.presenter = presenter;
	}
	
	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	
	@org.codehaus.jackson.map.annotate.JsonSerialize(using = com.yazuo.util.CustomDateSerializer.class)
	public java.sql.Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(java.sql.Timestamp insertTime) {
		this.insertTime = insertTime;
	}
	
	public Integer getVideoCatId() {
		return videoCatId;
	}

	public void setVideoCatId(Integer videoCatId) {
		this.videoCatId = videoCatId;
	}
}
