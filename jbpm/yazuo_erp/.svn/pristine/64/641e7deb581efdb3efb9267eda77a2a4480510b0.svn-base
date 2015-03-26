package com.yazuo.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

//import cn.com.egova.mis.excetpion.MisException;

/**
 * 用来方便记录日志，抛出异常，使得sping的事务发挥作用
 * @author yindl
 *
 */
@Service
public class LogUtils {
	@Autowired
	private static JdbcTemplate jdbcTemplate = null;
	public static Log logger = LogFactory.getLog(LogUtils.class);
	
	
	
	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Autowired(required = true)
	public static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		LogUtils.jdbcTemplate = jdbcTemplate;
	}


	public static void error(String msg, Log logger,Exception e,String sql,Object...params){
		String newLine = "\n";
		if("\\".equals(File.separator)){//windows 下 log 换行符
			newLine = "\r\n";
		}
		StringBuffer error = new StringBuffer();
		error.append(msg);
		error.append(newLine);
		error.append(e);
		error.append(newLine);
		error.append(sql);
		error.append(newLine);
		int i = 1;
		if(params != null){
			for(Object param : params){
				error.append("[参数" + i + ":" + param + "]");
				error.append(newLine);
				i++;
			}
		}
		logger.error(error.toString());
		throw new RuntimeException(e); //为了spring进行回滚,抛出未检查异常
	}
	
	public static void error(String msg, Log logger,Exception e){
		String newLine = "\n";
		if("\\".equals(File.separator)){//windows 下 log 换行符
			newLine = "\r\n";
		}
		StringBuffer error = new StringBuffer();
		error.append(msg);
		error.append(newLine);
		error.append(e);
		error.append(newLine);
		error.append(newLine);
		logger.error(error.toString());
		throw new RuntimeException(e); //为了spring进行回滚,抛出未检查异常
	}
	
	/*
	public static int log(int adminID,String operateType,String targetID,String targetValue,boolean result,String note){
		//adminID 操作人id
		//operateType操作类型     用const
		//targetID 操作对象ID 
		//targetValue 操作对象名称
		//result 操作结果
		
		String sql = "insert into public.admin_operate_log (account_no,operate_name,obj_type,obj_value,result,note) values (?,?,?,?,?,?);";
		try {
			return jdbcTemplate.update(sql, adminID, operateType,targetID ,targetValue , result,note);
		} catch (Exception e) {
			LogUtils.error("添加操作日志时出错：", logger, e, sql);
		}
		return -1;
	}*/
	
}
