/**
 * @Description 老员工管理相关服务
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 		gaoshan		2014-10-09	创建文档
 * 
 */
package com.yazuo.erp.train.service;

import java.util.List;
import java.util.Map;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import com.yazuo.erp.train.vo.TraTeacherStudentVO;

/**
 * @Description 老员工管理相关服务接口类
 * @author gaoshan
 * @date 2014-10-09 下午6:44:07
 */
public interface OldStaffManagementService {
	
	/**
	 * @Description 根据课件名称模糊查询有效的课件
	 * @param params
	 * @return
	 * @throws 
	 */
	Page<Map<String, Object>> queryCoursewareList(Map<String, Object> params);

	/**
	 * @Description 发起学习-查询，根据条件（1-按照部门，2-按照职位，3-按照员工），查询部门列表，职位列表，员工列表
	 * @param params
	 * @return
	 * @throws 
	 */
	Map<String, Object> queryInfo(Map<String, Object> params);

	/**
	 * @Description 发起学习
	 * @param params
	 * @return
	 * @throws 
	 */
	int executeTermBegin(Map<String, Object> params);

	/**
	 * @Description 学员管理-列表，根据姓名、状态查询学员信息，学习状态显示最近的一次学习进度（除了异常结束的）
	 * @param params
	 * @return
	 * @throws 
	 */
	Map<String, Object> queryOldStaffList(Map<String, Object> params); 

	/**
	 * @Description 移除学员
	 * @param params
	 * @return
	 * @throws 
	 */
	int executeRemove(Map<String, Object> params);

	/**
	 * @Description 延时学习
	 * @param params
	 * @return
	 * @throws 
	 */
	int executeDelay(Map<String, Object> params);

	/**
	 * @Description 根据领导ID查询待办事项
	 * @param params
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	Map<String, Object> queryToDoListByLeaderId(Map<String, Object> params);

	/**
	 * @Description 根据领导ID查询待办事项数量
	 * @param params
	 * @return
	 * @return Map<String,Object>
	 * @throws 
	 */
	Map<String, Object> queryToDoListCountByLeaderId(Map<String, Object> params);

}
