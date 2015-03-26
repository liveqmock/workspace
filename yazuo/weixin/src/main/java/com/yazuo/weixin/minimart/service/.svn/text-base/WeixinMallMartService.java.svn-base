package com.yazuo.weixin.minimart.service;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import net.sf.json.JSONObject;

import com.yazuo.weixin.minimart.vo.WxMallGoods;
import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;
import com.yazuo.weixin.minimart.vo.WxMallOrder;
import com.yazuo.weixin.minimart.vo.WxMallOrderCoupon;
import com.yazuo.weixin.minimart.vo.WxMallOrderCouponState;
import com.yazuo.weixin.minimart.vo.WxMallRefundRecord;
import com.yazuo.weixin.minimart.vo.WxTemplate;

/**
* @ClassName WeixinMallMartService
* @Description  微信商城 业务接口
* @author sundongfeng@yazuo.com
* @date 2014-8-14 下午2:34:31
* @version 1.0
*/
public interface WeixinMallMartService {
	//插入订单
	public int insertMallOrder(WxMallOrder wxorder)throws Exception;
	//插入商户数据
	public boolean insertMerchantParam(WxMallMerchantDict dict)throws Exception;
	//插入订单券
	public void inserMallCouponInfo(final List<WxMallOrderCoupon> list)throws Exception; 
	//更新支付结果
	public int updatePayResultInfo(WxMallOrder wxorder) throws Exception;
	//查询商品库存
	public int selectRemainNum(Integer goosId); 
	//更新商品库存
	public boolean updateOrderGoodRemain(Integer num,Integer goosId)throws Exception; 
	//查询商户数据
	public WxMallMerchantDict queryMerchantParam(Integer brandid);
	//查询商城商品
	public List<WxMallGoods> queryMallGoods(Integer brandid,Integer categoryId);
	//查询每一个商品信息
	public WxMallGoods queryEachGood(Integer brandid,Integer goodCode);
	//查询用户订单
	public List<Map<String,Object>> queryPaySuccessOrders(String openId,Integer brandId,Integer source)throws Exception;
	//单个订单详情
	public Map<String,Object> queryPaySuccessSingleOrder( String outTradeNo,Integer brandId)throws Exception;
	//是否生成券
	public boolean queryHasOrderCoupon(String outTradeNo);
	//是否有券状态
	public boolean queryHasBindCouponState(String outTradeNo);
	//插入券状态	
	public int insertCouponState(WxMallOrderCouponState state)throws Exception;
	//更新券状态
	public int updateWeixin_pay_coupon_state(WxMallOrderCouponState state)throws Exception;
	//查询用户购买商品个数
	public int queryUserBuyCount(String weixinId,Integer brandId,boolean flag,Integer goodId);
	//插入积分消费时的订单
	public int insertIntegralMallOrder(WxMallOrder wxorder)throws Exception;
	//查询数据库是否有成功支付的订单
	public WxMallOrder queryIntegralMallOrder(String outTradeNo,Integer brandId);
	//更新积分消费订单
	public int updateJifenState(WxMallOrder wxorder)throws Exception;
	//查询商品对应券id
	public Integer queryGoodsCouponId(Long goodId);
	//更新退款原因
	public boolean updateDrawBackDesc(WxMallRefundRecord dict)throws Exception;
	//查询库存量
	public Map<String,Object> queryGoodInventory(Integer goosId);
	//更新发送短信字段
	public int updateSendSmsNum(Integer goosId)throws Exception;
	//发送模版消息
	public String sendTemplateMessage(String tempId,String json,String token)throws Exception;
	//获取access_token
	public String getAcessTokenByDB(WxMallMerchantDict dict)throws Exception;
	/**根据订单表的id取信息*/
	public WxMallOrder getMallOrderById(Integer id);
	
	/**更新收货信息*/
	public int updateMallOrderAddress (WxMallOrder order);
	/**插入时id不采用自增，方便拿到插入的id*/
	public int insertMallOrderLuckUse(WxMallOrder wxorder)throws Exception;
	//查询数据库订单信息
	public WxMallOrder queryMallOrder(String outTradeNo,Integer brandId);
	//查询模版信息
	public List<Map<String,Object>> queryTemplateParam(Integer brandid);
	//更新模版消息
	public void updateTemplate(List<WxTemplate> voList,Integer brandid)throws Exception;
	//更新商户参数
	public boolean updateMerchantParam(WxMallMerchantDict dict) throws Exception;
	//查询未使用的券
	public List<Map<String,Object>> queryRemainOrderCoupon(String outTradeNo,Integer brandId);
	//删除accesstoken
	public boolean deleteAccessToken(Integer brandid)throws Exception;
	//更新Token时间
	public boolean updateTokenExpireTime(Integer brandid,Integer expiresin)throws Exception;
	public int queryMallOrderCard(String outTradeNo) ;
	public int insertMallOrderCard(String orderNo,String cardNo)throws Exception;
	public WxMallOrder queryCardMallOrder(String outTradeNo,Integer brandId);
	public String queryAccessToken( String brandid);
	public void updateAccessToken(JSONObject dict);
	public WxMallMerchantDict queryMerchantParamByDB(Integer brandid);
	/**根据卡类型查卡图片路径的接口*/
	public List<WxMallGoods> getCardPictureUrlByCardType(List<WxMallGoods> mallList);
	public List<Map<String,Object>> queryRefundRecodeList(String outradeno);
	public boolean insertRefundRecord(WxMallRefundRecord rr)throws Exception;
	public JSONObject queryRefundState(JSONObject data);
	public JSONObject refundRequest(String url,String mch_id,String xml,WxMallMerchantDict dict)throws Exception;
	public String createSign(SortedMap<String, String> packageParams,String apiKey);
}
