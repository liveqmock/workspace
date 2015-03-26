package com.yazuo.weixin.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.service.WeixinPayService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.MailUtil;
import com.yazuo.weixin.vo.WxOrder;
import com.yazuo.weixin.vo.WxPayCard;
import com.yazuo.weixin.vo.WxPayCouponState;

/**
* @ClassName WeixinXldBuQuanController
* @Description  新辣道补券程序
* @author sundongfeng@yazuo.com
* @date 2014-7-1 上午10:36:13
* @version 1.0
*/
@Controller
@Scope("prototype")
@RequestMapping("/weixin/{brandId}")
public class WeixinXldBuQuanController {
	private static final Log log = LogFactory.getLog("wxpay");
	@Value("#{payPropertiesReader['xld.key']}")
	private String xldKey;
	@Value("#{payPropertiesReader['xld.activeId']}")
	private String activeCode;
	@Value("#{payPropertiesReader['xld.appId']}")
	private String appid;
	@Value("#{payPropertiesReader['xld.appKey']}")
	private String appkey;
	@Value("#{payPropertiesReader['xld.appSecret']}")
	private String appSecret;
	@Value("#{payPropertiesReader['xld.partnerId']}")
	private String productid;
	@Value("#{payPropertiesReader['xld.partnerKey']}")
	private String partnerkey;
	@Value("#{payPropertiesReader['xld.optainTicketsUrl']}")
	private String optainTicketsUrl;
	@Value("#{payPropertiesReader['xld.optainTicketInfoUrl']}")
	private String optainTicketInfoUrl;
	@Value("#{payPropertiesReader['xld.mailUrl']}")
	private String mailUrl;
	@Value("#{payPropertiesReader['smsUrl']}")
	private String smsUrl;
	@Value("#{payPropertiesReader['xld.mails']}")
	private String mails;
	@Value("#{payPropertiesReader['xld.subject']}")
	private String subject;
	@Value("#{payPropertiesReader['xld.error']}")
	private String error;
	
	@Autowired
	private WeixinPayService weixinPayService;
	
	/**
	 * 查询所有支付订单
	 * @param request
	 * @param brandId 
	 * @param all 是否查询全部
	 * @param model
	 * @return
	 */
	@RequestMapping(value="queryOrders", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String queryOrders(HttpServletRequest request,
			@PathVariable(value = "brandId") String brandId,
			ModelMap model
			){
		try {
			List<WxOrder> list= weixinPayService.queryOrders(brandId);
			int size = 0;
			if(list!=null&&list.size()>0){
				size = list.size();
			}
			model.addAttribute("size", size);
			model.addAttribute("orderList", list);
		} catch (WeixinRuntimeException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		}
		return "wx-xldNoQuanOrderQuery"; //跳转火锅列表
	}
	/**
	 * 补券方法
	 * @param request
	 * @param response
	 * @param brandId
	 * @param mobile
	 * @param totalFee
	 * @param outTradeNo
	 * @param cardId
	 * @param num
	 * @param openId
	 * @return
	 */
	@RequestMapping(value="buQuan", method = { RequestMethod.POST,
			RequestMethod.GET })
	public void buQuan(HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "brandId") String brandId,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "tranid", required = false) String tranid,
			@RequestParam(value = "outTradeNo", required = false) String outTradeNo,
			@RequestParam(value = "cardId", required = false) String cardId,
			@RequestParam(value = "num", required = false) String num,
			@RequestParam(value = "openId", required = false) String openId
			){
		response.setCharacterEncoding("utf-8");
		String error ="补券失败";
		log.info("mobile:"+mobile+" tranid:"+tranid+" outTradeNo:"+outTradeNo+" cardId:"+cardId+" num:"+num+" openId:"+openId);
		boolean isHas = weixinPayService.queryHasTicketsCard(outTradeNo);
		log.info("是否有券了:"+isHas);
		log.info("生成券信息，入库，调用李斌接口获取券的信息.");
		List<WxPayCard> cardList = new ArrayList<WxPayCard>();
		WxPayCouponState pcsvo = new WxPayCouponState();//记录券状态
		pcsvo.setMobile(mobile);
		pcsvo.setTotalFee((Long)(159*new Long(num)));
		pcsvo.setCounter(new Long(num));
		pcsvo.setCardId(cardId);
		pcsvo.setTradeState("0");
		pcsvo.setTransactionId(tranid);
		pcsvo.setPartnerId(brandId);
		pcsvo.setOpenId(openId);
		pcsvo.setOutTradeNo(outTradeNo);
		pcsvo.setCardState("0");//先暂时插入0失败状态
		try{
			if(StringUtils.isNotBlank(mobile)&& !isHas){ //无券信息才执行
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put("count", num);
			jsonMap.put("merchantId", brandId);
			jsonMap.put("mobile", mobile);
			jsonMap.put("couponId", cardId);//券id
			String input = JSONObject.fromObject(jsonMap).toString();
			log.info("payResult.json:"+input);
			
			String result = CommonUtil.postSendMessage(optainTicketsUrl, input, xldKey);
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			JSONObject res=JSONObject.fromObject(data);
				if (res != null) { 
					Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
					String message = res.getString("message"); // 返回信息
					log.info("result message:"+message);
					if (success) {// 成功
						pcsvo.setCardState("1"); //券状态为1
						StringBuffer sb = new StringBuffer();
						JSONArray array = res.getJSONArray("couponList");
						for(Iterator iter = array.iterator();iter.hasNext();){
							JSONObject jsonObject = (JSONObject) iter.next();
							WxPayCard card = new WxPayCard();
							card.setBrandId(brandId);
							String resId = jsonObject.getString("id");
							sb.append(resId).append(",");
							card.setRes_card_no(resId);
							card.setCardId(cardId);
							card.setMobile(mobile);
							card.setOpenId(openId);
							card.setOutTradeNo(outTradeNo);
							card.setPartnerId(productid);
							cardList.add(card);
						}
						log.info("返回card_no:"+sb);
						log.info("生成券数量:"+cardList.size());
						//生成券信息入库
						weixinPayService.inserCardInfo(cardList);//插入绑定卡信息
						error ="补券成功";
					}
				}
			}else{
				error="券已经有了。";
			}
		}catch(Exception ex){
			log.error("code happen error.",ex);
		}finally{
			try {
				boolean hasCouponState = weixinPayService.queryHasBindCoupon(pcsvo.getOutTradeNo());
				if(!hasCouponState){
					int a =	weixinPayService.insertCouponState(pcsvo);//插入券状态
					log.info("插入券状态成功:"+(a>0));
					if("0".equals(pcsvo.getCardState())){
						String content = "用户手机:"+pcsvo.getMobile()+"\n"+
							"购买券Id:"+pcsvo.getCardId()+"\n"+
							"购买数量:"+pcsvo.getCounter()+"\n"+
							"购买金额:"+pcsvo.getTotalFee()+"\n"+
							"商户订单号:"+pcsvo.getOutTradeNo()+"\n"+
							"银行订单号:"+pcsvo.getTransactionId()+"\n"+
							"商户brandId:"+pcsvo.getPartnerId()+"\n"+
							"用户wxId:"+pcsvo.getOpenId()+"\n";
						String[] targetMails = mails.split(",");
						//失败发送邮件
						MailUtil.sendMail(Arrays.asList(targetMails),subject,content);
					}
				}else{
					if("1".equals(pcsvo.getCardState())){
						weixinPayService.updateWeixin_pay_coupon_state(pcsvo);
					}
				}
			} catch (Exception e) {
				log.error("插入券状态 code happen error.",e);
				e.printStackTrace();
			}
		}
		try {
			response.getWriter().print(error);
		} catch (IOException e) {
			log.error("code happen error.",e);
			e.printStackTrace();
		}
	}
}
