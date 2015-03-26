package com.yazuo.erp.system.vo;

public class SysKnowledgeProveVO {
    private Integer id;
    private Integer userId;
    private Integer knowledgeId;
    private Boolean approved;
    private Integer score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(Integer knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "SysKnowledgeProveVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", knowledgeId=" + knowledgeId +
                ", approved=" + approved +
                ", score=" + score +
                '}';
    }
}