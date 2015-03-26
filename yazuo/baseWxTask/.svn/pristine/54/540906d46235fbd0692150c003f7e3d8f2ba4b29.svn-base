/**
 * @Description 
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.syn.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.statistics.dao.HealthDegreeActualValueStatisticsDao;
import com.yazuo.erp.syn.dao.HealthDegreeDao;
import com.yazuo.erp.syn.dao.SynHealthDegreeDao;
import com.yazuo.erp.syn.service.HealthDegreeService;
import com.yazuo.erp.syn.service.SynHealthDegreeService;
import com.yazuo.erp.syn.vo.SynHealthDegreeVO;
import com.yazuo.utils.DateUtil;

/**
 * @Description
 * @author zhaohuaqin
 * @date 2014-8-12 下午1:38:08
 */
@Service
public class SynHealthDegreeServiceImpl implements SynHealthDegreeService {

	/**
	 * 新增收费会员占比目标值
	 */
	private static final int TARGET_VALUE_OF_THE_NEW_CHARGES_MEMBERSHIP_RATIO = 30;

	@Resource
	private HealthDegreeService healthDegreeService;

	@Resource
	private SynHealthDegreeDao synHealthDegreeDao;

	@Resource
	private HealthDegreeDao healthDegreeDao;

	@Resource
	private HealthDegreeActualValueStatisticsDao healthDegreeActualValueStatisticsDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean batchInsertSynHealthDegreeByIndexId(int indexId) throws Exception {
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String month = format.format(today);

		// 目标值
		List<Map<String, Object>> targetValueList = new ArrayList<Map<String, Object>>();
		if (-1 != indexId) {// 新增收费会员占比
			targetValueList = healthDegreeDao.getTargetValueByIndexId(indexId, month);
		}

		Map<String, Object> targetValueMap = new HashMap<String, Object>();
		for (Map<String, Object> map : targetValueList) {
			targetValueMap.put(map.get("brand_id").toString() + "|" + map.get("month").toString(), map);
		}

		// 实际值
		String minMonth = healthDegreeDao.getMinMonth();
		List<Map<String, Object>> actualValueList = queryActualValue(indexId, minMonth, month);
		Map<String, Object> actualValueMap = new HashMap<String, Object>();
		for (Map<String, Object> map : actualValueList) {
			actualValueMap.put(map.get("brand_id").toString() + "|" + map.get("trans_date").toString(), map);
		}

		// 开台数，会员消费占比和新增会员占比时使用
		Map<String, Object> deskNumMap = queryDeskNum(indexId, month);

		Set<String> targetValueKeySet = targetValueMap.keySet();
		Set<String> actualValueKeySet = actualValueMap.keySet();
		Collection disjunction = CollectionUtils.disjunction(targetValueKeySet, actualValueKeySet);
		Collection add = CollectionUtils.intersection(disjunction, targetValueKeySet);

		List<SynHealthDegreeVO> listHealthDegreeVOs = getSynHealthDegreeVO(actualValueList, targetValueMap, indexId, deskNumMap);
		List<SynHealthDegreeVO> listHealthDegreeVOs2 = getAddSynHealthDegreeVO(indexId, targetValueMap, add);

		batchInsertVO(listHealthDegreeVOs);
		batchInsertVO(listHealthDegreeVOs2);
		return true;
	}

	private Map<String, Object> queryDeskNum(int indexId, String month) throws Exception {
		Map<String, Object> deskNumMap = new HashMap<String, Object>();
		if (indexId == 16 || indexId == 15 || indexId == -1) {
			// 查询开台数
			List<Map<String, Object>> deskNumList = healthDegreeDao.getTargetValueByIndexId(11, month);
			for (Map<String, Object> map : deskNumList) {
				String brandId = map.get("brand_id").toString();
				String monthDeskNum = map.get("month").toString();
				deskNumMap.put(brandId + "|" + monthDeskNum, map.get("index_value"));
			}
		}
		return deskNumMap;
	}

	/**
	 * @Description TODO
	 * @param listHealthDegreeVOs
	 * @return void
	 * @throws
	 */
	private void batchInsertVO(List<SynHealthDegreeVO> listHealthDegreeVOs) {

		if (!CollectionUtils.isEmpty(listHealthDegreeVOs)) {
			int size = listHealthDegreeVOs.size();
			if (size > 1000) {
				List<SynHealthDegreeVO> synhealthdegreesplit = new ArrayList<SynHealthDegreeVO>();
				for (int i = 0; i < size; i++) {
					synhealthdegreesplit.add(listHealthDegreeVOs.get(i));
					if (synhealthdegreesplit.size() == 1000) {
						synHealthDegreeDao.batchInsertSynHealthDegree(synhealthdegreesplit);
						synhealthdegreesplit.clear();
					}
				}
				synHealthDegreeDao.batchInsertSynHealthDegree(synhealthdegreesplit);
			} else {
				synHealthDegreeDao.batchInsertSynHealthDegree(listHealthDegreeVOs);
			}
		}
	}

	private List<SynHealthDegreeVO> getAddSynHealthDegreeVO(int indexId, Map<String, Object> targetValueMap, Collection add)
			throws ParseException {
		List<SynHealthDegreeVO> vo1 = new ArrayList<SynHealthDegreeVO>();
		SynHealthDegreeVO synHealthDegreeVO;
		if (!CollectionUtils.isEmpty(add)) {
			for (Object object : add) {
				synHealthDegreeVO = new SynHealthDegreeVO();
				Map<String, Object> addMap = (Map<String, Object>) targetValueMap.get(object);
				synHealthDegreeVO.setMerchantId((Integer) addMap.get("brand_id"));
				synHealthDegreeVO.setTargetType(getTargetType(indexId));
				synHealthDegreeVO.setTargetValue((BigDecimal) addMap.get("index_value"));
				synHealthDegreeVO.setActualValue(new BigDecimal(0));
				synHealthDegreeVO.setHealthDegree(new BigDecimal(0));
				String month = addMap.get("month").toString();
				String[] split = org.apache.commons.lang.StringUtils.split(object.toString(), "|");
				synHealthDegreeVO.setReportTime(new SimpleDateFormat("yyyy-MM").parse(split[1]));
				synHealthDegreeVO.setUpdateBy(Constant.DEFAULTUSERID);
				synHealthDegreeVO.setUpdateTime(new Date());
				vo1.add(synHealthDegreeVO);
			}
		}
		return vo1;
	}

	private List<SynHealthDegreeVO> getSynHealthDegreeVO(List<Map<String, Object>> actualValueList,
			Map<String, Object> targetValueMap, int indexId, Map<String, Object> deskNumMap) throws ParseException {
		List<SynHealthDegreeVO> synMerchantVOs = new ArrayList<SynHealthDegreeVO>();
		if (!CollectionUtils.isEmpty(actualValueList)) {
			String targetType = getTargetType(indexId);
			SynHealthDegreeVO synHealthDegreeVO;
			for (Map<String, Object> map : actualValueList) {
				synHealthDegreeVO = new SynHealthDegreeVO();
				synHealthDegreeVO.setMerchantId((Integer) map.get("brand_id"));
				synHealthDegreeVO.setTargetType(targetType);
				String brand_id = map.get("brand_id").toString();
				String trans_date = map.get("trans_date").toString();
				Object object = targetValueMap.get(brand_id + "|" + trans_date);
				BigDecimal index_value = new BigDecimal(0);
				if (null != object) {
					index_value = (BigDecimal) ((Map<String, Object>) object).get("index_value");
				}
				if (indexId == -1) {
					index_value = new BigDecimal(TARGET_VALUE_OF_THE_NEW_CHARGES_MEMBERSHIP_RATIO);
				}
				synHealthDegreeVO.setTargetValue(index_value);
				String monthStr = map.get("trans_date").toString();
				BigDecimal actualValue = new BigDecimal(0);
				if (indexId == 14) {
					actualValue = (java.math.BigDecimal) map.get("card_income");
				} else if (indexId == 18) {
					actualValue = (java.math.BigDecimal) map.get("store_pay");
				} else if (indexId == 16) {
					BigDecimal memberDeskNum = new BigDecimal((Long) map.get("trans_quantity"));
					BigDecimal deskNum = new BigDecimal(0);
					if (null != deskNumMap.get(brand_id + "|" + monthStr)) {
						deskNum = (BigDecimal) deskNumMap.get(brand_id + "|" + monthStr);
					}
					if (deskNum.compareTo(new BigDecimal(0)) != 0) {
						actualValue = memberDeskNum.divide(deskNum, 2, BigDecimal.ROUND_HALF_UP);
						actualValue = actualValue.multiply(new BigDecimal(100));
					}
				} else if (indexId == 15) {
					BigDecimal subtract = getSubtract(deskNumMap, map, brand_id, monthStr);
					BigDecimal integralMember = new BigDecimal((Long) map.get("integral_member"));
					if (subtract.compareTo(new BigDecimal(0)) > 0) {
						BigDecimal divide = integralMember.divide(subtract, 2, BigDecimal.ROUND_HALF_UP);
						actualValue = divide.multiply(new BigDecimal(100));
					}
				} else if (indexId == -1) {
					BigDecimal subtract = getSubtract(deskNumMap, map, brand_id, monthStr);
					BigDecimal integralCard = new BigDecimal((Long) map.get("integral_card"));
					BigDecimal storeCard = new BigDecimal((Long) map.get("store_card"));
					BigDecimal newCardNum = integralCard.add(storeCard);// 截止当天新增激活的卡
					// 截止当天总开台数 - 截止当天老会员开台数，可能为负值，负值则表示异常(人工填入数据不对)，但结果按真实情况入库
					if (subtract.compareTo(new BigDecimal(0)) != 0) {
						BigDecimal divide = newCardNum.divide(subtract, 2, BigDecimal.ROUND_HALF_UP);
						actualValue = divide.multiply(new BigDecimal(100));
					}
				}

				synHealthDegreeVO.setActualValue(actualValue);
				synHealthDegreeVO.setReportTime(new SimpleDateFormat("yyyy-MM").parse(monthStr));

				if (index_value.compareTo(new BigDecimal(0)) == 0 || actualValue.compareTo(new BigDecimal(0)) == 0) {
					synHealthDegreeVO.setHealthDegree(new BigDecimal(0));
				} else {
					BigDecimal divide = actualValue.divide(index_value, 2, BigDecimal.ROUND_HALF_UP);
					synHealthDegreeVO.setHealthDegree(divide.multiply(new BigDecimal(100)));
				}
				synHealthDegreeVO.setUpdateBy(Constant.DEFAULTUSERID);
				synHealthDegreeVO.setUpdateTime(new Date());
				synMerchantVOs.add(synHealthDegreeVO);
			}

		}
		return synMerchantVOs;
	}

	/**
	 * @Description 截止当天总开台数 - 截止当天老会员开台数
	 * @param deskNumMap
	 * @param actualMap
	 * @param brand_id
	 * @param monthStr
	 * @return
	 * @return BigDecimal
	 * @throws
	 */
	private BigDecimal getSubtract(Map<String, Object> deskNumMap, Map<String, Object> actualMap, String brand_id, String monthStr) {
		BigDecimal oldMemberDeskNum = new BigDecimal((Long) actualMap.get("old_trans_quantity"));
		BigDecimal deskNum = new BigDecimal(0);
		if (null != deskNumMap.get(brand_id + "|" + monthStr)) {
			deskNum = (BigDecimal) deskNumMap.get(brand_id + "|" + monthStr);
		}
		BigDecimal subtract = deskNum.subtract(oldMemberDeskNum);
		return subtract;
	}

	private String getTargetType(int indexId) {
		String targetType = null;
		if (14 == indexId) {
			targetType = "2";
		} else if (15 == indexId) {
			targetType = "1";
		} else if (16 == indexId) {
			targetType = "3";
		} else if (18 == indexId) {
			targetType = "4";
		} else if (-1 == indexId) {
			targetType = "5";
		}
		return targetType;
	}

	private List<Map<String, Object>> queryActualValue(int indexId, String minMonth, String maxMonth) {
		List<Map<String, Object>> actualValueList = new ArrayList<Map<String, Object>>();
		actualValueList = healthDegreeActualValueStatisticsDao.getTargetActualValueByBrandIds(minMonth + "-01", maxMonth + "-01");
		return actualValueList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean batchInsertCurrentMonthSynHealthDegreeByIndexId(int indexId) throws Exception {
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

		int day = DateUtil.getDay(today);// 当前天数
		int daysOfMonth = DateUtil.getDaysOfMonth(today);// 本月总天数
		// 本月当前天数占比
		BigDecimal proportion = new BigDecimal(day).divide(new BigDecimal(daysOfMonth), 2, BigDecimal.ROUND_HALF_UP);

		Date previousDay = DateUtil.getPreviousMonth(today);
		String currentMonth = format.format(today);// 当前月
		String previousMonth = format.format(previousDay);// 上月
		Date nextDay = DateUtil.getNextMonth(today);
		String nextMonth = format.format(nextDay);// 下月
		List<Map<String, Object>> targetValueList = new ArrayList<Map<String, Object>>();// 当月目标值集合
		String month;
		if (day <= 15) {
			month = previousMonth;
		} else {
			month = currentMonth;
		}
		if (indexId == 15 || indexId == 16 || indexId == -1) {
			proportion = new BigDecimal(1);
		}
		// 截止当天目标值集合
		if (-1 != indexId) {
			targetValueList = healthDegreeDao.getTargetValueByIndexIdAndMonth(indexId, month, proportion);
		}

		Map<String, Object> targetValueMap = new HashMap<String, Object>();
		for (Map<String, Object> map : targetValueList) {
			targetValueMap.put(map.get("brand_id").toString() + "|" + currentMonth, map);
		}

		// 实际值集合
		List<Map<String, Object>> actualValueList = queryActualValue(indexId, currentMonth, nextMonth);
		Map<String, Object> actualValueMap = new HashMap<String, Object>();
		for (Map<String, Object> map : actualValueList) {
			actualValueMap.put(map.get("brand_id").toString() + "|" + map.get("trans_date").toString(), map);
		}
		// 开台数，会员消费占比和新增会员占比时使用
		Map<String, Object> deskNumMap = queryCurrMonDeskNum(indexId, month, currentMonth,
				new BigDecimal(day).divide(new BigDecimal(daysOfMonth), 2, BigDecimal.ROUND_HALF_UP));

		Set<String> targetValueKeySet = targetValueMap.keySet();
		Set<String> actualValueKeySet = actualValueMap.keySet();
		Collection disjunction = CollectionUtils.disjunction(targetValueKeySet, actualValueKeySet);
		Collection add = CollectionUtils.intersection(disjunction, targetValueKeySet);

		List<SynHealthDegreeVO> listHealthDegreeVOs = getSynHealthDegreeVO(actualValueList, targetValueMap, indexId, deskNumMap);
		List<SynHealthDegreeVO> listHealthDegreeVOs2 = getAddSynHealthDegreeVO(indexId, targetValueMap, add);

		batchInsertVO(listHealthDegreeVOs);
		batchInsertVO(listHealthDegreeVOs2);

		return true;
	}

	private Map<String, Object> queryCurrMonDeskNum(int indexId, String month, String currentMonth, BigDecimal proportion)
			throws Exception {
		Map<String, Object> deskNumMap = new HashMap<String, Object>();
		if (indexId == 16 || indexId == 15 || indexId == -1) {
			// 查询开台数
			List<Map<String, Object>> deskNumList = healthDegreeDao.getTargetValueByIndexIdAndMonth(11, month, proportion);
			for (Map<String, Object> map : deskNumList) {
				String brandId = map.get("brand_id").toString();
				deskNumMap.put(brandId + "|" + currentMonth, map.get("index_value"));
			}
		}
		return deskNumMap;
	}

	/**
	 * 更新当月之前所有商户某个指标的健康度的值(更新2个月的数据)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean batchUpdateSynHealthDegreeByIndexId(int indexId) throws Exception {
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		String month = format.format(today);

		Date previousYear = DateUtil.getPreviousSeveralMonth(today, 2);// 前两月
		String previousYearMon = format.format(previousYear);

		String minMonth = healthDegreeDao.getMinMonth();
		boolean flag = DateUtil.compare_date(previousYearMon + "-01", minMonth + "-01");
		String startMon = null;
		if (flag) {
			startMon = previousYearMon;
		} else {
			startMon = minMonth;
		}
		// 当月之前两月内目标值集合
		List<Map<String, Object>> targetValueList = new ArrayList<Map<String, Object>>();
		if (-1 != indexId) {
			targetValueList = healthDegreeDao.getTargetValueByIndexIdDate(indexId, startMon, month);
		}

		// 当月之前两月内实际值集合
		List<Map<String, Object>> actualValueList = queryActualValue(indexId, startMon, month);

		Map<String, Object> targetValueMap = new HashMap<String, Object>();
		for (Map<String, Object> map : targetValueList) {
			targetValueMap.put(map.get("brand_id").toString() + "|" + map.get("month").toString(), map);
		}

		Map<String, Object> actualValueMap = new HashMap<String, Object>();
		for (Map<String, Object> map : actualValueList) {
			actualValueMap.put(map.get("brand_id").toString() + "|" + map.get("trans_date").toString(), map);
		}

		Set<String> targetValueKeySet = targetValueMap.keySet();
		Set<String> actualValueKeySet = actualValueMap.keySet();
		Collection disjunction = CollectionUtils.disjunction(targetValueKeySet, actualValueKeySet);
		Collection add = CollectionUtils.intersection(disjunction, targetValueKeySet);

		// 开台数，会员消费占比和新增会员占比时使用
		Map<String, Object> deskNumMap = new HashMap<String, Object>();
		if (indexId == 16 || indexId == 15 || indexId == -1) {
			// 查询开台数
			List<Map<String, Object>> deskNumList = healthDegreeDao.getTargetValueByIndexIdDate(11, startMon, month);
			for (Map<String, Object> map : deskNumList) {
				String brandId = map.get("brand_id").toString();
				String monthDeskNum = map.get("month").toString();
				deskNumMap.put(brandId + "|" + monthDeskNum, map.get("index_value"));
			}
		}

		List<SynHealthDegreeVO> listHealthDegreeVOs = getSynHealthDegreeVO(actualValueList, targetValueMap, indexId, deskNumMap);
		List<SynHealthDegreeVO> listHealthDegreeVOs2 = getAddSynHealthDegreeVO(indexId, targetValueMap, add);
		listHealthDegreeVOs.addAll(listHealthDegreeVOs2);

		// 新增收费会员占比，ERP数据先删后增
		if (-1 == indexId) {
			// 删除ERP当月之前两月内的新增收费会员占比数据
			int count = synHealthDegreeDao.deleteSynHealthDegreeByIndexIdAndMonth(getTargetType(indexId),
					format.parse(startMon + "-01"), format.parse(month + "-01"));

			// 添加ERP当月之前两月内的新增收费会员占比数据
			batchInsertVO(listHealthDegreeVOs);
			return true;
		}

		Map<String, Object> newMap = new HashMap<String, Object>();
		for (SynHealthDegreeVO synHealthDegreeVO : listHealthDegreeVOs) {
			newMap.put(synHealthDegreeVO.getMerchantId().toString() + "|" + format.format(synHealthDegreeVO.getReportTime()),
					synHealthDegreeVO);
		}

		// ERP 集合
		List<Map<String, Object>> synHealthDegreeList = synHealthDegreeDao.getSynHealthDegreeByIndexIdAndMonth(
				getTargetType(indexId), format.parse(startMon + "-01"), format.parse(month + "-01"));
		Map<String, Object> oldMap = new HashMap<String, Object>();
		for (Map<String, Object> map : synHealthDegreeList) {
			oldMap.put(map.get("brand_id").toString() + "|" + map.get("month").toString(), map);
		}

		Set<String> newKeySet = newMap.keySet();
		Set<String> oldKeySet = oldMap.keySet();
		Collection disjunction2 = CollectionUtils.disjunction(newKeySet, oldKeySet);
		Collection update = CollectionUtils.intersection(newKeySet, oldKeySet);
		Collection addList = CollectionUtils.intersection(disjunction2, newKeySet);

		Collection<Object> updateList = new ArrayList<Object>();
		for (Object object : update) {
			SynHealthDegreeVO updateNew = (SynHealthDegreeVO) newMap.get(object);
			Map<String, Object> updateOld = (Map<String, Object>) oldMap.get(object);
			BigDecimal index_value = updateNew.getTargetValue();
			BigDecimal target_value = (BigDecimal) updateOld.get("target_value");
			int compare1 = index_value.compareTo(target_value);
			BigDecimal actual_value_new = updateNew.getActualValue();
			BigDecimal actual_value_old = (BigDecimal) updateOld.get("actual_value");
			int compare2 = actual_value_new.compareTo(actual_value_old);

			if (compare1 != 0 || compare2 != 0) {
				updateList.add(object);
			}
		}

		List<SynHealthDegreeVO> updateVOList = new ArrayList<SynHealthDegreeVO>();
		for (Object object : updateList) {
			SynHealthDegreeVO vo = (SynHealthDegreeVO) newMap.get(object);
			Map<String, Object> old = (Map<String, Object>) oldMap.get(object);
			vo.setId((Integer) old.get("id"));
			updateVOList.add(vo);
		}

		List<SynHealthDegreeVO> addVOList = new ArrayList<SynHealthDegreeVO>();
		for (Object object : addList) {
			SynHealthDegreeVO vo = (SynHealthDegreeVO) newMap.get(object);
			addVOList.add(vo);
		}

		if (!CollectionUtils.isEmpty(updateVOList)) {
			synHealthDegreeDao.batchUpdateSynHealthDegree(updateVOList);
		}

		if (!CollectionUtils.isEmpty(addVOList)) {
			synHealthDegreeDao.batchInsertSynHealthDegree(addVOList);
		}
		return true;
	}

	/**
	 * 更新本月所有商户某个指标的健康度的值
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean batchUpdateCurrentMonthSynHealthDegreeByIndexId(int indexId) throws Exception {
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");

		int day = DateUtil.getDay(today);// 当前天数
		int daysOfMonth = DateUtil.getDaysOfMonth(today);// 本月总天数
		// 本月当前天数占比
		BigDecimal proportion = new BigDecimal(day).divide(new BigDecimal(daysOfMonth), 2, BigDecimal.ROUND_HALF_UP);

		Date previousDay = DateUtil.getPreviousMonth(today);
		String currentMonth = format.format(today);// 当前月
		String previousMonth = format.format(previousDay);// 上月
		Date nextDay = DateUtil.getNextMonth(today);
		String nextMonth = format.format(nextDay);// 下月
		List<Map<String, Object>> targetValueList = new ArrayList<Map<String, Object>>();// 当月目标值集合
		/** 当前月的目标值取上月的目标值 修改日期：2014-09-16 */
		String month = previousMonth;// 目标月
		// if (day <= 15) {
		// month = previousMonth;
		// } else {
		// month = currentMonth;
		// }
		if (indexId == 15 || indexId == 16) {
			proportion = new BigDecimal(1);
		}
		// 截止当天目标值集合
		if (-1 != indexId) {
			targetValueList = healthDegreeDao.getTargetValueByIndexIdAndMonth(indexId, month, proportion);
		}
		Map<String, Object> targetValueMap = new HashMap<String, Object>();
		for (Map<String, Object> map : targetValueList) {
			targetValueMap.put(map.get("brand_id").toString() + "|" + currentMonth, map);
		}

		// 实际值集合
		List<Map<String, Object>> actualValueList = queryActualValue(indexId, currentMonth, nextMonth);
		Map<String, Object> actualValueMap = new HashMap<String, Object>();
		for (Map<String, Object> map : actualValueList) {
			actualValueMap.put(map.get("brand_id").toString() + "|" + map.get("trans_date").toString(), map);
		}
		// 开台数，会员消费占比和新增会员占比时使用
		Map<String, Object> deskNumMap = queryCurrMonDeskNum(indexId, month, currentMonth,
				new BigDecimal(day).divide(new BigDecimal(daysOfMonth), 2, BigDecimal.ROUND_HALF_UP));

		Set<String> targetValueKeySet = targetValueMap.keySet();
		Set<String> actualValueKeySet = actualValueMap.keySet();
		Collection disjunction = CollectionUtils.disjunction(targetValueKeySet, actualValueKeySet);
		Collection add = CollectionUtils.intersection(disjunction, targetValueKeySet);
		List<SynHealthDegreeVO> listHealthDegreeVOs = getSynHealthDegreeVO(actualValueList, targetValueMap, indexId, deskNumMap);
		List<SynHealthDegreeVO> listHealthDegreeVOs2 = getAddSynHealthDegreeVO(indexId, targetValueMap, add);

		listHealthDegreeVOs.addAll(listHealthDegreeVOs2);

		// 新增收费会员占比，ERP数据先删后增
		if (-1 == indexId) {
			// 删除ERP当月的新增收费会员占比数据
			int count = synHealthDegreeDao.deleteSynHealthDegreeByIndexIdAndCurrentMonth(getTargetType(indexId),
					format.parse(currentMonth + "-01"));

			// 添加ERP当月的新增收费会员占比数据
			batchInsertVO(listHealthDegreeVOs);
			return true;
		}

		Map<String, Object> newMap = new HashMap<String, Object>();
		for (SynHealthDegreeVO synHealthDegreeVO : listHealthDegreeVOs) {
			newMap.put(synHealthDegreeVO.getMerchantId().toString() + "|" + format.format(synHealthDegreeVO.getReportTime()),
					synHealthDegreeVO);
		}

		// ERP 集合
		List<Map<String, Object>> synHealthDegreeList = synHealthDegreeDao.getSynHealthDegree(getTargetType(indexId),
				format.parse(currentMonth + "-01"));
		Map<String, Object> oldMap = new HashMap<String, Object>();
		for (Map<String, Object> map : synHealthDegreeList) {
			oldMap.put(map.get("brand_id").toString() + "|" + map.get("month").toString(), map);
		}

		Set<String> newKeySet = newMap.keySet();
		Set<String> oldKeySet = oldMap.keySet();
		Collection disjunction2 = CollectionUtils.disjunction(newKeySet, oldKeySet);
		Collection update = CollectionUtils.intersection(newKeySet, oldKeySet);
		Collection addList = CollectionUtils.intersection(disjunction2, newKeySet);

		Collection<Object> updateList = new ArrayList<Object>();
		for (Object object : update) {
			SynHealthDegreeVO updateNew = (SynHealthDegreeVO) newMap.get(object);
			Map<String, Object> updateOld = (Map<String, Object>) oldMap.get(object);
			BigDecimal index_value = updateNew.getTargetValue();
			BigDecimal target_value = (BigDecimal) updateOld.get("target_value");
			int compare1 = index_value.compareTo(target_value);
			BigDecimal actual_value_new = updateNew.getActualValue();
			BigDecimal actual_value_old = (BigDecimal) updateOld.get("actual_value");
			int compare2 = actual_value_new.compareTo(actual_value_old);

			if (compare1 != 0 || compare2 != 0) {
				updateList.add(object);
			}
		}

		List<SynHealthDegreeVO> updateVOList = new ArrayList<SynHealthDegreeVO>();
		for (Object object : updateList) {
			SynHealthDegreeVO vo = (SynHealthDegreeVO) newMap.get(object);
			Map<String, Object> old = (Map<String, Object>) oldMap.get(object);
			vo.setId((Integer) old.get("id"));
			updateVOList.add(vo);
		}

		List<SynHealthDegreeVO> addVOList = new ArrayList<SynHealthDegreeVO>();
		for (Object object : addList) {
			SynHealthDegreeVO vo = (SynHealthDegreeVO) newMap.get(object);
			addVOList.add(vo);
		}

		if (!CollectionUtils.isEmpty(updateVOList)) {
			synHealthDegreeDao.batchUpdateSynHealthDegree(updateVOList);
		}

		if (!CollectionUtils.isEmpty(addVOList)) {
			synHealthDegreeDao.batchInsertSynHealthDegree(addVOList);
		}

		return true;
	}

}
