/**
 * @Description 工作计划接口类
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.fes.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;

/**
 * @Description 工作计划接口类
 * @author erp team
 * @date
 */
public interface FesPlanService {

	/**
	 * @Description 添加工作计划
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws
	 */
	int saveFesPlan(Map<String, Object> paramMap, HttpServletRequest request);

	/**
	 * 新增多个对象
	 * 
	 * @return : 
	 */
	int batchInsertFesPlans(Map<String, Object> map);

	/**
	 * 修改对象
	 * 
	 * @return : 影响的行数
	 */
	int updateFesPlan(FesPlanVO fesPlan);

	/**
	 * 修改多个对象（每一条记录可以不同）
	 * 
	 * @return : 影响的行数
	 */
	int batchUpdateFesPlansToDiffVals(Map<String, Object> map);

	/**
	 * 修改多个对象（每一条记录都相同）
	 * 
	 * @return : 影响的行数
	 */
	int batchUpdateFesPlansToSameVals(Map<String, Object> map);

	/**
	 * 按ID删除对象
	 */
	int deleteFesPlanById(Integer id);

	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteFesPlanByIds(List<Integer> ids);

	/**
	 * 通过主键查找对象
	 */
	FesPlanVO getFesPlanById(Integer id);

	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<FesPlanVO> getFesPlans(FesPlanVO fesPlan);

	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>> getFesPlansMap(FesPlanVO fesPlan);

	/**
	 * @Description 修改工作计划
	 * @param paramMap
	 * @param request
	 * @return
	 * @throws
	 */
	int updateFesPlan(Map<String, Object> paramMap, HttpServletRequest request);

	/**
	 * @Description 上传工作计划
	 * @param myfiles
	 * @param request
	 * @return
	 * @throws
	 */
	Object uploadPlanFiles(MultipartFile[] myfiles, HttpServletRequest request) throws IOException;

	/**
	 * @Description 查询工作计划列表
	 * @param paramMap
	 * @return
	 * @throws
	 */
	List<Map<String, Object>> queryFesPlanList(Map<String, Object> paramMap);

	/**
	 * @Description 根据工作计划ID查询详细信息
	 * @param paramMap
	 * @return
	 * @throws
	 */
	Map<String, Object> queryFesPlanById(Map<String, Object> paramMap);

	/**
	 * @Description 放弃工作计划
	 * @param paramMap
	 * @return
	 * @throws
	 */
	int updateAbandonFesPlanById(Map<String, Object> paramMap);

	/**
	 * @Description 延期工作计划
	 * @param paramMap
	 * @return
	 * @throws
	 */
	int updateDelayFesPlanById(Map<String, Object> paramMap);

	/**
	 * @Description 查询一天的工作计划列表
	 * @param paramMap
	 * @return
	 * @throws
	 */
	List<Map<String, Object>> queryDailyFesPlanList(Map<String, Object> paramMap);

	/**
	 * @Description 完成工作计划
	 * @param paramMap
	 * @return
	 * @throws
	 */
	int updateCompleteFesPlanById(Map<String, Object> paramMap);

	/**
	 * @Description 工作计划提醒设置
	 * @param paramMap
	 * @return
	 * @throws
	 */
	int updateRemindFesPlanById(Map<String, Object> paramMap);

	/**
	 * @Description 校验月报讲解，每月只能创建一次月报讲解的工作计划
	 * @param userId
	 * @param merchantId
	 * @param planItemType
	 * @param planId
	 * @return void
	 * @throws
	 */
	void checkMonthlyPlan(Integer userId, Integer merchantId, String planItemType, Integer planId);

    FesPlanVO getMonthlyPlanVO(Integer merchantId, Date from, Date to);

	/**
	 * @Description 校验，如果事项类型不为7-其他和5-公司日常工作，则项目必输
	 * @param merchantIdObj
	 * @param planItemType
	 * @return void
	 * @throws
	 */
	void checkMerchantId(Object merchantIdObj, String planItemType);
	
	/**
	 * @Description 校验，月报讲解一个月只能完成一次
	 * @param userId
	 * @param planId
	 * @param merchantId
	 * @param planItemType
	 * @return void
	 * @throws
	 */
	void checkCompleteMonthlyPlan(Integer userId, Integer planId, Integer merchantId, String planItemType);

    /**
     * 得到当前月报的工作任务对象
     *
     * @param merchantId
     * @param from
     * @param to
     * @return
     */
    FesPlanVO getCurrentMonthlyPlan(Integer merchantId, Date from, Date to);
}
