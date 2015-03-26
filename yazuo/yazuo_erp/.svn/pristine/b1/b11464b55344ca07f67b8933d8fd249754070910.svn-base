/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.*;
import com.yazuo.erp.train.vo.TraExamOptionVO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TraExamOptionDao{
	
	/**
	 * 新增对象
	 * @parameter entity
	 */
	public int saveTraExamOption (TraExamOptionVO entity);
	
	/**
	 * 修改对象
	 * @parameter entity
	 */
	public int updateTraExamOption (TraExamOptionVO entity);
	
	/**
	 * 删除对象
	 * @parameter id
	 */
	public int deleteTraExamOption(Integer id);
	
	/**
	 * 通过主键查找对象
	 * @parameter id
	 */
	public TraExamOptionVO getTraExamOptionById(Integer id);

    /**
     * 通过主键查找对象
     * @parameter examDtlId
     */
    public List<TraExamOptionVO> getTraExamOptionByExamDtlIds(List<Integer> examDtlIds);

    /**
	 * @parameter id
	 */
	public List<TraExamOptionVO> getAllTraExamOptions();

    /**
     * 批量添加TraExamOptionVO
     * @param examOptionVOs
     * @returnk
     */
    public int batchInsertExamOptionVO(List<TraExamOptionVO> examOptionVOs);

    /**
     * 对选择的选项进行更新
     * @param selectedIds
     * @return
     */
    public int batchUpdateExamOptionForSelected(List<Integer> selectedIds);
    /**
     * 对未选择的选项进行更新
     * @param selectedIds
     * @return
     */
    public int batchUpdateExamOptionForNonSelected(List<Integer> selectedIds);

    public TraExamOptionVO getExamOptionVOByPaperIdAndpptId(@Param("examPaperId") Integer examPaperId, @Param("pptId") Integer pptId);

}
