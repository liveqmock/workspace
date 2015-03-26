package com.yazuo.weixin.minimart.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.yazuo.weixin.exception.UserErrorException;
import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.minimart.service.WeixinMallMartService;
import com.yazuo.weixin.minimart.vo.WxMallGoods;
import com.yazuo.weixin.minimart.vo.WxMallMerchantDict;
import com.yazuo.weixin.minimart.vo.WxMallOrder;
import com.yazuo.weixin.minimart.vo.WxMallOrderCoupon;
import com.yazuo.weixin.minimart.vo.WxMallOrderCouponState;
import com.yazuo.weixin.minimart.vo.WxMallRefundRecord;
import com.yazuo.weixin.minimart.vo.WxMallTree;
import com.yazuo.weixin.service.RegisterPageService;
import com.yazuo.weixin.service.WeixinManageService;
import com.yazuo.weixin.service.WeixinPayService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.util.HttpClientCommonSSL;
import com.yazuo.weixin.util.IOUtil;
import com.yazuo.weixin.util.MailUtil;
import com.yazuo.weixin.util.WxPayUtil;
import com.yazuo.weixin.util.WxPayUtilV3;
import com.yazuo.weixin.vo.BusinessVO;
import com.yazuo.weixin.vo.MemberCardVO;
import com.yazuo.weixin.vo.MemberVO;

/**
* @ClassName WeixinMallMartController
* @Description  微信商城
* @author sundongfeng@yazuo.com
* @date 2014-7-30 下午5:48:55
* @version 1.0
*/
@Controller
@Scope("prototype")
@RequestMapping("/weixin/{brandId}")
@SessionAttributes({"dict","openid","brandId"}) 
public class WeixinMallMartController {
	private static final Log log = LogFactory.getLog("mall");
	private static final Log logAna = LogFactory.getLog("martlog");//日志记录分析
	@Value("#{payPropertiesReader['smsUrl']}")
	private String smsUrl;
	@Value("#{propertiesReader['consumeIntegral']}")
	private String consumeIntegralUrl;
	@Value("#{propertiesReader['getMemAndCardMobileAndmerchantId']}")
	private String getMemAndCardMobileAndmerchantIdUrl;
	@Value("#{payPropertiesReader['xld.optainTicketsUrl']}")
	private String optainTicketsUrl;
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	@Value("#{propertiesReader['caFileIp']}")
	private String caFileIp;
	@Value("#{propertiesReader['newPictureUrl']}")
	private String newPictureUrl;
	@Value("#{propertiesReader['checkCardTrade']}")
	private String checkCardTradeUrl;//销分前校验
	@Value("#{propertiesReader['judgeCardNeedWritePwd']}")
	private String checkCardTypeUrl;//校验
	@Value("#{propertiesReader['updateCouponByIdAndMerchantId']}")
	private String updateCouponByIdAndMerchantIdUrl;
	
	
	@Autowired
	private WeixinMallMartService weixinMallMartService;
	@Autowired
	private WeixinPayService weixinPayService;
	@Autowired
	private WxPayUtil wxpayUtil;
	@Autowired
	private WeixinPhonePageService weixinPhonePageService;
	@Autowired
	private WeixinManageService weixinManageService;
	@Autowired
	private RegisterPageService registerPageService;
	@Autowired
	private WxPayUtilV3 wxPayUtilV3;
	/**
	 * 私有方法，取出openid
	 * @param request
	 * @param code
	 * @param appid
	 * @param appSecret
	 * @return
	 * @throws Exception
	 */
	private String queryWeixinId(HttpServletRequest request,String code,String appid,String appSecret)throws Exception{
		String openid = (String) request.getSession().getAttribute("openid");
		log.info("获取session-openid:"+openid);
		if(StringUtils.isEmpty(openid)){
			JSONObject json = weixinPayService.getWeiXinId(code,"", appid, appSecret);
			log.info("获取auth2-openid:"+json.getString("openid"));
			openid = json.getString("openid");
		}
		return openid;
	}
	/**
	 * 微信商城主页面
	 * @param request
	 * @param brandId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="mallMartIndex", method = { RequestMethod.POST,RequestMethod.GET })
	public String mallMartIndex(HttpServletRequest request,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			ModelMap model
			){
		logAna.info(brandId+";"+DateUtil.getDate()+";mallMartIndex;");
		try{
			Map<String, WxMallTree> bigMap = new HashMap<String, WxMallTree> ();
			Map<String, WxMallTree> smallMap = new HashMap<String, WxMallTree> ();
			WxMallMerchantDict dict = weixinMallMartService.queryMerchantParamByDB(brandId);
			model.addAttribute("dict", dict);
			model.addAttribute("caFileIp", caFileIp);
			model.addAttribute("newPictureUrl", newPictureUrl);
			String openid=weixinId;
			if(StringUtils.isEmpty(openid)){
				try {
					openid = queryWeixinId(request,code ,dict.getAppId(), dict.getAppSecret());
					log.info("获取auth2-openid:"+openid);
				} catch (Exception e) {
					log.error("获取openid失败.",e);
					return "wx-xldPayError";
				}
			}
			
			BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
			
			List<WxMallGoods>  mallGoods = weixinMallMartService.queryMallGoods(brandId,1);// 1 虚拟商品
			if(mallGoods!=null&&mallGoods.size()>0){
				//组装二级菜单map
				for(WxMallGoods good:mallGoods){
					String subTypeId = good.getSubTypeId().toString();
					if(smallMap.containsKey(subTypeId)){
						WxMallTree tree = (WxMallTree) smallMap.get(subTypeId);
						List<WxMallGoods> goodList = tree.getGoodList();
						goodList.add(good);
						Collections.sort(goodList);//商品排序
					}else{
						List<WxMallGoods> goodList = new ArrayList<WxMallGoods>(); //子商品列表
						WxMallTree tree = new WxMallTree();
						tree.setBigId(good.getTypeId());
						tree.setSubId(good.getSubTypeId());
						tree.setBigOrder(good.getBigOrder());
						tree.setSmallOrder(good.getSmallOrder());
						tree.setBigName(good.getBigName());
						tree.setSubName(good.getSmallName());
						goodList.add(good);
						tree.setGoodList(goodList);
						smallMap.put(subTypeId, tree);
						Collections.sort(goodList);//商品排序
					}
				}
				//组装一级菜单map
				Iterator<Entry<String, WxMallTree>> iter = smallMap.entrySet().iterator();
				while(iter.hasNext()){
					Entry<String, WxMallTree> entry = iter.next();
					WxMallTree subTree = entry.getValue();
					String bigId = subTree.getBigId().toString();
					
					if(bigMap.containsKey(bigId)){
						WxMallTree bigTree = (WxMallTree) bigMap.get(bigId);
						List<WxMallTree> subTreeList =bigTree.getSubTreeList();
						subTreeList.add(subTree);
						Collections.sort(subTreeList);//子类排序
					}else{
						List<WxMallTree> subTreeList = new ArrayList<WxMallTree>();
						subTreeList.add(subTree);
						Collections.sort(subTreeList);//子类排序
						WxMallTree bigTree = new WxMallTree();
						bigTree.setBigId(subTree.getBigId());
						bigTree.setBigOrder(subTree.getBigOrder());
						bigTree.setBigName(subTree.getBigName());
						bigTree.setSubTreeList(subTreeList);//将子类加入父类
						bigMap.put(bigId, bigTree);
					}
				}
			
			}
			Object[] bigList = bigMap.values().toArray();
			Arrays.sort(bigList);//大类增加排序
			model.addAttribute("openid", openid);//加入request
			model.addAttribute("brandId", brandId);
			model.addAttribute("bigList", bigList);
			model.addAttribute("business", business);
			return "/mall/wx-mallMartIndex"; //跳转微信商城主页
		}catch(Exception ex){
			log.error("code happen error.",ex);
			return "wx-xldPayError";
		}
	}
	/**
	 * 我的订单页面
	 * @param brandId
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping(value="orderHistory", method = { RequestMethod.POST,RequestMethod.GET })
	public String orderHistory(
			HttpServletRequest request,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId", required = false) String openid,
			ModelMap model){
		logAna.info(brandId+";"+DateUtil.getDate()+";orderHistory;");
			try {
				WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
				if(dict==null){
					 dict=weixinMallMartService.queryMerchantParam(brandId) ;
				}
				model.addAttribute("dict", dict);
				List<Map<String,Object>>  orderList = weixinMallMartService.queryPaySuccessOrders(openid,brandId,6);//微信商城订单
				model.addAttribute("orderList", orderList);
				model.addAttribute("brandId", brandId);
				model.addAttribute("weixinId", openid);
				model.addAttribute("caFileIp", caFileIp);
				model.addAttribute("newPictureUrl", newPictureUrl);
			} catch (Exception e) {
				log.error("code happen error.",e);
			}
		return "/mall/wx-mallMartOrderHistory";
	}
	/**
	 * 查看订单详情
	 * @param request
	 * @param brandId
	 * @param openid
	 * @param outTradeNo
	 * @param model
	 * @return
	 */
	@RequestMapping(value="seeOrderInfo", method = { RequestMethod.POST,RequestMethod.GET })
	public String seeOrderInfo(
			HttpServletRequest request,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId", required = false) String openid,
			@RequestParam(value = "outTradeNo", required = false) String outTradeNo,
			ModelMap model){
		logAna.info(brandId+";"+DateUtil.getDate()+";seeOrderInfo;"+outTradeNo+";");
		try {
			WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
			if(dict==null){
				 dict=weixinMallMartService.queryMerchantParam(brandId) ;
			}
			model.addAttribute("dict", dict);
			Map<String,Object> order  = weixinMallMartService.queryPaySuccessSingleOrder(outTradeNo,brandId);
			String refund_no = (String) order.get("drawback_no");
			Long order_state =(Long)order.get("order_state");//6退款成功
			Long pay_type =(Long)order.get("pay_type");//2 积分支付
			if(StringUtils.isNotEmpty(refund_no)&&!"6".equals(order_state.toString())&&!"2".equals(pay_type.toString())){
				JSONObject json = new JSONObject();
				json.put("brandId", brandId.toString());
				json.put("outTradeNo", outTradeNo);
				json.put("transId",(String)order.get("transaction_id"));
				json.put("refundNo",refund_no);
				JSONObject resJson = weixinMallMartService.queryRefundState(json);

				if(resJson.getBoolean("success")){
					String state = resJson.getString("state");
					WxMallRefundRecord rr = new WxMallRefundRecord();
					rr.setBrandId(brandId);
					rr.setOutTradeNo(outTradeNo);
					if("8".equals(state)||"9".equals(state)||"11".equals(state)){
						rr.setDealState(5);
					}else if("4".equals(state)||"10".equals(state)){
						rr.setDealState(6);
					}else if("7".equals(state)){
						rr.setDealState(9);
					}else if("3".equals(state)||"5".equals(state)||"6".equals(state)){
						rr.setDealState(8);
					}else if("1".equals(state)||"2".equals(state)){
						rr.setDealState(10);
					}
					boolean flag = weixinMallMartService.insertRefundRecord(rr);
					log.info("查询退款后插入退款流水是否成功:"+flag);
				}
			}
			List<Map<String, Object>> refundList = weixinMallMartService.queryRefundRecodeList(outTradeNo);
			model.addAttribute("item", order);
			model.addAttribute("refundList", refundList);
			model.addAttribute("brandId", brandId);
			model.addAttribute("weixinId", openid);
			model.addAttribute("outTradeNo", outTradeNo);
			model.addAttribute("caFileIp", caFileIp);
			model.addAttribute("newPictureUrl", newPictureUrl);
		} catch (Exception e) {
			log.error("code happen error.",e);
			return "wx-xldPayError";
		}
		return "/mall/wx-mallMartEachOrder";
	}
	/**
	 * 还有几个没用的券
	 * @param request
	 * @param response
	 * @param brandId
	 * @param outTradeNo
	 */
	@RequestMapping(value="queryRemainCoupon", method = { RequestMethod.POST,RequestMethod.GET })
	public void queryRemainCoupon(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "outTradeNo", required = false) String outTradeNo){
		log.info("orderno:"+outTradeNo+" brandId:"+brandId);
		logAna.info(brandId+";"+DateUtil.getDate()+";queryRemainCoupon;");
		JSONObject json = new JSONObject();
		try {
			List<Map<String,Object>>  list = weixinMallMartService.queryRemainOrderCoupon(outTradeNo, brandId);//微信商城订单
			if(list!=null &&list.size()>0){
				json.put("size", list.size());
			}else{
				json.put("size", 0);
			}
		} catch (Exception e) {
			log.error("queryRemainCoupon error.",e);
			json.put("size", "0");
		}
		try {
			log.info("queryRemainCoupon:"+json.toString());
			response.getWriter().print(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			log.error("queryRemainCoupon error.",e);
		}
	}
	/**
	 * 更新退款原因
	 * @param request
	 * @param brandId
	 * @param refundDesc
	 * @param outTradeNo
	 * @param model
	 */
	@RequestMapping(value="updateRefundDesc", method = { RequestMethod.POST,RequestMethod.GET })
	public void updateRefundDesc(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "refundDesc", required = false) String refundDesc,
			@RequestParam(value = "outTradeNo", required = false) String outTradeNo,
			@RequestParam(value = "remainNum", required = false) Integer remainNum,
			ModelMap model){
		log.info("desc:"+refundDesc+" orderno:"+outTradeNo+" brandId:"+brandId);
		logAna.info(brandId+";"+DateUtil.getDate()+";updateRefundDesc;"+outTradeNo+";");
		JSONObject json = new JSONObject();
		try {
			
			List<Map<String,Object>>  list = weixinMallMartService.queryRemainOrderCoupon(outTradeNo, brandId);
			int count=0;
			if(remainNum>0&&(list!=null&&list.size()>0)){
				//更新券状态
				for(int i=0;i<remainNum;i++){
					Map<String,Object> obj = list.get(i);
					obj.put("merchantId", brandId);
					obj.put("status", 3);
					obj.put("availableQuantity", 0);
					Map<String,Object> jsonMap = new HashMap<String,Object>();
					jsonMap.put("coupon", obj);
					String input = JSONObject.fromObject(jsonMap).toString();
					log.info("updateCouponByIdAndMerchantIdUrl.req:"+input);
					
					String result = CommonUtil.postSendMessage(updateCouponByIdAndMerchantIdUrl, input, Constant.COUPONKEY);
					JSONObject requestObject = JSONObject.fromObject(result);
					String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
					log.info("updateCouponByIdAndMerchantIdUrl.res:"+data);
					JSONObject res=JSONObject.fromObject(data);
					if (res != null) { 
						Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
						String message = res.getString("message"); // 返回信息
						log.info("result message:"+message);
						if(success){
							 count++;
						}
					}
				}
			}
			if(remainNum==count){
				WxMallRefundRecord dict = new WxMallRefundRecord();
				dict.setBrandId(brandId);
				dict.setOutTradeNo(outTradeNo);
				dict.setRefundNum(remainNum);
				dict.setDescription(refundDesc);
				dict.setDealState(4);
//				dict.setDealUser("");
				boolean flag  = weixinMallMartService.updateDrawBackDesc(dict);
				
				if(flag){
					json.put("flag", true);
					json.put("message", "退款申请已提交，审核通过后会进入退款流程。");
				}else{
					json.put("flag", false);
					json.put("message", "发起退款失败，请稍后重试！");
				}
			}else{
				json.put("flag", false);
				json.put("message", "发起退款失败，请稍后重试！");
			}
		} catch (Exception e) {
			log.error("code happen error.",e);
			json.put("flag", false);
			json.put("message", "发起退款失败，请稍后重试！");
		}
		try {
			log.info("updateRefundDesc:"+json.toString());
			response.getWriter().print(json.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			log.error("code happen error.",e);
		}
	}
	
	
	/**
	 * 微信商城商品详情页面
	 * @param request
	 * @param brandId
	 * @param model
	 * @return
	 */
	@RequestMapping(value="goodInfoPage", method = { RequestMethod.POST,RequestMethod.GET })
	public String goodInfoPage(HttpServletRequest request,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "goodCode", required = false) Integer goodCode,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			@RequestParam(value = "code", required = false) String code,
			ModelMap model
			){
		logAna.info(brandId+";"+DateUtil.getDate()+";goodInfoPage;"+goodCode+";");
		/**
		 * 查询单个商品列表，加入request
		 */
		WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
		if(dict==null){
			 dict=weixinMallMartService.queryMerchantParam(brandId) ;
		}
		String openid=weixinId;
		if(StringUtils.isEmpty(openid)){
			try {
				openid = queryWeixinId(request,code ,dict.getAppId(), dict.getAppSecret());
				log.info("获取auth2-openid:"+openid);
			} catch (Exception e) {
				log.error("获取openid失败.",e);
				return "wx-xldPayError";
			}
		}
		
		BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
		
		model.addAttribute("business", business);
		model.addAttribute("dict", dict);
		WxMallGoods vo =  weixinMallMartService.queryEachGood( brandId, goodCode);
		model.addAttribute("goodvo", vo);
		model.addAttribute("brandId", brandId);
		model.addAttribute("weixinId", openid);
		model.addAttribute("caFileIp", caFileIp);
		model.addAttribute("newPictureUrl", newPictureUrl);
		return "/mall/wx-mallMartGoodInfo";//跳转商品详情页面
	}
	
	/**
	 * 跳转每一个购买页面
	 * @param request
	 * @param brandId
	 * @param cardCode 优惠券id
	 * @param activeId 活动id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="buyGood", method = { RequestMethod.POST,RequestMethod.GET })
	public String buyGood(HttpServletRequest request,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "goodCode", required = false) Integer goodCode,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			ModelMap model
			){
		try {
			logAna.info(brandId+";"+DateUtil.getDate()+";buyGood;"+goodCode+";");
			WxMallGoods vo =  weixinMallMartService.queryEachGood( brandId, goodCode);
			int paytype = vo.getPayment().intValue();
			log.info("消费方式paytype:"+paytype);
			List<MemberCardVO> cardList = new ArrayList<MemberCardVO>();
			/*
			 * 判断是否会员，不是会员加会员
			 * 
			 * */
			BusinessVO business = weixinManageService.getBusinessByBrandId(brandId);
			MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
			if (member == null) {
				member = new MemberVO();
				member.setBrandId(brandId);
				member.setWeixinId(weixinId);
				member.setIsMember(false);
				weixinPhonePageService.insertMember(member);
			}
			if (!member.getIsMember()) {
				boolean isAllowWeixinMember = registerPageService.isAllowWeixinMember(brandId);
				model.addAttribute("business", business);
				model.addAttribute("weixinId", weixinId);
				model.addAttribute("member", member);
				model.addAttribute("hasImage",weixinPhonePageService.hasImage(brandId,business.getSmallimageName()));
				model.addAttribute("toUrl",CommonUtil.parseRequest(request));//解析url，完成下次跳转
				model.addAttribute("isAllowWeixinMember", isAllowWeixinMember);
				return "/member/w-registerInfo";				
			}
			
			/**
			 * 积分支付，加载卡列表
			 */
			if(paytype!=1){
				//加载会员卡列表
				Map<String,Object> jsonMap = new HashMap<String,Object>();
				jsonMap.put("merchantId", brandId);
				jsonMap.put("mobile", member.getPhoneNo());
				String input = JSONObject.fromObject(jsonMap).toString();
				
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
							cardList.add(cardInfo);
						}
					}
				}
			}
			
			WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
			if(dict==null){
				 dict=weixinMallMartService.queryMerchantParam(brandId) ;
			}
			model.addAttribute("dict", dict);
			
			model.addAttribute("cardList", cardList);
			model.addAttribute("goodvo", vo);
			model.addAttribute("brandId", brandId);
			model.addAttribute("weixinId", weixinId);
			return "/mall/wx-mallMartBuy";//跳转购买页面
		
		} catch (Exception e) {
			log.error("code happen error.",e);
			return "wx-xldPayError";
		}
	}
	/**
	 * 校验是否需要输入密码
	 * @param request
	 * @param response
	 * @param brandId
	 * @param cardNo
	 * @throws IOException
	 */
	@RequestMapping(value="checkCardType",method = { RequestMethod.POST, RequestMethod.GET })
	public void checkCardType(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") int brandId,
			@RequestParam(value = "cardNo", required = false) String cardNo) throws IOException{
		JSONObject json = new JSONObject();
		try{
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("cardNo", cardNo);
			jsonMap.put("merchantId", brandId);
			String input = JSONObject.fromObject(jsonMap).toString();
			log.info("checkCardTypeUrl.req:"+input);
			
			String result = CommonUtil.postSendMessage(checkCardTypeUrl, input, Constant.COUPONKEY);
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			log.info("checkCardTypeUrl.res:"+data);
			JSONObject res=JSONObject.fromObject(data);
			if (res != null) { 
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				log.info("result message:"+message);
				if(success){
					Boolean isTradePassword = res.getBoolean("isTradePassword");
					if(isTradePassword){
						json.put("flag", "0");//需要输入密码
					}else{
						json.put("flag", "1");//不需要密码
					}
				}else{
					json.put("flag", "0");
				}
			}
		}catch(Exception e){
			log.error("checkCardTypeUrl error.",e);
			json.put("flag", "0");
		}
		response.getWriter().print(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	
	
	/**
	 * 检查是否有限制
	 * @param request
	 * @param response
	 * @param brandId
	 * @param integral
	 * @param cardNo
	 * @param password
	 * @throws IOException 
	 */
	@RequestMapping(value="checkCardTrade",method = { RequestMethod.POST, RequestMethod.GET })
	public void checkCardTrade(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") int brandId,
			@RequestParam(value = "integral", required = false) int integral,//积分
			@RequestParam(value = "cardNo", required = false) String cardNo,
			@RequestParam(value = "pwd", required = false) String password) throws IOException{
		JSONObject json = new JSONObject();
		try{
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("cardNo", cardNo);
			jsonMap.put("password", password );
			jsonMap.put("amount", integral);
			jsonMap.put("merchantId", brandId);
			jsonMap.put("transCode", "1002");
			String input = JSONObject.fromObject(jsonMap).toString();
			log.info("checkCardTradeUrl.json:"+input);
			
			String result = CommonUtil.postSendMessage(checkCardTradeUrl, input, Constant.COUPONKEY);
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			log.info("checkCardTradeUrl.res:"+data);
			JSONObject res=JSONObject.fromObject(data);
			if (res != null) { 
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				log.info("result message:"+message);
				if(success){
					json.put("flag", "0");
				}else{
					json.put("flag", "1");
					json.put("message", "此卡限制交易");
				}
			}
		}catch(Exception e){
			json.put("flag", "1");
			json.put("message", "此卡暂时不能提供交易");
		}
		response.getWriter().print(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}
	
	
	/**
	 * 购买前，加同步锁，检查购买规则
	 * @param request
	 * @param response
	 * @param brandId 商户id
	 * @param weixinId 微信id
	 * @param goodId 商品id
	 * @param num 购买个数
	 * @param payType 支付类型
	 * @throws Exception 
	 */
	@RequestMapping(value="checkBuy",method = { RequestMethod.POST, RequestMethod.GET })
	public synchronized void checkBuy(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			@RequestParam(value = "goodId", required = false) Integer goodId,
			@RequestParam(value = "num", required = false) Integer num,
			@RequestParam(value = "payType", required = false) Integer payType,
			@RequestParam(value = "buy_limit_is", required = false) String buy_limit_is,
			@RequestParam(value = "buy_limit_type", required = false) String buy_limit_type,
			@RequestParam(value = "buy_limit_num", required = false) Integer buy_limit_num
			) throws Exception{
		JSONObject json = new JSONObject();
		json.put("flag", "0");
		if("1".equals(buy_limit_is.trim())){//1 限购
			boolean flag = false;
			if("1".equals(buy_limit_type.trim())){ //1 每天限购
				flag = true; //每天限制
			}
			int buyNum = weixinMallMartService.queryUserBuyCount(weixinId, brandId, flag, goodId);
			if((num+buyNum)>buy_limit_num){
				json.put("flag", "1");
				json.put("message", "超过购买限制");
			}
		}
		//查商品库存量
		int remainNum = weixinMallMartService.selectRemainNum(goodId);
		if(remainNum<=0){
			json.put("flag", "1");
			json.put("message", "商品已售罄");
		}else if(num>remainNum){
			json.put("flag", "1");
			json.put("message", "商品库存不足了");
		}
		log.info("brandId:"+brandId+" weixinId:"+weixinId+" goodId:"+goodId+" num:"+num+" payType:"+payType+" json:"+json.toString());
		response.getWriter().print(json.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}
	/**
	 * 消费积分方法
	 * @param cardNo
	 * @param integral
	 * @param password
	 * @param brandId
	 * @return
	 * @throws Exception
	 */
	public int consumeIntegral(String cardNo,Integer integral,String password,String brandId){
		try{
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("cardNo", cardNo);
			jsonMap.put("integral", integral);
			jsonMap.put("password", password );
			jsonMap.put("merchantId", brandId);
			String input = JSONObject.fromObject(jsonMap).toString();
			log.info("consumeIntegral.json:"+input);
			
			String result = CommonUtil.postSendMessage(consumeIntegralUrl, input, Constant.COUPONKEY);
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			log.info("consumeIntegral.res:"+data);
			JSONObject res=JSONObject.fromObject(data);
			if (res != null) {
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				if(success){
					int masterId = res.getInt("masterId"); 
					log.info("返回message:"+message+" masterId:"+masterId);
					return masterId;
				}else{
					return -1;
				}
			}
		}catch(Exception e){
			log.error("code happen error.",e);
		}
		return -1;
	}
	/**
	 * 积分消费，单独走消费方法
	 * @param request
	 * @param response
	 * @param brandId
	 * @param weixinId
	 * @param goodInfo
	 * @param totalFee
	 * @param integral
	 * @param goodId
	 * @param num
	 * @param payType
	 * @throws IOException 
	 */
	@RequestMapping(value="integralPay",method = { RequestMethod.POST, RequestMethod.GET })
	public void integralPay(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			@RequestParam(value = "goodInfo", required = false) String goodInfo,
			@RequestParam(value = "integral", required = false) Long integral,//积分
			@RequestParam(value = "goodId", required = false) Long goodId,
			@RequestParam(value = "num", required = false) Long num,
			@RequestParam(value = "payType", required = false) Long payType,
			@RequestParam(value = "cardNo", required = false) String cardNo,
			@RequestParam(value = "pwd", required = false) String password
			) throws IOException{
		logAna.info(brandId+";"+DateUtil.getDate()+";integralPay;"+goodId+";"+num+";");
		JSONObject jsonRes = new JSONObject();
		try{
			MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
			log.info("brandId:"+brandId+"\t goodInfo:"+goodInfo+"\t integral:"+integral+"\t goodId:"+goodId+"\t num:"+num+"\t paytype:"+payType+"\t cardNo:"+cardNo+"\t password:"+password);
			WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
			if(dict==null){
				 dict=weixinMallMartService.queryMerchantParam(brandId) ;
			}
			//返回package json
			String outTradeNo = DateUtil.get12Date()+CommonUtil.CreateNoncestr();//生成商户订单号，前14位时间，后面16位随机数
			String nonceTr = CommonUtil.CreateNoncestr();
			integral =integral==null?0L:integral;
			WxMallOrder wxorder  = new WxMallOrder();
			wxorder.setAppId(dict.getAppId());
			wxorder.setMobile(member.getPhoneNo());
			wxorder.setOpenId(weixinId);
			wxorder.setPartner(dict.getPartnerId());
			wxorder.setTime_end(DateUtil.getDate());
			wxorder.setIntegral(integral);//积分
			wxorder.setBuyNum(num);
			wxorder.setOut_trade_no(outTradeNo);
			wxorder.setNonceTr(nonceTr);
			wxorder.setFee_type(1L);
			wxorder.setTrade_mode(1L);
			wxorder.setSign_type("MD5");
			wxorder.setBank_type("WX");
			wxorder.setPayType(payType);//支付方式
			wxorder.setProductInfo(goodInfo);
			wxorder.setGoodsId(goodId);
			wxorder.setBrandId(brandId.longValue());
			wxorder.setSource(6L);//微信为6
			wxorder.setDeliverState(1L);//发货状态
			wxorder.setCardNo(cardNo);
			wxorder.setPassword(password);
			
			WxMallOrderCouponState pcsvo = new WxMallOrderCouponState();//记录券状态
			pcsvo.setMobile(member.getPhoneNo());
			pcsvo.setIntegral(integral); //消费积分
			pcsvo.setCounter(num);
			pcsvo.setGoodsId(goodId); //买的什么商品
			pcsvo.setBrandId(brandId.longValue());
			pcsvo.setSendState(false);//先暂时插入false
			pcsvo.setOpenId(weixinId);
			pcsvo.setOutTradeNo(outTradeNo);
			
			int masterId = consumeIntegral(cardNo, integral.intValue(), password, brandId.toString());//消积分方法
			if(masterId!=-1){
				wxorder.setJifenState(masterId!=-1?true:false);
				wxorder.setMasterId(new Long(masterId));
				wxorder.setTrade_state("0");
				wxorder.setOrderState(1L);
				pcsvo.setTradeState("0");
				/**  生成券信息，生成券状态信息，扣除商品库存  */
				String flg = buyCouponBusiness("0",wxorder,pcsvo,true);
				
				jsonRes.put("flag", flg);
				jsonRes.put("outTradeNo", outTradeNo);
			}else{
				jsonRes.put("flag", "fail");
			}
			
		}catch(Exception ex){
			log.error("code happen error.",ex);
			jsonRes.put("flag", "fail");
		}finally{
			response.getWriter().write(jsonRes.toString());
			response.getWriter().flush();
			response.getWriter().close();
		}
	}
	
	
	/**
	 * 获取用户购买信息，组装商品信息
	 * @param request
	 * @param response
	 * @param mobile 手机号
	 * @param productInfo 商品title
	 * @param totalFee 总价
	 * @param nonceTr 随机数
	 * @param cardId 优惠券id
	 */
	@RequestMapping(value="obtainGoodInfo",method = { RequestMethod.POST, RequestMethod.GET })
	public void obtainGoodInfo(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			@RequestParam(value = "goodInfo", required = false) String goodInfo,
			@RequestParam(value = "totalFee", required = false) Double totalFee,
			@RequestParam(value = "integral", required = false) Long integral,//积分
			@RequestParam(value = "goodId", required = false) Long goodId,
			@RequestParam(value = "num", required = false) Long num,
			@RequestParam(value = "payType", required = false) Long payType
			){
		try {
			MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
			log.info("brandId:"+brandId+"\t goodInfo:"+goodInfo+"\t totalFee:"+totalFee+"\t integral:"+integral+"\t goodId:"+goodId+"\t num:"+num+"\t paytype:"+payType);
			WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
			if(dict==null){
				 dict=weixinMallMartService.queryMerchantParam(brandId) ;
			}
			//返回package json
			String spbill_create_ip=request.getRemoteAddr();
			String outTradeNo = DateUtil.get12Date()+CommonUtil.CreateNoncestr();//生成订单号，前12位时间，后面16位随机数
			String nonceTr = CommonUtil.CreateNoncestr();
			String timeStamp=Long.toString(new Date().getTime()/1000);
			integral =integral==null?0L:integral;
			logAna.info(brandId+";"+DateUtil.getDate()+";obtainGoodInfo;"+goodId+";"+num +";"+totalFee+";"+outTradeNo+";");
			WxMallOrder wxorder  = new WxMallOrder();
			wxorder.setAppId(dict.getAppId());
			wxorder.setMobile(member.getPhoneNo());
			wxorder.setPartner(dict.getPartnerId());
			wxorder.setTotal_fee(totalFee);//
			wxorder.setIntegral(integral);//积分
			wxorder.setBuyNum(num);
			wxorder.setOut_trade_no(outTradeNo);
			wxorder.setNonceTr(nonceTr);
			wxorder.setFee_type(1L);
			wxorder.setTrade_mode(1L);
			wxorder.setSign_type("MD5");
			wxorder.setBank_type("WX");
			wxorder.setPayType(payType);//支付方式
			wxorder.setProductInfo(goodInfo);
			wxorder.setGoodsId(goodId);
			wxorder.setBrandId(brandId.longValue());
			wxorder.setSource(6L);//微信为6

			weixinMallMartService.insertMallOrder(wxorder);//生成订单支付流水信息
			
			//生成微信支付信息
			String path = request.getContextPath();
			String notify_url = serverIp+path+"/weixin/"+brandId+"/"+num+"/"+member.getPhoneNo()+"/"+goodId+"/"+integral+"/mallPayResult.do"; 
			log.info("notify_url:"+notify_url);
			String packageInfo ="";
			JSONObject jsonRes = new JSONObject();
			DecimalFormat df = new DecimalFormat("#.0");
			String totalfee100=df.format(totalFee*100);
			if("2".equals(String.valueOf(dict.getV2_v3()))){
				wxpayUtil.SetParameter("bank_type", "WX");
				wxpayUtil.SetParameter("body", goodInfo);
				wxpayUtil.SetParameter("partner", dict.getPartnerId());
				wxpayUtil.SetParameter("out_trade_no",outTradeNo );
				wxpayUtil.SetParameter("total_fee", totalfee100.substring(0, totalfee100.length()-2));
				wxpayUtil.SetParameter("fee_type", "1");
				wxpayUtil.SetParameter("notify_url", notify_url); //notify_url
				wxpayUtil.SetParameter("spbill_create_ip", spbill_create_ip);
				wxpayUtil.SetParameter("input_charset", "UTF-8");
				wxpayUtil.setAppid(dict.getAppId());
				wxpayUtil.setAppkey(dict.getAppKey());
				wxpayUtil.setNoncestr(nonceTr);
				wxpayUtil.setPartnerkey(dict.getPartnerKey());
				wxpayUtil.setProductid(dict.getPartnerId());
				wxpayUtil.setTimeStamp(timeStamp);
				packageInfo = wxpayUtil.CreateBizPackage();
				log.info( "v2-packageInfo="+packageInfo);
				jsonRes.put("v2v3flag", "2");
			}else{
				
				String apikey =dict.getPartnerKey();//"sDGYszkxbbBMai7G8Vap7FL4gjwNXhvz";
				String mch_id=dict.getPartnerId();//"10063341";
				String openid=weixinId;
				String unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
				wxPayUtilV3.SetParameter("appid", dict.getAppId());
				wxPayUtilV3.SetParameter("body", goodInfo);
				wxPayUtilV3.SetParameter("mch_id", mch_id);
				wxPayUtilV3.SetParameter("nonce_str", nonceTr);
				wxPayUtilV3.SetParameter("notify_url", notify_url);
				wxPayUtilV3.SetParameter("openid", openid);
				wxPayUtilV3.SetParameter("out_trade_no", outTradeNo);
				wxPayUtilV3.SetParameter("spbill_create_ip",spbill_create_ip);
				wxPayUtilV3.SetParameter("total_fee", totalfee100.substring(0, totalfee100.length()-2));
				wxPayUtilV3.SetParameter("trade_type", "JSAPI");
				wxPayUtilV3.setAppid(dict.getAppId());
				wxPayUtilV3.setApikey( apikey);
				String sign = wxPayUtilV3.createSign(wxPayUtilV3.packageMap);
				wxPayUtilV3.SetParameter("sign", sign);
				String pre_pay_xml = wxPayUtilV3.createXml();
				log.info("v3-pre_pay_xml="+pre_pay_xml);
				
				String pre_pay_res = CommonUtil.postMessage2(unifiedorder, pre_pay_xml);
				log.info("v3-pre_pay_res:"+pre_pay_res);
				String prepay_id="";
				Map<String,String> refundMap = CommonUtil.doXMLParse(pre_pay_res);
	            String return_code= refundMap.get("return_code");
                if("SUCCESS".equals(return_code)){
                	String result_code= refundMap.get("result_code");
                	if("SUCCESS".equals(result_code)){
                		prepay_id=refundMap.get("prepay_id");
                	}
                }
				log.info("v3-prepay_id="+prepay_id);
				wxPayUtilV3.setPrepay_id(prepay_id);
				packageInfo = wxPayUtilV3.CreateBizPackage();
				log.info("v3-packageInfo="+ packageInfo);
				jsonRes.put("v2v3flag", "3");
			}
			jsonRes.put("outTradeNo", outTradeNo);
			jsonRes.put("order", packageInfo);
			response.getWriter().write(jsonRes.toString());
			response.getWriter().flush();
			response.getWriter().close();
		
		} catch (WeixinRuntimeException e) {
			log.error("code happen error.",e);
		} catch (IOException e) {
			log.error("response code happen error.",e);
		} catch (Exception e) {
			log.error("code happen error.",e);
		}
	}
	/**组装参数*/
	public String packageOrderBean(HttpServletRequest request,WxMallOrder order,WxMallOrderCouponState pcsvo,String flag) throws Exception{
		String return_result_code = "";
		String messageIn = "";
		String tradeState="";
		InputStream is = null;
		if("2".equals(flag)){
			Map<String,String> map = CommonUtil.convertString(request);
			BeanUtils.copyProperties(order,map);
			messageIn = IOUtil.inputStream2String(request.getInputStream());
			log.info("腾讯返回支付信息xml："+messageIn);
		
			tradeState = order.getTrade_state();//支付成功状态,0成功
			log.info("读取tx xml信息 start.");
			is = new ByteArrayInputStream(messageIn.getBytes());
			Document doc = new SAXReader().read(is);
			String OpenId=doc.selectSingleNode("/xml/OpenId").getText();
			String AppId=doc.selectSingleNode("/xml/AppId").getText();
			String IsSubscribe=doc.selectSingleNode("/xml/IsSubscribe").getText();
			String NonceStr=doc.selectSingleNode("/xml/NonceStr").getText();
			String AppSignature=doc.selectSingleNode("/xml/AppSignature").getText();
			log.info("读取tx xml信息 end.");
			//将支付结果加入bean
			order.setAppId(AppId);
			order.setOpenId(OpenId);
			order.setIsSubscribe("1".equals(IsSubscribe)?true:false);
			order.setNonceTr(NonceStr);
			order.setAppSignature(AppSignature);
			order.setOrderState(1L);
			
			pcsvo.setTotalFee(order.getTotal_fee());
			pcsvo.setTradeState(tradeState);//支付状态
			pcsvo.setTransactionId(order.getTransaction_id());
			pcsvo.setSendState(false);//先暂时插入false
			pcsvo.setOpenId(OpenId);
			pcsvo.setOutTradeNo(order.getOut_trade_no());
			return_result_code= "2";
		}else if("3".equals(flag)){
			messageIn = IOUtil.inputStream2String(request.getInputStream());
			log.info("【接收到的notify通知】："+messageIn);
			log.info("读取tx xml信息 start.");
			is = new ByteArrayInputStream(messageIn.getBytes());
			Document doc = new SAXReader().read(is);
			String return_code=doc.selectSingleNode("/xml/return_code").getText();
			if("SUCCESS".equals(return_code)){
				String result_code=doc.selectSingleNode("/xml/result_code").getText();
				if("SUCCESS".equals(result_code)){
					String OpenId=doc.selectSingleNode("/xml/openid").getText();
					String AppId=doc.selectSingleNode("/xml/appid").getText();
					String IsSubscribe=doc.selectSingleNode("/xml/is_subscribe").getText();
					String NonceStr=doc.selectSingleNode("/xml/nonce_str").getText();
					String transaction_id=doc.selectSingleNode("/xml/transaction_id").getText();
					String out_trade_no=doc.selectSingleNode("/xml/out_trade_no").getText();
					String time_end=doc.selectSingleNode("/xml/time_end").getText();
					String fee_type=doc.selectSingleNode("/xml/fee_type").getText();
					String sign=doc.selectSingleNode("/xml/sign").getText();
					String mch_id=doc.selectSingleNode("/xml/mch_id").getText();
					tradeState = "0";//支付成功状态,0成功
					order.setTrade_state(tradeState);
					order.setPartner(mch_id);
					order.setTransaction_id(transaction_id);
					order.setSign(sign);
					order.setTime_end(time_end);
					order.setOut_trade_no(out_trade_no);
					order.setAppId(AppId);
					order.setOpenId(OpenId);
					order.setIsSubscribe("1".equals(IsSubscribe)?true:false);
					order.setNonceTr(NonceStr);
					order.setOrderState(1L);
					
					pcsvo.setTotalFee(order.getTotal_fee());
					pcsvo.setTradeState(tradeState);//支付状态
					pcsvo.setTransactionId(order.getTransaction_id());
					pcsvo.setSendState(false);//先暂时插入false
					pcsvo.setOpenId(OpenId);
					pcsvo.setOutTradeNo(order.getOut_trade_no());
					return_result_code= "3";
				}
			}
		}
		return return_result_code;
	}
	
	/**
	 *  腾讯通知支付结果url
	 * @param request
	 * @param response
	 * @param brandId
	 * @param num 数量，传给李斌接口
	 * @param mobile 电话
	 * @param cardId 优惠券id
	 */
	@RequestMapping(value="/{num}/{mobile}/{goodId}/{integral}/mallPayResult",method = { RequestMethod.POST,RequestMethod.GET })
	public synchronized void mallPayResult(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Long brandId,
			@PathVariable(value = "num") Long num,
			@PathVariable(value = "mobile") String mobile,
			@PathVariable(value = "goodId") Long goodId,
			@PathVariable(value = "integral") Long integral,
			ModelMap model){
			WxMallOrder order = new WxMallOrder();
			WxMallOrderCouponState pcsvo = new WxMallOrderCouponState();//记录券状态
			String tradeState="";
			InputStream is = null;
			try{
				WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
				if(dict==null){
					 dict=weixinMallMartService.queryMerchantParam(brandId.intValue()) ;
				}
				String msg = packageOrderBean(request,order,pcsvo,String.valueOf(dict.getV2_v3()));//复制属性
				
				model.addAttribute("openid", order.getOpenId());//加入session
				log.info("读取tx xml信息 end.");
				logAna.info(brandId+";"+DateUtil.getDate()+";mallPayResult;"+goodId+";"+num+";"+mobile+";"+order.getTotal_fee()+";"+order.getOut_trade_no()+";"+order.getTrade_state()+";"+order.getOpenId()+";");
				order.setBrandId(brandId);
				tradeState=order.getTrade_state();
				
				pcsvo.setMobile(mobile);
				pcsvo.setIntegral(integral); //消费积分
				pcsvo.setCounter(num);
				pcsvo.setGoodsId(goodId); //买的什么商品
				pcsvo.setBrandId(brandId);
				pcsvo.setSendState(false);//先暂时插入false
				SortedMap<String, String> packageMap = new TreeMap<String,String>();
				String return_to_tx = "";
				if("2".equals(msg)){
					return_to_tx="success";//返回给腾讯success后，腾讯不再继续通知支付结果
				}else if("3".equals(msg)){
					 packageMap.put("return_code", "SUCCESS");
					 packageMap.put("return_msg", "OK");
					 return_to_tx=CommonUtil.ArrayToXml(packageMap);
				}
				response.getWriter().write(return_to_tx);
				log.info("通知腾讯success");
				
			}catch(IOException ex){
				log.error("支付结果通知异常.", ex);
			} catch (Exception e) {
				log.error("code error.", e);
			}finally{
				/**  更新订单信息，生成券信息，生成券状态信息，扣除商品库存 */
				try {
					buyCouponBusiness(tradeState,order,pcsvo,false);
				} catch (Exception e) {
					log.error("更新订单信息，生成券信息，生成券状态信息，扣除商品库存 code happen error.",e);
				}
				if(is!=null){
					try {
						is.close();
					} catch (IOException e) {
						log.error("code happen error.",e);
						e.printStackTrace();
					}
				}
			}
	}

	/**
	 * 买券业务，减少商品库存，消积分
	 * @param tradeState 支付状态
	 * @param order 订单
	 * @param pcsvo 券状态
	 * @param inflag true 积分支付
	 * @throws Exception
	 */
	public String buyCouponBusiness(String tradeState,WxMallOrder order,WxMallOrderCouponState pcsvo,boolean inflag) throws Exception{
		WxMallMerchantDict dict=weixinMallMartService.queryMerchantParam(order.getBrandId().intValue()) ;
		List<WxMallOrderCoupon> couponList = new ArrayList<WxMallOrderCoupon>();
		try{
			if("0".equals(tradeState)&&!inflag){
				order.setDeliverState(0L);//发货状态
				try{
					if("2".equals(dict.getV2_v3())){
						String token = weixinMallMartService.getAcessTokenByDB(dict);//获取access_token
						Map<String,String> ma = new HashMap<String,String>();
						ma.put("appid",dict.getAppId() );
						ma.put("openid",order.getOpenId() );
						ma.put("transid",order.getTransaction_id() );
						ma.put("out_trade_no", order.getOut_trade_no());
						String rs = deliverState(token,ma,dict.getAppKey());//立即发货
						String errmsg = JSONObject.fromObject(rs).getString("errmsg");
						log.info("发货:"+errmsg+" orderNo:"+order.getOut_trade_no());
						if("ok".equals(errmsg)){
							order.setDeliverState(1L);//发货状态 1 成功 0 失败
						}
					}else{
						//v3版是否有发货接口
						order.setDeliverState(1L);
					}
				}catch(Exception ex){
					log.error("发货异常:",ex);
				}
			}
			if("0".equals(tradeState)){
				//查询成功支付的订单 
				WxMallOrder jforder =weixinMallMartService.queryIntegralMallOrder(order.getOut_trade_no(),order.getBrandId().intValue());
				int innum = 0; //纯积分消费插入
				int jfupNum= 0; //纯积分消费更新
				int update= 0;
				// inflag ==true 是纯积分消费
				if(inflag){
					if(jforder==null){
						innum = weixinMallMartService.insertIntegralMallOrder(order);
					}else{
						jfupNum = weixinMallMartService.updateJifenState(order);
					}
					log.info("插入,更新积分订单信息:"+(innum>0)+","+(jfupNum>0));
				}else{
					update = weixinMallMartService.updatePayResultInfo(order);//更新数据库支付信息
					log.info("更新订单信息:"+(update>0));
				}
				log.info("是否应该减少库存:"+((update>0||(innum>0&&order.getJifenState()))&&jforder==null));
				if((update>0||(innum>0&&order.getJifenState()))&&jforder==null){
					/**
					 * 减少库存代码
					 */
					boolean kcflag = weixinMallMartService.updateOrderGoodRemain(pcsvo.getCounter().intValue(), pcsvo.getGoodsId().intValue());
					log.info("更新商品库存："+kcflag);
					/**
					 * 发库存预警短信
					 */
					if(kcflag){
						Map<String,Object> ma = weixinMallMartService.queryGoodInventory(pcsvo.getGoodsId().intValue());
						Integer sendsmsnum = (Integer) ma.get("send_sms_num");
						Integer remainnum = (Integer)ma.get("remain_num");
						Integer totalnum = (Integer)ma.get("total_num");
						Integer warningtype = (Integer)ma.get("warning_type");
						Float warningvalue = (Float)ma.get("warning_value");
						if("1".equals(warningtype.toString())&& warningvalue>0){
							warningvalue= totalnum*warningvalue/100;
						}
						String warnmobile = (String)ma.get("warning_mobile");
						String warncontent = (String)ma.get("warning_content");
						if((sendsmsnum==null || sendsmsnum<1)&& warningvalue>0 &&(remainnum<=warningvalue)){
							//发送短信内容
							String jsonpar = "{'mobile':'" + warnmobile + "','content':'" + warncontent
					                 + "','merchant_id':'"+order.getBrandId()+"','source':'8'}";
							
							try {
								CommonUtil.postSendMessage(smsUrl,jsonpar,Constant.KEY.toString());//发送短信
								weixinMallMartService.updateSendSmsNum(pcsvo.getGoodsId().intValue());//更新短信次数
							} catch (Exception e) {
								log.error("code happen error.",e);
							}
						}
					}
				}
				//查询是否发过券
				boolean flag = weixinMallMartService.queryHasOrderCoupon(order.getOut_trade_no());
				Integer couponId = weixinMallMartService.queryGoodsCouponId(pcsvo.getGoodsId()); //查询商品对应券Id
				log.info("是否有券了:"+flag+" couponId:"+couponId);
				if(!flag && "0".equals(tradeState)&& couponId!=-1){ //无券信息才执行
					log.info("生成券信息，入库，调用李斌接口获取券的信息.");
					Map<String,Object> jsonMap = new HashMap<String,Object>();
					jsonMap.put("count", pcsvo.getCounter());
					jsonMap.put("merchantId", order.getBrandId());
					jsonMap.put("mobile", pcsvo.getMobile());
					jsonMap.put("couponId", couponId);//券id
					jsonMap.put("couponCategory", 3);
					String input = JSONObject.fromObject(jsonMap).toString();
					log.info("payResult.json:"+input);
					
					String result = CommonUtil.postSendMessage(optainTicketsUrl, input, Constant.COUPONKEY);
					JSONObject requestObject = JSONObject.fromObject(result);
					String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
					JSONObject res=JSONObject.fromObject(data);
					if (res != null) { 
						Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
						String message = res.getString("message"); // 返回信息
						log.info("result message:"+message+"\t outTradeNo:"+order.getOut_trade_no());
						if (success) {// 成功
							pcsvo.setSendState(true);
							StringBuffer sb = new StringBuffer();
							JSONArray array = res.getJSONArray("couponList");
							for(Iterator iter = array.iterator();iter.hasNext();){
								JSONObject jsonObject = (JSONObject) iter.next();
								WxMallOrderCoupon coupon = new WxMallOrderCoupon();
								coupon.setBrandId(order.getBrandId());
								String resId = jsonObject.getString("id");
								sb.append(resId).append(",");
								coupon.setCouponId(Long.parseLong(resId));
								coupon.setGoodsId(pcsvo.getGoodsId());
								coupon.setMobile(pcsvo.getMobile());
								coupon.setOpenId(order.getOpenId());
								coupon.setOutTradeNo(order.getOut_trade_no());
								coupon.setBrandId(order.getBrandId());
								couponList.add(coupon);
							}
							log.info("返回coupon_no:"+sb);
							log.info("生成券数量:"+couponList.size());
							//生成券信息入库
							weixinMallMartService.inserMallCouponInfo(couponList);
						}
					}
					return "success";
				}
			}
			return "fail";
		}catch(Exception e){
			log.info("code happen error.",e);
			throw new UserErrorException(e.getMessage());
		}finally{
			try {
				boolean hasCouponState = weixinMallMartService.queryHasBindCouponState(order.getOut_trade_no());
				if(!hasCouponState){
					int a =	weixinMallMartService.insertCouponState(pcsvo);//插入券状态
					log.info("插入券状态成功:"+(a>0));
					if(!pcsvo.getSendState()){
						String content = "用户手机:"+pcsvo.getMobile()+"\n"+
								"购买商品Id:"+pcsvo.getGoodsId()+"\n"+
								"购买数量:"+pcsvo.getCounter()+"\n"+
								"购买金额:"+pcsvo.getTotalFee()+"分\n"+
								"购买积分:"+pcsvo.getIntegral()+"\n"+
								"消积分是否成功:"+order.getJifenState()+"\n"+
								"商户订单号:"+pcsvo.getOutTradeNo()+"\n"+
								"银行订单号:"+pcsvo.getTransactionId()+"\n"+
								"商户brandId:"+pcsvo.getBrandId()+"\n"+
								"用户wxId:"+pcsvo.getOpenId()+"\n";
						String[] targetMails = dict.getMails().split(",");
						//失败发送邮件
						MailUtil.sendMail(Arrays.asList(targetMails),dict.getSubject(),content);
						//发送短信内容
						String jsonpar = "{'mobile':'" + pcsvo.getMobile() + "','content':'" + dict.getContents()
				                 + "','merchant_id':'"+pcsvo.getBrandId()+"','source':'8'}";
						
						CommonUtil.postSendMessage(smsUrl,jsonpar,Constant.COUPONKEY);//发送短信
					}
				}else{
					if(pcsvo.getSendState()){
						weixinMallMartService.updateWeixin_pay_coupon_state(pcsvo);//更新发券状态
					}
				}
			} catch (Exception e) {
				log.error("插入券状态 code happen error.",e);
			}
		}
	}
	
	/**
	 * 微信商城跳转成功页面
	 * @param request
	 * @param brandId
	 * @param num 购买数量
	 * @param totalFee 总价
	 * @param cardCode 优惠券id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/{weixinId}/{outTradeNo}/goMallSuccessPage", method = { RequestMethod.POST, RequestMethod.GET })
	public String goMallSuccessPage(HttpServletRequest request,
			@PathVariable(value = "brandId") Integer brandId,
			@PathVariable(value = "weixinId") String weixinId,
			@PathVariable(value = "outTradeNo") String outTradeNo,
			@RequestParam(value = "code", required = false) String code, 
			ModelMap model
			){
		logAna.info(brandId+";"+DateUtil.getDate()+";goMallSuccessPage;"+outTradeNo+";");
		log.info("outTradeNo:"+outTradeNo);
		try {
			WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
			if(dict==null){
				 dict=weixinMallMartService.queryMerchantParam(brandId) ;
			}
			model.addAttribute("dict", dict);
			
			String openid=weixinId;
			if(StringUtils.isEmpty(openid)){
				try {
					openid = queryWeixinId(request,code ,dict.getAppId(), dict.getAppSecret());
					log.info("获取auth2-openid:"+openid);
				} catch (Exception e) {
					log.error("code happen error.",e);
				}
			}
			
			WxMallOrder order = weixinMallMartService.queryMallOrder(outTradeNo,brandId);
			model.addAttribute("order", order);
			model.addAttribute("brandId", brandId);
			model.addAttribute("weixinId", openid);
		} catch (Exception e) {
			log.error("code happen error.",e);
			return "wx-xldPayError";
		}
		return "/mall/wx-mallMartPaySuccess";//跳转购买页面
	}
	@RequestMapping(value="/{weixinId}/goMallErrorPage",method = { RequestMethod.POST,
			RequestMethod.GET })
	public String goMallErrorPage(HttpServletRequest request,
			@PathVariable(value = "brandId") Integer brandId,
			@PathVariable(value = "weixinId") String weixinId,
			ModelMap model){
		model.addAttribute("brandId", brandId);
		model.addAttribute("weixinId", weixinId);
		logAna.info(brandId+";"+DateUtil.getDate()+";goMallErrorPage;");
		return "/mall/wx-mallMartIntegralPayErr";
	}
	/**
	 * 发货通知
	 * @param token
	 * @return
	 * @throws WeixinRuntimeException 
	 */
	public String deliverState(String token,Map<String,String> map,String appkey) throws WeixinRuntimeException{
		String url="https://api.weixin.qq.com/pay/delivernotify?access_token="+token;
		HashMap<String,String> mp = new HashMap<String,String>();
		mp.put("appid",map.get("appid"));
		mp.put("openid",map.get("openid"));
		mp.put("transid",map.get("transid"));
		mp.put("out_trade_no",map.get("out_trade_no"));
		mp.put("deliver_timestamp",String.valueOf(new Date().getTime()/1000));
		mp.put("deliver_status","1");
		mp.put("deliver_msg","ok");
		mp.put("app_signature",CommonUtil.createSign(mp,appkey));//生成签名
		mp.put("sign_method", "sha1");
		log.info("发货json:"+JSONObject.fromObject(mp).toString());
		String content = JSONObject.fromObject(mp).toString();
		String result = HttpClientCommonSSL.commonPostStream(url, content);
		log.info("发货返回:"+result);
		return result;
	}
	/**  积分消费方法 */
	@RequestMapping(value="jiFenXiaoFei",method = { RequestMethod.POST,RequestMethod.GET })
	public void jiFenXiaoFei(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			@RequestParam(value = "goodInfo", required = false) String goodInfo,
			@RequestParam(value = "totalFee", required = false) Double totalFee,
			@RequestParam(value = "integral", required = false) Long integral,//积分
			@RequestParam(value = "goodId", required = false) Long goodId,
			@RequestParam(value = "num", required = false) Long num,
			@RequestParam(value = "payType", required = false) Long payType,
			@RequestParam(value = "cardNo", required = false) String cardNo,
			@RequestParam(value = "pwd", required = false) String password
			) throws IOException{
		logAna.info(brandId+";"+DateUtil.getDate()+";jiFenXiaoFei;"+goodId+";"+num+";");
		JSONObject jsonRes = new JSONObject();
		try{
			MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
			log.info("brandId:"+brandId+"\t goodInfo:"+goodInfo+"\t integral:"+integral+"\t goodId:"+goodId+"\t num:"+num+"\t paytype:"+payType+"\t cardNo:"+cardNo+"\t password:"+password);
			WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
			if(dict==null){
				 dict=weixinMallMartService.queryMerchantParam(brandId) ;
			}
			//返回package json
			String outTradeNo = DateUtil.get12Date()+CommonUtil.CreateNoncestr();//生成商户订单号，前14位时间，后面16位随机数
			String nonceTr = CommonUtil.CreateNoncestr();
			integral =integral==null?0L:integral;
			WxMallOrder wxorder  = new WxMallOrder();
			wxorder.setAppId(dict.getAppId());
			wxorder.setMobile(member.getPhoneNo());
			wxorder.setOpenId(weixinId);
			wxorder.setPartner(dict.getPartnerId());
			wxorder.setIntegral(integral);//积分
			wxorder.setTotal_fee(totalFee);//
			wxorder.setBuyNum(num);
			wxorder.setOut_trade_no(outTradeNo);
			wxorder.setNonceTr(nonceTr);
			wxorder.setFee_type(1L);
			wxorder.setTrade_mode(1L);
			wxorder.setSign_type("MD5");
			wxorder.setBank_type("WX");
			wxorder.setPayType(payType);//支付方式
			wxorder.setProductInfo(goodInfo);
			wxorder.setGoodsId(goodId);
			wxorder.setBrandId(brandId.longValue());
			wxorder.setSource(6L);//微信为6
			wxorder.setCardNo(cardNo);
			wxorder.setPassword(password);
			
			int masterId = consumeIntegral(cardNo, integral.intValue(), password, brandId.toString());//消积分
			if(masterId!=-1){
				wxorder.setJifenState(masterId!=-1?true:false);
				wxorder.setMasterId(new Long(masterId));
				wxorder.setOrderState(11L);//未完成支付
				int	insertNum = weixinMallMartService.insertMallOrder(wxorder);//生成订单支付流水信息
				if(insertNum>0){
					jsonRes.put("flag", true);
					jsonRes.put("nonceTr", nonceTr);
					jsonRes.put("outTradeNo", outTradeNo);
				}else{
					jsonRes.put("flag", false);
				}
			}else{
				jsonRes.put("flag", false);
			}
			
		}catch(Exception ex){
			log.error("code happen error.",ex);
			jsonRes.put("flag", false);
		}finally{
			response.getWriter().write(jsonRes.toString());
			response.getWriter().flush();
			response.getWriter().close();
		}
	}
	/**获取商品支付信息*/
	@RequestMapping(value="queryProductInfo",method = { RequestMethod.POST, RequestMethod.GET })
	public void queryProductInfo(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") Integer brandId,
			@RequestParam(value = "weixinId", required = false) String weixinId,
			@RequestParam(value = "integral", required = false) Long integral,
			@RequestParam(value = "goodInfo", required = false) String goodInfo,
			@RequestParam(value = "goodId", required = false) Long goodId,
			@RequestParam(value = "num", required = false) Long num,
			@RequestParam(value = "totalFee", required = false) Double totalFee,
			@RequestParam(value = "outTradeNo", required = false) String outTradeNo,
			@RequestParam(value = "nonceTr", required = false) String nonceTr
			){
		logAna.info(brandId+";"+DateUtil.getDate()+";queryProductInfo;"+goodId+";"+num+";"+totalFee+";"+outTradeNo+";");
		try {
			MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
			log.info("brandId:"+brandId+"\t goodInfo:"+goodInfo+"\t totalFee:"+totalFee+"\t goodId:"+goodId+"\t num:"+num);
			WxMallMerchantDict dict= (WxMallMerchantDict) request.getSession().getAttribute("dict");
			if(dict==null){
				 dict=weixinMallMartService.queryMerchantParam(brandId) ;
			}
			//返回package json
			String spbill_create_ip=request.getRemoteAddr();
			String timeStamp=Long.toString(new Date().getTime()/1000);
			
			
			//生成微信支付信息
			String path = request.getContextPath();
			String notify_url = serverIp+path+"/weixin/"+brandId+"/"+num+"/"+member.getPhoneNo()+"/"+goodId+"/"+integral+"/mallPayResult.do"; 
			log.info("notify_url:"+notify_url);
			String packageInfo ="";
			JSONObject jsonRes = new JSONObject();
			DecimalFormat df = new DecimalFormat("#.0");
			String totalfee100=df.format(totalFee*100);
			if("2".equals(String.valueOf(dict.getV2_v3()))){
				wxpayUtil.SetParameter("bank_type", "WX");
				wxpayUtil.SetParameter("body", goodInfo);
				wxpayUtil.SetParameter("partner", dict.getPartnerId());
				wxpayUtil.SetParameter("out_trade_no",outTradeNo );
				wxpayUtil.SetParameter("total_fee", totalfee100.substring(0, totalfee100.length()-2));
				wxpayUtil.SetParameter("fee_type", "1");
				wxpayUtil.SetParameter("notify_url", notify_url); //notify_url
				wxpayUtil.SetParameter("spbill_create_ip", spbill_create_ip);
				wxpayUtil.SetParameter("input_charset", "UTF-8");
				wxpayUtil.setAppid(dict.getAppId());
				wxpayUtil.setAppkey(dict.getAppKey());
				wxpayUtil.setNoncestr(nonceTr);
				wxpayUtil.setPartnerkey(dict.getPartnerKey());
				wxpayUtil.setProductid(dict.getPartnerId());
				wxpayUtil.setTimeStamp(timeStamp);
				packageInfo = wxpayUtil.CreateBizPackage();
				log.info( "v2-packageInfo="+packageInfo);
				jsonRes.put("v2v3flag", "2");
			}else{
				
				String apikey =dict.getPartnerKey();//"sDGYszkxbbBMai7G8Vap7FL4gjwNXhvz";
				String mch_id=dict.getPartnerId();//"10063341";
				String openid=weixinId;
				String unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
				wxPayUtilV3.SetParameter("appid", dict.getAppId());
				wxPayUtilV3.SetParameter("body", goodInfo);
				wxPayUtilV3.SetParameter("mch_id", mch_id);
				wxPayUtilV3.SetParameter("nonce_str", nonceTr);
				wxPayUtilV3.SetParameter("notify_url", notify_url);
				wxPayUtilV3.SetParameter("openid", openid);
				wxPayUtilV3.SetParameter("out_trade_no", outTradeNo);
				wxPayUtilV3.SetParameter("spbill_create_ip",spbill_create_ip);
				wxPayUtilV3.SetParameter("total_fee", totalfee100.substring(0, totalfee100.length()-2));
				wxPayUtilV3.SetParameter("trade_type", "JSAPI");
				wxPayUtilV3.setAppid(dict.getAppId());
				wxPayUtilV3.setApikey( apikey);
				String sign = wxPayUtilV3.createSign(wxPayUtilV3.packageMap);
				wxPayUtilV3.SetParameter("sign", sign);
				String pre_pay_xml = wxPayUtilV3.createXml();
				log.info("v3-pre_pay_xml="+pre_pay_xml);
				String pre_pay_res = CommonUtil.postMessage2(unifiedorder, pre_pay_xml);
				log.info("v3-pre_pay_res="+pre_pay_res);
				InputStream is = null;
				is = new ByteArrayInputStream(pre_pay_res.getBytes());
				Document doc = new SAXReader().read(is);
				String prepay_id=doc.selectSingleNode("/xml/prepay_id").getText();
				log.info("v3-prepay_id="+prepay_id);
				wxPayUtilV3.setPrepay_id(prepay_id);
				packageInfo = wxPayUtilV3.CreateBizPackage();
				log.info("v3-packageInfo="+ packageInfo);
				jsonRes.put("v2v3flag", "3");
			}
			jsonRes.put("outTradeNo", outTradeNo);
			jsonRes.put("order", packageInfo);
			response.getWriter().write(jsonRes.toString());
			response.getWriter().flush();
			response.getWriter().close();
		
		} catch (WeixinRuntimeException e) {
			log.error("code happen error.",e);
		} catch (IOException e) {
			log.error("response code happen error.",e);
		} catch (Exception e) {
			log.error("code happen error.",e);
		}
		
	}
	
}
