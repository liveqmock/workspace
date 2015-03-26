/**
 * @Description 组员管理相关服务实现类
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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.erp.base.Constant;
import com.yazuo.erp.interceptors.Page;
import com.yazuo.erp.interceptors.PageHelper;
import com.yazuo.erp.system.dao.SysGroupDao;
import com.yazuo.erp.system.dao.SysPositionDao;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.vo.SysPositionVO;
import com.yazuo.erp.system.vo.SysUserVO;
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
import com.yazuo.erp.train.service.OldStaffManagementService;
import com.yazuo.erp.train.service.OldStaffService;
import com.yazuo.erp.train.service.StudentManagementService;
import com.yazuo.erp.train.service.TeamMemberService;
import com.yazuo.erp.train.vo.CoursewareProgressVO;
import com.yazuo.erp.train.vo.TraCourseCoursewareVO;
import com.yazuo.erp.train.vo.TraCourseVO;
import com.yazuo.erp.train.vo.TraExamDtlVO;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import com.yazuo.erp.train.vo.TraFinalExamRuleVO;
import com.yazuo.erp.train.vo.TraLearningProgressVO;
import com.yazuo.erp.train.vo.TraPositionCourseVO;
import com.yazuo.erp.train.vo.TraRuleVO;
import com.yazuo.erp.train.vo.TraStudentRecordVO;
import com.yazuo.erp.train.vo.TraTeacherStudentVO;
import com.yazuo.util.DateUtil;

/**
 * @Description 组员管理相关服务实现类
 * @author gaoshan
 * @date 2014-10-09 下午6:44:38
 */
@Service
public class TeamMemberServiceImpl implements TeamMemberService {

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
	 * 课程课件关系表DAO
	 */
	@Resource
	private TraCourseCoursewareDao traCourseCoursewareDao;

	/**
	 * 组织架构表DAO
	 */
	@Resource
	private SysGroupDao sysGroupDao;

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
	 * @Title queryMemberList
	 * @Description 列表，领导查看下属的信息，并统计正在学习的课件数，根据姓名模糊查询
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.TeamMemberService#queryMemberList(java.util.Map)
	 */
	@Override
	public Map<String, Object> queryMemberList(Map<String, Object> params) {
		// 根据领导ID查询下属的信息
		List<SysUserVO> userList = (List<SysUserVO>) sysGroupDao.getSubordinateEmployees(params);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		long total = 0;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (null != userList && 0 < userList.size()) {
			Integer pageSize = (Integer) params.get("pageSize");
			Integer pageNumber = (Integer) params.get("pageNumber");
			PageHelper.startPage(pageNumber, pageSize, true);// 分页

			// 根据用户ID列表查询详细信息
			Page<Map<String, Object>> list = this.traTeacherStudentDao.queryMemberList(userList);
			for (Map<String, Object> map : list) {
				Integer studentId = (Integer) map.get("id");

				// 根据学生ID统计正在学习的课件数
				int count = this.traLearningProgressDao.getCoursewareCountByStudentId(studentId);
				map.put("coursewareCount", count);
			}
			total = list.getTotal();
			result = list.getResult();
		}

		dataMap.put(Constant.TOTAL_SIZE, total);
		dataMap.put(Constant.ROWS, result);

		return dataMap;
	}

	/**
	 * @Title queryLearningProcessList
	 * @Description 查看学习情况，根据用户ID查询基本信息，学习进度，包括异常结束的
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.TeamMemberService#queryLearningProcessList(java.util.Map)
	 */
	@Override
	public Map<String, Object> queryLearningProcessList(Map<String, Object> params) {
		Integer studentId = (Integer) params.get("studentId");
		if (null == studentId) {
			throw new TrainBizException("学生ID不能为空");
		}

		// 根据用户ID查询详细信息
		Map<String, Object> user = sysUserDao.getUserInfoById(studentId);
		if (null == user) {
			throw new TrainBizException("未查询到用户信息");
		}

		Integer pageSize = (Integer) params.get("pageSize");
		Integer pageNumber = (Integer) params.get("pageNumber");
		PageHelper.startPage(pageNumber, pageSize, true);// 分页

		// 根据用户ID查询基本信息，学习进度
		Page<Map<String, Object>> list = traLearningProgressDao.queryLearningProcessList(params);
		Map<String, Object> input = null;
		for (Map<String, Object> map : list) {
			Integer learningProgressId = (Integer) map.get("id");
			Integer coursewareId = (Integer) map.get("courseware_id");

			// 根据学习进度ID、课件ID、学生ID查询第一次学习的时间
			input = new HashMap<String, Object>();
			input.put("studentId", studentId);
			input.put("learningProgressId", learningProgressId);
			input.put("coursewareId", coursewareId);
			Map<String, Object> record = traStudentRecordDao.queryBeginTime(input);
			Object begin_time = null;
			if (null != record) {
				begin_time = record.get("begin_time");
			}

			TraRuleVO traRule = traRuleDao.getTraRuleByCoursewareId(coursewareId);
			if (null == traRule) {
				throw new TrainBizException("未查询到考卷规则");
			}
			String isTest = traRule.getIsTest();

			// 根据学习进度ID查询学习次数，考试次数，和最好成绩
			Map<String, Object> countAndBestScoreMap = this.traLearningProgressDao.getCountAndBestScore(learningProgressId,
					coursewareId);
			Object studyCount = countAndBestScoreMap.get("studycount");
			Object examCount = countAndBestScoreMap.get("examcount");
			Object bestScore = countAndBestScoreMap.get("bestscore");

			StringBuffer sb = new StringBuffer();
			sb.append("至今学习了").append(studyCount).append("次，");

			if (null != isTest && "0".equals(isTest)) {
				sb.append("无考试");
			}else{
				sb.append("考试").append(examCount).append("次");
			}

			map.put("begin_time", begin_time);
			map.put("bestScore", bestScore);
			map.put("tip", sb.toString());
			map.put("isTest", isTest);
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(Constant.TOTAL_SIZE, list.getTotal());
		dataMap.put(Constant.ROWS, list.getResult());
		dataMap.put("user", user);
		return dataMap;
	}

	/**
	 * @Title queryStudentRecordList
	 * @Description 查看历史，根据用户ID查询学习流水
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.TeamMemberService#queryStudentRecordList(java.util.Map)
	 */
	@Override
	public Page<Map<String, Object>> queryStudentRecordList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @Title updatePptPaper
	 * @Description 根据试卷ID进行PPT录音评分
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.TeamMemberService#updatePptPaper(java.util.Map)
	 */
	@Override
	public int updatePptPaper(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return 0;
	}

}
