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

import com.yazuo.erp.interceptors.Page;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.bes.service.BesRequirementService;
import com.yazuo.erp.bes.vo.BesRequirementVO;
import com.yazuo.erp.fes.vo.FesPlanVO;
import com.yazuo.erp.system.dao.SysOperationLogDao;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class SysOperationLogServiceImpl implements SysOperationLogService {
	
	@Resource private SysOperationLogDao sysOperationLogDao;
	@Resource private SysUserDao sysUserDao;
	
	/**
	 * 工作计划添加流水
	 */
	@Override
	public int saveSysOperationLogForFesPlan (FesPlanVO fesPlan) {
		SysOperationLogVO sysOperationLog = new SysOperationLogVO();
		//添加操作人信息
		Integer insertBy = fesPlan.getInsertBy();
		SysUserVO sysUserVO = this.sysUserDao.getSysUserById(insertBy);
		String userName = sysUserVO!=null? sysUserVO.getUserName(): "";
		String description = userName + " 已完成了工作计划";
		String planItemType = fesPlan.getPlanItemType();
		String fesLogType = "";
		if(planItemType.equals("1")){
			fesLogType = "17";
		}else if(planItemType.equals("2")){
		}else if(planItemType.equals("3")){
		}else if(planItemType.equals("4")){
		}else if(planItemType.equals("5")){
			
		}
		sysOperationLog.setMerchantId(fesPlan.getMerchantId());
		sysOperationLog.setModuleType("bes");
		sysOperationLog.setFesLogType(fesLogType);
		sysOperationLog.setDescription(description);
		sysOperationLog.setOperatingTime(new Date());
		sysOperationLog.setInsertBy(insertBy);
		sysOperationLog.setInsertTime(new Date());
		return sysOperationLogDao.saveSysOperationLog(sysOperationLog);
	}
	//月报需求管理 判断电话是否接通
	public static final String connected = "connected";
	/**
	 * 月报添加流水
	 * s 
	 * [s1=description], [s2=fesLogType]
	 */
	@Override
	public int saveSysOperationLogForMonthlyReport (BesRequirementVO besRequirement, Object... array) {
		
		String description = (String)array[0];
		String fesLogType = (String)array[1];
		Integer insertBy = (Integer)array[2];
		SysOperationLogVO sysOperationLog = new SysOperationLogVO();

		if(array.length==4){
			//月报需求管理 判断电话是否接通
			Integer flag = (Integer)array[3];
			if(flag == 2){
				sysOperationLog.setRemark(connected);
			}
		}
		sysOperationLog.setMerchantId(besRequirement.getMerchantId());
		sysOperationLog.setModuleType("bes");
		sysOperationLog.setFesLogType(fesLogType);
		sysOperationLog.setDescription(description);
		sysOperationLog.setOperatingTime(new Date());
		sysOperationLog.setInsertBy(insertBy);
		sysOperationLog.setInsertTime(new Date());
		sysOperationLogDao.saveSysOperationLog(sysOperationLog);
		return sysOperationLog.getId();
	}
	@Override
	public int saveSysOperationLog (SysOperationLogVO sysOperationLog) {
		//添加操作人信息
		Integer insertBy = sysOperationLog.getInsertBy();
		SysUserVO sysUserVO = this.sysUserDao.getSysUserById(insertBy);
		String userName = sysUserVO.getUserName();
		sysOperationLog.setDescription(sysOperationLog.getDescription() + " [操作人: "+userName +"]");
		return sysOperationLogDao.saveSysOperationLog(sysOperationLog);
	}
	public int batchInsertSysOperationLogs (Map<String, Object> map){
		return sysOperationLogDao.batchInsertSysOperationLogs(map);
	}
	public int updateSysOperationLog (SysOperationLogVO sysOperationLog){
		return sysOperationLogDao.updateSysOperationLog(sysOperationLog);
	}
	public int batchUpdateSysOperationLogsToDiffVals (Map<String, Object> map){
		return sysOperationLogDao.batchUpdateSysOperationLogsToDiffVals(map);
	}
	public int batchUpdateSysOperationLogsToSameVals (Map<String, Object> map){
		return sysOperationLogDao.batchUpdateSysOperationLogsToSameVals(map);
	}
	public int deleteSysOperationLogById (Integer id){
		return sysOperationLogDao.deleteSysOperationLogById(id);
	}
	public int batchDeleteSysOperationLogByIds (List<Integer> ids){
		return sysOperationLogDao.batchDeleteSysOperationLogByIds(ids);
	}
	public SysOperationLogVO getSysOperationLogById(Integer id){
		return sysOperationLogDao.getSysOperationLogById(id);
	}
	public List<SysOperationLogVO> getSysOperationLogs (SysOperationLogVO sysOperationLog){
		//添加操作人
		Integer insertBy = sysOperationLog.getInsertBy();
		SysUserVO sysUserVO = this.sysUserDao.getSysUserById(insertBy);
		if(sysUserVO!=null){
			sysOperationLog.setUserName(sysUserVO.getUserName());
		}
		return sysOperationLogDao.getSysOperationLogs(sysOperationLog);
	}
	public List<Map<String, Object>>  getSysOperationLogsMap (SysOperationLogVO sysOperationLog){
		return sysOperationLogDao.getSysOperationLogsMap(sysOperationLog);
	}
	
	@Override
	public boolean getSysOperationByTypeAndIds(Integer[] operationLogIds, String type) {
		if(ArrayUtils.isNotEmpty(operationLogIds)){
			for (Integer operId : operationLogIds) {
				SysOperationLogVO sysOperationLogById = this.sysOperationLogDao.getSysOperationLogById(operId);
				if(sysOperationLogById.getFesLogType().equals(type)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**创建后台需求保存操作流水*/
	@Override
	public SysOperationLogVO saveOperationLogForReq(BesRequirementVO besRequirement, SysUserVO sessionUser){

		SysOperationLogVO sysOperationLog = new SysOperationLogVO();
		sysOperationLog.setMerchantId(besRequirement.getMerchantId());
		sysOperationLog.setStoreId(besRequirement.getStoreId());
		sysOperationLog.setModuleType(Constant._BES);
		sysOperationLog.setOperatingTime(new Date());
		sysOperationLog.setInsertBy(sessionUser.getId());
		sysOperationLog.setInsertTime(new Date());
		String resourceRemark = besRequirement.getResourceRemark();
		if(resourceRemark.equals("marketing_act_148")){//营销活动
			sysOperationLog.setRelatedType("1");
			sysOperationLog.setRelatedId(besRequirement.getId());
			sysOperationLog.setFesLogType("21");
			sysOperationLog.setDescription("创建了营销活动需求"+ " [操作人: "+sessionUser.getUserName() +"]");
			sysOperationLogDao.saveSysOperationLog(sysOperationLog);
			if(besRequirement.getHandlerId()!=null){
				sysOperationLog.setId(null);
				sysOperationLog.setFesLogType("22");
				sysOperationLog.setDescription("修改了处理人"+ " [操作人: "+sessionUser.getUserName() +"]");
				sysOperationLogDao.saveSysOperationLog(sysOperationLog);
			}
		}
		return sysOperationLog;
	}
	@Resource BesRequirementService besRequirementService;
	/**修改后台需求保存操作流水*/
	@Override
	public SysOperationLogVO updateOperationLogForReq(BesRequirementVO besRequirement, SysUserVO sessionUser){
		
		SysOperationLogVO sysOperationLog = new SysOperationLogVO();
		sysOperationLog.setMerchantId(besRequirement.getMerchantId());
		sysOperationLog.setStoreId(besRequirement.getStoreId());
		sysOperationLog.setModuleType(Constant._BES);
		sysOperationLog.setOperatingTime(new Date());
		sysOperationLog.setInsertBy(sessionUser.getId());
		sysOperationLog.setInsertTime(new Date());
		String resourceRemark = besRequirement.getResourceRemark();
		if(StringUtils.isNotEmpty(resourceRemark)){
			if(resourceRemark.equals("marketing_act_148")){//营销活动
				sysOperationLog.setRelatedType("1");
				sysOperationLog.setRelatedId(besRequirement.getId());
				sysOperationLog.setFesLogType("21");
				sysOperationLog.setDescription("修改了营销活动需求"+ " [操作人: "+sessionUser.getUserName() +"]");
				sysOperationLogDao.saveSysOperationLog(sysOperationLog);
				Integer handlerId = besRequirement.getHandlerId();
				if(handlerId!=null){
					if( besRequirementService.handlerChange(besRequirement.getId(), handlerId)){
						sysOperationLog.setId(null);
						sysOperationLog.setFesLogType("22");
						sysOperationLog.setDescription("修改了处理人"+ " [操作人: "+sessionUser.getUserName() +"]");
						sysOperationLogDao.saveSysOperationLog(sysOperationLog);
					}
				}
			}
		}
		return sysOperationLog;
	}

	@Override
	public Page<SysOperationLogVO> getSysOperationByTypeAndIds(List<Integer> operationLogIds, String type, Date beginTime, Date endTime) {
		return this.sysOperationLogDao.searchSysOperationByTypeAndIds(operationLogIds, type,beginTime,endTime);
	}
}
