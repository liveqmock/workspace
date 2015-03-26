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

package system.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;

import base.JUnitDaoBaseWithTrans;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.controller.FesOnlineProcessController;
import com.yazuo.erp.fes.service.FesOnlineProcessService;
import com.yazuo.erp.system.controller.SysDocumentController;
import com.yazuo.erp.system.service.SysDocumentService;
import com.yazuo.erp.system.service.SysOperationLogService;
import com.yazuo.erp.system.vo.SysOperationLogVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.util.DateUtil;

/**
 * @Description 
 * @author erp team
 * @date 
 */

/**
 * @Description
 */
@SuppressWarnings("unused")
public class SysDocumentServiceTest extends JUnitDaoBaseWithTrans {

	private static final Log LOG = LogFactory.getLog(SysDocumentServiceTest.class);
    @Resource  	private FesOnlineProcessService fesOnlineProcessService;
    @Resource  	private SysDocumentService sysDocumentService;
    @Resource  	private SysDocumentController sysDocumentController;
    @Resource  	private FesOnlineProcessController fesOnlineProcessController;
    @Resource  	private SysOperationLogService sysOperationLogService;
	
	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		response = new MockHttpServletResponse();
	}	
	/**
	 * 处理请求回应，输出回应信息
	 */
	@After
	public void afterHandle() throws UnsupportedEncodingException, IOException, JsonParseException, JsonMappingException {
		String result = response.getContentAsString();
		Assert.assertNotNull(result);
		JsonResult jsonResult = mapper.readValue(result.getBytes(), JsonResult.class);
		this.printJson(jsonResult);
		Assert.assertEquals(response.getStatus(), 200);
	}
	@Test
	public void getSysOperationLogs() {
		List<SysOperationLogVO> sysOperationLogs = this.sysOperationLogService
		.getSysOperationLogs(new SysOperationLogVO() {
			{	
				setMerchantId(2280);
				setModuleType("bes");
				setFesLogType("17");//工作管理
				setBeginTime(DateUtil.fromMonth());
				setEndTime(new Date());
			}
		});
		Assert.assertTrue(sysOperationLogs.size()>0);
	}
	@Test
	public void testGetElementFromMap() {
		Map<String,String> map = new HashMap<String, String>();
		logger.info(map.get("dd"));
		logger.info(map.get(""));
		logger.info(map.get(null));
	}
	@Test
	public void uploadRecordAndSaveSysDocumentForStep9() throws NoSuchMethodException, Exception {
		final String fileName = "test.txt";
		final byte[] content = "Hallo Word".getBytes();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("myfile", fileName, "text/plain", content);
		MockMultipartHttpServletRequest mockMultipartHttpServletRequest = new MockMultipartHttpServletRequest();
		mockMultipartHttpServletRequest.setRequestURI("/uploadRecordAndSaveSysDocumentForStep9.do");
		mockMultipartHttpServletRequest.setContentType("multipart/form-data");
		mockMultipartHttpServletRequest.setMethod(HttpMethod.POST.name());
		
		String inputSysDocument ="{\"merchantId\":638,\"documentType\":1}";
		mockMultipartHttpServletRequest.setParameter("inputSysDocument", inputSysDocument);
		mockMultipartHttpServletRequest.addFile(mockMultipartFile);
		
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(109);
		mockMultipartHttpServletRequest.setAttribute(Constant.SESSION_USER, sessionUser);
		mockMultipartHttpServletRequest.getSession().setAttribute(Constant.SESSION_USER, sessionUser);
		
		handlerAdapter.handle(mockMultipartHttpServletRequest, response,
				new HandlerMethod(sysDocumentController, "uploadRecordAndSaveSysDocumentForStep9",
				MultipartFile.class,String.class, HttpServletRequest.class));
		String contentAsString = response.getContentAsString();
		logger.info(contentAsString);
	}
	@Test
	public void uploadRecordAndSaveSysDocumentAfterOnline() throws NoSuchMethodException, Exception {
		final String fileName = "test.txt";
		final byte[] content = "Hallo Word".getBytes();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("myfile", fileName, "text/plain", content);
		MockMultipartHttpServletRequest mockMultipartHttpServletRequest = new MockMultipartHttpServletRequest();
		mockMultipartHttpServletRequest.setRequestURI("/uploadRecordAndSaveSysDocumentAfterOnline.do");
		mockMultipartHttpServletRequest.setContentType("multipart/form-data");
		mockMultipartHttpServletRequest.setMethod(HttpMethod.POST.name());
		
		String inputSysDocuments ="[{\"merchantId\":11110,\"documentType\":2},{\"merchantId\":11110,\"documentType\":3}]";
		mockMultipartHttpServletRequest.setParameter("inputSysDocuments", inputSysDocuments);
		mockMultipartHttpServletRequest.addFile(mockMultipartFile);
		
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(109);
		mockMultipartHttpServletRequest.setAttribute(Constant.SESSION_USER, sessionUser);
		mockMultipartHttpServletRequest.getSession().setAttribute(Constant.SESSION_USER, sessionUser);
		
		handlerAdapter.handle(mockMultipartHttpServletRequest, response,
				new HandlerMethod(sysDocumentController, "uploadRecordAndSaveSysDocumentAfterOnline",
						MultipartFile.class,String.class, HttpServletRequest.class));
	}
	
	@Test 
	public void getStatusForSysDocument() {
		String documentByMerchantAndType = this.sysDocumentService.getStatusForSysDocument(638, "1");
		logger.info(documentByMerchantAndType);
		logger.info(documentByMerchantAndType);
		logger.info(documentByMerchantAndType);
		logger.info(documentByMerchantAndType);
	}

}
