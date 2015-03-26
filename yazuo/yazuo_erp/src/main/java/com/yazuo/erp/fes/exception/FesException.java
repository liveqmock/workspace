package com.yazuo.erp.fes.exception;

import com.yazuo.erp.exception.ErpException;

/**
 * 前端服务模块异常
 */
public abstract class FesException extends ErpException {

	private static final String MODULE_NAME = "前端服务";

	public String getModuleName() {
		return MODULE_NAME;
	}

	public FesException() {
		super();
	}

	public FesException(String message) {
		super(message);
	}

	public FesException(String message, Throwable cause) {
		super(message, cause);
	}

	public FesException(Throwable cause) {
		super(cause);
	}

}
