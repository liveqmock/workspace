/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.*;
import com.yazuo.erp.train.vo.TraExamPaperVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TraExamPaperDao{
	
	/**
	 * 新增对象
	 * @parameter entity
	 */
	public int saveTraExamPaper (TraExamPaperVO entity);
	
	/**
	 * 修改对象
	 * @parameter entity
	 */
	public int updateTraExamPaper (TraExamPaperVO entity);
	
	/**
	 * 删除对象
	 * @parameter id
	 */
	public int deleteTraExamPaper(Integer id);
	
	/**
	 * 通过主键查找对象
	 * @parameter id
	 */
	public TraExamPaperVO getTraExamPaperById(Integer id);
	
	/**
	 * @parameter id
	 */
	public List<TraExamPaperVO> getAllTraExamPapers();

    /**
     * 考卷ID
     *
     * @param coursewareIds
     * @param integer 
     * @return
     */
    public List<TraExamPaperVO> getTraExamPapersByCoursewareIds(@Param("studentId") Integer studentId, @Param("coursewareIds") List<Integer> coursewareIds,@Param("learningProgressId") Integer learningProgressId);


    /**
     * 查询已经通过考试的试卷列表
     * @param studentId
     * @param courseId
     * @param coursewareid
     * @return
     */
    public List<TraExamPaperVO> getExamPapersForPassed(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId, @Param("coursewareId") Integer coursewareid);

    /**
     * 得列表
     * @param paperIds
     * @return
     */
    public List<TraExamPaperVO> getExamPaperVOsByIds(List<Integer> paperIds);

    /**
     * 查询笔试已经通过的期末考试试卷列表
     * @param studentId
     * @param courseId
     * @param integer 
     * @param coursewareid
     * @return
     */
    public List<TraExamPaperVO> getExamPapersForPassedForFinalExam(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId, @Param("learningProgressId") Integer learningProgressId);

	/**
	 * @Description 根据试卷ID查询试卷信息
	 * @param paperId
	 * @return
	 * @throws 
	 */
	public Map<String, Object> queryTraExamPaperById(Integer paperId);

}
