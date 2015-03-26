package fes.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import junit.framework.Assert;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import base.BaseControllerTest;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.fes.controller.FesEntityCardController;
import com.yazuo.erp.fes.controller.FesOnlineProcessController;
import com.yazuo.erp.fes.vo.FesTrainPlanVO;
import com.yazuo.erp.syn.controller.SynMerchantController;
import com.yazuo.erp.system.service.SysToDoListService;
import com.yazuo.erp.system.service.SysUserService;
import com.yazuo.erp.system.vo.SysUserVO;

@RunWith(SpringJUnit4ClassRunner.class)
public class FesOnlineProcessControllerTest extends BaseControllerTest {
	@Resource
	private FesOnlineProcessController fesOnlineProcessController;
	@Resource
	private SysToDoListService sysToDoListService;
	@Resource
	private SysUserService sysUserService;

	@Before
	public void setUp() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		response = new MockHttpServletResponse();
	}
	@Resource SynMerchantController synMerchantController;
	/**
	 * 临时文件上传
	 */
	@Test
	public void uploadTempFiles1() throws Exception {
		this.uploadCommonFiles(synMerchantController, "uploadCommonMethod", "flag", "1");

		final String fileName = "test.txt";
		final byte[] content = "Hallo Word".getBytes();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("myfile", fileName, "text/plain", content);
		MockMultipartHttpServletRequest mock = new MockMultipartHttpServletRequest();
		mock.setRequestURI("/uploadCommonMethod.do");
		mock.setParameter("flag", "1");
		mock.setContentType("multipart/form-data");
		mock.setMethod(HttpMethod.POST.name());
		mock.addFile(mockMultipartFile);
		handlerAdapter.handle(mock, response, new HandlerMethod(synMerchantController, "uploadCommonMethod",
				MultipartFile.class, HttpServletRequest.class));
		afterHandle();
	}

	/**
	 * 通用方法
	 */
	public void uploadCommonFiles(Object controllor, String methodName, String ...parameter) throws Exception {
		final String fileName = "test.txt";
		final byte[] content = "Hallo Word".getBytes();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("myfile", fileName, "text/plain", content);
		MockMultipartHttpServletRequest mock = new MockMultipartHttpServletRequest();
		mock.setRequestURI("/"+methodName+".do");
		mock.setContentType("multipart/form-data");
		mock.setMethod(HttpMethod.POST.name());
		mock.addFile(mockMultipartFile);
		handlerAdapter.handle(mock, response, new HandlerMethod(controllor, methodName,
				MultipartFile.class, HttpServletRequest.class));
		afterHandle();
	}
	/**
	 * 保存修改培训计划
	 */
	@Test
	public void saveFesTrainPlan() throws Exception {
		String fesTrainPlan = "{\"processId\":\"31\", \"address\":\"111啊时代发生地方\",\"beginTime\":1405937603000,\"content\":\"111阿桑地方\",\"endTime\":1405937603000,\"trainer\":\"1阿桑地方\"}";
		request.setRequestURI("/fesOnlineProcess.do");
		request.setContentType("application/json");
		request.setMethod(HttpMethod.POST.name());
		request.setParameter("fesTrainPlan", fesTrainPlan);
		request.setContent(fesTrainPlan.getBytes());
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(109);
		request.getSession().setAttribute(Constant.SESSION_USER, sessionUser);
		handlerAdapter.handle(request, response, new HandlerMethod(fesOnlineProcessController, "saveFesTrainPlan",
				FesTrainPlanVO.class, HttpServletRequest.class));
	}
	
	/**
	 * 临时文件上传
	 */
	@Test
	public void uploadTempFiles() throws Exception {
		final String fileName = "test.txt";
		final byte[] content = "Hallo Word".getBytes();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("myfile", fileName, "text/plain", content);
		MockMultipartHttpServletRequest mockMultipartHttpServletRequest = new MockMultipartHttpServletRequest();
		mockMultipartHttpServletRequest.setRequestURI("/uploadTempFiles.do");
		mockMultipartHttpServletRequest.setContentType("multipart/form-data");
		mockMultipartHttpServletRequest.setMethod(HttpMethod.POST.name());
		mockMultipartHttpServletRequest.addFile(mockMultipartFile);
		handlerAdapter.handle(mockMultipartHttpServletRequest, response, new HandlerMethod(fesOnlineProcessController, "uploadTempFiles",
				MultipartFile.class, HttpServletRequest.class));
		afterHandle();
	}
	@Resource FesEntityCardController fesEntityCardController;
	
	
	/**
	 * 附件上传
	 */
	@Test
	public void uploadFiles() throws Exception {
		final String fileName = "test.txt";
		final byte[] content = "Hallo Word".getBytes();
		Integer processId = 155;
		MockMultipartFile mockMultipartFile = new MockMultipartFile("myfile", fileName, "text/plain", content);
		MockMultipartHttpServletRequest mockMultipartHttpServletRequest = new MockMultipartHttpServletRequest();
		mockMultipartHttpServletRequest.setRequestURI("/uploadFiles.do");
		mockMultipartHttpServletRequest.setContentType("multipart/form-data");
		mockMultipartHttpServletRequest.setMethod(HttpMethod.POST.name());
		mockMultipartHttpServletRequest.setParameter("processId", processId.toString());
		mockMultipartHttpServletRequest.addFile(mockMultipartFile);
		SysUserVO sessionUser = new SysUserVO();
		sessionUser.setId(109);
		mockMultipartHttpServletRequest.setAttribute(Constant.SESSION_USER, sessionUser);
		mockMultipartHttpServletRequest.getSession().setAttribute(Constant.SESSION_USER, sessionUser);
		handlerAdapter.handle(mockMultipartHttpServletRequest, response, new HandlerMethod(fesOnlineProcessController, "uploadFiles",
				MultipartFile.class, Integer.class, Integer.class, Map.class, HttpServletRequest.class));
		afterHandle();
	}

	/**
	 * 列表展示
	 */
	@Test
	public void listFesOnlineProcesss() throws Exception {
		// new AnnotationMethodHandlerAdapter().handle(request, new
		// MockHttpServletResponse(), new MyController());
		request.setRequestURI("/fesOnlineProcess.do");
		request.setContentType("application/json");
		request.setMethod(HttpMethod.POST.name());
		HashMap<String, String> pathvars = new HashMap<String, String>();
		pathvars.put("programId", "4");
		request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, pathvars);
		handlerAdapter.handle(request, response, new HandlerMethod(fesOnlineProcessController, "listFesOnlineProcesss",
				int.class));
		afterHandle();
	}

	@Test
	public void justForTest(){
		String tel="";
		sysUserService.getSysUserByTel(tel);
	}
}