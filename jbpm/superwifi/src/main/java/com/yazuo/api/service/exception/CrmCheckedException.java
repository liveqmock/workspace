package com.yazuo.api.service.exception;

/**
 * crm自定义异常
 * 
 * @author libin
 * 
 */
public class CrmCheckedException extends RuntimeException {
	public CrmCheckedException() {

	}

	public CrmCheckedException(String message) {
		super(message);
	}
}
