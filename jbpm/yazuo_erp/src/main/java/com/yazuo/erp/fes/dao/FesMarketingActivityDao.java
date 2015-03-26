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

package com.yazuo.erp.fes.dao;

import java.util.*;

import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;
import com.yazuo.erp.interceptors.Page;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface FesMarketingActivityDao{
	
    /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveFesMarketingActivity (FesMarketingActivityVO fesMarketingActivity);
	/**
	 * 新增多个对象 @return : 影响的行数
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertFesMarketingActivitys (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateFesMarketingActivity (FesMarketingActivityVO fesMarketingActivity);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateFesMarketingActivitysToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateFesMarketingActivitysToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteFesMarketingActivityById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteFesMarketingActivityByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	FesMarketingActivityVO getFesMarketingActivityById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<FesMarketingActivityVO> getFesMarketingActivitys (FesMarketingActivityVO fesMarketingActivity);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getFesMarketingActivitysMap (FesMarketingActivityVO fesMarketingActivity);
	
	/**根据商户名称和状态查询想对应活动*/
	Page<FesMarketingActivityVO> getActivityByOrder(Map<String, Object> paramMap);
	
}
