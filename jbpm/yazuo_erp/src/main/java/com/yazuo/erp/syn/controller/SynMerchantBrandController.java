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

import java.util.*;
import com.yazuo.erp.syn.vo.*;
import com.yazuo.erp.syn.dao.*;

/**
 * @Description TODO
 * @author erp team
 * @date 
 */
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;

import com.yazuo.erp.syn.service.SynMerchantBrandService;
import com.yazuo.erp.system.controller.SysRoleController;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysRoleVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 商户-minierp商户关系表 相关业务操作
 * @author 
 */
@Controller
@RequestMapping("synMerchantBrand")
public class SynMerchantBrandController {
	
	private static final Log LOG = LogFactory.getLog(SynMerchantBrandController.class);
	@Resource
	private SynMerchantBrandService synMerchantBrandService;
	
	/**
	 * 保存修改商户-minierp商户关系表
	 */
	@RequestMapping(value = "saveSynMerchantBrand", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveSynMerchantBrand(@RequestBody SynMerchantBrandVO synMerchantBrand) {
		int count = synMerchantBrandService.saveSynMerchantBrand(synMerchantBrand);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	
	/**
	 * 列表显示 商户-minierp商户关系表 
	 */
	@RequestMapping(value = "listSynMerchantBrands/{pageNumber}/{pageSize}", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listSynMerchantBrands(@PathVariable int pageNumber, @PathVariable int pageSize) {
		Map<String, Object> paramerMap = new HashMap<String, Object>();
		paramerMap.put("pageNumber", pageNumber);
		paramerMap.put("pageSize", pageSize);
		//Page<Map<String, Object>> pageList = synMerchantBrandService.getSynMerchantBrands(paramerMap);
		//Map<String, Object> dataMap = new HashMap<String, Object>();
		//dataMap.put(Constant.TOTAL_SIZE, pageList.getTotal());
		//dataMap.put(Constant.ROWS, pageList.getResult());
//		return new JsonResult(true).setData(dataMap);
		return null;
	}
	
	/**
	 * 删除 商户-minierp商户关系表 
	 */
	@RequestMapping(value = "deleteSynMerchantBrand/{id}", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult deleteSynMerchantBrand(@PathVariable int id) {
		int count = synMerchantBrandService.deleteSynMerchantBrandById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
