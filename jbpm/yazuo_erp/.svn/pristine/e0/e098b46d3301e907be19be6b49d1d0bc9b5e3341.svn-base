package com.yazuo.erp.train.service;

import com.yazuo.erp.train.JUnitBase;
import com.yazuo.erp.train.dao.TraExamDtlDao;
import com.yazuo.erp.train.dao.TraExamOptionDao;
import com.yazuo.erp.train.vo.TraExamDtlVO;
import com.yazuo.erp.train.vo.TraExamOptionVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author congshuanglong
 */
public class ExamServiceTest extends JUnitBase {

    @Resource
    private ExamService examService;

    @Resource
    private TraExamDtlDao traExamDtlDao;

    @Resource
    private TraExamOptionDao traExamOptionDao;

    @Before
    public void init() {

    }

    @Test
    public void testScoreExam() {
        List<TraExamDtlVO> examDtlVos = this.traExamDtlDao.getExamDtlVOs(43);
        List<TraExamOptionVO> allExamOptionVOs = this.traExamOptionDao.getAllTraExamOptions();
        for (TraExamDtlVO examDtlVO : examDtlVos) {
            for (TraExamOptionVO examOptionVO : allExamOptionVOs) {
                if (examOptionVO.getExamDtlId().equals(examDtlVO.getId())) {
                    examDtlVO.getExamOptionVOs().add(examOptionVO);
                }
                examOptionVO.setIsSelected(new Double(Math.random()).intValue() % 2 == 0 ? "1" : "0");
            }
        }
        this.examService.executeScoreExam(1, examDtlVos);
    }


    @After
    public void destroy() {

    }
}
