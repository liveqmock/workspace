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

package com.yazuo.erp.minierp.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.fes.dao.FesPlanDao;
import com.yazuo.erp.fes.vo.FesPlanVO;
import com.yazuo.erp.minierp.dao.PlansDao;
import com.yazuo.erp.minierp.dao.UsersDao;
import com.yazuo.erp.minierp.service.PlansService;
import com.yazuo.erp.syn.dao.SynMerchantBrandDao;
import com.yazuo.erp.syn.vo.SynMerchantBrandVO;
import com.yazuo.erp.system.dao.SysUserDao;

/**
 * @Description TODO
 * @author erp team
 * @date
 */

@Service
public class PlansServiceImpl implements PlansService {

	@Resource
	private PlansDao plansDao;

	@Resource
	private SysUserDao sysUserDao;

	@Resource
	private SynMerchantBrandDao synMerchantBrandDao;

	@Resource
	private FesPlanDao fesPlanDao;

	@Resource
	private UsersDao usersDao;

	public boolean savePlansList() throws ParseException {

		int plansCount = plansDao.getPlansCount();
		int limit = 1000;
		int index = (plansCount % limit == 0) ? plansCount / limit : plansCount / limit + 1;
		int offset = 0;

		// 1、查询minierp与erp商户对应关系
		List<Map<String, Object>> synMerchantBrandsList = synMerchantBrandDao.getSynMerchantBrandsMap(new SynMerchantBrandVO());
		Map<String, Object> synMerchantBrandsMap = new HashMap<String, Object>();
		for (Map<String, Object> map : synMerchantBrandsList) {
			synMerchantBrandsMap.put(map.get("mini_merchant_id").toString(), map.get("merchant_id"));
		}
		// 2、查询miniERP用户手机号
		List<String> usersMobileList = usersDao.getUsersMobileList();

		// 3、查询minierp对应的erp用户id
		Map<String, Object> userMap = new HashMap<String, Object>();
		List<Map<String, Object>> sysUserList = sysUserDao.getSysUserByTelAndStatusList(usersMobileList);
		for (Map<String, Object> map : sysUserList) {
			userMap.put(map.get("tel").toString(), map.get("id"));
		}

		for (int i = 0; i < index; i++) {
			// 1、查询minierp工作计划
			offset = limit * i;
			List<Map<String, Object>> plansList = plansDao.getPlansList(limit, offset);

			List<FesPlanVO> fesPlanVOList = new ArrayList<FesPlanVO>();
			FesPlanVO fesPlanVO;
			for (Map<String, Object> map : plansList) {
				String mobile = (String) map.get("mobile");
				Integer userId = (Integer) userMap.get(mobile);

				Integer brand_id = null;
				Integer brand_id_mini = (Integer) map.get("brand_id");// miniERP品牌ID
				if (null != brand_id_mini) {
					if (brand_id_mini.intValue() == 412) {
						brand_id = 259;
					} else if (brand_id_mini.intValue() == 298) {
						brand_id = 45;
					} else {
						brand_id = brand_id_mini;
					}
				}
				
				Integer merchantId = null;
				if (brand_id != null) {
					merchantId = (Integer) synMerchantBrandsMap.get(brand_id.toString());
				}

				if (null != userId) {
					fesPlanVO = getFesPlanVO(map, userId, merchantId);
					fesPlanVOList.add(fesPlanVO);
				}
			}

			// 2、批量插入工作计划
			if (!CollectionUtils.isEmpty(fesPlanVOList)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(FesPlanVO.COLUMN_USER_ID, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_MERCHANT_ID, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_TITLE, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_PLAN_ITEM_TYPE, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_COMMUNICATION_FORM_TYPE, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_DESCRIPTION, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_START_TIME, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_END_TIME, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_EXPLANATION, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_IS_REMIND, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_REMIND_TIME, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_IS_SEND_SMS, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_PLANS_SOURCE, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_STATUS, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_INSERT_BY, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_INSERT_TIME, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_UPDATE_BY, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_UPDATE_TIME, Constant.NOT_NULL);
				map.put(FesPlanVO.COLUMN_IS_ENABLE, Constant.NOT_NULL);
				map.put("list", fesPlanVOList);
				fesPlanDao.batchInsertFesPlans(map);
			}
		}

		return true;
	}

	/**
	 * @throws ParseException
	 * @Description TODO
	 * @param map
	 * @return
	 * @return FesPlanVO
	 * @throws
	 */
	private FesPlanVO getFesPlanVO(Map<String, Object> map, Integer userId, Integer merchantId) throws ParseException {
		FesPlanVO fesPlanVO = new FesPlanVO();
		fesPlanVO.setUserId(userId);
		fesPlanVO.setMerchantId(merchantId);
		fesPlanVO.setTitle((null != map.get("title")) ? map.get("title").toString() : null);
		fesPlanVO.setPlanItemType("7");
		fesPlanVO.setCommunicationFormType("5");
		fesPlanVO.setDescription((null != map.get("description")) ? map.get("description").toString() : null);
		fesPlanVO.setStartTime((null != map.get("start_time")) ? (Date) map.get("start_time") : null);
		fesPlanVO.setEndTime((null != map.get("end_time")) ? (Date) map.get("end_time") : null);
		fesPlanVO.setExplanation((null != map.get("explanation")) ? map.get("explanation").toString() : null);
		fesPlanVO.setIsRemind((null != map.get("is_remind")) ? (Boolean) map.get("is_remind") : false);
		Object object = map.get("start_time");
		Date remindTime = null;
		if (null != object) {
			remindTime = (Date) map.get("start_time");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH");
			remindTime = format.parse(format.format(remindTime));
		}
		fesPlanVO.setRemindTime(remindTime);
		fesPlanVO.setIsSendSms(false);
		fesPlanVO.setPlansSource(getPlansSource(map.get("source")));
		fesPlanVO.setStatus(getStatus(map.get("status")));
		fesPlanVO.setIsEnable("1");
		fesPlanVO.setInsertBy(userId);
		fesPlanVO.setInsertTime((null != map.get("created_at")) ? (Date) map.get("created_at") : null);
		fesPlanVO.setUpdateBy(userId);
		fesPlanVO.setUpdateTime((null != map.get("updated_at")) ? (Date) map.get("updated_at") : null);
		return fesPlanVO;
	}

	/**
	 * @Description TODO
	 * @param object
	 * @return
	 * @return String
	 * @throws
	 */
	private String getStatus(Object object) {
		String status = null;
		if (null != object) {
			String str = object.toString();
			if (StringUtils.equals(str, "未完成")) {
				status = "1";
			} else if (StringUtils.equals(str, "已延期")) {
				status = "2";
			} else if (StringUtils.equals(str, "已延期")) {
				status = "2";
			} else if (StringUtils.equals(str, "已放弃")) {
				status = "3";
			} else if (StringUtils.equals(str, "已完成")) {
				status = "4";
			} else if (StringUtils.equals(str, "已求助")) {
				status = "5";
			}
		}
		return status;
	}

	/**
	 * @Description TODO
	 * @param object
	 * @return
	 * @return String
	 * @throws
	 */
	private String getPlansSource(Object object) {
		String plansSource = "1";
		if (null != object && StringUtils.equals(object.toString(), "系统指派")) {
			plansSource = "2";
		}
		return plansSource;
	}
}
