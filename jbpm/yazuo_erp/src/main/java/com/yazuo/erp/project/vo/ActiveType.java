package com.yazuo.erp.project.vo;

public class ActiveType implements java.io.Serializable{
	private static final long serialVersionUID = -6328746424836936326L;
	
	private Integer typeId;
	private String typeName;
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
