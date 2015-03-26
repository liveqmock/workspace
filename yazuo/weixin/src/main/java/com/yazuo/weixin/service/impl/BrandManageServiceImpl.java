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
package com.yazuo.weixin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.weixin.dao.BrandAndKeyRelativeDao;
import com.yazuo.weixin.service.BrandManageService;
import com.yazuo.weixin.vo.BrandAndKeyRelativeVO;

/**
 * 商户与key关联关系
 * @author kyy
 * @date 2014-9-25 下午5:47:14
 */
@Service
public class BrandManageServiceImpl implements BrandManageService {

	@Resource
	private BrandAndKeyRelativeDao brandAndKeyRelativeDao;
	
	@Override
	public long judgeRelativeExist(Integer brandId, String key) {
		return brandAndKeyRelativeDao.judgeRelativeExist(brandId, key);
	}

	@Override
	public BrandAndKeyRelativeVO getRelativeById(Integer relativeId) {
		return brandAndKeyRelativeDao.getRelativeById(relativeId);
	}

	@Override
	public boolean saveRelative(BrandAndKeyRelativeVO relativeVO) {
		return brandAndKeyRelativeDao.saveRelative(relativeVO);
	}

	@Override
	public boolean updateActivityRecord(BrandAndKeyRelativeVO relativeVO) {
		return brandAndKeyRelativeDao.updateActivityRecord(relativeVO);
	}

	@Override
	public BrandAndKeyRelativeVO getRelativeByBrandId(Integer brandId) {
		return brandAndKeyRelativeDao.getRelativeByBrandId(brandId);
	}

	
}
