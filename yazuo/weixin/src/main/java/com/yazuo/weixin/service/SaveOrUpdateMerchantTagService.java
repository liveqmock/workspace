package com.yazuo.weixin.service;

import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 添加或者修改商户标签信息
 * 
 * @author gaoshan
 * 
 */
public interface SaveOrUpdateMerchantTagService {

	/**
	 * 添加或者修改商户标签信息
	 * 
	 * @param merchantId
	 * @param id
	 * @param tagName
	 * @param tagType
	 * @return
	 * @throws Exception
	 */
	JSONObject saveOrUpdateMerchantTagService(Integer merchantId, Integer id, String tagName, String tagType) throws Exception;
	

}
