package com.yazuo.weixin.minimart.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yazuo.weixin.es.service.MemberShipCenterService;
import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.minimart.dao.WxAccessTokenDao;
import com.yazuo.weixin.minimart.dao.WxMallGoodsDao;
import com.yazuo.weixin.minimart.dao.WxMallInventoryDao;
import com.yazuo.weixin.minimart.dao.WxMallMerchantDictDao;
import com.yazuo.weixin.minimart.dao.WxMallOrderCouponDao;
import com.yazuo.weixin.minimart.dao.WxMallOrderCouponStateDao;
import com.yazuo.weixin.minimart.dao.WxMallOrderDao;
import com.yazuo.weixin.minimart.dao.WxMallRefundRecordDao;
import com.yazuo.weixin.minimart.dao.WxTemplateDao;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallGoods;
import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;
import com.yazuo.weixin.minimart.vo.WxMallOrder;
import com.yazuo.weixin.minimart.vo.WxMallOrderCoupon;
import com.yazuo.weixin.minimart.vo.WxMallOrderCouponState;
import com.yazuo.weixin.minimart.vo.WxMallRefundRecord;
import com.yazuo.weixin.minimart.vo.WxTemplate;
import com.yazuo.weixin.refund.ClientResponseHandler;
import com.yazuo.weixin.refund.RequestHandler;
import com.yazuo.weixin.refund.TenpayHttpClient;
import com.yazuo.weixin.service.impl.AutoreplyData;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.MD5Util;
import com.yazuo.weixin.util.StringUtil;

/**
* @ClassName WeixinMallMartServiceImpl
* @Description 微信商城service
* @author sundongfeng@yazuo.com
* @date 2014-8-6 上午8:39:11
* @version 1.0
*/
@Service
public class WeixinMallMartServiceImpl implements WeixinMallMartService {
	private static final Log log = LogFactory.getLog("mall");
	@Autowired
	private WxMallOrderDao wxMallOrderDao;
	@Autowired
	private WxMallGoodsDao wxMallGoodsDao;
	@Autowired
	private WxMallMerchantDictDao wxMallMerchantDictDao;
	@Autowired
	private WxMallInventoryDao wxMallInventoryDao;
	@Autowired
	private WxMallOrderCouponDao wxMallOrderCouponDao;
	@Autowired
	private WxMallOrderCouponStateDao wxMallOrderCouponStateDao;
	@Autowired
	private WxAccessTokenDao wxAccessTokenDao;
	@Autowired
	private WxTemplateDao wxTemplateDao;
	@Autowired
	private MemcachedClient memcachedClient;
	@Resource
	private MemberShipCenterService memberShipCenterService;

	@Value("#{propertiesReader['getCardPictureByCardType']}")
	private String getCardPictureUrl; // 根据卡类型id取卡样图片路径
	@Value("#{propertiesReader['imageFileUrl']}")
	private String imageFileUrl; // 卡样图片地址
	@Value("#{propertiesReader['caFileIp']}")
	private String caFileIp;
	@Value("#{propertiesReader['certificateKeyUrl']}")
	private String certificateKeyUrl;
	@Value("#{propertiesReader['wxrefundquery']}")
	private String wxrefundquery;
	@Value("#{propertiesReader['wxrefundqueryv3']}")
	private String wxrefundqueryv3;
	@Autowired
	private WxMallRefundRecordDao wxMallRefundRecordDao;
	/**
	 * 插入订单
	 */
	@Transactional(rollbackFor=Exception.class)
	public int insertMallOrder(WxMallOrder wxorder)throws Exception{
		return wxMallOrderDao.insertMallOrder(wxorder);
	}
	@Transactional(rollbackFor=Exception.class)
	public synchronized int updatePayResultInfo(WxMallOrder wxorder) throws Exception {
		return wxMallOrderDao.updatePayResultInfo(wxorder);
	}
	/**
	 * 购买时判断库存
	 */
	public int selectRemainNum(Integer goosId) {
		return wxMallInventoryDao.selectRemainNum(goosId);
	}
	/**
	 * 查询商户参数
	 */
	public WxMallMerchantDict queryMerchantParam(Integer brandid) {
		if(AutoreplyData.getTokenMap()==null){//此处利用tokenmap的<String,Object>
			AutoreplyData.setTokenMap(new HashMap<String,Object>());
		}
		WxMallMerchantDict dict = (WxMallMerchantDict) AutoreplyData.getTokenMap().get(brandid.toString());
		if(dict==null){
			dict = wxMallMerchantDictDao.queryMerchantParam(brandid);
			if(AutoreplyData.getTokenMap().containsKey(brandid.toString())){
				AutoreplyData.getTokenMap().remove(brandid.toString());
			}
			AutoreplyData.getTokenMap().put(brandid.toString(), dict);
		}
		return dict;
	}
	/**
	 * 插入商家参数
	 */
	@Transactional(rollbackFor=Exception.class)
	public boolean insertMerchantParam(WxMallMerchantDict dict)
			throws Exception {
		return wxMallMerchantDictDao.insertMerchantParam(dict);
	}
	/**
	 * 查询商品列表
	 */
	public List<WxMallGoods> queryMallGoods(Integer brandid,Integer categoryId) {
		return wxMallGoodsDao.queryMallGoods(brandid,categoryId);
	}
	/**
	 * 查询单个商品信息
	 * @param brandid
	 * @param goodCode
	 * @return
	 */
	public WxMallGoods queryEachGood(Integer brandid,Integer goodCode) {
		return wxMallGoodsDao.queryEachGood(brandid,goodCode);
	}
	/**
	 * 我的订单查询
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>>  queryPaySuccessOrders(String openId,Integer brandId,Integer source)throws Exception{
		return wxMallOrderDao.queryPaySuccessOrders(openId,brandId,source);
	}
	/**
	 * 我的单个订单查询
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryPaySuccessSingleOrder( String outTradeNo,Integer brandId)throws Exception{
		return wxMallOrderDao.queryPaySuccessSingleOrder(outTradeNo, brandId);
	}
	/**
	 * 查询是否有券
	 */
	public boolean queryHasOrderCoupon(String outTradeNo) {
		return wxMallOrderCouponDao.queryHasOrderCoupon(outTradeNo);
	}
	@Transactional(rollbackFor=Exception.class)
	public void inserMallCouponInfo(List<WxMallOrderCoupon> list)
			throws Exception {
		wxMallOrderCouponDao.inserMallCouponInfo(list);
	}
	/**
	 * 购买后更新商品库存，同步加锁
	 */
	@Transactional(rollbackFor=Exception.class)
	public synchronized boolean updateOrderGoodRemain(Integer num,Integer goosId) throws Exception {
		int remainNum = wxMallInventoryDao.selectRemainNum(goosId); //剩余库存
		log.info("goodId:"+goosId+" remainNum:"+remainNum);
		int safeRemainNum = wxMallInventoryDao.selectSafeRemainNum(goosId);//安全剩余库存
		log.info("goodId:"+goosId+" safeRemainNum:"+safeRemainNum);
		int upn=0;
		int upsafen=0;
		if(remainNum>num){
			upn = wxMallInventoryDao.updateRemainNum(num, goosId);//更新库存
			log.info("更新库存："+(upn>0));
		}else{
			if(remainNum>0){
				 upn= wxMallInventoryDao.updateRemainNum(remainNum, goosId);//更新库存
				log.info("更新库存："+(upn>0));
			}
			upsafen=wxMallInventoryDao.updateSafeRemainNum(num-remainNum, goosId);//更新安全库存
			log.info("更新safe库存："+(upsafen>0));
		}
		int reNum = wxMallInventoryDao.selectRemainNum(goosId); //剩余库存
		if(reNum<=0){
			wxMallGoodsDao.updateGoodState(goosId,3);//商品售罄了，更新状态 为3
		}
		return upn>0 ||upsafen>0;
	}
	/**
	 * 是否有券状态
	 */
	public boolean queryHasBindCouponState(String outTradeNo) {
		return wxMallOrderCouponStateDao.queryHasBindCouponState(outTradeNo);
	}
	//插入发券状态
	@Transactional(rollbackFor=Exception.class)
	public int insertCouponState(WxMallOrderCouponState state) throws Exception {
		return wxMallOrderCouponStateDao.insertCouponState(state);
	}
	//更新发券状态
	@Transactional(rollbackFor=Exception.class)
	public int updateWeixin_pay_coupon_state(WxMallOrderCouponState state)
			throws Exception {
		return wxMallOrderCouponStateDao.updateWeixin_pay_coupon_state(state);
	}
	/**
	 * 查询用户购买商品个数
	 */
	public int queryUserBuyCount(String weixinId, Integer brandId,
			boolean flag, Integer goodId) {
		return wxMallOrderDao.queryUserBuyCount(weixinId, brandId, flag, goodId);
	}
	/**
	 * 插入积分消费订单
	 */
	@Transactional(rollbackFor=Exception.class)
	public int insertIntegralMallOrder(WxMallOrder wxorder) throws Exception {
		return wxMallOrderDao.insertIntegralMallOrder(wxorder);
	}
	
	/**
	 * 查询订单
	 */
	public WxMallOrder queryIntegralMallOrder(String outTradeNo,Integer brandId) {
		return wxMallOrderDao.queryIntegralMallOrder(outTradeNo,brandId);
	}
	/**
	 * 更新消费
	 */
	@Transactional(rollbackFor=Exception.class)
	public synchronized int updateJifenState(WxMallOrder wxorder) throws Exception {
		return wxMallOrderDao.updateJifenState(wxorder);
	}
	/**
	 * 查询商品对应的券id
	 * @param goodId
	 * @return
	 */
	public Integer queryGoodsCouponId(Long goodId) {
		return wxMallGoodsDao.queryGoodsCouponId(goodId);
	}
	/**
	 * 更新退款原因
	@Transactional(rollbackFor=Exception.class)
	public int updateDrawBackDesc(String desc, String outTradeNo)
			throws Exception {
		return wxMallOrderDao.updateDrawBackDesc(desc,outTradeNo);
	}*/
	
	/**
	 * 查询库存量参数，预警信息
	 */
	@Transactional(rollbackFor=Exception.class)
	public Map<String, Object> queryGoodInventory(Integer goosId) {
		return wxMallInventoryDao.queryGoodInventory(goosId);
	}
	/**
	 * 更新短信次数
	 */
	@Transactional(rollbackFor=Exception.class)
	public int updateSendSmsNum(Integer goosId) throws Exception {
		return wxMallInventoryDao.updateSendSmsNum(goosId);
	}
	
	public WxMallOrder getMallOrderById(Integer id) {
		return wxMallOrderDao.getMallOrderById(id);
	}
	public int updateMallOrderAddress(WxMallOrder order) {
		return wxMallOrderDao.updateMallOrderAddress(order);
	}

	@Transactional(rollbackFor=Exception.class)
	public int insertMallOrderLuckUse(WxMallOrder wxorder)throws Exception{
		return wxMallOrderDao.insertMallOrderLuckUse(wxorder);
	}
	//发送模版消息
	public String sendTemplateMessage(String tempId, String json,String token)
			throws Exception {
		return null;
	}
	/**
	 * 获取accesstoken
	 */
	public String getAcessTokenByDB(WxMallMerchantDict dict) throws Exception {
		String resToken = "";
		String brandId = dict.getBrandId().toString();
		String jsontoken = queryAccessToken(brandId);
		if(StringUtils.isEmpty(jsontoken)){
			JSONObject tokenJson = CommonUtil.getToken2(dict.getAppId(), dict.getAppSecret());
			String access_token = tokenJson.getString("access_token");
			String expires_in = Constant.EXPIRE_IN;
			Long currentTime = System.currentTimeMillis();
			JSONObject token = new JSONObject();//生成accessToken入库
			token.put("access_token", access_token);
			token.put("expires_in", Integer.parseInt(expires_in));
			token.put("brandId", dict.getBrandId().toString());
			token.put("createTime", currentTime);
			updateAccessToken(token);
			resToken = access_token;
			log.info("insert_resToken:"+resToken);
		}else{
			JSONObject json = JSONObject.fromObject(jsontoken);
			int expiresIn = json.getInt("expires_in");
			String access_token = json.getString("access_token");
			Long createTime = json.getLong("createTime");
			if((System.currentTimeMillis()-createTime)/1000>expiresIn){//过期重新生成，更新accessToken
				JSONObject tokenJson = CommonUtil.getToken2(dict.getAppId(), dict.getAppSecret());
				String access_token_up = tokenJson.getString("access_token");
				Long currentTime_up = System.currentTimeMillis();
				JSONObject token = new JSONObject();//生成accessToken入库
				token.put("access_token", access_token_up);
				token.put("expires_in", expiresIn);
				token.put("brandId", dict.getBrandId().toString());
				token.put("createTime", currentTime_up);
				updateAccessToken(token);
				resToken = access_token_up;
				log.info("update_resToken:"+resToken);
			}else{
				resToken = access_token;
				log.info("old_use_resToken:"+resToken);
			}
		}
		return resToken;
	}
	//查询订单信息
	public WxMallOrder queryMallOrder(String outTradeNo, Integer brandId) {
		return wxMallOrderDao.queryMallOrder(outTradeNo, brandId);
	}
	//查询模版消息
	public List<Map<String, Object>> queryTemplateParam(Integer brandid) {
		return wxTemplateDao.queryTemplateParam(brandid);
	}
	//更新模版消息
	@Transactional(rollbackFor=Exception.class)
	public void updateTemplate(List<WxTemplate> voList,Integer brandid) throws Exception {
		int del = wxTemplateDao.deleteTemplate(brandid);
		if(del>=0){
			for(WxTemplate vo:voList){
				 wxTemplateDao.insertTemplateMessage(vo);
			}
		}
	}
	//更新商户参数
	@Transactional(rollbackFor=Exception.class)
	public boolean updateMerchantParam(WxMallMerchantDict dict) throws Exception {
		int count = wxMallMerchantDictDao.queryCount(dict.getBrandId());
		int num = 0 ;
		boolean flag = false;
		if(count>0){
			num =wxMallMerchantDictDao.updateMerchantParam(dict);
			flag = num>0;
		}else{
			flag=  wxMallMerchantDictDao.insertMerchantParam(dict);
		}
		if(flag){
			dict = wxMallMerchantDictDao.queryMerchantParam(dict.getBrandId());//重新放入内存
			if(AutoreplyData.getTokenMap()==null){//此处利用tokenmap的<String,Object>
				AutoreplyData.setTokenMap(new HashMap<String,Object>());
			}
			AutoreplyData.getTokenMap().put(dict.getBrandId().toString(), dict);
		}
		return flag;
	}
	//查询未使用的券
	public List<Map<String, Object>> queryRemainOrderCoupon(String outTradeNo,
			Integer brandId) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		// 从数据库中取的相关卷信息
		List<Map<String, Object>> list = wxMallOrderCouponDao.queryRemainOrderCoupon(outTradeNo, brandId);
		List<Object> tempList = new ArrayList<Object>(); 
		for (Map<String, Object> map : list) {
			tempList.add(map.get("coupon_id"));
		}
		List<Object> esCouponList = memberShipCenterService.queryRemainOrderCouponByEs(brandId, tempList.toArray());
		if ((list!=null && list.size()>0 ) && (esCouponList!=null && esCouponList.size()>0)) {
			for (Object obj : esCouponList) {
				for (Map<String, Object> map : list) {
					if (map.get("coupon_id").equals(obj)) {
						Map<String, Object> resultMap = new HashMap<String, Object>();
						resultMap.put("id", obj);
						resultList.add(resultMap);
					}
				}
			}
		}
		return resultList;
	}

	
	@Transactional(rollbackFor=Exception.class)
	public boolean deleteAccessToken(Integer brandid) throws Exception {
		return wxAccessTokenDao.deleteAccessToken(brandid);
	}
	@Transactional(rollbackFor=Exception.class)
	public boolean updateTokenExpireTime(Integer brandid, Integer expiresin)
			throws Exception {
		return wxAccessTokenDao.updateTokenExpireTime(brandid, expiresin);
	}
	public int queryMallOrderCard(String outTradeNo) {
		return wxMallOrderCouponStateDao.queryMallOrderCard(outTradeNo);
	}
	@Transactional(rollbackFor=Exception.class)
	public int insertMallOrderCard(String orderNo, String cardNo)
			throws Exception {
		return wxMallOrderCouponStateDao.insertMallOrderCard(orderNo, cardNo);
	}
	/**
	 * 查询订单
	 */
	public WxMallOrder queryCardMallOrder(String outTradeNo,Integer brandId) {
		return wxMallOrderDao.queryCardMallOrder(outTradeNo,brandId);
	}
	public String queryAccessToken(String brandid){
		String token="";
		try {
			token= memcachedClient.get(brandid);
		} catch (TimeoutException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		} catch (MemcachedException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		}
		return token;
	}
	
	public void updateAccessToken(JSONObject dict){
		try {
			memcachedClient.delete(dict.getString("brandId"));
			memcachedClient.set(dict.getString("brandId"), dict.getInt("expires_in"), dict.toString());
		} catch (TimeoutException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		} catch (InterruptedException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		} catch (MemcachedException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		} 
	}
	public WxMallMerchantDict queryMerchantParamByDB(Integer brandid) {
		WxMallMerchantDict dict = wxMallMerchantDictDao.queryMerchantParam(brandid);
		if(AutoreplyData.getTokenMap()==null){//此处利用tokenmap的<String,Object>
			AutoreplyData.setTokenMap(new HashMap<String,Object>());
		}
		if(AutoreplyData.getTokenMap().containsKey(brandid.toString())){
			AutoreplyData.getTokenMap().remove(brandid.toString());
		}
		AutoreplyData.getTokenMap().put(brandid.toString(), dict);
		return dict;
	}
	
	
	@Override
	public List<WxMallGoods> getCardPictureUrlByCardType(List<WxMallGoods> mallList) {
		List<Integer> cardtypeIdList = new ArrayList<Integer>(); 
		if (mallList !=null && mallList.size()>0) {
			for (WxMallGoods m : mallList) {
				cardtypeIdList.add(m.getCardTypeId());
			}
			// 参数
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("cardtypeIdList", cardtypeIdList);
			log.info("卡类型id集合："+cardtypeIdList);
			String input = JSONObject.fromObject(paramMap).toString();
			// 返回结果
			String result = "";
			try {
				result = CommonUtil.postSendMessage(getCardPictureUrl, input, Constant.KEY+"");
			} catch (WeixinRuntimeException e) {
				e.printStackTrace();
			}
			JSONArray array = null;
			if (!StringUtil.isNullOrEmpty(result)) {
				JSONObject jo = JSONObject.fromObject(result);
				if (jo !=null) {
					boolean success = jo.getJSONObject("data").getJSONObject("result").getBoolean("success");
					if (success) {
						array = jo.getJSONObject("data").getJSONObject("result").getJSONArray("cardtypeIconList");
					}
				}
			}
			if (array !=null) {
				Iterator it = array.iterator();
				while(it.hasNext()){
					Map<String, Object> resultMap = (Map<String, Object>)it.next();
					Integer cardTypeId = Integer.parseInt(resultMap.get("cardtypeId")+"");
					String picUrl = String.valueOf(resultMap.get("icon"));
					for (WxMallGoods m : mallList) {
						if (m.getCardTypeId().equals(cardTypeId) && !StringUtil.isNullOrEmpty(picUrl)) {
							m.setCardPictureUrl(imageFileUrl+picUrl);
						}
					}
				}
			}
		}
		return mallList;
	}
	/**
	 * 更新退款原因
	 */
	@Transactional(rollbackFor=Exception.class)
	public boolean updateDrawBackDesc(WxMallRefundRecord dict) throws Exception {
		int num = wxMallOrderDao.updateDrawBackDesc(dict.getDescription(),dict.getOutTradeNo());
		boolean flag = wxMallRefundRecordDao.insertRefundRecord(dict);
		return num>0&&flag;
	}
	/*查询退款信息*/
	public List<Map<String, Object>> queryRefundRecodeList(String outradeno) {
		return wxMallRefundRecordDao.queryRefundRecodeList(outradeno);
	}
	@Transactional(rollbackFor=Exception.class)
	public boolean insertRefundRecord(WxMallRefundRecord rr) throws Exception {
		return wxMallRefundRecordDao.insertRefundRecord(rr);
	}
	/*查询退款状态*/
	public JSONObject queryRefundState(JSONObject obj) {
		JSONObject resJson = new JSONObject();
		try{
			String brandId = obj.getString("brandId");
			WxMallMerchantDict dict = queryMerchantParam(Integer.parseInt(brandId));
			if(!(3==dict.getV2_v3().intValue())){
				//商户号 
				String partner = dict.getPartnerId();
				//密钥 
				String appKey = dict.getPartnerKey();
			    //创建查询请求对象
			    RequestHandler reqHandler = new RequestHandler(null, null);
			    //通信对象
			    TenpayHttpClient httpClient = new TenpayHttpClient();
			    //应答对象
			    ClientResponseHandler resHandler = new ClientResponseHandler();
			    reqHandler.init();
			    reqHandler.setKey(appKey);
			    reqHandler.setGateUrl(wxrefundquery.trim());
			    reqHandler.setParameter("partner", partner);	
			    reqHandler.setParameter("out_trade_no", obj.getString("outTradeNo"));	
			    reqHandler.setParameter("transaction_id",  obj.getString("transId"));
			    reqHandler.setParameter("out_refund_no",obj.getString("refundNo"));	
			    httpClient.setTimeOut(5);	
			    httpClient.setMethod("POST");     
			    String requestUrl = reqHandler.getRequestURL();
			    httpClient.setReqContent(requestUrl);
			    String rescontent = "null";
			    if(httpClient.call()) {
			    	//设置结果参数
			    	rescontent = httpClient.getResContent();
			    	resHandler.setContent(rescontent);
			    	resHandler.setKey(appKey);
			    	String retcode = resHandler.getParameter("retcode");
			    	
			    	//判断签名及结果
			    	//只有签名正确并且retcode为0才是请求成功
			    	if(resHandler.isTenpaySign()&& "0".equals(retcode)) {
			    		//取结果参数做业务处理
						//退款笔数
						String refund_count = resHandler.getParameter("refund_count");
						
						log.info("退款笔数:" + refund_count);
						int count = Integer.parseInt(refund_count);
						//每笔退款详情
						/*退款状态	refund_status	
							4，10：退款成功。
							3，5，6：退款失败。
							8，9，11:退款处理中。
							1，2: 未确定，需要商户原退款单号重新发起。
							7：转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款。
							*/
						log.info("res content:" + rescontent);
						for(int i=0; i<count; i++){ 
						    String refund_state_n = "refund_state_" + Integer.toString(i);
						    String out_refund_no_n = "out_refund_no_" + Integer.toString(i);
						    String refund_fee_n = "refund_fee_" + Integer.toString(i);
						    
						    log.info("第" + Integer.toString(i) + "笔：" + refund_state_n + "=" + resHandler.getParameter(refund_state_n) 
						        + "," + out_refund_no_n + "=" + resHandler.getParameter(out_refund_no_n) 
						        + "," + refund_fee_n + "=" + resHandler.getParameter(refund_fee_n));
						    resJson.put("refundNo", resHandler.getParameter(out_refund_no_n));
						    resJson.put("state", resHandler.getParameter(refund_state_n) );
						}
			    		resJson.put("success", true);
			    	} else {
			    		log.info("验证签名失败或业务错误");
			    		log.info("retcode:" + resHandler.getParameter("retcode")+
			    				" retmsg:" + resHandler.getParameter("retmsg"));
			    		String retmsg = resHandler.getParameter("retmsg");
			    		resJson.put("success", false);
						resJson.put("message", retmsg);
			    		//错误时，返回结果未签名，记录retcode、retmsg看失败详情。
			    	}
			    }
			}else{
				//v3支付版退款查询
				String appid =dict.getAppId();
				String mch_id=dict.getPartnerId();
				String nonce_str =CommonUtil.CreateNoncestr();
				String transaction_id =obj.getString("transId");
				String out_refund_no =obj.getString("refundNo");
				String out_trade_no =obj.getString("outTradeNo");
				SortedMap<String, String> map = new TreeMap<String,String>();
				map.put("appid", appid);
				map.put("mch_id", mch_id);
				map.put("nonce_str", nonce_str);
				map.put("transaction_id", transaction_id);
				map.put("out_trade_no", out_trade_no);
				map.put("out_refund_no", out_refund_no);
				String sign = createSign(map,dict.getPartnerKey());
				map.put("sign", sign);
				String xml=CommonUtil.ArrayToXml(map);
				String refund_return = CommonUtil.postMessage2(wxrefundqueryv3.trim(), xml);
				Map<String,String> refundMap = CommonUtil.doXMLParse(refund_return);
	            String return_code= refundMap.get("return_code");
                if("SUCCESS".equals(return_code)){
                	String result_code= refundMap.get("result_code");
                	if("SUCCESS".equals(result_code)){
                		String refund_count = refundMap.get("refund_count");
                		for(int i=0; i<Integer.parseInt(refund_count); i++){ 
						    String refund_state_n = "refund_state_" + Integer.toString(i);
						    String out_refund_no_n = "out_refund_no_" + Integer.toString(i);
						    String refund_fee_n = "refund_fee_" + Integer.toString(i);
						    
						    log.info("第" + Integer.toString(i) + "笔：" + refund_state_n + "=" + refundMap.get(refund_state_n) 
						        + "," + out_refund_no_n + "=" + refundMap.get(out_refund_no_n) 
						        + "," + refund_fee_n + "=" + refundMap.get(refund_fee_n));
						    resJson.put("refundNo", refundMap.get(out_refund_no_n));
						    resJson.put("state", refundMap.get(refund_state_n) );
						}
			    		resJson.put("success", true);
                	}else{
                		String retmsg = refundMap.get("err_code_des");
                		resJson.put("success", false);
                		resJson.put("message", retmsg);
                	}
                }else{
                	String retmsg = refundMap.get("return_msg");
		    		resJson.put("success", false);
					resJson.put("message", retmsg);
                }
			}
	    }catch(Exception ex){
	    	log.error("code happen error.",ex);
	    	JSONObject errorResJson =new JSONObject();
			errorResJson.put("success", false);
			errorResJson.put("message", "接口异常");
			return errorResJson;
	    }
		return resJson;
	}
	/**
	 * v3 版退款申请
	 * @throws Exception 
	 */
	public JSONObject refundRequest(String url,String mch_id,String xml,WxMallMerchantDict dict) throws Exception {
		JSONObject resJson = new JSONObject();
		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
		 URL httpurl=null;
		 if(dict.getCertificateUrl().startsWith("M00/")){
		    	httpurl= new URL(certificateKeyUrl.trim()+dict.getCertificateUrl());
		    }else{
		    	httpurl= new URL(caFileIp+dict.getCertificateUrl());
		    }
		String projectPath = this.getClass().getResource("/").getPath();
	    File fw = new File(projectPath+"wxpaycacert/"+dict.getBrandId()+"/"+mch_id+".p12");   //记的去掉注释
//		File fw = new File("E:/"+mch_id+".p12");
	    InputStream instream= null;
	    if(!fw.exists()){
	    	try{
	    		FileUtils.copyURLToFile(httpurl, fw);
	    		instream = new FileInputStream(fw);
	    	}catch(Exception ex){
	    		instream = httpurl.openStream();
	    		log.error("copy file error.",ex);
	    	}
	    }else{
	    	instream = new FileInputStream(fw);
	    }
        try {
            keyStore.load(instream, mch_id.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mch_id.toCharArray())
                .build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpPost post= new HttpPost(url);
            post.setEntity(new StringEntity(xml, "UTF-8"));
            HttpResponse response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            if (entity != null&& response.getStatusLine().getStatusCode()==200) {
                log.info("Response content length: " + entity.getContentLength());
                String text=new String(EntityUtils.toString(entity).getBytes("ISO_8859_1"),"UTF-8");
                log.info("退款返回信息:"+text);
                Map<String,String> refundMap = CommonUtil.doXMLParse(text);
                String return_code= refundMap.get("return_code");
                if("SUCCESS".equals(return_code)){
                	String result_code= refundMap.get("result_code");
                	if("SUCCESS".equals(result_code)){
                		String out_refund_no=refundMap.get("out_refund_no");
                		resJson.put("success", true);
                		resJson.put("state", "4"); //4 退款成功 9 退款处理中
                		resJson.put("refundNo", out_refund_no);
                	}else{
                		resJson.put("success", false);
						resJson.put("message", refundMap.get("err_code_des"));
                	}
                }else{
                	String return_msg = refundMap.get("return_msg");
                	resJson.put("success", false);
					resJson.put("message", return_msg);
                }
            }
            EntityUtils.consume(entity);
        }catch(Exception ex){
        	log.error("[退款申请失败]",ex);
        	resJson.put("success", false);
			resJson.put("message", "退款服务异常");
        }finally {
            httpclient.close();
        }
        return resJson;
	}
	
	public String createSign(SortedMap<String, String> packageParams,String apiKey){
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key="+apiKey);
		String sign = MD5Util.MD5Encode(sb.toString(),"UTF-8").toUpperCase();
		return sign;
	}
}
