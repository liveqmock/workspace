/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.bes.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yazuo.erp.bes.vo.BesMonthlyReportVO;
import com.yazuo.erp.bes.vo.MonthlyConditionVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @author erp team
 * @date 
 */
public interface BesMonthlyReportService{
	
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveBesMonthlyReport (BesMonthlyReportVO besMonthlyReport);
	/**
	 * 新增多个对象 @return :
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

    /*
     * 检测是否发送月报
	 */
    int saveOrCheckSendMonthlyReport();

    /**
     * 查看是否已经完成工作计划
     */
    void checkIfCompletedWorkPlan();
	boolean existRequirement(int merchantId, Date beginTime, Date endTime);

    /**
     * 查询用户信息
     * @param conditionVOs
     * @param merchantName
     * @param beginTime
     * @param endTime @return
     * @param checkDate
     */
    Page<Integer> queryMonthlyReports(List<MonthlyConditionVO> conditionVOs, String merchantName, Date beginTime, Date endTime, int checkDate);

    List<Map<String, Object>> getMonthlyReports( List<Integer> merchantIds);
	boolean saveSendMonthlyRpt(Integer merchantId, SysUserVO sessionUser);
	boolean existMonthlyReport(int merchantId, String accessFactor, Date beginTime, Date endTime);
	BesMonthlyReportVO getMonthlyReport(int merchantId, String accessFactor, Date beginTime, Date endTime);

    /**
     * 得到联系人，
     * 确定在一定时间段，无重复联系人，否则只返回一个联系人
     * @param from
     * @param to
     * @return
     */
    Map<Integer, String> getMerchantContact(Date from, Date to);
}
