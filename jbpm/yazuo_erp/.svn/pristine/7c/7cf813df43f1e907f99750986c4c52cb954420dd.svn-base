/**
 * @Description 老员工管理相关服务实现类
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 		gaoshan		2014-10-09	创建文档
 * 
 */
package com.yazuo.erp.train.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.dao.SysGroupDao;
import com.yazuo.erp.system.dao.SysPositionDao;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.vo.SysGroupVO;
import com.yazuo.erp.system.vo.SysPositionVO;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.controller.StudentManagementController;
import com.yazuo.erp.train.dao.TraCourseCoursewareDao;
import com.yazuo.erp.train.dao.TraCourseDao;
import com.yazuo.erp.train.dao.TraCoursewareDao;
import com.yazuo.erp.train.dao.TraExamDtlDao;
import com.yazuo.erp.train.dao.TraExamPaperDao;
import com.yazuo.erp.train.dao.TraFinalExamRuleDao;
import com.yazuo.erp.train.dao.TraLearningProgressDao;
import com.yazuo.erp.train.dao.TraPositionCourseDao;
import com.yazuo.erp.train.dao.TraPptDao;
import com.yazuo.erp.train.dao.TraPptDtlDao;
import com.yazuo.erp.train.dao.TraRuleDao;
import com.yazuo.erp.train.dao.TraStudentRecordDao;
import com.yazuo.erp.train.dao.TraTeacherStudentDao;
import com.yazuo.erp.train.exception.TrainBizException;
import com.yazuo.erp.train.service.CalendarService;
import com.yazuo.erp.train.service.OldStaffManagementService;
import com.yazuo.erp.train.service.StudentManagementService;
import com.yazuo.erp.train.vo.CoursewareProgressVO;
import com.yazuo.erp.train.vo.TraCourseCoursewareVO;
import com.yazuo.erp.train.vo.TraCourseVO;
import com.yazuo.erp.train.vo.TraCoursewareVO;
import com.yazuo.erp.train.vo.TraExamDtlVO;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import com.yazuo.erp.train.vo.TraFinalExamRuleVO;
import com.yazuo.erp.train.vo.TraLearningProgressVO;
import com.yazuo.erp.train.vo.TraPositionCourseVO;
import com.yazuo.erp.train.vo.TraRuleVO;
import com.yazuo.erp.train.vo.TraStudentRecordVO;
import com.yazuo.erp.train.vo.TraTeacherStudentVO;
import com.yazuo.util.DateUtil;
import com.yazuo.util.StringUtil;

/**
 * @Description 老员工管理相关服务实现类
 * @author gaoshan
 * @date 2014-10-09 下午6:44:38
 */
@Service
public class OldStaffManagementServiceImpl implements OldStaffManagementService {

	private static final Log log = LogFactory.getLog(OldStaffManagementServiceImpl.class);

	/**
	 * 老师-学生关系表DAO
	 */
	@Resource
	private TraTeacherStudentDao traTeacherStudentDao;

	/**
	 * 老师-学生关系表DAO
	 */
	@Resource
	private TraStudentRecordDao traStudentRecordDao;

	/**
	 * PPT表DAO
	 */
	@Resource
	private TraPptDao traPptDao;

	/**
	 * PPT详情表DAO
	 */
	@Resource
	private TraPptDtlDao traPptDtlDao;

	/**
	 * 考卷规则表DAO
	 */
	@Resource
	private TraRuleDao traRuleDao;

	/**
	 * 试卷表DAO
	 */
	@Resource
	private TraExamPaperDao traExamPaperDao;

	/**
	 * 试卷题干表DAO
	 */
	@Resource
	private TraExamDtlDao traExamDtlDao;

	/**
	 * 期末考卷规则表DAO
	 */
	@Resource
	private TraFinalExamRuleDao traFinalExamRuleDao;

	/**
	 * 学习进度表DAO
	 */
	@Resource
	private TraLearningProgressDao traLearningProgressDao;

	/**
	 * 课程表DAO
	 */
	@Resource
	private TraCourseDao traCourseDao;

	/**
	 * 职位表DAO
	 */
	@Resource
	private SysPositionDao sysPositionDao;

	/**
	 * 课件表DAO
	 */
	@Resource
	private TraCoursewareDao traCoursewareDao;

	/**
	 * 用户表DAO
	 */
	@Resource
	private SysUserDao sysUserDao;

	/**
	 * 职位-课程关系表DAO
	 */
	@Resource
	private TraPositionCourseDao traPositionCourseDao;

	/**
	 * 课程-课件关系表DAO
	 */
	@Resource
	private TraCourseCoursewareDao traCourseCoursewareDao;

	/**
	 * 公司组织架构表DAO
	 */
	@Resource
	private SysGroupDao sysGroupDao;

	/**
	 * 职位表DAO
	 */
	@Resource
	private SysPositionDao sysPostionDao;

	/**
	 * 日历功能
	 */
	@Resource
	private CalendarService calendarService;

	/**
	 * 上传的试题PPT(真正用的文件)配置路径
	 */
	@Value("${pptPhotoPath}")
	private String pptPhotoPath;

	/**
	 * 上传的录音配置路径
	 */
	@Value("${audioPath}")
	private String audioPath;

	/**
	 * @Title queryCoursewareList
	 * @Description 根据课件名称模糊查询有效的课件
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.OldStaffManagementService#queryCoursewareList(java.util.Map)
	 */
	@Override
	public Page<Map<String, Object>> queryCoursewareList(Map<String, Object> params) {
		Page<Map<String, Object>> list = traCoursewareDao.queryCoursewareList(params);
		return list;
	}

	/**
	 * @Title queryInfo
	 * @Description 发起学习-查询，根据条件（1-按照部门，2-按照职位，3-按照员工），查询部门列表，职位列表，员工列表
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.OldStaffManagementService#queryInfo(java.util.Map)
	 */
	@Override
	public Map<String, Object> queryInfo(Map<String, Object> params) {
		String queryType = (String) params.get("queryType");

		if (null == queryType || 0 == queryType.trim().length()) {
			throw new TrainBizException("查询类型不能为空[queryType]");
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<SysGroupVO> sysGroupList = null;
		List<SysPositionVO> sysPositionList = null;
		Map<String, Object> dataMap = null;

		// 按照部门
		if ("1".equals(queryType)) {
			sysGroupList = sysGroupDao.getSysGroups(params);
		}

		// 按照职位
		else if ("2".equals(queryType)) {
			sysPositionList = sysPostionDao.queryPositionList();
		}

		// 按照员工
		else if ("3".equals(queryType)) {
			String flag = (String) params.get("flag");// 0-不排除，1-排除
			Integer coursewareId = (Integer) params.get("coursewareId");

			if (null == flag || 0 == flag.trim().length()) {
				throw new TrainBizException("是否排除已学习过的学员标识不能为空");
			}

			if (null == coursewareId) {
				throw new TrainBizException("课件ID不能为空");
			}

			Integer pageNumber = (Integer) params.get("pageNumber");
			Integer pageSize = (Integer) params.get("pageSize");
			PageHelper.startPage(pageNumber, pageSize, true);

			// 查询老员工信息
			Map<String, Object> input = new HashMap<String, Object>();
			input.put("flag", flag);
			input.put("coursewareId", coursewareId);
			Page<SysUserVO> sysUserList = sysUserDao.getFormalSysUserList(input);

			dataMap = new HashMap<String, Object>();
			dataMap.put(Constant.TOTAL_SIZE, sysUserList.getTotal());
			dataMap.put(Constant.ROWS, sysUserList.getResult());
		}

		// 查询类型只能为1-按照部门，2-按照职位，3-按照员工，其余异常
		else {
			throw new TrainBizException("查询类型异常");
		}

		returnMap.put("sysGroups", sysGroupList);
		returnMap.put("sysPositionList", sysPositionList);
		returnMap.put("sysUserList", dataMap);

		return returnMap;
	}

	/**
	 * @Title executeTermBegin
	 * @Description 发起学习
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.OldStaffManagementService#executeTermBegin(java.util.Map)
	 */
	@Override
	public int executeTermBegin(Map<String, Object> params) {
		SysUserVO user = (SysUserVO) params.get("user");
		Integer userId = user.getId();
		String conditionType = (String) params.get("conditionType");// 1-按照部门，2-按照职位，3-按照员工
		List<Integer> idList = (List<Integer>) params.get("idList");// id列表（组ID/职位ID/员工ID），具体类型根据conditionType判定
		String flag = (String) params.get("flag");// 是否排除已学习过的学员标志（0-不排除，1-排除）
		Integer coursewareId = (Integer) params.get("coursewareId");

		// 校验入参有效性
		this.checkTermBeginPrams(conditionType, idList, flag, coursewareId);

		// 校验考试规则是否设置
		TraRuleVO rule = traRuleDao.getTraRuleByCoursewareId(coursewareId);
		if (null == rule) {
			throw new TrainBizException("课件的考试规则还未设置，请先设置考试规则");
		}

		// 组织学习进度List
		List<TraLearningProgressVO> traLearningProgressList = getTraLearningProgressList(userId, conditionType, idList, flag,
				coursewareId);

		// 批量添加学习进度
		this.batchInsertTraLearningProgresss(traLearningProgressList);

		return 1;
	}

	/**
	 * @Description 批量添加学习进度
	 * @param traLearningProgressList
	 * @throws TrainBizException
	 * @throws
	 */
	private void batchInsertTraLearningProgresss(List<TraLearningProgressVO> traLearningProgressList) throws TrainBizException {
		if (null != traLearningProgressList && 0 < traLearningProgressList.size()) {
			Map<String, Object> traLearningProgressVO = new HashMap<String, Object>();
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_ID, Constant.NOT_NULL); // 进度ID
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_STUDENT_ID, Constant.NOT_NULL); // 学生ID
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_LEARNING_PROGRESS_TYPE, Constant.NOT_NULL); // 学习进度类型
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_COURSE_ID, Constant.NOT_NULL); // 课程ID
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_COURSEWARE_ID, Constant.NOT_NULL); // 课件ID
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_COURSE_STATUS, Constant.NOT_NULL); // 课程状态
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_COURSEWARE_STATUS, Constant.NOT_NULL); // 课件状态
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_COURSE_END_TIME, Constant.NOT_NULL); // 课程截止时间
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_COURSEWARE_END_TIME, Constant.NOT_NULL); // 课件截止时间
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_PROGRESS_TYPE, Constant.NOT_NULL); // 学习进度
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_REMARK, Constant.NOT_NULL); // 备注
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_INSERT_BY, Constant.NOT_NULL); // 创建人
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_INSERT_TIME, Constant.NOT_NULL); // 创建时间
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_UPDATE_BY, Constant.NOT_NULL); // 最后修改人
			traLearningProgressVO.put(TraLearningProgressVO.COLUMN_UPDATE_TIME, Constant.NOT_NULL); // 最后修改时间
			traLearningProgressVO.put("list", traLearningProgressList);
			int count = traLearningProgressDao.batchInsertTraLearningProgresss(traLearningProgressVO);
			log.info("批量发起学习共" + traLearningProgressList.size() + "条，学习进度列表：");
			log.info(traLearningProgressList.toString());
			if (1 > count) {
				throw new TrainBizException("批量添加学习进度失败");
			}
		}
	}

	/**
	 * @Description 组织学习进度List
	 * @param userId
	 * @param conditionType
	 * @param idList
	 * @param flag
	 * @param coursewareId
	 * @return
	 * @throws TrainBizException
	 * @throws
	 */
	private List<TraLearningProgressVO> getTraLearningProgressList(Integer userId, String conditionType, List<Integer> idList,
			String flag, Integer coursewareId) throws TrainBizException {
		List<TraLearningProgressVO> traLearningProgressList = new ArrayList<TraLearningProgressVO>();
		if ("1".equals(conditionType)) { // 按照部门
			// 根据部门ID列表查询老员工列表
			List<SysUserVO> userList = this.sysUserDao.getSysUserByGroupIdList(idList);
			for (SysUserVO sysUserVO : userList) {
				Integer studentId = sysUserVO.getId();
				traLearningProgressList = this.singleStudentBeginLearning(userId, flag, coursewareId, studentId,
						traLearningProgressList);
			}

		} else if ("2".equals(conditionType)) {// 按照职位
			// 根据职位ID列表查询老员工列表
			List<SysUserVO> userList = this.sysUserDao.getSysUserByPositionIdList(idList);
			for (SysUserVO sysUserVO : userList) {
				Integer studentId = sysUserVO.getId();
				traLearningProgressList = this.singleStudentBeginLearning(userId, flag, coursewareId, studentId,
						traLearningProgressList);
			}

		} else if ("3".equals(conditionType)) { // 按照员工
			for (Integer studentId : idList) {
				traLearningProgressList = this.singleStudentBeginLearning(userId, flag, coursewareId, studentId,
						traLearningProgressList);
			}
		} else {
			throw new TrainBizException("条件类型异常");
		}
		return traLearningProgressList;
	}

	/**
	 * @Description 发起学习，校验入参有效性
	 * @param conditionType
	 * @param idList
	 * @param flag
	 * @param coursewareId
	 * @throws TrainBizException
	 * @throws
	 */
	private void checkTermBeginPrams(String conditionType, List<Integer> idList, String flag, Integer coursewareId)
			throws TrainBizException {
		if (null == conditionType || 0 == conditionType.trim().length()) {
			throw new TrainBizException("[条件类型conditionType]不能为空");
		}

		if (null == idList || 0 == idList.size()) {
			throw new TrainBizException("请选择发起学习的ID列表");
		}

		if (null == flag || 0 == flag.trim().length()) {
			throw new TrainBizException("[是否排除已学习过的学员标志flag]不能为空");
		}

		if (null == coursewareId) {
			throw new TrainBizException("[课件ID coursewareId]不能为空");
		}

		TraCoursewareVO traCourseware = this.traCoursewareDao.getTraCoursewareById(coursewareId);
		if (null == traCourseware || "0".equals(traCourseware.getIsEnable())) {
			throw new TrainBizException("未查询到课件信息");
		}
	}

	/**
	 * @Description 单个学生开始学习前校验数据，并组织学习进度VO数据
	 * @param userId
	 * @param flag
	 * @param coursewareId
	 * @param studentId
	 * @param traLearningProgressList
	 * @throws TrainBizException
	 * @throws
	 */
	private List<TraLearningProgressVO> singleStudentBeginLearning(Integer userId, String flag, Integer coursewareId,
			Integer studentId, List<TraLearningProgressVO> traLearningProgressList) throws TrainBizException {
		TraLearningProgressVO traLearningProgressVO = null;

		// 校验用户ID的有效性
		SysUserVO sysUser = sysUserDao.getSysUserById(studentId);
		if (null == sysUser) {// 无效用户的略过
			log.info("[" + userId + "]无效用户的略过");
			return traLearningProgressList;
		} else if ("0".equals(sysUser.getIsFormal())) {// 新学员略过
			log.info("[" + userId + "]新学员略过");
			return traLearningProgressList;
		}

		// 根据员工ID和课件ID查询，历史中毕业的学习进度个数
		Map<String, Object> studiedCountInput = new HashMap<String, Object>();
		studiedCountInput.put("coursewareId", coursewareId);
		studiedCountInput.put("studentId", studentId);
		int count = this.traLearningProgressDao
				.getStudiedCountOfTraLearningProgresssByCoursewareIdAndStudentId(studiedCountInput);// 该学生该课件已毕业的学习进度个数
		boolean isStudied = count > 0 ? true : false;// 是否学习过

		// 根据员工ID和课件ID查询，当前正在学习的学习进度个数，如果正在学该课件，则不创建新的学习进度
		Map<String, Object> learningCountInput = new HashMap<String, Object>();
		learningCountInput.put("coursewareId", coursewareId);
		learningCountInput.put("studentId", studentId);
		count = this.traLearningProgressDao.getCountOfTraLearningProgresssByCoursewareIdAndStudentId(learningCountInput);// 该学生该课件正在学习的进度个数
		boolean isStudying = count > 0 ? true : false;// 是否正在学习

		if ("0".equals(flag)) {// 0-不排除历史学习过
			if (!isStudying) {
				traLearningProgressVO = this.getTraLearningProgressOfOldStaff(userId, coursewareId, studentId);
			} else {
				log.info("[" + studentId + "]正在学习该课件，不重新发起学习");
			}
		} else if ("1".equals(flag)) {// 1-排除历史学习过
			if (!isStudying && !isStudied) {
				traLearningProgressVO = this.getTraLearningProgressOfOldStaff(userId, coursewareId, studentId);
			} else if (isStudying) {
				log.info("[" + studentId + "]正在学习该课件，不重新发起学习");
			} else if (isStudied) {
				log.info("[" + studentId + "]已有学习历史，不重新发起学习");
			}
		}

		if (null != traLearningProgressVO) {
			traLearningProgressList.add(traLearningProgressVO);
		}

		return traLearningProgressList;
	}

	/**
	 * @Description 组织学习进度数据
	 * @param userId
	 * @param coursewareId
	 * @param studentId
	 * @throws TrainBizException
	 * @throws
	 */
	private TraLearningProgressVO getTraLearningProgressOfOldStaff(Integer userId, Integer coursewareId, Integer studentId)
			throws TrainBizException {
		TraCoursewareVO coursewareVO = this.traCoursewareDao.getTraCoursewareById(coursewareId);
		Float coursewareTimelimit = coursewareVO.getTimeLimit().floatValue();

		// 组织学习进度数据
		TraLearningProgressVO lp = new TraLearningProgressVO();
		lp.setStudentId(studentId);// 学生ID
		lp.setLearningProgressType("1");// 学习进度类型，0-新学员，1-老学员
		lp.setCourseId(null);// 课程ID
		lp.setCoursewareId(coursewareId);// 课件ID
		lp.setCourseStatus("1");// 课程状态
		lp.setCoursewareStatus("1");// 课件状态
		lp.setCourseEndTime(this.calendarService.afterHours(coursewareTimelimit / 60.0f));// 课程截止时间
		lp.setCoursewareEndTime(this.calendarService.afterHours(coursewareTimelimit / 60.0f));// 课件截止时间
		lp.setProgressType("0");// 学习进度
		lp.setRemark(null);// 备注
		lp.setInsertBy(userId);// 创建人
		lp.setInsertTime(new Date());// 创建时间
		lp.setUpdateBy(userId);// 最后修改人
		lp.setUpdateTime(new Date());// 最后修改时间

		return lp;
	}

	/**
	 * @Title queryOldStaffList
	 * @Description 学员管理-列表，根据姓名、状态查询学员信息，学习状态显示最近的一次学习进度（除了异常结束的）
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.OldStaffManagementService#queryOldStaffList(java.util.Map)
	 */
	@Override
	public Map<String, Object> queryOldStaffList(Map<String, Object> params) {
		Integer pageSize = (Integer) params.get("pageSize");
		Integer pageNumber = (Integer) params.get("pageNumber");
		PageHelper.startPage(pageNumber, pageSize, true);// 分页

		Integer coursewareId = (Integer) params.get("coursewareId");
		if (null == coursewareId) {
			throw new TrainBizException("课件ID不能为空");
		}

		// 查询老学员信息列表
		Page<Map<String, Object>> list = this.traTeacherStudentDao.queryOldStaffList(params);
		for (Map<String, Object> map : list) {
			Integer learningProgressId = (Integer) map.get("id");
			Integer cid = (Integer) map.get("courseware_id");
			String courseStatus = (String) map.get("course_status");

			// 根据学习进度ID查询学习次数，考试次数，和最好成绩
			Map<String, Object> countAndBestScoreMap = this.traLearningProgressDao.getCountAndBestScore(learningProgressId, cid);
			Object studyCount = countAndBestScoreMap.get("studycount");
			Object examCount = countAndBestScoreMap.get("examcount");
			Object bestScore = countAndBestScoreMap.get("bestscore");

			StringBuffer sb = new StringBuffer();
			if ("3".equals(courseStatus)) {// 已超时
				sb.append("超时，未通过");
			} else {
				sb.append("学习").append(String.valueOf(studyCount)).append("次，");
				sb.append("考试").append(String.valueOf(examCount)).append("次，");
				sb.append("最好成绩").append(String.valueOf(bestScore)).append("分");
			}
			map.put("tip", sb.toString());
			map.put("learning_progress_id", learningProgressId);
		}

		// 根据课程状态、课件ID和用户名称查询记录数
		String userName = (String) params.get("userName");
		long toLearnSize = this.getCountByCourseStatusAndCourseWareId(coursewareId, "0", userName);
		long learningSize = this.getCountByCourseStatusAndCourseWareId(coursewareId, "1", userName);
		long graduationSize = this.getCountByCourseStatusAndCourseWareId(coursewareId, "2", userName);
		long timeOutSize = this.getCountByCourseStatusAndCourseWareId(coursewareId, "3", userName);
		long sumSize = this.getCountByCourseStatusAndCourseWareId(coursewareId, "", userName);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.ROWS, list.getResult());// 列表
		dataMap.put(Constant.TOTAL_SIZE, list.getTotal());// 全部记录数
		dataMap.put("toLearnSize", toLearnSize);// 未学习个数
		dataMap.put("learningSize", learningSize);// 学习中个数
		dataMap.put("timeOutSize", timeOutSize);// 超时个数
		dataMap.put("graduationSize", graduationSize);// 已毕业个数
		dataMap.put("sumSize", sumSize);// 总学员个数
		return dataMap;
	}

	/**
	 * @param userName
	 * @Description 根据课程状态、课件ID和用户名称查询记录数
	 * @param coursewareId
	 * @param courseStatus
	 * @return
	 * @throws
	 */
	private long getCountByCourseStatusAndCourseWareId(Integer coursewareId, String courseStatus, String userName) {
		Map<String, Object> input = new HashMap<String, Object>();
		input.put("courseStatus", courseStatus);// 0-未学习，1-正在学习，2-已毕业，3-已超时，4-异常终止
		input.put("coursewareId", coursewareId);
		input.put("userName", userName);
		List<Map<String, Object>> list = this.traLearningProgressDao.getCountByCourseStatusAndCourseWareId(input);
		return list.size();
	}

	/**
	 * @Title executeRemove
	 * @Description 移除学员
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.OldStaffManagementService#executeRemove(java.util.Map)
	 */
	@Override
	public int executeRemove(Map<String, Object> params) {
		SysUserVO user = (SysUserVO) params.get("user");
		Integer userId = user.getId();

		List<Integer> list = (List<Integer>) params.get("learningProgressIdList");
		if (null == list || 0 == list.size()) {
			throw new TrainBizException("学习进度ID列表不能为空");
		}

		// 批量移除学员，学习进度的课程状态修改为4-异常终止
		int count = traLearningProgressDao.batchUpdateTraLearningProgresssToExceptionEnding(list, userId);
		if (1 > count) {
			throw new TrainBizException("批量移除学员失败");
		}

		return 1;
	}

	/**
	 * @Title executeDelay
	 * @Description 延时学习
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.OldStaffManagementService#executeDelay(java.util.Map)
	 */
	@Override
	public int executeDelay(Map<String, Object> params) {
		SysUserVO user = (SysUserVO) params.get("user");
		Integer userId = user.getId();
		Long endDateL = Long.parseLong(String.valueOf(params.get("endDate")));
		Date date = new Date(endDateL);
		String endDateStr = DateUtil.format(date, "yyyy-MM-dd 23:59:59");

		// 校验日期不可早于今天
		Date endDate = this.checkEndTime(endDateStr);

		// 课程延时，修改课程截止时间
		int sum = this.updateCourseEndTime(params, endDate, userId);

		// 增加学生流水信息
		sum = this.saveTraStudentRecord(params, endDateStr, userId);
		return sum;
	}

	/**
	 * @Description 校验日期不可早于今天
	 * @param endDateStr
	 * @return
	 * @throws TrainBizException
	 * @throws
	 */
	private Date checkEndTime(String endDateStr) throws TrainBizException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDate = null;
		Date todayDate = null;
		try {
			endDate = sdf.parse(endDateStr);
			todayDate = new Date();
			if (endDate.compareTo(todayDate) < 0) {
				throw new TrainBizException("日期不可早于今天");
			}
		} catch (ParseException e) {
			throw new TrainBizException("日期格式错误");
		}
		return endDate;
	}

	/**
	 * @Description 课程延时，修改课程截止时间
	 * @param params
	 * @param endDate
	 * @param userId
	 * @throws TrainBizException
	 */
	private int updateCourseEndTime(Map<String, Object> params, Date endDate, Integer userId) throws TrainBizException {
		Integer id = (Integer) params.get("learningProgressId");
		Date date = new Date();

		TraLearningProgressVO vo = new TraLearningProgressVO();
		vo.setId(id);
		vo.setCourseEndTime(endDate);
		vo.setCoursewareEndTime(endDate);
		vo.setCourseStatus("1");
		vo.setUpdateBy(userId);
		vo.setUpdateTime(date);
		int sum = this.traLearningProgressDao.updateCourseEndTime(vo);
		if (0 > sum) {
			throw new TrainBizException("系统异常，延时学习失败");
		}
		return sum;
	}

	/**
	 * @Description 增加学生流水信息
	 * @param params
	 * @param endDateStr
	 * @param userId
	 * @return
	 * @throws
	 */
	private int saveTraStudentRecord(Map<String, Object> params, String endDateStr, Integer userId) {
		int sum = 0;
		Date date = new Date();
		Integer learningProgressId = (Integer) params.get("learningProgressId");

		TraLearningProgressVO traLearningProgress = traLearningProgressDao.getTraLearningProgressById(learningProgressId);
		if (null == traLearningProgress) {
			throw new TrainBizException("未查询到学员的学习进度");
		}

		Integer courseId = traLearningProgress.getCourseId();
		Integer coursewareId = traLearningProgress.getCoursewareId();
		Integer studentId = traLearningProgress.getStudentId();

		SysUserVO leader = sysUserDao.getSysUserById(userId);
		if (null == leader) {
			throw new TrainBizException("未查询到领导信息");
		}

		String leaderName = leader.getUserName();
		StringBuffer sb = new StringBuffer(128);
		sb.append("领导").append(leaderName).append(" 将学习时限延长至 ").append(endDateStr);

		TraStudentRecordVO record = new TraStudentRecordVO();
		record.setStudentId(studentId);// 学生ID
		record.setLearningProgressId(learningProgressId);
		record.setTeacherId(null);// 老师ID
		record.setCourseId(courseId);// 课程ID
		record.setCoursewareId(coursewareId);// 课件ID
		record.setOperatingType("3");// 操作类型
		record.setBeginTime(date);// 开始时间
		record.setEndTime(null);// 结束时间
		record.setDescription(sb.toString());// 描述
		record.setPaperId(null);// 试卷ID
		record.setIsTimeout("1");// 是否超时
		record.setInsertBy(userId);// 创建人
		record.setInsertTime(date);// 创建时间
		record.setUpdateBy(userId);// 最后修改人
		record.setUpdateTime(date);// 最后修改时间

		sum = this.traStudentRecordDao.saveTraStudentRecord(record);
		if (0 > sum) {
			throw new TrainBizException("系统异常，延时处理时，添加学生流水信息失败");
		}
		return sum;
	}

	/**
	 * @Title queryToDoListByLeaderId
	 * @Description 根据领导ID查询待办事项
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.OldStaffManagementService#queryToDoListCountByLeaderId(java.util.Map)
	 */
	@Override
	public Map<String, Object> queryToDoListByLeaderId(Map<String, Object> params) {
		Integer baseUserId = (Integer) params.get("baseUserId");
		if (null == baseUserId) {
			throw new TrainBizException("领导ID不能为空");
		}

		// 根据领导ID查询下属的信息
		List<Integer> userIdList = new ArrayList<Integer>();
		List<SysUserVO> userList = (List<SysUserVO>) sysGroupDao.getSubordinateEmployees(params);
		for (SysUserVO sysUserVO : userList) {
			Integer userId = sysUserVO.getId();
			userIdList.add(userId);
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		long count = 0;
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		if (null != userIdList && 0 < userIdList.size()) {
			Integer pageSize = (Integer) params.get("pageSize");
			Integer pageNumber = (Integer) params.get("pageNumber");
			PageHelper.startPage(pageNumber, pageSize, true);// 分页
			Page<Map<String, Object>> list = this.traStudentRecordDao.queryToDoListByUserIdList(userIdList);
			count = list.getTotal();
			rows = list.getResult();
		}
		dataMap.put(Constant.TOTAL_SIZE, count);
		dataMap.put(Constant.ROWS, rows);
		return dataMap;
	}

	/**
	 * @Title queryToDoListCountByLeaderId
	 * @Description
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.OldStaffManagementService#queryToDoListCountByLeaderId(java.util.Map)
	 */
	@Override
	public Map<String, Object> queryToDoListCountByLeaderId(Map<String, Object> params) {
		Integer baseUserId = (Integer) params.get("baseUserId");
		if (null == baseUserId) {
			throw new TrainBizException("领导ID不能为空");
		}

		// 根据领导ID查询下属的信息
		List<Integer> userIdList = new ArrayList<Integer>();
		List<SysUserVO> userList = (List<SysUserVO>) sysGroupDao.getSubordinateEmployees(params);
		for (SysUserVO sysUserVO : userList) {
			Integer userId = sysUserVO.getId();
			userIdList.add(userId);
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		int count = 0;
		if (null != userIdList && 0 < userIdList.size()) {
			count = this.traStudentRecordDao.queryToDoListCountByUserIdList(userIdList);
		}
		dataMap.put(Constant.TOTAL_SIZE, count);
		return dataMap;
	}
}
