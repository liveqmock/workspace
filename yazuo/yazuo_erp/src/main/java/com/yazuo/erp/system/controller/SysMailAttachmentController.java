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

package com.yazuo.erp.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.mkt.service.MktContactService;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysMailTemplateService;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysDictionaryVO;
import com.yazuo.erp.system.vo.SysMailTemplateVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 邮件模板及附件 相关业务操作
 * @author 
 */
@Controller
@RequestMapping("mailAndAttachment")
public class SysMailAttachmentController {
	
	private static final Log LOG = LogFactory.getLog(SysMailAttachmentController.class);
	@Resource
	private SysMailTemplateService sysMailTemplateService;
	@Resource
	private SysDictionaryService sysDictionaryService;
	@Resource
	private SysAttachmentService sysAttachmentService;
	
	/**
	 * 保存修改邮件模板
	 */
	@RequestMapping(value = "saveMailTemplate", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveSysMailTemplate(@RequestBody SysMailTemplateVO sysMailTemplate, HttpServletRequest request) {
		SysUserVO user = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		sysMailTemplate.setUpdateBy(user.getId());
		sysMailTemplate.setUpdateTime(new Date());
		
		if (sysMailTemplate.getId()!=null && sysMailTemplate.getId() !=0) { // 修改
			int count = sysMailTemplateService.updateSysMailTemplate(sysMailTemplate);
			return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
		} else {
			sysMailTemplate.setInsertBy(user.getId());
			sysMailTemplate.setInsertTime(new Date());
			sysMailTemplate.setIsEnable("1");
			int count = sysMailTemplateService.saveSysMailTemplate(sysMailTemplate);
			return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
		}
	}
	
	/**
	 * 查询邮件模板
	 */
	@RequestMapping(value = "getSysMailTemplateById", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getSysMailTemplateById(@RequestBody SysMailTemplateVO sysMailTemplate, HttpServletRequest request) {
		return new JsonResult(sysMailTemplateService.getSysMailTemplateById(sysMailTemplate.getId()), true);
	}
	
	/**
	 * 列表显示 邮件模板 
	 */
	@RequestMapping(value = "listMailTemplates", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listSysMailTemplates(Integer pageNumber, Integer pageSize, @RequestParam(value="name", required=false) String name) {
		SysMailTemplateVO mail = new SysMailTemplateVO();
		mail.setName(name);
		mail.setIsEnable("1");
		// 分页
		PageHelper.startPage(pageNumber, pageSize, true);
		
		Page<SysMailTemplateVO> pageList = sysMailTemplateService.getSysMailTemplates(mail);

		JsonResult result = new JsonResult(true);
		result.putData(Constant.TOTAL_SIZE, pageList.getTotal());
		result.putData(Constant.ROWS, pageList);
		result.setMessage("邮件模板查询成功!");
		return result;
	}
	
	/**
	 * 删除 邮件模板 
	 */
	@RequestMapping(value = "deleteMailTemplate", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult deleteSysMailTemplate(Integer id) {
		int count = sysMailTemplateService.deleteSysMailTemplateById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
	
	//根据id取相应数据
	@RequestMapping(value = "edit", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult edit(Integer id) {
		SysMailTemplateVO mail = sysMailTemplateService.getSysMailTemplateById(id);
		return new JsonResult(true).setData(mail).setMessage("查询成功!");
	}
	
	@RequestMapping(value = "initType", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	@Deprecated
	public JsonResult initType(Integer id) {
		List<Map<String, Object>> list = sysDictionaryService.querySysDictionaryByTypeStd("00000060");
		return new JsonResult(true).setData(list).setMessage("查询成功!");
	}
	
	@SuppressWarnings("serial")
	@RequestMapping(value = "initParameter", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult initParameter() {
		final List<Map<String, Object>> roleType = sysDictionaryService.querySysDictionaryByTypeStd("00000065");
		CollectionUtils.transform(roleType, new Transformer() {
			public Object transform(Object input) {
				Map<String, Object> inputMap = (Map<String, Object>)input;
				return inputMap.get("text");
			}
		});
		CollectionUtils.filter(roleType, new Predicate() {//过滤掉其他
			public boolean evaluate(Object object) {
				return !object.toString().equals("其他");
			}
		});
		final List union = ListUtils.union(new ArrayList<String>(){{add("品牌名称");}}, roleType);
		return new JsonResult(union, true);
	}
	
	// 附件列表查询
	@RequestMapping(value = "listAttachment", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult listAttachment(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
		SysUserVO user = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		
		Integer pageSize = Integer.parseInt(String.valueOf(paramMap.get("pageSize")));
		Integer pageNumber = Integer.parseInt(String.valueOf(paramMap.get("pageNumber")));
		paramMap.put("userId", user.getId());
		paramMap.put("status", 1);//状态为可用的
		String attachmentType = paramMap.get("attachmentType").toString();
		//分页
		PageHelper.startPage(pageNumber, pageSize, true);
		
		Page<SysAttachmentVO> list = null;
		if (attachmentType.equals("plan")) { // 工作计划附件
			list = sysAttachmentService.queryPlanAttachment(paramMap);
		} else if (attachmentType.equals("online")) { //上线流程
			list = sysAttachmentService.queryOnlineAttachment(paramMap);
		} else { //营销活动申请
			list = sysAttachmentService.queryActivityAttachment(paramMap);
		}
		JsonResult result = new JsonResult();
		result.setFlag(true);
		result.putData(Constant.ROWS, list);
		result.putData(Constant.TOTAL_SIZE, list!=null && list.size()>0 ? list.getTotal():0);
		return result;
	}
	@Resource MktContactService mktContactService;
	@Resource SynMerchantService synMerchantService;
	/**
	 * 邮件模板文字替换
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "replaceMailTempment", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult replaceMailTempment(@RequestBody SynMerchantVO synMerchantVO, HttpServletRequest request) {
		
		final List<Map<String, Object>> roleType = sysDictionaryService.querySysDictionaryByTypeStd("00000065");
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		final Integer merchantId = synMerchantVO.getMerchantId();
		//品牌名称
		final SynMerchantVO synMerchantById = this.synMerchantService.getSynMerchantById(merchantId);
		list.add(new HashMap<String, String>() {
			{
				put("text", "品牌名称");
				put("value", synMerchantById.getMerchantName());
			}
		});
		//其他
		for (final Map<String, Object> mapDic : roleType) {
			List<Map<String, Object>> mktContactsMap = this.mktContactService.getMktContactsMap(new MktContactVO() {
				{
					setMerchantId(merchantId);
					setRoleType(mapDic.get("value").toString());
					setSortColumns("update_time desc");
				}
			});
			if(CollectionUtils.isNotEmpty(mktContactsMap)){
				if(mapDic.get("text").toString().equals("其他")) continue;
				final Map<String, Object> map = (Map<String, Object>) mktContactsMap.get(0);
				list.add(new HashMap<String, String>() {
					{
						put("text", mapDic.get("text").toString());
						put("value", map.get(MktContactVO.COLUMN_NAME).toString());
					}
				});
			}
		}
		//
		return new JsonResult(list, true);
	}
}
