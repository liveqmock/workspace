/**
 * @Description 组员管理相关服务
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
 * @Description 组员管理相关服务接口类
 * @author gaoshan
 * @date 2014-10-09 下午6:44:07
 */
public interface TeamMemberService {
	
	/**
	 * @Description 列表，领导查看下属的信息，并统计正在学习的课件数，根据姓名模糊查询
	 * @param params
	 * @return
	 * @throws 
	 */
	Map<String,Object> queryMemberList(Map<String, Object> params);

	/**
	 * @Description 查看学习情况，根据用户ID查询基本信息，学习进度，包括异常结束的
	 * @param params
	 * @return 
	 * @throws 
	 */
	Map<String, Object> queryLearningProcessList(Map<String, Object> params);

	/**
	 * @Description 查看历史，根据用户ID查询学习流水
	 * @param params
	 * @return
	 * @throws 
	 */
	Page<Map<String, Object>> queryStudentRecordList(Map<String, Object> params);

	/**
	 * @Description 根据试卷ID进行PPT录音评分
	 * @param params
	 * @return
	 * @throws 
	 */
	int updatePptPaper(Map<String, Object> params);

}
