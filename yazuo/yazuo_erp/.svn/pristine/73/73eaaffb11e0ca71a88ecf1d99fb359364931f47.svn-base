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
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.req.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.collections.comparators.FixedOrderComparator;
import org.apache.commons.collections.functors.InstanceofPredicate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.req.dao.ReqProjectDao;
import com.yazuo.erp.req.service.ReqRequirementService;
import com.yazuo.erp.req.vo.ReqRequirementVO;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.DateUtil;
/**
 * @author erp team
 * @date 
 */

/**
 * 需求 相关业务操作
 * @author 
 */
@Controller
@RequestMapping("reqRequirement")
public class ReqRequirementController {
	
	private static final Log LOG = LogFactory.getLog(ReqRequirementController.class);
	@Resource
	private ReqRequirementService reqRequirementService;
	@Resource
	private SysDictionaryService sysDictionaryService;
	@Resource
	private ReqProjectDao reqProjectDao;
	/**
	 * 保存修改需求
	 */
	@RequestMapping(value = "saveReqRequirement", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult saveReqRequirement(@RequestBody ReqRequirementVO requirementVO, HttpServletRequest request) {

		SysUserVO userVO = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		int userId = userVO.getId();
		requirementVO.setInsertBy(userId);
		requirementVO.setUpdateBy(userId);
		int count = reqRequirementService.saveReqRequirement(requirementVO);
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	
	/**
	 * 列表显示 需求 
	 */
	@RequestMapping(value = "listReqRequirements", method = {  RequestMethod.POST, RequestMethod.GET })
	@ResponseBody 
	public JsonResult listReqRequirements(@RequestBody ReqRequirementVO requirementVO) {
		// 预计完成时间指的是上线时间，如果延期未完成，可通过状态筛选，但不可手动编辑为延期状态
		//查询条件状态下拉框中的延期，是系统自动筛出来超过预计完成时间还未完成的需求
//		reqRequirementService.updateRequirementStatusIfOverTime();
		
		//query the list requirements.
		Page<ReqRequirementVO> pageList = (Page<ReqRequirementVO>)reqRequirementService.getComplexRequirementByJoin(requirementVO);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, pageList.getTotal());
		dataMap.put(Constant.ROWS, pageList.getResult());
		return new JsonResult(true).setData(dataMap);
	}
	@Deprecated  //未被调用
	private String getStringDate(String startPlanTime) {
		Long startPlanTimeLong = Long.parseLong(String.valueOf(startPlanTime))*1000;
		Date date = new Date(startPlanTimeLong);
		return DateUtil.format(date, "yyyy-MM-dd");
	} 
	/**
	 * 查询所有drop down lists.
	 */
	@RequestMapping(value = "getDropDownLists", method = {  RequestMethod.POST, RequestMethod.GET })
	@ResponseBody 
	public JsonResult getDropDownLists() {
		//query project and other items for dropdown list.
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("planType", this.sysDictionaryService.querySysDictionaryByTypeStd(Constant.DIC_TYPE_PLAN_TYPE));
		dataMap.put("project", this.reqRequirementService.getAllProjects());
		dataMap.put("state",  this.sysDictionaryService.querySysDictionaryByTypeStd(Constant.DIC_TYPE_PORJECT_STATE));
		List<Map<String, Object>> typeDic = this.sysDictionaryService.querySysDictionaryByTypeStd("00000116");
		//option其它放到最后
//		List<Map<String, Object>> newTypeDic = (List<Map<String, Object>>)CollectionUtils.subtract(typeDic, Arrays.asList(typeDic.get(2)));
//		newTypeDic.add(typeDic.get(2));
		
		Collections.sort(typeDic,new BeanComparator("value", new FixedOrderComparator(new Object[]{"1","2","4","5","6","3"})));
//		LOG.info(JsonResult.getJsonString(typeDic));
		dataMap.put("reqSource", typeDic);
		return new JsonResult(true).setData(dataMap);
	} 
	
	/**
	 * 删除 需求 
	 */
	@RequestMapping(value = "deleteReqRequirement/{id}", method = {  RequestMethod.POST, RequestMethod.GET })		
	@ResponseBody
	public JsonResult deleteReqRequirement(@PathVariable int id) {
		int count = reqRequirementService.deleteReqRequirementById(id);
		return new JsonResult(count > 0).setMessage(count > 0 ? "删除成功!" : "删除失败!");
	}
}
