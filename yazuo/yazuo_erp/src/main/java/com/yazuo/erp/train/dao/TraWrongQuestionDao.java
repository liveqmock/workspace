/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.*;
import com.yazuo.erp.train.vo.TraWrongQuestionVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TraWrongQuestionDao{
	
	/**
	 * 新增对象
	 * @parameter entity
	 */
	public int saveTraWrongQuestion (TraWrongQuestionVO entity);
	
	/**
	 * 修改对象
	 * @parameter entity
	 */
	public int updateTraWrongQuestion (TraWrongQuestionVO entity);
	
	/**
	 * 删除对象
	 * @parameter id
	 */
	public int deleteTraWrongQuestion(Integer id);
	
	/**
	 * 通过主键查找对象
	 * @parameter id
	 */
	public TraWrongQuestionVO getTraWrongQuestionById(Integer id);
	
	/**
	 * @parameter id
	 */
	public List<TraWrongQuestionVO> getAllTraWrongQuestions();

    /**
     * 批量保存错题库
     * @param wrongQuestionVOs
     * @return
     */
    public void batchInsertWrongQuestions(List<TraWrongQuestionVO> wrongQuestionVOs);

    /**
     * 得到错误题的QuestionId
     *
     * @param studentId
     * @param courseId
     * @return
     */
    public List<Integer> getAllWrongQuestionIds(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

}
