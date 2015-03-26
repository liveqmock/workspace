package com.yazuo.erp.fes.exception;

public class FesBizException extends FesException {

	private static final long serialVersionUID = 3538499414670088914L;

	public FesBizException() {
	}

	public FesBizException(String message) {
		super(message);
	}

	public FesBizException(String message, Throwable cause) {
		super(message, cause);
	}

	public FesBizException(Throwable cause) {
		super(cause);
	}

}
