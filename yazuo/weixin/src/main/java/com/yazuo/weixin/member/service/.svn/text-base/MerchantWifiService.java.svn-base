/**
 * <p>Project: weixin</p>
 * <p>Copyright:</p>
 * <p>Company: yazuo</p>
 * @author zc
 * @date 2014-10-17
 *
 ***************************************************
 * HISTORY:...
 ***************************************************
 */
package com.yazuo.weixin.member.service;

import java.util.List;
import java.util.Map;

import com.yazuo.weixin.es.vo.CardTypeVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @ClassName: MerchantWifiService
 * @Description: 处理wifi逻辑接口
 */
public interface MerchantWifiService {

	/**
	 * 根据手机号和brandId保存wifi入口的会员
	 */
	public Map<String, Object> connectWifiAddMember(Integer brandId, String phoneNo, String path);

	/**判断是否需要跳转推送页*/
	public boolean judgeRedirectPage(Integer brandId, Integer membershipId);
	
	/**推送短信及相关业务*/
	public void dealSms(String phoneNo, Integer brandId, Integer membershipId, String smsUrl);
	
	/**清空今天以前的数据*/
	public boolean clearData();
	
	/**获取微信id*/
	public String getWeixinId(String code, Integer brandId);
	
	/**判断是否为微信会员，不是即添加会员，是即跳portal页或会员中心*/
	public Map<String, Object> visitMemberByWifi(String param);
	
	/**取某个商户设置的卡推荐页卡类型id*/
	public List<Integer> getCardTypeIdsByMerchantId(Integer merchantId);
	
	/**根据brandId和券id取券信息*/
	public JSONArray getCoupons(String couponIdStr, Integer brandId);
	
	/**取某个商户自定义的广告页*/
	public String getCustomAdvert(Integer merchantId);
	
}
