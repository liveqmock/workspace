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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.fes.dao.FesOnlineProcessDao;
import com.yazuo.erp.fes.dao.FesOnlineProgramDao;
import com.yazuo.erp.fes.service.FesOnlineProgramService;
import com.yazuo.erp.sys.dao.SysRemindDao;
import com.yazuo.erp.sys.dao.SysUserMerchantDao;
import com.yazuo.erp.sys.vo.SysRemindVO;
import com.yazuo.erp.sys.vo.SysUserMerchantVO;
import com.yazuo.erp.tra.dao.TraCalendarDao;

/**
 * @author zhaohuaqin
 * @date 2014-8-4 上午11:20:49
 */
@Service
public class FesOnlineProgramServiceImpl implements FesOnlineProgramService {

	@Resource
	private FesOnlineProgramDao fesOnlineProgramDao;

	@Resource
	private FesOnlineProcessDao fesOnlineProcessDao;

	@Resource
	private SysUserMerchantDao sysUserMerchantDao;

	@Resource
	private SysRemindDao sysRemindDao;

	@Resource
	private TraCalendarDao traCalendarDao;

	@Value("#{configProperties['config.calendarUrl'] }")
	String calendarUrl;

	Log log = LogFactory.getLog(this.getClass());

	/**
	 * 
	 * @throws Exception
	 * @Title cardMakeRemind
	 * @Description 卡下单制作提醒
	 * @see com.yazuo.erp.fes.service.FesOnlineProgramService#cardMakeRemind()
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cardMakeRemind() throws Exception {

		Date today = new Date();
		Map<String, Object> beforeDays = traCalendarDao.beforeDays(today, 7);
		Date calendarDate = null; // 7天前日期
		if (null != beforeDays) {
			calendarDate = (Date) beforeDays.get("calendar_date");
		} else {
			throw new Exception("查询日期出错");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(calendarDate);
		List<Map<String, Object>> notOnlineMerchantList = fesOnlineProgramDao.getNotOnlineMerchant("entity_cards", date);

		List<SysRemindVO> sysRemindList = new ArrayList<SysRemindVO>();// 提醒列表
		SysRemindVO sysRemindVO = null;
		for (Map<String, Object> map : notOnlineMerchantList) {
			String onlineProcessStatus = (String) map.get("online_process_status");
			if ((!StringUtils.equals(onlineProcessStatus, "13")) && (!StringUtils.equals(onlineProcessStatus, "04"))) {

				Integer merchantId = (Integer) map.get("merchant_id");
				Integer id = (Integer) map.get("id");
				// 1、查询商户对应的前端用户
				List<SysUserMerchantVO> sysUserMerchantList = sysUserMerchantDao.getSysUserMerchantByMerchantId(merchantId);

				// 2、创建提醒VO
				for (SysUserMerchantVO sysUserMerchantVO : sysUserMerchantList) {
					sysRemindVO = createSysRemindVO(id, merchantId, sysUserMerchantVO.getUserId(),
							Constant.CARD_MARK_REMIND_CONTENT);
					sysRemindList.add(sysRemindVO);
				}
			}
		}

		// 3、批量插入提醒
		if (!CollectionUtils.isEmpty(sysRemindList)) {
			sysRemindDao.batchInsertSysRemind(sysRemindList);
		}
	}

	/**
	 * 
	 * @throws Exception
	 * @Title materielMakeRemind
	 * @Description 物料下单制作提醒
	 * @see com.yazuo.erp.fes.service.FesOnlineProgramService#materielMakeRemind()
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void materielMakeRemind() throws Exception {

		Date today = new Date();
		Map<String, Object> beforeDays = traCalendarDao.beforeDays(today, 20);
		Date calendarDate = null; // 20天前日期
		if (null != beforeDays) {
			calendarDate = (Date) beforeDays.get("calendar_date");
		} else {
			throw new Exception("查询日期出错");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(calendarDate);
		List<Map<String, Object>> notOnlineMerchantList = fesOnlineProgramDao.getNotOnlineMerchant("materials_design", date);

		List<SysRemindVO> sysRemindList = new ArrayList<SysRemindVO>();// 提醒列表
		SysRemindVO sysRemindVO = null;
		for (Map<String, Object> map : notOnlineMerchantList) {
			String onlineProcessStatus = (String) map.get("online_process_status");
			if (!StringUtils.equals(onlineProcessStatus, "04")) {
				Integer merchantId = (Integer) map.get("merchant_id");
				Integer id = (Integer) map.get("id");
				// 1、查询商户对应的前端用户
				List<SysUserMerchantVO> sysUserMerchantList = sysUserMerchantDao.getSysUserMerchantByMerchantId(merchantId);

				// 2、创建提醒VO
				for (SysUserMerchantVO sysUserMerchantVO : sysUserMerchantList) {
					sysRemindVO = createSysRemindVO(id, merchantId, sysUserMerchantVO.getUserId(),
							Constant.MATERIALS_MARK_REMIND_CONTENT);
					sysRemindList.add(sysRemindVO);
				}
			}
		}

		// 3、批量插入提醒
		if (!CollectionUtils.isEmpty(sysRemindList)) {
			sysRemindDao.batchInsertSysRemind(sysRemindList);
		}
	}

	private SysRemindVO createSysRemindVO(Integer id, Integer merchantId, Integer userId, String content) {
		SysRemindVO sysRemindVO = new SysRemindVO();
		sysRemindVO.setMerchantId(merchantId);
		sysRemindVO.setUserId(userId);
		sysRemindVO.setPriorityLevelType("01");
		sysRemindVO.setItemType("01");
		sysRemindVO.setItemContent(content);
		sysRemindVO.setItemStatus("1");
		sysRemindVO.setProcessId(id);
		sysRemindVO.setIsEnable("1");
		sysRemindVO.setInsertBy(Constant.DEFAULTUSERID);
		sysRemindVO.setInsertTime(new Date());
		sysRemindVO.setUpdateBy(Constant.DEFAULTUSERID);
		sysRemindVO.setUpdateTime(new Date());
		return sysRemindVO;
	}

	/**
	 * @throws Exception
	 * @Title cardDeliveryRemind
	 * @Description 卡配送提醒
	 * @see com.yazuo.erp.fes.service.FesOnlineProgramService#cardDeliveryRemind()
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void cardDeliveryRemind() throws Exception {

		Date today = new Date();
		Map<String, Object> beforeDays = traCalendarDao.beforeDays(today, 20);
		Date calendarDate = null; // 20天前日期
		if (null != beforeDays) {
			calendarDate = (Date) beforeDays.get("calendar_date");
		} else {
			throw new Exception("查询日期出错");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(calendarDate);
		List<Map<String, Object>> notOnlineMerchantList = fesOnlineProgramDao.getNotOnlineMerchant("entity_cards", date);

		List<SysRemindVO> sysRemindList = new ArrayList<SysRemindVO>();// 提醒列表
		SysRemindVO sysRemindVO = null;
		for (Map<String, Object> map : notOnlineMerchantList) {
			String onlineProcessStatus = (String) map.get("online_process_status");
			if (!StringUtils.equals(onlineProcessStatus, "04")) {
				Integer merchantId = (Integer) map.get("merchant_id");
				Integer id = (Integer) map.get("id");

				// 1、查询商户对应的前端用户
				List<SysUserMerchantVO> sysUserMerchantList = sysUserMerchantDao.getSysUserMerchantByMerchantId(merchantId);

				// 2、创建提醒VO
				for (SysUserMerchantVO sysUserMerchantVO : sysUserMerchantList) {
					sysRemindVO = createSysRemindVO(id, merchantId, sysUserMerchantVO.getUserId(),
							Constant.CARD_DELIVERY_REMIND_CONTENT);
					sysRemindList.add(sysRemindVO);
				}
			}
		}

		// 3、批量插入提醒
		if (!CollectionUtils.isEmpty(sysRemindList)) {
			sysRemindDao.batchInsertSysRemind(sysRemindList);
		}
	}

	/**
	 * @throws Exception
	 * @Title equipmentDeliveryRemind
	 * @Description 设备配送提醒
	 * @see com.yazuo.erp.fes.service.FesOnlineProgramService#equipmentDeliveryRemind()
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void equipmentDeliveryRemind() throws Exception {

		Date today = new Date();
		Map<String, Object> beforeDays = traCalendarDao.beforeDays(today, 27);
		Date calendarDate = null; // 27天前日期
		if (null != beforeDays) {
			calendarDate = (Date) beforeDays.get("calendar_date");
		} else {
			throw new Exception("查询日期出错");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(calendarDate);
		List<Map<String, Object>> notOnlineMerchantList = fesOnlineProgramDao
				.getNotOnlineMerchant("equipment_distribution", date);

		List<SysRemindVO> sysRemindList = new ArrayList<SysRemindVO>();// 提醒列表
		SysRemindVO sysRemindVO = null;
		for (Map<String, Object> map : notOnlineMerchantList) {
			String onlineProcessStatus = (String) map.get("online_process_status");
			if (!StringUtils.equals(onlineProcessStatus, "04") && !StringUtils.equals(onlineProcessStatus, "15")) {
				Integer merchantId = (Integer) map.get("merchant_id");
				Integer id = (Integer) map.get("id");

				// 1、查询商户对应的前端用户
				List<SysUserMerchantVO> sysUserMerchantList = sysUserMerchantDao.getSysUserMerchantByMerchantId(merchantId);

				// 2、创建提醒VO
				for (SysUserMerchantVO sysUserMerchantVO : sysUserMerchantList) {
					sysRemindVO = createSysRemindVO(id, merchantId, sysUserMerchantVO.getUserId(),
							Constant.EQUIPMENT_DELIVERY_REMIND_CONTENT);
					sysRemindList.add(sysRemindVO);
				}

			}
		}
		// 3、批量插入提醒
		if (!CollectionUtils.isEmpty(sysRemindList)) {
			sysRemindDao.batchInsertSysRemind(sysRemindList);
		}
	}

	/**
	 * @throws Exception
	 * @Title draftProgramRemind
	 * @Description 会员方案洽谈提醒
	 * @see com.yazuo.erp.fes.service.FesOnlineProgramService#draftProgramRemind()
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void draftProgramRemind() throws Exception {
		Date today = new Date();
		Map<String, Object> beforeDays = traCalendarDao.beforeDays(today, 7);
		Date calendarDate = null; // 7天前日期
		if (null != beforeDays) {
			calendarDate = (Date) beforeDays.get("calendar_date");
		} else {
			throw new Exception("查询日期出错");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(calendarDate);
		List<Map<String, Object>> notOnlineMerchantList = fesOnlineProgramDao.getNotOnlineMerchant("draft_program", date);

		List<SysRemindVO> sysRemindList = new ArrayList<SysRemindVO>();// 提醒列表
		SysRemindVO sysRemindVO = null;
		for (Map<String, Object> map : notOnlineMerchantList) {
			String onlineProcessStatus = (String) map.get("online_process_status");
			if (!StringUtils.equals(onlineProcessStatus, "04")) {
				Integer merchantId = (Integer) map.get("merchant_id");
				Integer id = (Integer) map.get("id");
				// 1、查询商户对应的前端用户
				List<SysUserMerchantVO> sysUserMerchantList = sysUserMerchantDao.getSysUserMerchantByMerchantId(merchantId);

				// 2、创建提醒VO
				for (SysUserMerchantVO sysUserMerchantVO : sysUserMerchantList) {
					sysRemindVO = createSysRemindVO(id, merchantId, sysUserMerchantVO.getUserId(),
							Constant.DRAFT_PROGRAM_REMIND_CONTENT);
					sysRemindList.add(sysRemindVO);
				}
			}
		}

		// 3、批量插入提醒
		if (!CollectionUtils.isEmpty(sysRemindList)) {
			sysRemindDao.batchInsertSysRemind(sysRemindList);
		}
	}

	/**
	 * @throws Exception
	 * @Title onlineBefore5daysRemind
	 * @Description 上线5天时提醒
	 * @see com.yazuo.erp.fes.service.FesOnlineProgramService#onlineBefore5daysRemind()
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void onlineBefore5daysRemind() throws Exception {

		Date today = new Date();
		Map<String, Object> beforeDays = traCalendarDao.beforeDays(today, 25);
		Date calendarDate = null; // 25天前日期
		if (null != beforeDays) {
			calendarDate = (Date) beforeDays.get("calendar_date");
		} else {
			throw new Exception("查询日期出错");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(calendarDate);
		List<Map<String, Object>> notOnlineMerchantList = fesOnlineProgramDao.getNotOnlineMerchant("online", date);

		List<SysRemindVO> sysRemindList = new ArrayList<SysRemindVO>();// 提醒列表
		SysRemindVO sysRemindVO = null;
		for (Map<String, Object> map : notOnlineMerchantList) {
			String onlineProcessStatus = (String) map.get("online_process_status");
			if (!StringUtils.equals(onlineProcessStatus, "04")) {
				Integer merchantId = (Integer) map.get("merchant_id");
				Integer id = (Integer) map.get("id");
				// 1、查询商户对应的前端用户
				List<SysUserMerchantVO> sysUserMerchantList = sysUserMerchantDao.getSysUserMerchantByMerchantId(merchantId);

				// 2、创建提醒VO
				for (SysUserMerchantVO sysUserMerchantVO : sysUserMerchantList) {
					sysRemindVO = createSysRemindVO(id, merchantId, sysUserMerchantVO.getUserId(),
							Constant.ONLINE_BEFORE5DAYS_REMIND_CONTENT);
					sysRemindList.add(sysRemindVO);
				}
			}
		}

		// 3、批量插入提醒
		if (!CollectionUtils.isEmpty(sysRemindList)) {
			sysRemindDao.batchInsertSysRemind(sysRemindList);
		}
	}

	/**
	 * @throws Exception
	 * @Title onlineRemind
	 * @Description 上线提醒
	 * @see com.yazuo.erp.fes.service.FesOnlineProgramService#onlineRemind()
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void onlineRemind() throws Exception {

		Date today = new Date();
		Map<String, Object> beforeDays = traCalendarDao.beforeDays(today, 30);
		Date calendarDate = null; // 25天前日期
		if (null != beforeDays) {
			calendarDate = (Date) beforeDays.get("calendar_date");
		} else {
			throw new Exception("查询日期出错");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(calendarDate);
		List<Map<String, Object>> notOnlineMerchantList = fesOnlineProgramDao.getNotOnlineMerchant("online", date);

		List<SysRemindVO> sysRemindList = new ArrayList<SysRemindVO>();// 提醒列表
		SysRemindVO sysRemindVO = null;
		for (Map<String, Object> map : notOnlineMerchantList) {
			String onlineProcessStatus = (String) map.get("online_process_status");
			if (!StringUtils.equals(onlineProcessStatus, "04")) {
				Integer merchantId = (Integer) map.get("merchant_id");
				Integer id = (Integer) map.get("id");
				List<SysUserMerchantVO> sysUserMerchantList = sysUserMerchantDao.getSysUserMerchantByMerchantId(merchantId);

				// 2、创建提醒VO
				for (SysUserMerchantVO sysUserMerchantVO : sysUserMerchantList) {
					sysRemindVO = createSysRemindVO(id, merchantId, sysUserMerchantVO.getUserId(), Constant.ONLINE_REMIND_CONTENT);
					sysRemindList.add(sysRemindVO);
				}
			}
		}

		// 3、批量插入提醒
		if (!CollectionUtils.isEmpty(sysRemindList)) {
			sysRemindDao.batchInsertSysRemind(sysRemindList);
		}
	}
}
