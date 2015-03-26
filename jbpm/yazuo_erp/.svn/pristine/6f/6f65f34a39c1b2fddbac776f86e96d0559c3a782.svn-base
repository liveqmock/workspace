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
import com.yazuo.erp.syn.service.SynMerchantBrandService;

@Service
public class SynMerchantBrandServiceImpl implements SynMerchantBrandService {
	
	@Resource
	private SynMerchantBrandDao synMerchantBrandDao;
	
	public int saveSynMerchantBrand (SynMerchantBrandVO synMerchantBrand) {
		return synMerchantBrandDao.saveSynMerchantBrand(synMerchantBrand);
	}
	public int batchInsertSynMerchantBrands (Map<String, Object> map){
		return synMerchantBrandDao.batchInsertSynMerchantBrands(map);
	}
	public int updateSynMerchantBrand (SynMerchantBrandVO synMerchantBrand){
		return synMerchantBrandDao.updateSynMerchantBrand(synMerchantBrand);
	}
	public int batchUpdateSynMerchantBrandsToDiffVals (Map<String, Object> map){
		return synMerchantBrandDao.batchUpdateSynMerchantBrandsToDiffVals(map);
	}
	public int batchUpdateSynMerchantBrandsToSameVals (Map<String, Object> map){
		return synMerchantBrandDao.batchUpdateSynMerchantBrandsToSameVals(map);
	}
	public int deleteSynMerchantBrandById (Integer id){
		return synMerchantBrandDao.deleteSynMerchantBrandById(id);
	}
	public int batchDeleteSynMerchantBrandByIds (List<Integer> ids){
		return synMerchantBrandDao.batchDeleteSynMerchantBrandByIds(ids);
	}
	public SynMerchantBrandVO getSynMerchantBrandById(Integer id){
		return synMerchantBrandDao.getSynMerchantBrandById(id);
	}
	public List<SynMerchantBrandVO> getSynMerchantBrands (SynMerchantBrandVO synMerchantBrand){
		return synMerchantBrandDao.getSynMerchantBrands(synMerchantBrand);
	}
	public List<Map<String, Object>>  getSynMerchantBrandsMap (SynMerchantBrandVO synMerchantBrand){
		return synMerchantBrandDao.getSynMerchantBrandsMap(synMerchantBrand);
	}
}
