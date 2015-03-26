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
package com.yazuo.external.active.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.external.active.dao.ActiveCrmDao;
import com.yazuo.external.active.service.ActiveCrmService;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-19 下午5:28:35
 */
@Service
public class ActiveCrmServiceImpl implements ActiveCrmService {
	@Resource
	private ActiveCrmDao activeCrmDao;

	/**
	 * 正在进行的营销活动
	 */
	@Override
	public List<Map<String, Object>> getActiveExecutiving(Integer brandId) {
		return activeCrmDao.getActiveExecutiving(brandId);
	}
}
