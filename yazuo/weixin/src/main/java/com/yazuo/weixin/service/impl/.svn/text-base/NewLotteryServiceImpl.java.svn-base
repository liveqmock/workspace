/**
 * @Description TODO
 * Copyright Copyright (c) 2014 
 * Company 雅座在线（北京）科技发展有限公司
 * 
 * 		author		date		description
 * —————————————————————————————————————————————
 * 
 * 
 */
package com.yazuo.weixin.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yazuo.weixin.exception.WeixinRuntimeException;
import com.yazuo.weixin.service.LotteryItemService;
import com.yazuo.weixin.service.LotteryRecordService;
import com.yazuo.weixin.service.LotteryRuleService;
import com.yazuo.weixin.service.NewLotteryService;
import com.yazuo.weixin.service.WeixinPhonePageService;
import com.yazuo.weixin.util.CommonUtil;
import com.yazuo.weixin.util.Constant;
import com.yazuo.weixin.util.StringUtil;
import com.yazuo.weixin.vo.AwardVO;
import com.yazuo.weixin.vo.MemberAwardVO;
import com.yazuo.weixin.vo.MemberVO;

/**
 * 在线办卡抽奖相关业务
 * @author kyy
 * @date 2014-8-24 上午10:03:19
 */
@Service
public class NewLotteryServiceImpl implements NewLotteryService {
	Logger LOG = Logger.getLogger(NewLotteryServiceImpl.class);

	ResourceBundle bundle = ResourceBundle.getBundle("image-config");
	
	private final String lottery_pic = bundle.getString("caFileIp");
	private final String newPictureUrl = bundle.getString("newPictureUrl"); // 新图片地址 image.yazuo.com
	
	@Value("#{propertiesReader['getValidLotteryCountPath']}")
	private String getValidLotteryCountPath; // 取该账号抽奖剩余次数
	@Value("#{propertiesReader['serverIp']}")
	private String serverIp;
	@Value("#{payPropertiesReader['smsUrl']}")
	private String smsUrl; // 预警发送短信路径
	@Value("#{propertiesReader['queryCouponListByIdList']}")
	private String queryCouponListByIdList; // 办卡送的劵查询接口
	@Resource
	private LotteryRuleService lotteryRuleService;
	@Resource
	private LotteryItemService lotteryItemService;
	@Resource
	private LotteryRecordService lotteryRecordService;
	@Resource
	private WeixinPhonePageService weixinPhonePageService;

	/*
	 * 
	 * <p> Description:幸运抽奖 只有会员才能抽奖（在微信的clink事件去判断，本方法不做判断） </p>
	 */
	public Map<String, Object> luckyDraw(MemberAwardVO member) throws Exception {
//		String weixinId = member.getWeixinId();
		int brandId = member.getBrandId();
		int type = member.getType();
		
		// 组装抽奖信息
		Map<String, Object> map = lotteryRuleService.getCardLotterRule(brandId, 28); // 抽奖规则
		
		String beginTime = StringUtil.isNullOrEmpty(member.getBeginTime()) ? map.get("begin_time")+"" : member.getBeginTime(); // 活动开始时间
		String endTime = StringUtil.isNullOrEmpty(member.getEndTime()) ? map.get("end_time")+"" : member.getEndTime();// 活动结束时间
		// 1:无该活动（或者活动没开始） 2：超过了次数 3:抽奖次数用完抽奖结束 4：中奖了 5：没中奖 6：(可以抽奖)但是没添加奖项
		int winflag = 1;
		// 中奖类
		AwardVO award = new AwardVO();
		if (!StringUtil.isNullOrEmpty(beginTime) && !StringUtil.isNullOrEmpty(endTime)) { // 有活动
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String todayDate = sdf.format(new Date());
			if (!(todayDate.compareTo(beginTime)>=0 && todayDate.compareTo(endTime)<=0)) { //当前时间不在活动范围
				winflag = 1;
			}
			
			award.setLottery_rule_type(type);// 抽奖规则类型
			award.setLottery_rule_id(map.get("id") +"");// 抽奖规则表id
			award.setLottery_rule_title(String.valueOf(map.get("title")));// 抽奖规则头部描述
			award.setLottery_rule_title_pic(map.get("title_pic") + "");// 抽奖规则头部描述图片

			if (type == 1) {// 刮刮卡
				award.setTime_unit(0);
				award.setFrequency(0);
				award.setBegin_time(map.get("begin_time") + "");// 活动开始时间
				award.setEnd_time(map.get("end_time") + "");// 活动结束时间
			}
			int ruleReatainCount = Integer.parseInt(map.get("residue_number").toString()); // 规则中剩余次数
			
			// 取此账号剩余抽奖次数
			String input = "{'merchantId':"+brandId+",'membershipId':"+member.getMembershipId()+",'source':1}";
			JSONObject json = convertData(getValidLotteryCountPath, input, Constant.KEY+"");
			if (json != null) { 
				Boolean success = json.getBoolean("success");// 接口调用是否成功标志位
				String message = json.getString("message"); // 返回信息
				LOG.info("result message:"+message + "; success:" + success);
				if (success) {// 成功
					JSONObject lotteryObj = json.getJSONObject("membershipLottery");
					int count = lotteryObj.getInt("countValid"); // 剩余次数
					award.setRemainCount(count); // 剩余次数
					award.setLasttime(ruleReatainCount ==1 ? 1 :2); // 是否最后一次
					
					if (count > 0 && ruleReatainCount >0) { // 还可以抽奖
						// 抽奖
						winflag = isWonLottery(member, award, count, map);
					} else {
						winflag=3;
					}
				} else {
					winflag = 2;
				}
			}
		}

		Map<String, Object> remap = new HashMap<String, Object>();
		// 大转盘角度
		getDegrees(award, winflag);
		remap.put("winflag", winflag);// 中奖状态
		remap.put("award", award);// 奖项信息
		remap.put("member", member);// 人员信息
		remap.put("lottery_pic", lottery_pic);// 图片地址
		remap.put("newPictureUrl", newPictureUrl); // 新图片地址
		return remap;
	}

	public Map<String, Object> whetherLuckyDraw(MemberAwardVO member) throws Exception {
//		String weixinId = member.getWeixinId();
		int brandId = member.getBrandId();
//		int type = member.getType();
		
		// 组装抽奖信息（取办卡活动规则）
		Map<String, Object> map = lotteryRuleService.getCardLotterRule(brandId, 28);
		
		String beginTime = StringUtil.isNullOrEmpty(member.getBeginTime()) ? map.get("begin_time")+"" : member.getBeginTime(); // 活动开始时间
		String endTime = StringUtil.isNullOrEmpty(member.getEndTime()) ? map.get("end_time")+"" : member.getEndTime();// 活动结束时间
		Map<String, Object> remap = new HashMap<String, Object>();
		// 1:无该活动（或者活动没开始） 2：超过了次数 3:抽奖次数用完抽奖结束 4：中奖了 5：没中奖 6：(可以抽奖)但是没添加奖项
		int winflag = 1;
		// 中奖类
		if (!StringUtil.isNullOrEmpty(beginTime) && !StringUtil.isNullOrEmpty(endTime)) { // 有活动
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String todayDate = sdf.format(new Date());
			if (!(todayDate.compareTo(beginTime)>=0 && todayDate.compareTo(endTime)<=0)) { //当前时间不在活动范围
				winflag = 1;
			}
			
			// 抽奖规则表id
			String lottery_rule_id = map.get("id") + "";
			// 奖品列表
			List<Map<String, Object>> list = lotteryItemService.getLotteryItem(lottery_rule_id);
			
			remap.put("list", list);
			remap.put("time_unit", 0);// 规则时间间隔1：一共总次数2：每天
			remap.put("frequency", 0);// 规则周期次数 例如：3就是每天3次
			remap.put("begin_time", map.get("begin_time") + "");// 活动开始时间
			remap.put("end_time", map.get("end_time") + "");// 活动结束时间
			remap.put("title", String.valueOf(map.get("title")));// 抽奖规则头部描述
			remap.put("title_pic", map.get("title_pic") + "");// 抽奖规则头部描述图片
			
			int ruleReatainCount = Integer.parseInt(map.get("residue_number").toString()); // 规则中剩余次数
			
			// 取此账号剩余抽奖次数
			String input = "{'merchantId':"+brandId+",'membershipId':"+member.getMembershipId()+",'source':1}";
			JSONObject json = convertData(getValidLotteryCountPath, input, Constant.KEY+"");
			if (json != null) { 
				Boolean success = json.getBoolean("success");// 接口调用是否成功标志位
				String message = json.getString("message"); // 返回信息
				LOG.info("result message:"+message + "; success:" + success);
				if (success) {// 成功
					JSONObject lotteryObj = json.getJSONObject("membershipLottery");
					int count = lotteryObj.getInt("countValid"); // 剩余次数
					remap.put("number", count);// 剩余抽奖次数
					if (count > 0 && ruleReatainCount >0) { // 还可以抽奖
						if (list != null && list.size() > 0) {// 设置奖品
							remap.put("length", list.size());
							winflag = 4;
						} else {// 没设置奖项
							winflag = 5;
						}
					} else {
//						winflag = 3;
						winflag = 2;
					}
				} else {
					winflag = 2;
				}
			}
		}
		remap.put("winflag", winflag);
		remap.put("lottery_pic", lottery_pic);// 图片地址
		remap.put("newPictureUrl", newPictureUrl); // 新图片地址
		return remap;
	}

	/*
	 * 
	 * <p> Description:抽奖逻辑根据规则id和允许抽取的次数 处理用户是否中奖了</p>
	 */
	@Transactional(rollbackFor=Exception.class)
	private synchronized int isWonLottery(MemberAwardVO member, AwardVO award,
			int residue_number, Map<String, Object> ruleMap) throws Exception {
		// 4：中奖了 5：没中奖 6：(可以抽奖)但是没添加奖项
		int winflag = 5;

		Random r = new Random();
		// 中奖随机数
		int ratio = r.nextInt(residue_number) + 1;

		// 根据规则id查找奖品列表（必须是按等级升序排列）
		List<Map<String, Object>> list = lotteryItemService
				.getLotteryItem(award.getLottery_rule_id());
		if (list != null && list.size() > 0) {
			// 中奖数字开始区间
			int wonBeginNum = 0;
			// 中奖数字结束区间
			int wonEndNum = 0;
			// 一共有几个等级的奖项
			award.setItem_seq_count(list.size());

			for (Map<String, Object> map : list) {
				// 奖品剩余数量
				int lottery_item_remain = Integer.parseInt(map
						.get("lottery_item_remain") + "");

				wonEndNum = wonEndNum + lottery_item_remain;
				wonBeginNum = wonEndNum - lottery_item_remain;

				if (ratio > wonBeginNum && ratio <= wonEndNum) {// 表示中奖了
					// 奖项id
					int id = Integer.parseInt(map.get("id") + "");
					// 奖项名称
					String lottery_item_name = map.get("lottery_item_name")
							+ "";
					// 奖项图片名称
					String picName = map.get("lottery_item_pic") + "";
					// 奖项等级
					int lottery_item_seq = Integer.parseInt(map
							.get("lottery_item_seq") + "");

					// 中奖vo
					award.setLottery_item_id(id);
					award.setLottery_item_seq(lottery_item_seq);
					award.setLottery_item_name(lottery_item_name);
					award.setLottery_coupon_name(map.get("lottery_coupon_name")+""); // 奖品名称
					award.setLottery_item_pic(picName);
					award.setLottery_item_type(Integer.parseInt(map.get("lottery_item_type").toString()));
					winflag = 4;// 中奖了
					break;
				}
			}

		} else {
			winflag = 6;// 6：(可以抽奖)但是没添加奖项
		}
		
		//机会减1,只要抽奖不管中没中机会数都要减1
		String input = "{'merchantId':"+member.getBrandId()+",'membershipId':"+member.getMembershipId()+",'source':1,'count':-1}";
		String result = CommonUtil.postSendMessage(getValidLotteryCountPath, input, Constant.KEY+"");
		LOG.info("机会减1接口返回值：" + result);
		int remtainCount = award.getRemainCount(); //剩余次数
		if(!StringUtil.isNullOrEmpty(result)) { // 抽奖减抽奖机会成功后，变量中的剩余数减1
			JSONObject backJson = JSONObject.fromObject(result);
			boolean success = backJson.getJSONObject("data").getJSONObject("result").getBoolean("success");
			if (success)
				remtainCount = remtainCount -1;
		}
		award.setRemainCount(remtainCount); //剩余次数赋值
		
		if (winflag == 4) {// 中奖保存信息
			if(award.getLottery_item_type() ==1){ //券
				winflag = lotteryRecordService.insertLotteryRecord(member, award, true);
			}else{ // 实物
				// 插入中奖记录
				winflag = lotteryRecordService.insertLotteryRecord(member, award);
			}
		} else if (winflag == 5) {// 没中奖保存信息
			lotteryRecordService.insertLotteryRecord(member, award, false);
		}
		
		// 短信预警
		LOG.info("活动规则查询结果：" + ruleMap);
		if(ruleMap!=null && ruleMap.size()>0)
			this.smsWarning(ruleMap); 
		LOG.info("------------------预警结束----------------------");
		return winflag;
	}

	@Override
	public String luckRedirectPath(String weixinId, Integer brandId, String path) {
		// 会员信息
		MemberVO member = weixinPhonePageService.getMemberByWeixinIdAndBrandId(brandId, weixinId);
		// 查询活动规则表
		Map<String, Object> map = lotteryRuleService.getCardLotterRule(brandId, 28);
		LOG.info("活动规则查询结果：" + map);
//		// 处理预警的情况（机会会低于预警数短信通知商户）
//		if(map!=null && map.size()>0)smsWarning(map);
//		LOG.info("------------------预警结束---------------------");
		
		int type = map !=null ? Integer.parseInt(String.valueOf(map.get("type"))) : 0; // 1
		String name = "";
		if (type == 1) {// 刮刮卡
			name = "scratchCard.do?type=1";
		} else if (type == 2) {// 大转盘
			name = "luckRoulette.do?type=2";
		} else if (type == 3) {// 老虎机
			name = "slotMachine.do?type=3";
		}
		StringBuilder url = new StringBuilder(serverIp);
		url.append(path).append("/caffe/cardLottery/");
		if (StringUtil.isNullOrEmpty(name)) { // 如果为空就跳转到错误页面
			url.append("errorPage.do");
			return url.toString();
		}
		url.append(name).append("&brandId=").append(brandId).append("&weixinId=").append(
			weixinId).append("&membershipId=").append(member.getMembershipId()).append("&phoneNo=").append(member.getPhoneNo());
		return url.toString();
	}

	
	@Override
	public void smsWarning(Map<String, Object> ruleMap) {
		// 处理预警的情况
		int residueNumber = Integer.parseInt(ruleMap.get("residue_number").toString()); // 取剩余次数
		int monitorNumber = Integer.parseInt(ruleMap.get("monitor_number").toString());// 取预警次数
		int isSendSms = Integer.parseInt(ruleMap.get("is_send_sms").toString()); // 是否已下发短信通知
		if (residueNumber <=monitorNumber && isSendSms ==0) { // 剩余次数小于等于预警次数且未下发短信
			Integer ruleId = (Integer)ruleMap.get("id");
			// 预警人信息
			String monitorStr = lotteryRuleService.getMonitorMsgByRuleId(ruleId);
			LOG.info("------------------------------收短信人：" +monitorStr);
			if (!StringUtil.isNullOrEmpty(monitorStr)) {
				String warncontent = "雅座CRM用户您好，caffebene中国微信幸运抽奖活动的抽奖机会数已不足"+monitorNumber+"个，请尽快调整机会数和奖品数量，以保证微信用户可正常抽奖。";
				//发送短信内容
				String jsonpar = "{'mobile':'" + monitorStr + "','content':'" + warncontent
		                 + "','merchant_id':'"+ ruleMap.get("brand_id")+"','source':'8'}";
				LOG.info("预警短信已发送！短信内容："+warncontent);
				try {
					CommonUtil.postSendMessage(smsUrl,jsonpar,Constant.KEY.toString());//发送短信
				} catch (Exception e) {
					LOG.error("code happen error.",e);
				}
				// 更新活动规则中是否
				lotteryRuleService.updateLotterRuleSmsStatus(ruleId);
			}
		}
	}

	/*
	 * 
	 * <p> Description:根据是否中奖和奖项设置判断转动角度数</p>
	 */
	private void getDegrees(AwardVO award, int winflag) {

		int type = award.getLottery_rule_type();
		if (type == 2) {// 大转盘
			Random r = new Random();
			int count = award.getItem_seq_count();// 一共有几个奖项
			int itemseq = award.getLottery_item_seq();// 奖品等级
			if (winflag == 4) {// 中奖了

				if (count == 1) {// 1个奖项
					int[] degrees = { 68, 157, 247, 337 };
					// 中奖随机角度
					award.setDegrees(degrees[r.nextInt(4)]);

				} else if (count == 2) {// 2个奖项

					if (itemseq == 1) {// 1等奖
						int[] degrees = { 157, 337 };
						award.setDegrees(degrees[r.nextInt(2)]);

					} else if (itemseq == 2) {// 2等奖
						int[] degrees = { 68, 247 };
						award.setDegrees(degrees[r.nextInt(2)]);
					}

				} else if (count == 3) {// 3个奖项
					if (itemseq == 1) {// 1等奖
						int[] degrees = { 23, 202 };
						award.setDegrees(degrees[r.nextInt(2)]);

					} else if (itemseq == 2) {// 2等奖
						int[] degrees = { 68, 247 };
						award.setDegrees(degrees[r.nextInt(2)]);
					} else if (itemseq == 3) {// 3等奖
						int[] degrees = { 113, 292 };
						award.setDegrees(degrees[r.nextInt(2)]);
					}
				}

			} else {// 没中奖

				if (count == 1 || count == 2) {// 1个或2个奖项

					int[] degrees = { 23, 113, 202, 292 };
					// 未中奖随机角度
					award.setDegrees(degrees[r.nextInt(4)]);

				} else if (count == 3) {// 3个奖项

					int[] degrees = { 157, 337 };
					// 未中奖随机角度
					award.setDegrees(degrees[r.nextInt(2)]);
				}

			}
		}
	}
	
	// 调用crm接口，将json格式数据转换为json对象
		private JSONObject convertData (String url, String input, String key) {
			String result = "";
			try {
				result = CommonUtil.postSendMessage(url, input, key);
			} catch (WeixinRuntimeException e) {
				e.printStackTrace();
			}
			//log.info("调用crm接口返回结果:" + result+"+++---------------------");
			if (!StringUtil.isNullOrEmpty(result)) { // 判断不为空
				JSONObject requestObject = JSONObject.fromObject(result);
				String data = "";
				JSONObject obj = (JSONObject) requestObject.get("data");
				try {
					if (obj !=null && obj.get("result")!=null && !StringUtil.isNullOrEmpty(obj.get("result").toString())) {
						data = URLDecoder.decode(obj.get("result").toString(), "UTF-8");
					}
					//log.info("result:" + data+"---------------------");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				return JSONObject.fromObject(data);
			}
			return null;
		}

		@Override
		public Map<String, Object> getSendActivity(String couponIdStr, String luckCount, Integer brandId) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (!StringUtil.isNullOrEmpty(luckCount) && !luckCount.equals("0.00")) { // 送的抽奖机会数
				double count = Double.parseDouble(luckCount);
				map.put("luckCount", (int)count);
				map.put("isGiveLuckCount", true);
			} 
			if (!StringUtil.isNullOrEmpty(couponIdStr)){ //送的劵
				String [] splitArray = couponIdStr.split(",");
				List<Integer> list = new ArrayList<Integer>();
				for(int i =0; i<splitArray.length; i++) {
					list.add(Integer.parseInt(splitArray[i]));
				}
				// 调用crm接口查询劵信息
				String input = "{'merchantId':"+brandId+", 'idList':"+list+"}";
				String result="";
				try {
					result = CommonUtil.postSendMessage(queryCouponListByIdList, input, Constant.KEY+"");
				} catch (WeixinRuntimeException e) {
					e.printStackTrace();
				}
				JSONArray array = new JSONArray();
				if (!StringUtil.isNullOrEmpty(result)) {
					JSONObject json = JSONObject.fromObject(result);
					JSONObject resultObj = json.getJSONObject("data").getJSONObject("result");
					boolean success = resultObj.getBoolean("success");
					if (success) {
						array = resultObj.getJSONArray("couponList");
					}
				}
				map.put("isGiveLuckCount", false);
				map.put("couponList", array);
			}
			return map;
		}
		
	
}
