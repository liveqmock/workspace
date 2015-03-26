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
import com.yazuo.erp.req.service.ReqProjectService;

@Service
public class ReqProjectServiceImpl implements ReqProjectService {
	
	@Resource
	private ReqProjectDao reqProjectDao;
	
	public int saveReqProject (ReqProjectVO entity) {
		return reqProjectDao.saveReqProject(entity);
	}
	public int batchInsertReqProjects (Map<String, Object> map){
		return reqProjectDao.batchInsertReqProjects(map);
	}
	public int updateReqProject (ReqProjectVO entity){
		return reqProjectDao.updateReqProject(entity);
	}
	public int batchUpdateReqProjectsToDiffVals (Map<String, Object> map){
		return reqProjectDao.batchUpdateReqProjectsToDiffVals(map);
	}
	public int batchUpdateReqProjectsToSameVals (Map<String, Object> map){
		return reqProjectDao.batchUpdateReqProjectsToSameVals(map);
	}
	public int deleteReqProjectById (Integer id){
		return reqProjectDao.deleteReqProjectById(id);
	}
	public int batchDeleteReqProjectByIds (List<Integer> ids){
		return reqProjectDao.batchDeleteReqProjectByIds(ids);
	}
	public ReqProjectVO getReqProjectById(Integer id){
		return reqProjectDao.getReqProjectById(id);
	}
	public List<ReqProjectVO> getReqProjects (ReqProjectVO ReqProjectVO){
		return reqProjectDao.getReqProjects(ReqProjectVO);
	}
	public List<Map<String, Object>>  getReqProjectsMap (ReqProjectVO ReqProjectVO){
		return reqProjectDao.getReqProjectsMap(ReqProjectVO);
	}
}
