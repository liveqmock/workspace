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
import com.yazuo.erp.fes.service.FesOpenCardApplicationDtlService;

@Service
public class FesOpenCardApplicationDtlServiceImpl implements FesOpenCardApplicationDtlService {
	
	@Resource
	private FesOpenCardApplicationDtlDao fesOpenCardApplicationDtlDao;
	
	public int saveFesOpenCardApplicationDtl (FesOpenCardApplicationDtlVO fesOpenCardApplicationDtl) {
		return fesOpenCardApplicationDtlDao.saveFesOpenCardApplicationDtl(fesOpenCardApplicationDtl);
	}
	public int batchInsertFesOpenCardApplicationDtls (Map<String, Object> map){
		return fesOpenCardApplicationDtlDao.batchInsertFesOpenCardApplicationDtls(map);
	}
	public int updateFesOpenCardApplicationDtl (FesOpenCardApplicationDtlVO fesOpenCardApplicationDtl){
		return fesOpenCardApplicationDtlDao.updateFesOpenCardApplicationDtl(fesOpenCardApplicationDtl);
	}
	public int batchUpdateFesOpenCardApplicationDtlsToDiffVals (Map<String, Object> map){
		return fesOpenCardApplicationDtlDao.batchUpdateFesOpenCardApplicationDtlsToDiffVals(map);
	}
	public int batchUpdateFesOpenCardApplicationDtlsToSameVals (Map<String, Object> map){
		return fesOpenCardApplicationDtlDao.batchUpdateFesOpenCardApplicationDtlsToSameVals(map);
	}
	public int deleteFesOpenCardApplicationDtlById (Integer id){
		return fesOpenCardApplicationDtlDao.deleteFesOpenCardApplicationDtlById(id);
	}
	public int batchDeleteFesOpenCardApplicationDtlByIds (List<Integer> ids){
		return fesOpenCardApplicationDtlDao.batchDeleteFesOpenCardApplicationDtlByIds(ids);
	}
	public FesOpenCardApplicationDtlVO getFesOpenCardApplicationDtlById(Integer id){
		return fesOpenCardApplicationDtlDao.getFesOpenCardApplicationDtlById(id);
	}
	public List<FesOpenCardApplicationDtlVO> getFesOpenCardApplicationDtls (FesOpenCardApplicationDtlVO fesOpenCardApplicationDtl){
		return fesOpenCardApplicationDtlDao.getFesOpenCardApplicationDtls(fesOpenCardApplicationDtl);
	}
	public List<Map<String, Object>>  getFesOpenCardApplicationDtlsMap (FesOpenCardApplicationDtlVO fesOpenCardApplicationDtl){
		return fesOpenCardApplicationDtlDao.getFesOpenCardApplicationDtlsMap(fesOpenCardApplicationDtl);
	}
}
