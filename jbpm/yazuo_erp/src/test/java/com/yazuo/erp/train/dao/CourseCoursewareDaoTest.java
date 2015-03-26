package com.yazuo.erp.train.dao;

import com.yazuo.erp.train.JUnitBase;
import com.yazuo.erp.train.vo.TraCourseCoursewareVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 *
 */
public class CourseCoursewareDaoTest extends JUnitBase {
    @Resource

    private TraCourseCoursewareDao traCourseCoursewareDao;

    @Before
    public void init() {

    }

    @Test
    public void test() {
        for (int i = 15; i <= 25; i++) {
            TraCourseCoursewareVO courseCoursewareVO = new TraCourseCoursewareVO();
            courseCoursewareVO.setCourseId(1);
            courseCoursewareVO.setCoursewareId(i);
            courseCoursewareVO.setSort(i);
            courseCoursewareVO.setIsRequired("1");
            courseCoursewareVO.setInsertBy(1);
            courseCoursewareVO.setInsertTime(new Date());
            courseCoursewareVO.setUpdateBy(1);
            courseCoursewareVO.setUpdateTime(new Date());
            this.traCourseCoursewareDao.saveTraCourseCourseware(courseCoursewareVO);
        }
    }



    @Test
    public void testNextCourseCoursewareVO(){

    }

    @After
    public void destroy() {

    }
}
