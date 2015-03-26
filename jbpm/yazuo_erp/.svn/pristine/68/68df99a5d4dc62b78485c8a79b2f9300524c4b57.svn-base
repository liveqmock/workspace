package com.yazuo.erp.train.service;

import com.yazuo.erp.train.JUnitBase;
import com.yazuo.erp.train.dao.*;
import com.yazuo.erp.train.vo.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author congshuanglong
 */
public class CoursewareServiceTest extends JUnitBase {

    @Resource
    private TraCoursewareService traCoursewareService;

    @Resource
    private TraQuestionDao traQuestionDao;

    @Resource
    private TraQuestionOptionDao traQuestionOptionDao;

    @Resource
    private TraRuleDao traRuleDao;

    @Resource
    private TraCourseDao traCourseDao;

    @Resource
    private TraCoursewareDao traCoursewareDao;

    @Resource
    private TraAttachmentDao traAttachmentDao;

    @Resource
    private TraLearningProgressDao traLearningProgressDao;

    @Resource
    private CalendarService calendarService;

    @Resource
    private TraStudentRecordDao traStudentRecordDao;

    @Resource
    private TraCourseCoursewareDao traCourseCoursewareDao;

    @Resource
    private TraTeacherStudentDao traTeacherStudentDao;

//    @Before
    public void init() {
        Integer studentId = 1;
        Integer teacherId = 40;
        // 添加课程
        TraCourseVO course = new TraCourseVO();
        course.setCourseName("整体测试");
        course.setRemark("ERP培训系统整体测试");
        course.setIsEnable("1");
        course.setInsertBy(studentId);
        course.setInsertTime(new Date());
        course.setUpdateBy(studentId);
        course.setUpdateTime(new Date());
        course.setTimeLimit(new BigDecimal(2.0));

        TraTeacherStudentVO teacherStudentVO = new TraTeacherStudentVO();
        teacherStudentVO.setStudentId(studentId);
        teacherStudentVO.setTeacherId(teacherId);
        teacherStudentVO.setInsertBy(teacherId);
        teacherStudentVO.setInsertTime(new Date());
        teacherStudentVO.setIsEnable("1");
        this.traTeacherStudentDao.saveTraTeacherStudent(teacherStudentVO);

        this.traCourseDao.saveTraCourse(course);
        String[] names = new String[]{"雅座入职培训","价值观","CRM运营数据分析","运营数据提取"};
        for (int i = 0; i < 4; i++) {
            //添加课件
            TraCoursewareVO coursewareVO = new TraCoursewareVO();
            coursewareVO.setCoursewareName(names[i]);
            coursewareVO.setHours(new BigDecimal(1.0));
            coursewareVO.setIsEnable("1");
            coursewareVO.setSpeaker("spearker");
            coursewareVO.setTimeLimit(new BigDecimal(1.4));
            coursewareVO.setInsertBy(teacherId);
            coursewareVO.setInsertTime(new Date());
            coursewareVO.setUpdateBy(teacherId);
            coursewareVO.setUpdateTime(new Date());

            TraAttachmentVO attachmentVO = new TraAttachmentVO();
            attachmentVO.setAttachmentName(i + "附件名称.mp3");
            attachmentVO.setAttachmentPath("path");
            attachmentVO.setAttachmentSize("20M");
            attachmentVO.setAttachmentType("0");
            attachmentVO.setHours(new BigDecimal(2.0));
            attachmentVO.setRemark("注释");
            attachmentVO.setIsEnable("1");
            attachmentVO.setInsertBy(teacherId);
            attachmentVO.setInsertTime(new Date());
            attachmentVO.setUpdateBy(teacherId);
            attachmentVO.setUpdateTime(new Date());
            attachmentVO.setIsDownloadable("0");

            this.traAttachmentDao.saveTraAttachment(attachmentVO);
            coursewareVO.setAttachmentId(attachmentVO.getId());
            this.traCoursewareDao.saveTraCourseware(coursewareVO);

            if (i == 0) {
                // 添加进度
                TraLearningProgressVO learningProgressVO = new TraLearningProgressVO();
                learningProgressVO.setStudentId(studentId);
                learningProgressVO.setCourseId(course.getId());
                learningProgressVO.setCourseStatus("1");
                learningProgressVO.setCoursewareId(coursewareVO.getId());
                learningProgressVO.setCoursewareStatus("1");
                learningProgressVO.setInsertBy(studentId);
                learningProgressVO.setInsertTime(new Date());
                learningProgressVO.setUpdateBy(studentId);
                learningProgressVO.setUpdateTime(new Date());
                learningProgressVO.setProgressType("0");
                learningProgressVO.setCourseEndTime(this.calendarService.afterDays(10));
                learningProgressVO.setCoursewareEndTime(this.calendarService.afterHours(12));
                this.traLearningProgressDao.saveTraLearningProgress(learningProgressVO);
            }

            TraStudentRecordVO studentRecordVO = new TraStudentRecordVO();
            studentRecordVO.setCourseId(course.getId());
            studentRecordVO.setCoursewareId(coursewareVO.getId());
            studentRecordVO.setDescription("description");
            studentRecordVO.setOperatingType("0");
            studentRecordVO.setStudentId(studentId);
            studentRecordVO.setTeacherId(studentId);
            studentRecordVO.setBeginTime(new Date());
            studentRecordVO.setEndTime(new Date());
            studentRecordVO.setInsertBy(studentId);
            studentRecordVO.setInsertTime(new Date());
            studentRecordVO.setUpdateBy(studentId);
            studentRecordVO.setUpdateTime(new Date());
            studentRecordVO.setIsTimeout("1");
            this.traStudentRecordDao.saveTraStudentRecord(studentRecordVO);

            TraCourseCoursewareVO courseCoursewareVO = new TraCourseCoursewareVO();
            courseCoursewareVO.setCourseId(course.getId());
            courseCoursewareVO.setCoursewareId(coursewareVO.getId());
            courseCoursewareVO.setInsertBy(studentId);
            courseCoursewareVO.setInsertTime(new Date());
            courseCoursewareVO.setUpdateBy(studentId);
            courseCoursewareVO.setUpdateTime(new Date());
            courseCoursewareVO.setSort(i);
            courseCoursewareVO.setIsRequired(i % 2 == 0 ? "1" : "0");
            courseCoursewareVO.setRemark("remark");
            this.traCourseCoursewareDao.saveTraCourseCourseware(courseCoursewareVO);

            // 添加试题规则
            TraRuleVO ruleVO = new TraRuleVO();
            ruleVO.setCoursewareId(coursewareVO.getId());
            ruleVO.setPaperType("0");
            ruleVO.setRuleType("1");
            ruleVO.setTimeLimit("2.3");
            ruleVO.setIsShortAnswer("0");
            ruleVO.setPassingScore(new BigDecimal(69.0 + (i * Math.random())));
            ruleVO.setInsertBy(studentId);
            ruleVO.setInsertTime(new Date());
            ruleVO.setUpdateBy(studentId);
            ruleVO.setUpdateTime(new Date());
            ruleVO.setCoursewareId(coursewareVO.getId());
            this.traRuleDao.saveTraRule(ruleVO);

            for (int j = 0; j < 10; j++) {
                // 添加试题
                TraQuestionVO questionVO = new TraQuestionVO();
                questionVO.setCoursewareId(coursewareVO.getId());
                questionVO.setIsEnable("1");
                questionVO.setIsSystemDetermine("1");
                questionVO.setQuestion("雅座成立时间");
                questionVO.setQuestionType("0");
                questionVO.setScore(new BigDecimal(1.0));
                questionVO.setInsertBy(studentId);
                questionVO.setInsertTime(new Date());
                questionVO.setUpdateBy(studentId);
                questionVO.setUpdateTime(new Date());
                this.traQuestionDao.saveTraQuestion(questionVO);
                for (int k = 0; k < 2; k++) {
                    // 添加试题选项
                    TraQuestionOptionVO questionOptionVO = new TraQuestionOptionVO();
                    questionOptionVO.setOptionContent(String.valueOf(2006+i));
                    questionOptionVO.setQuestionId(questionVO.getId());
                    questionOptionVO.setIsRight("1");
                    questionOptionVO.setInsertBy(studentId);
                    questionOptionVO.setInsertTime(new Date());
                    questionOptionVO.setUpdateBy(studentId);
                    questionOptionVO.setUpdateTime(new Date());
                    this.traQuestionOptionDao.saveTraQuestionOption(questionOptionVO);
                }
            }
        }
    }

    @Test
    public void testGetCoursewareProgresses() {
        List<CoursewareProgressVO> coursewareProgressVOList = this.traCoursewareService.getCoursewareProgresses(1);
        System.out.println(coursewareProgressVOList);
    }
    @After
    public void destroy() {

    }
}
