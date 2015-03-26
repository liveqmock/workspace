package com.yazuo.erp.system.exception;

import com.yazuo.erp.exception.ErpException;

/**
 * 培训模块异常
 */
public abstract class SystemException extends ErpException {

	private static final String MODULE_NAME = "权限系统";

	public String getModuleName() {
		return MODULE_NAME;
	}

	public SystemException() {
		super();
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

}
