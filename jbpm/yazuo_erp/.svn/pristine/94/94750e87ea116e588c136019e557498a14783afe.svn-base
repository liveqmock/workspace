/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.bes.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.yazuo.erp.bes.vo.*;
import com.yazuo.erp.bes.dao.*;
import com.yazuo.erp.bes.exception.BesBizException;

/**
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.bes.service.BesMonthlyCheckService;
import com.yazuo.erp.interceptors.Page;

@Service
public class BesMonthlyCheckServiceImpl implements BesMonthlyCheckService {

	@Resource
	private BesMonthlyCheckDao besMonthlyCheckDao;

	public int saveBesMonthlyCheck(BesMonthlyCheckVO besMonthlyCheck) {
		return besMonthlyCheckDao.saveBesMonthlyCheck(besMonthlyCheck);
	}

	public int batchInsertBesMonthlyChecks(Map<String, Object> map) {
		return besMonthlyCheckDao.batchInsertBesMonthlyChecks(map);
	}

	public int updateBesMonthlyCheck(BesMonthlyCheckVO besMonthlyCheck) {
		return besMonthlyCheckDao.updateBesMonthlyCheck(besMonthlyCheck);
	}

	public int batchUpdateBesMonthlyChecksToDiffVals(Map<String, Object> map) {
		return besMonthlyCheckDao.batchUpdateBesMonthlyChecksToDiffVals(map);
	}

	public int batchUpdateBesMonthlyChecksToSameVals(Map<String, Object> map) {
		return besMonthlyCheckDao.batchUpdateBesMonthlyChecksToSameVals(map);
	}

	public int deleteBesMonthlyCheckById(Integer id) {
		return besMonthlyCheckDao.deleteBesMonthlyCheckById(id);
	}

	public int batchDeleteBesMonthlyCheckByIds(List<Integer> ids) {
		return besMonthlyCheckDao.batchDeleteBesMonthlyCheckByIds(ids);
	}

	public BesMonthlyCheckVO getBesMonthlyCheckById(Integer id) {
		return besMonthlyCheckDao.getBesMonthlyCheckById(id);
	}

	public List<BesMonthlyCheckVO> getBesMonthlyChecks(BesMonthlyCheckVO besMonthlyCheck) {
		return besMonthlyCheckDao.getBesMonthlyChecks(besMonthlyCheck);
	}

	public List<Map<String, Object>> getBesMonthlyChecksMap(BesMonthlyCheckVO besMonthlyCheck) {
		return besMonthlyCheckDao.getBesMonthlyChecksMap(besMonthlyCheck);
	}

	/**
	 * @Title queryBesMonthlyCheckList
	 * @Description 列表显示 月报检查
	 * @param params
	 * @return
	 * @see com.yazuo.erp.bes.service.BesMonthlyCheckService#queryBesMonthlyCheckList(java.util.Map)
	 */
	@Override
	public Page<Map<String, Object>> queryBesMonthlyCheckList(Map<String, Object> params) {
		Object reportTimeObj = (Object) params.get("reportTime");
		if (null == reportTimeObj) {
			throw new BesBizException("请选择查询月份");
		}
		Date reportTime = this.toDate(reportTimeObj); // 例如，2014年10月，则yearMonth认为是2014-10-1-0-0-0转换的毫秒
		params.put("reportTime", reportTime);

		// 得到明天，yyyy-mm-dd
		String tomorrow = getTomorrow();

		params.put("tomorrow", tomorrow);
		Page<Map<String, Object>> list = besMonthlyCheckDao.queryBesMonthlyCheckList(params);
		return list;
	}

	/**
	 * @Description 得到明天，yyyy-mm-dd
	 * @return
	 * @return String
	 * @throws
	 */
	private String getTomorrow() {
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天，整数往后推，负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String tomorrow = sdf.format(date);
		return tomorrow;
	}

	/**
	 * @Description 由毫秒转换为日期格式
	 * @param timeObj
	 * @return
	 * @throws NumberFormatException
	 * @throws
	 */
	private Date toDate(Object timeObj) throws NumberFormatException {
		Date timeDate = null;
		String timeStr = String.valueOf(timeObj);
		if (null != timeObj && !"0".equals(timeStr)) {
			Long timeL = Long.parseLong(timeStr);
			timeDate = new Date(timeL);
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

		return timeDate;
	}
}
