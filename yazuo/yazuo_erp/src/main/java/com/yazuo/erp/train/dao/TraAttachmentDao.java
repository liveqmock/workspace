/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.*;

import com.yazuo.erp.train.vo.TraAttachmentVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


import org.springframework.stereotype.Repository;


@Repository
public interface TraAttachmentDao{
	
	/**
	 * 新增对象
	 * @parameter entity
	 */
	public int saveTraAttachment (TraAttachmentVO entity);
	
	/**
	 * 修改对象
	 * @parameter entity
	 */
	public int updateTraAttachment (TraAttachmentVO entity);
	
	/**
	 * 删除对象
	 * @parameter id
	 */
	public int deleteTraAttachment(Integer id);
	
	/**
	 * 通过主键查找对象
	 * @parameter id
	 */
	public TraAttachmentVO getTraAttachmentById(Integer id);
	
	/**
	 * @parameter id
	 */
	public List<TraAttachmentVO> getAllTraAttachments();

	/**
	 * @Description 根据课件ID批量删除 tra_attachment 附件
	 * @param coursewareIdlist
	 * @throws 
	 */
	public int batchDeleteTraAttachmentByCoursewareId(List<Integer> coursewareIdlist);

	/**
	 * @Description 根据课件ID列表查询出附件信息
	 * @param coursewareIdlist
	 * @return
	 * @throws 
	 */
	public List<Map<String, Object>> getAttachmentInfoByCoursewareIdList(List<Integer> coursewareIdlist);
    /**
     *
     * @param coursewareIdlist
     * @return
     */
    public List<TraAttachmentVO> getAttachmentVOsByIds(List<Integer> coursewareIdlist);
}
