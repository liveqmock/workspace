package com.yazuo.erp.train.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.erp.base.FileUploaderUtil;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.dao.TraCourseCoursewareDao;
import com.yazuo.erp.train.dao.TraFinalExamQuestionDao;
import com.yazuo.erp.train.dao.TraPptDao;
import com.yazuo.erp.train.dao.TraPptDtlDao;
import com.yazuo.erp.train.dao.TraQuestionDao;
import com.yazuo.erp.train.dao.TraQuestionOptionDao;
import com.yazuo.erp.train.dao.TraRequiredQuestionDao;
import com.yazuo.erp.train.exception.TrainBizException;
import com.yazuo.erp.train.service.TraQuestionService;
import com.yazuo.erp.train.vo.TraPptDtlVO;
import com.yazuo.erp.train.vo.TraPptVO;
import com.yazuo.erp.train.vo.TraQuestionOptionVO;
import com.yazuo.erp.train.vo.TraQuestionVO;

@Service
public class TraQuestionServiceImpl implements TraQuestionService {

	@Resource
	private TraQuestionDao traQuestionDao;

	@Resource
	private TraQuestionOptionDao traQuestionOptionDao;

	@Resource
	private TraPptDtlDao traPptDtlDao;

	@Resource
	private TraPptDao traPptDao;

	@Resource
	private TraRequiredQuestionDao traRequiredQuestionDao;

	@Resource
	private TraCourseCoursewareDao traCourseCoursewareDao;

	@Resource
	private TraFinalExamQuestionDao traFinalExamQuestionDao;

	/** 临时存放PPT上传图片 */
	@Value("${pptTempPhotoPath}")
	private String tempImgLocationPath;

	@Value("${pptPhotoPath}")
	private String realImgLocationPath;

	private static final Log LOG = LogFactory.getLog(TraQuestionServiceImpl.class);

	/** 限制上传文件大小 */
	final static long LIMIT_FILE_SIZE = 2 * 1024 * 1024;

	@Override
	public boolean saveQuestion(Map<String, Object> paramMap, HttpServletRequest request) {
		String questionType = (String) paramMap.get("questionType");
		questionType = getQuestionType(questionType);
		SysUserVO user = (SysUserVO) paramMap.get("user");
		Integer userId = user.getId();
		TraQuestionVO entity = getQuestionVO(paramMap, questionType, userId);
		if (StringUtils.equals("0", questionType) || StringUtils.equals("1", questionType)
				|| StringUtils.equals("2", questionType)) {// 0-单选,1-多选,2-判断
			traQuestionDao.saveTraQuestion(entity);
			Integer id = entity.getId();
			List<Map<String, Object>> answers = (List<Map<java.lang.String, Object>>) paramMap.get("answers");
			List<TraQuestionOptionVO> list = new ArrayList<TraQuestionOptionVO>();
			TraQuestionOptionVO traQuestionOptionVO;
			for (Map<String, Object> map : answers) {
				traQuestionOptionVO = getQuestOptionVO(map, id);
				traQuestionOptionVO.setQuestionId(id);
				traQuestionOptionVO.setInsertBy(userId);
				traQuestionOptionVO.setUpdateBy(userId);
				list.add(traQuestionOptionVO);
			}
			traQuestionOptionDao.batchSaveQuestionOption(list);
		} else if (StringUtils.equals("3", questionType)) {// 3-PPT
			// 1、保存PPT信息
			TraPptVO traPptVO = new TraPptVO();
			traPptVO.setCoursewareId((Integer) paramMap.get("coursewareId"));
			traPptVO.setPptName((String) paramMap.get("pptName"));
			traPptVO.setInsertBy(userId);
			traPptVO.setUpdateBy(userId);
			traPptDao.saveTraPpt(traPptVO);

			// 2、修改PPT信息，配置PPT路径为PPT ID
			Integer pptId = traPptVO.getId();
			TraPptVO traPptVoById = traPptDao.getTraPptById(pptId);
			traPptVoById.setPptPath(String.valueOf(pptId));
			traPptDao.updateTraPpt(traPptVoById);

			// 3、得到上送的PPT图片，批量添加PPT详情
			List<Map<String, Object>> fileNames = (List<Map<String, Object>>) paramMap.get("fileName");
			String fileName;
			for (Map<String, Object> map : fileNames) {
				fileName = (String) map.get("fileName");
				moveFile(fileName, String.valueOf(pptId), request);
			}
			List<TraPptDtlVO> list = new ArrayList<TraPptDtlVO>();
			TraPptDtlVO traPptDtlVO = null;
			String originalFileName;

			if (null != fileNames && fileNames.size() > 0) {
				Map<String, Object> map;
				for (int i = 0; i < fileNames.size(); i++) {
					map = fileNames.get(i);
					fileName = (String) map.get("fileName");
					originalFileName = (String) map.get("originalFileName");
					traPptDtlVO = new TraPptDtlVO();
					traPptDtlVO.setPptId(pptId);
					traPptDtlVO.setOriginalFileName(originalFileName);
					traPptDtlVO.setSort(i + 1);
					traPptDtlVO.setPptDtlName(fileName);
					traPptDtlVO.setInsertBy(userId);
					list.add(traPptDtlVO);
				}
			}

			traPptDtlDao.batchSaveTraPptDtl(list);
			entity.setPptId(pptId);
			entity.setIsSystemDetermine("0");// PPT不是系统自动判分
			traQuestionDao.saveTraQuestion(entity);
		} else if (StringUtils.equals("4", questionType)) {// 4-产品实操
			entity.setUrl((String) paramMap.get("url"));
			traQuestionDao.saveTraQuestion(entity);
		} else {// 其他题型暂不支持
			throw new TrainBizException("题型异常");
		}
		return true;
	}

	public boolean moveFile(String fileName, String pptId, HttpServletRequest request) {
		String orignPath = request.getSession().getServletContext().getRealPath(tempImgLocationPath) + "/" + fileName;
		String destPath = request.getSession().getServletContext().getRealPath(realImgLocationPath) + "/" + pptId;
		File orignFile = new File(orignPath); // 源文件
		File destFile = new File(destPath); // 目标文件夹
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		return orignFile.renameTo(new File(destFile, orignFile.getName()));
	}

	public boolean deleteFile(String fileName, String pptId, HttpServletRequest request) {
		String destPath = request.getSession().getServletContext().getRealPath(realImgLocationPath) + "/" + pptId;
		File destFile = new File(destPath + "/" + fileName); // 目标文件夹
		return destFile.delete();
	}

	private String getQuestionType(String questionType) {
		if (StringUtils.equals("single", questionType)) {
			questionType = "0";
		} else if (StringUtils.equals("multi", questionType)) {
			questionType = "1";
		} else if (StringUtils.equals("bool", questionType)) {
			questionType = "2";
		} else if (StringUtils.equals("ppt", questionType)) {
			questionType = "3";
		} else if (StringUtils.equals("practice", questionType)) {
			questionType = "4";
		} else if (StringUtils.equals("qa", questionType)) {
			questionType = "5";
		} else {
			throw new TrainBizException("题型异常");
		}
		return questionType;
	}

	private TraQuestionOptionVO getQuestOptionVO(Map<String, Object> paramMap, Integer id) {
		TraQuestionOptionVO traQuestionOptionVO = new TraQuestionOptionVO();
		traQuestionOptionVO.setOptionContent((String) paramMap.get("optionContent"));
		String isRight;
		Boolean flag = (Boolean) paramMap.get("isRight");
		if (flag) {
			isRight = "1";
		} else {
			isRight = "0";
		}
		traQuestionOptionVO.setIsRight(isRight);
		return traQuestionOptionVO;
	}

	private TraQuestionVO getQuestionVO(Map<String, Object> paramMap, String questionType, Integer userId) {
		TraQuestionVO entity = new TraQuestionVO();
		entity.setCoursewareId((Integer) paramMap.get("coursewareId"));
		entity.setQuestionType(questionType);
		entity.setQuestion((String) paramMap.get("question"));
		entity.setIsSystemDetermine("1");
		entity.setIsEnable("1");
		entity.setRemark((String) paramMap.get("remark"));
		entity.setInsertBy(userId);
		entity.setUpdateBy(userId);
		return entity;
	}

	@Override
	public Page<Map<String, Object>> getQuestionsInfo(Map<String, Object> paramMap) {
		String questionType = (String) paramMap.get("questionType");
		String question = (String) paramMap.get("question");
		String coursewareName = (String) paramMap.get("coursewareName");
		String pageNumber = (String) paramMap.get("pageNumber");
		String pageSize = (String) paramMap.get("pageSize");
		PageHelper.startPage(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), true);
		Page<Map<String, Object>> questionList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("questionType", questionType);
		map.put("question", question);
		if (StringUtils.isEmpty(coursewareName)) {
			questionList = (Page<Map<String, Object>>) traQuestionDao.getTraQuestionByOption(map);
		} else {
			map.put("coursewareName", coursewareName);
			questionList = (Page<Map<String, Object>>) traQuestionDao.getTraQuestionByOptionAndCoursewareName(map);
		}
		return questionList;
	}

	@Override
	public boolean deleteQuestion(List<Integer> questionIdList, HttpServletRequest request) {
		boolean flag = false;
		if (questionIdList != null && questionIdList.size() > 0) {

			// 1、删除PPT图片
			// 根据试题ID列表查询PPT图片集
			List<Map<String, Object>> dtlMap = this.traPptDtlDao.queryPptDtlByQuestionIdList(questionIdList);
			for (Map<String, Object> map : dtlMap) {
				String fileName = (String) map.get("ppt_dtl_name");
				String pptId = String.valueOf(map.get("ppt_id"));
				// 逐个删除PPT图片
				deletePptFile(fileName, pptId, request);
			}

			// 2、根据试题ID列表批量删除 tra_ppt_dtl PPT详情
			this.traPptDtlDao.batchDeleteTraPptDtlByQuestionIdList(questionIdList);

			// 3、根据试题ID列表批量删除 tra_ppt PPT
			this.traPptDao.batchDeleteTraPptByQuestionIdList(questionIdList);

			// 4、根据试题ID列表批量删除 tra_required_question 考卷必考题
			this.traRequiredQuestionDao.batchDeleteTraRequiredQuestionByQuestionIdList(questionIdList);

			// 5、根据试题ID列表批量删除 tra_final_exam_question 期末考卷必考题
			this.traFinalExamQuestionDao.batchDeleteTraFinalExamQuestionByQuestionId(questionIdList);

			// 6、根据试题ID列表批量删除 tra_question 试题（修改is_enable为'0'）
			this.traQuestionDao.batchUpdateTraQuestionByIdList(questionIdList);

			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	public boolean deletePptFile(String fileName, String pptId, HttpServletRequest request) {
		String destPath = request.getSession().getServletContext().getRealPath(realImgLocationPath) + "/" + pptId;
		File destFile = new File(destPath + "/" + fileName); // 目标文件夹
		return destFile.delete();
	}

	@Override
	public boolean updateQuestion(Map<String, Object> paramMap, HttpServletRequest request) {
		Integer questionId = (Integer) paramMap.get("questionId");
		TraQuestionVO entity = traQuestionDao.getTraQuestionById(questionId);
		String type = entity.getQuestionType();// 原试题类型
		Integer coursewareId = entity.getCoursewareId();// 试题对应的原课件ID
		String questionType = getQuestionType((String) paramMap.get("questionType"));// 上送试题类型
		if (!StringUtils.equals(type, questionType)) {
			throw new TrainBizException("试题类型不允许修改");
		}
		SysUserVO user = (SysUserVO) paramMap.get("user");
		Integer userId = user.getId();
		TraQuestionVO questionVO = getQuestionVOUpdate(questionType, paramMap, userId);
		if (StringUtils.equals("0", type) || StringUtils.equals("1", type) || StringUtils.equals("2", type)) {// 0-单选，1-多选，2-判断
			// 1、删除试题对应的试题选项
			traQuestionOptionDao.deleteTraQuestionOptionByQuestionId(questionId);

			// 2、修改试题,并添加试题选项
			traQuestionDao.updateTraQuestion(questionVO);
			List<Map<String, Object>> answers = (List<Map<java.lang.String, Object>>) paramMap.get("answers");
			List<TraQuestionOptionVO> list = new ArrayList<TraQuestionOptionVO>();
			TraQuestionOptionVO traQuestionOptionVO;
			for (Map<String, Object> map : answers) {
				traQuestionOptionVO = getQuestOptionVO(map, questionId);
				traQuestionOptionVO.setQuestionId(questionId);
				traQuestionOptionVO.setInsertBy(userId);
				traQuestionOptionVO.setUpdateBy(userId);
				list.add(traQuestionOptionVO);
			}
			traQuestionOptionDao.batchSaveQuestionOption(list);

		} else if (StringUtils.equals("3", type)) {// 3-PPT
			Integer pptId = (Integer) paramMap.get("pptId");
			
			// 1、修改试题
			traQuestionDao.updateTraQuestion(questionVO);

			// 2、分析图片列表，查询出新加的图片集合删除的图片集
			List<Map<String, Object>> files = (List<Map<String, Object>>) paramMap.get("fileName");// 图片列表
			if (files == null) {
				throw new TrainBizException("请选择需要上传的PPT图片");
			}

			List<Map<String, Object>> addFiles = new ArrayList<Map<String, Object>>();// 新加的图片集
			List<Map<String, Object>> deleteFiles = new ArrayList<Map<String, Object>>();// 删除的图片集

			// 根据PPT的ID查询详情列表
			List<Map<String, Object>> oldList = this.traPptDtlDao.getPptDtlByPptId(pptId);
			if (oldList == null) {
				throw new TrainBizException("未发现PPT的原图片信息");
			}
			for (int i = 0; i < files.size(); i++) {
				Map<String, Object> file = (Map<String, Object>) files.get(i);
				String fileData = (String) file.get("data");
				if (StringUtils.isEmpty(fileData)) {// 新文件
					addFiles.add(file);
				} else {// 原文件
					Map<String, Object> data = (Map<String, Object>) JSONObject.fromObject(file.get("data"));
					Integer id = (Integer) data.get("id");
					for (int j = 0; j < oldList.size(); j++) {// 与原图片比对，查找删除的图片列表
						Map<String, Object> old = (Map<String, Object>) oldList.get(j);
						Integer oldId = (Integer) old.get("id");
						if (oldId != null && oldId.equals(id)) {
							oldList.remove(j);// 找到原文件，则集合中删除该记录，减少一条记录
							break;
						}
					}
				}
			}

			// 在原数据库记录中遍历新的图片，发现一条减少一条，剩下的记录为需要删除的记录
			if (oldList != null && oldList.size() > 0) {
				deleteFiles = oldList;
			}
			// 2、修改PPT
			TraPptVO traPptVO = new TraPptVO();
			traPptVO.setId(pptId);
			traPptVO.setCoursewareId((Integer) paramMap.get("coursewareId"));
			traPptVO.setPptName((String) paramMap.get("pptName"));
			traPptVO.setUpdateBy(userId);
			traPptDao.updateTraPptCoursewareId(traPptVO);

			// 3、删除实际路径下文件
			String fileName;
			String pId = String.valueOf(pptId);
			for (Map<String, Object> map : deleteFiles) {
				fileName = (String) map.get("ppt_dtl_name");
				deleteFile(fileName, pId, request);
			}
			// 4、上传新增文件到实际路径
			for (Map<String, Object> map : addFiles) {
				fileName = (String) map.get("fileName");
				moveFile(fileName, pId, request);
			}

			// 5、批量删除PPT详情表中数据
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> deleteMap;
			for (Map<String, Object> map : deleteFiles) {
				fileName = (String) map.get("ppt_dtl_name");
				deleteMap = new HashMap<String, Object>();
				deleteMap.put("pptId", pptId);
				deleteMap.put("pptDtlName", fileName);
				list.add(deleteMap);
			}
			if (null != list && list.size() > 0) {
				traPptDtlDao.batchDeleteTraPptDtlByPptIdAndPptDtlName(list);
			}

			// 6、批量增加PPT详情
			List<TraPptDtlVO> traPptDtlVOList = new ArrayList<TraPptDtlVO>();
			TraPptDtlVO traPptDtlVO;
			String originalFileName;

			if (null != addFiles && addFiles.size() > 0) {
				Map<String, Object> map;
				for (int i = 0; i < addFiles.size(); i++) {
					map = addFiles.get(i);
					fileName = (String) map.get("fileName");
					originalFileName = (String) map.get("originalFileName");
					traPptDtlVO = new TraPptDtlVO();
					traPptDtlVO.setInsertBy(userId);
					traPptDtlVO.setPptDtlName(fileName);
					traPptDtlVO.setOriginalFileName(originalFileName);
					traPptDtlVO.setPptId(pptId);
					traPptDtlVO.setSort(i + 1);
					traPptDtlVOList.add(traPptDtlVO);
				}
			}

			if (null != traPptDtlVOList && traPptDtlVOList.size() > 0) {
				traPptDtlDao.batchSaveTraPptDtl(traPptDtlVOList);
			}
		} else if (StringUtils.equals("4", type)) {// 4-产品实操
			traQuestionDao.updateTraQuestion(questionVO);
		} else {// 暂不支持题型
			throw new TrainBizException("题型异常");
		}

		// 判断试题对应的原课件ID与新上送的课件ID是否相同，若相同，则不做修改;若不相同，则进行下述操作
		Integer coursewareIdNew = (Integer) paramMap.get("coursewareId");
		if (null != coursewareIdNew && coursewareIdNew.intValue() != coursewareId.intValue()) {
			// 1、删除课件考卷必考题中该试题
			traRequiredQuestionDao.deleteTraRequiredQuestionByRuleIdAndQuestionId(questionId, coursewareId);

			// 2、查询原课件对应的全部课程，若新课件属于该课程，则不删除课程对应的期末必考题中该试题，否则则删除
			List<Integer> courseIdList = traCourseCoursewareDao.getCourseIdByCoursewareId(coursewareId);
			boolean flag = false;// 新课件是否包含于原课件对应的课程
			List<Integer> courseList = new ArrayList<Integer>();
			for (Integer courseId : courseIdList) {
				List<Integer> coursewareIdList = traCourseCoursewareDao.getCoursewareIdByCourseId(courseId);
				flag = coursewareIdList.contains(coursewareIdNew);
				if (!flag) {
					// 过滤出该课程对应期末考试必考题中该试题
					courseList.add(courseId);
				}
			}

			// 3、删除课程对应期末考试必考题中该试题
			if (null != courseList && courseList.size() > 0) {
				traFinalExamQuestionDao.deleteTraFinalExamQuestionByCourseIdAndQuestionId(courseList, questionId);
			}
		}
		return true;
	}

	/**
	 * @param questionType
	 * @param paramMap
	 * @return void
	 * @throws
	 */
	private TraQuestionVO getQuestionVOUpdate(String questionType, Map<String, Object> paramMap, Integer userId) {
		TraQuestionVO entity = new TraQuestionVO();
		entity.setCoursewareId((Integer) paramMap.get("coursewareId"));
		entity.setQuestionType(questionType);
		entity.setQuestion((String) paramMap.get("question"));
		Integer pptId = (Integer) paramMap.get("pptId");
		entity.setPptId(pptId);
		String url = (String) paramMap.get("url");
		entity.setUrl(url);
		entity.setUpdateBy(userId);
		entity.setId((Integer) paramMap.get("questionId"));
		return entity;
	}

	@Override
	public Map<String, Object> getQuestionOptionByQId(Integer questionId, HttpServletRequest request) {
		Map<String, Object> questionMap = traQuestionDao.getQuestionByQId(questionId);
		String questionType = (String) questionMap.get("question_type");
		if (StringUtils.equals(questionType, "0") || StringUtils.equals(questionType, "1")
				|| StringUtils.equals(questionType, "2")) {// 0-单选，1-多选，2-判断
			List<Map<String, Object>> list = traQuestionDao.getQuestionOptionsById(questionId);
			for (Map<String, Object> map : list) {
				if (map.containsKey("is_right")) {
					if (StringUtils.equals("1", (String) map.get("is_right"))) {
						map.put("is_right", true);
					} else {
						map.put("is_right", false);
					}
				}
			}
			questionMap.put("questionOptions", list);
		} else if (StringUtils.equals(questionType, "3")) {// 3-PPT
			Integer pptId = (Integer) questionMap.get("ppt_id");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pptId", pptId);
			List<Map<String, Object>> pptDtlByPptIdList = traQuestionDao.getPptDtlByPptId(map);
			String pptName = (String) pptDtlByPptIdList.get(0).get("ppt_name");
			questionMap.put("relativePath", realImgLocationPath + "/" + pptId);
			questionMap.put("pptName", pptName);
			questionMap.put("pptList", pptDtlByPptIdList);
		}
		// 4-产品实操，没有选项内容
		return questionMap;
	}

	/**
	 * @Title uploadImage
	 * @Description
	 * @param myfiles
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Override
	public Object uploadImage(MultipartFile[] myfiles, HttpServletRequest request) throws IOException {
		return FileUploaderUtil.uploadFile(myfiles, "pptTempPhotoPath", null, 0, request);

	}
}
