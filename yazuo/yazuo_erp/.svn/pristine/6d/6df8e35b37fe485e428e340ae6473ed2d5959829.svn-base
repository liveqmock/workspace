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

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.system.dao.SysProductOperationDao;
import com.yazuo.erp.system.dao.SysUserRequirementDao;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysProductOperationService;
import com.yazuo.erp.system.service.SysUserRequirementService;
import com.yazuo.erp.system.vo.SysDictionaryVO;
import com.yazuo.erp.system.vo.SysProductOperationVO;
import com.yazuo.erp.system.vo.SysUserRequirementVO;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * @author erp team
 * @date 
 */

@Service
public class SysUserRequirementServiceImpl implements SysUserRequirementService {
	
	@Resource private SysUserRequirementDao sysUserRequirementDao;
	@Resource private SysProductOperationService sysProductOperationService;
	@Resource private SysDictionaryService sysDictionaryService;
	@Resource private SysProductOperationDao sysProductOperationDao;
	
	public int saveSysUserRequirement (SysUserRequirementVO sysUserRequirement, SysUserVO sessionUser) {
//		Integer userId = sessionUser==null? 1: sessionUser.getId();
		//fix bug, userId来自于不同的系统， 应该取传过来的值
		Integer userId = sysUserRequirement.getUserId();
		sysUserRequirement.setIsEnable(Constant.IS_ENABLE);
		sysUserRequirement.setInsertBy(userId);
		sysUserRequirement.setInsertTime(new Date());
		sysUserRequirement.setUpdateBy(userId);
		sysUserRequirement.setUpdateTime(new Date());
		SysProductOperationVO sysProductOperation = new SysProductOperationVO();
		sysProductOperation.setInputPromotionPlatform(sysUserRequirement.getPromotionPlatform());
		sysProductOperation.setFilterByCurrentDate("notNull"); //系统登录的时候按时间过滤
		sysProductOperation.setIsEnable(Constant.Enable);
		sysProductOperation.setIsOpen(Constant.Enable);
		//查询满足条件的产品运营
		List<SysProductOperationVO> sysProductOperations = sysProductOperationDao.getSysProductOperations(sysProductOperation);
		if(sysProductOperations!=null&&sysProductOperations.size()>0){
			for (SysProductOperationVO sysProductOperationVO : sysProductOperations) {
				//如果产品运营在用户需求中不存在，则添加用户需求， 并更新产品运营需求量
				Integer productOperId = sysProductOperationVO.getId();
				SysUserRequirementVO sysUserRequirementVO = new SysUserRequirementVO();
				sysUserRequirementVO.setProductOperationId(productOperId);
				sysUserRequirementVO.setIsEnable(Constant.Enable);
				sysUserRequirementVO.setUserId(userId);
				List<SysUserRequirementVO> sysUserRequirements = this.sysUserRequirementDao.getSysUserRequirements(sysUserRequirementVO);
				if(sysUserRequirements==null||sysUserRequirements.size()==0){
					Integer amount = sysProductOperationVO.getAmount();
					if(amount==null) amount = 0;
					sysProductOperationVO.setAmount(amount+1);
					sysProductOperationVO.setUpdateTime(new Date());
					sysProductOperationVO.setUpdateBy(userId);
					sysUserRequirement.setProductOperationId(productOperId);
					this.sysProductOperationService.updateSysProductOperation(sysProductOperationVO);
					sysUserRequirementDao.saveSysUserRequirement(sysUserRequirement);
				}
			}
		}
		return 1;
	}
	public int batchInsertSysUserRequirements (Map<String, Object> map){
		return sysUserRequirementDao.batchInsertSysUserRequirements(map);
	}
	public int updateSysUserRequirement (SysUserRequirementVO sysUserRequirement){
		return sysUserRequirementDao.updateSysUserRequirement(sysUserRequirement);
	}
	public int batchUpdateSysUserRequirementsToDiffVals (Map<String, Object> map){
		return sysUserRequirementDao.batchUpdateSysUserRequirementsToDiffVals(map);
	}
	public int batchUpdateSysUserRequirementsToSameVals (Map<String, Object> map){
		return sysUserRequirementDao.batchUpdateSysUserRequirementsToSameVals(map);
	}
	public int deleteSysUserRequirementById (Integer id){
		return sysUserRequirementDao.deleteSysUserRequirementById(id);
	}
	public int batchDeleteSysUserRequirementByIds (List<Integer> ids){
		return sysUserRequirementDao.batchDeleteSysUserRequirementByIds(ids);
	}
	public SysUserRequirementVO getSysUserRequirementById(Integer id){
		return sysUserRequirementDao.getSysUserRequirementById(id);
	}
	public List<SysUserRequirementVO> getSysUserRequirements (SysUserRequirementVO sysUserRequirement){
		 List<SysUserRequirementVO> sysUserRequirements = sysUserRequirementDao.getSysUserRequirements(sysUserRequirement);
		 for (SysUserRequirementVO sysUserRequirementVO : sysUserRequirements) {
			 String promotePlateForm = sysUserRequirementVO.getPromotionPlatform();
			 SysDictionaryVO dic1 = sysDictionaryService.querySysDictionaryByTypeAndKey(Constant.DIC_PROMOTION_PLATFORM, promotePlateForm);
			 sysUserRequirementVO.setPromotePlateFormObject(dic1);
		}
		return sysUserRequirements;
	}
	public List<Map<String, Object>>  getSysUserRequirementsMap (SysUserRequirementVO sysUserRequirement){
		return sysUserRequirementDao.getSysUserRequirementsMap(sysUserRequirement);
	}
}
