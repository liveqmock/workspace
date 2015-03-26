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

package com.yazuo.erp.system.service.impl;

import java.util.*;

import com.yazuo.erp.fes.exception.FesBizException;
import com.yazuo.erp.system.vo.*;
import com.yazuo.erp.system.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.junit.runners.Parameterized.Parameters;
import org.springframework.stereotype.Service;
import com.yazuo.erp.system.service.SysAssistantMerchantService;

@Service
public class SysAssistantMerchantServiceImpl implements SysAssistantMerchantService {
	
	@Resource
	private SysAssistantMerchantDao sysAssistantMerchantDao;
	

	/**
	 * 保存或修改客服商户关系
	 * @param "{merchantId, userId}"
	 */
	@Override
	public int saveOrUpdateSysAssistantMerchant (SysAssistantMerchantVO sysAssistantMerchant, SysUserVO sessionUser) {
		int row = 0;
		List<SysAssistantMerchantVO> sysAssistantMerchants = this.sysAssistantMerchantDao.getSysAssistantMerchants(sysAssistantMerchant);
		if(sysAssistantMerchants.size()>1){
			throw new FesBizException("同一个商户和用户不能有多条");
		}else if(sysAssistantMerchants.size()==1){ //修改客服助理
			SysAssistantMerchantVO sysAssistantMerchantVO = sysAssistantMerchants.get(0);
			sysAssistantMerchantVO.setUserId(sysAssistantMerchant.getNewUserId());
			row = this.sysAssistantMerchantDao.updateSysAssistantMerchant(sysAssistantMerchantVO);
		}else{ //新增客服助理
			sysAssistantMerchant.setUserId(sysAssistantMerchant.getNewUserId());
			row = this.saveSysAssistantMerchant(sysAssistantMerchant, sessionUser);
		}
		return row;
	}
	
	public int saveSysAssistantMerchant (SysAssistantMerchantVO sysAssistantMerchant, SysUserVO sessionUser) {
		sysAssistantMerchant.setInsertBy(sessionUser.getId());
		sysAssistantMerchant.setInsertTime(new Date());
		return sysAssistantMerchantDao.saveSysAssistantMerchant(sysAssistantMerchant);
	}
	public int batchInsertSysAssistantMerchants (Map<String, Object> map){
		return sysAssistantMerchantDao.batchInsertSysAssistantMerchants(map);
	}
	public int updateSysAssistantMerchant (SysAssistantMerchantVO sysAssistantMerchant){
		return sysAssistantMerchantDao.updateSysAssistantMerchant(sysAssistantMerchant);
	}
	public int batchUpdateSysAssistantMerchantsToDiffVals (Map<String, Object> map){
		return sysAssistantMerchantDao.batchUpdateSysAssistantMerchantsToDiffVals(map);
	}
	public int batchUpdateSysAssistantMerchantsToSameVals (Map<String, Object> map){
		return sysAssistantMerchantDao.batchUpdateSysAssistantMerchantsToSameVals(map);
	}
	public int deleteSysAssistantMerchantById (Integer id){
		return sysAssistantMerchantDao.deleteSysAssistantMerchantById(id);
	}
	public int batchDeleteSysAssistantMerchantByIds (List<Integer> ids){
		return sysAssistantMerchantDao.batchDeleteSysAssistantMerchantByIds(ids);
	}
	public SysAssistantMerchantVO getSysAssistantMerchantById(Integer id){
		return sysAssistantMerchantDao.getSysAssistantMerchantById(id);
	}
	public List<SysAssistantMerchantVO> getSysAssistantMerchants (SysAssistantMerchantVO sysAssistantMerchant){
		return sysAssistantMerchantDao.getSysAssistantMerchants(sysAssistantMerchant);
	}
	public List<Map<String, Object>>  getSysAssistantMerchantsMap (SysAssistantMerchantVO sysAssistantMerchant){
		return sysAssistantMerchantDao.getSysAssistantMerchantsMap(sysAssistantMerchant);
	}
}
