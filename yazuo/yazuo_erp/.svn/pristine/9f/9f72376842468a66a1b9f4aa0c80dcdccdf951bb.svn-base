package com.yazuo.erp.train.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.yazuo.erp.train.JUnitBase;
import com.yazuo.erp.train.vo.TraCourseVO;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author congshuanglong
 */
public class CourseDaoTest extends JUnitBase {

	@Resource
	private TraCourseDao courseDao;

	@Test
	public void testSaveSysRole() {
		TraCourseVO course = new TraCourseVO();
		course.setCourseName("雅座入职-10");
		course.setIsEnable("1");
		course.setRemark("入职培训-100");
		course.setTimeLimit(new BigDecimal(1));
		course.setInsertBy(1);
		course.setInsertTime(new Date());
		course.setUpdateBy(1);
		course.setUpdateTime(new Date());
		this.courseDao.saveTraCourse(course);
	}

	@Test
	public void testGetAllTraCourses() {
		TraCourseVO courseVO = this.courseDao.getTraCourseById(1);
		System.out.println(courseVO);
	}
}
