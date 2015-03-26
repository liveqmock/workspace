package com.yazuo.weixin.vo;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;
//这个类保存了回复图文模式信息的详细配置
public class ImageConfigVO {
	private Integer id;  //primary key
	private Integer brandId;  
	private String descripation;   
	private Boolean isDelete;      
	private Timestamp updateTime;   
	private String imageName;   
	private MultipartFile image;   
	private String replyTitle;     
	private String replyUrl;       
	private Integer autoreplyId;  
	
	
	public Integer getAutoreplyId() {
		return autoreplyId;
	}
	public void setAutoreplyId(Integer autoreplyId) {
		this.autoreplyId = autoreplyId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	
	public String getDescripation() {
		return descripation;
	}
	public void setDescripation(String descripation) {
		this.descripation = descripation;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	public String getReplyTitle() {
		return replyTitle;
	}
	public void setReplyTitle(String replyTitle) {
		this.replyTitle = replyTitle;
	}
	public String getReplyUrl() {
		return replyUrl;
	}
	public void setReplyUrl(String replyUrl) {
		this.replyUrl = replyUrl;
	}
	
	

}
