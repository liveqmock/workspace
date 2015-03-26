/**
 * @Description 工作计划接口实现类
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.fes.service.impl;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.bes.service.BesRequirementService;
import com.yazuo.erp.fes.controller.FesOnlineProcessController;
import com.yazuo.erp.fes.dao.FesPlanAttachmentDao;
import com.yazuo.erp.fes.dao.FesPlanDao;
import com.yazuo.erp.fes.exception.FesBizException;

/**
 * @Description 工作计划接口实现类
 * @author erp team
 * @date 
 */
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.fes.service.FesPlanService;
import com.yazuo.erp.fes.vo.FesPlanAttachmentVO;
import com.yazuo.erp.fes.vo.FesPlanVO;
import com.yazuo.erp.system.dao.SysAttachmentDao;
import com.yazuo.erp.system.dao.SysRemindDao;
import com.yazuo.erp.system.dao.SysUserMerchantDao;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysRemindVO;
import com.yazuo.erp.system.vo.SysUserMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Description 工作计划接口实现类
 * @author erp team
 * @date
 */

@Service
public class FesPlanServiceImpl implements FesPlanService {

	/**
	 * 上传的前端服务中工作计划相关附件(真正用的文件)
	 */
	@Value("${planFilePath}")
	private String planFilePath;

	/**
	 * 上传的前端服务中工作计划相关附件(临时文件)
	 */
	@Value("${planFileTempPath}")
	private String planFileTempPath;

	/**
	 * 工作计划表DAO
	 */
	@Resource
	private FesPlanDao fesPlanDao;

	/**
	 * 附件表DAO
	 */
	@Resource
	private SysAttachmentDao sysAttachmentDao;

	/**
	 * 工作计划-附件关系表DAO
	 */
	@Resource
	private FesPlanAttachmentDao fesPlanAttachmentDao;

	@Resource
	private SysOperationLogService sysOperationLogService;

    @Resource
    private BesRequirementService besRequirementService;
    
	@Resource
	private SysUserMerchantDao sysUserMerchantDao;

	/**
	 * 提醒DAO
	 */
	@Resource
	private SysRemindDao sysRemindDao;

	public int batchInsertFesPlans(Map<String, Object> map) {
		return fesPlanDao.batchInsertFesPlans(map);
	}

	public int updateFesPlan(FesPlanVO fesPlan) {
		return fesPlanDao.updateFesPlan(fesPlan);
	}

	public int batchUpdateFesPlansToDiffVals(Map<String, Object> map) {
		return fesPlanDao.batchUpdateFesPlansToDiffVals(map);
	}

	public int batchUpdateFesPlansToSameVals(Map<String, Object> map) {
		return fesPlanDao.batchUpdateFesPlansToSameVals(map);
	}

	public int deleteFesPlanById(Integer id) {
		return fesPlanDao.deleteFesPlanById(id);
	}

	public int batchDeleteFesPlanByIds(List<Integer> ids) {
		return fesPlanDao.batchDeleteFesPlanByIds(ids);
	}

	public FesPlanVO getFesPlanById(Integer id) {
		return fesPlanDao.getFesPlanById(id);
	}

	public List<FesPlanVO> getFesPlans(FesPlanVO fesPlan) {
		return fesPlanDao.getFesPlans(fesPlan);
	}

	public List<Map<String, Object>> getFesPlansMap(FesPlanVO fesPlan) {
		return fesPlanDao.getFesPlansMap(fesPlan);
	}

	/**
	 * @Title saveFesPlan
	 * @Description 添加工作计划
	 * @param paramMap
	 * @param request
	 * @return
	 * @see com.yazuo.erp.fes.service.FesPlanService#saveFesPlan(java.util.Map,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public int saveFesPlan(Map<String, Object> paramMap, HttpServletRequest request) {
		Integer userId = (Integer) paramMap.get("userId");// 当前用户ID

		// 添加工作计划
		FesPlanVO fesPlan = this.savePlan(paramMap);
		Integer planId = fesPlan.getId();
		Integer merchantId = fesPlan.getMerchantId();
		String planItemType = fesPlan.getPlanItemType();
		Integer planUserId = fesPlan.getUserId();

		// 校验用户是否是商户的前端顾问
		this.checkUserMerchant(planUserId, merchantId, planItemType);

		// 处理新增附件列表，移动文件（临时文件夹到目标文件夹），添加附件信息，添加工作计划-附件关系信息
		List<Map<String, Object>> attachmentList = (List<Map<String, Object>>) paramMap.get("attachmentList");
		this.handleAddAttachmentList(request, userId, planId, attachmentList);

		// 如果是工作指派，发送领导指派提醒
		this.insertSysRemind(userId, fesPlan);

		// 增加操作流水2014-11-20 11:27:42
		sysOperationLogService.saveSysOperationLogForFesPlan(fesPlan);
		return 1;
	}

	/**
	 * @Description 校验用户是否是商户的前端顾问
	 * @param userId
	 * @param merchantId
	 * @param planItemType
	 * @return void
	 * @throws
	 */
	public void checkUserMerchant(Integer userId, Integer merchantId, String planItemType) {
		if ("1".equals(planItemType)) {
			SysUserMerchantVO sysUserMerchant = new SysUserMerchantVO();
			sysUserMerchant.setUserId(userId);
			sysUserMerchant.setMerchantId(merchantId);
			List<SysUserMerchantVO> list = sysUserMerchantDao.getSysUserMerchants(sysUserMerchant);
			if (null == list || 0 == list.size()) {
				throw new FesBizException("月报讲解时，用户必须为商户的前端顾问");
			}
		}
	}

	/**
	 * @Description 如果是工作指派，发送领导指派提醒
	 * @param userId
	 * @param fesPlan
	 * @throws FesBizException
	 * @throws
	 */
	private void insertSysRemind(Integer userId, FesPlanVO fesPlan) throws FesBizException {
		String plansSource = fesPlan.getPlansSource();
		if ("2".equals(plansSource)) {
			SysRemindVO remind = new SysRemindVO();
			remind.setUserId(fesPlan.getUserId());// 用户ID
			remind.setMerchantId(fesPlan.getMerchantId());// 商户ID
			remind.setPriorityLevelType("01");// 优先级
			remind.setItemType("04");// 提醒事项类型
			remind.setItemContent(fesPlan.getTitle());// 提醒事项内容
			remind.setItemStatus("1");// 提醒事项状态
			remind.setProcessId(null);// 上线流程ID
			remind.setRemark(null);// 备注
			remind.setIsEnable("1");// 是否有效
			remind.setInsertBy(userId);// 创建人
			remind.setInsertTime(new Date());// 创建时间
			remind.setUpdateBy(userId);// 最后修改人
			remind.setUpdateTime(new Date());// 最后修改时间
			int count = sysRemindDao.saveSysRemind(remind);
			if (1 > count) {
				throw new FesBizException("发送领导指派事项提醒失败");
			}
		}
	}

	/**
	 * @Description 添加工作计划-附件关系表
	 * @param userId
	 * @param planId
	 * @param attachmentId
	 * @throws
	 */
	private void savePlanAttachment(Integer userId, Integer planId, Integer attachmentId) {
		FesPlanAttachmentVO planAttachment = new FesPlanAttachmentVO();
		planAttachment.setPlanId(planId);// 工作计划ID
		planAttachment.setAttachmentId(attachmentId);// 附件ID
		planAttachment.setRemark(null);// 备注
		planAttachment.setInsertBy(userId);// 创建人
		planAttachment.setInsertTime(new Date());
		int count = fesPlanAttachmentDao.saveFesPlanAttachment(planAttachment);
		if (1 > count) {
			throw new FesBizException("工作计划相关附件添加失败");
		}
	}

	/**
	 * @Description 添加工作计划的附件
	 * @param fileName
	 * @param attachmentMap
	 * @param userId
	 * @return
	 * @throws
	 */
	private Integer saveAttachment(String fileName, Map<String, Object> attachmentMap, Integer userId) {
		String originalFileName = (String) attachmentMap.get("originalFileName");
		String attachmentSize = (String) attachmentMap.get("attachmentSize");
		String attachmentSuffix = (String) attachmentMap.get("attachmentSuffix");

		SysAttachmentVO sysAttachmentVO = new SysAttachmentVO();
		sysAttachmentVO.setAttachmentName(fileName);
		sysAttachmentVO.setOriginalFileName(originalFileName);
		sysAttachmentVO.setAttachmentType("3");
		sysAttachmentVO.setAttachmentSize(attachmentSize);
		sysAttachmentVO.setAttachmentPath(null);
		sysAttachmentVO.setAttachmentSuffix(attachmentSuffix);
		sysAttachmentVO.setModuleType("fes");
		sysAttachmentVO.setIsEnable("1");
		sysAttachmentVO.setIsDownloadable("1");
		sysAttachmentVO.setHours(null);
		sysAttachmentVO.setRemark(null);
		sysAttachmentVO.setInsertBy(userId);
		sysAttachmentVO.setInsertTime(new Date());
		sysAttachmentVO.setUpdateBy(userId);
		sysAttachmentVO.setUpdateTime(new Date());
		int count = sysAttachmentDao.saveSysAttachment(sysAttachmentVO);
		if (1 > count) {
			throw new FesBizException("附件添加失败");
		}
		return sysAttachmentVO.getId();
	}

	/**
	 * @Description 添加工作计划
	 * @param paramMap
	 * @return
	 * @throws
	 */
	private FesPlanVO savePlan(Map<String, Object> paramMap) {
		Object userIdObj = paramMap.get("userId");// 当前用户ID
		Object merchantIdObj = paramMap.get("merchantId");
		Object titleObj = paramMap.get("title");
		Object planItemTypeObj = paramMap.get("planItemType");
		Object communicationFormTypeObj = paramMap.get("communicationFormType");
		Object contactIdObj = paramMap.get("contactId");
		Object startTimeObj = paramMap.get("startTime");
		Object isRemindObj = paramMap.get("isRemind");
		Object isSendSmsObj = paramMap.get("isSendSms");
		Object plansSourceObj = paramMap.get("plansSource");
		Object endTimeObj = paramMap.get("endTime");
		Object remindTimeObj = paramMap.get("remindTime");

		// 校验入参不能为空
		checkObjParam(userIdObj, "用户ID");
		checkObjParam(titleObj, "标题");
		checkObjParam(planItemTypeObj, "事项类型");
		checkObjParam(communicationFormTypeObj, "沟通方式");
		checkObjParam(startTimeObj, "开始时间");
		checkObjParam(isRemindObj, "是否提醒");
		checkObjParam(isSendSmsObj, "是否发送短信");
		checkObjParam(plansSourceObj, "来源");

		// 如果事项类型不为7-其他和5-公司日常工作，则项目必输
		String planItemType = (String) planItemTypeObj;
		this.checkMerchantId(merchantIdObj, planItemType);

		Integer userId = (Integer) userIdObj;
		Integer merchantId = (Integer) merchantIdObj;
		String title = (String) titleObj;
		String communicationFormType = (String) communicationFormTypeObj;
		Integer contactId = null;
		if (null == contactIdObj || "0".equals(String.valueOf(contactIdObj))) {
			contactId = null;
		} else {
			contactId = (Integer) contactIdObj;
		}
		String description = (String) paramMap.get("description");
		String explanation = (String) paramMap.get("explanation");
		boolean isRemind = (Boolean) isRemindObj;
		boolean isSendSms = (Boolean) isSendSmsObj;
		Integer sponsor = (Integer) paramMap.get("sponsor");
		String plansSource = (String) plansSourceObj;
		String remark = (String) paramMap.get("remark");

		Long startTimeL = Long.parseLong(String.valueOf(paramMap.get("startTime")));
		Date startTimeDate = new Date(startTimeL);// 开始时间

		Date endTimeDate = this.toDate(endTimeObj); // 结束时间
		Date remindTimeDate = this.toDate(remindTimeObj);// 提醒时间

		// 校验，月报讲解一个月只能创建一次
		this.checkCreateMonthlyPlan(planItemType, userId, merchantId, null, startTimeDate);

		FesPlanVO fesPlan = new FesPlanVO();
		fesPlan.setUserId(userId); // 用户ID
		fesPlan.setMerchantId(merchantId); // 商户ID
		fesPlan.setTitle(title);// 标题
		fesPlan.setPlanItemType(planItemType);// 事项类型
		fesPlan.setCommunicationFormType(communicationFormType);// 沟通方式
		fesPlan.setContactId(contactId);// 联系人ID
		fesPlan.setDescription(description); // 说明
		fesPlan.setStartTime(startTimeDate); // 开始时间
		fesPlan.setEndTime(endTimeDate); // 结束时间
		fesPlan.setExplanation(explanation); // 情况说明
		fesPlan.setIsRemind(isRemind); // 是否提醒
		fesPlan.setRemindTime(remindTimeDate); // 提醒时间
		fesPlan.setIsSendSms(isSendSms); // 是否发送短信
		fesPlan.setSponsor(sponsor); // 发起人
		fesPlan.setPlansSource(plansSource); // 来源
		fesPlan.setStatus("1"); // 状态
		fesPlan.setIsEnable("1"); // 是否有效
		fesPlan.setRemark(remark); // 备注
		fesPlan.setInsertBy(userId); // 创建人
		fesPlan.setInsertTime(new Date());
		fesPlan.setUpdateBy(userId); // 最后修改人
		fesPlan.setUpdateTime(new Date());

		int count = fesPlanDao.saveFesPlan(fesPlan);
		if (1 > count) {
			throw new FesBizException("添加工作计划失败");
		}
		return fesPlan;
	}

	/**
	 * @Description 校验，月报讲解一个月只能创建一次
	 * @param planItemType
	 * @param userId
	 * @param merchantId
	 * @param planId
	 * @param startTime
	 * @return void
	 * @throws
	 */
	public void checkCreateMonthlyPlan(String planItemType, Integer userId, Integer merchantId, Integer planId, Date startTime) {
		if ("1".equals(planItemType)) {
			Map<String, Object> inputMap = new HashMap<String, Object>();
			inputMap.put("userId", userId);
			inputMap.put("month", DateUtil.format(startTime, "yyyy-MM"));
			inputMap.put("merchantId", merchantId);
			inputMap.put("planId", planId);
			int count = this.fesPlanDao.getCountOfMonthlyPlans(inputMap);
			if (count > 0) {
				if (null == planId) {
					throw new FesBizException("此商户当月的月报讲解工作计划已经创建，不能再次创建");
				} else {
					throw new FesBizException("此商户当月的月报讲解工作计划已经创建，事项类型不能修改为讲解月报");
				}
			}
		}
	}

	private static final Log LOG = LogFactory.getLog(FesPlanServiceImpl.class);
	/**
	 * 一个月一个商户一个月报讲解的工作计划 未完成的可以有多条, 已完成的只允许有一条
	 */
    @Override
    public FesPlanVO getMonthlyPlanVO(Integer merchantId, Date from, Date to) {
        List<FesPlanVO> fesPlanVOs =  this.fesPlanDao.getMonthlyPlanVO(merchantId, from, to);
        for (FesPlanVO fesPlanVO : fesPlanVOs) {
        	//工作计划未完成的按创建时间倒序取第一条时间最大的
			if(new EqualsBuilder().append("1", fesPlanVO.getStatus()).append(Constant.IS_ENABLE, fesPlanVO.getIsEnable()).isEquals()){
				return fesPlanVOs.get(0);
			}
		}
        LOG.error("请先创建商户该月的月报讲解工作计划，再发送月报!");
        return null;
    }

    /**
	 * @Description 校验，如果事项类型不为7-其他和5-公司日常工作，则项目必输
	 * @param merchantIdObj
	 * @param planItemType
	 * @return void
	 * @throws
	 */
	public void checkMerchantId(Object merchantIdObj, String planItemType) {
		if (!"7".equals(planItemType) && !"5".equals(planItemType)) {
			checkObjParam(merchantIdObj, "项目");
		}
	}

	/**
	 * @Description 由毫秒转换为日期格式
	 * @param timeObj
	 * @return
	 * @throws NumberFormatException
	 * @throws
	 */
	private Date toDate(Object timeObj) throws NumberFormatException {
		Date timeDate = null;
		String timeStr = String.valueOf(timeObj);
		if (null != timeObj && !"0".equals(timeStr)) {
			Long timeL = Long.parseLong(timeStr);
			timeDate = new Date(timeL);
		}
		return timeDate;
	}

	/**
	 * @Description 工作管理相关附件从临时文件夹移动到目标文件夹
	 * @param fileName
	 * @param request
	 * @return
	 * @throws
	 */
	private boolean moveFile(String fileName, HttpServletRequest request) {
		String orignPath = request.getSession().getServletContext().getRealPath(planFileTempPath) + "/" + fileName;
		String destPath = request.getSession().getServletContext().getRealPath(planFilePath);
		File orignFile = new File(orignPath); // 源文件
		File destFile = new File(destPath); // 目标文件
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		return orignFile.renameTo(new File(destFile, orignFile.getName()));
	}

	/**
	 * @Description 校验参数不能为空
	 * @param obj
	 * @param name
	 * @throws FesBizException
	 * @throws
	 */
	private void checkObjParam(Object obj, String name) throws FesBizException {
		if (null == obj) {
			throw new FesBizException("[" + name + "]" + "不能为空");
		}
	}

	/**
	 * @Title updateFesPlan
	 * @Description 修改工作计划
	 * @param paramMap
	 * @param request
	 * @return
	 * @see com.yazuo.erp.fes.service.FesPlanService#updateFesPlan(java.util.Map,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public int updateFesPlan(Map<String, Object> paramMap, HttpServletRequest request) {
		Integer userId = (Integer) paramMap.get("userId");// 当前用户ID

		// 修改工作计划信息
		FesPlanVO fesPlan = this.updatePlan(paramMap);
		Integer planId = fesPlan.getId();
		Integer merchantId = fesPlan.getMerchantId();
		String planItemType = fesPlan.getPlanItemType();
		Integer planUserId = fesPlan.getUserId();
		Date startTime = fesPlan.getStartTime();

		// 校验用户是否是商户的前端顾问
		this.checkUserMerchant(planUserId, merchantId, planItemType);

		// 校验，月报讲解一个月只能创建一次，防止修改时重复
		this.checkCreateMonthlyPlan(planItemType, userId, merchantId, planId, startTime);

		// 解析附件列表，分析新增的、删除的附件列表
		Map<String, Object> listMap = this.analyzeAttachmentList(paramMap);
		List<Map<String, Object>> addList = (List<Map<String, Object>>) listMap.get("addList");// 新增附件
		List<FesPlanAttachmentVO> deleteList = (List<FesPlanAttachmentVO>) listMap.get("deleteList");// 删减附件

		// 处理删除附件列表，删除文件，删除附件信息，删除工作计划-附件关系信息
		this.handleDeleteAttachmentList(request, deleteList);

		// 处理新增附件列表，移动文件（临时文件夹到目标文件夹），添加附件信息，添加工作计划-附件关系信息
		this.handleAddAttachmentList(request, userId, planId, addList);

		return 1;

	}

	/**
	 * @Description 处理新增附件列表，移动文件（临时文件夹到目标文件夹），添加附件信息，添加工作计划-附件关系信息
	 * @param request
	 * @param userId
	 * @param planId
	 * @param addList
	 * @throws
	 */
	private void handleAddAttachmentList(HttpServletRequest request, Integer userId, Integer planId,
			List<Map<String, Object>> addList) {
		for (Map<String, Object> attachmentMap : addList) {
			// 文件从临时文件夹移动到实际位置
			String attachmentName = (String) attachmentMap.get("attachmentName");
			moveFile(attachmentName, request);

			// 添加附件
			Integer attachmentId = this.saveAttachment(attachmentName, attachmentMap, userId);

			// 添加工作计划-附件关系表
			this.savePlanAttachment(userId, planId, attachmentId);
		}
	}

	/**
	 * @Description 处理删除附件列表，删除文件，删除附件信息，删除工作计划-附件关系信息
	 * @param request
	 * @param deleteList
	 * @throws FesBizException
	 * @throws
	 */
	private void handleDeleteAttachmentList(HttpServletRequest request, List<FesPlanAttachmentVO> deleteList)
			throws FesBizException {
		for (FesPlanAttachmentVO vo : deleteList) {
			Integer id = vo.getId();
			Integer attachmentId = vo.getAttachmentId();
			SysAttachmentVO sysAttachment = sysAttachmentDao.getSysAttachmentById(attachmentId);
			if (null == sysAttachment) {
				throw new FesBizException("未找到附件信息[附件ID:" + attachmentId + "]");
			}
			String attachmentName = sysAttachment.getAttachmentName();

			// 删除文件
			deleteFile(attachmentName, request);

			// 删除工作计划-附件关系信息
			int count = fesPlanAttachmentDao.deleteFesPlanAttachmentById(id);
			if (1 > count) {
				throw new FesBizException("删除工作计划-附件关系信息失败[ID:" + id + "]");
			}

			// 删除附件信息
			count = sysAttachmentDao.deleteSysAttachmentById(attachmentId);
			if (1 > count) {
				throw new FesBizException("删除附件信息失败[附件ID:" + attachmentId + "]");
			}
		}
	}

	/**
	 * @Description 删除工作计划附件
	 * @param fileName
	 * @param request
	 * @return
	 * @throws
	 */
	public boolean deleteFile(String fileName, HttpServletRequest request) {
		String destPath = request.getSession().getServletContext().getRealPath(planFilePath);
		File destFile = new File(destPath + "/" + fileName); // 目标文件
		return destFile.delete();
	}

	/**
	 * @Description 解析附件列表，分析新增的、删除的附件列表
	 * @param paramMap
	 * @return
	 * @throws
	 */
	private Map<String, Object> analyzeAttachmentList(Map<String, Object> paramMap) {
		List<Map<String, Object>> attachmentList = (List<Map<String, Object>>) paramMap.get("attachmentList");
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();// 新增附件列表
		List<Map<String, Object>> restList = new ArrayList<Map<String, Object>>();// 原附件删减后剩下的，无变化的附件列表

		// addList为：附件ID为空的记录
		// restList：前端上传的附件列表-新增的附件
		for (Map<String, Object> map : attachmentList) {
			Integer attachmentId = (Integer) map.get("attachmentId");
			if (null == attachmentId) {// 新增附件
				addList.add(map);
			} else {
				restList.add(map);
			}
		}

		// 根据工作计划ID查询附件列表
		Integer planId = (Integer) paramMap.get("planId");
		FesPlanAttachmentVO fesPlanAttachment = new FesPlanAttachmentVO();
		fesPlanAttachment.setPlanId(planId);
		List<FesPlanAttachmentVO> planAttachmentList = fesPlanAttachmentDao.getFesPlanAttachments(fesPlanAttachment);// 原附件

		// 分析出删除的附件列表，原附件-无变化的附件=删减的附件
		for (Map<String, Object> map : restList) {
			Integer id = (Integer) map.get("attachmentId");
			for (FesPlanAttachmentVO vo : planAttachmentList) {
				Integer attachmentId = vo.getAttachmentId();
				if (attachmentId.equals(id)) {
					planAttachmentList.remove(vo);
					break;
				}
			}
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("addList", addList);// 新增附件
		returnMap.put("deleteList", planAttachmentList);// 删减附件

		return returnMap;
	}

	/**
	 * @Description 修改工作计划
	 * @param paramMap
	 * @param userId
	 * @return
	 * @throws
	 */
	private FesPlanVO updatePlan(Map<String, Object> paramMap) {
		Object userIdObj = paramMap.get("userId");// 当前用户ID
		Object planIdObj = paramMap.get("planId");
		Object merchantIdObj = paramMap.get("merchantId");
		Object titleObj = paramMap.get("title");
		Object planItemTypeObj = paramMap.get("planItemType");
		Object communicationFormTypeObj = paramMap.get("communicationFormType");
		Object contactIdObj = paramMap.get("contactId");
		Object startTimeObj = paramMap.get("startTime");
		Object isRemindObj = paramMap.get("isRemind");
		Object isSendSmsObj = paramMap.get("isSendSms");
		Object plansSourceObj = paramMap.get("plansSource");
		Object endTimeObj = paramMap.get("endTime");
		Object remindTimeObj = paramMap.get("remindTime");

		// 校验入参不能为空
		checkObjParam(planIdObj, "用户ID");
		checkObjParam(planIdObj, "工作计划ID");
		checkObjParam(titleObj, "标题");
		checkObjParam(planItemTypeObj, "事项类型");
		checkObjParam(communicationFormTypeObj, "沟通方式");
		checkObjParam(startTimeObj, "开始时间");
		checkObjParam(isRemindObj, "是否提醒");
		checkObjParam(isSendSmsObj, "是否发送短信");
		checkObjParam(plansSourceObj, "来源");

		// 如果事项类型不为7-其他和5-公司日常工作，则项目必输
		String planItemType = (String) planItemTypeObj;
		this.checkMerchantId(merchantIdObj, planItemType);

		Integer userId = (Integer) userIdObj;// 当前用户ID
		Integer planId = (Integer) planIdObj;
		Integer merchantId = (Integer) merchantIdObj;
		String title = (String) titleObj;
		String communicationFormType = (String) communicationFormTypeObj;
		Integer contactId = null;
		if (null == contactIdObj || "0".equals(String.valueOf(contactIdObj))) {
			contactId = null;
		} else {
			contactId = (Integer) contactIdObj;
		}
		String description = (String) paramMap.get("description");
		String explanation = (String) paramMap.get("explanation");
		boolean isRemind = (Boolean) isRemindObj;
		boolean isSendSms = (Boolean) isSendSmsObj;
		Integer sponsor = (Integer) paramMap.get("sponsor");
		String plansSource = (String) plansSourceObj;
		String remark = (String) paramMap.get("remark");
		String status = (String) paramMap.get("status");

		Long startTimeL = Long.parseLong(String.valueOf(paramMap.get("startTime")));
		Date startTimeDate = new Date(startTimeL);// 开始时间

		Date endTimeDate = this.toDate(endTimeObj); // 结束时间
		Date remindTimeDate = this.toDate(remindTimeObj);// 提醒时间

		FesPlanVO fesPlan = new FesPlanVO();
		fesPlan.setId(planId);// ID
		fesPlan.setUserId(userId); // 用户ID
		fesPlan.setMerchantId(merchantId); // 商户ID
		fesPlan.setTitle(title);// 标题
		fesPlan.setPlanItemType(planItemType);// 事项类型
		fesPlan.setCommunicationFormType(communicationFormType);// 沟通方式
		fesPlan.setContactId(contactId);// 联系人ID
		fesPlan.setDescription(description); // 说明
		fesPlan.setStartTime(startTimeDate); // 开始时间
		fesPlan.setEndTime(endTimeDate); // 结束时间
		fesPlan.setExplanation(explanation); // 情况说明
		fesPlan.setIsRemind(isRemind); // 是否提醒
		fesPlan.setRemindTime(remindTimeDate); // 提醒时间
		fesPlan.setIsSendSms(isSendSms); // 是否发送短信
		fesPlan.setSponsor(sponsor); // 发起人
		fesPlan.setPlansSource(plansSource); // 来源
		fesPlan.setStatus(status); // 状态
		fesPlan.setIsEnable("1"); // 是否有效
		fesPlan.setRemark(remark); // 备注
		fesPlan.setInsertBy(userId); // 创建人
		fesPlan.setInsertTime(new Date());
		fesPlan.setUpdateBy(userId); // 最后修改人
		fesPlan.setUpdateTime(new Date());

		int count = fesPlanDao.updateFesPlan(fesPlan);
		if (1 > count) {
			throw new FesBizException("修改工作计划失败");
		}
		return fesPlan;
	}

	/**
	 * @Title uploadPlanFiles
	 * @Description 上传工作计划附件
	 * @param myfiles
	 * @param request
	 * @return
	 * @see com.yazuo.erp.fes.service.FesPlanService#uploadPlanFiles(org.springframework.web.multipart.MultipartFile[],
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Object uploadPlanFiles(MultipartFile[] myfiles, HttpServletRequest request) throws IOException {
		return FileUploaderUtil.uploadFile(myfiles, "planFileTempPath", null, 0, request);
	}

	/**
	 * @Title queryFesPlanList
	 * @Description 查询工作计划列表
	 * @param paramMap
	 * @return
	 * @see com.yazuo.erp.fes.service.FesPlanService#queryFesPlanList(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> queryFesPlanList(Map<String, Object> paramMap) {
		Integer userId = (Integer) paramMap.get("userId");
		Object startTimeObj = paramMap.get("startTime");
		Object endTimeObj = paramMap.get("endTime");
		Integer merchantId = (Integer) paramMap.get("merchantId");

		// 校验入参不为空
		checkObjParam(startTimeObj, "开始时间");
		checkObjParam(endTimeObj, "结束时间");

		Date startTime = this.toDate(startTimeObj); // 开始时间
		Date endTime = this.toDate(endTimeObj); // 结束时间

		List<Map<String, Object>> list = this.fesPlanDao.queryFesPlanList(startTime, endTime, userId, merchantId);
		return list;
	}

	/**
	 * @Title queryFesPlanById
	 * @Description 根据工作计划ID查询详细信息
	 * @param paramMap
	 * @return
	 * @see com.yazuo.erp.fes.service.FesPlanService#queryFesPlanById(java.util.Map)
	 */
	@Override
	public Map<String, Object> queryFesPlanById(Map<String, Object> paramMap) {
		Integer planId = (Integer) paramMap.get("planId");

		// 校验入参不为空
		checkObjParam(planId, "工作计划ID");

		// 根据指定ID查询工作计划
		Map<String, Object> map = fesPlanDao.queryFesPlan(planId);

		if (null != map && map.size() > 0) {
			List<Map<String, Object>> attachmentList = sysAttachmentDao.getSysAttachmentList(planId);
			map.put("attachmentList", attachmentList);
			map.put("planFilePath", planFilePath);
		}
		return map;
	}

	/**
	 * @Title abandonFesPlanById
	 * @Description 放弃工作计划
	 * @param paramMap
	 * @return
	 * @see com.yazuo.erp.fes.service.FesPlanService#abandonFesPlanById(java.util.Map)
	 */
	@Override
	public int updateAbandonFesPlanById(Map<String, Object> paramMap) {
		SysUserVO user = (SysUserVO) paramMap.get("user");
		Integer userId = user.getId();// 当前用户ID

		Integer planId = (Integer) paramMap.get("planId");
		String explanation = (String) paramMap.get("explanation"); // 情况说明

		// 校验入参不为空
		checkObjParam(planId, "工作计划ID");
		checkObjParam(explanation, "原因");

		// 查询原工作计划
		FesPlanVO plan = fesPlanDao.getFesPlanById(planId);
		if (null == plan) {
			throw new FesBizException("未查询到工作计划[ID:" + planId + "]");
		}
		plan.setExplanation(explanation);
		plan.setStatus("3");
		plan.setUpdateBy(userId);
		plan.setUpdateTime(new Date());

		int count = fesPlanDao.updateFesPlan(plan);
		if (1 > count) {
			throw new FesBizException("放弃工作计划失败");
		}
		return count;
	}

	/**
	 * @Title delayFesPlanById
	 * @Description 延期工作计划
	 * @param paramMap
	 * @return
	 * @see com.yazuo.erp.fes.service.FesPlanService#delayFesPlanById(java.util.Map)
	 */
	@Override
	public int updateDelayFesPlanById(Map<String, Object> paramMap) {
		SysUserVO user = (SysUserVO) paramMap.get("user");
		Integer userId = user.getId();// 当前用户ID

		Integer planId = (Integer) paramMap.get("planId");
		String explanation = (String) paramMap.get("explanation"); // 情况说明
		Object startTimeObj = paramMap.get("startTime");// 延期时间

		// 校验入参不为空
		checkObjParam(planId, "工作计划ID");
		checkObjParam(explanation, "原因");
		checkObjParam(startTimeObj, "延期时间");

		Date startTime = this.toDate(startTimeObj); // 开始时间

		// 查询原工作计划
		FesPlanVO plan = fesPlanDao.getFesPlanById(planId);
		if (null == plan) {
			throw new FesBizException("未查询到工作计划[ID:" + planId + "]");
		}

		if (null != plan.getMerchantId() && 0 == plan.getMerchantId().intValue()) {
			plan.setMerchantId(null);
		}

		// 原工作计划延期
		this.statusToDalay(userId, planId, explanation, plan);

		// 创建新工作计划（从原工作计划复制）
		Integer id = this.copyPlan(startTime, plan, userId);

		// 批量创建新工作计划的附件关系（从原工作计划-附件关系复制）
		this.copyPlanAttachmentRef(userId, planId, id);

		return 1;
	}

	/**
	 * @Description 批量创建新工作计划的附件关系（从原工作计划-附件关系复制）
	 * @param userId
	 * @param planId
	 * @param id
	 * @throws FesBizException
	 * @throws
	 */
	private void copyPlanAttachmentRef(Integer userId, Integer planId, Integer id) throws FesBizException {
		int count;
		// 查询原工作计划-附件关系
		FesPlanAttachmentVO pa = new FesPlanAttachmentVO();
		pa.setPlanId(planId);
		List<FesPlanAttachmentVO> fesPlanAttachments = fesPlanAttachmentDao.getFesPlanAttachments(pa);

		// 批量创建新工作计划的附件关系
		List<FesPlanAttachmentVO> fesPlanAttachmentList = new ArrayList<FesPlanAttachmentVO>();
		for (FesPlanAttachmentVO vo : fesPlanAttachments) {
			FesPlanAttachmentVO ref = new FesPlanAttachmentVO();
			ref.setPlanId(id);
			ref.setAttachmentId(vo.getAttachmentId());
			ref.setRemark(vo.getRemark());
			ref.setInsertBy(userId);
			ref.setInsertTime(new Date());

			fesPlanAttachmentList.add(ref);
		}
		if (null != fesPlanAttachmentList && fesPlanAttachmentList.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(FesPlanAttachmentVO.COLUMN_PLAN_ID, Constant.NOT_NULL);
			map.put(FesPlanAttachmentVO.COLUMN_ATTACHMENT_ID, Constant.NOT_NULL);
			map.put(FesPlanAttachmentVO.COLUMN_INSERT_BY, Constant.NOT_NULL);
			map.put(FesPlanAttachmentVO.COLUMN_INSERT_TIME, Constant.NOT_NULL);
			map.put("list", fesPlanAttachmentList);
			count = fesPlanAttachmentDao.batchInsertFesPlanAttachments(map);
			if (1 > count) {
				throw new FesBizException("批量创建新工作计划的附件关系失败");
			}
		}
	}

	/**
	 * @Description 延期时创建新工作计划（从原工作计划复制）
	 * @param startTime
	 * @param plan
	 * @param userId
	 * @return
	 * @throws FesBizException
	 * @throws
	 */
	private Integer copyPlan(Date startTime, FesPlanVO plan, Integer userId) throws FesBizException {
		FesPlanVO newPlan = plan;
		newPlan.setId(null);
		newPlan.setStartTime(startTime);
		newPlan.setStatus("1");
		newPlan.setInsertBy(userId);
		newPlan.setInsertTime(new Date());
		newPlan.setUpdateBy(userId);
		newPlan.setUpdateTime(new Date());
		int count = fesPlanDao.saveFesPlan(newPlan);
		if (1 > count) {
			throw new FesBizException("延期工作计划失败");
		}
		Integer id = newPlan.getId();
		return id;
	}

	/**
	 * @Description 原工作计划延期
	 * @param userId
	 * @param planId
	 * @param explanation
	 * @param plan
	 * @throws FesBizException
	 * @throws
	 */
	private void statusToDalay(Integer userId, Integer planId, String explanation, FesPlanVO plan) throws FesBizException {
		plan.setId(planId);
		plan.setExplanation(explanation);
		plan.setStatus("2");
		plan.setUpdateBy(userId);
		plan.setUpdateTime(new Date());
		int count = fesPlanDao.updateFesPlan(plan);
		if (1 > count) {
			throw new FesBizException("延期工作计划失败");
		}
	}

	/**
	 * @Title queryDailyFesPlanList
	 * @Description 查询一天的工作计划列表
	 * @param paramMap
	 * @return
	 * @see com.yazuo.erp.fes.service.FesPlanService#queryDaylyFesPlanList(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> queryDailyFesPlanList(Map<String, Object> paramMap) {
		Integer userId = (Integer) paramMap.get("userId");
		Object startTimeObj = paramMap.get("startTime");
		Object endTimeObj = paramMap.get("endTime");
		Integer merchantId = (Integer) paramMap.get("merchantId");

		// 校验入参不为空
		checkObjParam(startTimeObj, "开始时间");
		checkObjParam(endTimeObj, "结束时间");

		Date startTime = this.toDate(startTimeObj); // 开始时间
		Date endTime = this.toDate(endTimeObj); // 结束时间

		List<Map<String, Object>> list = this.fesPlanDao.queryFesPlanList(startTime, endTime, userId, merchantId);

		// 根据工作计划ID查询附件信息
		for (Map<String, Object> map : list) {
			Integer planId = (Integer) map.get("id");
			List<Map<String, Object>> attachmentList = sysAttachmentDao.getSysAttachmentList(planId);
			map.put("attachmentList", attachmentList);
			map.put("planFilePath", planFilePath);
		}
		return list;
	}

	/**
	 * @Title updateCompleteFesPlanById
	 * @Description 完成工作计划
	 * @param paramMap
	 * @return
	 * @see com.yazuo.erp.fes.service.FesPlanService#updateCompleteFesPlanById(java.util.Map)
	 */
	@Override
	public int updateCompleteFesPlanById(Map<String, Object> paramMap) {
		SysUserVO user = (SysUserVO) paramMap.get("user");
		Integer userId = user.getId();// 当前用户ID

		Integer planId = (Integer) paramMap.get("planId");
		String explanation = (String) paramMap.get("explanation");

		// 校验入参不为空
		checkObjParam(planId, "工作计划ID");

		// 查询原工作计划
		FesPlanVO plan = fesPlanDao.getFesPlanById(planId);
		if (null == plan) {
			throw new FesBizException("未查询到工作计划[ID:" + planId + "]");
		}

		// 校验，月报讲解一个月只能完成一次
		this.checkCompleteMonthlyPlan(userId, planId, plan.getMerchantId(), plan.getPlanItemType(), plan.getStartTime());

		// 原工作计划完成
		this.statusToComplete(userId, plan, explanation);

		return 1;
	}

	/**
	 * @Title checkCompleteMonthlyPlan
	 * @Description 校验，月报讲解一个月只能完成一次
	 * @param userId
	 * @param planId
	 * @param merchantId
	 * @param planItemType
	 * @param startTime
	 * @see com.yazuo.erp.fes.service.FesPlanService#checkCompleteMonthlyPlan(java.lang.Integer,
	 *      java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	public void checkCompleteMonthlyPlan(Integer userId, Integer planId, Integer merchantId, String planItemType, Date startTime) {
		if ("1".equals(planItemType)) {
			Map<String, Object> inputMap = new HashMap<String, Object>();
			inputMap.put("userId", userId);
			inputMap.put("month", DateUtil.format(startTime, "yyyy-MM"));
			inputMap.put("merchantId", merchantId);
			inputMap.put("planId", planId);
			int count = this.fesPlanDao.getCountOfCompletedMonthlyPlans(inputMap);
			if (count > 0) {
				throw new FesBizException("此商户本月的月报讲解工作计划已经完成，不能重复完成");
			}
		}
	}

	@Override
	public FesPlanVO getCurrentMonthlyPlan(Integer merchantId, Date from, Date to) {
		return this.fesPlanDao.getCurrentMonthlyPlan(merchantId, from, to);
	}

	/**
	 * @param explanation
	 * @Description 原工作计划完成
	 * @param userId
	 * @param plan
	 * @throws FesBizException
	 * @throws
	 */
	private void statusToComplete(Integer userId, FesPlanVO plan, String explanation) throws FesBizException {
		if (null != plan.getMerchantId() && 0 == plan.getMerchantId().intValue()) {
			plan.setMerchantId(null);
		}
		Date endTime = new Date(); // 结束时间
		plan.setId(plan.getId());
		plan.setEndTime(endTime);
		plan.setExplanation(explanation);
		plan.setStatus("4");
		plan.setUpdateBy(userId);
		plan.setUpdateTime(new Date());
		int count = fesPlanDao.updateFesPlan(plan);
		if (1 > count) {
			throw new FesBizException("完成工作计划失败");
		}else if("1".equals(plan.getPlanItemType())){
            this.besRequirementService.saveCreateBesReq(plan);
        }
	}

	/**
	 * @Title updateRemindFesPlanById
	 * @Description 工作计划提醒设置
	 * @param paramMap
	 * @return
	 * @see com.yazuo.erp.fes.service.FesPlanService#updateRemindFesPlanById(java.util.Map)
	 */
	@Override
	public int updateRemindFesPlanById(Map<String, Object> paramMap) {
		SysUserVO user = (SysUserVO) paramMap.get("user");
		Integer userId = user.getId();// 当前用户ID

		Integer planId = (Integer) paramMap.get("planId");

		// 校验入参不为空
		checkObjParam(planId, "工作计划ID");

		// 查询原工作计划
		FesPlanVO plan = fesPlanDao.getFesPlanById(planId);
		if (null == plan) {
			throw new FesBizException("未查询到工作计划[ID:" + planId + "]");
		}

		// 工作计划提醒设置
		this.remindConfig(paramMap, userId, plan);

		return 1;
	}

	/**
	 * @Description 工作计划提醒设置
	 * @param paramMap
	 * @param userId
	 * @param plan
	 * @throws FesBizException
	 * @throws NumberFormatException
	 * @throws
	 */
	private void remindConfig(Map<String, Object> paramMap, Integer userId, FesPlanVO plan) throws FesBizException,
			NumberFormatException {
		Object isRemindObj = paramMap.get("isRemind");
		Object isSendSmsObj = paramMap.get("isSendSms");
		Object remindTimeObj = paramMap.get("remindTime");

		// 校验入参不能为空
		checkObjParam(isRemindObj, "是否提醒");
		checkObjParam(isSendSmsObj, "是否发送短信");

		boolean isRemind = (Boolean) isRemindObj;
		boolean isSendSms = (Boolean) isSendSmsObj;
		Date remindTimeDate = this.toDate(remindTimeObj);// 提醒时间

		if (null != plan.getMerchantId() && 0 == plan.getMerchantId().intValue()) {
			plan.setMerchantId(null);
		}
		plan.setId(plan.getId());
		plan.setIsRemind(isRemind);
		plan.setRemindTime(remindTimeDate);
		plan.setIsSendSms(isSendSms);
		plan.setUpdateBy(userId);
		plan.setUpdateTime(new Date());
		int count = fesPlanDao.updateFesPlan(plan);
		if (1 > count) {
			throw new FesBizException("工作计划提醒设置失败");
		}
	}

}
