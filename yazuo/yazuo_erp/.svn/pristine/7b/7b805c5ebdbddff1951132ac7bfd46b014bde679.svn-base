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

package com.yazuo.erp.bes.dao;

import java.util.*;
import com.yazuo.erp.bes.vo.*;
import com.yazuo.erp.bes.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import com.yazuo.erp.interceptors.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BesMonthlyReportDao{
	
    /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveBesMonthlyReport (BesMonthlyReportVO besMonthlyReport);
	/**
	 * 新增多个对象 @return : 影响的行数
	 * @parameter maps: (key:'要更新的属性列名+关键字list), 下同;
	 */
	int batchInsertBesMonthlyReports (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateBesMonthlyReport (BesMonthlyReportVO besMonthlyReport);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateBesMonthlyReportsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateBesMonthlyReportsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteBesMonthlyReportById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteBesMonthlyReportByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	BesMonthlyReportVO getBesMonthlyReportById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<BesMonthlyReportVO> getBesMonthlyReports (BesMonthlyReportVO besMonthlyReport);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getBesMonthlyReportsMap (BesMonthlyReportVO besMonthlyReport);

    /**
     *
     * @param conditionVOs
     * @param merchantName
     * @param checkDate
     * @return
     */
    Page<Integer> getMerchantIds(@Param("conditions") List<MonthlyConditionVO> conditionVOs,
                                 @Param("merchantName") String merchantName,
                                 @Param("beginTime") Date beginTime,
                                 @Param("endTime") Date endTime,
                                 @Param("checkDate") int checkDate);

    List<Map<String, Object>> getMonthlyReports( @Param("merchantIds") List<Integer> merchantIds);


    List<Map<Integer, Object>> getMerchantContact(@Param("from")Date from, @Param("to")Date to);
}
