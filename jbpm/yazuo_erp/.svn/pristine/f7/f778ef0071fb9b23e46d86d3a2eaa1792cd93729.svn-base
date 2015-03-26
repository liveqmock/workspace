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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.service.SysPositionService;
import com.yazuo.erp.system.vo.SysPositionVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * @Description 职位相关的业务操作
 * @author kyy
 * @date 2014-6-4 上午9:56:44
 */
@Controller
@RequestMapping("position")
public class SysPositionController {

	private static final Log LOG = LogFactory.getLog(SysPositionController.class);

	private SysPositionService sysPositionService;

	/**
	 * 保存修改职位
	 * 
	 * @param positionVO
	 * @throws
	 */
	@RequestMapping(value = "savePosition", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object savePosition(SysPositionVO positionVO, HttpServletRequest request) {
		HttpSession session = request.getSession();
		SysUserVO user = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		int count = 0;
		String str = "";
		if (positionVO.getId() != null) { // 修改
			String name = positionVO.getPositionName();
			positionVO = sysPositionService.getById(positionVO.getId());
			positionVO.setPositionName(name);
			positionVO.setUpdateBy(user.getId());
			positionVO.setUpdateTime(new Date());
			positionVO.setIsEnable(Constant.Enable);
			count = sysPositionService.updatePosition(positionVO);
			str = "修改";
			LOG.debug("修改成功!输出值" + count);
		} else {
			positionVO.setInsertBy(user.getId());
			positionVO.setInsertTime(new Date());
			positionVO.setUpdateBy(user.getId());
			positionVO.setUpdateTime(new Date());
			positionVO.setIsEnable(Constant.Enable);
			count = sysPositionService.savePosition(positionVO);
			str = "添加";
			LOG.debug("添加成功!输出值" + count);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", count > 0 ? 1 : 0);
		map.put("message", count > 0 ? str + "成功!" : str + "失败!");
		map.put("data", "");
		return map;
	}

	@RequestMapping(value = "list", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public  Map<String, Object> list(@RequestParam("pageSize") int pageSize, @RequestParam("pageNumber") int pageNumber) {
		// 总数量
		long totalCount = sysPositionService.getTotalCount();
		
		PageHelper.startPage(pageNumber, pageSize);
		List<Map<String, String>> listMap = sysPositionService.getAllPageByOder();
		
		// 返回页面的分页信息
		 // 返回页面的分页信息
		 Map<String, Object> pageMap = new HashMap<String, Object>();
		
		 Map<String, Object> dataMap = new HashMap<String, Object>();
		 dataMap.put("totalSize", totalCount);
		 dataMap.put("rows", listMap);
		 
		 pageMap.put("data", dataMap);
		 pageMap.put("flag", 1);
		 pageMap.put("message", "");
		// 格式化json
		return pageMap;
	}
	
	@RequestMapping(value = "initPosition", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object initPosition() {
		List<Map<String, String>> listMap = sysPositionService.getAllPageByOder();
		 Map<String, Object> pageMap = new HashMap<String, Object>();
		 pageMap.put("data", listMap);
		 pageMap.put("flag", 1);
		 pageMap.put("message", "");
		 return pageMap;
	}

	@RequestMapping(value = "edit", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object edit(Integer id) {
		SysPositionVO position = sysPositionService.getById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", 1);
		map.put("message", "");
		map.put("data", position);
		return map;
	}

	@RequestMapping(value = "delete", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object delete(@RequestParam("id") Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 判断该岗位下是否存在用户
		boolean isExistsUser = sysPositionService.isExistsUserOfPosition(id);
		if (isExistsUser) {
			map.put("flag", 0);
			map.put("message", "已经被用户使用不能删除!");
		} else {
			int count = sysPositionService.deletePosition(id);
			map.put("flag", count > 0 ? 1 : 0);
			map.put("message", count > 0 ? "删除成功!" : "删除失败!");
			//throws new SystemException("刹车");
		}
		map.put("data", "");
		return map;
	}

	@RequestMapping(value = "deleteMany", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object deleteMany(@RequestParam(value="idString[]") String [] idstr) {
		Map<String, Object> mapList = sysPositionService.deleteManyPosition(idstr);
		return mapList;
	}

	@Resource
	public void setSysPositionService(SysPositionService sysPositionService) {
		this.sysPositionService = sysPositionService;
	}

}
