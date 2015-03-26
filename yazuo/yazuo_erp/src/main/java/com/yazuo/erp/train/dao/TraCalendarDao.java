package com.yazuo.erp.train.dao;

import com.yazuo.erp.train.vo.TraCalendarVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Repository
public interface TraCalendarDao {
    /**
     * 当前日期之后的第dayNum个工作日
     *
     * @param date
     * @param dayNum
     * @return
     */
    Date nthWorkdays(@Param("date") Date date, @Param("dayNum") Integer dayNum);

    int insertCalendarVO(TraCalendarVO traCalendarVO);

    int batchInsertCalendarVO(List<TraCalendarVO> calendarVOList);

    /**
     * 是否为工作日
     *
     * @param date
     * @return
     */
    boolean isWorkday(Date date);

    /**
     * 最大日期
     *
     * @return
     */
    Date maxDate();

    /**
     * date之前第dayNum个工作日
     * @param date 基础工作日
     * @param dayNum
     * @return
     */
    Date nthFormerWorkdays(@Param("date") Date date, @Param("dayNum") Integer dayNum);
}
