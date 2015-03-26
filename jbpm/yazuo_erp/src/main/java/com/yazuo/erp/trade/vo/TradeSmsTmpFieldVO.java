package com.yazuo.erp.trade.vo;

/**
 * 短信模板字段信息
 */
public class TradeSmsTmpFieldVO {
    private Integer id;
    private String fieldName;
    private String fieldType;
    private String description;
    private String transCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransCode() {
        return transCode;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    @Override
    public String toString() {
        return "TradeSmsTmpFieldVO{" +
                "id=" + id +
                ", fieldName='" + fieldName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", description='" + description + '\'' +
                ", transCode='" + transCode + '\'' +
                '}';
    }
}
