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

package com.yazuo.erp.system.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.fes.dao.FesOnlineProcessDao;
import com.yazuo.erp.fes.dao.FesOnlineProgramDao;
import com.yazuo.erp.fes.exception.FesBizException;
import com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;
import com.yazuo.erp.fes.vo.FesOnlineProgramVO;
import com.yazuo.erp.system.dao.SysResourceDao;
import com.yazuo.erp.system.dao.SysToDoListDao;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * @author erp team
 * @date 
 */

@Service
public class SysToDoListServiceImpl implements SysToDoListService {

	private static final Log LOG = LogFactory.getLog(SysToDoListServiceImpl.class);
	@Resource
	private SysToDoListDao sysToDoListDao;
	@Resource
	private SysResourceDao sysResourceDao;
	@Resource
	private FesOnlineProgramDao fesOnlineProgramDao;
	@Resource
	private FesOnlineProcessDao fesOnlineProcessDao;
	
	public int saveSysToDoList (SysToDoListVO sysToDoList) {
		return sysToDoListDao.saveSysToDoList(sysToDoList);
	}
	public int batchInsertSysToDoLists (Map<String, Object> map){
		return sysToDoListDao.batchInsertSysToDoLists(map);
	}
	public int updateSysToDoList (SysToDoListVO sysToDoList){
		return sysToDoListDao.updateSysToDoList(sysToDoList);
	}
	public int batchUpdateSysToDoListsToDiffVals (Map<String, Object> map){
		return sysToDoListDao.batchUpdateSysToDoListsToDiffVals(map);
	}
	public int batchUpdateSysToDoListsToSameVals (Map<String, Object> map){
		return sysToDoListDao.batchUpdateSysToDoListsToSameVals(map);
	}
	public int deleteSysToDoListById (Integer id){
		return sysToDoListDao.deleteSysToDoListById(id);
	}
	public int batchDeleteSysToDoListByIds (List<Integer> ids){
		return sysToDoListDao.batchDeleteSysToDoListByIds(ids);
	}
	public SysToDoListVO getSysToDoListById(Integer id){
		return sysToDoListDao.getSysToDoListById(id);
	}
	public List<SysToDoListVO> getSysToDoLists (SysToDoListVO sysToDoList){
		return sysToDoListDao.getSysToDoLists(sysToDoList);
	}
	public List<Map<String, Object>>  getSysToDoListsMap (SysToDoListVO sysToDoList){
		return sysToDoListDao.getSysToDoListsMap(sysToDoList);
	}	
	
	/**
	 * @Description 查询代办事项 返回关联的数据
	 */
	@Override
	public List<SysToDoListVO> getComplexSysToDoLists (SysToDoListVO sysToDoList){
		return sysToDoListDao.getComplexSysToDoLists(sysToDoList);
	}
	/**
	 * @Description 完成代办事项 更改状态为已完成
	 * @param SysToDoListVO[
	 * 		merchantId: 商户ID
	        relatedId: 相关id  integer
	        relatedType: 相关类型  String]
	 */
	@Override
	public int batchUpdateCloseSysToDoLists (SysToDoListVO sysToListVO){
		sysToListVO.setItemStatus("0"); //查询状态为0待处理的
		sysToListVO.setIsEnable("1");//查询有效的
		List<SysToDoListVO> toDoList = this.getSysToDoLists(sysToListVO);
		for (SysToDoListVO toDo : toDoList) {
			toDo.setItemStatus("1");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SysToDoListVO.COLUMN_ITEM_STATUS, Constant.NOT_NULL);
		map.put(SysToDoListVO.COLUMN_UPDATE_TIME, Constant.NOT_NULL);
		map.put("list", toDoList);
		return sysToDoListDao.batchUpdateSysToDoListsToDiffVals(map);
	}
	
	/**
	 * 新增待办事项 
	 * 
	 * @param processId
	 * @param sessionUser
	 * @param sysToDoList Object parameter
	 * 
		sysToDoList.setRelatedId(relatedId); //实际关联表的ID
		sysToDoList.setRelatedType(relatedType); //关联表的类型
		sysToDoList.setPriorityLevelType("01");//一般
		sysToDoList.setItemType("01"); //上线工作
		sysToDoList.setItemContent("哈哈哈");
		sysToDoList.setVisitId("0");
		
		关联类型描述：
//	    1-上线流程  fes_online_process
//		2-实施培训回访单 
//		3-客户投诉 sys_customer_complaint
//		4-营销活动 fes_marketing_activity
 *      5-上线后回访
//		对应数据字典[00000062]"
	 */
	@Override
	public void saveToDoList(SysUserVO sessionUser, SysToDoListVO sysToDoList) {
		String relatedType = sysToDoList.getRelatedType();
		if(StringUtils.isEmpty(relatedType)) throw new FesBizException("附件相关类型不能为空");
		Integer relatedId = sysToDoList.getRelatedId();
		Integer merchantId = null;
		if(relatedType.equals("1")){ 
			FesOnlineProcessVO fesOnlineProcess = this.fesOnlineProcessDao.getFesOnlineProcessById(relatedId);
			if(fesOnlineProcess==null){
				merchantId = sysToDoList.getMerchantId();
			}else{
				FesOnlineProgramVO fesOnlineProgramVO = fesOnlineProgramDao.getFesOnlineProgramById(fesOnlineProcess.getProgramId());
				merchantId = fesOnlineProgramVO.getMerchantId();
			}
		}if(relatedType.equals("5")){//上线回访
			relatedId = null;
		}
		//查找所有需要创建待办事项的用户
		SysResourceVO sysResourceVO = new SysResourceVO();
		sysResourceVO.setRemark(sysToDoList.getResourceRemark()); //由前台传过来
		List<SysUserVO> listToDoUsers = sysResourceDao.getAllUsersByResourceCode(sysResourceVO);
		if(listToDoUsers.size()==0){
			LOG.info("创建待办事项失败，没有找到资源("+sysToDoList.getResourceRemark()+")对应的用户");
		}else{
			LOG.info("资源("+sysToDoList.getResourceRemark()+")对应的用户: "+ listToDoUsers.size() +"个");
			for (SysUserVO sysUserVO : listToDoUsers) {
				sysToDoList.setInsertBy(sessionUser.getId());
				sysToDoList.setUpdateBy(sessionUser.getId());
				sysToDoList.setInsertTime(new Date());
				sysToDoList.setUpdateTime(new Date());
				sysToDoList.setIsEnable(Constant.IS_ENABLE);
				sysToDoList.setUserId(sysUserVO.getId());
				sysToDoList.setMerchantId(merchantId);
				sysToDoList.setPriorityLevelType(sysToDoList.getPriorityLevelType());
				sysToDoList.setItemType(sysToDoList.getItemType()); //上线工作
				sysToDoList.setItemContent(sysToDoList.getItemContent());
				sysToDoList.setBusinessType(sysToDoList.getBusinessType());
				sysToDoList.setRelatedId(relatedId);
				sysToDoList.setRelatedType(relatedType);
				sysToDoList.setItemStatus("0");//待处理
				this.saveSysToDoList(sysToDoList);
			}
		}
	}
	
	/**
	 * 点击 一键上线 代办事项处理
	 * @param merchantId
	 * @param sessionUser
	 */
	@Override
	public void saveToDoListForClickOnline(Integer merchantId, SysUserVO sessionUser) {
		
		//关闭所有当前商户的代办事项
		SysToDoListVO inputSysToDoListVO = new SysToDoListVO();
		inputSysToDoListVO.setMerchantId(merchantId);
		inputSysToDoListVO.setRelatedType("1");
		int row = this.batchUpdateCloseSysToDoLists(inputSysToDoListVO);
		LOG.info("关闭了" + row + "条待办事项");

		// 生成上线后代办事项
		SysToDoListVO sysToDoList = new SysToDoListVO();
//		sysToDoList.setRelatedId(processId);
		sysToDoList.setMerchantId(merchantId);
		sysToDoList.setRelatedType("1");
		sysToDoList.setPriorityLevelType("01");// 一般
		sysToDoList.setItemType("01"); // 上线工作

		sysToDoList.setItemContent(FesOnlineProcessServiceImpl.toDoListStep10_04);
		sysToDoList.setResourceRemark(FesOnlineProcessServiceImpl.resource_end_custom_service);
		sysToDoList.setBusinessType("11");// 上线后服务[上线回访]
		sysToDoList.setItemType("04"); // 上线后服务 [特殊处理]
		this.saveToDoList(sessionUser, sysToDoList);
	}
}
