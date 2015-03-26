package com.yazuo.erp.train.dao;

import com.yazuo.erp.train.JUnitBase;
import com.yazuo.erp.train.vo.TraExamDtlVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * @author congshuanglong
 */
public class ExamDtlDaoTest extends JUnitBase {
    @Resource
    private TraExamDtlDao traExamDtlDao;

    private List<TraExamDtlVO> examDtlVOList = new ArrayList<TraExamDtlVO>();

    private List<Integer> examDtlIds = new ArrayList<Integer>();

    @Before
    public void init() {
            TraExamDtlVO examDtlVO;
        for (int i = 0; i < 5; i++) {
            examDtlVO = new TraExamDtlVO();
            examDtlVO.setPaperId(1);
            examDtlVO.setQuestionId(1);
            examDtlVO.setQuestionType("1");
            examDtlVO.setContent("核心价值观-exam-test");
            examDtlVO.setScore(new BigDecimal(56.3));
            examDtlVO.setQuestionScore(new BigDecimal(23.0));
            examDtlVO.setIsCorrect("0");
            examDtlVO.setIsSystemDetermine("1");
            examDtlVO.setPptId(1);
            examDtlVO.setUrl("http://erp.yazuo.com");
            examDtlVO.setRemark("备注");
            examDtlVO.setInsertBy(1);
            examDtlVO.setInsertTime(new Date());
            examDtlVO.setUpdateBy(1);
            examDtlVO.setUpdateTime(new Date());
            this.traExamDtlDao.saveTraExamDtl(examDtlVO);
            this.examDtlVOList.add(examDtlVO);
        }

        for (TraExamDtlVO vo : this.examDtlVOList) {
            this.examDtlIds.add(vo.getId());
        }
    }

    @Test
    public void testBatchInsertExamDtl() {
        List<TraExamDtlVO> examDtlVOs = new ArrayList<TraExamDtlVO>();
        TraExamDtlVO examDtlVO;
        for (int i = 0; i < 5; i++) {
            examDtlVO = new TraExamDtlVO();
            examDtlVO.setPaperId(1);
            examDtlVO.setQuestionId(1);
            examDtlVO.setQuestionType("1");
            examDtlVO.setContent("核心价值观");
            examDtlVO.setScore(new BigDecimal(56.3));
            examDtlVO.setIsCorrect("0");
            examDtlVO.setIsSystemDetermine("1");
            examDtlVO.setPptId(1);
            examDtlVO.setUrl("http://erp.yazuo.com");
            examDtlVO.setRemark("批量插入");
            examDtlVO.setInsertBy(1);
            examDtlVO.setInsertTime(new Date());
            examDtlVO.setUpdateBy(1);
            examDtlVO.setUpdateTime(new Date());
            examDtlVOs.add(examDtlVO);
        }
        int affectRowsNum = this.traExamDtlDao.batchInsertExamDtl(examDtlVOs);
        assertEquals(examDtlVOs.size(),affectRowsNum);
    }

    @Test
    public void testGetTraExamDtlVOsWithOptionsByIds() {
        List<TraExamDtlVO> examDtlVOs = this.traExamDtlDao.getTraExamDtlVOsWithOptionsByIds(this.examDtlIds);
        assertEquals(this.examDtlIds.size(),examDtlVOs.size());
    }

    @Test
    public void testBatchUpdateExamDtlVOs() {
        List<TraExamDtlVO> traExamDtlVOs = this.traExamDtlDao.getTraExamDtlVOsWithOptionsByIds(this.examDtlIds);
        for (TraExamDtlVO vo : traExamDtlVOs) {
            vo.setRemark("batch update");
        }
        if (this.traExamDtlDao.batchUpdateExamDtlVOs(traExamDtlVOs) <= 0) {
            fail("更新失败");
        }
    }

    @After
    public void destroy() {
        for (TraExamDtlVO vo : this.examDtlVOList) {
            this.traExamDtlDao.deleteTraExamDtl(vo.getId());
        }
    }
}
