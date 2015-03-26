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
package com.yazuo.external.account.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.minierp.service.BrandsService;
import com.yazuo.erp.syn.service.SynMerchantBrandService;
import com.yazuo.external.account.dao.MerchantDao;
import com.yazuo.external.account.service.MerchantService;
import com.yazuo.external.account.vo.MerchantVO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-8 下午3:08:02
 */
@Service
public class MerchantServiceImpl implements MerchantService {
	@Resource
	private MerchantDao merchantDao;

	@Resource
	private SynMerchantBrandService synMerchantBrandService;

	@Resource
	private BrandsService brandsService;

	/**
	 * @Title getAllMerchants
	 * @Description
	 * @return
	 * @see com.yazuo.external.account.service.MerchantService#getAllMerchants()
	 */
	@Override
	public List<Map<String, Object>> getAllMerchants() {
		return merchantDao.getAllMerchants();
	}

	@Override
	public List<Map<String, Object>> getMerchantsInfo(String merchantName) {
		List<Integer> merchantIdsList = new ArrayList<Integer>();
		List<Map<String, Object>> list = synMerchantBrandService.getSynMerchantBrandsMap(null);
		if (!CollectionUtils.isEmpty(list)) {
			for (Map<String, Object> map : list) {
				merchantIdsList.add((Integer) map.get("merchant_id"));
			}
		}
		List<Integer> crmIds = brandsService.getCrmIds();
		List<Integer> union = (List<Integer>) CollectionUtils.union(merchantIdsList, crmIds);
		
		List<Map<String, Object>> merchantsInfo = merchantDao.getMerchantsInfo(union, merchantName);
		return merchantsInfo;
	}

	@Override
	public MerchantVO getMerchantVO(Integer merchantId) {
		return merchantDao.getMerchantVO(merchantId);
	}

	/**
	 * 查询品牌拥有的门店数
	 */
	@Override
	public Long getMerchantFaceShopCount(Integer brandId) {
		return merchantDao.getMerchantFaceShopCount(brandId);
	}
}
