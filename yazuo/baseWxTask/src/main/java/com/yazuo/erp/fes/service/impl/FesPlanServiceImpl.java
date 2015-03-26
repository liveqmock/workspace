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
package com.yazuo.erp.fes.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.fes.dao.FesPlanDao;
import com.yazuo.erp.fes.service.FesPlanService;
import com.yazuo.erp.fes.vo.FesPlanVO;
import com.yazuo.erp.sys.dao.SysRemindDao;
import com.yazuo.erp.sys.vo.SysRemindVO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-11 上午11:26:10
 */

@Service
public class FesPlanServiceImpl implements FesPlanService {

	@Resource
	private FesPlanDao fesPlanDao;

	@Resource
	private SysRemindDao sysRemindDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void batchSaveFesPlan() {
		List<SysRemindVO> sysRemindList = new ArrayList<SysRemindVO>();// 提醒列表
		SysRemindVO sysRemindVO = null;
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
		String remindTime = format.format(today) + ":00:00";

		// 需要提醒的工作计划
		List<FesPlanVO> list = fesPlanDao.getFesPlan(remindTime);
		for (FesPlanVO fesPlanVO : list) {
			sysRemindVO = new SysRemindVO();
			sysRemindVO.setUserId(fesPlanVO.getUserId());
			sysRemindVO.setMerchantId(fesPlanVO.getMerchantId());
			sysRemindVO.setPriorityLevelType("01");
			sysRemindVO.setItemType(StringUtils.equals(fesPlanVO.getPlansSource(), "1") ? "04" : "03");
			sysRemindVO.setItemContent(fesPlanVO.getTitle());
			sysRemindVO.setItemStatus("1");
			sysRemindVO.setIsEnable("1");
			sysRemindVO.setInsertBy(Constant.DEFAULTUSERID);
			sysRemindVO.setInsertTime(new Date());
			sysRemindVO.setUpdateBy(Constant.DEFAULTUSERID);
			sysRemindVO.setUpdateTime(new Date());
			sysRemindList.add(sysRemindVO);
		}

		// 批量插入提醒
		if (!CollectionUtils.isEmpty(sysRemindList)) {
			sysRemindDao.batchInsertSysRemind(sysRemindList);
		}
	}
}
