package com.yazuo.erp.project.vo;
import com.yazuo.util.CustomDateSerializer;

public class ProjectLabel implements java.io.Serializable{
	private static final long serialVersionUID = 4584041594247951173L;
	private Integer projectLabelId;
	private Integer projectId;
	private Integer labelId;
	private java.sql.Timestamp insertTime;
	
	public Integer getProjectLabelId() {
		return projectLabelId;
	}

	public void setProjectLabelId(Integer projectLabelId) {
		this.projectLabelId = projectLabelId;
	}
	
	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	
	@org.codehaus.jackson.map.annotate.JsonSerialize(using = CustomDateSerializer.class)
	public java.sql.Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(java.sql.Timestamp insertTime) {
		this.insertTime = insertTime;
	}
}
