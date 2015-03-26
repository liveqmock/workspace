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
import com.yazuo.erp.fes.service.FesStowageDtlService;

@Service
public class FesStowageDtlServiceImpl implements FesStowageDtlService {
	
	@Resource
	private FesStowageDtlDao fesStowageDtlDao;
	
	public int saveFesStowageDtl (FesStowageDtlVO fesStowageDtl) {
		return fesStowageDtlDao.saveFesStowageDtl(fesStowageDtl);
	}
	public int batchInsertFesStowageDtls (Map<String, Object> map){
		return fesStowageDtlDao.batchInsertFesStowageDtls(map);
	}
	public int updateFesStowageDtl (FesStowageDtlVO fesStowageDtl){
		return fesStowageDtlDao.updateFesStowageDtl(fesStowageDtl);
	}
	public int batchUpdateFesStowageDtlsToDiffVals (Map<String, Object> map){
		return fesStowageDtlDao.batchUpdateFesStowageDtlsToDiffVals(map);
	}
	public int batchUpdateFesStowageDtlsToSameVals (Map<String, Object> map){
		return fesStowageDtlDao.batchUpdateFesStowageDtlsToSameVals(map);
	}
	public int deleteFesStowageDtlById (Integer id){
		return fesStowageDtlDao.deleteFesStowageDtlById(id);
	}
	public int batchDeleteFesStowageDtlByIds (List<Integer> ids){
		return fesStowageDtlDao.batchDeleteFesStowageDtlByIds(ids);
	}
	public FesStowageDtlVO getFesStowageDtlById(Integer id){
		return fesStowageDtlDao.getFesStowageDtlById(id);
	}
	public List<FesStowageDtlVO> getFesStowageDtls (FesStowageDtlVO fesStowageDtl){
		return fesStowageDtlDao.getFesStowageDtls(fesStowageDtl);
	}
	public List<Map<String, Object>>  getFesStowageDtlsMap (FesStowageDtlVO fesStowageDtl){
		return fesStowageDtlDao.getFesStowageDtlsMap(fesStowageDtl);
	}
}
