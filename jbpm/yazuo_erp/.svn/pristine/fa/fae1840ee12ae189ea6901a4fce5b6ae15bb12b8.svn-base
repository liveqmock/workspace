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

package com.yazuo.erp.bes.service.impl;

import java.util.*;
import com.yazuo.erp.bes.vo.*;
import com.yazuo.erp.bes.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.bes.service.BesFormerUserMerchantService;

@Service
public class BesFormerUserMerchantServiceImpl implements BesFormerUserMerchantService {
	
	@Resource
	private BesFormerUserMerchantDao besFormerUserMerchantDao;
	
	public int saveBesFormerUserMerchant (BesFormerUserMerchantVO besFormerUserMerchant) {
		return besFormerUserMerchantDao.saveBesFormerUserMerchant(besFormerUserMerchant);
	}
	public int batchInsertBesFormerUserMerchants (Map<String, Object> map){
		return besFormerUserMerchantDao.batchInsertBesFormerUserMerchants(map);
	}
	public int updateBesFormerUserMerchant (BesFormerUserMerchantVO besFormerUserMerchant){
		return besFormerUserMerchantDao.updateBesFormerUserMerchant(besFormerUserMerchant);
	}
	public int batchUpdateBesFormerUserMerchantsToDiffVals (Map<String, Object> map){
		return besFormerUserMerchantDao.batchUpdateBesFormerUserMerchantsToDiffVals(map);
	}
	public int batchUpdateBesFormerUserMerchantsToSameVals (Map<String, Object> map){
		return besFormerUserMerchantDao.batchUpdateBesFormerUserMerchantsToSameVals(map);
	}
	public int deleteBesFormerUserMerchantById (Integer id){
		return besFormerUserMerchantDao.deleteBesFormerUserMerchantById(id);
	}
	public int batchDeleteBesFormerUserMerchantByIds (List<Integer> ids){
		return besFormerUserMerchantDao.batchDeleteBesFormerUserMerchantByIds(ids);
	}
	public BesFormerUserMerchantVO getBesFormerUserMerchantById(Integer id){
		return besFormerUserMerchantDao.getBesFormerUserMerchantById(id);
	}
	public List<BesFormerUserMerchantVO> getBesFormerUserMerchants (BesFormerUserMerchantVO besFormerUserMerchant){
		return besFormerUserMerchantDao.getBesFormerUserMerchants(besFormerUserMerchant);
	}
	public List<Map<String, Object>>  getBesFormerUserMerchantsMap (BesFormerUserMerchantVO besFormerUserMerchant){
		return besFormerUserMerchantDao.getBesFormerUserMerchantsMap(besFormerUserMerchant);
	}
}
