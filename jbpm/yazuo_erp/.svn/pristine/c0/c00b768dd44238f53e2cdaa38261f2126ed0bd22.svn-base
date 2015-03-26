package com.yazuo.erp.train.dao;

import com.yazuo.erp.train.JUnitBase;
import com.yazuo.erp.train.vo.TraCoursewareVO;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author congshuanglong
 */
public class ExamPaperDaoTest extends JUnitBase {

    @Resource
    private TraExamPaperDao traExamPaperDao;

    @Resource
    private TraCoursewareDao traCoursewareDao;

    private TraCoursewareVO traCoursewareVO;
    private TraCoursewareVO otherTraCoursewareVO;

    @Before
    public void init() {
        TraCoursewareVO coursewareVO = new TraCoursewareVO();
        coursewareVO.setCoursewareName("雅座入学考试");
        coursewareVO.setSpeaker("主讲人");
        coursewareVO.setAttachmentId(1);
        coursewareVO.setIsEnable("1");
        coursewareVO.setHours(new BigDecimal(1.0));
        coursewareVO.setRemark("remark");
        coursewareVO.setTimeLimit(new BigDecimal(3.0));
        coursewareVO.setInsertTime(new Date());
        coursewareVO.setInsertBy(1);
        coursewareVO.setUpdateTime(new Date());
        coursewareVO.setUpdateBy(1);
        this.traCoursewareDao.saveTraCourseware(coursewareVO);
        this.traCoursewareVO = coursewareVO;
        TraCoursewareVO otherCoursewareVO = new TraCoursewareVO();
        otherCoursewareVO.setCoursewareName("雅座入学考试");
        otherCoursewareVO.setSpeaker("主讲人");
        otherCoursewareVO.setAttachmentId(1);
        otherCoursewareVO.setIsEnable("1");
        otherCoursewareVO.setHours(new BigDecimal(1.0));
        otherCoursewareVO.setRemark("remark");
        otherCoursewareVO.setTimeLimit(new BigDecimal(3.0));
        otherCoursewareVO.setInsertTime(new Date());
        otherCoursewareVO.setInsertBy(1);
        otherCoursewareVO.setUpdateTime(new Date());
        otherCoursewareVO.setUpdateBy(1);
        this.traCoursewareDao.saveTraCourseware(otherCoursewareVO);
        this.otherTraCoursewareVO = otherCoursewareVO;
    }

    @Test
    public void testGetTraExamPapersByCoursewareIds() {
        List<Integer> includingCoursewareIds = new ArrayList<Integer>();
        List<Integer> excludeCoursewareIds = new ArrayList<Integer>();

        for (int i = 0; i < 5; i++) {
            TraExamPaperVO examPaperVO = new TraExamPaperVO();
            examPaperVO.setPaperName("考题");
            if (i % 2 != 0) {
                examPaperVO.setCoursewareId(this.traCoursewareVO.getId());
            } else {
                examPaperVO.setCoursewareId(this.otherTraCoursewareVO.getId());
            }
            examPaperVO.setLearningProgressId(2);
            examPaperVO.setBeginTime(new Date());
            examPaperVO.setExamPaperType("1");
            examPaperVO.setPaperStatus("1");
            examPaperVO.setStudentId(1);
            examPaperVO.setTeacherId(1);
            examPaperVO.setTotalScore(new BigDecimal(90));
            examPaperVO.setInsertBy(1);
            examPaperVO.setUpdateBy(1);
            examPaperVO.setInsertTime(new Date());
            examPaperVO.setUpdateTime(new Date());
            this.traExamPaperDao.saveTraExamPaper(examPaperVO);
            if (i % 2 != 0) {
                includingCoursewareIds.add(this.traCoursewareVO.getId());
            }else{
                excludeCoursewareIds.add(this.otherTraCoursewareVO.getId());
            }
        }
        List<TraExamPaperVO> examPaperVOs = this.traExamPaperDao.getTraExamPapersByCoursewareIds(1,Arrays.asList(this.traCoursewareVO.getId()),2);

        List<Integer> coursewareIds = new ArrayList<Integer>();
        for (TraExamPaperVO vo : examPaperVOs) {
            coursewareIds.add(vo.getCoursewareId());
        }

        for (Integer coursewareId : includingCoursewareIds) {
            Assert.assertTrue(coursewareIds.contains(coursewareId));
        }
        for (Integer coursewareId : excludeCoursewareIds) {
            Assert.assertFalse(coursewareIds.contains(coursewareId));
        }
    }

    @After
    public void destroy() {
        this.traCoursewareDao.deleteTraCourseware(this.traCoursewareVO.getId());
    }
}
