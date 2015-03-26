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
import com.yazuo.erp.fes.service.FesStepService;

@Service
public class FesStepServiceImpl implements FesStepService {
	
	@Resource
	private FesStepDao fesStepDao;
	
	public int saveFesStep (FesStepVO fesStep) {
		return fesStepDao.saveFesStep(fesStep);
	}
	public int batchInsertFesSteps (Map<String, Object> map){
		return fesStepDao.batchInsertFesSteps(map);
	}
	public int updateFesStep (FesStepVO fesStep){
		return fesStepDao.updateFesStep(fesStep);
	}
	public int batchUpdateFesStepsToDiffVals (Map<String, Object> map){
		return fesStepDao.batchUpdateFesStepsToDiffVals(map);
	}
	public int batchUpdateFesStepsToSameVals (Map<String, Object> map){
		return fesStepDao.batchUpdateFesStepsToSameVals(map);
	}
	public int deleteFesStepById (Integer id){
		return fesStepDao.deleteFesStepById(id);
	}
	public int batchDeleteFesStepByIds (List<Integer> ids){
		return fesStepDao.batchDeleteFesStepByIds(ids);
	}
	public FesStepVO getFesStepById(Integer id){
		return fesStepDao.getFesStepById(id);
	}
	public List<FesStepVO> getFesSteps (FesStepVO fesStep){
		return fesStepDao.getFesSteps(fesStep);
	}
	public List<Map<String, Object>>  getFesStepsMap (FesStepVO fesStep){
		return fesStepDao.getFesStepsMap(fesStep);
	}
}
