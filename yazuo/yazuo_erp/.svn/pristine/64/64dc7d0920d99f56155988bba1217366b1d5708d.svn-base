/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.*;
import com.yazuo.erp.train.vo.TraFinalExamRuleVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

import org.springframework.stereotype.Repository;

@Repository
public interface TraFinalExamRuleDao {

	/**
	 * 新增对象
	 * 
	 * @parameter entity
	 */
	public int saveTraFinalExamRule(TraFinalExamRuleVO entity);

	/**
	 * 修改对象
	 * 
	 * @parameter entity
	 */
	public int updateTraFinalExamRule(TraFinalExamRuleVO entity);

	/**
	 * 删除对象
	 * 
	 * @parameter id
	 */
	public int deleteTraFinalExamRule(Integer id);

	/**
	 * 通过主键查找对象
	 * 
	 * @parameter id
	 */
	public TraFinalExamRuleVO getTraFinalExamRuleById(Integer id);

	/**
	 * @parameter id
	 */
	public List<TraFinalExamRuleVO> getAllTraFinalExamRules();

	/**
	 * @Description 根据试卷ID查询期末考卷规则
	 * @param paperId
	 * @return
	 * @throws
	 */
	public TraFinalExamRuleVO queryTraFinalExamRuleByPaperId(Integer paperId);

	public Map<String, Object> getTraFinalExamRulesMap(Map<String, Object> map);

    /**
     * 通过课程ID得到期末考试规则
     * @param courseId
     * @return
     */
    public TraFinalExamRuleVO getFinalExamRuleByCourseId(Integer courseId);

    /**
     * 通过课程考卷ID得到考试规则
     * @param examPaperId
     * @return
     */
    public TraFinalExamRuleVO getFinalExamRuleByExamPaperId(Integer examPaperId);
    
	/**
	 * @Description 根据课程ID列表批量删除 tra_final_exam_rule 期末考卷规则
	 * @param list
	 * @throws 
	 */
	public int batchDeleteTraFinalExamRuleByCourseIds(List<Integer> list);

}
