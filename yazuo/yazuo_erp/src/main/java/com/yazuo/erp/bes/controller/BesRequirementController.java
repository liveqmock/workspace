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

package com.yazuo.erp.bes.controller;

import static junit.framework.Assert.assertNotNull;

import java.util.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yazuo.erp.bes.controller.vo.AbandonRequirementVO;
import com.yazuo.erp.fes.vo.FesPlanVO;
import com.yazuo.erp.system.service.*;
import com.yazuo.erp.system.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.lang.builder.EqualsBuilder;
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
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.bes.service.BesRequirementService;
import com.yazuo.erp.bes.service.BesTalkingSkillsService;
import com.yazuo.erp.bes.vo.BesCallRecordVO;
import com.yazuo.erp.bes.vo.BesRequirementVO;
import com.yazuo.erp.bes.vo.BesTalkingSkillsVO;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.mkt.service.MktContactService;
import com.yazuo.erp.mkt.vo.MktContactVO;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.syn.vo.SynMerchantVO;
import com.yazuo.erp.system.service.ResourceService;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.service.SysDocumentService;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.service.SysQuestionService;
import com.yazuo.erp.system.vo.SysDocumentVO;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysQuestionVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.DateUtil;

/**
 * 后端需求 相关业务操作
 * @author
 */
@Controller
@RequestMapping("besRequirement")
@SuppressWarnings("unused")
public class BesRequirementController extends BesBasicController{
	
	private static final Log LOG = LogFactory.getLog(BesRequirementController.class);
	@Resource private BesRequirementService besRequirementService;
	@Resource private ResourceService resourceService;
	@Resource private SysDictionaryService sysDictionaryService;
	@Resource private BesTalkingSkillsService besTalkingSkillsService;
	@Resource private SysQuestionService sysQuestionService;
    @Resource private SysDocumentService sysDocumentService;
    @Resource private MktContactService mktContactService;
	@Resource private SysOperationLogService sysOperationLogService;
	@Resource private SynMerchantService synMerchantService;
	
	/**
	 * 显示商户 域/门店信息 google suggest 
	 */
	@RequestMapping(value = "getCascateSelectMerchants", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getCascateSelectMerchants(@RequestBody BesRequirementVO besRequirementVO, HttpSession session) {
		assertNotNull("商户ID不能为空！", besRequirementVO.getMerchantId());
		SysUserVO sessionUser = this.getUser(session);
		List<Object> cascateSelectMerchants = this.synMerchantService.getCascateSelectMerchants(besRequirementVO);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("merchants", cascateSelectMerchants);
		result.put("contact", this.besRequirementService.getContactListsOfReq(besRequirementVO));
		return new JsonResult(true).setData(result);
	}
	
	/**
	 * 下拉列表选择完一个门店的时候调用
	 */
	@RequestMapping(value = "getStoreContacts", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getStoreContacts(@RequestBody BesRequirementVO besRequirementVO, HttpSession session) {
		
		assertNotNull("商户ID不能为空！", besRequirementVO.getMerchantId());
		assertNotNull("门店ID不能为空！", besRequirementVO.getStoreId());
		//联系人
		final List<Map<String, Object>> contact = this.besRequirementService.getContactListsOfReq(besRequirementVO);
		return new JsonResult(true).setData(contact);
	}


//	@Value("${besReqAttachmentPath}")
//	private String besReqAttachmentPath;
//	/**
//	 * 上传文件
//	 */
//	@RequestMapping(value = "uploadFile", method = { RequestMethod.POST, RequestMethod.GET })
//	@ResponseBody
//	public JsonResult uploadFile(@RequestParam MultipartFile myfile, HttpServletRequest request) throws IOException {
//		SysUserVO sessionUser = this.getUser(request.getSession());
//		String basePath = request.getSession().getServletContext().getRealPath("/");
//		return this.besRequirementService.uploadFile(myfile,  basePath, "/"+besReqAttachmentPath, sessionUser);
//	}
	
	/**
	 * 进入创建需求页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "loadBesRequirement", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult loadBesRequirement(@RequestBody BesRequirementVO besRequirement, HttpSession session) {
		SysUserVO sessionUser = this.getUser(session);

		Integer reqId = besRequirement.getId();
		List<Map<String, Object>> contactList = new ArrayList<Map<String,Object>>();
		if(reqId != null){
			besRequirement = besRequirementService.getBesRequirementById(reqId);
		}
		contactList = this.besRequirementService.getContactListsOfReq(besRequirement);
		String resourceRemark = besRequirement.getResourceRemark();
		String resourceExtraRemark = besRequirement.getResourceExtraRemark();
		//资源
		final List<Object> resourceDB = this.besTalkingSkillsService.getResourceCascateList(reqId, resourceRemark, resourceExtraRemark);
		//TODO 目前只有营销活动
		final List<Object> resource = (List<Object>)CollectionUtils.select(resourceDB, new Predicate() {
			public boolean evaluate(Object object) {
				return new EqualsBuilder().append(((Map<String, Object>)object).get("value"), "marketing_act_148").isEquals(); 
			}
		});
		//联系人
		final List<Map<String, Object>> contact = contactList;
		//来源分类
		final List<Map<String, Object>> sourceCat = 
			sysDictionaryService.getDicListWithSelected("00000103", new String[]{besRequirement.getSourceCat()});
		
		//来源内容
		final List<Map<String, Object>> source = 
			sysDictionaryService.getDicListWithSelected("00000104", new String[]{besRequirement.getSourceContent()});

//		final List<Object> cascateSelectMerchants = this.synMerchantService.getCascateSelectMerchants(besRequirement);
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("merchants", cascateSelectMerchants);
		map.put("besRequirement", besRequirement);//后端需求信息
		map.put("type", resource);//资源
		map.put("contact", contact);//联系人
		map.put("sourceCat", sourceCat);//来源分类
		map.put("source", source);//来源
		return new JsonResult(map, true);
	}
	
	/**
	 * 保存修改 后端需求 修改处理人, 需求指派等
	 */
	@RequestMapping(value = "saveBesRequirement", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveBesRequirement(@RequestBody BesRequirementVO besRequirement, HttpSession session) {
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		BesRequirementVO besRequirementVO = besRequirementService.saveOrUpdateBesRequirement(besRequirement, sessionUser);
		return new JsonResult(besRequirementVO!=null).setData(besRequirementVO);
	}

	/**
	 *  回访 电话未接通 点击每一个按钮
	 */
	@RequestMapping(value = "saveVisteFailTelCall", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveVisteFailTelCall(@RequestBody BesRequirementVO besRequirement, HttpSession session) {

		assertNotNull("需求ID不能为空!", besRequirement.getId());
		assertNotNull("回访内容不能为空!", besRequirement.getVisiteButtonName());
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		List<Map<String, Object>> list = besRequirementService.saveVisteFailTelCall(besRequirement, sessionUser);
		return new JsonResult(list.size()>0).setData(list);
	}

	/**
	 *  点击 放弃 或 完成按钮
	 */
	@RequestMapping(value = "saveQuitOrComplete", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult saveQuitOrComplete(@RequestBody BesRequirementVO besRequirement, HttpSession session) {

		assertNotNull("需求ID不能为空!", besRequirement.getId());
		assertNotNull("按钮标识不能为空!", besRequirement.getBtnFlag());
		if(besRequirement.getBtnFlag() == 1) assertNotNull("放弃原因不能为空!", besRequirement.getRemark());

		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		List<Map<String, Object>> list = besRequirementService.saveVisteFailTelCall(besRequirement, sessionUser);
		return new JsonResult(list.size()>0).setData(list);
	}

    /**
     * 批量放弃功能
     *
     * @param requirementVO
     * @param session
     * @return
     */
    @RequestMapping(value = "batchSaveQuit", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JsonResult batchSaveQuit(@RequestBody AbandonRequirementVO requirementVO, HttpSession session) {
        SysUserVO userVO = this.getUser(session);
        this.besRequirementService.batchSaveVisitFailTelCall(Arrays.asList(requirementVO.getIds()), userVO, requirementVO.getContent(), true);
        return new JsonResult(true, "批量放弃完成");
    }

    /**
	 * 显示 后端需求 查询条件
	 */
	@RequestMapping(value = "listBesReqConditions", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult listBesReqConditions(@RequestBody BesRequirementVO besRequirement, HttpSession session) {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		//封装查询条件
		//资源
		List<Map<String,Object>> resourcesByParent = resourceService.getResourceByParent(besRequirement.getResourceRemark());
		dataMap.put("parameResources", resourcesByParent);
		//前端分类
		List<Map<String, Object>> parameSourceCat = sysDictionaryService.getDicListWithSelected("00000103", new String[]{besRequirement.getSourceCat()});
		dataMap.put("parameSourceCat", parameSourceCat);
		//处理状态
		List<Map<String, Object>> parameStatus = sysDictionaryService.getDicListWithSelected("00000113", new String[]{besRequirement.getStatus()});
		dataMap.put("parameStatus", parameStatus);
		return new JsonResult(true).setData(dataMap);
	}

	/**
	 * 列表显示 后端需求
	 */
	@RequestMapping(value = "listBesRequirements", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult listBesRequirements(@RequestBody BesRequirementVO besRequirement, HttpSession session) {

		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		assertNotNull("页号不允许为空!", besRequirement.getPageNumber());
		assertNotNull("页总数不允许为空!", besRequirement.getPageSize());
		PageHelper.startPage(besRequirement.getPageNumber(), besRequirement.getPageSize(), true);
		List<BesRequirementVO> list = besRequirementService.getBesRequirements(besRequirement, sessionUser);
		Page<BesRequirementVO> pageList = (Page<BesRequirementVO>)list;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, pageList ==null ? 0: pageList.getTotal());
		dataMap.put(Constant.ROWS, pageList ==null ? null: pageList.getResult());
		return new JsonResult(true).setData(dataMap);
	}

	/**
	 * 查询 后端需求 [已完成,未完成]
	 */
	@RequestMapping(value = "getBesRequirement", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult getBesRequirement(@RequestBody BesRequirementVO besRequirement, HttpSession session) {
		SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);
		String realPath = session.getServletContext().getRealPath("/");
		Integer id = besRequirement.getId();
		assertNotNull("需求ID不能为空 !", id);
		final BesRequirementVO besRequirementVO = besRequirementService.getBesRequirementById(id, sessionUser);
		//回访记录  弹层
		MktContactVO mktContact = besRequirementVO.getMktContact();
		final Map<String, Object> popUpMap = new HashMap<String, Object>();
		popUpMap.put("userName", mktContact!=null? mktContact.getName(): "");
		popUpMap.put("mobilePhone", mktContact!=null? mktContact.getMobilePhone(): "");
		popUpMap.put("revisteDic", this.sysDictionaryService.querySysDictionaryByTypeStd("00000106"));
		//工作管理附件
		final Map<String, Object> attaMap = new HashMap<String, Object>();
		List<Map<String, String>> monthlyFesPlan = this.besRequirementService.getMonthlyFesPlan(realPath, besRequirementVO);
		attaMap.put("attachments", monthlyFesPlan);
		@SuppressWarnings("serial")
		Map<String, Object>  result = new HashMap<String, Object>(){{
			put("besRequirement", besRequirementVO);
			put("popUpDiv", popUpMap);
			put("fesPlan", attaMap);
		}};
		return new JsonResult(besRequirementVO!=null).setData(result);
	}


	/**
	 * 查询 电话已接通
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "loadTelConnectPage", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult loadTelConnectPage(@RequestBody final BesRequirementVO besRequirement, HttpSession session) {
		final Integer merchantId = besRequirement.getMerchantId();
		assertNotNull("文档类型不能为空!", besRequirement.getDocumentType());
		assertNotNull("话术资源不能为空!", besRequirement.getTakingSkillResource());
		//目前只有一个文档 2014-11-27 14:08:35
//		assertNotNull("文档ID不能为空 !", besRequirement.getDocumentId());
		assertNotNull("商户ID不能为空!", merchantId);
		assertNotNull("需求ID不能为空 !", besRequirement.getId());
        BesRequirementVO requirementVO = this.besRequirementService.getBesRequirementById(besRequirement.getId());

		//回访话术
		final List<BesTalkingSkillsVO> besTalkingSkillss = besTalkingSkillsService.getBesTalkingSkillss(new BesTalkingSkillsVO() {
			{
				setResourceRemark(besRequirement.getTakingSkillResource());
				setIsEnable("1");
			}
		});
		//问卷
        SysDocumentVO resultDocumentVO = this.getDocumentVO(requirementVO, besRequirement.getDocumentType());

        //变更联系人的Tab
		final Map<String, Object> contactMap = this.besRequirementService.getContactMap(besRequirement.getId());
		final Map<String, Object> changeContact = this.besRequirementService.encapsulateChangeContact(merchantId, contactMap);
		// 通话记录 [通话记录只有新增的需求]
		final BesCallRecordVO besCallRecordVO = new BesCallRecordVO();
		besCallRecordVO.setContactMap(contactMap);
        JsonResult jsonResult = new JsonResult(true)
                .putData("talkingSkills", besTalkingSkillss)
                .putData("callRecord", besCallRecordVO)
                .putData("changeContact", changeContact)
                .putData("document", resultDocumentVO);
        return jsonResult;
    }

    @RequestMapping(value = "getDocumentDtls", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JsonResult getDocumentDtls(@RequestBody BesRequirementVO requirementVO) {
        BesRequirementVO persistVO = this.besRequirementService.getBesRequirementById(requirementVO.getId());
        SysDocumentVO documentVO = this.getDocumentVO(persistVO, requirementVO.getDocumentType());
        return new JsonResult(true).setData(documentVO.getSysDocumentDtlList());
    }

	private Map<String, Object> encapsulateChangeContact(final Integer merchantId, final Map<String, Object> contactMap) {
		final List<Map<String, Object>> contactCats = sysDictionaryService.querySysDictionaryByTypeStd("00000102");
		//过滤前端
		CollectionUtils.filter(contactCats, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>)object;
				if(map.get("value")!=null && map.get("value").equals("1")) return true;
				return false;
			}
		});
		final List<Map<String, Object>> contactsDropDownList = this.mktContactService.getContactsDropDownList(new MktContactVO(){
			 {setMerchantId(merchantId);}});

		final Map<String, Object> changeContact = new HashMap<String, Object>() {
			{
				put("contactCat", contactCats);
				put("contactsList", contactsDropDownList);
				put("mobile", contactMap.get("mobile_phone"));
			}
		};
		return changeContact;
	}
	/**
	 * 保存 通话记录
	 *
	 * 1. 保存问卷调研单
	 * 2. 保存通话记录
	 * 3. 添加流水
	 */
	@RequestMapping(value = "savePaperAndCallRecord", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public JsonResult savePaperAndCallRecord(@RequestBody CostomParameter callRecordAndDocument, HttpServletRequest request, HttpSession session) {

        final BesCallRecordVO besCallRecord = callRecordAndDocument.getBesCallRecordVO();
        final SysDocumentVO sysDocument = callRecordAndDocument.getSysDocumentVO();
        final Map<String, Object> contactChangedMap = callRecordAndDocument.getContact(); //变更联系人标识
        SysUserVO sessionUser = (SysUserVO)session.getAttribute(Constant.SESSION_USER);

        assertNotNull("商户ID不能为空!", besCallRecord.getMerchantId());
        assertNotNull("需求ID不能为空!", besCallRecord.getRequirementId());
        assertNotNull("开始时间不能为空!", besCallRecord.getBeginTime());
        assertNotNull("结束时间不能为空!", besCallRecord.getEndTime());

        besCallRecord.setContactChangedMap(contactChangedMap);
        return new JsonResult(true).setData(besRequirementService.savePaperAndCallRecord(besCallRecord, sysDocument, sessionUser, request));
    }

	@RequestMapping(value = "testGetSysOperationLogs")
    @ResponseBody
    public Object testGetSysOperationLogs() {
    	SysOperationLogVO sysOperationLogVO = new SysOperationLogVO();
    	sysOperationLogVO.setMerchantId(2280);
    	sysOperationLogVO.setModuleType("bes");
    	sysOperationLogVO.setFesLogType("17");//工作管理
    	sysOperationLogVO.setBeginTime(DateUtil.fromMonth());
    	sysOperationLogVO.setEndTime(new Date());
		List<SysOperationLogVO> sysOperationLogs = this.sysOperationLogService.getSysOperationLogs(sysOperationLogVO);
		System.out.println(JsonResult.getJsonString(sysOperationLogs));
    	return new JsonResult(true);
    }

    /**
     * 将questions转换成DocumentVO
     * @param questions
     * @return
     */
    private List<SysDocumentDtlVO> toDocumentVO(List<Map<String, Object>> questions) {
        List<SysDocumentDtlVO> resultDocumentDtlVOs = new LinkedList<SysDocumentDtlVO>();
        for (Map<String, Object> question : questions) {
            SysDocumentDtlVO documentDtlVO = new SysDocumentDtlVO();
            documentDtlVO.setId(null);
            documentDtlVO.setQuestionId((Integer) question.get("id"));
            documentDtlVO.setQuestionType((String) question.get("question_type"));
            documentDtlVO.setTitle((String) question.get("title"));
            documentDtlVO.setTip((String) question.get("tip"));
            List<SysDocumentDtlOptionVO> documentDtlOptionVOs = new LinkedList<SysDocumentDtlOptionVO>();
            List<SysQuestionOptionVO> questionOptionVOs = (List<SysQuestionOptionVO>) question.get("sysQuestionOptionList");
            for (SysQuestionOptionVO optionVO : questionOptionVOs) {
                SysDocumentDtlOptionVO dtlOptionVO = new SysDocumentDtlOptionVO();
                dtlOptionVO.setTip(optionVO.getTip());
                dtlOptionVO.setIsSelected("0");
                dtlOptionVO.setOptionContent(optionVO.getOptionContent());
                dtlOptionVO.setIsOpenTextarea(optionVO.getIsOpenTextarea());
                documentDtlOptionVOs.add(dtlOptionVO);
            }
            documentDtlVO.setSysDocumentDtlOptionList(documentDtlOptionVOs);
            resultDocumentDtlVOs.add(documentDtlVO);
        }
        return resultDocumentDtlVOs;
    }

    /**
     * 得到需求的回访单信息
     * @param requirementVO
     * @param documentType
     * @return
     */
    private SysDocumentVO getDocumentVO(BesRequirementVO requirementVO, String documentType) {
        //查询回访单
        Integer merchantId = requirementVO.getMerchantId();
        Date from = DateUtils.truncate(requirementVO.getInsertTime(), Calendar.MONTH);
        Date to = DateUtils.addMonths(from, 1);
        SysDocumentVO resultSysDocumentVO = null;
        List<SysDocumentDtlVO> tmpDocumentDtlVOS = null;
        SysDocumentVO documentVO = this.sysDocumentService.getDocumentByMerchantAndType(merchantId, documentType, from, to);
        if (documentVO != null) {
            // 查询需求单对应的回访单
            SysDocumentVO searchVO = new SysDocumentVO();
            searchVO.setId(documentVO.getId());
            resultSysDocumentVO = this.sysDocumentService.querySysDocumentById(searchVO);
            Date explainTime = this.sysQuestionService.getExplainTime(merchantId, requirementVO.getInsertTime());
            String placeholder = "@replace";
            String monthPlaceholder = "@lastMonth";
            for (SysDocumentDtlVO sysDocumentDtlVO : resultSysDocumentVO.getSysDocumentDtlList()) {
                String str = sysDocumentDtlVO.getTitle();
                if (str.contains(placeholder)) {
                    String time = explainTime != null ? FastDateFormat.getInstance("yyyy-MM-dd").format(explainTime) : "";
                    String month = explainTime != null ? FastDateFormat.getInstance("M").format(DateUtils.addMonths(explainTime,-1)) : "";
                    str = str.replaceAll(placeholder, time);
                    str = str.replaceAll(monthPlaceholder, month);
                    sysDocumentDtlVO.setTitle(str);
                }
            }
        } else {
            SysQuestionVO searchQuestionVO = new SysQuestionVO();
            searchQuestionVO.setDocumentType(documentType);
            searchQuestionVO.setMerchantId(merchantId);
            List<Map<String, Object>> sysQuestions = sysQuestionService.querySysQuestionList(searchQuestionVO,requirementVO.getInsertTime());
            tmpDocumentDtlVOS = this.toDocumentVO(sysQuestions);
            resultSysDocumentVO = new SysDocumentVO();
            resultSysDocumentVO.setDocumentType(documentType);
            resultSysDocumentVO.setMerchantId(merchantId);
            resultSysDocumentVO.setIsEnable("1");
            resultSysDocumentVO.setId(null);
            resultSysDocumentVO.setSysDocumentDtlList(tmpDocumentDtlVOS);
        }
        return resultSysDocumentVO;
    }
}

/*
 * 参数对象
 */
class CostomParameter{
	private BesCallRecordVO besCallRecordVO;
	private SysDocumentVO sysDocumentVO;
	private Map<String, Object> contact;
	public Map<String, Object> getContact() {
		return contact;
	}
	public void setContact(Map<String, Object> contact) {
		this.contact = contact;
	}
	public BesCallRecordVO getBesCallRecordVO() {
		return besCallRecordVO;
	}
	public void setBesCallRecordVO(BesCallRecordVO besCallRecordVO) {
		this.besCallRecordVO = besCallRecordVO;
	}
	public SysDocumentVO getSysDocumentVO() {
		return sysDocumentVO;
	}
	public void setSysDocumentVO(SysDocumentVO sysDocumentVO) {
		this.sysDocumentVO = sysDocumentVO;
	}
}