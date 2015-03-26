package com.yazuo.weixin.card.service.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.annotation.Resource;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yazuo.weixin.card.service.CardService;
import com.yazuo.weixin.dao.MemberDao;
import com.yazuo.weixin.es.service.MemberShipCenterService;
import com.yazuo.weixin.es.vo.BrandVO;
import com.yazuo.weixin.es.vo.CardTypeVo;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.vo.CouponVO2;
import com.yazuo.weixin.vo.MemberCardVO;
import com.yazuo.weixin.vo.Message;
import com.yazuo.weixin.vo.TransWaterPage;

/**
* @ClassName CardUtils
* @Description 获取卡列表
* @author sundongfeng@yazuo.com
* @date 2014-11-24 上午11:18:46
* @version 1.0
*/
@Service("CardServiceImpl")
public class CardServiceImpl implements CardService {
	private static final Log log = LogFactory.getLog("weixin");
	@Value("#{propertiesReader['getMemAndCardMobileAndmerchantId']}")
	private String getMemAndCardMobileAndmerchantIdUrl;
	@Value("#{propertiesReader['getCouponByCardNoAndMerchantId']}")
	private String getCouponByCardNoAndMerchantIdUrl;
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	@Value("#{propertiesReader['imageFileUrl']}")
	private String imageFileUrl;
	@Value("#{propertiesReader['getMerchantByMerchantId']}")
	private String getMerchantByMerchantIdUrl;
	@Value("#{propertiesReader['selectCardType']}")
	private String selectCardTypeUrl;
	@Value("#{propertiesReader['getCardPictureByCardType']}")
	private String getIconUrl;
	@Value("#{propertiesReader['selectEvaluate']}")
	private String selectEvaluate;
	@Value("#{propertiesReader['getMerchant']}")
	private String getMerchantUrl;
	@Autowired
	private MemcachedClient memcachedClient;
	@Autowired
	private MemberDao memberDao;
	@Resource
	private MemberShipCenterService memberShipCenterService;
	/**
	 * 获取卡列表
	 * @param brandId
	 * @param mobile
	 * @param url
	 * @param imageFileUrl
	 * @return
	 */
	public List<MemberCardVO> queryCardList(Integer brandId,String mobile){
		List<MemberCardVO> cardList = new ArrayList<MemberCardVO>();
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("merchantId", brandId);
		jsonMap.put("mobile", mobile);
		String input = JSONObject.fromObject(jsonMap).toString();
		
		try {
			String result = CommonUtil.postSendMessage(getMemAndCardMobileAndmerchantIdUrl, input, Constant.KEY.toString());
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			JSONObject res=JSONObject.fromObject(data);
			log.info("接口返回："+res.toString());
			if (res != null) { 
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				log.info("接口返回message："+message);
				if (success) {// 成功
					JSONArray array = res.getJSONArray("cardList");
					for(Iterator iter = array.iterator();iter.hasNext();){
						JSONObject jsonObject = (JSONObject) iter.next();
						MemberCardVO cardInfo = (MemberCardVO) JSONObject.toBean(jsonObject, MemberCardVO.class);
						String icon = cardInfo.getIcon();
						log.info("icon:"+icon);
						if(StringUtils.isNotEmpty(icon)){
							cardInfo.setIcon(imageFileUrl+icon);
						}
						cardList.add(cardInfo);
					}
				}
			}
		} catch (Exception e) {
			log.error("CardUtils.queryCardList error.",e);
		}
		return cardList;
	}
	
	// 从搜索引擎取卡列表数据
	@Override
	public List<MemberCardVO> queryCardList(Integer brandId, Integer membershipId) {
		return memberShipCenterService.queryMemberCardList(brandId+"", membershipId);
	}


	/**
	 * 查询券列表
	 * @param brandId
	 * @param mobile
	 * @param cardNo
	 * @param url
	 * @return
	 */
	public List<CouponVO2> getCouponList(Integer brandId,String mobile,String cardNo){
		Map<String, List<CouponVO2>> map = memberShipCenterService.getCardCouponList(brandId+"", cardNo, mobile);
		List<CouponVO2> couponList = new ArrayList<CouponVO2>();
		couponList.addAll(map.get("card"));
		couponList.addAll(map.get("mobile"));
		return couponList;
		
//		List<CouponVO2> couponList = new ArrayList<CouponVO2>();
//		try{
//			Map<String,Object> jsonMap = new HashMap<String,Object>();
//			jsonMap.put("merchantId", brandId);
//			jsonMap.put("mobile",mobile);
//			jsonMap.put("cardNo", cardNo);
//			String input = JSONObject.fromObject(jsonMap).toString();
//			
//			String result = CommonUtil.postSendMessage(getCouponByCardNoAndMerchantIdUrl, input, Constant.KEY.toString());
//			JSONObject requestObject = JSONObject.fromObject(result);
//			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
//			JSONObject res=JSONObject.fromObject(data);
//			log.info("接口返回："+res.toString());
//			if (res != null) { 
//				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
//				String message = res.getString("message"); // 返回信息
//				log.info("接口返回message："+message);
//				if (success) {// 成功
//					JSONArray array = res.getJSONArray("couponList");
//					for(Iterator iter = array.iterator();iter.hasNext();){
//						JSONObject jsonObject = (JSONObject) iter.next();
//						CouponVO2 cardInfo = (CouponVO2) JSONObject.toBean(jsonObject, CouponVO2.class);
//						couponList.add(cardInfo);
//					}
//				}
//			}
//		}catch(Exception e){
//			log.error("CardUtils.getCouponList error.",e);
//		}
//		return couponList;
	}
	
	public void showCouponList(List<Message> articles,List<MemberCardVO> cardList,Integer brandId,String mobile,String weixinId, String path){
		if(cardList!=null){
			
			if(cardList.size()==1){
				//单卡
				MemberCardVO card = cardList.get(0);
				Message cardInfo1 = new Message();
				cardInfo1.setTitle("积分余额："	+ card.getIntegralAvailable());
				cardInfo1.setUrl(serverIp + path + "/weixin/phonePage/fensiCard.do?brandId=" + brandId + "&weixinId="+ weixinId);
				articles.add(cardInfo1);
				Message cardInfo2 = new Message();
				cardInfo2.setTitle("储值余额：" + card.getStoreBalance());
				cardInfo2.setUrl(serverIp + path + "/weixin/phonePage/fensiCard.do?brandId=" + brandId + "&weixinId="+ weixinId);
				articles.add(cardInfo2);
				List<CouponVO2> couponList = getCouponList(brandId,mobile,card.getCardNo());
				int i = 0;
				if(couponList!=null&&couponList.size()>0){
					for (CouponVO2 coupon : couponList) {
						if (i >= 6)
							break;
						Message couponInfo = new Message();
						if(brandId.intValue() == Constant.CUSTOM_SETTING_BRAND){
							couponInfo.setTitle(coupon.getName() + "\n"
									+ "活动有效期"
									+ coupon.getStartDate() + "至"
									+ coupon.getExpiredDate());
						}else{
							couponInfo.setTitle(coupon.getName() + "\n"
									+ coupon.getStartDate() + "至"
									+ coupon.getExpiredDate() + "有效");
						}
						couponInfo.setUrl(serverIp  +path+ "/weixin/phonePage/fensiCard.do?brandId=" + brandId + "&weixinId="+ weixinId);
						articles.add(couponInfo);
						i++;
					}
				}
			}else if(cardList.size()>1){
				//多卡显示
				String url=serverIp;
				for(MemberCardVO cardvo:cardList){
					String cardNo = cardvo.getCardNo();
					String cardType=cardvo.getCardtype();
					String storeBalance=cardvo.getStoreBalance();
					if(StringUtils.isEmpty(storeBalance)){
						storeBalance="0";
					}
					String integralAvailable=cardvo.getIntegralAvailable();
					if(StringUtils.isEmpty(integralAvailable)){
						integralAvailable="0";
					}
					Boolean pwdIf =cardvo.getIsTradePassword();
					String icon = cardvo.getIcon();
					Message cardInfo = new Message();
					cardInfo.setTitle(cardType);
					cardInfo.setUrl(url + path + "/weixin/phonePage/membershipCoupon.do?brandId="+brandId+"&cardNo="+cardNo+"&mobile="+mobile+"&weixinId="+weixinId+"&cardType="+cardType+"&storeBalance="+storeBalance+"&integralAvailable="+integralAvailable+"&pwdIf="+pwdIf+"&icon="+icon);
					articles.add(cardInfo);
				}
			}
		}
	}
	
	public BrandVO getMerchant(Integer brandId){
		BrandVO brandvo = new BrandVO();
		brandvo.setBrandId(brandId);
		Map<String,Object> jsonMap1 = new HashMap<String,Object>();
		jsonMap1.put("merchantId", brandId);
		String input1 = JSONObject.fromObject(jsonMap1).toString();
		try{
		String result = CommonUtil.postSendMessage(getMerchantByMerchantIdUrl, input1, Constant.KEY.toString());
		JSONObject requestObject = JSONObject.fromObject(result);
		String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
		JSONObject res=JSONObject.fromObject(data);
		log.info("接口返回："+res.toString());
		if (res != null) {
			Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
			String message = res.getString("message"); // 返回信息
			JSONObject merchants=res.getJSONObject("merchants");
			log.info("接口返回message："+message);
			if (success) {// 成功
				brandvo = (BrandVO) JSONObject.toBean(merchants, BrandVO.class);
			}
		}
		}catch(Exception ex){
			log.error("getMerchant error.",ex);
		}
		return brandvo;
	}
	
	public CardTypeVo getCardType(Integer cardTypeId){
		List<Integer> cardtypeidlist = new ArrayList<Integer>();
		cardtypeidlist.add(cardTypeId);
		CardTypeVo cardType= new CardTypeVo();
		try{
			String cardType_mem = memcachedClient.get("yazuo.cardtype:"+cardTypeId);
			if(StringUtils.isNotEmpty(cardType_mem)){
				JSONObject json_mem=JSONObject.fromObject(cardType_mem);
				log.info("cardType:"+json_mem);
				cardType = (CardTypeVo) JSONObject.toBean(json_mem, CardTypeVo.class);
			}else{
				Map<String,Object> jsonMap = new HashMap<String,Object>();
				jsonMap.put("cardtypeIdList", cardtypeidlist);
				String input = JSONObject.fromObject(jsonMap).toString();
				String result = CommonUtil.postSendMessage(selectCardTypeUrl, input, Constant.KEY.toString());
				JSONObject requestObject = JSONObject.fromObject(result);
				String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
				JSONObject res=JSONObject.fromObject(data);
				log.info("接口返回："+res.toString());
				if (res != null) {
					Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
					String message = res.getString("message"); // 返回信息
					log.info("接口返回message："+message);
					if (success) {// 成功
						JSONArray array = res.getJSONArray("cardtypeList");
						if(array!=null&&array.size()>0){
							JSONObject merchants=array.getJSONObject(0);
							cardType = (CardTypeVo) JSONObject.toBean(merchants, CardTypeVo.class);
						}
					}
				}
			}
		}catch(Exception ex){
			log.error("getCardType error.",ex);
		}
		try{
			String cardTypeicon_mem = memcachedClient.get("yazuo.cardtypeIcon:"+cardTypeId);
			if(StringUtils.isNotEmpty(cardTypeicon_mem)){
				log.info("cardTypeicon:"+cardTypeicon_mem);
				JSONObject iconJson = JSONObject.fromObject(cardTypeicon_mem);
				cardType.setCardtypeIcon(iconJson.getString("icon"));
			}else{
				Map<String,Object> jsonMap1 = new HashMap<String,Object>();
				jsonMap1.put("cardtypeIdList", cardtypeidlist);
				String input1 = JSONObject.fromObject(jsonMap1).toString();
				String result = CommonUtil.postSendMessage(getIconUrl, input1, Constant.KEY.toString());
				JSONObject requestObject = JSONObject.fromObject(result);
				String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
				JSONObject res=JSONObject.fromObject(data);
				log.info("接口返回："+res.toString());
				if (res != null) {
					Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
					String message = res.getString("message"); // 返回信息
					log.info("接口返回message："+message);
					if (success) {// 成功
						JSONArray array = res.getJSONArray("cardtypeIconList");
						if(array!=null&&array.size()>0){
							cardType.setCardtypeIcon(array.getJSONObject(0).getString("icon"));
						}
					}
				}
			}
		}catch(Exception ex){
			log.error("getIcon error.",ex);
		}
		return cardType;
	}
	
	public String getCouponDesc(int batchNo) throws Exception{
		String desc = memcachedClient.get("yazuo.com:"+batchNo);
		/*if(StringUtils.isEmpty(desc)){
			desc = memberDao.getCouponDesc(batchNo);
			memcachedClient.set("yazuo.com:"+batchNo, 0, desc);
		}*/
		return StringUtils.isNotEmpty(desc)?desc:"";
	}

	
	@Override
	public List<CardTypeVo> getCardType(List<Integer> cardTypeIdList) {
		if (cardTypeIdList==null || cardTypeIdList.size()==0) {
			return null;
		}
		List<CardTypeVo> cardTypeList = new ArrayList<CardTypeVo>();
		List<Integer> noCacheCardIconList = new ArrayList<Integer>();
		List<Integer> noCacheCardTypeList = new ArrayList<Integer>();
		for (Integer cardTypeId : cardTypeIdList) {
			// 取卡信息
			String cardType_mem = "";
			try {
				cardType_mem = memcachedClient.get("yazuo.cardtype:"+cardTypeId);
			} catch (Exception e) {
				log.info("yazuo.cardtype Exception:"+ e);
			}
			CardTypeVo cardType = null;
			if(StringUtils.isNotEmpty(cardType_mem)){
				JSONObject json_mem=JSONObject.fromObject(cardType_mem);
				log.info("cardType:"+json_mem);
				cardType = (CardTypeVo) JSONObject.toBean(json_mem, CardTypeVo.class);
				// 取卡类型图片
				String cardTypeicon_mem = "";
				try {
					cardTypeicon_mem = memcachedClient.get("yazuo.cardtypeIcon:"+cardTypeId);
				}catch (Exception e) {
					log.info("yazuo.cardtypeIcon Exception:"+ e);
				}
				if(StringUtils.isNotEmpty(cardTypeicon_mem)){
					log.info("cardTypeicon:"+cardTypeicon_mem);
					JSONObject iconJson = JSONObject.fromObject(cardTypeicon_mem);
					cardType.setDescription(iconJson.getString("remark"));
					cardType.setCardtypeIcon(imageFileUrl+iconJson.getString("icon"));
				} else {
					noCacheCardIconList.add(cardTypeId);
				}
				cardTypeList.add(cardType);
			} else {
				noCacheCardTypeList.add(cardTypeId);
			}
		}
		// 未放到缓存里面的卡类型数据
		if (noCacheCardTypeList.size() > 0) {
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("cardtypeIdList", noCacheCardTypeList);
			String input = JSONObject.fromObject(jsonMap).toString();
			log.info("----调用selectCardType接口参数："+input);
			try{
				String result = CommonUtil.postSendMessage(selectCardTypeUrl, input, Constant.KEY.toString());
				JSONObject requestObject = JSONObject.fromObject(result);
				String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
				JSONObject res=JSONObject.fromObject(data);
				log.info("接口返回："+res.toString());
				if (res != null) {
					Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
					String message = res.getString("message"); // 返回信息
					log.info("接口返回message："+message);
					if (success) {// 成功
						JSONArray array = res.getJSONArray("cardtypeList");
						Iterator it = array.iterator();
						while(it.hasNext()){
							JSONObject json = (JSONObject)it.next();
							CardTypeVo cardtypeVo = (CardTypeVo)JSONObject.toBean(json, CardTypeVo.class);
							// 取卡类型图片
							String cardTypeicon_mem = memcachedClient.get("yazuo.cardtypeIcon:"+cardtypeVo.getId());
							if(StringUtils.isNotEmpty(cardTypeicon_mem)){
								log.info("cardTypeicon:"+cardTypeicon_mem);
								JSONObject iconJson = JSONObject.fromObject(cardTypeicon_mem);
								cardtypeVo.setDescription(iconJson.getString("remark"));
								cardtypeVo.setCardtypeIcon(imageFileUrl+iconJson.getString("icon"));
							} else {
								noCacheCardIconList.add(cardtypeVo.getId());
							}
							cardTypeList.add(cardtypeVo);
						}
					}
				}
			}catch(Exception e){
				log.error("selectCardType error.", e);
			}
		}
		// 未放到缓存里面的图片信息
		Map<Integer,CardTypeVo> iconMap = new HashMap<Integer, CardTypeVo>();
		if (noCacheCardIconList.size() >0) {
			Map<String,Object> jsonMap1 = new HashMap<String,Object>();
			jsonMap1.put("cardtypeIdList", noCacheCardIconList);
			String input1 = JSONObject.fromObject(jsonMap1).toString();
			log.info("----调用getIcon接口参数："+input1);
			try {
				String result = CommonUtil.postSendMessage(getIconUrl, input1, Constant.KEY.toString());
				JSONObject requestObject = JSONObject.fromObject(result);
				String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
				JSONObject res=JSONObject.fromObject(data);
				log.info("接口返回："+res.toString());
				if (res != null) {
					Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
					String message = res.getString("message"); // 返回信息
					log.info("接口返回message："+message);
					if (success) {// 成功
						JSONArray array = res.getJSONArray("cardtypeIconList");
						Iterator it = array.iterator();
						while(it.hasNext()){
							JSONObject tCardIcon = ((JSONObject)it.next());
							CardTypeVo cardtypeVo = new CardTypeVo();
							cardtypeVo.setDescription(tCardIcon.getString("remark"));
							cardtypeVo.setCardtypeIcon(imageFileUrl + tCardIcon.getString("icon"));
							iconMap.put(tCardIcon.getInt("cardtypeId"), cardtypeVo);
						}
					}
				}
			}catch(Exception e) {
				log.error("getIcon error.", e);
			}
		}
		
		if (iconMap !=null && iconMap.size() > 0) {
			for (CardTypeVo card : cardTypeList) {
				CardTypeVo iconCard = (CardTypeVo)iconMap.get(card.getId());
				if (iconCard !=null) {
					card.setDescription(iconCard.getDescription());
					card.setCardtypeIcon(iconCard.getCardtypeIcon());
				}
			}
		}
		return cardTypeList;
	}
	public Map<Integer, TransWaterPage> queryEvaluate(Integer brandId,Set<Integer> masterIds) {
		Map<Integer, TransWaterPage> resultMap = new HashMap<Integer, TransWaterPage>();
		try{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("merchantId",brandId);
			map.put("idList",masterIds);
			String input = JSONObject.fromObject(map).toString();
			String result = CommonUtil.postSendMessage(selectEvaluate, input, Constant.ENTITYKEY.toString());
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			JSONObject res=JSONObject.fromObject(data);
			if (res != null) { 
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				log.info("接口返回message："+message);
				if (success) {// 成功
					JSONArray array = res.getJSONArray("list");
					for(Iterator iter = array.iterator();iter.hasNext();){
						JSONObject eachEva = (JSONObject) iter.next();
						TransWaterPage tw = new TransWaterPage();
						Integer twid = eachEva.getInt("transWaterId");
						tw.setEvaluateId(eachEva.getString("id"));
						tw.setScore(eachEva.getDouble("score"));
						tw.setContent(eachEva.getString("content"));
						tw.setId(twid);
						resultMap.put(twid, tw);
					}
				}
			}
		}catch(Exception ex){
			log.error("getIcon error.",ex);
		}
		return resultMap;
	}

	/**
	 * 获取门店名称
	 */
	public Map<String,String> queryMerchant(Set<String> set) {
		Map<String,String> names = new HashMap<String,String>();
		try{
			if(set!=null&&set.size()>0){
				 Iterator<String> it =set.iterator();
				while(it.hasNext()){
					Map<String,Object> jsonMap = new HashMap<String,Object>();
					String mno = it.next();
					jsonMap.put("merchantNo", mno);
					String input = JSONObject.fromObject(jsonMap).toString();
					String result = CommonUtil.postSendMessage(getMerchantUrl, input, Constant.KEY.toString());
					JSONObject requestObject = JSONObject.fromObject(result);
					String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
					JSONObject res=JSONObject.fromObject(data);
					log.info("接口返回："+res.toString());
					if (res != null) {
						Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
						String message = res.getString("message"); // 返回信息
						log.info("接口返回message："+message);
						if (success) {// 成功
							String merchantName = (String) ((JSONObject)res.get("merchant")).get("merchantName");
							names.put(mno, merchantName);
						}
					}
				}
			}
		}catch(Exception ex){
			log.error("queryMerchant error.",ex);
		}
		return names;
	}
	
}
