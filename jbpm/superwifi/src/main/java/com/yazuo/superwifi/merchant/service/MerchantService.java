/*
 * 文件名：MerchantService.java 版权：Copyright by www.yazuo.com 描述： 修改人：ququ 修改时间：2014年7月31日 跟踪单号： 修改单号： 修改内容：
 */

package com.yazuo.superwifi.merchant.service;


import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.multipart.MultipartFile;

import com.yazuo.superwifi.merchant.vo.HomePage;
import com.yazuo.superwifi.merchant.vo.MerchantInfo;
import com.yazuo.superwifi.util.JsonResult;
import com.yazuo.superwifi.util.StringList;


public interface MerchantService
{

    JSONArray getMerchantList(Integer merchantid)
        throws Exception;

    JsonResult updateConnetType(Map<String, Object> map)
        throws Exception;

    JsonResult getConnectType(Map<String, Object> paramMap)
        throws Exception;

    JsonResult updateAdminPassWord(Integer merchantId, String oldPassWord, String newPassWord)
        throws Exception;

    Boolean checkAdminPassWord(Integer merchantId, Integer brandId, String pasd)
        throws Exception;

    JsonResult updateHomePageInfo(Map<String, Object> map)
        throws Exception;

    JsonResult getHomePageInfo(Map<String, Object> map)
        throws Exception;

    Boolean isHavePassWord(Integer merchantId);

    JsonResult setShopTime(Map<String, Object> map)
        throws Exception;

    JsonResult getShopTime(Map<String, Object> map)
        throws Exception;

    JsonResult savePortalPic(Integer merchantId, Integer brandId, String myfile)
        throws Exception;

    JsonResult getPortalPicByMerchantId(Map<String, Object> map)
        throws Exception;

    String findAdminPassword(Integer merchantId, Integer brandId, String mobileNumber)
        throws Exception;

    JsonResult getCardTypeIds(Integer merchantId)
        throws Exception;

    JsonResult getCardTypeIdsFromCrm(Integer merchantId)
        throws Exception;

    /**
     * Description: <br> 运营平台为老商户开通雅座回头宝 Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param brandId
     * @param merchantId
     * @param macs
     * @param ssid1
     * @param ssid2
     * @param ssid3
     * @param userId
     * @param userName
     * @param userMobile
     * @param operator
     * @param operator  来源
     * @return
     * @throws Exception
     * @see
     */
    JsonResult addOrUpdateWifiProduct(Integer brandId, Integer merchantId, StringList macs, String ssid1,
                                                 String ssid2, String ssid3, String password1, String password2,
                                                 String password3, Integer userId, String operatorId,Integer source,Boolean isPassWordCheck,Integer merchantType)
        throws Exception;

    MerchantInfo getWifiProductInfomation(Integer merchantId)
        throws Exception;

    JsonResult uploadPic(MultipartFile file)
        throws Exception;

    /**
     * Description: <br> 为新商户开通雅座回头宝 Implement: <br> 1、…<br> 2、…<br>
     * 
     * @param merchantName
     * @param macs
     * @param ssid1
     * @param ssid2
     * @param ssid3
     * @param userName
     * @param userMobile
     * @param operator
     * @return
     * @throws Exception
     * @see
     */
    JsonResult addOrUpdateWifiProductForNewBrand(Integer merchantId,String merchantName, StringList macs, String ssid1, String ssid2,
                                         String ssid3, String password1, String password2, String password3,
                                         String userName, String mobileNumber, String operatorId,String operatorMobileNumber,
                                         Boolean isPassWordCheck,Integer merchantType)
        throws Exception;

    /**
     * Description: <br> 获取crm库中的merchant信息
     * 
     * @param merchantId
     * @return
     * @throws BadCredentialsException
     * @see
     */
    JSONObject getMerchantByMerchantId(Integer merchantId)
        throws BadCredentialsException;

    /**
     * Description: <br> 获取crm库中的merchantInfo信息
     * 
     * @param merchantId
     * @return
     * @throws BadCredentialsException
     * @see
     */
    JSONObject getMerchantInfoByMerchantId(Integer merchantId)
        throws BadCredentialsException;

    MerchantInfo getMerchantInfoByMid(Integer merchantId)
        throws Exception;

    HomePage getCustomizeHomepage(Integer merchantId)
        throws Exception;
    
    List<MerchantInfo> getMerchantListByOperatorId(String operatorId,Integer timeSortBy)
        throws Exception;
    List<MerchantInfo> getMerchantListBySearchKey(String operatorId, String searchKey)
        throws Exception;
    /**
     * 
     * Description:获取商户的类型，品牌、域、门店
     * Implement: <br>
     * 1、…<br>
     * 2、…<br>
     * 
     * @param merchantId
     * @return
     * @throws BadCredentialsException 
     * @see
     */
    Integer getMerchantTypeByMerchantId(Integer merchantId)
        throws BadCredentialsException;
    
    Boolean selectAuthForMerchant(Integer merchantId) throws Exception;
}
