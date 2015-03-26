package com.yazuo.erp.api.dao;

import com.yazuo.erp.api.vo.CardTypeVO;

import java.util.Date;
import java.util.List;

/**
 */
public interface RenewCardTypeDao {
    List<Integer> getAllCardtypeIds(List<Integer> brandIds);

    /**
     * 最后一个卡批次(总数量，开卡日期，批次ID)
     *
     * @param cardTypeId
     * @return
     */
    CardTypeVO getCardType(Integer cardTypeId);

    /**
     * 未激活数量
     *
     * @param cardBatchId
     * @return
     */
    Integer getNoActivatedNum(Integer cardBatchId);

    /**
     * 发卡总量
     *
     * @param brandId
     * @param from
     * @param to
     * @return
     */
    Integer getNewCardNum(Integer brandId, Date from, Date to);
}
