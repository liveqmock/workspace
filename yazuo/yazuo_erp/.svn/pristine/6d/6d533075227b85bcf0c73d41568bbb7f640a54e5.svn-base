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

package com.yazuo.erp.external.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

import com.yazuo.erp.fes.service.FesPlanService;
import com.yazuo.erp.fes.service.impl.FesPlanServiceImpl;
import com.yazuo.erp.fes.vo.*;
import com.yazuo.erp.fes.dao.*;
import com.yazuo.erp.fes.exception.FesBizException;

/**
 * @Description 工作计划接口实现类
 * @author erp team
 * @date 
 */
import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.external.exception.ExternalBizException;
import com.yazuo.erp.external.service.PlanService;
import com.yazuo.erp.system.dao.SysAttachmentDao;
import com.yazuo.erp.system.dao.SysRemindDao;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.vo.SysRemindVO;

@Service
public class PlanServiceImpl implements PlanService {

	Logger log = Logger.getLogger(this.getClass());

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

	/**
	 * 提醒DAO
	 */
	@Resource
	private SysRemindDao sysRemindDao;

	@Resource
	private FesPlanService fesPlanService;

	@Resource
	private SysOperationLogService sysOperationLogService;

	/**
	 * @Title saveFesPlan
	 * @Description 添加工作计划
	 * @param param
	 * @param request
	 * @return
	 * @see com.yazuo.erp.fes.service.FesPlanService#saveFesPlan(java.util.Map,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public int saveFesPlan(String param) {
		// 从json字符串转换为json对象
		JSONObject jo = this.toJsonObjectFromString(param);

		// 添加工作计划
		FesPlanVO fesPlan = this.savePlan(jo);

		// 校验用户是否是商户的前端顾问
		fesPlanService.checkUserMerchant(fesPlan.getUserId(), fesPlan.getMerchantId(), fesPlan.getPlanItemType());

		// 如果是工作指派，发送领导指派提醒
		this.insertSysRemind(fesPlan);

		return 1;
	}

	/**
	 * @Description 如果是工作指派，发送领导指派提醒
	 * @param fesPlan
	 * @throws FesBizException
	 * @throws
	 */
	private void insertSysRemind(FesPlanVO fesPlan) throws FesBizException {
		Integer userId = fesPlan.getUserId();
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
	 * @Description 从json字符串转换为json对象
	 * @param param
	 * @return
	 * @throws ExternalBizException
	 * @throws
	 */
	private JSONObject toJsonObjectFromString(String param) throws ExternalBizException {
		String data = null;
		JSONObject jo = null;
		try {
			data = URLDecoder.decode(param, "UTF-8");
			jo = JSONObject.fromObject(data);
		} catch (UnsupportedEncodingException e) {
			log.error("参数解析失败");
			throw new ExternalBizException("参数解析失败");
		}
		return jo;
	}

	/**
	 * @Description 添加工作计划
	 * @param userId
	 * @return
	 * @throws
	 */
	private FesPlanVO savePlan(JSONObject jo) {
		Object userIdObj = jo.get("userId");
		Object merchantIdObj = jo.get("merchantId");
		Object titleObj = jo.get("title");
		Object planItemTypeObj = jo.get("planItemType");
		Object communicationFormTypeObj = jo.get("communicationFormType");
		Object contactIdObj = jo.get("contactId");
		Object startTimeObj = jo.get("startTime");
		Object isRemindObj = jo.get("isRemind");
		Object isSendSmsObj = jo.get("isSendSms");
		Object plansSourceObj = jo.get("plansSource");
		Object endTimeObj = jo.get("endTime");
		Object remindTimeObj = jo.get("remindTime");

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
		this.fesPlanService.checkMerchantId(merchantIdObj, planItemType);

		Integer userId = (Integer) userIdObj;
		Integer merchantId = null;
		if (null == merchantIdObj || "0".equals(String.valueOf(merchantIdObj))) {
			merchantId = null;
		} else {
			merchantId = (Integer) merchantIdObj;
		}
		String title = (String) titleObj;
		String communicationFormType = (String) communicationFormTypeObj;
		Integer contactId = null;
		if (null == contactIdObj || "0".equals(String.valueOf(contactIdObj))) {
			contactId = null;
		} else {
			contactId = (Integer) contactIdObj;
		}
		String description = (String) jo.get("description");
		String explanation = (String) jo.get("explanation");
		boolean isRemind = (Boolean) isRemindObj;
		boolean isSendSms = (Boolean) isSendSmsObj;
		Integer sponsor = (Integer) jo.get("sponsor");
		String plansSource = (String) plansSourceObj;
		String remark = (String) jo.get("remark");

		Long startTimeL = Long.parseLong(String.valueOf(jo.get("startTime")));
		Date startTimeDate = new Date(startTimeL);// 开始时间

		Date endTimeDate = this.toDate(endTimeObj); // 结束时间
		Date remindTimeDate = this.toDate(remindTimeObj);// 提醒时间

		// 校验，月报讲解一个月只能创建一次
		fesPlanService.checkCreateMonthlyPlan(planItemType, userId, merchantId, null, startTimeDate);

		FesPlanVO fesPlan = new FesPlanVO();
		fesPlan.setUserId(userId); // 用户ID
		fesPlan.setMerchantId(merchantId); // 商户ID
		fesPlan.setTitle(title);// 标题
		fesPlan.setPlanItemType(planItemType);// 事项类型
		fesPlan.setCommunicationFormType(communicationFormType);// 沟通方式
		fesPlan.setContactId(contactId);
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

		// 增加操作流水2014-11-20 11:27:42
		sysOperationLogService.saveSysOperationLogForFesPlan(fesPlan);

		if (1 > count) {
			throw new ExternalBizException("添加工作计划失败");
		}
		return fesPlan;
	}

	/**
	 * @Title updateFesPlan
	 * @Description 修改工作计划
	 * @param request
	 * @return
	 * @see com.yazuo.erp.fes.service.FesPlanService#updateFesPlan(java.util.Map,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public int updateFesPlan(String param) {
		// 从json字符串转换为json对象
		JSONObject jo = this.toJsonObjectFromString(param);

		// // 修改工作计划信息
		FesPlanVO fesPlan = this.updatePlan(jo);
		Integer planId = fesPlan.getId();
		Integer merchantId = fesPlan.getMerchantId();
		String planItemType = fesPlan.getPlanItemType();
		Integer userId = fesPlan.getInsertBy();
		Date startTime = fesPlan.getStartTime();

		// 校验用户是否是商户的前端顾问
		fesPlanService.checkUserMerchant(fesPlan.getUserId(), fesPlan.getMerchantId(), fesPlan.getPlanItemType());

		// 校验，月报讲解一个月只能创建一次，防止修改时重复
		fesPlanService.checkCreateMonthlyPlan(planItemType, userId, merchantId, planId, startTime);

		return 1;

	}

	/**
	 * @Description 修改工作计划
	 * @param userId
	 * @return
	 * @throws
	 */
	private FesPlanVO updatePlan(JSONObject jo) {
		Object userIdObj = jo.get("userId");
		Object planIdObj = jo.get("planId");
		Object merchantIdObj = jo.get("merchantId");
		Object titleObj = jo.get("title");
		Object planItemTypeObj = jo.get("planItemType");
		Object communicationFormTypeObj = jo.get("communicationFormType");
		Object contactIdObj = jo.get("contactId");
		Object startTimeObj = jo.get("startTime");
		Object isRemindObj = jo.get("isRemind");
		Object isSendSmsObj = jo.get("isSendSms");
		Object plansSourceObj = jo.get("plansSource");
		Object endTimeObj = jo.get("endTime");
		Object remindTimeObj = jo.get("remindTime");

		// 校验入参不能为空
		checkObjParam(userIdObj, "用户ID");
		checkObjParam(planIdObj, "工作计划ID");
		checkObjParam(titleObj, "标题");
		checkObjParam(planItemTypeObj, "事项类型");
		checkObjParam(communicationFormTypeObj, "沟通方式");
		checkObjParam(startTimeObj, "开始时间");
		checkObjParam(isRemindObj, "是否提醒");
		checkObjParam(isSendSmsObj, "是否发送短信");
		checkObjParam(plansSourceObj, "来源");

		Integer planId = (Integer) planIdObj;
		Integer userId = (Integer) userIdObj;
		Integer merchantId = null;
		if (null == merchantIdObj || "0".equals(String.valueOf(merchantIdObj))) {
			merchantId = null;
		} else {
			merchantId = (Integer) merchantIdObj;
		}

		// 如果事项类型不为7-其他和5-公司日常工作，则项目必输
		String planItemType = (String) planItemTypeObj;
		this.fesPlanService.checkMerchantId(merchantIdObj, planItemType);

		// 查询原工作计划
		FesPlanVO plan = fesPlanDao.getFesPlanById(planId);
		if (null == plan) {
			throw new FesBizException("未查询到工作计划[ID:" + planId + "]");
		}

		// 工作计划完成时，校验，月报讲解一个月只能完成一次
		String status = (String) jo.get("status");
		if ("1".equals(planItemType) && !"4".equals(plan.getStatus()) && "4".equals(status)) {
			this.fesPlanService.checkCompleteMonthlyPlan(userId, planId, merchantId, planItemType, plan.getStartTime());
		}

		String title = (String) titleObj;
		String communicationFormType = (String) communicationFormTypeObj;
		Integer contactId = null;
		if (null == contactIdObj || "0".equals(String.valueOf(contactIdObj))) {
			contactId = null;
		} else {
			contactId = (Integer) contactIdObj;
		}
		String description = (String) jo.get("description");
		String explanation = (String) jo.get("explanation");
		boolean isRemind = (Boolean) isRemindObj;
		boolean isSendSms = (Boolean) isSendSmsObj;
		Integer sponsor = (Integer) jo.get("sponsor");
		String plansSource = (String) plansSourceObj;
		String remark = (String) jo.get("remark");

		Long startTimeL = Long.parseLong(String.valueOf(jo.get("startTime")));
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
			throw new ExternalBizException("修改工作计划失败");
		}
		return fesPlan;
	}

	public int deleteFesPlanById(Integer id) {
		return fesPlanDao.deleteFesPlanById(id);
	}

	/**
	 * @Description 校验参数不能为空
	 * @param obj
	 * @param name
	 * @throws ExternalBizException
	 * @throws
	 */
	private void checkObjParam(Object obj, String name) throws ExternalBizException {
		if (null == obj) {
			throw new ExternalBizException("[" + name + "]" + "不能为空");
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
	 * @Title updateAbandonFesPlanById
	 * @Description 放弃工作计划
	 * @param ciphertext
	 * @return
	 * @see com.yazuo.erp.external.service.PlanService#updateAbandonFesPlanById(java.lang.String)
	 */
	@Override
	public int updateAbandonFesPlanById(String param) {
		// 从json字符串转换为json对象
		JSONObject jo = this.toJsonObjectFromString(param);
		Integer userId = (Integer) jo.get("userId");
		Integer planId = (Integer) jo.get("planId");
		String explanation = (String) jo.get("explanation"); // 情况说明

		// 校验入参不为空
		checkObjParam(userId, "用户ID");
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
			throw new ExternalBizException("放弃工作计划失败");
		}
		return count;
	}

	/**
	 * @Title updateDelayFesPlanById
	 * @Description 延期工作计划
	 * @param param
	 * @return
	 * @see com.yazuo.erp.external.service.PlanService#updateDelayFesPlanById(java.lang.String)
	 */
	@Override
	public int updateDelayFesPlanById(String param) {
		// 从json字符串转换为json对象
		JSONObject jo = this.toJsonObjectFromString(param);

		Integer userId = (Integer) jo.get("userId");
		Integer planId = (Integer) jo.get("planId");
		String explanation = (String) jo.get("explanation"); // 情况说明
		Object startTimeObj = jo.get("startTime");// 延期时间

		// 校验入参不为空
		checkObjParam(userId, "用户ID");
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
}
