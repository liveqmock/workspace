package com.yazuo.erp.api.dao;

import com.yazuo.erp.api.vo.MonthlyStatVO;

import java.util.Date;

/**
 */
public interface ReportDataDao {
    double getOpenNumPercent(Integer brandId, Date month);

    /**
     * 得到会员的开台数
     * @param brandId
     * @param month
     * @return
     */
    int getAllOpenNum(Integer brandId, Date month);

    /**
     * 计算存储沉淀
     *
     * @param brandId
     * @param lastDay
     * @return
     */
    double getStoreBalance(Integer brandId, Date lastDay);

    MonthlyStatVO getStatForBrand(Integer brandId, Date from, Date to);
}
