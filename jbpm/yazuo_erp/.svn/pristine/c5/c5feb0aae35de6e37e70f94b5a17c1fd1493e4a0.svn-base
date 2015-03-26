package com.yazuo.erp.project.vo;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.yazuo.erp.base.Constant;
import com.yazuo.util.CustomDateSerializer;

public class Project implements java.io.Serializable{
	private static final long serialVersionUID = 5829405813110607645L;
	private Integer projectId;
	private String projectName;
	private String projectComments;
	private Integer activeId;
	private Integer activeTypeId;
	private String activeImgPath;
	private String cityType;
	private String cateType;
	private String catType;
    private String avgPriceType;
    private String promoteType;
	private java.sql.Timestamp insertTime;
	private List<Label> labels;
	private String[] labelIds;
	private Active active;
	//判断图片路径是否已经改变
	private Boolean pathChanged;

	//页面输入的新标签
	private String newLabels;
	
	List<Map<String, Object>> listCity;
	List<Map<String, Object>> listCat;
	List<Map<String, Object>> listCate;
	List<Map<String, Object>> listAvgPrice;
	List<Map<String, Object>> listPromote;
	
	public String getPromoteType() {
		return promoteType;
	}

	public void setPromoteType(String promoteType) {
		this.promoteType = promoteType;
	}

	public List<Map<String, Object>> getListPromote() {
		return listPromote;
	}

	public void setListPromote(List<Map<String, Object>> listPromote) {
		this.listPromote = listPromote;
	}

	public List<Map<String, Object>> getListCity() {
		return listCity;
	}

	public void setListCity(List<Map<String, Object>> listCity) {
		this.listCity = listCity;
	}

	public List<Map<String, Object>> getListCat() {
		return listCat;
	}

	public void setListCat(List<Map<String, Object>> listCat) {
		this.listCat = listCat;
	}

	public List<Map<String, Object>> getListCate() {
		return listCate;
	}

	public void setListCate(List<Map<String, Object>> listCate) {
		this.listCate = listCate;
	}

	public List<Map<String, Object>> getListAvgPrice() {
		return listAvgPrice;
	}

	public void setListAvgPrice(List<Map<String, Object>> listAvgPrice) {
		this.listAvgPrice = listAvgPrice;
	}

	public String getNewLabels() {
		return newLabels;
	}

	public void setNewLabels(String newLabels) {
		this.newLabels = newLabels;
	}


	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public String getCateType() {
		return cateType;
	}

	public void setCateType(String cateType) {
		this.cateType = cateType;
	}

	public String getCatType() {
		return catType;
	}

	public void setCatType(String catType) {
		this.catType = catType;
	}

	public String getAvgPriceType() {
		return avgPriceType;
	}

	public void setAvgPriceType(String avgPriceType) {
		this.avgPriceType = avgPriceType;
	}
	public Boolean getPathChanged() {
		return pathChanged;
	}

	public void setPathChanged(Boolean pathChanged) {
		this.pathChanged = pathChanged;
	}

	public Active getActive() {
		return active;
	}

	public void setActive(Active active) {
		this.active = active;
	}

	public String[] getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(String[] labelIds) {
		this.labelIds = labelIds;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectComments() {
		return projectComments;
	}

	public void setProjectComments(String projectComments) {
		this.projectComments = projectComments;
	}
	
	public Integer getActiveId() {
		return activeId;
	}

	public void setActiveId(Integer activeId) {
		this.activeId = activeId;
	}
	
	public Integer getActiveTypeId() {
		return activeTypeId;
	}

	public void setActiveTypeId(Integer activeTypeId) {
		this.activeTypeId = activeTypeId;
	}
	
	public String getActiveImgPath() {
		return activeImgPath;
	}

	public void setActiveImgPath(String activeImgPath) {
		this.activeImgPath = activeImgPath;
	}
	
	@org.codehaus.jackson.map.annotate.JsonSerialize(using = CustomDateSerializer.class)
	public java.sql.Timestamp getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(java.sql.Timestamp insertTime) {
		this.insertTime = insertTime;
	}
}
