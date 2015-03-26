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

package com.yazuo.erp.bes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.bes.service.BesTalkingSkillsService;
import com.yazuo.erp.bes.vo.BesTalkingSkillsVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.vo.SysProductOperationVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 话术表 相关业务操作
 * @author 
 */
@Controller
@RequestMapping("besTalkingSkills")
public class BesTalkingSkillsController {
	
	private static final Log LOG = LogFactory.getLog(BesTalkingSkillsController.class);
	@Resource
	private BesTalkingSkillsService besTalkingSkillsService;
	
	/**
	 * 展示 话术表
	 */
	@RequestMapping(value = "loadBesTalkingSkills", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult loadBesTalkingSkills(@RequestBody BesTalkingSkillsVO besTalkingSkills) {
		BesTalkingSkillsVO besTalkingSkillsVO = besTalkingSkillsService.loadBesTalkingSkills(besTalkingSkills);
		return new JsonResult(true).setData(besTalkingSkillsVO);
	}
	
	/**
	 * 保存修改话术表
	 */
	@RequestMapping(value = "saveBesTalkingSkills", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveBesTalkingSkills(@RequestBody BesTalkingSkillsVO besTalkingSkills, HttpSession session) {
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		int count = besTalkingSkillsService.saveOrUpdateBesTalkingSkills(besTalkingSkills, sessionUser);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	
	/**
	 * 列表显示 话术表 
	 */
	@RequestMapping(value = "listBesTalkingSkills", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listBesTalkingSkills(@RequestBody BesTalkingSkillsVO besTalkingSkills) {
		
		assertNotNull("页号不允许为空!", besTalkingSkills.getPageNumber());
		assertNotNull("页总数不允许为空!", besTalkingSkills.getPageSize());
		
		PageHelper.startPage(besTalkingSkills.getPageNumber(), besTalkingSkills.getPageSize(), true);
		List<BesTalkingSkillsVO> list = besTalkingSkillsService.getBesTalkingSkillss(besTalkingSkills);
		Page<BesTalkingSkillsVO> pageList = (Page<BesTalkingSkillsVO>)list;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, pageList ==null ? 0: pageList.getTotal());
		dataMap.put(Constant.ROWS, pageList ==null ? null: pageList.getResult());
		dataMap.put("parameResources", besTalkingSkillsService.getResourceByParent(besTalkingSkills));
		return new JsonResult(true).setData(dataMap);
	}
	 
	/**
	 * 删除 话术表 
	 */
	@RequestMapping(value = "deleteBesTalkingSkills/{id}", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult deleteBesTalkingSkills(@PathVariable int id) {
		int count = besTalkingSkillsService.deleteBesTalkingSkillsById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
