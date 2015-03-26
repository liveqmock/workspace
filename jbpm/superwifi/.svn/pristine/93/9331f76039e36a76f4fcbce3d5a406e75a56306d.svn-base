package com.yazuo.api.service.card;

import org.springframework.stereotype.Service;

import com.yazuo.api.service.merchant.MerchantVo;

@Service
public interface CardWebService {
    /**
     * 批量给商户开一定数量的卡， 如果该批次卡存在，自动叠加 这里开出来的卡都是无卡类型的
     * 
     * @param merchant
     * @param cardBatchType
     * @param size
     * @param flag
     * @return
     */
    public void createCardRecord(MerchantVo merchant, int cardBatchType,
            int size, boolean flag);
}
