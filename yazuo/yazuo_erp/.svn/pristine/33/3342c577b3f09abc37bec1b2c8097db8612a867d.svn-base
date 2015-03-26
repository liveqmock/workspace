/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.yazuo.erp.base.MD5Util;
import com.yazuo.erp.interceptors.OperationLog;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * TODO 目前只用用户密码加密需求
 * 
 * @author song
 * @date 2014-6-10 13:26:03
 */
@Aspect
public class ServiceAspect {

	private static final Log LOG = LogFactory.getLog(ServiceAspect.class);
	protected static final ObjectMapper mapper = new ObjectMapper();
//	@Before("execution(* com.yazuo.erp.*.dao.*.get*(..)) or execution(* com.yazuo.erp.*.dao.*.update*(..))")
//	public void logBefore(JoinPoint joinPoint) {
//		LOG.debug("111111111111-----logBefore() is running!");
//		LOG.debug("hijacked : " + joinPoint.getSignature().getName());
//		LOG.debug("******");
//	}
	/*@After("execution(* com.mkyong.customer.bo.CustomerBo.addCustomer(..))")
	public void logAfter(JoinPoint joinPoint) {

		LOG.debug("logAfter() is running!");
		LOG.debug("hijacked : " + joinPoint.getSignature().getName());
		LOG.debug("******");

	}*/
	
	/*
	@Resource 
	SysToDoListService sysToDoListService;
	
	@AfterReturning(pointcut = "@annotation(com.yazuo.erp.interceptors.OperationLog) && @annotation(operationLog)",
			returning= "result")
	public void logAfterReturning(JoinPoint joinPoint, OperationLog operationLog, Object result) throws JsonGenerationException, JsonMappingException, IOException {
		String moduleType = operationLog.moduleType();
//		Object[] args = joinPoint.getArgs();
//		for (Object object : args) {
//			LOG.info("object: "+object);
//		}
//		LOG.info("logAfterReturning() is running!");
		LOG.info("hijacked : " + joinPoint.getSignature().getName());
		LOG.info("Method returned value is : " + result);
		LOG.info("******");
//		SysToDoListVO sysToDoList = ;
		List<SysToDoListVO> complexSysToDoLists = sysToDoListService.getComplexSysToDoLists(null);
		this.printJson(complexSysToDoLists);
	}
	
	/*@AfterThrowing(
			pointcut = "execution(* com.mkyong.customer.bo.CustomerBo.addCustomerThrowException(..))",
			throwing= "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {

		LOG.debug("logAfterThrowing() is running!");
		LOG.debug("hijacked : " + joinPoint.getSignature().getName());
		LOG.debug("Exception : " + error);
		LOG.debug("******");

	}*/
	/**
	 * @Description : 动态改变SysUserVO中的password， 用md5方式加密
	 */
	@Around("execution(* com.yazuo.erp.system.service.impl.SysUserServiceImpl.saveSysUser(..)) " +
			" or execution(* com.yazuo.erp.system.dao.SysUserDao.updateSysUserPWD(..))") 
	public Object savePasswordAround(ProceedingJoinPoint joinPoint) throws Throwable {
		LOG.debug("logAround() is running!");
		LOG.debug("hijacked method : " + joinPoint.getSignature().getName());
		LOG.debug("hijacked arguments : " + Arrays.toString(joinPoint.getArgs()));
		Object[] args = joinPoint.getArgs();
		LOG.debug("Around before is running!");
		if(args[0] instanceof SysUserVO){
			SysUserVO sysUserVO = (SysUserVO)args[0];
			//用md5方式加密
			String password = MD5Util.createPassword(sysUserVO.getPassword());
			sysUserVO.setPassword(password);
			args[0] = sysUserVO;
			Object result = joinPoint.proceed(args);
			return result;
		}
		LOG.debug("Around after is running!");
		LOG.debug("******");
		return null;
	}
	/**
	 * @Description :  用md5方式验证密码是否正确
	 *///main\java\com\yazuo\erp\system\service\impl\SysUserServiceImpl.java
	@Around("execution(* com.yazuo.erp.system.service.impl.SysUserServiceImpl.toVerifyPassword(..))") 
	public Object checkPasswordAround(ProceedingJoinPoint joinPoint) throws Throwable {
		LOG.debug("logAround() is running!");
		LOG.debug("hijacked method : " + joinPoint.getSignature().getName());
		LOG.debug("hijacked arguments : " + Arrays.toString(joinPoint.getArgs()));
		LOG.debug("Around before is running!");
		Object[] args = joinPoint.getArgs();
		// MD5Util.authenticatePassword(password, inputString));
		Object result = joinPoint.proceed(args);
		//更改返回值，用md5加密验证的方式比较
		result = MD5Util.authenticatePassword(args[1].toString(), args[0].toString());
		LOG.debug("Around after is running!");
		LOG.debug("******");
		return result;
	}
	//*******************以下注释为测试例子*************//
//	@Around("execution(* com.yazuo.erp.system.dao.SysResourceDao.*(..))")  //test
//	@Around("execution(* com.yazuo.erp.system.dao.SysResourceDao.getSysResourceById(..))")  //test
//	public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//		
//		LOG.debug("logAround() is running!");
//		LOG.debug("hijacked method : " + joinPoint.getSignature().getName());
//		LOG.debug("hijacked arguments : " + Arrays.toString(joinPoint.getArgs()));
//		Object[] args = joinPoint.getArgs();
//		if(args[0] instanceof Integer){
//			args[0] = 4;//id 改成4
//			joinPoint.proceed(args);
//		}
//		LOG.debug("Around before is running!");
//		LOG.debug(joinPoint.proceed());
//		LOG.debug("Around after is running!");
//		
//		LOG.debug("******");
//		
//	}
	/**
	 * 输出json格式的对象, 用父类记录日志
	 */
	private static void printJson(Object object) {
		try {
			LOG.info(object.getClass()+": \n"+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
//			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}