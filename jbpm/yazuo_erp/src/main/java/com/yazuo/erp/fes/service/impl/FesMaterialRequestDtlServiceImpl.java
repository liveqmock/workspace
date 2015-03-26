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

package com.yazuo.erp.fes.service.impl;

import java.util.*;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.fes.service.FesMaterialRequestDtlService;

@Service
public class FesMaterialRequestDtlServiceImpl implements FesMaterialRequestDtlService {
	
	@Resource
	private FesMaterialRequestDtlDao fesMaterialRequestDtlDao;
	
	public int saveFesMaterialRequestDtl (FesMaterialRequestDtlVO fesMaterialRequestDtl) {
		return fesMaterialRequestDtlDao.saveFesMaterialRequestDtl(fesMaterialRequestDtl);
	}
	public int batchInsertFesMaterialRequestDtls (Map<String, Object> map){
		return fesMaterialRequestDtlDao.batchInsertFesMaterialRequestDtls(map);
	}
	public int updateFesMaterialRequestDtl (FesMaterialRequestDtlVO fesMaterialRequestDtl){
		return fesMaterialRequestDtlDao.updateFesMaterialRequestDtl(fesMaterialRequestDtl);
	}
	public int batchUpdateFesMaterialRequestDtlsToDiffVals (Map<String, Object> map){
		return fesMaterialRequestDtlDao.batchUpdateFesMaterialRequestDtlsToDiffVals(map);
	}
	public int batchUpdateFesMaterialRequestDtlsToSameVals (Map<String, Object> map){
		return fesMaterialRequestDtlDao.batchUpdateFesMaterialRequestDtlsToSameVals(map);
	}
	public int deleteFesMaterialRequestDtlById (Integer id){
		return fesMaterialRequestDtlDao.deleteFesMaterialRequestDtlById(id);
	}
	public int batchDeleteFesMaterialRequestDtlByIds (List<Integer> ids){
		return fesMaterialRequestDtlDao.batchDeleteFesMaterialRequestDtlByIds(ids);
	}
	public FesMaterialRequestDtlVO getFesMaterialRequestDtlById(Integer id){
		return fesMaterialRequestDtlDao.getFesMaterialRequestDtlById(id);
	}
	public List<FesMaterialRequestDtlVO> getFesMaterialRequestDtls (FesMaterialRequestDtlVO fesMaterialRequestDtl){
		return fesMaterialRequestDtlDao.getFesMaterialRequestDtls(fesMaterialRequestDtl);
	}
	public List<Map<String, Object>>  getFesMaterialRequestDtlsMap (FesMaterialRequestDtlVO fesMaterialRequestDtl){
		return fesMaterialRequestDtlDao.getFesMaterialRequestDtlsMap(fesMaterialRequestDtl);
	}
}
