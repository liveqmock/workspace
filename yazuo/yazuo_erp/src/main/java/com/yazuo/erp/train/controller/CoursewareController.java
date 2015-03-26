package com.yazuo.erp.train.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.util.BufferRecycler;
import org.springframework.beans.factory.annotation.Value;
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
import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.base.HttpClientUtil;
import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.service.TraCoursewareService;

@Controller
@RequestMapping("courseware")
@SessionAttributes(Constant.SESSION_USER)
public class CoursewareController {

    Log log = LogFactory.getLog(this.getClass());
	@Resource
	private TraCoursewareService traCoursewareService;
	/**
	 * 
	 * @Description 添加课件
	 * @param paramMap
	 * @return
	 * @return JsonResult
	 * @throws
	 */
	@RequestMapping(value = "saveCourseware", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult saveCourseware(@RequestBody Map<String, Object> paramMap, HttpServletRequest request,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) throws IOException {
		paramMap.put("user", user);
		traCoursewareService.saveCourseware(paramMap, request);
		return new JsonResult(true).setMessage("添加成功");
	}

	/**
	 * 
	 * @Description 修改课件
	 * @param traCoursewareVO
	 * @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "updateCourseware", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult updateCourseware(@RequestBody Map<String, Object> paramerMap, HttpServletRequest request,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user) {
		paramerMap.put("user", user);
		traCoursewareService.updateCourseware(paramerMap, request);
		return new JsonResult(true).setMessage("修改成功");
	}

	/**
	 * 
	 * @Description 删除课件
	 * @param paramerMap
	 * @return
	 * @return JsonResult
	 * @throws
	 */

	@RequestMapping(value = "deleteCourseware", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult deleteCourseware(@RequestBody Map<String, Object> paramerMap,
			@ModelAttribute(Constant.SESSION_USER) SysUserVO user, HttpServletRequest request) {
		List<String> idString = (List<String>) paramerMap.get("idString");
		List<Integer> listIds = new ArrayList<Integer>();
		for (Object obj : idString) {
			listIds.add(Integer.parseInt(obj.toString()));
		}
		traCoursewareService.deleteCourseware(listIds, user, request);
		return new JsonResult(true).setMessage("删除成功");
	}

	/**
	 * 
	 * @Description 查询课件信息
	 * @param coursewareName
	 * @param courseName
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "getCoursewareInfo", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getCoursewareByName(@RequestParam(value = "coursewareName", required = false) String coursewareName,
			@RequestParam(value = "courseName", required = false) String courseName,
			@RequestParam(value = "pageNumber", required = false) int pageNumber,
			@RequestParam(value = "pageSize", required = false) int pageSize) {
		Page<Map<String, Object>> coursewareList = (Page<Map<String, Object>>) traCoursewareService.getCourseCourseware(
				coursewareName, courseName, pageNumber, pageSize);
		return new JsonResult(true).setMessage("查询成功").putData("totalSize", coursewareList.getTotal())
				.putData("rows", coursewareList);
	}

	@RequestMapping(value = "getCourseware", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getCourseware(@RequestParam(value = "coursewareName", required = false) String coursewareName) {
		List<Map<String, Object>> coursewareList = traCoursewareService.getCourseware(coursewareName);
		return new JsonResult(true).setMessage("查询成功").putData("totalSize", coursewareList.size())
				.putData("rows", coursewareList);
	}

	@RequestMapping(value = "getCourseByCoursewareId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getCourseByCoursewareId(@RequestParam(value = "coursewareId") Integer coursewareId) {
		List<Map<String, Object>> coursewareList = traCoursewareService.getCourseByCoursewareId(coursewareId);
		return new JsonResult(true).setMessage("查询成功").setData(coursewareList);

	}

	@RequestMapping(value = "getCoursewareInfoByCoursewareId", method = { RequestMethod.POST, RequestMethod.GET }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public JsonResult getCoursewareInfoByCoursewareId(@RequestBody Map<String, Object> map) {
		Map<String, Object> coursewareInfoMap = traCoursewareService.getCoursewareInfoByCoursewareId(map);
		return new JsonResult(true).setMessage("查询成功").setData(coursewareInfoMap);
	}

	@RequestMapping(value = "uploadVideo", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object uploadVideo(@RequestParam(value = "myfiles", required = false) MultipartFile[] myfiles,
			HttpServletRequest request) throws IOException {
		 return traCoursewareService.uploadVideo(myfiles, request);
	}
}
