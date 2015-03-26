/**
 * @Description 登录的session user
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.filter;

import java.util.Vector;
import java.util.Enumeration;

import com.yazuo.erp.system.vo.SysUserVO;

public class SessionUserList {
	private static final SessionUserList userList = new SessionUserList();
	private Vector<Object> v;

	private SessionUserList() {
		v = new Vector<Object>();
	}

	public static SessionUserList getInstance() {
		return userList;
	}
	/**
	 * 根据sessionId查找online user对应的list
	 * @return SysUserVO
	 */
	public static SysUserVO getOnlineSessionUser(String sessionId) {
		Enumeration<Object> enums = SessionUserList.getInstance().getUserList();
		while(enums.hasMoreElements()){
			SysUserVO onlineUser = (SysUserVO)enums.nextElement();
			if(onlineUser.getSessionId().equals(sessionId)){
				return onlineUser;
			}
		}
		return null;
	}

	public void addUser(Object name) {
		if (name != null)
			v.addElement(name);
	}

	public void removeUser(Object name) {
		if (name != null)
			v.remove(name);
	}

	public Enumeration<Object> getUserList() {
		return v.elements();
	}

	public int getUserCount() {
		return v.size();
	}
}