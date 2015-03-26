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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.req.vo.ReqRequirementVO;
import com.yazuo.erp.system.dao.SysProductOperationDao;
import com.yazuo.erp.system.dao.SysUserRequirementDao;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysProductOperationService;
import com.yazuo.erp.system.vo.SysDictionaryVO;
import com.yazuo.erp.system.vo.SysProductOperationVO;
import com.yazuo.erp.system.vo.SysUserRequirementVO;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * @Description TODO
 * @author erp team
 * @date 
 */

@Service
public class SysProductOperationServiceImpl implements SysProductOperationService {
	
	@Resource private SysProductOperationDao sysProductOperationDao;
	@Resource private SysDictionaryService sysDictionaryService;
	@Resource private SysUserRequirementDao sysUserRequirementDao;
	@Override
	public int saveOrUpdateSysProductOperation (SysProductOperationVO sysProductOperation, SysUserVO sessionUser) {
		Integer id = sysProductOperation.getId();
		Integer userId = sessionUser.getId();
		int row = -1;
		if(id!=null){
			sysProductOperation.setUpdateBy(userId);
			sysProductOperation.setUpdateTime(new Date());
			sysProductOperation.setIsEnable(Constant.Enable);
			row = sysProductOperationDao.updateSysProductOperation(sysProductOperation);
		}else{
			sysProductOperation.setInsertBy(userId);
			sysProductOperation.setInsertTime(new Date());
			sysProductOperation.setUpdateBy(userId);
			sysProductOperation.setUpdateTime(new Date());
			sysProductOperation.setIsEnable(Constant.Enable);
			row = sysProductOperationDao.saveSysProductOperation(sysProductOperation);
		}
		return row;
	}
	public int saveSysProductOperation (SysProductOperationVO sysProductOperation) {
		return sysProductOperationDao.saveSysProductOperation(sysProductOperation);
	}
	public int batchInsertSysProductOperations (Map<String, Object> map){
		return sysProductOperationDao.batchInsertSysProductOperations(map);
	}
	public int updateSysProductOperation (SysProductOperationVO sysProductOperation){
		return sysProductOperationDao.updateSysProductOperation(sysProductOperation);
	}
	public int batchUpdateSysProductOperationsToDiffVals (Map<String, Object> map){
		return sysProductOperationDao.batchUpdateSysProductOperationsToDiffVals(map);
	}
	public int batchUpdateSysProductOperationsToSameVals (Map<String, Object> map){
		return sysProductOperationDao.batchUpdateSysProductOperationsToSameVals(map);
	}
	public int deleteSysProductOperationById (Integer id){
		//first delete all cascate records.
		SysUserRequirementVO sysUserRequirement = new SysUserRequirementVO();
		sysUserRequirement.setProductOperationId(id);
		List<SysUserRequirementVO> sysUserRequirements = this.sysUserRequirementDao.getSysUserRequirements(sysUserRequirement );
		for (SysUserRequirementVO sysUserRequirementVO : sysUserRequirements) {
			this.sysUserRequirementDao.deleteSysUserRequirementById(sysUserRequirementVO.getId());
		}
		return sysProductOperationDao.deleteSysProductOperationById(id);
	}
	public int batchDeleteSysProductOperationByIds (List<Integer> ids){
		return sysProductOperationDao.batchDeleteSysProductOperationByIds(ids);
	}

	public SysProductOperationVO getSysProductOperationById(Integer id){
		SysProductOperationVO sysProductOperationVO = sysProductOperationDao.getSysProductOperationById(id); 
		//转码改为由前端处理
//		this.addContentEncode(sysProductOperationVO);
		return sysProductOperationVO;
	}
	/**
	 * 查询产品运营
	 * @param inputPromotionPlatform
	 * @param userId
	 */
	public List<SysProductOperationVO> getSysProductOperations (SysProductOperationVO sysProductOperation){
		JsonResult.getJsonStringHandleNull(sysProductOperation);
		 if(sysProductOperation.getPageNumber()!=null){
				//添加数据字典
			    List<SysProductOperationVO> sysProductOperations = sysProductOperationDao.getSysProductOperations(sysProductOperation);
				this.addDictionary(sysProductOperations);
				return sysProductOperations;
		 }else{
			 sysProductOperation.setIsOpen(Constant.Enable);
			 List<SysProductOperationVO> sysProductOperations = sysProductOperationDao.getSysProductOperations(sysProductOperation);
			 List<SysProductOperationVO> filteredSysProductOperations = new ArrayList<SysProductOperationVO>();
			 for (SysProductOperationVO sysProductOperationVO : sysProductOperations) {
				 //如果用户已经关注过此产品运营则不需要在提示
				 //如果产品运营在用户需求中不存在，则添加用户需求， 并更新产品运营需求量
				 Integer productOperId = sysProductOperationVO.getId();
				 SysUserRequirementVO sysUserRequirementVO = new SysUserRequirementVO();
				 sysUserRequirementVO.setProductOperationId(productOperId);
				 sysUserRequirementVO.setUserId(sysProductOperation.getUserId());
				 sysUserRequirementVO.setIsEnable(Constant.Enable);
				 List<SysUserRequirementVO> sysUserRequirements = this.sysUserRequirementDao.getSysUserRequirements(sysUserRequirementVO);
				 if(sysUserRequirements==null||sysUserRequirements.size()==0){
//					 this.addContentEncode(sysProductOperationVO);
					 filteredSysProductOperations.add(sysProductOperationVO);
				 }
			 }
			 //添加数据字典
			 this.addDictionary(filteredSysProductOperations);
			 return filteredSysProductOperations;
		 }
	}
	private void addContentEncode(SysProductOperationVO sysProductOperationVO) {
		//对content内容编码
		 String content = sysProductOperationVO.getContent();
		 content = URLEncoder.encode(content);
		 sysProductOperationVO.setContent(content);
	}
	/**
	 * @param filteredSysProductOperations
	 */
	private void addDictionary(List<SysProductOperationVO> filteredSysProductOperations) {
		for (SysProductOperationVO sysProductOperationVO : filteredSysProductOperations) {
			String[] productType = sysProductOperationVO.getProductType();
			List<SysDictionaryVO> dicProductTypes = new LinkedList<SysDictionaryVO>();
			for (String type : productType) {
			 SysDictionaryVO dic1 = sysDictionaryService.querySysDictionaryByTypeAndKey(Constant.DIC_PRODUCT_TYPE, type);
			 dicProductTypes.add(dic1);
			}
			sysProductOperationVO.setDicProductTypes(dicProductTypes);
			List<SysDictionaryVO> dicPlatforms = new LinkedList<SysDictionaryVO>();
			String[] platForms = sysProductOperationVO.getPromotionPlatform();
			for (String type : platForms) {
				SysDictionaryVO dic1 = sysDictionaryService.querySysDictionaryByTypeAndKey(Constant.DIC_PROMOTION_PLATFORM, type);
				dicPlatforms.add(dic1);
			}
			sysProductOperationVO.setDicPlatforms(dicPlatforms);
			if(sysProductOperationVO.getAmount()==null)
				sysProductOperationVO.setAmount(0);
		}
	}
	public List<Map<String, Object>>  getSysProductOperationsMap (SysProductOperationVO sysProductOperation){
		return sysProductOperationDao.getSysProductOperationsMap(sysProductOperation);
	}
}
