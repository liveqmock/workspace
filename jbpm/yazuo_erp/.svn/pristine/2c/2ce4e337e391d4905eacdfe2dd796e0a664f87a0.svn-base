/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.*;

import com.yazuo.erp.train.vo.TraPptVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TraPptDao{
	
	/**
	 * 新增对象
	 * @parameter entity
	 */
	public int saveTraPpt (TraPptVO entity);
	
	/**
	 * 修改对象
	 * @parameter entity
	 */
	public int updateTraPpt (TraPptVO entity);
	
	/**
	 * 删除对象
	 * @parameter id
	 */
	public int deleteTraPpt(Integer id);
	
	/**
	 * 通过主键查找对象
	 * @parameter id
	 */
	public TraPptVO getTraPptById(Integer id);
	
	/**
	 * @parameter id
	 */
	public List<TraPptVO> getAllTraPpts();
	
	/**
	 * @Description 根据试卷ID查找PPT基本信息
	 * @param paperId
	 * @return
	 * @throws 
	 */
	public Map<String, Object> queryPptInfoByPaperId(Integer paperId);

	/**
	 * @Description 根据试卷ID查找PPT的录音信息
	 * @param paperId
	 * @return
	 * @throws 
	 */
	public Map<String, Object> queryRecordingByPaperId(Integer paperId);
	
	public List<Map<String, Object>> getTraPptsMap(Map<String, Object> map);

    /**
     * 通过coursewareId得到TraPptVO
     * @param coursewareId
     * @return
     */
    public TraPptVO getPptVOByCoursewareId(Integer coursewareId);
    
    /**
     * @Description 修改PPT课件ID
     * @param entity
     * @return
     * @return int
     * @throws
     */
    public int updateTraPptCoursewareId (TraPptVO entity);

	/**
	 * @Description 根据课件ID批量删除 tra_ppt PPT
	 * @param coursewareIdlist
	 * @throws 
	 */
	public int batchDeleteTraPptByCoursewareId(List<Integer> coursewareIdlist);

	/**
	 * @Description 根据试题ID列表批量删除 tra_ppt PPT
	 * @param questionIdList
	 * @throws 
	 */
	public int batchDeleteTraPptByQuestionIdList(List<Integer> questionIdList);
}
