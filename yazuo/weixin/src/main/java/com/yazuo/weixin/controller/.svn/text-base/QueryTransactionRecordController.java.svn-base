package com.yazuo.weixin.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yazuo.weixin.es.service.MemberShipCenterService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.DateUtil;
import com.yazuo.weixin.vo.PageVO;
import com.yazuo.weixin.vo.TransWaterPage;

/**
 * 交易查询控制类
 * 
 * @author gaoshan
 * 
 */
@Controller
@RequestMapping("/weixin/transactionRecord")
public class QueryTransactionRecordController {

	Logger log = Logger.getLogger(this.getClass());

	private static final Log LOGS = LogFactory.getLog("statistical");
	/**查询交易流水*/
	@Value("#{propertiesReader['getTransWaterSumByCardNo']}")
	private String getTransWaterSumByCardNoUrl;
	@Value("#{propertiesReader['addEvaluate']}")
	private String addEvaluateUrl;
	@Value("#{propertiesReader['modifyEvaluate']}")
	private String modifyEvaluateUrl;
	@Resource
	private MemberShipCenterService memberShipCenterService;
	
	/**
	 * add by sundongfeng 2014-07-17最新查交易流水
	 * @param cardno 会员卡号
	 * @param membershipId
	 * @param merchantId
	 * @param beginTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "queryTransactionRecord", method = {
			RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Object queryTransactionRecord(
			@RequestParam(value = "cardno", required = true) String cardno,
			@RequestParam(value = "membershipId", required = true) String membershipId,
			@RequestParam(value = "merchantId", required = true) String merchantId,
			@RequestParam(value = "beginTime", required = false) String beginTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			HttpServletRequest request, HttpServletResponse response,
			PageVO page) {
		List<TransWaterPage> list= new ArrayList<TransWaterPage>();
		SimpleDateFormat simpleFormate2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		try {
			// 加入统计日志
			if (membershipId != null && merchantId != null) {
				LOGS.info(";" + DateUtil.getDate4d() + ";" + merchantId + ";"
						+ membershipId + ";2");
			}
			/*Map<String,Object> map = new HashMap<String,Object>();
			map.put("cardNo",cardno);
			map.put("merchantId",merchantId);
			map.put("page",	 page.getPageindex());
			map.put("size",	 page.getPagesize());
			map.put("evaluate",	 true);
			String input = JSONObject.fromObject(map).toString();
			
			String result = CommonUtil.postSendMessage(getTransWaterSumByCardNoUrl, input, Constant.ENTITYKEY.toString());
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			JSONObject res=JSONObject.fromObject(data);
			if (res != null) { 
				Boolean success = res.getBoolean("success");// 接口调用是否成功标志位
				String message = res.getString("message"); // 返回信息
				log.info("接口返回message："+message);
				if (success) {// 成功
					JSONObject waterPage = res.getJSONObject("transWaterPage");
					int allcount= waterPage.getInt("total");
					int allpage= waterPage.getInt("page");
					page.setAllcount(allcount);
					page.setAllpage(allpage);
					int records= waterPage.getInt("records");
					JSONArray array = waterPage.getJSONArray("rows");
					for(Iterator iter = array.iterator();iter.hasNext();){
						JSONObject jsonObject = (JSONObject) iter.next();
						TransWaterPage mapeve =  (TransWaterPage) JSONObject.toBean(jsonObject, TransWaterPage.class);
						long transtime = simpleFormate2.parse(mapeve.getTransTime()).getTime();
						long nowtime = new Date().getTime();
						boolean out = (nowtime-transtime)/1000>((long)(30*24*60*60));
						mapeve.setOut(out);
						list.add(mapeve);
					}
				}
			}
			Map<String, Object> resmap = new HashMap<String, Object>();
			resmap.put("page", page);
			resmap.put("list", list);*/
			Map<String, Object> resmap = memberShipCenterService.queryTransWaterRecordByEs(cardno, membershipId, merchantId, page);
			return resmap;
		} catch (Exception e) {
			log.error("微信会员卡交易查询异常", e);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", null);
			map.put("page", null);
			return map;
		}
	}
	/**
	 * 保存消费评价
	 * @param twid
	 * @param commentStar
	 * @param commentTxt
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveTransactionRecord", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void saveTransactionRecord(
			@RequestParam(value = "twid", required = true) Integer twid,
			@RequestParam(value = "commentStar", required = true) Integer commentStar,
			@RequestParam(value = "commentTxt", required = true) String commentTxt,
			HttpServletRequest request, HttpServletResponse response) {
			String message ="";
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("content",commentTxt);
			map.put("type",1);
			map.put("score",commentStar);
			map.put("taste_mark",commentStar);
			map.put("environment_mark",commentStar);
			map.put("service_mark",commentStar);
			map.put("impress","");
			map.put("transWaterId",	twid);
			String input = JSONObject.fromObject(map).toString();
			
			String result = CommonUtil.postSendMessage(addEvaluateUrl, input, Constant.ENTITYKEY.toString());
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			JSONObject res=JSONObject.fromObject(data);
			log.info("接口返回："+res.toString());
			if (res != null) { 
				message =  res.toString();
			}
		} catch (Exception e) {
			log.info("code error.",e);
			message =  "{'success':false,'message':'评价失败'}";
		}
		try {
			log.info(message);
			response.getWriter().write(JSONObject.fromObject(message).toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			log.error("response date error.",e);
		} 
	}
	
	
	/**
	 * 修改消费评价
	 * @param twid 交易流水id
	 * @param commentStar 几颗星
	 * @param commentTxt 评价内容
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateTransactionRecord", method = {
			RequestMethod.POST, RequestMethod.GET })
	public void updateTransactionRecord(
			@RequestParam(value = "evaluateId", required = true) Integer evaluateId,
			@RequestParam(value = "commentStar", required = true) Integer commentStar,
			@RequestParam(value = "commentTxt", required = true) String commentTxt,
			HttpServletRequest request, HttpServletResponse response) {
				String message ="";
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id",evaluateId); //评价id
			map.put("content",commentTxt);
			map.put("type",1);
			map.put("score",commentStar);
			map.put("taste_mark",commentStar);
			map.put("environment_mark",commentStar);
			map.put("service_mark",commentStar);
			map.put("impress","");
			String input = JSONObject.fromObject(map).toString();
			
			String result = CommonUtil.postSendMessage(modifyEvaluateUrl, input, Constant.ENTITYKEY.toString());
			JSONObject requestObject = JSONObject.fromObject(result);
			String data = URLDecoder.decode(((JSONObject) requestObject.get("data")).get("result").toString(), "UTF-8");
			JSONObject res=JSONObject.fromObject(data);
			if (res != null) { 
				log.info("接口返回："+res.toString());
				message =  res.toString();
			}
		} catch (Exception e) {
			log.info("code error.",e);
			message =  "{'success':false,'message':'评价失败'}";
		}
		try {
			log.info(message);
			response.getWriter().write(JSONObject.fromObject(message).toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			log.error("response date error.",e);
		} 
	}
}