package com.yazuo.erp.train.dao;

import com.yazuo.erp.train.JUnitBase;
import com.yazuo.erp.train.vo.TraLearningProgressVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testng.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author congshuanglong
 */
public class LearningProcessDaoTest extends JUnitBase {
    @Resource
    private TraLearningProgressDao traLearningProgressDao;

    private TraLearningProgressVO learningProgressVO;
    @Before
    public void init() {
        TraLearningProgressVO learningProgressVO = new TraLearningProgressVO();
        learningProgressVO.setCourseId(1);
        learningProgressVO.setStudentId(2);
        learningProgressVO.setCourseStatus("1");
        learningProgressVO.setCoursewareId(2);//not null
        learningProgressVO.setCoursewareStatus("1");
        learningProgressVO.setProgressType("0");
        learningProgressVO.setInsertBy(1);
        learningProgressVO.setInsertTime(new Date());
        learningProgressVO.setUpdateBy(1);
        learningProgressVO.setUpdateTime(new Date());
        this.traLearningProgressDao.saveTraLearningProgress(learningProgressVO);
        this.learningProgressVO = learningProgressVO;
    }

    @Test
    public void testGetCourseIdByStudentId() {
        Integer course_id = this.traLearningProgressDao.getCourseIdByStudentId(this.learningProgressVO.getStudentId());
        Assert.assertEquals(this.learningProgressVO.getCourseId(),course_id);
    }

    @After
    public void destroy() {
        this.traLearningProgressDao.deleteTraLearningProgress(this.learningProgressVO.getId());
    }
}
