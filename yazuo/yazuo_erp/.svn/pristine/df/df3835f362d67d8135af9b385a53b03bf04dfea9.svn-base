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
import com.yazuo.erp.base.JsonResult;
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
import com.yazuo.erp.fes.service.FesOpenCardApplicationService;
import com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.StepNum;
import com.yazuo.erp.system.vo.SysUserVO;

@Service
public class FesOpenCardApplicationServiceImpl implements FesOpenCardApplicationService {
	
	@Resource
	private FesOpenCardApplicationDao fesOpenCardApplicationDao;
	@Resource
	private FesOpenCardApplicationDtlDao fesOpenCardApplicationDtlDao;
	@Resource private FesOnlineProcessService fesOnlineProcessService;
	
	public int saveFesOpenCardApplication (FesOpenCardApplicationVO fesOpenCardApplication) {
		return fesOpenCardApplicationDao.saveFesOpenCardApplication(fesOpenCardApplication);
	}
	public int batchInsertFesOpenCardApplications (Map<String, Object> map){
		return fesOpenCardApplicationDao.batchInsertFesOpenCardApplications(map);
	}
	public int updateFesOpenCardApplication (FesOpenCardApplicationVO fesOpenCardApplication){
		return fesOpenCardApplicationDao.updateFesOpenCardApplication(fesOpenCardApplication);
	}
	public int batchUpdateFesOpenCardApplicationsToDiffVals (Map<String, Object> map){
		return fesOpenCardApplicationDao.batchUpdateFesOpenCardApplicationsToDiffVals(map);
	}
	public int batchUpdateFesOpenCardApplicationsToSameVals (Map<String, Object> map){
		return fesOpenCardApplicationDao.batchUpdateFesOpenCardApplicationsToSameVals(map);
	}
	public int deleteFesOpenCardApplicationById (Integer id){
		return fesOpenCardApplicationDao.deleteFesOpenCardApplicationById(id);
	}
	public int batchDeleteFesOpenCardApplicationByIds (List<Integer> ids){
		return fesOpenCardApplicationDao.batchDeleteFesOpenCardApplicationByIds(ids);
	}
	public FesOpenCardApplicationVO getFesOpenCardApplicationById(Integer id){
		return fesOpenCardApplicationDao.getFesOpenCardApplicationById(id);
	}
	public List<FesOpenCardApplicationVO> getFesOpenCardApplications (FesOpenCardApplicationVO fesOpenCardApplication){
		return fesOpenCardApplicationDao.getFesOpenCardApplications(fesOpenCardApplication);
	}
	/**
	 * 包含明细
	 */
	@Override
	public List<FesOpenCardApplicationVO> getFesOpenCardApplicationsAndDtls (FesOpenCardApplicationVO fesOpenCardApplication){
		return fesOpenCardApplicationDao.getFesOpenCardApplicationsAndDtls(fesOpenCardApplication);
	}
	public List<Map<String, Object>>  getFesOpenCardApplicationsMap (FesOpenCardApplicationVO fesOpenCardApplication){
		return fesOpenCardApplicationDao.getFesOpenCardApplicationsMap(fesOpenCardApplication);
	}
	
	/**
	 * 1.保存申请开卡
	 * 2.生成代办事项
	 * 3.生成操作流水
	 */
	@Override
	public JsonResult saveFesOpenCardApplicationAndDtls(FesOpenCardApplicationVO fesOpenCardApplicationVO, SysUserVO user) {

//		 <!--不允许为空的字段： application_id,card_name,card_type,card_amount,insert_by,insert_time,--> 
		Integer cardId = fesOpenCardApplicationVO.getId();
		Integer userId = user.getId();
		fesOpenCardApplicationVO.setInsertBy(userId);
		fesOpenCardApplicationVO.setInsertTime(new Date());
		fesOpenCardApplicationVO.setUpdateBy(userId);
		fesOpenCardApplicationVO.setUpdateTime(new Date());
		fesOpenCardApplicationVO.setOpenCardApplicationStatus("1");
		fesOpenCardApplicationVO.setIsEnable("1");
		if(cardId!=null && cardId.intValue()!=0){
			this.updateFesOpenCardApplication(fesOpenCardApplicationVO);
		}else{
			this.saveFesOpenCardApplication(fesOpenCardApplicationVO);
		}
		List<FesOpenCardApplicationDtlVO> fesOpenCardApplicationDtlVOs = fesOpenCardApplicationVO.getFesOpenCardApplicationDtls();
		for (FesOpenCardApplicationDtlVO fesOpenCardApplicationDtlVO : fesOpenCardApplicationDtlVOs) {
			//保存实体卡明细附件关系
			fesOpenCardApplicationDtlVO.setAttachmentId(fesOpenCardApplicationDtlVO.getAttachmentId());
			fesOpenCardApplicationDtlVO.setApplicationId(fesOpenCardApplicationVO.getId());
			fesOpenCardApplicationDtlVO.setInsertBy(userId);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(FesOpenCardApplicationDtlVO.COLUMN_APPLICATION_ID, Constant.NOT_NULL);
		map.put(FesOpenCardApplicationDtlVO.COLUMN_CARD_NAME, Constant.NOT_NULL);
		map.put(FesOpenCardApplicationDtlVO.COLUMN_CARD_AMOUNT, Constant.NOT_NULL);
		map.put(FesOpenCardApplicationDtlVO.COLUMN_VALIDITY_TERM, Constant.NOT_NULL);
		map.put(FesOpenCardApplicationDtlVO.COLUMN_IS_CONTAIN_FOUR, Constant.NOT_NULL);
		map.put(FesOpenCardApplicationDtlVO.COLUMN_IS_CONTAIN_SEVEN, Constant.NOT_NULL);
		map.put(FesOpenCardApplicationDtlVO.COLUMN_INSERT_BY, Constant.NOT_NULL);
		map.put(FesOpenCardApplicationDtlVO.COLUMN_INSERT_TIME, Constant.NOT_NULL);
		map.put(FesOpenCardApplicationDtlVO.COLUMN_CARD_TAG, Constant.NOT_NULL);
		map.put(FesOpenCardApplicationDtlVO.COLUMN_MEMBER_LEVEL, Constant.NOT_NULL);
		map.put(FesOpenCardApplicationDtlVO.COLUMN_ATTACHMENT_ID, Constant.NOT_NULL);
		map.put(FesOpenCardApplicationDtlVO.COLUMN_IS_CONTAIN_SECURITY_CODE, Constant.NOT_NULL);
		map.put("list", fesOpenCardApplicationDtlVOs);
		if(cardId!=null){
			//先删除所有 然后 批量新增
			FesOpenCardApplicationDtlVO dtl = new FesOpenCardApplicationDtlVO();
			dtl.setApplicationId(cardId);
			List<FesOpenCardApplicationDtlVO> applicationDtls = fesOpenCardApplicationDtlDao.getFesOpenCardApplicationDtls(dtl);
			for (FesOpenCardApplicationDtlVO vo : applicationDtls) {
				fesOpenCardApplicationDtlDao.deleteFesOpenCardApplicationDtlById(vo.getId());
			}
		}
		this.fesOpenCardApplicationDtlDao.batchInsertFesOpenCardApplicationDtls(map);
		//调用更改状态接口生成待办事项和操作流水
		FesOnlineProcessVO fesOnlineProcess = new FesOnlineProcessVO();
		fesOnlineProcess.setId(fesOpenCardApplicationVO.getProcessId());
		fesOnlineProcess.setOnlineProcessStatus("20"); //20-待提交申请 2015-1-29 10:38:57
		return this.fesOnlineProcessService.updateFesOnlineProcessStatus(fesOnlineProcess, user);
	}
}
