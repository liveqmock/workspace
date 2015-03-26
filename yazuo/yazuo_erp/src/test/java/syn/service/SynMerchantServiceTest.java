/**
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package syn.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import base.JUnitDaoBaseWithTrans;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.controller.FesMyHomePageController;
import com.yazuo.erp.fes.dao.FesProcessAttachmentDao;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.service.FesOnlineProgramService;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;
import com.yazuo.erp.fes.vo.FesOnlineProgramVO;
import com.yazuo.erp.fes.vo.FesProcessAttachmentVO;
import com.yazuo.erp.mkt.service.MktShopSurveyService;
import com.yazuo.erp.req.service.ReqRequirementService;
import com.yazuo.erp.req.vo.ReqRequirementVO;
import com.yazuo.erp.syn.dao.SynMerchantDao;
import com.yazuo.erp.syn.service.SynMerchantService;
import com.yazuo.erp.syn.vo.ComplexSynMerchantVO;
import com.yazuo.erp.system.controller.SysGroupController;
import com.yazuo.erp.system.controller.SysUserController;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.service.SysUserMerchantService;
import com.yazuo.erp.system.vo.SysResourceVO;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserMerchantVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.trade.service.TradeMessageTemplateService;
import com.yazuo.erp.trade.vo.TradeMessageTemplateVO;

/**
 * @Description 
 */

//@RunWith(Suite.class)
//@Suite.SuiteClasses({ TestCase1.class, TestCase2.class })
public class SynMerchantServiceTest extends  JUnitDaoBaseWithTrans {
	 
	private static final Log LOG = LogFactory.getLog(SynMerchantServiceTest.class);
	private static final String List = null;
	@Resource	private SynMerchantService synMerchantService;
	@Resource	private FesMyHomePageController fesMyHomePageController;
	@Resource	private SysUserMerchantService sysUserMerchantService;
	@Resource	private FesOnlineProgramService fesOnlineProgramService;
	@Resource	private FesOnlineProcessService fesOnlineProcessService;
	@Resource	private SysToDoListService sysToDoListService;
	@Resource	private ReqRequirementService reqRequirementService;
	@Resource	private SysUserController sysUserController;
	@Resource	private SysGroupController sysGroupController;
	@Resource	private MktShopSurveyService mktShopSurveyService;
	@Resource private TradeMessageTemplateService tradeMessageTemplateService;
	@Resource private FesProcessAttachmentDao fesProcessAttachmentDao;
	@Resource private SynMerchantDao synMerchantDao;
	 
	
	/**
	 * 运营平台新建商户测试
	 *   需要更改  MerchantDaoImpl类中的方法 getMerchantsForSurvey() ， 得到门店的数据
	 */
	@Test
	@Rollback(false)
	public void saveSynMerchant(){
        Map<String, Object> result = this.synMerchantService.saveTestMerchant();
	}
	
	/**
	 *  查询交易短信模板
	 */
	@Test
	public void listTradeMessageTemplates (){
		logger.info("");
		logger.info("");
		logger.info("");
		logger.info(System.getProperties());
		logger.info("");
		logger.info("");
		logger.info("");
//		String merchantNo = "111111111111111";
//		List<TradeMessageTemplateVO> listTradeMessageTemplates = tradeMessageTemplateService.listTradeMessageTemplates(merchantNo);
//		this.printJson(listTradeMessageTemplates);
	}
	/**
	 *  返回前端我的主页查询出来的所有商户信息
	 */
	@Test
	public void getComplexSynMerchants () {
		Map<String, Object> inputMap = new HashMap<String, Object>();
//		inputMap.put("userId", 1);
//		inputMap.put("merchantName", "北京");
//		inputMap.put("merchantStatus", "2");
//		inputMap.put("merchantStatusType", 0);
		inputMap.put(Constant.PAGE_NUMBER, 1);
		inputMap.put(Constant.PAGE_SIZE, 10);		
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(8);
		List<ComplexSynMerchantVO> complexSynMerchants = this.synMerchantService.getComplexSynMerchants(inputMap, sessionUser);
		this.printJson(complexSynMerchants);
		this.printJson("complexSynMerchants.size() "+complexSynMerchants.size());
//		Assert.assertTrue(complexSynMerchants.size()>0);
		//test
//		String fesStepJson = "{\"id\":10,\"stepNum\":\"online\",\"stepName\":\"项目上线\",\"tip\":\"请确认项目是否已上线。\",\"sort\":10,\"isEnable\":\"1\",\"remark\":null,\"insertBy\":null,\"insertTime\":null,\"updateBy\":null,\"updateTime\":null,\"sortColumns\":null,\"fesOnlineProcesss\":[]}";
//		FesStepVO fesStep = mapper.readValue(fesStepJson.getBytes(), FesStepVO.class);
//		LOG.info("test ---------- :"+ mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fesStep));
	}

	/**
	 * 分配负责人
	 */
	@Test
	public void saveSysUserMerchant () throws JsonParseException, JsonMappingException, IOException{
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(8);

		Integer merchantId = 965;
		Integer userId = 139;
		SysUserMerchantVO sysUserMerchant = new SysUserMerchantVO();
		sysUserMerchant.setUserId(userId);
		sysUserMerchant.setOldUserId(1);
		sysUserMerchant.setMerchantId(merchantId);

		SysToDoListVO sysToDoList = new SysToDoListVO();
		String[] inputItemTypes = new String[]{"01"};
		sysToDoList.setUserId(userId);
		sysToDoList.setItemStatus("0");
		sysToDoList.setInputItemTypes(inputItemTypes);
		LOG.info("新用户的待办事项个数before："+ this.sysToDoListService.getComplexSysToDoLists(sysToDoList).size());
		int pk = sysUserMerchantService.saveSysUserMerchant(sysUserMerchant, sessionUser);
		Assert.assertTrue(pk>0);
		LOG.info("新用户的待办事项个数after："+ this.sysToDoListService.getComplexSysToDoLists(sysToDoList).size());
//		this.printJson(sysUserMerchant);
		//查询上线流程
		FesOnlineProgramVO fesOnlineProgram = new FesOnlineProgramVO();
		fesOnlineProgram.setMerchantId(merchantId);
		List<FesOnlineProgramVO> fesOnlinePrograms = fesOnlineProgramService.getFesOnlinePrograms(fesOnlineProgram);
		Assert.assertTrue(fesOnlinePrograms.size()>0);
		Integer programId = fesOnlinePrograms.get(0).getId();//只有一个上线计划
		FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
		fesOnlineProcessVO.setProgramId(programId);
		List<FesOnlineProcessVO> fesOnlineProcesss = fesOnlineProcessService.getComplexFesOnlineProcesss(fesOnlineProcessVO);
//		this.printJson(fesOnlineProcesss);
	}
	
	/**
	 * 查询待办事项
	 */
	@Test
	public void getComplexSysToDoLists(){
		SysToDoListVO sysToDoList = new SysToDoListVO();
//		sysToDoList.setMerchantName("飞洒");
		sysToDoList.setUserId(1);
		sysToDoList.setPageNumber(1);
		sysToDoList.setPageSize(10);
		String[] inputItemTypes = new String[]{"01"};
		sysToDoList.setInputItemTypes(inputItemTypes);
		sysToDoList.setBusinessTypes(new String[]{"4","5","6"});
//		String desc= "查询待办事项";
//		this.genTestReport(sysToDoList, "myHomePage/getComplexSysToDoLists", desc);
//		JsonResult complexSysToDoLists = fesMyHomePageController.getComplexSysToDoLists(sysToDoList);
//		Map<String, Object> map = (Map<String, Object>)complexSysToDoLists.getData();
//		List<SysToDoListVO> list = (List)map.get(Constant.ROWS);
//		for (SysToDoListVO sysToDoListVO :list ) {
//			LOG.info("sysToDoListVO.getItemTypeText(): "+sysToDoListVO.getItemTypeText());
//		}
//		JsonResult complexSysToDoLists = fesMyHomePageController.getComplexSysToDoLists(sysToDoList);
		this.printJson(fesMyHomePageController.getComplexSysToDoLists(sysToDoList));
	}
	
	/**
	 * 通过资源 remark 查询 所有拥有该资源的用户 
	 */
	@Test
	public void getAllUsersByResourceCode() {
		SysResourceVO sysResourceVO = new SysResourceVO();
		sysResourceVO.setRemark("course_sty_his_review");
//		String desc = "通过资源 remark 查询 所有拥有该资源的用户";
//		this.genTestReport(sysResourceVO, "user/getAllUsersByResourceCode", desc);
		JsonResult list = sysUserController.getAllUsersByResourceCode(sysResourceVO);
		this.printJson(list);
	}
	/**
	 * 查找下属
	 * @param map{}
	 */
	@Test
	public void getSubordinateEmployees() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.PAGE_NUMBER, 1);
		map.put(Constant.PAGE_SIZE, 10);
		map.put("baseUserId", 109);//谁的下属
//		map.put("subUserName", "0828");//下属的名字
//		String desc = "我的主页, 我的下属";
//		this.genTestReport(map, "group/getSubordinateEmployees", desc);
		JsonResult subordinateEmployees = sysGroupController.getSubordinateEmployees(map);
		this.printJson(subordinateEmployees);
	}
	
	/**
	 * 需求看板相关
	 */
	@Test
	public void saveReqRequirement(){
		ReqRequirementVO reqRequirementVO = new ReqRequirementVO();
		java.util.List projectIds = new ArrayList<String>();
		reqRequirementVO.setProjectIds(projectIds);
		reqRequirementVO.setId(16);
		reqRequirementVO.setRequirementStatus("6");
		this.printJson(reqRequirementService.saveReqRequirement(reqRequirementVO));
		this.printJson(reqRequirementVO);
	}
	@Test
	public void updateReqRequirement(){
		ReqRequirementVO reqRequirementVO = new ReqRequirementVO();
		reqRequirementVO.setId(1);
		Date date = new Date();
		reqRequirementVO.setOnlineTime(date);
		this.printJson(reqRequirementService.updateReqRequirement(reqRequirementVO));
		LOG.info("onlineTime: " + reqRequirementService.getReqRequirementById(1).getOnlineTime());
	}
	/**
	 * 其他
	 */
	@Test
	public void salesConfirm(){
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(8);
		Integer merchantId = 881;
		this.mktShopSurveyService.salesConfirm(merchantId, sessionUser);
	}
	
	@Test
	public void checkIfChangeStatusForStep7() {
		Integer processId = 885;
		List<String> listStatus = Arrays.asList("6","7","8","9","10","11","13");
		FesProcessAttachmentVO fesProcessAttachment = new FesProcessAttachmentVO();
		fesProcessAttachment.setProcessId(processId);
		List<FesProcessAttachmentVO> fesProcessAttachments = this.fesProcessAttachmentDao.getFesProcessAttachments(fesProcessAttachment);
		boolean flag = true;
		for (String status : listStatus) {
			for (FesProcessAttachmentVO fesProcessAttachmentVO : fesProcessAttachments) {
				if(!status.equals(fesProcessAttachmentVO.getProcessAttachmentType())){
					flag = false; //存在没有上传的附件
					break;
				}
			}
		}
		logger.info(flag);
	}
}