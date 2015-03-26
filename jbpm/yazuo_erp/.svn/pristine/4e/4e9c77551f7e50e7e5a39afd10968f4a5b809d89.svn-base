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
import com.yazuo.erp.fes.service.FesOnlineProgramService;

@Service
public class FesOnlineProgramServiceImpl implements FesOnlineProgramService {
	
	@Resource
	private FesOnlineProgramDao fesOnlineProgramDao;
	
	public int saveFesOnlineProgram (FesOnlineProgramVO fesOnlineProgram) {
		return fesOnlineProgramDao.saveFesOnlineProgram(fesOnlineProgram);
	}
	public int batchInsertFesOnlinePrograms (Map<String, Object> map){
		return fesOnlineProgramDao.batchInsertFesOnlinePrograms(map);
	}
	public int updateFesOnlineProgram (FesOnlineProgramVO fesOnlineProgram){
		return fesOnlineProgramDao.updateFesOnlineProgram(fesOnlineProgram);
	}
	public int batchUpdateFesOnlineProgramsToDiffVals (Map<String, Object> map){
		return fesOnlineProgramDao.batchUpdateFesOnlineProgramsToDiffVals(map);
	}
	public int batchUpdateFesOnlineProgramsToSameVals (Map<String, Object> map){
		return fesOnlineProgramDao.batchUpdateFesOnlineProgramsToSameVals(map);
	}
	public int deleteFesOnlineProgramById (Integer id){
		return fesOnlineProgramDao.deleteFesOnlineProgramById(id);
	}
	public int batchDeleteFesOnlineProgramByIds (List<Integer> ids){
		return fesOnlineProgramDao.batchDeleteFesOnlineProgramByIds(ids);
	}
	public FesOnlineProgramVO getFesOnlineProgramById(Integer id){
		return fesOnlineProgramDao.getFesOnlineProgramById(id);
	}
	public List<FesOnlineProgramVO> getFesOnlinePrograms (FesOnlineProgramVO fesOnlineProgram){
		return fesOnlineProgramDao.getFesOnlinePrograms(fesOnlineProgram);
	}
	public List<Map<String, Object>>  getFesOnlineProgramsMap (FesOnlineProgramVO fesOnlineProgram){
		return fesOnlineProgramDao.getFesOnlineProgramsMap(fesOnlineProgram);
	}
}
