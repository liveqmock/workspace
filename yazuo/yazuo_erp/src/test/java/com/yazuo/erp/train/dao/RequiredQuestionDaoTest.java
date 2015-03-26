package com.yazuo.erp.train.dao;

import com.yazuo.erp.train.JUnitBase;
import com.yazuo.erp.train.vo.TraRequiredQuestionVO;
import com.yazuo.erp.train.vo.TraRuleVO;
import org.junit.After;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
public class RequiredQuestionDaoTest extends JUnitBase {

    @Resource
    private TraRequiredQuestionDao traRequiredQuestionDao;

    @Resource
    private TraRuleDao traRuleDao;

    private TraRuleVO ruleVO;
    private TraRuleVO otherRuleVO;

    @Before
    public void init() {
        ruleVO = new TraRuleVO();
        ruleVO.setCoursewareId(1);
        ruleVO.setPaperType("2");
        ruleVO.setIsShortAnswer("1");
        ruleVO.setTimeLimit("200");
        ruleVO.setRuleType("2");
        ruleVO.setPassingScore(new BigDecimal(100L));
        ruleVO.setInsertTime(new Date());
        ruleVO.setInsertBy(1);
        ruleVO.setUpdateTime(new Date());
        ruleVO.setUpdateBy(2);
        this.traRuleDao.saveTraRule(ruleVO);

        otherRuleVO = new TraRuleVO();
        otherRuleVO.setCoursewareId(2);
        otherRuleVO.setPaperType("2");
        otherRuleVO.setIsShortAnswer("1");
        otherRuleVO.setTimeLimit("200");
        otherRuleVO.setRuleType("2");
        otherRuleVO.setPassingScore(new BigDecimal(100L));
        otherRuleVO.setInsertTime(new Date());
        otherRuleVO.setInsertBy(1);
        otherRuleVO.setUpdateTime(new Date());
        otherRuleVO.setUpdateBy(2);
        this.traRuleDao.saveTraRule(otherRuleVO);
    }

    @Test
    public void testGetQuestionIdsByCoursewareId() {
        TraRequiredQuestionVO vo;
        List<Integer> includingIds = new ArrayList<Integer>();
        List<Integer> excludingIds = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            vo = new TraRequiredQuestionVO();
            vo.setQuestionId(1);
            vo.setRuleId(1);
            vo.setRuleId(i % 2 == 0 ? ruleVO.getId() : otherRuleVO.getId());

            this.traRequiredQuestionDao.saveTraRequiredQuestion(vo);
            if (i % 2 == 0) {
                includingIds.add(vo.getId());
            }else{
                excludingIds.add(vo.getId());
            }
        }
        List<Integer> questionIds = this.traRequiredQuestionDao.getQuestionIdsByCoursewareId(ruleVO.getId());
        for (Integer id : includingIds) {
            assertTrue(questionIds.contains(id));
        }
        for (Integer id : excludingIds) {
            assertFalse(questionIds.contains(id));
        }
    }

    @After
    public void destroy() {
        this.traRuleDao.deleteTraRuleById(ruleVO.getId());
    }
}
