/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.system.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.erp.system.dao.SysProblemCommentsDao;
import com.yazuo.erp.system.service.SysProblemCommentsService;
import com.yazuo.erp.system.vo.SysProblemCommentsVO;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class SysProblemCommentsServiceImpl implements SysProblemCommentsService {
	
	@Resource
	private SysProblemCommentsDao sysProblemCommentsDao;
	
	@Override
	public int saveOrUpdateSysProblemComments (SysProblemCommentsVO sysProblemComments, SysUserVO sessionUser) {
		Integer id = sysProblemComments.getId();
		Integer userId = sessionUser.getId();
		int row = -1;
		if(id!=null){
			row = sysProblemCommentsDao.updateSysProblemComments(sysProblemComments);
		}else{
			sysProblemComments.setInsertBy(userId);
			sysProblemComments.setInsertTime(new Date());
			row = sysProblemCommentsDao.saveSysProblemComments(sysProblemComments);
		}
		return row;
	}
	
	public int saveSysProblemComments (SysProblemCommentsVO sysProblemComments) {
		return sysProblemCommentsDao.saveSysProblemComments(sysProblemComments);
	}
	public int batchInsertSysProblemCommentss (Map<String, Object> map){
		return sysProblemCommentsDao.batchInsertSysProblemCommentss(map);
	}
	public int updateSysProblemComments (SysProblemCommentsVO sysProblemComments){
		return sysProblemCommentsDao.updateSysProblemComments(sysProblemComments);
	}
	public int batchUpdateSysProblemCommentssToDiffVals (Map<String, Object> map){
		return sysProblemCommentsDao.batchUpdateSysProblemCommentssToDiffVals(map);
	}
	public int batchUpdateSysProblemCommentssToSameVals (Map<String, Object> map){
		return sysProblemCommentsDao.batchUpdateSysProblemCommentssToSameVals(map);
	}
	public int deleteSysProblemCommentsById (Integer id){
		return sysProblemCommentsDao.deleteSysProblemCommentsById(id);
	}
	public int batchDeleteSysProblemCommentsByIds (List<Integer> ids){
		return sysProblemCommentsDao.batchDeleteSysProblemCommentsByIds(ids);
	}
	public SysProblemCommentsVO getSysProblemCommentsById(Integer id){
		return sysProblemCommentsDao.getSysProblemCommentsById(id);
	}
	public List<SysProblemCommentsVO> getSysProblemCommentss (SysProblemCommentsVO sysProblemComments){
		return sysProblemCommentsDao.getSysProblemCommentss(sysProblemComments);
	}
	public List<Map<String, Object>>  getSysProblemCommentssMap (SysProblemCommentsVO sysProblemComments){
		return sysProblemCommentsDao.getSysProblemCommentssMap(sysProblemComments);
	}

	@Override
	public SysProblemCommentsVO saveSysProblemComments(SysProblemCommentsVO sysProblemComments, SysUserVO sessionUser) {
		
		Integer userId = sessionUser.getId();
		sysProblemComments.setInsertBy(userId);
		sysProblemComments.setInsertTime(new Date());
		sysProblemComments.setSolveredBy(userId);
		sysProblemComments.setSolvingTime(new Date());
		sysProblemCommentsDao.saveSysProblemComments(sysProblemComments);
		return sysProblemComments;
	}
}
