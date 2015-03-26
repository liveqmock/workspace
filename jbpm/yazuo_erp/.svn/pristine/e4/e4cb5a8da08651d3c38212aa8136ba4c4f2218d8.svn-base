package com.yazuo.erp.demo.controller;

import java.io.IOException;
import java.sql.SQLException;
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
import com.yazuo.erp.demo.service.RunScriptService;
import com.yazuo.erp.demo.vo.SQLAdapter;

/**
 * 工具类
 * 
 */
@Controller
@RequestMapping("/tools")
public class ToolsController {

	@Resource
	private RunScriptService runscript;

	private static final Log LOG = LogFactory.getLog("erp");

	 /**
	  * 根据组id获取组下面的所有人员
	  * @param groupId
	  */
	@RequestMapping(value = "runScript", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult runScript(@RequestBody String script) {
		JsonResult jsonResult = new JsonResult();
		List<Map<String, String>> updateSql = runscript.updateSql(new SQLAdapter(script));
		jsonResult.setFlag(jsonResult!=null);
		jsonResult.setData(updateSql);
	    return jsonResult;
	}
}