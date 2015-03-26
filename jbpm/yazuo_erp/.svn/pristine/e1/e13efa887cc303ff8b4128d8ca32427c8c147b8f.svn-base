package com.yazuo.erp.train.dao;

import com.yazuo.erp.train.JUnitBase;
import com.yazuo.erp.train.vo.CoursewareProgressVO;
import com.yazuo.erp.train.vo.TraCoursewareVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author congshuanglong
 */
public class CoursewareDaoTest extends JUnitBase {

    @Resource
    private TraCoursewareDao traCoursewareDao;
    private List<TraCoursewareVO> traCoursewareVOs = new ArrayList<TraCoursewareVO>();
    private List<Integer> traCoursewareIds = new ArrayList<Integer>();

    @Before
    public void init() {
        for (int i = 0; i < 10; i++) {
            TraCoursewareVO coursewareVO = new TraCoursewareVO();
            coursewareVO.setCoursewareName("雅座课件测试");
            coursewareVO.setSpeaker("Boss");
            coursewareVO.setHours(new BigDecimal(10.2));
            coursewareVO.setTimeLimit(new BigDecimal(20.5));
            coursewareVO.setIsEnable("1");
            coursewareVO.setAttachmentId(1);
            coursewareVO.setRemark("雅座remark" + UUID.randomUUID().toString());
            coursewareVO.setInsertBy(1);
            coursewareVO.setInsertTime(new Date());
            coursewareVO.setUpdateBy(2);
            coursewareVO.setUpdateTime(new Date());
            this.traCoursewareDao.saveTraCourseware(coursewareVO);
            this.traCoursewareVOs.add(coursewareVO);
        }

        for (TraCoursewareVO courseware : this.traCoursewareVOs) {
            this.traCoursewareIds.add(courseware.getId());
        }
    }


    @Test
    public void testSaveTraCourseware() {
        TraCoursewareVO coursewareVO = new TraCoursewareVO();
        coursewareVO.setCoursewareName("雅座课件测试");
        coursewareVO.setSpeaker("Boss");
        coursewareVO.setHours(new BigDecimal(10.2));
        coursewareVO.setTimeLimit(new BigDecimal(20.5));
        coursewareVO.setIsEnable("1");
        coursewareVO.setAttachmentId(1);
        coursewareVO.setRemark("雅座remark");
        coursewareVO.setInsertBy(1);
        coursewareVO.setInsertTime(new Date());
        coursewareVO.setUpdateBy(2);
        coursewareVO.setUpdateTime(new Date());
        assertEquals(1, this.traCoursewareDao.saveTraCourseware(coursewareVO));
        assertNotNull(coursewareVO.getId());
    }

    @Test
    public void testGetTraCoursewareByIds() {
        List<TraCoursewareVO> coursewareVOs = this.traCoursewareDao.getTraCoursewaresByIds(this.traCoursewareIds);
        List<Integer> afterSearch = new ArrayList<Integer>();
        for (TraCoursewareVO coursewareVO : coursewareVOs) {
            afterSearch.add(coursewareVO.getId());
        }
        System.out.println(afterSearch);
        assertEquals(this.traCoursewareIds.size(), coursewareVOs.size());
        assertTrue(this.traCoursewareIds.equals(afterSearch));
    }

    @Test
    public void testBatchUpdateTraCourseware() {
        List<TraCoursewareVO> coursewareVOs = this.traCoursewareDao.getTraCoursewaresByIds(this.traCoursewareIds);
        for (TraCoursewareVO coursewareVO : coursewareVOs) {
            coursewareVO.setRemark(coursewareVO.getRemark() + "after batchupdate");
        }
        assertEquals(this.traCoursewareIds.size(), this.traCoursewareDao.batchUpdateTraCourseware(coursewareVOs));
    }

    @Test
    public void testGetTraCoursewaresByCourseId() {
        //TODO 为实现测试
        List<CoursewareProgressVO> coursewareProgressVOs = this.traCoursewareDao.getTraCoursewaresByCourseId(2);

    }
    @After
    public void destroy() {
        for (Integer coursewareId : this.traCoursewareIds) {
            this.traCoursewareDao.deleteTraCourseware(coursewareId);
        }
    }
}
