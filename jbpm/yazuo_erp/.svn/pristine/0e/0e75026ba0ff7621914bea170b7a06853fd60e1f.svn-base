package com.yazuo.erp.train.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.service.SysDictionaryService;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.service.TraQuestionService;

@Controller
@RequestMapping("question")
@SessionAttributes(Constant.SESSION_USER)
public class QuestionController {

	@Resource
	private TraQuestionService traQuestionService;
	@Resource
	private SysDictionaryService sysDictionaryService;

	private static final Log LOG = LogFactory.getLog(QuestionController.class);

	@RequestMapping(value = "getQuestionsInfo", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getQuestionsInfo(@RequestParam Map<String, Object> paramMap,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		Page<Map<String, Object>> questionsInfoList = traQuestionService.getQuestionsInfo(paramMap);
		List<Map<String, Object>> list = questionsInfoList.getResult();
		Map<String, String> dictionaryMap = sysDictionaryService.getSysDictionaryByType("00000019");
		for (Map<String, Object> m : list) {
			String type = String.valueOf(m.get("question_type"));
			m.put("question_type", dictionaryMap.get(type));
		}
		return new JsonResult(true).setMessage("查询成功").putData("totalSize", questionsInfoList.getTotal())
				.putData("rows", questionsInfoList);
	}

	/**
	 * @Description 添加试题
	 * @param paramMap
	 * example：
	 * {	
	 * 	"questionType":"ppt","question":"测试PPT添加","pptName":"测试PPT添加",
	 * 	"fileName":[
	 * 		{
	 * 			"originalFileName":"1.jpg",
	 * 			"data":"{\"id\":167,\"sort\":1,\"ppt_name\":\"测试PPT添加\",\"ppt_id\":51,\"original_file_name\":\"1.jpg\",\"ppt_dtl_name\":\"cc1c832d-39fe-4417-a934-4fc747fd45eb.jpg\"}"
	 * 		},
	 * 		{
	 * 			"originalFileName":"2.jpg",
	 * 			"data":"{\"id\":168,\"sort\":2,\"ppt_name\":\"测试PPT添加\",\"ppt_id\":51,\"original_file_name\":\"2.jpg\",\"ppt_dtl_name\":\"87df3a64-8bb9-4a11-b6fa-ec2d64c1e13e.jpg\"}"
	 * 		}
	 * 	],
	 * 	"coursewareId":158,
	 * 	"questionId":151,
	 * 	"pptId":51
	 * }
	 * @param request
	 * @param user
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "saveQuestion", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult saveQuestion(@RequestBody Map<String, Object> paramMap, HttpServletRequest request,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramMap.put("user", user);
		traQuestionService.saveQuestion(paramMap, request);
		return new JsonResult(true).setMessage("添加成功");
	}

	@RequestMapping(value = "deleteQuestion", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult deleteQuestion(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
		List<String> idString = (List<String>) paramMap.get("idString");
		List<Integer> idList = new ArrayList<Integer>();
		for (Object obj : idString) {
			idList.add(Integer.parseInt(obj.toString()));
		}
		traQuestionService.deleteQuestion(idList, request);
		return new JsonResult(true).setMessage("删除成功");
	}

	@RequestMapping(value = "updateQuestion", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult updateQuestion(@RequestBody Map<String, Object> paramMap, HttpServletRequest request,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramMap.put("user", user);
		traQuestionService.updateQuestion(paramMap, request);
		return new JsonResult(true).setMessage("修改成功");
	}

	@RequestMapping(value = "getQuestionOptionByQId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getQuestionOptionByQId(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
		Map<String, Object> map = traQuestionService.getQuestionOptionByQId((Integer) paramMap.get("questionId"), request);
		return new JsonResult(true).setMessage("查询成功").setData(map);
	}

	@RequestMapping(value = "uploadImage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object uploadImage(@RequestParam(value = "myfiles", required = false) MultipartFile[] myfiles,
			HttpServletRequest request) throws IOException {
		return traQuestionService.uploadImage(myfiles, request);
	}
}
