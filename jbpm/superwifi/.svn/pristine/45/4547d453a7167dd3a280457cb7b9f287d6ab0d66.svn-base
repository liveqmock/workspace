package com.yazuo.api.service.merchant;


import java.util.List;

import com.yazuo.api.service.account.exception.AccountCheckedException;
import com.yazuo.api.service.exception.CrmCheckedException;


public interface MerchantWebService {
    /**
     * 添加或者修改门店信息， 如果是 新增需要添加集团， 非新增 修改门店信息
     * 
     * @param merchant
     */
    public MerchantVo addOrUpDateMerchant(MerchantVo merchant)
            throws CrmCheckedException, Exception;

    /**
     * 给商户授权
     * 
     * @param merchantId
     * @param productId
     * @param useTime
     *            使用时间当前时间 添加毫秒数据, 0: 表示永久有效
     * @throws AccountCheckedException
     * @throws Exception
     */
    public void accreditAuthForMerchant(int merchantId, int productId,
            long useTime) throws CrmCheckedException, Exception;

    /**
     * 查询商户所有的产品权限
     * 
     * @param merchantId
     * @throws CrmCheckedException
     * @throws Exception
     */
    public List<MerchantProductVo> selectAuthForMerchant(int merchantId)
            throws CrmCheckedException, Exception;

}
