/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.mkt.service.impl;

import java.util.*;
import com.yazuo.erp.mkt.vo.*;
import com.yazuo.erp.mkt.dao.*;

/**
 * @author erp team
 * @date 
 */
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.mkt.service.MktFormerSalesMerchantService;
import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class MktFormerSalesMerchantServiceImpl implements MktFormerSalesMerchantService {
	
	@Resource
	private MktFormerSalesMerchantDao mktFormerSalesMerchantDao;
	
	@Override
	public int saveOrUpdateMktFormerSalesMerchant (MktFormerSalesMerchantVO mktFormerSalesMerchant, SysUserVO sessionUser) {
		Integer id = mktFormerSalesMerchant.getId();
		Integer userId = sessionUser.getId();
		int row = -1;
		if(id!=null){
			mktFormerSalesMerchant.setUpdateBy(userId);
			mktFormerSalesMerchant.setUpdateTime(new Date());
			mktFormerSalesMerchant.setIsEnable(Constant.Enable);
			row = mktFormerSalesMerchantDao.updateMktFormerSalesMerchant(mktFormerSalesMerchant);
		}else{
			mktFormerSalesMerchant.setInsertBy(userId);
			mktFormerSalesMerchant.setInsertTime(new Date());
			mktFormerSalesMerchant.setUpdateBy(userId);
			mktFormerSalesMerchant.setUpdateTime(new Date());
			mktFormerSalesMerchant.setIsEnable(Constant.Enable);
			row = mktFormerSalesMerchantDao.saveMktFormerSalesMerchant(mktFormerSalesMerchant);
		}
		return row;
	}
	
	public int saveMktFormerSalesMerchant (MktFormerSalesMerchantVO mktFormerSalesMerchant) {
		return mktFormerSalesMerchantDao.saveMktFormerSalesMerchant(mktFormerSalesMerchant);
	}
	public int batchInsertMktFormerSalesMerchants (Map<String, Object> map){
		return mktFormerSalesMerchantDao.batchInsertMktFormerSalesMerchants(map);
	}
	public int updateMktFormerSalesMerchant (MktFormerSalesMerchantVO mktFormerSalesMerchant){
		return mktFormerSalesMerchantDao.updateMktFormerSalesMerchant(mktFormerSalesMerchant);
	}
	public int batchUpdateMktFormerSalesMerchantsToDiffVals (Map<String, Object> map){
		return mktFormerSalesMerchantDao.batchUpdateMktFormerSalesMerchantsToDiffVals(map);
	}
	public int batchUpdateMktFormerSalesMerchantsToSameVals (Map<String, Object> map){
		return mktFormerSalesMerchantDao.batchUpdateMktFormerSalesMerchantsToSameVals(map);
	}
	public int deleteMktFormerSalesMerchantById (Integer id){
		return mktFormerSalesMerchantDao.deleteMktFormerSalesMerchantById(id);
	}
	public int batchDeleteMktFormerSalesMerchantByIds (List<Integer> ids){
		return mktFormerSalesMerchantDao.batchDeleteMktFormerSalesMerchantByIds(ids);
	}
	public MktFormerSalesMerchantVO getMktFormerSalesMerchantById(Integer id){
		return mktFormerSalesMerchantDao.getMktFormerSalesMerchantById(id);
	}
	public List<MktFormerSalesMerchantVO> getMktFormerSalesMerchants (MktFormerSalesMerchantVO mktFormerSalesMerchant){
		return mktFormerSalesMerchantDao.getMktFormerSalesMerchants(mktFormerSalesMerchant);
	}
	public List<Map<String, Object>>  getMktFormerSalesMerchantsMap (MktFormerSalesMerchantVO mktFormerSalesMerchant){
		return mktFormerSalesMerchantDao.getMktFormerSalesMerchantsMap(mktFormerSalesMerchant);
	}
}
