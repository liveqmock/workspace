package com.yazuo.erp.bes.exception;

public class BesBizException extends BesException {

	public BesBizException() {
	}

	public BesBizException(String message) {
		super(message);
	}

	public BesBizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BesBizException(Throwable cause) {
		super(cause);
	}

}
