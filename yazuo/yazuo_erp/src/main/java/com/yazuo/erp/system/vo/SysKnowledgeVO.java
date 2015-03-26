package com.yazuo.erp.system.vo;

import java.util.Date;

public class SysKnowledgeVO {

    private Integer id;
    private Integer kindId;
    private String title;
    private Integer merchantId;
    private Integer businessTypeId;
    private String description;
    private String analysis;
    private String resolution;
    private String successfulCase;
    private String talkingSkills;
    private Integer viewedTimes;
    private Integer attachmentId;
    private Date updatedTime;
    private Date createdTime;
    private Integer createdBy;
    private Integer updatedBy;
    // 附加字段
    private Date beginUpdatedTime;
    private Date endUpdatedTime;
    private String username;//作者
    private String businessType;//业态
    private String content;
    private Integer pageSize;
    private Integer pageNumber;
    private String kindCn;
    private SysAttachmentVO attachment;
    private String merchantName;
    private Integer score;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKindId() {
        return kindId;
    }

    public void setKindId(Integer kindId) {
        this.kindId = kindId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(Integer businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getSuccessfulCase() {
        return successfulCase;
    }

    public void setSuccessfulCase(String successfulCase) {
        this.successfulCase = successfulCase;
    }

    public String getTalkingSkills() {
        return talkingSkills;
    }

    public void setTalkingSkills(String talkingSkills) {
        this.talkingSkills = talkingSkills;
    }

    public Integer getViewedTimes() {
        return viewedTimes;
    }

    public void setViewedTimes(Integer viewedTimes) {
        this.viewedTimes = viewedTimes;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Date getBeginUpdatedTime() {
        return beginUpdatedTime;
    }

    public void setBeginUpdatedTime(Date beginUpdatedTime) {
        this.beginUpdatedTime = beginUpdatedTime;
    }

    public Date getEndUpdatedTime() {
        return endUpdatedTime;
    }

    public void setEndUpdatedTime(Date endUpdatedTime) {
        this.endUpdatedTime = endUpdatedTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getKindCn() {
        return kindCn;
    }

    public void setKindCn(String kindCn) {
        this.kindCn = kindCn;
    }

    public SysAttachmentVO getAttachment() {
        return attachment;
    }

    public void setAttachment(SysAttachmentVO attachment) {
        this.attachment = attachment;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "SysKnowledgeVO{" +
                "id=" + id +
                ", kindId=" + kindId +
                ", title='" + title + '\'' +
                ", merchantId=" + merchantId +
                ", businessTypeId=" + businessTypeId +
                ", description='" + description + '\'' +
                ", analysis='" + analysis + '\'' +
                ", resolution='" + resolution + '\'' +
                ", successfulCase='" + successfulCase + '\'' +
                ", talkingSkills='" + talkingSkills + '\'' +
                ", viewedTimes=" + viewedTimes +
                ", attachmentId=" + attachmentId +
                ", updatedTime=" + updatedTime +
                ", createdTime=" + createdTime +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", beginUpdatedTime=" + beginUpdatedTime +
                ", endUpdatedTime=" + endUpdatedTime +
                ", username='" + username + '\'' +
                ", businessType='" + businessType + '\'' +
                ", content='" + content + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", pageNumber='" + pageNumber + '\'' +
                '}';
    }
}