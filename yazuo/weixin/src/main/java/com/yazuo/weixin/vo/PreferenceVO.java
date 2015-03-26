package com.yazuo.weixin.vo;

public class PreferenceVO  {
private Integer id;
private Integer brandId;
private String title;
private String content;
private Boolean isNew;
private Boolean isRecommend;
private Boolean isDelete;
private Integer sortNumber;

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
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public Boolean getIsNew() {
	return isNew;
}
public void setIsNew(Boolean isNew) {
	this.isNew = isNew;
}
public Boolean getIsRecommend() {
	return isRecommend;
}
public void setIsRecommend(Boolean isRecommend) {
	this.isRecommend = isRecommend;
}
public Boolean getIsDelete() {
	return isDelete;
}
public void setIsDelete(Boolean isDelete) {
	this.isDelete = isDelete;
}
public Integer getSortNumber() {
	return sortNumber;
}
public void setSortNumber(Integer sortNumber) {
	this.sortNumber = sortNumber;
}


}
