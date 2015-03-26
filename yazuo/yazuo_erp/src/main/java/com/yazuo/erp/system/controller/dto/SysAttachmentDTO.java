package com.yazuo.erp.system.controller.dto;

/**
 */
public class SysAttachmentDTO {
    private Integer oldAttachmentId;
    private Integer attachmentId;
    private Integer entityId;

    public Integer getOldAttachmentId() {
        return oldAttachmentId;
    }

    public void setOldAttachmentId(Integer oldAttachmentId) {
        this.oldAttachmentId = oldAttachmentId;
    }

    public Integer getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Integer attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }
}
