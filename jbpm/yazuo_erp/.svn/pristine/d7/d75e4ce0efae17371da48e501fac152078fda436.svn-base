package com.zdp.service;

import com.zdp.dao.LogDao;
import com.zdp.dao.UserDao;

public class UserServiceJTA {
	private UserDao userDao;
	private LogDao logDao;

	public void saveUser(String id, String name) {
		userDao.insertUser(id, name);
//		 int i = 1 / 0;  //制造异常
		logDao.insertLog(id, id + "_" + name);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public LogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}
}
