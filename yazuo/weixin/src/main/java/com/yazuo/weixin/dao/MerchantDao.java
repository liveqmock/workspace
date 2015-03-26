package com.yazuo.weixin.dao;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.StringUtil;

@Repository
public class MerchantDao {
	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(this.getClass());
	
	@Value("#{propertiesReader['getMerchantByMerchantId']}")
	private String getMerchantByMerchantId;

	/**
	 * 根据merchantId查询brandId
	 * @param merchantId
	 * @return
	 */
	public int queryBrandIdByMerchantId(Integer merchantId) {
		String input = "{\"merchantId\":"+merchantId+"}";
		String result = "";
		try {
			result = CommonUtil.postSendMessage(getMerchantByMerchantId, input, Constant.KEY+"");
		} catch (WeixinRuntimeException e) {
			e.printStackTrace();
		}
		if (!StringUtil.isNullOrEmpty(result)) {
			JSONObject jo = JSONObject.fromObject(result);
			if (jo !=null) {
				boolean success = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");
				if (success) {
					JSONObject merchantJson = jo.getJSONObject("data").getJSONObject("result").getJSONObject("merchants");
					return merchantJson.getInt("brandId");
				}
			}
		}
		return 0;
	}

}
