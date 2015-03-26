/**
 * @Description 打印select sql 语句
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.interceptors;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.yazuo.erp.base.JsonResult;

// ExamplePlugin.java
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class PrintSqlPlugin implements Interceptor {

	private static final Log log = LogFactory.getLog(PrintSqlPlugin.class);
	
	public Object intercept(Invocation invocation) throws Throwable {
		   final Object[] args = invocation.getArgs();
	            MappedStatement ms = (MappedStatement) args[0];
	            Object parameterObject = args[1];
	            BoundSql boundSql = ms.getBoundSql(parameterObject);
	            Object object = boundSql.getParameterObject();
	            log.info("sql parameters:  "+object);
	            log.info("sql parameters:  "+JsonResult.getJsonStringHandleNull(object));
	            log.info("sql:  "+boundSql.getSql());
		return invocation.proceed();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		log.info("properties:  "+properties);
	}
}