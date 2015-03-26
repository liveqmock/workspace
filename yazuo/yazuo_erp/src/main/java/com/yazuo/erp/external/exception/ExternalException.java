package com.yazuo.erp.external.exception;

import com.yazuo.erp.exception.ErpException;

/**
 * 前端服务模块异常
 */
public abstract class ExternalException extends ErpException {

	private static final String MODULE_NAME = "外部接口模块";

	public String getModuleName() {
		return MODULE_NAME;
	}

	public ExternalException() {
		super();
	}

	public ExternalException(String message) {
		super(message);
	}

	public ExternalException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExternalException(Throwable cause) {
		super(cause);
	}

}
