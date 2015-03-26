/**
 * @Description 管理所有用户的session
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.filter;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yazuo.erp.system.controller.LoginController;
import com.yazuo.erp.system.vo.SysUserVO;

public class BindSessionUser implements HttpSessionBindingListener {

private static final Log LOG = LogFactory.getLog(LoginController.class);
	private Object user;

	public Object getUser() {
		return user;
	}

	public void setUser(Object user) {
		this.user = user;
	}

	private SessionUserList ul = SessionUserList.getInstance();

	public BindSessionUser() {
	}

	public BindSessionUser(Object user) {
		this.user = user;
	}

	public void valueBound(HttpSessionBindingEvent event) {
		ul.addUser(user);
		LOG.info("valueBound " + ((SysUserVO)user).getSessionId()+ " "+((SysUserVO)user).getTel());
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		ul.removeUser(user);
		LOG.info("valueUnbound "+ ((SysUserVO)user).getSessionId()+ " "+((SysUserVO)user).getTel());
	}
}