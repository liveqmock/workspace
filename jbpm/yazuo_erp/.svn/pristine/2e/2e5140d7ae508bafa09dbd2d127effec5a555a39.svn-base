package com.zdp.dao;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.atomikos.jdbc.AtomikosDataSourceBean;

public class UserDao extends JdbcDaoSupport {

//	@Resource(name = "jdbcTemplate1")
//	private JdbcTemplate jdbcTemplate; //user datasource
	@Resource AtomikosDataSourceBean mysqlDS1;
	public void insertUser(String id, String name) {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.setDataSource(mysqlDS1);
		try {
			jdbcTemplate.getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		jdbcTemplate.execute("insert into user values('" + id + "','" + name + "')");
	}
}
