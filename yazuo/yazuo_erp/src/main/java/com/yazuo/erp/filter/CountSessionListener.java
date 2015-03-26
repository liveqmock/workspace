/**
 * @Description 管理登录用户的session，目前用在flash异步上传文件的时候得不到session的情况
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.filter;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.vo.SysUserVO;
@Deprecated  //测试没有使用，缺陷： session生成与用户的加入没有关系，业务需要在添加或删除用户的时候记录session
public class CountSessionListener implements HttpSessionListener {

	private static final Log LOG = LogFactory.getLog(CountSessionListener.class);
	private SessionUserList ul = SessionUserList.getInstance();

	/***********
	 * 创建session时调用
	 */
	public void sessionCreated(HttpSessionEvent event) {
		LOG.info("创建session......");
		HttpSession session = event.getSession();
		SysUserVO sessionUser = (SysUserVO) session.getAttribute(Constant.SESSION_USER);
		if(sessionUser!=null){
			ul.addUser(sessionUser);
		}
	}

	/************
	 * 销毁session时调用
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		LOG.info("销毁session......");
		HttpSession session = event.getSession();
		SysUserVO sessionUser = (SysUserVO) session.getAttribute(Constant.SESSION_USER);
		ul.removeUser(sessionUser);
	}

}