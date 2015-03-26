package com.yazuo.erp.train.service;

import com.yazuo.erp.train.vo.TraExamDtlVO;
import com.yazuo.erp.train.vo.TraExamPaperVO;
import com.yazuo.erp.train.vo.TraPptVO;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * 试题服务类
 *
 * @author congshuanglong
 */
public interface ExamService {
    /**
     *
     * @param userId
     * @param courseId
     * @param coursewareId
     * @param learningProgressId 
     * @return
     */
    boolean isExamAvailable(Integer userId, Integer courseId, Integer coursewareId, Integer learningProgressId); 

    /**
     * 得到试题信息
     *
     *
     *
     * @param studentId
     * @param courseId
     *@param coursewareId  @return
     * @param learningProgressId 
     */
    TraExamPaperVO getExamPaper(Integer studentId, Integer courseId, Integer coursewareId, Integer learningProgressId); 

    /**
     *
     * @return
     */
    TraPptVO getPpt(Integer pptId);

    /**
     * 保存试卷信息
     * 返回学生成绩
     *
     * @param studentId
     * @param examDtlVOs
     */
    List<TraExamDtlVO> executeScoreExam(Integer studentId, List<TraExamDtlVO> examDtlVOs);

    /**
     * 得到期末考试信息
     *
     * @param studentId
     * @param courseId
     * @return
     */
    TraExamPaperVO getFinalExamPaper(Integer studentId,Integer courseId);

    /**
     * 保存考试信息
     *
     * @param studentId
     * @param examDtlVOs
     * @return
     */
    List<TraExamDtlVO> executeScoreFinalExam(Integer studentId, List<TraExamDtlVO> examDtlVOs);

    /**
     * 参数信息
     *
     * @param studentId
     * @param examPaperId
     * @param courseId
     * @param prefix
     * @param audioFile
     * @param hours
     */
    void saveAudio(Integer studentId, Integer examPaperId, Integer courseId, String prefix, MultipartFile audioFile, BigDecimal hours);

	/**
	 * @Description 开始考试
	 * @param studentId
	 * @param examPaperId
	 * @return void
	 * @throws 
	 */
	void executeStartExam(Integer studentId, Integer examPaperId);


}