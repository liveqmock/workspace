package com.yazuo.erp.project.vo;

import java.util.List;

import com.yazuo.util.CustomDateSerializer;

public class Label implements java.io.Serializable{
	private static final long serialVersionUID = 6800609298537895501L;
	private Integer labelId;
	private String lableName;
	private String lableDesc;
	private String createdPerson;
	private java.sql.Timestamp insertTime;
	private List<Project> projects;
	
	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public Integer getLabelId() {
		return labelId;
	}

	public void setLabelId(Integer labelId) {
		this.labelId = labelId;
	}
	
	public String getLableName() {
		return lableName;
	}

	public void setLableName(String lableName) {
		this.lableName = lableName;
	}
	
	public String getLableDesc() {
		return lableDesc;
	}

	public void setLableDesc(String lableDesc) {
		this.lableDesc = lableDesc;
	}
	
	public String getCreatedPerson() {
		return createdPerson;
	}

	public void setCreatedPerson(String createdPerson) {
		this.createdPerson = createdPerson;
	}
	
	@org.codehaus.jackson.map.annotate.JsonSerialize(using = CustomDateSerializer.class)
	public java.sql.Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(java.sql.Timestamp insertTime) {
		this.insertTime = insertTime;
	}
}
