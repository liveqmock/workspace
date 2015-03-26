/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yazuo.erp.train.vo.TraQuestionOptionVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

@Repository
public interface TraQuestionOptionDao {

	/**
	 * 新增对象
	 * 
	 * @parameter entity
	 */
	public int saveTraQuestionOption(TraQuestionOptionVO entity);

	/**
	 * 修改对象
	 * 
	 * @parameter entity
	 */
	public int updateTraQuestionOption(TraQuestionOptionVO entity);

	/**
	 * 删除对象
	 * 
	 * @parameter id
	 */
	public int deleteTraQuestionOption(Integer id);

	/**
	 * 通过主键查找对象
	 * 
	 * @parameter id
	 */
	public TraQuestionOptionVO getTraQuestionOptionById(Integer id);

	/**
	 * @parameter id
	 */
	public List<TraQuestionOptionVO> getAllTraQuestionOptions();

	/**
	 * 
	 * @param questionIds
	 * @return
	 */
	public List<TraQuestionOptionVO> getTraQuestionOptionsByQuestionIds(List<Integer> questionIds);

	/**
	 * 
	 * @Description 批量保存试题选项
	 * @param list
	 * @return
	 * @return int
	 * @throws
	 */
	public int batchSaveQuestionOption(List<TraQuestionOptionVO> list);

	/**
	 * 
	 * @Description 根据试题ID删除试题选项
	 * @param questionId
	 * @return
	 * @return int
	 * @throws
	 */
	public int deleteTraQuestionOptionByQuestionId(Integer questionId);

}
