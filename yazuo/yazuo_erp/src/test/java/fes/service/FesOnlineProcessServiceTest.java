/**
 * @Description 上线流程步骤测试
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package fes.service;

import static com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.resource_fes_card_and_materials;
import static junit.framework.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;

import base.JUnitDaoBaseWithTrans;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.base.SendMessageVoid;
import com.yazuo.erp.fes.controller.FesOnlineProcessController;
import com.yazuo.erp.fes.dao.FesOnlineProgramDao;
import com.yazuo.erp.fes.service.FesMaterialRequestService;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.fes.service.FesOnlineProgramService;
import com.yazuo.erp.fes.service.FesOpenCardApplicationService;
import com.yazuo.erp.fes.service.FesRemarkService;
import com.yazuo.erp.fes.service.FesStowageService;
import com.yazuo.erp.fes.service.FesTrainPlanService;
import com.yazuo.erp.fes.service.impl.FesOnlineProcessServiceImpl.StepNum;
import com.yazuo.erp.fes.vo.FesMaterialRequestDtlVO;
import com.yazuo.erp.fes.vo.FesMaterialRequestVO;
import com.yazuo.erp.fes.vo.FesOnlineProcessVO;
import com.yazuo.erp.fes.vo.FesOnlineProgramVO;
import com.yazuo.erp.fes.vo.FesOpenCardApplicationDtlVO;
import com.yazuo.erp.fes.vo.FesOpenCardApplicationVO;
import com.yazuo.erp.fes.vo.FesStowageDtlVO;
import com.yazuo.erp.fes.vo.FesStowageVO;
import com.yazuo.erp.fes.vo.FesTrainPlanVO;
import com.yazuo.erp.mkt.controller.MktContactController;
import com.yazuo.erp.syn.dao.SynMerchantDao;
import com.yazuo.erp.system.dao.SysCustomerComplaintDao;
import com.yazuo.erp.system.dao.SysProductOperationDao;
import com.yazuo.erp.system.service.SysAssistantMerchantService;
import com.yazuo.erp.system.service.SysAttachmentService;
import com.yazuo.erp.system.service.SysDocumentService;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.service.SysUserMerchantService;
import com.yazuo.erp.system.vo.SysAssistantMerchantVO;
import com.yazuo.erp.system.vo.SysAttachmentVO;
import com.yazuo.erp.system.vo.SysCustomerComplaintVO;
import com.yazuo.erp.system.vo.SysDocumentVO;
import com.yazuo.erp.system.vo.SysMailTemplateVO;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysProductOperationVO;
import com.yazuo.erp.system.vo.SysToDoListVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.trade.service.TradeCardtypeService;
import com.yazuo.erp.trade.service.TradeMessageTemplateService;
import com.yazuo.erp.trade.vo.TradeCardtypeVO;
import com.yazuo.erp.trade.vo.TradeMessageTemplateVO;
import com.yazuo.erp.train.service.CalendarService;
import com.yazuo.external.card.dao.MemberCardInfoDao;

/**
 * @Description 
 * @author erp team
 * @date 
 */

/**
 * @Description
 */
public class FesOnlineProcessServiceTest extends JUnitDaoBaseWithTrans {

	private static final Log LOG = LogFactory.getLog(FesOnlineProcessServiceTest.class);
    @Resource  	private FesOnlineProcessService fesOnlineProcessService;
	@Resource  	private CalendarService calendarService;
	@Resource  	private FesTrainPlanService fesTrainPlanService;
	@Resource  	private FesRemarkService fesRemarkService;
	@Resource  	private SysAttachmentService sysAttachmentService;
	@Resource  	private FesOnlineProgramDao fesOnlineProgramDao;
	@Resource  	private SysUserMerchantService sysUserMerchantService;
	@Resource  	private FesOnlineProgramService fesOnlineProgramService;
	@Resource  	private SysToDoListService sysToDoListService;
	@Resource  	private FesOnlineProcessController fesOnlineProcessController;
	@Resource  	private SysOperationLogService sysOperationLogService;
	@Resource  	private FesOpenCardApplicationService fesOpenCardApplicationService;
	@Resource  	private FesStowageService fesStowageService;
	@Resource  	private SendMessageVoid sendMessageVoid;
	@Resource  	private SysAssistantMerchantService sysAssistantMerchantService;
	@Resource  	private TradeCardtypeService tradeCardtypeService;
	@Resource  	private TradeMessageTemplateService tradeMessageTemplateService;
	@Resource  	private SynMerchantDao synMerchantDao;
	@Resource  	private MemberCardInfoDao memberCardInfoDao;
	@Resource  	private MktContactController mktContactController;
	@Resource  	private SysDocumentService sysDocumentService;
	@Resource  	private FesMaterialRequestService fesMaterialRequestService;
	
	@Resource  	private SysCustomerComplaintDao sysCustomerComplaintDao;
	@Resource  	private SysProductOperationDao sysProductOperationDao;
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void getSysCustomerComplaints() {
		sysCustomerComplaintDao.getSysCustomerComplaints(new SysCustomerComplaintVO());
	}
	
	@Test
	public void getSysProductOperations() {
		sysProductOperationDao.getSysProductOperations(new SysProductOperationVO());
	}
	@Test
	public void testGetElementFromMap() {
		Map<String,String> map = new HashMap<String, String>();
		logger.info(map.get("dd"));
		logger.info(map.get(""));
		logger.info(map.get(null));
	}
	@Test
	@Rollback(false)
	public void saveFesMaterialRequestAndDtls() {	
		FesMaterialRequestVO fesMaterialRequest = new FesMaterialRequestVO();
		fesMaterialRequest.setProcessId(959);
		fesMaterialRequest.setAccessId(1);
		fesMaterialRequest.setDescription("物料文案");
		List<FesMaterialRequestDtlVO> list = new LinkedList<FesMaterialRequestDtlVO>();
		for(int i=0;i<2;i++){
			FesMaterialRequestDtlVO fesMaterialRequestDtlVO = new FesMaterialRequestDtlVO();
			fesMaterialRequestDtlVO.setMaterialRequestType("2");
			fesMaterialRequestDtlVO.setSpecificationType("2");
			list.add(fesMaterialRequestDtlVO);
		}
		fesMaterialRequest.setFesMaterialRequestDtlVOs(list);
		this.printJson(fesMaterialRequest);
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(1);
		FesOnlineProcessVO fesOnlineProcessVO = this.fesMaterialRequestService.saveFesMaterialRequestAndDtls(fesMaterialRequest, sessionUser);
		this.printJson(fesOnlineProcessVO);
	}
	@Test
	public void getFesMaterialRequestById() {
		this.printJson(this.fesMaterialRequestService.getFesMaterialRequestById(13));
	}
	/**
	 * 测试查找邮件模板
	 */
	@Test
	public void getEmailTemplates() {
		SysMailTemplateVO sysMailTemplate = new SysMailTemplateVO();
		sysMailTemplate.setMailTemplateType("001");
		this.printJson(fesOnlineProcessController.getEmailTemplates(sysMailTemplate));
		this.genTestReport(sysMailTemplate, "fesOnlineProcess/getEmailTemplates", "查找邮件模板");
	}
	/**
	 * 查找联系人
	 */
	@Test
	public void searchContacts() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantId", 1);
		this.printJson(mktContactController.searchContacts(paramMap));
	}
	/**
	 * 测试第十歩 上线 并发送邮件
	 */
	@Test
	public void updateOnlineStatusAndSendEmail() {
		/*Integer processId = 782;
		FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
		fesOnlineProcessVO.setId(processId);
		fesOnlineProcessVO.setOnlineProcessStatus("04");
		
		String context = "<div class=\"app-inner-l fn-left\"><a class=\"logo-l\" href=\"http://localhost:8080/yazuo_erp/\">雅座ERP</a></div>";
		String[] contacts = new String[]{"songfuyu@yazuo.com"};
		EmailVO email = new EmailVO();
		email.setSendAddress("fff@dd.com");
		email.setSubject("haha");
		email.setContext(context);
		email.setTo(contacts);
		*/
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(1);
		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constant.SESSION_USER, sessionUser);
		
		String input = "{\"id\":1,\"onlineProcessStatus\":\"04\",\"email\":{\"sendAddress\":\"11@yazuo.com\",\"subject\":\"subject\",\"to\":[\"1@yazuo.com\",\"1@yazuo.com\"],\"context\":\"context\"}}";
		FesOnlineProcessVO fesOnlineProcessVO = (FesOnlineProcessVO) this.readValueToVO(input, FesOnlineProcessVO.class);
		JsonResult jsonResult  = 
				fesOnlineProcessController.updateOnlineStatusAndSendEmail(fesOnlineProcessVO, session);
		this.printJson(jsonResult);
	}
	@Test
	public void getMemberCardInfoByBrandId() {
		List<Map<String, Object>> stepNumByProcessId = memberCardInfoDao.getMemberCardInfoByBrandId(788);
		this.printJson(stepNumByProcessId);
	}
	
	/**
	 * 返回所有的上线流程信息
	 */
	@Test
	public void getComplexFesOnlineProcesss() throws JsonGenerationException, JsonMappingException, IOException {
		FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
		fesOnlineProcessVO.setMerchantId(638);
		fesOnlineProcessVO.setUserId(1);
		fesOnlineProcessVO.setResourceRemark(resource_fes_card_and_materials);
		
		List<FesOnlineProcessVO> complexFesOnlineProcesss = fesOnlineProcessService
				.getComplexFesOnlineProcesss(fesOnlineProcessVO);
		 this.printJson(complexFesOnlineProcesss);
//		for (FesOnlineProcessVO fesOnlineProcessVO2 : complexFesOnlineProcesss) {
//			logger.info(fesOnlineProcessVO2.getTradeCardtypes());
//			logger.info(fesOnlineProcessVO2.getTradeMessageTemplates());
//		}
		// Assert.assertTrue(complexFesOnlineProcesss.size() > 0);
		LOG.info(complexFesOnlineProcesss.size());
		// test
		// String fesStepJson =
		// "{\"id\":10,\"stepNum\":\"online\",\"stepName\":\"项目上线\",\"tip\":\"请确认项目是否已上线。\",\"sort\":10,\"isEnable\":\"1\",\"remark\":null,\"insertBy\":null,\"insertTime\":null,\"updateBy\":null,\"updateTime\":null,\"sortColumns\":null,\"fesOnlineProcesss\":[]}";
		// FesStepVO fesStep = mapper.readValue(fesStepJson.getBytes(),
		// FesStepVO.class);
		// LOG.info("test ---------- :"+
		// mapper.writerWithDefaultPrettyPrinter().writeValueAsString(fesStep));
		// this.printJson(fesOnlineProcessController.listFesOnlineProcesss(4));
	}

	/**
	 * 上传附件 包括：[1.文件上传 2.状态更新 3.待办事项]
	 */
	@Test
	@Rollback(true)
	public void saveUploadFiles() {
		int processId = 839;
		Integer typeId = 13;
		final String fileName = "test.txt";
		final byte[] content = "Hello Word".getBytes();
		MockMultipartFile myfile = new MockMultipartFile("myfile", fileName, "text/plain", content);
		String stepNumByProcessId = fesOnlineProcessService.getStepNumByProcessId(processId);
		LOG.info("stepNumByProcessId: " + stepNumByProcessId);
		String realPath = "D:\\workspaces\\YaZuo\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp4\\wtpwebapps\\yazuo_erp_trunk";
		SysUserVO sessionUser = new SysUserVO();// falsh 方式上传
		sessionUser.setId(8);
		// String method = "fesOnlineProcess/uploadFiles";
		// this.genTestReport(parameter, method, desc);
		Map<String, Object> fileMap = null;
		JsonResult result = this.fesOnlineProcessService.saveUploadFiles(myfile, processId, typeId, realPath, fileMap, sessionUser);
		this.printJson(result);
	}

	/**
	 * 删除 附件
	 */
	@Test
	@Rollback(false)
	public void deleteSysAttachmentById() {
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(109);
		String realPath = "D:\\workspaces\\YaZuo\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp4\\wtpwebapps\\yazuo_erp_trunk";
		JsonResult jsonResult = this.fesOnlineProcessService.deleteSysAttachmentById(155, 200, realPath, sessionUser);
		this.printJson(jsonResult);
		Assert.assertTrue(jsonResult.getFlag() > 0);
	}

	/**
	 * 更新上线流程状态， 前台用 ， 此方法和上传没有关系
	 */
	@Test
	public void updateProgramOnlineTime() {
		FesOnlineProgramVO fesOnlineProgramById1 = this.fesOnlineProgramDao.getFesOnlineProgramById(86);
		Date onlineTimeBefore = fesOnlineProgramById1.getOnlineTime();
		LOG.info("program online_time:"+onlineTimeBefore.toString());
		
		this.fesOnlineProcessService.updateProgramOnlineTime(448);
		
		FesOnlineProgramVO fesOnlineProgramById = this.fesOnlineProgramDao.getFesOnlineProgramById(86);
		Date onlineTimeAfter = fesOnlineProgramById.getOnlineTime();
		Assert.assertEquals(onlineTimeBefore, onlineTimeAfter);
		LOG.info("program online_time:"+onlineTimeAfter.toString());
	}
	/**
	 * 更新上线流程状态， 前台用 ， 此方法和上传没有关系
	 * 
	 * @param fesOnlineProcess
	 *            [id, onlineProcessStatus]
	 */
	@Test
	public void updateFesOnlineProcessStatus() {
		Integer processId = 84;
		FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
		fesOnlineProcessVO.setId(processId);
		fesOnlineProcessVO.setOnlineProcessStatus("06");
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(1);
		// String desc = "更新 上线流程 状态(点击按钮的时候用)";
		// this.genTestReport(fesOnlineProcessVO,
		// "fesOnlineProcess/updateFesOnlineProcessStatus", desc);
		JsonResult jsonResult = fesOnlineProcessService.updateFesOnlineProcessStatus(fesOnlineProcessVO, sessionUser);
		this.printJson(jsonResult);
//		FesOnlineProcessVO fesOnlineProcessById = fesOnlineProcessService.getFesOnlineProcessById(processId);
//		FesOnlineProgramVO fesOnlineProgramById = this.fesOnlineProgramDao.getFesOnlineProgramById(fesOnlineProcessById.getProgramId());
//		try {
//			LOG.info("process end_time:"+fesOnlineProcessById.getEndTime().toString());
//			LOG.info("program online_time:"+fesOnlineProgramById.getOnlineTime().toString());
//			SynMerchantVO synMerchantById = this.synMerchantDao.getSynMerchantById(fesOnlineProgramById.getMerchantId());
//			LOG.info("syn_emrchant service start time: "+synMerchantById.getServiceStartTime().toString());
//			LOG.info("syn_emrchant service end time: "+synMerchantById.getServiceEndTime().toString());
//		} catch (Exception e) {
//			Assume.assumeNoException(e);
//		}
		
		// LOG.info(fesOnlineProcessVO.getOnlineProcessStatus());
		// 查询新添加的待办事项
//		SysToDoListVO sysToDoList = new SysToDoListVO();
//		sysToDoList.setRelatedId(processId);
//		sysToDoList.setRelatedType("1");
//		List<SysToDoListVO> sysToDoLists = sysToDoListService.getSysToDoLists(sysToDoList);
//		Assume.assumeTrue(sysToDoLists.size() > 0);
//		this.printJson(sysToDoLists);
		// 查询操作流水
//		List<SysOperationLogVO> sysOperationLogs = sysOperationLogService.getSysOperationLogs(null);
//		this.printJson(sysOperationLogs);
	}

	/**
	 * 查询待办事项
	 */
	@Test
	public void getSysToDoLists(){
		// 查询新添加的待办事项
		SysToDoListVO sysToDoList = new SysToDoListVO();
		sysToDoList.setRelatedId(333);
		sysToDoList.setRelatedType("1");
		String[] bussinessTypes = new String[]{"3","4"};
		sysToDoList.setBusinessTypes(bussinessTypes);
		List<SysToDoListVO> sysToDoLists = sysToDoListService.getSysToDoLists(sysToDoList);
	}
	/**
	 * 文件下载
	 * 
	 * @throws IOException
	 */
	@Test
	public void testDownload() throws IOException {
		String relPath = null;
		String desc = "文件下载";
		this.genTestReport(null, "fesOnlineProcess/download", desc);
		this.fesOnlineProcessController.download(relPath, response, request);
	}

	/**
	 * 关闭待办事项
	 * 
	 * @param SysToDoListVO
	 *            [ relatedId: 相关id integer relatedType: 相关类型 String]
	 */
	@Test
	public void batchUpdateCloseSysToDoLists() {
		SysToDoListVO inputSysToDoListVO = new SysToDoListVO();
		inputSysToDoListVO.setRelatedId(26);
		inputSysToDoListVO.setRelatedType("1");// 上线流程
		int row = this.sysToDoListService.batchUpdateCloseSysToDoLists(inputSysToDoListVO);
		assertTrue(row > 0);
		this.printJson(sysToDoListService.getSysToDoLists(inputSysToDoListVO));
	}

	/**
	 * 上传附件 成功后 更新流程状态
	 */
	@Test
	public void updateSpecificProccessStatus() {
		int[] processIds = new int[] { 878 }; 
		for (int processId : processIds) {
			FesOnlineProcessVO updateProccessStatusForUpload = this.fesOnlineProcessService.updateProccessStatusForUpload(processId, 4);
			this.printJson(updateProccessStatusForUpload);
		}

	}

	/**
	 * 保存 培训计划
	 */
	@Test
	@Rollback(true)
	public void saveFesTrainPlan() throws JsonParseException, JsonMappingException, IOException {
		String fesTrainPlan = "{\"processId\":\"31\", \"address\":\"111啊时代发生地方\",\"beginTime\":1405937603000,\"content\":\"111阿桑地方\",\"endTime\":1405937603000,\"trainer\":\"1阿桑地方\"}";
		FesTrainPlanVO fesTrainPlanVO = mapper.readValue(fesTrainPlan.getBytes(), FesTrainPlanVO.class);
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(109);
		this.genTestReport(fesTrainPlanVO, "fesOnlineProcess/saveFesTrainPlan", "保存 培训计划");
		FesTrainPlanVO fesTrainPlanVO2 = this.fesTrainPlanService.saveFesTrainPlan(fesTrainPlanVO, sessionUser);
		this.printJson(fesTrainPlanVO2);
		Assert.assertTrue(fesTrainPlanVO2 != null);
	}

	/**
	 * 测试保存开卡申请
	 */
	@Test
	public void saveFesOpenCardApplicationAndDtls() {
//		String testString = "{\"id\":40,\"processId\":1,\"merchantId\":1,\"openCardApplicationStatus\":\"1\",\"isEnable\":\"1\",\"remark\":null,\"insertBy\":1,\"insertTime\":1408956432714,\"updateBy\":1,\"updateTime\":1408956432714,\"sortColumns\":null,\"fesOpenCardApplicationDtls\":[{\"id\":null,\"applicationId\":40,\"cardName\":\"₋뚰鹇\",\"cardAmount\":5.0,\"validityTerm\":0,\"isContainFour\":\"0\",\"isContainSeven\":\"0\",\"remark\":null,\"insertBy\":1,\"insertTime\":null,\"processId\":null,\"merchantId\":null,\"fesOpenCardApplication\":null,\"sortColumns\":null}],\"synMerchant\":null,\"fesOnlineProcess\":null}";
		String testString = "{\"processId\":198,\"merchantId\":243,\"fesOpenCardApplicationDtls\":[{\"cardName\":\"213\",\"cardAmount\":123123,\"validityTerm\":123123,\"isContainFour\":\"1\",\"isContainSeven\":\"1\"},{\"cardName\":\"123\",\"cardAmount\":123,\"validityTerm\":123,\"isContainFour\":\"1\",\"isContainSeven\":\"0\"}]}";
		FesOpenCardApplicationVO fesOpenCardApplicationVO =null;
		try {
			fesOpenCardApplicationVO = this.mapper.readValue(testString.getBytes(), FesOpenCardApplicationVO.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		this.genTestReport(fesOpenCardApplicationVO, "applySetting/saveOpenCardApply", "保存开卡申请");
//		FesOpenCardApplicationVO fesOpenCardApplicationVO = getFesOpenCardApplicationVO();
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(1);
		fesOpenCardApplicationService.saveFesOpenCardApplicationAndDtls(fesOpenCardApplicationVO, sessionUser);
		this.printJson(fesOpenCardApplicationVO);
	}
	
	/**
	 * 保存设备配送
	 */
	@Test
	public void saveFesStowage() {
//		String input = "{\"logisticsNum\":\"asfasdf\",\"field1\":\"asdfasdf\",\"contactId\":10,\"processId\":198,\"fesStowageDtls\":[{\"category\":\"2\"}]}";
//		FesStowageVO fesStowage =null;
//		try {
//			fesStowage = this.mapper.readValue(input.getBytes(), FesStowageVO.class);
//		} catch (JsonParseException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(1);
		FesStowageVO fesStowage = new FesStowageVO();
		fesStowage.setProcessId(28);
		fesStowage.setContactId(9);
		List<FesStowageDtlVO> fesStowageDtlVOs = new ArrayList<FesStowageDtlVO>();
		for (int i=0; i<3; i++) {
			FesStowageDtlVO fesStowageDtlVO = new FesStowageDtlVO();
			fesStowageDtlVO.setCategory("2");
			fesStowageDtlVO.setAmount(BigDecimal.valueOf(5f));
			fesStowageDtlVO.setUnitType(RandomStringUtils.random(3));
			fesStowageDtlVOs.add(fesStowageDtlVO);
		}
		fesStowage.setFesStowageDtls((List<FesStowageDtlVO>) fesStowageDtlVOs);
		this.genTestReport(fesStowage, "applySetting/saveFesStowage", "新增设备配送");
		fesStowageService.saveFesStowage(fesStowage, sessionUser);
		this.printJson(fesStowage);
	}

	/**
	 * @Description 返回测试参数
	 */
	private FesOpenCardApplicationVO getFesOpenCardApplicationVO() {
		FesOpenCardApplicationVO fesOpenCardApplicationVO = new FesOpenCardApplicationVO();
		fesOpenCardApplicationVO.setMerchantId(1);
		fesOpenCardApplicationVO.setProcessId(1);
		List<FesOpenCardApplicationDtlVO> fesOpenCardApplicationDtls = new LinkedList<FesOpenCardApplicationDtlVO>();
		for (int i=0; i<4; i++) {
			FesOpenCardApplicationDtlVO fesOpenCardApplicationDtlVO = new FesOpenCardApplicationDtlVO();
			fesOpenCardApplicationDtlVO.setCardName(RandomStringUtils.random(3));
			fesOpenCardApplicationDtlVO.setCardAmount(BigDecimal.valueOf(5f));
			fesOpenCardApplicationDtlVO.setValidityTerm(RandomUtils.nextInt(1));
			fesOpenCardApplicationDtlVO.setIsContainFour("0");
			fesOpenCardApplicationDtlVO.setIsContainSeven("0");
			fesOpenCardApplicationDtls.add(fesOpenCardApplicationDtlVO);
		} 
		fesOpenCardApplicationVO.setFesOpenCardApplicationDtls(fesOpenCardApplicationDtls);
		return fesOpenCardApplicationVO;
	}
	/**
	 * 创建10个步骤
	 */
	@Test
	@Rollback(true)
	public void batchInsertFesOnlineProcesss() {
		// 不允许为空的字段：
		// program_id,step_id,online_process_status,insert_by,insert_time,update_by,update_time,
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(1);
		int rows = fesOnlineProcessService.batchInsertFesOnlineProcesss(4, sessionUser);

		FesOnlineProgramVO fesOnlineProgram = fesOnlineProgramDao.getFesOnlineProgramById(4);
		LOG.info(" contractId: " + fesOnlineProgram.getContractId());

		List<FesOnlineProcessVO> onlineProcesss = this.fesOnlineProcessService.getFesOnlineProcesss(null);
		for (FesOnlineProcessVO fesOnlineProcessVO : onlineProcesss) {
			LOG.info(fesOnlineProcessVO);
		}
		Assert.assertTrue(rows > 0);
	}

	/**
	 * 测试查询开卡申请单和明细
	 */
	@Test
	public void getFesOpenCardApplicationsAndDtls() {
		FesOpenCardApplicationVO fesOpenCardApplication = new FesOpenCardApplicationVO();
		fesOpenCardApplication.setProcessId(958);
		List<FesOpenCardApplicationVO> dtls = this.fesOpenCardApplicationService.getFesOpenCardApplicationsAndDtls(fesOpenCardApplication);
		this.printJson(dtls);
//		this.genTestReport(fesOpenCardApplication, "fesOnlineProcess/getFesOpenCardApplicationsAndDtls", "查询开卡申请单和明细");
	}
	/**
	 * 工具方法测试
	 */
	@Test
	public void getFesOnlineProcessByMerchantAndStep() {
		FesOnlineProcessVO fesOnlineProcessVO = new FesOnlineProcessVO();
		fesOnlineProcessVO.setMerchantId(1592);
		fesOnlineProcessVO.setStepNum(StepNum.background_set.toString());
		FesOnlineProcessVO result = this.fesOnlineProcessService.getFesOnlineProcessByMerchantAndStep(fesOnlineProcessVO);
		assertTrue(result!=null);
		this.printJson(result);
	}
	
	/**
	 * 保存客服商户关系
	 */
	@Test
	@Rollback(false)
	public void saveSysAssistantMerchant() {		
		SysUserVO sessionUser = new SysUserVO();
	    sessionUser.setId(1);
		SysAssistantMerchantVO sysAssistantMerchant = new SysAssistantMerchantVO();
		sysAssistantMerchant.setMerchantId(1);
		sysAssistantMerchant.setUserId(13);
		sysAssistantMerchant.setNewUserId(null);
//		this.genTestReport(sysAssistantMerchant, "fesOnlineProcess/saveSysAssistantMerchant", "保存客服商户关系");
		sysAssistantMerchantService.saveOrUpdateSysAssistantMerchant(sysAssistantMerchant, sessionUser);
		this.printJson(sysAssistantMerchant);
	}
	
	/**
	 * 保存会员卡类型 会员规则
	 */
	@Test
	public void saveCardAndOperatoinLogs() {		
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(1);
		String cardType = "{\"cardtype\":\"test11\",\"id\":55,\"gradeLevel\":1,\"tradeAwardRuleVOs\":[],\"merchantId\":1212},{\"cardtype\":\"1\",\"id\":56,\"price\":\"1\",\"gradeLevel\":2,\"tradeAwardRuleVOs\":[{\"id\":71,\"awardAmount\":\"1\",\"awardType\":1,\"transCode\":\"0002\",\"awardAmountType\":2}],\"merchantId\":1212},{\"cardtype\":\"1\",\"id\":57,\"gradeLevel\":3,\"rebateType\":2,\"tradeAwardRuleVOs\":[{\"id\":72,\"lowerLimit\":\"1\",\"upperLimit\":\"1\",\"awardAmount\":\"1\",\"rebateType\":2,\"awardType\":2,\"awardAmountType\":2,\"transCode\":\"2001\"}],\"merchantId\":1212},{\"cardtype\":\"1\",\"price\":\"1\",\"id\":58,\"gradeLevel\":4,\"rebateType\":2,\"tradeAwardRuleVOs\":[{\"id\":73,\"lowerLimit\":\"0\",\"upperLimit\":\"10000000\",\"awardAmount\":\"1\",\"rebateType\":2,\"awardType\":2,\"awardAmountType\":2,\"transCode\":\"2001\"},{\"id\":74,\"awardAmount\":\"1\",\"awardType\":1,\"transCode\":\"0002\",\"awardAmountType\":2}],\"merchantId\":1212}";
		TradeCardtypeVO tradeCardtypeVO = (TradeCardtypeVO)this.readValueToVO(cardType, TradeCardtypeVO.class);
		List<TradeCardtypeVO> tradeCardtypeVOs = new LinkedList<TradeCardtypeVO>();
		tradeCardtypeVOs.add(tradeCardtypeVO);
		SysOperationLogVO logVOs = this.tradeCardtypeService.saveCardAndOperatoinLogs(tradeCardtypeVOs, sessionUser);
		this.printJson(logVOs);
	}
	
	/**
	 * 保存交易短信规则 会员规则
	 */
	@Test
	@Deprecated //未做测试
	public void saveMessageAndOperatoinLogs() {		
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(1);
		String input = "{\"cardtype\":\"test11\",\"id\":55,\"gradeLevel\":1,\"tradeAwardRuleVOs\":[],\"merchantId\":1212},{\"cardtype\":\"1\",\"id\":56,\"price\":\"1\",\"gradeLevel\":2,\"tradeAwardRuleVOs\":[{\"id\":71,\"awardAmount\":\"1\",\"awardType\":1,\"transCode\":\"0002\",\"awardAmountType\":2}],\"merchantId\":1212},{\"cardtype\":\"1\",\"id\":57,\"gradeLevel\":3,\"rebateType\":2,\"tradeAwardRuleVOs\":[{\"id\":72,\"lowerLimit\":\"1\",\"upperLimit\":\"1\",\"awardAmount\":\"1\",\"rebateType\":2,\"awardType\":2,\"awardAmountType\":2,\"transCode\":\"2001\"}],\"merchantId\":1212},{\"cardtype\":\"1\",\"price\":\"1\",\"id\":58,\"gradeLevel\":4,\"rebateType\":2,\"tradeAwardRuleVOs\":[{\"id\":73,\"lowerLimit\":\"0\",\"upperLimit\":\"10000000\",\"awardAmount\":\"1\",\"rebateType\":2,\"awardType\":2,\"awardAmountType\":2,\"transCode\":\"2001\"},{\"id\":74,\"awardAmount\":\"1\",\"awardType\":1,\"transCode\":\"0002\",\"awardAmountType\":2}],\"merchantId\":1212}";
		TradeMessageTemplateVO tradeMessageTemplateVO = (TradeMessageTemplateVO)this.readValueToVO(input, TradeMessageTemplateVO.class);
		List<TradeMessageTemplateVO> lists = new LinkedList<TradeMessageTemplateVO>();
		lists.add(tradeMessageTemplateVO);
		SysOperationLogVO logVOs = this.tradeMessageTemplateService.saveMessageAndOperatoinLogs(lists, sessionUser);
		this.printJson(logVOs);
	}
	
	
	/**
	 * 短信提醒测试
	 */
	@Test
	@Rollback(false)
	public void sendMessageVoid() {
		this.sendMessageVoid.sendMessage("和哈哈哈哈哈哈哈哈哈", "13521768597");
	}
	
	/**
	 * 测试日历
	 */
	@Test
	public void testCalendarRoll() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR)+ 0;
		int month = calendar.get(Calendar.MONTH)+ 6; //赠送月份取值范围0-5
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		LOG.info("当前是几月几日："+month+" "+day);
		if(month>=11){
			year = year+1;
			month = month%12;
		}
		LOG.info("month："+month);
		calendar.set(year, month, day);
		logger.info(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE));
	}

	/**
	 * 日期 延后多少个工作日测试
	 */
	@Test
	public void testAfterDays() {
		Calendar calendar = Calendar.getInstance();
		// month 是从0开始的
		calendar.set(2014, 7, 5);
		Date newDate = calendarService.afterDays(calendar, 10);
		LOG.info(newDate);
		LOG.info("TotalMemory ::::"+(Runtime.getRuntime().totalMemory()/(1024*1024)+"M"));
		LOG.info("MaxMemory::::"+(Runtime.getRuntime().maxMemory()/(1024*1024)+"M"));
		LOG.info("FreeMemory::::"+(Runtime.getRuntime().freeMemory()/(1024*1024)+"M"));
		LOG.info("Count CUP::::"+(Runtime.getRuntime().availableProcessors()));
	}
	
//	@Resource private HttpInvokerProxy httpInvokerProxy;
//	@Test
//	public void testHttpInvoke() {
//		SysUserVO sysUserById = httpInvokerProxy.getSysUserById(1);
//		this.printJson(sysUserById);
//	}
	/**
	 * 测试list排序
	 */
	@Test
	@SuppressWarnings({"unchecked","rawtypes"})
	public void addOperationLogAndSortBy() {
		//附件和操作流水 合成一个对象，为了排序
		List<Object> listAttachmentAndOperateLog = new ArrayList<Object>();
		List sysOperationLogs = new ArrayList<Object>();
		sysOperationLogs.add((SysOperationLogVO)this.readValueToVO("{'insertTime':'2014-3-3'}", SysOperationLogVO.class));
		sysOperationLogs.add((SysOperationLogVO)this.readValueToVO("{'insertTime':'2014-3-1'}", SysOperationLogVO.class));
		sysOperationLogs.add((SysOperationLogVO)this.readValueToVO("{'insertTime':'2014-3-8'}", SysOperationLogVO.class));
		List sysAttachements = new ArrayList<Object>();
		sysAttachements.add((SysAttachmentVO)this.readValueToVO("{'insertTime':'2014-3-2'}", SysAttachmentVO.class));
		sysAttachements.add((SysAttachmentVO)this.readValueToVO("{'insertTime':'2014-3-7'}", SysAttachmentVO.class));
		listAttachmentAndOperateLog.addAll(sysOperationLogs);
		listAttachmentAndOperateLog.addAll(sysAttachements);
		if(listAttachmentAndOperateLog.size()>0){
			Collections.sort(listAttachmentAndOperateLog, new BeanComparator(SysOperationLogVO.COLUMN_INSERT_TIME));
		}
		logger.info(listAttachmentAndOperateLog);
	}
	
	/**
	 * 通过商户Id 和类型查找对应的回访信息
	 */
	@Test
	public void getSysDocumentsByMerchantAndType() {
		String inputParames = "[{'merchantId':10, 'documentType':'2'}, {'merchantId':11, 'documentType':'3'}]";
		SysDocumentVO[] sysDocuments =(SysDocumentVO[])this.readValueToVO(inputParames, SysDocumentVO[].class);
		List<SysDocumentVO> lists = sysDocumentService.getSysDocumentsByMerchantAndType(sysDocuments);
		this.printJson(lists);
//		this.genTestReport(inputParames, "sysDocument/getSysDocumentsByMerchantAndType", "通过商户Id 和类型查找对应的回访信息");
	}
}
