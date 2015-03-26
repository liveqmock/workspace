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
package com.yazuo.erp.syn.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.erp.syn.dao.HealthDegreeDao;
import com.yazuo.erp.syn.service.HealthDegreeService;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-12 上午11:23:31
 */
@Service
public class HealthDegreeServiceImpl implements HealthDegreeService {

	@Resource
	private HealthDegreeDao healthDegreeDao;

	/**
	 * 查询指定月份之前某指标的目标值
	 * 
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> getTargetValueByIndexId(Integer indexId, String month) throws Exception {
		return healthDegreeDao.getTargetValueByIndexId(indexId, month);
	}

}
