/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yazuo.erp.interceptors.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.system.vo.SysOperationLogVO;


@Repository
public interface SysOperationLogDao{
	
    /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveSysOperationLog (SysOperationLogVO sysOperationLog);
	/**
	 * 新增多个对象 @return : 影响的行数
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertSysOperationLogs (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateSysOperationLog (SysOperationLogVO sysOperationLog);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateSysOperationLogsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateSysOperationLogsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteSysOperationLogById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteSysOperationLogByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	SysOperationLogVO getSysOperationLogById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<SysOperationLogVO> getSysOperationLogs (SysOperationLogVO sysOperationLog);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getSysOperationLogsMap (SysOperationLogVO sysOperationLog);


	Page<SysOperationLogVO> searchSysOperationByTypeAndIds(@Param("operationLogIds") List<Integer> operationLogIds, @Param("type")String type,@Param("beginTime")Date beginTime, @Param("endTime")Date endTime);

}
