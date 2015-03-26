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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.external.account.dao.MerchantDao;
import com.yazuo.external.account.dao.MerchantProductDao;
import com.yazuo.external.account.service.MerchantProductService;
import com.yazuo.external.account.vo.MerchantProductVO;

/**
 * @Description TODO
 * @author zhaohuaqin
 * @date 2014-8-6 下午1:51:15
 */
@Service
public class MerchantProductServiceImpl implements MerchantProductService {

	@Resource
	private MerchantProductDao merchantProductDao;

	@Resource
	private MerchantDao merchantDao;

	@Override
	public List<String> getProductByMerchantId(Integer merchantId) {
		return merchantProductDao.getProductsByMerchantId(merchantId);
	}

	@Override
	public Map<Integer, Object> getProductsAllMerchant() {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		List<String> productList;
		// 1、查询所有品牌
		List<Integer> brandIdsList = merchantDao.getAllBrandId();

		// 2、所有品牌开通的产品
		List<MerchantProductVO> merchantProductList = merchantProductDao.getProductsAllMerchant(brandIdsList);
		for (Integer brandId : brandIdsList) {
			productList = new ArrayList<String>();
			for (MerchantProductVO merchantProductVO : merchantProductList) {

				if (brandId.intValue() == merchantProductVO.getMerchantId().intValue()) {
					if (brandId.intValue() == 0) {
						if ((merchantProductVO.getProductId().equals(602)) || (merchantProductVO.getProductId().equals(33))
								|| (merchantProductVO.getProductId().equals(37))) {
							productList.add(merchantProductVO.getProductName());
						}
					} else {
						productList.add(merchantProductVO.getProductName());
					}
				}
			}
			map.put(brandId, productList);
		}
		return map;
	}
}
