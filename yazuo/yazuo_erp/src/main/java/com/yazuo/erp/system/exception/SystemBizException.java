package com.yazuo.erp.system.exception;

public class SystemBizException extends SystemException {
	
	public SystemBizException() {
	}

	public SystemBizException(String message) {
		super(message);
	}

	public SystemBizException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemBizException(Throwable cause) {
		super(cause);
	}
	
}
