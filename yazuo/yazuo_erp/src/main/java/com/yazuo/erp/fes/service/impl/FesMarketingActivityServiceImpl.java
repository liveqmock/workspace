/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.fes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.dao.FesMarketingActivityDao;
import com.yazuo.erp.fes.service.FesMarketingActivityService;
import com.yazuo.erp.fes.vo.FesMarketingActivityVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.dao.SysOperationLogDao;
import com.yazuo.erp.system.dao.SysResourceDao;
import com.yazuo.erp.system.dao.SysToDoListDao;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * @Description TODO
 * @author erp team
 * @date 
 */

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.yazuo.erp.fes.service.FesMarketingActivityService;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.vo.SysAttachmentVO;

@Service
public class FesMarketingActivityServiceImpl implements FesMarketingActivityService {
	private static final Log LOG = LogFactory.getLog(FesMarketingActivityServiceImpl.class);
	@Resource private FesMarketingActivityDao fesMarketingActivityDao;
	@Resource private SysAttachmentService sysAttachmentService;
	@Resource
	private SysResourceDao sysResourceDao;
	@Resource
	private SysToDoListDao sysToDoListDao;
	@Resource
	private SysOperationLogDao sysOperationLogDao;
	@Resource private SysUserDao sysUserDao;
	
	public JsonResult saveFesMarketingActivity (FesMarketingActivityVO fesMarketingActivity, SysUserVO user) {
		int count = fesMarketingActivityDao.saveFesMarketingActivity(fesMarketingActivity);
		// 给有后端客服模块权限的用户发待办事项
		SysResourceVO resource = new SysResourceVO();
		resource.setRemark("end_custom_service");
		List<SysUserVO> userList = sysResourceDao.getAllUsersByResourceCode(resource);
		List<SysToDoListVO> todoList = new ArrayList<SysToDoListVO>();
		for (SysUserVO u : userList) {
			SysToDoListVO todo = new SysToDoListVO();
			todo.setUserId(u.getId());
			todo.setInsertBy(user.getId());
			todo.setUpdateBy(user.getId());
			todo.setInsertTime(new Date());
			todo.setUpdateTime(new Date());
			todo.setMerchantId(fesMarketingActivity.getMerchantId());
			todo.setPriorityLevelType("01");
			todo.setItemStatus("0");
			todo.setRelatedType("4");
			todo.setRelatedId(fesMarketingActivity.getId());
			todo.setIsEnable("1");
			// 上线后流程
			todo.setItemContent("营销活动创建");
			todo.setItemType("04");
			todo.setBusinessType("12");
			todoList.add(todo);
		}
		LOG.info("后端客服权限的人数：" + userList!=null ? userList.size() : 0);
		if (todoList !=null && todoList.size() > 0) {
			Map<String, Object> todoMap = new HashMap<String, Object>();
			todoMap.put("list", todoList);
			todoMap.put(SysToDoListVO.ALIAS_BUSINESS_TYPE,  Constant.NOT_NULL);
			int num1 = sysToDoListDao.batchInsertSysToDoLists(todoMap); // 批量添加待办事项
			LOG.info("添加待办事项返回值："+num1);
		}
		// 添加流水
		SysOperationLogVO operateLog = new SysOperationLogVO();
		operateLog.setInsertBy(user.getId());
		operateLog.setInsertTime(new Date());
		operateLog.setFesLogType("16");
		operateLog.setMerchantId(fesMarketingActivity.getMerchantId());
		operateLog.setModuleType("fes");
		operateLog.setRemark(fesMarketingActivity.getId()+"");
		operateLog.setOperatingTime(new Date());
		//添加操作人信息
		SysUserVO sysUserVO = this.sysUserDao.getSysUserById(user.getId());
		String userName = sysUserVO.getUserName();
		operateLog.setDescription("营销活动创建"+ " [操作人: "+userName +"]");
				
		int num = sysOperationLogDao.saveSysOperationLog(operateLog);
		LOG.info("添加日志信息返回值："+num);
		return new JsonResult(true).setMessage(count>0 ? "保存成功!":"保存失败!");
	}
	public int batchInsertFesMarketingActivitys (Map<String, Object> map){
		return fesMarketingActivityDao.batchInsertFesMarketingActivitys(map);
	}
	public int updateFesMarketingActivity (FesMarketingActivityVO fesMarketingActivity){
		return fesMarketingActivityDao.updateFesMarketingActivity(fesMarketingActivity);
	}
	public int batchUpdateFesMarketingActivitysToDiffVals (Map<String, Object> map){
		return fesMarketingActivityDao.batchUpdateFesMarketingActivitysToDiffVals(map);
	}
	public int batchUpdateFesMarketingActivitysToSameVals (Map<String, Object> map){
		return fesMarketingActivityDao.batchUpdateFesMarketingActivitysToSameVals(map);
	}
	public int deleteFesMarketingActivityById (Integer id){
		return fesMarketingActivityDao.deleteFesMarketingActivityById(id);
	}
	public int batchDeleteFesMarketingActivityByIds (List<Integer> ids){
		return fesMarketingActivityDao.batchDeleteFesMarketingActivityByIds(ids);
	}
	public FesMarketingActivityVO getFesMarketingActivityById(Integer id){
		return fesMarketingActivityDao.getFesMarketingActivityById(id);
	}

	@Value("${activityFileUrl}")
	private String activityFileUrl; // 营销活动附件路径
	/**
	 * 查找营销活动和附件信息
	 */
	public List<FesMarketingActivityVO> getFesMarketingActivitys (FesMarketingActivityVO fesMarketingActivity){
		List<FesMarketingActivityVO> fesMarketingActivityVOs =  fesMarketingActivityDao.getFesMarketingActivitys(fesMarketingActivity);
		for (FesMarketingActivityVO fesMarketingActivityVO : fesMarketingActivityVOs) {
			SysAttachmentVO sysAttachmentVO = this.sysAttachmentService.getSysAttachmentById(fesMarketingActivityVO.getAttachmentId());
			if(sysAttachmentVO!=null){
				String fileFullPath = activityFileUrl +"/"+ sysAttachmentVO.getAttachmentName();
				sysAttachmentVO.setFileFullPath(fileFullPath);
			}
			fesMarketingActivityVO.setSysAttachmentVO(sysAttachmentVO);
		}
		return fesMarketingActivityVOs;
	}
	public List<Map<String, Object>>  getFesMarketingActivitysMap (FesMarketingActivityVO fesMarketingActivity){
		return fesMarketingActivityDao.getFesMarketingActivitysMap(fesMarketingActivity);
	}
	@Override
	public Page<FesMarketingActivityVO> getActivityByOrder(Map<String, Object> paramMap) {
		return fesMarketingActivityDao.getActivityByOrder(paramMap);
	}
	
	
}
