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

import com.yazuo.erp.base.Constant;
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

import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.service.FesTrainPlanService;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class FesTrainPlanServiceImpl implements FesTrainPlanService {
	
	@Resource private FesTrainPlanDao fesTrainPlanDao;
	@Resource private FesOnlineProcessService fesOnlineProcessService;
	
	public FesTrainPlanVO saveFesTrainPlan (FesTrainPlanVO fesTrainPlan, SysUserVO sessionUser) {
	    Integer processId = fesTrainPlan.getProcessId();
	    FesOnlineProcessVO fesOnlineProcess = this.fesOnlineProcessService.getFesOnlineProcessById(processId);
	    String onlineProcessStatus = fesOnlineProcess.getOnlineProcessStatus();
		if(fesTrainPlan.getId()==null){
			fesTrainPlan.setInsertBy(sessionUser.getId());
			fesTrainPlan.setInsertTime(new Date());
			fesTrainPlan.setIsEnable(Constant.IS_ENABLE);
			fesTrainPlan.setUpdateBy(sessionUser.getId());
			fesTrainPlan.setUpdateTime(new Date());
		    fesTrainPlanDao.saveFesTrainPlan(fesTrainPlan);
			if(this.fesOnlineProcessService.getAttachmentListByProcess(processId, null).size()>0){
				FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
				fesOnlineProcessVO.setId(processId);
				FesOnlineProcessVO processVO = this.fesOnlineProcessService.updateStepProcessStatus(fesOnlineProcessVO, "03", sessionUser.getId());
				fesTrainPlan.setOnlineProcessStatus(processVO.getOnlineProcessStatus());
			}else{
				fesTrainPlan.setOnlineProcessStatus(onlineProcessStatus);
			}
		}else{
			fesTrainPlan.setUpdateBy(sessionUser.getId());
			fesTrainPlan.setUpdateTime(new Date());
			if(fesTrainPlan.getIsEnable()==null){
				fesTrainPlan.setIsEnable(Constant.IS_ENABLE);
			}
		    fesTrainPlanDao.updateFesTrainPlan(fesTrainPlan);
		    fesTrainPlan.setOnlineProcessStatus(onlineProcessStatus);
		}
		return fesTrainPlan;
	}
	@Override
	public Boolean haveFesTrainPlans(Integer processId){
		FesTrainPlanVO fesTrainPlan = new FesTrainPlanVO();
		fesTrainPlan.setIsEnable(Constant.IS_ENABLE);
		fesTrainPlan.setProcessId(processId);
		return this.fesTrainPlanDao.getFesTrainPlans(fesTrainPlan).size()>0;
	}
	public int batchInsertFesTrainPlans (Map<String, Object> map){
		return fesTrainPlanDao.batchInsertFesTrainPlans(map);
	}
	public int updateFesTrainPlan (FesTrainPlanVO fesTrainPlan){
		return fesTrainPlanDao.updateFesTrainPlan(fesTrainPlan);
	}
	public int batchUpdateFesTrainPlansToDiffVals (Map<String, Object> map){
		return fesTrainPlanDao.batchUpdateFesTrainPlansToDiffVals(map);
	}
	public int batchUpdateFesTrainPlansToSameVals (Map<String, Object> map){
		return fesTrainPlanDao.batchUpdateFesTrainPlansToSameVals(map);
	}
	public int deleteFesTrainPlanById (Integer id){
		return fesTrainPlanDao.deleteFesTrainPlanById(id);
	}
	public int batchDeleteFesTrainPlanByIds (List<Integer> ids){
		return fesTrainPlanDao.batchDeleteFesTrainPlanByIds(ids);
	}
	public FesTrainPlanVO getFesTrainPlanById(Integer id){
		return fesTrainPlanDao.getFesTrainPlanById(id);
	}
	public List<FesTrainPlanVO> getFesTrainPlans (FesTrainPlanVO fesTrainPlan){
		return fesTrainPlanDao.getFesTrainPlans(fesTrainPlan);
	}
	public List<Map<String, Object>>  getFesTrainPlansMap (FesTrainPlanVO fesTrainPlan){
		return fesTrainPlanDao.getFesTrainPlansMap(fesTrainPlan);
	}
}
