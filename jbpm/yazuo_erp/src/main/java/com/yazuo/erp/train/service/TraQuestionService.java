package com.yazuo.erp.train.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.interceptors.Page;

public interface TraQuestionService {
	public boolean saveQuestion(Map<String, Object> paramMap, HttpServletRequest request);

	public Page<Map<String, Object>> getQuestionsInfo(Map<String, Object> paramMap);

	public boolean deleteQuestion(List<Integer> questionIdList, HttpServletRequest request);  

	public boolean updateQuestion(Map<String, Object> paramMap,HttpServletRequest request);

	public Map<String, Object> getQuestionOptionByQId(Integer questionId, HttpServletRequest request);

	/** 上传PPT图片 */
	public Object uploadImage(MultipartFile[] myfiles, HttpServletRequest request) throws IOException;
}
