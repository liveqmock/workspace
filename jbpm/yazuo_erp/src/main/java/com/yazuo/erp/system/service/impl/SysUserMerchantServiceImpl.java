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

import static com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.resource_end_custom_service;
import static com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.resource_end_delivery_service;
import static com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.resource_fes_card_and_materials;
import static com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.toDoListStep2_init;
import static com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.toDoListStep6_init;
import static com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.toDoListStep8_init;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.yazuo.erp.fes.dao.FesOnlineProcessDao;
import com.yazuo.erp.fes.exception.FesBizException;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.service.FesOnlineProgramService;
import com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl;
import com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.StepNum;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;
import com.yazuo.erp.syn.dao.SynMerchantDao;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.dao.SysUserMerchantDao;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.service.SysUserMerchantService;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * @Description 分配负责人相关 
 * @author erp team
 * @date 
 */

@Service
public class SysUserMerchantServiceImpl implements SysUserMerchantService {

	private static final Log LOG = LogFactory.getLog(SysUserMerchantServiceImpl.class);
	@Resource
	private SysUserMerchantDao sysUserMerchantDao;
	@Resource
	private FesOnlineProcessService fesOnlineProcessService;
	@Resource
	private SysToDoListService sysToDoListService;
	@Resource
	private FesOnlineProgramService fesOnlineProgramService;
	@Resource
	private FesOnlineProcessDao fesOnlineProcessDao;
	@Resource
	private SynMerchantDao synMerchantDao;
	@Resource
	private SysUserService sysUserService;

	/**
	 * @Description 分配负责人
	 * @param private java.lang.Integer userId; //"用户ID"; not null private
	 *        java.lang.Integer merchantId; //"商户ID"; not null 0. 保存负责人商户关系 1.
	 *        创建上线计划 3. 创建上线流程 4. 创建待办事项
	 * @return row ：新增返回主键id, 修改返回影响的行数
	 */
	public int saveSysUserMerchant(SysUserMerchantVO sysUserMerchant, SysUserVO sessionUser) {
		Integer userMerchantId = getUserMerchantId(sysUserMerchant);
		int row = 0;
		if (userMerchantId == null) {
			sysUserMerchant.setInsertBy(sessionUser.getId());
			sysUserMerchant.setInsertTime(new Date());
			sysUserMerchantDao.saveSysUserMerchant(sysUserMerchant);
			row = sysUserMerchant.getId();
			Integer merchantId = sysUserMerchant.getMerchantId();
			fesOnlineProcessService.batchInsertFesOnlineProcesss(merchantId, sessionUser);
			FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
			fesOnlineProcessVO.setMerchantId(merchantId);
			List<FesOnlineProcessVO> complexFesOnlineProcesss = this.fesOnlineProcessService
					.getComplexFesOnlineProcesss(fesOnlineProcessVO);
			/*
			 * 商户信息同步到ERP时，当分配了服务人员后，即时创建的待办事项 制卡/物料 
			 * 1、实体卡制作，请尽快安排。
			 * 2、物料设计，请尽快安排。 后端客服 1、微信申请，请尽快安排。 设备配送 
			 * 3、设配配送，请尽快安排。
			 */
			this.createToDoListForAssignUser(sessionUser, complexFesOnlineProcesss);				
			//更改商户状态为 '未上线'
			SynMerchantVO synMerchant = new SynMerchantVO();
			synMerchant.setMerchantId(merchantId);
			synMerchant.setMerchantStatus("0");
			synMerchant.setUpdateTime(new Date());
			this.synMerchantDao.updateSynMerchant(synMerchant);
		} else {
			sysUserMerchant.setId(userMerchantId);//主键
			row = sysUserMerchantDao.updateSysUserMerchant(sysUserMerchant);
			/*
			 * 逻辑更改为 [不需要考虑待办事项的关闭开启问题]
			 */
			/*// 编辑负责人的时候，需要把原有的待办事项为完成的完成， 还需要创建新用户的待办事项
			Integer oldUserId = sysUserMerchant.getOldUserId();
			SysToDoListVO sysToDoList = new SysToDoListVO();
			sysToDoList.setUserId(oldUserId);
			sysToDoList.setItemStatus("0");// 待处理
			sysToDoList.setMerchantId(sysUserMerchant.getMerchantId());//当前商户
			List<SysToDoListVO> sysToDoLists = this.sysToDoListService.getSysToDoLists(sysToDoList);
			for (SysToDoListVO sysToDoListVO : sysToDoLists) {
				sysToDoListVO.setItemStatus("1");// 关闭前任用户的当前商户的待办事项
				this.sysToDoListService.updateSysToDoList(sysToDoListVO);
				try {
					SysToDoListVO cloneSysToDoListVO = (SysToDoListVO) BeanUtils.cloneBean(sysToDoListVO);
					Integer userId = sysUserMerchant.getUserId();
					cloneSysToDoListVO.setUserId(userId);
					cloneSysToDoListVO.setInsertBy(userId);
					cloneSysToDoListVO.setUpdateBy(userId);
					cloneSysToDoListVO.setInsertTime(new Date());
					cloneSysToDoListVO.setUpdateTime(new Date());
					cloneSysToDoListVO.setItemStatus("0");// 从新生成新用户的待办事项
					this.sysToDoListService.updateSysToDoList(cloneSysToDoListVO);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}*/
		}
		return row;
	}

	/**
	 * @Description 分配负责人创建待办事项
	 */
	private void createToDoListForAssignUser(SysUserVO sessionUser, List<FesOnlineProcessVO> complexFesOnlineProcesss) {
		for (FesOnlineProcessVO complexFesOnlineProcessVO : complexFesOnlineProcesss) {
			String stepNum = complexFesOnlineProcessVO.getFesStep().getStepNum();
			Integer processId = complexFesOnlineProcessVO.getId();
			// 分配负责人的时候处理的待办事项
			if (StepNum.entity_cards.toString().equals(stepNum)) {
				SysToDoListVO sysToDoList = new SysToDoListVO();
				sysToDoList.setRelatedId(processId);
				sysToDoList.setRelatedType("1");
				sysToDoList.setPriorityLevelType("01");// 一般
				sysToDoList.setItemType("01"); // 上线工作
				sysToDoList.setItemContent(toDoListStep6_init);
				sysToDoList.setResourceRemark(resource_fes_card_and_materials);
				sysToDoList.setBusinessType("4"); //4-上线流程[实体卡制作]
				this.sysToDoListService.saveToDoList(sessionUser, sysToDoList);
			}
			/*  此待办事项创建改为 物料设计流程 点击 '确认设计完成' 的时候处理 
			 * else if (StepNum.materials_design.toString().equals(stepNum)) {
				SysToDoListVO sysToDoList = new SysToDoListVO();
				sysToDoList.setRelatedId(processId);
				sysToDoList.setRelatedType("1");
				sysToDoList.setPriorityLevelType("01");// 一般
				sysToDoList.setItemType("01"); // 上线工作
				sysToDoList.setResourceRemark(resource_fes_card_and_materials);
				sysToDoList.setItemContent(toDoListStep7_init);
				sysToDoList.setBusinessType("5"); //5-上线流程[物料设计]
				this.sysToDoListService.saveToDoList(sessionUser, sysToDoList);
			} */else if (StepNum.micro_message.toString().equals(stepNum)) {
				SysToDoListVO sysToDoList = new SysToDoListVO();
				sysToDoList.setRelatedId(processId);
				sysToDoList.setRelatedType("1");
				sysToDoList.setPriorityLevelType("01");// 一般
				sysToDoList.setItemType("01"); // 上线工作
				sysToDoList.setItemContent(toDoListStep2_init);
				sysToDoList.setResourceRemark(resource_end_custom_service);
				sysToDoList.setBusinessType("3"); //3-上线流程[微信申请]
				this.sysToDoListService.saveToDoList(sessionUser, sysToDoList);
			} 
			/* 改为 会员方案确认 完成的时候 生成 待办事项  
			 * else if (StepNum.background_set.toString().equals(stepNum)) {
				SysToDoListVO sysToDoList = new SysToDoListVO();
				sysToDoList.setRelatedId(processId);
				sysToDoList.setRelatedType("1");
				sysToDoList.setPriorityLevelType("01");// 一般
				sysToDoList.setItemType("01"); // 上线工作
				sysToDoList.setItemContent(FesOnlineProcessServiceImpl.toDoListStep5_init);
				sysToDoList.setResourceRemark(resource_end_custom_service);
				sysToDoList.setBusinessType("13"); //3-上线流程[后台设置]
				this.sysToDoListService.saveToDoList(sessionUser, sysToDoList);
			}*/
			else if (StepNum.equipment_distribution.toString().equals(stepNum)) {
				SysToDoListVO sysToDoList = new SysToDoListVO();
				sysToDoList.setRelatedId(processId);
				sysToDoList.setRelatedType("1");
				sysToDoList.setPriorityLevelType("01");// 一般
				sysToDoList.setItemType("01"); // 上线工作
				sysToDoList.setItemContent(toDoListStep8_init);
				sysToDoList.setResourceRemark(resource_end_delivery_service);
				sysToDoList.setBusinessType("8"); //8-上线流程[设备配送]
				this.sysToDoListService.saveToDoList(sessionUser, sysToDoList);
			}
		}
	}

	/**
	 * @Description 通过oldUserId和merchantId查询是否存在当前记录
	 */
	private Integer getUserMerchantId(SysUserMerchantVO sysUserMerchant) {
		Integer userMerchantId = null;
		Integer oldUserId = sysUserMerchant.getOldUserId();
		if (oldUserId != null) {
			SysUserMerchantVO sysUserMerchantVO = new SysUserMerchantVO();
			sysUserMerchantVO.setMerchantId(sysUserMerchant.getMerchantId());
			sysUserMerchantVO.setUserId(oldUserId);
			List<SysUserMerchantVO> sysUserMerchants = this.getSysUserMerchants(sysUserMerchantVO);
			if (sysUserMerchants.size() == 0) {
				throw new FesBizException("参数原用户的id或者商户id不正确");
			}
			SysUserMerchantVO sysUserMerchantDB = sysUserMerchants.get(0);
			userMerchantId = sysUserMerchantDB.getId();
		}
		return userMerchantId;
	}

	public int batchInsertSysUserMerchants(Map<String, Object> map) {
		return sysUserMerchantDao.batchInsertSysUserMerchants(map);
	}

	public int updateSysUserMerchant(SysUserMerchantVO sysUserMerchant) {
		return sysUserMerchantDao.updateSysUserMerchant(sysUserMerchant);
	}

	public int batchUpdateSysUserMerchantsToDiffVals(Map<String, Object> map) {
		return sysUserMerchantDao.batchUpdateSysUserMerchantsToDiffVals(map);
	}

	public int batchUpdateSysUserMerchantsToSameVals(Map<String, Object> map) {
		return sysUserMerchantDao.batchUpdateSysUserMerchantsToSameVals(map);
	}

	public int deleteSysUserMerchantById(Integer id) {
		return sysUserMerchantDao.deleteSysUserMerchantById(id);
	}

	public int batchDeleteSysUserMerchantByIds(List<Integer> ids) {
		return sysUserMerchantDao.batchDeleteSysUserMerchantByIds(ids);
	}

	public SysUserMerchantVO getSysUserMerchantById(Integer id) {
		return sysUserMerchantDao.getSysUserMerchantById(id);
	}

	public List<SysUserMerchantVO> getSysUserMerchants(SysUserMerchantVO sysUserMerchant) {
		return sysUserMerchantDao.getSysUserMerchants(sysUserMerchant);
	}
	/**
	 * 通过商户ID查找前端负责人
	 */
	@Override
	public SysUserVO getFesUserByMerchantId(Integer merchantId) {
		SysUserMerchantVO sysUserMerchant = new SysUserMerchantVO();
		sysUserMerchant.setMerchantId(merchantId);
		 List<SysUserMerchantVO> sysUserMerchants = sysUserMerchantDao.getSysUserMerchants(sysUserMerchant);
		 if(sysUserMerchants!=null && sysUserMerchants.size()>0){
			 Integer userId = ((SysUserMerchantVO)sysUserMerchants.get(0)).getUserId();
			 return this.sysUserService.getSysUserById(userId);
		 }else{
			 return new SysUserVO();
		 }
	}

    @Override
    public Map<Integer, String> getFesUserByMerchantIds(List<Integer> merchantIds) {
        List<Map<String, Object>> userVOs = this.sysUserMerchantDao.getSysUserMerchantsMapByMIds(merchantIds);
        Map<Integer, String> result = new HashMap<Integer, String>();
        for (Map<String, Object> row : userVOs) {
            result.put((Integer)row.get("merchantid"), String.valueOf(row.get("username")));
        }
        return result;
    }

    public List<Map<String, Object>> getSysUserMerchantsMap(SysUserMerchantVO sysUserMerchant) {
		return sysUserMerchantDao.getSysUserMerchantsMap(sysUserMerchant);
	}

	/**
	 * @Title isExistSysUserMerchant
	 * @Description 根据用户ID和商户ID查询前端顾问和商户的关系
	 * @param userMerchantVO
	 * @return
	 * @see com.yazuo.erp.system.service.SysUserMerchantService#isExistSysUserMerchant(com.yazuo.erp.system.vo.SysUserMerchantVO)
	 */
	@Override
	public boolean isExistSysUserMerchant(SysUserMerchantVO userMerchantVO) {
		List<SysUserMerchantVO> list = sysUserMerchantDao.getSysUserMerchants(userMerchantVO);
		if (null == list || list.size() < 1) {
			return false;
		}
		return true;
	}
}
