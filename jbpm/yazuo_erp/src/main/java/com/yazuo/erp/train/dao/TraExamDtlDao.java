/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.yazuo.erp.train.dao;

import com.yazuo.erp.train.vo.TraExamDtlVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


@Repository
public interface TraExamDtlDao {

    /**
     * 新增对象
     *
     * @parameter entity
     */
    public int saveTraExamDtl(TraExamDtlVO entity);

    /**
     * 修改对象
     *
     * @parameter entity
     */
    public int updateTraExamDtl(TraExamDtlVO entity);

    /**
     * 删除对象
     *
     * @parameter id
     */
    public int deleteTraExamDtl(Integer id);

    /**
     * 通过主键查找对象
     *
     * @parameter id
     */
    public TraExamDtlVO getTraExamDtlById(Integer id);

    /**
     * @parameter id
     */
    public List<TraExamDtlVO> getAllTraExamDtls();

    /**
     * 批量插入TraExamDtl
     *
     * @param examDtlVOs
     * @return
     */
    public int batchInsertExamDtl(List<TraExamDtlVO> examDtlVOs);

    /**
     * 按ID查询TraExamDtl
     *
     * @param examDtlIds
     * @return
     */
    public List<TraExamDtlVO> getTraExamDtlVOsWithOptionsByIds(List<Integer> examDtlIds);

    /**
     * 批量更新ExamDtlVO
     *
     * @param examDtlVOs
     * @return
     */
    public int batchUpdateExamDtlVOs(List<TraExamDtlVO> examDtlVOs);

    /**
     * @param paperId
     * @return
     * @throws
     * @Description 根据试卷ID查询试卷题干
     */
    public List<TraExamDtlVO> queryTraExamDtlByPaperId(Integer paperId);

    /**
     * 按正确的ExamDtl的id，更新ExamDtlVO对象
     *
     * @param correctIds
     * @return
     */
    public int updateTraExamDtlForCorrect(List<Integer> correctIds);

    /**
     * 按错误的ExamDtl的id，更新ExamDtlVO对象
     *
     * @param wrongIds
     * @return
     */
    public int updateTraExamDtlForWrong(List<Integer> wrongIds);

    /**
     *
     * 通过ExamPaperId
     * @param examPaperId
     * @return
     */

    public List<TraExamDtlVO> getExamDtlVOs(Integer examPaperId);
}
