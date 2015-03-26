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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.erp.minierp.dao.BrandsDao;
import com.yazuo.erp.minierp.service.BrandsService;
import com.yazuo.erp.minierp.vo.BrandsVO;
import com.yazuo.external.account.service.MerchantService;

/**
 * @Description TODO
 * @author erp team
 * @date
 */

@Service
public class BrandsServiceImpl implements BrandsService {

	@Resource
	private BrandsDao brandsDao;

	@Resource
	private MerchantService merchantService;

	public int saveBrands(BrandsVO brands) {
		return brandsDao.saveBrands(brands);
	}

	public int batchInsertBrandss(Map<String, Object> map) {
		return brandsDao.batchInsertBrandss(map);
	}

	public int updateBrands(BrandsVO brands) {
		return brandsDao.updateBrands(brands);
	}

	public int batchUpdateBrandssToDiffVals(Map<String, Object> map) {
		return brandsDao.batchUpdateBrandssToDiffVals(map);
	}

	public int batchUpdateBrandssToSameVals(Map<String, Object> map) {
		return brandsDao.batchUpdateBrandssToSameVals(map);
	}

	public int deleteBrandsById(Integer id) {
		return brandsDao.deleteBrandsById(id);
	}

	public int batchDeleteBrandsByIds(List<Integer> ids) {
		return brandsDao.batchDeleteBrandsByIds(ids);
	}

	public BrandsVO getBrandsById(Integer id) {
		return brandsDao.getBrandsById(id);
	}

	public List<BrandsVO> getBrandss(BrandsVO brands) {
		return brandsDao.getBrandss(brands);
	}

	public List<Map<String, Object>> getBrandssMap(BrandsVO brands) {
		return brandsDao.getBrandssMap(brands);
	}

	@Override
	public List<Map<String, Object>> getBrandsInfo(String name) {
		List<Map<String, Object>> merchants = merchantService.getAllMerchants();
		Map<String, Object> merchantsMap = new HashMap<String, Object>();
		for (Map<String, Object> map : merchants) {
			merchantsMap.put(map.get("merchant_id").toString(), map.get("merchant_name"));
		}
		List<Map<String, Object>> brandsInfo = brandsDao.getBrandsInfo(name);
		for (Map<String, Object> map : brandsInfo) {
			Object object = map.get("crm_id");
			String merchantName = null;
			if (null != object) {
				Integer crm_id = (Integer) object;
				merchantName = (String) merchantsMap.get(crm_id.toString());
			}
			map.put("merchant_name", merchantName);
		}
		return brandsInfo;
	}

	@Override
	public List<Integer> getCrmIds() {
		return brandsDao.getCrmIds();
	}
}
