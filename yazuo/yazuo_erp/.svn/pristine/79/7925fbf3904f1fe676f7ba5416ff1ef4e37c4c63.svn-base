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

package com.yazuo.erp.syn.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.syn.service.SynMembershipCardService;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */

/**
 * 会员卡信息 相关业务操作
 * 
 * @author
 */
@Controller
@RequestMapping("synMembershipCard")
public class SynMembershipCardController {

	private static final Log LOG = LogFactory.getLog(SynMembershipCardController.class);
	@Resource
	private SynMembershipCardService synMembershipCardService;

	/**
	 * 列表显示 会员卡信息
	 */
	@RequestMapping(value = "listSynMembershipCards", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult listSynMembershipCards(@RequestParam(value = "pageNumber", required = false) int pageNumber,
			@RequestParam(value = "pageSize", required = false) int pageSize,
			@RequestParam(value = "merchantId", required = false) Integer merchantId) {
		Page<Map<String, Object>> cardInfoList = (Page<Map<String, Object>>) synMembershipCardService.getSynMembershipCardInfo(
				merchantId, pageNumber, pageSize);

		return new JsonResult(true).setMessage("查询成功").putData("totalSize", cardInfoList.getTotal())
				.putData("rows", cardInfoList);
	}

}
