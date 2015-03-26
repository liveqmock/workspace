/** 
* @author kyy
* @version 创建时间：2015-3-4 上午11:30:43 
* 游戏类
*/ 
package com.yazuo.weixin.game.service;

import java.util.List;
import java.util.Map;


public interface GameService {

	/**取微信签名*/
	public Map<String, Object> queryWeixinSign(Integer brandId, String url);
	
	/**经纬度取门店信息*/
	
	/**根据门店id取相关设置的游戏*/
	public List<Map<String, Object>> getGameListByMerchantId(Integer merchantId);
}
