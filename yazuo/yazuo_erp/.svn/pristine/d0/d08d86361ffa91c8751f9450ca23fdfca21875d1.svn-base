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

package com.yazuo.erp.mkt.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.mkt.service.MktContactService;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.mkt.vo.MktShopSurveyVO;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.StringUtils;

/**
 *通讯录相关页面 
 */

/**
 * 通讯录 相关业务操作
 * 
 * @author
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)
@RequestMapping({"contact", "mktContact"})
public class MktContactController {

	private static final Log LOG = LogFactory.getLog(MktContactController.class);
	@Resource
	private MktContactService mktContactService;
	@Resource
	private SysDictionaryService sysDictionaryService;

	/**
	 * 调研单 访谈单模块， 点击填加联系人的时候调用
	 * @param merchantId
	 */
	@RequestMapping(value = "loadMktContact", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult loadMktContact(@RequestBody MktContactVO mktContactVO) {
		MktContactVO mktContact = mktContactService.loadMktContact(mktContactVO);
		return new JsonResult(true).setData(mktContact);
	}
	
	/**
	 * 保存修改通讯录
	 */
	@RequestMapping(value = "saveMktContact", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveMktContact(@RequestBody MktContactVO mktContact, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		MktContactVO contact = mktContactService.saveMktContact(mktContact, user);
		return new JsonResult(contact!=null).setData(contact);
	}

	/**
	 * 列表显示 联系人
	 */
	@RequestMapping(value = {"listContacts", "listSynContacts"}, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult listSynContacts(@RequestBody Map<String, Object> paramMap, HttpSession session) {
		SysUserVO user = (SysUserVO) session.getAttribute(Constant.SESSION_USER);

		Integer pageNumber = Integer.parseInt(String.valueOf(paramMap.get("pageNumber")));
		Integer pageSize = Integer.parseInt(String.valueOf(paramMap.get("pageSize")));
		PageHelper.startPage(pageNumber, pageSize, true);

		// update by gaoshan，userId为过滤条件，应由前端上送，不应后端写死
		// paramMap.put("userId", user.getId());
		Page<MktContactVO> pageList = mktContactService.getMktContacts(paramMap);
		JsonResult result = new JsonResult(true);
		result.putData("rows", pageList);
		result.putData("totalSize", pageList.getTotal());
		result.setMessage("联系人查询成功！");
		return result;
	}
	
	/**
	 * 查找 联系人， 第十步发邮件的时候用到
	 * 参数： {'merchantId':  int}
	 */
	@RequestMapping(value = "searchContacts", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult searchContacts(@RequestBody Map<String, Object> paramMap) {
		Integer pageNumber = 1;
		Integer pageSize = 10000; //应该不会有太多的人
		PageHelper.startPage(pageNumber, pageSize, true);
		Page<MktContactVO> pageList = mktContactService.getMktContacts(paramMap);
		JsonResult result = new JsonResult(true);
		result.putData("rows", pageList);
		result.putData("totalSize", pageList.getTotal());
		result.setMessage("联系人查询成功！");
		return result;
	}

	@RequestMapping(value = "initRoleType", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult initRoleType() {
		List<Map<String, Object>> list = sysDictionaryService.querySysDictionaryByTypeStd("00000065");
		return new JsonResult(true).setData(list);
	}

	@RequestMapping(value = "editContact", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult editContact(String contactIdStr) {
		JsonResult result = new JsonResult(true);
		MktContactVO mktContactVO = null;
		if (!StringUtils.isEmpty(contactIdStr)) {
			Integer contactId = Integer.parseInt(contactIdStr);
			mktContactVO = mktContactService.getMktContactById(contactId);
		}
		result.setData(mktContactVO != null ? mktContactVO : new MktContactVO());
		return result;
	}

	/**
	 * @Description 根据商户ID查询通讯录信息
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "queryContact", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult queryContact(@RequestBody MktContactVO mktContactVO) {
		List<Map<String, Object>> list = this.mktContactService.queryContact(mktContactVO);
		return new JsonResult().setFlag(true).setData(list);
	}

	/**
	 * 删除 联系人 
	 */
	@RequestMapping(value = "deleteMktContact/{id}", method = {RequestMethod.POST, RequestMethod.GET })		
	@ResponseBody
	public JsonResult deleteMktContact(@PathVariable int id) {
		return new JsonResult(this.mktContactService.updateMktContactForDelete(id), true);
	}
}
