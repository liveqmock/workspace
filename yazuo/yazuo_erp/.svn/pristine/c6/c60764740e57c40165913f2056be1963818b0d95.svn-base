package com.yazuo.erp.train.service;

import com.yazuo.erp.system.vo.SysUserVO;
import com.yazuo.erp.train.vo.CoursewareProgressVO;
import com.yazuo.erp.train.vo.TraCoursewareVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TraCoursewareService {
	public int saveCourseware(Map<String, Object> paramMap, HttpServletRequest request) throws IOException;

	public int updateCourseware(Map<String, Object> paramerMap, HttpServletRequest request);

	public int deleteCourseware(List<Integer> listIds, SysUserVO user,HttpServletRequest request); 

	public List<Map<String, Object>> getCourseware(String coursewareName);

	public List<Map<String, Object>> getCourseCourseware(String coursewareName, String courseName, int pageNumber, int pageSize);

	/**
	 * 得到一个课件的下一课件的ID
	 * 
	 * 
	 * @param courseId
	 * @param coursewareId
	 * @return
	 */
	Integer nextCoursewareId(Integer courseId, Integer coursewareId);

	/**
	 * 得到一个课件是否可用
	 * 
	 *
     * @param userId
     * @param courseId
     *@param coursewareId  @return
	 */
	boolean isStudyForCourseware(Integer userId, Integer courseId, Integer coursewareId);

	/**
	 * 得到学习课程信息，附带进度信息
	 * 
	 * 
	 * 
	 * @param studentId
	 * @return
	 */
	List<CoursewareProgressVO> getCoursewareProgresses(Integer studentId);

	/**
	 * 课程信息，附带历史
	 * 
	 * 
	 * @param userId
	 * @param courseId
	 * @param coursewareId
	 * @param learningProgressId 
	 * @param isOldStaff 
	 * @return
	 */
	CoursewareProgressVO getCoursewareWithHistories(Integer userId, Integer courseId, Integer coursewareId, Integer learningProgressId, Integer isOldStaff);

    /**
     * 是否为最后一节课
     * @param courseId
     * @param coursewareId
     * @return
     */
    boolean isLastCourseware(Integer courseId, Integer coursewareId);



	/**
	 * 根据课件ID查询所属课程
	 */
	public List<Map<String, Object>> getCourseByCoursewareId(Integer coursewareId);

	public Map<String, Object> getCoursewareInfoByCoursewareId(Map<String, Object> map);

	public Object uploadVideo(MultipartFile[] myfiles, HttpServletRequest request) throws IOException;

	/**
	 * @Description 老学员课件列表（除异常结束的课件）
	 * @param userId
	 * @return
	 * @return List<CoursewareProgressVO>
	 * @throws 
	 */
	public List<CoursewareProgressVO> getCoursewareProgressOfOldStaff(Integer userId);

	/**
	 * @Description 老学员历史课件列表
	 * @param userId
	 * @return
	 * @return List<CoursewareProgressVO>
	 * @throws 
	 */
	public List<CoursewareProgressVO> getHistoryCoursewareProgressOfOldStaff(Integer userId);
}
