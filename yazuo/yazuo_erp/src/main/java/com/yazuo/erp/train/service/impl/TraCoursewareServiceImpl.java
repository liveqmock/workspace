package com.yazuo.erp.train.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.smvp.sdk.SmvpClient;
import com.yazuo.erp.base.Constant;
import com.yazuo.erp.base.HttpClientUtil;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.dao.TraAttachmentDao;
import com.yazuo.erp.train.dao.TraCourseCoursewareDao;
import com.yazuo.erp.train.dao.TraCourseDao;
import com.yazuo.erp.train.dao.TraCoursewareAttachmentDao;
import com.yazuo.erp.train.dao.TraCoursewareDao;
import com.yazuo.erp.train.dao.TraExamPaperDao;
import com.yazuo.erp.train.dao.TraFinalExamQuestionDao;
import com.yazuo.erp.train.dao.TraLearningProgressDao;
import com.yazuo.erp.train.dao.TraPptDao;
import com.yazuo.erp.train.dao.TraPptDtlDao;
import com.yazuo.erp.train.dao.TraQuestionDao;
import com.yazuo.erp.train.dao.TraRequiredQuestionDao;
import com.yazuo.erp.train.dao.TraRuleDao;
import com.yazuo.erp.train.dao.TraStudentRecordDao;
import com.yazuo.erp.train.exception.TrainBizException;
import com.yazuo.erp.train.service.TraCoursewareService;
import com.yazuo.erp.train.util.TimeUtils;
import com.yazuo.erp.train.vo.CoursewareProgressVO;
import com.yazuo.erp.train.vo.TraAttachmentVO;
import com.yazuo.erp.train.vo.TraCourseCoursewareVO;
import com.yazuo.erp.train.vo.TraCourseVO;
import com.yazuo.erp.train.vo.TraCoursewareAttachmentVO;
import com.yazuo.erp.train.vo.TraCoursewareVO;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import com.yazuo.erp.train.vo.TraLearningProgressVO;
import com.yazuo.erp.train.vo.TraRuleVO;
import com.yazuo.erp.train.vo.TraStudentRecordVO;

/**
 * 
 * @Description 备注：关于石山视频的操作 1.视频上传由前端控制 2.视频保存的时候需要接收前端传过来时视频id
 *              3.视频修改的时候需要判断视频id是否与原数据库中保存的id相同，不相同需要删除原有的视频id
 *              4.课件删除的时候需要同时删除视频附件，视频id是从数据库中查询附件表获得的
 *              5.用户登录的时候需要一个smvp信息，包括视频分类，需要从Constant中属性获得，前端使用，后端只取他的token属性
 *              6.视频查看的时候需要调用石山API通过视频id获取一个属性smvpAttachData对象给前端解析，得到查看地址
 * @date 2014-7-25 下午1:39:17
 */
@Service
public class TraCoursewareServiceImpl implements TraCoursewareService {
	@Resource
	private TraCoursewareDao traCoursewareDao;

	@Resource
	private TraCourseCoursewareDao traCourseCoursewareDao;

	@Resource
	private TraExamPaperDao traExamPaperDao;

	@Resource
	private TraAttachmentDao traAttachmentDao;

	@Resource
	private TraStudentRecordDao traStudentRecordDao;

	@Resource
	private TraLearningProgressDao traLearningProgressDao;

	@Resource
	private TraCourseDao traCourseDao;

	@Resource
	private TraCoursewareAttachmentDao traCoursewareAttachmentDao;

	@Resource
	private TraQuestionDao traQuestionDao;

	@Resource
	private TraRuleDao traRuleDao;

	@Resource
	private TraRequiredQuestionDao traRequiredQuestionDao;

	@Resource
	private TraFinalExamQuestionDao traFinalExamQuestionDao;

	@Resource
	private TraPptDao traPptDao;

	@Resource
	private TraPptDtlDao traPptDtlDao;

	private static final Log LOG = LogFactory.getLog(TraCoursewareServiceImpl.class);

	@Value("${imgLocationPath}")
	private String imgLocationPath;

	@Value("${videoPath}")
	private String videoPath;

	@Value("${viewVideoPath}")
	private String viewVideoPath;

	@Value("${pptPhotoPath}")
	private String realImgLocationPath;

	@Value("${videoServerAppPath}")
	private String videoServerAppPath;

	@Deprecated
	// 原有的实现方式
	public int saveCourseware1(Map<String, Object> paramMap, HttpServletRequest request) throws IOException {

		// 1、从临时文件移动视频文件到实际位置
		List<Map<String, Object>> attachmentList = (List<Map<String, Object>>) paramMap.get("attachmentId");
		Map<String, Object> attachmentMap = new HashMap<String, Object>();
		if (null != attachmentList && attachmentList.size() > 0) {
			attachmentMap = attachmentList.get(0);
		}
		String fileName = (String) attachmentMap.get("fileName");
		moveFile(fileName, request);

		SysUserVO user = (SysUserVO) paramMap.get("user");
		Integer userId = user.getId();// 当前用户ID
		// 2、添加附件表
		String originalFileName = (String) attachmentMap.get("originalFileName");
		Integer hours = Integer.valueOf((String) paramMap.get("hours"));
		Integer attachmentId = saveAttachment(fileName, originalFileName, userId, hours);

		// 3、添加课件信息
		TraCoursewareVO coursewareVO = getTraCoursewareVO(paramMap, attachmentId, userId, hours);
		traCoursewareDao.saveTraCourseware(coursewareVO);
		Integer coursewareId = coursewareVO.getId();

		// 4、添加课件与附件关系
		saveCoursewareAttachment(attachmentId, coursewareId, userId);
		return 1;
	}

	/**
	 * 石山视频实现方式
	 */
	public int saveCourseware(Map<String, Object> paramMap, HttpServletRequest request) throws IOException {
		// 1、从临时文件移动视频文件到实际位置
		String fileName = (String) paramMap.get("videoId");
		String originalFileName = (String) paramMap.get("originalFileName");
		SysUserVO user = (SysUserVO) paramMap.get("user");
		Integer userId = user.getId();// 当前用户ID
		// 2、添加附件表
		Integer hours = Integer.valueOf((String) paramMap.get("hours"));
		Integer attachmentId = saveAttachment(fileName, originalFileName, userId, hours);

		// 3、添加课件信息
		TraCoursewareVO coursewareVO = getTraCoursewareVO(paramMap, attachmentId, userId, hours);
		traCoursewareDao.saveTraCourseware(coursewareVO);
		Integer coursewareId = coursewareVO.getId();

		// 4、添加课件与附件关系
		saveCoursewareAttachment(attachmentId, coursewareId, userId);
		return 1;
	}

	/**
	 * @param attachmentId
	 * @param coursewareId
	 * @return void
	 * @throws
	 */
	private void saveCoursewareAttachment(Integer attachmentId, Integer coursewareId, Integer userId) {
		TraCoursewareAttachmentVO traCoursewareAttachmentVO = new TraCoursewareAttachmentVO();
		traCoursewareAttachmentVO.setCoursewareId(coursewareId);
		traCoursewareAttachmentVO.setAttachmentId(attachmentId);
		traCoursewareAttachmentVO.setInsertBy(userId);
		traCoursewareAttachmentDao.saveTraCoursewareAttachment(traCoursewareAttachmentVO);
	}

	public boolean moveFile(String fileName, HttpServletRequest request) {
		String orignPath = request.getSession().getServletContext().getRealPath(imgLocationPath) + "/" + fileName;
		String destPath = request.getSession().getServletContext().getRealPath(videoPath);
		File orignFile = new File(orignPath); // 源文件
		File destFile = new File(destPath); // 目标文件夹
		if (!destFile.exists()) {
			destFile.mkdirs();
		}
		return orignFile.renameTo(new File(destFile, orignFile.getName()));
	}

	private Integer saveAttachment(String fileName, String originalFileName, Integer userId, Integer hours) {
		TraAttachmentVO traAttachmentVO = new TraAttachmentVO();
		traAttachmentVO.setAttachmentName(fileName);
		traAttachmentVO.setAttachmentType("1");
		traAttachmentVO.setIsEnable("1");
		traAttachmentVO.setIsDownloadable("1");
		traAttachmentVO.setHours(new BigDecimal(hours));
		traAttachmentVO.setInsertBy(userId);
		traAttachmentVO.setUpdateBy(userId);
		traAttachmentVO.setRemark(originalFileName);
		traAttachmentDao.saveTraAttachment(traAttachmentVO);
		return traAttachmentVO.getId();
	}

	private TraCoursewareVO getTraCoursewareVO(Map<String, Object> paramMap, Integer attachmentId, Integer userId, Integer hours) {
		TraCoursewareVO coursewareVO = new TraCoursewareVO();
		coursewareVO.setCoursewareName((String) paramMap.get("coursewareName"));
		coursewareVO.setSpeaker((String) paramMap.get("speaker"));
		coursewareVO.setAttachmentId(attachmentId);
		coursewareVO.setHours(new BigDecimal(hours));
		String timeLimit = (String) paramMap.get("timeLimit");
		coursewareVO.setTimeLimit(new BigDecimal(timeLimit).multiply(new BigDecimal(60)));
		coursewareVO.setIsEnable("1");
		coursewareVO.setRemark((String) paramMap.get("remark"));
		coursewareVO.setInsertBy(userId);
		coursewareVO.setUpdateBy(userId);
		return coursewareVO;
	}

	@Deprecated
	// 原有的实现方式
	public int updateCourseware1(Map<String, Object> paramerMap, HttpServletRequest request) {
		// 1、查询课件所归属的课程是否有人正在学习
		Integer coursewareId = (Integer) paramerMap.get("coursewareId");
		List<TraLearningProgressVO> learningProgressList = this.getLearningCourseList(coursewareId);
		if (null != learningProgressList && learningProgressList.size() > 0) {
			throw new TrainBizException("此课件归属的课程仍有学生学习中，不允许修改");
		}
		SysUserVO user = (SysUserVO) paramerMap.get("user");
		Integer userId = user.getId();// 当前用户ID

		// 2、判断视频是否需要修改
		Object object = paramerMap.get("attachmentId");
		Boolean flag = false;// 是否需改视频标志
		if (object instanceof Integer) {
			LOG.info("视频未修改");
		} else if (object instanceof List) {
			flag = true;
			LOG.info("修改视频");
		}

		Integer hours = null;
		hours = Integer.valueOf((String) paramerMap.get("hours"));
		if (flag) {
			// 1、删除实际路径下该视频
			Map<String, Object> coursewareInfoMap = getAttachmentNameByCoursewareId(coursewareId);
			String attachmentName = null;// 原视频名称
			Integer attachmentId = null;
			if (null != coursewareInfoMap) {
				attachmentName = (String) coursewareInfoMap.get("attachment_name");
				attachmentId = (Integer) coursewareInfoMap.get("attachment_id");
			}
			// 2、移动新的视频到实际路径下
			List<Map<String, Object>> attachmentList = (List<Map<String, Object>>) object;
			Map<String, Object> attachmentMap = new HashMap<String, Object>();
			if (null != attachmentList && attachmentList.size() > 0) {
				attachmentMap = attachmentList.get(0);
			}
			String fileName = (String) attachmentMap.get("fileName");
			moveFile(fileName, request);
			// 3、修改附件表
			String originalFileName = (String) attachmentMap.get("originalFileName");
			updateAttachment(fileName, originalFileName, attachmentId, userId, hours);

			// 逐个删除附件文件
			// deleteFile(attachmentName, request);
			String url = this.videoServerAppPath + "file/delete.do";
			new HttpClientUtil().getDeleteFileResponseBody(url, attachmentName, this.videoPath);
		}
		// 3、修改课件
		TraCoursewareVO entity = getUpdateTraCoursewareVO(paramerMap, null, userId, hours);
		entity.setId(coursewareId);
		traCoursewareDao.updateTraCourseware(entity);

		return 1;
	}

	/**
	 * 石山视频实现方式
	 */
	public int updateCourseware(Map<String, Object> paramerMap, HttpServletRequest request) {
		// 1、查询课件所归属的课程是否有人正在学习
		Integer coursewareId = (Integer) paramerMap.get("coursewareId");
		List<TraLearningProgressVO> learningProgressList = this.getLearningCourseList(coursewareId);
		if (null != learningProgressList && learningProgressList.size() > 0) {
			throw new TrainBizException("此课件归属的课程仍有学生学习中，不允许修改");
		}
		SysUserVO user = (SysUserVO) paramerMap.get("user");
		Integer userId = user.getId();// 当前用户ID

		Integer hours = Integer.valueOf((String) paramerMap.get("hours"));
		// 1、删除实际路径下该视频
		Map<String, Object> coursewareInfoMap = getAttachmentNameByCoursewareId(coursewareId);
		String fileName = (String) paramerMap.get("videoId");
		String originalFileName = (String) paramerMap.get("originalFileName");
		String attachmentName = null;
		Integer attachmentId = null;
		if (null != coursewareInfoMap) {
			attachmentName = (String) coursewareInfoMap.get("attachment_name");
			attachmentId = (Integer) coursewareInfoMap.get("attachment_id");
			if (!StringUtils.isEmpty(fileName) && !attachmentName.equals(fileName)) {// 查看视频id是否相同，不相同则删除原有视频
				JSONObject jsonObjectSmvp = JSONObject.fromObject(Constant.smvpVideoCatInfo);
				SmvpClient client = new SmvpClient(jsonObjectSmvp.getString("token"));
				if (!StringUtils.isEmpty(attachmentName)) {
					client.entries.delete(attachmentName);
				}
			} else {
				// 视频未做更改
				fileName = attachmentName; // 用原来的名字
			}
		}
		// 3、修改附件表
		updateAttachment(fileName, originalFileName, attachmentId, userId, hours);
		// 原有实现方式： 逐个删除附件文件
		// String url = this.videoServerAppPath + "file/delete.do";
		// new HttpClientUtil().getDeleteFileResponseBody(url, attachmentName,
		// this.videoPath);
		// 3、修改课件
		TraCoursewareVO entity = getUpdateTraCoursewareVO(paramerMap, null, userId, hours);
		entity.setId(coursewareId);
		traCoursewareDao.updateTraCourseware(entity);

		return 1;
	}

	/**
	 * @param fileName
	 * @param originalFileName
	 * @return
	 * @return Integer
	 * @throws
	 */
	private int updateAttachment(String fileName, String originalFileName, Integer attachmentId, Integer userId, Integer hours) {
		TraAttachmentVO traAttachmentVO = new TraAttachmentVO();
		traAttachmentVO.setAttachmentName(fileName);
		traAttachmentVO.setAttachmentType("1");
		traAttachmentVO.setHours(new BigDecimal(hours));
		traAttachmentVO.setUpdateBy(userId);
		traAttachmentVO.setRemark(originalFileName);
		traAttachmentVO.setId(attachmentId);
		int count = traAttachmentDao.updateTraAttachment(traAttachmentVO);
		return 1;
	}

	/**
	 * @param hours
	 * @Description
	 * @param paramerMap
	 * @param attachmentId
	 * @return
	 * @return TraCoursewareVO
	 * @throws
	 */
	private TraCoursewareVO getUpdateTraCoursewareVO(Map<String, Object> paramMap, Integer attachmentId, Integer userId,
			Integer hours) {
		TraCoursewareVO coursewareVO = new TraCoursewareVO();
		coursewareVO.setCoursewareName((String) paramMap.get("coursewareName"));
		coursewareVO.setSpeaker((String) paramMap.get("speaker"));
		String timeLimit = (String) paramMap.get("timeLimit");
		coursewareVO.setTimeLimit(new BigDecimal(timeLimit).multiply(new BigDecimal(60)));
		coursewareVO.setHours(new BigDecimal(hours));
		coursewareVO.setUpdateBy(userId);
		return coursewareVO;
	}

	/**
	 * @Description
	 * @param coursewareId
	 * @return
	 * @return String
	 * @throws
	 */
	private Map<String, Object> getAttachmentNameByCoursewareId(Integer coursewareId) {
		Map<String, Object> coursewareInfoMap = traCoursewareDao.getCoursewareInfoByCoursewareId(coursewareId);
		return coursewareInfoMap;
	}

	public boolean deleteFile(String fileName, HttpServletRequest request) {
		String destPath = request.getSession().getServletContext().getRealPath(videoPath);
		File destFile = new File(destPath + "/" + fileName); // 目标文件夹
		return destFile.delete();
	}

	public boolean deletePptFile(String fileName, String pptId, HttpServletRequest request) {
		String destPath = request.getSession().getServletContext().getRealPath(realImgLocationPath) + "/" + pptId;
		File destFile = new File(destPath + "/" + fileName); // 目标文件夹
		return destFile.delete();
	}

	@Override
	public int deleteCourseware(List<Integer> coursewareIdlist, SysUserVO user, HttpServletRequest request) {
		int sum = 0;
		if (coursewareIdlist != null && coursewareIdlist.size() > 0) {

			// 1、查询课件所归属的课程是否有人正在学习
			Integer coursewareId = coursewareIdlist.get(0);
			List<TraLearningProgressVO> learningProgressList = getLearningCourseList(coursewareId);
			if (null != learningProgressList && learningProgressList.size() > 0) {
				throw new TrainBizException("此课件归属的课程仍有新员工学习中，不允许删除");
			}

			// 查询课件是否有老学员正在学习
			int count = traLearningProgressDao.getLearningCountOfOldStaffByCoursewareId(coursewareId);
			if (0 < count) {
				throw new TrainBizException("此课件仍有老员工学习中，不允许删除");
			}

			// 2、根据课件ID批量删除 tra_course_courseware 课程-课件关系表
			this.traCourseCoursewareDao.batchDeleteCourseCoursewareByCoursewareId(coursewareIdlist);

			// 3、根据课件ID删除附件文件
			String attachmentName = null;// 原视频名称

			// 根据课件ID列表查询出附件信息
			List<Map<String, Object>> coursewareInfoList = this.traAttachmentDao
					.getAttachmentInfoByCoursewareIdList(coursewareIdlist);

			// 4、根据课件ID批量删除 tra_attachment 附件
			this.traAttachmentDao.batchDeleteTraAttachmentByCoursewareId(coursewareIdlist);

			// 5、根据课件ID批量删除 tra_courseware_attachment 课件-附件关系表
			this.traCoursewareAttachmentDao.batchDeleteTraCoursewareAttachmentByCoursewareId(coursewareIdlist);

			// 6、根据课件ID批量删除 tra_required_question 考卷必考题
			this.traRequiredQuestionDao.batchDeleteTraRequiredQuestionByCoursewareId(coursewareIdlist);

			// 7、根据课件ID批量删除 tra_rule 考卷规则
			this.traRuleDao.batchDeleteTraRuleByCoursewareId(coursewareIdlist);

			// 8、根据课件ID批量删除 tra_final_exam_question 期末考卷必考题
			this.traFinalExamQuestionDao.batchDeleteTraFinalExamQuestionByCoursewareId(coursewareIdlist);

			// 9、删除PPT文件
			// 根据课件ID列表查询PPT图片集
			List<Map<String, Object>> dtlMap = this.traPptDtlDao.queryPptDtlByCoursewareIdlist(coursewareIdlist);
			for (Map<String, Object> map : dtlMap) {
				String fileName = (String) map.get("ppt_dtl_name");
				String pptId = String.valueOf(map.get("ppt_id"));
				// 逐个删除PPT图片
				deletePptFile(fileName, pptId, request);
			}

			// 10、根据课件ID批量删除 tra_ppt_dtl PPT详情
			this.traPptDtlDao.batchDeleteTraPptDtlByCoursewareId(coursewareIdlist);

			// 11、根据课件ID批量删除 tra_ppt PPT
			this.traPptDao.batchDeleteTraPptByCoursewareId(coursewareIdlist);

			// 12、根据课件ID批量删除 tra_question 试题（修改is_enable为'0'）
			this.traQuestionDao.batchUpdateTraQuestionByCoursewareId(coursewareIdlist);

			// 13、批量更新课件
			this.batchUpdateCourseware(coursewareIdlist, user);

			// 重构， 删除的动作放在最后
			for (Map<String, Object> map : coursewareInfoList) {
				attachmentName = (String) map.get("attachment_name");// 视频id
				JSONObject jsonObjectSmvp = JSONObject.fromObject(Constant.smvpVideoCatInfo);
				SmvpClient client = new SmvpClient(jsonObjectSmvp.getString("token"));
				client.entries.delete(attachmentName);
				// 原实现方式： 逐个删除附件文件
				// String url = this.videoServerAppPath + "file/delete.do";
				// new HttpClientUtil().getDeleteFileResponseBody(url,
				// attachmentName, this.videoPath);
			}
			sum = 1;
		} else {
			sum = 0;
		}
		return sum;
	}

	/**
	 * @Description
	 * @param coursewareId
	 * @return
	 * @return List<TraLearningProgressVO>
	 * @throws
	 */
	private List<TraLearningProgressVO> getLearningCourseList(Integer coursewareId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coursewareId", coursewareId);
		List<TraLearningProgressVO> learningProgressList = traLearningProgressDao.getTraLearningProgresssByCoursewareId(map);
		return learningProgressList;
	}

	private int batchUpdateCourseware(List<Integer> idsList, SysUserVO user) {
		List<TraCoursewareVO> list = new ArrayList<TraCoursewareVO>();
		TraCoursewareVO traCoursewareVO;
		for (Integer id : idsList) {
			traCoursewareVO = new TraCoursewareVO();
			traCoursewareVO.setId(id);
			traCoursewareVO.setIsEnable("0");
			traCoursewareVO.setUpdateBy(user.getId());
			list.add(traCoursewareVO);
		}
		return traCoursewareDao.batchUpdateTraCourseware(list);
	}

	@Override
	public List<Map<String, Object>> getCourseware(String coursewareName) {
		Map<String, Object> param = new HashMap<String, Object>();
		return traCoursewareDao.getTraCoursewareList(param);
	}

	@Override
	public List<Map<String, Object>> getCourseCourseware(String coursewareName, String courseName, int pageNumber, int pageSize) {
		Page<Map<String, Object>> list = null;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		PageHelper.startPage(pageNumber, pageSize, true);
		if (StringUtils.isEmpty(coursewareName)) {
			if (!StringUtils.isEmpty(courseName)) {// 按课程去查询
				paramMap.put("courseName", courseName);
				list = (Page<Map<String, Object>>) traCourseCoursewareDao.getTraCourseCoursewaresMapByCourseName(paramMap);
			} else {
				list = (Page<Map<String, Object>>) traCourseCoursewareDao.getTraCourseCoursewaresAll();
			}
		} else {
			if (!StringUtils.isEmpty(courseName)) {
				paramMap.put("courseName", courseName);
				paramMap.put("coursewareName", coursewareName);
				list = (Page<Map<String, Object>>) traCourseCoursewareDao.getTraCourseCoursewaresByName(paramMap);
			} else {
				paramMap.put("coursewareName", coursewareName);
				list = (Page<Map<String, Object>>) traCourseCoursewareDao.getTraCourseCoursewaresByCoursewareName(paramMap);
			}
		}
		return list;
	}

	@Override
	public Integer nextCoursewareId(Integer courseId, Integer coursewareId) {
		TraCourseCoursewareVO courseCoursewareVO0 = this.traCourseCoursewareDao.getCourseCoursewareVO(courseId, coursewareId);
		TraCourseCoursewareVO courseCoursewareVO;
		if ("0".equals(courseCoursewareVO0.getIsRequired())) {
			courseCoursewareVO = this.traCourseCoursewareDao.nextCourseCoursewareVOForOptional(courseId, coursewareId);
		} else {
			courseCoursewareVO = this.traCourseCoursewareDao.nextCourseCoursewareVO(courseId, coursewareId);
		}
		return courseCoursewareVO == null ? null : courseCoursewareVO.getCoursewareId();
	}

	@Override
	public boolean isStudyForCourseware(Integer userId, Integer courseId, Integer coursewareId) {
		TraCourseCoursewareVO coursewareVO = this.traCourseCoursewareDao.preCourseCoursewareVO(courseId, coursewareId);
		// 前一课件考试通过 或 为第一个课件
		return this.isPassed(userId, courseId, coursewareVO.getCoursewareId())
				|| this.traCourseCoursewareDao.isFirstForCourse(courseId, coursewareId);
	}

	@Override
	public List<CoursewareProgressVO> getCoursewareProgresses(Integer studentId) {

		TraCourseVO courseVO = this.traCourseDao.getCourseVOByStudentId(studentId);
		if (courseVO == null) {
			throw new TrainBizException("没有相应的课程!");
		}
		LOG.debug("studentId " + studentId + "得到课程:" + courseVO);

		List<CoursewareProgressVO> resultCourseware = this.traCoursewareDao.getTraCoursewaresByCourseId(courseVO.getId());

		// 得到课件id列表
		List<Integer> coursewareIds = new LinkedList<Integer>();

		for (TraCoursewareVO vo : resultCourseware) {
			coursewareIds.add(vo.getId());
		}

		// 设置是否学习过
		TraLearningProgressVO learningProgressVO = this.traLearningProgressDao.getLearningProgressVOByStudentId(studentId);

		// update by gaoshan,2014-09-28
		// 两个学习进度，同样的课程、课件，无法区分课件是否已经学习过，需要在学生流程表添加进度ID，根据进度区分
		List<Integer> studiedCoursewareIds = this.traStudentRecordDao.getStudiedCoursewareIds(studentId, coursewareIds,
				courseVO.getId(), learningProgressVO.getId());

		for (CoursewareProgressVO vo : resultCourseware) {
			if (studiedCoursewareIds.contains(vo.getId())) {
				vo.setStudied(true);
			} else {
				vo.setStudied(false);
			}
		}

		List<Integer> attachmentIds = new ArrayList<Integer>();
		for (CoursewareProgressVO progressVO : resultCourseware) {
			attachmentIds.add(progressVO.getAttachmentId());
		}
		List<TraAttachmentVO> attachmentVOs = this.traAttachmentDao.getAttachmentVOsByIds(attachmentIds);

		Map<Integer, TraAttachmentVO> attachmentVOMap = new HashMap<Integer, TraAttachmentVO>();
		for (TraAttachmentVO attachmentVO : attachmentVOs) {
			attachmentVOMap.put(attachmentVO.getId(), attachmentVO);
		}

		// 转换时间
		for (CoursewareProgressVO coursewareProgressVO : resultCourseware) {
			Integer attachmentId = coursewareProgressVO.getAttachmentId();
			TraAttachmentVO attachmentVO = attachmentVOMap.get(attachmentId);
			if (attachmentVO != null && null == coursewareProgressVO.getHours() && null != attachmentVO.getHours()) {
				coursewareProgressVO.setHours(attachmentVO.getHours());
				coursewareProgressVO.setCourseHours(TimeUtils.toHoursStr(attachmentVO.getHours()));
			} else if (null != coursewareProgressVO.getHours()) {
				coursewareProgressVO.setCourseHours(TimeUtils.toHoursStr(coursewareProgressVO.getHours()));
			}
		}

		// TraLearningProgressVO learningProgressVO =
		// this.traLearningProgressDao.getLearningProgressVOByStudentId(studentId);

		// 设置考试情况
		// update by gaoshan,2014-09-28
		// 修正统计不准确的bug，需要添加进度ID，根据进度区分
		List<TraExamPaperVO> examPaperVOs = this.traExamPaperDao.getTraExamPapersByCoursewareIds(studentId, coursewareIds,
				learningProgressVO.getId());
		// update by gaoshan,2014-09-28
		for (CoursewareProgressVO vo : resultCourseware) {
			for (TraExamPaperVO examPaper : examPaperVOs) {
				if (vo.getId().equals(examPaper.getCoursewareId())) {
					vo.setExamined(true);
					if (!vo.isPassed()) {
						vo.setPassed(examPaper.getPaperStatus().equals("3"));
					}
				}
			}
			if (!vo.isStudied() && vo.getId().equals(learningProgressVO.getCoursewareId())) {
				vo.setIsNew(true);
			}
		}

		return resultCourseware;
	}

	@Override
	public List<CoursewareProgressVO> getCoursewareProgressOfOldStaff(Integer studentId) {
		// 查询老员工的课件（正在学习的课件）
		List<CoursewareProgressVO> resultCourseware = this.traCoursewareDao.getTraCoursewaresByOldStaffId(studentId);
		for (CoursewareProgressVO courseware : resultCourseware) {
			Integer learningProgressId = courseware.getLearningProgressId();
			Integer coursewareId = courseware.getId();

			// 设置是否学习过
			Integer studyCount = traStudentRecordDao
					.getStudentRecordStudyCount(studentId, null, coursewareId, learningProgressId);
			if (null != studyCount && 0 < studyCount.intValue()) {
				courseware.setStudied(true);
			} else {
				courseware.setStudied(false);
			}

			List<TraExamPaperVO> examPaperVOs = this.traExamPaperDao.getTraExamPapersByCoursewareIds(studentId, null,
					learningProgressId);
			for (TraExamPaperVO examPaper : examPaperVOs) {
				if (courseware.getId().equals(examPaper.getCoursewareId())) {
					courseware.setExamined(true);
					if (!courseware.isPassed()) {
						courseware.setPassed(examPaper.getPaperStatus().equals("3"));
					}
				}
			}
			if (!courseware.isStudied() && courseware.getId().equals(coursewareId)) {
				courseware.setIsNew(true);
			}
		}

		if (null != resultCourseware && 0 < resultCourseware.size()) {
			// 组织附件信息列表
			List<Integer> attachmentIds = new ArrayList<Integer>();
			for (CoursewareProgressVO progressVO : resultCourseware) {
				attachmentIds.add(progressVO.getAttachmentId());
			}
			List<TraAttachmentVO> attachmentVOs = this.traAttachmentDao.getAttachmentVOsByIds(attachmentIds);

			// 组织附件信息Map<id,vo>
			Map<Integer, TraAttachmentVO> attachmentVOMap = new HashMap<Integer, TraAttachmentVO>();
			for (TraAttachmentVO attachmentVO : attachmentVOs) {
				attachmentVOMap.put(attachmentVO.getId(), attachmentVO);
			}

			// 转换时间
			for (CoursewareProgressVO coursewareProgressVO : resultCourseware) {
				Integer attachmentId = coursewareProgressVO.getAttachmentId();
				TraAttachmentVO attachmentVO = attachmentVOMap.get(attachmentId);
				if (attachmentVO != null && null == coursewareProgressVO.getHours() && null != attachmentVO.getHours()) {
					coursewareProgressVO.setHours(attachmentVO.getHours());
					coursewareProgressVO.setCourseHours(TimeUtils.toHoursStr(attachmentVO.getHours()));
				} else if (null != coursewareProgressVO.getHours()) {
					coursewareProgressVO.setCourseHours(TimeUtils.toHoursStr(coursewareProgressVO.getHours()));
				}
			}
		}
		return resultCourseware;
	}

	@Override
	public List<CoursewareProgressVO> getHistoryCoursewareProgressOfOldStaff(Integer studentId) {
		// 查询老员工的课件（历史课件）
		List<CoursewareProgressVO> resultCourseware = this.traCoursewareDao.getHistoryCoursewareByOldStaffId(studentId);
		for (CoursewareProgressVO courseware : resultCourseware) {
			Integer learningProgressId = courseware.getLearningProgressId();
			Integer coursewareId = courseware.getId();

			// 设置是否学习过
			courseware.setStudied(true);

			List<TraExamPaperVO> examPaperVOs = this.traExamPaperDao.getTraExamPapersByCoursewareIds(studentId, null,
					learningProgressId);
			for (TraExamPaperVO examPaper : examPaperVOs) {
				if (courseware.getId().equals(examPaper.getCoursewareId())) {
					courseware.setExamined(true);
					if (!courseware.isPassed()) {
						courseware.setPassed(examPaper.getPaperStatus().equals("3"));
					}
				}
			}
		}

		if (null != resultCourseware && 0 < resultCourseware.size()) {
			// 组织附件信息列表
			List<Integer> attachmentIds = new ArrayList<Integer>();
			for (CoursewareProgressVO progressVO : resultCourseware) {
				attachmentIds.add(progressVO.getAttachmentId());
			}
			List<TraAttachmentVO> attachmentVOs = this.traAttachmentDao.getAttachmentVOsByIds(attachmentIds);

			// 组织附件信息Map<id,vo>
			Map<Integer, TraAttachmentVO> attachmentVOMap = new HashMap<Integer, TraAttachmentVO>();
			for (TraAttachmentVO attachmentVO : attachmentVOs) {
				attachmentVOMap.put(attachmentVO.getId(), attachmentVO);
			}

			// 转换时间
			for (CoursewareProgressVO coursewareProgressVO : resultCourseware) {
				Integer attachmentId = coursewareProgressVO.getAttachmentId();
				TraAttachmentVO attachmentVO = attachmentVOMap.get(attachmentId);
				if (attachmentVO != null && null == coursewareProgressVO.getHours() && null != attachmentVO.getHours()) {
					coursewareProgressVO.setHours(attachmentVO.getHours());
					coursewareProgressVO.setCourseHours(TimeUtils.toHoursStr(attachmentVO.getHours()));
				} else if (null != coursewareProgressVO.getHours()) {
					coursewareProgressVO.setCourseHours(TimeUtils.toHoursStr(coursewareProgressVO.getHours()));
				}
			}
		}
		return resultCourseware;
	}

	@Override
	public CoursewareProgressVO getCoursewareWithHistories(Integer userId, Integer courseId, Integer coursewareId,
			Integer learningProgressId, Integer isOldStaff) {
		CoursewareProgressVO result = null;
		if (null == isOldStaff || 0 == isOldStaff.intValue()) {// 新学员
			result = this.getCoursewareWithHistoriesOfNewStaff(userId, courseId, coursewareId, learningProgressId, isOldStaff);
		} else if (1 == isOldStaff.intValue()) {// 老学员
			result = this.getCoursewareWithHistoriesOfOldStaff(userId, coursewareId, learningProgressId, isOldStaff);
		}
		return result;
	}

	/**
	 * @Description 新学员，获取课件信息和历史记录
	 * @param userId
	 * @param courseId
	 * @param coursewareId
	 * @param learningProgressId
	 * @return
	 * @return CoursewareProgressVO
	 * @throws
	 */
	private CoursewareProgressVO getCoursewareWithHistoriesOfNewStaff(Integer userId, Integer courseId, Integer coursewareId,
			Integer learningProgressId, Integer isOldStaff) {
		CoursewareProgressVO result = new CoursewareProgressVO();

		// 查询课件
		TraCoursewareVO coursewareVO = this.traCoursewareDao.getTraCoursewareById(coursewareId);
		TraRuleVO ruleVO = this.traRuleDao.getTraRuleByCoursewareId(coursewareVO.getId());
		if (ruleVO == null) {
			throw new TrainBizException("没有设置考试规则");
		}

		BeanUtils.copyProperties(coursewareVO, result);

		BigDecimal hour = coursewareVO.getHours();
		if (hour == null) {
			TraAttachmentVO attachmentVO = this.traAttachmentDao.getTraAttachmentById(coursewareVO.getAttachmentId());
			hour = attachmentVO.getHours();
		}
		result.setCourseHours(TimeUtils.toHoursStr(hour));

		// 学习历史

		// start,update by gaoshan,2014-09-28
		// 修正次数统计不准确的bug，需要根据进度ID查询
		List<TraStudentRecordVO> studentRecordVOs = this.traStudentRecordDao.getStudentRecordsByCoursewareId(userId, courseId,
				coursewareId, learningProgressId);
		// end,update by gaoshan,2014-09-28

		result.setStudentRecordVOs(studentRecordVOs);

		List<TraStudentRecordVO> examStudentRecords = new ArrayList<TraStudentRecordVO>();
		List<TraStudentRecordVO> studyStudentRecords = new ArrayList<TraStudentRecordVO>();
		List<Integer> paperIds = new ArrayList<Integer>();

		for (TraStudentRecordVO studentRecordVO : studentRecordVOs) {
			if ("0".equals(studentRecordVO.getOperatingType())) {
				studyStudentRecords.add(studentRecordVO);

			} else if ("1".equals(studentRecordVO.getOperatingType())) {
				examStudentRecords.add(studentRecordVO);
				paperIds.add(studentRecordVO.getPaperId());
			}
		}

		if (paperIds.size() > 0) {
			// 改为 关联查询
			List<TraExamPaperVO> paperVOs = this.traExamPaperDao.getExamPaperVOsByIds(paperIds);
			Map<Integer, TraExamPaperVO> examPaperVOMap = new HashMap<Integer, TraExamPaperVO>();
			for (TraExamPaperVO examPaperVO : paperVOs) {
				examPaperVOMap.put(examPaperVO.getId(), examPaperVO);
			}

			for (TraStudentRecordVO studentRecordVO : studentRecordVOs) {
				if (null != studentRecordVO.getPaperId()) {
					TraExamPaperVO examPaperVO = examPaperVOMap.get(studentRecordVO.getPaperId());
					if (examPaperVO != null) {
						BigDecimal score = examPaperVO.getTotalScore();
						studentRecordVO.setScore(score);
					}
				}
			}

		}
		result.setStudyStudentRecords(studyStudentRecords);
		result.setExamStudentRecords(examStudentRecords);
		result.setPaperType(ruleVO.getPaperType());

		// 查询附件
		TraAttachmentVO attachmentVO = this.traAttachmentDao.getTraAttachmentById(coursewareVO.getAttachmentId());
		// attachmentVO.setUri(DeviceTokenUtil.getPropertiesValue("videoPath") +
		// "/" + attachmentVO.getAttachmentName());
		// 石山视频处理逻辑
		JSONObject jsonObjectSmvp = JSONObject.fromObject(Constant.smvpVideoCatInfo);
		SmvpClient client = new SmvpClient(jsonObjectSmvp.getString("token"));
		List<String> formats = new ArrayList<String>();
		formats.add("MP4");
		LOG.info("video id: " + attachmentVO.getAttachmentName() + " formats: " + formats);
		Map smvpAttachData = client.entries.json(attachmentVO.getAttachmentName(), formats, false); // 名字就是videoId
		attachmentVO.setSmvpAttachData(smvpAttachData);
		// 原有的逻辑
		// attachmentVO.setUri(this.viewVideoPath + "/" +
		// attachmentVO.getAttachmentName());
		// LOG.info("view courseware address: "+ this.viewVideoPath + "/" +
		// attachmentVO.getAttachmentName());
		result.setAttachmentVO(attachmentVO);

		// 是否学习过
		result.setStudied(studentRecordVOs.size() > 0);
		result.setIsTest(ruleVO.getIsTest());
		return result;
	}

	/**
	 * @Description 老学员，获取课件信息和历史记录
	 * @param userId
	 * @param courseId
	 * @param coursewareId
	 * @param learningProgressId
	 * @return
	 * @return CoursewareProgressVO
	 * @throws
	 */
	private CoursewareProgressVO getCoursewareWithHistoriesOfOldStaff(Integer userId, Integer coursewareId,
			Integer learningProgressId, Integer isOldStaff) {
		CoursewareProgressVO result = new CoursewareProgressVO();

		// 查询课件
		TraCoursewareVO coursewareVO = this.traCoursewareDao.getTraCoursewareById(coursewareId);
		TraRuleVO ruleVO = this.traRuleDao.getTraRuleByCoursewareId(coursewareVO.getId());
		if (ruleVO == null) {
			throw new TrainBizException("没有设置考试规则");
		}

		BeanUtils.copyProperties(coursewareVO, result);

		BigDecimal hour = coursewareVO.getHours();
		if (hour == null) {
			TraAttachmentVO attachmentVO = this.traAttachmentDao.getTraAttachmentById(coursewareVO.getAttachmentId());
			hour = attachmentVO.getHours();
		}
		result.setCourseHours(TimeUtils.toHoursStr(hour));

		// 学习历史
		List<TraStudentRecordVO> studentRecordVOs = this.traStudentRecordDao.getStudentRecordsByCoursewareId(userId, null,
				coursewareId, learningProgressId);

		result.setStudentRecordVOs(studentRecordVOs);

		List<TraStudentRecordVO> examStudentRecords = new ArrayList<TraStudentRecordVO>();
		List<TraStudentRecordVO> studyStudentRecords = new ArrayList<TraStudentRecordVO>();
		List<Integer> paperIds = new ArrayList<Integer>();

		for (TraStudentRecordVO studentRecordVO : studentRecordVOs) {
			if ("0".equals(studentRecordVO.getOperatingType())) {
				studyStudentRecords.add(studentRecordVO);

			} else if ("1".equals(studentRecordVO.getOperatingType())) {
				examStudentRecords.add(studentRecordVO);
				paperIds.add(studentRecordVO.getPaperId());
			}
		}

		if (paperIds.size() > 0) {
			// 改为 关联查询
			List<TraExamPaperVO> paperVOs = this.traExamPaperDao.getExamPaperVOsByIds(paperIds);
			Map<Integer, TraExamPaperVO> examPaperVOMap = new HashMap<Integer, TraExamPaperVO>();
			for (TraExamPaperVO examPaperVO : paperVOs) {
				examPaperVOMap.put(examPaperVO.getId(), examPaperVO);
			}

			for (TraStudentRecordVO studentRecordVO : studentRecordVOs) {
				if (null != studentRecordVO.getPaperId()) {
					TraExamPaperVO examPaperVO = examPaperVOMap.get(studentRecordVO.getPaperId());
					if (examPaperVO != null) {
						BigDecimal score = examPaperVO.getTotalScore();
						studentRecordVO.setScore(score);
					}
				}
			}

		}
		result.setStudyStudentRecords(studyStudentRecords);
		result.setExamStudentRecords(examStudentRecords);
		result.setPaperType(ruleVO.getPaperType());

		// 查询附件
		TraAttachmentVO attachmentVO = this.traAttachmentDao.getTraAttachmentById(coursewareVO.getAttachmentId());

		// 石山视频处理逻辑
		JSONObject jsonObjectSmvp = JSONObject.fromObject(Constant.smvpVideoCatInfo);
		SmvpClient client = new SmvpClient(jsonObjectSmvp.getString("token"));
		List<String> formats = new ArrayList<String>();
		formats.add("MP4");
		LOG.info("video id: " + attachmentVO.getAttachmentName() + " formats: " + formats);
		Map smvpAttachData = client.entries.json(attachmentVO.getAttachmentName(), formats, false); // 名字就是videoId
		attachmentVO.setSmvpAttachData(smvpAttachData);
		result.setAttachmentVO(attachmentVO);

		// 是否学习过
		result.setStudied(studentRecordVOs.size() > 0);
		result.setIsTest(ruleVO.getIsTest());
		return result;
	}

	@Override
	public boolean isLastCourseware(Integer courseId, Integer coursewareId) {
		return this.traCourseCoursewareDao.isLastForCourse(courseId, coursewareId);
	}

	/**
	 * @Title getCourseByCoursewareId
	 * @Description
	 * @param coursewareId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getCourseByCoursewareId(Integer coursewareId) {
		return traCoursewareDao.getCourseByCoursewareId(coursewareId);
	}

	/**
	 * @Title getCoursewareInfoByCoursewareId
	 * @Description
	 * @param coursewareId
	 * @return
	 */
	@Override
	public Map<String, Object> getCoursewareInfoByCoursewareId(Map<String, Object> map) {
		Integer coursewareId = (Integer) map.get("coursewareId");
		Map<String, Object> coursewareInfoMap = traCoursewareDao.getCoursewareInfoByCoursewareId(coursewareId);
		List<Map<String, Object>> courseInfoList = getCourseByCoursewareId(coursewareId);
		coursewareInfoMap.put("courseInfo", courseInfoList);
		return coursewareInfoMap;
	}

	/**
	 * @Title uploadVideo
	 * @Description
	 * @param myfiles
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Override
	public Object uploadVideo(MultipartFile[] myfiles, HttpServletRequest request) throws IOException {
		String url = this.videoServerAppPath + "file/uploadVideo.do";
		return new HttpClientUtil().getUploadFileResponseBody(url, myfiles, this.videoPath, this.videoServerAppPath, request
				.getSession().getAttribute(Constant.TEMP_FILE_PATH).toString());
		// return FileUploaderUtil.uploadFile(myfiles, "videoPath", null, 0,
		// request);
	}

	private boolean isPassed(Integer studentId, Integer courseId, Integer coursewareId) {
		return this.traExamPaperDao.getExamPapersForPassed(studentId, courseId, coursewareId).size() > 0;
	}
}
