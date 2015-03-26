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
import com.yazuo.erp.syn.service.SynHealthDegreeService;

@Service
public class SynHealthDegreeServiceImpl implements SynHealthDegreeService {
	
	@Resource
	private SynHealthDegreeDao synHealthDegreeDao;
	
	public int saveSynHealthDegree (SynHealthDegreeVO synHealthDegree) {
		return synHealthDegreeDao.saveSynHealthDegree(synHealthDegree);
	}
	public int batchInsertSynHealthDegrees (Map<String, Object> map){
		return synHealthDegreeDao.batchInsertSynHealthDegrees(map);
	}
	public int updateSynHealthDegree (SynHealthDegreeVO synHealthDegree){
		return synHealthDegreeDao.updateSynHealthDegree(synHealthDegree);
	}
	public int batchUpdateSynHealthDegreesToDiffVals (Map<String, Object> map){
		return synHealthDegreeDao.batchUpdateSynHealthDegreesToDiffVals(map);
	}
	public int batchUpdateSynHealthDegreesToSameVals (Map<String, Object> map){
		return synHealthDegreeDao.batchUpdateSynHealthDegreesToSameVals(map);
	}
	public int deleteSynHealthDegreeById (Integer id){
		return synHealthDegreeDao.deleteSynHealthDegreeById(id);
	}
	public int batchDeleteSynHealthDegreeByIds (List<Integer> ids){
		return synHealthDegreeDao.batchDeleteSynHealthDegreeByIds(ids);
	}
	public SynHealthDegreeVO getSynHealthDegreeById(Integer id){
		return synHealthDegreeDao.getSynHealthDegreeById(id);
	}
	public List<SynHealthDegreeVO> getSynHealthDegrees (Map<String, Object> paramMap){
		return synHealthDegreeDao.getSynHealthDegrees(paramMap);
	}
	public List<Map<String, Object>>  getSynHealthDegreesMap (SynHealthDegreeVO synHealthDegree){
		return synHealthDegreeDao.getSynHealthDegreesMap(synHealthDegree);
	}
}
