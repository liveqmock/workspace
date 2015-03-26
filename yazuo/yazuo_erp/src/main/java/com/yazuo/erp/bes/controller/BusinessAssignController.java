package com.yazuo.erp.bes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.dao.SysResourceDao;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 业务分配
 */
@Controller
@RequestMapping(value = { "businessAssign" })
public class BusinessAssignController {

	@SuppressWarnings("unused")
	private static final Log LOG = LogFactory.getLog(BusinessAssignController.class);
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysResourceDao sysResourceDao;

	/**
	 * 业务分配查找用户
	 */
	@RequestMapping(value = "listUsers", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object list(@RequestBody SysUserVO sysUserVO) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userName", sysUserVO.getUserName());
		paramMap.put("tel", sysUserVO.getTel());
		paramMap.put("positionId", sysUserVO.getPositionId());
		paramMap.put("remark", "end_custom_service");// 只查询有后端客服权限的用户
		// 分页
		PageHelper.startPage(sysUserVO.getPageNumber(), sysUserVO.getPageSize(), true);

		Page<SysUserVO> listMap = sysUserService.getComplexSysUsersForBes(paramMap);
		// 返回页面的分页信息
		Map<String, Object> pageMap = new HashMap<String, Object>();

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, listMap.getTotal());
		dataMap.put(Constant.ROWS, listMap.getResult());

		pageMap.put("data", dataMap);
		pageMap.put("flag", 1);
		pageMap.put("message", "");
		// 格式化json
		return pageMap;
	}

}
