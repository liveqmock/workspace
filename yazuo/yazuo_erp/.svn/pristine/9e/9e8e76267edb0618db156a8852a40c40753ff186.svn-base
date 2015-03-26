/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.bes.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.bes.vo.BesCallRecordVO;
import com.yazuo.erp.bes.vo.BesRequirementVO;
import com.yazuo.erp.fes.vo.FesPlanVO;
import com.yazuo.erp.system.vo.SysDocumentVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @author erp team
 * @date 
 */
public interface BesRequirementService{

	/**
	 * 保存或修改
	 */
	BesRequirementVO saveOrUpdateBesRequirement (BesRequirementVO besRequirement, SysUserVO sessionUser);
   /**
	 * 新增对象 @return : 新增加的主键id
	 */
	int saveBesRequirement (BesRequirementVO besRequirement);
	/**
	 * 新增多个对象 @return : 
	 */
	int batchInsertBesRequirements (Map<String, Object> map);
	/**
	 * 修改对象 @return : 影响的行数
	 */
	int updateBesRequirement (BesRequirementVO besRequirement);
	/**
	 * 修改多个对象（每一条记录可以不同） @return : 影响的行数
	 */
	int batchUpdateBesRequirementsToDiffVals (Map<String, Object> map);
	/**
	 * 修改多个对象（每一条记录都相同） @return : 影响的行数
	 */
	int batchUpdateBesRequirementsToSameVals (Map<String, Object> map);
	/**
	 * 按ID删除对象
	 */
	int deleteBesRequirementById (Integer id);
	/**
	 * 按IDs删除多个对象
	 */
	int batchDeleteBesRequirementByIds (List<Integer> ids);
	/**
	 * 通过主键查找对象
	 */
	BesRequirementVO getBesRequirementById(Integer id);
	/**
	 * 返回所有返回所有满足条件的Object对象的List
	 */
	List<BesRequirementVO> getBesRequirements (BesRequirementVO besRequirement, SysUserVO sessionUser);
	/**
	 * 返回所有返回所有满足条件的Map对象的List
	 */
	List<Map<String, Object>>  getBesRequirementsMap (BesRequirementVO besRequirement);
	BesRequirementVO getBesRequirementById(Integer id, SysUserVO sessionUser);
	List<Map<String, String>> getMonthlyFesPlan(String realPath, BesRequirementVO besRequirementVO);

    List<FesPlanVO> getFesPlanForReq(Integer merchantId, Date time);

    /**
	 * 回访电话未接通
	 */
	List<Map<String, Object>> saveVisteFailTelCall(BesRequirementVO besRequirement, SysUserVO sessionUser);
	List<FesPlanVO> getFesPlanForReq(Integer merchantId);
	List<Map<String, Object>> savePaperAndCallRecord(BesCallRecordVO besCallRecord, SysDocumentVO sysDocument, SysUserVO sessionUser,
			HttpServletRequest request);
	Map<String, Object> getContactMap(Integer besReqId);
	Map<String, Object> encapsulateChangeContact(Integer merchantId, Map<String, Object> contactMap);

	List<Map<String, Object>> getContactListsOfReq(BesRequirementVO besRequirementVO);
    void saveCreateBesReq(FesPlanVO fesPlanVO);
	JsonResult uploadFile(MultipartFile myfile, String basePath, String dir, SysUserVO sessionUser);

	boolean handlerChange(Integer reqId, Integer handlerId);
	void saveReqRemindForTimer();
	/**
	 * 批量完成或放弃
     * @param requirementIds
     * @param userVO
     * @param content
     * @param abandoned
     */
    void batchSaveVisitFailTelCall(List<Integer> requirementIds, SysUserVO userVO, String content, boolean abandoned);
}
