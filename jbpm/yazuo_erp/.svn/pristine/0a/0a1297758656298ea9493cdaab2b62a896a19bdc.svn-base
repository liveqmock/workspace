package com.yazuo.erp.interceptors;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

	//columns START
//	private java.lang.Integer id; //"ID";
//	private java.lang.Integer merchantId; //"商户ID";
//	private java.lang.String moduleType; //"模块类型";
//	private java.lang.String fesLogType; //"日志类型";
//	private java.util.Date operatingTime; //"操作时间";
//	private java.lang.String description; //"操作描述";
//	private java.lang.String remark; //"备注";
//	private java.lang.Integer insertBy; //"创建人";
//	private java.util.Date insertTime; //"创建时间";
	//columns END
	/**
	 * 
	 */
	String[] value() default {};
	public String moduleType() default "";
	String[] fesLogType() default {};
	String[] insertBy() default {};
	String[] merchantId() default {};
}