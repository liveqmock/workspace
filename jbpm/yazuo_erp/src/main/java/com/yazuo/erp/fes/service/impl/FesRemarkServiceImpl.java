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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.dao.FesOnlineProcessDao;
import com.yazuo.erp.fes.dao.FesRemarkDao;
import com.yazuo.erp.fes.service.FesRemarkService;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;
import com.yazuo.erp.fes.vo.FesRemarkVO;
import com.yazuo.erp.system.dao.SysOperationLogDao;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * @Description TODO
 * @author erp team
 * @date 
 */

@Service
public class FesRemarkServiceImpl implements FesRemarkService {
	
	@Resource
	private FesRemarkDao fesRemarkDao;
	@Resource
	private SysOperationLogDao sysOperationLogDao;
	@Resource	private FesOnlineProcessDao fesOnlineProcessDao;
	@Resource	private SysUserDao sysUserDao;
	
	public FesRemarkVO saveFesRemark (FesRemarkVO fesRemark, SysUserVO sessionUser) {
		if(fesRemark.getId()==null){
			fesRemark.setInsertBy(sessionUser.getId());
			fesRemark.setInsertTime(new Date());
			fesRemark.setUpdateBy(sessionUser.getId());
			fesRemark.setUpdateTime(new Date());
			
			Integer insertBy = sessionUser.getId();
			SysUserVO sysUserVO = this.sysUserDao.getSysUserById(insertBy);
			String userName = sysUserVO.getUserName();
			fesRemark.setRemark(fesRemark.getRemark()+ " [操作人: "+userName +"]");
			
			fesRemarkDao.saveFesRemark(fesRemark);
		}else{
			fesRemark.setUpdateBy(sessionUser.getId());
			fesRemark.setUpdateTime(new Date());
			fesRemarkDao.updateFesRemark(fesRemark);
		}
		return fesRemark;
	}
	public int batchInsertFesRemarks (Map<String, Object> map){
		return fesRemarkDao.batchInsertFesRemarks(map);
	}
	public int updateFesRemark (FesRemarkVO fesRemark){
		return fesRemarkDao.updateFesRemark(fesRemark);
	}
	public int batchUpdateFesRemarksToDiffVals (Map<String, Object> map){
		return fesRemarkDao.batchUpdateFesRemarksToDiffVals(map);
	}
	public int batchUpdateFesRemarksToSameVals (Map<String, Object> map){
		return fesRemarkDao.batchUpdateFesRemarksToSameVals(map);
	}
	public JsonResult deleteFesRemarkById (FesRemarkVO remark ,SysUserVO user){
	
		int count = fesRemarkDao.deleteFesRemarkById(remark.getId()); //删除
		String onlineStatus = "";

		if (count > 0) {
			// 判断是否第一次添加备注
			FesRemarkVO remarkVo = new FesRemarkVO();
			remarkVo.setProcessId(remark.getProcessId());
			List<FesRemarkVO> remarkList = fesRemarkDao.getFesRemarks(remarkVo); // 该流程的微信备注
			
			FesOnlineProcessVO online = fesOnlineProcessDao.getFesOnlineProcessById(remark.getProcessId()); 
			if (remarkList ==null || remarkList.size() ==0) { // 流程状态更新
				online = new FesOnlineProcessVO();
				online.setId(remark.getProcessId());
				online.setOnlineProcessStatus("02");
				fesOnlineProcessDao.updateFesOnlineProcess(online);
			}
			onlineStatus = online.getOnlineProcessStatus();
		}
		return new JsonResult(count > 0).setMessage(count>0?"删除成功" : "删除失败").putData("onlineProcessStatus", onlineStatus);
	}
	
	public int batchDeleteFesRemarkByIds (List<Integer> ids){
		return fesRemarkDao.batchDeleteFesRemarkByIds(ids);
	}
	public FesRemarkVO getFesRemarkById(Integer id){
		return fesRemarkDao.getFesRemarkById(id);
	}
	public List<FesRemarkVO> getFesRemarks (FesRemarkVO fesRemark){
		return fesRemarkDao.getFesRemarks(fesRemark);
	}
	public List<Map<String, Object>>  getFesRemarksMap (FesRemarkVO fesRemark){
		return fesRemarkDao.getFesRemarksMap(fesRemark);
	}
	
	@Override
	public JsonResult saveAndUpdateRemarkAddOther(FesRemarkVO fesRemarkVO, SysUserVO user) {
		// 判断是否第一次添加备注
		FesRemarkVO remarkVo = new FesRemarkVO();
		remarkVo.setProcessId(fesRemarkVO.getProcessId());
		List<FesRemarkVO> remarkList = fesRemarkDao.getFesRemarks(remarkVo); // 该流程的微信备注

		String onlineStatus = "";
		
		FesRemarkVO remark = this.saveFesRemark(fesRemarkVO, user); // 添加或修改
		
		FesOnlineProcessVO online = fesOnlineProcessDao.getFesOnlineProcessById(remark.getProcessId()); 
		if (remarkList ==null || remarkList.size() ==0) { // 流程状态更新
			online = new FesOnlineProcessVO();
			online.setId(remark.getProcessId());
			online.setOnlineProcessStatus("03");
			fesOnlineProcessDao.updateFesOnlineProcess(online);
		}
		onlineStatus = online.getOnlineProcessStatus();
		return new JsonResult(true).setMessage("保存成功!").putData("onlineProcessStatus", onlineStatus);
	}
}
