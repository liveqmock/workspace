package com.yazuo.erp.train.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yazuo.erp.base.JsonResult;
import com.yazuo.erp.system.dao.SysUserDao;
import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.dao.TraExamPaperDao;
import com.yazuo.erp.train.dao.TraLearningProgressDao;
import com.yazuo.erp.train.service.ExamPaperService;
import com.yazuo.erp.train.service.ExamService;
import com.yazuo.erp.train.service.TraCoursewareService;
import com.yazuo.erp.train.vo.TraExamDtlVO;
import com.yazuo.erp.train.vo.TraExamOptionVO;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import com.yazuo.erp.train.vo.TraLearningProgressVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ExamPaperServiceImpl implements ExamPaperService {
	@Resource
	private TraExamPaperDao examPaperDao;

	@Resource
	private TraLearningProgressDao learningProgressDao;

	@Resource
	private SysUserDao sysUserDao;

	@Override
	public TraExamPaperVO getExamPaperWithLearningProcessById(Integer examPaperId) {
		TraExamPaperVO examPaperVO = this.examPaperDao.getTraExamPaperById(examPaperId);
		TraLearningProgressVO learningProgressVO = this.learningProgressDao.getTraLearningProgressById(examPaperVO
				.getLearningProgressId());
		examPaperVO.setLearningProgressVO(learningProgressVO);
		return examPaperVO;
	}

}
