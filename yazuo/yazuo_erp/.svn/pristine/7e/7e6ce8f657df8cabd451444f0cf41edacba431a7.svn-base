/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


public class TraExamPaperVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TraExamPaper";
	public static final String ALIAS_ID = "试卷ID";
	public static final String ALIAS_COURSEWARE_ID = "课件ID";
	public static final String ALIAS_PAPER_NAME = "试卷名称";
	public static final String ALIAS_STUDENT_ID = "学生ID";
	public static final String ALIAS_TEACHER_ID = "老师ID";
	public static final String ALIAS_BEGIN_TIME = "开始时间";
	public static final String ALIAS_END_TIME = "结束时间";
	public static final String ALIAS_TOTAL_SCORE = "成绩";
	public static final String ALIAS_MARK = "评分";
	public static final String ALIAS_COMMENT = "评语";
	public static final String ALIAS_PAPER_STATUS = "状态";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";

	//columns START
	private java.lang.Integer id;
    private Integer learningProgressId;
    private String examPaperType;
	private java.lang.Integer coursewareId;
	private java.lang.String paperName;
	private java.lang.Integer studentId;
	private java.lang.Integer teacherId;
	private Date beginTime;
	private Date endTime;
	private BigDecimal totalScore;
	private BigDecimal mark;
	private java.lang.String comment;
	private java.lang.String paperStatus;
	private java.lang.String remark;
	private java.lang.Integer insertBy;
	private Date insertTime;
	private java.lang.Integer updateBy;
	private Date updateTime;
    //columns END
    private String timeLimit;
    //添加对象信息
    private List<TraExamDtlVO> traExamDtlVOs;
    private TraLearningProgressVO learningProgressVO;

	public TraExamPaperVO(){
	}

	public TraExamPaperVO(
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

    public Integer getLearningProgressId() {
        return learningProgressId;
    }

    public void setLearningProgressId(Integer learningProgressId) {
        this.learningProgressId = learningProgressId;
    }

    public String getExamPaperType() {
        return examPaperType;
    }

    public void setExamPaperType(String examPaperType) {
        this.examPaperType = examPaperType;
    }

    public void setCoursewareId(java.lang.Integer value) {
		this.coursewareId = value;
	}
	
	public java.lang.Integer getCoursewareId() {
		return this.coursewareId;
	}
	public void setPaperName(java.lang.String value) {
		this.paperName = value;
	}
	
	public java.lang.String getPaperName() {
		return this.paperName;
	}
	public void setStudentId(java.lang.Integer value) {
		this.studentId = value;
	}
	
	public java.lang.Integer getStudentId() {
		return this.studentId;
	}
	public void setTeacherId(java.lang.Integer value) {
		this.teacherId = value;
	}
	
	public java.lang.Integer getTeacherId() {
		return this.teacherId;
	}
	public void setBeginTime(Date value) {
		this.beginTime = value;
	}
	
	public Date getBeginTime() {
		return this.beginTime;
	}
	public void setEndTime(Date value) {
		this.endTime = value;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	public void setTotalScore(BigDecimal value) {
		this.totalScore = value;
	}
	
	public BigDecimal getTotalScore() {
		return this.totalScore;
	}
	public void setMark(BigDecimal value) {
		this.mark = value;
	}
	
	public BigDecimal getMark() {
		return this.mark;
	}
	public void setComment(java.lang.String value) {
		this.comment = value;
	}
	
	public java.lang.String getComment() {
		return this.comment;
	}
	public void setPaperStatus(java.lang.String value) {
		this.paperStatus = value;
	}
	
	public java.lang.String getPaperStatus() {
		return this.paperStatus;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getInsertBy() {
        return insertBy;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public TraLearningProgressVO getLearningProgressVO() {
        return learningProgressVO;
    }

    public void setLearningProgressVO(TraLearningProgressVO learningProgressVO) {
        this.learningProgressVO = learningProgressVO;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("CoursewareId",getCoursewareId())
			.append("PaperName",getPaperName())
			.append("StudentId",getStudentId())
			.append("TeacherId",getTeacherId())
			.append("BeginTime",getBeginTime())
			.append("EndTime",getEndTime())
			.append("TotalScore",getTotalScore())
			.append("Mark",getMark())
			.append("Comment",getComment())
			.append("PaperStatus",getPaperStatus())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("UpdateBy",getUpdateBy())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}

    public List<TraExamDtlVO> getTraExamDtlVOs() {
        if (this.traExamDtlVOs == null) {
            this.traExamDtlVOs = new LinkedList<TraExamDtlVO>();
        }
        return traExamDtlVOs;
    }

    public void setTraExamDtlVOs(List<TraExamDtlVO> traExamDtlVOs) {
        this.traExamDtlVOs = traExamDtlVOs;
    }

    public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TraExamPaperVO == false) return false;
		if(this == obj) return true;
		TraExamPaperVO other = (TraExamPaperVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

