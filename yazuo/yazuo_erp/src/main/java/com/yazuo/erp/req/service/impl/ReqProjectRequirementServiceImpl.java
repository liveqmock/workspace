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

package com.yazuo.erp.req.service.impl;

import java.util.*;
import com.yazuo.erp.req.vo.*;
import com.yazuo.erp.req.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.req.service.ReqProjectRequirementService;

@Service
public class ReqProjectRequirementServiceImpl implements ReqProjectRequirementService {
	
	@Resource
	private ReqProjectRequirementDao reqProjectRequirementDao;
	
	public int saveReqProjectRequirement (ReqProjectRequirementVO reqProjectRequirement) {
		return reqProjectRequirementDao.saveReqProjectRequirement(reqProjectRequirement);
	}
	public int batchInsertReqProjectRequirements (Map<String, Object> map){
		return reqProjectRequirementDao.batchInsertReqProjectRequirements(map);
	}
	public int updateReqProjectRequirement (ReqProjectRequirementVO entity){
		return reqProjectRequirementDao.updateReqProjectRequirement(entity);
	}
	public int batchUpdateReqProjectRequirementsToDiffVals (Map<String, Object> map){
		return reqProjectRequirementDao.batchUpdateReqProjectRequirementsToDiffVals(map);
	}
	public int batchUpdateReqProjectRequirementsToSameVals (Map<String, Object> map){
		return reqProjectRequirementDao.batchUpdateReqProjectRequirementsToSameVals(map);
	}
	public int deleteReqProjectRequirementById (Integer id){
		return reqProjectRequirementDao.deleteReqProjectRequirementById(id);
	}
	public int batchDeleteReqProjectRequirementByIds (List<Integer> ids){
		return reqProjectRequirementDao.batchDeleteReqProjectRequirementByIds(ids);
	}
	public ReqProjectRequirementVO getReqProjectRequirementById(Integer id){
		return reqProjectRequirementDao.getReqProjectRequirementById(id);
	}
	public List<ReqProjectRequirementVO> getReqProjectRequirements (ReqProjectRequirementVO reqProjectRequirement){
		return reqProjectRequirementDao.getReqProjectRequirements(reqProjectRequirement);
	}
	public List<Map<String, Object>>  getReqProjectRequirementsMap (ReqProjectRequirementVO reqProjectRequirement){
		return reqProjectRequirementDao.getReqProjectRequirementsMap(reqProjectRequirement);
	}
}
