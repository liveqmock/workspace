package com.yazuo.erp.train.service;

import com.yazuo.erp.train.vo.TraCourseVO;

/**
 * 学生学习
 */
public interface StudentService {

    boolean hasCourse(Integer studentId);

    TraCourseVO getCourseByStudentId(Integer studentId);

    void executeStartStudy(Integer studentId, Integer courseId, Integer coursewareId, Integer learningProgressId, Integer isOldStaff); 

    void executeCompleteStudy(Integer studentId, Integer courseId, Integer coursewareId, Integer learningProgressId, Integer isOldStaff); 

    /**
     * 得到老师的名称
     * @param studentId
     * @return
     */
    String getTeacherName(Integer studentId);

    void synchronizeExpiredState();
}
