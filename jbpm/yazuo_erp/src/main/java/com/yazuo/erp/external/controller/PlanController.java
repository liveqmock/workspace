/**
 * @Description 对外接口，工作管理相关操作
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.external.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;

import com.yazuo.erp.external.exception.ExternalBizException;
import com.yazuo.erp.external.service.PlanService;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 工作计划 相关业务操作
 * 
 * @author
 */
@Controller
@RequestMapping("external/fesPlan")
public class PlanController {

	private static final Log LOG = LogFactory.getLog(PlanController.class);
	@Resource
	private PlanService fesPlanService;

	/**
	 * 保存工作计划
	 */
	@RequestMapping(value = "saveFesPlan", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult saveFesPlan(@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "ciphertext", required = true) String ciphertext) {
		JsonResult jr = null;
		try {
			LOG.info("[工作计划添加]请求：" + "{key:" + key + ",ciphertext:" + ciphertext + "}");
			int count = fesPlanService.saveFesPlan(ciphertext);
			jr = new JsonResult(count > 0).setMessage(count > 0 ? "添加成功!" : "添加失败!");
			LOG.info("[工作计划添加]响应：" + JsonResult.getJsonString(jr));

			return jr;
		} catch (ExternalBizException e) {
			jr = new JsonResult(false).setMessage(e.getMessage());
			LOG.error("[工作计划添加]响应：" + JsonResult.getJsonString(jr));
			LOG.error(e.getMessage());
			e.printStackTrace();
			return jr;
		} catch (Exception e) {
			jr = new JsonResult(false).setMessage("修改失败!");
			LOG.error("[工作计划添加]响应：" + JsonResult.getJsonString(jr));
			LOG.error(e.getMessage());
			e.printStackTrace();
			return jr;
		}
	}

	/**
	 * 修改工作计划
	 */
	@RequestMapping(value = "updateFesPlan", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult updateFesPlan(@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "ciphertext", required = true) String ciphertext) {
		JsonResult jr = null;
		try {
			LOG.info("[工作计划修改]请求：" + "{key:" + key + ",ciphertext:" + ciphertext + "}");
			int count = fesPlanService.updateFesPlan(ciphertext);
			jr = new JsonResult(count > 0).setMessage(count > 0 ? "修改成功!" : "修改失败!");
			LOG.info("[工作计划修改]响应：" + JsonResult.getJsonString(jr));
			return jr;
		} catch (ExternalBizException e) {
			jr = new JsonResult(false).setMessage(e.getMessage());
			LOG.error("[工作计划修改]响应：" + JsonResult.getJsonString(jr));
			LOG.error(e.getMessage());
			e.printStackTrace();
			return jr;
		} catch (Exception e) {
			jr = new JsonResult(false).setMessage("修改失败!");
			LOG.error("[工作计划修改]响应：" + JsonResult.getJsonString(jr));
			LOG.error(e.getMessage());
			e.printStackTrace();
			return jr;
		}
	}
	
	/**
	 * 放弃工作计划
	 */
	@RequestMapping(value = "updateAbandonFesPlanById", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult updateAbandonFesPlanById(@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "ciphertext", required = true) String ciphertext) {
		JsonResult jr = null;
		try {
			LOG.info("[放弃工作计划]请求：" + "{key:" + key + ",ciphertext:" + ciphertext + "}");
			int count = fesPlanService.updateAbandonFesPlanById(ciphertext);
			jr = new JsonResult(count > 0).setMessage(count > 0 ? "放弃成功!" : "放弃失败!");
			LOG.info("[放弃工作计划]响应：" + JsonResult.getJsonString(jr));
			return jr;
		} catch (ExternalBizException e) {
			jr = new JsonResult(false).setMessage(e.getMessage());
			LOG.error("[放弃工作计划]响应：" + JsonResult.getJsonString(jr));
			LOG.error(e.getMessage());
			e.printStackTrace();
			return jr;
		} catch (Exception e) {
			jr = new JsonResult(false).setMessage("修改失败!");
			LOG.error("[放弃工作计划]响应：" + JsonResult.getJsonString(jr));
			LOG.error(e.getMessage());
			e.printStackTrace();
			return jr;
		}
	}
	
	/**
	 * 延期工作计划
	 */
	@RequestMapping(value = "updateDelayFesPlanById", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult updateDelayFesPlanById(@RequestParam(value = "key", required = true) String key,
			@RequestParam(value = "ciphertext", required = true) String ciphertext) {
		JsonResult jr = null;
		try {
			LOG.info("[延期工作计划]请求：" + "{key:" + key + ",ciphertext:" + ciphertext + "}");
			int count = fesPlanService.updateDelayFesPlanById(ciphertext);
			jr = new JsonResult(count > 0).setMessage(count > 0 ? "延期成功!" : "延期失败!");
			LOG.info("[延期工作计划]响应：" + JsonResult.getJsonString(jr));
			return jr;
		} catch (ExternalBizException e) {
			jr = new JsonResult(false).setMessage(e.getMessage());
			LOG.error("[延期工作计划]响应：" + JsonResult.getJsonString(jr));
			LOG.error(e.getMessage());
			e.printStackTrace();
			return jr;
		} catch (Exception e) {
			jr = new JsonResult(false).setMessage("修改失败!");
			LOG.error("[延期工作计划]响应：" + JsonResult.getJsonString(jr));
			LOG.error(e.getMessage());
			e.printStackTrace();
			return jr;
		}
	}

	/**
	 * 删除 工作计划
	 */
	@RequestMapping(value = "deleteFesPlan/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public JsonResult deleteFesPlan(@PathVariable int id) {
		int count = fesPlanService.deleteFesPlanById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
