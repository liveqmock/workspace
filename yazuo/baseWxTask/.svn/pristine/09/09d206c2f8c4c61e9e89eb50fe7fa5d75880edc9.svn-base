package com.yazuo.task;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * 自定义task类示例 Created by Luo on 2014/7/4.
 */
public class TestTask extends BaseTask {
	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	// JdbcTemplate jdbcTemplateTrade;
	@Value("#{configProperties['config.test_property'] }")
	String testProperty;

	/**
	 * 自定义任务执行内容
	 * 
	 * @param params
	 *            自定义任务参数
	 */
	public void execute1(Map params) {
		log.info("测试任务执行");
		log.info("params: " + params);
		// log.info("数据库时间 ：" + jdbcTemplateTrade.queryForObject("select now()",
		// Date.class));
		log.info("testProperty ：" + testProperty);

	}

}
