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

import java.util.*;

import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.syn.vo.*;
import com.yazuo.erp.syn.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.syn.service.SynMerchantProductService;

@Service
public class SynMerchantProductServiceImpl implements SynMerchantProductService {

	@Resource
	private SynMerchantProductDao synMerchantProductDao;

	public int saveSynMerchantProduct(SynMerchantProductVO synMerchantProduct) {
		return synMerchantProductDao.saveSynMerchantProduct(synMerchantProduct);
	}

	public int batchInsertSynMerchantProducts(Map<String, Object> map) {
		return synMerchantProductDao.batchInsertSynMerchantProducts(map);
	}

	public int updateSynMerchantProduct(SynMerchantProductVO synMerchantProduct) {
		return synMerchantProductDao.updateSynMerchantProduct(synMerchantProduct);
	}

	public int batchUpdateSynMerchantProductsToDiffVals(Map<String, Object> map) {
		return synMerchantProductDao.batchUpdateSynMerchantProductsToDiffVals(map);
	}

	public int batchUpdateSynMerchantProductsToSameVals(Map<String, Object> map) {
		return synMerchantProductDao.batchUpdateSynMerchantProductsToSameVals(map);
	}

	public int deleteSynMerchantProductById(Integer id) {
		return synMerchantProductDao.deleteSynMerchantProductById(id);
	}

	public int batchDeleteSynMerchantProductByIds(List<Integer> ids) {
		return synMerchantProductDao.batchDeleteSynMerchantProductByIds(ids);
	}

	public SynMerchantProductVO getSynMerchantProductById(Integer id) {
		return synMerchantProductDao.getSynMerchantProductById(id);
	}

	public List<SynMerchantProductVO> getSynMerchantProducts(SynMerchantProductVO synMerchantProduct) {
		return synMerchantProductDao.getSynMerchantProducts(synMerchantProduct);
	}

	public List<Map<String, Object>> getSynMerchantProductsMap(SynMerchantProductVO synMerchantProduct) {
		return synMerchantProductDao.getSynMerchantProductsMap(synMerchantProduct);
	}

	@Override
	public List<String> getProdectsByMerchantId(Integer merchantId) {
		return synMerchantProductDao.getProdectsByMerchantId(merchantId);
	}
}
