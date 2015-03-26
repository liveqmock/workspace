/**
 * @Description 填单题目集相关接口
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @Description 填单题目集相关接口
 * @author erp team
 * @date
 */
public interface SysQuestionService {

	/**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSysQuestion(SysQuestionVO sysQuestion);

	/**
	 * 新增多个对象 @return : //TODO
	 */
	int batchInsertSysQuestions(Map<String, Object> map);

	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSysQuestion(SysQuestionVO sysQuestion);

	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysQuestionsToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysQuestionsToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteSysQuestionById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysQuestionByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	SysQuestionVO getSysQuestionById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysQuestionVO> getSysQuestions(SysQuestionVO sysQuestion);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getSysQuestionsMap(SysQuestionVO sysQuestion);

	/**
	 * @Description 查询填单题目集
	 * @param params
	 * @return
	 * @throws
	 */
	List<Map<String, Object>> querySysQuestionList(SysQuestionVO sysQuestionVO);

}
