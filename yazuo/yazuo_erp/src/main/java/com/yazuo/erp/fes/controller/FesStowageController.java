/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
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

package com.yazuo.erp.fes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.base.SendMessageVoid;
import com.yazuo.erp.fes.service.FesStowageService;
import com.yazuo.erp.fes.vo.FesOpenCardApplicationVO;
import com.yazuo.erp.fes.vo.FesStowageVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.mkt.service.MktContactService;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.syn.vo.ComplexSynMerchantVO;
import com.yazuo.erp.syn.vo.HealthDegree;
import com.yazuo.erp.system.dao.SysDictionaryDao;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.service.SysUserMerchantService;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * @author erp team
 * @date 
 */

/**
 *  我的主页 相关业务操作
 * @author 
 */
@Controller
@RequestMapping("fesStowage")
public class FesStowageController {
	
	private static final Log LOG = LogFactory.getLog(FesStowageController.class);
	@Resource private FesStowageService fesStowageService;			
	@Resource private SysDictionaryService sysDictionaryService;			
	@Resource private MktContactService mktContactService;			
	@Resource private SendMessageVoid sendMessageVoid;			
	/**
	 * 配送信息保存
	 */
	@Deprecated
	@RequestMapping(value = "saveFesStowage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult saveFesStowage(@RequestBody FesStowageVO fesStowage, HttpSession session) {
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		JsonResult result = fesStowageService.saveFesStowage(fesStowage, sessionUser); 
		return result;
	}
	/**
	 * 测试发短信
	 */
	@Deprecated
	@RequestMapping(value = "testSendSMS", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult testSendSMS(@RequestBody FesStowageVO fesStowage, HttpSession session) {
		for (int i = 0; i < 1; i++) {
			sendMessageVoid.sendMessage("test send sms deadlock!", "13521768697",  LogFactory.getLog("sms"));
		}
		LOG.info("短信都发完成了！");
		return new JsonResult(true);
	}
	
	/**
	 * 设备配送
	 * 保存多个配送信息
	 */
	@RequestMapping(value = "saveFesStowages", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult saveFesStowages(@RequestBody FesStowageVO[] fesStowages, HttpSession session) {
//		LOG.info(JsonResult.getJsonStringHandleNull(fesStowages)); 
//		if(true) return null;
		for (FesStowageVO fesStowageVO : fesStowages) {
			fesStowageVO.setDistributeFlag(1);
		}
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		JsonResult result = fesStowageService.saveFesStowages(fesStowages, sessionUser); 
		return result;
	}
	
	/**
	 * 设备配送明细弹层初始化
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "loadFesStowage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult loadFesStowage(@RequestBody final FesStowageVO fesStowage, HttpSession session) {
		Assert.assertNotNull("商户ID不能为空！", fesStowage.getMerchantId());
		final List<Map<String, Object>> dicForStowage = this.fesStowageService.querySysDicForStowage();
		final List<Map<String, Object>> sysDictionaryByType = this.sysDictionaryService.querySysDictionaryByTypeStd("00000115");
		final List<Map<String, Object>> queryContact = this.mktContactService.queryContactsStd(new MktContactVO(){{setMerchantId(fesStowage.getMerchantId());}});
		return new JsonResult(new HashMap<String, Object>() {
			{
				put("category", dicForStowage);//品类
				put("logisticCompany", sysDictionaryByType);//快递公司
				put("contacts", queryContact);//快递公司
			}
		}, dicForStowage.size() > 0);
	}
}
