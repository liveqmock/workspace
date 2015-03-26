package com.yazuo.weixin.exception;

import java.beans.PropertyEditorSupport;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

public class ExceptionHandling {
	Logger log = Logger.getLogger(this.getClass());
	/**
	 * 统一异常处理
	 * 
	 * @param e
	 * @param request
	 * @param writer
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ExceptionHandler(Exception.class)
	public String handleException(final Exception e,
			HttpServletRequest request,  
            HttpServletResponse response) throws UnsupportedEncodingException {
		log.error("统一异常处理 :"+e.getMessage());
		String Message = "";
		try {
			Message = e.getMessage();
			if(Message == null){
				Message = "";
			}
			if(Message.startsWith("TError:")){
				Message = Message.replaceAll("TError:", "");
			}else{
				Message = "操作中发现未知错误";
			}
		} catch (Exception e2) {
			log.error("统一异常处理内部出错 :"+e2.toString());
			return "error";
		}
		request.setAttribute("error", Message);
		return "error";
	}

	@InitBinder
	public void InitBinder(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(Date.class,
				new PropertyEditorSupport() {
					public void setAsText(String value) {
						try {
							setValue(new SimpleDateFormat("yyyy-MM-dd")
									.parse(value));
						} catch (ParseException e) {
							setValue(null);
						} catch (java.text.ParseException e) {
							setValue(null);
						}
					}

					public String getAsText() {
						try{
						return new SimpleDateFormat("yyyy-MM-dd")
								.format((Date) getValue());
						}catch(Exception e){
							return null;
						}
					}

				});
	}

}
