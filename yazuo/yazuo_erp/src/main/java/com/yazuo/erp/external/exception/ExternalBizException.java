package com.yazuo.erp.external.exception;

public class ExternalBizException extends ExternalException {

	private static final long serialVersionUID = -2218372060837086473L;

	public ExternalBizException() {
	}

	public ExternalBizException(String message) {
		super(message);
	}

	public ExternalBizException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExternalBizException(Throwable cause) {
		super(cause);
	}

}
