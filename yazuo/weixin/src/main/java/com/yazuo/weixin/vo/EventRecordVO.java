package com.yazuo.weixin.vo;

import java.sql.Timestamp;

public class EventRecordVO {
private Integer id;
private String toUsername;
private String fromUsername;
private Integer eventId;
private String receiveContent;
private String replyContent;
private String receiveType;
private String replyType;
private Timestamp messageTime;
private Long processTime;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getToUsername() {
	return toUsername;
}
public void setToUsername(String toUsername) {
	this.toUsername = toUsername;
}
public String getFromUsername() {
	return fromUsername;
}
public void setFromUsername(String fromUsername) {
	this.fromUsername = fromUsername;
}
public Integer getEventId() {
	return eventId;
}
public void setEventId(Integer eventId) {
	this.eventId = eventId;
}
public String getReceiveContent() {
	return receiveContent;
}
public void setReceiveContent(String receiveContent) {
	this.receiveContent = receiveContent;
}
public String getReplyContent() {
	return replyContent;
}
public void setReplyContent(String replyContent) {
	this.replyContent = replyContent;
}
public String getReceiveType() {
	return receiveType;
}
public void setReceiveType(String receiveType) {
	this.receiveType = receiveType;
}
public String getReplyType() {
	return replyType;
}
public void setReplyType(String replyType) {
	this.replyType = replyType;
}
public Timestamp getMessageTime() {
	return messageTime;
}
public void setMessageTime(Timestamp messageTime) {
	this.messageTime = messageTime;
}
public Long getProcessTime() {
	return processTime;
}

@Override
public String toString() {
	return "EventRecordVO [id=" + id + ", toUsername=" + toUsername
			+ ", fromUsername=" + fromUsername + ", eventId=" + eventId
			+ ", receiveContent=" + receiveContent + ", replyContent="
			+ replyContent + ", receiveType=" + receiveType + ", replyType="
			+ replyType + ", messageTime=" + messageTime + ", processTime="
			+ processTime + "]";
}
public void setProcessTime(Long processTime) {
	this.processTime = processTime;
}




}
