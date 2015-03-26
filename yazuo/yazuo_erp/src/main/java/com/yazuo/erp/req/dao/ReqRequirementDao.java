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

package com.yazuo.erp.req.dao;

import java.util.*;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.req.vo.*;
import com.yazuo.erp.req.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface ReqRequirementDao{
	
    /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveReqRequirement (ReqRequirementVO ReqRequirementVO);
	/**
	 * 新增多个对象 @return : 影响的行数
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertReqRequirements (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateReqRequirement (ReqRequirementVO ReqRequirementVO);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateReqRequirementsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateReqRequirementsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteReqRequirementById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteReqRequirementByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	ReqRequirementVO getReqRequirementById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<com.yazuo.erp.req.vo.ReqRequirementVO> getReqRequirements(ReqRequirementVO reqRequirementVO);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getReqRequirementsMap (ReqRequirementVO ReqRequirementVO);
	/** 
	 * 分页查询
	 */
	List<ReqRequirementVO> getComplexRequirementByJoin(ReqRequirementVO reqRequirementVO);
	/**
	 * 查询条件状态下拉框中的延期，是系统自动筛出来超过预计完成时间还未完成的需求
	 */
	void updateRequirementStatusIfOverTime (ReqRequirementVO ReqRequirementVO);

}
