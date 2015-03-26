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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.req.dao.ReqProjectDao;
import com.yazuo.erp.req.dao.ReqProjectRequirementDao;
import com.yazuo.erp.req.dao.ReqRequirementDao;
import com.yazuo.erp.req.exception.ReqBizException;
import com.yazuo.erp.req.service.ReqRequirementService;
import com.yazuo.erp.req.vo.ReqProjectRequirementVO;
import com.yazuo.erp.req.vo.ReqProjectVO;
import com.yazuo.erp.req.vo.ReqRequirementVO;
import com.yazuo.erp.system.dao.SysDictionaryDao;
import com.yazuo.erp.system.service.SysDictionaryService;
/**
 * @Description TODO
 * @author erp team
 * @date 
 */

@Service
public class ReqRequirementServiceImpl implements ReqRequirementService {
	
	@Resource
	private ReqRequirementDao reqRequirementDao;
	@Resource
	private ReqProjectDao reqProjectDao;
	@Resource
	private ReqProjectRequirementDao reqProjectRequirementDao;
	@Resource
	private SysDictionaryService sysDictionaryService;
	@Resource
	private SysDictionaryDao sysDictionary;
	
	public int saveReqRequirement (ReqRequirementVO requirementVO) {
		requirementVO.setIsEnable("1");
		Integer requirementId = requirementVO.getId();
		if(requirementId==null){
			//save correct 'ReqRequirementVO' entity.
			  requirementVO.setInsertTime(new Date());
			  reqRequirementDao.saveReqRequirement(requirementVO);
			  requirementId = requirementVO.getId();
		}else{
			if(reqRequirementDao.getReqRequirementById(requirementId)==null){
				throw new ReqBizException("不存在要更改的对象", new RuntimeException());
			}
			reqRequirementDao.updateReqRequirement(requirementVO);
			//delete all related tables.
			reqProjectRequirementDao.batchDeleteReqProjectRequirementByReq(requirementId);
		}
		//2. save all the selected projects to relaction table.
		List<String> projectIds = requirementVO.getProjectIds();
		int reqProjectRequirements = 0 ;
		if(projectIds!=null&&projectIds.size()>0){
			List<ReqProjectRequirementVO> listReqProjectRequirementVO = new ArrayList<ReqProjectRequirementVO>();
			for (String projectId : projectIds) {
				ReqProjectRequirementVO reqProjectRequirementVO = new ReqProjectRequirementVO();
				reqProjectRequirementVO.setProjectId(Integer.parseInt(projectId));
				reqProjectRequirementVO.setRequirementId(requirementId);
				reqProjectRequirementVO.setInsertBy(requirementVO.getInsertBy());
				listReqProjectRequirementVO.add(reqProjectRequirementVO);
			}
			Map<String, Object> mapReqProjectRequirementVO = new HashMap<String, Object>();
			mapReqProjectRequirementVO.put(ReqProjectRequirementVO.COLUMN_PROJECT_ID, Constant.NOT_NULL);
			mapReqProjectRequirementVO.put(ReqProjectRequirementVO.COLUMN_REQUIREMENT_ID, Constant.NOT_NULL);
			mapReqProjectRequirementVO.put(ReqProjectRequirementVO.COLUMN_INSERT_BY, Constant.NOT_NULL);
			mapReqProjectRequirementVO.put(ReqProjectRequirementVO.COLUMN_INSERT_TIME, Constant.NOT_NULL);
			mapReqProjectRequirementVO.put("list", listReqProjectRequirementVO);
		    reqProjectRequirements = reqProjectRequirementDao.batchInsertReqProjectRequirements(mapReqProjectRequirementVO);
		}
		return reqProjectRequirements;
	}
	public List<Map<String, Object>> getAllProjects(){
		List<ReqProjectVO> list = this.reqProjectDao.getReqProjects(null);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (ReqProjectVO reqProjectVO : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("value", reqProjectVO.getId());
			map.put("text", reqProjectVO.getName());
			resultList.add(map);
		}
		return resultList;
	}
	public int batchInsertReqRequirements (Map<String, Object> map){
		return reqRequirementDao.batchInsertReqRequirements(map);
	}
	public int updateReqRequirement (ReqRequirementVO entity){
		return reqRequirementDao.updateReqRequirement(entity);
	}
	public int batchUpdateReqRequirementsToDiffVals (Map<String, Object> map){
		return reqRequirementDao.batchUpdateReqRequirementsToDiffVals(map);
	}
	public int batchUpdateReqRequirementsToSameVals (Map<String, Object> map){
		return reqRequirementDao.batchUpdateReqRequirementsToSameVals(map);
	}
	public int deleteReqRequirementById (Integer requirementId){
		//first delete the rel-table.
		this.reqProjectRequirementDao.batchDeleteReqProjectRequirementByReq(requirementId);
		return reqRequirementDao.deleteReqRequirementById(requirementId);
	}
	public int batchDeleteReqRequirementByIds (List<Integer> ids){
		return reqRequirementDao.batchDeleteReqRequirementByIds(ids);
	}
	public ReqRequirementVO getReqRequirementById(Integer id){
		return reqRequirementDao.getReqRequirementById(id);
	}
	public List<ReqRequirementVO> getReqRequirements (ReqRequirementVO ReqRequirementVO){
		return reqRequirementDao.getReqRequirements(ReqRequirementVO);
	}
	public List<Map<String, Object>>  getReqRequirementsMap (ReqRequirementVO ReqRequirementVO){
		return reqRequirementDao.getReqRequirementsMap(ReqRequirementVO);
	}
	@Override
	public Page<ReqRequirementVO> getComplexRequirementByJoin(ReqRequirementVO reqRequirementVO) {
		List projectIds = reqRequirementVO.getProjectIds();
		if(projectIds==null||projectIds.size()==0){
			reqRequirementVO.setProjectIds(null);
		}
		PageHelper.startPage(reqRequirementVO.getPageNumber(), reqRequirementVO.getPageSize(), true, true);
		return (Page<ReqRequirementVO>) reqRequirementDao.getComplexRequirementByJoin(reqRequirementVO);
	}
	/**
	 * 查询条件状态下拉框中的延期，是系统自动筛出来超过预计完成时间还未完成的需求
	 */
	@Override
	public int updateRequirementStatusIfOverTime() {
		//按当前日期和状态为延期的条件查询所有需求数据
		ReqRequirementVO reqRequirementVO = new ReqRequirementVO();
		reqRequirementVO.setRequirementStatus("8");//延期
		reqRequirementVO.setPlanEndTime(new Date()); //当前日期
	    this.reqRequirementDao.updateRequirementStatusIfOverTime(reqRequirementVO);
	    return 1;
	}
}
