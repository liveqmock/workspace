/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */

package com.yazuo.erp.train.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yazuo.erp.train.vo.TraCoursewareAttachmentVO;
/**
 * @Description TODO
 * @author erp team
 * @date 
 */


@Repository
public interface TraCoursewareAttachmentDao{
	
	/**
	 * 新增对象
	 */
	public int saveTraCoursewareAttachment (TraCoursewareAttachmentVO entity);
	
	/**
	 * 修改对象
	 */
	public int updateTraCoursewareAttachment (TraCoursewareAttachmentVO entity);
	
	/**
	 * 删除对象
	 */
	public int deleteTraCoursewareAttachment(Integer id);
	
	/**
	 * 通过主键查找对象
	 */
	public TraCoursewareAttachmentVO getTraCoursewareAttachmentById(Integer id);
	
	/**
	 * 返回所有返回所有满足条件的对象，返回对象list<Object>
	 */
	public List<TraCoursewareAttachmentVO> getTraCoursewareAttachments(Map<String, Object> map);
	
	/**
	 * 返回所有满足条件的对象，返回对象list<Map<String, Object>>
	 */
	public List<Map<String, Object>>  getTraCoursewareAttachmentsMap(Map<String, Object> map);
	
	/**
	 * 返回所有的对象,无查询条件
	 */
	public List<Map<String, Object>> getAllTraCoursewareAttachments();

	/**
	 * @Description 根据课件ID批量删除 tra_courseware_attachment 课件-附件关系表
	 * @param coursewareIdlist
	 * @throws 
	 */
	public int batchDeleteTraCoursewareAttachmentByCoursewareId(List<Integer> coursewareIdlist);
	

}
