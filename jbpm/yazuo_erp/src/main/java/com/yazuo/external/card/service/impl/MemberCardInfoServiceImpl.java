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
package com.yazuo.external.card.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.external.card.dao.MemberCardInfoDao;
import com.yazuo.external.card.service.MemberCardInfoService;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-20 下午7:53:31
 */
@Service
public class MemberCardInfoServiceImpl implements MemberCardInfoService {

	@Resource
	private MemberCardInfoDao memberCardInfoDao;

	@Override
	public List<Map<String, Object>> getMemberCardInfoByBrandId(Integer brandId) {
		return memberCardInfoDao.getMemberCardInfoByBrandId(brandId);
	}

}
