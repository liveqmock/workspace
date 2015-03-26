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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.mkt.service.MktTeamService;
import com.yazuo.erp.mkt.vo.MktTeamVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.StringUtil;

/**
 * 团队信息 相关业务操作
 * @author 
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)
@RequestMapping("mktTeam")
public class MktTeamController {
	
	private static final Log LOG = LogFactory.getLog(MktTeamController.class);
	@Resource
	private MktTeamService mktTeamService;
	@Value("${userPhotoPath}")
	private String userPhotoPath;
	/**
	 * 保存修改团队信息
	 */
	@RequestMapping(value = "saveMktTeam", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveMktTeam(@RequestBody MktTeamVO mktTeam, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		int count = 0;
		if (mktTeam!=null && mktTeam.getId()!=null) { // 修改
			count = mktTeamService.updateMktTeam(mktTeam, user);
		} else { // 添加
			count = mktTeamService.saveMktTeam(mktTeam);
		}
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	
	/**
	 * 列表显示 团队信息 
	 */
	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult list(@RequestParam(value="date", required=false) String date, @RequestParam(value="isFromAssiant", required=false) String isFromAssiant) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date reportDate = null;
		if (StringUtils.isNotEmpty(date)) {
			long second = Long.parseLong(date);
			Date time = new Date();
			time.setTime(second); // 将秒转换成毫秒数
			
			try {
				reportDate = sdf.parse(sdf.format(time));// 格式化
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		} else {
			reportDate = new Date();
		}
		MktTeamVO team = new MktTeamVO();
		team.setDateTime(reportDate);
		List<MktTeamVO> list = mktTeamService.getMktTeams(team);
		JsonResult result = new JsonResult(true);
		result.setMessage("查询成功!");
		result.setData(list);
		if (!StringUtil.isNullOrEmpty(isFromAssiant) && isFromAssiant.equals("true")) {
			for (MktTeamVO t : list) {
				t.setLeaderImage(userPhotoPath+"/"+t.getLeaderImage());
				List<SysUserVO> supportList = mktTeamService.getSupportUserByTeamId(t.getId());
				t.setSupportList(supportList);
			}
		}
		return result;
	}
	
	/**
	 * 列某个对的支持者
	 */
	@RequestMapping(value = "listSupport", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listSupport(Integer teamId) {
		List<SysUserVO> list = mktTeamService.getSupportUserByTeamId(teamId);
		return new JsonResult(true).setMessage("查询成功!").setData(list);
	}
	
	/**
	 *删除支持者 
	 */
	@RequestMapping(value = "deleteSupport", method = { RequestMethod.GET, RequestMethod.POST })		
	@ResponseBody
	public JsonResult deleteSupportByTeamIdAndUserId(Integer supportId, Integer teamId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("supportId", supportId);
		paramMap.put("teamId", teamId);
		int count = mktTeamService.deleteSupportByTeamIdAndUserId(paramMap);
		return new JsonResult(count>0).setMessage(count>0?"删除成功!" : "删除失败!");
	}
	
	/**
	 *添加支持者 
	 */
	@RequestMapping(value = "saveSupporter", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveSupporter(@RequestBody MktTeamVO mktTeam, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		LOG.info("传过来参数："+ mktTeam.getSupportList());
		int count = mktTeamService.saveSupporter(mktTeam.getSupportList(), user, mktTeam.getId());
		return new JsonResult(count>0).setMessage(count>0?"添加成功!":"添加修改!");
	}
	
	/*
	 * 初始化选择支持者数据
	 */
	@RequestMapping(value = "listNoChooseSupport", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody 
	public JsonResult listNoChooseSupport(@RequestParam(value="userName", required=false) String userName, Integer teamId) {
		SysUserVO user = new SysUserVO();
		user.setUserName(userName);
		List<SysUserVO> allList = mktTeamService.getNoSupportCurrentTeamUser(user); // 除角色为销售的用户
		List<SysUserVO> list = mktTeamService.getSupportUserByTeamId(teamId);
		JsonResult result = new JsonResult(true);
		result.setMessage("查询成功!");
		result.putData("chooseSupportList", list); //已选的支持者
		result.putData("supportCount,", list.size());
		allList.removeAll(list);
		result.putData("noChooseSupportList", allList); // 带选的用户
		return result;
	}
}
