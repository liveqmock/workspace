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

package com.yazuo.erp.mkt.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.*;
import com.yazuo.erp.mkt.vo.*;
import com.yazuo.erp.mkt.dao.*;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
public class MktTeamVO implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "团队信息";
	public static final String ALIAS_ID = "ID";
	public static final String ALIAS_TEAM_NAME = "团队名称";
	public static final String ALIAS_TEAM_SLOGAN = "口号";
	public static final String ALIAS_TEAM_LEADER = "队长id";
	public static final String ALIAS_DATE_TIME = "月份";
	public static final String ALIAS_TEAM_ODDS = "赔率";
	public static final String ALIAS_SALES_DEGREES = "销售额度";
	public static final String ALIAS_IS_ENABLE = "是否有效";
	public static final String ALIAS_REMARK = "备注";
	public static final String ALIAS_INSERT_BY = "创建人";
	public static final String ALIAS_INSERT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_BY = "最后修改人";
	public static final String ALIAS_UPDATE_TIME = "最后修改时间";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TEAM_NAME = "teamName";
	public static final String COLUMN_TEAM_SLOGAN = "teamSlogan";
	public static final String COLUMN_TEAM_LEADER = "teamLeader";
	public static final String COLUMN_DATE_TIME = "dateTime";
	public static final String COLUMN_TEAM_ODDS = "teamOdds";
	public static final String COLUMN_SALES_DEGREES = "salesDegrees";
	public static final String COLUMN_IS_ENABLE = "isEnable";
	public static final String COLUMN_REMARK = "remark";
	public static final String COLUMN_INSERT_BY = "insertBy";
	public static final String COLUMN_INSERT_TIME = "insertTime";
	public static final String COLUMN_UPDATE_BY = "updateBy";
	public static final String COLUMN_UPDATE_TIME = "updateTime";

	//columns START
	private java.lang.Integer id; //"ID";
	private java.lang.String teamName; //"团队名称";
	private java.lang.String teamSlogan; //"口号";
	private java.lang.Integer teamLeader; //"队长id";
	private java.util.Date dateTime; //"月份";
	private java.lang.String teamOdds; //"赔率";
	private java.math.BigDecimal salesDegrees; //"销售额度";
	private java.lang.String isEnable; //"是否有效";
	private java.lang.String remark; //"备注";
	private java.lang.Integer insertBy; //"创建人";
	private java.util.Date insertTime; //"创建时间";
	private java.lang.Integer updateBy; //"最后修改人";
	private java.util.Date updateTime; //"最后修改时间";
	
	
	private String leaderImage; // 队长图像
	private String leaderName; // 队长姓名
	private String teamMember; // 队员
	private List<SysUserVO> supportList; // 支持者集合
	private long supportCount; // 支持者数量
	//columns END

	public MktTeamVO(){
	}

	public MktTeamVO(
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
	public void setTeamName(java.lang.String value) {
		this.teamName = value;
	}
	
	public java.lang.String getTeamName() {
		return this.teamName;
	}
	public void setTeamSlogan(java.lang.String value) {
		this.teamSlogan = value;
	}
	
	public java.lang.String getTeamSlogan() {
		return this.teamSlogan;
	}
	public void setTeamLeader(java.lang.Integer value) {
		this.teamLeader = value;
	}
	
	public java.lang.Integer getTeamLeader() {
		return this.teamLeader;
	}
	public void setDateTime(java.util.Date value) {
		this.dateTime = value;
	}
	
	public java.util.Date getDateTime() {
		return this.dateTime;
	}
	public void setTeamOdds(java.lang.String value) {
		this.teamOdds = value;
	}
	
	public java.lang.String getTeamOdds() {
		return this.teamOdds;
	}
	public void setSalesDegrees(java.math.BigDecimal value) {
		this.salesDegrees = value;
	}
	
	public java.math.BigDecimal getSalesDegrees() {
		return this.salesDegrees;
	}
	public void setIsEnable(java.lang.String value) {
		this.isEnable = value;
	}
	
	public java.lang.String getIsEnable() {
		return this.isEnable;
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
	
	public java.lang.Integer getInsertBy() {
		return this.insertBy;
	}
	public void setInsertTime(java.util.Date value) {
		this.insertTime = value;
	}
	
	public java.util.Date getInsertTime() {
		return this.insertTime;
	}
	public void setUpdateBy(java.lang.Integer value) {
		this.updateBy = value;
	}
	
	public java.lang.Integer getUpdateBy() {
		return this.updateBy;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public long getSupportCount() {
		return supportCount;
	}

	public void setSupportCount(long supportCount) {
		this.supportCount = supportCount;
	}

	private String sortColumns;
	public String getSortColumns() {
		return sortColumns;
	}
	
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}

	public String getLeaderImage() {
		return leaderImage;
	}

	public void setLeaderImage(String leaderImage) {
		this.leaderImage = leaderImage;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(String teamMember) {
		this.teamMember = teamMember;
	}
	
	public List<SysUserVO> getSupportList() {
		return supportList;
	}

	public void setSupportList(List<SysUserVO> supportList) {
		this.supportList = supportList;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("TeamName",getTeamName())
			.append("TeamSlogan",getTeamSlogan())
			.append("TeamLeader",getTeamLeader())
			.append("DateTime",getDateTime())
			.append("TeamOdds",getTeamOdds())
			.append("SalesDegrees",getSalesDegrees())
			.append("IsEnable",getIsEnable())
			.append("Remark",getRemark())
			.append("InsertBy",getInsertBy())
			.append("InsertTime",getInsertTime())
			.append("UpdateBy",getUpdateBy())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MktTeamVO == false) return false;
		if(this == obj) return true;
		MktTeamVO other = (MktTeamVO)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

