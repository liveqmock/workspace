package com.zdp.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.atomikos.jdbc.AtomikosDataSourceBean;
public class LogDao extends JdbcDaoSupport {

//	@Resource(name = "jdbcTemplate2")
//	private JdbcTemplate jdbcTemplate; //log datasource

	@Resource AtomikosDataSourceBean mysqlDS2;
	public void insertLog(String id, String content) {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.setDataSource(mysqlDS2);
		jdbcTemplate.execute("insert into log values('" + id + "','" + content + "')");
	}
}
