/** 
* @author kyy
* @version 创建时间：2015-3-4 上午11:30:13 
* 游戏类
*/ 
package com.yazuo.weixin.game.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;
import net.sf.json.JSONObject;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.game.service.GameService;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.SHA1;
import com.yazuo.weixin.util.StringUtil;

@Service
public class GameServiceImpl implements GameService {
	private Log weixinLog = LogFactory.getLog("weixin");
	
	@Resource
	private WeixinMallMartService weixinMallMartService;
	@Resource
	private MemcachedClient memcachedClient;
	
	@Override
	public Map<String, Object> queryWeixinSign(Integer brandId, String url) {
		// 取商户设置参数
		WxMallMerchantDict dict=weixinMallMartService.queryMerchantParam(brandId) ;
		String token = "";
		try {
			token = weixinMallMartService.getAcessTokenByDB(dict);
		} catch (Exception e) {
			weixinLog.info("获取token失败");
			e.printStackTrace();
		}
		// 随机字符串
		String noncestr = RandomStringUtils.randomAlphanumeric(16);
		// 时间
		String timestamp = String.valueOf(new Date().getTime()/1000);
		// 获取jsapi_ticket
		String finalTicket = getSignTicket(token, brandId);
		// 排序
		TreeMap<String, Object> tree = new TreeMap<String, Object>();
		tree.put("noncestr", noncestr);
		tree.put("timestamp", timestamp);
		tree.put("jsapi_ticket", finalTicket);
		tree.put("url", url);
		
		StringBuilder sb = new StringBuilder();
		for (String key : tree.keySet()) {
			sb.append(key).append("=").append(tree.get(key)).append("&");
		}
		String signParam = "";
		if (!StringUtil.isNullOrEmpty(sb.toString())) {
			signParam = sb.toString().substring(0, sb.toString().length()-1);
		}
		
		SHA1 sha1 = new SHA1();
		String sign = sha1.getDigestOfString(signParam.getBytes()); // 签名
		weixinLog.info("游戏获取地理位置的签名值："+sign);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("timestamp", timestamp);
		resultMap.put("noncestr", noncestr);
		resultMap.put("appId", dict.getAppId());
		resultMap.put("sign", sign);
		return resultMap;
	}
	
	
	@Override
	public List<Map<String, Object>> getGameListByMerchantId(Integer merchantId) {
		
		return null;
	}

	// 获取js_ticket
	private String getSignTicket(String token, Integer brandId) {
		String key = "weixin_sign_ticket_" + brandId;
		String jsTiketJson = "";
		try {
			jsTiketJson = memcachedClient.get(key);
		} catch (Exception e1) {
			weixinLog.info("缓存中取ticket数据失败");
			e1.printStackTrace();
		}
		
		if(StringUtil.isNullOrEmpty(jsTiketJson)){
			String url = "http://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+token;
			try {
				jsTiketJson = CommonUtil.postMessage(url);
			} catch (WeixinRuntimeException e) {
				e.printStackTrace();
			}
			JSONObject sjon = JSONObject.fromObject(jsTiketJson);
			String err_msg = sjon.getString("errmsg");
			if("ok".equals(err_msg)){
				jsTiketJson= sjon.getString("ticket");
				Long currentTime = System.currentTimeMillis();
				JSONObject ticketJson = new JSONObject();//生成accessToken入库
				ticketJson.put("ticket", jsTiketJson);
				ticketJson.put("expires_in", Integer.parseInt(Constant.EXPIRE_IN));
				ticketJson.put("brandId", brandId);
				ticketJson.put("createTime", currentTime);
				// 将数据放入缓存
				try {
					memcachedClient.delete(key);
					memcachedClient.set(key, ticketJson.getInt("expires_in"), ticketJson.toString());
				} catch (Exception e) {
					e.printStackTrace();
					weixinLog.info("商户【"+brandId+"】的ticket数据更新缓存失败");
				}
				weixinLog.info("js_ticket的值"+jsTiketJson);
				return jsTiketJson;
			}
		} else {
			JSONObject json = JSONObject.fromObject(jsTiketJson);
			int expiresIn = json.getInt("expires_in");
			String ticket = json.getString("ticket");
			Long createTime = json.getLong("createTime");
			if((System.currentTimeMillis()-createTime)/1000>expiresIn){//过期重新生成，更新js_ticket
				String url = "http://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+token;
				try {
					jsTiketJson = CommonUtil.postMessage(url);
				} catch (WeixinRuntimeException e) {
					e.printStackTrace();
				}
				JSONObject sjon = JSONObject.fromObject(jsTiketJson);
				String err_msg = sjon.getString("errmsg");
				if("ok".equals(err_msg)){
					jsTiketJson= sjon.getString("ticket");
					Long currentTime = System.currentTimeMillis();
					JSONObject ticketJson = new JSONObject();//生成accessToken入库
					ticketJson.put("ticket", jsTiketJson);
					ticketJson.put("expires_in", Integer.parseInt(Constant.EXPIRE_IN));
					ticketJson.put("brandId", brandId);
					ticketJson.put("createTime", currentTime);
					// 将数据放入缓存
					try {
						memcachedClient.delete(key);
						memcachedClient.set(key, ticketJson.getInt("expires_in"), ticketJson.toString());
					} catch (Exception e) {
						e.printStackTrace();
						weixinLog.info("商户【"+brandId+"】的ticket数据更新缓存失败");
					}
					weixinLog.info("js_ticket的值"+jsTiketJson);
					return jsTiketJson;
				}
			}else{
				weixinLog.info("js_ticket的值"+ticket);
				return ticket;
			}
		}
		return null;
	}
	
}
