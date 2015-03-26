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

package fes.service;


import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.Rollback;

import base.JUnitDaoBaseWithTrans;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.controller.FesActivityController;
import com.yazuo.erp.fes.controller.FesEntityCardController;
import com.yazuo.erp.fes.vo.FesMarketingActivityVO;
import com.yazuo.erp.fes.vo.FesOpenCardApplicationDtlVO;
import com.yazuo.erp.fes.vo.FesRemarkVO;
import com.yazuo.erp.fes.vo.FesStowageDtlVO;
import com.yazuo.erp.system.vo.SysRemindVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description 
 */
public class FesEntityCardServiceTest extends  JUnitDaoBaseWithTrans {
	 
	@Resource
	private FesEntityCardController fesEntityCardController;
	
	@Resource
	private FesActivityController fesActivityController;
	
	/**
	 * 保存开卡明细
	 */
	@Test
	public void saveOpenApply() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		String fesTrainPlan = "{\"processId\":\"31\", \"merchantId\":24,\"cardName\":\"积分会员2\",\"cardType\":1,\"cardAmount\":10}";
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(109);
		request.getSession().setAttribute(Constant.SESSION_USER, sessionUser);

		FesOpenCardApplicationDtlVO cardApplyDtl = mapper.readValue(fesTrainPlan.getBytes(), FesOpenCardApplicationDtlVO.class);
		this.printJson(cardApplyDtl);
		JsonResult saveOpenApply = fesEntityCardController.saveOpenApply(cardApplyDtl, sessionUser);
		this.printJson(saveOpenApply);
	}
	
	
	/**
	 * 保存配载信息
	 */
	@Test
	public void saveStowage() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		String fesTrainPlan = "{\"processId\":\"31\", \"logisticsNum\":\"E1903434\",\"category\":1,\"amount\":10}";
		
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(109);
		request.getSession().setAttribute(Constant.SESSION_USER, sessionUser);

		FesStowageDtlVO cardApplyDtl = mapper.readValue(fesTrainPlan.getBytes(), FesStowageDtlVO.class);
		this.printJson(cardApplyDtl);
//		JsonResult result = fesEntityCardController.saveFesStowage(fesStowage, session);
//		this.printJson(result);
	}
	
	/**
	 * 保存营销活动
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void saveActivtiy() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		String fesTrainPlan = "{\"merchantId\":\"21\", \"activityTitle\":\"新会员奖励营销活动22\",\"activityType\":1,\"attachmentId\":1,\"description\":\"测试创建活动\"}";

		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(109);
		request.getSession().setAttribute(Constant.SESSION_USER, sessionUser);

		FesMarketingActivityVO remark = mapper.readValue(fesTrainPlan.getBytes(), FesMarketingActivityVO.class);
		this.printJson(remark);
		JsonResult result = null;//fesActivityController.saveActivity(remark, sessionUser);
		this.printJson(result);
	}
	
	/**
	 * 查询营销活动
	 */
	@Test
	@Rollback(true)
	public void listActivtiy() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		String fesTrainPlan = "{\"merchantName\":\"雅\",\"marketingActivityStatus\":\"2\"}";
		
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(109);
		request.getSession().setAttribute(Constant.SESSION_USER, sessionUser);

		JSONObject json = JSONObject.fromObject(fesTrainPlan);
		JsonResult result =null;// fesActivityController.listActivity(json.getString("merchantName"), json.getString("marketingActivityStatus"), 10, 1);
		this.printJson(result);
	}
	
	
	/**
	 * 更改营销活动状态
	 */
	@Test
	@Rollback(true)
	@Ignore
	public void updateActivtiy() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		String fesTrainPlan = "{\"id\":2,\"marketingActivityStatus\":\"2\"}";
		
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(109);
		request.getSession().setAttribute(Constant.SESSION_USER, sessionUser);

		JSONObject json = JSONObject.fromObject(fesTrainPlan);
		JsonResult result =null;// fesActivityController.updateActivityStatus(json.getInt("id"), json.getString("marketingActivityStatus"), sessionUser);
		this.printJson(result);
	}
	
	@Test
	public void listRemind() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		String fesTrainPlan = "{\"merchantName\":\"\",\"itemStatus\":\"1\"}";
		
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(109);
		request.getSession().setAttribute(Constant.SESSION_USER, sessionUser);

		JSONObject json = JSONObject.fromObject(fesTrainPlan);
		SysRemindVO remind = new SysRemindVO();
		remind.setMerchantName(String.valueOf(json.get("merchantName")));
		remind.setItemStatus(String.valueOf(json.get("itemStatus")));
		JsonResult result = null;//fesActivityController.listRemind(remind, sessionUser, 10, 1);
		this.printJson(result);
	}
	
}