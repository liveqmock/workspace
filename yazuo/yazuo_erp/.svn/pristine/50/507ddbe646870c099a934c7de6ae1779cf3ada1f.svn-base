/**
 * @Description 老员工学习相关服务实现类
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

import com.yazuo.erp.interceptors.Page;
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
 * @Description 老员工学习相关服务实现类
 * @author gaoshan
 * @date 2014-10-09 下午6:44:38
 */
@Service
public class OldStaffServiceImpl implements OldStaffService {

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
	 * 职位-课程关系表
	 */
	@Resource
	private TraPositionCourseDao traPositionCourseDao;

	@Resource
	private TraCourseCoursewareDao traCourseCoursewareDao;

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
	 * @Title queryPosition
	 * @Description 查询有效的职位信息
	 * @param params
	 * @return
	 * @see com.yazuo.erp.train.service.StudentManagementService#queryPosition(java.util.Map)
	 */
	@Override
	public List<Map<String, Object>> queryPosition(Map<String, Object> params) {
		Integer courseId = (Integer) params.get("courseId");
		List<Map<String, Object>> list = this.sysPositionDao.queryPosition(courseId);
		return list;
	}
}
