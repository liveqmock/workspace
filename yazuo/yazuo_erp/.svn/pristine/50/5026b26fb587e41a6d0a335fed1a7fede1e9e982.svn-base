/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.erp.system.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.system.service.SysDictionaryService;

/**
 * @Description 数据字典相关的业务操作
 * @author gaoshan
 * @date 2014-6-16 上午9:56:44
 */
@Controller
@RequestMapping("dictionary")
public class SysDictionaryController {

	private static final Log LOG = LogFactory.getLog(SysDictionaryController.class);

	@Resource
	private SysDictionaryService sysDictionaryService;

	/**
	 * @Description 根据类型（例如，00000001）查询有效的数据字典
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "querySysDictionaryByType", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult querySysDictionaryByType(@RequestParam(value = "dictionaryType", required = true) String dictionaryType) {
		List<Map<String, Object>> list = this.sysDictionaryService.querySysDictionaryByType(dictionaryType);

		JsonResult json = new JsonResult();
		json.setData(list);
		json.setFlag(list != null && list.size() > 0);
		json.setMessage(list != null && list.size() > 0 ? "查询成功!" : "查询失败!");
		return json;
	}

	/**
	 * @Description 根据类型列表查询有效的数据字典
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "querySysDictionaryByTypeList", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult querySysDictionaryByTypeList(
			@RequestBody List<String> dictionaryTypeList) {
		Map<String, Object> map = this.sysDictionaryService.querySysDictionaryByTypeList(dictionaryTypeList);

		JsonResult json = new JsonResult();
		json.setData(map);
		json.setFlag(map != null && map.size() > 0);
		json.setMessage(map != null && map.size() > 0 ? "查询成功!" : "查询失败!");
		return json;
	}

}
