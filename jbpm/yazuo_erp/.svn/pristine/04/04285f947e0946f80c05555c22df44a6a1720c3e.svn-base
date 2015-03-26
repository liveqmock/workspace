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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.service.FesOpenCardApplicationDtlService;
import com.yazuo.erp.fes.service.FesOpenCardApplicationService;
import com.yazuo.erp.fes.service.FesRemarkService;
import com.yazuo.erp.fes.service.FesStowageDtlService;
import com.yazuo.erp.fes.service.FesStowageService;
import com.yazuo.erp.fes.vo.FesOpenCardApplicationDtlVO;
import com.yazuo.erp.fes.vo.FesOpenCardApplicationVO;
import com.yazuo.erp.fes.vo.FesRemarkVO;
import com.yazuo.erp.fes.vo.FesStowageVO;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.vo.SysUserVO;
/**
 * 实体卡开卡申请及设备配送
 * @author kyy
 * @date 2014-8-11 下午3:47:04
 */
@Controller
@SessionAttributes(Constant.SESSION_USER)
@RequestMapping("applySetting")
public class FesEntityCardController {

	@Resource
	private FesOpenCardApplicationDtlService fesOpenCardApplicationDtlService;
	@Resource
	private FesOpenCardApplicationService fesOpenCardApplicationService;
	@Resource
	private SysDictionaryService sysDictionaryService;
	@Resource
	private FesStowageDtlService fesStowageDtlService;
	@Resource
	private FesStowageService fesStowageService;
	@Resource
	private FesRemarkService fesRemarkService;
	@Resource
	private FesOnlineProcessService fesOnlineProcessService;
	@Resource
	private SysToDoListService sysToDoListService;
	
	
	/**
	 * 保存开卡明细
	 */
	@RequestMapping(value = "saveOpenApply", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	@Deprecated //please user method 'saveOpenCardApply' instead.
	public JsonResult saveOpenApply(@RequestBody FesOpenCardApplicationDtlVO cardApplyDtl, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		FesOpenCardApplicationVO cardApply = new FesOpenCardApplicationVO();
		cardApply.setMerchantId(cardApplyDtl.getMerchantId());
		cardApply.setProcessId(cardApplyDtl.getProcessId());
		cardApply.setInsertBy(user.getId());
		cardApply.setUpdateBy(user.getId());
		cardApply.setIsEnable("1");
		List<Map<String, Object>> dictionaryList = sysDictionaryService.querySysDictionaryByType("00000051");
		String keyVal = "";
		if (dictionaryList !=null && dictionaryList.size()>0) {
			keyVal = String.valueOf(dictionaryList.get(0).get("dictionary_key"));
		}
		cardApply.setOpenCardApplicationStatus(keyVal); //状态 
		fesOpenCardApplicationService.saveFesOpenCardApplication(cardApply); // 主表数据保存
		Integer applyId = cardApply.getId();
		
		cardApplyDtl.setApplicationId(applyId); // 主表id
		cardApplyDtl.setInsertBy(user.getId());
		int count = fesOpenCardApplicationDtlService.saveFesOpenCardApplicationDtl(cardApplyDtl); // 明细表数据保存
		return new JsonResult(count > 0).setMessage(count > 0 ? "保存成功!" : "保存失败!");
	}
	/**
	 * 保存开卡申请
	 */
	@RequestMapping(value = "saveOpenCardApply", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult saveOpenCardApply(@RequestBody FesOpenCardApplicationVO fesOpenCardApplicationVO, HttpSession session) {
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		JsonResult result = fesOpenCardApplicationService.saveFesOpenCardApplicationAndDtls(fesOpenCardApplicationVO, sessionUser); 
		return result;
	}
	
	/**
	 * 开卡申请弹层
	 */
	@RequestMapping(value = "loadOpenCardApply", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult loadOpenCardApply(@RequestBody final FesOpenCardApplicationVO fesOpenCardApplicationVO) {
		
//		Integer processId = fesOpenCardApplicationVO.getProcessId();
		
		final List<Map<String, Object>> querySysDictionaryByTypeStd = this.sysDictionaryService.querySysDictionaryByTypeStd("00000114");
		FesOpenCardApplicationVO fesOpenCardApp = new FesOpenCardApplicationVO();
		if(fesOpenCardApplicationVO.getProcessId()!=null){
			List<FesOpenCardApplicationVO> lists = this.fesOpenCardApplicationService.getFesOpenCardApplicationsAndDtls(fesOpenCardApplicationVO);
			fesOpenCardApp = lists.size()>0? lists.get(0): new FesOpenCardApplicationVO();
		}
		List<FesOpenCardApplicationDtlVO> fesOpenCardApplicationDtls = fesOpenCardApp.getFesOpenCardApplicationDtls();
		if(fesOpenCardApplicationDtls==null) fesOpenCardApplicationDtls = new ArrayList<FesOpenCardApplicationDtlVO>();
		for (final FesOpenCardApplicationDtlVO fesOpenCardApplicationDtlVO : fesOpenCardApplicationDtls) {
			CollectionUtils.forAllDo(querySysDictionaryByTypeStd, new Closure() {
				public void execute(Object input) {
					Map<String, Object> map = (Map<String, Object>)input;
					if(map.get("value").toString().equals(fesOpenCardApplicationDtlVO.getMemberLevel())){
						map.put(Constant.DropDownList.isSelected, "1");
					}else{
						map.put(Constant.DropDownList.isSelected, "0");
					}
				}
			});
		}
		fesOpenCardApp.setMembershipDic(querySysDictionaryByTypeStd);
		return new JsonResult(fesOpenCardApp, true);
	}
	
	/**
	 * 实体卡配送:　配送信息保存
	 */
	@RequestMapping(value = "saveFesStowage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult saveFesStowage(@RequestBody FesStowageVO fesStowage, HttpSession session) {
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		fesStowage.setDistributeFlag(0);
		JsonResult result = fesStowageService.saveFesStowage(fesStowage, sessionUser); 
		return result;
	}
	
	/**
	 * 取开卡申请单信息
	 */
	@RequestMapping(value = "eidtApply", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult eidtApply(Integer applyId) {
		FesOpenCardApplicationDtlVO applyDtl = new FesOpenCardApplicationDtlVO();
		applyDtl.setApplicationId(applyId);
		List<FesOpenCardApplicationDtlVO> list = fesOpenCardApplicationDtlService.getFesOpenCardApplicationDtls(applyDtl);
		if (list !=null && list.size() > 0) {
			applyDtl = list.get(0);
		}
		JsonResult result = new JsonResult();
		result.setData(applyDtl);
		result.setFlag(true);
		result.setMessage("查询成功");
		return result;
	}
	/**
	 *填写微信备注信息
	 */
	@RequestMapping(value = "addRemark", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult addRemark(@RequestBody FesRemarkVO remark, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		return fesRemarkService.saveAndUpdateRemarkAddOther(remark, user);
	}
	
	@RequestMapping(value = "deleteRemark", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public JsonResult deleteRemark(@RequestBody FesRemarkVO remark, @ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		return fesRemarkService.deleteFesRemarkById(remark, user);
	}
	
}
