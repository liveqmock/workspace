/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yazuo.erp.train.vo.TraCourseCoursewareVO;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

@Repository
public interface TraCourseCoursewareDao {

	/**
	 * 新增对象
	 * 
	 * @parameter entity
	 */
	public int saveTraCourseCourseware(TraCourseCoursewareVO entity);

	/**
	 * 修改对象
	 * 
	 * @parameter entity
	 */
	public int updateTraCourseCourseware(TraCourseCoursewareVO entity);

	/**
	 * 删除对象
	 * 
	 * @parameter id
	 */
	public int deleteTraCourseCourseware(Integer id);

	/**
	 * 通过主键查找对象
	 * 
	 * @parameter id
	 */
	public TraCourseCoursewareVO getTraCourseCoursewareById(Integer id);

	/**
	 * @parameter id
	 */
	public List<TraCourseCoursewareVO> getAllTraCourseCoursewares();

	/**
	 * 通过课程查找所有课件
	 */
	public long getTraCourseCoursewareCountByCourseId(@Param(value = "courseId") Integer courseId,
			@Param(value = "isRequired") String isRequired);

	/**
	 * 通过课程查询课件信息
	 * 
	 * @param courseId
	 */
	public List<Map<String, Object>> getTraCourseCoursewaresInfo(Map<String, Object> paramMap);

	public List<Map<String, Object>> getTraCourseCoursewaresMapByCourseName(Map<String, Object> paramMap);

	public List<Map<String, Object>> getTraCourseCoursewaresByName(Map<String, Object> paramMap);

	public List<Map<String, Object>> getTraCourseCoursewaresByCoursewareName(Map<String, Object> paramMap);

	public List<Map<String, Object>> getTraCourseCoursewaresAll(); 

	public int deleteCourseCoursewareByCourseId(Integer courseId);

	public int deleteCourseCoursewareByCoursewareId(Integer coursewareId);

	public int deleteCourseCoursewareByCourseIdAndCoursewareId(Integer courseId, Integer coursewareId);

	/**
	 * 
	 * @Description 根据课程ID批量删除课程与课件关系
	 * @param list
	 *            课程ID
	 * @return
	 * @return int
	 * @throws
	 */
	public int batchDeleteCourseCoursewareByCourseId(List<Map<String, Object>> list);

	/**
	 * 
	 * @Description 根据课件ID批量删除课程与课件关系
	 * @param list
	 * @return
	 * @return int
	 * @throws
	 */
	public int batchDeleteCourseCoursewareByCoursewareId(List<Integer> list);

	/**
	 * 
	 * @Description 批量保存课程课件关系
	 * @param list
	 * @return
	 * @return int
	 * @throws
	 */
	public int batchSaveCourseCourseware(List<TraCourseCoursewareVO> list);

	/**
	 *
	 */
	public int batchUpdateTraCourseCoursewareSort(List<TraCourseCoursewareVO> list);

	public int getTraCourseCoursewareCount(Map<String, Object> map);

	public List<Map<String, Object>> getCoursewareByCourseId(Map<String, Object> map);

	/**
	 * 通过courseId得到关联关系
	 * 
	 * @param courseId
	 * @return
	 */
	public List<TraCourseCoursewareVO> getCourseCoursewareByCourseId(Integer courseId);

	/**
	 * 得到TraCourseCoursewareVO
	 * 
	 * @param courseId
	 * @param coursewareId
	 * @return
	 */
	public TraCourseCoursewareVO getCourseCoursewareVO(@Param("courseId") Integer courseId,
			@Param("coursewareId") Integer coursewareId);
    /**
     * 选修课的下一节课
     *
     * @return
     */
    public TraCourseCoursewareVO nextCourseCoursewareVOForOptional(@Param("courseId") Integer courseId,
                                                        @Param("coursewareId") Integer coursewareId);

	/**
	 * 下一节课
	 * 
	 * @return
	 */
	public TraCourseCoursewareVO nextCourseCoursewareVO(@Param("courseId") Integer courseId,
			@Param("coursewareId") Integer coursewareId);

	/**
	 * 上一节课
	 * 
	 * @param courseId
	 * @param coursewareId
	 * @return
	 */
	public TraCourseCoursewareVO preCourseCoursewareVO(@Param("courseId") Integer courseId,
			@Param("coursewareId") Integer coursewareId);

	/**
	 * 是否为第一节课
	 * 
	 * @param courseId
	 * @param coursewareId
	 * @return
	 */
	public boolean isFirstForCourse(@Param("courseId") Integer courseId, @Param("coursewareId") Integer coursewareId);

	/**
	 * 是否为最后一节课
	 * 
	 * @param courseId
	 * @param coursewareId
	 * @return
	 */
	public boolean isLastForCourse(@Param("courseId") Integer courseId, @Param("coursewareId") Integer coursewareId);

	/**
	 * 根据课程ID查询包含的课件ID
	 * 
	 * @param courseId
	 * @return
	 * @throws
	 */
	public List<Integer> getCoursewareIdByCourseId(@Param("courseId") Integer courseId);

	/**
	 * 根基课件ID查询归属的课程ID
	 * 
	 * @param coursewareId
	 * @return
	 * @return List<Integer>
	 * @throws
	 */
	public List<Integer> getCourseIdByCoursewareId(@Param("coursewareId") Integer coursewareId);
}
