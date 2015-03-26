package train;

import base.JUnitDaoBase;
import com.yazuo.erp.train.dao.TraCourseDao;
import com.yazuo.erp.train.service.StudentManagementService;
import com.yazuo.erp.train.vo.TraCourseVO;
import org.junit.Test;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class StudentManagementTest extends JUnitDaoBase {

	@Resource
	private StudentManagementService studentManagementService;

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
		//this.courseDao.saveTraCourse(course);
	}

	@Test
	public void testGetAllTraCourses() {
		//TraCourseVO courseVO = this.courseDao.getTraCourseById(1);
		//System.out.println(courseVO);
	}
}
