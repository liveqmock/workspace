/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.*;

import com.yazuo.erp.train.vo.TraPptDtlVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

import org.springframework.stereotype.Repository;

@Repository
public interface TraPptDtlDao {

	/**
	 * 新增对象
	 * 
	 * @parameter entity
	 */
	public int saveTraPptDtl(TraPptDtlVO entity);

	/**
	 * 修改对象
	 * 
	 * @parameter entity
	 */
	public int updateTraPptDtl(TraPptDtlVO entity);

	/**
	 * 删除对象
	 * 
	 * @parameter id
	 */
	public int deleteTraPptDtl(Integer id);

	/**
	 * 通过主键查找对象
	 * 
	 * @parameter id
	 */
	public TraPptDtlVO getTraPptDtlById(Integer id);

	/**
	 * @parameter id
	 */
	public List<TraPptDtlVO> getAllTraPptDtls();

	/**
	 * @Description 根据试卷ID查找PPT的详情信息
	 * @param paperId
	 * @return
	 * @throws
	 */
	public List<Map<String, Object>> queryPptDtlByPaperId(Integer paperId);

	public int batchSaveTraPptDtl(List<TraPptDtlVO> list);

	public int batchDeleteTraPptDtlByPptIdAndPptDtlName(List<Map<String, Object>> list);

	/**
	 * @Description  根据PPT的ID查找PPT的详情信息
	 * @param pptId
	 * @return 
	 * @throws 
	 */
	public List<Map<String, Object>> getPptDtlByPptId(Integer pptId);


    /**
     * 通过pptId查询TraPptDtlVO列表
     * @param pptId
     * @return
     */
    public List<TraPptDtlVO> getPptDtlVOsByPptId(Integer pptId);

	/**
	 * @Description 根据课件ID批量删除 tra_ppt_dtl PPT详情
	 * @param coursewareIdlist
	 * @throws 
	 */
	public int batchDeleteTraPptDtlByCoursewareId(List<Integer> coursewareIdlist);

	/**
	 * @Description 根据试题ID列表批量删除 tra_ppt_dtl PPT详情
	 * @param questionIdList
	 * @throws 
	 */
	public int batchDeleteTraPptDtlByQuestionIdList(List<Integer> questionIdList); 
	
	/**
	 * @Description 根据课件ID列表查询PPT图片集
	 * @param coursewareIdlist
	 * @return
	 * @throws 
	 */
	public List<Map<String, Object>> queryPptDtlByCoursewareIdlist(List<Integer> coursewareIdlist);
	
	/**
	 * @Description 根据试题ID列表查询PPT图片集
	 * @param questionIdList
	 * @return
	 * @throws 
	 */
	public List<Map<String, Object>> queryPptDtlByQuestionIdList(List<Integer> questionIdList); 

}
