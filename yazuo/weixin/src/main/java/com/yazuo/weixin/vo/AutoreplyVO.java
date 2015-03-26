package com.yazuo.weixin.vo;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class AutoreplyVO implements Comparable<AutoreplyVO>{
	private Integer id;
	private Integer brandId; // 商户id
	private String keywordType; // 匹配类型  equals、like
	private String keyword; // 匹配值
	private String replyContent; // 回复内容
	private String replyType; // 回复模式
	private Boolean isDelete; // 是否删除
	private Timestamp updateTime;
	private List<ImageConfigVO> imageConfigList;
	private Integer eventType; //事件类型  1：点击关注下发图文；2：非关注时
	private Integer specificType; // 特殊类型     1 是已规定的菜单key对应的自动回复 ； 2 普通非限制会员自定义回复；3普通限制会员自定义回复
	
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
	public String getKeywordType() {
		return keywordType;
	}
	public void setKeywordType(String keywordType) {
		this.keywordType = keywordType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getReplyContent() {
		return replyContent;
	}
	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}
	public String getReplyType() {
		return replyType;
	}
	public void setReplyType(String replyType) {
		this.replyType = replyType;
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
	public List<ImageConfigVO> getImageConfigList() {
		return imageConfigList;
	}
	public void setImageConfigList(List<ImageConfigVO> imageConfigList) {
		this.imageConfigList = imageConfigList;
	}
	public Integer getEventType() {
		return eventType;
	}
	
	public void setEventType(Integer eventType) {
		this.eventType = eventType;
	}
	public Integer getSpecificType() {
		return specificType;
	}
	public void setSpecificType(Integer specificType) {
		this.specificType = specificType;
	}
	public int compareTo(AutoreplyVO o) {
		String akeyword = this.getKeyword();
		String bkeyword = o.getKeyword();
		if(StringUtils.isEmpty(akeyword)){
			return 1;
		}else if(StringUtils.isEmpty(bkeyword)){
			return -1;
		}else if(!StringUtils.isEmpty(akeyword) && !StringUtils.isEmpty(bkeyword)){
			return akeyword.compareTo(bkeyword);
		}else{
			return 0;
		}
	}
	
}
