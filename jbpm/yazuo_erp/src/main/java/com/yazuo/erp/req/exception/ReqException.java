package com.yazuo.erp.req.exception;

import com.yazuo.erp.exception.ErpException;

/**
 * 需求管理模块异常
 */
public abstract class ReqException extends ErpException {

    private static final String MODULE_NAME = "需求管理";

    public String getModuleName() {
        return MODULE_NAME;
    }

    public ReqException() {
        super();
    }

    public ReqException(String message) {
        super(message);
    }

    public ReqException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReqException(Throwable cause) {
        super(cause);
    }

}
