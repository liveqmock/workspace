package com.yazuo.erp.base;

import com.yazuo.erp.exception.ErpException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理类，根据前后端通信协议
 * @author congshuanglong
 */
public class ExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        JsonResult result = new JsonResult(false);
        ex.printStackTrace();
        String message = ex instanceof ErpException ? ex.getMessage() : "internal server error";
        return new ModelAndView("500").addObject("message", message).addObject("flag", result.getFlag());
    }
}
