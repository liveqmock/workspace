/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.syn.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Description SynMerchantVO的定制对象
 * @author erp team
 * @date
 */
public class ComplexSynMerchantVO extends SynMerchantVO {
	private static final long serialVersionUID = 5454155825314635342L;

	private String salesName = null; // 销售负责人
	private Integer programId = null;
	private List<FesUser> listUsers = null;//前端负责人
	private List<AssistantUser> listAssistantUsers = null;//客服助理
	private Integer countToDoList = null; // 代办事项个数
	private static final List<HealthDegree> healthDegreeStd = new LinkedList<HealthDegree>();//标准的健康度 前台用于不全数据库不全的健康度数据
	private String merchantStatusText = null;// 前端页面显示流程信息
	private List<Step> listSteps = new ArrayList<Step>(0);// 前端页面显示流程信息
	private static String[] types = new String[]{"1","2","3","4"}; //健康度类型 {"会员","售卡","消费","储值"}; 
	private int merchantStatusType = 0; //为了区分是商户我的主页[0] 还是销售我的主页[1]
	static{
		for (String type : types) {
			HealthDegree healthDegree = new HealthDegree();
			healthDegree.setActualValue(new BigDecimal(0));
			healthDegree.setHealthDegree(new BigDecimal(0));
			healthDegree.setTargetValue(new BigDecimal(0));
			healthDegree.setTargetType(type);
			healthDegreeStd.add(healthDegree);
		}
	}
	
	public int getMerchantStatusType() {
		return merchantStatusType;
	}

	public void setMerchantStatusType(int merchantStatusType) {
		this.merchantStatusType = merchantStatusType;
	}
	
	public String getMerchantStatusText() {
		return merchantStatusText;
	}

	public List<AssistantUser> getListAssistantUsers() {
		return listAssistantUsers;
	}

	public void setListAssistantUsers(List<AssistantUser> listAssistantUsers) {
		this.listAssistantUsers = listAssistantUsers;
	}
	public void setMerchantStatusText(String merchantStatusText) {
		this.merchantStatusText = merchantStatusText;
	}
	/**
	 * 返回标准的健康度描述
	 */
	public static List<HealthDegree> getStdHealthDegrees(){
		return healthDegreeStd;
	}

	public Integer getCountToDoList() {
		return countToDoList;
	}

	public void setCountToDoList(Integer countToDoList) {
		this.countToDoList = countToDoList;
	}
	public List<Step> getListSteps() {
		return listSteps;
	}

	public void setListSteps(List<Step> listSteps) {
		this.listSteps = listSteps;
	}

	private Set synHealthDegrees = null; // 健康度

	public Set getSynHealthDegrees() {
		return synHealthDegrees;
	}

	public void setSynHealthDegrees(Set synHealthDegrees) {
		this.synHealthDegrees = synHealthDegrees;
	}

	public Integer getProgramId() {
		return programId;
	}

	public void setProgramId(Integer programId) {
		this.programId = programId;
	}

	public String getSalesName() {
		return salesName;
	}

	public List<FesUser> getListUsers() {
		return listUsers;
	}

	public void setListUsers(List<FesUser> listUsers) {
		this.listUsers = listUsers;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
}

class FesUser {
	private java.lang.Integer id;
	private java.lang.String userName;
	private java.lang.String tel;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.String getTel() {
		return tel;
	}

	public void setTel(java.lang.String tel) {
		this.tel = tel;
	}
}
class AssistantUser {
	private java.lang.Integer id;
	private java.lang.String userName;
	private java.lang.String tel;
	
	public java.lang.Integer getId() {
		return id;
	}
	
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	
	public java.lang.String getUserName() {
		return userName;
	}
	
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	
	public java.lang.String getTel() {
		return tel;
	}
	
	public void setTel(java.lang.String tel) {
		this.tel = tel;
	}
}

class Step {
	private Integer stepId = null;
	private String stepText = null;
	private Integer mainPagePlanEndTimeStatus = null;


	public Integer getMainPagePlanEndTimeStatus() {
		return mainPagePlanEndTimeStatus;
	}

	public void setMainPagePlanEndTimeStatus(Integer mainPagePlanEndTimeStatus) {
		this.mainPagePlanEndTimeStatus = mainPagePlanEndTimeStatus;
	}

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public String getStepText() {
		return stepText;
	}

	public void setStepText(String stepText) {
		this.stepText = stepText;
	}
}
