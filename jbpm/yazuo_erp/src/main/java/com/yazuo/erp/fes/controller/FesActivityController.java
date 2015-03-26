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
package com.yazuo.erp.fes.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.service.FesMarketingActivityService;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.vo.FesMarketingActivityVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysRemindService;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysRemindVO;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserVO;

/**
 * 营销活动
 * @author kyy
 * @date 2014-8-12 下午3:08:22
 */
@Controller
@RequestMapping("/activity")
public class FesActivityController {
	
	private static final Log LOG = LogFactory.getLog(FesActivityController.class);
	@Resource 
	private FesMarketingActivityService fesMarketingActivityService;
	@Resource
	private SysDictionaryService sysDictionaryService;
	@Resource
	private SysToDoListService sysToDoListService;
	@Resource
	private SysRemindService sysRemindService;
	@Resource
	private SysAttachmentService sysAttachmentService;
	@Resource
	private FesOnlineProcessService fesOnlineProcessService;
	@Resource
	private SysUserService sysUserService;
	@Value("${activityFileUrl}")
	private String activityFileUrl; // 营销活动附件路径
	

	/**
	 * 保存营销活动
	 */
	@RequestMapping(value = "saveActivity", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult saveActivity(@RequestBody FesMarketingActivityVO activity, HttpServletRequest request) {
		SysUserVO user = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		
		activity.setInsertBy(user.getId());
		activity.setUpdateBy(user.getId());
		activity.setInsertTime(new Date());
		activity.setUpdateTime(new Date());
		activity.setApplicantBy(user.getId());
		activity.setApplicationTime(new Date());
		activity.setIsEnable("1");
		activity.setMarketingActivityStatus("1");
		
		String fileName = activity.getAttachmentName();
		if (StringUtils.isNotEmpty(fileName)) { // 有附件上传时即向附件表插入数据
			String suffix = StringUtils.isNotEmpty(fileName) ? fileName.substring(fileName.lastIndexOf(".")+1) : "";
			SysAttachmentVO attachment = new SysAttachmentVO();
			attachment.setAttachmentName(fileName);
			attachment.setOriginalFileName(activity.getOriginalFileName());
			attachment.setAttachmentPath(""); // 除配置文件以为路径存入此字段
			attachment.setAttachmentSize(activity.getFileSize());
			attachment.setAttachmentSuffix(suffix);
			attachment.setAttachmentType("3");
			attachment.setModuleType("fes");
			attachment.setInsertBy(user.getId());
			attachment.setInsertTime(new Date());
			attachment.setUpdateBy(user.getId());
			attachment.setUpdateTime(new Date());
			attachment.setIsDownloadable("1");
			attachment.setIsEnable("1");
			sysAttachmentService.saveSysAttachment(attachment); // 保存附件
			Integer attachmentId = attachment.getId(); // 附件id
			
			activity.setAttachmentId(attachmentId);
		} else {
			activity.setAttachmentId(0);
		}
		return fesMarketingActivityService.saveFesMarketingActivity(activity, user);

		
	}
	
	// 上传文件
	@RequestMapping(value = "uploadFile", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult uploadFile(@RequestParam MultipartFile myfiles, HttpServletRequest request) throws Exception {
		String basePath = request.getSession().getServletContext().getRealPath("/");
		return FileUploaderUtil.uploadFile(myfiles, basePath, activityFileUrl, 0);
	}
	
	@RequestMapping(value = "initActivityType", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult initActivityType () {
		List<Map<String, Object>> list = sysDictionaryService.querySysDictionaryByType("00000056");
		JsonResult result = new JsonResult(true);
		result.setData(list);
		result.setMessage("查询成功!");
		return result;
	}
	
	@RequestMapping(value = "listActivity", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult listActivity (@RequestParam(value="merchantName", required=false)String merchantName, @RequestParam(value="status", required=false) String marketingActivityStatus , 
		Integer pageSize, Integer pageNumber, @RequestParam(value="merchantId", required=false)Integer merchantId,  HttpServletRequest request) {
		SysUserVO user = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantName", merchantName);
		paramMap.put("marketingActivityStatus", marketingActivityStatus);
		paramMap.put("merchantId", merchantId);
		
		PageHelper.startPage(pageNumber, pageSize, true); // 分页
		LOG.info("当前登录用户：" + user.getId());
		paramMap.put("userId", user.getId());
		Page<FesMarketingActivityVO> list = fesMarketingActivityService.getActivityByOrder(paramMap);
		
		for (FesMarketingActivityVO a : list.getResult()) {
			SysAttachmentVO attach =a.getAttachmentId() !=0 ? sysAttachmentService.getSysAttachmentById(a.getAttachmentId()) : null;
			String filePath = "";
			if (attach !=null) {
				a.setOriginalFileName(attach.getOriginalFileName());
				a.setAttachmentName(attach.getAttachmentName());
				if (StringUtils.isNotEmpty(attach.getAttachmentPath())) {
					filePath =activityFileUrl +"/"+ attach.getAttachmentPath() +"/"; 
				} else {
					filePath =activityFileUrl+"/";
				}
				a.setFilePath(filePath);
			}
			a.setFilePath(filePath);
		}
		JsonResult result = new JsonResult(true);
		result.setMessage("营销活动查询成功!");
		result.putData("rows", list);
		result.putData("totalSize", list.getTotal());
		result.putData("statusList", sysDictionaryService.querySysDictionaryByType("00000046"));
		return result;
	}
	
	@RequestMapping(value = "updateActivityStatus", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult updateActivityStatus(Integer activityId, String status,@RequestParam(value="reason", required=false) String reason, HttpServletRequest request) {
		SysUserVO user = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		
		FesMarketingActivityVO activity = new FesMarketingActivityVO();
		activity.setId(activityId);
		activity.setMarketingActivityStatus(status);
		activity.setUpdateBy(user.getId());
		activity.setUpdateTime(new Date());
		activity.setReason(reason);
		int count = fesMarketingActivityService.updateFesMarketingActivity(activity);
		
		//TODO 状态为已创建时将对应的待办事项关闭
		SysToDoListVO todoVo = new SysToDoListVO();
		todoVo.setRelatedId(activityId);// 设置活动id
		todoVo.setItemStatus("0");
		todoVo.setItemType("04"); // 上线后
		
		List<SysToDoListVO> todoList = sysToDoListService.getSysToDoLists(todoVo); // 该营销活动相关的待办事项
		for (SysToDoListVO todo : todoList) {
			todo.setItemStatus("1"); // 待办事项完成状态
			todo.setUpdateBy(user.getId());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SysToDoListVO.COLUMN_ITEM_STATUS, Constant.NOT_NULL);
		map.put(SysToDoListVO.COLUMN_UPDATE_TIME, Constant.NOT_NULL);
		map.put(SysToDoListVO.COLUMN_UPDATE_BY, Constant.NOT_NULL);
		map.put("list", todoList);
		sysToDoListService.batchUpdateSysToDoListsToDiffVals(map); // 批量更新
		
		return new JsonResult(count>0).setMessage(count > 0 ? "修改成功!" : "修改失败!");
	}
	
	//提醒页面查询
	@RequestMapping(value = "listRemind", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult listRemind(@RequestBody SysRemindVO sysRemind) {
		 //分页
		 PageHelper.startPage(sysRemind.getPageNumber(), sysRemind.getPageSize(), true);
		 
		 Page<SysRemindVO> listMap = sysRemindService.getSysReminds(sysRemind);
		 
		 JsonResult result = new JsonResult(true);
		 result.putData("rows", listMap);
		 result.putData("totalSize", listMap.getTotal());
		 return result;
	}
	
	//提醒页面查询
	@RequestMapping(value = "updateRemind", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult updateRemind(Integer id, HttpServletRequest request) {
		SysUserVO user = (SysUserVO)request.getSession().getAttribute(Constant.SESSION_USER);
		SysRemindVO sysRemind = new SysRemindVO();
		sysRemind.setId(id);
		sysRemind.setItemStatus("2");
		sysRemind.setUpdateBy(user.getId());
		sysRemind.setUpdateTime(new Date());
		int count = sysRemindService.updateSysRemind(sysRemind);
		return new JsonResult(true).setMessage(count > 0 ? "修改成功!" : "修改失败!");
	}
	
	//提醒页面查询不分页
	@RequestMapping(value = "listNoneReadRemind", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult listNoneReadRemind(Integer merchantId, HttpSession session) {
		 SysUserVO user = (SysUserVO) session.getAttribute(Constant.SESSION_USER);
		 
		 SysRemindVO sysRemind = new SysRemindVO();
		 sysRemind.setMerchantId(merchantId);
		 List<SysRemindVO> list = sysRemindService.getRemindsByMerchantIdAndUserId(sysRemind, user);
		 
		 return new JsonResult(true).setData(list).setMessage("查询成功!");
	}
	
	
}
