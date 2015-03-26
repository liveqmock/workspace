package com.yazuo.erp.train.dao;

import com.yazuo.erp.train.JUnitBase;
import com.yazuo.erp.train.vo.TraExamDtlVO;
import com.yazuo.erp.train.vo.TraExamOptionVO;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author congshuanglong
 */
public class ExamOptionDaoTest extends JUnitBase {
    @Resource
    private TraExamOptionDao traExamOptionDao;

    @Resource
    private TraExamDtlDao traExamDtlDao;

    private TraExamDtlVO traExamDtlVO;

    @Before
    public void init() {
        TraExamDtlVO examDtlVO = new TraExamDtlVO();
        examDtlVO.setContent("雅座成立年");
        examDtlVO.setPaperId(1);
        examDtlVO.setRemark("remark");
        examDtlVO.setQuestionId(1);
        examDtlVO.setQuestionType("1");
        examDtlVO.setQuestionScore(new BigDecimal(10));
        examDtlVO.setInsertBy(1);
        examDtlVO.setInsertTime(new Date());
        examDtlVO.setUpdateBy(2);
        examDtlVO.setUpdateTime(new Date());
        examDtlVO.setIsSystemDetermine("1");
        this.traExamDtlDao.saveTraExamDtl(examDtlVO);
        this.traExamDtlVO = examDtlVO;
    }

    @Test
    public void testBatchInsertExamOptionVO() {
        String[] answers = new String[]{"1998", "2000", "2006", "2010"};
        List<TraExamOptionVO> examOptionVOs = new ArrayList<TraExamOptionVO>();
        for (int i = 0; i < 4; i++) {
            TraExamOptionVO examOptionVO = new TraExamOptionVO();
            examOptionVO.setOptionContent(answers[i]);
            examOptionVO.setExamDtlId(this.traExamDtlVO.getId());
            examOptionVO.setIsAnswer("1");
            examOptionVO.setInsertBy(1);
            examOptionVO.setInsertTime(new Date());
            examOptionVO.setUpdateBy(1);
            examOptionVO.setUpdateTime(new Date());
            examOptionVOs.add(examOptionVO);
        }
        int affectNum = this.traExamOptionDao.batchInsertExamOptionVO(examOptionVOs);
        Assert.assertEquals(examOptionVOs.size(),affectNum);
    }

    @After
    public void destroy() {
        this.traExamDtlDao.deleteTraExamDtl(this.traExamDtlVO.getId());
    }
}
