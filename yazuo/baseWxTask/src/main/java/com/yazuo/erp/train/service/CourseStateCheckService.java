/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.train.service;

/**
 * @Description 定时检查课程是否超时 相关服务
 * @author gaoshan
 * @date 2014-11-20 下午1:39:26
 */
public interface CourseStateCheckService {

	/** 
	 * @Description 定时检查课程是否超时
	 * @return
	 * @throws Exception
	 * @return boolean
	 * @throws
	 */
	boolean courseStateCheck() throws Exception;

}
